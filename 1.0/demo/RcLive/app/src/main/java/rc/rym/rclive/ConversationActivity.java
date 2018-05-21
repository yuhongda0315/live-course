package rc.rym.rclive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.rong.imkit.fragment.ConversationFragment;

public class ConversationActivity extends AppCompatActivity {
    private ConversationFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        // for Baymax.
        fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation_fragment);
    }

    @Override
    public void onBackPressed() {
        // for Baymax.
        if (fragment != null && !fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
