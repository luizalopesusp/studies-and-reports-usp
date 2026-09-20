import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;//para leitura de arquivos
import java.io.FileReader;//para leitura de arquivos

//import java.util.*;

public class GeradorDeRelatorios {

	private List<Produto> produtos;
	private AlgoritmoOrdenacao algoritmoOrdenacao; 
	private CriterioOrdenacao criterioOrdenacao;  
	private CriterioFiltragem criterioFiltragem;

	//construtor
	public GeradorDeRelatorios(List<Produto> produtos, AlgoritmoOrdenacao algoritmo, CriterioOrdenacao criterio, CriterioFiltragem filtro){

		this.produtos = new ArrayList<>(produtos);
		this.algoritmoOrdenacao = algoritmo;
		this.criterioOrdenacao = criterio;
		this.criterioFiltragem = filtro;
	}

	//método que delega a tarefa para o algoritmo de ordenação que foi escolhido
	private void ordena(){
    	algoritmoOrdenacao.ordena(this.produtos, criterioOrdenacao);
	}
	
	public void debug(){
		System.out.println("Gerando relatório para array contendo " + produtos.size() + " produto(s)");
	}

	//escrita do relatorio num arquivo HTML de saída
	public void geraRelatorio(String arquivoSaida) throws IOException {

		debug();
		ordena();

		PrintWriter out = new PrintWriter(arquivoSaida);

		out.println("<!DOCTYPE html><html>");
		out.println("<head><title>Relatorio de produtos</title></head>");
		out.println("<body>");
		out.println("Relatorio de Produtos:");
		out.println("<ul>");

		 int count = 0;

		 for(Produto p : produtos){
        	if(criterioFiltragem.ehSelecionado(p)){
				out.print("<li>");

				out.print(p.formataParaImpressao());

				out.println("</li>");
				count++;
			}
		}

		out.println("</ul>");
		out.println(count + " produtos listados, de um total de " + produtos.size() + ".");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}

	//carregando os produtos lidos do arquivo na coleção
	public static List<Produto> carregaProdutos(){

	List<Produto> listaDeProdutos = new ArrayList<>();
	String linha;
    String caminhoArquivo = "produtos.csv";

    try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
		br.readLine();//pulando a primeira linha do arquivo .csv

        while ((linha = br.readLine()) != null) {
            if (linha.trim().isEmpty()) {
                continue;
            }
            
            String[] dados = linha.split(",");

            //a linha deve ter 8 partes
            if (dados.length == 8) {
                try {
					//lendo colunas comuns
                    int id = Integer.parseInt(dados[0].trim());
                    String descricao = dados[1].trim();
                    String categoria = dados[2].trim();
                    int qtdEstoque = Integer.parseInt(dados[3].trim());
                    double preco = Double.parseDouble(dados[4].trim());

					//lendo colunas para formato das letras
                    boolean isNegrito = Boolean.parseBoolean(dados[5].trim());
                    boolean isItalico = Boolean.parseBoolean(dados[6].trim());
                   
					//lendo coluna de cor
                    String cor = dados[7].trim(); // A cor é uma String

                    Produto produto = new ProdutoPadrao(id, descricao, categoria, qtdEstoque, preco);

                    if (cor != null && !cor.trim().isEmpty() && !cor.trim().equalsIgnoreCase("black") && !cor.trim().equalsIgnoreCase("none") && !cor.trim().equalsIgnoreCase("#000000")) {
                    	produto = new CorDecorator(produto, cor); // Passa a string hexadecimal diretamente
                	}

                    if (isNegrito) {
                        produto = new NegritoDecorator(produto);
                    }
                    if (isItalico) {
                        produto = new ItalicoDecorator(produto);
                    }

                    listaDeProdutos.add(produto);

                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter número/booleano em uma linha do CSV: '" + linha + "'. Detalhes: " + e.getMessage());
                }

            } else {
                System.err.println("Linha do CSV ignorada devido ao formato inválido (esperado 8 colunas): '" + linha + "'");
            }
        }

		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo CSV '" + caminhoArquivo);
			e.printStackTrace();
		}
			
		return listaDeProdutos;
	} 

	public static void main(String [] args) {

    if(args.length < 4){
        System.out.println("Uso:");
        System.out.println("\tjava " + GeradorDeRelatorios.class.getName() + " <algoritmo> <critério de ordenação> <critério de filtragem> <parâmetro de filtragem> <opções de formatação>");
        System.out.println("Onde:");
        System.out.println("\talgoritmo: 'quick' ou 'insertion'");
        System.out.println("\tcriterio de ordenação: 'preco_c' ou 'descricao_c' ou 'estoque_c'");
        System.out.println("\tcriterio de filtragem: 'todos' ou 'estoque_menor_igual' ou 'categoria_igual'");
        System.out.println("\tparâmetro de filtragem: argumentos adicionais necessários para a filtragem");
        System.out.println("\topções de formatação: 'negrito' e/ou 'italico'");
        System.out.println();
        System.exit(1);
    }

    String opcao_algoritmo = args[0];
    String opcao_criterio_ord = args[1];
    String opcao_criterio_filtro = args[2];
    String opcao_parametro_filtro = args[3];

    //pegando as opções de formatação como strings
    List<String> opcoesFormatacao = new ArrayList<>();
    if (args.length > 4) {
        opcoesFormatacao.add(args[4]);
    }
    if (args.length > 5) {
        opcoesFormatacao.add(args[5]);
    }

    //carregando os produtos originais
    List<Produto> produtosOriginais = carregaProdutos(); //
    //criando uma nova lista para os produtos decorados
    List<Produto> produtosDecorados = new ArrayList<>(); //

    //iterando sobre cada produto original
    for (Produto p : produtosOriginais) { //
        Produto produtoAtual = p;

        //aplicando os decoradores com base nas opções de formatação
        for (String op : opcoesFormatacao) { //
            if ("negrito".equalsIgnoreCase(op)) { //
                produtoAtual = new NegritoDecorator(produtoAtual); //
            } else if ("italico".equalsIgnoreCase(op)) { //
                produtoAtual = new ItalicoDecorator(produtoAtual); //
            }
        }
        produtosDecorados.add(produtoAtual); //
    }

    //criando a instância do critério de filtragem
    CriterioFiltragem filtro;
    if(opcao_criterio_filtro.equals("todos")){
        filtro = new FiltroTodos();
    } else if (opcao_criterio_filtro.equals("estoque_menor_igual")){
        filtro = new FiltroEstoqueMenorIgual(opcao_parametro_filtro); 
    } else if (opcao_criterio_filtro.equals("categoria_igual")){
        filtro = new FiltroCategoriaIgual(opcao_parametro_filtro);
    } else {
        throw new RuntimeException("Filtro invalido!");
    }

    //criando a instância do algoritmo de ordenação 
    AlgoritmoOrdenacao alg;
    if(opcao_algoritmo.equals("quick")){
        alg = new QuickSortStrategy();
    } else if (opcao_algoritmo.equals("insertion")){
        alg = new InsertionSortStrategy();
    } else {
        throw new RuntimeException("Algoritmo invalido!");
    }

    //criando a instância do critério de ordenação
    CriterioOrdenacao crit;
    if(opcao_criterio_ord.equals("descricao_c")){
        crit = new CriterioDescricaoCrescente();
    } else if (opcao_criterio_ord.equals("preco_c")){
        crit = new CriterioPrecoCrescente();
    } else if (opcao_criterio_ord.equals("estoque_c")){
        crit = new CriterioEstoqueCrescente();
    } else {
        throw new RuntimeException("Criterio invalido!");
    }


    GeradorDeRelatorios gdr = new GeradorDeRelatorios(
                            produtosDecorados,
                            alg,
                            crit,
                            filtro
                         );

    try{
        gdr.geraRelatorio("saida.html");
    }
    catch(IOException e){
        e.printStackTrace();
    }
}
}
