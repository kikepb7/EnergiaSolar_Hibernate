package dao;

import dto.PanelDTO;
import entidades.Panel;
import org.hibernate.Session;
import util.HibernateUtil;
import org.hibernate.query.Query;
import javax.persistence.PersistenceException;
import java.util.List;

public class PanelDAO implements PanelDAOInterface {
    @Override
    public List<Panel> getAllPanels() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Panel> allPanels = session.createQuery("FROM Panel", Panel.class).list();
        session.close();

        return allPanels;
    }

    @Override
    public List<Panel> getAll(int page, int amount) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("FROM Panel", Panel.class);
        query.setMaxResults(amount);
        query.setFirstResult((page - 1) * amount);
        List<Panel> allPanels = query.list();

        session.close();

        return allPanels;
    }

    @Override
    public List<Panel> getMoreExpensive() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Panel> moreExpensive = session.createQuery("FROM Panel p WHERE p.price > 200", Panel.class).list();
        session.close();

        return moreExpensive;
    }

    @Override
    public List<String> getAllImages() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<String> images = session.createQuery("SELECT p.image FROM Panel p", String.class).list();
        session.close();

        return images;
    }

    @Override
    public List<PanelDTO> getImagesName() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<PanelDTO> panels = session.createQuery("SELECT NEW dto.PanelDTO(p.model, p.image) FROM Panel p", PanelDTO.class).list();
        session.close();

        return panels;
    }

    @Override
    public List<PanelDTO> getModelsProduction() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<PanelDTO> panels = session.createQuery("SELECT NEW dto.PanelDTO(p.model, p.productionDate) FROM Panel p", PanelDTO.class).list();
        session.close();

        return panels;
    }

    @Override
    public Long totalPanels() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Long counter = (Long) session.createQuery("SELECT COUNT(p) FROM Panel p").getSingleResult();
        session.close();

        return counter;
    }

    @Override
    public Panel findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Panel panel = session.find(Panel.class, id);
        session.close();

        return panel;
    }

    @Override
    public Double avgPrices() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Double average = session.createQuery("SELECT AVG(p.price) FROM Panel p", Double.class).getSingleResult();
        session.close();

        return null;
    }

    @Override
    public Double avgBrandPrices(String brand) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Double> query = session.createQuery("SELECT AVG(p.price) FROM Panel p WHERE p.brand = :brand", Double.class);
        query.setParameter("brand", brand);
        Double average = (query.getSingleResult());

        session.close();

        return average;
    }

    @Override
    public List<Panel> getPanelsByMaxProductionYear(int year) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Panel> query = session.createQuery("FROM Panel p WHERE YEAR(p.productionDate) = :year", Panel.class);
        query.setParameter("year", year);

        List<Panel> panels = query.getResultList();

        session.close();

        return panels;
    }

    @Override
    public Panel getPanelMaxEfficiency(String brand) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query<Panel> query = session.createQuery("FROM Panel p WHERE p.brand = :brand ORDER BY p.efficiency DESC", Panel.class);
            query.setParameter("brand", brand);
            query.setMaxResults(1);
            return query.uniqueResult();
    }

    @Override
    public List<Panel> findByModelLike(String model) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Panel> query = session.createQuery("FROM Panel p WHERE p.model like :model", Panel.class);

        List<Panel> filter = query.setParameter("model", "%" + model + "%").list();
        session.close();

        return filter;
    }

    @Override
    public List<Panel> findByCategoryLike(String category) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Panel> query = session.createQuery("FROM Panel p WHERE p.category like :category", Panel.class);

        List<Panel> filter = query.setParameter("category", "%" + category + "%").list();
        session.close();

        return filter;
    }

    @Override
    public List<Panel> findBetweenPrices(Double min, Double max) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Panel> query = session.createQuery("FROM Panel p WHERE p.price >= :min AND m.price <= :max", Panel.class);
        query.setParameter("min", min);
        query.setParameter("max", max);

        List<Panel> filter = query.list();
        session.close();

        return filter;
    }

    @Override
    public List<Panel> findBetweenBrandPrices(Double min, Double max, String brand) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Panel> query = session.createQuery("FROM Panel p WHERE p.price >= :min AND p.price <= :max AND p.brand = :brand", Panel.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        query.setParameter("brand", brand);

        List<Panel> filter = query.list();
        session.close();

        return filter;
    }

    @Override
    public List<Panel> findBetweenCategoryPower(Double min, Double max, String category) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Panel> query = session.createQuery("FROM Panel p WHERE p.nominal_power >= :min AND p.nominal_power <= :max AND p.category = :category", Panel.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        query.setParameter("category", category);

        List<Panel> filter = query.list();
        session.close();

        return filter;
    }

    @Override
    public List<Panel> findBetweenBrandPrices(Double min, Double max, List<String> brands) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Panel> query = session.createQuery("FROM Panel p WHERE p.price >= :min AND p.price <= :max AND p.brand IN (:brands)", Panel.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        query.setParameterList("brand", brands);

        List<Panel> filter = query.list();
        session.close();

        return filter;
    }

    @Override
    public Panel create(Panel panel) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(panel);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();

        return panel;
    }

    @Override
    public Panel update(Panel panel) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.update(panel);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();

        return panel;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            Panel panel = this.findById(id);

            if (panel != null) {
                session.delete(panel);
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
    public boolean deleteAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            String deleteQuery = "DELETE FROM Panel";
            Query query = session.createQuery(deleteQuery);
            query.executeUpdate();

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
