package zhang.jie.api.domain.member;

import com.google.gson.Gson;

/**
 * Created by zhouhuanping$ on 2024/5/1$.
 */
public class Member {
    private int id;
    private String name;
    private String gender;
    private String balance;
    private String phone;
    private String viewing_history;
    private String password;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getViewing_history() {
        return viewing_history;
    }

    public void setViewing_history(String viewing_history) {
        this.viewing_history = viewing_history;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
