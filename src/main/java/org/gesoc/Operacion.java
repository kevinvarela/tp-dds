package org.gesoc;

import org.gesoc.excepciones.OperacionException;
import org.gesoc.item.Item;
import org.gesoc.item.TipoItem;

import java.util.ArrayList;
import java.util.List;

public class Operacion {

    private List<Item> items = new ArrayList<>();
    private Double valorBaseFinal;
    private boolean cerrada = false;
    private String documento = "";

    public Operacion() {
    }

    public void agregarItem(Item item){
        items.add(item);
    }

    public Double valorBase() {
        if (this.cerrada) {
            return valorBaseFinal;
        }
        return items.stream().mapToDouble(item -> item.getPrecio()).sum();
    }

    public Boolean puedeGenerarRemito() {
        return !items.isEmpty() && items.stream().allMatch(item -> item.esDeTipo(TipoItem.ARTICULO));
    }

    public void cerrar() {
        this.valorBaseFinal = this.valorBase();
        this.cerrada = true;
    }

    public boolean esCerrada() {
        return cerrada;
    }

    public void generarRemito() {
        if (this.puedeGenerarRemito()) {
            this.documento = this.obtenerPathDocumento();
        } else {
            throw new OperacionException("No se puede generar el remito, porque no todos los items son Articulos");
        }
    }

    //En esta entrega aún no se obtienen mas informacion acerca de que pasa con el documento
    private String obtenerPathDocumento() {
        return "remito.pdf";
    }

    public String getDocumento() {
        return documento;
    }
}
