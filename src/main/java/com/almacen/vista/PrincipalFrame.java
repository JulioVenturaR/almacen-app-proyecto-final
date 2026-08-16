package com.almacen.vista;

import com.almacen.modelo.Usuario;
import com.almacen.util.Styles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Pantalla principal tras iniciar sesión: dos botones con ícono
 * (Usuarios / Productos) y un botón para cerrar sesión.
 */
public class PrincipalFrame extends JFrame {

    private final Usuario usuarioActivo;

    public PrincipalFrame(Usuario usuarioActivo) {
        super("Sistema de Almacén - Principal");
        this.usuarioActivo = usuarioActivo;
        getContentPane().setBackground(Styles.FONDO);
        construirUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 380);
        setLocationRelativeTo(null);
    }

    private void construirUI() {
        // --- Barra superior con degradado sutil de color primario ---
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Styles.PRIMARIO);
        panelSuperior.setBorder(new EmptyBorder(14, 20, 14, 16));

        JLabel lblBienvenida = new JLabel("Hola, " + usuarioActivo.getNombreCompleto());
        lblBienvenida.setFont(Styles.SUBTITULO);
        lblBienvenida.setForeground(Color.WHITE);

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setFont(Styles.TEXTO_NEGRITA);
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setContentAreaFilled(false);
        btnCerrarSesion.setBorderPainted(false);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelSuperior.add(lblBienvenida, BorderLayout.WEST);
        panelSuperior.add(btnCerrarSesion, BorderLayout.EAST);

        // --- Centro: tarjetas de navegación ---
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 26, 0));
        panelCentro.setBackground(Styles.FONDO);
        panelCentro.setBorder(new EmptyBorder(30, 40, 40, 40));

        JButton btnUsuarios = crearTarjetaNav("\uD83D\uDC64", "Usuarios", "Gestionar cuentas registradas");
        JButton btnProductos = crearTarjetaNav("\uD83D\uDCE6", "Productos", "Gestionar inventario de almacén");

        panelCentro.add(btnUsuarios);
        panelCentro.add(btnProductos);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);

        btnUsuarios.addActionListener(e -> {
            this.setVisible(false);
            new UsuariosFrame(this).setVisible(true);
        });

        btnProductos.addActionListener(e -> {
            this.setVisible(false);
            new ProductosFrame(this).setVisible(true);
        });

        btnCerrarSesion.addActionListener(e -> {
            this.dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        });
    }

    /** Botón de navegación estilo "tarjeta": ícono grande, título y subtítulo. */
    private JButton crearTarjetaNav(String emoji, String titulo, String subtitulo) {
        JButton boton = new JButton();
        boton.setLayout(new GridBagLayout());
        boton.setBackground(Styles.FONDO_TARJETA);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Styles.BORDE, 1, true),
                new EmptyBorder(20, 10, 20, 10)));
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.putClientProperty("JButton.buttonType", "roundRect");

        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

        JLabel lblIcono = new JLabel(emoji);
        lblIcono.setFont(new Font(Styles.FAMILIA, Font.PLAIN, 44));
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(Styles.SUBTITULO);
        lblTitulo.setForeground(Styles.TEXTO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(new EmptyBorder(10, 0, 4, 0));

        JLabel lblSubtitulo = new JLabel(subtitulo);
        lblSubtitulo.setFont(Styles.TEXTO_NORMAL);
        lblSubtitulo.setForeground(Styles.TEXTO_SUAVE);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenido.add(lblIcono);
        contenido.add(lblTitulo);
        contenido.add(lblSubtitulo);

        boton.add(contenido);
        return boton;
    }
}
