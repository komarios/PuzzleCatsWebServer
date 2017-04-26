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
@RequestMapping("/login")
public class LoginController {

	private UserDAO userDao = new UserDAO();
	
        @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
        public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User userParam) {
		User user = userDao.getUserById(id);
	        if ( user == null ) {
	            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        	}

		user.setToken( TokenRandomizer.next() );
		user.setLastloginTime( Calendar.getInstance().getTime() );
                user = userDao.saveUser(user);
                return new ResponseEntity<User>(user, HttpStatus.OK);
        }

}
