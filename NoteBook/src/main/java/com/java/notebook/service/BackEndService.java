package com.java.notebook.service;

import javax.servlet.http.HttpSession;

import com.java.notebook.entities.Input;

public interface BackEndService {
	
	String createFileCode(Input input, HttpSession httpSession);
	
	String createPythonProcess(String fileName);
	
}
