package dao;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import entidades.Token;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.PersistenceException;

public class APIKeyDAO implements APIKeyDAOInterface {

    @Override
    public Token findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Token token = session.find(Token.class, id);
        session.close();

        return token;
    }

    @Override
    public Token createAPIKey(Token token) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String tokenHashed = argon2.hash(1, 1024, 1, token.getApikey());
            token.setApikey(tokenHashed);

            session.save(token);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();

        return token;
    }

    @Override
    public Token update(Token token) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.update(token);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();

        return token;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            Token token = this.findById(id);

            if (token != null) {
                session.delete(token);
            } else {
                return false;
            }

            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }

        return true;
    }
}
