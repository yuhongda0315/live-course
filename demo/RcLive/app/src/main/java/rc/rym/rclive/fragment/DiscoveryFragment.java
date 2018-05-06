package rc.rym.rclive.fragment;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import rc.rym.rclive.App;
import rc.rym.rclive.R;
import rc.rym.rclive.message.ChatroomGift;
import rc.rym.rclive.message.CustomizeMessage;

public class DiscoveryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment_discovery, container,false);
        DiscoveryListItem streamShow = new DiscoveryListItem(root.findViewById(R.id.discovery_stream_show), R.mipmap.panel_stream_show, "发送自定义消息", true);
        streamShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(App.TAG, "视频直播");
                CustomizeMessage cm = new CustomizeMessage();
                cm.setContent("呵呵呵");
                Message msg = Message.obtain("User_D", Conversation.ConversationType.PRIVATE, cm);
                RongIM.getInstance().sendMessage(msg, null, null, new IRongCallback.ISendMessageCallback() {
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
        });

        DiscoveryListItem watchShow = new DiscoveryListItem(root.findViewById(R.id.discovery_watch_show), R.mipmap.panel_watch_show, "观看直播", false);
        watchShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(App.TAG, "观看直播");
                ChatroomGift giftMsg = new ChatroomGift();
                giftMsg.setId("火箭");
                giftMsg.setNumber(1);
                giftMsg.setTotal(5);
                Message msg = Message.obtain("User_D", Conversation.ConversationType.PRIVATE, giftMsg);
                RongIM.getInstance().sendMessage(msg, null, null, new IRongCallback.ISendMessageCallback() {
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
        });
        return root;
    }

    private class DiscoveryListItem {
        private View rootView;

        DiscoveryListItem(View root, @DrawableRes int imgId, String name, boolean showUnderline) {
            rootView = root;

            ImageView image = rootView.findViewById(R.id.image);
            image.setImageResource(imgId);
            TextView title = rootView.findViewById(R.id.title);
            title.setText(name);
            View underline = rootView.findViewById(R.id.underline);
            underline.setVisibility(showUnderline ? View.VISIBLE : View.INVISIBLE);
        }

        void setOnClickListener(@Nullable View.OnClickListener listener) {
            rootView.setOnClickListener(listener);
        }
    }
}
