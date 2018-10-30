package com.gtw.dto;

import java.io.Serializable;

public class StateRequestDto implements Serializable {

	private String gameToken;
	private String playerToken;

	public String getGameToken() {
		return gameToken;
	}

	public void setGameToken(String gameToken) {
		this.gameToken = gameToken;
	}

	public String getPlayerToken() {
		return playerToken;
	}

	public void setPlayerToken(String playerToken) {
		this.playerToken = playerToken;
	}

}
