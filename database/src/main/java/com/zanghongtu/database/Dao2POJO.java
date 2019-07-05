package com.zanghongtu.database;


import com.zanghongtu.alphabeta.common.CamelCase;
import com.zanghongtu.alphabeta.common.file.FileOperator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLDecoder;
import java.sql.*;
import java.util.*;

/**
 * @author hongtu
 */
@Service
public class Dao2POJO {
    private String jdbcUrl;

    private String dbUser;

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

    public void init(String jdbcUrl, String dbUser, String dbPwd) {
        this.jdbcUrl = jdbcUrl;
        this.dbUser = dbUser;
        this.dbPwd = dbPwd;
    }

    public void generateCodes() {
        String basePackage = findSpringBootApplicationPackage();
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
                writePojos(connection, tableName, className, basePackage);
                writeRepositories(className, basePackage);
                writeServices(className, basePackage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void writePojos(Connection connection, String tableName, String className, String basePackage) {
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
                    .replaceAll("###BASE_PACKAGE###", basePackage)
                    .replaceAll("###CLASS_NAME###", className)
                    .replaceAll("###COLUMNS###", columnStr.toString())
                    .replaceAll("###TABLE_NAME###", tableName);
            String filePath = srcBasePath + "model/" + className + ".java";
            FileOperator.writeFile(filePath, content);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void writeServices(String className, String basePackage) {
        writeServiceCode(className, basePackage);
        writeServiceImplCode(className, basePackage);
    }

    private void writeServiceImplCode(String className, String basePackage) {
        String content = readTemplate("serviceImpl")
                .replaceAll("###BASE_PACKAGE###", basePackage)
                .replaceAll("###CLASS_NAME###", className);
        String filePath = srcBasePath + "service/impl/" + className + "ServiceImpl.java";
        if (exist(filePath)) {
            return;
        }
        FileOperator.writeFile(filePath, content);
    }

    private void writeServiceCode(String className, String basePackage) {
        String content = readTemplate("service")
                .replaceAll("###BASE_PACKAGE###", basePackage)
                .replaceAll("###CLASS_NAME###", className);
        String filePath = srcBasePath + "service/" + className + "Service.java";
        if (exist(filePath)) {
            return;
        }
        FileOperator.writeFile(filePath, content);
    }

    private void writeRepositories(String className, String basePackage) {
        String content = readTemplate("repository")
                .replaceAll("###BASE_PACKAGE###", basePackage)
                .replaceAll("###CLASS_NAME###", className);
        String filePath = srcBasePath + "repository/" + className + "Repository.java";
        if (exist(filePath)) {
            return;
        }
        FileOperator.writeFile(filePath, content);
    }

    private boolean exist(String filePath) {
        File file = new File(filePath);
        return file.exists();
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

    private String findSpringBootApplicationPackage() {
        String url = System.getProperty("user.dir");
        List<String> classes = getClassesList(url);
        // 遍历classes，如果发现@Component就注入到容器中
        return scanSpringBootApplication(classes);
    }

    private String scanSpringBootApplication(List<String> classes) {
        for (String className : classes) {
            Class clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Annotation[] annotations = new Annotation[0];
            if (clazz != null) {
                annotations = clazz.getDeclaredAnnotations();
            }
            for (Annotation annotation : annotations) {
                if (annotation instanceof SpringBootApplication) {
                    return clazz.getPackage().getName();
                }
            }
        }
        return null;
    }

    private String getClassPath() {
        String url = null;
        try {
            url = URLDecoder.decode(Context.class.getResource("/").getPath(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (url != null && url.startsWith("/")) {
            url = url.replaceFirst("/", "");
            url = url.replaceAll("/", "\\\\");
        }
        return url;
    }

    private List<String> getClassesList(String url) {
        File file = new File(url);
        List<String> classes = getAllClass(file);
        for (int i = 0; i < classes.size(); i++) {
            classes.set(i, classes.get(i).replace(url, "").replace(".java", "")
                    .replace("\\", "."));
        }
        List<String> result = new LinkedList<>();
        for (String classFile : classes) {
            if (!classFile.startsWith("/src/main/java")) {
                continue;
            }
            result.add(classFile.replace("/src/main/java/", "")
                    .replace("/", "."));
        }
        return result;
    }

    private List<String> getAllClass(File file) {
        List<String> ret = new ArrayList<>();
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            if (list != null) {
                for (File i : list) {
                    List<String> j = getAllClass(i);
                    ret.addAll(j);
                }
            }
        } else {
            ret.add(file.getAbsolutePath());
        }
        return ret;
    }

    private void fetchFileList(File dir, List<File> fileList) {
        if (dir.isDirectory()) {
            for (File f : Objects.requireNonNull(dir.listFiles())) {
                fetchFileList(f, fileList);
            }
        } else {
            fileList.add(dir);
        }
    }
}

