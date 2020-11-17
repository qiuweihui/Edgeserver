package com.cug.springbootutils.vehicle;

import com.cug.springbootutils.utils.Client;

import java.io.IOException;

/**
 * @author qiuweihui
 * @create 2020-10-27 22:14
 */
public class TokenTransmit  extends Client {

    public static void main(String[] args) throws IOException {
        TokenTransmit.start("D:\\TestData\\EdgeServer\\Token.json");
    }

}
