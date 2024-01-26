package dao;

import entidades.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.PersistenceException;

public class UserDAO implements UserDAOInterface {
    @Override
    public User createUser(User u) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(u);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return u;
    }

    @Override
    public User findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        User u = session.find(User.class, id);
        session.close();

        return u;
    }
}
