package cn.rym.rcsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cn.rym.rcsdkdemo.messages.CustomizeMessage;
import cn.rym.rcsdkdemo.messages.QuizMessage;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class MainActivity extends AppCompatActivity {

    private String TAG = "RcSdkDemo";
    private TextView information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        information = findViewById(R.id.information);
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                Log.i("RYM_DG",  "obj = " + message.getObjectName());
                return false;
            }
        });
    }

    public void onQuizzerConnect(View view) {
        String token ="o1EuaIVw8YmZ0nn43LFZWOqtT+2pTtqTJxvueMQ9leckLdWGAI2gKZ9bfeDI0Vs19Il2KbI5DF1mtGrzAPr5GCjlj5dFX+c9";
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(final String userId) {
                Log.i(TAG, "onSuccess userId = " + userId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        information.setText(userId + " login.");
                        findViewById(R.id.quizzer_view).setVisibility(View.VISIBLE);
                    }
                });
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

    public void onAnswererConnect(View view) {
        String token ="x15s5i4hijb2jgXgnyI3tkl+2deeBHLB8PmGCLXd7bLgKFKO58voW8NC/Xf+Qdvy4o9qt2PVeCnSguyA5d67zA==";
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(final String userId) {
                Log.i(TAG, "onSuccess userId = " + userId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        information.setText(userId + " login.");
                        findViewById(R.id.answerer_view).setVisibility(View.VISIBLE);
                    }
                });
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

    public void onSendQuiz(View view) {
        QuizMessage quiz = new QuizMessage(18);
//        CustomizeMessage quiz = new CustomizeMessage();
        Message msg = Message.obtain("answerer", Conversation.ConversationType.PRIVATE, quiz);
        RongIM.getInstance().sendMessage(msg, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                Log.i(TAG, "send onAttached");
            }

            @Override
            public void onSuccess(Message message) {
                Log.i(TAG, "send onSuccess");
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                Log.i(TAG, "send onError");
            }
        });
    }

    public void onAnswerQuiz(View view) {
    }
}
