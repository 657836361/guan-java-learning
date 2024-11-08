package entity;

/**
 * @author GG
 * @date 2022/5/15
 */
public class User1 {
    private String name;

    public User1(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
