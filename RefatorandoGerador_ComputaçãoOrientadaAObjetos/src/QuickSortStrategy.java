import java.util.List;

public class QuickSortStrategy implements AlgoritmoOrdenacao {

    private List<Produto> produtos;
    private CriterioOrdenacao criterio;

    @Override
    public void ordena(List<Produto> produtos, CriterioOrdenacao criterio) {
        this.produtos = produtos; 
        this.criterio = criterio;
        ordenaRecursivo(0, produtos.size() - 1);
    }

    //método particiona que estava no GeradorDeRelatorios
    private int particiona(int ini, int fim){
        Produto x = produtos.get(ini);
        int i = (ini - 1);
        int j = (fim + 1);
        
        //agora utilizando while ao invés dos if-else-ifs
        while(true){
            do{
                j--;
            } while(criterio.compara(produtos.get(j), x) > 0); 

            do{
                i++;
            } while(criterio.compara(produtos.get(i), x) < 0); 

            if(i < j){
                Produto temp = produtos.get(i);
                produtos.set(i, produtos.get(j));
                produtos.set(j, temp);
            }
            else return j;
        }
    }

    //método ordena recursivo que estava no GeradorDeRelatorios
    private void ordenaRecursivo(int ini, int fim){
        if(ini < fim) {
            int q = particiona(ini, fim);
            ordenaRecursivo(ini, q);
            ordenaRecursivo(q + 1, fim);
        }
    }
}
