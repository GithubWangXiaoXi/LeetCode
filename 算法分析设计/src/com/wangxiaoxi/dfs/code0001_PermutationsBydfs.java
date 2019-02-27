package com.wangxiaoxi.dfs;

import org.junit.Test;

public class code0001_PermutationsBydfs {

    /**
     * 问题描述:使用深度优先搜索得到全排列。
     *
     * 实现思路：
     * 1，用value和sign两个数组分别存储值和标记。
     * 2，可以理解成3张扑克牌放入3个盒子，一共有多少种放法
     *    A B C
     *    1 2 3
     * 取出3（sign标记为0，盒子为空），2可以放入C中，这时B空了，3可以放入B中
     *    1 3 2 。。。
     *
     * value数组中存的是值，step表示所在深度的状态，value[step]表示第几个盒子。
     * 而sign数组负责标记哪个数字已经加入到value数组中。
     */
    void dfs(int step, int n, int sign[], int value[]){

        if(step == n + 1){
            for(int i = 1; i <= n; i++){
                System.out.print(value[i] + " ");
            }
            System.out.println();
            return;
        }

        for(int i = 1; i <= n; i++) {

            if(sign[i] == 0){

               value[step] = i; //先将扑克牌i放入第step个盒子中。
               sign[i] = 1;  //将i标记为已经放入value数组中
               dfs(step + 1, n, sign,value);
               sign[i] = 0;
            }
        }
    }

    public void show(int[] sign){
        for(int i = 1; i < 4; i++){
            System.out.print(sign[i] + " ");
        }
        System.out.println();
    }

    @Test
    public void test(){

        int sign[] = new int[100];
        int value[] = new int[100];
        int n = 3;

        dfs(1,n,sign,value);
    }
}
