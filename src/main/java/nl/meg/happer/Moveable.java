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

    protected GameObject currentPos;

    public Moveable(int id) {
        super(id);
        this.enterable = true;
    }

    public boolean up() {
        return moveTo(north);
    }

    public boolean down() {
        return moveTo(south);
    }

    public boolean right() {
        return moveTo(east);
    }

    public boolean left() {
        return moveTo(west);
    }

    protected boolean moveTo(GameObject pos) {

        if (pos != null && pos.enterable) {
            System.out.print(pos.x + " - " + pos.y);
            setCurrentPos(pos);
        }
        boolean bool = pos != null && pos.enterable;
        System.out.println(bool + " mogelijk");
        return bool;
    }

    public GameObject getCurrentPos() {
        return currentPos;
    }

    @Override
    public void setCurrentPos(GameObject newPos) {
        super.setCurrentPos(newPos);
        // de buren van vorige positie weer op de vorige positie zetten zetten
        if (this.currentPos != null) {
            if (currentPos.north != null) {
                currentPos.north.south = currentPos;
            }
            if (currentPos.west != null) {
                currentPos.west.east = currentPos;
            }

            if (currentPos.east != null) {
                currentPos.east.west = currentPos;
            }

            if (currentPos.south != null) {
                currentPos.south.north = currentPos;
            }
        }
        this.currentPos = newPos;
    }
}
