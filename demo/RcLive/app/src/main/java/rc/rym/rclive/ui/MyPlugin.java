package rc.rym.rclive.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import rc.rym.rclive.App;

class MyPlugin implements IPluginModule {
    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, io.rong.imkit.R.drawable.rc_ext_plugin_location_selector);
    }

    @Override
    public String obtainTitle(Context context) {
        return "我的扩展";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        Log.d(App.TAG, "my plugin onclick.");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
