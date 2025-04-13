import Arquivo.Arquivo;
import Arquivo.Registro;
import Lista.Lista;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        // EXERCÍCIO 1: LISTAS
        executarTodosMetodosLista();
        try {
            // EXERCÍCIO 2: ARQUIVOS

            // Arquivos de entrada
            Arquivo arqOrd = new Arquivo("ordenado.dat");
            Arquivo arqRev = new Arquivo("reverso.dat");
            Arquivo arqRand = new Arquivo("randomico.dat");

            // Auxiliares
            Arquivo auxOrd = new Arquivo("auxOrd.dat");
            Arquivo auxRev = new Arquivo("auxRev.dat");
            Arquivo auxRand = new Arquivo("auxRand.dat");

            // Criar base de dados
            arqOrd.geraArquivoOrdenado();
            arqRev.geraArquivoReverso();
            arqRand.geraArquivoRandomico();

            PrintWriter out = new PrintWriter("resultado_tabela.txt");

            out.println("Metodo                  Tempo(ms)   CompProg  CompEq    MovProg   MovEq");
            out.println("--------------------------------------------------------------------------");


            String[] metodos = {
                    "insercaoDireta", "insercaoBinaria", "selecaoDireta", "bubble", "shake",
                    "shell", "heap", "quickSPivo", "quickCPivo", "merge1",
                    "merge2", "comb", "gnome", "tim","radix","counting","bucket"
            };

            for (String metodo : metodos) {
                executarTeste(metodo, arqOrd, arqRev, arqRand, auxOrd, auxRev, auxRand, out);
            }

            out.close();
            System.out.println("Arquivo resultado_tabela.txt gerado com sucesso.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executarTodosMetodosLista() {
        String[] nomes = {
                "Insertion Sort", "Binary Insertion Sort", "Selection Sort",
                "Bubble Sort", "Shake Sort", "Shell Sort",
                "Heap Sort", "Quick Sort", "Merge Sort",
                "Comb Sort", "Gnome Sort", "Tim Sort",
                "Counting Sort", "Radix Sort", "Bucket Sort"
        };
        for (String metodo : nomes) {
            System.out.println("\n####### Método de Ordenação: " + metodo + " #######");

            Lista lista = new Lista();
            lista.preencher(32);

            System.out.println("Lista |Randômica|:");
            System.out.println("\tNão ordenada");
            System.out.print("\t\t");
            lista.exibir();

            switch (metodo) {
                case "Insertion Sort":
                    lista.insercaoDireta();
                    break;
                case "Binary Insertion Sort":
                    lista.insercaoBinaria();
                    break;
                case "Selection Sort":
                    lista.selecaoDireta();
                    break;
                case "Bubble Sort":
                    lista.bubble();
                    break;
                case "Shake Sort":
                    lista.shake();
                    break;
                case "Shell Sort":
                    lista.shell();
                    break;
                case "Heap Sort":
                    lista.heap();
                    break;
                case "Quick Sort":
                    lista.quickSpivo();
                    break;
                case "Merge Sort":
                    lista.mergeSort();
                    break;
                case "Comb Sort":
                    lista.comb();
                    break;
                case "Gnome Sort":
                    lista.gnome();
                    break;
                case "Tim Sort":
                    lista.tim(8);
                    break;
                case "Counting Sort":
                    lista.counting();
                    break;
                case "Radix Sort":
                    lista.radix();
                    break;
                case "Bucket Sort":
                    lista.bucket(10);
                    break;
            }


            System.out.println("\nLista |Randômica|:");
            System.out.println("\tOrdenada");
            System.out.print("\t\t");
            lista.exibir();
        }
    }


    private static void executarTeste(String metodo, Arquivo arqOrd, Arquivo arqRev, Arquivo arqRand,
                                      Arquivo auxOrd, Arquivo auxRev, Arquivo auxRand, PrintWriter out) throws IOException {
        // Ordenado
        clonar(arqOrd, auxOrd);
        medir(auxOrd, metodo, "Ord", out);

        // Reverso
        clonar(arqRev, auxRev);
        medir(auxRev, metodo, "Rev", out);

        // Randomico
        clonar(arqRand, auxRand);
        medir(auxRand, metodo, "Rand", out);
    }

    private static void medir(Arquivo arq, String metodo, String tipo, PrintWriter out) {
        try {

            int n = arq.filesize(); // ← aqui

            arq.initComp();
            arq.initMov();
            long inicio = System.currentTimeMillis();
            String compEqua = "-";
            String movEqua = "-";

            String metodoNormalizado = metodo.replaceAll("\\s+", "").toLowerCase();

            switch (metodoNormalizado) {
                case "insercaodireta":

                    compEqua = String.valueOf((n * (n - 1)) / 2); // n(n-1)/2
                    movEqua = String.valueOf(n * (n - 1));        // n(n-1)
                    arq.insercaoDireta();
                    break;

                case "insercaobinaria":
                    compEqua = String.format("%.0f", n * (Math.log(n) / Math.log(2))); // n * log2(n)
                    movEqua = String.valueOf((n * (n - 1)) / 2);                        // n(n-1)/2
                    arq.insercaoBinaria();
                    break;

                case "selecaodireta":
                    compEqua = String.valueOf((n * (n - 1)) / 2); // n(n-1)/2
                    movEqua = String.valueOf(3 * (n - 1));        // 3(n-1)
                    arq.selecaoDireta();
                    break;

                case "bubble":
                    compEqua = String.valueOf((n * (n - 1)) / 2);            // n(n-1)/2
                    movEqua = String.valueOf(3 * ((n * (n - 1)) / 2));       // 3(n(n-1)/2)
                    arq.bubble();
                    break;

                case "shake":
                    compEqua = String.valueOf((n * (n - 1)) / 2);            // n(n-1)/2
                    movEqua = String.valueOf(3 * ((n * (n - 1)) / 2));       // 3(n(n-1)/2)
                    arq.shake();
                    break;
                case "shell":
                    arq.shell();
                    break;
                case "heap":
                    arq.heap();
                    break;
                case "quickspivo":
                    arq.quickSPivo();
                    break;
                case "quickcpivo":
                    arq.quickCPivo();
                    break;
                case "merge1":
                    arq.merge1();
                    break;
                case "merge2":
                    arq.merge2();
                    break;
                case "comb":
                    arq.comb();
                    break;
                case "gnome":
                    arq.gnome();
                    break;
                case "tim":
                    arq.tim();
                    break;
                case "radix":
                    arq.radix();
                    break;

                case "counting":
                    arq.counting();
                    break;

                case "bucket":
                    arq.bucket();
                    break;

            }

            long fim = System.currentTimeMillis();

            out.printf("%-23s %10d %10d %10s %10d %10s\n",
                    metodo + " (" + tipo + ")",
                    (fim - inicio),
                    arq.getComp(),       // Comparações programadas
                    compEqua,            // Comparações teóricas (equação)
                    arq.getMov(),        // Movimentações programadas
                    movEqua);            // Movimentações teóricas (equação)


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void clonar(Arquivo origem, Arquivo destino) throws IOException {
        destino.getFile().setLength(0);
        Registro r = new Registro();
        for (int i = 0; i < origem.filesize(); i++) {
            origem.seekArq(i);
            r.leDoArq(origem.getFile());
            destino.seekArq(i);
            r.gravaNoArq(destino.getFile());
        }
    }
}
