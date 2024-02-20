package servicios;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RespuestaPaginacion<T> {

    // 1. Attributes
    @Getter @Setter @Expose
    private List<T> elements;

    @Getter @Setter @Expose
    private long totalElements;

    @Getter @Setter @Expose
    private int numberPage;

    @Getter @Setter @Expose
    private int amountElements;


    // 2. Constructors
    public RespuestaPaginacion(List<T> elements, long totalElements, int numberPage, int amountElements) {
        this.elements = elements;
        this.totalElements = totalElements;
        this.numberPage = numberPage;
        this.amountElements = amountElements;
    }
}
