/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meg.happer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author meine
 */
public class Happer extends Moveable {
    private Human enemy;
    
    
    public Happer(int id) {
        super(id);
    }

    
    public void calcRoute(Controller c) {
        List<GameObject> length = new ArrayList<>();
        List<GameObject> done = new ArrayList<>();
        GameObject start = c.lookup(x, y);
        GameObject end = c.lookup(enemy.x, enemy.y);
        cost = 0;
        solution = true;
        start.calculate(length, done);
        done.add(start);

        while (!length.isEmpty()) {
            Collections.sort(length);
            GameObject go = length.get(0);
            if (go.moveable != null && go.moveable == enemy ) {
                enemy.cost = end.cost;
                enemy.solution = true;
                done.add(end);
                break;
            }
            go.calculate(length, done);
            length.remove(go);
            done.add(go);
        }

        if (done.contains(end) && done.contains(start)) {
            System.out.println(  " ******************************");
            Set<GameObject> traversed = new HashSet<>();
            end.traverseNeighbours(start, traversed);
            System.out.println( "YES FOUND!");
        }else{
            System.out.println( "NOT FOUND!");
        }
        
    }
    
    @Override
    public void draw(Graphics g, int maxCost){
        g.setColor(Color.ORANGE);
        g.fillOval(x*size, y*size, size, size);
    }

    public Human getEnemy() {
        return enemy;
    }

    public void setEnemy(Human enemy) {
        this.enemy = enemy;
    }
}
