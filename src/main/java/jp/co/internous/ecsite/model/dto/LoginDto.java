//ログイン情報をJava側からControllerに渡すためのLoginDto

package jp.co.internous.ecsite.model.dto;

import jp.co.internous.ecsite.model.entity.User;

public class LoginDto {

	private long id;
	private String userName;
	private String password;
	private String fullName;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id=id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName=userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password=password;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName=fullName;
	}
	
	
	
	public LoginDto() {}
	
	public LoginDto(User user) {
		this.id=user.getId();
		this.userName=user.getUserName();
		this.password=user.getPassword();
		this.fullName=user.getFullName();
	}
	
	public LoginDto(long id, String userName, String password, String fullName) {
		this.id=id;
		this.userName=userName;
		this.password=password;
		this.fullName=fullName;
	}
	//LoinDtoはコンストラクタが3つ。(publicのやつ)以下の3パターンでインスタンス化できるようにするため。
	//1　インスタンス化の時に初期設定せずあとからsetterを使い1つずつデータをセットする
	//2　Userエンティティをまとめて受け取りデータをセット
	//3　引数を分けて受け取りデータをセット
}