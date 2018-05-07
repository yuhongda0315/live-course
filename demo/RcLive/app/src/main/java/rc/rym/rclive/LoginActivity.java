package rc.rym.rclive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import org.json.JSONObject;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import rc.rym.rclive.utils.AppUtil;
import rc.rym.rclive.utils.HttpRequest;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                // 异步返回时，操作 UI 前先要线程同步，并且确保使用的对象没有被释放。
                Log.d(App.TAG, "onChanged = " + connectionStatus);
            }
        });
        autoLogin();
    }

    private void autoLogin() {
        EditText userId = findViewById(R.id.edit_user_id);
        userId.setText(AppUtil.getCachedUserId());
        String token = AppUtil.getCachedToken();
        if (!token.isEmpty()) {
            connectRcServer(token);
        }
    }

    public void onLogin(View view) {
        String userId = ((EditText) findViewById(R.id.edit_user_id)).getText().toString();
        if (userId.isEmpty()) {
            Toast.makeText(getApplicationContext(), "UserID can not be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        new HttpRequest().getToken(userId, new HttpRequest.ResponseHandler() {
            @Override
            public void onHttpResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String token = obj.getString("token");
                    connectRcServer(token);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Http response parse error: " + response, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    public void onExit(View view) {
        finish();
    }

    private void connectRcServer(final String token) {
        Log.d(App.TAG, "connectRcServer token = " + token);
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e(App.TAG, "connectRcServer onTokenIncorrect.");
                Toast.makeText(getApplicationContext(), "Get token incorrect.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String userId) {
                Log.d(App.TAG, "connectRcServer onSuccess. userId = " + userId);
                AppUtil.setCurrentUser(userId, "XXX", "");
                AppUtil.saveUserInfoCached(userId, token);
                Intent intent = new Intent();
                intent.putExtra("UserID", userId);
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d(App.TAG, "connectRcServer onError. errorCode = " + errorCode);
            }
        });
    }
}
