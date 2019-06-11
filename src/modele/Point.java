package modele;

import java.io.Serializable;

/**
 * modélise un point
 */
public class Point implements Serializable {

    /** coordonnées du point */
    private int x,y;

    /**
     * constructeur
     * @param abs abscisse
     * @param ord ordonnée
     */
    public Point(int abs, int ord) {
        x = abs;
        y = ord;
    }

    /**
     * getter
     * @return abscisse
     */
    public int getX() {
        return x;
    }

    /**
     * getter
     * @return ordonnée
     */
    public int getY() {
        return y;
    }

    /**
     * setter
     * @param x abscisse
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * setter
     * @param y ordonnée
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * déplace le point
     * @param dx déplacement en x
     * @param dy déplacement en y
     */
    public void translation(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * déplace le point su x
     * @param dx déplacement en x
     */
    public void translationX(int dx) {
        x += dx;
    }

    /**
     * déplace le point sur y
     * @param dy déplacement en y
     */
    public void translationY(int dy) {
        y += dy;
    }

    /**
     * calcul la distance entre 2 points
     * @param p deuxième point
     * @return la distance en pixel
     */
    public double distance(Point p) {
        return Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
    }

    /**
     * calcul la distance entre 2 points
     * @param x abscisse du deuxième point
     * @param y ordonnée du deuxième point
     * @return la distance en pixel
     */
    public double distance(int x, int y) {
        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
    }
}
