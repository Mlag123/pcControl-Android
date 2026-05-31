package com.example.pccontrol.Core;

import android.graphics.Color;

import com.example.pccontrol.Adapter.ProcessInfo;
import com.example.pccontrol.Systems.ConsoleManager;

import java.util.ArrayList;

public class APIConnect {


    private static String host  = "http://10.0.2.2:8080/api/windows/getProcessName";



    public static void getProcess(HttpConnect.HttpCallback collback){
      HttpConnect.get(host, new HttpConnect.HttpCallback() {
          @Override
          public void onSuccess(int code, String repsonse) {
              collback.onSuccess(code,repsonse.toString());
          }

          @Override
          public void onError(String error) {

          }
      });
    }




}
