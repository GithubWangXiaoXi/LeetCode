package com.wangxiaoxi.tree;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

public class code0009_trieTree {

    /** 前缀树：根据字符串的字符顺序，依次创建节点。输入多个前缀相同的单词，则前缀的节点需走过多次 */

    /**
     * 主要功能：
     * 1，查找某单词出现的次数
     * 2，查找某个前缀的单词出现几次
     * 3，通过单词创建节点
     * 4，删除单词
     */

    //前缀树节点:
    //节点成员包括: 某节点经过的次数path，字符串走到结尾的节点end
    //节点不存储字符信息，字符信息用节点中的成员数组或者hashmap来存储（如果字符串是26个小写字母，则数组的长度为26）
    class TrieTreeNode{

        int path;
        int end;
        HashMap<TrieTreeNode,HashMap<Character,TrieTreeNode>> map;  //一个节点对应多个字符，则key为当前node节点，value为map（key:letter, value:subNode）

        public TrieTreeNode(){
            map = new HashMap<>();
        }

        public void setMap(HashMap<TrieTreeNode, HashMap<Character, TrieTreeNode>> map) {
            this.map = map;
        }
    }

    //前缀树
    class TrieTree{

        private TrieTreeNode head = new TrieTreeNode();  //创建前缀树的头结点
        private TrieTreeNode node;

        public TrieTree(){

        }

        public void insert(String word){

            char charArray[] = word.toCharArray();  //得到字符串数组

            node = head;
            HashMap<Character,TrieTreeNode> subNodeMap;

            if(head.map.get(head) == null){
                HashMap<TrieTreeNode,HashMap<Character,TrieTreeNode>> map = new HashMap<>();
                map.put(head,new HashMap<>());
                head.setMap(map);
            }

            for(char letter : charArray){

                subNodeMap = node.map.get(node);

                if(subNodeMap == null){   //如果node节点的map成员中的value（hashMap）为空，则初始化
                    subNodeMap = new HashMap<>();
                    HashMap<TrieTreeNode,HashMap<Character,TrieTreeNode>> hashMap = new HashMap<>();
                    hashMap.put(node,subNodeMap);
                    node.map = hashMap;
                }

                if(subNodeMap.get(letter) == null){   //从头结点开始，如果字符非空，依次创建节点

                    TrieTreeNode subNode = new TrieTreeNode();  //创建下一个节点
                    subNodeMap.put(letter,subNode);   //在subMap中添加sub节点，并置value为sub节点，这样parent就可以取到sub节点了。
                }

                node = subNodeMap.get(letter);
                node.path++;
            }
            node.end++;
        }

        //如果某个节点的path自减然后为0,则说明该前缀没有走过，则将后面的所有节点删除
        public void delete(String word){

            //先判断该word是否存在
            if(findPrefixCount(word) != 0){

                char charArray[] = word.toCharArray();
                TrieTreeNode node = head;

                HashMap<Character,TrieTreeNode> subMap;

                for(char letter : charArray){

                    subMap = node.map.get(node);

                    if(subMap.get(letter) != null){
                        subMap.get(letter).path--;

                        if(subMap.get(letter).path <= 0){
                            subMap.put(letter,null);
                            return;
                        }

                        node = subMap.get(letter);
                    }
                }
                node.end--;
            }
        }

        //统计某单词在前缀树中出现的个数
        public int findWordCount(String word){

            char charArray[] = word.toCharArray();  //得到字符串数组

            TrieTreeNode node = head;
            HashMap<Character,TrieTreeNode> subMap;

            for(char letter : charArray){

                subMap = node.map.get(node);

                if(subMap.get(letter) == null){
                    return 0;
                }

                node = subMap.get(letter);
            }

            return node.end;
        }

        //统计某前缀在前缀树中出现的次数
        public int findPrefixCount(String prefix){

            char charArray[] = prefix.toCharArray();  //得到字符串数组

            TrieTreeNode node = head;
            HashMap<Character,TrieTreeNode> subMap;

            for(char letter : charArray){

                subMap = node.map.get(node);

                if(subMap.get(letter) == null){
                    return 0;
                }

                node = subMap.get(letter);
            }

            return node.path;
        }
    }

    @Test
    public void trieTreeTest(){

        TrieTree prefixTree = new TrieTree();

        prefixTree.insert("hello");
        prefixTree.insert("hellow");
        prefixTree.insert("hello");

        int wordCount = prefixTree.findWordCount("hello");
        int prefixCount = prefixTree.findPrefixCount("hell");
        int wordCount2 = prefixTree.findWordCount("hellow");

        System.out.println("\"hello\"单词出现的次数:" + wordCount);
        System.out.println("\"hell\"前缀出现的次数:" + prefixCount);
        System.out.println("\"hellow\"前缀出现的次数:" + wordCount2);

        System.out.println("删除掉\"hellow\"");
        prefixTree.delete("hellow");
        int wordCount3 = prefixTree.findWordCount("hellow");
        System.out.println("\"hellow\"前缀出现的次数:" + wordCount3);
    }
}


