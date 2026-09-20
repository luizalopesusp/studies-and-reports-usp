public class FiltroTodos implements CriterioFiltragem{
    @Override
    public boolean ehSelecionado(Produto p) {
        return true; //retorna true se todos os produtos são selecionados
    }
}
