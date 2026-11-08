
package fidecompro.interfaz;

import fidecompro.modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;

    public Login() {

        setTitle("Fidecompro - Iniciar sesión");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JLabel titulo = new JLabel("Fidecompro");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(new Color(30, 55, 100));
        titulo.setBounds(125, 45, 250, 45);
        panel.add(titulo);

        JLabel subtitulo = new JLabel("Sistema de facturación");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitulo.setForeground(Color.GRAY);
        subtitulo.setBounds(135, 90, 250, 25);
        panel.add(subtitulo);

        JLabel usuario = new JLabel("Usuario");
        usuario.setBounds(70, 155, 300, 25);
        panel.add(usuario);

        campoUsuario = new JTextField();
        campoUsuario.setBounds(70, 185, 300, 40);
        panel.add(campoUsuario);

        JLabel contrasena = new JLabel("Contraseña");
        contrasena.setBounds(70, 245, 300, 25);
        panel.add(contrasena);

        campoContrasena = new JPasswordField();
        campoContrasena.setBounds(70, 275, 300, 40);
        panel.add(campoContrasena);

        JButton botonIngresar = new JButton("Iniciar sesión");
        botonIngresar.setBounds(70, 345, 300, 45);
        botonIngresar.setBackground(new Color(35, 105, 190));
        botonIngresar.setForeground(Color.WHITE);
        botonIngresar.setFocusPainted(false);
        panel.add(botonIngresar);

        botonIngresar.addActionListener(e -> iniciarSesion());

        // Credenciales de demostración
        JPanel panelCredenciales = new JPanel();
        panelCredenciales.setLayout(new GridLayout(3, 1, 0, 5));
        panelCredenciales.setBackground(new Color(235, 242, 252));
        panelCredenciales.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(190, 210, 235)
                        ),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                )
        );
        panelCredenciales.setBounds(70, 415, 300, 85);

        JLabel tituloCredenciales = new JLabel(
                "Credenciales de acceso para demostración"
        );
        tituloCredenciales.setFont(
                new Font("Arial", Font.BOLD, 12)
        );
        tituloCredenciales.setForeground(
                new Color(30, 55, 100)
        );

        JLabel usuarioDemo = new JLabel("Usuario: admin");
        usuarioDemo.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel claveDemo = new JLabel("Contraseña: 1234");
        claveDemo.setFont(new Font("Arial", Font.PLAIN, 12));

        panelCredenciales.add(tituloCredenciales);
        panelCredenciales.add(usuarioDemo);
        panelCredenciales.add(claveDemo);

        panel.add(panelCredenciales);

        add(panel);
    }

    private void iniciarSesion() {

        String nombre = campoUsuario.getText();
        String clave = new String(campoContrasena.getPassword());

        Usuario usuario = new Usuario(
                1, "Administrador", "admin", "1234", "Administrador"
        );

        if (usuario.validarAcceso(nombre, clave)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Bienvenido a Fidecompro."
            );

            new MenuPrincipal().setVisible(true);
            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario o contraseña incorrectos."
            );
        }
    }
}


