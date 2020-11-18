package com.cug.springapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.cug.utils.chain.ChainCheck;
import com.cug.utils.server.KeyEncrypt;
import com.cug.utils.server.SignVerify;
import com.cug.utils.server.Signature;
import com.cug.utils.utils.Input;
import com.cug.utils.utils.Output;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author qiuweihui
 * @create 2020-11-17 21:20
 */

@RestController
@RequestMapping("server")
public class Broadcast {

    @GetMapping("/broadcast")
    public static Object broadcast(@RequestBody JsonObject jsonObject ) throws Exception {
        Output.wirteText(jsonObject.toString(),"D:\\TestData\\EdgeServer\\broadcast_receive.json");
        //1.BroadcastReceive，接收并存储
        //BroadcastReceive.main();

        //2.ChainCheck,提出VID，计算hash(PKv),到区块链核验(VID：hash(PKv))
        int a = ChainCheck.appadd("D:\\TestData\\EdgeServer\\broadcast_receive.json");

        //3.SignVerify,用PKv和原文验证签名S
        boolean b = SignVerify.main();

        if (a == 1 && b == true){
            //表示核验通过和签名验证通过

            //4.KeyEncrypt，使用接收到的小车SM2公钥，对服务器公钥和SM4Key密钥加密
            Object keyEncrypt = KeyEncrypt.main();

            //5.Signature，用服务器私钥，对SID和时间Time进行签名
            Object signature =  Signature.main();

            JSONObject object = new  JSONObject();

            //6.添加SID_Time到返回的object中
            String sIDTime = Input.getString("D:\\TestData\\EdgeServer\\SID_Time.json");
            //读入接收到的小车SM2公钥
            JSONObject sTobject = JSONObject.parseObject(sIDTime);

            object.putAll(sTobject);
            object.putAll((Map<? extends String, ? extends Object>) keyEncrypt);
            object.putAll((Map<? extends String, ? extends Object>) signature);
            object.put("code","200");
            object.put("Message","验证通过");

            return object;
        }
        else {
            JSONObject object = new JSONObject();
            object.put("code", "200");
            object.put("Message", "验证失败");
            return object;
        }

    }
}
