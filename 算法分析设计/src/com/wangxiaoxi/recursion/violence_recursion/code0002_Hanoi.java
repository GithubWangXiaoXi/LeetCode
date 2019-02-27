package com.wangxiaoxi.recursion.violence_recursion;

import org.junit.Test;

public class code0002_Hanoi {

    /**
     *  汉诺塔问题:
     *
     *  解决思路：
     *  1)先将n-1个盘子移动到中间的辅助位置，再将n移动到目的位置
     *  2）再将n-1个盘子移动到目的位置。
     */

    public void hanoi(int n, char from, char to, char help){

        //n == 1 为 n-1个盘子移动到help,或者to的杠上的中止条件
        if(n == 1){
            System.out.println("1 move from " + from + " to " + to);
            return;
        }

        //先将n-1个盘子移动到中间的辅助位置
        hanoi(n - 1, from, help,to);

        //再将n移动到目的位置
        System.out.println(n + " move from " + from + " to " + to);

        //再将n-1个盘子移动到目的位置。
        hanoi(n - 1, help, to, from);
    }

    @Test
    public void test(){
        hanoi(3,'左','中','右');
    }
}
