package com.jenking.xiaoyunhui.tools;

//比赛类型工具类
public class MatchTypeTool {
    public static String getMatchType(String key){
        String matchType = "";
        switch (key){
            case "1":
                matchType = "报名中";
                break;
            case "2":
                matchType = "比赛中";
                break;
            case "3":
                matchType = "已完成";
                break;
        }
        return matchType;
    }
}
