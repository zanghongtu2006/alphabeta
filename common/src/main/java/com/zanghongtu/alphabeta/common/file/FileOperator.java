package com.zanghongtu.alphabeta.common.file;

import java.io.*;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午3:57 19-1-16
 */
public class FileOperator {
    public static void writeFile(String filePath, String content) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!file.exists()) {
            try {
                if (!parent.exists()) {
                    if (!parent.mkdirs()) {
                        throw new Exception("Create file parent dir failed: " + filePath);
                    }
                }
                if (!file.createNewFile()) {
                    throw new Exception("Create file failed:" + filePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String filePath) {
        File file = new File(filePath);
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
    }
}
