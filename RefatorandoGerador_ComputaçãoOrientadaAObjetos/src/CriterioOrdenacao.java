public interface CriterioOrdenacao {
    /*
     * As classes que implementarem esta interface terão que ter este método que recebe dois produtos a serem comparados
     * e retorna um numero negativo se p1 for menor que p2, zero se forem iguais ou um número positivo se p1 for maior que p2
     */
    int compara(Produto p1, Produto p2);

}

