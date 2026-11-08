
package fidecompro.interfaz;

import fidecompro.modelo.Categoria;
import fidecompro.modelo.Producto;
import fidecompro.servicios.ProductoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Productos extends JFrame {

    private ProductoService productoService;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JTextField campoBuscar;

    private final Color FONDO = new Color(13, 17, 28);
    private final Color TARJETA = new Color(24, 31, 46);
    private final Color AZUL = new Color(35, 115, 245);
    private final Color TEXTO = new Color(240, 244, 255);
    private final Color GRIS = new Color(150, 160, 180);

    public Productos(ProductoService productoService) {

        this.productoService = productoService;

        setTitle("Fidecompro - Productos");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800, 500));

        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout());

        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(FONDO);
        panelSuperior.setBorder(
                new EmptyBorder(25, 30, 20, 30)
        );

        JLabel titulo = new JLabel("Productos");
        titulo.setForeground(TEXTO);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));

        JLabel descripcion = new JLabel(
                "Administra los productos y controla el inventario."
        );
        descripcion.setForeground(GRIS);
        descripcion.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setBackground(FONDO);

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(8));
        textos.add(descripcion);

        JButton botonNuevo = new JButton("+ Nuevo producto");
        estilizarBoton(botonNuevo, AZUL);

        panelSuperior.add(textos, BorderLayout.WEST);
        panelSuperior.add(botonNuevo, BorderLayout.EAST);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel central
        JPanel panelCentral = new JPanel(new BorderLayout(10, 18));
        panelCentral.setBackground(FONDO);
        panelCentral.setBorder(
                new EmptyBorder(10, 30, 25, 30)
        );

        // Buscador
        JPanel panelBusqueda = new JPanel(new BorderLayout());
        panelBusqueda.setBackground(TARJETA);
        panelBusqueda.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(43, 57, 80)
                        ),
                        new EmptyBorder(5, 12, 5, 12)
                )
        );

        JLabel iconoBuscar = new JLabel("⌕  ");
        iconoBuscar.setForeground(AZUL);
        iconoBuscar.setFont(new Font("SansSerif", Font.BOLD, 23));

        campoBuscar = new JTextField();
        campoBuscar.setBackground(TARJETA);
        campoBuscar.setForeground(TEXTO);
        campoBuscar.setCaretColor(TEXTO);
        campoBuscar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campoBuscar.setBorder(null);
        campoBuscar.setPreferredSize(new Dimension(300, 38));

        panelBusqueda.add(iconoBuscar, BorderLayout.WEST);
        panelBusqueda.add(campoBuscar, BorderLayout.CENTER);

        panelCentral.add(panelBusqueda, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(
                new Object[]{
                    "Código", "Nombre", "Precio", "Stock"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(38);
        tabla.setBackground(TARJETA);
        tabla.setForeground(TEXTO);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setSelectionBackground(new Color(35, 80, 145));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(new Color(43, 57, 80));
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setFillsViewportHeight(true);

        JTableHeader encabezado = tabla.getTableHeader();
        encabezado.setBackground(new Color(17, 23, 36));
        encabezado.setForeground(TEXTO);
        encabezado.setFont(new Font("SansSerif", Font.BOLD, 13));
        encabezado.setPreferredSize(new Dimension(0, 40));
        encabezado.setBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 1, 0, new Color(43, 57, 80)
                )
        );

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(43, 57, 80)
                )
        );
        scroll.getViewport().setBackground(TARJETA);

        panelCentral.add(scroll, BorderLayout.CENTER);

        // Botones inferiores
        JPanel panelBotones = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 12, 5)
        );
        panelBotones.setBackground(FONDO);

        JButton botonEditar = new JButton("Editar");
        JButton botonEliminar = new JButton("Eliminar");

        estilizarBoton(botonEditar, new Color(35, 115, 245));
        estilizarBoton(botonEliminar, new Color(190, 55, 65));

        panelBotones.add(botonEditar);
        panelBotones.add(botonEliminar);

        panelCentral.add(panelBotones, BorderLayout.SOUTH);

        add(panelCentral, BorderLayout.CENTER);

        // Eventos
        botonNuevo.addActionListener(e -> nuevoProducto());
        botonEditar.addActionListener(e -> editarProducto());
        botonEliminar.addActionListener(e -> eliminarProducto());

        campoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                buscarProducto();
            }
        });

        actualizarTabla();
    }

    private void estilizarBoton(JButton boton, Color color) {

        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(new EmptyBorder(12, 18, 12, 18));
    }

    private void actualizarTabla() {

        modeloTabla.setRowCount(0);

        ArrayList<Producto> productos =
                productoService.listarProductos();

        for (Producto producto : productos) {

            modeloTabla.addRow(new Object[]{
                producto.getCodigo(),
                producto.getNombre(),
                "₡" + producto.getPrecio(),
                producto.getStock()
            });
        }
    }

    private void nuevoProducto() {

        JTextField codigo = new JTextField();
        JTextField nombre = new JTextField();
        JTextField precio = new JTextField();
        JTextField stock = new JTextField();

        Object[] campos = {
            "Código:", codigo,
            "Nombre:", nombre,
            "Precio:", precio,
            "Stock:", stock
        };

        int resultado = JOptionPane.showConfirmDialog(
                this,
                campos,
                "Registrar producto",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultado == JOptionPane.OK_OPTION) {

            try {

                double precioProducto =
                        Double.parseDouble(precio.getText());

                int stockProducto =
                        Integer.parseInt(stock.getText());

                Categoria categoria =
                        new Categoria(1, "General");

                productoService.registrarProducto(
                        codigo.getText(),
                        nombre.getText(),
                        precioProducto,
                        stockProducto,
                        categoria
                );

                actualizarTabla();

                JOptionPane.showMessageDialog(
                        this,
                        "Producto registrado correctamente."
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "El precio y el stock deben ser números."
                );

            } catch (IllegalArgumentException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }
        }
    }

    private void editarProducto() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un producto."
            );
            return;
        }

        String codigo = modeloTabla.getValueAt(fila, 0).toString();

        Producto producto =
                productoService.buscarPorCodigo(codigo);

        String precio = JOptionPane.showInputDialog(
                this,
                "Nuevo precio:",
                producto.getPrecio()
        );

        if (precio == null) return;

        String stock = JOptionPane.showInputDialog(
                this,
                "Nuevo stock:",
                producto.getStock()
        );

        if (stock == null) return;

        try {

            productoService.editarProducto(
                    codigo,
                    Double.parseDouble(precio),
                    Integer.parseInt(stock)
            );

            actualizarTabla();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese valores numéricos válidos."
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    private void eliminarProducto() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un producto."
            );
            return;
        }

        String codigo = modeloTabla.getValueAt(fila, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            productoService.eliminarProducto(codigo);

            actualizarTabla();
        }
    }

    private void buscarProducto() {

        String busqueda =
                campoBuscar.getText().toLowerCase();

        modeloTabla.setRowCount(0);

        for (Producto producto : productoService.listarProductos()) {

            if (producto.getNombre().toLowerCase().contains(busqueda)
                    || producto.getCodigo().toLowerCase().contains(busqueda)) {

                modeloTabla.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    "₡" + producto.getPrecio(),
                    producto.getStock()
                });
            }
        }
    }
}
