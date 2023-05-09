package abbgui;

/**
 *
 * @author mathisa
 */
public class NodoB {

    int dato;
    NodoB Hizq;
    NodoB Hder;

    //Constructores
    NodoB(int Elem) {
        dato = Elem;
        NodoB Hizq;
        NodoB Hder = null;
    }

    public Boolean insertar(int Elem) {
        if (Elem < dato) {
            if (Hizq == null) {
                Hizq = new NodoB(Elem);
                return true;
            }
            
            return Hizq.insertar(Elem);
        } else if (Elem > dato) {
            if (Hder == null) {
                Hder = new NodoB(Elem);
                return true;
            }

            return Hder.insertar(Elem);
        }
        else{
            return false;
        }
    }
    
    // Borra recursivamente un nodo del árbol
    public Boolean delete(int data) {
        if (data < this.dato) {
            if (this.hasLeft()) {
                if (data == this.Hizq.dato) {
                    if (this.Hizq.isLeaf()) {
                        this.Hizq = null;
                    } else if (this.Hizq.hasLeft() && this.Hizq.hasRight()) {
                        this.Hizq.dato = this.Hizq.getAndRemovePredecessor().dato;
                    } else if (this.Hizq.hasLeft()) {
                        this.Hizq = this.Hizq.Hizq;
                    } else if (this.Hizq.hasRight()) {
                        this.Hizq = this.Hizq.Hder;
                    }
                    
                    return true;
                } else {
                    return this.Hizq.delete(data);
                }
            }
            
            return false;
        }
        
        if (data > this.dato) {
            if (this.hasRight()) {
                if (data == this.Hder.dato) {
                    if (this.Hder.isLeaf()) {
                        this.Hder = null;
                    } else if (this.Hder.hasLeft() && this.Hder.hasRight()) {
                        this.Hder.dato = this.Hder.getAndRemovePredecessor().dato;
                    } else if (this.Hder.hasLeft()) {
                        this.Hder = this.Hder.Hizq;
                    } else if (this.Hder.hasRight()) {
                        this.Hder = this.Hder.Hder;
                    }
                    
                    return true;
                } else {
                    return this.Hder.delete(data);
                }
            }
            
            return false;
        }
        
        return false;
    }
    
    // Si el nodo tiene hijo izquierdo
    public Boolean hasLeft() {
        return this.Hizq != null;
    }
    
    // Si el nodo tiene hijo derecho
    public Boolean hasRight() {
        return this.Hder != null;
    }
    
    // Si el nodo no tiene hijos
    public Boolean isLeaf() {
        return this.Hizq == null && this.Hder == null;
    }
    
    // Busca el predecesor del nodo, lo desconecta del árbol y lo retorna.
    public NodoB getAndRemovePredecessor() {
        if (!this.hasLeft()) {
            return null;
        }
        
        NodoB current = this.Hizq;
        
        if (current.hasRight()) {
            while (current.Hder.hasRight()) {
                current = current.Hder;
            }

            NodoB predecessor = current.Hder;
            current.Hder = null;

            return predecessor;
        }
        
        if (this.Hizq.isLeaf()) {
            this.Hizq = null;
        } else if (this.Hizq.hasLeft() && this.Hizq.hasRight()) {
            this.Hizq.dato = this.Hizq.getAndRemovePredecessor().dato;
        } else if (this.Hizq.hasLeft()) {
            this.Hizq = this.Hizq.Hizq;
        } else if (this.Hizq.hasRight()) {
            this.Hizq = this.Hizq.Hder;
        }
        
        return current;
    }
    
    // Busca el sucesor del nodo, lo desconecta del árbol y lo retorna.
    public NodoB getAndRemoveSuccessor() {
        if (!this.hasRight()) {
            return null;
        }
        
        NodoB current = this.Hder;
        
        if (current.hasLeft()) {
            while (current.Hizq.hasLeft()) {
                current = current.Hizq;
            }

            NodoB successor = current.Hizq;
            current.Hizq = null;

            return successor;
        }
        
        if (this.Hder.isLeaf()) {
            this.Hder = null;
        } else if (this.Hder.hasLeft() && this.Hder.hasRight()) {
            this.Hder.dato = this.Hder.getAndRemovePredecessor().dato;
        } else if (this.Hder.hasLeft()) {
            this.Hder = this.Hder.Hizq;
        } else if (this.Hder.hasRight()) {
            this.Hder = this.Hder.Hder;
        }
        
        return current;
    }
}
