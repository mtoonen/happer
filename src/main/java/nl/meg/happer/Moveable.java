/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meg.happer;

/**
 *
 * @author meine
 */
public class Moveable extends GameObject {

  //  protected GameObject currentPos;

    public Moveable(int id) {
        super(id);
        this.enterable = true;
    }
}
