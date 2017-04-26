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
@RequestMapping("/user")
public class UserController {

	private UserDAO userDao = new UserDAO();

	@RequestMapping(value="/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
                List<User> users = userDao.getAllUsers();
		return new ResponseEntity<List<User>>( users, HttpStatus.OK);
	}

        @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<User> getUser(@PathVariable("id") long id) {
                User user = userDao.getUserById(id);
                if (user == null) {
                        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<User>(user, HttpStatus.OK);
        }
	
        @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<User> createUser(@RequestBody User userParam, UriComponentsBuilder ucBuilder) {
                User user = new User( userParam.getNick(), userParam.getImei(), userParam.getGmail() );
                user = userDao.saveUser(userParam);
                return new ResponseEntity<User>(user, HttpStatus.OK);
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
        public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User userParam) {
                User user = userDao.saveUser(userParam);
                return new ResponseEntity<User>(user, HttpStatus.OK);
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
        public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
                User user = userDao.getUserById(id);
		userDao.deleteUser(user);
                return new ResponseEntity<User>(user, HttpStatus.OK);
        }
}
