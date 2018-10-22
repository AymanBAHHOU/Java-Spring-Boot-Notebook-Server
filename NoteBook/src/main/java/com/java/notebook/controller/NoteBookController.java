package com.java.notebook.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.java.notebook.entities.Input;
import com.java.notebook.entities.Output;
import com.java.notebook.service.BackEndService;

@Controller
public class NoteBookController {

	@Autowired
	private BackEndService backEndService;

	@RequestMapping("/execute")
	@ResponseBody

	public Output Interprete(@RequestBody Input input, HttpSession httpSession) {

		Output output = new Output();

		// return the file name. This file will be used as an environment to store all
		// the user requests for a specific session
		String fileName = backEndService.createFileCode(input, httpSession);

		// create a python process and return the process result
		String outString = backEndService.createPythonProcess(fileName);

		output.setResult(outString);

		return output;

	}

}