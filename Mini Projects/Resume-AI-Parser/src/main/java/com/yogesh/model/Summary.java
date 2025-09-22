package com.yogesh.model;

public class Summary {
    private int id;
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private String content;

    public Summary(String content) {
        this.content = content;
    }
    // Getters and Setters
}
