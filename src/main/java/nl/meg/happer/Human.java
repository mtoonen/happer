/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meg.happer;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author meine
 */
public class Human extends Moveable{
    
    public Human(int id){
        super(id);
    }
    
    @Override
    public void draw(Graphics g, int drawCost){
    	g.setColor(Color.GREEN);
        g.fillOval(x*size, y*size, size, size);
    }
}
