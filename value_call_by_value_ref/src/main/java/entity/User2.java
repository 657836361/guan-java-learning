package entity;

/**
 * @author GG
 * @date 2022/5/15
 */
public class User2 {
    String name;
    int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "name = " + name + " --- age = " + age;
    }
}
