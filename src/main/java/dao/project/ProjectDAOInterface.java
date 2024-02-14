package dao.project;

public interface ProjectDAOInterface {

    ProjectDAO createProject(ProjectDAO p);

    ProjectDAO findById(Long id);
}
