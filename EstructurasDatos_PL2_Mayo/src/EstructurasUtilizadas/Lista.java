package EstructurasUtilizadas;

public interface Lista<T extends Comparable<T>>{
    void add(T dato);
    T get(T dato);
    T del(T dato);
    boolean isEmpty();
    int size();
    MiIterador<T> getIterador();
    T findElement(int pos);
    int findPosition(T dato);
}
