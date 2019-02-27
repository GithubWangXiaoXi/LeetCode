package com.wangxiaoxi.dp;

import org.junit.Test;

public class code0003_01Package {

    /** 01背包问题
     *    问题描述：
     *    价值数组v = {8, 10, 6, 3, 7, 2}，
     *    重量数组w = {4, 6, 2, 2, 5, 1}，
     *    背包容量C = 12时对应的m[i][j]数组。
     */

    class Node{
        int value;
        int weight;

        public Node(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }


    //暴力递归,在不超出C的情况下，每个物品有2种选择
    public int getMaxValue(Node nodeArr[], int index, int weightSum, int valueSum, int C){

//         //有可能返回值时weight已超出，所以需要在加入物品时进行控制
//         if(weightSum >= C){
//             return valueSum;
//         }
         if( index >= nodeArr.length){
             return valueSum;
         }
         else{
             //不要该物品
             int value1 = getMaxValue(nodeArr,index + 1, weightSum, valueSum,C);

             int value2;

             //要该物品，判断是否超重
             if(weightSum + nodeArr[index].weight > C){
                 value2 = 0;
             }else{
                 value2 = getMaxValue(nodeArr,index + 1, weightSum + nodeArr[index].weight, valueSum + nodeArr[index].value,C);
             }

             return Math.max(value1,value2);
         }
    }

    //通过暴力递归修改成动态规划
     public int getMaxValue1(Node nodeArr[], int totalWeight){

         int dpMatrix[][];
         int row = nodeArr.length; //行表示物品
         int col = totalWeight + 1;  //列表示背包weight的变化范围

         dpMatrix = new int[row][col];

         //先将第0列添上0
         for(int i = 0; i < row; i++){
             dpMatrix[i][0] = 0;
         }

         //再在第一行上，如果背包体积允许，装上第1个物品
         for(int i = 1; i < col; i++){
             if(i < nodeArr[0].weight){
                 dpMatrix[0][i] = 0;
             }else{
                 dpMatrix[0][i] = nodeArr[0].value;
             }
         }

         //普遍位置:第二行上，
         // 加入的背包B有两种情况:加入（当前背包体积i需要腾出w[1]，让其装下，然后比较装下后和不装下时价值哪个大）
         // 或 不加入（则价值为上一行对应列的值，不加入的原因是背包体积过小）
         for(int i = 1; i < row; i++){
             for(int j = 1; j < col; j++){

                 //如果第二行物品装不下，则不装，价值为第一行物品对应列的值
                 if(j < nodeArr[i].weight){
                     dpMatrix[i][j] = dpMatrix[i-1][j];
                 }else{
                     //如果加入第二行物品的价值大于不加入（及价值为第一行物品对应列的值），则背包需要腾出空间，加入
                     if(dpMatrix[i - 1][j] < dpMatrix[i - 1][j - nodeArr[i].weight] + nodeArr[i].value){
                         dpMatrix[i][j] = dpMatrix[i - 1][j - nodeArr[i].weight] + nodeArr[i].value;
                     }else{
                         dpMatrix[i][j] = dpMatrix[i - 1][j];
                     }
                 }
             }
         }

         return dpMatrix[row - 1][col - 1];
     }

    @Test
    public void packageTest(){

        int value[] = {8, 10, 6, 3, 7, 2};
        int weight[] = {4, 6, 2, 2, 5, 1};

        Node nodeArr[] = new Node[6];

        for (int i = 0; i < value.length; i++){
            Node node = new Node(value[i],weight[i]);
            nodeArr[i] = node;
        }

        for(Node node : nodeArr){
            System.out.println("weight:" + node.weight + " value:" + node.value);
        }

        int C = 12;
        int maxValue = getMaxValue(nodeArr,0,0, 0, C);
        System.out.println("maxValue:" + maxValue);

        int maxValue1 = getMaxValue1(nodeArr, C);
        System.out.println("maxValue1:" + maxValue1);
    }
}
