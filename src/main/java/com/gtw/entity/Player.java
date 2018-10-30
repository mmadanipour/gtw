package com.gtw.entity;

import java.io.Serializable;

public class Player implements Serializable {

	private String name;
	private String token;
	private Integer idleRound = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getIdleRound() {
		return idleRound;
	}

	public void addIdleRound() {
		this.idleRound ++;
	}
	
	public void resetIdelRoud() {
		this.idleRound = 0;
	}

}
