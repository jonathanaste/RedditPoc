package jonas.com.redditpoc.backend.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import jonas.com.redditpoc.interfaces.DataListener;
import jonas.com.redditpoc.backend.responses.TopResponse;

public class TopRequest extends BaseRequest<TopResponse> {

    private static final String BASE_URL = "https://www.reddit.com/top.json?";
    private static final String URL_LIMIT_PARAM = "limit";
    private static final String URL_AFTER_PARAM = "after";
    private static final String URL_COUNT_PARAM = "count";

    private String after;
    private Integer count;

    public TopRequest(DataListener<TopResponse> dataListener) {
        super(Request.Method.GET,
                BASE_URL,
                TopResponse.class,
                dataListener);
    }

    public TopRequest(DataListener<TopResponse> dataListener, String after, int count) {
        super(Request.Method.GET,
                BASE_URL,
                TopResponse.class,
                dataListener);

        this.after = after;
        this.count = count;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put(URL_LIMIT_PARAM, "10");
        if (after != null) {
            params.put(URL_AFTER_PARAM, after);
        }
        if (count != null) {
            params.put(URL_COUNT_PARAM, String.valueOf(count));
        }
        return params;
    }
}
