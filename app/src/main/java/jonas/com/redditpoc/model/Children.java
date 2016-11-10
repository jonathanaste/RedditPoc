package jonas.com.redditpoc.model;

import com.google.gson.annotations.SerializedName;

public class Children {

    String kind;
    @SerializedName("data")
    Post post;

    public String getKind() {
        return kind;
    }

    public Post getPost() {
        return post;
    }
}
