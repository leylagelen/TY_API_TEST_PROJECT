package model.response;

import com.google.gson.annotations.SerializedName;

public class BookResponse extends BaseResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("author")
    private String author;

    public BookResponse(String error, int id, String title, String author) {
        super(error);
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
