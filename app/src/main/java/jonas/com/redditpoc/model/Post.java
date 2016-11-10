package jonas.com.redditpoc.model;

public class Post {

    String author;
    String thumbnail;
    String title;
    int num_comments;
    int created_utc;

    public String getAuthor() {
        return author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public int getCreated_utc() {
        return created_utc;
    }

}
