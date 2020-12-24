package com.cug.springapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.cug.utils.chain.UpChain;
import com.cug.utils.server.HashCompute;
import com.cug.utils.utils.GenerateSM2Key;
import com.cug.utils.utils.GenerateSM4Key;
import com.cug.utils.utils.Input;
import com.cug.utils.utils.Url;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author qiuweihui
 * @create 2020-11-19 9:15
 *
 * 初始化主要是两部分，一个是生成服务器自己的密钥，一个是上传SID和公钥hash，这是在部署时就要进行的
 *
 */
@Component
@Order(0)
public class Initialization implements CommandLineRunner {
    public static final String ADD_URL = "http://mgds.mingbyte.com/carbaas/uploadServerKey";

    public static void init() throws Exception {
        //1.GenerateSM2Key，产生SM2密钥，分为公钥和私钥
        GenerateSM2Key.main("D:\\TestData\\EdgeServer\\pubkey.json",
                "D:\\TestData\\EdgeServer\\prikey.json");
        //2.GenerateSM4Key产生SM4密钥，对称密钥只有一份
        GenerateSM4Key.main("D:\\TestData\\EdgeServer\\sm4key.json");

        //3.UpChain，上传SID和hash(PKv)对到区块链
        JSONObject obj = new JSONObject();
        //读入并添加SID
        String jsonSID = Input.getString("D:\\TestData\\EdgeServer\\SID.json");
        JSONObject jsonObject1 = JSONObject.parseObject(jsonSID);
        String SID = jsonObject1.getString("SID");
        obj.put("serverId",SID);
        //计算并上传服务器公钥哈希
        obj.put("pubKeyHash", HashCompute.hashCompute("D:\\TestData\\EdgeServer\\pubkey.json","pubkey"));
        Url.send(ADD_URL,obj);

    }

    @Override
    public void run(String... args) throws Exception {
        init();
        System.out.println("！！！初始化完成！！！");
    }
}
