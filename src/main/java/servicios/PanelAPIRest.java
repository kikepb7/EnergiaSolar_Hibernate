package servicios;

import com.appslandia.common.gson.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.PanelDAOInterface;
import dao.security.APIKeyDAOInterface;
import dto.panelDTO.PanelDTO;
import dto.panelDTO.ModelPricePowerDTO;
import entidades.Panel;
import entidades.Token;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.before;

public class PanelAPIRest {

    // 1. Atributtes
    private final PanelDAOInterface panelDAO;
    private final APIKeyDAOInterface tokenDAO;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();


    // 2. Constructor
    public PanelAPIRest(PanelDAOInterface implementation, APIKeyDAOInterface impl) {
        Spark.port(8080);
        panelDAO = implementation;
        tokenDAO = impl;

        /* PROTECCIÓN CON TOKEN */
        /*Spark.before("paneles/*", (request, response) -> {
            String apikey = request.headers("token");   // Valor de Key en Postman

            Token token = tokenDAO.findTokenByApiKey(apikey);

            if (token == null || !token.isActive()) {
                Spark.halt(403, "Token is not active");
            }

            String requestMethod = request.requestMethod();
            if (token != null) {
                if ((requestMethod.equals("GET") && !token.isAllowRead()) ||
                        (
                                (
                                        requestMethod.equals("POST") ||
                                                requestMethod.equals("PUT") ||
                                                requestMethod.equals("DELETE")
                                ) && token.isAllowRead()
                        )
                ) {
                    Spark.halt(403, "Operation not allowed");
                }
            }
        });

        Spark.before("token/*", (request, response) -> {
            String apikey = request.headers("token");   // Valor de Key en Postman

            Token token = tokenDAO.findTokenByApiKey(apikey);

            if (token == null || !token.isActive()) {
                Spark.halt(403, "Token is not active");
            }

            String requestMethod = request.requestMethod();
            if (token != null) {
                if ((requestMethod.equals("GET") && !token.isAllowRead()) ||
                        (
                                (
                                        requestMethod.equals("POST") ||
                                        requestMethod.equals("PUT") ||
                                        requestMethod.equals("DELETE")
                                ) && token.isAllowRead()
                        )
                ) {
                    Spark.halt(403, "Operation not allowed");
                }
            }
        });*/


        // ---------------------------------------------------------------------------------------- //


        /* GET */
        // Página de inicio
        Spark.get("/paneles_procesados", (request, response) -> {
            List<Panel> panels = panelDAO.getAllPanels();

            Map<String, Object> model = new HashMap<>();
            model.put("panels", panels);

            return new ModelAndView(model, "paneles"); // --> resources/templates/
        }, new ThymeleafTemplateEngine());

        // Obtener todos los paneles disponibles en la BD
        Spark.get("/paneles/mostrar", (request, response) -> {

            response.type("application/json");
            List<Panel> panels = panelDAO.getAllPanels();
            return gson.toJson(panels);
        });

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
        Spark.get("/paneles/modelos_potencia_precio", (request, response) -> {

            response.type("application/json");

            List<ModelPricePowerDTO> resume = panelDAO.getModelsPricePower();

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

        // Buscar paneles por categoría
        Spark.get("/paneles/buscar_categoria/:category", (request, response) -> {

            response.type("application/json");

            String category = request.params(":category");

            List<Panel> panels = panelDAO.findByCategoryLike(category);

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


        // ---------------------------------------------------------------------------------------- //


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


        // ---------------------------------------------------------------------------------------- //


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


        // ---------------------------------------------------------------------------------------- //


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


        // ---------------------------------------------------------------------------------------- //


        /* ENDPOINT INCORRECTO */
        Spark.notFound((request, response) -> {
            response.type("application/json");
            return "{\"error\": \"Ruta no encontrada\"," +
                    "\"hint 1\": \"/paneles/resumen\"," +
                    "\"hint 2\": \"/paneles/mostrar\"," +
                    "\"hint 3\": \"/paneles/imagenes\"," +
                    "\"hint 4\": \"/paneles/mas_caros\"," +
                    "\"hint 5\": \"/paneles/buscarID/:id\"," +
                    "\"hint 6\": \"/paneles/buscar/:model\"," +
                    "\"hint 7\": \"/paneles/media_precios\"," +
                    "\"hint 8\": \"/paneles/buscar/:min/:max\"," +
                    "\"hint 9\": \"/paneles/fabricacion/:year\"," +
                    "\"hint 10\": \"/paneles/media_precios/:brand\"," +
                    "\"hint 11\": \"/paneles/max_eficiencia/:brand\"," +
                    "\"hint 12\": \"/paneles/paginado/:page/:amount\"," +
                    "\"hint 13\": \"/paneles/modelos_potencia_precio\"," +
                    "\"hint 14\": \"/paneles/buscar/:min/:max/:brand\"," +
                    "\"hint 15\": \"/paneles/buscar_categoria/:category\"," +
                    "\"hint 16\": \"/paneles/buscar/potencia/:min/:max/:category\"," +
                    "\"hint 17\": \"/paneles/buscar_varias/:min/:max/:listbrands\"}";
        });


        // ---------------------------------------------------------------------------------------- //


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


        // ---------------------------------------------------------------------------------------- //


        /* APIKEYS */
        // Crear nuevos tokens de verificación
        Spark.post("/crear_token", (request, response) -> {
            String body = request.body();
            Token newToken = gson.fromJson(body, Token.class);

            Token created = tokenDAO.createAPIKey(newToken);

            return gson.toJson(created);
        });

        // Mostrar todos los tokens actuales y su situación
        Spark.get("/token/mostrar", (request, response) -> {
            response.type("application/json");
            List<Token> tokens = tokenDAO.getAllTokens();
            return gson.toJson(tokens);
        });

        // Modificar parámetros de un token
        Spark.put("/token/editar/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            String body = request.body();

            Token token = gson.fromJson(body, Token.class);
            token.setId(id);

            Token updatedToken = tokenDAO.update(token);
            if (updatedToken != null) {
                return gson.toJson(updatedToken);
            } else {
                response.status(404);
                return "Token no encontrado";
            }
        });

        // Eliminar un token por su ID
        Spark.delete("token/eliminar/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            boolean isDeleted = tokenDAO.deleteById(id);

            if (isDeleted) {
                return "Token eliminado correctamente";
            } else {
                response.status(404);
                return "Token no encontrado";
            }
        });
    }
}
