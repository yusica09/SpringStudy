package kr.spring.mvc05.vo;

public class SearchCommand {
	
	private String type;
	private String query;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	
	@Override
	public String toString() {
		return "SearchCommand [type=" + type + ", query=" + query + "]";
	}
	
	

}
