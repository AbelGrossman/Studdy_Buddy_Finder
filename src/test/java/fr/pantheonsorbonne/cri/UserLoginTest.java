package fr.pantheonsorbonne.cri;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserLoginTest {

    @Test
    public void testLogin() {
        // Test de la connexion avec des identifiants corrects
        assertTrue(UserLogin.login("Abel31", "abel123"));

        // Test de la connexion avec identifiant correct et mot de passe incorrect
        assertFalse(UserLogin.login("Abel31", "wrongpassword"));

        // Test de la connexion avec identifiant incorrect et mot de passe correct
        assertFalse(UserLogin.login("johndoe", "abel123"));

        // Test de la connexion avec des identifiants incorrects
        assertFalse(UserLogin.login("wrongusername", "password"));
    }
}

