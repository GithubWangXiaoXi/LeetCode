package com.wangxiaoxi.matrixPrinting;

public class code0002_rotateMatrix {

    /**
     *     顺时针将矩阵旋转90度：宏观思路，绕着左上角的点，右下角的点进行旋转，实质上是一圈上
     * 四个角上的值的交换。下一圈，左上角的点斜向下移动，右下角的点斜向上移动，开始循环
     */

    public static void rotateMatrix(int[][] matrix){
        int aR = 0;
        int aC = 0;
        int bR = matrix.length-1;  //通过一维数组获取矩阵的行数
        int bC = matrix[0].length-1; //通过第一个一维数组获取列数


        while(true){
            //两个条件存在一个，就说明不用再打印了，构成不了圈了
            if(aR > bR || aC > bC){
                break;
            }else{
                swapInCircle(matrix,aR++,aC++,bR--,bC--);
            }
        }
    }

    public static void swapInCircle(int[][] matrix, int aR, int aC, int bR, int bC){
        for(int i = 0; aC+i < bC; i++){
            int temp = matrix[aR][aC+i];
            matrix[aR][aC+i] = matrix[bR-i][aC];
            matrix[bR-i][aC] = matrix[bR][bC-i];
            matrix[bR][bC-i] = matrix[aR+i][bC];
            matrix[aR+i][bC] = temp;
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[5][5];
        int count = 1;
        System.out.println("matrix：");
        for(int i = 0;i<5;i++){
            for(int j = 0;j<5;j++){
                matrix[i][j] = count;
                System.out.print(count+" ");
                count++;
            }
            System.out.println();
        }

        rotateMatrix(matrix);

        System.out.println("rotatedMatrix：");
        for(int i = 0;i<5;i++){
            for(int j = 0;j<5;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }
}
