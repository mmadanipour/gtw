package com.gtw.entity;

import java.io.Serializable;

import com.gtw.common.enums.GuessStatus;
import com.gtw.common.enums.RoundPlayer;

public class History implements Serializable{

	private RoundPlayer roundPlayer;
	private GuessStatus guessStatus;
	private Integer position;
	private String letter;

	public RoundPlayer getRoundPlayer() {
		return roundPlayer;
	}

	public void setRoundPlayer(RoundPlayer roundPlayer) {
		this.roundPlayer = roundPlayer;
	}

	public GuessStatus getGuessStatus() {
		return guessStatus;
	}

	public void setGuessStatus(GuessStatus guessStatus) {
		this.guessStatus = guessStatus;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

}
