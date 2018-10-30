package com.gtw.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gtw.common.enums.RoundPlayer;

public class Game implements Serializable {

	private Player player1;
	private Player player2; 
	private Word correctWord;
	private String currenntWord;
	private RoundPlayer round;
	private Date lastActionTime;
	private List<History> gameHistory = new ArrayList<>();

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Word getCorrectWord() {
		return correctWord;
	}

	public void setCorrectWord(Word correctWord) {
		this.correctWord = correctWord;
	}

	public String getCurrenntWord() {
		return currenntWord;
	}

	public void setCurrenntWord(String currenntWord) {
		this.currenntWord = currenntWord;
	}

	public RoundPlayer getRound() {
		return round;
	}

	public void setRound(RoundPlayer round) {
		this.round = round;
	}

	public Date getLastActionTime() {
		return lastActionTime;
	}

	public void setLastActionTime(Date lastActionTime) {
		this.lastActionTime = lastActionTime;
	}
	
	

	public List<History> getGameHistory() {
		return gameHistory;
	}

	public void setGameHistory(List<History> gameHistory) {
		this.gameHistory = gameHistory;
	}

}
