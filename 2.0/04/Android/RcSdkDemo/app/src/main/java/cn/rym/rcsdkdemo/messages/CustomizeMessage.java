package cn.rym.rcsdkdemo.messages;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "app:custom", flag = MessageTag.ISCOUNTED)
public class CustomizeMessage extends MessageContent {
    private String content;
    private int count;
    private double time;
    private boolean isVIP;
    public ArrayList<String> highlightList = new ArrayList<String>() {{
        add("百度");
        add("新浪");
    }};

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
                content = jsonObj.optString("mContent");
            if (jsonObj.has("mCount"))
                count = jsonObj.optInt("mCount");
            if (jsonObj.has("mTime"))
                time = jsonObj.optDouble("mTime");
            if (jsonObj.has("isVIP"))
                isVIP = jsonObj.optBoolean("isVIP");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("mContent", content);
            jsonObj.put("mCount", count);
            jsonObj.put("mTime", time);
            jsonObj.put("isVIP", isVIP);
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
        dest.writeString(content);
        dest.writeInt(count);
        dest.writeDouble(time);
        dest.writeByte((byte) (isVIP ? 1 : 0));
    }

    protected CustomizeMessage(Parcel in) {
        content = in.readString();
        count = in.readInt();
        time = in.readDouble();
        isVIP = in.readByte() != 0;
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
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public boolean getIsVIP() {
        return isVIP;
    }

    public void setIsVIP(boolean isVIP) {
        this.isVIP = isVIP;
    }
}