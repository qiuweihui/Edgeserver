package com.cug.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiuweihui
 * @create 2020-11-17 20:45
 *广播接收内容（签名S，原文，小车公钥）
 *经过提取小车VID，计算hash(PKv)，到区块链核验(VID：hash(PKv))，用PKv和原文验证签名S；
 *之间有一个验证不通过，随即返回拒绝给车
 *验证通过后，服务器进行加密、签名，给小车返回（加密的服务器公钥和SM4密钥，签名，原文）
 */

@RestController
@RequestMapping("edgeserver")
public class Broadcast {

    @GetMapping("/broadcast")
    public Object broadcast(String[] args) {

        cn.hutool.json.JSONObject object = new  cn.hutool.json.JSONObject();
    //1.BroadcastReceive，接收并存储


    //2.ChainCheck,提出VID，计算hash(PKv),到区块链核验(VID：hash(PKv))


    //3.SignVerify,用PKv和原文验证签名S


    //4.KeyEncrypt，使用接收到的小车SM2公钥，对服务器公钥和SM4Key密钥加密


    //5.Signature，用服务器私钥，对SID和时间Time进行签名


    //6.将加密内容和签名内容存到一个Json object中，返回给小车
        object.accumulate("encrypt","woshi加密内容");
        object.accumulate("Sign","我是sign");

        return object;
    }
}
