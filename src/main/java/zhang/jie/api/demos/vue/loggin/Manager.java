package zhang.jie.api.demos.vue.loggin;

import com.google.gson.Gson;

/**
 * Created by zhouhuanping$ on 2024/4/24$.
 */
public class Manager {
    private String name,position;
    private int index;
    private int id;
    private String phone;
    private String password;
    private String hashcode;
    private String role;
    private String portrait;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    private String createTime;
    private String gender;
    private String data_of_birth;
    private int salary_info;
    private int income;
    private String workTime;
    private String note;

    @Override
    public String toString() {
        return new Gson().toJson(this);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHash() {
        return hashcode;
    }

    public void setHash(String hash) {
        this.hashcode = hash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getData_of_birth() {
        return data_of_birth;
    }

    public void setData_of_birth(String data_of_birth) {
        this.data_of_birth = data_of_birth;
    }

    public int getSalary_info() {
        return salary_info;
    }

    public void setSalary_info(int salary_info) {
        this.salary_info = salary_info;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}