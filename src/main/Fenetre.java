package main;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import controleur.BarreEtat;
import controleur.PanneauChoix;
import modele.DessinModele;
import vue.VueDessin;

/**
 * @author Louis Demange
 * @version v5
 * class principal qui permet de lancer l'application
 */
public class Fenetre extends JFrame {

    /** vue */
    private VueDessin vdessin;
    /** panneau principal */
    private JPanel principal;
    /** panneau choix */
    private PanneauChoix choix;
    /** barre d'état */
    private BarreEtat barreEtat;

    /**
     * constructeur
     * @param s titre de la fenêtre
     * @param w largeur
     * @param h hauteur
     */
    public Fenetre(String s, int w, int h) {
        super(s);
        barreEtat = new BarreEtat();
        vdessin = new VueDessin(barreEtat);
        choix = new PanneauChoix(vdessin, barreEtat);
        DessinModele dmodele = new DessinModele();
        barreEtat.liee(dmodele);
        vdessin.liee(dmodele);
        choix.liee(dmodele);

        principal = new JPanel();
        principal.setLayout(new BorderLayout());
        //principal.setPreferredSize(new Dimension(w, h));
        principal.add(choix, BorderLayout.NORTH);
        principal.add(vdessin, BorderLayout.CENTER);
        principal.add(barreEtat, BorderLayout.SOUTH);
        setContentPane(principal);
        setCloseOperation();
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/icon2.png")));
            setIconImage(icon.getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        pack();
    }

    /**
     * ajoute une boite de dialogue d'enregistrement avant de quitter
     */
    private void setCloseOperation() {
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                String[] options = {"Enregistrer", "Ne pas enregistrer", "Annuler"};
                int res = JOptionPane.showOptionDialog(vdessin, "Voulez-vous enregistrer les modifications ?", "Figures Géométriques", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
                switch (res) {
                    case 0 :
                        boolean reussi = choix.enregistrer();
                        if (reussi)
                            dispose();
                        break;
                    case 1 :
                        dispose();
                        break;
                    case 2 :
                    default:
                        break;
                }
            }
        };
        addWindowListener(exitListener);
    }

    /**
     * méthode principal pour lancer l'application
     * @param args arges
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Fenetre f = new Fenetre("Figures Géométriques", 800, 600);
        f.setVisible(true);
    }

}
