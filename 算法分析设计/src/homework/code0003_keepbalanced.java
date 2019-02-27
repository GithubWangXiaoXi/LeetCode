package homework;

public class code0003_keepbalanced {

    public static int getBalancedMethod(int array[]) {

        int sum = 0;
        for(int i = 0; i < array.length; i++) {
            sum += array[i];
        }

        int col = array.length + 1;
        int row = sum/2 + 1;
        int dpmatrix[][] = new int[row][col];

        //先绘制第一列
        for(int i = 0; i < row; i++) {
            dpmatrix[i][0] = 0;
        }

        //再绘制第一行
        for(int j = 0; j < col; j++) {
            dpmatrix[0][j] = 1;
        }

        //再通过依赖式子，绘制普遍位置,每一列每一列
        for(int j = 1; j < col; j++) {
            for(int i = 1; i < row; i++) {

                if( i < array[j - 1]) {
                    dpmatrix[i][j] = 0;
                }
                else {
                    dpmatrix[i][j] = dpmatrix[i][j - 1] + dpmatrix[i - array[j - 1]][j - 1];
                }
            }
        }

//		//打印dp表
//		for(int i = 0; i < row; i++) {
//			for(int j = 0; j < col; j++) {
//				System.out.print(dpmatrix[i][j] + " ");
//			}
//			System.out.println();
//		}

        //输出:
        System.out.print("输出: ");
        for(int i = 0; i < row; i++) {
            System.out.print(dpmatrix[i][col - 1] + " ");
        }
        System.out.println();

        return dpmatrix[row - 1][col - 1];

    }

    public static void  main(String[] argv) {
        int array[] = {7,6,4,3};
        int method = getBalancedMethod(array);
        System.out.print("balance method = " + method);
    }

}