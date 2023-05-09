/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abbgui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashMap;

/**
 *
 * @author david
 */

// Clase que ayuda a representar gráficamente el árbol
public class TreeDrawer {
    
    // El árbol a representar
    public ABin tree;
    
    // Las posiciones de cada nodo
    public HashMap<NodoB, NodePositionData> positions;
    
    // El nodo que está siendo visitado actualmente en un recorrido si corresponde.
    private NodoB selected;
    
    // El nodo que está siendo visitado actualmente en un recorrido si corresponde.
    private NodoB deleted;
    
    // Los límites del espacio que abarca el árbol
    public Range<NodePositionData> bounds;
    
    // El objeto graphics sobre el cual se trabajará
    public Graphics2D g;
    
    // Las dimenciones del panel sobre el cual se dibujará el arbol en pixeles
    int WIDTH = 880;
    int HEIGHT = 420;
    
    // Radio del nodo
    int NODE_RADIUS = 15;
    
    // espacio horizontal y vertical entre los nodos 
    int NODE_SEPARATION = 50;
    
    // Posición de la raíz
    int ROOT_X = this.WIDTH / 2;
    int ROOT_Y = 20;
    int LINE_WIDTH = 4;
    int FONT_SIZE = 13;
    
    public TreeDrawer(ABin tree, Graphics2D g) {
        this.positions = new HashMap<>();
        this.bounds = new Range(new NodePositionData(0, 0), new NodePositionData(0, 0));
        this.tree = tree;
        this.selected = null;
        this.g = g;
    }
    
    // Visita un nuevo nodo y lo pinta de color verde;
    public void setSelected(NodoB selected) {
        if (this.selected != null) {
            this.changeNodeColor(this.selected, Color.RED);
        }
        
        this.selected = selected;
        this.changeNodeColor(selected, Color.GREEN);
    }
    
    // Visita un nuevo nodo y lo pinta de color verde;
    public void setDeleted(NodoB deleted) {
        this.deleted = deleted;
        this.changeNodeColor(deleted, Color.ORANGE);
    }
    
    // Procesa el árbol para posteriomente ser dibujado
    public void revalidateTree() {
        this.bounds.max = new NodePositionData(0, 0);
        this.bounds.min = new NodePositionData(0, 0);
        
        this.positions.clear();
        this.setInitialPositions();
        this.fixNodeCollisions();
        
        if (this.bounds.max.y > 7) {
            this.NODE_RADIUS = 10;
            this.NODE_SEPARATION = 30;
            this.LINE_WIDTH = 2;
            this.FONT_SIZE = 7;
        } else {
            this.NODE_RADIUS = 15;
            this.NODE_SEPARATION = 50;
            this.LINE_WIDTH = 4;
            this.FONT_SIZE = 13;
        }
        
        this.centerTree();
    }
    
    // Dibuja el árbol
    public void draw() {
        this.revalidateTree();
        this.g.clearRect(0, 0, this.WIDTH, this.HEIGHT);
        drawNode(this.tree.Raiz);
    }
    
    // Dibuja los nodos recursivamente en preorden
    private void drawNode(NodoB node) {
        if (node == null)
            return;
        
        NodePositionData pos = this.positions.get(node);
        
        int x = Math.round(this.ROOT_X + pos.x * this.NODE_SEPARATION);
        int y = Math.round(this.ROOT_Y + pos.y * this.NODE_SEPARATION);
        int diameter = 2 * this.NODE_RADIUS;
                
        this.g.setStroke(new BasicStroke(this.LINE_WIDTH));
        this.g.setFont(new Font("Tahoma", Font.BOLD, this.FONT_SIZE));
        
        this.g.setColor(Color.BLACK);
        
        if (node.hasLeft()) {
            NodePositionData childPos = this.positions.get(node.Hizq);
            int childX = Math.round(this.ROOT_X + childPos.x * this.NODE_SEPARATION);
            int childY = y + this.NODE_SEPARATION;
            
            this.g.drawLine(x + this.NODE_RADIUS, y + this.NODE_RADIUS, childX + this.NODE_RADIUS, childY + this.NODE_RADIUS);
        }
            
        if (node.hasRight()) {
            NodePositionData childPos = this.positions.get(node.Hder);
            int childX = Math.round(this.ROOT_X + childPos.x * this.NODE_SEPARATION);
            int childY = y + this.NODE_SEPARATION;
            
            this.g.drawLine(x + this.NODE_RADIUS, y + this.NODE_RADIUS, childX + this.NODE_RADIUS, childY + this.NODE_RADIUS);
        }
        
        this.g.setColor(Color.RED);
        this.g.fillOval(x, y, diameter, diameter);
        
        this.g.setColor(Color.BLACK);
        this.g.drawOval(x, y, diameter, diameter);
        this.g.drawString(Integer.toString(node.dato), x + this.NODE_RADIUS - g.getFontMetrics().stringWidth(Integer.toString(node.dato)) / 2, y + this.NODE_RADIUS + g.getFontMetrics().getHeight() / 4);
        
        this.drawNode(node.Hizq);
        this.drawNode(node.Hder);
    }
    
    // Dibuja un nodo de un determinado color
    private void changeNodeColor(NodoB node, Color color) {
        if (node == null)
            return;
        
        NodePositionData pos = this.positions.get(node);
        
        int x = Math.round(this.ROOT_X + pos.x * this.NODE_SEPARATION);
        int y = Math.round(this.ROOT_Y + pos.y * this.NODE_SEPARATION);
        int diameter = 2 * this.NODE_RADIUS;
                
        this.g.setStroke(new BasicStroke(this.LINE_WIDTH));
        this.g.setFont(new Font("Tahoma", Font.BOLD, this.FONT_SIZE));
        
        this.g.setColor(Color.BLACK);
        this.g.drawOval(x, y, diameter, diameter);
        
        this.g.setColor(color);
        this.g.fillOval(x, y, diameter, diameter);
        
        this.g.setColor(Color.BLACK);
        this.g.drawOval(x, y, diameter, diameter);
        this.g.drawString(Integer.toString(node.dato), x + this.NODE_RADIUS - g.getFontMetrics().stringWidth(Integer.toString(node.dato)) / 2, y + this.NODE_RADIUS + g.getFontMetrics().getHeight() / 4);
    }
    
    private void setInitialPositions() {
        this.setInitialPositions(this.tree.Raiz, 0, 0);
    }
    
    // Posiciona los nodos inicialmente basado en la posición de la raíz
    private void setInitialPositions(NodoB node, float xPos, int depth) {
        if (node == null)
            return;
        
        this.positions.put(node, new NodePositionData(xPos, depth));
        
        this.setInitialPositions(node.Hizq, xPos - 0.5f, depth + 1);
        this.setInitialPositions(node.Hder, xPos + 0.5f, depth + 1);
    }
    
    private void fixNodeCollisions() {
        this.fixNodeCollisions(this.tree.Raiz, new HashMap<>());
    }
    
    // Mueve los nodos para que no existan nodos que se posicionen sobre otros nodos y que los nodos estén separados. Al final centra los nodos sobre sus hijos
    private void fixNodeCollisions(NodoB node, HashMap<Integer, Float> levelMaxX) {
        if (node == null)
            return;
        
        NodePositionData position = this.positions.get(node);
        
        if (levelMaxX.containsKey((int) position.y)) {
            float leftX = levelMaxX.get((int) position.y);
            
            if (position.x < leftX + 1) {
                float shift = leftX - position.x + 1;
                this.shiftX(node, shift);
            }
        }
        
        this.fixNodeCollisions(node.Hizq, levelMaxX);
        this.fixNodeCollisions(node.Hder, levelMaxX);
        
        if (node.hasLeft() && node.hasRight()) {
            float leftChildX = this.positions.get(node.Hizq).x;
            float rightChildX = this.positions.get(node.Hder).x;
            position.x = leftChildX + (rightChildX - leftChildX) / 2;
        } else if (node.hasLeft()) {
            position.x = this.positions.get(node.Hizq).x + 0.5f;
        } else if (node.hasRight()) {
            position.x = this.positions.get(node.Hder).x - 0.5f;
        }
        
        levelMaxX.put((int) position.y, position.x);
        
        if (position.x > this.bounds.max.x) {
            this.bounds.max.x = position.x;
        } 
        
        if (position.x < this.bounds.min.x) {
            this.bounds.min.x = position.x;
        }
        
        if (position.y > this.bounds.max.y) {
            this.bounds.max.y = position.y;
        } 
        
        if (position.y < this.bounds.min.y) {
            this.bounds.min.y = position.y;
        }
    }
    
    // Desplaza todos los nodos en el árbol para que este quede centrado
    private void centerTree() {
        float shift = (this.bounds.max.x + this.bounds.min.x) / 2;
        
        if (shift > 0) {
            this.shiftX(this.tree.Raiz, -shift);
            this.bounds.max.x -= shift;
            this.bounds.min.x -= shift;
        }
    }
    
    // Desplaza un nodo y todos sus hijos
    private void shiftX(NodoB node, float shift) {
        if (node == null || shift == 0)
            return;
        
        this.positions.get(node).x += shift;
        
        this.shiftX(node.Hizq, shift);
        this.shiftX(node.Hder, shift);
    }
}
