package dao.report;

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
}