import dao.PanelDAO;
import servicios.PanelAPIRest;
import spark.Spark;

import static spark.Spark.*;

public class Servidor {
    public static void main(String[] args) {
        PanelAPIRest api = new PanelAPIRest(new PanelDAO());
    }
}
