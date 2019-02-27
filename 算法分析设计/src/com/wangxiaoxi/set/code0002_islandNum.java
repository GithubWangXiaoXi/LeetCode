package com.wangxiaoxi.set;

import org.junit.Test;

public class code0002_islandNum {

    /** 根据矩阵上的标记，判断该矩阵一共有多少个岛（大片标记了1区域的是1个岛）*/

    /**
     *  实现思路: 判断该位置是否为1，如果是1，则启动感染函数，将大片区域感染成2，num++
     */


    public int getislandNum(int matrix[][]){

        int row = matrix.length;
        int col = matrix[0].length;
        int islandNum = 0;

        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(matrix[i][j] == 1){
                    infect(matrix,i,j,row,col);
                    islandNum++;
                }
            }
        }

        return islandNum;
    }

    public void infect(int matrix[][], int i, int j, int row, int col){
        if(i < 0 || i >= row  || j < 0 || j >= col || matrix[i][j] != 1){
            return;
        }
        matrix[i][j] = 2;  //将矩阵中的1感染成2
        infect(matrix,i + 1,j,row,col); //感染下边的元素
        infect(matrix,i - 1,j,row,col); //感染上边的元素
        infect(matrix,i,j - 1,row,col); //感染左边的元素
        infect(matrix,i,j + 1,row,col); //感染右边的元素
    }

    //

    @Test
    public void islandNumTest(){

        int row = 4;
        int col = 6;

        int matrix[][] = {{1,0,0,0,1,1},
                          {1,0,0,0,1,1},
                          {1,0,0,0,1,1},
                          {1,0,0,0,1,1}};

        int matrix1[][] = {{1,0,1,0,1,1},
                          {1,1,0,0,1,1},
                          {1,0,1,0,0,1},
                          {1,0,0,0,1,1}};

        int islandNum = getislandNum(matrix1);
        System.out.println("岛的个数为:" + islandNum);
    }
}
