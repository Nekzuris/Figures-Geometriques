package modele;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;

/**
 * modélise un polygone à partir d'une Figure abstraite
 */
public abstract class Polygone extends FigureColoree {

    /** polygone de java */
    private Polygon p;

    /**
     * constructeur
     */
    public Polygone() {
        super();
    }

    @Override
    public void affiche(Graphics g) {
        p = new Polygon();
        for (int i=0; i<tab_mem.length; i++) {
            p.addPoint(tab_mem[i].getX(), tab_mem[i].getY());
        }
        if (p == null) return;
        if (remplissage) {
            g.setColor(couleurRemplissage);
            g.fillPolygon(p);
        }
        if (contour) {
            Graphics2D g2 = (Graphics2D) g;
            Stroke oldStroke = g2.getStroke();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(epaisseurContour, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(couleurContour);
            g.drawPolygon(p);
            g2.setStroke(oldStroke);
        }
        super.affichePoints(g);

    }

    /**
     * retourne le nombre de points nécessaire pour créer la figure
     */
    @Override
    public int nbClics() {
        return nbPoints();
    }

    /**
     * change les points qui définisse la figure
     */
    @Override
    public void modifierPoints(Point[] tab_saisie) {
        tab_mem = tab_saisie;
    }

    /**
     * retourn vrai si les coordonées sont dans la figure
     */
    @Override
    public boolean estDedans(int x, int y) {
        if (p != null)
            return p.contains(x, y);
        else
            return false;
    }

}
