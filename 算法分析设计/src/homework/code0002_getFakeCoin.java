package homework;

import org.junit.Test;

public class code0002_getFakeCoin {

    public int getFakeCoin(int array[],int left,int right){

        if(left == right){
            return left;
        }

        int middle = left + (right - left)/2;
        //得到左边的最小值的数组下标
        int indexleft =  getFakeCoin(array,left,middle);

        //得到最右边的最小值的数组下标
        int indexright = getFakeCoin(array,middle+1,right);

        //两者进行比较
        int result = array[indexleft] < array[indexright] ? indexleft : indexright;
        return result;
    }

    @Test
    public void getFakeCoinTest(){

        int coinArr[] = {3,4,5,2,6,8,9};
        int result = getFakeCoin(coinArr,0,coinArr.length - 1);
        System.out.print("coinArr[" + result + "] = " + coinArr[result] + "为假币");

    }

}
