/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abbgui;

import java.util.ArrayList;

/**
 *
 * @author david
 */
public class SinglyLinkedList {
    public SinglyLinkedListNode head;
    public SinglyLinkedListNode tail;
    public int n;
    
    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.n = 0;
    }
    
    public Boolean add(int data) {
        if (this.tail == null) {
            this.head = new SinglyLinkedListNode(data);
            this.tail = head;
            this.n = 1;
            
            return true;
        }
        
        this.tail.next = new SinglyLinkedListNode(data);
        this.tail = this.tail.next;
        this.n++;
        
        return true;
    }
    
    public ArrayList<SinglyLinkedListNode> traverse() {
        ArrayList<SinglyLinkedListNode> traversal = new ArrayList<>();
        this.traverse(this.head, traversal);
        
        return traversal;
    }
    
    public void traverse(SinglyLinkedListNode node, ArrayList<SinglyLinkedListNode> traversal) {
        if (node == null)
            return;
        
        traversal.add(node);
        this.traverse(node.next, traversal);
    }
}
