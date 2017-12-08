package com.cassey.house.base;

/**
 * 多态经典的例子
 * @author chunyang.zhao
 *
当超类对象引用变量引用子类对象时，被引用对象的类型而不是引用变量的类型决定了调用谁的成员方法，
但是这个被调用的方法必须是在超类中定义过的，也就是说被子类覆盖的方法，
但是它仍然要根据继承链中方法调用的优先级来确认方法，
该优先级为：this.show(O)->super.show(O)->this.show((super)O)->super.show((super)O)
 */

class A {
    public String show(D obj) {
        return ("A and D");
    }

    public String show(A obj) {
        return ("A and A");
    } 
    
/*    public String show(B obj) {
        return ("A and A111111111");
    } */

    /*
    public String show(C obj) {
        return ("A and C");
    } 
    */
}

class B extends A{
    public String show(B obj){
        return ("B and B");
    }
    
    public String show(A obj){
        return ("B and A");
    } 
}

class C extends B{

}

class D extends B{

}

public class PolymorphismTest {
    public static void main(String[] args) {
        A a1 = new A();
        A a2 = new B();
        A a3 = new C();
        A a4 = new D();
        
        B b = new B();
        C c = new C();
        D d = new D();
        
        //前三个比较容易理解
        System.out.println("1--" + a1.show(b));//1--A and A
        System.out.println("2--" + a1.show(c));//2--A and A
        System.out.println("3--" + a1.show(d));//3--A and D
        
        //先找到A.show(B),但是超类中没有定义A.show(B)，
        //这时找到了B.show(A),作了转型B.show((B)A)
        System.out.println("4--" + a2.show(b));//4--B and A
        //同上
        System.out.println("5--" + a2.show(c));//5--B and A
        //先找B.show(D),没有，再去超类中找A.show(D)，ok找到了
        System.out.println("6--" + a2.show(d));//6--A and D
        
        //注意与上边三个的差别，上边是超类对象变量引用子类对象时
        //这里是子类对象引用变量引用子类对象，当然先找到B.show(B)
        System.out.println("7--" + b.show(b));//7--B and B
        //先找B.show(c)没有，再找A.show(C),也没有，然后B.show((C)B)
        System.out.println("8--" + b.show(c));//8--B and B
        //先找B.show(D)没有，再找A.show(D)有了
        System.out.println("9--" + b.show(d));//9--A and D
        
        //先找C.show(b)，没有，再找B.show(b)有，但是没有A.show(b)
        //则找到了B.show(a)
        //如果定义了A.show(b)，则会调用B.show(b)
        System.out.println("10--" + a3.show(b));//B and A
        //同上
        System.out.println("11--" + a3.show(c));//B and A
        //先找C.show(d),再找B.show(d),再找A.show(d)
        System.out.println("12--" + a3.show(d));//A and D
        
        //同以上三个
        System.out.println("13--" + a4.show(b));//B and A
        System.out.println("14--" + a4.show(c));//B and A
        System.out.println("15--" + a4.show(d));//A and D
    }
}