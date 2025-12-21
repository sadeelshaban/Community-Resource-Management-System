package Stage2;

import Stage1.AidManagement;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AidManagement manager = new AidManagement();
            new WelcomeScreen(manager);
        });
    }
}
