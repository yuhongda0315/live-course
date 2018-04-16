package rc.rym.rclive;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;
import rc.rym.rclive.fragment.ContactFragment;
import rc.rym.rclive.fragment.DiscoveryFragment;
import rc.rym.rclive.fragment.MeFragment;
import rc.rym.rclive.utils.DbManager;
import rc.rym.rclive.utils.HttpRequest;
import rc.rym.rclive.utils.User;

public class MainActivity extends AppCompatActivity {

    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        dbManager = new DbManager(this);

        // 设置用户信息提供者。
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(final String userId) {
                Log.d(App.TAG, "getUserInfo userId = " + userId);
                UserInfo userInfo = null;
                User user = dbManager.getUser(userId);
                if (user != null) {
                    userInfo = new UserInfo(user.userId, user.nickname, Uri.parse(user.portrait));
                } else {
                    new HttpRequest().getUser(userId, new HttpRequest.ResponseHandler() {
                        @Override
                        public void onHttpResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String nickname = obj.getString("nickname");
                                String portrait = obj.getString("portrait");
                                User user = new User(userId, nickname, portrait);
                                dbManager.replaceUser(user);
                                RongIM.getInstance().refreshUserInfoCache(user.getUserInfo());
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "Http response parse error: " + response, Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    });
                }
                return userInfo ;
            }
        }, true);

        // 设置群组信息提供者。
        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
            @Override
            public Group getGroupInfo(String groupId) {
                Log.d(App.TAG, "getGroupInfo groupId = " + groupId);
                Group group = new Group(groupId, "我们是一家人", Uri.parse("http://7xs9j5.com1.z0.glb.clouddn.com/rcdemo/icon_6.jpg"));
                return group;
            }
        }, true);
    }

    private void initView() {
        final ArrayList<TitleIcon> titleIconList = new ArrayList<>();
        final ViewPager viewPager = findViewById(R.id.viewpager);
        titleIconList.add(new TitleIcon(findViewById(R.id.titleicon_im), R.mipmap.titlebar_im_nor, R.mipmap.titlebar_im_sel));
        titleIconList.add(new TitleIcon(findViewById(R.id.titleicon_contact), R.mipmap.titlebar_contact_nor, R.mipmap.titlebar_contact_sel));
        titleIconList.add(new TitleIcon(findViewById(R.id.titleicon_discovery), R.mipmap.titlebar_discovery_nor, R.mipmap.titlebar_discovery_sel));
        titleIconList.add(new TitleIcon(findViewById(R.id.titleicon_me), R.mipmap.titlebar_me_nor, R.mipmap.titlebar_me_sel));
        for (int i = 0; i < titleIconList.size(); i++) {
            final int index = i;
            titleIconList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (index != viewPager.getCurrentItem()) {
                        titleIconList.get(viewPager.getCurrentItem()).setGradient(0.0f);
                        viewPager.setCurrentItem(index, false);
                    }
                }
            });
        }

        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), this));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                titleIconList.get(position).setGradient(1 - positionOffset);
                if (position + 1 < titleIconList.size()) {
                    titleIconList.get(position + 1).setGradient(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        RongIM.getInstance().disconnect();
        finish();
    }

    private class TitleIcon {

        private View rootView;
        private ImageView normal;
        private ImageView selected;

        TitleIcon(View root, @DrawableRes int norId, @DrawableRes int selId) {
            rootView = root;
            normal = root.findViewById(R.id.normal);
            selected = root.findViewById(R.id.selected);
            normal.setImageResource(norId);
            selected.setImageResource(selId);
            setGradient(0.0f);
        }

        void setGradient(@FloatRange(from = 0.0, to = 1.0) float alpha) {
            normal.setAlpha(1 - alpha);
            selected.setAlpha(alpha);
        }

        void setOnClickListener(@Nullable View.OnClickListener l) {
            rootView.setOnClickListener(l);
        }
    }

    public static class MainAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragmentList = new ArrayList<>();

        MainAdapter(FragmentManager fm, Context context) {
            super(fm);
            ConversationListFragment fragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://" + context.getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")
                    .build();
            fragment.setUri(uri);
            fragmentList.add(fragment);
            fragmentList.add(new ContactFragment());
            fragmentList.add(new DiscoveryFragment());
            fragmentList.add(new MeFragment());
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
    }
}
