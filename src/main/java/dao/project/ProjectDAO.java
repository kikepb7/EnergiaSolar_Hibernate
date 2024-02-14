package dao.project;

import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.PersistenceException;

public class ProjectDAO implements ProjectDAOInterface {
    @Override
    public ProjectDAO createProject(ProjectDAO p) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(p);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return p;
    }

    @Override
    public ProjectDAO findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        ProjectDAO p = session.find(ProjectDAO.class, id);
        session.close();

        return p;
    }
}
