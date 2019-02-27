package com.wangxiaoxi.recursion;


public class code0101_reverseCouple {

    private static int[] help = null;

    public code0101_reverseCouple(int arr[]){
        help = new int[arr.length];
    }

    public static void reverseCouple(int left,int right,int arr[]){
         if(arr == null || arr.length < 2){
             return;
         }
         if(left == right){
             return;
         }
         int middle = left + (right-left)/2;
         //求左边的存在的逆序对
         reverseCouple(left,middle,arr);
         //求右边的存在的逆序对
         reverseCouple(middle+1,right,arr);
         merge(left,right,middle,arr);

    }

    private static void merge(int left, int right, int middle, int[] arr) {

        int p1 = left;
        int p2 = middle+1;
        int k = left;
        int key = 0;
        int value = 0;
        while(p1 <= middle && p2 <= right){
            int temp = p1;
            //对于左右两边各有序的序列，如果P1>P2,则P1后的所有的数都大于P2
            if(arr[p1] > arr[p2]){
                value = arr[p2];
                while(temp<=middle){
                    key = arr[temp++];
                    if(key != 0 && value != 0){
                        System.out.println("<"+key+","+value+">");
                    }
                }
            }
            //只有arr[p1]<arr[p2]才能解开flag = true；因为p1(key)，前进了
            help[k++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while(p1<=middle){
            help[k++] = arr[p1++];
        }
        while(p2<=right){
            help[k++] = arr[p2++];
        }

        for(int i = left;i<=right;i++){
            arr[i] = help[i];
        }
        return;
    }

//    public static void copyArray(int arr[]){
//        for(int i = 0;i<arr.length;i++){
//            arr[i] = help[i];
//        }
//    }

    public static void main(String[] args) {
        int[] arr = {1,3,4,2,1};
        System.out.print("序列:");
        for(int i = 0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();

        code0101_reverseCouple object = new code0101_reverseCouple(arr);
        object.reverseCouple(0,arr.length-1,arr);

//        object.copyArray(arr);
        for(int i = 0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }
}
