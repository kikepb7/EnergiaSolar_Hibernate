package dao.report;

import entidades.Report;
import entidades.User;

import java.util.List;

public interface ReportDAOInterface {
    Report findById(Long id);
    boolean assignUser(Report r, User u);
    List<Report> getReportsUser(User u);
}
