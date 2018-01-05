package datav.entity;

public class Point {
	
	private Float lat;
	private Float lng;
	private int value;
	private int type;
	private String info;
	
	
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Point(){
		this.value=1;
		this.type=1;
	}
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public Float getLng() {
		return lng;
	}
	public void setLng(Float lng) {
		this.lng = lng;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
