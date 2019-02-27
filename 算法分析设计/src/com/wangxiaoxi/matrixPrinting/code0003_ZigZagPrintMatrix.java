package com.wangxiaoxi.matrixPrinting;

public class code0003_ZigZagPrintMatrix {
    /**
     *    之字形打印矩阵，实际上是像穿针引线一样输出对角线上的值：实现思路：
     * 想输出对角线方向上的值，可以联想到物理上的合速度，x，y的方向移动，最终的运动轨迹是斜着的
     * 这次虽然也用两点坐标来实现，但是不是使用左上和右下的点来实现。
     * 初始的A,B两点在0,0的位置
     * A点先向右运动，到右边界则向下运动
     * B点先向下运动，到下边界后则向右移动
     * 知道A，B两点到达同一个位置，则打印结束
     */

    public static void zigZagPrintMatrix(int[][] matrix){

        int endRow = matrix.length - 1;
        int endCol = matrix[0].length - 1;

        int aR = 0;
        int aC = 0;
        int bR = 0;
        int bC = 0;
        boolean flag = false; //flag是用来标识是左下打印还是右上打印，一开始右上打印一个点，再左下打印一条对角线

        //A点先向右运动，到右边界则向下运动
        //B点先向下运动，到下边界后则向右移动
        //因为一开始是斜向上打印一个数，所以最后一定是斜向下打印一个数，所以结束时aR == endRow
        while(aR != endRow + 1){
            printZigZag(matrix,aR,aC,bR,bC,flag);

            //A移动
            if(aC < endCol){
               aC++;
            }
            else if(aC == endCol && aR <= endRow){
                aR++;
            }

            //b移动
            if(bR < endRow){
                bR++;
            }
            else if(bR == endRow && bC <= endCol){
                bC++;
            }

            //改变打印的方向
            flag = !flag;
        }
    }

    public static void printZigZag(int[][] matrix, int aR, int aC, int bR, int bC, boolean flag){
        if(flag){
            while(aR != bR + 1){
                System.out.print(matrix[aR++][aC--]+" ");
            }
        }else{
            while(bR != aR - 1){
                System.out.print(matrix[bR--][bC++]+" ");
            }
        }
    }


    public static void main(String[] args) {
        int[][] matrix = new int[3][5];
        int count = 1;
        System.out.println("matrix：");
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 5;j++){
                matrix[i][j] = count;
                System.out.print(count+" ");
                count++;
            }
            System.out.println();
        }

        System.out.println("zigZagPrintMatrix");
        zigZagPrintMatrix(matrix);

    }
}
