package parent.guard.service;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import parent.guard.GuardApplication;

public class PatternService extends BaseService {
  private static final String KEY_SECURITY_PATTERN = "security_pattern";
  private static final String KEY_PATTERN_HELPED = "pattern_helped";
  private final static String HEX = "0123456789ABCDEF";
  
  public boolean setPatternHelped() {
    return setPreference(KEY_PATTERN_HELPED, true);
  }
  
  public boolean getPatternHelped() {
    return getPreferenceAsBoolean(KEY_PATTERN_HELPED);
  }
  
  public boolean setPattern(String pPattern) {
    String tEncryptedPattern = encryptString(pPattern);
    return setPreference(KEY_SECURITY_PATTERN, tEncryptedPattern);
  }
  
  public boolean isPatternCorrected(String pPattern) {
    String tEncryptedPattern = getPreferenceAsString(KEY_SECURITY_PATTERN);
    String tPattern = decryptAsString(pPattern, tEncryptedPattern);
    return tPattern.equals(pPattern);
  }
  
  private String encryptString(String pPattern) {
    String tSeed = pPattern.concat(pPattern);
    try {
      byte[] tRawKey = getRawKey(tSeed.getBytes());
      byte[] tEncryptedValue = encryptBytes(tRawKey, pPattern.getBytes());
      return toHex(tEncryptedValue);
    } catch(Exception pException) {
      GuardApplication.debug("fail to encrypt pattern");
      return pPattern;
    }
  }
  
  private String decryptAsString(String pPattern, String pEncryptedValue) {
    String tSeed = pPattern.concat(pPattern);
    try {
      byte[] tRawKey = getRawKey(tSeed.getBytes());
      byte[] tEncryptedBytes = toByte(pEncryptedValue);
      byte[] tOpenBytes = decryptAsBytes(tRawKey, tEncryptedBytes);
      return new String(tOpenBytes);
    } catch(Exception pException) {
      GuardApplication.debug("fail to decrypt pattern");
      return pEncryptedValue;
    }
  }

  
  private byte[] encryptBytes(byte[] pRawBytes, byte[] pOpenBytes) {
    try {
      SecretKeySpec tSecretKeySpec = new SecretKeySpec(pRawBytes, "AES");
      Cipher tCipher = Cipher.getInstance("AES");
      tCipher.init(Cipher.ENCRYPT_MODE, tSecretKeySpec);
      byte[] tEncryptedBytes = tCipher.doFinal(pOpenBytes);
      return tEncryptedBytes;
    } catch(Exception pException) {
      GuardApplication.debug("fail to encrypt bytes");
      return pOpenBytes;
    }
  }

  private byte[] decryptAsBytes(byte[] pRawBytes, byte[] pEncryptedBytes) {
    try {
      SecretKeySpec tSecretKeySpec = new SecretKeySpec(pRawBytes, "AES");
      Cipher tCipher = Cipher.getInstance("AES");
      tCipher.init(Cipher.DECRYPT_MODE, tSecretKeySpec);
      byte[] tDecryptedBytes = tCipher.doFinal(pEncryptedBytes);
      return tDecryptedBytes;
    } catch(Exception pException) {
      GuardApplication.debug("fail to decrypt bytes");
      return pEncryptedBytes;
    }
  }
  
  private byte[] getRawKey(byte[] pSeed) {
    try {
      KeyGenerator tKeyGenerator = KeyGenerator.getInstance("AES");
      SecureRandom tSecureRandom = SecureRandom.getInstance("SHA1PRNG");
      tSecureRandom.setSeed(pSeed);
      tKeyGenerator.init(128, tSecureRandom);
      SecretKey tSecretKey = tKeyGenerator.generateKey();
      byte[] tRawKey = tSecretKey.getEncoded();
      return tRawKey;
    } catch(Exception pException) {
      GuardApplication.debug("fail to get raw key from given seed");
      return pSeed;
    }
  }
  
  private byte[] toByte(String pHexString) {
    int tLength = pHexString.length() / 2;
    byte[] tBytes = new byte[tLength];
    for(int i = 0; i < tLength; i++) {
      String tHexByte = pHexString.substring(2 * i, 2 * i + 2);
      tBytes[i] = Integer.valueOf(tHexByte, 16).byteValue();
    }
    return tBytes;
  }
  
  private String toHex(byte[] pBytes) {
    if(pBytes == null) {
      return "";
    }
    
    StringBuffer tStringBuffer = new StringBuffer(2 * pBytes.length);
    for(int i = 0; i < pBytes.length; i++) {
      appendHex(tStringBuffer, pBytes[i]);
    }
    return tStringBuffer.toString();
  }
  
  private void appendHex(StringBuffer pStringBuffer, byte pByte) {
    pStringBuffer.append(HEX.charAt((pByte >> 4) & 0x0f));
    pStringBuffer.append(HEX.charAt(pByte & 0x0f));
  }
}
