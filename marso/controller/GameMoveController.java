package marso.controller;

import marso.model.GameMove;
import marso.model.GameMoveDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.*;

@RestController
@RequestMapping("/move")
public class GameMoveController {

        private GameMoveDAO gameMoveDao = new GameMoveDAO();

        @RequestMapping(value = "/game/{id}/user/{userId}/move/{moveId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<GameMove>> getLatestMove(
			@PathVariable("id") long id, @PathVariable("userId") long userid, @PathVariable("moveId") long moveid) {
                List<GameMove> gamemoves = gameMoveDao.getLatestGameMoves(id, userid, moveid);
		int i=0;
		while ( i < 30 && gamemoves.size() == 0 ) {
			i++;
			try{
				Thread.sleep(500);
			}catch(java.lang.InterruptedException ie){
				ie.printStackTrace();	
			}
			gamemoves = gameMoveDao.getLatestGameMoves(id, userid, moveid);
		}
		if( gamemoves.size() == 0 ){
                        return new ResponseEntity<List<GameMove>>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<List<GameMove>>(gamemoves, HttpStatus.OK);
        }

        @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<GameMove> makeMove(@RequestBody GameMove gameMoveParam, UriComponentsBuilder ucBuilder) {
                GameMove gamemove = new GameMove( gameMoveParam.getGameId(), gameMoveParam.getUserId(), gameMoveParam.getMove() );
                gamemove = gameMoveDao.saveGameMove(gamemove);
                return new ResponseEntity<GameMove>(gamemove, HttpStatus.OK);
        }
}
