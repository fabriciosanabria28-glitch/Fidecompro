
package fidecompro.interfaz;

import fidecompro.modelo.*;
import fidecompro.servicios.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class NuevaFactura extends JFrame {

    private final Color FONDO = new Color(12, 18, 32);
    private final Color PANEL = new Color(20, 29, 48);
    private final Color AZUL = new Color(35, 105, 220);
    private final Color TEXTO = new Color(235, 240, 250);
    private final Color SECUNDARIO = new Color(155, 170, 195);

    private ClienteService clienteService;
    private ProductoService productoService;
    private FacturaService facturaService;
    private Usuario usuario;

    private JComboBox<Cliente> comboClientes;
    private JComboBox<Producto> comboProductos;
    private JTextField campoCantidad;
    private DefaultTableModel modeloTabla;
    private JLabel etiquetaTotal;
    private JLabel etiquetaImpuesto;

    private ArrayList<DetalleFactura> detalles;

    public NuevaFactura(ClienteService clientes,
            ProductoService productos,
            FacturaService facturas,
            Usuario usuario) {

        this.clienteService = clientes;
        this.productoService = productos;
        this.facturaService = facturas;
        this.usuario = usuario;

        detalles = new ArrayList<>();

        setTitle("Fidecompro - Nueva factura");
        setSize(1050, 760);
        setMinimumSize(new Dimension(900, 680));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(FONDO);

        JPanel superior = new JPanel(new BorderLayout());
        superior.setBackground(FONDO);
        superior.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));

        JLabel titulo = new JLabel("Nueva factura");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(TEXTO);

        JLabel subtitulo = new JLabel("Registra una nueva venta y sus productos");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitulo.setForeground(SECUNDARIO);

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 5));
        textos.setBackground(FONDO);
        textos.add(titulo);
        textos.add(subtitulo);

        superior.add(textos, BorderLayout.CENTER);
        add(superior, BorderLayout.NORTH);

        JPanel central = new JPanel(new BorderLayout(15, 15));
        central.setBackground(FONDO);
        central.setBorder(BorderFactory.createEmptyBorder(5, 30, 25, 30));

        JPanel panelCliente = crearPanel();
        panelCliente.setLayout(new BorderLayout(0, 10));
        panelCliente.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(42, 55, 78)),
                BorderFactory.createEmptyBorder(15, 18, 15, 18)
        ));

        JLabel labelCliente = crearEtiqueta("Cliente");
        comboClientes = new JComboBox<>();
        estilizarCombo(comboClientes);

        panelCliente.add(labelCliente, BorderLayout.NORTH);
        panelCliente.add(comboClientes, BorderLayout.CENTER);

        central.add(panelCliente, BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout(0, 15));
        contenido.setBackground(FONDO);

        JPanel panelAgregar = crearPanel();
        panelAgregar.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelAgregar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(42, 55, 78)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JPanel panelProducto = new JPanel(new BorderLayout(0, 7));
        panelProducto.setBackground(PANEL);
        panelProducto.add(crearEtiqueta("Producto"), BorderLayout.NORTH);

        comboProductos = new JComboBox<>();
        estilizarCombo(comboProductos);
        comboProductos.setPreferredSize(new Dimension(330, 38));
        panelProducto.add(comboProductos, BorderLayout.CENTER);

        JPanel panelCantidad = new JPanel(new BorderLayout(0, 7));
        panelCantidad.setBackground(PANEL);
        panelCantidad.add(crearEtiqueta("Cantidad"), BorderLayout.NORTH);

        campoCantidad = new JTextField();
        estilizarCampo(campoCantidad);
        campoCantidad.setPreferredSize(new Dimension(100, 38));
        panelCantidad.add(campoCantidad, BorderLayout.CENTER);

        JButton botonAgregar = crearBoton("＋  Agregar producto");
        botonAgregar.addActionListener(e -> agregarProducto());

        panelAgregar.add(panelProducto);
        panelAgregar.add(panelCantidad);
        panelAgregar.add(botonAgregar);

        modeloTabla = new DefaultTableModel(
                new Object[]{"Producto", "Cantidad", "Precio", "Subtotal"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(38);
        tabla.setFillsViewportHeight(true);
        tabla.setPreferredScrollableViewportSize(new Dimension(800, 150));
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setForeground(TEXTO);
        tabla.setBackground(PANEL);
        tabla.setGridColor(new Color(42, 55, 78));
        tabla.setSelectionBackground(new Color(35, 75, 135));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setShowVerticalLines(false);
        tabla.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader encabezado = tabla.getTableHeader();
        encabezado.setBackground(new Color(27, 39, 62));
        encabezado.setForeground(TEXTO);
        encabezado.setFont(new Font("Arial", Font.BOLD, 13));
        encabezado.setPreferredSize(new Dimension(0, 40));
        encabezado.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(850, 260));
        scroll.setMinimumSize(new Dimension(500, 180));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(42, 55, 78)));
        scroll.getViewport().setBackground(PANEL);

        contenido.add(panelAgregar, BorderLayout.NORTH);
        contenido.add(scroll, BorderLayout.CENTER);

        central.add(contenido, BorderLayout.CENTER);

        JPanel inferior = crearPanel();
        inferior.setLayout(new BorderLayout(10, 10));
        inferior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(42, 55, 78)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JPanel totales = new JPanel(new GridLayout(2, 1, 0, 8));
        totales.setBackground(PANEL);

        etiquetaImpuesto = new JLabel("Impuesto (13%): ₡0.00");
        etiquetaImpuesto.setFont(new Font("Arial", Font.PLAIN, 14));
        etiquetaImpuesto.setForeground(SECUNDARIO);

        etiquetaTotal = new JLabel("Total: ₡0.00");
        etiquetaTotal.setFont(new Font("Arial", Font.BOLD, 22));
        etiquetaTotal.setForeground(TEXTO);

        totales.add(etiquetaImpuesto);
        totales.add(etiquetaTotal);

        JButton botonGenerar = crearBoton("Generar factura");
        botonGenerar.addActionListener(e -> generarFactura());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.setBackground(PANEL);
        botones.add(botonGenerar);

        inferior.add(totales, BorderLayout.CENTER);
        inferior.add(botones, BorderLayout.SOUTH);

        central.add(inferior, BorderLayout.SOUTH);

        add(central, BorderLayout.CENTER);

        cargarClientes();
        cargarProductos();
    }

    private JPanel crearPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PANEL);
        return panel;
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 13));
        etiqueta.setForeground(TEXTO);
        return etiqueta;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setForeground(Color.WHITE);
        boton.setBackground(AZUL);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(175, 40));
        return boton;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setForeground(TEXTO);
        campo.setBackground(new Color(27, 39, 62));
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 75, 105)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }

    private void estilizarCombo(JComboBox<?> combo) {
        combo.setFont(new Font("Arial", Font.PLAIN, 13));
        combo.setForeground(TEXTO);
        combo.setBackground(new Color(27, 39, 62));
        combo.setPreferredSize(new Dimension(250, 38));
    }

    private void cargarClientes() {
        for (Cliente cliente : clienteService.listarClientes()) {
            comboClientes.addItem(cliente);
        }
    }

    private void cargarProductos() {
        for (Producto producto : productoService.listarProductos()) {
            if (producto.getStock() > 0) {
                comboProductos.addItem(producto);
            }
        }
    }

    private void agregarProducto() {
        Producto producto = (Producto) comboProductos.getSelectedItem();

        if (producto == null) {
            JOptionPane.showMessageDialog(this,
                    "No hay productos disponibles.");
            return;
        }

        try {
            int cantidad = Integer.parseInt(campoCantidad.getText());

            if (cantidad <= 0 || cantidad > producto.getStock()) {
                throw new IllegalArgumentException(
                        "Cantidad inválida o stock insuficiente.");
            }

            for (DetalleFactura detalle : detalles) {
                if (detalle.getProducto() == producto) {
                    throw new IllegalArgumentException(
                            "El producto ya está agregado.");
                }
            }

            DetalleFactura detalle = new DetalleFactura(producto, cantidad);
            detalles.add(detalle);

            modeloTabla.addRow(new Object[]{
                producto.getNombre(),
                cantidad,
                "₡" + producto.getPrecio(),
                "₡" + detalle.calcularSubtotal()
            });

            modeloTabla.fireTableDataChanged();
            campoCantidad.setText("");
            actualizarTotales();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese una cantidad válida.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void actualizarTotales() {
        double subtotal = 0;

        for (DetalleFactura detalle : detalles) {
            subtotal += detalle.calcularSubtotal();
        }

        double impuesto = subtotal * 0.13;
        double total = subtotal + impuesto;

        etiquetaImpuesto.setText(
                "Impuesto (13%): ₡" + String.format("%.2f", impuesto));

        etiquetaTotal.setText(
                "Total: ₡" + String.format("%.2f", total));
    }

    private void generarFactura() {
        Cliente cliente = (Cliente) comboClientes.getSelectedItem();

        if (cliente == null || detalles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un cliente y agregue productos.");
            return;
        }

        try {
            Factura factura = facturaService.generarFactura(
                    cliente, usuario, detalles);

            JOptionPane.showMessageDialog(this,
                    "Factura generada correctamente.\n"
                    + "Número: " + factura.getNumero()
                    + "\nTotal: ₡"
                    + String.format("%.2f", factura.calcularTotal()));

            exportarFactura(factura);
            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void exportarFactura(Factura factura) {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar factura como archivo TXT");
        selector.setSelectedFile(
                new File("Factura_" + factura.getNumero() + ".txt"));

        if (selector.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = selector.getSelectedFile();

        if (!archivo.getName().toLowerCase().endsWith(".txt")) {
            archivo = new File(archivo.getAbsolutePath() + ".txt");
        }

        try (PrintWriter salida = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream(archivo),
                        StandardCharsets.UTF_8))) {

            salida.println("FIDECOMPRO - FACTURA");
            salida.println("Número: " + factura.getNumero());
            salida.println("Fecha: " + factura.getFecha());
            salida.println("Cliente: " + factura.getCliente().getNombre());
            salida.println("----------------------------------------");

            for (DetalleFactura detalle : factura.getDetalles()) {
                salida.println(detalle.getProducto().getNombre()
                        + " | Cantidad: " + detalle.getCantidad()
                        + " | Precio: ₡"
                        + String.format("%.2f", detalle.getPrecioUnitario())
                        + " | Subtotal: ₡"
                        + String.format("%.2f", detalle.calcularSubtotal()));
            }

            salida.println("----------------------------------------");
            salida.println("Subtotal: ₡"
                    + String.format("%.2f", factura.calcularSubtotal()));
            salida.println("Impuesto (13%): ₡"
                    + String.format("%.2f", factura.calcularImpuesto()));
            salida.println("TOTAL: ₡"
                    + String.format("%.2f", factura.calcularTotal()));

            JOptionPane.showMessageDialog(this,
                    "Factura exportada en:\n" + archivo.getAbsolutePath());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo exportar la factura: " + ex.getMessage());
        }
    }
}