package com.almacen.vista;

import com.almacen.dao.ProductoDAO;
import com.almacen.modelo.Producto;
import com.almacen.util.Styles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Ventana emergente del producto: formulario para registrar un producto
 * nuevo o editar uno existente, con botones Guardar (💾) y Eliminar (❌),
 * tal como en el mockup del enunciado.
 */
public class ProductoFormDialog extends JDialog {

    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtMarca = new JTextField(20);
    private final JTextField txtCategoria = new JTextField(20);
    private final JTextField txtPrecio = new JTextField(20);
    private final JTextField txtCantidad = new JTextField(20);

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final Producto existente; // null si es un producto nuevo
    private boolean huboCambios = false;

    public ProductoFormDialog(Frame padre, Producto existente) {
        super(padre, existente == null ? "Nuevo Producto" : "Editar Producto", true);
        this.existente = existente;
        getContentPane().setBackground(Styles.FONDO_TARJETA);
        construirUI();
        if (existente != null) {
            precargarDatos();
        }
        setSize(400, 430);
        setLocationRelativeTo(padre);
        setResizable(false);
    }

    private void construirUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Styles.FONDO_TARJETA);
        panel.setBorder(new EmptyBorder(20, 22, 20, 22));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = Styles.titulo(existente == null ? "Nuevo Producto" : "Editar Producto");
        lblTitulo.setFont(Styles.SUBTITULO);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 6, 14, 6);
        panel.add(lblTitulo, gbc);

        gbc.insets = new Insets(6, 6, 6, 6);
        addCampo(panel, gbc, 1, "Nombre:", txtNombre);
        addCampo(panel, gbc, 2, "Marca:", txtMarca);
        addCampo(panel, gbc, 3, "Categoría:", txtCategoria);
        addCampo(panel, gbc, 4, "Precio:", txtPrecio);
        addCampo(panel, gbc, 5, "Cantidad Disponible:", txtCantidad);

        JButton btnGuardar = Styles.botonPrimario("\uD83D\uDCBE  Guardar"); // 💾
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setOpaque(false);
        panelBotones.add(btnGuardar);

        if (existente != null) {
            JButton btnEliminar = Styles.botonPeligro("\u274C  Eliminar"); // ❌
            panelBotones.add(btnEliminar);
            btnEliminar.addActionListener(e -> eliminar());
        }

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.insets = new Insets(14, 6, 0, 6);
        panel.add(panelBotones, gbc);

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
        Dimension tamCampo = new Dimension(200, 32);
        campo.setPreferredSize(tamCampo);
        campo.setMinimumSize(tamCampo);
        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    private void precargarDatos() {
        txtNombre.setText(existente.getNombre());
        txtMarca.setText(existente.getMarca());
        txtCategoria.setText(existente.getCategoria());
        txtPrecio.setText(String.valueOf(existente.getPrecio()));
        txtCantidad.setText(String.valueOf(existente.getCantidadDisponible()));
    }

    private void guardar() {
        double precio;
        int cantidad;
        try {
            precio = Double.parseDouble(txtPrecio.getText().trim());
        } catch (NumberFormatException ex) {
            avisar("El precio debe ser un número válido.");
            return;
        }
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException ex) {
            avisar("La cantidad disponible debe ser un número entero.");
            return;
        }

        Producto p = new Producto(txtNombre.getText().trim(), txtMarca.getText().trim(),
                txtCategoria.getText().trim(), precio, cantidad);

        String error = p.validar(); // POLIMORFISMO: Producto.validar()
        if (error != null) {
            avisar(error);
            return;
        }

        boolean exito;
        if (existente == null) {
            exito = productoDAO.insertar(p);
        } else {
            p.setIdProducto(existente.getIdProducto());
            exito = productoDAO.actualizar(p);
        }

        if (exito) {
            huboCambios = true;
            dispose(); // al guardar se cierra la ventana y se actualiza el listado
        } else {
            avisar("Ocurrió un error al guardar el producto.");
        }
    }

    private void eliminar() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar \"" + existente.getNombre() + "\"?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) return;

        if (productoDAO.eliminar(existente.getIdProducto())) {
            huboCambios = true;
            dispose(); // al eliminar se cierra la ventana y se actualiza el listado
        } else {
            avisar("Ocurrió un error al eliminar el producto.");
        }
    }

    private void avisar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean huboCambios() {
        return huboCambios;
    }
}
