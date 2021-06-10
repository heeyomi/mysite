package com.douzone.mysite.vo;

public class GalleryVo {
	private Long no;
	private String comments;
	private String url;

	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getComments() {
		return comments;
	}
	public void setComment(String comments) {
		this.comments = comments;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "GalleryVo [no=" + no + ", comments=" + comments + ", url=" + url + "]";
	}
}
