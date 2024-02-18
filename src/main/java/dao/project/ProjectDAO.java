package dao.project;

import entidades.Project;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.PersistenceException;

public class ProjectDAO implements ProjectDAOInterface {
    @Override
    public Project createProject(Project p) {
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
    public Project findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Project p = session.find(Project.class, id);
        session.close();

        return p;
    }
}
