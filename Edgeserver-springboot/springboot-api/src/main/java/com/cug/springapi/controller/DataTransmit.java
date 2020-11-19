package com.cug.springapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.cug.springapi.utils.Transmit;
import com.cug.utils.server.ImageDecrypt;
import com.cug.utils.server.TokenCreate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.Map;

/**
 * @author qiuweihui
 * @create 2020-11-18 9:13
 *
 * 步骤8-11
 * 服务器接收小车发送的加密数据，在服务器端进行解密(ImageDecrypt)
 * 转发解密后的视频到中心服务器(ImageTransmit)
 * 中心服务器返回消息(TransmitResponse)
 * 生成Token并返回给小车(TokenCreate,TokenTransmit)
 */
@RestController
@ResponseBody
@RequestMapping("server")

public class DataTransmit {

    static String adress = null;
    static String msg = null;

    @RequestMapping(value = "/datatransmit",method = RequestMethod.POST)
    public static Object upload(MultipartFile file) {
        if (file == null) {
            msg = "上传失败，未选择文件";
        }
        String fileName = file.getOriginalFilename();
        String filePath = "D:\\TestData\\EdgeServer\\EncryptData\\";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            //上传成功！
            msg = "上传成功";
        } catch (IOException e) {
            //上传异常！
            msg = "上传失败";
        }

        //2.ImageDecrypt,解密视频,返回解密后的视频地址
        try {
             adress = ImageDecrypt.main();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //3.ImageTransmit,传入解密视频路径和中心服务器ip,发送至中心服务器
/*        try {
            Transmit.start(adress,"127.0.0.1");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //4.TransimitResponse


        //5.TokenCreate，生成Token
        Object jsonToken = TokenCreate.main();

        //6.object返回Token及消息
        JSONObject object = new  JSONObject();
        object.putAll((Map<? extends String, ? extends Object>) jsonToken);
        object.put("code","200");
        object.put("message",msg);
        object.put("adress",adress);
        return object;
    }

}
