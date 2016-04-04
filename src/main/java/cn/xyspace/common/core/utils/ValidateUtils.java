package cn.xyspace.common.core.utils;

/**
 * 字段验证工具类。
 * 
 * @author ChenFangjie
 * 
 * @since 1.0.0
 * @version 1.0.0
 *
 */
public final class ValidateUtils {

    // private static final List<String> ANSWER_LIST = Arrays.asList("男", "女"); // 活动预设答案

    // 让工具类彻底不可以实例化
    private ValidateUtils() {
        throw new Error("工具类不可以实例化！");
    }

    /**
     * 告知此字符串是否匹配给定的正则表达式。
     * 
     * @param regex
     *            用来匹配此字符串的正则表达式
     * @param str
     *            需要进行验证的字符串
     * @return 当且仅当此字符串匹配给定的正则表达式时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean validateByRegexp(String str, String regex) {
        if (null != str && null != regex) {
            return str.matches(regex);
        }
        return false;
    }

    /**
     * 验证用户名（登录名），5-16位的字符且以汉字或字母开头（只可包含汉字、字母、数字）。
     * 
     * @param username
     *            需要进行验证的字符串
     * @return 当且仅当此字符串满足要求时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isUsername(String username) {
        // ^[a-zA-Z][a-zA-Z0-9_]{4,15}$ // 5-16位的字符且以字母开头（只可包含字母，数字，下划线）
        // ^[\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5]{3,15}$ // 4-16位的字符且以汉字开头（只可包含汉字、字母、数字）
        return ValidateUtils.validateByRegexp(username, "^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5]{4,15}$");
    }

    /**
     * 验证姓名。 <br/>
     * 1、可以是汉字或字母，但是不能两者都有； <br/>
     * 2、不能包含任何符号和数字； <br/>
     * 3、允许英文名字中出现空格，但是不能连续出现多个； <br/>
     * 4、中文名不能出现空格。
     * 
     * @param name
     *            需要进行验证的字符串
     * @return 当且仅当此字符串满足要求时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isName(String name) {
        return ValidateUtils.validateByRegexp(name, "^([\\u4e00-\\u9fa5]+|([a-zA-Z]+\\s?)+)$");
    }

    /**
     * 验证密码。 <br/>
     * 1、密码不能包含中文,长度在1-16位以内；<br/>
     * 2、可以输入纯数字或纯字母；<br/>
     * 3、可以输入数字和字母组合；<br/>
     * 4、可以输入数字，字母，特殊符号(!@#$%&)组合。
     * 
     * @param password
     *            需要进行验证的字符串
     * @return 当且仅当此字符串满足要求时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isPassword(String password) {
        return ValidateUtils.validateByRegexp(password, "^[\\dA-Za-z(!@#$%&)]{1,16}$");
    }

    /**
     * 验证日期（要求为：yyyy-mm-dd格式且必须是有效的日期）。
     * 
     * @param date
     *            需要进行验证的字符串
     * @return 当且仅当此字符串满足要求时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isDate(String date) {
        // "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
        String regex = "^([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
        return ValidateUtils.validateByRegexp(date, regex);
    }

    /**
     * 验证中国大陆的电话号码（格式要求为：1xx xxxx xxxx 注：x表示[0-9]的数字）。
     * 
     * @param phoneNumber
     *            需要进行验证的字符串
     * @return 当且仅当此字符串满足要求时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        // ^\\d{11,}$
        // ^\\+86\\s1[0-9]{2}\\s[0-9]{4}\\s[0-9]{4}$ // 格式要求为：+86 1xx xxxx xxxx
        return ValidateUtils.validateByRegexp(phoneNumber, "^1[0-9]{2}\\s[0-9]{4}\\s[0-9]{4}$");
    }

    /**
     * 验证电子邮件地址。
     * 
     * @param email
     *            需要进行验证的字符串
     * @return 当且仅当此字符串满足要求时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isEmail(String email) {
        return ValidateUtils.validateByRegexp(email, "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$");
    }

    /**
     * 验证主键。
     * 
     * @param value
     *            需要进行验证的字符串
     * @return 当且仅当此字符串是长整数时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isPK(String value) {
        return ValidateUtils.validateByRegexp(value, "^\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}$");
    }

    /**
     * 验证是否是经纬度取值范围。
     * 
     * @param value
     *            需要进行验证的字符串
     * @return 当且仅当此字符串是长整数时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isLatitudeAndLongitude(String value) {
        boolean passed = true;
        double number = 0.0;

        try {
            number = Double.parseDouble(value);
        }
        catch (Exception e) {
            passed = false;
        }

        if (passed && (number > 999.0 || number < -999.0)) {
            passed = false;
        }

        return passed;
    }

    /**
     * 验证整数。
     * 
     * @param value
     *            需要进行验证的字符串
     * @return 当且仅当此字符串是整数时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isInt(String value) {
        boolean passed = true;
        try {
            Integer.parseInt(value);
        }
        catch (Exception e) {
            passed = false;
        }

        return passed;
    }

    /**
     * 验证无符号整数。
     * 
     * @param value
     *            需要进行验证的字符串
     * @return 当且仅当此字符串是无符号整数时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isUnsignedInt(String value) {
        boolean passed = true;
        try {
            Integer.parseUnsignedInt(value);
        }
        catch (Exception e) {
            passed = false;
        }

        return passed;
    }

    /**
     * 验证整数。
     * 
     * @param value
     *            需要进行验证的字符串
     * @param min
     *            最小值
     * @param max
     *            最大值
     * @return 当且仅当此字符串是整数并在指定的范围内返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isInt(String value, int min, int max) {
        boolean passed = true;
        try {
            int temp = Integer.parseInt(value);
            if (temp < min || temp > max) {
                passed = false;
            }
        }
        catch (Exception e) {
            passed = false;
        }

        return passed;
    }

    /**
     * 验证长整数。
     * 
     * @param value
     *            需要进行验证的字符串
     * @return 当且仅当此字符串是长整数时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isLong(String value) {
        boolean passed = true;
        try {
            Long.parseLong(value);
        }
        catch (Exception e) {
            passed = false;
        }

        return passed;
    }

    /**
     * 验证无符号长整数。
     * 
     * @param value
     *            需要进行验证的字符串
     * @return 当且仅当此字符串是无符号长整数时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isUnsignedLong(String value) {
        boolean passed = true;
        try {
            Long.parseUnsignedLong(value);
        }
        catch (Exception e) {
            passed = false;
        }

        return passed;
    }

    /**
     * 验证浮点数。
     * 
     * @param value
     *            需要进行验证的字符串
     * @return 当且仅当此字符串是浮点数时返回 true
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean isDouble(String value) {
        boolean passed = true;
        try {
            Double.parseDouble(value);
        }
        catch (Exception e) {
            passed = false;
        }

        return passed;
    }

    // /**
    // * 验证性别（要求表示性别的值为："SECRET"、"MALE"、"FEMALE"）。
    // *
    // * @param gender
    // * 需要进行验证的字符串
    // * @return 当且仅当此字符串满足要求时返回 true
    // *
    // * @since 1.0.0
    // */
    // public static boolean isGender(String gender) {
    // return UserGender.contains(gender);
    // }

}
