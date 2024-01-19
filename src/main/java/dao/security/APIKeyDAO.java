package dao.security;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import entidades.Panel;
import entidades.Token;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;

public class APIKeyDAO implements APIKeyDAOInterface {

    @Override
    public Token findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Token token = session.find(Token.class, id);
        session.close();

        return token;
    }

    @Override
    public List<Token> getAllTokens() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Token> tokens = session.createQuery("FROM Token", Token.class).list();
        session.close();

        return tokens;
    }

    @Override
    public Token createAPIKey(Token token) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String tokenHashed = argon2.hash(1, 1024, 1, token.getApikey());

            tokenHashed = tokenHashed.substring(0, Math.min(tokenHashed.length(), 40));

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


    @Override
    public boolean validateAPIKey(String apikey) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Token token;
            session.beginTransaction();

            Query<Token> query = session.createQuery("SELECT t FROM Token t WHERE t.apikey = :apikey", Token.class);
            query.setParameter("apikey", apikey);

            try {
                token = query.getSingleResult();
            } catch (NoResultException e) {
                e.printStackTrace();
                return false;
            }

            boolean isValid = (token != null) && token.isActive() && (token.getUses() < 20);

            if (isValid) {
                token.setUses(token.getUses() + 1);
                session.update(token);
            }

            session.getTransaction().commit();

            return isValid;

        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            return false;

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
