import dao.PanelDAO;
import servicios.PanelAPIRest;

public class Servidor {
    public static void main(String[] args) {
        PanelAPIRest api = new PanelAPIRest(new PanelDAO());
    }
}
