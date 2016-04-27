package com.zzia.graduation.http;//package com.appshare.android.http;
//
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.Closeable;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
///**
// * Created by apple on 15/7/10.
// */
//public class Files {
//
//    /**
//     * Reads the full contents of this byte source as a byte array.
//     *
//     * @throws IOException if an I/O error occurs in the process of reading from this source
//     */
//    public static byte[] read(File file) {
//        if (file == null) throw new NullPointerException("file is null");
//
//        InputStream in = null;
//        try {
//            in = new FileInputStream(file);
//            return toByteArray(in);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            closeQuietly(in);
//        }
//
//        return null;
//    }
//
//
//    /**
//     * Reads all bytes from an input stream into a byte array.
//     * Does not close the stream.
//     *
//     * @param in the input stream to read from
//     * @return a byte array containing all the bytes from the stream
//     * @throws IOException if an I/O error occurs
//     */
//    public static byte[] toByteArray(InputStream in) throws IOException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        copy(in, out);
//        return out.toByteArray();
//    }
//
//
//    private static final int BUF_SIZE = 0x1000; // 4K
//
//    /**
//     * Copies all bytes from the input stream to the output stream.
//     * Does not close or flush either stream.
//     *
//     * @param from the input stream to read from
//     * @param to   the output stream to write to
//     * @return the number of bytes copied
//     * @throws IOException if an I/O error occurs
//     */
//    public static long copy(InputStream from, OutputStream to)
//            throws IOException {
//        if (from == null) throw new NullPointerException("input stream is null");
//        if (to == null) throw new NullPointerException("output stream is null");
//
//        byte[] buf = new byte[BUF_SIZE];
//        long total = 0;
//        while (true) {
//            int r = from.read(buf);
//            if (r == -1) {
//                break;
//            }
//            to.write(buf, 0, r);
//            total += r;
//        }
//        return total;
//    }
//
//
//    /**
//     * 将字节数组写入文件
//     */
//    public static void write(byte[] from, File to) {
//        if (from == null)  throw new NullPointerException("bytes is null");
//        if (to == null) throw new NullPointerException("file is null");
//
//        BufferedOutputStream bos = null;
//
//        try {
//            bos = new BufferedOutputStream(new FileOutputStream(to));
//            bos.write(from);
//            bos.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            closeQuietly(bos);
//        }
//    }
//
//
//    /**
//     * 关闭流
//     */
//    public static void closeQuietly(Closeable stream) {
//        if (stream != null) {
//            try {
//                stream.close();
//            } catch (IOException e) {
//                // ignore
//            }
//        }
//    }
//
//}
