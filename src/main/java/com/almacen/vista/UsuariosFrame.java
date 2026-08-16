package com.almacen.vista;

import com.almacen.dao.UsuarioDAO;
import com.almacen.modelo.Usuario;
import com.almacen.util.Styles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Pantalla de gestión de usuarios: listado de todos los usuarios
 * registrados con sus nombres, teléfono y correo, más Nuevo /
 * Actualizar / Eliminar y un botón para volver.
 */
public class UsuariosFrame extends JFrame {

    private final JFrame padre;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Apellido", "Teléfono", "Correo Electrónico", "Usuario"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modelo);

    public UsuariosFrame(JFrame padre) {
        super("Clientes Registrados");
        this.padre = padre;
        getContentPane().setBackground(Styles.FONDO);
        construirUI();
        cargarDatos();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(760, 460);
        setLocationRelativeTo(padre);
    }

    private void construirUI() {
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(Styles.FONDO);
        encabezado.setBorder(new EmptyBorder(18, 20, 10, 20));
        JLabel lblTitulo = Styles.titulo("Clientes Registrados");
        encabezado.add(lblTitulo, BorderLayout.WEST);

        tabla.getColumnModel().getColumn(0).setMinWidth(0);
        tabla.getColumnModel().getColumn(0).setMaxWidth(0);
        tabla.getColumnModel().getColumn(0).setWidth(0);
        tabla.setRowHeight(28);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(Styles.BORDE, 1, true));
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Styles.FONDO);
        panelTabla.setBorder(new EmptyBorder(0, 20, 0, 20));
        panelTabla.add(scroll, BorderLayout.CENTER);

        JButton btnNuevo = Styles.botonPrimario("+ Nuevo");
        JButton btnActualizar = Styles.botonSecundario("Actualizar");
        JButton btnEliminar = Styles.botonPeligro("Eliminar");
        JButton btnVolver = Styles.botonSecundario("←  Volver");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBotones.setBackground(Styles.FONDO);
        panelBotones.setBorder(new EmptyBorder(6, 12, 14, 12));
        panelBotones.add(btnNuevo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        add(encabezado, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnNuevo.addActionListener(e -> abrirFormulario(null));
        btnActualizar.addActionListener(e -> {
            Usuario seleccionado = obtenerSeleccionado();
            if (seleccionado == null) {
                avisar("Seleccione un usuario de la lista para actualizar.");
                return;
            }
            abrirFormulario(seleccionado);
        });
        btnEliminar.addActionListener(e -> eliminarSeleccionado());
        btnVolver.addActionListener(e -> {
            dispose();
            padre.setVisible(true);
        });
    }

    private void cargarDatos() {
        modelo.setRowCount(0);
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        for (Usuario u : usuarios) {
            modelo.addRow(new Object[]{
                    u.getIdUser(), u.getNombre(), u.getApellido(),
                    u.getTelefono(), u.getEmail(), u.getUserName()
            });
        }
    }

    private Usuario obtenerSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return null;
        Usuario u = new Usuario();
        u.setIdUser((int) modelo.getValueAt(fila, 0));
        u.setNombre((String) modelo.getValueAt(fila, 1));
        u.setApellido((String) modelo.getValueAt(fila, 2));
        u.setTelefono((String) modelo.getValueAt(fila, 3));
        u.setEmail((String) modelo.getValueAt(fila, 4));
        u.setUserName((String) modelo.getValueAt(fila, 5));
        return u;
    }

    private void abrirFormulario(Usuario existente) {
        UsuarioFormDialog dialogo = new UsuarioFormDialog(this, existente);
        dialogo.setVisible(true);
        if (dialogo.isGuardado()) {
            cargarDatos(); // los cambios se reflejan automáticamente en el listado
        }
    }

    private void eliminarSeleccionado() {
        Usuario seleccionado = obtenerSeleccionado();
        if (seleccionado == null) {
            avisar("Seleccione un usuario de la lista para eliminar.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar a " + seleccionado.getNombreCompleto() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (usuarioDAO.eliminar(seleccionado.getIdUser())) {
                cargarDatos();
            } else {
                avisar("No se pudo eliminar el usuario.");
            }
        }
    }

    private void avisar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}
