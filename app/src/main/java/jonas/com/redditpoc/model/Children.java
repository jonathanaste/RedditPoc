package jonas.com.redditpoc.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Children implements Serializable {

    private String kind;
    @SerializedName("data")
    private Post post;

    public String getKind() {
        return kind;
    }

    public Post getPost() {
        return post;
    }
}
