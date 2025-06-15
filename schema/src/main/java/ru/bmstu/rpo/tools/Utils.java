package ru.bmstu.rpo.tools;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String computeHash(String password, String salt) {
        try {
            // 1. Конкатенируем пароль и соль
            String combined = password + salt;

            // 2. Получаем байты в UTF-8
            byte[] combinedBytes = combined.getBytes(StandardCharsets.UTF_8);

            // 3. Вычисляем хеш SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(combinedBytes);

            // 4. Конвертируем в hex-строку
            return Hex.encodeHexString(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}