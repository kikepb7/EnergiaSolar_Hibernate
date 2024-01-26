package dao;

public interface ProjectDAOInterface {

    ProjectDAO createProject(ProjectDAO p);

    ProjectDAO findById(Long id);
}
