package cn.rym.rcsdkdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class ConversationListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话，该会话非聚合显示
                .build();
        fragment.setUri(uri);  //设置 ConverssationListFragment 的显示属性

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.conversationlist, fragment);
        transaction.commit();
    }
}
