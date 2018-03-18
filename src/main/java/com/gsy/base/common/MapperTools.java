package com.gsy.base.common;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */
public class MapperTools {
    private static final Logger L = Logger.getLogger(MapperTools.class);
    /**
     *
     * @方法名 ：rsMapEntity<br>
     * @方法描述 ：根据结果集（一条数据）映射 到 实体类<br>
     * @创建者 ：Andy.wang<br>
     * @创建时间 ：2013-9-4上午10:09:16 <br>
     * @param <T>
     *            ：类型
     * @param clazz
     *            ：实体的Class
     * @param rs
     *            ：查询的结果集
     * @return 返回类型 ：T
     */
    public static <T> T rsMapEntity(Class<T> clazz, ResultSet rs) {
        ResultSetMetaData rsmd = null;
        String temp = "";
        Method s = null;
        T t = null;
        try {
            rsmd = rs.getMetaData();
            t = clazz.newInstance();
            L.debug("MapperTools:--2"+rsmd.getColumnCount());
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                temp = rsmd.getColumnLabel(i);
                String javaName = StringHelper.toJavaAttributeName(temp);
                Field field = clazz.getDeclaredField(javaName);
                s = clazz.getDeclaredMethod(StringHelper
                        .asserSetMethodName(javaName),field.getType());
                s.invoke(t, rs.getObject(temp));
                L.debug("MapperTools:"+rs.getObject(temp));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            L.error(e);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            L.error(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            L.error(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            L.error(e);
        } catch (SecurityException e) {
            e.printStackTrace();
            L.error(e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            L.error(e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            L.error(e);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            L.error(e);
        }
        return t;
    }


    public static <T> T entityToDTO(Class<T> clazz, Object srcType) {
        try {
            T returnObj = clazz.newInstance();
            Field[] fields = srcType.getClass().getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                Class fieldType = field.getType();
                String newName = StringHelper.toJavaAttributeName(fieldName);
                Method s = returnObj.getClass().getDeclaredMethod(StringHelper
                        .asserSetMethodName(newName),fieldType);
                Method g = srcType.getClass().getDeclaredMethod(
                        StringHelper.asserGetMethodName(fieldName)
                );
                s.invoke(returnObj,g.invoke(srcType));
            }
            return returnObj;
        }catch (NoSuchMethodException e){
            e.printStackTrace();
            L.error(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            L.error(e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            L.error(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            L.error(e);
        }
        return null;
    }


    /**
     *
     * @方法名 ：resultSetMapToEntityList<br>
     * @方法描述 ：根据结果集（多条数据）映射 到 实体类集合<br>
     * @创建者 ：Andy.wang<br>
     * @创建时间 ：2013-9-4上午10:11:37 <br>
     * @param <T>
     *            ：泛型
     * @param clazz
     *            ：实体类的Class
     * @param rs
     *            ：查询的结果集
     * @return 返回类型 ：List<T>
     */
    public static <T> List<T> rsMapToEntityList(Class<T> clazz,
                                                ResultSet rs) {
        ResultSetMetaData rsmd = null;
        List<T> list = new ArrayList<T>();
        String temp = "";
        Method s = null;
        T t = null;
        try {
            rsmd = rs.getMetaData();
            while (rs.next()) {
                t = clazz.newInstance();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    temp = rsmd.getColumnName(i);
                    String javaName = StringHelper.toJavaAttributeName(temp);
                    Field field = clazz.getField(javaName);
                    s = clazz.getDeclaredMethod(StringHelper
                            .asserSetMethodName(javaName),field.getType());
                    s.invoke(t, rs.getObject(temp));
                }
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return list;
    }

}


