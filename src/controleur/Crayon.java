package controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import modele.DessinModele;

/**
 * dessin des traits avec la souris
 */
public class Crayon implements MouseListener, MouseMotionListener {

    /** coordonnée du dernier clique */
    private int lastX, lastY;
    /** PanneauChoix */
    private PanneauChoix pc;
    /** modèle du dessin */
    private DessinModele dmodele;

    /**
     * constructeur
     * @param pc PanneauChoix
     */
    public Crayon(PanneauChoix pc) {
        this.pc = pc;
        this.dmodele = pc.getModele();
    }

    /** inutilisé */
    public void mouseClicked(MouseEvent e) {}
    /** inutilisé */
    public void mouseEntered(MouseEvent e) {}
    /** inutilisé */
    public void mouseExited(MouseEvent e) {}
    /** inutilisé */
    public void mouseReleased(MouseEvent e) {}
    /** inutilisé */
    public void mouseMoved(MouseEvent e) {}

    /**
     * initialise le dernier point cliqué
     * @param e MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }

    /**
     * créer un trait entre le dernier point cliqué et la position de la souris
     * @param e MouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (distance(lastX, lastY, x, y) > 2) {
            dmodele.ajouteTrait(x, y, lastX, lastY, pc.getEpaisseurCrayon(), pc.getCouleurCrayon());
            lastX = x;
            lastY = y;
        }
    }

    /**
     * calcul la distance entre 2 points
     * @param x abscisse du premier point
     * @param y ordonnée du premier point
     * @param X abscisse du deuxième point
     * @param Y ordonnée du deuxième point
     * @return la distance en pixel
     */
    private double distance(int x, int y, int X, int Y) {
        return Math.sqrt(Math.pow(x - X, 2) + Math.pow(y - Y, 2));
    }
}
