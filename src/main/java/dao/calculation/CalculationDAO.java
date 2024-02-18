package dao.calculation;

import entidades.Calculation;
import entidades.User;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.PersistenceException;

public class CalculationDAO implements CalculationDAOInterface {

    @Override
    public Calculation createCalculation(Calculation c) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(c);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return c;
    }

    @Override
    public Calculation findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Calculation c = session.find(Calculation.class, id);
        session.close();

        return c;
    }
}
