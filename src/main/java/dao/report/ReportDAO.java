package dao.report;

import entidades.Panel;
import entidades.Report;
import entidades.User;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO implements ReportDAOInterface {

    @Override
    public Report createReport(Report r) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(r);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();

        return r;
    }

    @Override
    public Report findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Report r = session.find(Report.class, id);
        session.close();

        return r;
    }

    @Override
    public Report update(Report report) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.update(report);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();

        return report;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            Report report = this.findById(id);

            if (report != null) {
                session.delete(report);
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