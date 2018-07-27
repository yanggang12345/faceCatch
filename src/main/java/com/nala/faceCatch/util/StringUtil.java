package com.nala.faceCatch.util;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: heshangqiu
 * @Date: 2018/3/26
 * <p>
 * 字符串相关的工具类
 * <p>
 * 通用方法调用 org.apache.commons.lang3.StringUtils
 * 需要特殊处理的方法再具体编写
 */
public class StringUtil {

    private static Pattern pattern = Pattern.compile("^1[3456789][0-9]{9}$");
    private static Pattern patternNumber = Pattern.compile("[0-9]*");
    // 判断小数点后2位的数字的正则表达式
    private static Pattern patternNumber2Decimal = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");

    private static Pattern patternEmoji = Pattern.compile("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]");
    private static final int LENGTH = 16;

    public static boolean isMobile(String src) {
        Matcher matcher = pattern.matcher(src);
        return matcher.find();
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Matcher isNum = patternNumber.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断小数点后2位的数字的正则表达式
     *
     * @param str
     * @return
     */
    public static boolean isNumber2Decimal(String str) {
        Matcher isNum = patternNumber2Decimal.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 验证给定的email是否符合邮箱格式
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        boolean tag = true;
        final String pattern1 = "^([a-z0-9A-Z]+[-|_\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }


    public static String matchPattern(String content, String patt, String replace) {
        Pattern pattern = Pattern.compile(patt);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            content = matcher.replaceAll(replace);
        }
        return content;
    }


    //UUID

    /**
     * 获取UUID
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取ID
     *
     * @return
     */
    public static String getId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 字符串md5
     *
     * @param varchar
     * @return
     */
    public static String md5(String varchar) {
        if (varchar == null) {
            return null;
        }
        try {
            // 用来将字节转换成16进制表示的字符
            char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f'};
            //创建MD5算法的信息摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] results = md.digest(varchar.getBytes());
            char[] str = new char[LENGTH * 2];
            int k = 0;
            for (int i = 0; i < LENGTH; i++) {
                byte byte0 = results[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception ex) {
        }
        return null;
    }


//    /**
//     * 生成随机数
//     *
//     * @param length 位数
//     * @return
//     */
//    public static String generateRandomNum(int length) {
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            builder.append(NumberUtil.random.nextInt(10));
//        }
//        return builder.toString();
//    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim());
    }

    /**
     * 判断字符串是否相等
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }


    public static String getSubString(String content, int length) {
        if (null == content || content.length() <= 0) {
            return "";
        }
        if (length < 1 || content.length() <= length) {
            return content;
        }
        return content.substring(0, length - 1) + "...";
    }


    /**
     * 字符串分割
     *
     * @param strSource     要分割的字符串
     * @param segmentSymbol 分割符
     * @return
     */
    public static List stringSegment(String strSource, String segmentSymbol) {
        List list = new ArrayList<String>();
        if (StringUtils.isEmpty(strSource)) {
            return list;
        }
        list = Splitter.on(segmentSymbol).splitToList(strSource);
        return list;
    }


    /**
     * 判断字符串中是否包含特殊表情符号
     *
     * @param str
     * @return
     */
    public static boolean isEmoji(String str) {
        Matcher matcher = patternEmoji.matcher(str);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 由于Java是基于Unicode编码的，因此，一个汉字的长度为1，而不是2。
     * 但有时需要以字节单位获得字符串的长度。例如，“123abc长城”按字节长度计算是10，而按Unicode计算长度是8。
     * 为了获得10，需要从头扫描根据字符的Ascii来获得具体的长度。如果是标准的字符，Ascii的范围是0至255，如果是汉字或其他全角字符，Ascii会大于255。
     * 因此，可以编写如下的方法来获得以字节为单位的字符串长度。
     *
     * @param s
     * @return
     */
    public static int getWordCount(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255) {
                length++;
            } else {
                length += 2;
            }

        }
        return length;

    }

    /**
     * 基本原理是将字符串中所有的非标准字符（双字节字符）替换成两个标准字符（**，或其他的也可以）。这样就可以直接例用length方法获得字符串的字节长度了
     *
     * @param s
     * @return
     */
    public static int getWordCountRegex(String s) {

        s = s.replaceAll("[^\\x00-\\xff]", "**");
        int length = s.length();
        return length;
    }

    /**
     * 按特定的编码格式获取长度
     *
     * @param str
     * @param code
     * @return
     * @throws UnsupportedEncodingException
     */
    public static int getWordCountCode(String str, String code) throws UnsupportedEncodingException {
        return str.getBytes(code).length;
    }

    /**
     * 通过 & 连接字符串
     *
     * @param strings
     * @param delimiter
     * @return
     */
    public static String join(String[] strings, String delimiter) {

        StringBuffer stringBuffer = new StringBuffer();
        for (String string : strings) {
            stringBuffer.append(string)
                    .append(delimiter);
        }

        return stringBuffer.toString().replaceAll(delimiter + "$", "");
    }

}
