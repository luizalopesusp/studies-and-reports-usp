import java.util.List;

public class InsertionSortStrategy implements AlgoritmoOrdenacao {
    @Override
    public void ordena(List<Produto> produtos, CriterioOrdenacao criterio) {
        for(int i = 0; i < produtos.size(); i++){
            Produto x = produtos.get(i);
            int j = (i - 1);

            //utilizando o criterio.compara
            while(j >= 0 && criterio.compara(x, produtos.get(j)) < 0){
                produtos.set(j + 1, produtos.get(j));
                j--;
            }
            produtos.set(j + 1, x);
        }
    }
}
