package com.cassey.house.base.interfacetest;

/**
 * 实现接口的例子
 * 接口引用变量指向实现类的实例
 * 通过接口引用变量，只能调用到接口里定义的以及其父亲接口定义的方法
 * @author chunyang.zhao
 *
 */

interface Base {
    String show();
}

interface Child extends Base{
    public String show_Child();
}

interface Base2{
    public String show_Child2();
}

class ChildChild implements Child,Base2{
	ChildChild(){};
	
    public String show() {
        return ("i am ChildChild");
    }
	
	@Override
	public String show_Child() {
        return ("i am show_Child");
	}

	@Override
	public String show_Child2() {
        return ("i am show_Child2");
	}
}

public class ExtendsTestForInterface {
    public static void main(String[] args) {
    	
    	Base base = new ChildChild();
        System.out.println(base.show());
        
        Child child = new ChildChild();
        System.out.println(child.show());
        System.out.println(child.show_Child());
        
        Base2 base2 = new ChildChild();
        System.out.println(base2.show_Child2());
        
        ChildChild childchild = new ChildChild();
        System.out.println(childchild.show());
        System.out.println(childchild.show_Child());
        System.out.println(childchild.show_Child2());
    }
}
