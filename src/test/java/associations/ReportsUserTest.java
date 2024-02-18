package associations;

import dao.associations.AssociationsDAO;
import dao.associations.AssociationsDAOInterface;
import dao.calculation.CalculationDAO;
import dao.calculation.CalculationDAOInterface;
import dao.panel.PanelDAO;
import dao.panel.PanelDAOInterface;
import dao.project.ProjectDAO;
import dao.project.ProjectDAOInterface;
import dao.report.ReportDAO;
import dao.report.ReportDAOInterface;
import dao.user.UserDAO;
import dao.user.UserDAOInterface;
import entidades.Report;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportsUserTest {

    static ReportDAOInterface reportDAO;
    static UserDAOInterface userDAO;
    static ProjectDAOInterface projectDAO;
    static PanelDAOInterface panelDAO;
    static CalculationDAOInterface calculationDAO;
    static AssociationsDAOInterface associationsDAO;

    @BeforeAll
    static void setUp() {
        reportDAO = new ReportDAO();
        userDAO = new UserDAO();
        projectDAO = new ProjectDAO();
        panelDAO = new PanelDAO();
        calculationDAO = new CalculationDAO();
        associationsDAO = new AssociationsDAO();
    }

    @Test
    public void test1() {

    }
}
