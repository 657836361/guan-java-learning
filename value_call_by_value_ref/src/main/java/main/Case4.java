package main;


import entity.User1;

/**
 * @author GG
 * @date 2022/5/15
 */
public class Case4 {
    public static void main(String[] args) {
        User1 A = new User1("ali");
        User1 B = new User1("bd");
        System.out.println("交换前name：" + A + "-->" + B);
        swap(A, B);
        System.out.println("交换后name：" + A + "-->" + B);
    }

    private static void swap(User1 a, User1 b) {
        User1 tmp = a;
        a = b;
        b = tmp;
    }
}


