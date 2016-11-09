package src;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gud
 */
import java.util.*;
//ksdasdfsdf
public class ObligSBinTre<T> implements Beholder<T>
{
  private static final class Node<T>   // en indre nodeklasse
  {
    private T verdi;                   // nodens verdi
    private Node<T> venstre, høyre;    // venstre og høyre barn
    private Node<T> forelder;          // forelder

    // konstruktør
    private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder)
    {
      this.verdi = verdi;
      venstre = v; høyre = h;
      this.forelder = forelder;
    }

    private Node(T verdi, Node<T> forelder)  // konstruktør
    {
      this(verdi, null, null, forelder);
    }

    @Override
    public String toString(){ return "" + verdi;}

  } // class Node

  private Node<T> rot;                            // peker til rotnoden
  private int antall;                             // antall noder
  private int endringer;                          // antall endringer

  private final Comparator<? super T> comp;       // komparator

  public ObligSBinTre(Comparator<? super T> c)    // konstruktør
  {
    rot = null;
    antall = 0;
    comp = c;
  }
  
  @Override
  public boolean leggInn(T verdi)
   {
    Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

    Node<T> p = rot, q = null;               // p starter i roten
    int cmp = 0;                             // hjelpevariabel

    while (p != null)       // fortsetter til p er ute av treet
    {
      q = p;                                 // q er forelder til p
      cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
      p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
    }

    // p er nå null, dvs. ute av treet, q er den siste vi passerte

    p = new Node<>(verdi, q);                   // oppretter en ny node

    if (q == null) rot = p;                  // p blir rotnode
    else if (cmp < 0) q.venstre = p;         // venstre barn til q
    else q.høyre = p;                        // høyre barn til q

    antall++;                                // én verdi mer i treet
    return true;                             // vellykket innlegging
  }
  
  @Override
  public boolean inneholder(T verdi)
  {
    if (verdi == null) return false;

    Node<T> p = rot;

    while (p != null)
    {
      int cmp = comp.compare(verdi, p.verdi);
      if (cmp < 0) p = p.venstre;
      else if (cmp > 0) p = p.høyre;
      else return true;
    }

    return false;
  }
  
  @Override
  public boolean fjern(T verdi)
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public int fjernAlle(T verdi)
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  @Override
  public int antall()
  {
    return antall;
  }
 
  public int antall(T verdi){
      
    if (!inneholder(verdi)){
        return 0;
    }
    Node<T> p = rot;
    int teller =0;
                               // hjelpevariabe
    while (p != null){
        int cmp = comp.compare(verdi,p.verdi);
       
        if (cmp<0) p=p.venstre;
    
        else{
            if (cmp ==0){
            teller++;}
            p=p.høyre;
                
            }     
    }
    return teller;
  }
  
  @Override
  public boolean tom()
  {
    return antall == 0;
  }
  
  @Override
  public void nullstill()
  {
      rot = null;
    
  }
  
  private static <T> Node<T> nesteInorden(Node<T> p)
  {
    if(p.høyre != null)  // p har h¯yre barn
    {
      p=p.høyre;
      while (p.venstre != null) p = p.venstre; //f¯rste inorden
      return p;
    }
    else if(p.forelder != null && p.forelder.venstre == p){
            return p.forelder;
            }
    else  // mÂ gÂ oppover i treet
    {
      while (p.forelder != null && p.forelder.høyre == p){
        p = p.forelder;
      }
      return p.forelder;
    }
  }
  
  @Override
  public String toString()
  {

    StringBuilder s = new StringBuilder();
    s.append("[");
    if (!tom()) {
        Node<T> p = rot;
        while(p.venstre != null){
            p = p.venstre;
        }
        s.append(p.verdi);
        while (nesteInorden(p) != null) {
            s.append(", ");
            s.append(nesteInorden(p).verdi);
            p = nesteInorden(p);
        }
    }
    s.append("]");
    return s.toString();
  }
  
  public String omvendtString()
  {
    ArrayDeque<Node<T>> stakk = new ArrayDeque<>();
    
    if (!tom()) {
        Node<T> p = rot;
        while(p.venstre != null){
            p = p.venstre;
        }
        stakk.push(p);
        while (nesteInorden(p) != null) {
            stakk.push(nesteInorden(p));
            p = nesteInorden(p);
        }
    }else{
        return "[]";
    }
    StringBuilder s = new StringBuilder();
    s.append("[");
    
    while(!stakk.isEmpty()){
        
        s.append(stakk.poll().verdi);
        s.append(", ");
    }
    
    s.deleteCharAt(s.length()-1);
    
    s.deleteCharAt(s.length()-1);
    s.append("]");
    
    return s.toString();
  }
  
  public String høyreGren()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }

  public String lengstGren() {
      
    throw new UnsupportedOperationException("Ikke kodet ennå!");
    
  }
  
  public String[] grener()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String bladnodeverdier()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String postString()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  @Override
  public Iterator<T> iterator()
  {
    return new BladnodeIterator();
  }
  
  private class BladnodeIterator implements Iterator<T>
  {
    private Node<T> p = rot, q = null;
    private boolean removeOK = false;
    private int iteratorendringer = endringer;
    
    private BladnodeIterator()  // konstruktør
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }
    
    @Override
    public boolean hasNext()
    {
      return p != null;  // Denne skal ikke endres!
    }
    
    @Override
    public T next()
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }
    
    @Override
    public void remove()
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

  } // BladnodeIterator

} // ObligSBinTre
