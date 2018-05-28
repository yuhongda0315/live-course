package cn.rym.rcsdkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.rong.imkit.fragment.ConversationFragment;

public class ConversationActivity extends AppCompatActivity {

    private ConversationFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        // for Baymax.
        fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
    }

    @Override
    public void onBackPressed() {
        // for Baymax.
        if (fragment != null && !fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
