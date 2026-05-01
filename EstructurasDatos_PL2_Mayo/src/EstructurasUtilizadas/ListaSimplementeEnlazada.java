package EstructurasUtilizadas;

public class ListaSimplementeEnlazada<T extends Comparable<T>> implements Lista<T>{
    protected ElementoSE<T> primero;

    @Override
    public void add(T dato){
        ElementoSE<T> elemento = new ElementoSE<>(dato);
        if (isEmpty()) primero = elemento;
        else{
            ElementoSE<T> aux = primero;
            while (aux.siguiente != null) aux = aux.siguiente;
            aux.siguiente = elemento;
        }
    }

    @Override
    public T get(T dato){
        ElementoSE<T> aux = primero;
        while (aux != null){
            if (aux.dato.compareTo(dato) == 0) return aux.dato;
            aux = aux.siguiente;
        }
        return null;
    }

    @Override
    public T del(T dato){
        ElementoSE<T> act = primero, ant = null;
        while (act != null){
            if (act.dato.compareTo(dato) == 0){
                if (ant == null) primero = act.siguiente;
                else ant.siguiente = act.siguiente;
                return act.dato;
            }
            ant = act;
            act = act.siguiente;
        }
        return null;
    }

    @Override
    public boolean isEmpty(){
        return primero == null;
    }

    @Override
    public int size(){
        int size = 0;
        if (!isEmpty()){
            size++;
            ElementoSE<T> aux = primero;
            while (aux.siguiente != null){
                size++;
                aux = aux.siguiente;
            }
        }
        return size;
    }

    @Override
    public MiIterador<T> getIterador(){
        return new IteradorLSE<>(this.primero);
    }

    @Override
    public T findElement(int pos){
        if (!isEmpty() && pos < size()){
            ElementoSE<T> aux = this.primero;
            for (int i = 0; i < pos; i++){
                aux = aux.siguiente;
            }
            return aux.dato;
        }
        return null;
    }

    @Override
    public int findPosition(T dato){
        if (!isEmpty()){
            ElementoSE<T> aux = this.primero;
            MiIterador<T> it = getIterador();
            int position = 0;
            do{
                if (aux.dato.compareTo(dato) == 0) return position;
                aux = aux.siguiente;
                position++;
                it.next();
            }while(it.hasNext());
        }
        return -1;
    }
}
