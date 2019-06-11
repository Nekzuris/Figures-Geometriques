package controleur;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import modele.DessinModele;
import modele.FigureColoree;
import vue.VueDessin;

/**
 * déplace ou modifie les figures avec la souris et le clavier
 */
public class ManipulateurFormes implements MouseListener, MouseMotionListener, KeyListener {

    /** coordonnée du dernier clique */
    private int lastX, lastY;
    /** indice du sommet selectionné */
    private int sommet;
    /** autorise la modification ou non */
    private boolean peutModifier;
    /** modèle du dessin */
    private DessinModele dmodele;
    /** vue */
    private VueDessin vdessin;

    /**
     * constructeur
     * @param pc PanneauChoix
     */
    public ManipulateurFormes(PanneauChoix pc) {
        dmodele = pc.getModele();
        vdessin = pc.getVue();
        peutModifier = false;
    }

    /** inutilisé */
    public void mouseClicked(MouseEvent e) {}
    /** inutilisé */
    public void mouseEntered(MouseEvent e) {}
    /** inutilisé */
    public void mouseExited(MouseEvent e) {}
    /** inutilisé */
    public void mouseReleased(MouseEvent e) {}

    /**
     * change la forme du curseur en fonction de là où il se trouve sur le dessin
     * @param e MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        FigureColoree fc = dmodele.getFigureSelectionne();
        if (fc != null) {
            int s = fc.sommetTouche(x, y);
            if (s != -1) {
                vdessin.setCursor(new Cursor(Cursor.HAND_CURSOR));
                return;
            }
            if (fc.estDedans(x, y)) {
                vdessin.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                return;
            }
        }
        vdessin.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * sélectionne la figure touche par un clique de souris ou désélectionne tout
     * @param e MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        lastX = x;
        lastY = y;
        FigureColoree fc = dmodele.getFigureSelectionne();
        if (fc != null) {
            sommet = fc.sommetTouche(x, y);
            if (sommet != -1) {
                peutModifier = true;
                return;
            }
        } else {
            sommet = - 1;
        }

        int fcs = dmodele.survol(x, y);

        if (fcs != -1) {
            vdessin.setCursor(new Cursor (Cursor.MOVE_CURSOR));
            if (dmodele.isSelected(fcs)) {
                peutModifier = true;
            } else {
                peutModifier = false;
                dmodele.setSelectedIndex(fcs);
            }
        } else if (sommet == -1) {
            dmodele.deselectionne();
            peutModifier = false;
        } else {
            peutModifier = true;
        }
    }

    /**
     * déplace la figure sélectionné ou un de ses sommets
     * @param e MouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (peutModifier) {
            int x = e.getX();
            int y = e.getY();
            if (sommet == -1) {
                dmodele.translationFigureSelectionne(x - lastX, y - lastY);
            } else {
                dmodele.transformationFigureSelectionne(sommet, x - lastX, y - lastY);
            }
            lastX = x;
            lastY = y;
        }
    }

    /**
     * ajoute des actions à des touches
     * +: monter la figure
     * -: descendre la figure
     * supp: supprimer la figure
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ADD:
            case KeyEvent.VK_PLUS:
                dmodele.monterFigure();
                break;
            case KeyEvent.VK_SUBTRACT:
            case KeyEvent.VK_MINUS:
                dmodele.descendreFigure();
                break;
            case KeyEvent.VK_DELETE:
            case KeyEvent.VK_BACK_SPACE:
                dmodele.supprimerFigureSelectionne();
                break;
        }
    }

    /** inutilisé */
    public void keyReleased(KeyEvent e) {}
    /** inutilisé */
    public void keyTyped(KeyEvent e) {}

}
