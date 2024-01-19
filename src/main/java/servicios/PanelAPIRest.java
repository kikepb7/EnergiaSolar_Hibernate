package servicios;

import com.appslandia.common.gson.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.APIKeyDAOInterface;
import dao.PanelDAOInterface;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import dto.panelDTO.PanelDTO;
import dto.panelDTO.PanelModelProductionDTO;
import entidades.Panel;
import entidades.Token;
import org.hibernate.Session;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.time.*;
import java.util.*;

import static spark.Spark.halt;

public class PanelAPIRest {

    // Atributtes
    private final PanelDAOInterface panelDAO;
//    private final APIKeyDAOInterface apiKeyDAO;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public PanelAPIRest(PanelDAOInterface implementation) {
        Spark.port(8080);
        panelDAO = implementation;

        /* GET */
        // Protección con Token
        Spark.before( (request, response) -> {
            String apiKey = request.headers("APIKEY");
            System.out.println(apiKey);
            if (apiKey == null && !validateAPIKEY(apiKey)){
                Spark.halt(401,"Unauthorized access");
            }
        });

        // Página de inicio
        Spark.get("/paneles_procesados", (request, response) -> {
            List<Panel> panels = panelDAO.getAllPanels();

            Map<String, Object> model = new HashMap<>();
            model.put("panels", panels);

            return new ModelAndView(model, "paneles"); // resources/templates/
        }, new ThymeleafTemplateEngine());

        // Obtener todos los paneles disponibles en la BD
//        Spark.get("/paneles", (request, response) -> {
//
//            response.type("application/json");
//            List<Panel> panels = panelDAO.getAllPanels();
//            return gson.toJson(panels);
//        });

        // Obtener las imágenes de los paneles disponibles en la BD
        Spark.get("/paneles/imagenes", (request, response) -> {

            response.type("application/json");

            List<String> images = panelDAO.getAllImages();

            if (images != null && !images.isEmpty()) {
                return gson.toJson(images);
            } else {
                response.status(404);
                return "No se encontraron paneles en la base de datos";
            }
        });

        // Obtener los paneles más caros disponibles en la BD
        Spark.get("/paneles/mas_caros", (request, response) -> {

            response.type("application/json");

            List<Panel> moreExpensive = panelDAO.getMoreExpensive();

            if (moreExpensive != null && !moreExpensive.isEmpty()) {
                return gson.toJson(moreExpensive);
            } else {
                response.status(404);
                return "No se encontraron paneles en la base de datos";
            }
        });

        // Obtener un resumen con solo el modelo y la imagen
        Spark.get("/paneles/resumen", (request, response) -> {

            response.type("application/json");

            List<PanelDTO> resume = panelDAO.getImagesName();

            if (resume != null && !resume.isEmpty()) {
                return gson.toJson(resume);
            } else {
                response.status(404);
                return "No se encontraron paneles en la base de datos";
            }
        });

        // Obtener un resumen con los modelos y la fecha de fabricación
        Spark.get("/paneles/modelos_fabricacion", (request, response) -> {

            response.type("application/json");

            List<PanelModelProductionDTO> resume = panelDAO.getModelsProduction();

            if (resume != null && !resume.isEmpty()) {
                return gson.toJson(resume);
            } else {
                response.status(404);
                return "No se encontraron paneles en la base de datos";
            }
        });

        // Obtener un panel por su ID
        Spark.get("/paneles/buscarID/:id", (request, response) -> {

            response.type("application/json");

            Long id = Long.parseLong(request.params(":id"));
            Panel panel = panelDAO.findById(id);

            if (panel != null) {
                return gson.toJson(panel);
            } else {
                response.status(404);
                return "Panel no encontrado";
            }
        });

        // Buscar paneles por modelo
        Spark.get("/paneles/buscar/:model", (request, response) -> {

            response.type("application/json");

            String model = request.params(":model");

            List<Panel> panels = panelDAO.findByModelLike(model);

            return gson.toJson(panels);
        });

        // Calcular la media de precios de los paneles
        Spark.get("/paneles/media_precios", (request, response) -> {

            response.type("application/json");

            Double average = panelDAO.avgPrices();

            if (average != null) {
                return average.toString();
            } else {
                response.status(404);
                return "No se encontraron paneles para calcular la media de precios";
            }
        });

        // Calcular la media de precios de los paneles de una marca concreta
        Spark.get("/paneles/media_precios/:brand", (request, response) -> {

            response.type("application/json");

            String brand = request.params(":brand");

            Double average = panelDAO.avgBrandPrices(brand);

            if (average != null) {
                return average.toString();
            } else {
                response.status(404);
                return "No se encontraron paneles para la marca " + brand;
            }
        });

        // Obtener los paneles de un año de fabricación concreto
        Spark.get("/paneles/fabricacion/:year", (request, response) -> {

            response.type("application/json");

            int year = Integer.parseInt(request.params(":year"));

            List<Panel> panels = panelDAO.getPanelsByMaxProductionYear(year);

            return gson.toJson(panels);
        });

        // Obtener el panel con máxima eficiencia de una marca concreta
        Spark.get("/paneles/max_eficiencia/:brand", (request, response) -> {

            response.type("application/json");

            String brand = request.params(":brand");
            Panel panel = panelDAO.getPanelMaxEfficiency(brand);

            if (panel != null) {
                return gson.toJson(panel);
            } else {
                response.status(404);
                return "Panel de la marca " + brand + " no encontrado";
            }
        });

        // Buscar paneles entre precios
        Spark.get("/paneles/buscar/:min/:max", (request, response) -> {

            response.type("application/json");

            Double min = Double.parseDouble(request.params(":min"));
            Double max = Double.parseDouble(request.params(":max"));

            List<Panel> panels = panelDAO.findBetweenPrices(min, max);

            return gson.toJson(panels);
        });

        // Buscar paneles entre precios de una marca concreta
        Spark.get("/paneles/buscar/:min/:max/:brand", (request, response) -> {

            response.type("application/json");

            Double min = Double.parseDouble(request.params(":min"));
            Double max = Double.parseDouble(request.params(":max"));
            String brand = request.params(":brand");

            List<Panel> panels = panelDAO.findBetweenBrandPrices(min, max, brand);

            return gson.toJson(panels);
        });

        // Buscar paneles entre potencias de una categoría concreta
        Spark.get("/paneles/buscar/potencia/:min/:max/:category", (request, response) -> {

            response.type("application/json");

            Integer min = Integer.parseInt(request.params(":min"));
            Integer max = Integer.parseInt(request.params(":max"));
            String category = request.params(":category");

            List<Panel> panels = panelDAO.findBetweenCategoryPower(min, max, category);

            return gson.toJson(panels);
        });

        // Buscar paneles entre precios de varias marcas
        Spark.get("/paneles/buscar_varias/:min/:max/:listbrands", (request, response) -> {

            response.type("application/json");

            Double min = Double.parseDouble(request.params(":min"));
            Double max = Double.parseDouble(request.params(":max"));
            String brandsParam = request.params(":listbrands");

            List<String> brands = Arrays.asList(brandsParam.split("-"));

            List<Panel> panels = panelDAO.findBetweenBrandsPrices(min, max, brands);
            return gson.toJson(panels);
        });



        /* POST */
        // Endpoint para crear un panel con todos los datos
        Spark.post("/paneles/registrar", (request, response) -> {

            String body = request.body();
            Panel newPanel = gson.fromJson(body, Panel.class);

            Panel created = panelDAO.create(newPanel);

            return gson.toJson(created);
        });

        Spark.get("paneles/registrar", (request, response) -> {

            List<Panel> panels = panelDAO.getAllPanels();
            Panel panel = new Panel();

            Map<String, Object> model = new HashMap<>();
            model.put("panels", panels);
            model.put("panel", panel);

            return new ModelAndView(model, "registrar");
        }, new ThymeleafTemplateEngine());


        // PUT
        // Endpoint para actualizar un panel por su ID
        Spark.put("paneles/editar/:id", (request, response) -> {

            Long id = Long.parseLong(request.params(":id"));
            String body = request.body();

            Panel panel = gson.fromJson(body, Panel.class);
            panel.setId(id);

            Panel updatedPanel = panelDAO.update(panel);
            if (updatedPanel != null) {
                return gson.toJson(updatedPanel);
            } else {
                response.status(404);
                return "Panel no encontrado";
            }
        });

        Spark.get("/paneles/editar/:id", (request, response) -> {

            Long id = Long.parseLong(request.params(":id"));
            Panel panel = panelDAO.findById(id);

            if (panel != null) {
                Map<String, Object> model = new HashMap<>();
                model.put("panel", panel);
                return new ModelAndView(model, "editar");
            } else {
                response.status(404);
                return null;
            }
        }, new ThymeleafTemplateEngine());


        /* DELETE */
        // Endpoint para eliminar un panel por su ID
        Spark.delete("/paneles/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            boolean isDeleted = panelDAO.deleteById(id);

            if (isDeleted) {
                return "Panel eliminado correctamente";
            } else {
                response.status(404);
                return "Panel no encontrado";
            }
        });


        /* ENDPOINT INCORRECTO */
        Spark.notFound((request, response) -> {
            response.type("application/json");
            return "{\"error\": \"Ruta no encontrada\"," +
                    "\"hint 1\": \"/paneles\"," +
                    "\"hint 2\": \"/paneles/resumen\"," +
                    "\"hint 3\": \"/paneles/imagenes\"," +
                    "\"hint 4\": \"/paneles/mas_caros\"," +
                    "\"hint 5\": \"/paneles/buscarID/:id\"," +
                    "\"hint 6\": \"/paneles/buscar/:model\"," +
                    "\"hint 7\": \"/paneles/media_precios\"," +
                    "\"hint 8\": \"/paneles/buscar/:min/:max\"," +
                    "\"hint 9\": \"/paneles/fabricacion/:year\"," +
                    "\"hint 10\": \"/paneles/modelos_fabricacion\"," +
                    "\"hint 11\": \"/paneles/media_precios/:brand\"," +
                    "\"hint 12\": \"/paneles/max_eficiencia/:brand\"," +
                    "\"hint 13\": \"/paneles/paginado/:page/:amount\"," +
                    "\"hint 14\": \"/paneles/buscar/:min/:max/:brand\"," +
                    "\"hint 15\": \"/paneles/buscar/potencia/:min/:max/:category\"," +
                    "\"hint 16\": \"/paneles/buscar_varias/:min/:max/:listbrands\"}";
        });


        /* PAGINACIÓN DE DATOS DE TODOS LOS PANELES */
        Spark.get("/paneles/paginado/:page/:amount", (request, response) -> {

            response.type("application/json");

            Integer page = Integer.parseInt(request.params("page"));
            Integer amount = Integer.parseInt(request.params("amount"));

            List<Panel> panels = panelDAO.getAll(page, amount);
            Long totalElements = panelDAO.totalPanels();
            RespuestaPaginacion<Panel> pageResult = new RespuestaPaginacion<>(panels, totalElements, page, amount);

            return gson.toJson(pageResult);
        });
    }

    /*private boolean validateAPIKey(String apiKey) {
        APIKey token = APIKeyDAO.createAPIKey(apiKey);
        return key != null && key.isActiva() && key.getNumUsos() > 0;
    }*/

    private boolean validateAPIKEY(String apiKey) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("SELECT t FROM Token t WHERE t.apikey = :apikey", Token.class);
            query.setParameter("apikey", apiKey);

            Token token;
            try {
                token = (Token) query.getSingleResult();


            } catch (NoResultException e) {
                e.printStackTrace();
                return false;
            }

            boolean isValid = (token != null) && token.isActive();

            session.getTransaction().commit();
            session.close();

            return isValid;

        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
    }
}
