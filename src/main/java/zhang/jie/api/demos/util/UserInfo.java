package zhang.jie.api.demos.util;

public class UserInfo {

    private String portrait;
    private String name;

    public UserInfo() {
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{" + "\"portrait\":\"" + portrait + "\"" +
                ", \"name\":\"" + name + '\"' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }
}