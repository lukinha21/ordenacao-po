package Lista;

import java.util.Random;

public class Lista {
    private No inicio;
    private No fim;

    public Lista() {
        this.inicio = null;
        this.fim = null;
    }

    public void inicializa(){
        inicio = null;
        fim = null;
    }

    public void inserirInicio(int info){
        No nova = new No(null, info, inicio);
        if(inicio==null){
            inicio = fim =  nova;
        }
        else{
            inicio.setAnt(nova);
            inicio = nova;
        }
    }

    public void insercaoFinal(int info){
        No nova = new No(fim, info, null);
        if(fim==null){
            inicio = fim = nova;
        }
        else{
            fim.setProx(nova);
            fim = nova;
        }
    }
    public No retornaPrimeiro(){
        return inicio;
    }
    public int removePrimeiro() {
        int i=inicio.getInfo();
        inicio = inicio.getProx();
        return i;
    }
    public void preencher(int qtd){
        Random random = new Random();
        for (int i = 0; i < qtd; i++) {
            No nova = new No(null, random.nextInt(100)+1, inicio);
            if(inicio==null){
                inicio = fim =  nova;
            }
            else{
                inicio.setAnt(nova);
                inicio = nova;
            }
        }
    }

    public void exibir(){
        No aux = inicio;
        while(aux!=null){
            System.out.print("| " + aux.getInfo()+" ");
            aux=aux.getProx();
        }
        System.out.println("|");
    }

    public No buscaExaustiva(int info){
        No aux = inicio;
        while(aux!=null && aux.getInfo()!=info)
            aux=aux.getProx();
        return aux;
    }

    public No posicionaMeio(No ini, No fim){
        No aux=ini;
        int i;
        for(i=1;aux!=fim;i++)
            aux=aux.getProx();
        aux=ini;
        i=(i-1)/2;
        for(int j=0;j<i;j++){
            aux=aux.getProx();
        }
        return aux;
    }

    private No getNoNaPosicao(int pos) {
        No aux = inicio;
        int i = 0;
        while (aux != null && i < pos) {
            aux = aux.getProx();
            i++;
        }
        return aux;
    }


    public int buscaBinaria(int chave, int TL) {
        int inicio = 0, fim = TL - 1, meio = 0;
        int valorMeio;

        while (inicio <= fim) {
            meio = (inicio + fim) / 2;
            No meioNo = getNoNaPosicao(meio);

            if (meioNo == null) break; // proteção extra
            valorMeio = meioNo.getInfo();

            if (chave < valorMeio)
                fim = meio - 1;
            else
                inicio = meio + 1;
        }

        return inicio; // posição correta para inserção
    }
    public void remover(int info){
        No aux = buscaExaustiva(info);
        if(aux!=null){
            if(inicio==fim){
                inicio=fim=null;
            }else if(aux==inicio){
                inicio = aux.getProx();
                inicio.setAnt(null);
            }else if(aux==fim){
                fim = aux.getAnt();
                fim.setProx(null);
            }else{
                aux.getAnt().setProx(aux.getProx());
                aux.getProx().setAnt(aux.getAnt());
            }
        }
    }

    public void insercaoDireta(){ //inicia i no segundo elemento, enquanto i>0 e aux maior que vet[i-1] vai trocando, dps incrementa i ate tl
        int aux;
        No pos, i=inicio.getProx();
        while (i!=null) {
            aux=i.getInfo();
            pos=i;
            while(pos!=inicio && aux<pos.getAnt().getInfo()){
                pos.setInfo(pos.getAnt().getInfo());
                pos = pos.getAnt();
            }
            pos.setInfo(aux);
            i = i.getProx();
        }
    }

    public void insercaoBinaria() {
        No i = inicio.getProx(); // primeiro elemento real
        int idx = 1;

        while (i != null) {
            int chave = i.getInfo();

            // busca posição correta de inserção com base nos primeiros idx elementos
            int pos = buscaBinaria(chave, idx);

            No j = i;
            // move os elementos para frente até a posição correta
            while (idx > pos) {
                j.setInfo(j.getAnt().getInfo());
                j = j.getAnt();
                idx--;
            }

            j.setInfo(chave);

            i = i.getProx();
            idx++; // avança o índice
        }
    }


    public void selecaoDireta() {
        No inicio = this.inicio;
        No aux=inicio;
        No menor;
        int troca;
        while(inicio!=fim){
            menor=inicio;
            aux=inicio.getProx();
            while(aux!=null){
                if(aux.getInfo()<menor.getInfo()){
                    menor=aux;
                }
                aux=aux.getProx();
            }
            troca=inicio.getInfo();
            inicio.setInfo(menor.getInfo());
            menor.setInfo(troca);
            inicio=inicio.getProx();
        }
    }

    public void bubble() {
        No ultima = fim;
        No aux;
        int auxi;
        boolean troca=true;
        while(ultima!=inicio.getProx() && troca){
            troca=false;
            aux=inicio;
            while(aux!=ultima){
                if(aux.getInfo()>aux.getProx().getInfo()){
                    troca=true;
                    auxi=aux.getInfo();
                    aux.setInfo(aux.getProx().getInfo());
                    aux.getProx().setInfo(auxi);
                }
                aux=aux.getProx();
            }
            ultima=ultima.getAnt();
        }
    }

    public void shake() {
        No ultima= fim;
        No ini = inicio;
        No aux;
        int auxi;
        boolean troca=true;
        while(ini!=ultima && troca){
            troca=false;
            aux=ini;
            while(aux!=ultima){
                if(aux.getInfo()>aux.getProx().getInfo()){
                    troca=true;
                    auxi=aux.getInfo();
                    aux.setInfo(aux.getProx().getInfo());
                    aux.getProx().setInfo(auxi);
                }
                aux=aux.getProx();
            }
            ultima=ultima.getAnt();
            if(troca){
                aux=ultima;
                while(aux!=ini){
                    if(aux.getInfo()<aux.getAnt().getInfo()){
                        troca=true;
                        auxi=aux.getInfo();
                        aux.setInfo(aux.getAnt().getInfo());
                        aux.getAnt().setInfo(auxi);
                    }
                    aux=aux.getAnt();
                }
            }
            ini=ini.getProx();
        }
    }
    private No posicionaPai(No ult) {
        No aux=inicio;
        int i;
        for(i=1;aux!=ult;i++)
            aux=aux.getProx();
        i=i/2-1;
        aux=inicio;
        for(int j=0;j<i;j++){
            aux=aux.getProx();
        }
        return aux;
    }
    private No posicionaFilho(No pai, No ult) {
        No aux=inicio;
        int i;
        for(i=0;aux!=pai;i++)
            aux=aux.getProx();
        aux=inicio;
        for(int j=0;j<i*2+1;j++)
            aux=aux.getProx();
        return aux;
    }
    private boolean filhoDireitaDentro(No FE, No ultima) {
        No aux;
        aux=inicio;
        if(FE!=null){
            while(aux!=FE && aux!=ultima)
                aux=aux.getProx();
            if(aux==FE)
                return true;
        }
        return false;
    }
    public void heap() {
        No ult = fim;
        No FE, FD;
        No pai;
        No maiorF;
        int aux;
        while(ult!=inicio){
            pai = posicionaPai(ult);
            while(pai!=null){
                FE=posicionaFilho(pai, ult);
                FD=FE.getProx();
                maiorF=FE;
                if(filhoDireitaDentro(FD,ult) && FD.getInfo()>FE.getInfo())
                    maiorF=FD;
                if(maiorF.getInfo()>pai.getInfo()){
                    aux=pai.getInfo();
                    pai.setInfo(maiorF.getInfo());
                    maiorF.setInfo(aux);
                }
                pai=pai.getAnt();
            }
            aux=ult.getInfo();
            ult.setInfo(inicio.getInfo());
            inicio.setInfo(aux);
            ult=ult.getAnt();
        }
    }

    private int calculaDist() {
        No aux = inicio;
        int i;
        for(i=0; aux!=null;i++)
            aux=aux.getProx();
        int dist=1;
        while(dist<i)
            dist=dist*3+1;
        dist=dist/3;
        return dist;
    }
    private No posicionaPont(int pos) {
        No aux=inicio;
        int i;
        for(i=0;i<pos;i++)
            aux=aux.getProx();
        return aux;
    }
    private No posicionaJDist(No no, int dist){
        No aux=no;
        for(int i=0;aux!=null && i<dist;i++)
            aux=aux.getAnt();
        return aux;
    }
    public void shell() {
        int dist = calculaDist(), aux;
        No i,j,jdist;
        while(dist>0){
            i = posicionaPont(dist);
            while(i!=null){
                aux=i.getInfo();
                j=i;
                jdist = posicionaJDist(j,dist);
                while(jdist!=null && aux<jdist.getInfo()){
                    j.setInfo(jdist.getInfo());
                    j=jdist;
                    jdist = posicionaJDist(j,dist);
                }
                j.setInfo(aux);
                i=i.getProx();
            }
            dist=dist/3;
        }
    }

    private int tl() {
        No aux = inicio;
        int i=0;
        while(aux!=null){
            aux=aux.getProx();
            i++;
        }
        return i;
    }

    private void setValor(Lista lista, int pos, int info) {
        No aux= lista.inicio;
        for (int i = 0; i < pos; i++)
            aux=aux.getProx();
        aux.setInfo(info);
    }
    public void counting() {
        No aux = inicio, auxs;
        Lista saida = new Lista();
        saida.inicializa();
        int maior = aux.getInfo();
        aux=aux.getProx();
        saida.insercaoFinal(0);
        while(aux!=null) {
            if(aux.getInfo()>maior)
                maior= aux.getInfo();
            aux=aux.getProx();
            saida.insercaoFinal(0);
        }
        int cont[] = new int[maior+1];
        aux=inicio;
        while(aux!=null){
            cont[aux.getInfo()]++;
            aux=aux.getProx();
        }
        for(int i=1;i<=maior;i++)
            cont[i]=cont[i-1]+cont[i];
        aux=fim;
        while(aux!=null){
            setValor(saida,cont[aux.getInfo()]-1,aux.getInfo());
            cont[aux.getInfo()]--;
            aux=aux.getAnt();
        }
        aux=inicio;
        auxs=saida.inicio;
        while (aux!=null) {
            aux.setInfo(auxs.getInfo());
            aux=aux.getProx();
            auxs=auxs.getProx();
        }
    }

    public void quickSpivo() {
        quickSP(inicio,fim);
    }

    private boolean estaAntes(No antes, No dps) {
        if(antes==dps)
            return false;
        while(antes!=dps && antes!=null)
            antes=antes.getProx();
        if(antes==dps)
            return true;
        return false;
    }
    private void quickSP(No ini, No fim) {
        No i=ini, j=fim;
        int aux;
        boolean flag = true;
        while (i!=j){
            if(flag)
                while(i!=j && i.getInfo()<=j.getInfo())
                    i=i.getProx();
            else
                while(i!=j && i.getInfo()<=j.getInfo())
                    j=j.getAnt();
            aux=i.getInfo();
            i.setInfo(j.getInfo());
            j.setInfo(aux);
            flag=!flag;
        }
        if(estaAntes(ini,i.getAnt()) && i.getAnt()!=null)
            quickSP(ini,i.getAnt());
        if(estaAntes(j.getProx(),fim) && j.getProx()!=null)
            quickSP(j.getProx(),fim);
    }

    public void quickCpivo() {
        quickCP(inicio,fim);
    }

    private int getPivo(No ini, No fim) {
        No aux = ini;
        int i=1;
        while(aux!=fim) {
            aux = aux.getProx();
            i++;
        }
        int j=0;
        aux=ini;
        while(j<i/2){
            aux=aux.getProx();
            j++;
        }
        return aux.getInfo();
    }
    private boolean estaAntesOuIgual(No antes, No dps) {
        while(antes!=dps && antes!=null)
            antes=antes.getProx();
        if(antes==dps)
            return true;
        return false;
    }
    private void quickCP(No ini, No fim) {
        No i=ini, j=fim;
        int pivo = getPivo(ini,fim);
        int aux;
        while (estaAntes(i,j)){
            while(i.getInfo()<pivo)
                    i=i.getProx();
            while(j.getInfo()>pivo)
                    j=j.getAnt();
            if(estaAntesOuIgual(i,j)) {
                aux = i.getInfo();
                i.setInfo(j.getInfo());
                j.setInfo(aux);
                i=i.getProx();
                j=j.getAnt();
            }
        }
        if(estaAntes(ini,j) && i!=null)
            quickCP(ini,j);
        if(estaAntes(i,fim) && j!=null)
            quickCP(i,fim);
    }


    public void bucket(int qtd) {
        No aux= inicio.getProx();
        int maior= inicio.getInfo(),i,j=0;
        while(aux!=null){
            if(aux.getInfo()>maior)
                maior=aux.getInfo();
            aux=aux.getProx();
        }
        Lista baldes[] = new Lista[qtd];
        for (i = 0; i < qtd; i++) {
            baldes[i] = new Lista();
            baldes[i].inicializa();
        }
        aux=inicio;
        while(aux!=null){
            baldes[(aux.getInfo()*qtd)/(maior+1)].insercaoFinal(aux.getInfo());
            aux=aux.getProx();
        }
        for (i = 0; i < qtd; i++) {
            if(baldes[i].retornaPrimeiro()!=null)
                baldes[i].insercaoDireta();
        }
        aux=inicio;
        while(aux!=null){
            while(j<qtd && baldes[j].inicio!=null){
                aux.setInfo(baldes[j].removePrimeiro());
                aux=aux.getProx();
            }
            j++;
        }
    }
    public void countingRadix(int chave) {
        No aux = inicio, auxs;
        Lista saida = new Lista();
        saida.inicializa();
        int maior = aux.getInfo();
        aux=aux.getProx();
        saida.insercaoFinal(0);
        while(aux!=null) {
            if(aux.getInfo()>maior)
                maior= aux.getInfo();
            aux=aux.getProx();
            saida.insercaoFinal(0);
        }
        int cont[] = new int[maior+1];
        aux=inicio;
        while(aux!=null){
            cont[(aux.getInfo()/chave)%10]++;
            aux=aux.getProx();
        }
        for(int i=1;i<=maior;i++)
            cont[i]=cont[i-1]+cont[i];
        aux=fim;
        while(aux!=null){
            setValor(saida,cont[(aux.getInfo()/chave)%10]-1,aux.getInfo());
            cont[(aux.getInfo()/chave)%10]--;
            aux=aux.getAnt();
        }
        aux=inicio;
        auxs=saida.inicio;
        while (aux!=null) {
            aux.setInfo(auxs.getInfo());
            aux=aux.getProx();
            auxs=auxs.getProx();
        }
    }
    public void radix() {
        No aux = inicio;
        int chave;
        int maior = aux.getInfo();
        aux=aux.getProx();
        while(aux!=null) {
            if(aux.getInfo()>maior)
                maior= aux.getInfo();
            aux=aux.getProx();
        }
        for (chave=1; maior/chave>0; chave*=10)
            countingRadix(chave);
    }

    private No andaGap(No aux, int gap) {
        for (int i = 0; aux!=null && i < gap; i++) {
            aux=aux.getProx();
        }
        return aux;
    }
    public void comb() {
        No aux=inicio,agap;
        int i, gap, iaux;
        for (i=0;aux!=null;i++)
            aux=aux.getProx();
        gap=i;
        boolean troca=true;
        while(gap > 1 || troca){
            gap=gap*10/13;
            if(gap<=1)
                gap=1;
            troca=false;
            aux=inicio;
            agap = andaGap(aux, gap);
            while(agap!=null){
                if(aux.getInfo()>agap.getInfo()){
                    iaux=aux.getInfo();
                    aux.setInfo(agap.getInfo());
                    agap.setInfo(iaux);
                    troca=true;
                }
                aux=aux.getProx();
                agap = andaGap(aux, gap);
            }
        }

    }
    public void gnome() {
        No aux = inicio;
        int iaux;
        while(aux!=null){
            if(aux==inicio)
                aux=aux.getProx();
            if(aux.getInfo()>=aux.getAnt().getInfo())
                aux=aux.getProx();
            else{
                iaux=aux.getInfo();
                aux.setInfo(aux.getAnt().getInfo());
                aux.getAnt().setInfo(iaux);
                aux=aux.getAnt();
            }
        }
    }

    private void fusao(Lista l1, Lista l2, int seq) {
        int i=0,j=0,k=0,seqaux=seq;
        No aux = inicio;
        No aux1 = l1.inicio;
        No aux2 = l2.inicio;
        while(aux!=null){
            while (i < seq && j < seq) {
                if (aux1.getInfo() > aux2.getInfo()) {
                    aux.setInfo(aux2.getInfo());
                    aux=aux.getProx();
                    aux2=aux2.getProx();
                    j++;
                }
                else{
                    aux.setInfo(aux1.getInfo());
                    aux=aux.getProx();
                    aux1=aux1.getProx();
                    i++;
                }
            }
            while(i<seq){
                aux.setInfo(aux1.getInfo());
                aux=aux.getProx();
                aux1=aux1.getProx();
                i++;
            }
            while(j<seq){
                aux.setInfo(aux2.getInfo());
                aux=aux.getProx();
                aux2=aux2.getProx();
                j++;
            }
            seq=seq+seqaux;
        }
    }

    private void particao(Lista l1, Lista l2, int tam) {
        No aux=inicio;
        No aux1=l1.inicio;
        for (int i = 0; i < tam/2; i++) {
            aux1.setInfo(aux.getInfo());
            aux=aux.getProx();
            aux1=aux1.getProx();
        }
        No aux2=l2.inicio;
        for (int i = 0; i < tam/2; i++) {
            aux2.setInfo(aux.getInfo());
            aux=aux.getProx();
            aux2=aux2.getProx();
        }
    }
    public void merge() {
        Lista l1 = new Lista();
        l1.inicializa();
        Lista l2 = new Lista();
        l2.inicializa();
        No aux = inicio;
        int i=0;
        while(aux!=null){
            aux=aux.getProx();
            i++;
        }
        for (int j = 0; j < i/2; j++) {
            l1.insercaoFinal(0);
            l2.insercaoFinal(0);
        }
        int seq=1;
        while(seq<i){
            particao(l1,l2,i);
            fusao(l1,l2,seq);
            seq=seq*2;
        }
    }

    private int calcTam() {
        int i;
        No aux=inicio;
        for(i=0; aux!=null;i++)
            aux=aux.getProx();
        return i;
    }
    public void mergeSort() {
        Lista aux = new Lista();
        aux.inicializa();
        No naux = inicio;
        while(naux!=null){
            aux.insercaoFinal(0);
            naux=naux.getProx();
        }
        int i = calcTam();
        mergeDiv(aux, 0, i);
    }

    private void mergeDiv(Lista aux, int esq, int dir) {
        int meio;
        if(esq<dir){
            meio=(esq+dir)/2;
            mergeDiv(aux, esq, meio);
            mergeDiv(aux, meio+1, dir);
            fusaoM(aux, esq, meio, meio+1, dir);
        }
    }

    private void fusaoM(Lista aux, int ini1, int fim1, int ini2, int fim2) {
        int i=ini1, j=ini2, k=0;
        No noi = posicionaPont(i), noj = posicionaPont(j), noa=aux.inicio , nol;
        while(noi!=null && i<=fim1 && noj!=null && j<=fim2){
            if (noi.getInfo() > noj.getInfo()){
                noa.setInfo(noj.getInfo());
                noa=noa.getProx();
                noj=noj.getProx();
                k++;
                j++;
            }
            else{
                noa.setInfo(noi.getInfo());
                noa=noa.getProx();
                noi=noi.getProx();
                k++;
                i++;
            }
        }
        while(noi!=null && i<=fim1){
            noa.setInfo(noi.getInfo());
            noa=noa.getProx();
            noi=noi.getProx();
            k++;
            i++;
        }
        while(noj != null && j<=fim2) {
            noa.setInfo(noj.getInfo());
            noa=noa.getProx();
            noj=noj.getProx();
            k++;
            j++;
        }
        noa=aux.inicio;
        nol = posicionaPont(ini1);
        for(i=0;i<k;i++){
            nol.setInfo(noa.getInfo());
            nol=nol.getProx();
            noa=noa.getProx();
        }
    }
    private int tamMin(int runs) {
        int tam=runs;
        int i = 0;
        while (tam >= runs) {
            tam /= runs;
            i++;
        }
        return tam + i;
    }

    private int min(int i, int j) {
        return (i <= j) ? i : j;
    }

    public void insercaoDiretaTim(No esq, No dir){
        int aux;
        No pos, i=esq.getProx();
        while (i!=dir.getProx()) {
            aux=i.getInfo();
            pos=i;
            while(pos!=esq && aux<pos.getAnt().getInfo()){
                pos.setInfo(pos.getAnt().getInfo());
                pos = pos.getAnt();
            }
            pos.setInfo(aux);
            i = i.getProx();
        }
    }

    private void mergeTim(int esq, int meio, int dir) {
        int tam1 = meio - esq + 1, tam2 = dir - meio;
        Lista l1 = new Lista();
        l1.inicializa();
        Lista l2 = new Lista();
        l2.inicializa();
        for (int pos=0; pos<tam1; pos++)
            l1.insercaoFinal(posicionaPont(esq + pos).getInfo());

        for (int pos=0; pos<tam2; pos++)
            l2.insercaoFinal(posicionaPont(meio + 1 + pos).getInfo());

        int i=0,j=0;
        No aux = posicionaPont(esq);
        No aux1 = l1.inicio;
        No aux2 = l2.inicio;
        while (aux1!=null && aux2!=null && i < tam1 && j < tam2) {
            if (aux1.getInfo() >= aux2.getInfo()) {
                aux.setInfo(aux2.getInfo());
                aux=aux.getProx();
                aux2=aux2.getProx();
                j++;
            }
            else{
                aux.setInfo(aux1.getInfo());
                aux=aux.getProx();
                aux1=aux1.getProx();
                i++;
            }
        }
        while(aux1!=null && i<tam1){
            aux.setInfo(aux1.getInfo());
            aux=aux.getProx();
            aux1=aux1.getProx();
            i++;
        }
        while(aux2!=null && j<tam2){
            aux.setInfo(aux2.getInfo());
            aux=aux.getProx();
            aux2=aux2.getProx();
            j++;
        }
    }
    public void tim(int runs){
        int run = tamMin(runs);
        int TL = calcTam();
        for (int i = 0; i < TL; i += run) {
            insercaoDiretaTim(posicionaPont(i),posicionaPont(min((i + runs - 1), (TL - 1))));
        }
        for (int tam = run; tam < TL; tam = 2 * tam) {
            for (int esq = 0; esq < TL; esq += 2 * tam) {
                int meio = esq + tam - 1;
                int dir = min((esq + 2 * tam - 1),(TL - 1));
                if (meio < dir)
                    mergeTim(esq, meio, dir);
            }
        }
    }
}
