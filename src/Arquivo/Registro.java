package Arquivo;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Registro {
    public static final int TF = 1022;

    private int numero;
    private char[] lixo = new char[TF];

    public Registro(int numero) {
        this.numero = numero;
        for (int i = 0; i < TF; i++)
            lixo[i] = 'X';
    }

    public Registro() {
        for (int i = 0; i < TF; i++)
            lixo[i] = 'X';
    }

    public void gravaNoArq(RandomAccessFile arq) throws IOException {
        arq.writeInt(numero);
        for (int i = 0; i < TF; i++)
            arq.writeChar(lixo[i]);
    }

    public void leDoArq(RandomAccessFile arq) throws IOException {
        numero = arq.readInt();
        for (int i = 0; i < TF; i++)
            lixo[i] = arq.readChar();
    }

    public static int length() {
        return 4 + 2 * TF; // 4 bytes int + 2 bytes por char * 1022
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
