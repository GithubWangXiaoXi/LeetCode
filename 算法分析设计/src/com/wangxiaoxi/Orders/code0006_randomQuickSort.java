package com.wangxiaoxi.Orders;

public class code0006_randomQuickSort {

    public static void randomQuickSort(int left,int right,int arr[]){
        if(left<right){
            //在序列中随机获取下标，与right数进行交换。
            int index =  (left+(int)(Math.random()*(right-left+1)));
            swap(index,right,arr);
            int part[] = partition(left,right,arr);
            randomQuickSort(left,part[0],arr);
            randomQuickSort(part[1],right,arr);
        }
    }

    //荷兰国旗，将序列分成小于，等于，大于三个部分,返回小于，大于的下标
    public static int[] partition(int left,int right,int arr[]){
        //最好不要在partition中写，不是该函数的功能
//        //在序列中随机获取下标，与right数进行交换。
//        int index =  (left+(int)(Math.random()*(right-left+1)));
//        swap(index,right,arr);
        int less = left-1;
        int more = right+1;
        int num = arr[right];
        while(left<more){
            if(arr[left] < num){
                swap(left,++less,arr);
                left++;
            }
            else if(arr[left]>num){
                swap(left,--more,arr);
            }else {
                left++;
            }
        }

        return new int[] {less,more};
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

        randomQuickSort(0,arr.length-1,arr);
        System.out.print("resultSeq: ");
        showArrays(arr);
    }
}
