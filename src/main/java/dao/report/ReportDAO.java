package dao.report;

import entidades.Report;
import entidades.User;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO implements ReportDAOInterface {
    @Override
    public Report findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Report report = session.find(Report.class, id);
        session.close();

        return report;
    }

    @Override
    public boolean assignUser(Report r, User userId) {
        return false;
    }

    @Override
    public List<Report> getReportsUser(User u) {
        List<Report> reports = null;

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Report> query = session.createQuery("SELECT r FROM Report r JOIN fetch r.user_id WHERE r.id = :id", Report.class);
            query.setParameter("id", u.getId());
            reports = query.getResultList()
        } catch (NoResultException nre) {
            reports = new ArrayList<>();
        }

        session.close();

        return reports;
    }
}