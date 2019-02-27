package com.wangxiaoxi.Orders;

import java.util.Arrays;

public class code1000_correctCountMachine {

    /**
     *  对数器:需要一个结构产生器，可以随机产生数组大小和数组元素，二叉树等
     *         需要一个绝对正确的方法
     *         需要一个“两个方法”校验结果的方法
     */

    //随机数组产生器
    public static int[] generateArray(int size){
        int temp = size + 1; //便于产生随机的[0,size]之间的随机大小的数组
        int arr[] = new int[(int) (Math.random()*temp)];

        for(int i = 0;i<arr.length;i++){
            arr[i] = (int) (Math.random()*10)+1;
        }

        return arr;
    }

    //结果校验的方法
    public static boolean isEqualForArrays(int arr1[],int arr2[]){
        //考虑两者比较的所有情况！！
        if(arr1 == null && arr2 != null || arr2 == null && arr1 != null){
            return false;
        }
        if(arr1 == null && arr2 == null){
            return true;
        }
        if(arr1.length != arr2.length){
            return false;
        }
        else{
            for(int i=0; i<arr1.length; i++){
                if(arr1[i] != arr2[i]){
                    return false;
                }
            }
            return true;
        }
    }

    public static int[] copyArray(int arr[]){

        if(arr == null){
            return null;
        }

        int arr1[] = new int[arr.length];
        for(int i=0;i<arr.length;i++){
            arr1[i] = arr[i];
        }
        return arr1;
    }

    public static void showArrays(int arr[]){
        for(int i = 0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int exampleN = 10;
        boolean result = true;
        for(int i = 0;i<testTimes;i++){
            int arr[] = generateArray(10);
            int arr1[] = copyArray(arr);  //使用正确方法
            int arr2[] = copyArray(arr);   //输出原来的样本数据

            insertSort(arr);
            Arrays.sort(arr1);

            boolean tempResult = isEqualForArrays(arr,arr1);

            //如果有报错，则输出两个拍完序的序列，并输出测试用例
            if(tempResult == false){
                result = false;
                System.out.print("arr ");
                showArrays(arr);

                System.out.print("arr1 ");
                showArrays(arr1);

                System.out.print("arr2 ");
                showArrays(arr2);
                break;
            }
        }
        System.out.println(result?"Excellent":"awful");
    }

    private static void insertSort(int[] arr) {

        if(arr.length < 2 || arr == null){
            return;
        }else{
            for(int i = 0;i<arr.length-1;i++){
                //如果arr[j]<arr[j-1]才交换
                for(int j = i+1;j>0 && arr[j]<arr[j-1];j--){
                    swap(arr,j,j-1);
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
