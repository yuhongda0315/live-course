package cn.rym.rcsdkdemo;

import android.app.Application;

import cn.rym.rcsdkdemo.messages.CustomizeMessage;
import cn.rym.rcsdkdemo.messages.QuizMessage;
import io.rong.imkit.RongIM;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this, "p5tvi9dspnnn4");
        RongIM.registerMessageType(QuizMessage.class);
    }
}