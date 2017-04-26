package marso.controller;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import marso.model.Game;
import marso.model.User;
import marso.model.GameDAO;
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
@RequestMapping("/game")
public class GameController {

	private GameDAO lobbyDao = new GameDAO();
	private UserDAO userDao = new UserDAO();

        @RequestMapping(value = "/{id}/user/{userId}/token/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Game> getGame(@PathVariable("id") long id, @PathVariable("userId") long userId, @PathVariable("token") String token) {
                User user = userDao.getUserById( userId );
                if( user == null || user.getToken() == null || !user.getToken().equals(token) ){
                        return new ResponseEntity<Game>(HttpStatus.UNAUTHORIZED);
                }
                Game lobby = lobbyDao.getGameById(id);
                if ( lobby == null ) {
                        return new ResponseEntity<Game>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<Game>( lobby, HttpStatus.OK);
        }

}
