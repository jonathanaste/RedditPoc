package jonas.com.redditpoc.backend.requests;

import com.android.volley.Request;

import jonas.com.redditpoc.interfaces.DataListener;
import jonas.com.redditpoc.backend.responses.TopResponse;

public class TopRequest extends BaseRequest<TopResponse> {

    private static String BASE_URL = "https://www.reddit.com/top.json?";
    private static String URL_LIMIT_PARAM = "limit=10";

    public TopRequest(DataListener<TopResponse> dataListener) {
        super(Request.Method.GET, BASE_URL + URL_LIMIT_PARAM, TopResponse.class, dataListener);
    }

}
