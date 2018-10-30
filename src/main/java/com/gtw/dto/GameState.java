package com.gtw.dto;

import java.io.Serializable;
import java.util.List;

import com.gtw.common.enums.RoundPlayer;
import com.gtw.entity.History;

public class GameState implements Serializable {

	private String gameToken;
	private String playerToken;
	private String playerName;
	private String currentWord;
	private String wordDescription;
	private RoundPlayer playerRound;
	private RoundPlayer round;
	private List<History> histories;

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

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getCurrentWord() {
		return currentWord;
	}

	public void setCurrentWord(String currentWord) {
		this.currentWord = currentWord;
	}

	public RoundPlayer getPlayerRound() {
		return playerRound;
	}

	public void setPlayerRound(RoundPlayer playerRound) {
		this.playerRound = playerRound;
	}

	public RoundPlayer getRound() {
		return round;
	}

	public void setRound(RoundPlayer round) {
		this.round = round;
	}

	public List<History> getHistories() {
		return histories;
	}

	public void setHistories(List<History> histories) {
		this.histories = histories;
	}

	public String getWordDescription() {
		return wordDescription;
	}

	public void setWordDescription(String wordDescription) {
		this.wordDescription = wordDescription;
	}
	


}
