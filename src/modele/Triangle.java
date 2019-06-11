package modele;

/**
 * modélise un triangle à partir d'un polygone abstrait
 */
public class Triangle extends Polygone {

    /**
     * constructeur
     */
    public Triangle() {
        super();
    }

    /**
     * retourne le nombre de points qui definisse la figure
     */
    @Override
    public int nbPoints() {
        return 3;
    }
}
