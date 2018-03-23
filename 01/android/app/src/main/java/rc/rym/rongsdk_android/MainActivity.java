package rc.rym.rongsdk_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onInit(View view) {
        RongIMClient.init(this, "8brlm7ufrg9e3");
        RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                MessageContent msgContent = message.getContent();
                if (msgContent instanceof TextMessage) {
                    Log.d(TAG, "msg = " + ((TextMessage) msgContent).getContent());
                }
                return false;
            }
        });
    }


    public void onConnect(View view) {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.getToken("User_B", new HttpRequest.TokenHandler() {
            @Override
            public void onTokenHandler(String token) {
                RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
                    @Override
                    public void onTokenIncorrect() {
                        Log.d(TAG, "onTokenIncorrect");
                    }

                    @Override
                    public void onSuccess(String userId) {
                        Log.d(TAG, "onSuccess userId = " + userId);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        Log.d(TAG, "onError errorCode = " + errorCode);
                    }
                });
            }
        });
    }

    public void onSend(View view) {
        TextMessage msg = TextMessage.obtain("Hello User_A, from Android");
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, "User_A", msg, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {

            }

            @Override
            public void onSuccess(Message message) {

            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {

            }
        });
    }
}
