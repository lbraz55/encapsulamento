package app;

import javax.swing.SwingUtilities;

import controller.Controlador;

public class Main {
    public static void main(String[] args) {
        // Componentes Swing devem ser criados na Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new Controlador().iniciarLoja());
    }
}
