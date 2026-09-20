public class FiltroEstoqueMenorIgual implements CriterioFiltragem {
    
    private int valorLimite;

    public FiltroEstoqueMenorIgual(String argFiltro) {
        this.valorLimite = Integer.parseInt(argFiltro); //converte a string para número
    }

    @Override
    public boolean ehSelecionado(Produto p) {
        return p.getQtdEstoque() <= valorLimite;
    }
}
