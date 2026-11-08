
package fidecompro.interfaz;

import fidecompro.modelo.*;
import fidecompro.servicios.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Reportes extends JFrame {

    private final Color FONDO = new Color(12, 18, 32);
    private final Color PANEL = new Color(20, 29, 48);
    private final Color TEXTO = new Color(235, 240, 250);
    private final Color SECUNDARIO = new Color(155, 170, 195);

    private ClienteService clienteService;
    private ProductoService productoService;
    private FacturaService facturaService;

    public Reportes(ClienteService clientes,
                    ProductoService productos,
                    FacturaService facturas) {

        clienteService = clientes;
        productoService = productos;
        facturaService = facturas;

        setTitle("Fidecompro - Reportes");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800, 500));

        crearInterfaz();
    }

    private void crearInterfaz() {

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(FONDO);

        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setBackground(FONDO);
        encabezado.setBorder(new EmptyBorder(25, 30, 15, 30));

        JLabel titulo = new JLabel("Reportes");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(TEXTO);

        JLabel descripcion = new JLabel(
                "Resumen de la información registrada en Fidecompro."
        );
        descripcion.setFont(new Font("Arial", Font.PLAIN, 13));
        descripcion.setForeground(SECUNDARIO);

        encabezado.add(titulo);
        encabezado.add(Box.createVerticalStrut(7));
        encabezado.add(descripcion);

        JPanel contenido = new JPanel(new GridLayout(2, 2, 20, 20));
        contenido.setBackground(FONDO);
        contenido.setBorder(new EmptyBorder(20, 30, 30, 30));

        int cantidadClientes = clienteService.listarClientes().size();
        int cantidadProductos = productoService.listarProductos().size();
        int cantidadFacturas = facturaService.listarFacturas().size();

        double ventas = 0;

        for (Factura factura : facturaService.listarFacturas()) {
            ventas += factura.calcularTotal();
        }

        contenido.add(crearTarjeta(
                "VENTAS",
                "₡" + String.format("%.2f", ventas),
                "Total acumulado de ventas.",
                new Color(35, 105, 220)
        ));

        contenido.add(crearTarjeta(
                "FACTURAS",
                String.valueOf(cantidadFacturas),
                "Cantidad de facturas generadas.",
                new Color(30, 150, 180)
        ));

        contenido.add(crearTarjeta(
                "CLIENTES",
                String.valueOf(cantidadClientes),
                "Clientes registrados en el sistema.",
                new Color(120, 90, 210)
        ));

        contenido.add(crearTarjeta(
                "PRODUCTOS",
                String.valueOf(cantidadProductos),
                "Productos registrados en inventario.",
                new Color(35, 160, 115)
        ));

        principal.add(encabezado, BorderLayout.NORTH);
        principal.add(contenido, BorderLayout.CENTER);

        JButton actualizar = new JButton("Actualizar reportes");
        actualizar.setBackground(new Color(35, 105, 220));
        actualizar.setForeground(Color.WHITE);
        actualizar.setFocusPainted(false);
        actualizar.addActionListener(e -> {
            dispose();
            new Reportes(
                    clienteService,
                    productoService,
                    facturaService
            ).setVisible(true);
        });

        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        inferior.setBackground(FONDO);
        inferior.setBorder(new EmptyBorder(0, 20, 15, 30));
        inferior.add(actualizar);

        principal.add(inferior, BorderLayout.SOUTH);

        add(principal);
    }

    private JPanel crearTarjeta(
            String etiqueta,
            String valor,
            String descripcion,
            Color color
    ) {

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(PANEL);

        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(42, 55, 78)),
                new EmptyBorder(22, 25, 22, 25)
        ));

        JLabel textoEtiqueta = new JLabel(etiqueta);
        textoEtiqueta.setFont(new Font("Arial", Font.BOLD, 12));
        textoEtiqueta.setForeground(color);

        JLabel textoValor = new JLabel(valor);
        textoValor.setFont(new Font("Arial", Font.BOLD, 25));
        textoValor.setForeground(TEXTO);

        JLabel textoDescripcion = new JLabel(
                "<html><body style='width:250px'>"
                + descripcion + "</body></html>"
        );
        textoDescripcion.setFont(new Font("Arial", Font.PLAIN, 13));
        textoDescripcion.setForeground(SECUNDARIO);

        tarjeta.add(textoEtiqueta);
        tarjeta.add(Box.createVerticalStrut(15));
        tarjeta.add(textoValor);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(textoDescripcion);

        return tarjeta;
    }
}
