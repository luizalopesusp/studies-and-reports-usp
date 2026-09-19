/*LUIZA DE JESUS LOPES
  NºUSP: 12822076
  EP1 - IAA
  09/2024
*/

//bibliotecas utilizadas
#include <stdio.h>
#include <stdlib.h>



//estruturas para manipular a matriz dinamicamente
typedef struct {
    int **valores;//valores com ponteiros para matriz
    int dim;//dimensao da matriz
} MATRIZ;

typedef struct {
    int lin;//linha do elemento da matriz
    int col;//coluna do elemento da matriz
    int rgb;//var que vai especificar o nivel do rgb (0 a 255)
} ELEMENTO;

//definindo constantes maximas e minimas
#define MAX 255
#define MIN 0

//função de inicialização da matriz com alocação dinâmica
void inicializa_matriz(MATRIZ *matriz, int dim) {//recebe 2 parâmetros: var matriz apontando para a matriz na estrutura e um inteiro dim sinalizando a dimensao da matriz, respectivamente
    matriz->valores = (int **)malloc(dim * sizeof(int *));//o tamanho da alocação de espaço na memória está atrelado ao tamanho (dimensão) da matriz
    for (int i = 0; i < dim; i++) {
        matriz->valores[i] = (int *)calloc(dim, sizeof(int));
    }
}

//função recursiva para fazer a busca pelos quadrantes a procura do MENOR valor
ELEMENTO busca_menor_matriz(MATRIZ *matriz, int prim_linha, int ultim_linha, int prim_col, int ultim_col) {//seus parâmetros são referentes as linhas e colunas da matriz
   
    ELEMENTO menor_elem;
    menor_elem.rgb = MAX;//a var recebe a restrição de receber apenas valores menores que a constante MAX

    if(prim_linha == ultim_linha && prim_col == ultim_col){//condição de base: se a primeira linha for igual a ultima coluna da matriz, então se trata de um só quadrante
        //então, acontecem algumas atribuições para, então, conseguirmos retornar o menor elemento e comparar o rgb com os outros quadrantes
        menor_elem.lin = prim_linha;
        menor_elem.col = prim_col;
        menor_elem.rgb = matriz->valores[prim_linha][prim_col];
        return menor_elem;
    }

    //aqui é o calculo entre os indices da matriz para conseguirmos definir as metades das colunas e linhas que separam os 4 quadrantes
    int metade_col = (prim_col + ultim_col)/2;
    int metade_linhas = (prim_linha + ultim_linha)/2;
    

    /*aqui são chamadas da função recursiva busca_menor_matriz, cada uma se tratando de um quadrante diferente,
    assim, cada quadrante consegue seu retorno de menor elemento*/
    ELEMENTO quad1 = busca_menor_matriz(matriz, prim_linha, metade_linhas, prim_col, metade_col);
    ELEMENTO quad2 = busca_menor_matriz(matriz, prim_linha, metade_linhas, metade_col+1, ultim_col);
    ELEMENTO quad3 = busca_menor_matriz(matriz, metade_linhas+1, ultim_linha, prim_col, metade_col);
    ELEMENTO quad4 = busca_menor_matriz(matriz, metade_linhas+1, ultim_linha, metade_col+1, ultim_col);

    //condições de comparação para cada menor rgb de cada quadrante
    if(quad1.rgb < menor_elem.rgb) {
        menor_elem = quad1;
    } if(quad2.rgb < menor_elem.rgb){
        menor_elem = quad2;
    } if(quad3.rgb < menor_elem.rgb){ 
        menor_elem = quad3;
    } if(quad4.rgb < menor_elem.rgb){ 
        menor_elem = quad4;
    }

    return menor_elem;//após as comparações, função retorna o menor elemento entre os quadrantes
}

//função recursiva para fazer a busca pelos quadrantes a procura do MAIOR valor
ELEMENTO busca_maior_matriz(MATRIZ *matriz, int prim_linha, int ultim_linha, int prim_col, int ultim_col) {
   
    ELEMENTO maior_elem;
    maior_elem.rgb = MIN;//a var recebe a restrição de receber apenas valores maiores que a constante MIN

    if(prim_linha == ultim_linha && prim_col == ultim_col){//condição de base: se a primeira linha for igual a ultima coluna da matriz, então se trata de um só quadrante
        //então, acontecem algumas atribuições para, então, conseguirmos retornar o maior elemento e comparar o rgb com os outros quadrantes
        maior_elem.lin = prim_linha;
        maior_elem.col = prim_col;
        maior_elem.rgb = matriz->valores[prim_linha][prim_col];
        return maior_elem;
    }
    //mesma logica acima de calculo das metades dos quadrantes
    int metade_linhas = (prim_linha + ultim_linha) / 2;
    int metade_col = (prim_col + ultim_col) / 2;

    //chamada da função recursiva busca_maior_matriz para retornar o maior valor de cada quadrante
    ELEMENTO quad1 = busca_maior_matriz(matriz, prim_linha, metade_linhas, prim_col, metade_col);
    ELEMENTO quad2 = busca_maior_matriz(matriz, prim_linha, metade_linhas, metade_col + 1, ultim_col);
    ELEMENTO quad3 = busca_maior_matriz(matriz, metade_linhas + 1, ultim_linha, prim_col, metade_col);
    ELEMENTO quad4 = busca_maior_matriz(matriz, metade_linhas + 1, ultim_linha, metade_col + 1, ultim_col);

    //condições de comparação para cada maior rgb de cada quadrante
    if(quad1.rgb > maior_elem.rgb) {
        maior_elem = quad1;
    }if(quad2.rgb > maior_elem.rgb){
        maior_elem = quad2;
    }if(quad3.rgb > maior_elem.rgb){ 
        maior_elem = quad3;
    }if(quad4.rgb > maior_elem.rgb){ 
        maior_elem = quad4;
    }

    return maior_elem;//após as comparações, função retorna o maior elemento entre os quadrantes
}

//funcao main
int main(int argc, char **argv) {//os parâmetros argc e argv armazenam, respectivamente, o número e cada parametro que foi dado na linha de comando no terminal
    if (argc != 4) {//condição de teste para certificar que há, no máximo, 3 parâmetros para as informações da matriz + o parametro 0 da linha de execucao (./executavel)
        printf("O número de argumentos não corresponde ao esperado. Digite até 4 argumentos na linha de comando contando com o executavel.exe!\n");
        return 1;
    }

    //função atoi recebe o parametro 1 (N) e converte ASCll para Integer 
    int N = atoi(argv[1]);
    if (N > 64 || N % 2 != 0) {//condição para testar se N é compatível ou não
        printf("O valor N (%d) não é permitido. Digite novamente! \n", N);//mensagem de erro caso não for compatível
        return 1;
    }

    char *pega_arq = argv[2];//ponteiro recebe o parametro 2 (entrada.txt)
    FILE *arq_entrada = fopen(pega_arq, "r");//abertura do arquivo de entrada.txt
    if (arq_entrada == NULL) {//condição de teste para certificar que o arquivo não veio nulo
        printf("Arquivo aberto incorretamente \n");
        return 1;
    }

    //declaração da matriz da estrutura
    MATRIZ matriz;
    inicializa_matriz(&matriz, N);//chamada de função para inicilizar a matriz e alocar memória dinamicamente

    //laços de repetição com indices i e j para lermos com fscanf o arquivo de entrada.txt
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            fscanf(arq_entrada, "%d", &matriz.valores[i][j]);
            //condições de teste para certificar que os valores da matriz estão no intervalo definido (0 a 255)           
            if (matriz.valores[i][j] > 255) {
                printf("Valor %d da matriz é maior que o limite máximo do RGB (255) \n", matriz.valores[i][j]);
            } else if (matriz.valores[i][j] < 0) {
                printf("Valor %d da matriz é menor que o limite mínimo do RGB (0) \n", matriz.valores[i][j]);
            }
        }
    }

    fclose(arq_entrada);//fechando arquivo de entrada.txt

    //laços de repetição para printar a matriz lida afim de apoiar no desenvolvimento do EP
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            printf("%3d ", matriz.valores[i][j]);
        }
        printf("\n");
        
    }
    printf("-------------------------------- \n");

    //declaracao da var que irá receber o menor elemento entre todos os quadrantes a partir da chamada da função recursiva busca_menor_matriz
    ELEMENTO elem_menor = busca_menor_matriz(&matriz, 0, N - 1, 0, N - 1);

    //impressões no terminal afim de apoiar no desenvolvimento do EP
    printf("O MENOR elemento da matriz é: %d \n", elem_menor.rgb);
    printf("Sua posição é: [%d, %d]\n", elem_menor.lin, elem_menor.col);
    printf("-------------------------------- \n");

    //declaracao da var que irá receber o maior elemento entre todos os quadrantes a partir da chamada da função recursiva busca_maior_matriz
    ELEMENTO elem_maior = busca_maior_matriz (&matriz, 0, N - 1, 0, N - 1);

    //impressões no terminal afim de apoiar no desenvolvimento do EP
    printf("O MAIOR elemento da matriz é: %d \n", elem_maior.rgb);
    printf("Sua posição é: [%d, %d]\n", elem_maior.lin, elem_maior.col);


    //laço de repetição liberando a memória anteriormente alocada para a matriz utilizando a função free()
    for (int i = 0; i < N; i++) {
        free(matriz.valores[i]);//liberando memória de cada valor da matriz
    }
    free(matriz.valores);//liberando a matriz


    //criando o arquivo de saida.txt
    char* pega_saida = argv[3];

    FILE *arq_saida = fopen(pega_saida,"w");//abrindo arquivo de saida e armazenando na var
    if(arq_saida == NULL){//condição de teste para certificar que o arquivo não está nulo
        printf("Arquivo de saida aberto incorretamente \n");
        return 1;
    }

    //escrevendo informações dos maiores e menores valores dos 4 quadrantes e suas respectivas posições na matriz; utiliza-se a função fprintf
    fprintf(arq_saida,"min = %d; pos = (%d, %d) \n",elem_menor.rgb,elem_menor.lin, elem_menor.col);
    fprintf(arq_saida,"max = %d; pos = (%d, %d) \n",elem_maior.rgb,elem_maior.lin, elem_maior.col);
    

    fclose(arq_saida);//fechando arquivo de saida.txt

    return 0;
}