package com.wangxiaoxi.Orders;

public class code0002_selectionSort {

    public static void selectionSort(int arr[]){

        //首先需要去掉不用排序的情况
        if(arr == null ||arr.length<2){
            return;
        }

        for(int i = 0;i<arr.length-1;i++){
            //初始化最小下标为i
            int minIndex = i;
            for(int j = i+1;j<arr.length;j++){
                minIndex = arr[j] < arr[minIndex] ? j:minIndex;
            }
            swap(arr,minIndex,i);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {1,5,10,3,6,8};

        selectionSort(arr);

        for(int i = 0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }
}
