

package fidecompro.interfaz;

import fidecompro.modelo.Cliente;
import fidecompro.servicios.ClienteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Clientes extends JFrame {

    private ClienteService clienteService;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JTextField campoBuscar;

    private final Color FONDO = new Color(13, 17, 28);
    private final Color TARJETA = new Color(24, 31, 46);
    private final Color AZUL = new Color(35, 115, 245);
    private final Color TEXTO = new Color(240, 244, 255);
    private final Color GRIS = new Color(150, 160, 180);

    public Clientes(ClienteService clienteService) {

        this.clienteService = clienteService;

        setTitle("Fidecompro - Clientes");
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

        JLabel titulo = new JLabel("Clientes");
        titulo.setForeground(TEXTO);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));

        JLabel descripcion = new JLabel(
                "Administra la información de tus clientes."
        );
        descripcion.setForeground(GRIS);
        descripcion.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setBackground(FONDO);

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(8));
        textos.add(descripcion);

        JButton botonNuevo = new JButton("+ Nuevo cliente");
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

        // Tabla de clientes
        modeloTabla = new DefaultTableModel(
                new Object[]{
                    "Cédula", "Nombre", "Teléfono",
                    "Correo", "Dirección"
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

        estilizarBoton(botonEditar, AZUL);
        estilizarBoton(botonEliminar, new Color(190, 55, 65));

        panelBotones.add(botonEditar);
        panelBotones.add(botonEliminar);

        panelCentral.add(panelBotones, BorderLayout.SOUTH);

        add(panelCentral, BorderLayout.CENTER);

        // Eventos
        botonNuevo.addActionListener(e -> nuevoCliente());
        botonEditar.addActionListener(e -> editarCliente());
        botonEliminar.addActionListener(e -> eliminarCliente());

        campoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                buscarCliente();
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

        for (Cliente cliente : clienteService.listarClientes()) {

            modeloTabla.addRow(new Object[]{
                cliente.getCedula(),
                cliente.getNombre(),
                cliente.getTelefono(),
                cliente.getCorreo(),
                cliente.getDireccion()
            });
        }
    }

    private void nuevoCliente() {

        JTextField nombre = new JTextField();
        JTextField cedula = new JTextField();
        JTextField telefono = new JTextField();
        JTextField correo = new JTextField();
        JTextField direccion = new JTextField();

        Object[] campos = {
            "Nombre:", nombre,
            "Cédula:", cedula,
            "Teléfono:", telefono,
            "Correo:", correo,
            "Dirección:", direccion
        };

        int resultado = JOptionPane.showConfirmDialog(
                this,
                campos,
                "Registrar cliente",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultado == JOptionPane.OK_OPTION) {

            try {

                clienteService.registrarCliente(
                        nombre.getText(),
                        cedula.getText(),
                        telefono.getText(),
                        correo.getText(),
                        direccion.getText()
                );

                actualizarTabla();

                JOptionPane.showMessageDialog(
                        this,
                        "Cliente registrado correctamente."
                );

            } catch (IllegalArgumentException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }
        }
    }

    private void editarCliente() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un cliente."
            );
            return;
        }

        String cedula = modeloTabla.getValueAt(fila, 0).toString();

        Cliente cliente = clienteService.buscarPorCedula(cedula);

        String telefono = JOptionPane.showInputDialog(
                this,
                "Nuevo teléfono:",
                cliente.getTelefono()
        );

        if (telefono == null) return;

        String correo = JOptionPane.showInputDialog(
                this,
                "Nuevo correo:",
                cliente.getCorreo()
        );

        if (correo == null) return;

        try {

            clienteService.editarCliente(
                    cedula,
                    telefono,
                    correo
            );

            actualizarTabla();

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente actualizado correctamente."
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    private void eliminarCliente() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un cliente."
            );
            return;
        }

        String cedula = modeloTabla.getValueAt(fila, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar este cliente?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            try {

                clienteService.eliminarCliente(cedula);
                actualizarTabla();

            } catch (IllegalArgumentException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }
        }
    }

    private void buscarCliente() {

        String busqueda = campoBuscar.getText().toLowerCase();

        modeloTabla.setRowCount(0);

        for (Cliente cliente : clienteService.listarClientes()) {

            if (cliente.getNombre().toLowerCase().contains(busqueda)
                    || cliente.getCedula().toLowerCase().contains(busqueda)) {

                modeloTabla.addRow(new Object[]{
                    cliente.getCedula(),
                    cliente.getNombre(),
                    cliente.getTelefono(),
                    cliente.getCorreo(),
                    cliente.getDireccion()
                });
            }
        }
    }
}

