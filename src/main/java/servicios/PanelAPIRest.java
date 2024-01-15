package servicios;

import com.appslandia.common.gson.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.PanelDAOInterface;
import dto.panelDTO.PanelDTO;
import dto.panelDTO.PanelModelProductionDTO;
import entidades.Panel;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PanelAPIRest {
    private PanelDAOInterface panelDAO;
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public PanelAPIRest(PanelDAOInterface implementation) {
        Spark.port(8080);
        panelDAO = implementation;

        /* GET */
        // Endpoint para la página de inicio
        Spark.get("/paneles", (request, response) -> {
            List<Panel> panels = panelDAO.getAllPanels();

            Map<String, Object> model = new HashMap<>();
            model.put("panels", panels);

            return new ModelAndView(model, "paneles"); // resources/templates/
        }, new ThymeleafTemplateEngine());

        // Endpoint para obtener todos los paneles disponibles en la BD
        /*Spark.get("/paneles", (request, response) -> {
           List<Panel> panels = panelDAO.getAllPanels();
           return gson.toJson(panels);
        });*/

        // Endpoint para obtener los paneles más caros disponibles en la BD
        Spark.get("/paneles/mas_caros", (request, response) -> {
            try {
                List<Panel> moreExpensive = panelDAO.getMoreExpensive();

                if (moreExpensive != null && !moreExpensive.isEmpty()) {
                    return gson.toJson(moreExpensive);
                } else {
                    response.status(404);
                    return "No se encontraron paneles en la base de datos";
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }
        });

        // Endpoint para obtener las imágenes de los paneles disponibles en la BD
        Spark.get("/paneles/imagenes", (request, response) -> {
            try {
                List<String> images = panelDAO.getAllImages();

                if (images != null && !images.isEmpty()) {
                    return gson.toJson(images);
                } else {
                    response.status(404);
                    return "No se encontraron paneles en la base de datos";
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }
        });

        // Endpoint para obtener un resumen con solo el modelo y la imagen
        Spark.get("/paneles/resumen", (request, response) -> {
            try {
                List<PanelDTO> resume = panelDAO.getImagesName();

                if (resume != null && !resume.isEmpty()) {
                    return gson.toJson(resume);
                } else {
                    response.status(404);
                    return "No se encontraron paneles en la base de datos";
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }
        });

        // Endpoint para obtener un resumen con los modelos y la fecha de fabricación
        Spark.get("/paneles/modelos_fabricacion", (request, response) -> {
            try {
                List<PanelModelProductionDTO> resume = panelDAO.getModelsProduction();

                if (resume != null && !resume.isEmpty()) {
                    return gson.toJson(resume);
                } else {
                    response.status(404);
                    return "No se encontraron paneles en la base de datos";
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }

        });

        // Endpoint para calcular la media de precios de los paneles
        Spark.get("/paneles/media_precios", (request, response) -> {
            try {
                Double average = panelDAO.avgPrices();

                if (average != null) {
                    return average.toString();
                } else {
                    response.status(404);
                    return "No se encontraron paneles para calcular la media de precios";
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }
        });

        // Endpoint para obtener un panel por su ID
        Spark.get("/paneles/buscarID/:id", (request, response) -> {
            try {
                Long id = Long.parseLong(request.params(":id"));
                Panel panel = panelDAO.findById(id);

                if (panel != null) {
                    return gson.toJson(panel);
                } else {
                    response.status(404);
                    return "Panel no encontrado";
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return "Error en el formato del ID";
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }
        });

        // Endpoint para calcular la media de precios de los paneles de una marca concreta
        Spark.get("/paneles/media_precios/:brand", (request, response) -> {
            try {
                String brand = request.params(":brand");

                Double average = panelDAO.avgBrandPrices(brand);

                if (average != null) {
                    return average.toString();
                } else {
                    response.status(404);
                    return "No se encontraron paneles para la marca " + brand;
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }
        });

        // Endpoint para obtener los paneles de un año de fabricación concreto
        Spark.get("/paneles/fabricacion/:year", (request, response) -> {
            try {
                int year = Integer.parseInt(request.params(":year"));

                List<Panel> panels = panelDAO.getPanelsByMaxProductionYear(year);

                return gson.toJson(panels);
            } catch (NumberFormatException e) {
                response.status(400);
                return "Error en el formato del año";
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }
        });

        // Endpoint para obtener el panel con máxima eficiencia de una marca concreta
        Spark.get("/paneles/max_eficiencia/:brand", (request, response) -> {
            try {
                String brand = request.params(":brand");
                Panel panel = panelDAO.getPanelMaxEfficiency(brand);

                if (panel != null) {
                    return gson.toJson(panel);
                } else {
                    response.status(404);
                    return "Panel de la marca " + brand + " no encontrado";
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }
        });

        // Endpoint para buscar paneles por nombre
        Spark.get("/paneles/buscar/:model", (request, response) -> {
            try {
                String model = request.params(":model");

                List<Panel> panels = panelDAO.findByModelLike(model);

                return gson.toJson(panels);
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }
        });

        // Endpoint para buscar paneles entre precios
        Spark.get("/paneles/buscar/:min/:max", (request, response) -> {
            try {
                Double min = Double.parseDouble(request.params(":min"));
                Double max = Double.parseDouble(request.params(":max"));

                List<Panel> panels = panelDAO.findBetweenPrices(min, max);

                return gson.toJson(panels);
            } catch (NumberFormatException e) {
                response.status(400);
                return "Error en el formato de los parámetros";
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }
        });

        // Endpoint para buscar paneles entre precios de una marca concreta
        Spark.get("/paneles/buscar/:min/:max/:brand", (request, response) -> {
            try {
                Double min = Double.parseDouble(request.params(":min"));
                Double max = Double.parseDouble(request.params(":max"));
                String brand = request.params(":brand");

                List<Panel> panels = panelDAO.findBetweenBrandPrices(min, max, brand);

                return gson.toJson(panels);
            } catch (NumberFormatException e) {
                response.status(400);
                return "Error en el formato de los parámetros";
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }
        });

        // ERROR
        // Endpoint para buscar paneles entre potencias de una categoría concreta
        Spark.get("/paneles/buscar/:min/:max/:category", (request, response) -> {
            try {
                Double min = Double.parseDouble(request.params(":min"));
                Double max = Double.parseDouble(request.params(":max"));
                String category = request.params(":category");

                List<Panel> panels = panelDAO.findBetweenCategoryPower(min, max, category);

                return gson.toJson(panels);
            } catch (NumberFormatException e) {
                response.status(400);
                return "Error en el formato de los parámetros";
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }
        });

        // ERROR
        // Endpoint para buscar paneles entre precios de varias marcas
        Spark.get("/paneles/buscar/:min/:max/:listbrands", (request, response) -> {
            try {
                Double min = Double.parseDouble(request.params(":min"));
                Double max = Double.parseDouble(request.params(":max"));
                String brandsParam = request.params(":listbrands");

                List<String> brands = Arrays.asList(brandsParam.split("-"));

                List<Panel> panels = panelDAO.findBetweenBrandsPrices(min, max, brands);
                return gson.toJson(panels);
            } catch (NumberFormatException e) {
                response.status(400);
                return "Error en el formato de los parámetros";
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "Error interno del servidor";
            }
        });



        /* POST */
        // Endpoint para crear un panel con todos los datos
        Spark.post("/paneles", (request, response) -> {

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



        /* PUT */
        // Endpoint para actualizar un panel por su ID
        Spark.put("paneles/:id", (request, response) -> {
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
                    "\"hint1\": \"/muebles\"," +
                    "\"hint2\": \"/muebles/paginado/:pagina/:tam_pagina\"," +
                    "\"hint3\": \"/muebles/id/:id\"}";
        });


        /* PAGINACIÓN DE DATOS DE TODOS LOS PANELES */
        Spark.get("/paneles/paginado/:page/:amount", (request, response) -> {
            Integer page = Integer.parseInt(request.params("page"));
            Integer amount = Integer.parseInt(request.params("amount"));

            List<Panel> panels = panelDAO.getAll(page, amount);
            Long totalElements = panelDAO.totalPanels();
            RespuestaPaginacion<Panel> pageResult = new RespuestaPaginacion<>(panels, totalElements, page, amount);

            return gson.toJson(pageResult);
        });
    }
}
