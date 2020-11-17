package com.cug.springbootutils.server;

import cn.hutool.json.JSONObject;
import com.cug.springbootutils.utils.Output;

/**
 * @author qiuweihui
 * @create 2020-10-27 22:14
 *
 * Token ,内容暂时没确定
 */
public class TokenCreate {

    public static void main(String[] args) {
        JSONObject jsonkey =new  JSONObject();
        jsonkey.accumulate("Token", "TokenExample");
        Output.wirteText(String.valueOf(jsonkey),"D:\\TestData\\EdgeServer\\Token.json");
    }

}
