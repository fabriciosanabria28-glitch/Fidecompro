
package fidecompro.interfaz;

import fidecompro.modelo.Factura;
import fidecompro.servicios.FacturaService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class HistorialFacturas extends JFrame {

    private final Color FONDO = new Color(12, 18, 32);
    private final Color PANEL = new Color(20, 29, 48);
    private final Color AZUL = new Color(35, 105, 220);
    private final Color TEXTO = new Color(235, 240, 250);
    private final Color GRIS = new Color(155, 170, 195);

    private FacturaService facturaService;
    private DefaultTableModel modelo;

    public HistorialFacturas(FacturaService servicio) {

        facturaService = servicio;

        setTitle("Fidecompro - Historial de facturas");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(FONDO);

        crearInterfaz();
        cargarFacturas();
    }

    private void crearInterfaz() {

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(FONDO);
        encabezado.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 20, 30)
        );

        JLabel titulo = new JLabel("Historial de facturas");
        titulo.setFont(new Font("Arial", Font.BOLD, 27));
        titulo.setForeground(TEXTO);

        JLabel descripcion = new JLabel(
                "Consulta las facturas generadas en el sistema."
        );
        descripcion.setFont(new Font("Arial", Font.PLAIN, 13));
        descripcion.setForeground(GRIS);

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 7));
        textos.setBackground(FONDO);
        textos.add(titulo);
        textos.add(descripcion);

        encabezado.add(textos, BorderLayout.CENTER);

        JButton botonActualizar = new JButton("Actualizar");
        botonActualizar.setBackground(AZUL);
        botonActualizar.setForeground(Color.WHITE);
        botonActualizar.setFocusPainted(false);
        botonActualizar.addActionListener(e -> cargarFacturas());

        encabezado.add(botonActualizar, BorderLayout.EAST);

        add(encabezado, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new Object[]{
                    "Número", "Fecha", "Cliente", "Usuario", "Total"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(38);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setForeground(TEXTO);
        tabla.setBackground(PANEL);
        tabla.setGridColor(new Color(42, 55, 78));
        tabla.setSelectionBackground(new Color(35, 75, 135));
        tabla.setSelectionForeground(Color.WHITE);

        tabla.getTableHeader().setBackground(
                new Color(27, 39, 62)
        );
        tabla.getTableHeader().setForeground(TEXTO);
        tabla.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(
                BorderFactory.createEmptyBorder(10, 30, 30, 30)
        );
        scroll.getViewport().setBackground(PANEL);

        add(scroll, BorderLayout.CENTER);
    }

    private void cargarFacturas() {

        modelo.setRowCount(0);

        for (Factura factura : facturaService.listarFacturas()) {

            modelo.addRow(new Object[]{
                factura.getNumero(),
                factura.getFecha().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                ),
                factura.getCliente().getNombre(),
                factura.getUsuario().getNombre(),
                "₡" + String.format("%.2f", factura.calcularTotal())
            });
        }
    }
}

