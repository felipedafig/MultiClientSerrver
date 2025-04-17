package Network;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger
{
  private static Logger instance;
  private PrintWriter fileWriter;
  private static final String LOG_FILE_NAME = "server_log.txt";
  private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

  private Logger(){}

  public static Logger getInstance()
  {
    if (instance == null)
    {
      synchronized (Logger.class)
      {
        if (instance == null)
        {
          instance = new Logger();
        }
      }
    }
    return instance;
  }

  public synchronized void log(String ipAddress, String message) {
    if (fileWriter == null) {
      writerInit();
    }

    String timestamp = LocalDateTime.now().format(timeFormatter);
    String logEntry = String.format("[%s] [%s] %s", timestamp, ipAddress, message);
    System.out.println(logEntry);
    if (fileWriter != null) {
      fileWriter.println(logEntry);
      fileWriter.flush();
    } else {
      System.err.println("Log file writer is not initialized.");
    }
  }

  private void writerInit() {
    if (fileWriter != null) {
      fileWriter.close();
      fileWriter = null;
    }
    try {
      FileWriter fw = new FileWriter(LOG_FILE_NAME, true);
      BufferedWriter bw = new BufferedWriter(fw);
      fileWriter = new PrintWriter(bw, true);
      System.out.println("Logging to file: " + LOG_FILE_NAME);
    } catch (IOException e) {
      System.err.println("Could not open log file: " + LOG_FILE_NAME);
      e.printStackTrace();
      fileWriter = null;
    }
  }
  public synchronized void close() {
    if (fileWriter != null) {
      System.out.println("Closing log file: " + LOG_FILE_NAME);
      fileWriter.close();
      fileWriter = null;
    }
  }
}
