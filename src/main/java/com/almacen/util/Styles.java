package com.almacen.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Paleta de colores y fuentes centralizada para mantener una apariencia
 * consistente y elegante en toda la aplicación.
 */
public final class Styles {

    private Styles() {
    }

    // --- Paleta de colores (azul/índigo profesional) ---
    public static final Color PRIMARIO = new Color(0x3B5BDB);
    public static final Color PRIMARIO_OSCURO = new Color(0x2F46A8);
    public static final Color ACENTO = new Color(0x748FFC);
    public static final Color FONDO = new Color(0xF4F6FB);
    public static final Color FONDO_TARJETA = Color.WHITE;
    public static final Color TEXTO = new Color(0x1B1F3B);
    public static final Color TEXTO_SUAVE = new Color(0x6B7280);
    public static final Color PELIGRO = new Color(0xE03131);
    public static final Color EXITO = new Color(0x2F9E44);
    public static final Color BORDE = new Color(0xD9DEEA);

    // --- Fuentes ---
    public static final String FAMILIA = "Segoe UI";
    public static final Font TITULO = new Font(FAMILIA, Font.BOLD, 22);
    public static final Font SUBTITULO = new Font(FAMILIA, Font.BOLD, 15);
    public static final Font TEXTO_NORMAL = new Font(FAMILIA, Font.PLAIN, 13);
    public static final Font TEXTO_NEGRITA = new Font(FAMILIA, Font.BOLD, 13);

    /** Crea un botón con el estilo primario (fondo azul, texto blanco). */
    public static JButton botonPrimario(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(TEXTO_NEGRITA);
        boton.setBackground(PRIMARIO);
        boton.setForeground(Color.WHITE);
        boton.putClientProperty("JButton.buttonType", "roundRect");
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setBorder(new EmptyBorder(8, 18, 8, 18));
        return boton;
    }

    /** Crea un botón de estilo peligro (rojo), usado para eliminar. */
    public static JButton botonPeligro(String texto) {
        JButton boton = botonPrimario(texto);
        boton.setBackground(PELIGRO);
        return boton;
    }

    /** Crea un botón de estilo secundario (borde, sin relleno). */
    public static JButton botonSecundario(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(TEXTO_NEGRITA);
        boton.setForeground(PRIMARIO);
        boton.setBackground(FONDO_TARJETA);
        boton.putClientProperty("JButton.buttonType", "roundRect");
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE, 1, true),
                new EmptyBorder(7, 16, 7, 16)));
        return boton;
    }

    /** Etiqueta de título de pantalla, con color y fuente consistentes. */
    public static JLabel titulo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(TITULO);
        lbl.setForeground(TEXTO);
        return lbl;
    }

    /** Aplica un padding uniforme a un contenedor. */
    public static void aplicarPadding(JComponent componente, int px) {
        componente.setBorder(new EmptyBorder(px, px, px, px));
    }
}
