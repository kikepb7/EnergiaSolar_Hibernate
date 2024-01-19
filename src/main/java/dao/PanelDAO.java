package dao;

import dto.panelDTO.PanelDTO;
import dto.panelDTO.PanelModelProductionDTO;
import entidades.Panel;
import org.hibernate.Session;
import util.HibernateUtil;
import org.hibernate.query.Query;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class PanelDAO implements PanelDAOInterface {
    /**
     * Obtiene todos los registros de la entidad Panel desde la base de datos
     * @return Lista de objetos Panel
     */
    @Override
    public List<Panel> getAllPanels() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Panel> allPanels = session.createQuery("FROM Panel", Panel.class).list();
        session.close();

        return allPanels;
    }

    /**
     * Obtiene una página específica de registros de la entidad Panel desde la base de datos
     * @param page      Número de página
     * @param amount    Cantidad de registros por página
     * @return Lista de objetos Panel para la página especificada
     */
    @Override
    public List<Panel> getAll(int page, int amount) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Panel> query = session.createQuery("FROM Panel", Panel.class);
        query.setMaxResults(amount);
        query.setFirstResult((page - 1) * amount);
        List<Panel> allPanels = query.list();

        session.close();

        return allPanels;
    }

    /**
     * Lista de paneles cuyo precio es mayor a 200 desde la base de datos
     * @return Lista de objetos Panel que cumplen con la condición de precio
     */
    @Override
    public List<Panel> getMoreExpensive() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Panel> moreExpensive = session.createQuery("FROM Panel p WHERE p.price > 200", Panel.class).list();
        session.close();

        return moreExpensive;
    }

    /**
     * Lista de rutas de imágenes asociadas a los paneles desde la base de datos
     * @return Lista de enlaces de las imágenes
     */
    @Override
    public List<String> getAllImages() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<String> images = session.createQuery("SELECT p.image FROM Panel p", String.class).list();
        session.close();

        return images;
    }

    /**
     * Obtiene una lsitta de DTO que contiene información de modelo e imágenes de los paneles desde la base de datos
     * @return Lista de objetos PanelDTO
     */
    @Override
    public List<PanelDTO> getImagesName() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<PanelDTO> panels = session.createQuery("SELECT NEW dto.panelDTO.PanelDTO(p.model, p.image) FROM Panel p", PanelDTO.class).list();
        session.close();

        return panels;
    }

    /**
     * Obtiene una lista de DTO que contiene información de modelo y fecha de producción de los paneles desde la base de datos
     * @return Lista de objetos PanelModelProductionDTO
     */
    @Override
    public List<PanelModelProductionDTO> getModelsProduction() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<PanelModelProductionDTO> panels = session.createQuery("SELECT NEW dto.panelDTO.PanelModelProductionDTO(p.model, p.productionDate) FROM Panel p", PanelModelProductionDTO.class).list();
        session.close();

        return panels;
    }

    /**
     * Obtiene el número total de paneles en la base de datos
     * @return Número total de paneles
     */
    @Override
    public Long totalPanels() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Long counter = (Long) session.createQuery("SELECT COUNT(p) FROM Panel p").getSingleResult();
        session.close();

        return counter;
    }

    /**
     * Busca y devuelve un panel por su identificador (ID)
     * @param id Identificador del panel
     * @return Panel encontrado o null en caso de no existir en la base de datos
     */
    @Override
    public Panel findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Panel panel = session.find(Panel.class, id);
        session.close();

        return panel;
    }

    /**
     * Calcula y devuelve el precio promedio de todos los paneles
     * @return Precio promeddio de todos los paneles
     */
    @Override
    public Double avgPrices() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Double average = session.createQuery("SELECT AVG(p.price) FROM Panel p", Double.class).getSingleResult();
        session.close();

        return average;
    }

    /**
     * Calcula y devuelve el precio promedio de los paneles de una marca específica
     * @param brand Marca de los paneles para calcular el precio promedio
     * @return Precio promedio de los paneles de la marca específica
     */
    @Override
    public Double avgBrandPrices(String brand) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Double> query = session.createQuery("SELECT AVG(p.price) FROM Panel p WHERE p.brand = :brand", Double.class);
        query.setParameter("brand", brand);
        Double average = (query.getSingleResult());

        session.close();

        return average;
    }

    /**
     * Obtiene una lista de paneles producidos en un año específico
     * @param year Año para filtrar los paneles
     * @return Lista de objetos Panel producidos en el año específicado
     */
    @Override
    public List<Panel> getPanelsByMaxProductionYear(int year) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Panel> query = session.createQuery("FROM Panel p WHERE YEAR(p.productionDate) = :year", Panel.class);
        query.setParameter("year", year);

        List<Panel> panels = query.getResultList();

        session.close();

        return panels;
    }

    /**
     * Obtiene el panel más eficiente de una marca específica
     * @param brand Marca de los paneles para buscar el más eficiente
     * @return Panel más eficiente de la marca especificada
     */
    @Override
    public Panel getPanelMaxEfficiency(String brand) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query<Panel> query = session.createQuery("FROM Panel p WHERE p.brand = :brand ORDER BY p.efficiency DESC", Panel.class);
            query.setParameter("brand", brand);
            query.setMaxResults(1);
            return query.uniqueResult();
    }

    /**
     * Busca y devuelve una lsita de paneles cuyo modelo coincide parcialmente con el proporcionado por parámetro
     * @param model Cadena de búsqueda para el modelo
     * @return Lista de objetos Panel cuyo modelo coincide parcialmente con el proporcionado
     */
    @Override
    public List<Panel> findByModelLike(String model) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Panel> query = session.createQuery("FROM Panel p WHERE p.model like :model", Panel.class);

        List<Panel> filter = query.setParameter("model", "%" + model + "%").list();
        session.close();

        return filter;
    }

    /**
     * Busca y devuelve una lista de paneles cuya categoría coincide parcialmente con la proporcionada por parámetro
     * @param category Cadena de búsqueda para la categoría
     * @return Lista de oobjetos Panel cuya categoría coincide parcialmente con la proporcionada
     */
    @Override
    public List<Panel> findByCategoryLike(String category) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Panel> query = session.createQuery("FROM Panel p WHERE p.category like :category", Panel.class);

        List<Panel> filter = query.setParameter("category", "%" + category + "%").list();
        session.close();

        return filter;
    }

    /**
     * Busca y devuelve una lista de paneles cuyos precios se encuentran en el rango especificado
     * @param min Valor mínimo del rango de precios
     * @param max Valor máximo del rango de precios
     * @return Lista de objetos Panel cuyos precios están en el rango especificado
     */
    @Override
    public List<Panel> findBetweenPrices(Double min, Double max) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Panel> query = session.createQuery("FROM Panel p WHERE p.price >= :min AND p.price <= :max", Panel.class);
        query.setParameter("min", min);
        query.setParameter("max", max);

        List<Panel> filter = query.list();
        session.close();

        return filter;
    }

    /**
     * Busca y devuelve una lista de paneles cuyos precios se encuentran en el rango especificado y pertenecen a una marca específica
     * @param min Valor mínimo del rango de precios
     * @param max Valor máximo del rango de precios
     * @param brand Marca de los paneles para filtrar
     * @return Lista de objetos Panel cuyos precios están en el rango especificado y pertenecen a la marca proporcionada
     */
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

    /**
     * Busca y devuelve una lista de paneles cuya potencia nominal se encuentre en el rango especificado y pertenecen
     * a una categoría específica
     * @param min Valor mínimo del rango de potencia nominal
     * @param max Valor máximo del rango de potencia nominal
     * @param category Categoría de los paneles para filtrar
     * @return Lista de objetos Panel cuya potencia nominal está en el rango especificado y pertenecen a la categoría
     * especificada
     */
    @Override
    public List<Panel> findBetweenCategoryPower(Integer min, Integer max, String category) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Panel> filter = new ArrayList<>();

        try {
            Query<Panel> query = session.createQuery("FROM Panel p WHERE p.nominalPower >= :min AND p.nominalPower <= :max AND p.category = :category", Panel.class);
            query.setParameter("min", min);
            query.setParameter("max", max);
            query.setParameter("category", category);

            filter = query.list();
            System.out.println(filter);
            session.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return filter;
    }

    /**
     * Busca y devuelve una lista de paneles cuyos precios están en el rango especificado y pertenecen a marcas específicas
     * @param min Valor mínimo del rango de precios
     * @param max Valor máximo del rango de precios
     * @param brands Lista de marcas para filtrar
     * @return Lista de objetos Panel cuyos precios están en el rango especificado y pertenecen a las marcas proporcionadas
     */
    @Override
    public List<Panel> findBetweenBrandsPrices(Double min, Double max, List<String> brands) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Panel> query = session.createQuery("FROM Panel p WHERE p.price >= :min AND p.price <= :max AND p.brand IN (:brands)", Panel.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        query.setParameterList("brands", brands);

        List<Panel> filter = query.list();
        session.close();

        return filter;
    }

    /**
     * Crea un nuevo panel en la base de datos
     * @param panel Panel a crear
     * @return Panel creado
     */
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

    /**
     * Actualiza un panel en la base de datos
     * @param panel Panel a actualizar
     * @return Panel actualizado
     */
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

    /**
     * Elimina un panel de la base de datos por su identificador (ID)
     * @param id Identificador del panel a eliminar
     * @return 'True' si se ha eliminado correctamente, 'False' si no se encuentra el panel
     */
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

    /**
     * Elimina todos los registros de la entidad Panel en la base de datos
     * @return 'True' si se han eliminado correctamente, 'False' en caso contrario
     */
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
