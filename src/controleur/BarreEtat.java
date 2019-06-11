package controleur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;

import modele.DessinModele;

/**
 * affiche des informations sur le dessin
 */
public class BarreEtat extends JPanel implements Observer {

    /** position du curseur sur le dessin */
    private JLabel curseur;
    /** nombre de figure sur le dessin */
    private JLabel nbFigures;
    /** dimension du dessin */
    private JLabel dimension;

    /**
     * constructeur
     */
    public BarreEtat() {
        setLayout(new BorderLayout());

        Font font = new Font(null, Font.PLAIN, 12);

        JPanel gauche = new JPanel(new FlowLayout(FlowLayout.LEADING, 4, 4));

        curseur = new JLabel(" ");
        curseur.setPreferredSize(new Dimension(100, 20));
        curseur.setFont(font);
        gauche.add(curseur);

        // ================================================================================================================================
        JSeparator sp1 = new JSeparator(JSeparator.VERTICAL);
        sp1.setBackground(new Color(200, 200, 200));
        sp1.setForeground(new Color(200, 200, 200));
        sp1.setPreferredSize(new Dimension(1, 20));
        gauche.add(sp1);
        // ================================================================================================================================

        nbFigures = new JLabel("0 Figure");
        nbFigures.setPreferredSize(new Dimension(100, 20));
        nbFigures.setFont(font);
        gauche.add(nbFigures);

        // ================================================================================================================================
        JSeparator sp2 = new JSeparator(JSeparator.VERTICAL);
        sp2.setBackground(new Color(200, 200, 200));
        sp2.setForeground(new Color(200, 200, 200));
        sp2.setPreferredSize(new Dimension(1, 20));
        gauche.add(sp2);
        // ================================================================================================================================

        dimension = new JLabel("1280 x 720px");
        dimension.setPreferredSize(new Dimension(100, 20));
        dimension.setFont(font);
        gauche.add(dimension);

        // ================================================================================================================================
        JSeparator sp3 = new JSeparator(JSeparator.VERTICAL);
        sp3.setBackground(new Color(200, 200, 200));
        sp3.setForeground(new Color(200, 200, 200));
        sp3.setPreferredSize(new Dimension(1, 20));
        gauche.add(sp3);
        // ================================================================================================================================

        add(gauche, BorderLayout.WEST);



        ImageIcon moinsIcon = null;
        ImageIcon plusIcon = null;
        try {
            moinsIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/moins.png")).getScaledInstance(18, 18, Image.SCALE_FAST));
            plusIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/plus.png")).getScaledInstance(18, 18, Image.SCALE_FAST));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel droite = new JPanel(new FlowLayout(FlowLayout.LEADING, 4, 4));

        // ================================================================================================================================
        JSeparator sp4 = new JSeparator(JSeparator.VERTICAL);
        sp4.setBackground(new Color(200, 200, 200));
        sp4.setForeground(new Color(200, 200, 200));
        sp4.setPreferredSize(new Dimension(1, 20));
        droite.add(sp4);
        // ================================================================================================================================

        JLabel zoomLvl = new JLabel("100%");
        zoomLvl.setPreferredSize(new Dimension(40, 20));
        zoomLvl.setFont(font);
        zoomLvl.setToolTipText("Niveau de zoom");
        droite.add(zoomLvl);

        JButton zoomMoins = new JButton(moinsIcon);
        zoomMoins.setPreferredSize(new Dimension(20, 20));
        zoomMoins.setToolTipText("Zoom arrière");
        zoomMoins.setFocusable(false);
        droite.add(zoomMoins);

        JSlider zoom = new JSlider(JSlider.HORIZONTAL, 1, 9, 5);
        zoom.setPreferredSize(new Dimension(100, 20));
        zoom.setToolTipText("Zoom");
        zoom.setMinorTickSpacing(1);
        zoom.setSnapToTicks(true);
        zoom.setFocusable(false);
        droite.add(zoom);

        JButton zoomPlus = new JButton(plusIcon);
        zoomPlus.setPreferredSize(new Dimension(20, 20));
        zoomPlus.setToolTipText("Zoom avant");
        zoomPlus.setFocusable(false);
        droite.add(zoomPlus);

        // TODO implementer le zoom
        //add(droite, BorderLayout.EAST);

    }

    @Override
    public void update(Observable dm, Object arg) {
        DessinModele dmodele = (DessinModele) dm;
        setNbFigures(dmodele.getNbFigures());

    }

    /**
     * met à jour la position du curseur
     * @param x abscisse du curseur
     * @param y ordonnée du curseur
     */
    public void setCurseur(int x, int y) {
        if (x < 0) {
            curseur.setText(" ");
        } else {
            curseur.setText(x + ", " + y + "px");
        }
    }

    /**
     * met à jour le nombre de figure
     * @param n nombres de figures
     */
    private void setNbFigures(int n) {
        if (n > 1) {
            nbFigures.setText(n + " Figures");
        } else {
            nbFigures.setText(n + " Figure");
        }
    }

    /**
     * met à jour les dimension du dessin
     * @param x largeur
     * @param y hauteur
     */
    public void setDimension(int x, int y) {
        dimension.setText(x + " x " + y + "px");
    }

    /**
     * lie la barre d'état au modèle du dessin
     * @param dm modèle du dessin
     */
    public void liee(DessinModele dm) {
        dm.addObserver(this);
        nbFigures.setText("0 Figure");
        update(dm, null);
    }

}
