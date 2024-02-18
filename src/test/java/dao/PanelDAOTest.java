package dao;

import dao.panel.PanelDAO;
import dao.panel.PanelDAOInterface;
import entidades.Panel;
import java.time.LocalDate;

import entidades.Project;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PanelDAOTest {
    static PanelDAOInterface dao;

    @BeforeAll
    static void setUp(){

        dao = new PanelDAO();
    }

    @AfterAll
    static void setDown(){
        //     dao.deleteAll();
    }

    @Test
    void getAll() {
        System.out.println(dao.getAllPanels());
    }

    @Test
    void getMoreExpensive() {
        System.out.println(dao.getMoreExpensive());
    }

    @Test
    void getAllImages() {
        System.out.println(dao.getAllImages());
    }

    @Test
    void getImagesName() {
        System.out.println(dao.getImagesName());
    }

    @Test
    void getModelsPricePower() {
        System.out.println(dao.getModelsPricePower());
    }

    @Test
    void create() {

        List<Project> projects = new ArrayList<>();
        Panel panel1 = new Panel(21L,
                "Samsung",
                "Categoría ejemplo",
                LocalDate.now(),
                25.5,
                "image_example.jpg",
                "Modelo ejemplo",
                300,
                200.0,
                projects
        );

        dao.create(panel1);
    }

    @Test
    void update() {

        List<Project> projects = new ArrayList<>();
        Panel panel1 = new Panel(21L,
                "Nokia",
                "Categoría ejemplo modificado",
                LocalDate.now(),
                25.5,
                "image_example.jpg",
                "Modelo ejemplo modificado",
                300,
                200.0,
                projects
        );

        dao.update(panel1);
    }

    @Test
    void deleteById() {
        dao.deleteById(20L);
    }
}