package view;

import java.text.NumberFormat;
import java.util.Locale;

/** Utilitários de formatação e leitura de números usados pelas telas. */
final class Formato {
    private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    private Formato() {
    }

    static String moeda(double valor) {
        return MOEDA.format(valor);
    }

    /** Aceita tanto "10.5" quanto "10,5". */
    static double lerDecimal(String texto) {
        return Double.parseDouble(texto.trim().replace(',', '.'));
    }
}
