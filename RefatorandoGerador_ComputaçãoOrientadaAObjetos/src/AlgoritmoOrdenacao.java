import java.util.List;

public interface AlgoritmoOrdenacao {
     /*
     * As classes que implementarem esta interface terão que ter este método que recebe uma coleção de produtos a serem ordenados
     * utilizando um critério de ordenação específico.
     */

    void ordena(List<Produto> produtos, CriterioOrdenacao criterio);

}
