public class CriterioDescricaoCrescente implements CriterioOrdenacao {
      @Override
    public int compara(Produto p1, Produto p2) {
        //compara a descrição dos produtos (ignorando maiúsculas/minúsculas)
        return p1.getDescricao().compareToIgnoreCase(p2.getDescricao());
    }
}
