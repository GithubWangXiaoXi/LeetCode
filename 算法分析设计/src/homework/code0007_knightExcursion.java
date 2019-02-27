package homework;

import org.junit.Test;

public class code0007_knightExcursion {

    /**
     *  骑士游历：
     *  骑士骑马。在棋盘上只能走"日"字，对于1个点，可以往四周走日
     */

    private int sumStep;
    private int orientation[][] = {{-1,-2},{-1,2},{1,-2},{1,2},
                                   {-2,-1},{-2,1},{2,-1},{2,1}};
    private int chessboard[][];
//    private int mark[][];  //用来标记当前位置可以走的方向一共有几个



    public void dfs(int step, int coordX, int coordY){

        if(step == sumStep + 1){
            showChessBoard(chessboard);
            return;
        }

        chessboard[coordX][coordY] = step;//当前位置
        int tempX = coordX;
        int tempY = coordY;

        //起点往8个方向走
        for(int i = 0; i < orientation.length; i++){

            coordX = tempX + orientation[i][0];
            coordY = tempY + orientation[i][1];

            if(coordX < 0 || coordX >= chessboard.length || coordY < 0 || coordY >= chessboard[0].length){
                continue;
            }

            if(chessboard[coordX][coordY] == 0){
                dfs(step + 1,coordX,coordY);
                //恢复状态
                chessboard[coordX][coordY] = 0;
            }
//            showChessBoard(chessboard);
        }
    }

    //使用预见算法实现的dfs
    public void advancedDfs(int step, int coordX, int coordY){

        if(step == sumStep + 1){
            showChessBoard(chessboard);
        }

        chessboard[coordX][coordY] = step;//当前位置
        int tempX = coordX;
        int tempY = coordY;
        int signCount[] = new int[8];  //用一维数组来标记8个位置可以走的方向一共有几个

        // 记录当前位置的8个方向，可走的位置有哪些
        for(int i = 0; i < orientation.length; i++){

            coordX = tempX + orientation[i][0];
            coordY = tempY + orientation[i][1];

            if(coordX < 0 || coordX >= chessboard.length || coordY < 0 || coordY >= chessboard[0].length){
                continue;
            }

            //用signCount记录试探的位置周围有多少可走的路线(如果先将mark标记出来，会出现问题，因为点的位置是变化的，而mark上的路线次数是死的）
            setSignCount(signCount,coordX,coordY,i);
        }

        coordX = tempX;
        coordY = tempY;

        //开始根据signCount[i]中的路线数进行dfs
        for(int i = 0; i < signCount.length; i++){

            int decidedX = -1;
            int decidedY = -1;
            int index = -1;
            int minRoute = Integer.MAX_VALUE;

            for(int j = 0; j < signCount.length; j++){
                if(signCount[i] != 0 && signCount[i] < minRoute){
                    minRoute = signCount[i];
                    index = i;
                }
            }

            //如果index = -1，则无路可选
            if(index != -1){
                decidedX = coordX + orientation[index][0];
                decidedY = coordY + orientation[index][1];
                signCount[index] = 0; //表示该位置用过
                advancedDfs(step + 1,decidedX,decidedY);
                chessboard[decidedX][decidedY] = 0;
            }else{
                break;
            }
        }
    }

    public void setSignCount(int signCount[],int coordX,int coordY,int index){
        int coordX1 = coordX;
        int coordY1 = coordY;

        int count = 0;

        for(int j = 0; j < orientation.length; j++){

            coordX1 = coordX + orientation[j][0];
            coordY1 = coordY + orientation[j][1];

            if(coordX1 < 0 || coordX1 >= chessboard.length || coordY1 < 0 || coordY1 >= chessboard[0].length){
                continue;
            }

            if(chessboard[coordX1][coordY1] == 0){
                count++;
            }
        }

        signCount[index] = count;
    }

    public void showChessBoard(int chessboard[][]){
        for(int i = 0; i < chessboard.length; i++){
            for(int j = 0; j < chessboard[0].length; j++){
                System.out.print(chessboard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("*************************************");
    }

    @Test
    public void main(){

        int row = 8;
        int col = 8;
        chessboard = new int[row][col];

        int startCoordX = 3;
        int startCoordY = 3;

        showChessBoard(chessboard);

        sumStep = row * col;

        System.out.println("dfs:");
        advancedDfs(1,startCoordX,startCoordY);
    }
}
