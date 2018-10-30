package com.gtw.service;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.gtw.common.Constant;
import com.gtw.common.RoundMaintainer;
import com.gtw.common.enums.GuessStatus;
import com.gtw.common.enums.RoundPlayer;
import com.gtw.dto.ActionDto;
import com.gtw.dto.GameState;
import com.gtw.dto.StateRequestDto;
import com.gtw.entity.Game;
import com.gtw.entity.History;
import com.gtw.entity.Player;
import com.gtw.entity.Word;

public class GameService {
	
    private static final Logger logger = Logger.getLogger(GameService.class);

	public GameState startGame(String name) {

		Player playerDto = new Player();
		playerDto.setName(name);
		playerDto.setToken(UUID.randomUUID().toString());

		Iterator<Map.Entry<String, Game>> it = Constant.games.entrySet().iterator();
		Game game = null;
		String gameToken = null;

		
		while (it.hasNext()) {
			Map.Entry<String, Game> entry = it.next();
			if (entry.getValue().getPlayer2() == null) {
				game = entry.getValue();
				gameToken = entry.getKey();
			}
		}

		if (game == null) {
			game = new Game();
			game.setPlayer1(playerDto);
			game.setLastActionTime(new Date());
			gameToken = UUID.randomUUID().toString();
			Constant.games.put(gameToken, game);
			logger.info(String.format("A new game %S started.", gameToken));
			RoundMaintainer roundMaintainer = new RoundMaintainer(gameToken);
			Thread thread = new Thread(roundMaintainer);
			thread.start();

			GameState gameState = convertGameToGameState(game, gameToken);
			gameState.setPlayerName(playerDto.getName());
			gameState.setPlayerToken(playerDto.getToken());
			gameState.setPlayerRound(RoundPlayer.PLAYER1);
			return gameState;
		}
		game.setPlayer2(playerDto);
		game.setCorrectWord(selectRandomWord());
		game.setCurrenntWord(setCurrentWordForFirstTime(game.getCorrectWord()));
		game.setLastActionTime(new Date());
		game.setRound(RoundPlayer.PLAYER1);

		Constant.games.put(gameToken, game);

		GameState gameState = convertGameToGameState(game, gameToken);
		gameState.setPlayerName(playerDto.getName());
		gameState.setPlayerToken(playerDto.getToken());
		gameState.setPlayerRound(RoundPlayer.PLAYER2);

		return gameState;
	}

	public GameState checkAction(ActionDto dto) {

		Game game = Constant.games.get(dto.getGameToken());

		// check game existence
		if (game == null) {
			throw new RuntimeException("There isn't any game with given specification");
		}

		// check player accuracy
		if (!game.getPlayer1().getToken().equals(dto.getPlayerToken())
				&& !game.getPlayer2().getToken().equals(dto.getPlayerToken())) {
			throw new RuntimeException("You aren't player of this game!");
		}

		// check game is finished or not
		if (game.getCurrenntWord().equals(game.getCorrectWord().getWord())) {
			return convertGameToGameState(game, dto.getGameToken());
		}

		// check round
		if ((game.getRound().equals(RoundPlayer.PLAYER1) && !dto.getPlayerToken().equals(game.getPlayer1().getToken()))
				|| (game.getRound().equals(RoundPlayer.PLAYER2)
						&& !dto.getPlayerToken().equals(game.getPlayer2().getToken()))) {

			throw new RuntimeException("It's not your turn");
		}

		// check letter accuracy and set round
		History history = new History();
		if (!checkLetterAccuracy(dto.getCurrentWord(), game, history)) {
			if (game.getRound().equals(RoundPlayer.PLAYER1)) {
				game.setRound(RoundPlayer.PLAYER2);
			} else {
				game.setRound(RoundPlayer.PLAYER1);
			}
			game.getGameHistory().add(history);
			game.setLastActionTime(new Date());

			return convertGameToGameState(game, null);
		}

		// reset player idle round
		if (game.getRound().equals(RoundPlayer.PLAYER1)) {
			game.getPlayer1().resetIdelRoud();
		} else {
			game.getPlayer2().resetIdelRoud();
		}

		game.setCurrenntWord(dto.getCurrentWord());
		game.setLastActionTime(new Date());
		game.getGameHistory().add(history);
		// TODO : check game is finished or not
		return convertGameToGameState(game, null);

	}

	public GameState checkState(StateRequestDto stateRequestDto) {

		Game game = Constant.games.get(stateRequestDto.getGameToken());

		if (game == null) {
			throw new RuntimeException("There isn't any game with given specifications");
		}

		if (!game.getPlayer1().getToken().equals(stateRequestDto.getPlayerToken())
				&& !game.getPlayer2().getToken().equals(stateRequestDto.getPlayerToken())) {
			throw new RuntimeException("You aren't the player of this game");
		}

		return convertGameToGameState(game, stateRequestDto.getGameToken());

	}

	private Boolean checkLetterAccuracy(String newWord, Game gameDto, History history) {

		if (newWord.equals(gameDto.getCurrenntWord())) {
			throw new RuntimeException("No change occure");
		}

		char[] charArrayCurrentWord = gameDto.getCurrenntWord().toCharArray();
		char[] charArrayNewWord = newWord.toCharArray();
		char[] charArrayCorrectWord = gameDto.getCorrectWord().getWord().toCharArray();

		int diff = 0;
		boolean result = false;

		for (int i = 0; i < charArrayCurrentWord.length; i++) {

			if (charArrayCurrentWord[i] == '_'
					&& Character.toLowerCase(charArrayCurrentWord[i]) != Character.toLowerCase(charArrayNewWord[i])) {
				diff++;
				history.setLetter(new String(charArrayNewWord, i, 1).toLowerCase());
				history.setPosition(i + 1);
				history.setGuessStatus(GuessStatus.FAILED);
				history.setRoundPlayer(gameDto.getRound());
				if (Character.toLowerCase(charArrayNewWord[i]) == Character.toLowerCase(charArrayCorrectWord[i])) {
					history.setGuessStatus(GuessStatus.SUCCESS);
					result = true;
				}
			}
		}

		if (diff > 1) {
			throw new RuntimeException("More than one letter changed in input word");
		}

		return result;
	}

	private String setCurrentWordForFirstTime(Word correctWord) {
		StringBuilder currentWord = new StringBuilder();
		for (char c : correctWord.getWord().toCharArray()) {
			currentWord.append("_");
		}

		return currentWord.toString();
	}

	private Word selectRandomWord() {

		Random random = new Random();
		return Constant.words.get(random.nextInt(Constant.words.size()));

	}

	private GameState convertGameToGameState(Game game, String gameToken) {
		GameState gameState = new GameState();
		gameState.setCurrentWord(game.getCurrenntWord());
		gameState.setGameToken(gameToken);
		gameState.setHistories(game.getGameHistory());
		gameState.setRound(game.getRound());
		if (game.getCorrectWord() != null)
			gameState.setWordDescription(game.getCorrectWord().getDescription());
		return gameState;
	}

}
