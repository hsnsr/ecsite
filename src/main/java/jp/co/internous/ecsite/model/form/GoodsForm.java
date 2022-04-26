package jp.co.internous.ecsite.model.form;

import java.io.Serializable;

public class GoodsForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String goodsName;
	private int price;
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id=id;
	}
	
	public String getGoodsName() {
		return goodsName;
	}
	
	public void setGoodsName(String goodsName) {
		this.goodsName=goodsName;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price=price;
	}
}

