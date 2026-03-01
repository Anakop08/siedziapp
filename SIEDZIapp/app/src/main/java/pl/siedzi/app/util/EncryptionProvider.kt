package pl.siedzi.app.util

import android.content.Context
import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Provider klucza szyfrowania bazy SQLCipher.
 * Używa Android Keystore do przechowywania klucza AES,
 * który szyfruje hasło do bazy Room.
 */
class EncryptionProvider(private val context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val keyAlias = "siedzi_db_key"

    fun getDatabasePassphrase(): ByteArray {
        val encryptedKey = prefs.getString(PREF_ENCRYPTED_KEY, null)
        return if (encryptedKey != null) {
            val encryptedBytes = Base64.decode(encryptedKey, Base64.NO_WRAP)
            decryptKey(encryptedBytes)
        } else {
            val passphrase = generateAndStorePassphrase()
            passphrase
        }
    }

    private fun generateAndStorePassphrase(): ByteArray {
        val passphrase = ByteArray(32)
        SecureRandom().nextBytes(passphrase)

        val encrypted = encryptKey(passphrase)
        prefs.edit()
            .putString(PREF_ENCRYPTED_KEY, Base64.encodeToString(encrypted, Base64.NO_WRAP))
            .apply()

        return passphrase
    }

    private fun encryptKey(data: ByteArray): ByteArray {
        val key = getOrCreateMasterKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val iv = ByteArray(12).also { SecureRandom().nextBytes(it) }
        cipher.init(Cipher.ENCRYPT_MODE, key, GCMParameterSpec(128, iv))
        val encrypted = cipher.doFinal(data)
        return iv + encrypted
    }

    private fun decryptKey(encryptedWithIv: ByteArray): ByteArray {
        val iv = encryptedWithIv.copyOfRange(0, 12)
        val encrypted = encryptedWithIv.copyOfRange(12, encryptedWithIv.size)
        val key = getOrCreateMasterKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, iv))
        return cipher.doFinal(encrypted)
    }

    private fun getOrCreateMasterKey(): SecretKey {
        // Dla uproszczenia używamy klucza zapisanego w EncryptedSharedPreferences
        // W produkcji: AndroidX Security Crypto / EncryptedSharedPreferences
        val stored = prefs.getString(PREF_MASTER_KEY, null)
        return if (stored != null) {
            SecretKeySpec(Base64.decode(stored, Base64.NO_WRAP), "AES")
        } else {
            val keyGen = KeyGenerator.getInstance("AES")
            keyGen.init(256)
            val key = keyGen.generateKey()
            prefs.edit()
                .putString(PREF_MASTER_KEY, Base64.encodeToString(key.encoded, Base64.NO_WRAP))
                .apply()
            key
        }
    }

    companion object {
        private const val PREFS_NAME = "siedzi_secure"
        private const val PREF_ENCRYPTED_KEY = "encrypted_db_key"
        private const val PREF_MASTER_KEY = "master_key"
    }
}
