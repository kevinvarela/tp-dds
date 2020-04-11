package org.example;

import org.gesoc.Operacion;
import org.gesoc.excepciones.OperacionException;
import org.gesoc.item.Item;
import org.gesoc.item.TipoItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperacionTest {

    Operacion operacionSoloArticulos = new Operacion();
    Operacion operacionSoloServicios = new Operacion();
    Operacion operacionSinItems = new Operacion();

    private Item articulo;
    private Item articulo2;
    private Item articulo3;
    private Item servicio;

    @Before
    public void initializeContext() {
        articulo = new Item(100D, TipoItem.ARTICULO);
        articulo2 = new Item(120D, TipoItem.ARTICULO);
        articulo3 = new Item(20D, TipoItem.ARTICULO);
        servicio = new Item(85D, TipoItem.SERVICIO);

        operacionSoloArticulos.agregarItem(articulo);
        operacionSoloArticulos.agregarItem(articulo2);
        operacionSoloArticulos.agregarItem(articulo3);

        operacionSoloServicios.agregarItem(servicio);
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void elValorFinalDeLaOperacionConTodosArticulosEs240() {
        Assert.assertEquals(operacionSoloArticulos.valorBase(), Double.valueOf(240));
    }

    @Test
    public void elValorFinalDeLaOperacionConUnServicioDe85PesosEs85() {
        Assert.assertEquals(operacionSoloServicios.valorBase(), Double.valueOf(85));
    }

    @Test
    public void elValorFinalDeLaOperacionSinItemsEs0() {
        Assert.assertEquals(operacionSinItems.valorBase(), Double.valueOf(0));
    }

    @Test
    public void elValorFinalDeLaOperacionConUnServicioModificandoElPrecioDelServicioEstandoCerradaEs85() {
        operacionSoloServicios.cerrar();
        servicio.setPrecio(100D);
        Assert.assertEquals(operacionSoloServicios.valorBase(), Double.valueOf(85));
    }

    @Test
    public void elValorFinalDeLaOperacionConTodosArticulosModificandoUnItemEs1220() {
        articulo3.setPrecio(1000D);
        Assert.assertEquals(operacionSoloArticulos.valorBase(), Double.valueOf(1220));
    }

    @Test
    public void unaOperacionQueNuncaFueCerradaElMetodoEsCerradaDaFalse() {
        Assert.assertFalse(operacionSoloArticulos.esCerrada());
    }

    @Test
    public void cerrarUnOperacionColocaSuEstadoEsCerradaTrue() {
        operacionSoloArticulos.cerrar();
        Assert.assertTrue(operacionSoloArticulos.esCerrada());
    }

    @Test
    public void elValorFinalDeLaOperacionModificandoPrecioDeUnItemLuegoDeCerrarlaEs240() {
        Assert.assertEquals(operacionSoloArticulos.valorBase(), Double.valueOf(240));
        operacionSoloArticulos.cerrar();
        articulo.setPrecio(1000D);
        Assert.assertEquals(operacionSoloArticulos.valorBase(), Double.valueOf(240));
    }

    @Test
    public void soloPuedeGenerarRemitoSiSusItemsSonTodosArticulos() {
        operacionSoloArticulos.generarRemito();
        Assert.assertEquals("remito.pdf", operacionSoloArticulos.getDocumento());
    }


    @Test
    public void noSePuedeGenerarRemitoSiSusItemsNoSonTodosArticulos() {
        expectedEx.expect(OperacionException.class);
        expectedEx.expectMessage("No se puede generar el remito, porque no todos los items son Articulos");
        operacionSoloArticulos.agregarItem(servicio);
        operacionSoloArticulos.generarRemito();
    }

    @Test
    public void noSePuedeGenerarRemitoDeUnaOperacionSinItems() {
        expectedEx.expect(OperacionException.class);
        expectedEx.expectMessage("No se puede generar el remito, porque no todos los items son Articulos");
        operacionSinItems.generarRemito();
    }

}
