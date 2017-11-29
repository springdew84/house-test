package com.cassey.house.base.abtracttest;

/**
 * Child类没有申明show2(),但可以通过Child类实例调用show2()
 * @author chunyang.zhao
 *
 */

abstract class Base {
	Base(){};
	
    public String show() {
        return ("i am Base");
    }
    public abstract String show2();
}

abstract class Child extends Base{
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
    
	@Override
	public String show2() {
		return ("i am show2222");
	}
}

public class ExtendsTestForAbstract {
    public static void main(String[] args) {
    	Child child = new ChildChild();
        System.out.println(child.show2());
    }
}
