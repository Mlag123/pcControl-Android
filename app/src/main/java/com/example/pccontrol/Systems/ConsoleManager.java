package com.example.pccontrol.Systems;

import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConsoleManager {



    private static final int MAX_MESSAGES = 500;//fixme Сделать это значение настраиваемым из настроек


    public interface OnConsoleUpdateListener {
        void onConsoleUpdated();
    }

    private static OnConsoleUpdateListener listener = null;
    private static Runnable onConsoleUpdated = null;

    public static void setOnUpdateCallback(Runnable callback) {
        onConsoleUpdated = callback;
    }

    private static final Map<String, Integer> consoleMap = new LinkedHashMap<>() {

        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
            return size() > MAX_MESSAGES;
        }
    };


    public static void add(String message, int color) {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH.mm:ss", Locale.getDefault()); //fixme Сделать это значение настраиваемым из настроек
        String timestamp = sdf.format(now);
        String unionKey = timestamp + " - " + message + "\n";

        consoleMap.put(unionKey, color);
        if(onConsoleUpdated!=null){

            onConsoleUpdated.run();
        }
    }

    public static void addNonDate(String message, int color) {



        consoleMap.put(message+"\n", color);
        if(onConsoleUpdated!=null){

            onConsoleUpdated.run();
        }
    }


    public static void add(String message) {
        add(message, Color.WHITE);
    }


    public static Map<String, Integer> getMessages() {
        return new LinkedHashMap<>(consoleMap);
    }

    public static void clear() {
        consoleMap.clear();
        if(onConsoleUpdated!=null){
            onConsoleUpdated.run();
        }
    }


}
