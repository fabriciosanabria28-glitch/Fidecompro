
package fidecompro.modelo;

public class Usuario extends Persona {

    private String nombreUsuario;
    private String contrasena;
    private String rol;

    public Usuario(int id, String nombre, String nombreUsuario,
                   String contrasena, String rol) {

        super(id, nombre);

        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public boolean validarAcceso(String usuario, String clave) {
        return nombreUsuario.equals(usuario)
                && contrasena.equals(clave);
    }

    @Override
    public String mostrarInformacion() {
        return "Usuario: " + nombre
                + " | Rol: " + rol;
    }
}
