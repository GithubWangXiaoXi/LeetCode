package com.wangxiaoxi.dp;

import org.junit.Test;

public class code0001_minPath {

    /** 问题描述：计算从矩阵左上角的点到右下角的点的最短路径，矩阵中的数表示路径的权值*/

    /**
     *  实现思路：
     *  1，先用暴力递归尝试的方式来解决：a[i][j]到最右下角的minPath依赖于a[i+1][j](向下移动) 和 a[i][j+1](向上移动)
     *  2, 中止条件分成3种：
     *     1）a到达最后一行，则只能向右移动，minPath依赖于右边的点
     *     2）a到达最后一列，则只能向下移动，minPath依赖于下边的点
     *     3）a到达最右下角的点，则结束
     */
    public int getMinPath(int matrix[][], int i, int j){

        //到达最右下角点
        if(i == matrix.length - 1 && j == matrix[0].length - 1){
            return matrix[i][j];
        }

        //到达最后一行,minPath依赖于右边的点
        if(i == matrix.length - 1){
            return matrix[i][j] + matrix[i][j+1];
        }

        //到达最后一列,minPath依赖于下边的点
        if(j == matrix[0].length - 1){
            return matrix[i][j] + matrix[i+1][j];
        }

        //普遍位置则依赖于右,下点，minPath最小值为 min(右,下) + cur
//        int i1;
//        int j1;
//        i1 = getMinPath(matrix,i + 1,j) < getMinPath(matrix,i,j + 1) ? i + 1 : i;
//        if(i1 == i+1){
//            j1 = j;
//        }else{
//            j1 = j + 1;
//        }

//        return matrix[i][j] + getMinPath(matrix,i1,j1);
        return matrix[i][j] + Math.min(getMinPath(matrix,i+1,j),getMinPath(matrix,i,j+1));
    }

    //通过暴力递归改成动态规划(注意关键步骤,搭积木填表，非递归)
    //暴力递归是通过回溯来实现的，而动态规划是用dp表记录每条分支路的最小值，我们可以根据暴力递归的思路逆着来绘制dp表
    //这里是先绘制末行，末列的。  其实可以从a[0][0]开始，绘制首行，首列，再来处理普遍点。
    public int getMinPath1(int matrix[][]){

         if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
             return 0;
         }

         int row = matrix.length;
         int col = matrix[0].length;
         int dpMatrix[][] = new int[row][col];   //建立一张动态规划表


         dpMatrix[row - 1][col - 1] = matrix[row - 1][col - 1];

         //先搭最末行
         for(int j = col - 2; j >= 0; j--){
             //最后一行倒数第2个数的值由最dp右边的值 + matrix当前值决定
             dpMatrix[row - 1][j] = dpMatrix[row - 1][j + 1] + matrix[row - 1][j];
         }

         //再搭最右一列
         for(int i = row - 2; i >= 0 ; i--){
             //最后一列倒数第2个数的值由dp最下边的值 + matrix当前值决定
             dpMatrix[i][col - 1] = dpMatrix[i + 1][col - 1] + matrix[i][col - 1];
         }

         //普遍位置,从末行 - 1，末列 - 1位置开始累加返回值
         for (int i = col - 2; i >= 0; i--){
             for(int j = col - 2; j >= 0; j--){
                 dpMatrix[i][j] = Math.min(dpMatrix[i+1][j],dpMatrix[i][j+1]) + matrix[i][j];
             }
         }

         return dpMatrix[0][0];
    }

    @Test
    public void test(){
        int matrix[][] = { { 1, 3, 5, 9 },
                           { 8, 1, 3, 4 },
                           { 5, 0, 6, 1 },
                           { 8, 8, 4, 0 } };
        int result = getMinPath(matrix,0,0);
        System.out.println("minPath:" + result);
        System.out.println("minPath1:" + getMinPath1(matrix));
    }
}
