
package fidecompro.interfaz;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import fidecompro.servicios.ProductoService;
import fidecompro.servicios.ClienteService;
import fidecompro.modelo.Usuario;
import fidecompro.servicios.FacturaService;

public class MenuPrincipal extends JFrame {

    private ProductoService productoService = new ProductoService();
    private ClienteService clienteService = new ClienteService();
    private FacturaService facturaService = new FacturaService();

    private Usuario usuarioActual = new Usuario(
            1, "Administrador", "admin", "1234", "Administrador"
    );

    private JPanel contenido;

    private final Color FONDO = new Color(13, 17, 28);
    private final Color SIDEBAR = new Color(10, 14, 24);
    private final Color TARJETA = new Color(24, 31, 46);
    private final Color AZUL = new Color(35, 115, 245);
    private final Color TEXTO = new Color(240, 244, 255);
    private final Color GRIS = new Color(150, 160, 180);

    public MenuPrincipal() {

        setTitle("Fidecompro | Sistema de facturación");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));

        setLayout(new BorderLayout());
        getContentPane().setBackground(FONDO);

        crearBarraLateral();
        crearPanelDerecho();
    }

    private void crearBarraLateral() {

        JPanel barra = new JPanel();
        barra.setBackground(SIDEBAR);
        barra.setPreferredSize(new Dimension(220, 700));
        barra.setLayout(new BoxLayout(barra, BoxLayout.Y_AXIS));
        barra.setBorder(new EmptyBorder(25, 15, 20, 15));

        JLabel logo = new JLabel("  ◆ FIDECOMPRO");
        logo.setForeground(TEXTO);
        logo.setFont(new Font("SansSerif", Font.BOLD, 20));
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        barra.add(logo);
        barra.add(Box.createVerticalStrut(45));

        JLabel etiqueta = new JLabel("  MENÚ PRINCIPAL");
        etiqueta.setForeground(GRIS);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 11));
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);

        barra.add(etiqueta);
        barra.add(Box.createVerticalStrut(15));

        agregarBotonMenu(barra, "⌂", "Inicio");
        agregarBotonMenu(barra, "♙", "Clientes");
        agregarBotonMenu(barra, "▣", "Productos");
        agregarBotonMenu(barra, "▤", "Facturación");
        agregarBotonMenu(barra, "▥", "Reportes");
        agregarBotonMenu(barra, "▤", "Historial");

        barra.add(Box.createVerticalGlue());

        JLabel version = new JLabel("FIDECOMPRO  |  V1.0");
        version.setForeground(GRIS);
        version.setFont(new Font("SansSerif", Font.PLAIN, 10));
        version.setAlignmentX(Component.LEFT_ALIGNMENT);

        barra.add(version);

        add(barra, BorderLayout.WEST);
    }

    private void agregarBotonMenu(
            JPanel barra, String icono, String nombre
    ) {

        JButton boton = new JButton(icono + "     " + nombre);

        boton.setMaximumSize(new Dimension(190, 48));
        boton.setPreferredSize(new Dimension(190, 48));
        boton.setMinimumSize(new Dimension(190, 48));

        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setForeground(TEXTO);
        boton.setBackground(SIDEBAR);
        boton.setFont(new Font("SansSerif", Font.PLAIN, 14));

        boton.setBorder(new EmptyBorder(0, 15, 0, 5));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);

        boton.addActionListener(e -> abrirSeccion(nombre));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boton.setBackground(new Color(30, 47, 75));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                boton.setBackground(SIDEBAR);
            }
        });

        barra.add(boton);
        barra.add(Box.createVerticalStrut(8));
    }

    private void crearPanelDerecho() {

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(FONDO);

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(new Color(17, 23, 36));
        encabezado.setBorder(new EmptyBorder(18, 30, 18, 30));

        JLabel nombreSistema = new JLabel("Panel de administración");
        nombreSistema.setForeground(TEXTO);
        nombreSistema.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel usuario = new JLabel("●  Administrador");
        usuario.setForeground(new Color(100, 170, 255));
        usuario.setFont(new Font("SansSerif", Font.BOLD, 13));

        encabezado.add(nombreSistema, BorderLayout.WEST);
        encabezado.add(usuario, BorderLayout.EAST);

        panelDerecho.add(encabezado, BorderLayout.NORTH);

        contenido = new JPanel(new BorderLayout());
        contenido.setBackground(FONDO);

        mostrarInicio();

        panelDerecho.add(contenido, BorderLayout.CENTER);

        add(panelDerecho, BorderLayout.CENTER);
    }

    private void abrirSeccion(String seccion) {

        if (seccion.equals("Inicio")) {

            mostrarInicio();

        } else if (seccion.equals("Productos")) {

            new Productos(productoService).setVisible(true);

        } else if (seccion.equals("Clientes")) {

            new Clientes(clienteService).setVisible(true);

        } else if (seccion.equals("Facturación")) {

            new NuevaFactura(
                    clienteService,
                    productoService,
                    facturaService,
                    usuarioActual
            ).setVisible(true);

        } else if (seccion.equals("Reportes")) {

            new Reportes(
                    clienteService,
                    productoService,
                    facturaService
            ).setVisible(true);

        } else if (seccion.equals("Historial")) {

            new HistorialFacturas(facturaService).setVisible(true);
        }
    }

    private void mostrarInicio() {

        contenido.removeAll();

        JPanel panelInicio = new JPanel(new BorderLayout());
        panelInicio.setBackground(FONDO);
        panelInicio.setBorder(new EmptyBorder(35, 35, 35, 35));

        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setBackground(FONDO);

        JLabel titulo = new JLabel("Bienvenido a Fidecompro");
        titulo.setForeground(TEXTO);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));

        JLabel descripcion = new JLabel(
                "Administra tus clientes, productos y facturación."
        );
        descripcion.setForeground(GRIS);
        descripcion.setFont(new Font("SansSerif", Font.PLAIN, 14));

        encabezado.add(titulo);
        encabezado.add(Box.createVerticalStrut(10));
        encabezado.add(descripcion);

        panelInicio.add(encabezado, BorderLayout.NORTH);

        JPanel tarjetas = new JPanel(new GridLayout(2, 2, 20, 20));
        tarjetas.setBackground(FONDO);
        tarjetas.setBorder(new EmptyBorder(35, 0, 20, 0));

        tarjetas.add(crearTarjeta(
                "♙", "CLIENTES", "Administración de clientes"
        ));

        tarjetas.add(crearTarjeta(
                "▣", "PRODUCTOS", "Control de productos e inventario"
        ));

        tarjetas.add(crearTarjeta(
                "▤", "FACTURACIÓN", "Generación de facturas"
        ));

        tarjetas.add(crearTarjeta(
                "▥", "REPORTES", "Información general del sistema"
        ));

        panelInicio.add(tarjetas, BorderLayout.CENTER);

        contenido.add(panelInicio, BorderLayout.CENTER);

        contenido.revalidate();
        contenido.repaint();
    }

    private JPanel crearTarjeta(
            String icono, String titulo, String descripcion
    ) {

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(TARJETA);

        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(43, 57, 80), 1
                ),
                new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel simbolo = new JLabel(icono);
        simbolo.setForeground(AZUL);
        simbolo.setFont(new Font("SansSerif", Font.BOLD, 30));

        JLabel nombre = new JLabel(titulo);
        nombre.setForeground(TEXTO);
        nombre.setFont(new Font("SansSerif", Font.BOLD, 17));

        JLabel detalle = new JLabel(descripcion);
        detalle.setForeground(GRIS);
        detalle.setFont(new Font("SansSerif", Font.PLAIN, 12));

        tarjeta.add(simbolo);
        tarjeta.add(Box.createVerticalStrut(18));
        tarjeta.add(nombre);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(detalle);

        return tarjeta;
    }
}

