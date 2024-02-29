package dao.report;

import entidades.Panel;
import entidades.Report;
import entidades.User;

import java.util.List;

public interface ReportDAOInterface {
    Report findById(Long id);
    Report createReport(Report r);
    Report update(Report report);
    boolean deleteById(Long id);
}
