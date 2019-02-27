package com.wangxiaoxi.recursion.violence_recursion;

import org.junit.Test;

public class code0004_cow {

    /**
     * 问题描述：奶牛生子问题：每只奶牛一年生出一子，而且生出的牛不会死，3年后才有生育能力，求N年后奶牛的数量
     *
     * 解决思路：
     * 一只奶牛A
     * 1 - A  1
     * 2 - A,B  2
     * 3 - A,B,C  3
     * 4 - A,B,C,D  4
     * 5 - A,B,C,D,E,F  6 (B奶牛有生育能力)
     * 6 - A,B,C,D,E,F,G,H,I (C奶牛有生育能力)  9  今年奶牛的数量是去年的奶牛的数量 + 可以生出来的奶牛的数量（三年前的奶牛都有生育能力，所以 + 三年前的奶牛的数量 * 1）
     */

    public int getCow(int n){
        if(n == 1){
            return 1;
        }
        if(n == 2){
            return 2;
        }
        if(n == 3){
            return 3;
        }
        else{
            return getCow(n-1) + getCow(n - 3);
        }
    }

    @Test
    public void test(){
        int count = getCow(6);
        System.out.println(count);
    }
}
