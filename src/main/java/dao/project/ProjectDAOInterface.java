package dao.project;

import entidades.Project;

public interface ProjectDAOInterface {

    Project createProject(Project p);

    Project findById(Long id);
}
