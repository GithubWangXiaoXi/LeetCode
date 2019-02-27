package com.wangxiaoxi.Orders;

public class code0003_insertSort {

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

    public static void main(String[] args) {
        int[] arr = {1,5,10,3,6,8};

        insertSort(arr);

        for(int i = 0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }
}
