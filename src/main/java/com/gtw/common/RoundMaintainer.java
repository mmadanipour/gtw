package com.gtw.common;

import java.util.Date;

import org.apache.log4j.Logger;

import com.gtw.common.enums.RoundPlayer;
import com.gtw.entity.Game;
import com.gtw.entity.History;

public class RoundMaintainer implements Runnable {
	
	private static final Logger logger = Logger.getLogger(RoundMaintainer.class);

	public RoundMaintainer(String gameToken) {
		this.gameToken = gameToken;
	}

	private String gameToken;

	@Override
	public void run() {

		if (gameToken !=
				null) {
			Game game = Constant.games.get(gameToken);

			while (game != null) {
				Date date = new Date();
				if (game.getRound() != null && game.getCurrenntWord().equalsIgnoreCase(game.getCorrectWord().getWord())
						&& (date.getTime() - game.getLastActionTime().getTime()) / 1000 > Constant.validActionTime) {
					Constant.games.remove(gameToken);
					logger.info(String.format("Game %S finished", gameToken));
					break;
				}

				Long duration = (date.getTime() - game.getLastActionTime().getTime()) / 1000;

				if (duration >= Constant.validActionTime) {
					if (game.getRound() == null) {
						Constant.games.remove(gameToken);
						logger.info(String.format("Game %S finished", gameToken));
						break;
					} else if (game.getRound().equals(RoundPlayer.PLAYER1)) {
						History history = new History();
						history.setRoundPlayer(RoundPlayer.PLAYER1);
						game.getGameHistory().add(history);
						game.getPlayer1().addIdleRound();
						game.setRound(RoundPlayer.PLAYER2);
					} else if (game.getRound().equals(RoundPlayer.PLAYER2)) {
						History history = new History();
						history.setRoundPlayer(RoundPlayer.PLAYER1);
						game.getGameHistory().add(history);
						game.getPlayer2().addIdleRound();
						game.setRound(RoundPlayer.PLAYER2);
					}

					//check idleRound of Players
					if (game.getPlayer1().getIdleRound() == Constant.maxIdleRound
							|| game.getPlayer2().getIdleRound() == Constant.maxIdleRound) {
						game.setCurrenntWord(game.getCorrectWord().getWord());
					}

					game.setLastActionTime(date);
					Constant.games.put(gameToken, game);

					try {
						Thread.sleep(1000l);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

			}
		}

	}

}
