package dao.associations;

import entidades.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class AssociationsDAO implements AssociationsDAOInterface {

    @Override
    public boolean assignReportUser(Report r, User u) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            r.setUser(u);
            session.update(r);
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
    public boolean assignProjectUser(Project pr, User u) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            pr.setUser(u);
            session.update(pr);
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
    public boolean assignPanelProject(Panel p, Project pr) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            List<Panel> panel_list = installedPanels(pr);
            panel_list.add(p);
            pr.setPanels(panel_list);

            List<Project> project_list = projectPanels(p);
            project_list.add(pr);
            p.setProjects(project_list);

            session.beginTransaction();

            session.update(p);
            session.update(pr);
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
    public boolean assignCalculationProject(Calculation c, Project pr) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            c.setProject(pr);
            session.update(c);
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
    public User getUserReport(Report r) {
        User user;

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Report> query = session.createQuery("SELECT r FROM Report r JOIN FETCH r.user WHERE r.id =:id", Report.class);
            query.setParameter("id", r.getId());

            user = query.getSingleResult().getUser();
        } catch (NoResultException nre) {
            user = null;
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public List<Report> getReportsUser(User u) {
        List<Report> listReports;

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<User> query = session.createQuery("SELECT u FROM User u JOIN FETCH u.reports WHERE u.id =:id", User.class);
            query.setParameter("id", u.getId());

            listReports = query.getSingleResult().getReports();
        } catch (NoResultException nre) {
            listReports = new ArrayList<>();
        } catch (NullPointerException npe) {
            listReports = null;
        } finally {
            session.close();
        }

        return listReports;
    }

    @Override
    public User getUserProject(Project p) {
        User user;

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Project> query = session.createQuery("SELECT p FROM Project p JOIN FETCH p.user WHERE p.id =:id", Project.class);
            query.setParameter("id", p.getId());

            user = query.getSingleResult().getUser();
        } catch (NoResultException nre) {
            user = null;
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public List<Project> getProjectsUser(User u) {
        List<Project> listProjects;

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<User> query = session.createQuery("SELECT u FROM User u JOIN FETCH u.projects WHERE u.id =:id", User.class);
            query.setParameter("id", u.getId());

            listProjects = query.getSingleResult().getProjects();
        } catch (NoResultException nre) {
            listProjects = new ArrayList<>();
        } catch (NullPointerException npe) {
            listProjects = null;
        } finally {
            session.close();
        }

        return listProjects;
    }

    @Override
    public List<Project> projectPanels(Panel p) {
        List<Project> listProjects;

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Panel> query = session.createQuery("SELECT p FROM Panel p JOIN FETCH p.projects WHERE p.id =:id", Panel.class);
            query.setParameter("id", p.getId());

            listProjects = query.getSingleResult().getProjects();

        } catch (NoResultException nre) {
            listProjects = new ArrayList<>();
        } catch (NullPointerException npe) {
            listProjects = null;
        } finally {
            session.close();
        }

        return listProjects;
    }

    @Override
    public List<Panel> installedPanels(Project pr) {
        List<Panel> listPanels;

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Project> query = session.createQuery("SELECT pr FROM Project pr JOIN FETCH pr.panels WHERE pr.id =:id", Project.class);
            query.setParameter("id", pr.getId());

            listPanels = query.getSingleResult().getPanels();

        } catch (NoResultException nre) {
            listPanels = new ArrayList<>();
        } catch (NullPointerException npe) {
            listPanels = null;
        } finally {
            session.close();
        }

        return listPanels;
    }

    @Override
    public Project getProjectCaltulation(Calculation c) {
        Project project;

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Calculation> query = session.createQuery("SELECT c FROM Calculation c JOIN FETCH c.project WHERE c.id =:id", Calculation.class);
            query.setParameter("id", c.getId());
            project = query.getSingleResult().getProject();
        } catch (NoResultException nre) {
            project = null;
        } finally {
            session.close();
        }

        return project;
    }

    @Override
    public List<Calculation> doneCalculations(Project pr) {
        List<Calculation> listCalculations;

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Project> query = session.createQuery("SELECT pr FROM Project pr JOIN FETCH pr.calculations WHERE pr.id =:id", Project.class);
            query.setParameter("id", pr.getId());

            listCalculations = query.getSingleResult().getCalculations();

        } catch (NoResultException nre) {
            listCalculations = new ArrayList<>();
        } catch (NullPointerException npe) {
            listCalculations = null;
        } finally {
            session.close();
        }

        return listCalculations;
    }
}
