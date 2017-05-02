package com.example.kru13.bitmaptest;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Jakub on 02.05.2017.
 */

public class Md5Hash {

    private int i[];
    private int len;
    private static final char START = 65;
    private static final char END = 126;
    private static final char characters[] = {
            'A', 'B','C','D','E','F','G','H','I','J','K','L','M',
            'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a', 'b','c','d','e','f','g','h','i','j','k','l','m',
            'n','o','p','q','r','s','t','u','v','w','x','y','z',
            '0','1','2','3','4','5','6','7','8','9',
    };

    Md5Hash () {
        len = 0;
        i = new int[512];
    }
    public String createMd5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
           return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String generate() {
        // Generate key
        StringBuilder sb = new StringBuilder();
        for (int l = 0; l <= len; l++) {
            sb.append(characters[i[l]]);
        }

        // Update indexes to the start
        for (int l = len; l >= 0; l--) {
            i[l]++;
            if (i[l] + START > END) {
                i[l] = 0;

                // End
                if (l == 0) {
                    len++;
                    break;
                }
            } else {
                break;
            }
        }

        return sb.toString();
    }
}
