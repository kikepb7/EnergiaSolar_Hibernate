import dao.associations.AssociationsDAO;
import dao.panel.PanelDAO;
import dao.project.ProjectDAO;
import dao.report.ReportDAO;
import dao.security.APIKeyDAO;
import dao.user.UserDAO;
import servicios.AssociationsAPIRest;
import servicios.PanelAPIRest;
import spark.Spark;

import static spark.Spark.before;

public class Servidor {
    public static void main(String[] args) {

        Spark.port(8080);
        PanelAPIRest api = new PanelAPIRest(new PanelDAO(), new APIKeyDAO());

        AssociationsAPIRest apiAssociations = new AssociationsAPIRest(new PanelDAO(), new ReportDAO(), new UserDAO(), new ProjectDAO(), new AssociationsDAO(), new APIKeyDAO());




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
