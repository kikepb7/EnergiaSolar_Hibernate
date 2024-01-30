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

    /**
     * Busca y devuelve un token por su identificador (ID)
     * @param id Identificador del token
     * @return Token encontrado o null en caso de no existir en la base de datos
     */
    @Override
    public Token findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Token token = session.find(Token.class, id);
        session.close();

        return token;
    }

    /**
     * Obtiene todos los registros de la entidad Token desde la base de datos
     * @return Lista de objetos Token
     */
    @Override
    public List<Token> getAllTokens() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Token> tokens = session.createQuery("FROM Token", Token.class).list();
        session.close();

        return tokens;
    }

    /**
     * Crea un nuevo token en la base de datos
     * @param token Token a crear
     * @return Token creado
     */
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

    /**
     * Actualiza un token en la base de datos
     * @param token Token a actualizar
     * @return Token actualizado
     */
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

    /**
     * Elimina un token de la base de datos por su identificador (ID)
     * @param id Identificador del token a eliminar
     * @return 'True' si se ha eliminado correctamente, 'False' si no se encuentra el token
     */
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

    /**
     * Busca y ddevuelve un token por su apikey
     * @param apikey apikey del token
     * @return Token encontrado o null en caso de no existir en la base de datos
     */
    @Override
    public Token findTokenByApiKey(String apikey) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            Query<Token> query = session.createQuery("FROM Token t WHERE t.apikey = :apikey", Token.class);
            Token token = query.setParameter("apikey", apikey).getSingleResult();

            if (token != null && token.isActive() && token.getUses() <= 20) {
                token.setUses(token.getUses() + 1);
                if (token.getUses() > 20) {
                    token.setActive(false);
                }
                session.update(token);
                session.getTransaction().commit();
                return token;
            } else {
                return null;
            }

        } catch (NoResultException e) {
            return null;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
