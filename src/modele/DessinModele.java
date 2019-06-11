package modele;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Observable;

/**
 * modélise un dessin
 */
public class DessinModele extends Observable implements Serializable {

    /** liste de figures */
    private ArrayList<FigureColoree> lfg;
    /** liste de traits de crayon */
    private ArrayList<Trait> traits;
    /** liste de figures supprimmées */
    private Deque<FigureColoree> ctrlZ;
    /** index de la figure sélectionné */
    private int selectedIndex;
    /** emplacement du fichier de sauvegarde */
    private String emplacement;

    /**
     * constructeur
     */
    public DessinModele() {
        lfg = new ArrayList<FigureColoree>();
        traits = new ArrayList<Trait>();
        ctrlZ = new ArrayDeque<FigureColoree>();
        selectedIndex = -1;
    }

    private void refresh() {
        setChanged();
        notifyObservers();
    }

    /**
     * getter
     * @return nombre de figures
     */
    public int getNbFigures() {
        return lfg.size();
    }

    /**
     * getter
     * @return emplacement
     */
    public String getEmplacement() {
        return emplacement;
    }

    /**
     * setter
     * @param e emplacement
     */
    public void setEmplacement(String e) {
        emplacement = e;
    }

    /**
     * setter
     * @param i index de la figure
     */
    public void setSelectedIndex(int i) {
        deselectionne();
        selectedIndex = i;
        lfg.get(i).selectionne();
        refresh();
    }

    /**
     * retourne l'état de sélection de la figure
     * @param i index de la figure
     * @return vrai si la figure est sélectionné
     */
    public boolean isSelected(int i) {
        if (i >= 0 && i < lfg.size()) {
            return lfg.get(i).isSelected();
        } else {
            return false;
        }
    }

    /**
     * getter
     * @return figure selectionnée
     */
    public FigureColoree getFigureSelectionne() {
        if (selectedIndex >= 0) {
            return lfg.get(selectedIndex);
        } else {
            return null;
        }
    }

    /**
     * désélectionne la figure selectionnée
     */
    public void deselectionne() {
        if (selectedIndex >= 0) {
            lfg.get(selectedIndex).deSelectionne();
            selectedIndex = -1;
            refresh();
        }
    }

    /**
     * ajoute une figure au dessin
     * @param fc figure à ajouter
     */
    public void ajouteFigure(FigureColoree fc) {
        deselectionne();
        selectedIndex = lfg.size();
        lfg.add(fc);
        refresh();
    }

    /**
     * supprime la figure sélectionnée
     */
    public void supprimerFigureSelectionne() {
        if (selectedIndex != -1) {
            ctrlZ.add(getFigureSelectionne());
            lfg.remove(selectedIndex);
            selectedIndex = -1;
            refresh();
        }
    }

    /**
     * réajoute au dessin la dernière figure supprimée
     */
    public void ctrlZ() {
        if (ctrlZ.size() > 0)
            ajouteFigure(ctrlZ.pollLast());
    }

    /**
     * fait monter d'un plan la figure sélectionnée (pour l'afficher au dessus des autres)
     */
    public void monterFigure() {
        if (selectedIndex != -1 && selectedIndex < lfg.size() - 1) {
            swapFig(selectedIndex, selectedIndex + 1);
            selectedIndex++;
            refresh();
        }
    }

    /**
     * fait descendre d'un plan la figure sélectionnée (pour l'afficher en dessous des autres)
     */
    public void descendreFigure() {
        if (selectedIndex > 0) {
            swapFig(selectedIndex, selectedIndex - 1);
            selectedIndex--;
            refresh();
        }
    }

    /**
     * ajoute un trait de crayon au dessin
     * @param x abscisse du premier point
     * @param y ordonnée du premier point
     * @param X abscisse du deuxième point
     * @param Y ordonnée du deuxième point
     * @param epaisseur épaisseur du trait
     * @param couleur couleur du trait
     */
    public void ajouteTrait(int x, int y, int X, int Y, int epaisseur, Color couleur) {
        traits.add(new Trait(x, y, X, Y, epaisseur, couleur));
        refresh();
    }

    /**
     * efface le dessin
     */
    public void effacer() {
        lfg.clear();
        traits.clear();
        ctrlZ.clear();
        selectedIndex = -1;
        refresh();
    }

    /**
     * interverti 2 figures dans la liste
     * @param i index de la première figure
     * @param j index de la deuxième figure
     */
    private void swapFig(int i, int j) {
        FigureColoree tmp = lfg.get(j);
        lfg.set(j, lfg.get(i));
        lfg.set(i, tmp);
    }

    /**
     * set le remplissage de la figure sélectionnée
     * @param r vrai: activer remplissage
     */
    public void remplissageFigureSelectionne(boolean r) {
        FigureColoree fc = getFigureSelectionne();
        if (fc != null) {
            fc.afficheRemplissage(r);
            refresh();
        }
    }

    /**
     * set la couleur de la figure sélectionnée
     * @param color couleur de remplissage
     */
    public void couleurRemplissageFigureSelectionne(Color color) {
        FigureColoree fc = getFigureSelectionne();
        if (fc != null) {
            fc.couleurRemplissage(color);
            refresh();
        }
    }

    /**
     * set le contour de la figure sélectionnée
     * @param c vrai: activer contour
     */
    public void contourFigureSelectionne(boolean c) {
        FigureColoree fc = getFigureSelectionne();
        if (fc != null) {
            fc.afficheContour(c);
            refresh();
        }
    }

    /**
     * set l'épaisseur du contour de la figure sémectionnée
     * @param c épaisseur du contour
     */
    public void epaisseurContourFigureSelectionne(int c) {
        FigureColoree fc = getFigureSelectionne();
        if (fc != null) {
            fc.epaisseurContour(c);
            refresh();
        }
    }

    /**
     * set la couleur du contour de la figure sélectionnée
     * @param color couleur du contour
     */
    public void couleurContourFigureSelectionne(Color color) {
        FigureColoree fc = getFigureSelectionne();
        if (fc != null) {
            fc.couleurContour(color);
            refresh();
        }
    }

    /**
     * dessine les figures
     * @param g Graphics
     */
    public void paintFigures(Graphics g) {
        if (lfg != null) {
            for (FigureColoree fc : lfg) {
                fc.affiche(g);
            }
        }
        if (getFigureSelectionne() != null )
            getFigureSelectionne().affichePoints(g);
    }

    /**
     * dessine les traits de crayon
     * @param g Graphics
     */
    public void paintTrait(Graphics g) {
        if (traits != null) {
            for (Trait tr : traits) {
                tr.affiche(g);
            }
        }
    }

    /**
     * déplace la figure sélectionnée
     * @param x déplacement en x
     * @param y déplacement en y
     */
    public void translationFigureSelectionne(int x, int y) {
        getFigureSelectionne().translation(x, y);
        refresh();
    }

    /**
     * déplace un sommet de la figure séléctionnée
     * @param sommet index du sommet à déplacer
     * @param x déplacement en x
     * @param y déplacement en y
     */
    public void transformationFigureSelectionne(int sommet, int x, int y) {
        getFigureSelectionne().transformation(sommet, x, y);
        refresh();
    }

    /**
     * retourne l'index de la figure au coordnnées spécifiées
     * @param x x
     * @param y y
     * @return index de la figure
     */
    public int survol(int x, int y) {
        for (int i = lfg.size() - 1; i >= 0; i--) {
            if (lfg.get(i).estDedans(x, y)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * supprime le trait de crayon aux coordonnées spécifiées
     * @param x x
     * @param y y
     * @param r rayon de la gomme
     */
    public void gommer(int x, int y, int r) {
        for (int i = traits.size() - 1; i >= 0 ; i--) {
            if (traits.get(i).touche(x, y, r)) {
                traits.remove(i);
                refresh();
            }
        }
    }

    /**
     * supprime la figure aux coordonnées spécifiées
     * @param x x
     * @param y y
     */
    public void supprimerFigure(int x, int y) {
        FigureColoree fc = null;
        for (int i = lfg.size() - 1; i >= 0; i--) {
            if (lfg.get(i).estDedans(x, y)) {
                if (!lfg.get(i).isSelected()) {
                    setSelectedIndex(i);
                }
                fc = lfg.get(i);
            }
        }
        if (fc != null) {
            supprimerFigureSelectionne();
        }
    }

}
