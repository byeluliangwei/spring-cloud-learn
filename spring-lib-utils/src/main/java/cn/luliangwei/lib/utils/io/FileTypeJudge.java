/*
 * Copyright © 2017 signit.cn. All rights reserved.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package cn.luliangwei.lib.utils.io;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件真实类型判断.
 * </p>
 *
 * @author luliangwei
 * @since 1.0.0
 */
public class FileTypeJudge {

    /**
     * Constructor
     */
    private FileTypeJudge() {
    }

    /**
     * 将文件头转换成16进制字符串
     * 
     * @param 原生byte
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 得到文件头
     * 
     * @param filePath
     *            文件路径
     * @return 文件头
     * @throws IOException
     */
    private static String getFileContent(String filePath) throws IOException {
        byte[] b = new byte[28];
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return bytesToHexString(b);
    }

    /**
     * 得到文件头
     * 
     * @param fileBytes
     *            文件字节数组
     * @return 文件头
     * @throws IOException
     */
    private static String getFileHeadContent(byte[] fileBytes) throws IOException {
        ByteArrayInputStream bais = null;
        byte[] b = new byte[28];
        try {
            bais = new ByteArrayInputStream(fileBytes);
            bais.read(b, 0, 28);
            bais.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        return bytesToHexString(b);
    }

    /**
     * 判断文件类型
     * 
     * @param fileBytes
     *            文件二进制原始数据
     * @return 文件类型
     */
    public static FileType getType(byte[] fileBytes) {
        String fileHead;
        try {
            fileHead = getFileHeadContent(fileBytes);
            if (fileHead == null || fileHead.length() == 0) {
                return FileType.UNKNOWN;
            }

            fileHead = fileHead.toUpperCase();

            FileType[] fileTypes = FileType.values();

            for (FileType type : fileTypes) {
                if (fileHead.startsWith(type.fileHeader())) {
                    return type;
                }
            }
        } catch (IOException e) {
            return FileType.UNKNOWN;
        }
        return FileType.UNKNOWN;
    }

    /**
     * 判断文件类型
     * 
     * @param filePath
     *            文件路径
     * @return 文件类型
     */
    public static FileType getType(String filePath) {

        String fileHead;
        try {
            fileHead = getFileContent(filePath);
            if (fileHead == null || fileHead.length() == 0) {
                return FileType.UNKNOWN;
            }

            fileHead = fileHead.toUpperCase();

            FileType[] fileTypes = FileType.values();

            for (FileType type : fileTypes) {
                if (fileHead.startsWith(type.fileHeader())) {
                    return type;
                }
            }
        } catch (IOException e) {
            return FileType.UNKNOWN;
        }
        return FileType.UNKNOWN;
    }

    /**
     * 判断文件是否属于某类型
     * 
     * @param fileType
     *            待比较的文件类型
     * @param fileBytes
     *            文件二进制原始数据
     * @return true 属于某文件类型；false 不属于某文件类型
     */
    public static boolean isType(FileType fileType, byte[] fileBytes) {
        String fileHead;
        try {
            fileHead = getFileHeadContent(fileBytes);
            if (fileHead == null || fileHead.length() == 0) {
                return false;
            }
            fileHead = fileHead.toUpperCase();
            return fileHead.startsWith(fileType.fileHeader());
        } catch (Throwable e) {
            return false;
        }
    }

}
