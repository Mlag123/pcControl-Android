package com.example.pccontrol.Systems;

import android.graphics.Color;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import kotlin.sequences.ConstrainedOnceSequence;

public class CommandManager {


    private static boolean logcat = false;

    private static boolean isRootMode = false;

    private static Process rootProcess = null;
    private static DataOutputStream rootOutputStream = null;
    private static BufferedReader rootInputStream = null;

    private static String currentUser = "u0_a10249";  // дефолт
    private static String currentHost = "localhost";
    private static String currentPath = "~";


    public static void commandEntry(String command) {

        if (!isRootMode) {

            switch (command) {

                case "clear":
                    ConsoleManager.clear();
                    break;
                case "logcat":
                    turnOnLogCat();
                    break;
                case "version":
                    ConsoleManager.add("PcControl version 0.0.1 Alpha");
                    break;
                case "marko":
                    ConsoleManager.add("Polo!");
                    break;
                case "su":
                    turnOnSuperUserMode();
                    break;
                default:
                    ConsoleManager.add("Command '" + command + "' is not found", Color.RED);
                    break;


            }


        } else {
            if (command.equals("exit")) {
                isRootMode = false;
                closeRootSession();
            } else if (command.equals("clear")) {
                ConsoleManager.clear();

            } else {
                //  String promt = getPrompt();

                runRootCommandInSession(command);


            }
        }

    }


    private static void turnOnSuperUserMode() {
        isRootMode = !isRootMode;
        startRootSession();
    }

    private static void turnOnLogCat() {
        logcat = !logcat;
        if (logcat) {
            ConsoleManager.add("Logcat in enabled", Color.GREEN);
        } else {
            ConsoleManager.add("Logcat in disabled", Color.GREEN);

        }
    }


    @Deprecated
    public static void commandExecutable(String command) {

        if (isRootMode == false) {
            if (command.contains("clear")) {
                ConsoleManager.clear();
            } else if (command.contains("logcat")) {
                logcat = !logcat;
                if (logcat) {
                    ConsoleManager.add("Logcat in enabled", Color.GREEN);
                } else {
                    ConsoleManager.add("Logcat in disabled", Color.GREEN);

                }
            } else if (command.contains("version")) {
                ConsoleManager.add("PcControl version 0.0.1 Alpha");
            } else if (command.contains("marko")) {
                ConsoleManager.add("Polo");
            } else if (command.contains("furry")) {
                ConsoleManager.add("Meow!", Color.MAGENTA);
            } else if (command.equals("su")) {
                isRootMode = !isRootMode;
                startRootSession();

            } else {


                ConsoleManager.add("Command '" + command + "' is not found", Color.RED);
            }
        } else if (isRootMode) {
            if (command.contains("exit")) {
                isRootMode = false;
                closeRootSession();
            } else if (command.contains("clear")) {
                ConsoleManager.clear();

            } else {
                String promt = getPrompt();

                runRootCommandInSession(command);


            }


        }
    }


    private static void getSystemInfo() {
        try {
            // Получаем имя хоста
            Process hostnameProcess = Runtime.getRuntime().exec(new String[]{"getprop", "net.hostname"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(hostnameProcess.getInputStream()));
            String host = reader.readLine();
            if (host != null && !host.isEmpty()) {
                currentHost = host;
            } else {
                // Альтернатива через shell
                hostnameProcess = Runtime.getRuntime().exec(new String[]{"sh", "-c", "echo $HOSTNAME"});
                reader = new BufferedReader(new InputStreamReader(hostnameProcess.getInputStream()));
                host = reader.readLine();
                if (host != null && !host.isEmpty()) {
                    currentHost = host;
                }
            }

            // Если не удалось определить, пробуем через uname
            if (currentHost.equals("localhost")) {
                Process unameProcess = Runtime.getRuntime().exec(new String[]{"uname", "-n"});
                reader = new BufferedReader(new InputStreamReader(unameProcess.getInputStream()));
                String uname = reader.readLine();
                if (uname != null && !uname.isEmpty()) {
                    currentHost = uname;
                }
            }

            reader.close();

        } catch (Exception e) {
            currentHost = "android";
        }
    }

    private static void startRootSession() {
        try {
            if (rootProcess == null || !rootProcess.isAlive()) {
                rootProcess = Runtime.getRuntime().exec("su");
                rootOutputStream = new DataOutputStream(rootProcess.getOutputStream());
                rootInputStream = new BufferedReader(new InputStreamReader(rootProcess.getInputStream()));

                getSystemInfo();

                // Получаем текущего пользователя (root или кто)
                rootOutputStream.writeBytes("whoami\n");
                rootOutputStream.flush();
                Thread.sleep(100);
                while (rootInputStream.ready()) {
                    String line = rootInputStream.readLine();
                    if (line != null && !line.isEmpty() && !line.contains("whoami")) {
                        currentUser = line.trim();
                        break;
                    }
                }
                updateCurrentPath();
                ConsoleManager.add("🔓 Root session started", Color.GREEN);
            }
        } catch (Exception e) {
            ConsoleManager.add("Failed to start root session: " + e.getMessage(), Color.RED);
        }
    }

    private static void updateCurrentPath() {
        try {
            if (rootOutputStream != null && rootInputStream != null) {
                rootOutputStream.writeBytes("pwd\n");
                rootOutputStream.flush();
                Thread.sleep(100);

                while (rootInputStream.ready()) {
                    String line = rootInputStream.readLine();
                    if (line != null && !line.isEmpty() && !line.contains("pwd")) {
                        currentPath = line.trim();

                        // Заменяем /data/data/... на ~ для home директории
                        if (currentPath.startsWith("/data/data/com.example.pccontrol")) {
                            currentPath = "~";
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // ignore
        }
    }

    private static void runRootCommandInSession(String command) {
        try {
            if (rootProcess == null || !rootProcess.isAlive()) {
                return;
            }

            ConsoleManager.addNonDate(getPrompt() + command, Color.WHITE);

            rootOutputStream.writeBytes(command + "\n");
            rootOutputStream.flush();


            Thread.sleep(120);

            boolean hasOutput = false;
            while (rootInputStream.ready()) {
                String line = rootInputStream.readLine();
                if (line != null && !line.isEmpty()) {
                    ConsoleManager.addNonDate(line, Color.CYAN);
                    hasOutput = true;
                }
            }

            if (command.trim().startsWith("cd")) {
                updateCurrentPath();
            }
            if (!hasOutput) {
                if (!command.trim().startsWith("cd")) {
                    ConsoleManager.addNonDate("[Command executed]", Color.GRAY);
                }
            }
        } catch (Exception e) {
            ConsoleManager.add("Root command error: " + e.getMessage(), Color.RED);
        }
    }

    public static String getPrompt() {
        if (isRootMode) {
            // Для root: root@device:/path#
            return currentUser + "@" + currentHost + ":" + currentPath + "# ";
        } else {
            // Для обычного режима
            return "$ ";
        }
    }

    private static void closeRootSession() {
        try {
            if (rootOutputStream != null) {
                rootOutputStream.writeBytes("exit\n");
                rootOutputStream.flush();
                rootOutputStream.close();
            }
            if (rootInputStream != null) {
                rootInputStream.close();
            }
            if (rootProcess != null) {
                rootProcess.destroy();
            }
        } catch (Exception e) {
            // ignore
        } finally {
            rootProcess = null;
            rootOutputStream = null;
            rootInputStream = null;
        }
        ConsoleManager.add("🔒 Root session closed", Color.YELLOW);
    }

    @Deprecated
    private static boolean runRootCommand(String command) {
        try {
            // Используем su -c для выполнения одной команды
            Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", command});

            // Читаем вывод в отдельном потоке, чтобы не блокировать
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Ждем завершения команды
            int exitCode = process.waitFor();

            // Выводим результат
            if (output.length() > 0) {
                ConsoleManager.add(output.toString(), Color.CYAN);
            } else {
                ConsoleManager.add("[Команда выполнена, вывод отсутствует]", Color.GRAY);
            }

            reader.close();
            return exitCode == 0;

        } catch (Exception e) {
            ConsoleManager.add("Root error: " + e.getMessage(), Color.RED);
            return false;
        }
    }

    public static boolean isLogcat() {
        return logcat;
    }
}
