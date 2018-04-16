package rc.rym.rclive;

import android.app.Application;

import io.rong.imkit.RongIM;
import rc.rym.rclive.message.CustomizeMessage;
import rc.rym.rclive.message.CustomizeMessageItemProvider;
import rc.rym.rclive.utils.AppUtil;

public class App extends Application {

    public static final String TAG = "RcLiveDemo";

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.init(this);
        RongIM.init(this, "8brlm7ufrg9e3");
        RongIM.registerMessageType(CustomizeMessage.class);
        RongIM.registerMessageTemplate(new CustomizeMessageItemProvider());
    }
}
