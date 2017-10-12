package com.cassey.house.base;

/**
 * 继承的例子 super关键字
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
}

class ChildChild extends Child{
	ChildChild(){};
    public String show() {
        return ("i am ChildChild");
    } 
    
    public String show_this() {
        return this.show();
    } 
    
    public String show_super() {
        return super.show();
    } 
}

public class ExtendsTest {
    public static void main(String[] args) {
    	ChildChild childChild = new ChildChild();
        
        System.out.println("1--" + childChild.show());//1--i am ChildChild
        System.out.println("2--" + childChild.show_super());//2--i am Child
        System.out.println("3--" + childChild.show_this());//3--i am ChildChild
    }
}
