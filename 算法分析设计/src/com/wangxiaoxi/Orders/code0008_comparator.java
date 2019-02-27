package com.wangxiaoxi.Orders;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class code0008_comparator {

    static class Person{
        private String name;
        private int id;
        private int age;

        public Person(String name, int id, int age) {
            this.name = name;
            this.id = id;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    ", age=" + age +
                    '}';
        }
    }

    public static class idAscendComparator implements Comparator<Person>{

        @Override
        public int compare(Person o1, Person o2) {
            return o1.getId() - o2.getId();
        }
    }

    public static class nameAscendComparator implements Comparator<Person>{

        @Override
        public int compare(Person o1, Person o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    public static class ageAscendComparator implements Comparator<Person>{

        @Override
        public int compare(Person o1, Person o2) {
            return o1.getAge() - o2.getAge();
        }
    }

    public static void main(String[] args) {
        Person person1 = new Person("Amy",1,12);
        Person person2 = new Person("Tom",3,12);
        Person person3 = new Person("Mike",2,10);

        //将对象放入数组中，待会使用Arrays.sort排序
        Person[] personList = new Person[3];
        personList[0] = person1;
        personList[1] = person2;
        personList[2] = person3;

        //自定义类型的排序使用归并排序
        Arrays.sort(personList,new idAscendComparator());
        System.out.print("idAscend");
        for(Person person : personList){
            System.out.println(person);
        }

        Arrays.sort(personList,new ageAscendComparator());
        System.out.print("nameAscend");
        for(Person person : personList){
            System.out.println(person);
        }
    }

    @Test
    public void test(){
        Person person1 = new Person("Amy",1,12);
        Person person2 = new Person("Tom",3,12);
        Person person3 = new Person("Mike",2,10);

//        Person[] personList1 = new Person[3];
//        personList1[0] = person1;
//        personList1[1] = person2;
//        personList1[2] = person3;

        //建立一个堆(应用名称为优先队列),需要将多个对象放入堆中
        PriorityQueue<Person> priorityQueue = new PriorityQueue(new ageAscendComparator());
        priorityQueue.add(person1);
        priorityQueue.add(person2);
        priorityQueue.add(person3);

        while (!priorityQueue.isEmpty()){
             Person person = priorityQueue.poll();
             System.out.println(person);
        }
    }
}
