package com.gsy.base.common;

/**
 * Created by Administrator on 2017/7/5.
 */
public class StringHelper {


    /**
     *
     * @方法名 ：toJavaAttributeName<br>
     * @方法描述 ：将数据库中列名转化为Java类的属性名（按照Java驼峰标示规范命名）<br>
     * @创建者 ：Andy.wang<br>
     * @创建时间 ：2013-9-3下午12:16:40 <br>
     * @param dbColumnName
     *            ：数据库表列名称
     * @return 返回类型 ：String
     */
    public static String toJavaAttributeName(String dbColumnName) {
        char ch[] = dbColumnName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (i == 0) {
                ch[i] = Character.toLowerCase(ch[i]);
            }
            if ((i + 1) < ch.length) {
                if (ch[i] == '_') {
                    ch[i + 1] = Character.toUpperCase(ch[i + 1]);
                } else {
                    //ch[i + 1] = Character.toLowerCase(ch[i + 1]);
                }
            }
        }
        return new String(ch).replace("_", "");
    }

    /**
     *
     * @方法名 ：toDbColumnName<br>
     * @方法描述 ：将Java类的属性名转化为数据库中列名（按照DB规范命名）<br>
     * @创建者 ：Andy.wang<br>
     * @创建时间 ：2013-9-3下午12:27:30 <br>
     * @param javaAttributeName
     *            ：Java类的属性名
     * @return 返回类型 ：String
     */
    public static String toDbColumnName(String javaAttributeName) {
        StringBuffer sb = new StringBuffer(javaAttributeName);
        char[] c = sb.toString().toCharArray();
        byte b = 0;
        int k = 0;
        for (int i = 0; i < c.length; i++) {
            b = (byte) c[i];
            if (b >= 65 && b <= 90) {
                sb.insert((i + k), "_");
                k++;
            }

        }
        return sb.toString().toLowerCase();
    }

    /**
     *
     * @方法名 ：asserGetMethodName<br>
     * @方法描述 ：组装Java实体类属性的get方法名<br>
     * @创建者 ：Andy.wang<br>
     * @创建时间 ：2013-9-3下午01:38:49 <br>
     * @param attributeName
     *            ：Java类的属性名
     * @return 返回类型 ：String
     */
    public static String asserGetMethodName(String attributeName) {
        StringBuffer sb = new StringBuffer(16);
        char[] ch = attributeName.toCharArray();
        ch[0] = Character.toUpperCase(ch[0]);
        sb.append("get");
        sb.append(new String(ch));
        return sb.toString();
    }

    /**
     *
     * @方法名 ：asserSetMethodName<br>
     * @方法描述 ：组装Java实体类属性的set方法名<br>
     * @创建者 ：Andy.wang<br>
     * @创建时间 ：2013-9-3下午01:41:54 <br>
     * @param attributeName
     *            : Java类的属性名
     * @return 返回类型 ：String
     */
    public static String asserSetMethodName(String attributeName) {
        StringBuffer sb = new StringBuffer(16);
        char[] ch = attributeName.toCharArray();
        ch[0] = Character.toUpperCase(ch[0]);
        sb.append("set");
        sb.append(new String(ch));
        return sb.toString();
    }

    public static void main(String[] args){
        System.out.println(StringHelper.asserSetMethodName("labelBackground1"));
    }


}
