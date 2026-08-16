package com.almacen.vista;

import com.almacen.dao.UsuarioDAO;
import com.almacen.modelo.Usuario;
import com.almacen.util.Styles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Pantalla de inicio de sesión. Al autenticar correctamente, cierra esta
 * ventana y abre PrincipalFrame.
 */
public class LoginFrame extends JFrame {

    private final JTextField txtUsuario = new JTextField(22);
    private final JPasswordField txtPassword = new JPasswordField(22);
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public LoginFrame() {
        super("Sistema de Almacén - Login");
        getContentPane().setBackground(Styles.FONDO);
        construirUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 460);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void construirUI() {
        JPanel contenedor = new JPanel(new GridBagLayout());
        contenedor.setBackground(Styles.FONDO);
        GridBagConstraints raiz = new GridBagConstraints();

        // Tarjeta blanca centrada con sombra sutil (borde + padding)
        JPanel tarjeta = new JPanel(new GridBagLayout());
        tarjeta.setBackground(Styles.FONDO_TARJETA);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Styles.BORDE, 1, true),
                new EmptyBorder(30, 34, 30, 34)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 6, 7, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblIcono = new JLabel("\uD83D\uDCE6", SwingConstants.CENTER); // 📦
        lblIcono.setFont(new Font(Styles.FAMILIA, Font.PLAIN, 40));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        tarjeta.add(lblIcono, gbc);

        JLabel lblTitulo = Styles.titulo("Bienvenido");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        tarjeta.add(lblTitulo, gbc);

        JLabel lblSub = new JLabel("Sistema de Gestión de Almacén", SwingConstants.CENTER);
        lblSub.setFont(Styles.TEXTO_NORMAL);
        lblSub.setForeground(Styles.TEXTO_SUAVE);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 6, 20, 6);
        tarjeta.add(lblSub, gbc);

        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.gridwidth = 1;

        JLabel lblUsuario = new JLabel("Nombre de usuario");
        lblUsuario.setFont(Styles.TEXTO_NEGRITA);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weightx = 1.0;
        tarjeta.add(lblUsuario, gbc);

        gbc.gridy = 4;
        txtUsuario.setFont(Styles.TEXTO_NORMAL);
        txtUsuario.setPreferredSize(new Dimension(260, 34));
        txtUsuario.setMinimumSize(new Dimension(260, 34));
        tarjeta.add(txtUsuario, gbc);

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(Styles.TEXTO_NEGRITA);
        gbc.gridy = 5;
        gbc.insets = new Insets(12, 6, 6, 6);
        tarjeta.add(lblPass, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(6, 6, 6, 6);
        txtPassword.setFont(Styles.TEXTO_NORMAL);
        txtPassword.setPreferredSize(new Dimension(260, 34));
        txtPassword.setMinimumSize(new Dimension(260, 34));
        tarjeta.add(txtPassword, gbc);

        JButton btnEntrar = Styles.botonPrimario("Entrar");
        gbc.gridy = 7;
        gbc.insets = new Insets(18, 6, 8, 6);
        tarjeta.add(btnEntrar, gbc);

        JButton btnRegistrarse = new JButton("¿No tienes cuenta? Regístrate");
        btnRegistrarse.setBorderPainted(false);
        btnRegistrarse.setContentAreaFilled(false);
        btnRegistrarse.setFocusPainted(false);
        btnRegistrarse.setForeground(Styles.PRIMARIO);
        btnRegistrarse.setFont(Styles.TEXTO_NORMAL);
        btnRegistrarse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridy = 8;
        gbc.insets = new Insets(2, 6, 6, 6);
        tarjeta.add(btnRegistrarse, gbc);

        contenedor.add(tarjeta, raiz);
        add(contenedor);

        btnEntrar.addActionListener(e -> intentarLogin());
        btnRegistrarse.addActionListener(e -> new RegistroFrame(this).setVisible(true));
        getRootPane().setDefaultButton(btnEntrar);
    }

    private void intentarLogin() {
        String user = txtUsuario.getText().trim();
        String pass = new String(txtPassword.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar su usuario y contraseña, si no está registrado debe registrarse.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario u = usuarioDAO.buscarPorUserName(user);
        if (u == null || !u.getPassword().equals(pass)) {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos, si no está registrado debe registrarse.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.dispose();
        SwingUtilities.invokeLater(() -> new PrincipalFrame(u).setVisible(true));
    }
}
