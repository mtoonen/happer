package nl.meg.happer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

class Screen extends JFrame {

    private int tool = 1;
    int currentX, currentY, oldX, oldY;
    private Controller controller;
    int screenWidth, screenHeight;

    public Screen(Controller c, int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
        initComponents();
       //this.setExtendedState(Frame.MAXIMIZED_BOTH);
        // this.setUndecorated(true);
        
        this.addKeyListener(c);
        controller = c;
    }

    private void initComponents() {
        // we want a custom Panel2, not a generic JPanel!
        jPanel2 = new Panel2();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jPanel2.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jPanel2MousePressed(evt);
            }

            public void mouseReleased(MouseEvent evt) {
                jPanel2MouseReleased(evt);
            }
        });
        jPanel2.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });

        // add the component to the frame to see it!
        this.setContentPane(jPanel2);
        // be nice to testers..
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }// </editor-fold>

    private void jPanel2MouseDragged(MouseEvent evt) {
        if (tool == 1) {
            currentX = evt.getX();
            currentY = evt.getY();
            oldX = currentX;
            oldY = currentY;
            System.out.println(currentX + " " + currentY);
            System.out.println("PEN!!!!");
        }
    }

    private void jPanel2MousePressed(MouseEvent evt) {
        if(evt.getButton() ==1){
            controller.init();
        }
        
        if(evt.getButton() == 3){
//            controller.replaceGO(evt.getX(), evt.getY(), false);
        }
        oldX = evt.getX();
        oldY = evt.getY();
        System.out.println(oldX + " " + oldY);
    }

    //mouse released//
    private void jPanel2MouseReleased(MouseEvent evt) {
        if (tool == 2) {
            currentX = evt.getX();
            currentY = evt.getY();
            System.out.println("line!!!! from" + oldX + "to" + currentX);
        }
    }

    // Variables declaration - do not modify
    private JPanel jPanel2;
    // End of variables declaration

    // This class name is very confusing, since it is also used as the
    // name of an attribute!
    //class jPanel2 extends JPanel {
    class Panel2 extends JPanel {

        Panel2() {
            // set a preferred size for the custom panel.
            setPreferredSize(new Dimension(screenWidth, screenHeight));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            controller.draw(g);

        }
    }
}
