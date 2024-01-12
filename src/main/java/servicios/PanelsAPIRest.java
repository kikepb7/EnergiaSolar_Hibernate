package servicios;

import com.google.gson.Gson;
import dao.PanelDAOInterface;
import entidades.Panel;
import spark.Spark;

import java.util.List;

public class PanelsAPIRest {
    private PanelDAOInterface panelDAO;
    private Gson gson = new Gson();

    public PanelsAPIRest(PanelDAOInterface implementation) {
        Spark.port(8080);
        panelDAO = implementation;

        // Endpoint para obtener todos los paneles disponibles en la BD
        Spark.get("/paneles", (request, response) -> {
           List<Panel> panels = panelDAO.getAllPanels();
           return gson.toJson(panels);
        });

        // Endpoint para obtener los paneles más caros disponibles en la BD
        Spark.get("/paneles/mas_caros", (request, response) -> {
           List<Panel> moreExpensive = panelDAO.getMoreExpensive();
           return gson.toJson(moreExpensive);
        });

        // Endpoint para obtener las imágenes de los paneles disponibles en la BD
        Spark.get("/paneles/imagenes", (request, response) -> {
           List<String> images = panelDAO.getAllImages();
           return gson.toJson(images);
        });

        //


    }
}
