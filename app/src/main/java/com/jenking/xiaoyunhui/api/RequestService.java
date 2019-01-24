package com.jenking.xiaoyunhui.api;

import android.content.Context;

import com.jenking.xiaoyunhui.tools.SPUtils;

import java.util.HashMap;
import java.util.Map;

//基本请求参数类
public class RequestService {

    /**
     * 获取基本请求参数
     * @param context
     * @return Map<String,String>  enum:token,device
     */
    public static Map<String,String> getBaseParams(Context context){
        Map<String,String> params = new HashMap<>();
        if (context!=null){
//            params.put("token", (String) SPUtils.get(context,SPUtils.FILE_USER,SPUtils.user_token,""));
        }
        return params;
    }
}
