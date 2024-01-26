package dao;

import dao.SolarInstalationDAOInterface;
import entidades.SolarInstalation;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.PersistenceException;

public class SolarInstalationDAO implements SolarInstalationDAOInterface {
    @Override
    public SolarInstalation createSolarInstalation(SolarInstalation si) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(si);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return si;
    }

    @Override
    public SolarInstalation findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        SolarInstalation si = session.find(SolarInstalation.class, id);
        session.close();

        return si;
    }
}
