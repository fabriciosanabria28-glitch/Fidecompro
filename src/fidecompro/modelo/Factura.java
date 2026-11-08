
package fidecompro.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura {

    private int numero;
    private LocalDate fecha;
    private Cliente cliente;
    private Usuario usuario;
    private List<DetalleFactura> detalles;

    private static final double IMPUESTO = 0.13;

    public Factura(int numero, Cliente cliente, Usuario usuario) {
        this.numero = numero;
        this.fecha = LocalDate.now();
        this.cliente = cliente;
        this.usuario = usuario;
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetalleFactura detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException("Detalle inválido.");
        }

        detalles.add(detalle);
    }

    public double calcularSubtotal() {
        double subtotal = 0;

        for (DetalleFactura detalle : detalles) {
            subtotal += detalle.calcularSubtotal();
        }

        return subtotal;
    }

    public double calcularImpuesto() {
        return calcularSubtotal() * IMPUESTO;
    }

    public double calcularTotal() {
        return calcularSubtotal() + calcularImpuesto();
    }

    public int getNumero() {
        return numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }
}

