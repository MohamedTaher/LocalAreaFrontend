package com.example.taher.localarea;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


/**
 * Created by taher on 3/20/2016.
 */
public class Connection extends AsyncTask<String, String, String> {
    private HashMap<String, String> mData = null;// post data
    private ConnectionPostListener connectionPostListener;
    private Fragment f = null;

    /**
     * constructor
     * This constructor takes the data to be sent to the service using a hashmap, sends the data through
     * doInBackground, and then calls the connectionPostListener methond "doSomething" implemented by
     * the user to process the result of the service call.
     */
    public Connection(HashMap<String, String> data, ConnectionPostListener connectionPostListener) {
        mData = data;
        this.connectionPostListener = connectionPostListener;
    }

    /**
     * background
     * This is where you call the service and send your parameters and gt response.
     */
    @Override
    protected String doInBackground(String... params) {
        byte[] result = null;
        String str = "";

        HttpClient client;
        client = new DefaultHttpClient();
        HttpPost post = new HttpPost(params[0]);// in this case, params[0] is URL
        try {
            // set up post data
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            Iterator<String> it = mData.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                nameValuePair.add(new BasicNameValuePair(key, mData.get(key)));
            }

            post.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
            HttpResponse response = client.execute(post);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
                result = EntityUtils.toByteArray(response.getEntity());
                str = new String(result, "UTF-8");
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
        }
        return str;
    }

    /**
     * on getting result
     * this will call the implemented function in the interface to process the returned data.
     */
    @Override
    protected void onPostExecute(String result) {
        // something...
        connectionPostListener.doSomething(result);
    }

    public void setF(Fragment f)
    {
        this.f = f;
    }

    public Fragment getF()
    {
        return f;
    }
}

