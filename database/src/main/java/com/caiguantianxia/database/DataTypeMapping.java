package com.caiguantianxia.database;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Hongtu Zang
 * @date : Created in 上午11:15 19-1-16
 */
public class DataTypeMapping {
    public static Map<String, String> mysqlJavaMapping = new HashMap<String, String>() {
        {
            put("VARCHAR", "String");
            put("CHAR", "String");
            put("TEXT", "String");
            put("MEDIUMTEXT", "String");

            put("INTEGER", "Integer");
            put("INTEGER UNSIGNED", "Integer");
            put("INT", "Integer");
            put("INT UNSIGNED", "Integer");
            put("TINYINT", "Integer");
            put("TINYINT UNSIGNED", "Integer");
            put("SMALLINT", "Integer");
            put("MEDIUMINT", "Integer");

            put("BIT", "Boolean");

            put("BIGINT", "Long");
            put("BIGINT UNSIGNED", "Long");

            put("FLOAT", "Float");
            put("DOUBLE", "Double");
            put("DECIMAL", "BigDecimal");

            put("DATE", "Date");
            put("TIME", "Date");
            put("DATETIME", "Date");
            put("TIMESTAMP", "Date");
        }
    };

    public static Map<String, String> typeImportMapping = new HashMap<String, String>() {
        {
            put("String", "java.lang.String");
            put("Integer", "java.lang.Integer");
            put("Boolean", "java.lang.Boolean");
            put("Long", "java.lang.Long");
            put("BigDecimal", "java.math.BigDecimal");
            put("Date", "java.util.Date");
        }
    };

}
