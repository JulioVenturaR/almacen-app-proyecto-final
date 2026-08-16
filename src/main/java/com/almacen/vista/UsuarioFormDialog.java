package com.almacen.vista;

import com.almacen.dao.UsuarioDAO;
import com.almacen.modelo.Usuario;
import com.almacen.util.Styles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Formulario emergente para crear o actualizar un usuario desde la
 * pantalla de Gestión de Usuarios.
 */
public class UsuarioFormDialog extends JDialog {

    private final JTextField txtNombre = new JTextField(22);
    private final JTextField txtApellido = new JTextField(22);
    private final JTextField txtUserName = new JTextField(22);
    private final JTextField txtTelefono = new JTextField(22);
    private final JTextField txtEmail = new JTextField(22);
    private final JPasswordField txtPassword = new JPasswordField(22);

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final Usuario existente; // null si es un usuario nuevo
    private boolean guardado = false;

    public UsuarioFormDialog(Frame padre, Usuario existente) {
        super(padre, existente == null ? "Nuevo Usuario" : "Actualizar Usuario", true);
        this.existente = existente;
        getContentPane().setBackground(Styles.FONDO_TARJETA);
        construirUI();
        if (existente != null) {
            precargarDatos();
        }
        setSize(440, 460);
        setLocationRelativeTo(padre);
        setResizable(false);
    }

    private void construirUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Styles.FONDO_TARJETA);
        panel.setBorder(new EmptyBorder(20, 22, 20, 22));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = Styles.titulo(existente == null ? "Nuevo Usuario" : "Editar Usuario");
        lblTitulo.setFont(Styles.SUBTITULO);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 14, 5);
        panel.add(lblTitulo, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);
        addCampo(panel, gbc, 1, "Nombre:", txtNombre);
        addCampo(panel, gbc, 2, "Apellido:", txtApellido);
        addCampo(panel, gbc, 3, "Usuario:", txtUserName);
        addCampo(panel, gbc, 4, "Teléfono:", txtTelefono);
        addCampo(panel, gbc, 5, "Correo:", txtEmail);
        addCampo(panel, gbc, 6, "Contraseña:", txtPassword);

        JButton btnGuardar = Styles.botonPrimario("Guardar");
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 5, 5, 5);
        panel.add(btnGuardar, gbc);

        add(panel);
        btnGuardar.addActionListener(e -> guardar());
        getRootPane().setDefaultButton(btnGuardar);
    }

    private void addCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(Styles.TEXTO_NEGRITA);
        gbc.gridy = fila; gbc.gridx = 0; gbc.gridwidth = 1; gbc.weightx = 0;
        panel.add(lbl, gbc);

        campo.setFont(Styles.TEXTO_NORMAL);
        Dimension tamCampo = new Dimension(220, 32);
        campo.setPreferredSize(tamCampo);
        campo.setMinimumSize(tamCampo);
        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    private void precargarDatos() {
        txtNombre.setText(existente.getNombre());
        txtApellido.setText(existente.getApellido());
        txtUserName.setText(existente.getUserName());
        txtTelefono.setText(existente.getTelefono());
        txtEmail.setText(existente.getEmail());
        // La contraseña no se precarga por seguridad; si se deja vacía, se conserva la actual.
    }

    private void guardar() {
        Usuario u = new Usuario();
        u.setNombre(txtNombre.getText().trim());
        u.setApellido(txtApellido.getText().trim());
        u.setUserName(txtUserName.getText().trim());
        u.setTelefono(txtTelefono.getText().trim());
        u.setEmail(txtEmail.getText().trim());
        String pass = new String(txtPassword.getPassword());

        if (existente != null && pass.isEmpty()) {
            pass = existente.getPassword(); // conservar contraseña actual si no se cambia
        }
        u.setPassword(pass);

        String error = u.validar(); // POLIMORFISMO: Usuario.validar()
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito;
        if (existente == null) {
            exito = usuarioDAO.insertar(u);
        } else {
            u.setIdUser(existente.getIdUser());
            exito = usuarioDAO.actualizar(u);
        }

        if (exito) {
            guardado = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar. Intente de nuevo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isGuardado() {
        return guardado;
    }
}
