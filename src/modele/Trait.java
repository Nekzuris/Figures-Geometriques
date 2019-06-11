package modele;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.io.Serializable;

public class Trait implements Serializable {

    private int x, y, X, Y;
    private int epaisseur;
    private Color couleur;

    public Trait(int x, int y, int X, int Y, int epaisseur, Color couleur) {
        this.x = x;
        this.y = y;
        this.X = X;
        this.Y = Y;
        this.epaisseur = epaisseur;
        this.couleur = couleur;
    }

    public void affiche(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Stroke oldStroke = g2.getStroke();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(epaisseur, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(couleur);
        g2.drawLine(x, y, X, Y);
        g2.setStroke(oldStroke);
    }

    public boolean touche(int i, int j, int r) {
        double longueur = distance(x, y, X, Y);
        double d1 = distance(x, y, i, j);
        double d2 = distance(X, Y, i, j);
        // distance du point a la ligne
        double d = Math.abs((Y - y) * i - (X - x) * j + X * y - Y * x) / longueur;

        return d <= r && d1 <= longueur + r && d2 <= longueur + r;
    }

    private double distance(int x, int y, int i, int j) {
        return Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
    }

}
