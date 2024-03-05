package fr.pantheonsorbonne.cri;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class ConnexionBDD {
    Connection connection = null;

    public void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8887/MaBDD", "root", "root");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        ConnexionBDD connexionBDD = new ConnexionBDD();
        connexionBDD.connectToDatabase();
        if (connexionBDD.connection != null) {
            System.out.println("Connexion réussie à la base de données !");
        } else {
            System.out.println("Échec de la connexion à la base de données.");
        }
    }
}