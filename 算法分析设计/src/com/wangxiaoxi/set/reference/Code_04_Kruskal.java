package com.wangxiaoxi.set.reference;

import org.junit.Test;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

//undirected graph only
public class Code_04_Kruskal {

	// Union-Find Set
	public static class UnionFind {
		private HashMap<Node, Node> fatherMap;
		private HashMap<Node, Integer> rankMap;

		public UnionFind() {
			fatherMap = new HashMap<Node, Node>();
			rankMap = new HashMap<Node, Integer>();
		}

		//找某个点在集合中的代表顶点
		private Node findFather(Node n) {
			Node father = fatherMap.get(n);
			if (father != n) {
				father = findFather(father);
			}
			fatherMap.put(n, father);
			return father;
		}

		public void makeSets(Collection<Node> nodes) {
			fatherMap.clear();
			rankMap.clear();
			for (Node node : nodes) {
				fatherMap.put(node, node);
				rankMap.put(node, 1);
			}
		}

		public boolean isSameSet(Node a, Node b) {
			return findFather(a) == findFather(b);
		}

		public void union(Node a, Node b) {
			if (a == null || b == null) {
				return;
			}
			Node aFather = findFather(a);
			Node bFather = findFather(b);
			if (aFather != bFather) {
				int aFrank = rankMap.get(aFather);
				int bFrank = rankMap.get(bFather);
				if (aFrank <= bFrank) {
					fatherMap.put(aFather, bFather);
					rankMap.put(bFather, aFrank + bFrank);
				} else {
					fatherMap.put(bFather, aFather);
					rankMap.put(aFather, aFrank + bFrank);
				}
			}
		}
	}

	public static class EdgeComparator implements Comparator<Edge> {

		@Override
		public int compare(Edge o1, Edge o2) {
			return o1.weight - o2.weight;
		}

	}

	public static Set<Edge> kruskalMST(Graph graph) {
		UnionFind unionFind = new UnionFind();

		//传入图中的各点，使各点独自形成集合（fatherMap的key，value均为自己，而rankMap中的value为某节点的子节点个数）
		unionFind.makeSets(graph.nodes.values());

		//edge和node是相互独立的，edge从小到大排序，压入队列中
		PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
		for (Edge edge : graph.edges) {
			priorityQueue.add(edge);
		}

		//弹出队列头元素，对各自独立的node进行合并，用贪心的策略合并节点，在result中记录使用了那一条edge
		Set<Edge> result = new HashSet<>();
		while (!priorityQueue.isEmpty()) {
			Edge edge = priorityQueue.poll();
			if (!unionFind.isSameSet(edge.from, edge.to)) {
				result.add(edge);
				unionFind.union(edge.from, edge.to);
			}
		}
		return result;
	}

	@Test
	public void kruskalMSTTest(){

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
		Graph graph = GraphGenerator.createGraph(matrix);
		Set<Edge> set = kruskalMST(graph);
		for(Edge edge : set){
			System.out.println("from " + edge.from + " to " + edge.to + ", weight = "+edge.weight);
		}
	}
}
