
package fidecompro.modelo;

public class Cliente extends Persona {

    private String cedula;
    private String telefono;
    private String correo;
    private String direccion;

    public Cliente(int id, String nombre, String cedula,
                   String telefono, String correo, String direccion) {

        super(id, nombre);

        this.cedula = cedula;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    public String getCedula() {
        return cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String mostrarInformacion() {
        return "Cliente: " + nombre
                + " | Cedula: " + cedula
                + " | Telefono: " + telefono;
    }

    @Override
    public String toString() {
        return nombre + " - " + cedula;
    }
}

