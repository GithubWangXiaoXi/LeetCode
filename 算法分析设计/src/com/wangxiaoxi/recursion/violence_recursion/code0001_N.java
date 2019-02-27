package com.wangxiaoxi.recursion.violence_recursion;

import org.junit.Test;

public class code0001_N {

    public int func1(int n){

        if(n == 1){
            System.out.print(n);
            return 1;
        }

        System.out.print(n + " * ");
        //其中func1(n - 1)为n!的子问题
        return n * func1(n - 1);
    }

    @Test
    public void test(){
        int result = func1(5);
        System.out.println(" = " + result);
    }
}
