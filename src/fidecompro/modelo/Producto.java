
package fidecompro.modelo;

public class Producto {

    private int id;
    private String codigo;
    private String nombre;
    private double precio;
    private int stock;
    private Categoria categoria;

    public Producto(int id, String codigo, String nombre,
                    double precio, int stock, Categoria categoria) {

        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void actualizarStock(int cantidad) {
        if (cantidad < 0 || cantidad > stock) {
            throw new IllegalArgumentException(
                    "La cantidad solicitada no está disponible."
            );
        }

        stock -= cantidad;
    }

    @Override
    public String toString() {
        return nombre + " - ₡" + precio + " (Stock: " + stock + ")";
    }
}

