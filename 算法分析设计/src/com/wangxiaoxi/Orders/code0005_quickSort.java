package com.wangxiaoxi.Orders;

public class code0005_quickSort {

    public static void quickSort(int left,int right,int arr[]){
       if(left<right){
           int part[] = partition(left,right,arr);
           quickSort(left,part[0],arr);
           quickSort(part[1],right,arr);
       }
    }

    //荷兰国旗，将序列分成小于，等于，大于三个部分,返回小于，大于的下标
    public static int[] partition(int left,int right,int arr[]){
         int less = left-1;
         int more = right;
         while(left<more){
             if(arr[left] < arr[right]){
                 swap(left,++less,arr);
                 left++;
             }
             else if(arr[left]>arr[right]){
                 //这里保留最后一个数，知道最后前面处理完后，再将最后的数归位
                 swap(left,--more,arr);
             }else {
                 left++;
             }
         }
         //需要将right上的数字归位
         swap(right,more,arr);
         return new int[] {less,more+1};
    }

    public static void swap(int i,int j,int arr[]){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void showArrays(int arr[]){
        for(int i = 0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    //随机数组产生器
    public static int[] generateArray(int size){
        int temp = size + 1; //便于产生随机的[0,size]之间的随机大小的数组
        int arr[] = new int[(int) (Math.random()*temp)];

        for(int i = 0;i<arr.length;i++){
            arr[i] = (int) (Math.random()*10)+1;
        }

        return arr;
    }

    public static void main(String[] args) {
        int arr[] = generateArray(10);
        System.out.print("example: ");
        showArrays(arr);

        quickSort(0,arr.length-1,arr);
        System.out.print("resultSeq: ");
        showArrays(arr);
    }
}
