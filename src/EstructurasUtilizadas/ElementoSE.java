package EstructurasUtilizadas;

class ElementoSE<T>{
    T dato;
    ElementoSE<T> siguiente;

    ElementoSE(T dato){
        this.dato = dato;
        this.siguiente = null;
    }
}