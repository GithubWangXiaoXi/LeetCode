package com.wangxiaoxi.queue;

import java.util.LinkedList;
import java.util.Queue;

public class code0113_dogCatQueue {

    /**
     *    猫狗问题：猫狗进宠物队列（猫队列和狗队列），有获取第一只进来的猫或狗的功能
     *  还有获取第一只进来的宠物（可以是猫，也可以是狗，但只存在一只），不修改Cat，Dog类
     */

    //pet是个总称，可以获取子类的动物的类型
    static class Pet{
        private String petType;
        private String petName;

        public Pet(String type, String name){
            petType = type;
            petName = name;
        }

        public String getPetType(){
            return petType;
        }

        public String getPetName() {
            return petName;
        }
    }

    static class Cat extends Pet{

        private String catName;

        public Cat(String name){
            //Java子类的构造函数中必须调用super()，父类的构造器
            super("cat",name);
        }
    }

    static class Dog extends Pet{

        private String dogName;

        public Dog(String name){
            //Java子类的构造函数中必须调用super()，父类的构造器
            super("dog",name);
        }
    }

    //petEnter类可以获取进来的宠物类型和时间点
    static class PetEnter{

        private String petType;
        private int enterTime;
        private String name;

        public PetEnter(Pet pet, int count){
            petType = pet.getPetType();
            enterTime = count;
            name = pet.getPetName();
        }

        public int getEnterTime() {
            return enterTime;
        }

        public String getPetType() {
            return petType;
        }

        public String getName() {
            return name;
        }
    }

    static class DogCatQueue{
        private Queue<PetEnter> dogQueue;
        private Queue<PetEnter> catQueue;
        private int enterTime;

        public DogCatQueue(){
            dogQueue = new LinkedList<>();
            catQueue = new LinkedList<>();
            enterTime = 0;
        }

        //给宠物贴上计数器标签，并将其放入对应的宠物队列
        public void push(Pet pet){
            PetEnter petEnter = new PetEnter(pet,enterTime);

            if("dog".equals(petEnter.getPetType())){
                dogQueue.add(petEnter);
            }
            else if("cat".equals(petEnter.getPetType())){
                catQueue.add(petEnter);
            }
            else{
                System.out.println("There is no such petType");
            }

            enterTime++;
        }

        public String popCat(){
            if(catQueue.isEmpty()){
                throw new ArrayIndexOutOfBoundsException("queue is empty");
            }
            String cat = catQueue.poll().getName();
            return cat;
        }

        public String popDog(){
            if(catQueue.isEmpty()){
                throw new ArrayIndexOutOfBoundsException("queue is empty");
            }
            String dog = dogQueue.poll().getName();
            return dog;
        }

        //popAll需要考虑多种情况！！！：catQueue，dogQueue分别是否为空等多种情况
        public String popAll(){
            //如果猫狗队列都不为空
            if(!catQueue.isEmpty() && !dogQueue.isEmpty()){
                if(catQueue.peek().getEnterTime() < dogQueue.peek().getEnterTime()){
                    return catQueue.poll().getName();
                }else{
                    return dogQueue.poll().getName();
                }
            }
            //如果猫队列非空
            else if(!catQueue.isEmpty()){
                return catQueue.poll().getName();
            }
            //如果狗队列非空
            else if(!dogQueue.isEmpty()){
                return dogQueue.poll().getName();
            }
            else{
                throw new RuntimeException("err, queue is empty!");
            }
        }
    }

    public static Dog[] dogGenerate(int number){
        Dog[] dogs = new Dog[number];

        for (int i = 0;i < number; i++){
            String dogName = "dog"+i+5;
            dogs[i] = new Dog(dogName);
        }
        return dogs;
    }

    public static Cat[] catGenerate(int number){
        Cat[] cats = new Cat[number];

        for (int i = 0;i < number; i++){
            String dogName = "cat"+i+5;
            cats[i] = new Cat(dogName);
        }
        return cats;
    }

    public static void main(String[] args) {
        Dog[] dogs = dogGenerate(5);
        Cat[] cats = catGenerate(5);
        DogCatQueue dogCatQueue = new DogCatQueue();

        for(int i = 0;i<5;i++){
            if(i%2 == 0){
                dogCatQueue.push(dogs[i]);
            }else{
                dogCatQueue.push(cats[i]);
            }
        }

        System.out.print("第一只动物是:"+dogCatQueue.popAll());
    }
}
