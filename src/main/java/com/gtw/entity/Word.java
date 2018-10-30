package com.gtw.entity;

import java.io.Serializable;

public class Word implements Serializable{

	private String word;
	private String description;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
