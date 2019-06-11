package modele;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

/**
 * modélise une ligne à partir d'une figure abstraite
 */
public class Ligne extends FigureColoree {

    /**
     * constructeur
     */
    public Ligne() {
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
    public void modifierPoints(Point[] tab_saisie) {
        tab_mem = tab_saisie;
    }


    /**
     * retourn vrai si les coordonées sont sur la ligne (avec une tolérance de 4 pixels)
     */
    @Override
    public boolean estDedans(int x, int y) {
        double longueur = tab_mem[0].distance(tab_mem[1]);
        double largeur = epaisseurContour / 2.0;
        double d1 = tab_mem[0].distance(x, y);
        double d2 = tab_mem[1].distance(x, y);
        // distance du point a la ligne
        double d = Math.abs((tab_mem[1].getY() - tab_mem[0].getY()) * x - (tab_mem[1].getX() - tab_mem[0].getX()) * y + tab_mem[1].getX() * tab_mem[0].getY() - tab_mem[1].getY() * tab_mem[0].getX()) / longueur;
        if (largeur < 4) largeur = 4;
        return d <= largeur && d1 <= longueur + largeur && d2 <= longueur + largeur;
    }


    /**
     * dessine la figure
     */
    @Override
    public void affiche(Graphics g) {
        if (contour) {
            Graphics2D g2 = (Graphics2D) g;
            Stroke oldStroke = g2.getStroke();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(epaisseurContour, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(couleurContour);
            g.drawLine(tab_mem[0].getX(), tab_mem[0].getY(), tab_mem[1].getX(), tab_mem[1].getY());;
            g2.setStroke(oldStroke);
        }
        super.affichePoints(g);
    }

}
