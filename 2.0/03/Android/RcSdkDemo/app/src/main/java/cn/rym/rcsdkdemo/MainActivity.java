package cn.rym.rcsdkdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class MainActivity extends AppCompatActivity {

    private String TAG = "RcSdkDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onConnectClick(View view) {
        String token ="k5NIL8mbeabYwjqBchah2VT230/IBYT8fvJZJhhGSFqLEcDEyEBM+jKzqGSQNB/dN8WsqlWR2vfRLKWmJLTU3Q==";
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String userId) {
                Log.i(TAG, "onSuccess userId = " + userId);
//                Map<String, Boolean> map = new HashMap<>();
//                map.put(Conversation.ConversationType.PRIVATE.getName(), false);
//                RongIM.getInstance().startConversationList(MainActivity.this, map);

                Intent intent = new Intent(MainActivity.this, ConversationListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i(TAG, "onError errorCode = " + errorCode);
            }

            @Override
            public void onTokenIncorrect() {
                Log.i(TAG, "onTokenIncorrect");
            }
        });
    }
}
