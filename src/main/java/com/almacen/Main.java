package com.almacen;

import com.almacen.util.Styles;
import com.formdev.flatlaf.FlatLightLaf;
import com.almacen.vista.LoginFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Punto de entrada de la aplicación. Configura el look and feel (FlatLaf)
 * con una paleta consistente y lanza la pantalla de Login.
 */
public class Main {
    public static void main(String[] args) {
        aplicarTema();
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    /** Configura FlatLaf y ajustes globales de UI para una apariencia elegante. */
    private static void aplicarTema() {
        try {
            FlatLightLaf.setup();

            // Bordes más redondeados en botones y campos de texto
            UIManager.put("Button.arc", 14);
            UIManager.put("Component.arc", 12);
            UIManager.put("TextComponent.arc", 12);
            UIManager.put("ProgressBar.arc", 12);

            // Paleta de acentos y foco
            UIManager.put("Component.focusColor", Styles.ACENTO);
            UIManager.put("Component.focusedBorderColor", Styles.PRIMARIO);
            UIManager.put("ScrollBar.thumbArc", 999);
            UIManager.put("ScrollBar.width", 12);

            // Fondo general
            UIManager.put("Panel.background", Styles.FONDO);
            UIManager.put("OptionPane.background", Styles.FONDO_TARJETA);

            // Tablas: filas alternadas y encabezado con color propio
            UIManager.put("Table.rowHeight", 28);
            UIManager.put("Table.alternateRowColor", new Color(0xEFF2FB));
            UIManager.put("Table.selectionBackground", Styles.PRIMARIO);
            UIManager.put("Table.selectionForeground", Color.WHITE);
            UIManager.put("TableHeader.background", Styles.PRIMARIO_OSCURO);
            UIManager.put("TableHeader.foreground", Color.WHITE);
            UIManager.put("TableHeader.height", 34);

            // Fuente por defecto de toda la app
            Font base = new Font(Styles.FAMILIA, Font.PLAIN, 13);
            UIManager.put("defaultFont", base);
        } catch (Exception e) {
            // Si algo falla, la app sigue con el look and feel por defecto
            System.err.println("No se pudo aplicar el tema FlatLaf: " + e.getMessage());
        }
    }
}
