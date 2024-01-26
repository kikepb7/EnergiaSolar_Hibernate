/*
package dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class AsociacionesDAO implements AsociacionesDAOInterface {

    @Override
    public boolean asignarProveedor(Mueble m, Proveedor p) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            m.setProv(p);
            session.update(m);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }

        session.close();
        return true;
    }

    @Override
    public Proveedor obtenerProvedorMueble(Mueble m) {
        Proveedor proveedor = null;

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Mueble> query = session.createQuery("SELECT m FROM Mueble m JOIN fetch m.prov WHERE m.id =:id", Mueble.class);
            query.setParameter("id", m.getId());
            proveedor = query.getSingleResult().getProv();

        } catch (NoResultException nre) {
            proveedor = null;
        }
        session.close();

        return proveedor;
    }

    @Override
    public List<Mueble> mueblesProveedor(Proveedor p) {
        List<Mueble> lista_muebles=null;

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Proveedor> query = session.createQuery("select p from Proveedor p join fetch p.almacen  where p.id =:id", Proveedor.class);
            query.setParameter("id", p.getId());
            lista_muebles = query.getSingleResult().getAlmacen();
        } catch (NoResultException nre) {
            lista_muebles = new ArrayList<>();
        }

        session.close();

        return lista_muebles;
    }

    @Override
    public List<Cliente> clientesConMueble(Mueble m) {
        List<Cliente> lista_clientes = null;

        Session session = HibernateUtil.getSessionFactory().openSession();


        return lista_clientes;
    }

    @Override
    public List<Mueble> mueblesComprados(Cliente c) {
        List<Mueble> lista_muebles = null;

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.close();
        return lista_muebles;
    }

    @Override
    public boolean comprarMueble(Mueble m, Cliente c) {
        Session session = HibernateUtil.getSessionFactory().openSession();


        session.close();
        return true;
    }

}*/
