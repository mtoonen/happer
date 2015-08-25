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

    
    public void calcRoute() {

        List<GameObject> length = new ArrayList<>();
        List<GameObject> done = new ArrayList<>();
        cost = 0;
        solution = true;
        currentPos.calculate(length, done);
        done.add(this.currentPos);

        while (!length.isEmpty()) {
            Collections.sort(length);
            GameObject go = length.get(0);
            if (go == enemy || go == enemy.currentPos) {
                done.add(enemy.currentPos);
                break;
            }
            go.calculate(length, done);
            length.remove(go);
            done.add(go);
        }

        if (done.contains(enemy.currentPos) && done.contains(this.currentPos)) {
            System.out.println(  " ******************************");
            GameObject go = enemy;
            Set<GameObject> traversed = new HashSet<>();
            enemy.currentPos.traverseNeighbours(this.currentPos, traversed);
        }
    }
    
    @Override
    public void draw(Graphics g, int maxCost){
       // super.draw(g, maxCost);
        g.setColor(Color.yellow);
        g.fillOval(x*size, y*size, size, size);
    }

    public Human getEnemy() {
        return enemy;
    }

    public void setEnemy(Human enemy) {
        this.enemy = enemy;
    }
}
