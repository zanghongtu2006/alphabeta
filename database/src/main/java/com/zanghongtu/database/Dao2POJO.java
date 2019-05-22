package com.zanghongtu.database;


import com.zanghongtu.alphabeta.common.CamelCase;
import com.zanghongtu.alphabeta.common.file.FileOperator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author hongtu
 */
@Component
public class Dao2POJO {
    @Value("${database.package}")
    private String basePackage;

    @Value("${database.service}")
    private Boolean initServices;

    @Value("${database.repository}")
    private Boolean initRepositories;

    @Value("${database.model}")
    private Boolean initModels;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPwd;

    private String srcBasePath;

    private Set<String> defaultColumns;

    public Dao2POJO() {
        defaultColumns = new HashSet<>();
        defaultColumns.add("id");
        defaultColumns.add("create_time");
        defaultColumns.add("update_time");
        defaultColumns.add("available");
    }

    public void generateCodes() {
        srcBasePath = System.getProperty("user.dir") + "/src/main/java/" + basePackage.replaceAll("\\.", "/") + "/";
        String dbName = jdbcUrl.split("[/?]")[3];
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPwd)) {
            DatabaseMetaData metaData = connection.getMetaData();
            //获取数据库的全部表名
            ResultSet resultSet = metaData.getTables(dbName, null, null, new String[]{"TABLE"});
            List<String> tableNames = new LinkedList<>();
            while (resultSet.next()) {
                tableNames.add(resultSet.getString("TABLE_NAME"));
            }
            //获取表的字段，拼成POJO
            for (String tableName : tableNames) {
                String className = CamelCase.toCamelCaseClassName(tableName);
                writePojos(connection, tableName, className);
                writeRepositories(className);
                writeServices(className);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void writePojos(Connection connection, String tableName, String className) {
        if (!initModels) {
            return;
        }
        String sql = "SELECT * FROM " + tableName + " WHERE 1=2;";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            System.out.println("================" + tableName + "================");

            StringBuilder columnStr = new StringBuilder();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                String colName = resultSetMetaData.getColumnName(i);
                String paramName = CamelCase.toCamelCase(colName);
                if (defaultColumns.contains(colName)) {
                    continue;
                }
                String colType = DataTypeMapping.mysqlJavaMapping.get(resultSetMetaData.getColumnTypeName(i));
                columnStr.append("    @Column(name = \"").append(colName).append("\")").append("\n");
                columnStr.append("    private ").append(colType).append(" ").append(paramName).append(";\n\n");
            }
            String content = readTemplate("model")
                    .replaceAll("###BASE_PACKAGE###", this.basePackage)
                    .replaceAll("###CLASS_NAME###", className)
                    .replaceAll("###COLUMNS###", columnStr.toString())
                    .replaceAll("###TABLE_NAME###", tableName);
            String filePath = srcBasePath + "model/" + className + ".java";
            FileOperator.writeFile(filePath, content);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void writeServices(String className) {
        if (!initServices) {
            return;
        }
        writeServiceCode(className);
        writeServiceImplCode(className);
    }

    private void writeServiceImplCode(String className) {
        String content = readTemplate("serviceImpl")
                .replaceAll("###BASE_PACKAGE###", this.basePackage)
                .replaceAll("###CLASS_NAME###", className);
        String filePath = srcBasePath + "service/impl/" + className + "ServiceImpl.java";
        FileOperator.writeFile(filePath, content);
    }

    private void writeServiceCode(String className) {
        String content = readTemplate("service")
                .replaceAll("###BASE_PACKAGE###", this.basePackage)
                .replaceAll("###CLASS_NAME###", className);
        String filePath = srcBasePath + "service/" + className + "Service.java";
        FileOperator.writeFile(filePath, content);
    }

    private void writeRepositories(String className) {
        if (!initRepositories) {
            return;
        }
        String content = readTemplate("repository")
                .replaceAll("###BASE_PACKAGE###", this.basePackage)
                .replaceAll("###CLASS_NAME###", className);
        String filePath = srcBasePath + "repository/" + className + "Repository.java";
        FileOperator.writeFile(filePath, content);
    }

    private String readTemplate(String templateName) {
        Resource resource = new ClassPathResource("tmpl/" + templateName + ".template");
        try {
            File file = resource.getFile();
            Long filelength = file.length();
            byte[] filecontent = new byte[filelength.intValue()];
            try {
                FileInputStream in = new FileInputStream(file);
                in.read(filecontent);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new String(filecontent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

