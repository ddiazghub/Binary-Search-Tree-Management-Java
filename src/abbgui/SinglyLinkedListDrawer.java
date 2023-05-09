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
public class SinglyLinkedListDrawer {
    public SinglyLinkedList list;
    public HashMap<SinglyLinkedListNode, Integer> positions;
    public Graphics2D g;
    public int maxX;
    
    int WIDTH = 430;
    int HEIGHT = 50;
    int NODE_RADIUS = 15;
    int NODE_SEPARATION = 50;
    int HEAD_X = 25;
    int HEAD_Y = this.HEIGHT / 2;
    int LINE_WIDTH = 4;
    int FONT_SIZE = 13;
    
    public SinglyLinkedListDrawer(SinglyLinkedList list, Graphics2D g) {
        this.positions = new HashMap<>();
        this.list = list;
        this.g = g;
    }
    
    public void draw() {
        this.maxX = 0;
        this.positions.clear();
        this.setNodePositions(this.list.head, 0);
        
        if (this.maxX > 8) {
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
        
        this.drawNode(this.list.head);
        
    }
    
    public void setNodePositions(SinglyLinkedListNode node, int i) {
        if (node == null)
            return;
        
        if (i > this.maxX)
            this.maxX = i;
        
        this.positions.put(node, i);
        this.setNodePositions(node.next, i + 1);
    }
    
    // Dibuja los nodos recursivamente en preorden
    private void drawNode(SinglyLinkedListNode node) {
        if (node == null)
            return;
        
        int x = this.positions.get(node) * this.NODE_SEPARATION;
        int y = this.HEAD_Y - this.NODE_RADIUS;
        int diameter = 2 * this.NODE_RADIUS;
                
        this.g.setStroke(new BasicStroke(this.LINE_WIDTH));
        this.g.setFont(new Font("Tahoma", Font.BOLD, this.FONT_SIZE));
        
        this.g.setColor(Color.BLACK);
        
        if (node.hasNext()) {
            int nextPos = this.positions.get(node.next);
            int nextX = Math.round(x + this.NODE_SEPARATION);
            
            this.g.drawLine(x + this.NODE_RADIUS, y + this.NODE_RADIUS, nextX + this.NODE_RADIUS, y + this.NODE_RADIUS);
        }
        
        this.g.setColor(Color.RED);
        this.g.fillOval(x, y, diameter, diameter);
        
        this.g.setColor(Color.BLACK);
        this.g.drawOval(x, y, diameter, diameter);
        this.g.drawString(Integer.toString(node.data), x + this.NODE_RADIUS - g.getFontMetrics().stringWidth(Integer.toString(node.data)) / 2, y + this.NODE_RADIUS + g.getFontMetrics().getHeight() / 4);
        
        this.drawNode(node.next);
    }
}
