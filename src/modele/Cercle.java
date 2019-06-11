package modele;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

/**
 * modélise un cercle à partir d'une figure abstraite
 */
public class Cercle extends FigureColoree {

    /**
     * constructeur
     */
    public Cercle() {
        super();
    }

    /**
     * retourne le nombre de points qui definisse la figure
     */
    @Override
    public int nbPoints() {
        return 2;
    }

    /**
     * retourne le nombre de points nécessaire pour créer la figure
     */
    @Override
    public int nbClics() {
        return 2;
    }


    /**
     * change les points qui définisse la figure
     */
    @Override
    public void modifierPoints(Point[] ps) {
        int r = (int) Math.round(ps[0].distance(ps[1]));
        Point p = new Point(ps[0].getX() + r, ps[0].getY());
        tab_mem[0] = ps[0];
        tab_mem[1] = p;
    }


    /**
     * retourn vrai si les coordonées sont dans la figure
     */
    @Override
    public boolean estDedans(int x, int y) {
        int r = tab_mem[1].getX() - tab_mem[0].getX();
        Point p = new Point(x, y);
        return tab_mem[0].distance(p) <= r;
    }

    /**
     * dessine la figure
     */
    @Override
    public void affiche(Graphics g) {
        int r = tab_mem[1].getX() - tab_mem[0].getX();
        int x = tab_mem[0].getX() - r;
        int y = tab_mem[0].getY() - r;

        if (remplissage) {
            g.setColor(couleurRemplissage);
            g.fillOval(x, y, r * 2 , r * 2);
        }
        if (contour) {
            Graphics2D g2 = (Graphics2D) g;
            Stroke oldStroke = g2.getStroke();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(epaisseurContour, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(couleurContour);
            g.drawOval(x, y, r * 2 , r * 2);
            g2.setStroke(oldStroke);
        }
        super.affichePoints(g);
    }

    /**
     * déplace un sommet de la figure
     * @param sommet index du sommet
     * @param dx déplacement en x
     * @param dy déplacement en y
     */
    @Override
    public void transformation(int sommet, int dx, int dy) {
        if (sommet == 0) {
            super.translation(dx, dy);
        } else if (tab_mem[sommet].getX() + dx > tab_mem[0].getX() + 1){
            tab_mem[sommet].translationX(dx);
        }
    }

    /**
     * retourne l'index du sommet touché
     * @param x x
     * @param y y
     * @return index du sommet ou -1
     */
    @Override
    public int sommetTouche(int x, int y) {
        /*
        override sommetTouche pour tester le deuxieme point en premier,
        (sinon quand le cercle est petit et que les 2 points se superpose
        on ne peut plus selectionner le point exterieur et donc on ne peut plus l'agrandir)
         */
        if (tab_mem[1].distance(x, y) <= FigureColoree.ZONE_CARRE_SELECTION)
            return 1;
        if (tab_mem[0].distance(x, y) <= FigureColoree.ZONE_CARRE_SELECTION)
            return 0;
        return -1;
    }

}
