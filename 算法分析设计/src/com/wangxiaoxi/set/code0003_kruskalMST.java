package com.wangxiaoxi.set;
import java.util.*;

public class code0003_kruskalMST {

    /**使用并查集实现kruskal的最小生成树问题，kruskal的最小生成树用的是贪心的思想，每次选择最短的路径连起来，并且保证不出现环*/

    /**
     * 实现思路：
     * 1，graph的实现：
     *    graph由node集合和edge集合构成
     *    node的属性：值（value）
     *    edge的属性：入点（from），出点（to），边的权值（weight）
     *
     * 2，graph的生成器：通过矩阵（每一行由weight,from,to的edge信息组成）
     *
     * 3，并查集在kruskal算法中的应用：
     *    1），先用graph中的nodes初始化节点，每个节点为独立的集合
     *    2），查：通过找到两个node的father，来判断两个node是否在同一个集合
     *    3），并：如果两个node不在同一个集合中，则将其合并。
     *    4），优化路径
     *    注意graph中的edge和node是相互独立的，edge从小到大依次加入到队列中，每次弹出最小的，
     *在判断弹出的edge的from，to节点不在同一个集合中时，将from，to合并，并加入edge到最终result中
     */

    class Node{

        public int value;

        public Node(int value){
            this.value = value;
        }

        public String toString(){
            return "" + value;
        }
    }

    class Edge{
        public int weight;
        public Node from;
        public Node to;

        public Edge(Node from,Node to,int weight){
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    class Graph{

        public HashMap<Integer,Node> nodes;
        public HashSet<Edge> edges;

        public Graph(){
            nodes =  new HashMap<>();
            edges = new HashSet<>();
        }
    }

    //结合图的并查集结构
    class UnionFindSet{

        HashMap<Node,Node> fatherMap;  //记录并查集中节点和父节点的关系
        HashMap<Node,Integer> sizeMap;  //主要记录代表节点的子节点个数

        public UnionFindSet(){
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
        }

        //将各个节点处理成当个的集合
        public void makeSets(Set<Node> nodes){

            for (Node node : nodes){
                fatherMap.put(node,node);
                sizeMap.put(node,1);
            }
        }

        //查找节点的代表节点
        public Node findFather(Node node){

            Node father = fatherMap.get(node);
            if(father != node){
                father = findFather(father);
            }

            fatherMap.put(node,father);  //路径压缩

            return father;
        }

        //判断两个节点是否在同一个集合中
        public boolean isSameSet(Node node1,Node node2){
            return findFather(node1) == findFather(node2);
        }

        //通过两个节点合并两个集合
        public void union(Node node1,Node node2){
            if(!isSameSet(node1,node2)){
                Node node1_father = findFather(node1);
                Node node2_father = findFather(node2);

                int father1_size = sizeMap.get(node1_father);
                int father2_size = sizeMap.get(node2_father);

                if(father1_size >= father2_size){
                    fatherMap.put(node2_father,node1_father);
                    sizeMap.put(node1_father,father1_size + father2_size);
                }else{
                    fatherMap.put(node1_father,node2_father);
                    sizeMap.put(node2_father,father1_size + father2_size);
                }
            }
        }
    }

    public Set kruskalMST(Graph graph){

        UnionFindSet unionFindSet = new UnionFindSet();

        Set<Integer> indices = graph.nodes.keySet();
        Set<Node> nodes = new HashSet<>();

        for(Integer index : indices){
            nodes.add(graph.nodes.get(index));
        }

        //初始化各节点
        unionFindSet.makeSets(nodes);

        //对graph中的edges排序,并加入queue中(优先队列才有比较器，形成堆结构，linkedList没有)
        Queue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        });

        for(Edge edge : graph.edges){
            queue.offer(edge);
        }

        Set<Edge> result = new HashSet<>();
        while(!queue.isEmpty()){

            Edge edge = queue.poll();

            Node from = edge.from;
            Node to = edge.to;

            if(!unionFindSet.isSameSet(from,to)){
                unionFindSet.union(from,to);
                result.add(edge);
            }
        }
        return result;
    }

    public Graph createGraph(Integer[][] matrix) {

        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            Integer weight = matrix[i][0];
            Integer from = matrix[i][1];
            Integer to = matrix[i][2];

            System.out.println("from " + from + " to " + to + ", weight = " + weight);

            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            Edge newEdge = new Edge(fromNode, toNode, weight);
            graph.edges.add(newEdge);
        }
        return graph;
    }



    public static void main(String[] args){

        Integer matrix[][] = {{1,1,3},
                {6,1,2},
                {5,1,4},
                {5,2,3},
                {5,4,3},
                {6,3,5},
                {4,3,6},
                {2,4,6},
                {3,2,5},
                {6,5,6}};

        System.out.println("打印图的路径:");
        Graph graph = new code0003_kruskalMST().createGraph(matrix);

        System.out.println("*************************************");
        System.out.println("打印最小生成树路径:");
        Set<Edge> set = new code0003_kruskalMST().kruskalMST(graph);
        for(Edge edge : set){
            System.out.println("from " + edge.from + " to " + edge.to + ", weight = "+edge.weight);
        }
    }
}