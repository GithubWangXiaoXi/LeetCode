package com.wangxiaoxi.dfs;

import org.junit.Test;

import java.util.Arrays;

public class code0002_smallestSticks {

    /**
     * 问题描述:有等长的sticks，切分成n段。如果给出n个sticks part的值，给出sticks的最小长度
     *
     * 实现思路:
     * dfs + 剪枝
     *
     * input
     * 9
     * 5 2 1 5 2 1 5 2 1  24 % 6 = 0, 24 / 6 = 4
     * output
     * 6
     *
     * input
     * 4
     * 1 2 3 4   10 % 5 = 0,  10 / 5 = 2  5 在 [4,10]之间，也在[4,5]区间上
     * output:
     * 5
     *
     * 剪枝：sum % result = 0
     *
     *         令initlen为所求的最短原始棒长，maxlen为给定的棒子堆中最长的棒子，sumlen为这堆棒子的长度之和，那么initlen必定在范围[maxlen，sumlen]中，
     *    cnt为可能组成的数目，也就是sumlen/maxlen。

     *    根据棒子的灵活度（棒子越长，灵活度越低） DFS前先对所有棒子降序排序
     * 剪枝：
     *    1、  由于所有原始棒子等长，那么必有sumlen % Initlen == 0，这个我在main函数中做了。
     *    2、  若能在[maxlen,sumlen-InitLen]找到最短的InitLen，该InitLen必也是[maxlen,sumlen]的最短；
     *    若不能在[maxlen,sumlen-InitLen]找到最短的InitLen，则必有InitLen=sumlen；
     *    3、  由于所有棒子已降序排序，在DFS时，若某根棒子不合适，则跳过其后面所有与它等长的棒子；
     *    4、  最重要的剪枝：对于某个目标InitLen，在每次构建新的长度为InitLen的原始棒时，检查新棒的第一根stick[i]，
     *    若在搜索完所有stick[]后都无法组合，则说明stick[i]无法在当前组合方式下组合，不用往下搜索(往下搜索会令stick[i]被舍弃)，直接返回上一层
     *
     */

     public int initsLen;  //sticks的最短长度
     public int sticksCount;
     public int bestRes = Integer.MAX_VALUE;
     public boolean isAugment[] = new boolean[65];
     public boolean isFlag = false;

     //返回bool值，表示在指定区间内递增的initLen是否满足(存在某几项的累加和 = initLen，并且num = sticksCount
     public boolean dfs(int num, int sticks[], int step, int len){

         if(num == sticksCount){
             isFlag = true;
             return true;
         }

         for(int i = 0; i < sticks.length; i++){

             //如果该位置上的数已经累加过，则跳过
             if(isAugment[i] == true)
                 continue;

             //否则该位置可以进行累加，注意最后该位置需要恢复原样（解锁，其他"线程"也能进入这个房间）
             else{
                 isAugment[i] = true;

                 //如果累加项 < initsLen，继续向下找
                 if(len + sticks[i] < initsLen){
                     dfs(num,sticks,i+1,len + sticks[i]);
                 }
                 //有累加项 = initsLen,则num+1,累加项len清0,重新找len是否 = initsLen，对num进行统计，直到num = sticksCount结束
                 else if(len + sticks[i] == initsLen){
                     dfs(num + 1,sticks,0, 0);
                 }

                 //如果isFlag标识符为true，则num == sticksCount，返回true,表示该initLen可以实现
                 if(isFlag == true){
                     return true;
                 }
                 isAugment[i] = false;
             }
         }
         return false;
     }

     @Test
     public void test(){

         int num = 9;
         int input[] = {5,2,1,5,2,1,5,2,1};

         //对sticks段进行排序
         Arrays.sort(input);

         //得到initsLen的区间两端点
         int maxsLen = input[input.length - 1]; //sticks片段中最长
         int sumsLen = 0;  //sticks的总长度

         for(int i = 0; i < input.length; i++){
             sumsLen += input[i];
         }

         for(initsLen = maxsLen; initsLen < sumsLen; initsLen++){

             //剪枝1 sumlen % Initlen == 0,则InitLen不满足
             if(sumsLen % initsLen != 0){
                 continue;
             }else{
                 //假设当前initsLen是可行解，则最终stickCount的数目为sumsLen/initsLen
                 sticksCount = sumsLen/initsLen;

                 //如果dfs满足条件，则打印initLen
                 if(dfs(1,input,0,0)){
                     System.out.println("sticks最短长度为:" + initsLen);
                     break;
                 }
             }
         }
     }
}
