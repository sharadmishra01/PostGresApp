package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.db.DBHandler;

@Controller
public class DownloadDBDataController {

	@Autowired
	private Environment env;

	@RequestMapping("/")
	public String index() {
		return "hello.html";
	}

	@RequestMapping("/saveStudents")
	@ResponseBody
	public String readAndSaveStudents() {
		try {
			String path = env.getProperty("csvPath", "D:\\students.csv");
			DBHandler.getInstance().getAllStudentsAndSavetoFile(path);
		} catch (Exception ex) {
			return "Download of Student Data Failed: " + ex.getMessage();
		}
		return "Download Done";
	}

}
