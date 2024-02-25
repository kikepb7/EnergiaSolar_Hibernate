package dao.user;

import dto.userDTO.UserDTO;
import entidades.Report;
import entidades.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.PersistenceException;
import java.util.List;

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

        User user = session.find(User.class, id);
        session.close();

        return user;
    }

    @Override
    public User authUser(String email, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user;

        try {
            session.beginTransaction();

            Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
            query.setParameter("email", email);
            user = query.uniqueResult();

            if (user != null && user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public UserDTO getUserInfo(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            UserDTO user = session.createQuery(
                            "SELECT NEW dto.userDTO.UserDTO(u.name, u.lastName, u.email, u.image) FROM User u WHERE id = :id", UserDTO.class)
                    .setParameter("id", id)
                    .uniqueResult();

            session.close();

            return user;
        } catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
