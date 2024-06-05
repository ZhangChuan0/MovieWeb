package zhang.jie.api.demos.web;

public class Util {
    private String id;
    private String name;
    private String title;

    public Util(String id, String name, String title, String web) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.web = web;
    }

    private String web;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", name:'" + name + '\'' +
                ", title:'" + title + '\'' +
                ", web:'" + web + '\'' +
                '}';
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
}
