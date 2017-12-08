package com.cassey.house.base;

/**
 * 继承的例子 super关键字，super是指向直接父类
 * 基类对象指向派生类对象时，this指向派生类对象
 * @author chunyang.zhao
 *
 */

class Base {
	Base(){};
	
    public String show() {
        return ("i am Base");
    }
}

class Child extends Base{
	Child(){};
	
    public String show() {
        return ("i am Child");
    }
    
    public String show_this1() {
        return this.show();
    } 
    
    public String show_super1() {
        return super.show();
    } 
}

class ChildChild extends Child{
	ChildChild(){};
    public String show() {
        return ("i am ChildChild");
    } 
    
    public String show_this2() {
        return this.show();
    } 
    
    public String show_super2() {
        return super.show();
    } 
}

public class ExtendsTest {
    public static void main(String[] args) {
    	ChildChild childChild = new ChildChild();
        
        System.out.println("1--" + childChild.show());
        System.out.println("2--" + childChild.show_super2());
        System.out.println("3--" + childChild.show_this2());
        System.out.println("4--" + childChild.show_super1());
        System.out.println("5--" + childChild.show_this1());
        
/*      1--i am ChildChild
        2--i am Child
        3--i am ChildChild
        4--i am Base
        5--i am ChildChild*/
        
        Child child = new ChildChild();
        
        System.out.println("6--" + child.show());
        System.out.println("7--" + child.show_super1());
        System.out.println("8--" + childChild.show_this1());
        System.out.println("9--" + childChild.show_super2());
        System.out.println("10--" + childChild.show_this2());
        
/*      1--i am ChildChild
        2--i am Base
        3--i am ChildChild
        9--i am Child
        10--i am ChildChild*/
    }
}
