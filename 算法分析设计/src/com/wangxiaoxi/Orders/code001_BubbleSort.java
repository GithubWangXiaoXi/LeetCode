package com.wangxiaoxi.Orders;

public class code001_BubbleSort {

    public static void bubbleSort(int arr[]){

        if(arr == null || arr.length < 2){
            return;
        }

        for(int end = arr.length-1;end>0;end--){
            for(int i = 0;i<arr.length-1;i++){
                if(arr[i]>arr[i+1]){
                    swap(arr,i,i+1);
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

        bubbleSort(arr);

        for(int i = 0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }
}
