package modele;

/**
 * modélise un rectangle à partir d'un Quadrilatère
 */
public class Rectangle extends Quadrilatere {

    /**
     * constructeur
     */
    public Rectangle() {
        super();
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
        int x = tab_saisie[0].getX();
        int y = tab_saisie[0].getY();
        int X = tab_saisie[1].getX();
        int Y = tab_saisie[1].getY();
        if (x > X) {
            int t = x;
            x = X;
            X = t;
        }
        if (y > Y) {
            int t = y;
            y = Y;
            Y = t;
        }
        tab_mem[0] = new Point(x, y);
        tab_mem[1] = new Point(X, y);
        tab_mem[2] = new Point(X, Y);
        tab_mem[3] = new Point(x, Y);
    }

    /**
     * déplace un sommet de la figure
     * @param sommet index du sommet
     * @param dx déplacement en x
     * @param dy déplacement en y
     */
    @Override
    public void transformation(int sommet, int dx, int dy) {
        int prevSommet = sommet - 1;
        int nextSommet = sommet + 1;
        if (prevSommet == -1)
            prevSommet = 3;
        if (nextSommet == 4)
            nextSommet = 0;
        if (sommet == 0 || sommet == 2) {
            tab_mem[sommet].translation(dx, dy);
            tab_mem[prevSommet].translationX(dx);
            tab_mem[nextSommet].translationY(dy);
        } else {
            tab_mem[sommet].translation(dx, dy);
            tab_mem[prevSommet].translationY(dy);
            tab_mem[nextSommet].translationX(dx);
        }
    }
}
