package kr.spring.mvc03.vo;

public class NewArticleCommand {
	private String title;
	private String name;
	private String content;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	@Override
	public String toString() {
		return "NewArticleCommand [title=" + title + ", name=" + name + ", content=" + content + "]";
	}
	
	
}
