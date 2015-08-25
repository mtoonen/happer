/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meg.happer;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author meine
 */
public class Controller implements KeyListener {

    private List<GameObject> objecten = new ArrayList<>();

    public int width = 20;
    public int height = 15;

    private Screen screen;

    private Happer happer;
    private Human human;

    public Controller() {
        screen = new Screen(this, width * GameObject.size, height * GameObject.size);
    }

    private int count = 0;
    public void init() {
    	do {
	        count = 0;
	        objecten.clear();
	        
	        happer = new Happer(count++);
	
	        human = new Human(count++);
	        for (int x = 0; x < width; x++) {
	            for (int y = 0; y < height; y++) {
	                GameObject go = new GameObject(count++);
	                go.setX(x);
	                go.setY(y);
	                if (x == 0 && y == 0) {
	                    go.setMoveable(happer);
	                    go.setEnterable(true);
	                }
	                if (x == width - 1 && y == height - 1) {
	                    go.setMoveable(human);
	                    go.setEnterable(true);
	                }
	                objecten.add(go);
	            }
	        }
	
	        happer.setEnemy(human);
	
	        for (GameObject go : objecten) {
	            go.setNorth(lookup(go.getX(), go.getY() - 1));
	            go.setEast(lookup(go.getX() + 1, go.getY()));
	            go.setSouth(lookup(go.getX(), go.getY() + 1));
	            go.setWest(lookup(go.getX() - 1, go.getY()));
	        }
	        
    	}while(!happer.calcRoute(this));
        screen.setVisible(true);
        repaint();
    }
    
    public void start() throws InterruptedException {
       repaint();
    }

    public GameObject lookup(int x, int y) {
        for (GameObject go : objecten) {
            if (go.getX() == x && go.getY() == y) {
                return go;
            }
        }
        return null;
    }

    public void draw(Graphics g) {
        for (GameObject go : objecten) {
            go.draw(g, human.cost);
        }
    }

    public void repaint() {
        screen.repaint();
    }

    public static void main(String[] args) throws InterruptedException {
        Controller c = new Controller();
        c.init();
        c.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
    		System.exit(0);
    	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        boolean moved = false;
        int curX = human.x;
        int curY = human.y;
        GameObject possibleNew = null;
        switch (e.getKeyChar()) {
            case 'w':
                possibleNew = lookup(curX, curY-1);
                break;
            case 'a':
                possibleNew = lookup(curX-1, curY);
                break;
            case 's':
                possibleNew = lookup(curX, curY+1);
                break;
            case 'd':
                possibleNew = lookup(curX+1, curY);
                break;
        }
        
        if(possibleNew != null && possibleNew.enterable){
            GameObject prev = lookup(human.x, human.y);
            prev.setMoveable(null);
            possibleNew.setMoveable(human);
            moved = true;
        }

        if (moved) {
            for (GameObject go : objecten) {
                go.solution = false;
                go.cost = 0;
                if(go.moveable != null){
                    go.moveable.cost = 0;
                    go.moveable.solution = false;
                }
            }
            happer.calcRoute(this);
        }
        repaint();
    }
}
