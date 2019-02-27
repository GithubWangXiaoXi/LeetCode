package com.wangxiaoxi.matrixPrinting;

public class code0001_printMatrixSpiralOrder {

    /**
     * 循环依次打印矩阵：宏观思路：确定左上和右上的点就可以打印一圈，左上角的点斜向下移动，右上角的点斜向上移动
     * 当左上点A和右下点B出现A.x > B.x 或 A.y > B.y，则跳出打印
     */

    private static int endRow;    //右下角的点斜向上移动时的行坐标
    private static int endCol;
    private static int beginRow;  //左上角的点斜向下移动时的行坐标
    private static int beginCol;

    public static void spiralOrder(int matrix[][]){
        int aR = 0;
        int aC = 0;
        int bR = matrix.length-1;  //通过一维数组获取矩阵的行数
        int bC = matrix[0].length-1; //通过第一个一维数组获取列数
//        int i = 0;    //使用i，累赘

        while(true){
//            aR += i;
//            aC += i;
//            bR -= i;
//            bC -= i;
            //两个条件存在一个，就说明不用再打印了，构成不了圈了
            if(aR > bR || aC > bC){
                break;
            }else{
                printMatrix(matrix,aR++,aC++,bR--,bC--);
            }
//            i=1;
        }
    }

    public static void printMatrix(int[][] matrix,int aR, int aC, int bR, int bC){

        beginRow = aR;
        beginCol = aC;
        endRow = bR;
        endCol = bC;

        //打印上-右(有可能只打印一行）
        while(aC != endCol){
            System.out.print(matrix[aR][aC++]+" ");
        }
        //打印右-下(有可能只打印一列）
        while(aR != endRow){
            System.out.print(matrix[aR++][bC]+" ");
        }
        //打印下-左
        while(bC != beginCol){
            System.out.print(matrix[aR][bC--]+" ");
        }
        //打印左-上
        while(bR != beginRow){
            System.out.print(matrix[bR--][bC]+" ");
        }
        //如果a,b在同一个点上，则打印该点
        if(beginCol == endCol && beginRow == endRow){
            System.out.print(matrix[beginRow][beginCol]);
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[4][5];
        int count = 1;
        System.out.println("matrix：");
        for(int i = 0;i<4;i++){
            for(int j = 0;j<5;j++){
                matrix[i][j] = count;
                System.out.print(count+" ");
                count++;
            }
            System.out.println();
        }

        System.out.println("spiralOrder:");
        spiralOrder(matrix);

    }
}
