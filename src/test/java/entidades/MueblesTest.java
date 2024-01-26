package entidades;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import java.util.List;

public class MueblesTest {
    @Test
    void Test1() {


        Mueble mueble1 = new Mueble(null,
                "Silla barata",
                "Ikea",
                "silla.jpg",
                27.7
        );

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(mueble1);

        session.getTransaction().commit();

        session.close();
        HibernateUtil.shutdown();
    }

    @Test
    void Test2() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL
        List<Mueble> todos = session.createQuery("from Mueble", Mueble.class).list();
        session.close();
        HibernateUtil.shutdown();

        System.out.println(todos);
    }


    @Test
    void TestPersistencia() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL
        List<Mueble> todos = session.createQuery("from Mueble", Mueble.class).list();


        session.beginTransaction();


        for (Mueble mu:todos) {
            mu.setPrecio(234.0);
            session.save(mu);
        }

        session.getTransaction().commit();
        session.close();
        HibernateUtil.shutdown();
    }

}