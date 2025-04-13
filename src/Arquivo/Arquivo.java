package Arquivo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Arquivo {
    private String nome;
    private RandomAccessFile arq;
    private int comp = 0, mov = 0;

    public Arquivo(String nome) {
        this.nome = nome;
        try {
            arq = new RandomAccessFile(nome, "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RandomAccessFile getFile() {
        return arq;
    }

    public void truncate(long pos) {
        try {
            arq.setLength(pos * Registro.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean eof() {
        try {
            return arq.getFilePointer() >= arq.length();
        } catch (IOException e) {
            return true;
        }
    }

    public void seekArq(int pos) {
        try {
            arq.seek(pos * Registro.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int filesize() {
        try {
            return (int)(arq.length() / Registro.length());
        } catch (IOException e) {
            return 0;
        }
    }

    public void initComp() { comp = 0; }
    public void initMov()  { mov = 0; }
    public int getComp()   { return comp; }
    public int getMov()    { return mov; }
    public void geraArquivoOrdenado() {
        try {
            arq.setLength(0); // limpa
            for (int i = 0; i < 128; i++) {
                Registro r = new Registro(i);
                r.gravaNoArq(arq);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void geraArquivoReverso() {
        try {
            arq.setLength(0);
            for (int i = 128; i >= 0; i--) {
                Registro r = new Registro(i);
                r.gravaNoArq(arq);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void geraArquivoRandomico() {
        try {
            arq.setLength(0);
            Random rand = new Random();
            for (int i = 0; i < 128; i++) {
                Registro r = new Registro(rand.nextInt(10000));
                r.gravaNoArq(arq);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void insercaoDireta() {
        try {
            int TL = filesize();
            Registro reg1 = new Registro();
            Registro reg2 = new Registro();

            for (int i = 1; i < TL; i++) {
                seekArq(i);
                reg1.leDoArq(arq);
                mov++; // leitura do aux

                int j = i - 1;
                while (j >= 0) {
                    seekArq(j);
                    reg2.leDoArq(arq);
                    if (reg2.getNumero() > reg1.getNumero()) {
                        comp++; // <-- só conta se a comparação envolver .getNumero()
                        seekArq(j + 1);
                        reg2.gravaNoArq(arq);
                        mov++;
                        j--;
                    } else {
                        comp++; // também conta a comparação mesmo que dê falso
                        break;
                    }


                }
                seekArq(j + 1);
                reg1.gravaNoArq(arq);
                mov++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void insercaoBinaria() {
        try {
            int TL = filesize();
            Registro regI = new Registro();
            Registro regJ = new Registro();

            for (int i = 1; i < TL; i++) {
                seekArq(i);
                regI.leDoArq(arq);
                mov++;

                int inicio = 0, fim = i - 1, meio = 0;
                while (inicio <= fim) {
                    meio = (inicio + fim) / 2;
                    seekArq(meio);
                    regJ.leDoArq(arq);
                    if (regI.getNumero() < regJ.getNumero()) {
                        comp++;
                        fim = meio - 1;
                    } else {
                        comp++;
                        inicio = meio + 1;
                    }

                }

                for (int j = i; j > inicio; j--) {
                    seekArq(j - 1);
                    regJ.leDoArq(arq);
                    seekArq(j);
                    regJ.gravaNoArq(arq);
                    mov++;
                }

                seekArq(inicio);
                regI.gravaNoArq(arq);
                mov++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void selecaoDireta() {
        try {
            int TL = filesize();
            Registro regI = new Registro();
            Registro regMenor = new Registro();
            Registro regAux = new Registro();

            for (int i = 0; i < TL - 1; i++) {
                int posMenor = i;

                seekArq(posMenor);
                regMenor.leDoArq(arq);

                for (int j = i + 1; j < TL; j++) {
                    seekArq(j);
                    regAux.leDoArq(arq);
                    comp++;
                    if (regAux.getNumero() < regMenor.getNumero()) {
                        posMenor = j;
                        regMenor = new Registro(regAux.getNumero());
                    }
                }

                if (posMenor != i) {
                    seekArq(i);
                    regI.leDoArq(arq);
                    seekArq(posMenor);
                    regI.gravaNoArq(arq);
                    seekArq(i);
                    regMenor.gravaNoArq(arq);
                    mov += 2; // ← isso precisa existir

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void bubble() {
        try {
            int TL = filesize();
            Registro r1 = new Registro(), r2 = new Registro();

            for (int i = TL - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    seekArq(j);
                    r1.leDoArq(arq);
                    seekArq(j + 1);
                    r2.leDoArq(arq);
                    comp++;
                    if (r1.getNumero() > r2.getNumero()) {
                        seekArq(j);
                        r2.gravaNoArq(arq);
                        seekArq(j + 1);
                        r1.gravaNoArq(arq);
                        mov += 2;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void shake() {
        try {
            int TL = filesize();
            Registro r1 = new Registro(), r2 = new Registro();
            boolean troca = true;
            int ini = 0, fim = TL - 1;

            while (ini < fim && troca) {
                troca = false;
                for (int i = ini; i < fim; i++) {
                    seekArq(i);
                    r1.leDoArq(arq);
                    seekArq(i + 1);
                    r2.leDoArq(arq);
                    comp++;
                    if (r1.getNumero() > r2.getNumero()) {
                        seekArq(i);
                        r2.gravaNoArq(arq);
                        seekArq(i + 1);
                        r1.gravaNoArq(arq);
                        mov += 2;
                        troca = true;
                    }
                }
                fim--;

                for (int i = fim; i > ini; i--) {
                    seekArq(i);
                    r1.leDoArq(arq);
                    seekArq(i - 1);
                    r2.leDoArq(arq);
                    comp++;
                    if (r1.getNumero() < r2.getNumero()) {
                        seekArq(i);
                        r2.gravaNoArq(arq);
                        seekArq(i - 1);
                        r1.gravaNoArq(arq);
                        mov += 2;
                        troca = true;
                    }
                }
                ini++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void shell() {
        try {
            int TL = filesize();
            Registro r1 = new Registro(), r2 = new Registro();
            int dist = 1;
            while (dist < TL)
                dist = dist * 3 + 1;
            dist /= 3;

            while (dist > 0) {
                for (int i = dist; i < TL; i++) {
                    seekArq(i);
                    r1.leDoArq(arq);
                    mov++;

                    int j = i;
                    while (j - dist >= 0) {
                        seekArq(j - dist);
                        r2.leDoArq(arq);
                        comp++;
                        if (r1.getNumero() < r2.getNumero()) {
                            seekArq(j);
                            r2.gravaNoArq(arq);
                            mov++;
                            j -= dist;
                        } else break;
                    }
                    seekArq(j);
                    r1.gravaNoArq(arq);
                    mov++;
                }
                dist /= 3;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void heap() {
        try {
            int TL = filesize();
            Registro pai = new Registro(), fe = new Registro(), fd = new Registro(), aux = new Registro();

            while (TL > 1) {
                int p = TL / 2 - 1;
                while (p >= 0) {
                    int feIdx = 2 * p + 1;
                    int fdIdx = feIdx + 1;
                    int maiorF = feIdx;

                    seekArq(feIdx);
                    fe.leDoArq(arq);

                    if (fdIdx < TL) {
                        seekArq(fdIdx);
                        fd.leDoArq(arq);
                        comp++;
                        if (fd.getNumero() > fe.getNumero())
                            maiorF = fdIdx;
                    }

                    seekArq(p);
                    pai.leDoArq(arq);
                    seekArq(maiorF);
                    aux.leDoArq(arq);
                    comp++;
                    if (aux.getNumero() > pai.getNumero()) {
                        seekArq(p);
                        aux.gravaNoArq(arq);
                        seekArq(maiorF);
                        pai.gravaNoArq(arq);
                        mov += 2;
                    }

                    p--;
                }

                seekArq(0);
                aux.leDoArq(arq);
                seekArq(TL - 1);
                pai.leDoArq(arq);
                seekArq(0);
                pai.gravaNoArq(arq);
                seekArq(TL - 1);
                aux.gravaNoArq(arq);
                mov += 2;

                TL--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void quickSPivo() {
        try {
            quickSP(0, filesize() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void quickSP(int ini, int fim) throws IOException {
        int i = ini, j = fim;
        boolean flag = true;

        Registro r1 = new Registro(), r2 = new Registro();

        while (i < j) {
            if (flag) {
                while (i < j) {
                    seekArq(i);
                    r1.leDoArq(arq);
                    seekArq(j);
                    r2.leDoArq(arq);
                    comp++; // ← comparação programada
                    if (r1.getNumero() > r2.getNumero()) break;
                    i++;
                }
            } else {
                while (i < j) {
                    seekArq(i);
                    r1.leDoArq(arq);
                    seekArq(j);
                    r2.leDoArq(arq);
                    comp++; // ← comparação programada
                    if (r1.getNumero() > r2.getNumero()) break;
                    j--;
                }
            }

            if (i < j) {
                // Troca
                seekArq(i);
                r1.leDoArq(arq);
                seekArq(j);
                r2.leDoArq(arq);

                seekArq(i);
                r2.gravaNoArq(arq);
                seekArq(j);
                r1.gravaNoArq(arq);

                mov += 2; // ← movimentação programada
                flag = !flag;
            }
        }

        if (ini < i - 1) quickSP(ini, i - 1);
        if (j + 1 < fim) quickSP(j + 1, fim);
    }

    public void quickCPivo() {
        try {
            quickC(0, filesize() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void quickC(int ini, int fim) throws IOException {
        int i = ini, j = fim;
        Registro ri = new Registro(), rj = new Registro(), rMid = new Registro();

        seekArq((ini + fim) / 2);
        rMid.leDoArq(arq);
        int pivo = rMid.getNumero();

        while (i <= j) {
            do {
                seekArq(i);
                ri.leDoArq(arq);
                comp++; // Comparação programada
                i++;
            } while (ri.getNumero() < pivo);

            do {
                seekArq(j);
                rj.leDoArq(arq);
                comp++; // Comparação programada
                j--;
            } while (rj.getNumero() > pivo);

            if (i <= j) {
                // Troca
                seekArq(i - 1);
                rj.gravaNoArq(arq);
                seekArq(j + 1);
                ri.gravaNoArq(arq);
                mov += 2; // ← movimentação programada
                i++;
                j--;
            }
        }

        if (ini < j) quickC(ini, j);
        if (i < fim) quickC(i, fim);
    }

    public void merge1() {
        try {
            int TL = filesize();
            Registro r1 = new Registro(), r2 = new Registro();
            RandomAccessFile aux = new RandomAccessFile("auxMerge1.dat", "rw");

            for (int seq = 1; seq < TL; seq *= 2) {
                int i = 0;

                while (i + seq < TL) {
                    int ini1 = i;
                    int fim1 = i + seq - 1;
                    int ini2 = fim1 + 1;
                    int fim2 = Math.min(ini2 + seq - 1, TL - 1);

                    int i1 = ini1, i2 = ini2, k = ini1;

                    while (i1 <= fim1 && i2 <= fim2) {
                        seekArq(i1);
                        r1.leDoArq(arq);
                        seekArq(i2);
                        r2.leDoArq(arq);
                        comp++;

                        if (r1.getNumero() <= r2.getNumero()) {
                            aux.seek(k * Registro.length());
                            r1.gravaNoArq(aux);
                            mov++;
                            i1++;
                        } else {
                            aux.seek(k * Registro.length());
                            r2.gravaNoArq(aux);
                            mov++;
                            i2++;
                        }
                        k++;
                    }

                    while (i1 <= fim1) {
                        seekArq(i1++);
                        r1.leDoArq(arq);
                        aux.seek(k++ * Registro.length());
                        r1.gravaNoArq(aux);
                        mov++;
                    }

                    while (i2 <= fim2) {
                        seekArq(i2++);
                        r2.leDoArq(arq);
                        aux.seek(k++ * Registro.length());
                        r2.gravaNoArq(aux);
                        mov++;
                    }

                    i = fim2 + 1;
                }

                while (i < TL) {
                    seekArq(i);
                    r1.leDoArq(arq);
                    aux.seek(i * Registro.length());
                    r1.gravaNoArq(aux);
                    mov++;
                    i++;
                }

                // copiar de volta
                for (i = 0; i < TL; i++) {
                    aux.seek(i * Registro.length());
                    r1.leDoArq(aux);
                    seekArq(i);
                    r1.gravaNoArq(arq);
                }
            }

            aux.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void merge2() {
        try {
            Registro[] aux = new Registro[filesize()];
            for (int i = 0; i < aux.length; i++) aux[i] = new Registro();
            mergeRec(0, filesize() - 1, aux);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeRec(int ini, int fim, Registro[] aux) throws IOException {
        if (ini < fim) {
            int meio = (ini + fim) / 2;
            mergeRec(ini, meio, aux);
            mergeRec(meio + 1, fim, aux);
            fusaoMerge(ini, meio, fim, aux);
        }
    }

    private void fusaoMerge(int ini, int meio, int fim, Registro[] aux) throws IOException {
        int i = ini, j = meio + 1, k = 0;
        Registro r1 = new Registro(), r2 = new Registro();

        while (i <= meio && j <= fim) {
            seekArq(i); r1.leDoArq(arq);
            seekArq(j); r2.leDoArq(arq);
            comp++;
            aux[k++] = (r1.getNumero() <= r2.getNumero()) ? r1 : r2;
            if (r1.getNumero() <= r2.getNumero()) i++; else j++;
        }

        while (i <= meio) {
            seekArq(i++); r1.leDoArq(arq); aux[k++] = r1;
        }

        while (j <= fim) {
            seekArq(j++); r2.leDoArq(arq); aux[k++] = r2;
        }

        for (int m = 0; m < k; m++) {
            seekArq(ini + m);
            aux[m].gravaNoArq(arq);
            mov++;
        }
    }
    public void comb() {
        try {
            int TL = filesize();
            Registro r1 = new Registro(), r2 = new Registro();
            int gap = TL;
            boolean troca = true;

            while (gap > 1 || troca) {
                gap = (int)(gap / 1.3);
                if (gap < 1) gap = 1;
                troca = false;

                for (int i = 0; i + gap < TL; i++) {
                    seekArq(i); r1.leDoArq(arq);
                    seekArq(i + gap); r2.leDoArq(arq);
                    comp++;
                    if (r1.getNumero() > r2.getNumero()) {
                        seekArq(i); r2.gravaNoArq(arq);
                        seekArq(i + gap); r1.gravaNoArq(arq);
                        mov += 2;
                        troca = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void gnome() {
        try {
            int TL = filesize();
            Registro r1 = new Registro(), r2 = new Registro();
            int i = 0;

            while (i < TL) {
                if (i == 0) i++;
                seekArq(i); r1.leDoArq(arq);
                seekArq(i - 1); r2.leDoArq(arq);
                comp++;
                if (r1.getNumero() >= r2.getNumero()) {
                    i++;
                } else {
                    seekArq(i); r2.gravaNoArq(arq);
                    seekArq(i - 1); r1.gravaNoArq(arq);
                    mov += 2;
                    i--;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void insercaoTim(int ini, int fim) throws IOException {
        Registro aux = new Registro(), r = new Registro();
        for (int i = ini + 1; i <= fim; i++) {
            seekArq(i); aux.leDoArq(arq); mov++;
            int j = i - 1;
            while (j >= ini) {
                seekArq(j); r.leDoArq(arq); comp++;
                if (r.getNumero() > aux.getNumero()) {
                    seekArq(j + 1); r.gravaNoArq(arq); mov++;
                    j--;
                } else break;
            }
            seekArq(j + 1); aux.gravaNoArq(arq); mov++;
        }
    }

    public void tim() {
        try {
            int TL = filesize();
            int run = 32;

            for (int i = 0; i < TL; i += run) {
                insercaoTim(i, Math.min(i + run - 1, TL - 1));
            }

            for (int size = run; size < TL; size *= 2) {
                for (int left = 0; left < TL; left += 2 * size) {
                    int mid = left + size - 1;
                    int right = Math.min((left + 2 * size - 1), (TL - 1));
                    if (mid < right) fusaoMerge(left, mid, right, new Registro[TL]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void radix() throws IOException {
        int TL = filesize();
        Registro[] aux = new Registro[TL];
        int i, j, max = 0;
        Registro r = new Registro();

        // Encontra o maior número
        for (i = 0; i < TL; i++) {
            seekArq(i);
            r.leDoArq(arq);
            if (r.getNumero() > max) max = r.getNumero();
        }

        for (int exp = 1; max / exp > 0; exp *= 10) {
            int[] count = new int[10];

            // Contagem das ocorrências
            for (i = 0; i < TL; i++) {
                seekArq(i);
                r.leDoArq(arq);
                int digito = (r.getNumero() / exp) % 10;
                count[digito]++;
            }

            for (i = 1; i < 10; i++) count[i] += count[i - 1];

            for (i = TL - 1; i >= 0; i--) {
                seekArq(i);
                r.leDoArq(arq);
                int digito = (r.getNumero() / exp) % 10;
                aux[--count[digito]] = new Registro(r.getNumero()); // clone manual se necessário
            }

            for (i = 0; i < TL; i++) {
                seekArq(i);
                mov++;
                aux[i].gravaNoArq(arq);
            }
        }
    }


    public void bucket() throws IOException {
        int TL = filesize(), i;
        int divisor = 10;
        int maiorValor = 0;

        // Descobrir o maior valor
        for (i = 0; i < TL; i++) {
            seekArq(i);
            Registro reg = new Registro();
            reg.leDoArq(arq); // arq → arquivo (nome do atributo da classe)
            if (reg.getNumero() > maiorValor)
                maiorValor = reg.getNumero();
        }

        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>();
        for (int k = 0; k < divisor; k++) {
            buckets.add(new ArrayList<>());
        }

        for (i = 0; i < TL; i++) {
            seekArq(i);
            Registro reg = new Registro();
            reg.leDoArq(arq);
            int bucketIndex = Math.min((reg.getNumero() * divisor) / (maiorValor + 1), divisor - 1);
            buckets.get(bucketIndex).add(reg.getNumero());
        }

        for (ArrayList<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }

        int pos = 0;
        for (ArrayList<Integer> bucket : buckets) {
            for (Integer val : bucket) {
                Registro reg = new Registro(val);
                seekArq(pos++);
                reg.gravaNoArq(arq);
                mov++;
            }
        }
    }

    public void counting() throws IOException {
        int TL = filesize();
        Registro r = new Registro();
        int max = 0;

        for (int i = 0; i < TL; i++) {
            seekArq(i);
            r.leDoArq(arq);
            if (r.getNumero() > max) max = r.getNumero();
        }

        int[] count = new int[max + 1];
        Registro[] aux = new Registro[TL];

        for (int i = 0; i < TL; i++) {
            seekArq(i);
            r.leDoArq(arq);
            count[r.getNumero()]++;
        }

        for (int i = 1; i <= max; i++)
            count[i] += count[i - 1];

        for (int i = TL - 1; i >= 0; i--) {
            seekArq(i);
            r.leDoArq(arq);
            aux[--count[r.getNumero()]] = new Registro(r.getNumero()); // clone manual se necessário
        }

        for (int i = 0; i < TL; i++) {
            seekArq(i);
            mov++;
            aux[i].gravaNoArq(arq);
        }
    }


}
