
package fidecompro;

import fidecompro.interfaz.Login;

public class Main {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
