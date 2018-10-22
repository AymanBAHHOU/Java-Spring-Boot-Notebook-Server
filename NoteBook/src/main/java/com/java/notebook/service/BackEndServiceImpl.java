package com.java.notebook.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.java.notebook.entities.Input;

@Service(value = "BackEndService")
public class BackEndServiceImpl implements BackEndService {

	@Override
	public String createFileCode(Input input, HttpSession httpSession) {

		String prg = input.getCode().toString(); // get the input of the user and convert it to string
		String fileName = "code" + httpSession.getId() + ".py"; // the file name <<code+sessionId>>
		File file = new File(fileName);

		// write the input code in the created file
		try {

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file, true);

			BufferedWriter out = new BufferedWriter(fw);
			if (file.length() == 0) {
				out.write(prg);
				out.close();
			} else {
				out.newLine();
				out.write(prg);
				out.close();
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return fileName;
	}

	@Override
	public String createPythonProcess(String fileName) {

		// Create the python process
		ProcessBuilder pb = new ProcessBuilder("python", fileName);
		
		pb.redirectErrorStream(true);

		String outString = null;

		try {

			// start the python process
			Process p = pb.start();

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			outString = org.apache.commons.io.IOUtils.toString(in);

			// if the outputStream contain an Error then delete the last line of the file
			// that contains the instructions of the session
			if (outString.contains("Error")) {
				RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
				byte b;
				long length = randomAccessFile.length();
				if (length != 0) {
					do {
						length -= 1;
						randomAccessFile.seek(length);
						b = randomAccessFile.readByte();
					} while (b != 10 && length > 0);
					randomAccessFile.setLength(length);
					randomAccessFile.close();
				}
				// else we have to get only the last element of the outString which corresponds
				// to the response of the last request
			} else {

				String[] l = outString.split("\n");
				outString = l[l.length - 1];

			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return outString;

	}

}
