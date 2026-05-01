import EstructurasUtilizadas.ListaSimplementeEnlazada;

public class Main{
    //Parte de Samuel Dorado Grande
    class NodoArbol<T> implements Comparable<NodoArbol<T>> {
        T dato;
        NodoArbol<T> izq;
        NodoArbol<T> der;
        NodoArbol<T> padre;
        int grado;
        int nivel;

        NodoArbol(T dato){
            this.dato = dato;
            izq = null;
            der = null;
            padre = null;
            grado = 0;
            nivel = 1;
        }

        @Override
        public int compareTo(NodoArbol<T> o) {
            return 0;
        }
    }

    public class ArbolBinarioDeBusqueda<T extends Comparable<T>>{
        private NodoArbol<T> raiz;
        private final ListaSimplementeEnlazada<NodoArbol<T>> listaNodos;

        public ArbolBinarioDeBusqueda(){
            listaNodos = new ListaSimplementeEnlazada<>();
        }

        private void add(T dato){
            NodoArbol<T> nuevo = new NodoArbol<>(dato);
            if (listaNodos.get(nuevo) != null){
                if (raiz == null) raiz = nuevo;
                else{
                    NodoArbol<T> aux = raiz;
                    boolean added = false;
                    while (!added){
                        if (dato.compareTo(aux.dato) < 0){
                            if (aux.izq == null){
                                aux.izq = nuevo;
                                listaNodos.add(nuevo);
                                aux.grado++;
                                added = true;
                            }
                            else aux = aux.izq; nuevo.nivel++;
                        }
                        else if (dato.compareTo(aux.dato) > 0){
                            if (aux.der == null){
                                aux.der = nuevo;
                                listaNodos.add(nuevo);
                                aux.grado++;
                                added = true;
                            }
                            else aux = aux.der; nuevo.nivel++;
                        }
                    }
                }
            }
        }

        public ArbolBinarioDeBusqueda<T> getSubArbolIzquierdo(){
            ArbolBinarioDeBusqueda<T> arbolIzq = new ArbolBinarioDeBusqueda<>();
            NodoArbol<T> raizIzq = raiz.izq;
            arbolIzq.addRecursivoDatos(raizIzq);
            return arbolIzq;
        }

        public ArbolBinarioDeBusqueda<T> getSubArbolDerecho(){
            ArbolBinarioDeBusqueda<T> arbolDer = new ArbolBinarioDeBusqueda<>();
            NodoArbol<T> raizDer = raiz.der;
            arbolDer.addRecursivoDatos(raizDer);
            return arbolDer;
        }

        private void addRecursivoDatos(NodoArbol<T> nodo){
            add(nodo.dato);
            if (nodo.izq != null) addRecursivoDatos(nodo.izq);
            if (nodo.der != null) addRecursivoDatos(nodo.der);
        }

        public int getAltura(){
            NodoArbol<T> aux = raiz;
            if(raiz == null){
                int alturaArbol = 1;
                for (int i = 0; i < listaNodos.size(); i++){
                    int alturaNueva = listaNodos.findElement(i).nivel;
                    if (alturaNueva > alturaArbol) alturaArbol = alturaNueva;
                }
                return alturaArbol;
            }
            return 0;
        }

        public ListaSimplementeEnlazada<T> getListaDatosNivel(int nivel){
            ListaSimplementeEnlazada<T> listaDatosNivel = new ListaSimplementeEnlazada<>();
            for (int i = 0; i < listaNodos.size(); i++){
                if (listaNodos.findElement(i).nivel == nivel) listaDatosNivel.add(listaNodos.findElement(i).dato);
            }
            return listaDatosNivel;
        }

        public boolean isArbolHomogeneo(){
            if (raiz.grado == 1) return false;
            else{
                if (raiz.grado == 0) return true;
                else return getSubArbolIzquierdo().isArbolHomogeneo() && getSubArbolDerecho().isArbolHomogeneo();
            }
        }

        public boolean isArbolCompleto(){
            ListaSimplementeEnlazada<NodoArbol<T>> listaNodosHoja = getListaNodosHoja();
            boolean arbolCompleto = true;
            int nivelActual = listaNodosHoja.findElement(0).nivel;
            for (int i = 1; i < listaNodosHoja.size(); i++){
                if (listaNodosHoja.findElement(i).nivel != nivelActual){
                    arbolCompleto = false;
                    break;
                }
            }
            return arbolCompleto;
        }

        public boolean isArbolCasiCompleto(){
            ListaSimplementeEnlazada<NodoArbol<T>> listaNodosHoja = getListaNodosHoja();
            boolean arbolCasiCompleto = true;
            int nivelActual = listaNodosHoja.findElement(0).nivel;
            int cambioNivel = 0;
            for (int i = 1; i < listaNodosHoja.size(); i++){
                int nivelAnalizado = listaNodosHoja.findElement(i).nivel;
                if (nivelAnalizado != nivelActual){
                    cambioNivel++;
                    nivelActual = nivelAnalizado;
                }
                if (cambioNivel == 2){
                    arbolCasiCompleto = false;
                    break;
                }
            }
            if (cambioNivel == 0) return false;
            return arbolCasiCompleto;
        }

        public ListaSimplementeEnlazada<NodoArbol<T>> getListaNodosHoja(){
            ListaSimplementeEnlazada<NodoArbol<T>> listaNodosHoja = new ListaSimplementeEnlazada<>();
            addRecursivoListaNodosHoja(raiz, listaNodosHoja);
            return listaNodosHoja;
        }

        private void addRecursivoListaNodosHoja(NodoArbol<T> nodo, ListaSimplementeEnlazada<NodoArbol<T>> listaNodosHoja){
            if (raiz.izq == null && raiz.der == null) listaNodosHoja.add(raiz);
            else{
                if (raiz.izq != null) addRecursivoListaNodosHoja(raiz.izq, listaNodosHoja);
                if (raiz.der != null) addRecursivoListaNodosHoja(raiz.der, listaNodosHoja);
            }
        }

        public ListaSimplementeEnlazada<NodoArbol<T>> getCamino(T dato){
            ListaSimplementeEnlazada<NodoArbol<T>> camino = new ListaSimplementeEnlazada<>();
            camino.add(raiz);
            NodoArbol<T> aux = raiz;
            while (aux.dato != dato){
                if (dato.compareTo(aux.dato) < 0){
                    aux = aux.izq;
                    camino.add(aux);
                }
                else if (dato.compareTo(aux.dato) > 0){
                    aux = aux.der;
                    camino.add(aux);
                }
            }
            return camino;
        }

        public ListaSimplementeEnlazada<T> getListaPreOrden(){
            ListaSimplementeEnlazada<T> listaPreOrden = new ListaSimplementeEnlazada<>();
            addRecursivoPreOrden(listaPreOrden, raiz);
            return listaPreOrden;
        }

        public ListaSimplementeEnlazada<T> getListaOrdenCentral(){
            ListaSimplementeEnlazada<T> listaOrdenCentral = new ListaSimplementeEnlazada<>();
            addRecursivoOrdenCentral(listaOrdenCentral, raiz);
            return listaOrdenCentral;
        }

        public ListaSimplementeEnlazada<T> getListaPostOrden(){
            ListaSimplementeEnlazada<T> listaPostOrden = new ListaSimplementeEnlazada<>();
            addRecursivoPostOrden(listaPostOrden, raiz);
            return listaPostOrden;
        }

        private void addRecursivoPreOrden(ListaSimplementeEnlazada<T> listaPreOrden, NodoArbol<T> nodo){
            listaPreOrden.add(nodo.dato);
            if (nodo.izq != null) addRecursivoPreOrden(listaPreOrden, nodo.izq);
            if (nodo.der != null) addRecursivoPreOrden(listaPreOrden, nodo.der);
        }

        private void addRecursivoOrdenCentral(ListaSimplementeEnlazada<T> listaOrdenCentral, NodoArbol<T> nodo){
            if (nodo.izq != null) addRecursivoPreOrden(listaOrdenCentral, nodo.izq);
            listaOrdenCentral.add(nodo.dato);
            if (nodo.der != null) addRecursivoPreOrden(listaOrdenCentral, nodo.der);
        }

        private void addRecursivoPostOrden(ListaSimplementeEnlazada<T> listaPostOrden, NodoArbol<T> nodo){
            if (nodo.izq != null) addRecursivoPreOrden(listaPostOrden, nodo.izq);
            if (nodo.der != null) addRecursivoPreOrden(listaPostOrden, nodo.der);
            listaPostOrden.add(nodo.dato);
        }
    }

    //Parte de Eduardo Nicolás Minea Patrascu

    //Parte de Pablo Cayetano Herrero Martín

}
