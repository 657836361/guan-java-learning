package main;


import entity.User2;

/**
 * @author GG
 * @date 2022/5/15
 */
public class Case5 {
    public static void main(String[] args) {
        User2 user = new User2(); //new Class 引用类型，调用 pass 方法后 name 与 age 的值改变了
        user.setName("main"); // 调用 pass 后，name 为 pass 了
        user.setAge(2); //调用 pass 后，age 为 4 了
        pass(user); //pass 方法调用
        System.out.println("main 方法 user 是：" + user);
    }

    public static void pass(User2 user) {
        user = new User2();
        user.setName("pass");
        user.setAge(4);
        System.out.println("pass 方法 user 是：" + user);
    }
}

