
package fidecompro.servicios;

import fidecompro.modelo.Producto;
import java.util.ArrayList;

public class ProductoService {

    private ArrayList<Producto> productos;
    private int siguienteId = 1;

    public ProductoService() {
        productos = new ArrayList<>();
    }

    public void registrarProducto(String codigo, String nombre,
            double precio, int stock,
            fidecompro.modelo.Categoria categoria) {

        if (codigo == null || codigo.trim().isEmpty()
                || nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El codigo y nombre son obligatorios."
            );
        }

        if (precio <= 0 || stock < 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor a cero y el stock no puede ser negativo."
            );
        }

        if (buscarPorCodigo(codigo) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un producto con ese codigo."
            );
        }

        Producto producto = new Producto(
                siguienteId++,
                codigo,
                nombre,
                precio,
                stock,
                categoria
        );

        productos.add(producto);
    }

    public ArrayList<Producto> listarProductos() {
        return productos;
    }

    public Producto buscarPorCodigo(String codigo) {

        for (Producto producto : productos) {
            if (producto.getCodigo().equalsIgnoreCase(codigo)) {
                return producto;
            }
        }

        return null;
    }

    public void eliminarProducto(String codigo) {

        Producto producto = buscarPorCodigo(codigo);

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no existe."
            );
        }

        productos.remove(producto);
    }

    public void editarProducto(String codigo, double precio, int stock) {

        Producto producto = buscarPorCodigo(codigo);

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no existe."
            );
        }

        if (precio <= 0 || stock < 0) {
            throw new IllegalArgumentException(
                    "Precio o stock no valido."
            );
        }

        producto.setPrecio(precio);
        producto.setStock(stock);
    }
}

