package dao;

import entidades.SolarInstalation;

public interface SolarInstalationDAOInterface {

    SolarInstalation createSolarInstalation(SolarInstalation si);

    SolarInstalation findById(Long id);
}
