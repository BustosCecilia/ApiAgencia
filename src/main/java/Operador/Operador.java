package Operador;

import java.util.Arrays;
import java.util.Comparator;

public class Operador {

    public static <T extends Comparable<T>> void ordenar(T[]array){
        Arrays.sort(array, Comparator.nullsLast(T::compareTo));
    }
}
