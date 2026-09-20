public class ItalicoDecorator extends ProdutoDecorator {
     public ItalicoDecorator(Produto produtoDecorado) {
        super(produtoDecorado); //chamando o construtor da classe ProdutoDecorator
    }

    @Override
    public String formataParaImpressao() {
        //pega a formatação do produto original
        String textoOriginal = super.formataParaImpressao();
        //adiciona a tag de itálico e retorna
        return "<span style=\"font-style:italic\">" + textoOriginal + "</span>";
    }
}
