package dao;


import dto.PanelDTO;
import entidades.Panel;
import java.util.List;

public interface PanelDAOInterface {
    List<Panel> getAllPanels();
    List<Panel> getAll(int page, int amount);
    List<Panel> getMoreExpensive();
    List<String> getAllImages();
    List<PanelDTO> getImagesName();
    List<PanelDTO> getModelsFabrication();
    Long totalPanels();
    Panel findById(Long id);
    Double avgPrices();
    Double avgBrandPrices(String brand);
    List<Panel> getPanelsByMaxFabricationYear(int year);
    Panel getPanelMaxEfficiency(String brand);
    List<Panel> findByModelLike(String name);
    List<Panel> findByMaterialLike(String material);
    List<Panel> findBetweenPrices(Double min, Double max);
    List<Panel> findBetweenBrandPrices(Double min, Double max, String brand);
    List<Panel> findBetweenCategoryPower(Double min, Double max, String category);
    List<Panel> findBetweenBrandPrices(Double min, Double max, List<String> brands);
    Panel create(Panel panel);
    Panel update(Panel panel);
    boolean deleteById(Long id);
    boolean deleteAll();
}
