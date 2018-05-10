package marso.controller;

import marso.model.Vue;
import marso.model.VueFile;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.*;
import java.io.File;

@RestController
@RequestMapping("/vue")
public class VueController {

		@Autowired
		ServletContext context;
        private Vue vue = new Vue( "Marios", "Kogias" );

        @RequestMapping(value="/", method = RequestMethod.GET)
        public ResponseEntity<Vue> getDefaultVue(HttpServletRequest request) {
                vue = new Vue( "Marios", "Kogias" );
                return new ResponseEntity<Vue>( vue, HttpStatus.OK);
        }

        @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Vue> createVue(@RequestBody Vue vueParam, UriComponentsBuilder ucBuilder) {
                vue = new Vue( vueParam.getfname()+"111", vueParam.getlname()+"222" );
                return new ResponseEntity<Vue>(vue, HttpStatus.OK);
        }
		
		@RequestMapping(value = "/fileupload", headers=("content-type=multipart/*"), method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<VueFile> fileupload(@RequestParam("file") MultipartFile inputFile) {
			VueFile vueFile = new VueFile();
			//HttpHeaders headers = new HttpHeaders();
			if (!inputFile.isEmpty()) {
				try {
					String originalFilename = inputFile.getOriginalFilename();
					File destinationFile = new File(context.getRealPath("/WEB-INF/uploaded") + File.separator + originalFilename);
					inputFile.transferTo(destinationFile);
					vueFile.setFileName(destinationFile.getPath());
					vueFile.setFileSize(inputFile.getSize());
					//headers.add("File Uploaded Successfully - ", originalFilename);
					//return new ResponseEntity<VueFile>(vueFile, headers, HttpStatus.OK);
					return new ResponseEntity<VueFile>(vueFile, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity<VueFile>(HttpStatus.BAD_REQUEST);
				}
			}else{
				return new ResponseEntity<VueFile>(HttpStatus.BAD_REQUEST);
			}
		}

}
