package info.lzzy.models.view;


import org.json.JSONException;
import org.json.JSONObject;

public class UserLogin {
    private String password;
    private String iphone;
    private long loginTime;
    private String user;
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	JSONObject json=new JSONObject();
        try {
            json.put("password", password);
            json.put("iphone", iphone);
            json.put("loginTime", loginTime);
            json.put("user", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    	return json.toString();
    }
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	public UserLogin() {
	}
	public UserLogin(String password, String iphone, Long loginTime,String user) {
		this.password = password;
		this.iphone = iphone;
		this.loginTime = loginTime;
		this.user=user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIphone() {
		return iphone;
	}
	public void setIphone(String iphone) {
		this.iphone = iphone;
	}
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}
    
}
