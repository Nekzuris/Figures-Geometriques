package controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import modele.DessinModele;
import modele.FigureColoree;
import modele.Point;

/**
 * MouseListener qui contruit une figure a partir des cliques de souris
 */
public class FabricantFigures implements MouseListener {

    /** panneau choix */
    private PanneauChoix pc;
    /** modele */
    private DessinModele dmodele;
    /** figure en cours de construction */
    private FigureColoree figureEnConstruction;
    /** nombre de point déjà cliqué */
    private int nb_points_cliques;
    /** tableau contenant les points cliqués */
    private Point[] points_cliques;

    /**
     * constructeur
     * @param pc panneau choix
     */
    public FabricantFigures(PanneauChoix pc) {
        this.pc = pc;
        this.dmodele = pc.getModele();
        figureEnConstruction = null;
    }

    /** inutilisé */
    public void mouseClicked(MouseEvent e) {}
    /** inutilisé */
    public void mouseEntered(MouseEvent e) {}
    /** inutilisé */
    public void mouseReleased(MouseEvent e) {}

    /**
     * ajoute le point où la souris a cliqué dans le tableau points_cliqus[]
     * et ajoute la figure au modele quand tous les points sont définis
     * @param e MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(figureEnConstruction != null) {
            points_cliques[nb_points_cliques] = new Point(e.getX(), e.getY());
            nb_points_cliques++;
            if (nb_points_cliques == points_cliques.length) {
                figureEnConstruction.modifierPoints(points_cliques);
                dmodele.ajouteFigure(figureEnConstruction);
                figureEnConstruction = null;
            }
        } else {
            dmodele.deselectionne();
            figureEnConstruction = pc.getFigureDemande();
            points_cliques = new Point[figureEnConstruction.nbClics()];
            points_cliques[0] = new Point(e.getX(), e.getY());
            nb_points_cliques = 1;
        }
    }


    /**
     * arrête la construction de la figure si la souris sort de la fenêtre
     * @param e MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent e) {
        figureEnConstruction = null;
    }

}
