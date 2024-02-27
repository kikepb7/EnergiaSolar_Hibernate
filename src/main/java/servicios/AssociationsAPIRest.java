package servicios;

import com.appslandia.common.gson.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.associations.AssociationsDAOInterface;
import dao.panel.PanelDAOInterface;
import dao.project.ProjectDAOInterface;
import dao.report.ReportDAOInterface;
import dao.security.APIKeyDAOInterface;
import dao.user.UserDAOInterface;
import entidades.*;
import spark.Spark;

import java.time.LocalDate;
import java.util.List;

public class AssociationsAPIRest {

    // 1. Atributtes
    private final PanelDAOInterface panelDAO;
    private final ReportDAOInterface implReport;
    private final UserDAOInterface implUser;
    private final ProjectDAOInterface implProject;
    private final AssociationsDAOInterface implAssociation;
    private final APIKeyDAOInterface tokenDAO;

    private final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    // 2. Constructor
    public AssociationsAPIRest(PanelDAOInterface implementation, ReportDAOInterface reportDAO, UserDAOInterface userDAO, ProjectDAOInterface projectDAO, AssociationsDAOInterface associationDAO, APIKeyDAOInterface impl) {
        panelDAO = implementation;
        implReport = reportDAO;
        implUser = userDAO;
        implProject = projectDAO;
        implAssociation = associationDAO;
        tokenDAO = impl;


        // ---------------------------------------------------------------------------------------- //
        // RELACIONES

        // Listar el usuario de un reporte
        Spark.get("/usuario/reporte/:reportId", (request, response) -> {

            Long reportId = Long.parseLong(request.params(":reportId"));

            Report r = implReport.findById(reportId);

            response.type("application/json");

            if (r != null) {
                User u = implAssociation.getUserReport(r);

                return gson.toJson(u);
            } else {
                response.status(404);
                return "Reporte no encontrado";
            }
        });

        // Listar todos los reportes de un usuario
        Spark.get("/reportes/:userId", (request, response) -> {

            Long userId = Long.parseLong(request.params(":userId"));

            User u = implUser.findById(userId);

            response.type("application/json");

            if (u != null) {
                List<Report> reports = implAssociation.getReportsUser(u);

                return gson.toJson(reports);
            } else {
                response.status(404);
                return "Usuario no encontrado";
            }
        });

        // Listar el usuario de un proyecto
        Spark.get("/usuario/proyecto/:projectId", (request, response) -> {

            Long projectId = Long.parseLong(request.params(":projectId"));

            Project p = implProject.findById(projectId);

            response.type("application/json");

            if (p != null) {
                User u = implAssociation.getUserProject(p);

                return gson.toJson(u);
            } else {
                response.status(404);
                return "Proyecto no encontrado";
            }
        });

        // Listar todos los proyectos de un usuario
        Spark.get("/proyectos/:userId", (request, response) -> {

            Long userId = Long.parseLong(request.params(":userId"));

            User u = implUser.findById(userId);

            response.type("application/json");

            if (u != null) {
                List<Project> projects = implAssociation.getProjectsUser(u);

                return gson.toJson(projects);
            } else {
                response.status(404);
                return "Usuario no encontrado";
            }
        });

        // Listar todos los proyectos donde se haya instalado un panel concreto
        Spark.get("/proyecto/paneles/:panelId", (request, response) -> {

            Long panelId = Long.parseLong(request.params(":panelId"));

            Panel p = panelDAO.findById(panelId);

            response.type("application/json");

            if (p != null) {
                List<Project> projects = implAssociation.projectPanels(p);

                return gson.toJson(projects);
            } else {
                response.status(404);
                return "Panel no encontrado";
            }
        });

        // Listar todos los paneles instalados en un proyecto
        Spark.get("/paneles/proyecto/:projectId", (request, response) -> {

            Long projectId = Long.parseLong(request.params(":projectId"));

            Project pr = projectDAO.findById(projectId);

            response.type("application/json");

            if (pr != null) {
                List<Panel> panels = implAssociation.installedPanels(pr);

                return gson.toJson(panels);
            } else {
                response.status(404);
                return "Proyecto no encontrado";
            }
        });

        // Listar todos los cálculos de un proyecto
        Spark.get("proyecto/calculos/:projectId", (request, response) -> {

            Long projectId = Long.parseLong(request.params(":projectId"));

            Project pr = projectDAO.findById(projectId);

            response.type("application/json");

            if (pr != null) {
                List<Calculation> calculations = implAssociation.doneCalculations(pr);

                return gson.toJson(calculations);
            } else {
                response.status(404);
                return "Proyecto no encontrado";
            }
        });

        // Listar todos los proyectos de un cálculo concreto


        // Crear un nuevo reporte
        Spark.post("/reportes/registrar/:userId", (request, response) -> {
            String body = request.body();

            Report newReport = gson.fromJson(body, Report.class);
            Long userId = Long.parseLong(request.params("userId"));
            User newUser = userDAO.findById(userId);

            Report createdReport = reportDAO.createReport(newReport);

            associationDAO.assignReportUser(createdReport, newUser);
            return gson.toJson(createdReport);
        });


        // Asociar un nuevo proyecto a un usuario concreto
        Spark.post("/proyecto/registrar/:userId", (request, response) -> {
            String body = request.body();

            Project newProject = gson.fromJson(body, Project.class);
            Long userId = Long.parseLong(request.params("userId"));
            User newUser = userDAO.findById(userId);

            Project createProject = projectDAO.createProject(newProject);

            associationDAO.assignUserProject(newUser, createProject);
            return gson.toJson(createProject);
        });

        // Asignar paneles solares a un proyecto
        Spark.post("/proyecto/asignar_panel/:proyectId/:panelId", (request, response) -> {
            String body = request.body();

            Project project = gson.fromJson(body, Project.class);
            Panel panel = gson.fromJson(body, Panel.class);

            Long projectId = Long.parseLong(request.params("projectId"));
            Long panelId = Long.parseLong(request.params("panelId"));

            Project createdProject = projectDAO.findById(projectId);
            Panel createdPanel = panelDAO.findById(panelId);

            return gson.toJson(associationDAO.assignPanelProject(createdPanel, createdProject));
        });


        // PRUEBA
        Spark.exception(Exception.class, (e, req, res) -> {
            e.printStackTrace();
            res.status(500);
            res.body("Excepcion en tu codigo");
        });
    }
}
