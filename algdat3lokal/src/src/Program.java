/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.Comparator;

/**
 *
 * @author Gud
 */
public class Program {
    
    public static void main(String[] args){

  Integer[] a = {4,7,2,9,4,10,8,7,4,6};
  ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
  for (int verdi : a) tre.leggInn(verdi);

  System.out.println(tre.antall());      // Utskrift: 10
  System.out.println(tre.antall(5));     // Utskrift: 0
  System.out.println(tre.antall(4));     // Utskrift: 3
  System.out.println(tre.antall(7));     // Utskrift: 2
  System.out.println(tre.antall(10));    // Utskrift: 1
    
    
    }
    
}
