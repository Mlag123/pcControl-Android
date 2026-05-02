package com.example.pccontrol.Systems;

import android.graphics.Color;

import com.example.pccontrol.ConsoleFragment;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;

public class LogcatReader {

    private Thread logcatThread;
    private boolean isReading = false;

    private ConsoleFragment consoleFragment;


    public void startingRead() {
        isReading = true;


        if (CommandManager.isLogcat()) {
            logcatThread = new Thread(() -> {

                try {
                    Process process = Runtime.getRuntime().exec("logcat *:w");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                    String line;

                    while (isReading && (line = reader.readLine()) != null) {
                        final String logFile = line;
                        ConsoleManager.add(logFile);
                    }
                    reader.close();

                } catch (Exception ex) {
                }


            });
            logcatThread.start();
        }
    }

    public void stopReading() {
        isReading = false;
        if (logcatThread != null) {
            logcatThread.interrupt();
        }
    }
}
