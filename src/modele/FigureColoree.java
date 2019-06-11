package modele;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * class abstraite qui modélise une figure
 */
public abstract class FigureColoree implements Serializable {

    /** taille des carré de sélection */
    public static final int TAILLE_CARRE_SELECTION = 8;
    /** taille de la zone de sélection */
    public static final int ZONE_CARRE_SELECTION = 10;
    /** points qui définisse la figure */
    protected Point[] tab_mem;
    /** sélection */
    private boolean selected;
    /** remplissage */
    protected boolean remplissage;
    /** couleur de remplissage */
    protected Color couleurRemplissage;
    /** contour */
    protected boolean contour;
    /** épaisseur du contour */
    protected int epaisseurContour;
    /** couleur du contour */
    protected Color couleurContour;

    /**
     * constructeur
     */
    public FigureColoree() {
        tab_mem = new Point[nbPoints()];
        selected = true;
        remplissage = true;
        couleurRemplissage = Color.RED;
        contour = true;
        epaisseurContour = 1;
        couleurContour = Color.BLACK;
    }

    /** retourne le nombre de points qui definisse la figure */
    public abstract int nbPoints();
    /** retourne le nombre de points nécessaire pour créer la figure */
    public abstract int nbClics();
    /** change les points qui définisse la figure */
    public abstract void modifierPoints(Point[] ps);
    /** retourn vrai si les coordonées sont dans la figure */
    public abstract boolean estDedans(int x, int y);
    /** dessine la figure */
    public abstract void affiche(Graphics g);

    /**
     * dessine les points qui définisse la figure
     * @param g Graphics
     */
    public void affichePoints(Graphics g) {
        if (tab_mem != null && selected) {
            for (Point p : tab_mem) {
                g.setColor(Color.WHITE);
                g.fillRect(p.getX() - TAILLE_CARRE_SELECTION / 2, p.getY() - TAILLE_CARRE_SELECTION / 2, TAILLE_CARRE_SELECTION, TAILLE_CARRE_SELECTION);
                g.setColor(Color.BLACK);
                g.drawRect(p.getX() - TAILLE_CARRE_SELECTION / 2, p.getY() - TAILLE_CARRE_SELECTION / 2, TAILLE_CARRE_SELECTION, TAILLE_CARRE_SELECTION);
            }
        }
    }

    /**
     * sélectionne la figure
     */
    public void selectionne() {
        selected = true;
    }

    /**
     * dessélectionne la figure
     */
    public void deSelectionne() {
        selected = false;
    }

    /**
     * getter
     * @return selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * setter
     * @param r remplissage
     */
    public void afficheRemplissage(boolean r) {
        remplissage = r;
    }

    /**
     * setter
     * @param c contour
     */
    public void afficheContour(boolean c) {
        contour = c;
    }

    /**
     * setter
     * @param c couleur de remplissage
     */
    public void couleurRemplissage(Color c) {
        couleurRemplissage = c;
    }

    /**
     * setter
     * @param c couleur du contour
     */
    public void couleurContour(Color c) {
        couleurContour = c;
    }

    /**
     * setter
     * @param c épaisseur du contour
     */
    public void epaisseurContour(int c) {
        if (c >= 0) {
            epaisseurContour = c;
        }
    }

    /**
     * déplace la figure
     * @param dx déplacement en x
     * @param dy déplacement en y
     */
    public void translation(int dx, int dy) {
        for (Point p : tab_mem) {
            p.translation(dx, dy);
        }
    }

    /**
     * déplace un sommet de la figure
     * @param sommet index du sommet
     * @param dx déplacement en x
     * @param dy déplacement en y
     */
    public void transformation(int sommet, int dx, int dy) {
        tab_mem[sommet].translation(dx, dy);
    }

    /**
     * retourne l'index du sommet touché
     * @param x x
     * @param y y
     * @return index du sommet ou -1
     */
    public int sommetTouche(int x, int y) {
        for (int i = 0; i < tab_mem.length; i++) {
            if (tab_mem[i].distance(x, y) <= ZONE_CARRE_SELECTION) {
                return i;
            }
        }
        return -1;
    }

}
