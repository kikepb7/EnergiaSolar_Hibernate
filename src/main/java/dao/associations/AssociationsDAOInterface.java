package dao.associations;

import entidades.*;

import java.util.List;

public interface AssociationsDAOInterface {

    boolean assignReportUser(Report r, User u);

    boolean assignProjectUser(Project pr, User u);

    boolean assignPanelProject(Panel p, Project pr);

    boolean assignCalculationProject(Calculation c, Project pr);

    User getUserReport(Report r);

    List<Report> getReportsUser(User u);

    User getUserProject(Project p);

    List<Project> getProjectsUser(User u);

    List<Project> projectPanels(Panel p);

    List<Panel> installedPanels(Project pr);

    Project getProjectCaltulation(Calculation c);

    List<Calculation> doneCalculations(Project pr);
}
