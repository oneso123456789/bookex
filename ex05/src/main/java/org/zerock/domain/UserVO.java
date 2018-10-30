package org.zerock.domain;

public class UserVO {
//	VO객체를 선언할때 null을 고려해볼것  int(null 허용X) 와 integer(null 허용O)
	private String uid;
	private String upw;
	private String uname;
	private int upoint;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUpw() {
		return upw;
	}
	public void setUpw(String upw) {
		this.upw = upw;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public int getUpoint() {
		return upoint;
	}
	public void setUpoint(int upoint) {
		this.upoint = upoint;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "UserVO[uid=" + uid + ", upw=" + upw + ", uname" + uname + ", upoint=" + upoint+ "]";
	}
	
	
}
