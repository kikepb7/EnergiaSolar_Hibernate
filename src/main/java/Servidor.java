import dao.PanelDAO;
import dao.security.APIKeyDAO;
import servicios.PanelAPIRest;

import static spark.Spark.before;

public class Servidor {
    public static void main(String[] args) {

        PanelAPIRest api = new PanelAPIRest(new PanelDAO(), new APIKeyDAO());

        enableCORS("*", "*", "*");
    }


    // Método para habilitar el CORS
    private static void enableCORS(String origin, String methods, String headers) {
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
    }
}
