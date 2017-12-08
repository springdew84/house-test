package com.cassey.house.base.abtracttest;

/**
 * Child类没有申明show2()
 * 但可以通过Child类实例调用show2()
 * 因为Child从Base处继承了show2()
 * 
 * 如果ChildChild重载了show(),
 * 那调用show()就会调用ChildChild的
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
    
    public abstract String show4();
}

class ChildChild extends Child{
	ChildChild(){};
	
/*    public String show() {
        return ("i am ChildChild");
    } */
    
    public String show3() {
    	super.show();
        return ("i am ChildChild333");
    } 
    
    public String show_this() {
        return this.show();
    } 
    
    public String show_super() {
        return super.show();
    }
    
	@Override
	public String show4() {
		return ("i am show444");
	}
	
	@Override
	public String show2() {
		return ("i am show222");
	}
}

public class ExtendsTestForAbstract {
    public static void main(String[] args) {
    	
    	Base base = new ChildChild();
    	System.out.println(base.show());//i am Child
        System.out.println(base.show2());//i am show222
        
    	Child child = new ChildChild();
    	System.out.println(child.show());//i am Child
        System.out.println(child.show2());//i am show222
        System.out.println(child.show4());//i am show444
        
        ChildChild childChild = new ChildChild();
        System.out.println(childChild.show());//i am Child
        System.out.println(childChild.show2());//i am show222
        System.out.println(childChild.show3());//i am ChildChild333
        System.out.println(childChild.show4());//i am show444
    }
}
