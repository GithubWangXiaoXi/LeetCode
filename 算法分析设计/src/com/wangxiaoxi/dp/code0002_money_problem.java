package com.wangxiaoxi.dp;

import org.junit.Test;

public class code0002_money_problem {

    /** 问题描述：在数组中任意选择几个数，它们累加组成的数sum 能否等于 aim
     *  为了简化问题：这里假定数组中的数都是整数，返回的结果为bool值，表示是否sum = aim。
     */

    /**
     *  解决思路：
     *  1，先使用暴力递归的方式来实现（数组中的每个数只有2种选择）
     *  2，
     */

    public boolean isSumEqualAim(int array[], int sum, int index, int aim){

        //如果index越界了,则说明sum已经累加完毕
        if(index == array.length){

            return sum == aim;

        }else{
            //当前的数不选择
            boolean result1 = isSumEqualAim(array, sum, index + 1,aim);

            //当前的数选择
            boolean result2 = isSumEqualAim(array,sum + array[index],index + 1, aim);

            //第一个数a能否与后面的数组成sum = aim，依赖于第二个数b能否取到sum = aim，这时a有两种选择。
            return result1 || result2;

//            //每个数都有两种情况 return a||b的意思就是如果a是true则返回a,否则返回b(false/true)
//            return isSumEqualAim(array, sum, index + 1,aim) || isSumEqualAim(array,sum + array[index],index + 1, aim);
        }
    }

    //由于返回值为true/false，无后效性，可以将暴力递归改成用动态规划，根据aim逆向将依赖的位置填入行数为array.length+1，列数为 aim + 1 的dp表中
    public boolean isSumEqualAim1(int array[],int aim){

        boolean dpMatrix[][];
//        int sum1 = 0;

//        for(int i = 0; i < array.length; i++){
//            sum1 += array[i];
//        }
        int col = aim + 1;  //matrix的列数aim + 1列就行，不用到数组中sum的最大值，节省matrix的空间
        int row = array.length + 1;  //比array多一行

        dpMatrix = new boolean[row][col];

//        //如果aim大于sum，则返回false
//        if(aim > sum1){
//            return false;
//        }

        //先绘制最后一行，将目标值所在位置标为true
        dpMatrix[row - 1][col - 1] = true;
        for (int i = 0; i < col - 1; i++){
            dpMatrix[row - 1][i] = false;
        }

        //绘制普遍位置：假设aim = 10，倒数第二行数A = 1
        //1有两种情况 不选该值: [(row - 2 , i) 对应 (row - 1 , i)],和选该值: [(row - 2, i) 对应 (row - 1 , i + array[row-1])]
        //正着思考难以理解，可以逆着思考: 在10确定的情况下, 1可能是10中某一个累加项，也可能不是，所以倒数第二行的取值只能9,10，倒数第二行确定了，倒数第三行也可以计算出来
        for(int i = row - 2; i >= 0; i--){
            for(int j = 0; j <= col - 1; j++){

                dpMatrix[i][j] = dpMatrix[i+1][j];

                //如果当前位置的列坐标+array的值不越界的话,则dpMatrix[i][j] = dpMatrix[i+1][j+array[i]]
                if(j + array[i] < col){
                    dpMatrix[i][j] = dpMatrix[i][j] || dpMatrix[i + 1][j + array[i]];
                }
            }
        }

        //最右下角的点为aim,如果最左上角的点为true,则左上到右下的路径可达
        //逆着来，下一行减去上一行的对应的array值，往上一行的前面标记为true。依次往上，依次向前标记，必会到达起始入点[0][0]
        return dpMatrix[0][0];
    }


    //随机数组产生器
    public static int[] generateArray(int size){

        int arr[] = new int[size];

        for(int i = 0;i<arr.length;i++){
            arr[i] = (int) (Math.random()*10)+1;
        }

        return arr;
    }

    @Test
    public void money_problemTest(){

//        int array[] = generateArray(3);
//
//        System.out.println("数组各元素为:");
//        int sum = 0;
//        for(int i = 0; i < array.length; i++){
//            sum += array[i];
//            System.out.print(array[i] + " ");
//        }
//        System.out.println("sum = " + sum);

        int array[] = {2,8,1};

        int aim = 10;
        boolean result = isSumEqualAim1(array,aim);
        System.out.println("能否使得sum = aim = "+ aim +" : " + result);
    }
}
