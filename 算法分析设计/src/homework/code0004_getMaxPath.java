package homework;

import org.junit.Test;

public class code0004_getMaxPath {

    /**
     *   楼塔问题：
     *   9
     *   12 15
     *   10 6 8
     *   2 18 9 5
     *   19 7 10 4 16
     */

    public int getMaxPath(int matrix[][], int indexI, int indexJ, int sum){

        if(indexI == matrix.length){
            return sum;
        }

        //在下方两个路径中选最大的一条
        int value1 = getMaxPath(matrix, indexI + 1, indexJ, sum + matrix[indexI][indexJ]);
        int value2 = getMaxPath(matrix,indexI + 1, indexJ + 1, sum + matrix[indexI][indexJ]);

        return Math.max(value1,value2);
    }

    public int getMaxPath1(int matrix[][]){

        int row = matrix.length;
        int col = matrix[row - 1].length;
        int dp[][] = new int[row][col];


        //先绘制最后一行(最后一行各数据是相互独立的，倒数第二行依赖倒数第一行的值，所以倒数第一行是baseLine)
        for(int j = 0; j < col; j++){
            dp[row - 1][j] = matrix[row - 1][j];
        }

        //绘制普通位置的点,在正下方/正下方+1的位置，取两者的最大值，最终得到最大路径
        for (int i = row - 2; i >= 0; i--){
            for(int j = 0; j <= i ; j++){
                dp[i][j] = matrix[i][j] + Math.max(dp[i + 1][j],dp[i + 1][j + 1]);
            }
        }

        return dp[0][0];
    }

    @Test
    public void getMinPathTest(){

        int matrix[][] = {{9},{12, 15},{10, 6, 8},
                {2, 18, 9, 5},
                {19, 7, 10, 4, 16}};

        int maxPath = getMaxPath(matrix,0,0,0);
        int maxPath1 = getMaxPath1(matrix);

        System.out.println("maxPath:" + maxPath);
        System.out.println("maxPath1:" + maxPath1);
    }
}
