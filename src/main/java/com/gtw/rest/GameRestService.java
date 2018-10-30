package com.gtw.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.gtw.common.enums.ResponseStatus;
import com.gtw.dto.ActionDto;
import com.gtw.dto.GameState;
import com.gtw.dto.ResponseDto;
import com.gtw.dto.StateRequestDto;
import com.gtw.service.GameService;

@Path("/game")
public class GameRestService {

	private GameService gameService;

	public GameRestService() {
		this.gameService = new GameService();
	}
	
	private static final Logger logger = Logger.getLogger(GameRestService.class);

	@Path("/start")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDto startGame(@QueryParam("name") String name) {

		ResponseDto responseDto = null;
		try {
			GameState startGame = gameService.startGame(name);
			responseDto = new ResponseDto<GameState>(startGame, ResponseStatus.REQUEST_OK);

		} catch (Exception e) {
			logger.error(e.getMessage());
			responseDto = new ResponseDto<String>(e.getMessage(), ResponseStatus.BAD_REQUEST);
		}
		return responseDto;

	}

	@Path("/checkAction")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDto startGame(ActionDto action) {
		
		ResponseDto responseDto = null;
		try {
			GameState gameState = gameService.checkAction(action);
			responseDto = new ResponseDto<GameState>(gameState, ResponseStatus.REQUEST_OK);

		} catch (Exception e) {
			logger.error(e.getMessage());
			responseDto = new ResponseDto<String>(e.getMessage(), ResponseStatus.BAD_REQUEST);
		}
		return responseDto;
	}
	
	@Path("/checkState")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseDto checkGameState(StateRequestDto stateRequest) {
		
		ResponseDto responseDto = null;
		try {
			GameState gameState = gameService.checkState(stateRequest);
			responseDto = new ResponseDto<GameState>(gameState, ResponseStatus.REQUEST_OK);

		} catch (Exception e) {
			logger.error(e.getMessage());
			responseDto = new ResponseDto<String>(e.getMessage(), ResponseStatus.BAD_REQUEST);
		}
		return responseDto;
		
	}
	
	

}
