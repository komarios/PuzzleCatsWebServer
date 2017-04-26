package marso.controller;

import marso.util.TokenRandomizer;
import marso.model.User;
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
@RequestMapping("/logout")
public class LogoutController {

	private UserDAO userDao = new UserDAO();
	
        @RequestMapping(value = "/user/{id}/token/{token}", method = RequestMethod.PUT)
        public ResponseEntity<Void> updateUser(@PathVariable("id") long userId, @PathVariable("token") String token, @RequestBody User userParam) {
		User user = userDao.getUserById(userId);
	        if ( user == null || user.getToken() == null || !user.getToken().equals( token ) ) {
	            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
		user.setToken( null ) ;
                user = userDao.saveUser(user);
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

}
