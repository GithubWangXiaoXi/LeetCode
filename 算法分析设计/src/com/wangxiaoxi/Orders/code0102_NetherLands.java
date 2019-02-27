package com.wangxiaoxi.Orders;

public class code0102_NetherLands {

    /**
     * 将数列中的数分成左边小于num，右边大于num的区域
     * x < num,则swap cur和less下一个数，并移动less,cur（less<=cur）
     * x = num,则移动cur
     * x > num,则swap cur和more前一个数，并移动more，此时cur需要待定，观察和num的关系(cur>=more，所以数是未知的)
     */
    public static void NetherLandsFlag(int standard,int arr[]){
        int less = -1;
        int more = arr.length;
        int cur = 0;
        while(cur != more){
            //x < num,则swap cur和less下一个数，并移动less,cur（less<=cur）
            if(arr[cur]<standard){
                swap(cur,++less,arr);
                cur++;
            }
            else if(arr[cur] == standard){
                cur++;
            }
            //x > num,则swap cur和more前一个数，并移动more，此时cur需要待定，观察和num的关系(cur>=more，所以数是未知的)
            else {
                swap(cur,--more,arr);
            }
        }
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

        NetherLandsFlag(5,arr);
        System.out.print("resultSeq: ");
        showArrays(arr);
    }
}
