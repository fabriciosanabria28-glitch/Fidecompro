
package fidecompro.servicios;

import fidecompro.modelo.Cliente;
import java.util.ArrayList;

public class ClienteService {

    private ArrayList<Cliente> clientes;
    private int siguienteId = 1;

    public ClienteService() {
        clientes = new ArrayList<>();
    }

    public void registrarCliente(String nombre, String cedula,
            String telefono, String correo, String direccion) {

        if (nombre == null || nombre.trim().isEmpty()
                || cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre y la cedula son obligatorios."
            );
        }

        if (buscarPorCedula(cedula) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un cliente con esa cedula."
            );
        }

        Cliente cliente = new Cliente(
                siguienteId++,
                nombre,
                cedula,
                telefono,
                correo,
                direccion
        );

        clientes.add(cliente);
    }

    public ArrayList<Cliente> listarClientes() {
        return clientes;
    }

    public Cliente buscarPorCedula(String cedula) {

        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equalsIgnoreCase(cedula)) {
                return cliente;
            }
        }

        return null;
    }

    public void eliminarCliente(String cedula) {

        Cliente cliente = buscarPorCedula(cedula);

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no existe."
            );
        }

        clientes.remove(cliente);
    }

    public void editarCliente(String cedula, String telefono,
            String correo) {

        Cliente cliente = buscarPorCedula(cedula);

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no existe."
            );
        }

        cliente.setTelefono(telefono);
        cliente.setCorreo(correo);
    }
}
