package rc.rym.rclive.message;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "RC:Chatroom:Gift", flag = 3)
public class ChatroomGift extends MessageContent {

    public ChatroomGift() {
    }

    public ChatroomGift(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("id")) {
                id = jsonObj.optString("id");
            }

            if (jsonObj.has("number")) {
                number = jsonObj.optInt("number");
            }

            if (jsonObj.has("total")) {
                total = jsonObj.optInt("total");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("number", number);
            jsonObj.put("total", total);
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
        ParcelUtils.writeToParcel(dest, id);
        ParcelUtils.writeToParcel(dest, number);
        ParcelUtils.writeToParcel(dest, total);
    }

    protected ChatroomGift(Parcel in) {
        id = ParcelUtils.readFromParcel(in);
        number = ParcelUtils.readIntFromParcel(in);
        total = ParcelUtils.readIntFromParcel(in);
    }

    public static final Creator<ChatroomGift> CREATOR = new Creator<ChatroomGift>() {
        @Override
        public ChatroomGift createFromParcel(Parcel source) {
            return new ChatroomGift(source);
        }

        @Override
        public ChatroomGift[] newArray(int size) {
            return new ChatroomGift[size];
        }
    };

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    private int number;

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    private int total;

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

}
