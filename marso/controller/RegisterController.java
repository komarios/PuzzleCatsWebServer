package marso.controller;

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
@RequestMapping("/register")
public class RegisterController {

	private UserDAO userDao = new UserDAO();

        @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Void> registerUser(@RequestBody User userParam, UriComponentsBuilder ucBuilder) {
                User user = new User( userParam.getNick(), userParam.getImei(), userParam.getGmail() );
		try {
                	user = userDao.saveUser(user);

		        HttpHeaders headers = new HttpHeaders();
	        	headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getUserId()).toUri());
	        	return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} catch(org.hibernate.exception.ConstraintViolationException unqex){
	        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
        }
}
