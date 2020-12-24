package com.cug.utils.centerserver;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author qiuweihui
 * @create 2020-10-27 21:43
 * 边缘服务器将视频图片数据传送到中心服务器
 * 步骤9
 */
public class DataTransmit {

    public static final String URL = "http://phfz.mingbyte.com/html/upload.php";
    public static String result;

    public static String send(String path) {
        try{
            //创建连接
            URL url1 = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            byte[] byteData = InputStream2ByteArray(path);
            out.write(byteData);
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer();
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "UTF-8");
                sb.append(lines);
            }
            result = sb.toString();
            System.out.println("返回信息："+result);
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
    private static byte[] InputStream2ByteArray(String filePath) throws IOException {

        InputStream in = new FileInputStream(filePath);
        byte[] data = toByteArray(in);
        in.close();
        return data;
    }

    public static void main(String[] args) {
        send("D:\\TestData\\Vehicle\\ImageData\\car.png");
    }

}
