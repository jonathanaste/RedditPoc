package jonas.com.redditpoc.model;

import android.text.format.DateUtils;

public class Post {

    String author;
    String thumbnail;
    String title;
    int num_comments;
    long created_utc;

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

    public long getCreated_utc() {
        return created_utc;
    }

    public long getEntryDateInMillis(){
        return created_utc * 1000;
    }

    public CharSequence getFormattedDate(){
        return DateUtils.getRelativeTimeSpanString(getEntryDateInMillis(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }

}
