package com.almacen.vista;

import com.almacen.dao.UsuarioDAO;
import com.almacen.modelo.Usuario;
import com.almacen.util.Styles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Pantalla de registro de un nuevo usuario. Todos los campos son
 * obligatorios; valida coincidencia de contraseña y campos vacíos.
 */
public class RegistroFrame extends JFrame {

    private final JTextField txtNombre = new JTextField(24);
    private final JTextField txtApellido = new JTextField(24);
    private final JTextField txtUserName = new JTextField(24);
    private final JTextField txtTelefono = new JTextField(24);
    private final JTextField txtEmail = new JTextField(24);
    private final JPasswordField txtPassword = new JPasswordField(24);
    private final JPasswordField txtConfirmar = new JPasswordField(24);

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final JFrame padre;

    public RegistroFrame(JFrame padre) {
        super("Registro de Usuario");
        this.padre = padre;
        getContentPane().setBackground(Styles.FONDO);
        construirUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(540, 580);
        setLocationRelativeTo(padre);
        setResizable(false);
    }

    private void construirUI() {
        JPanel contenedor = new JPanel(new GridBagLayout());
        contenedor.setBackground(Styles.FONDO);

        JPanel tarjeta = new JPanel(new GridBagLayout());
        tarjeta.setBackground(Styles.FONDO_TARJETA);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Styles.BORDE, 1, true),
                new EmptyBorder(26, 30, 26, 30)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = Styles.titulo("Crear Cuenta");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 16, 5);
        tarjeta.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        addCampo(tarjeta, gbc, 1, "Nombre", txtNombre);
        addCampo(tarjeta, gbc, 2, "Apellido", txtApellido);
        addCampo(tarjeta, gbc, 3, "Nombre de Usuario", txtUserName);
        addCampo(tarjeta, gbc, 4, "Teléfono", txtTelefono);
        addCampo(tarjeta, gbc, 5, "Correo Electrónico", txtEmail);
        addCampo(tarjeta, gbc, 6, "Contraseña", txtPassword);
        addCampo(tarjeta, gbc, 7, "Confirmar Contraseña", txtConfirmar);

        JButton btnRegistrar = Styles.botonPrimario("Registrar");
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 5, 5, 5);
        tarjeta.add(btnRegistrar, gbc);

        contenedor.add(tarjeta);
        add(contenedor);
        btnRegistrar.addActionListener(e -> registrar());
        getRootPane().setDefaultButton(btnRegistrar);
    }

    private void addCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(Styles.TEXTO_NEGRITA);
        gbc.gridy = fila; gbc.gridx = 0; gbc.weightx = 0;
        panel.add(lbl, gbc);

        campo.setFont(Styles.TEXTO_NORMAL);
        Dimension tamCampo = new Dimension(230, 32);
        campo.setPreferredSize(tamCampo);
        campo.setMinimumSize(tamCampo);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(campo, gbc);
    }

    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String userName = txtUserName.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String pass = new String(txtPassword.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        // Validación de campo faltante individual, según el mandato
        if (nombre.isEmpty()) { avisar("El campo Nombre es obligatorio."); return; }
        if (apellido.isEmpty()) { avisar("El campo Apellido es obligatorio."); return; }
        if (userName.isEmpty()) { avisar("El campo Nombre de Usuario es obligatorio."); return; }
        if (telefono.isEmpty()) { avisar("El campo Teléfono es obligatorio."); return; }
        if (email.isEmpty()) { avisar("El campo Correo Electrónico es obligatorio."); return; }
        if (pass.isEmpty()) { avisar("El campo Contraseña es obligatorio."); return; }
        if (confirmar.isEmpty()) { avisar("Debe confirmar la contraseña."); return; }

        if (!pass.equals(confirmar)) {
            avisar("La contraseña y su confirmación no coinciden.");
            return;
        }

        if (usuarioDAO.buscarPorUserName(userName) != null) {
            avisar("Ese nombre de usuario ya está registrado.");
            return;
        }

        Usuario u = new Usuario(userName, nombre, apellido, telefono, email, pass);
        String error = u.validar(); // POLIMORFISMO: usa Usuario.validar()
        if (error != null) {
            avisar(error);
            return;
        }

        if (usuarioDAO.insertar(u)) {
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.");
            dispose();
        } else {
            avisar("Ocurrió un error al registrar el usuario. Intente de nuevo.");
        }
    }

    private void avisar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
