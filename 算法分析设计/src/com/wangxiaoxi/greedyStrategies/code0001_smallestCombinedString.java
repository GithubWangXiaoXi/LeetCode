package com.wangxiaoxi.greedyStrategies;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

public class code0001_smallestCombinedString {

    /** 给定一些字符串，让它们自行组合，最终得到字典序最小的拼接的字符串 */
    /**
     * 实现思路：
     * 使用贪心的策略（贪心策略有多种，这个问题的字符串比较策略就是贪心策略。至于所选的贪心策略是否正确，切记不要去证明它！！）
     *
     * 策略1：每次在字符串序列中选最小的字符串，拼接在头部。其次在子序列中找到最小的字符串...
     *        容易举出反例： bac 和 b -> b00 < bac, b放在头部，组成bbac，但是bbac的字典序比bacb大，说明策略1不可行
     *
     * 策略2：先将两字符串拼接起来（2种情况），再选字典序最小的字符串，将该字符串的头子串排在前面
     *        至于可不可行，用对数器，用用证明
     */

    public String getsmallestCombinedString(String[] strings){

        if(strings == null || strings.length == 0){
            return "";
        }

        Arrays.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //如果o1+o2的字典序小于o2+o1,返回-1
                if((o1+o2).compareTo(o2 + o1) < 0){
                     return -1;
                }
                else if((o1+o2).compareTo(o2 + o1) > 0){
                    return 1;
                }else{
                    return 0;
                }
            }
        });

        //拼接最小的字符串
        String smallestString = "";
        for(String str : strings){
            System.out.println(str);
            smallestString += str;
        }

        return smallestString;
    }

    @Test
    public void smallestCombinedStringTest(){

        String str[] = {"china","chi","japan","chian"};
//        String str[] = {"bac","b"};
        System.out.println(getsmallestCombinedString(str));

        //ASCII码大写字母比小写字母小
        System.out.println("ChiChianChinaJapan".compareTo("ChianChiChinaJapan"));
    }
}
