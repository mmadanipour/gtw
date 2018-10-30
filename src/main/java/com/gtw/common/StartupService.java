package com.gtw.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.gtw.entity.Word;

public class StartupService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		readWordFile();

	}

	private void readWordFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("words.csv").getFile());
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Word wordDto = new Word();
				String[] split = line.split(",");
				if (split.length != 2) {
					// TODO
					throw new RuntimeException("Words file is damaged");
				}

				wordDto.setWord(split[0]);
				wordDto.setDescription(split[1]);
				Constant.words.add(wordDto);
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("There is a problem in word file reading: " + e.getMessage());
		}

	}

}
