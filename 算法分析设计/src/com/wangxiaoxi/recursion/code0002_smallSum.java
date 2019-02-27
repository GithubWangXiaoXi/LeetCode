package com.wangxiaoxi.recursion;


/**
 *  eg: {1,3,4,2,5}
 *  1 左边比1小的数：没有
 *  3 左边比3小的数  1
 *  4 左边比4小的数  1，3
 *  2 左边比2小的数  1
 *  5 左边比5小的数  1，3，2，4
 *  small_sum = 1+1+1+1+3+3+2+4 = 16
 *
 *  解法:先求1,3的小和，再把1，3看成整体，求(1,3)，4的小和(用归并排序，可以对两边进行排序，
 *  前边小的作为后面的小和参数。
 */
public class code0002_smallSum {

    private static int[] help = null;

    public code0002_smallSum(int arr[]){
        help = new int[arr.length];
    }

    public static int smallSum(int left,int right,int arr[]){
         if(arr == null || arr.length < 2){
             return 0;
         }
         if(left == right){
             return 0;
         }
         int middle = left + (right-left)/2;
         //求最左边的小和
         int smallSumLeft = smallSum(left,middle,arr);
         //求最右边的小和
         int smallSumRight = smallSum(middle+1,right,arr);
         //求左右两边合并得到的小和
         int smallSumMerge = merge(left,right,middle,arr);

         return smallSumLeft+smallSumRight+smallSumMerge;
    }

    private static int merge(int left, int right, int middle, int[] arr) {

        int p1 = left;
        int p2 = middle+1;
        int k = left;
        int smallSumElement = 0;  //smallSumElement是获取每个数之前的小和的元素，没有对各个元素进行累加
        int mergeResult = 0;
        while(p1 <= middle && p2 <= right){
            smallSumElement = arr[p1] < arr[p2] ? arr[p1] : 0;  //p1,p2只会向后面移动，如果2，5比前面的1大，则这么写只会输出一个1
            smallSumElement = smallSumElement*(right-p2+1);   //eg:这里将5少了个1补回(后面的数是排好序的，2如果比1大，则2后面的数一定比1大！！）
            if(smallSumElement != 0){
                System.out.print(smallSumElement+" ");  //这里输出:1 1 3 2 1 3 4
            }
            mergeResult += smallSumElement;
            help[k++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while(p1<=middle){
            help[k++] = arr[p1++];
        }
        while(p2<=right){
            help[k++] = arr[p2++];
        }

        //每次merge排序后，需要立即将help数组拷贝到arr[]中
        for(int i = left;i<=right;i++){
            arr[i] = help[i];
        }

        System.out.println("mergeResult:"+mergeResult);
        return mergeResult;
    }

    public static void main(String[] args) {
        int[] arr = {1,3,4,2,5};
        code0002_smallSum object = new code0002_smallSum(arr);
        int smallSum = object.smallSum(0,arr.length-1,arr);

        for(int i = 0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
        System.out.println("smallSum:"+smallSum);
    }
}
