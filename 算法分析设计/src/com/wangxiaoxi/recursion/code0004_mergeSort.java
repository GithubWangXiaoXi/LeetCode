package com.wangxiaoxi.recursion;

public class code0004_mergeSort {

    public static void mergeSort(int left,int right,int arr[]){

        if(arr.length < 2){
            return;
        }

        if(left == right){
            return;
        }
        int middle = left+(right-left)/2;
        //左边排好序
        mergeSort(left,middle,arr);   //T(n/2)
        //右边排好序
        mergeSort(middle+1,right,arr); //T(n/2)    左右两边时间复杂度=2*T(n/2) = O(n^log2 2） = O(n)
        //将左右两边合并
        merge(left,middle,right,arr); //O(N)
    }

    public static void merge(int left,int middle,int right,int arr[]){
        int[] help = new int[right - left +1];
        int p1 = left;
        int p2 = middle+1;
        int k = 0;
        while(p1 <= middle && p2 <= right){
            //先对help[k],arr[p1],arr[p2]进行处理，然后k，p1，p2才自增
            help[k++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while(p1<=middle){
            help[k++] = arr[p1++];
        }
        while(p2<=right){
            help[k++] = arr[p2++];
        }
        for(int i = 0;i<help.length;i++){
            arr[left+i] = help[i];
        }
    }

    public static void main(String[] args) {
        int[] arr = {1,10,5,3,8,6};

        mergeSort(0,arr.length-1,arr);

        for(int i = 0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }
}
