package EstructurasUtilizadas;

public class IteradorLSE<T> implements MiIterador<T>{
    private ElementoSE<T> actual;

    public IteradorLSE(ElementoSE<T> start){
        this.actual = start;
    }

    @Override
    public boolean hasNext(){
        return actual != null;
    }

    @Override
    public T next(){
        if (!hasNext()) return null;
        T dato = actual.dato;
        actual = actual.siguiente;
        return dato;
    }
}