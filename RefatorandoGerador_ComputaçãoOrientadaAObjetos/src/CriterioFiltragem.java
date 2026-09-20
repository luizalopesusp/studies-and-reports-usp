public interface CriterioFiltragem {
    /**
     * As classes que implementarem esta interface terão que ter este método que recebe um produto a ser verificado e retorna true se o 
     * o produto deve ser incluído no relatório e flase caso contrário.
     */
    boolean ehSelecionado(Produto p);
}
