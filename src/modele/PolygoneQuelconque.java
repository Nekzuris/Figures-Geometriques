package modele;

/**
 * modélise un Polygone à n coté à partir d'un polygone abstrait
 */
public class PolygoneQuelconque extends Polygone {

    /** nombre de coté du polygone */
    private int nbCote;

    /**
     * constructeur
     * @param n nombre de coté du polygone
     */
    public PolygoneQuelconque(int n) {
        super();
        if (n < 3)
            n = 3;
        nbCote = n;
    }

    /**
     * retourne le nombre de points qui definisse la figure
     */
    @Override
    public int nbPoints() {
        return nbCote;
    }

}
