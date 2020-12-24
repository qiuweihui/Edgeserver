package com.cug.utils.chain;

import com.alibaba.fastjson.JSONObject;
import com.cug.utils.server.HashCompute;
import com.cug.utils.utils.Input;
import com.cug.utils.utils.Url;

/**
 * @author qiuweihui
 * @create 2020-10-27 21:41
 * 服务器验证小车公钥和VID是否已在区块链上
 * 步骤3
 * 根据返回内容，若为1，继续下一步，若为0，直接返回拒绝服务
 */
public class ChainCheck {
    public static final String ADD_URL = "http://mgds.mingbyte.com/carbaas/verifyVehicleKey";

    public static int send(String keypath) throws Exception {
        //边缘服务器端验证的是车的VID和公钥是否匹配
        String jsonVID = Input.getString(keypath);
        JSONObject jsonObject = JSONObject.parseObject(jsonVID);
        String VID_Time = jsonObject.getString("VID_Time");
        //取前四位为VID
        String VID = VID_Time.substring(0,4);
        JSONObject obj = new JSONObject();
        obj.put("vehicleId", VID);
        // VID，后面再调用VID返回函数
        obj.put("pubKeyHash", HashCompute.hashCompute(keypath,"pubkey"));
        String payLoad = Url.send(ADD_URL,obj);
        int a = Integer.parseInt(payLoad);
        return a;
    }

}
