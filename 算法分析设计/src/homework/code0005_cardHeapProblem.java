package homework;

public class code0005_cardHeapProblem {

    /**
     *  40张card
     *  5 13 17 5
     *  1） 10 8 17 5
     *  2）10 10 15 5
     *  3）10 10 10 10
     *
     *  如果left < partSum, left从right中取数
     *  如果left > partSum,left将多的数移到right
     */

    public static int makecardHeapEqual(int cardHeap[],int partSum){

        int supplement;
        int methodCount = 0;

        for(int i = 0; i < cardHeap.length - 1; i++){

            //判断所有堆卡片是否相等
            if(isEqualHeap(cardHeap,partSum)){
                return methodCount;
            }

            //如果left < partSum, left从right中取数
            if(cardHeap[i] < partSum){
                supplement = partSum - cardHeap[i];
                cardHeap[i] = partSum;
                cardHeap[i + 1] = cardHeap[i + 1] - supplement;
                System.out.println((i + 2) + "--->" + (i + 1) + ":" + supplement + "张");
            }
            //如果left > partSum,left将多的数移到right
            else{
                supplement = cardHeap[i] - partSum ;
                cardHeap[i] = partSum;
                cardHeap[i + 1] = cardHeap[i + 1] + supplement;
                System.out.println((i + 1)  + "--->" + (i + 2) + ":" + supplement + "张");
            }

            methodCount++;
        }

        return methodCount;
    }

    public static int getSum(int cardHeap[]){
        int sum = 0;
        for(int i = 0; i < cardHeap.length; i++){
            sum += cardHeap[i];
        }
        return sum;
    }

    public static void showCardHeap(int cardHeap[]){
        for(int i = 0; i < cardHeap.length; i++){
            System.out.print(cardHeap[i] + " ");
        }
        System.out.println();
    }

    public static boolean isEqualHeap(int cardHeap[], int partSum){
        for(int i = 0; i < cardHeap.length; i++){
            if(cardHeap[i] != partSum){
                return false;
            }
        }
        return true;
    }

    public static void main(String argvs[]){

        int n = 4;
        int cardHeap[] = {9,8,17,6};

        int sum = getSum(cardHeap);
        showCardHeap(cardHeap);

        if(sum % n == 0){
            int methodCount = makecardHeapEqual(cardHeap,sum/n);
            System.out.println("一共需要" + methodCount + "步");
            showCardHeap(cardHeap);
        }
        else{
            System.out.println("sum不能形成数量相等的堆");
        }


    }
}
