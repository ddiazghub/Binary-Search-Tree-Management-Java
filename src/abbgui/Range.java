/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abbgui;

/**
 *
 * @author david
 */
// Clase que representa un rango de valores comprendidos entre un límite máximo y uno mínimo. Se utiliza para representar el espacio que abarcará el árbol.
public class Range<T> {
    public T min;
    public T max;

    public Range(T min, T max) {
        this.min = min;
        this.max = max;
    }
}
