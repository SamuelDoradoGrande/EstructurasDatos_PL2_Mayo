import EstructurasUtilizadas.LSEOrdenada;
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

    public class Vertice<T> implements Comparable<Vertice<T>> {
        T data;
        ListaSimplementeEnlazada<Arista<T>> aristasSimples;
        ListaSimplementeEnlazada<Arista<T>> aristasEntrada;
        ListaSimplementeEnlazada<Arista<T>> aristasSalida;
        int grado;

        public Vertice(T data) {
            this.data = data;
            this.aristasEntrada = new ListaSimplementeEnlazada<>();
            this.aristasSalida = new ListaSimplementeEnlazada<>();
            grado = 0;
        }

        public void addArista(Arista<T> arista){
            if (arista.origen == this || arista.destino == this){
                this.aristasSimples.add(arista);
                grado++;
            }
            else System.out.println("No se pudo añadir la arista; ninguno de los extremos de la arista corresponden con este vértice");
        }

        public void addAristaSalida(Arista<T> arista){
            if (arista.origen == this){
                this.aristasSalida.add(arista);
                grado++;
            }
            else System.out.println("No se pudo añadir la arista; el vértice de origen de la arista no corresponde con este vértice");
        }

        public void addAristaEntrada(Arista<T> arista){
            if (arista.destino == this){
                this.aristasEntrada.add(arista);
                grado++;
            }
            else System.out.println("No se pudo añadir la arista; el vértice de destino de la arista no corresponde con este vértice");
        }

        public int getGrado(){
            return grado;
        }

        @Override
        public int compareTo(Vertice<T> o) {
            return 0;
        }
    }

    public class Arista<T> implements Comparable<Arista<T>> {
        Vertice<T> origen;
        Vertice<T> destino;
        double coste;

        public Arista(Vertice<T> origen, Vertice<T> destino, double coste) {
            this.origen = origen;
            this.destino = destino;
            this.coste = coste;
        }

        @Override
        public int compareTo(Arista<T> o) {
            return 0;
        }
    }

    public class Grafo<T extends Comparable<T>>{
        ListaSimplementeEnlazada<Vertice<T>> vertices;
        ListaSimplementeEnlazada<Arista<T>> aristas;

        public Grafo(){
            this.vertices = new ListaSimplementeEnlazada<>();
            this.aristas = new ListaSimplementeEnlazada<>();
        }

        public void addVertice(Vertice<T> vertice) {
            this.vertices.add(vertice);
        }

        public void addArista(Arista<T> arista) {
            this.aristas.add(arista);
            arista.origen.addAristaSalida(arista);
            arista.destino.addAristaEntrada(arista);
        }

        public boolean isGrafoSimple(){
            for (int i = 0; i < aristas.size(); i++){
                Arista<T> aristaI = aristas.findElement(i);
                if (aristaI.origen == aristaI.destino) return false;
                if (i++ < aristas.size()){
                    for (int j = i + 1; j < aristas.size(); j++){
                        Arista<T> aristaII = aristas.findElement(j);
                        if ((aristaII.origen == aristaI.origen && aristaII.destino == aristaI.destino) || (aristaII.destino == aristaI.origen && aristaII.origen == aristaI.destino)) return false;
                    }
                }
            }
            return true;
        }

        public boolean isGrafoDirigido(){
            for (int i = 0; i < vertices.size(); i++){
                Vertice<T> vertice = vertices.findElement(i);
                if (!vertice.aristasEntrada.isEmpty() || !vertice.aristasSalida.isEmpty()) return true;
            }
            return false;
        }

        public boolean isCompleto(){
            for (int i = 0; i < vertices.size(); i++){
                Vertice<T> verticeI = vertices.findElement(i);
                if (i++ < vertices.size()){
                    for (int j = i + 1; j < vertices.size(); j++){
                        Vertice<T> verticeII = vertices.findElement(i);
                        Arista<T> aristaAuxI = new Arista<>(verticeI, verticeII, 1);
                        Arista<T> aristaAuxII = new Arista<>(verticeII, verticeI, 1);
                        if(aristas.get(aristaAuxI) == null && aristas.get(aristaAuxII) == null) return false;
                    }
                }
            }
            return true;
        }

        public boolean isConexo(){
            Vertice<T> vertice = vertices.findElement(0);
            return MSTPrim(vertice).vertices.size() == vertices.size();
        }

        public boolean isArbol(){
            return isConexo() && aristas.size() == vertices.size() - 1;
        }

        public boolean isEuleriano(){
            if (isConexo()){
                for (int i = 0; i < vertices.size(); i++){
                    if (vertices.findElement(i).grado % 2 != 0) return false;
                }
                return true;
            }
            return false;
        }

        public Grafo<T> MSTPrim(Vertice<T> nodoInicio){
            Grafo<T> MST = new Grafo<>();
            MST.addVertice(nodoInicio);
            ListaSimplementeEnlazada<Vertice<T>> visitados = new ListaSimplementeEnlazada<>();
            LSEOrdenada<Arista<T>> heap = new LSEOrdenada<>();
            visitados.add(nodoInicio);
            addHeap(nodoInicio, heap, visitados);
            while (!heap.isEmpty()){
                Arista<T> aristaMenorPeso = heap.del(heap.findElement(0));
                Vertice<T> nuevo = aristaMenorPeso.destino;
                if (visitados.get(nuevo) == null){
                    MST.addVertice(nuevo);
                    MST.addArista(aristaMenorPeso);
                    addHeap(nuevo, heap, visitados);
                }
            }
            return MST;
        }

        private void addHeap(Vertice<T> nodo, LSEOrdenada<Arista<T>> heap, ListaSimplementeEnlazada<Vertice<T>> visitados){
            for (int i = 0; i < nodo.aristasSimples.size(); i++){
                Arista<T> nuevo = nodo.aristasSimples.findElement(i);
                if (heap.get(nuevo) == null){
                    if (visitados.get(nuevo.destino) == null) heap.add(nuevo);
                    else if (visitados.get(nuevo.origen) == null) heap.add(nuevo);
                }
            }
            for (int i = 0; i < nodo.aristasSalida.size(); i++){
                Arista<T> nuevo = nodo.aristasSalida.findElement(i);
                if (heap.get(nuevo) == null && visitados.get(nuevo.destino) == null) heap.add(nuevo);
            }
        }
    }


    //Parte de Eduardo Nicolás Minea Patrascu

}
