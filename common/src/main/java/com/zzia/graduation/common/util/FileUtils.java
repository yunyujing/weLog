//package com.zzia.graduation.common.util;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.os.Environment;
//import android.os.StatFs;
//import android.text.TextUtils;
//
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Enumeration;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipException;
//import java.util.zip.ZipFile;
//import java.util.zip.ZipOutputStream;
//
///**
// * 文件处理
// *
// * @author xiongfei.miao
// * @date 20111027
// */
//public class FileUtils {
//
//    protected static final String TAG = "FileUtils";
//    public static long SAVE_FREESPACE_BYTE = 20 * 1024 * 1024; // sd卡剩余空间不足15MB时，不进行存储。
//    public static final String CACHEDIR_COMMON = Environment.getExternalStorageDirectory().getPath() + "/aps/common/";// 产品公共目录
//
//    /*
//     * 保存文件到指定路径，后缀名不修改
//     */
//    public static void saveImageToSD(Bitmap bitmap, String path, String fileName) {
//        if (!isHaveSDCard()) {
//            return;
//        }
//        if (null == bitmap) {
//            return;
//        }
//        if (SAVE_FREESPACE_BYTE > freeSpaceOnSd_BYTE()) {
//            return;
//        }
//        File dir = new File(path);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        File imageFile = new File(path + fileName);
//        try {
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
//            if (bitmap != null) {
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
//                bos.flush();
//                bos.close();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void saveImageToSD(Bitmap bitmap, String filePath) {
//        if (!isHaveSDCard()) {
//            return;
//        }
//        if (null == bitmap) {
//            return;
//        }
//        if (SAVE_FREESPACE_BYTE > freeSpaceOnSd_BYTE()) {
//            return;
//        }
//        File imageFile = new File(filePath);
//        if (!imageFile.getParentFile().exists()) {
//            imageFile.getParentFile().mkdirs();
//        }
//        try {
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
//            if (bitmap != null) {
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
//                bos.flush();
//                bos.close();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 将文本内容写入SD卡中
//     *
//     * @param content
//     */
//    public static boolean writeText2File(File toFile, String content, boolean rewrite) {
//        if (StringUtils.isEmpty(content)) {
//            return false;
//        }
//        if (!toFile.getParentFile().exists()) {
//            if (!toFile.getParentFile().mkdirs()) {
//                return false;
//            }
//        }
//        if (toFile.exists() && rewrite) {
//            toFile.delete();
//        }
//
//        OutputStreamWriter output = null;
//        BufferedReader buffer = null;
//
//        try {
//            output = new OutputStreamWriter(new FileOutputStream(toFile));
//            byte[] bytes = content.getBytes();
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//            buffer = new BufferedReader(new InputStreamReader(byteArrayInputStream));
//            char[] chs = new char[1024];
//            int s = -1;
//            while ((s = buffer.read(chs)) != -1) {
//                output.write(chs, 0, s);
//            }
//            output.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (output != null) {
//                try {
//                    output.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//            }
//            if (buffer != null) {
//                try {
//                    buffer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 将文本内容写入SD卡中
//     *
//     * @param content
//     */
//    public static boolean writeText2FileAppend(File toFile, String content) {
//        if (StringUtils.isEmpty(content)) {
//            return false;
//        }
//
//        if (!toFile.getParentFile().exists()) {
//            if (!toFile.getParentFile().mkdirs()) {
//                return false;
//            }
//        }
//
//        OutputStreamWriter output = null;
//        BufferedReader buffer = null;
//
//        try {
//            output = new OutputStreamWriter(new FileOutputStream(toFile, true));
//            byte[] bytes = content.getBytes();
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//            buffer = new BufferedReader(new InputStreamReader(byteArrayInputStream));
//            char[] chs = new char[1024];
//            int s = -1;
//            while ((s = buffer.read(chs)) != -1) {
//                output.write(chs, 0, s);
//            }
//            output.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (output != null) {
//                try {
//                    output.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//            }
//            if (buffer != null) {
//                try {
//                    buffer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 将文本内容写入SD卡中
//     *
//     * @param filePath
//     * @param fileName
//     * @param content
//     */
//    public static void writeFileSdcard(final String filePath, final String fileName, final String content) {
//        if (!isHaveSDCard()) {
//            return;
//        }
//        if (SAVE_FREESPACE_BYTE > freeSpaceOnSd_BYTE()) { // SD卡的剩余空间少于10MB就不写入
//            return;
//        }
//        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(fileName)) {
//            return;
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OutputStreamWriter output = null;
//                BufferedReader buffer = null;
//                File file = new File(filePath);
//                if (!file.exists()) {// 如果没有这个路径
//                    file.mkdirs();// 创建这个路径
//                }
//                File cache = new File(filePath + fileName);
//                if (cache.exists()) {
//                    cache.delete();
//                }
//                try {
//                    output = new OutputStreamWriter(new FileOutputStream(cache));
//
//                    byte[] bytes = content.getBytes();
//                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//                    buffer = new BufferedReader(new InputStreamReader(byteArrayInputStream));
//                    char[] chs = new char[1024];
//                    int s = -1;
//                    while ((s = buffer.read(chs)) != -1) {
//                        output.write(chs, 0, s);
//                    }
//                    output.flush();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    return;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (output != null) {
//                        try {
//                            output.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (buffer != null) {
//                        try {
//                            buffer.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }).start();
//    }
//
//    /**
//     * 把bytes写入文件
//     * @param filePath
//     * @param contentBytes
//     * @return
//     */
//    public static boolean writeBytesToFile(final String filePath, final byte[] contentBytes) {
//        if(contentBytes == null || contentBytes.length == 0){
//        	return false;
//        }
//
//        OutputStreamWriter output = null;
//        BufferedReader buffer = null;
//        File cache = new File(filePath);
//        File parnetFile = cache.getParentFile();
//        if (!parnetFile.exists()) {// 如果没有这个路径
//            parnetFile.mkdirs();// 创建这个路径
//        }
//        if (cache.exists()) {
//            cache.delete();
//        }
//        try {
//            output = new OutputStreamWriter(new FileOutputStream(cache));
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contentBytes);
//            buffer = new BufferedReader(new InputStreamReader(byteArrayInputStream));
//            char[] chs = new char[1024];
//            int s = -1;
//            while ((s = buffer.read(chs)) != -1) {
//                output.write(chs, 0, s);
//            }
//            output.flush();
//            return true;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            if (output != null) {
//                try {
//                    output.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (buffer != null) {
//                try {
//                    buffer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    /**
//     * 将文本内容写入SD卡中
//     *
//     * @param filePath
//     * @param content
//     */
//    public static boolean writeFileSdcard(final String filePath, final String content) {
//        return writeFileSdcard(filePath, content, true);
//    }
//
//    /**
//     * 将内容写入文件
//     *
//     * @param filePath 文件路径
//     * @param content 内容
//     * @param checkfreeSpace 是否检查存储空间
//     * @return 是否写入成功
//     */
//    public static boolean writeFileSdcard(final String filePath, final String content, boolean checkfreeSpace) {
//        if (!isHaveSDCard()) {
//            return false;
//        }
//        if (checkfreeSpace && SAVE_FREESPACE_BYTE > freeSpaceOnSd_BYTE()) { // SD卡的剩余空间少于10MB就不写入
//            return false;
//        }
//        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(filePath)) {
//            return false;
//        }
//        OutputStreamWriter output = null;
//        BufferedReader buffer = null;
//        File cache = new File(filePath);
//        File parnetFile = cache.getParentFile();
//        if (!parnetFile.exists()) {// 如果没有这个路径
//            parnetFile.mkdirs();// 创建这个路径
//        }
//        if (cache.exists()) {
//            cache.delete();
//        }
//        try {
//            output = new OutputStreamWriter(new FileOutputStream(cache));
//            byte[] bytes = content.getBytes();
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//            buffer = new BufferedReader(new InputStreamReader(byteArrayInputStream));
//            char[] chs = new char[1024];
//            int s = -1;
//            while ((s = buffer.read(chs)) != -1) {
//                output.write(chs, 0, s);
//            }
//            output.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            if (output != null) {
//                try {
//                    output.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (buffer != null) {
//                try {
//                    buffer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return true;
//    }
//
//    public static boolean append(String path, String content) {
//        if (StringUtils.isEmpty(content)) {
//            return false;
//        }
//
//        File toFile = new File(path);
//        if (!toFile.getParentFile().exists()) {
//            if (!toFile.getParentFile().mkdirs()) {
//                return false;
//            }
//        }
//
//        OutputStreamWriter output = null;
//        BufferedReader buffer = null;
//
//        try {
//            output = new OutputStreamWriter(new FileOutputStream(toFile, true)); // append
//            byte[] bytes = content.getBytes();
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//            buffer = new BufferedReader(new InputStreamReader(byteArrayInputStream));
//            char[] chs = new char[1024];
//            int s = -1;
//            while ((s = buffer.read(chs)) != -1) {
//                output.write(chs, 0, s);
//            }
//            output.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (output != null) {
//                try {
//                    output.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//            }
//            if (buffer != null) {
//                try {
//                    buffer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    public static boolean copyfile(File fromFile, File toFile, boolean rewrite) {
//        if (!fromFile.exists()) {
//            return false;
//        }
//        if (!fromFile.isFile()) {
//            return false;
//        }
//        if (!fromFile.canRead()) {
//            return false;
//        }
//        if (!toFile.getParentFile().exists()) {
//            if (!toFile.getParentFile().mkdirs()) {
//                return false;
//            }
//        }
//        if (toFile.exists() && rewrite) {
//            toFile.delete();
//        }
//        try {
//            FileInputStream fosfrom = new FileInputStream(fromFile);
//            FileOutputStream fosto = new FileOutputStream(toFile);
//            byte bt[] = new byte[1024];
//            int c;
//            while ((c = fosfrom.read(bt)) > 0) {
//                fosto.write(bt, 0, c); // 将内容写到新文件当中
//            }
//            fosfrom.close();
//            fosto.close();
//        } catch (Exception ex) {
//            // LogUtils.e("readfile", ex.getMessage());
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 将文件中的内容转换成byte
//     * @param fileAbsPath
//     * @return
//     */
//    public static byte[] readFileToBytes(String fileAbsPath){
//    	if(TextUtils.isEmpty(fileAbsPath)){
//    		return null;
//    	}
//
//    	File file = new File(fileAbsPath);
//    	if(!file.exists() || file.isDirectory()){
//    		return null;
//    	}
//
//    	InputStream inputStream = null;
//
//    	try {
//    		inputStream = new FileInputStream(file);
//			int length = inputStream.available();
//			if(length > 0){
//				byte[] bs = new byte[length];
//				inputStream.read(bs);
//				return bs;
//			} else {
//				return null;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			if(inputStream != null){
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//    }
//
//    /**
//     * 读取文本内容
//     *
//     * @param fileAbsPath
//     * @return
//     */
//    public static String readFileContent(String fileAbsPath) {
//        if (!isHaveSDCard()) {
//            return "";
//        }
//        File file = new File(fileAbsPath);
//        if (!file.exists()) {
//            return "";
//        }
//        String res = null;
////        StringBuffer buffer = new StringBuffer();
//        InputStream inputStream = null;
//        int length = 0;
//        try {
//            inputStream = new FileInputStream(file);
//            length = inputStream.available();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//			e.printStackTrace();
//		}
//        if (null == inputStream) {
//            return "";
//        }
//        byte[] bs = new byte[length];
////        int len = -1;
//        try {
////            while ((len = inputStream.read(bs)) != -1) {
////                buffer.append(new String(bs, 0, len, "UTF-8"));
////            }
//        	inputStream.read(bs);
//        	res = EncodingUtils.getString(bs, "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "";
//        } finally {
//            try {
//                if (null != inputStream) {
//                    inputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "";
//            }
//        }
//        return res;
//    }
//
//    /**
//     * 读取文本内容
//     *
//     * @param inputStream
//     * @return
//     */
//    public static String readFileContent(InputStream inputStream) {
//        if (null == inputStream) {
//            return "";
//        }
//        StringBuffer buffer = new StringBuffer();
//        byte[] bs = new byte[1024];
//        int len = -1;
//        try {
//            while ((len = inputStream.read(bs)) != -1) {
//                buffer.append(new String(bs, 0, len, "UTF-8"));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "";
//        } finally {
//
//            try {
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "";
//            }
//        }
//        return buffer.toString();
//    }
//
//    /**
//     * 是否有内存卡
//     *
//     * @return
//     */
//    public static boolean isHaveSDCard() {
//        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
//    }
//
//    /**
//     * 另启一个线程删除文件或文件夹
//     *
//     * @param fileName 文件或文件夹的名字
//     */
//    public static void deleteAllFilesOnThread(final String fileName) {
//        if (!isHaveSDCard()) {
//            return;
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                deleteAllFiles(fileName);
//            }
//        }).start();
//    }
//
//    /**
//     * 通过文件绝对路径删除文件
//     *
//     * @param fileName 文件的完整路径，eg:/sdcard/a/b.txt
//     */
//    private static void deleteAllFiles(String fileName) {
//        File file = new File(fileName);
//        if (!file.exists()) {
//            return;
//        }
//        if (file.isDirectory()) {
//            File[] files = file.listFiles();
//            int len = files.length;
//            for (int i = 0; i < len; i++) {
//                deleteAllFiles(files[i].getPath());
//            }
//            file.delete();
//        } else {
//            file.delete();
//        }
//    }
//
//    public static void deleteFile(File dir, ArrayList<String> excludeFileNameArr) {
//        if (null == dir || !dir.isDirectory()) {
//            return;
//        }
//        if (null == excludeFileNameArr || excludeFileNameArr.size() == 0) {
//            deleteFile(dir);
//        }
//        File[] files = dir.listFiles();
//        if(files == null || files.length == 0){
//        	return;
//        }
//        for (File file : files) {
//            if (excludeFileNameArr.contains(file.getName())) {
//                continue;
//            }
//            file.delete();
//        }
//    }
//
//    public static void deleteFile(File dir, String excludeFileName) {
//        if (null == dir || !dir.isDirectory()) {
//            return;
//        }
//        File[] files = dir.listFiles();
//        if(files == null || files.length == 0){
//        	return;
//        }
//        for (File file : files) {
//            if (file.getName().equals(excludeFileName)) {
//                continue;
//            }
//            file.delete();
//        }
//    }
//
//    public static void deleteFile(File dir) {
//        if (null == dir || !dir.isDirectory()) {
//            return;
//        }
//        File[] files = dir.listFiles();
//        if(files == null || files.length == 0){
//        	return;
//        }
//        for (File file : files) {
//            if (!file.isDirectory()) {
//                file.delete();
//            }
//        }
//    }
//
//    /**
//     * 计算sdcard上的剩余空间
//     *
//     * @return kb
//     */
//    public static long freeSpaceOnSd_BYTE() {
//        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
//        long sdFreeKB = ((long) stat.getAvailableBlocks() * (long) stat.getBlockSize());
//        return sdFreeKB;
//    }
//
//    public static void zipFolder(String srcFilePath, String zipFilePath) throws Exception {
//        // 创建Zip包
//        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFilePath));
//        // 打开要输出的文件
//        File file = new File(srcFilePath);
//        // 压缩
//         zipFile(file, outZip, zipFilePath);
//        // 完成,关闭
//        outZip.finish();
//        outZip.close();
//    }
//
//    private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte
//
//    /**
//     * 批量压缩文件（夹）
//     *
//     * @param resFileList 要压缩的文件（夹）列表
//     * @param zipFile 生成的压缩文件
//     * @throws IOException 当压缩过程出错时抛出
//     */
//    public static void zipFiles(Collection<File> resFileList, File zipFile) throws IOException {
//        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), BUFF_SIZE));
//        for (File resFile : resFileList) {
//            zipFile(resFile, zipout, "");
//        }
//        zipout.close();
//    }
//
//    /**
//     * 批量压缩文件（夹）
//     *
//     * @param resFileList 要压缩的文件（夹）列表
//     * @param zipFile 生成的压缩文件
//     * @param comment 压缩文件的注释
//     * @throws IOException 当压缩过程出错时抛出
//     */
//    public static void zipFiles(Collection<File> resFileList, File zipFile, String comment) throws IOException {
//        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), BUFF_SIZE));
//        for (File resFile : resFileList) {
//            zipFile(resFile, zipout, "");
//        }
//        zipout.setComment(comment);
//        zipout.close();
//    }
//
//    /**
//     * 解压缩一个文件
//     *
//     * @param zipFile 压缩文件
//     * @param folderPath 解压缩的目标目录
//     * @throws IOException 当解压缩过程出错时抛出
//     */
//    public static void upZipFile(File zipFile, String folderPath) throws IOException {
//        File desDir = new File(folderPath);
//        if (!desDir.exists()) {
//            desDir.mkdirs();
//        }
//        ZipFile zf = new ZipFile(zipFile);
//        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
//            ZipEntry entry = ((ZipEntry) entries.nextElement());
//            InputStream in = zf.getInputStream(entry);
//            String str = folderPath + File.separator + entry.getName();
//            str = new String(str.getBytes("8859_1"), "GB2312");
//            File desFile = new File(str);
//            if (!desFile.exists()) {
//                File fileParentDir = desFile.getParentFile();
//                if (!fileParentDir.exists()) {
//                    fileParentDir.mkdirs();
//                }
//                desFile.createNewFile();
//            }
//            OutputStream out = new FileOutputStream(desFile);
//            byte buffer[] = new byte[BUFF_SIZE];
//            int realLength;
//            while ((realLength = in.read(buffer)) > 0) {
//                out.write(buffer, 0, realLength);
//            }
//            in.close();
//            out.close();
//        }
//        zf.close();
//    }
//
//    /**
//     * 解压文件名包含传入文字的文件
//     *
//     * @param zipFile 压缩文件
//     * @param folderPath 目标文件夹
//     * @param nameContains 传入的文件匹配名
//     * @throws ZipException 压缩格式有误时抛出
//     * @throws IOException IO错误时抛出
//     */
//    public static ArrayList<File> upZipSelectedFile(File zipFile, String folderPath, String nameContains) throws IOException {
//        ArrayList<File> fileList = new ArrayList<File>();
//
//        File desDir = new File(folderPath);
//        if (!desDir.exists()) {
//            desDir.mkdir();
//        }
//
//        ZipFile zf = new ZipFile(zipFile);
//        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
//            ZipEntry entry = ((ZipEntry) entries.nextElement());
//            if (entry.getName().contains(nameContains)) {
//                InputStream in = zf.getInputStream(entry);
//                String str = folderPath + File.separator + entry.getName();
//                str = new String(str.getBytes("8859_1"), "GB2312");
//                // str.getBytes("GB2312"),"8859_1" 输出
//                // str.getBytes("8859_1"),"GB2312" 输入
//                File desFile = new File(str);
//                if (!desFile.exists()) {
//                    File fileParentDir = desFile.getParentFile();
//                    if (!fileParentDir.exists()) {
//                        fileParentDir.mkdirs();
//                    }
//                    desFile.createNewFile();
//                }
//                OutputStream out = new FileOutputStream(desFile);
//                byte buffer[] = new byte[BUFF_SIZE];
//                int realLength;
//                while ((realLength = in.read(buffer)) > 0) {
//                    out.write(buffer, 0, realLength);
//                }
//                in.close();
//                out.close();
//                zf.close();
//                fileList.add(desFile);
//            }
//        }
//        return fileList;
//    }
//
//    /**
//     * 获得压缩文件内文件列表
//     *
//     * @param zipFile 压缩文件
//     * @return 压缩文件内文件名称
//     * @throws ZipException 压缩文件格式有误时抛出
//     * @throws IOException 当解压缩过程出错时抛出
//     */
//    public static ArrayList<String> getEntriesNames(File zipFile) throws IOException {
//        ArrayList<String> entryNames = new ArrayList<String>();
//        Enumeration<?> entries = getEntriesEnumeration(zipFile);
//        while (entries.hasMoreElements()) {
//            ZipEntry entry = ((ZipEntry) entries.nextElement());
//            entryNames.add(new String(getEntryName(entry).getBytes("GB2312"), "8859_1"));
//        }
//        return entryNames;
//    }
//
//    /**
//     * 获得压缩文件内压缩文件对象以取得其属性
//     *
//     * @param zipFile 压缩文件
//     * @return 返回一个压缩文件列表
//     * @throws ZipException 压缩文件格式有误时抛出
//     * @throws IOException IO操作有误时抛出
//     */
//    public static Enumeration<?> getEntriesEnumeration(File zipFile) throws IOException {
//        ZipFile zf = new ZipFile(zipFile);
//        return zf.entries();
//    }
//
//    /**
//     * 取得压缩文件对象的注释
//     *
//     * @param entry 压缩文件对象
//     * @return 压缩文件对象的注释
//     * @throws UnsupportedEncodingException
//     */
//    public static String getEntryComment(ZipEntry entry) throws UnsupportedEncodingException {
//        return new String(entry.getComment().getBytes("GB2312"), "8859_1");
//    }
//
//    /**
//     * 取得压缩文件对象的名称
//     *
//     * @param entry 压缩文件对象
//     * @return 压缩文件对象的名称
//     * @throws UnsupportedEncodingException
//     */
//    public static String getEntryName(ZipEntry entry) throws UnsupportedEncodingException {
//        return new String(entry.getName().getBytes("GB2312"), "8859_1");
//    }
//
//    /**
//     * 压缩文件
//     *
//     * @param resFile 需要压缩的文件（夹）
//     * @param zipout 压缩的目的文件
//     * @param rootpath 压缩的文件路径
//     * @throws FileNotFoundException 找不到文件时抛出
//     * @throws IOException 当压缩过程出错时抛出
//     */
//    private static void zipFile(File resFile, ZipOutputStream zipout, String rootpath) throws IOException {
//        rootpath = rootpath + (rootpath.trim().length() == 0 ? "" : File.separator) + resFile.getName();
//        rootpath = new String(rootpath.getBytes("8859_1"), "UTF-8");
//        if (resFile.isDirectory()) {
//            File[] fileList = resFile.listFiles();
//            for (File file : fileList) {
//                zipFile(file, zipout, rootpath);
//            }
//        } else {
//            byte buffer[] = new byte[BUFF_SIZE];
//            BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile), BUFF_SIZE);
//            zipout.putNextEntry(new ZipEntry(rootpath));
//            int realLength;
//            while ((realLength = in.read(buffer)) != -1) {
//                zipout.write(buffer, 0, realLength);
//            }
//            in.close();
//            zipout.flush();
//            zipout.closeEntry();
//        }
//    }
//
//    /**
//     * 获得某个路径下的文件结构
//     * @param path
//     * @return
//     */
//    public static String getFileDirTree(StringBuffer stringBuffer, String path) {
//        if (!StringUtils.isEmpty(path)) {
//            File all = new File(path);
//            if (!all.exists()) {
//                return "file dir not exists";
//            }
//            if (all.isFile()) {
//                return "this is a file";
//            }
//            File[] allFiles = all.listFiles();
//            for(File file : allFiles) {
//                stringBuffer.append(getFileLabel(file));
//                if (file.isDirectory()) {
//                    getFileDirTree(stringBuffer, file.getAbsolutePath());
//                }
//            }
//            return stringBuffer.toString();
//        }
//        return "fileDir is not available";
//    }
//
//    private static String getFileLabel(File file) {
//        if (file == null) {
//            return "file null\n";
//        }
//        if (file.isDirectory()) {
//            return "dir_" + file.getAbsolutePath() + "   can read:" + file.canRead() + "\n";
//        }
//        if (file.isFile()) {
//            return "file_" + file.getAbsolutePath() + "   can read:" + file.canRead() + "\n";
//        }
//        return "";
//    }
//
//    /**
//     * 将assets下的文件copy到SDcard
//     * @param context
//     * @param assetsPath
//     * @param sdPath
//     */
//    public static void copyFileAssets2SDcard(Context context, String assetsPath, String sdPath) {
//        try {
//            InputStream is = context.getAssets().open(assetsPath);
//            File file = new File(sdPath);
//            if (file.exists()) {
//                return;
//            }
//            FileOutputStream fos = new FileOutputStream(file);
//            byte[] buffer = new byte[1024];
//            int byteCount = 0;
//            while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取 buffer字节
//                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
//            }
//            fos.flush();// 刷新缓冲区
//            is.close();
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
