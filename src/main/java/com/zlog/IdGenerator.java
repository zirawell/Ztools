package com.zlog;

import java.util.HashSet;
import java.util.Random;

/**
 * @Description:
 * @Author: Dark Wang
 * @Create: 2025/2/24 13:17
 **/
public class IdGenerator {
    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static HashSet<String> set = new HashSet<>();

    public static String generateCode(long redisId) {
        long timestamp = System.currentTimeMillis() % 1000000; // 取6位时间
        long mixedSeed = (timestamp << 38) | (redisId & 0x3FFFFFFFFFL);
        Random random = new Random(mixedSeed);
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(BASE62_CHARS.length());
            sb.append(BASE62_CHARS.charAt(index));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10000000; i++){
            String randomCode = generateCode(i+1);
            System.out.println("Generated 8-digit code: " + randomCode);
            if(set.contains(randomCode)){
                throw new RuntimeException("Duplicate code generated: " + randomCode);
            }else{
                set.add(randomCode);
            }
        }


    }

}
