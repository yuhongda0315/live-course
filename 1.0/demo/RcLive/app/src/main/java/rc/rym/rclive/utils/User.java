package rc.rym.rclive.utils;

import android.net.Uri;

import io.rong.imlib.model.UserInfo;

public class User {

    public String userId;
    public String nickname;
    public String portrait;

    private User() {
    }

    public User(String userId, String nickname, String portrait) {
        this.userId = userId;
        this.nickname = nickname;
        this.portrait = portrait;
    }

    public UserInfo getUserInfo() {
        return new UserInfo(userId, nickname, Uri.parse(portrait));
    }
}
