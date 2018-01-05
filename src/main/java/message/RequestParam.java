package message;

public class RequestParam{
	private String ID;
	private String system;
	private String type;
	private String toUsers;
	private String title;
	private String picUrl;
	private String description;
	private String content;
	private String signature;
	private String request_key;
	
	public String getRequest_key() {
		return request_key;
	}
	public void setRequest_key(String request_key) {
		this.request_key = request_key;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getToUsers() {
		return toUsers;
	}
	public void setToUsers(String toUsers) {
		this.toUsers = toUsers;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
}
