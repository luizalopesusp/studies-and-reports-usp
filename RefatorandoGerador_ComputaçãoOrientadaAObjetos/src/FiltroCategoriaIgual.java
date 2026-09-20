public class FiltroCategoriaIgual implements CriterioFiltragem {
    private String categoriaAlvo;

    public FiltroCategoriaIgual(String argFiltro) {
        this.categoriaAlvo = argFiltro;
    }

    @Override
    public boolean ehSelecionado(Produto p) {
        return p.getCategoria().equalsIgnoreCase(categoriaAlvo);
    }
}
