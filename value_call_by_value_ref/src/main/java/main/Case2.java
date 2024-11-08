package main;

/**
 * @author GG
 * @date 2022/5/15
 */
public class Case2 {
    public static void main(String[] args) {
        Integer A = 2;
        Integer B = 3;
        swap(A, B);
        System.out.println(A);
        System.out.println(B);
    }

    public static void swap(Integer a, Integer b) {
        Integer tmp = a;
        a = b;
        b = tmp;
    }
}
