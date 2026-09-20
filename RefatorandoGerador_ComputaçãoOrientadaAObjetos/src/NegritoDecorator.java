public class NegritoDecorator extends ProdutoDecorator {
    
     public NegritoDecorator(Produto produtoDecorado) {
        super(produtoDecorado); //chamando o construtor da classe ProdutoDecorator
    }

    @Override
    public String formataParaImpressao() {
        //pega a formatação do produto original
        String textoOriginal = super.formataParaImpressao();
        //adiciona a tag de negrito e retorna
        return "<span style=\"font-weight:bold\">" + textoOriginal + "</span>";
    }

}
