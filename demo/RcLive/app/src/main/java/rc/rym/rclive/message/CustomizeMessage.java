package rc.rym.rclive.message;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "app:custom", flag = MessageTag.ISCOUNTED)
public class CustomizeMessage extends MessageContent {
    private String mContent;

    public CustomizeMessage() {
    }

    public CustomizeMessage(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("mContent"))
                mContent = jsonObj.optString("mContent");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("mContent", mContent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mContent);
    }

    protected CustomizeMessage(Parcel in) {
        mContent = in.readString();
    }

    public static final Creator<CustomizeMessage> CREATOR = new Creator<CustomizeMessage>() {
        @Override
        public CustomizeMessage createFromParcel(Parcel source) {
            return new CustomizeMessage(source);
        }

        @Override
        public CustomizeMessage[] newArray(int size) {
            return new CustomizeMessage[size];
        }
    };

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

}