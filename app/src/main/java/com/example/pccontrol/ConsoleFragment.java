package com.example.pccontrol;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.pccontrol.Core.APIConnect;
import com.example.pccontrol.Core.HttpConnect;
import com.example.pccontrol.Systems.CommandManager;
import com.example.pccontrol.Systems.ConsoleManager;
import com.example.pccontrol.Systems.LogcatReader;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsoleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsoleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private boolean messageIsStarted = true;

    private ScrollView scrollView;
    private TextView consoleView;

    private EditText consolePlain;
    private Button sendBut;

    private boolean autoScroll = false;

    private String start_mes = "Windows Console for ssh and more" + "\n" +
            "Use only secure connection";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConsoleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsoleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsoleFragment newInstance(String param1, String param2) {
        ConsoleFragment fragment = new ConsoleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    private void init(View view) {
        scrollView = view.findViewById(R.id.scrollViewConsole);
        consoleView = view.findViewById(R.id.tvConsole);
        consolePlain = view.findViewById(R.id.consolePlain);
        sendBut = view.findViewById(R.id.sendButton);
    }


    private void addStartingMessage() {
        if (messageIsStarted) {
            ConsoleManager.add(start_mes, Color.GREEN);
            messageIsStarted = false;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Отписываемся, чтобы избежать утечек и вызовов после уничтожения
        ConsoleManager.setOnUpdateCallback(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_console, container, false);
        init(view);
        addStartingMessage();
        logcat();
        APIConnect.getProcess();

        ConsoleManager.setOnUpdateCallback(() -> {
            // ✅ Проверяем, что фрагмент все еще прикреплен к Activity
            if (isAdded() && getActivity() != null && !isDetached()) {
                requireActivity().runOnUiThread(this::refreshConsole);
            }
        });


        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            if (!scrollView.canScrollVertically(1)) {
                autoScroll = true;
            } else {
                autoScroll = false;
            }
        });


        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String line = String.valueOf(consolePlain.getText());
                CommandManager.commandEntry(line);
                consolePlain.setText("");
            }
        });

        refreshConsole();

        // Inflate the layout for this fragment
        return view;


    }

    private void logcat() {
        new LogcatReader().startingRead();
    }


    public void sendOnConsole(String message) {
        sendOnConsole(message, Color.WHITE);
    }


    public void refreshConsole() {
        Map<String, Integer> messages = ConsoleManager.getMessages();

        SpannableStringBuilder builder = new SpannableStringBuilder();

        for (Map.Entry<String, Integer> entry : messages.entrySet()) {
            String message = entry.getKey();
            int color = entry.getValue();
            SpannableString coloredMessage = new SpannableString(message);
            coloredMessage.setSpan(new ForegroundColorSpan(color), 0, message.length(), 0);
            builder.append(coloredMessage);
        }
        consoleView.setText(builder);

        if (autoScroll) {
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
        }

    }

    public void sendOnConsole(String message, int color) {


        SpannableStringBuilder builder = new SpannableStringBuilder();


 /*       if (message.contains("ERROR") || message.contains("Ошибка") || message.contains("Exception") || message.contains("EXCEPTION") || message.contains("FATAL") || message.contains("fatal")) {
            color = Color.RED;
        } else if (message.contains("WARNING") || message.contains("Внимание") || message.contains("DEBUG") || message.contains("debug")) {
            color = Color.YELLOW;
        } else if (message.contains("SUCCESS") || message.contains("Успешно")) {
            color = Color.GREEN;

        }else if (color == Color.WHITE){
            color = Color.WHITE;
        }*/
        final int finalColor = color;

        requireActivity().runOnUiThread(() -> {
            if (!(scrollView == null && consoleView == null)) {
                SpannableString coloredMessage = new SpannableString(message + "\n");
                coloredMessage.setSpan(new ForegroundColorSpan(finalColor), 0, coloredMessage.length(), 0);
                builder.append(coloredMessage);
                consoleView.append(builder);

                if (autoScroll == true) {

                    scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
                }


            }
        });

    }
}