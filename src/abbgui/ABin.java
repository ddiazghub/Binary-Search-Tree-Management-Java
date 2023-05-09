package abbgui;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author mathisa
 */
public class ABin {
    NodoB Raiz;

    //Constructor
    public ABin() {
        Raiz = null;
    }

    //Insercion de un elemento en el arbol
    public Boolean insertaNodo(int Elem) {
        if (Raiz == null) {
            Raiz = new NodoB(Elem);
            return true;
        }
        
        return Raiz.insertar(Elem);
    }
    
    // Borra un nodo del árbol
    public Boolean deleteNode(int data) {
        if (this.Raiz == null) {
            return false;
        }
        
        if (this.Raiz.dato == data) {
            if (this.Raiz.isLeaf()) {
                this.Raiz = null;
            } else if (this.Raiz.hasLeft() && this.Raiz.hasRight()) {
                this.Raiz.dato = this.Raiz.getAndRemovePredecessor().dato;
            } else if (this.Raiz.hasLeft()) {
                this.Raiz = this.Raiz.Hizq;
            } else if (this.Raiz.hasRight()) {
                this.Raiz = this.Raiz.Hder;
            }
            
            return true;
        }
        
        return Raiz.delete(data);
    }

    // Si el árbol está vacío
    public Boolean isEmpty() {
        return this.Raiz == null;
    }
    
    //Escribir arbol
    public void escribir(NodoB Nodo) {
        if (Nodo == null) {
            return;
        } else {
            System.out.print(Nodo.dato + " ");
            escribir(Nodo.Hizq);
            escribir(Nodo.Hder);
        }
    }
    
    // Realiza un recorrido a través del árbol con el algoritmo que se suministre.
    public ArrayList<NodoB> traverse(TraversalAlgorithm algorithm) {
        switch (algorithm) {
            case PREORDER:
                return this.preorden();
                
            case INORDER:
                return this.inorden();
                
            case POSTORDER:
                return this.postOrden();
                
            case LEVELORDER:
                return this.levelOrder();
            
            default:
                return null;
        }
    }

    public ArrayList<NodoB> preorden() {
        ArrayList<NodoB> traversal = new ArrayList<>();
        this.preorden(this.Raiz, traversal);
        
        return traversal;
    }
    
    //Preorden Recursivo del arbol
    private void preorden(NodoB Nodo, ArrayList<NodoB> traversal) {
        if (Nodo == null)
            return;
            
        traversal.add(Nodo);
        preorden(Nodo.Hizq, traversal);
        preorden(Nodo.Hder, traversal);
    }

    //PostOrden recursivo del arbol
    public ArrayList<NodoB> postOrden() {
        ArrayList<NodoB> traversal = new ArrayList<>();
        this.postOrden(this.Raiz, traversal);
        
        return traversal;
    }
    
    private void postOrden(NodoB Nodo, ArrayList<NodoB> traversal) {
        if (Nodo == null)
            return;
            
        postOrden(Nodo.Hizq, traversal);
        postOrden(Nodo.Hder, traversal);
        traversal.add(Nodo);
    }

    public ArrayList<NodoB> inorden() {
        ArrayList<NodoB> traversal = new ArrayList<>();
        this.inorden(this.Raiz, traversal);
        
        return traversal;
    }
    
    //Inorden Recursivo del arbol
    private void inorden(NodoB Nodo, ArrayList<NodoB> traversal) {
        if (Nodo == null)
            return;
        
        inorden(Nodo.Hizq, traversal);
        traversal.add(Nodo);
        inorden(Nodo.Hder, traversal);
    }

    // Recorrido del árbol por niveles.
    public ArrayList<NodoB> levelOrder() {
        ArrayList<NodoB> traversal = new ArrayList<>();
        Queue<NodoB> queue = new LinkedList<>();
        
        if (!this.isEmpty()) {
            queue.add(this.Raiz);
            
            while (!queue.isEmpty()) {
                NodoB node = queue.remove();
                traversal.add(node);
                
                if (node.hasLeft())
                    queue.add(node.Hizq);
                
                if (node.hasRight())
                    queue.add(node.Hder);
            }
        }
        
        return traversal;
    }
    
    // Recorrido del árbol por niveles.
    public SinglyLinkedList getLevel(int level) {
        SinglyLinkedList nodes = new SinglyLinkedList();
        this.getLevel(this.Raiz, level, 0, nodes);
        
        return nodes;
    }
    
    private void getLevel(NodoB node, int level, int i, SinglyLinkedList nodes) {
        if (node == null)
            return;
        
        if (i < level) {
            this.getLevel(node.Hizq, level, i + 1, nodes);
            this.getLevel(node.Hder, level, i + 1, nodes);
        } else {
            nodes.add(node.dato);
        }
    }
    
//cantidad de niveles que posee el arbol
    public int altura(NodoB Nodo) {
        if (Nodo == null) {
            return -1;
        } else {
            return 1 + Math.max(altura(Nodo.Hizq), altura(Nodo.Hder));
        }
    }
//cantidad de nodos que posee el arbol	

    public int tamaño(NodoB Nodo) {
        if (Nodo == null) {
            return 0;
        } else {
            return 1 + tamaño(Nodo.Hizq) + tamaño(Nodo.Hder);
        }
    }

    //cantidad de nodos hojas que posee el arbol	
    public int hojas(NodoB Nodo) {
        if (Nodo == null) {
            return 0;
        } else if (Nodo.Hizq == null && Nodo.Hder == null) {
            return 1;
        } else {
            return hojas(Nodo.Hizq) + hojas(Nodo.Hder);
        }
    }

    //cantidad de nodos completos que posee el arbol	
    public int completos(NodoB Nodo) {
        int cont = 0;
        if (Nodo != null) {
            cont = completos(Nodo.Hizq) + completos(Nodo.Hder);
            if (Nodo.Hizq != null && Nodo.Hder != null) {
                cont = cont + 1;
            }
        }
        return cont;
    }

//Busca un elemento en el arbol
    public void buscar(int Elem, NodoB A) {
        if (A == null) {
            System.out.println("Nodo no encontrado ");
        } else if (A.dato == Elem) {
            System.out.println("Nodo encontrado ");
        } else if (Elem > A.dato) {
            buscar(Elem, A.Hder);
        } else {
            buscar(Elem, A.Hizq);
        }
    }
}
