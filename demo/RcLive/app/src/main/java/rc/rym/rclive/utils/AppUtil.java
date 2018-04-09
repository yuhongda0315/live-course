package rc.rym.rclive.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import rc.rym.rclive.R;

public class AppUtil {

    private static final String SHARED_PREFERENCES = "rclive";
    private static final String LOGIN_USERID = "LOGIN_USERID";
    private static final String LOGIN_TOKEN = "LOGIN_TOKEN";

    private static SharedPreferences gsp;
    private static DisplayImageOptions imageOptions;
    private static User currentUser;

    public static void init(Context context) {
        gsp = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);;
        initAsyncImageLoader(context);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void setCurrentUser(String userId, String nickname, String portrait) {
        currentUser = new User(userId, nickname, portrait);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void saveUserInfoCached(String userId, String token) {
        SharedPreferences.Editor editor = gsp.edit();
        editor.putString(LOGIN_USERID, userId);
        editor.putString(LOGIN_TOKEN, token);
        editor.apply();
    }

    public static String getCachedUserId() {
        return gsp.getString(LOGIN_USERID, "");
    }

    public static String getCachedToken() {
        return gsp.getString(LOGIN_TOKEN, "");
    }

    public static void clearUserInfoCached() {
        SharedPreferences.Editor editor = gsp.edit();
        editor.clear().apply();
    }

    public static void loadRemoteImage(String uri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(uri, imageView, imageOptions);
    }

    private static void initAsyncImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        ImageLoader.getInstance().init(config.build());

        imageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.image_default)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }
}
