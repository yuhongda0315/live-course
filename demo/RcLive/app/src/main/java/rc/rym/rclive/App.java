package rc.rym.rclive;

import android.app.Application;

import io.rong.imkit.RongIM;
import rc.rym.rclive.utils.AppUtil;
import rc.rym.rclive.utils.DbManager;

public class App extends Application {

    public static final String TAG = "RcLiveDemo";

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.init(this);
        RongIM.init(this, "8brlm7ufrg9e3");
    }
}
