package com.amit.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import javax.persistence.criteria.Path;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amit.blog.services.FileService;
@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// File name
		String name = file.getOriginalFilename();

		String randomId = UUID.randomUUID().toString();

		String randomfilename = randomId.concat(name.substring(name.lastIndexOf(".")));
		// Full path
		String filePath = path + File.separator + randomfilename;
		File newFile = new File(path);
		// create folder if not present
		if (!newFile.exists())
			newFile.mkdir();

		
			Files.copy(file.getInputStream(), Paths.get(filePath));
		

		return randomfilename;
	}

	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String fullpath=path+  File.separator+filename;
		InputStream iStream= new FileInputStream(fullpath);
		
		return iStream;
	}

}
