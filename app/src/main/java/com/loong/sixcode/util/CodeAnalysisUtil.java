package com.loong.sixcode.util;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lxl on 2017/7/12.
 */

public  class CodeAnalysisUtil {
    public static List<String> String2List(String codeString){
        return Arrays.asList(codeString.split("、"));
    }

    public static String List2String(List<String> codeList){
        String result="";
        for (String code:codeList){
            result=result+code+"、";
        }
        if (result.endsWith("、")) result=result.substring(0,result.length()-1);
        return result;
    }

}
