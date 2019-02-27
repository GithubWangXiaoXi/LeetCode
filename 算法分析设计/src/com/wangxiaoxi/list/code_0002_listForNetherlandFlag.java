package com.wangxiaoxi.list;

public class code_0002_listForNetherlandFlag {

    /**
     * 链表实现荷兰国旗问题：
     * 法1：将节点信息保存在Node数组中，对其进行数组的partition，然后将排好序的数组以链表的形式返回（数组是连续分配内存
     * 而链表是动态分配内存，将节点放在数组中，是引用）
     * 法2：将partition分成3部分，less，equal，more，最后3个区域的首尾相接
     */

    static class Node{
        public int data;
        public Node next;

        public Node(int data) {
            this.data = data;
        }
    }

    //法1:    链表通过将节点存储在数组（连续的地址空间中），通过数组的partition解决荷兰问题（注意数组中存储的
    //    节点是对链表地址的引用，所以在partition排序后，拼接到源链表中时，要将链表的末尾元素的next置null，否则会形成环路）
    public static Node listPartiton(Node head, int num){
        if(head == null){
            return head;
        }
        Node[] nodes = storageNodeIntoArray(head);

        System.out.println("array:");
        for(int i = 0; i < nodes.length; i++){
            System.out.print(nodes[i].data+" ");
        }
        System.out.println();

        head = partition1(nodes,num);
        return head;
    }

    public static Node[] storageNodeIntoArray(Node head){

        int size = 0;
        if(head == null){
            return null;
        }

        Node p1 = head;
        while(p1 != null){
            size++;
            p1 = p1.next;
        }

        Node[] nodeArray = new Node[size];

        p1 = head;
        int i = 0;
        while(p1 != null){
            nodeArray[i++] = p1;
            p1 = p1.next;
        }

        return nodeArray;
    }

    public static Node partition1(Node[] nodes,int num){
        int less = -1;
        int more = nodes.length;
        int cur = 0;
        while(cur != more){
            //less
            if(nodes[cur].data < num){
                swap(nodes,++less,cur++);
            }
            //more
            else if(nodes[cur].data > num){
                swap(nodes,--more,cur);
            }
            //equal
            else{
                cur++;
            }
        }

        Node head = nodeArrayToList(nodes);
        return head;
    }

    public static Node nodeArrayToList(Node[] nodes){
        Node head = null;
        Node temp = null;
        for(int i = 0; i < nodes.length; i++){
            if(head == null){
                head = nodes[i];
                temp = head;
            }else{
                temp.next = nodes[i];
                temp = temp.next;
            }
        }
        //数组中的node是对链表节点的引用，如果重新组成链表时，需要对末尾元素next指针置为null，否则会出现环路
        //通过数组下标可以获得对应的list节点
        nodes[nodes.length - 1].next = null;
        return head;
    }

    public static void swap(Node[] nodes, int i, int j){
        Node temp = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = temp;
    }

    //法2:将链表分成三个部分，less,equal,more,每个部分都有各自的头结点和尾节点
    public static Node listPartiton2(Node head, int num){
        Node lessFront = null;
        Node lessRear = null;
        Node equalFront = null;
        Node equalRear = null;
        Node moreFront = null;
        Node moreRear = null;

        Node p1 = head;
        while(p1 != null){
            //小于num放在less链表中
            if(p1.data < num){
                if(lessFront == null){
                    lessFront = p1;
                    lessRear = lessFront;
                }else{
                    lessRear.next = p1;
                    lessRear = lessRear.next;
                }
            }
            //大于num放在more链表中
            else if(p1.data > num){
                if(moreFront == null){
                    moreFront = p1;
                    moreRear = moreFront;
                }else{
                    moreRear.next = p1;
                    moreRear = moreRear.next;
                }
            }
            else{
                if(equalFront == null){
                    equalFront = p1;
                    equalRear = equalFront;
                }else{
                    equalRear.next = p1;
                    equalRear = equalRear.next;
                }
            }
            p1 = p1.next;
        }
        //将less,equal,more链表的末尾节点的next指针置为null，防止形成环
        if(lessRear != null){
            lessRear.next = null;
        }
        if(moreRear != null){
            moreRear.next = null;
        }
        if(equalRear != null){
            equalRear.next = null;
        }

        //将三个子链表首尾相连,先equal和more连（equal，more有可能空），再less和equal连（equal有可能空）
        //       如果先less连接equal，equal再连接more。如果只有more区域，equal为null，less也为null;
        //       less如果先连equal，equal为空，equal再和more连，equal和more连上了，但是less却没有和equal连上

        //先连equal-more,保证后面一定能连上
        if(equalRear != null){
            equalRear.next = moreFront;
        }else{
            //如果equal子链表不存在，则将equalFront移动到moreFront区域
            equalFront = moreFront;
        }

        if(lessRear != null){
            lessRear.next = equalFront;
        }else{
            //如果less子链表不存在，则将lessFront移动到equalFront区域，保证lessFront一定有节点
            lessFront = equalFront;
        }

        head = lessFront;
        return head;
    }


    //通过数组来给节点赋值
    public static Node createNodeByArray(int array[]){
        Node head = null;
        Node temp1 = null;

        for(int i = 0; i < array.length; i++){
            if(head == null){
                head = new Node(array[i]);
                temp1 = head;
            }else{
                temp1.next = new Node(array[i]);
                temp1 = temp1.next;
            }
        }
        return head;
    }

    public static void showNodeList(Node head){
        Node p1 = head;
        while(p1 != null){
            System.out.print(p1.data+" ");
            p1 = p1.next;
        }
        System.out.println();
    }

    //随机数组产生器
    public static int[] generateArray(int size){
        int temp = size + 1; //便于产生随机的[0,size]之间的随机大小的数组
        int arr[] = new int[(int) (Math.random()*temp)];

        for(int i = 0;i<arr.length;i++){
            arr[i] = (int) (Math.random()*20)+1;
        }

        return arr;
    }

    public static void main(String[] args) {
        int[] array = generateArray(8);
        Node head = createNodeByArray(array);
        System.out.println("分区前的链表:");
        showNodeList(head);

        int num = 10;
        System.out.println("分区的中间值:" + num);

        System.out.println("法1:通过数组分区后的链表:");
        head = listPartiton(head,num);
        showNodeList(head);

        System.out.println("法2:通过拼接子链表得到分区后的链表:");
        head = listPartiton2(head,num);
        showNodeList(head);
    }


}
