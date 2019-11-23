package com.ydh;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class Config {
    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());
    public static boolean USE_ASYNC_UPGRADE;
    public static boolean VERBOSE_CONSOLE;
    public static boolean VERBOSE_LOGS;
    public static boolean AUTO_CLEAN;

    private Config() {}

    public static void loadConfig () {

        try {
            final Properties Settings = new Properties();
            final InputStream is = new FileInputStream(new File("Config.ini"));
            Settings.load(is);
            is.close();

            USE_ASYNC_UPGRADE = Boolean.valueOf(Settings.getProperty("UseAsyncUpgrade", "false"));
            LOGGER.info("Config: " + "UseAsyncUpgrade" + "=" + USE_ASYNC_UPGRADE);

            VERBOSE_CONSOLE = Boolean.valueOf(Settings.getProperty("VerboseConsole", "true"));
            LOGGER.info("Config: " + "VerboseConsole" + "=" + VERBOSE_CONSOLE);

            VERBOSE_LOGS = Boolean.valueOf(Settings.getProperty("VerboseLogs", "false"));
            LOGGER.info("Config: " + "VerboseLogs" + "=" + VERBOSE_LOGS);

            AUTO_CLEAN = Boolean.valueOf(Settings.getProperty("AutoClean", "false"));
            LOGGER.info("Config: " + "AutoClean" + "=" + AUTO_CLEAN);
        }
        catch (Exception e) {e.printStackTrace();}
    }
}
