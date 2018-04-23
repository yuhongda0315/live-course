package rc.rym.rclive;

import android.app.Application;

import com.facebook.stetho.Stetho;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.push.RongPushClient;
import rc.rym.rclive.message.CustomizeMessage;
import rc.rym.rclive.message.CustomizeMessageItemProvider;
import rc.rym.rclive.ui.MyExtensionModule;
import rc.rym.rclive.ui.MyPrivateConversationProvider;
import rc.rym.rclive.utils.AppUtil;

public class App extends Application {

    public static final String TAG = "RcLiveDemo";

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        AppUtil.init(this);
        RongIM.init(this, "8brlm7ufrg9e3");
        RongIM.registerMessageType(CustomizeMessage.class);
        RongIM.registerMessageTemplate(new CustomizeMessageItemProvider());
//        RongIM.getInstance().registerConversationTemplate(new MyPrivateConversationProvider());
//        RongPushClient.registerMiPush(this, miAppId, miAppKey);

        RongIM.getInstance().enableNewComingMessageIcon(true);  // 显示新消息提醒
        RongIM.getInstance().enableUnreadMessageIcon(true);  // 显示未读消息数目
    }
}
