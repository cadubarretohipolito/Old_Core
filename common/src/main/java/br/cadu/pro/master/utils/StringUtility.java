package br.cadu.pro.master.utils;

import java.util.Random;

public class StringUtility {
    public static String randomCharacters(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Comprimento inválido. O comprimento deve ter pelo menos 1 caractere");
        }

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
