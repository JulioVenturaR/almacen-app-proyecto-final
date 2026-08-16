package com.almacen.vista;

import com.almacen.dao.ProductoDAO;
import com.almacen.modelo.Producto;
import com.almacen.util.Styles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Pantalla de gestión de productos: listado de todos los productos
 * registrados, botón Nuevo, click en fila abre el formulario del
 * producto seleccionado (con Guardar / Eliminar), y botón para volver.
 */
public class ProductosFrame extends JFrame {

    private final JFrame padre;
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Marca", "Categoría", "Precio", "Cantidad Disponible"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modelo);

    public ProductosFrame(JFrame padre) {
        super("Productos de Almacén");
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
        JLabel lblTitulo = Styles.titulo("Productos de Almacén");
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
        JButton btnVolver = Styles.botonSecundario("←  Volver");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBotones.setBackground(Styles.FONDO);
        panelBotones.setBorder(new EmptyBorder(6, 12, 14, 12));
        panelBotones.add(btnNuevo);
        panelBotones.add(btnVolver);

        JLabel lblAyuda = new JLabel("  Haz clic en un producto para editarlo");
        lblAyuda.setFont(Styles.TEXTO_NORMAL);
        lblAyuda.setForeground(Styles.TEXTO_SUAVE);
        panelBotones.add(lblAyuda);

        add(encabezado, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnNuevo.addActionListener(e -> abrirFormulario(null));
        btnVolver.addActionListener(e -> {
            dispose();
            padre.setVisible(true);
        });

        // Click en una fila del listado abre el formulario de ese producto
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila == -1) return;
                Producto p = obtenerFila(fila);
                abrirFormulario(p);
            }
        });
    }

    private void cargarDatos() {
        modelo.setRowCount(0);
        List<Producto> productos = productoDAO.listarTodos();
        for (Producto p : productos) {
            modelo.addRow(new Object[]{
                    p.getIdProducto(), p.getNombre(), p.getMarca(),
                    p.getCategoria(), p.getPrecio(), p.getCantidadDisponible()
            });
        }
    }

    private Producto obtenerFila(int fila) {
        Producto p = new Producto();
        p.setIdProducto((int) modelo.getValueAt(fila, 0));
        p.setNombre((String) modelo.getValueAt(fila, 1));
        p.setMarca((String) modelo.getValueAt(fila, 2));
        p.setCategoria((String) modelo.getValueAt(fila, 3));
        p.setPrecio((double) modelo.getValueAt(fila, 4));
        p.setCantidadDisponible((int) modelo.getValueAt(fila, 5));
        return p;
    }

    private void abrirFormulario(Producto existente) {
        ProductoFormDialog dialogo = new ProductoFormDialog(this, existente);
        dialogo.setVisible(true);
        if (dialogo.huboCambios()) {
            cargarDatos(); // los cambios se reflejan automáticamente en el listado
        }
    }
}
