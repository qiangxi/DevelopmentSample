package com.qiangxi.developmentsample.helper;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 正则表达式帮助类
 */
public class PatternHelper {
    private PatternHelper() {
    }

    /**
     * 描述：手机号格式验证.
     *
     * @param str
     *            指定的手机号码字符串
     * @return 是否为手机号码格式:是为true，否则false
     */
    public static Boolean isMobileNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String pattern = "^1[3|4|5|7|8][0-9]\\d{4,8}$";
        return str.matches(pattern);
    }

    /**
     * 描述：是否只是字母和数字.
     *
     * @param str
     *            指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isNumberLetter(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String pattern = "^[A-Za-z0-9]+$";
        return str.matches(pattern);
    }

    /**
     * 描述：是否只是数字.
     *
     * @param str
     *            指定的字符串
     * @return 是否只是数字:是为true，否则false
     */
    public static Boolean isNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String pattern = "^[0-9]+$";
        return str.matches(pattern);
    }

    /**
     * 判断给定字符串是否是邮箱.
     * @return 是否是邮箱:是为true，否则false
     */
    public static Boolean isEmail(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String pattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return str.matches(pattern);
    }

    /**
     * 判断给定字符串是否全是中文(包括中文标点符号).
     */
    public static boolean isChinese(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String chinese = "^([\u0391-\uFFE5]|A-Za-z)";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < str.length(); i++) {
            // 获取一个字符
            String temp = str.substring(i, i + 1);
            // 判断是否为中文字符
            if (!temp.matches(chinese)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断指定字符串是否是中文或字母,且长度为10以内
     */
    public static Boolean isChineseAndNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String pattern = "[\u0391-\uFFE5\\a-zA-Z]{0,10}";
        return str.matches(pattern);
    }

    /**
     * 描述：是否包含中文.
     * @return 包含中文:true,不包含中文:false
     */
    public static Boolean isContainsChinese(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String chinese = "[\u0391-\uFFE5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < str.length(); i++) {
            // 获取一个字符
            String temp = str.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 描述：从输入流中获得String.
     * @return 获得的String
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            // 最后一个\n删除
            if (sb.indexOf("\n") != -1 && sb.lastIndexOf("\n") == sb.length() - 1) {
                sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n") + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
