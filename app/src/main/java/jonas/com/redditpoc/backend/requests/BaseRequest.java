package jonas.com.redditpoc.backend.requests;

import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import jonas.com.redditpoc.interfaces.DataListener;

public abstract class BaseRequest<T> extends Request<T> {

    private final DataListener<T> dataListener;
    private final Gson gson = new Gson();
    private final Class<T> clazz;

    BaseRequest(int method, String url, Class<T> clazz, DataListener<T> dataListener) {
        super(method, url, null);
        this.clazz = clazz;
        this.dataListener = dataListener;
    }

    @Override
    protected void deliverResponse(T response) {
        dataListener.onDataUpdate(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        dataListener.onError(error.getCause().getMessage());
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        Log.d("Request-Url", getUrl());
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.d("Request-Response", json);
            return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override
    public String getUrl() {
        try {
            Uri.Builder builder = Uri.parse(super.getUrl()).buildUpon();
            for (Map.Entry<String, String> entry : getParams().entrySet()) {
                builder.appendQueryParameter(entry.getKey(), entry.getValue());
            }
            return builder.build().toString();
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        return super.getUrl();

    }
}