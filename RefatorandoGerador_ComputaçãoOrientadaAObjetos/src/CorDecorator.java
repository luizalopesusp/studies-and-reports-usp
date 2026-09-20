public class CorDecorator extends ProdutoDecorator {
    
    private String cor;

    public CorDecorator(Produto produtoDecorado, String cor) {
        super(produtoDecorado);
        this.cor = cor;
    }

    @Override
    public String formataParaImpressao() {
        String textoOriginal = super.formataParaImpressao();
        
        //verificando se a cor não é nula, vazia ou sem cor
        if (cor != null && !cor.trim().isEmpty() && !cor.trim().equalsIgnoreCase("black") && !cor.trim().equalsIgnoreCase("none") && !cor.trim().equalsIgnoreCase("#000000")) {
            String corHex = cor.trim();
            // Garante que a cor comece com '#' se já não começar
            if (!corHex.startsWith("#")) {
                corHex = "#" + corHex;
            }
            return "<span style=\"color:" + corHex + "\">" + textoOriginal + "</span>";
        }
        return textoOriginal;
    }

}
