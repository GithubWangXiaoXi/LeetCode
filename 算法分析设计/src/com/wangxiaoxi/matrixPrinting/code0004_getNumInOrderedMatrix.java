package com.wangxiaoxi.matrixPrinting;


public class code0004_getNumInOrderedMatrix {

    /**
     *   在行列有序的矩阵中，查找是否存在某个数。
     *   实现思路：由于有特殊的数据状况，则在查找时可以避开一些过大或过小的值，阶梯查找，越界则结束
     *   最终返回数的下标
     */

    public static int[] getNumInOrderedMatrix(int[][] matrix,int num){
        int row = matrix.length - 1;
        int col = matrix[0].length - 1;

        int beginR = 0;
        int beginC = col;
        //x,y是用来返回坐标的，因为beginR,beginC有可能会出现越界，坐标不正确
        int x = 0;
        int y = 0;

        //起始值为右上角的点(小于或大于begin的方向只有一种，不存在二义性)
        int begin = matrix[beginR][beginC];

        while(beginR != row + 1 && beginC != -1){
            //如果begin比num大，则num向左移动
            if(begin > num){
                begin = matrix[beginR][beginC--];
                y = beginC+1;
            }
            //如果begin比num小，则num向下移动
            else if(begin < num){
                begin = matrix[beginR++][beginC];
                x = beginR-1;
            }else{
                break;
            }
        }

        if(beginR == row + 1 || beginC == -1){
            return null;
        }else{
            return new int[] { x , y };
        }
    }

    public static int[][] generateMatrixInColAndRowOrder(int row,int col){
        int[][] matrix = new int[row][col];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(i == 0){
                    int num = (int) (Math.random()*10+1);
                    while(j >= 1 && num < matrix[0][j-1]){
                        num = (int) (Math.random()*10+1);
                    }
                    matrix[0][j] = num;
                }else{
                    matrix[i][j] = matrix[i-1][j]+10;
                }
            }
        }
        return matrix;
    }

    public static void showArrays(int matrix[][], int row, int col){
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] matrix = generateMatrixInColAndRowOrder(5,5);
        System.out.println("原始matrix:");
        showArrays(matrix,5,5);

        int num = 15;
        System.out.println("要查找的数:"+num);

        System.out.println("查找的数的坐标位置:");
        int[] res = getNumInOrderedMatrix(matrix,num);
        if(res != null){
            System.out.println("("+res[0]+","+res[1]+")");
        }
        else{
            System.out.println("null");
        }
    }
}
