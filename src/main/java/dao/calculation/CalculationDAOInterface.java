package dao.calculation;

import entidades.Calculation;
import entidades.Project;

public interface CalculationDAOInterface {

    Calculation createCalculation(Calculation c);

    Calculation findById(Long id);
}
