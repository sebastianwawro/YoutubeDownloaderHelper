package com.ydh;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProcessHelper {

    public static String execute(String command) {
        try {
            Process p = Runtime.getRuntime().exec(command);

            //if (command.startsWith("ffmpeg")) return "HOTFIX";

            StringBuilder log = new StringBuilder();

            try(BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;

                while ((line = input.readLine()) != null) {
                    System.out.println(line);
                    log.append(line+"\n");
                }
            }
            //FileManager.writeFile("output.log", log.toString());
            return log.toString();
        } catch (Exception err) {
            err.printStackTrace();
            return err.toString();
        }
    }

    public synchronized String upgradeServer() {
        try {
            Process p = Runtime.getRuntime().exec("C:\\Program Files\\7-Zip\\7z x -aoa -o\"C:\\inetpub\\wwwroot\" C:\\inetpub\\wwwroot\\wwwroot\\OTA\\current.ota");

            StringBuilder log = new StringBuilder();

            try(BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;

                while ((line = input.readLine()) != null) {
                    System.out.println(line);
                    log.append(line+"<br>");
                }
            }
            FileManager.writeFile("output.log", log.toString().replaceAll("<br>", "\n"));
            return log.toString();
        } catch (Exception err) {
            err.printStackTrace();
            return err.toString();
        }
    }

    public synchronized void upgradeServerAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process p = Runtime.getRuntime().exec("C:\\Program Files\\7-Zip\\7z x -aoa -o\"C:\\inetpub\\wwwroot\" C:\\inetpub\\wwwroot\\wwwroot\\OTA\\current.ota");

                    StringBuilder log = new StringBuilder();

                    try(BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                        String line;

                        while ((line = input.readLine()) != null) {
                            System.out.println(line);
                            log.append(line+"<br>");
                        }
                    }

                    FileManager.writeFile("output.log", log.toString());

                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        }).start();
    }
}
