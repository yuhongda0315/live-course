package rc.rym.rclive.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import io.rong.imkit.emoticon.IEmoticonTab;

public class MyEmoticon implements IEmoticonTab {
    @Override
    public Drawable obtainTabDrawable(Context context) {
        return context.getResources().getDrawable(io.rong.imkit.R.drawable.rc_unread_msg_arrow);
    }

    public View obtainTabPager(Context context) {
        View view = LayoutInflater.from(context).inflate(io.rong.imkit.R.layout.rc_ext_emoji_pager, null);
        return view;
    }

    public void onTableSelected(int position) {
    }
}
