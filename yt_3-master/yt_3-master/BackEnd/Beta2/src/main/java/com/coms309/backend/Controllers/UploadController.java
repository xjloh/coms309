package com.coms309.backend.Controllers;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


@Controller
public class UploadController {
	//Save the uploaded file to this folder
	//private static String UPLOADED_FOLDER = "/home/gylim/var/www/uploads";
    private static String UPLOADED_FOLDER = System.getProperty("user.dir");
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    
    /**
     * Function to get a file upload from client, have to modify to add the file to the database
     * @param file
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/upload") // //new annotation since 4.3
    public ResponseEntity<String> uploadImage(@RequestBody Map<String,String> data) {
		String result="false";
		
		//decode Base64 String to image
		try{
			FileOutputStream fos = new FileOutputStream(UPLOADED_FOLDER + "/"+ data.get("name")); //change the path where you want to save the image
			byte byteArray[] = Base64.decodeBase64((String)data.get("image"));
			fos.write(byteArray);
			 
			result="true";
			fos.close();		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return new ResponseEntity<> ("Upload success",HttpStatus.OK);
	}
        
        
   
    
    @RequestMapping(value = "/getImage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getPhoto() throws IOException {

        File imgPath = new File("/home/gylim/var/www/uploads/pika.jpg");

        byte[] image = Files.readAllBytes(imgPath.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
    	
}
