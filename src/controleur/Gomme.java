package controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import modele.DessinModele;

import javax.swing.*;

/**
 * efface un trait de crayon ou une figure
 */
public class Gomme implements MouseListener, MouseMotionListener {

    /** rayon de la gomme */
    private int RAYON = 7;
    /** modèle du dessin */
    private DessinModele dmodele;

    /**
     * constructeur
     * @param pc PanneauChoix
     */
    public Gomme(PanneauChoix pc) {
        dmodele = pc.getModele();
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
     * efface un trait de crayon ou une figure
     * @param e MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (SwingUtilities.isLeftMouseButton(e)) {
            dmodele.gommer(x, y, RAYON);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            dmodele.supprimerFigure(x, y);
        }
    }

    /**
     * efface un trait de crayon ou une figure
     * @param e MouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        mousePressed(e);
    }

}
