package zhang.jie.api.domain;

import com.google.gson.Gson;

/**
 * Created by zhouhuanping$ on 2024/5/5$.
 */
public class Seat {
    private int id;
    private int movie_id;
    private String row_column;
    private String status;

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

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }


    public String getRow_column() {
        return row_column;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
