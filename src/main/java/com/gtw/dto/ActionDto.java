package com.gtw.dto;

import java.io.Serializable;

public class ActionDto implements Serializable {

	private String gameToken;

	private String playerToken;

	private String currentWord;

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

	public String getCurrentWord() {
		return currentWord;
	}

	public void setCurrentWord(String currentWord) {
		this.currentWord = currentWord;
	}

}
