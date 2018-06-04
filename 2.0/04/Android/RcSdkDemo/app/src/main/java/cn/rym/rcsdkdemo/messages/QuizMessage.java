package cn.rym.rcsdkdemo.messages;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "app:quiz", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class QuizMessage extends MessageContent {

    private int num;

    public QuizMessage(int num) {
        this.num = num;
    }

    public QuizMessage(Parcel source) {
        num = source.readInt();
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("number", num);
            return jsonObj.toString().getBytes("UTF-8");
        } catch (JSONException | UnsupportedEncodingException e) {
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
        dest.writeInt(num);
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<QuizMessage> CREATOR = new Creator<QuizMessage>() {

        @Override
        public QuizMessage createFromParcel(Parcel source) {
            return new QuizMessage(source);
        }

        @Override
        public QuizMessage[] newArray(int size) {
            return new QuizMessage[size];
        }
    };
}
