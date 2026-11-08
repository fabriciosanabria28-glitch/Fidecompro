
package fidecompro.modelo;

public class DetalleFactura {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public DetalleFactura(Producto producto, int cantidad) {
        if (producto == null) {
            throw new IllegalArgumentException("Debe seleccionar un producto.");
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }

        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double calcularSubtotal() {
        return cantidad * precioUnitario;
    }
}
