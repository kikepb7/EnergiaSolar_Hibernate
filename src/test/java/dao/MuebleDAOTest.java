package dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class MuebleDAOTest {
    static MuebleDAOInterface dao;

    @BeforeAll
    static void setUp(){
        dao=new MuebleDAO();
    }

    @AfterAll
    static void setDown(){
        //     dao.deleteAll();
    }

    @Test
    void devolverTodos() {
        System.out.println(dao.devolverTodos());


    }

    @Test
    void devolverMasCaros() {
        System.out.println(dao.devolverMasCaros());
    }

    @Test
    void devolverTodasImages() {
        System.out.println(dao.devolverTodasImages());
    }

    @Test
    void devolverNombreImagenes() {
        System.out.println(dao.devolverNombreImagenes());
    }

    @Test
    void totalMuebles() {
        System.out.println(dao.totalMuebles());
    }

    @Test
    void buscarPorId() {
        System.out.println(dao.buscarPorId(23L));
    }

    @Test
    void mediaPrecios() {
        System.out.println(dao.mediaPrecios());
    }

    @Test
    void mediaPreciosMarca() {
        System.out.println(dao.mediaPreciosMarca("caressa"));
    }

    @Test
    void buscarPorNombreLike() {
        System.out.println(dao.buscarPorNombreLike("cama"));
    }

    @Test
    void buscarEntrePrecios() {
        System.out.println(dao.buscarEntrePrecios(10D,100D));
    }

    @Test
    void buscarEntrePreciosMarca() {
        System.out.println(dao.buscarEntrePreciosMarca(10D,100D,"liddy"));
    }

    @Test
    void buscarEntrePreciosMarcas() {
        List<String> marcasFiltro = Arrays.asList("liddy", "marcos");
        System.out.println(dao.buscarEntrePreciosMarcas(10D, 100D, marcasFiltro));
    }

    @Test
    void create() {
        Mueble mueble1 = new Mueble(null,
                "Silla mas cara",
                "Ikea",
                "sillaza.jpg",
                27.7
        );

        dao.create(mueble1);
    }

    @Test
    void update() {
        Mueble mueble1 = new Mueble(20L,
                "Silla mas cara",
                "Ikea",
                "sillaza.jpg",
                27777.7
        );

        dao.update(mueble1);
    }

    @Test
    void deleteById() {
        dao.deleteById(20L);
    }
}