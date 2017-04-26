package marso.controller;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import marso.model.Game;
import marso.model.Lobby;
import marso.model.User;
import marso.model.GameDAO;
import marso.model.LobbyDAO;
import marso.model.UserDAO;
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
@RequestMapping("/lobby")
public class LobbyController {

	private GameDAO  gameDao  = new GameDAO();
	private LobbyDAO lobbyDao = new LobbyDAO();
	private UserDAO  userDao  = new UserDAO();

        @RequestMapping(value = "/user/{userId}/token/{token}", method = RequestMethod.POST)
        public ResponseEntity<Void> checkInLobby(@PathVariable("userId") long userId, @PathVariable("token") String token, @RequestBody Lobby lobbyParam, UriComponentsBuilder ucBuilder, HttpServletRequest request) {
		User user = userDao.getUserById( userId );
		if( user == null || user.getToken() == null || !user.getToken().equals(token) ){
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
                Lobby lobby = new Lobby( userId, request.getRemoteAddr() );
		try {
                	lobby = lobbyDao.saveLobby(lobby);
			Map<String,String> vars = new HashMap<>();
			vars.put("id", ""+lobby.getLobbyId() );
			vars.put("userId", ""+userId );
			vars.put("token", token ); 

		        HttpHeaders headers = new HttpHeaders();
	        	headers.setLocation( ucBuilder.path("/lobby/{id}/user/{userId}/token/{token}").buildAndExpand( vars ).toUri() );
	        	return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} catch(org.hibernate.exception.ConstraintViolationException unqex){
	        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
        }

        @RequestMapping(value = "/{id}/user/{userId}/token/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Lobby> getLobby(@PathVariable("id") long id, @PathVariable("userId") long userId, @PathVariable("token") String token, UriComponentsBuilder ucBuilder) {
                User user = userDao.getUserById( userId );
                if( user == null || user.getToken() == null || !user.getToken().equals(token) ){
                        return new ResponseEntity<Lobby>(HttpStatus.UNAUTHORIZED);
                }
                Lobby lobby = lobbyDao.getLobbyById(id);
                if ( lobby == null ) {
			List<Game> games = gameDao.getGamesByUserIdAndStatus( userId, Game.GameStatus.created );
			if( games.size() == 0 ){
				return new ResponseEntity<Lobby>(HttpStatus.NOT_FOUND);
			} else {
				Map<String,String> vars = new HashMap<>();
                	        vars.put("id", ""+((Game)(games.get(0))).getGameId() );
                        	vars.put("userId", ""+userId );
	                        vars.put("token", token );

				HttpHeaders headers = new HttpHeaders();
        	                headers.setLocation( ucBuilder.path("/game/{id}/user/{userId}/token/{token}").buildAndExpand( vars ).toUri() );
	                        return new ResponseEntity<Lobby>(headers, HttpStatus.MOVED_PERMANENTLY);
			}
                }
                return new ResponseEntity<Lobby>( lobby, HttpStatus.OK);
        }

}
