package zhang.jie.api.domain.mapper.courses;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseTheme {
    private String name;
    private String code;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseTheme)) return false;
        CourseTheme that = (CourseTheme) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getCode(), that.getCode()) && Objects.equals(getRecords(), that.getRecords());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCode(), getRecords());
    }

    private final List<CourseTheme> records = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public List<CourseTheme> getRecords() {
        return records;
    }

    public void setRecords(CourseTheme courseTheme) {

        this.records.add(courseTheme);
    }


}
