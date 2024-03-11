package fr.pantheonsorbonne.cri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * The main class for the application.
 */
public final class App {
    private static boolean initialized = false;

    /**
     * main entrypoint for my class.
     * 
     * @param args a bunch of string from the cli
     */
    public static void main(final String[] args) {
        if (!initialized) {
            DataBaseConnection.dataBaseConnect();
            initialized = true;
        }
    }
}
