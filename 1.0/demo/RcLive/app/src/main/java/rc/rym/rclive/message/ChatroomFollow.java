
package rc.rym.rclive.message;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "RC:Chatroom:Follow", flag = 3)
public class ChatroomFollow extends MessageContent {
  public ChatroomFollow() {
  }
  public ChatroomFollow(byte[] data) {
    String jsonStr = null;
    try {
        jsonStr = new String(data, "UTF-8");
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    try {
        JSONObject jsonObj = new JSONObject(jsonStr);
        
          if (jsonObj.has("id")){
            id = jsonObj.optString("id");
          }
        
          if (jsonObj.has("name")){
            name = jsonObj.optString("name");
          }
        
          if (jsonObj.has("portrait")){
            portrait = jsonObj.optString("portrait");
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
        
            jsonObj.put("name", name);
        
            jsonObj.put("portrait", portrait);
        
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
      
    
      
         ParcelUtils.writeToParcel(dest, name);
      
    
      
         ParcelUtils.writeToParcel(dest, portrait);
      
    
  }
  protected ChatroomFollow(Parcel in) {
    
      
        id = ParcelUtils.readFromParcel(in);
      
    
      
        name = ParcelUtils.readFromParcel(in);
      
    
      
        portrait = ParcelUtils.readFromParcel(in);
      
    
  }
  public static final Creator<ChatroomFollow> CREATOR = new Creator<ChatroomFollow>() {
    @Override
    public ChatroomFollow createFromParcel(Parcel source) {
        return new ChatroomFollow(source);
    }
    @Override
    public ChatroomFollow[] newArray(int size) {
        return new ChatroomFollow[size];
    }
  };
  
    private String id;
    public void setId(   String  id) {
        id = id;
    }
    public String getId() {
      return id;
    }
  
    private String name;
    public void setName(   String  name) {
        name = name;
    }
    public String getName() {
      return name;
    }
  
    private String portrait;
    public void setPortrait(   String  portrait) {
        portrait = portrait;
    }
    public String getPortrait() {
      return portrait;
    }
  
}
