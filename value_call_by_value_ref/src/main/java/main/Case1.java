package main;

/**
 * 在 swap() 方法中，a、b 的值进行交换，并不会影响到 A、B。
 * 因为，a、b 中的值，只是从 A、B 复制过来的。
 * 也就是说，a、b 相当于 A、B 的副本，副本的内容无论怎么修改，都不会影响到原件本身。
 *
 * @author GG
 * @date 2022/5/15
 */
public class Case1 {

    public static void main(String[] args) {
        int A = 2;
        int B = 3;
        swap(A, B);
        System.out.println(A);
        System.out.println(B);
    }

    public static void swap(int a, int b) {
        int tmp = a;
        a = b;
        b = tmp;
    }
}
