package asociaciones;

import dao.AsociacionesDAO;
import dao.AsociacionesDAOInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MueblesProvTest {

    static MuebleDAOInterface dao_mueble;
    static ProveedorDAOInterface dao_prov;
    static AsociacionesDAOInterface dao_asoc;
    static ClienteDAOInterface dao_cliente;

    @BeforeAll
    static void setUp(){
        dao_mueble=new MuebleDAO();
        dao_prov=new ProveedorDAO();
        dao_asoc=new AsociacionesDAO();
        dao_cliente=new ClienteDAO();
    }

    @Test
    public void test1(){
//        Proveedor prov=new Proveedor(null,"Ikea SA","Malaga plaza mayor","95243246","ikea@gmail.com");
//        dao_prov.crearProveedor(prov);
        Mueble mueble=dao_mueble.buscarPorNombreLike("albany table").get(0);
        //asignar uno nuevo
//        dao_asoc.asignarProveedor(mueble,prov);
        //asignar otro ya existente
        dao_asoc.asignarProveedor(mueble,dao_prov.buscarPorID(2L));

    }

    @Test
    public void test2(){
        Mueble m=dao_mueble.buscarPorId(3L);
        Proveedor p=dao_asoc.obtenerProvedorMueble(m);
        System.out.println(p.getNombre()+" "+p.getEmail());
    }

    @Test
    public void test3(){
        Proveedor p=dao_prov.buscarPorID(1L);
        List<Mueble> lista=dao_asoc.mueblesProveedor(p);
        System.out.println(lista);
    }

    @Test
    public void test4(){
        Mueble m=dao_mueble.buscarPorId(2L);
        List<Cliente> lista=dao_asoc.clientesConMueble(m);
        System.out.println(lista);
    }

    @Test
    public void test5(){
        Cliente c=dao_cliente.buscarPorId(1L);
        List<Mueble> lista=dao_asoc.mueblesComprados(c);
        System.out.println(lista);
    }

    @Test
    public void test6(){
        Cliente c=dao_cliente.buscarPorId(4L);
        Mueble m=dao_mueble.buscarPorId(6L);
        dao_asoc.comprarMueble(m,c);

    }
}
