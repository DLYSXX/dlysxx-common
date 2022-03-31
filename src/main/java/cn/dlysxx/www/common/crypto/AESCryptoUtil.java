package cn.dlysxx.www.common.crypto;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.io.FileUtils;

/**
 * Class to encrypt/decrypt file or string in AES.
 *
 * @author yukut
 **/
public class AESCryptoUtil {

    /**
     * Encrypt plain text. IMPORTANT: text should use UTF-8.
     *
     * @param plainText UTF-8 encoded text
     * @param password  AES password
     * @return encrypted data byte array
     * @throws InvalidKeyException                Invalid Key Exception
     * @throws InvalidAlgorithmParameterException Invalid Algorithm Parameter Exception
     * @throws IllegalBlockSizeException          Illegal Block Size Exception
     * @throws BadPaddingException                Bad Padding Exception
     */
    public static byte[] encryptString(String plainText, String password)
        throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] textByte = plainText.getBytes(StandardCharsets.UTF_8);
        return AESCryptoUtil.encrypt(textByte, password);
    }

    /**
     * Decrypt AES encrypted String. IMPORTANT: text should use UTF-8.
     *
     * @param encrypted encrypted String with UTF-8 encoded
     * @param password  AES password
     * @return decrypted byte array
     * @throws InvalidKeyException                Invalid Key Exception
     * @throws InvalidAlgorithmParameterException Invalid Algorithm Parameter Exception
     * @throws IllegalBlockSizeException          Illegal Block Size Exception
     * @throws BadPaddingException                Bad Padding Exception
     */
    public static byte[] decryptString(String encrypted, String password)
        throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        return AESCryptoUtil.decrypt(Base64.getDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), password);
    }

    /**
     * Encrypt file in AES.
     *
     * @param inputFilePath  input file path
     * @param outputFilePath encrypted file path
     * @param password       AES password
     * @throws InvalidKeyException                Invalid Key Exception
     * @throws InvalidAlgorithmParameterException Invalid Algorithm Parameter Exception
     * @throws IllegalBlockSizeException          Illegal Block Size Exception
     * @throws BadPaddingException                Bad Padding Exception
     * @throws IOException                        IOException
     */
    public static void encryptFile(String inputFilePath, String outputFilePath, String password)
        throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
        IOException {
        File input = new File(inputFilePath);
        byte[] content = FileUtils.readFileToByteArray(input);
        byte[] encrypted = AESCryptoUtil.encrypt(content, password);

        FileUtils.writeByteArrayToFile(new File(outputFilePath), encrypted);
    }

    /**
     * Decrypt file in AES.
     *
     * @param inputFilePath  encrypted file path
     * @param outputFilePath output decrypted file path
     * @param password       AES password
     * @throws IOException                        IOException
     * @throws InvalidKeyException                Invalid Key Exception
     * @throws InvalidAlgorithmParameterException Invalid Algorithm Parameter Exception
     * @throws IllegalBlockSizeException          Illegal Block Size Exception
     * @throws BadPaddingException                Bad Padding Exception
     */
    public static void decryptFile(String inputFilePath, String outputFilePath, String password)
        throws IOException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
        BadPaddingException {
        byte[] encryptedData = FileUtils.readFileToByteArray(new File(inputFilePath));
        byte[] decrypted = AESCryptoUtil.decrypt(encryptedData, password);
        FileUtils.writeByteArrayToFile(new File(outputFilePath), decrypted);
    }

    private static void getKeyAndGenerateIv(String password, byte[] salt, byte[] keyBytes, byte[] ivBytes) {
        try {
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            int length = passwordBytes.length + salt.length;
            ByteBuffer byteBuffer = ByteBuffer.allocate(length);
            byteBuffer.put(passwordBytes);
            byteBuffer.put(salt);
            byteBuffer.rewind();
            byte[] byteArray = new byte[length];
            byteBuffer.get(byteArray);
            System.arraycopy(MessageDigest.getInstance("MD5").digest(byteArray), 0, keyBytes, 0, keyBytes.length);
            length = passwordBytes.length + salt.length + keyBytes.length;
            byteBuffer = ByteBuffer.allocate(length);
            byteBuffer.put(keyBytes);
            byteBuffer.put(passwordBytes);
            byteBuffer.put(salt);
            byteBuffer.rewind();
            byteArray = new byte[length];
            byteBuffer.get(byteArray);
            System.arraycopy(MessageDigest.getInstance("MD5").digest(byteArray), 0, ivBytes, 0, ivBytes.length);
        } catch (NoSuchAlgorithmException e) {
            //This exception should never happen
        }
    }

    private static byte[] encrypt(byte[] plaintext, String password)
        throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] randomBytes = new byte[8];
        new SecureRandom().nextBytes(randomBytes);

        byte[] keyBytes = new byte[16];
        byte[] ivBytes = new byte[16];
        getKeyAndGenerateIv(password, randomBytes, keyBytes, ivBytes);

        SecretKey secret = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            //This Exception should never happen
        }
        assert cipher != null;
        cipher.init(Cipher.ENCRYPT_MODE, secret, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(plaintext);

        final String header_string = "Salted__";
        byte[] headerBytes = header_string.getBytes(StandardCharsets.UTF_8);
        int length = headerBytes.length + randomBytes.length + encryptedBytes.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        byteBuffer.put(headerBytes);
        byteBuffer.put(randomBytes);
        byteBuffer.put(encryptedBytes);
        byteBuffer.rewind();
        byte[] byteArray = new byte[length];
        byteBuffer.get(byteArray);

        return byteArray;
    }

    private static byte[] decrypt(byte[] payloadBytes, String password)
        throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] headerBytes = new byte[8];
        byte[] saltBytes = new byte[8];
        int length = payloadBytes.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        byteBuffer.put(payloadBytes);
        byteBuffer.rewind();
        byteBuffer.get(headerBytes);
        byteBuffer.get(saltBytes);
        length = payloadBytes.length - headerBytes.length - saltBytes.length;
        byte[] dataBytes = new byte[length];
        byteBuffer.get(dataBytes);

        byte[] keyByte = new byte[16];
        byte[] ivBytes = new byte[16];
        getKeyAndGenerateIv(password, saltBytes, keyByte, ivBytes);

        SecretKey secret = new SecretKeySpec(keyByte, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            //This Exception should never happen
        }
        assert cipher != null;
        cipher.init(Cipher.DECRYPT_MODE, secret, ivSpec);
        return cipher.doFinal(dataBytes);
    }
}
