package com.example.project.util;

import java.io.File;

import org.springframework.stereotype.Component;

@Component
public class CreateFileUtil {
	public void createFile(String directory, String fileName) {

		File fileDirectory = new File(directory);
		String filePath = directory + File.separator +fileName;
		File file = new File(filePath);

		if (!fileDirectory.exists()) {

			fileDirectory.mkdir();
			System.out.println(fileDirectory.exists());

		} // createDirectory
		if (fileDirectory.exists() == true && !file.exists()) {
			try {
				file.createNewFile();
				System.out.println("file존재" + file.exists());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // createnewFile
	}// createFile()
}
