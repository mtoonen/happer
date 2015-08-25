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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author meine
 */
public class GameObject implements Comparable<GameObject> {

    protected GameObject north, south, east, west;
    protected int x, y;
    protected static final int size = 100;
    protected int id;
    protected Moveable moveable;

    protected boolean enterable = true;
    public int cost = 999999999;

    public boolean solution = false;

    public GameObject(int id) {
        this.id = id;
          enterable = Math.random() < 0.75;

    }

    public void draw(Graphics g, int max) {
        if (enterable) {
            if (solution) {
                g.setColor(Color.green);
                g.fillRect(x * size, y * size, size, size);
            } else {
                g.setColor(new Color(Color.HSBtoRGB((((float) cost) % max) / max, 0.5f, 0.8f)));
                //g.drawRect(x * size, y * size, size, size);
                g.fillRect(x * size, y * size, size, size);
            }
            g.setColor(Color.red);
            g.drawString(cost + "", x * size + 2, y * size + 20);
            if (moveable != null) {
                moveable.draw(g, cost);
            }
        } else {
            g.setColor(Color.blue);
            g.fillRect(x * size, y * size, size, size);
        }

        g.setColor(Color.white);
        g.drawString("" + id, x * size + 2, y * size + 50);
    }

    public void calculate(List<GameObject> length, List<GameObject> done) {
        int newCost = cost + 1;

        checkNeighbour(north, newCost, length, done);
        checkNeighbour(east, newCost, length, done);
        checkNeighbour(south, newCost, length, done);
        checkNeighbour(west, newCost, length, done);
    }

    protected void checkNeighbour(GameObject go, int cost, List<GameObject> length, List<GameObject> done) {
        if (go != null && go.enterable && !done.contains(go)) {
            go.cost = cost;
            if (!length.contains(go)) {
                length.add(go);
            }
        }
    }

    public void traverseNeighbours(GameObject goal, Set<GameObject> traversed) {
        System.out.println("Via: " +id);
        this.solution = true;
        if (goal == this || traversed.contains(this)) {
            return;
        }
        traversed.add(this);
        Set<GameObject> lowestNeighbours = getLowestCostNeighbours(traversed);
        for (GameObject lowestNeighbour : lowestNeighbours) {
            lowestNeighbour.traverseNeighbours(goal, traversed);
        }
    }

    public Set<GameObject> getLowestCostNeighbours(Set<GameObject> dontInclude) {
        List<GameObject> nbs = new ArrayList<GameObject>() {

            @Override
            public boolean add(GameObject e) {
                if (e != null && e.isEnterable()) {
                    return super.add(e); //To change body of generated methods, choose Tools | Templates.
                } else {
                    return false;
                }
            }
        };
        nbs.add(north);
        nbs.add(east);
        nbs.add(south);
        nbs.add(west);
        
        for (Iterator<GameObject> iterator = nbs.iterator(); iterator.hasNext();) {
            GameObject nb = iterator.next();
            for (GameObject dont : dontInclude) {
                if(nb.id == dont.id){
                    iterator.remove();
                }
            }
        }
        
        Collections.sort(nbs);
        if(nbs.isEmpty()){
            return new HashSet<GameObject>();
        }
        GameObject lowest = nbs.get(0);
        Set<GameObject> lowestNeighbours = new HashSet<>();
        lowestNeighbours.add(lowest);

        for (GameObject go : nbs) {
            if (lowest.cost == go.cost) {
                lowestNeighbours.add(go);
            }
        }
        return lowestNeighbours;
    }

    protected void setCurrentPos(GameObject newPos) {
        if (newPos.north != null) {
            newPos.north.south = this;
        }
        if (newPos.west != null) {
            newPos.west.east = this;
        }

        if (newPos.east != null) {
            newPos.east.west = this;
        }

        if (newPos.south != null) {
            newPos.south.north = this;
        }

        this.x = newPos.x;
        this.y = newPos.y;
        this.north = newPos.north;
        this.east = newPos.east;
        this.south = newPos.south;
        this.west = newPos.west;
    }

    @Override
    public int compareTo(GameObject o) {
        if (this.cost == o.cost) {
            return 0;
        } else if (this.cost > o.cost) {
            return 1;
        } else {
            return -1;
        }
    }

    // <editor-fold desc="Getters and Setters" default-state="collapsed">
    public GameObject getNorth() {
        return north;
    }

    public void setNorth(GameObject north) {
        this.north = north;
    }

    public GameObject getSouth() {
        return south;
    }

    public void setSouth(GameObject south) {
        this.south = south;
    }

    public GameObject getEast() {
        return east;
    }

    public void setEast(GameObject east) {
        this.east = east;
    }

    public GameObject getWest() {
        return west;
    }

    public void setWest(GameObject west) {
        this.west = west;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isEnterable() {
        return enterable;
    }

    public void setEnterable(boolean enterable) {
        this.enterable = enterable;
    }

    public Moveable getMoveable() {
        return moveable;
    }

    public void setMoveable(Moveable moveable) {
        this.moveable = moveable;
        if (moveable != null) {
            moveable.x = x;
            moveable.y = y;
            moveable.setCurrentPos(this);
        }
    }

    // </editor-fold>
    @Override
    public String toString() {
        return (x + "x" + y + ": " + cost);

    }

}
