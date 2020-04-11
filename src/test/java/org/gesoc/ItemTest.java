package org.gesoc;

import org.gesoc.excepciones.ItemException;
import org.gesoc.item.Item;
import org.gesoc.item.TipoItem;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ItemTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void noSePuedenCrearItemsSinPrecio() {
        expectedEx.expect(ItemException.class);
        expectedEx.expectMessage("El precio del Item no puede ser nulo o negativo");
        new Item(null, TipoItem.ARTICULO);
    }

    @Test
    public void noSePuedenCrearItemsConPrecioMenosACero() {
        expectedEx.expect(ItemException.class);
        expectedEx.expectMessage("El precio del Item no puede ser nulo o negativo");
        new Item(-1D, TipoItem.ARTICULO);
    }

    @Test
    public void noSePuedenCrearItemsSinTipoItem() {
        expectedEx.expect(ItemException.class);
        expectedEx.expectMessage("El Item debe tener un TipoItem valido");
        new Item(100D, null);
    }
}
