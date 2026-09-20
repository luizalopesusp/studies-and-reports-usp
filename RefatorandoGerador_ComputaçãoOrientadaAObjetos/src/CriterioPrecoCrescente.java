public class CriterioPrecoCrescente implements CriterioOrdenacao {
    @Override
    public int compara(Produto p1, Produto p2) {
        //compara o preço dos produtos
        return Double.compare(p1.getPreco(), p2.getPreco());
    }
}
