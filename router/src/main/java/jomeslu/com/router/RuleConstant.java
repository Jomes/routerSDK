package jomeslu.com.router;

/**
 * Created by jomeslu on 17-1-21.
 * http://www.androidblog.cn?{s:name}=11&&{b:t}=true
 * http;//www.androidblog.cn?name=1
 */

public class RuleConstant {
    /**
     * string 字符串
     **/
    public static final String PARAM_S = "S";
    /**
     * boolean 布尔值
     **/
    public static final String PARAM_B = "B";
    /**
     * int 整形
     **/
    public static final String PARAM_I = "I";
    /**
     * long 长整形
     **/
    public static final String PARAM_L = "L";
    /**
     * float 浮点数
     **/
    public static final String PARAM_F = "F";
    /**
     * float Object
     **/
    public static final String PARAM_O = "O";
    /**
     * double
     **/
    public static final String PARAM_D = "D";

    public static final String PARAM_SPIT_LEFT = "{";
    public static final String PARAM_SPIT_RIGHT = "}";
    public static final String PARAM_SPIT_SIGN = ":";
    public static final String PARAM_SPIT_EQUALS = "=";
    public static final int MATHCH_SUCCESS = 0;
    public static final int MATHCH_OTHER = 1;
    public static final int MATHCH_ERROR_BUNDLE = 2;
    public static final int MATHCH_ERROR_URL = 3;


}
