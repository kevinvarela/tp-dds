package org.gesoc.item;

import org.gesoc.excepciones.ItemException;

public class Item {

    private Double precio;
    private TipoItem tipoItem;

    public Item(Double precio, TipoItem tipoItem) {
        if (tipoItem == null) {
            throw new ItemException("El Item debe tener un TipoItem valido");
        }
        this.setPrecio(precio);
        this.tipoItem = tipoItem;
    }

    public Boolean esDeTipo(TipoItem tipoItem) {
        return this.tipoItem.equals(tipoItem);
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.validarPrecio(precio);
        this.precio = precio;
    }

    private void validarPrecio(Double precio) {
        if (precio == null || precio < 0) {
            throw new ItemException("El precio del Item no puede ser nulo o negativo");
        }
    }
}
