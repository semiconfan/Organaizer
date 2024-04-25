package server.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogManager {
	
	private File logFile;
	
	public LogManager() {
		String filePath = "src/server/ServerLogs/";
		filePath += getCurrentDateAsString();
		filePath += "_log.txt";
		this.setLogFile(openFileAt(filePath));
		if(getLogFile() == null) {
			System.out.println("LOG_MANAGER:ERROR:Could not open or create a log file, path:" + filePath);
		} else {
			System.out.println("LOG_MANAGER:Log file opened:" + filePath);
		}
	}
	
	private File getLogFile() {
		return logFile;
	}

	private void setLogFile(File logFile) {
		this.logFile = logFile;
	}

	private String getCurrentDateAsString() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return currentDateTime.format(formatter);
	}
	
	private String getFullTimeAsString() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return currentDateTime.format(formatter);
	}
	
	private File openFileAt(String path) {
		File file = new File(path);	
		try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Файл создан: " + path);
            }
		} catch (IOException e) {
            e.printStackTrace();
        }			
		return file;
	}
	
	public void writeToLogs(String info) {
		FileWriter fileWriter = null;
		
		try {
			fileWriter = new FileWriter(this.getLogFile(), true);
			fileWriter.append(getFullTimeAsString() + ":" + info + "\n");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
