package com.cug.springbootutils.vehicle;

import com.cug.springbootutils.utils.Server;

import java.io.IOException;

/**
 * @author qiuweihui
 * @create 2020-10-27 21:40
 * 服务器接收小车发来的广播，并存储广播内容，角色为Server
 *
 */
public class BroadcastReceive extends Server{
    public static void main(String[] arge) throws IOException {

        BroadcastReceive.start("D:\\TestData\\EdgeServer\\broadcast_receive.json");
        //小车公钥，小车签名，签名原文，都存在此Json对象中，需要时直接调用

    }

}
