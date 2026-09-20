public class CriterioEstoqueCrescente implements CriterioOrdenacao {
    @Override
    public int compara(Produto p1, Produto p2) {
        // Compara a quantidade em estoque dos produtos
        return Integer.compare(p1.getQtdEstoque(), p2.getQtdEstoque());
    }
}
