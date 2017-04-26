package marso.controller;

import marso.util.TokenRandomizer;
import marso.model.Pinger;
import javax.servlet.http.HttpServletRequest;
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
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/ping")
public class PingController {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
	private Pinger pinger = new Pinger();
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ResponseEntity<Pinger> pingServer(HttpServletRequest request) {
		pinger.setserver_time( sdf.format( Calendar.getInstance().getTime() ) );
		//pinger.setserver_time( ""+System.currentTimeMillis() );
		pinger.setclient_ip( request.getRemoteAddr() );
		return new ResponseEntity<Pinger>( pinger, HttpStatus.OK);
	}
}

