/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abbgui;

/**
 *
 * @author david
 */
public class SinglyLinkedListNode {
    public int data;
    public SinglyLinkedListNode next;
    
    public SinglyLinkedListNode(int data) {
        this.data = data;
        this.next = null;
    }
    
    public Boolean hasNext() {
        return this.next != null;
    }
}
