package com.ankhnotes.utils;

import java.util.ArrayList;
import java.util.List;

public class SystemUtils {

    /**
     * 形如"1,2,3"的字符串转换为List<Long>
     */
    public static List<Long> stringTransferToListLong(String stringIds){
        //处理String
        List<Long> Ids = new ArrayList<>();
        //去空格
        stringIds = stringIds.replace(" ", "");
        //转换
        if(stringIds.contains(",")){
            String[] splitIds = stringIds.split(",");
            for(String splitId : splitIds)
                Ids.add(Long.parseLong(splitId));
        }else{
            Ids.add(Long.parseLong(stringIds));
        }
        return Ids;
    }

}
