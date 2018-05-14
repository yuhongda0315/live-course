package rc.rym.rongsdk_android;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhanglei on 23/03/2018.
 */

public class HttpRequest extends AsyncTask<String, Void, String> {

    private TokenHandler handler;

    public interface TokenHandler {
        void onTokenHandler(String token);
    }

    public void getToken(String userId, TokenHandler handler) {
        this.handler = handler;
        this.execute(userId);
    }

    @Override
    protected String doInBackground(String... userId) {
        String token = null;
        try {
            URL uri = new URL("http://10.0.2.2:8585/get_token/" + userId[0]);
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setRequestMethod("GET");

            int statusCode = conn.getResponseCode();
            InputStream inStream = conn.getInputStream();

            byte[] data = new byte[1024];
            int len;
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            String json = new String(outStream.toByteArray());
            JSONObject obj = new JSONObject(json);
            token = obj.getString("token");
            Log.d("RYM_DG", "statusCode = " + statusCode + "token = " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    @Override
    protected void onPostExecute(String token) {
        super.onPostExecute(token);
        handler.onTokenHandler(token);
    }
}