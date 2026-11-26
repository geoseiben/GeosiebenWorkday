package com.geosieben.gsbworkday.addon;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AddonServ {

    public static Map<String,Integer> targetHoursMap=new HashMap<>();
    public static Map<String,Integer> extendedTargetMap=new HashMap<>();
    static {
        targetHoursMap.put("Trainee", 110);
        targetHoursMap.put("GIS Trainee", 110);
        targetHoursMap.put("Associate", 140);
        targetHoursMap.put("GIS Associate", 140);
        targetHoursMap.put("Analyst", 160);
        targetHoursMap.put("GIS Analyst", 160);
        targetHoursMap.put("Team Lead", 184);
        targetHoursMap.put("Senior Associate", 184);
        targetHoursMap.put("Un-Assigned", 175);


        extendedTargetMap.put("Trainee", 110);
        extendedTargetMap.put("GIS Trainee", 110);
        extendedTargetMap.put("Associate", 160);
        extendedTargetMap.put("GIS Associate", 160);
        extendedTargetMap.put("Analyst", 184);
        extendedTargetMap.put("GIS Analyst", 184);
        extendedTargetMap.put("Team Lead", 200);
        extendedTargetMap.put("Senior Associate", 200);
        extendedTargetMap.put("Un-Assigned", 184);

    }
    public static String fullDateFormatter(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return date.format(formatter);
    }

    
        public static String monthYearFormatter(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yyyy");
        return date.format(formatter);
    }
public static String monthYearFormatterNew(LocalDate date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yyyy", Locale.ENGLISH);
    return date.format(formatter);
}

            public static String monthNumExtract(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M");
        return date.format(formatter);
    }
                public static int dateNumExtract(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("D");
        return Integer.parseInt(date.format(formatter));
    }
            public static String yearExtract(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        return date.format(formatter);

        
    }
public static String changePattern(String input) {
    DateTimeFormatter inputFormatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive() // This makes it ignore casing
            .appendPattern("MMM-yyyy")
            .toFormatter(Locale.ENGLISH);

    YearMonth yearMonth = YearMonth.parse(input, inputFormatter);
    return yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
}

public static int noofdaysinmonth(String input) {
    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("MMM-yyyy")
            .toFormatter(Locale.ENGLISH);

    return YearMonth.parse(input, formatter).lengthOfMonth();
}

	public static String bytesToHexa(byte[] bytes){
		StringBuilder hexString=new StringBuilder(2*bytes.length);
		for(byte b:bytes){
			hexString.append(String.format("%02x", b&0xff));
		}
		return hexString.toString();
	}
public static byte[] encyptPlainText(SecretKeySpec secretKey,IvParameterSpec iv,String plaintext) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
return encryptedBytes;
}
 public static byte[] getSecretKey(String password) throws Exception{
            MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        String hexSalt = "";
        for (byte b : md5Bytes) hexSalt += String.format("%02x", b);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), hexSalt.getBytes(StandardCharsets.UTF_8), 10000, 128);
        byte[] rawKey = factory.generateSecret(spec).getEncoded();
        return rawKey;
 }

 public static  byte[] getIv() throws Exception{
         byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
       return iv;
 }

 
    	public static String randomPasswordGenerator() {
	String raw="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	String password="";
	for(int i=0;i<16;i++) {
		Random rand=new Random();
		int num=rand.nextInt(62);
		password+=raw.charAt(num);
	}
	return password;
	}
public static String decryptCipherText(SecretKeySpec secretKeySpec,IvParameterSpec ivParameterSpec,byte[] cipherText)throws Exception
{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(cipherText);
        String decryptedMessage = new String(decryptedBytes, StandardCharsets.UTF_8);
        return decryptedMessage;
}

}
