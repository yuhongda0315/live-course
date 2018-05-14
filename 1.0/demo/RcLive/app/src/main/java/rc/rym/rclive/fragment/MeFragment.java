package rc.rym.rclive.fragment;

import android.content.Intent;
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

import io.rong.imlib.RongIMClient;
import rc.rym.rclive.App;
import rc.rym.rclive.LoginActivity;
import rc.rym.rclive.R;
import rc.rym.rclive.utils.AppUtil;

public class MeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment_me, container, false);
        initView(root);
        MeListItem logout = new MeListItem(root.findViewById(R.id.me_logout), R.mipmap.panel_logout, "退出登录", false);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.clearUserInfoCached();
                RongIMClient.getInstance().logout();

                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                intent.putExtra("logout", true);
                startActivity(intent);
                Log.d(App.TAG, "finish");
                getActivity().finish();
            }
        });
        return root;
    }

    private void initView(View root) {
        TextView nickname = root.findViewById(R.id.nickname);
        nickname.setText(AppUtil.getCurrentUser().nickname);
        TextView userId = root.findViewById(R.id.user_id);
        userId.setText("登录号: " + AppUtil.getCurrentUser().userId);
    }

    private class MeListItem {
        private View rootView;

        MeListItem(View root, @DrawableRes int imgId, String name, boolean showUnderline) {
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
