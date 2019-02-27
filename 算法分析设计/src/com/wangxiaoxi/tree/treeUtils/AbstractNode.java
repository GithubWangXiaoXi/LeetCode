package com.wangxiaoxi.tree.treeUtils;

public abstract class AbstractNode {

    //抽象类可以有构造方法，只是不能直接创建抽象类的实例对象而已

    public int value;
    public AbstractNode left;
    public AbstractNode right;
    public AbstractNode parent;

    public AbstractNode(int value){
        this.value = value;
    }
}
