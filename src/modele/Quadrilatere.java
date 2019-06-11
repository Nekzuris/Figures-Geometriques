package modele;

/**
 * modélise un Quadrilatère à partir d'un polygone abstrait
 */
public class Quadrilatere extends Polygone {

    /**
     * constructeur
     */
    public Quadrilatere() {
        super();
    }

    /**
     * retourne le nombre de points qui definisse la figure
     */
    @Override
    public int nbPoints() {
        return 4;
    }

}
