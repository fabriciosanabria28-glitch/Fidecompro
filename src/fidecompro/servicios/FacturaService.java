
package fidecompro.servicios;

import fidecompro.modelo.Factura;
import fidecompro.modelo.Cliente;
import fidecompro.modelo.Usuario;
import fidecompro.modelo.DetalleFactura;
import java.util.ArrayList;

public class FacturaService {

    private ArrayList<Factura> facturas;
    private int siguienteNumero = 1;

    public FacturaService() {
        facturas = new ArrayList<>();
    }

    public Factura generarFactura(Cliente cliente, Usuario usuario,
            ArrayList<DetalleFactura> detalles) {

        if (cliente == null || usuario == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un cliente y un usuario."
            );
        }

        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException(
                    "La factura debe tener productos."
            );
        }

        // Verificar que todos los productos tengan stock suficiente.
        for (DetalleFactura detalle : detalles) {

            if (detalle.getCantidad() >
                    detalle.getProducto().getStock()) {

                throw new IllegalArgumentException(
                        "Stock insuficiente para: "
                        + detalle.getProducto().getNombre()
                );
            }
        }

        // Crear la factura.
        Factura factura = new Factura(
                siguienteNumero,
                cliente,
                usuario
        );

        // Agregar los productos y descontar el inventario.
        for (DetalleFactura detalle : detalles) {

            factura.agregarDetalle(detalle);

            detalle.getProducto().actualizarStock(
                    detalle.getCantidad()
            );
        }

        facturas.add(factura);
        siguienteNumero++;

        return factura;
    }

    public ArrayList<Factura> listarFacturas() {
        return facturas;
    }
}

