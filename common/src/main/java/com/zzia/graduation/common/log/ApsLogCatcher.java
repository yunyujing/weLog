package com.zzia.graduation.common.log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ApsLogCatcher implements ApsLogInterface{

    private boolean isDebug;

    private String logFilePath;

    private final long FILE_MAXSIZE = 1024 * 24; //最大24k

    private ApsLogSenderInterface apsLogSender;

    public ApsLogCatcher(boolean isDebug, String filePath, ApsLogSenderInterface logSender) {
        this.isDebug = isDebug;
        this.logFilePath = filePath;
        this.apsLogSender = logSender;
    }



    @Override
    public String getLogFilePath(){
        return logFilePath;
    }


    //	@Override
    //	public void init(boolean isDebug,String filePath, ApsLogSenderInterface logSender){
    //		this.isDebug = isDebug;
    //		this.logFilePath = filePath;
    //	}

    @Override
    public void save(String content) {
        if (null == content || "".equals(content)) {
            return;
        }
        content = "\n**************** "+getNowStr()+" ****************\n"+content;
        File file = new File(logFilePath);
        if (file.length() > FILE_MAXSIZE) {
            try {
                StringBuffer buffer = new StringBuffer();
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"UTF-8");
                char[] chs = new char[1024];
                int len = -1;
                while((len = inputStreamReader.read(chs)) != -1){
                    buffer.append(chs,0,len);
                }
                inputStreamReader.close();
                fileInputStream.close();
                buffer.delete(0, content.length());
                buffer.append(content).append("\n");
                writeText2File(file, buffer.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (OutOfMemoryError e) {
            }
        }else{
            appendLog(file,content);
        }



    }


    private String getNowStr(){
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.US);
        return sdf.format(Calendar.getInstance().getTime());
    }

    @Override
    public void sendLog(){
        if (isDebug) {
            return;
        }
        if (null == apsLogSender) {
            return;
        }
        apsLogSender.sendLog(new File(logFilePath));
    }


    /**
     * 将文本内容写入SD卡中
     * @param content
     */
    private boolean writeText2File(File toFile, String content){
        if (null == content || "".equals(content)) {
            return false;
        }
        if (!toFile.getParentFile().exists()) {
            if(!toFile.getParentFile().mkdirs()){
                return false;
            }
        }
        if (toFile.exists()) {
            toFile.delete();
        }

        OutputStreamWriter output = null;
        StringReader reader = null;
        try {
            output = new OutputStreamWriter(new FileOutputStream(toFile),"UTF-8");
            char[] chs = new char[1024];
            reader = new StringReader(content);
            int s = -1;
            while ((s = reader.read(chs)) != -1) {
                output.write(chs, 0, s);
            }
            output.flush();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            if (reader != null) {
                reader.close();
            }
        }
        return true;
    }


    private boolean appendLog(File toFile , String content){
        if (null == content || "".equals(content) || null == toFile) {
            return false;
        }
        if (!toFile.getParentFile().exists()) {
            if(!toFile.getParentFile().mkdirs()){
                return false;
            }
        }
        OutputStreamWriter output = null;
        BufferedReader buffer = null;

        try {
            output = new OutputStreamWriter(new FileOutputStream(toFile,true));		//append
            byte[] bytes=content.getBytes("UTF-8");
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
            buffer = new BufferedReader(new InputStreamReader(byteArrayInputStream,"UTF-8"));
            char[] chs = new char[1024];
            int s = -1;
            while ((s = buffer.read(chs)) != -1) {
                output.write(chs, 0, s);
            }
            output.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void cleanLog() {
        new File(logFilePath).delete();
    }


}