package rc.rym.rclive.utils;

import android.os.AsyncTask;

import junit.framework.Assert;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rc.rym.rclive.App;

public class HttpRequest {

    public interface ResponseHandler {
        void onHttpResponse(String response);
    }

    private interface IHttpRequest {
        void getToken(String userId, HttpRequest.ResponseHandler handler);

        void getUser(String userId, HttpRequest.ResponseHandler handler);
    }

    private IHttpRequest httpRequest;

    public HttpRequest() {
        httpRequest = new HttpRequestDummy();
    }

    public void getToken(String userId, HttpRequest.ResponseHandler handler) {
        httpRequest.getToken(userId, handler);
    }

    public void getUser(String userId, HttpRequest.ResponseHandler handler) {
        httpRequest.getUser(userId, handler);
    }

    private static class HttpRequestDummy extends AsyncTask<String, Void, String> implements IHttpRequest {

        private HttpRequest.ResponseHandler handler;

        public void getToken(String userId, ResponseHandler handler) {
            this.handler = handler;
            String token = null;
            switch (userId) {
                case "User_A":
                    token = "/1042teZCgK7LmSrdJPyQISNp6+KPs4PEobRGeLUpGYiDIv4tnbyCqco4kuPNdMOVKH/aYbTqn+j1039XQoWtg==";
                    break;
                case "User_B":
                    token = "JkOw4QpPX9yi6vZTKgsvboSNp6+KPs4PEobRGeLUpGYiDIv4tnbyCrYaw9WeGBDbVKH/aYbTqn/7NZJDluCrIg==";
                    break;
                case "User_C":
                    token = "OWFm3j0O/Rp8goKqIzh4Aj7ehB7EizrQscjx0HJEIPuTvQqx4EdsmF0AIohCyPZyWbm4rzz+r9F+S9YAIzr2Qg==";
                    break;
                case "User_D":
                    token = "BrX+eJA2JYeCbppkXFXLSl6j7VuCnlLIyuk9hpP3vEILjzoAJkW9S2SZI/leuo+nDbbRqO7+vYD6x4s8setO5w==";
                    break;
                case "User_E":
                    token = "05qswqqxJvRTZdbMsd1JYISNp6+KPs4PEobRGeLUpGYiDIv4tnbyCr216BnTGo79VKH/aYbTqn/fsqx0x0/Buw==";
                    break;
                default:
                    Assert.fail(App.TAG + ": HttpRequest 使用本地假数据，只支持 User_A, User_B, User_C, User_D, User_E 五个用户！");
            }
            String json = "{'code':200,'userId':'" + userId + "','token':'" + token + "'}";
            this.execute(json);
        }

        public void getUser(String userId, ResponseHandler handler) {
            this.handler = handler;
            String nickname = null;
            String portrait = null;
            switch (userId) {
                case "User_A":
                    nickname = "AAA";
                    portrait = "http://7xs9j5.com1.z0.glb.clouddn.com/rcdemo/icon_1.jpg";
                    break;
                case "User_B":
                    nickname = "BBB";
                    portrait = "http://7xs9j5.com1.z0.glb.clouddn.com/rcdemo/icon_2.jpg";
                    break;
                case "User_C":
                    nickname = "CCC";
                    portrait = "http://7xs9j5.com1.z0.glb.clouddn.com/rcdemo/icon_3.jpg";
                    break;
                case "User_D":
                    nickname = "DDD";
                    portrait = "http://7xs9j5.com1.z0.glb.clouddn.com/rcdemo/icon_4.jpg";
                    break;
                case "User_E":
                    nickname = "EEE";
                    portrait = "http://7xs9j5.com1.z0.glb.clouddn.com/rcdemo/icon_5.jpg";
                    break;
                default:
                    Assert.fail(App.TAG + ": HttpRequest 使用本地假数据，只支持 User_A, User_B, User_C, User_D, User_E 五个用户！");
            }
            String json = "{'userId':'" + userId + "'," + "'nickname':'" + nickname + "','portrait':'" + portrait + "'}";
            this.execute(json);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            handler.onHttpResponse(response);
        }
    }

    private static class HttpRequestReal extends AsyncTask<String, Void, String> {

        private static final String SERVER_DOMAIN = "http://10.0.2.2:8585";
        private HttpRequest.ResponseHandler handler;

        public void getToken(String userId, HttpRequest.ResponseHandler handler) {
            this.handler = handler;
            String url = SERVER_DOMAIN + "/get_token/" + userId;
            this.execute(url, "GET");
        }

        public void getUser(String userId, HttpRequest.ResponseHandler handler) {
            this.handler = handler;
            String url = SERVER_DOMAIN + "/get_token/" + userId;
            this.execute(url, "GET");
        }

        @Override
        protected String doInBackground(String... params) {
            String response = null;
            try {
                URL uri = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
                conn.setRequestMethod(params[1]);
                InputStream inStream = conn.getInputStream();

                byte[] data = new byte[1024];
                int len;
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                while ((len = inStream.read(data)) != -1) {
                    outStream.write(data, 0, len);
                }
                inStream.close();
                response = new String(outStream.toByteArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            handler.onHttpResponse(response);
        }
    }
}
