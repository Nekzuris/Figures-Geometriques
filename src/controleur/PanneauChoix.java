package controleur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import modele.*;
import vue.VueDessin;

/**
 * permet a l'utilisateur choisir les actions à affectuer
 */
public class PanneauChoix extends JPanel {

    /** modèle du dessin */
    private DessinModele dmodele;
    /** vue */
    private VueDessin vdessin;
    /** barre d'état */
    private BarreEtat betat;
    /** id de la figure demandé */
    private int figureID;
    /** nombre de coté du polygone demandé */
    private int nbCotePolygone;
    /** boutton pour créer un nouveau dessin */
    private JButton nouveau;
    /** boutton pour ouvrir un dessin */
    private JButton ouvrir;
    /** boutton pour enregistrer un dessin */
    private JButton enregistrer;
    /** boutton pour enregistrer un dessin sous un autre nom et/ou emplacement */
    private JButton enregistrerSous;
    /** boutton pour créer une ligne */
    private JToggleButton ligne;
    /** boutton pour créer un rectangle */
    private JToggleButton rectangle;
    /** boutton pour créer un triangle */
    private JToggleButton triangle;
    /** boutton pour créer un quadrilatère */
    private JToggleButton quadrilatere;
    /** boutton pour créer un polygone (pas encore implémenté) */
    private JToggleButton polygone;
    /** boutton pour créer un cercle */
    private JToggleButton cercle;
    /** boutton pour créer un ellipse (pas encore implémenté) */
    private JToggleButton ellipse;
    /** boutton pour activer le remplissage */
    private JCheckBox remplissage;
    /** boutton pour choisir la couleur de remplissage */
    private JButton couleurRemplissage;
    /** boutton pour activer le contour */
    private JCheckBox contour;
    /** menu déroulant pour choisir l'épaisseur du contour */
    private JComboBox<Integer> epaisseurContour;
    /** boutton pour choisir la couleur du contour */
    private JButton couleurContour;
    /** boutton pour activer le crayon */
    private JToggleButton crayon;
    /** boutton pour activer la gomme */
    private JToggleButton gomme;
    /** boutton pour déplacer/modifier les figures */
    private JToggleButton deplacer;
    /** boutton pour choisir la couleur du crayon */
    private JButton couleurCrayon;
    /** menu déroulant pour choisir l'épaisseur du crayon */
    private JComboBox<Integer> epaisseurCrayon;
    /** gère le groupe de boutton */
    private ButtonGroup groupe;
    /** indique si la sauvegarde a réussi */
    private boolean sauvegardeReussi = false;

    /**
     * constructeur
     * @param vue vue
     * @param br barre d'état
     */
    public PanneauChoix(VueDessin vue, BarreEtat br) {
        vdessin = vue;
        betat = br;
        figureID = -1;
        initComponents();
        addAction();
    }

    /**
     * initialise tous les composants swing
     */
    private void initComponents() {
        setLayout(new FlowLayout(FlowLayout.LEADING));
        UIManager.put("ToolTip.background", Color.WHITE);
        // UIManager.put("ToolTip.border",new LineBorder(Color.BLACK,1));

        ImageIcon nouveauIcon = null;
        ImageIcon ouvrirIcon = null;
        ImageIcon enregistrerIcon = null;
        ImageIcon enregistrerSousIcon = null;
        ImageIcon crayonIcon = null;
        ImageIcon gommeIcon = null;
        ImageIcon deplacerIcon = null;
        ImageIcon ligneIcon = null;
        ImageIcon rectangleIcon = null;
        ImageIcon triangleIcon = null;
        ImageIcon quadrilatereIcon = null;
        ImageIcon polygoneIcon = null;
        ImageIcon cercleIcon = null;
        ImageIcon ellipseIcon = null;

        try {
            nouveauIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/nouveau.png")).getScaledInstance(48, 48, Image.SCALE_SMOOTH));
            ouvrirIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/ouvrir.png")).getScaledInstance(48, 48, Image.SCALE_SMOOTH));
            enregistrerIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/enregistrer.png")).getScaledInstance(48, 48, Image.SCALE_SMOOTH));
            enregistrerSousIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/enregistrer_sous.png")).getScaledInstance(48, 48, Image.SCALE_SMOOTH));
            crayonIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/crayon.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            gommeIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/gomme.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            deplacerIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/deplacer.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            ligneIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/ligne.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            rectangleIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/rectangle.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            triangleIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/triangle.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            quadrilatereIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/quadrilatere.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            polygoneIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/polygone.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            cercleIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/cercle.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            ellipseIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/img/ellipse.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        nouveau = new JButton(nouveauIcon);
        nouveau.setPreferredSize(new Dimension(48, 48));
        nouveau.setToolTipText("Nouveau");
        nouveau.setFocusable(false);
        add(nouveau);

        ouvrir = new JButton(ouvrirIcon);
        ouvrir.setPreferredSize(new Dimension(48, 48));
        ouvrir.setToolTipText("Ouvrir");
        ouvrir.setFocusable(false);
        add(ouvrir);

        enregistrer = new JButton(enregistrerIcon);
        enregistrer.setPreferredSize(new Dimension(48, 48));
        enregistrer.setToolTipText("Enregistrer");
        enregistrer.setFocusable(false);
        add(enregistrer);

        enregistrerSous = new JButton(enregistrerSousIcon);
        enregistrerSous.setPreferredSize(new Dimension(48, 48));
        enregistrerSous.setToolTipText("Enregistrer sous");
        enregistrerSous.setFocusable(false);
        add(enregistrerSous);

        JPanel fichier = new JPanel();
        fichier.setLayout(new GridLayout(2, 2, 2, 2));
        fichier.add(nouveau);
        fichier.add(ouvrir);
        fichier.add(enregistrer);
        fichier.add(enregistrerSous);

        JLabel fichierTitre = new JLabel("Fichier", SwingConstants.CENTER);
        fichierTitre.setForeground(new Color(80, 80, 80));
        fichierTitre.setBorder(new EmptyBorder(4, 0, 0, 0));

        JPanel fichierMenu = new JPanel(new BorderLayout());
        fichierMenu.add(fichier, BorderLayout.NORTH);
        fichierMenu.add(fichierTitre, BorderLayout.SOUTH);
        add(fichierMenu);

        // ================================================================================================================================
        JSeparator sp1 = new JSeparator(JSeparator.VERTICAL);
        sp1.setBackground(new Color(200, 200, 200));
        sp1.setForeground(new Color(200, 200, 200));
        sp1.setPreferredSize(new Dimension(1, 110));
        add(sp1);
        // ================================================================================================================================

        ligne = new JToggleButton(ligneIcon);
        ligne.setPreferredSize(new Dimension(48, 48));
        ligne.setToolTipText("Ligne");
        ligne.setFocusable(false);

        rectangle = new JToggleButton(rectangleIcon);
        rectangle.setPreferredSize(new Dimension(48, 48));
        rectangle.setToolTipText("Rectangle");
        rectangle.setFocusable(false);

        triangle = new JToggleButton(triangleIcon);
        triangle.setPreferredSize(new Dimension(48, 48));
        triangle.setToolTipText("Triangle");
        triangle.setFocusable(false);

        quadrilatere = new JToggleButton(quadrilatereIcon);
        quadrilatere.setPreferredSize(new Dimension(48, 48));
        quadrilatere.setToolTipText("Quadrilatere");
        quadrilatere.setFocusable(false);

        polygone = new JToggleButton(polygoneIcon);
        polygone.setPreferredSize(new Dimension(48, 48));
        polygone.setToolTipText("Polygone");
        polygone.setFocusable(false);

        cercle = new JToggleButton(cercleIcon);
        cercle.setPreferredSize(new Dimension(48, 48));
        cercle.setToolTipText("Cercle");
        cercle.setFocusable(false);

        ellipse = new JToggleButton(ellipseIcon);
        ellipse.setPreferredSize(new Dimension(48, 48));
        ellipse.setToolTipText("Ellipse");
        ellipse.setFocusable(false);

        JPanel formesListe = new JPanel(new GridLayout(2, 3, 2, 2));
        // formesListe.setPreferredSize(new Dimension(82, 56)); //
        // arrrrrrrrrrrrrrrrrrrrrrr
        formesListe.setBackground(new Color(245, 245, 245));
        formesListe.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(173, 173, 173)),
                BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(245, 245, 245))));
        formesListe.add(ligne);
        formesListe.add(rectangle);
        formesListe.add(triangle);
        formesListe.add(quadrilatere);
        formesListe.add(polygone);
        formesListe.add(cercle);
        //formesListe.add(ellipse);

        JLabel remplissageTitre = new JLabel("Remplissage");

        remplissage = new JCheckBox();
        remplissage.setFocusable(false);

        couleurRemplissage = new JButton();
        couleurRemplissage.setPreferredSize(new Dimension(16, 16));
        couleurRemplissage.setBackground(Color.RED);
        couleurRemplissage.setToolTipText("Couleur de remplissage");
        couleurRemplissage.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(173, 173, 173)),
                        BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE)));
        couleurRemplissage.setContentAreaFilled(false);
        couleurRemplissage.setOpaque(true);
        couleurRemplissage.setFocusable(false);

        JPanel rrr = new JPanel(new BorderLayout());
        rrr.add(remplissageTitre, BorderLayout.NORTH);
        rrr.add(remplissage, BorderLayout.WEST);
        rrr.add(couleurRemplissage, BorderLayout.EAST);

        JLabel contourTitre = new JLabel("Contour");

        contour = new JCheckBox();
        contour.setFocusable(false);

        epaisseurContour = new JComboBox<Integer>(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
        epaisseurContour.setPreferredSize(new Dimension(32, 16));
        epaisseurContour.setToolTipText("Epaisseur du contour");
        epaisseurContour.setMaximumRowCount(20);
        epaisseurContour.setFocusable(false);

        couleurContour = new JButton();
        couleurContour.setPreferredSize(new Dimension(16, 16));
        couleurContour.setBackground(Color.BLACK);
        couleurContour.setToolTipText("Couleur du contour");
        couleurContour.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(173, 173, 173)),
                        BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE)));
        couleurContour.setContentAreaFilled(false);
        couleurContour.setOpaque(true);
        couleurContour.setFocusable(false);

        JPanel ccc = new JPanel(new BorderLayout());
        ccc.add(contourTitre, BorderLayout.NORTH);
        ccc.add(contour, BorderLayout.WEST);
        ccc.add(couleurContour, BorderLayout.EAST);
        ccc.add(epaisseurContour, BorderLayout.SOUTH);

        JPanel formesControle = new JPanel(new BorderLayout());
        formesControle.setPreferredSize(new Dimension(80, 116));
        formesControle.add(rrr, BorderLayout.NORTH);
        formesControle.add(ccc, BorderLayout.SOUTH);

        JLabel formesTitre = new JLabel("Figures", SwingConstants.CENTER);
        formesTitre.setForeground(new Color(80, 80, 80));
        formesTitre.setBorder(new EmptyBorder(4, 0, 0, 0));

        JPanel formesMenu = new JPanel(new BorderLayout());
        formesMenu.add(formesListe, BorderLayout.WEST);
        formesMenu.add(formesControle, BorderLayout.EAST);
        formesMenu.add(formesTitre, BorderLayout.SOUTH);
        formesMenu.setPreferredSize(new Dimension(240, 116)); // layout manager de merde
        add(formesMenu);

        // ================================================================================================================================
        JSeparator sp2 = new JSeparator(JSeparator.VERTICAL);
        sp2.setBackground(new Color(200, 200, 200));
        sp2.setForeground(new Color(200, 200, 200));
        sp2.setPreferredSize(new Dimension(1, 110));
        add(sp2);
        // ================================================================================================================================

        crayon = new JToggleButton(crayonIcon);
        crayon.setPreferredSize(new Dimension(32, 32));
        crayon.setToolTipText("Crayon");
        crayon.setFocusable(false);

        gomme = new JToggleButton(gommeIcon);
        gomme.setPreferredSize(new Dimension(32, 32));
        gomme.setToolTipText("Gomme");
        gomme.setFocusable(false);

        deplacer = new JToggleButton(deplacerIcon);
        deplacer.setPreferredSize(new Dimension(32, 32));
        deplacer.setToolTipText("Deplacer/Redimensionner");
        deplacer.setFocusable(false);

        couleurCrayon = new JButton();
        couleurCrayon.setPreferredSize(new Dimension(32, 32));
        couleurCrayon.setBackground(Color.BLUE);
        couleurCrayon.setToolTipText("Couleur du crayon");
        couleurCrayon.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(173, 173, 173)),
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE)));
        couleurCrayon.setContentAreaFilled(false);
        couleurCrayon.setOpaque(true);
        couleurCrayon.setFocusable(false);

        epaisseurCrayon = new JComboBox<Integer>(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
        epaisseurCrayon.setPreferredSize(new Dimension(32, 32));
        epaisseurCrayon.setToolTipText("Epaisseur du crayon");
        epaisseurCrayon.setMaximumRowCount(20);
        epaisseurCrayon.setFocusable(false);

        groupe = new ButtonGroup();
        groupe.add(ligne);
        groupe.add(rectangle);
        groupe.add(triangle);
        groupe.add(quadrilatere);
        groupe.add(polygone);
        groupe.add(cercle);
        groupe.add(ellipse);
        groupe.add(crayon);
        groupe.add(gomme);
        groupe.add(deplacer);

        JPanel outils = new JPanel(new GridLayout(2, 3, 2, 2));
        outils.add(crayon);
        outils.add(gomme);
        outils.add(deplacer);
        outils.add(couleurCrayon);
        outils.add(epaisseurCrayon);

        JLabel outilsTitre = new JLabel("Outils", SwingConstants.CENTER);
        outilsTitre.setForeground(new Color(80, 80, 80));
        outilsTitre.setBorder(new EmptyBorder(4, 0, 0, 0));

        JPanel outilsMenu = new JPanel(new BorderLayout());
        outilsMenu.add(outils, BorderLayout.NORTH);
        outilsMenu.add(outilsTitre, BorderLayout.SOUTH);
        outilsMenu.setPreferredSize(new Dimension(100, 116));
        add(outilsMenu);

        // ================================================================================================================================
        JSeparator sp3 = new JSeparator(JSeparator.VERTICAL);
        sp3.setBackground(new Color(200, 200, 200));
        sp3.setForeground(new Color(200, 200, 200));
        sp3.setPreferredSize(new Dimension(1, 110));
        add(sp3);
        // ================================================================================================================================

    }

    /**
     * ajoute les actions aux bouttons
     */
    private void addAction() {
        nouveau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Enregistrer", "Ne pas enregistrer", "Annuler"};
                int res = JOptionPane.showOptionDialog(vdessin, "Voulez-vous enregistrer les modifications ?", "Figure Géométriques", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
                switch (res) {
                    case 0 :
                        enregistrer.doClick();
                        dmodele.effacer();
                        dmodele.setEmplacement(null);
                        break;
                    case 1 :
                        dmodele.effacer();
                        dmodele.setEmplacement(null);
                        break;
                    case 2 :
                    default:
                        break;
                }
            }
        });

        ouvrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chemin = "";
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Ouvrir");
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(vdessin);
                if (result == JFileChooser.APPROVE_OPTION) {
                    chemin = fileChooser.getSelectedFile().getAbsolutePath();
                } else {
                    return;
                }

                try {
                    ObjectInputStream file = new ObjectInputStream(new FileInputStream(chemin));
                    DessinModele dm = (DessinModele) file.readObject();
                    file.close();
                    liee(dm);
                    vdessin.liee(dm);
                    betat.liee(dm);
                    groupe.clearSelection();
                } catch (IOException | ClassNotFoundException err) {
                    err.printStackTrace();
                }
            }
        });

        enregistrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chemin = dmodele.getEmplacement();

                if (chemin == null || chemin.equals("")) {
                    enregistrerSous.doClick();
                } else {
                    try {
                        ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(chemin));
                        file.writeObject(dmodele);
                        file.close();
                    } catch (IOException err) {
                        sauvegardeReussi = false;
                        err.printStackTrace();
                    }
                }

            }
        });

        enregistrerSous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chemin = "";

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Enregistrer sous");
                int userSelection = fileChooser.showSaveDialog(vdessin);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    chemin = fileChooser.getSelectedFile().getAbsolutePath();
                    dmodele.setEmplacement(chemin);
                    sauvegardeReussi = true;
                } else {
                    sauvegardeReussi = false;
                    return;
                }

                try {
                    ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(chemin));
                    file.writeObject(dmodele);
                    file.close();
                } catch (IOException err) {
                    sauvegardeReussi = false;
                    err.printStackTrace();
                }
            }
        });

        // ================================================================================================================================

        ligne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vdessin.desactiveCrayon();
                vdessin.desactiveManipulationsSouris();
                vdessin.activeCreationFigure();
                figureID = 0;
            }
        });

        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vdessin.desactiveCrayon();
                vdessin.desactiveManipulationsSouris();
                vdessin.activeCreationFigure();
                figureID = 1;
            }
        });

        triangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vdessin.desactiveCrayon();
                vdessin.desactiveManipulationsSouris();
                vdessin.activeCreationFigure();
                figureID = 2;
            }
        });

        quadrilatere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vdessin.desactiveCrayon();
                vdessin.desactiveManipulationsSouris();
                vdessin.activeCreationFigure();
                figureID = 3;
            }
        });

        polygone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vdessin.desactiveCrayon();
                vdessin.desactiveManipulationsSouris();
                vdessin.activeCreationFigure();
                figureID = 4;

                String nbCote = JOptionPane.showInputDialog("Nombre de coté : ");
                try {
                    nbCotePolygone = Integer.parseInt(nbCote);
                    if (nbCotePolygone < 3) {
                        JOptionPane.showMessageDialog(vdessin, "Un polygone doit avoir au moins 3 cotés", "Figures Géométriques", JOptionPane.WARNING_MESSAGE);
                        throw new NumberFormatException("Nombre de coté incorrecte");
                    }
                } catch (NumberFormatException err) {
                    deplacer.doClick();
                }
            }
        });

        cercle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vdessin.desactiveCrayon();
                vdessin.desactiveManipulationsSouris();
                vdessin.activeCreationFigure();
                figureID = 5;
            }
        });

        ellipse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vdessin.desactiveCrayon();
                vdessin.desactiveManipulationsSouris();
                vdessin.activeCreationFigure();
                figureID = 6;
            }
        });

        remplissage.setSelected(true);
        remplissage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (deplacer.isSelected()) {
                    dmodele.remplissageFigureSelectionne(remplissage.isSelected());
                }
            }
        });

        couleurRemplissage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(vdessin, "Choix couleur", couleurRemplissage.getBackground());
                if (color != null) {
                    couleurRemplissage.setBackground(color);
                }
                if (deplacer.isSelected()) {
                    dmodele.couleurRemplissageFigureSelectionne(color);
                }
            }
        });

        contour.setSelected(true);
        contour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (deplacer.isSelected()) {
                    dmodele.contourFigureSelectionne(contour.isSelected());
                }
            }
        });

        epaisseurContour.setSelectedItem(3);
        epaisseurContour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (deplacer.isSelected()) {
                    dmodele.epaisseurContourFigureSelectionne((int) epaisseurContour.getSelectedItem());
                }
            }
        });

        couleurContour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(vdessin, "Choix couleur", couleurContour.getBackground());
                if (color != null) {
                    couleurContour.setBackground(color);
                }
                if (deplacer.isSelected()) {
                    dmodele.couleurContourFigureSelectionne(color);
                }
            }
        });

        // ================================================================================================================================

        crayon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vdessin.desactiveCreationFigure();
                vdessin.desactiveManipulationsSouris();
                vdessin.desactiveGomme();
                vdessin.activeCrayon();
                dmodele.deselectionne();
            }
        });

        gomme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vdessin.desactiveCrayon();
                vdessin.desactiveCreationFigure();
                vdessin.desactiveManipulationsSouris();
                vdessin.activeGomme();
            }
        });

        deplacer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vdessin.desactiveCreationFigure();
                vdessin.desactiveCrayon();
                vdessin.desactiveGomme();
                vdessin.activeManipulationsSouris();
            }
        });

        couleurCrayon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(vdessin, "Choix couleur", couleurCrayon.getBackground());
                if (color != null) {
                    couleurCrayon.setBackground(color);
                }
            }
        });
        epaisseurCrayon.setSelectedItem(5);
        epaisseurCrayon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    /**
     * retourne la figure demandé par l'utilisateur lorsque FabriquantFigures commence à construire une nouvelle figure
     * @return figures correspondant au choix de PanneauChoix
     */
    public FigureColoree getFigureDemande() {
        FigureColoree fc = null;
        switch (figureID) {
            case 0:
                fc = new Ligne();
                break;
            case 1:
                fc = new Rectangle();
                break;
            case 2:
                fc = new Triangle();
                break;
            case 3:
                fc = new Quadrilatere();
                break;
            case 4:
                fc = new PolygoneQuelconque(nbCotePolygone);
                break;
            case 5:
                fc = new Cercle();
                break;
            case 6:
                // Ellipse
                break;
        }

        if (fc == null)
            return null;

        fc.epaisseurContour((int) epaisseurContour.getSelectedItem());
        fc.afficheContour(contour.isSelected());
        if (contour.isSelected()) {
            fc.couleurContour(couleurContour.getBackground());
        }
        fc.afficheRemplissage(remplissage.isSelected());
        if (remplissage.isSelected()) {
            fc.couleurRemplissage(couleurRemplissage.getBackground());
        }
        return fc;
    }

    /**
     * retourne l'épaisseur du crayon sélectionné dans le PanneauChoix
     * @return épaisseur du crayon
     */
    public int getEpaisseurCrayon() {
        return (int) epaisseurCrayon.getSelectedItem();
    }

    /**
     * retourne la couleur du crayon sélectionné dans le PanneauChoix
     * @return couleur du crayon
     */
    public Color getCouleurCrayon() {
        return couleurCrayon.getBackground();
    }

    /**
     * retourne le modèle (utilisé pas les MouseListener)
     * @return DessinModele
     */
    public DessinModele getModele() {
        return dmodele;
    }

    /**
     * retourne la vue (utilisé pas ManipulateurFormes)
     * @return VueDessin
     */
    public VueDessin getVue() {
        return vdessin;
    }

    /**
     * lie le PanneauChoix et les MouseListener aux modèle du dessin
     * @param dm modèle du dessin
     */
    public void liee(DessinModele dm) {
        dmodele = dm;
        vdessin.setListener(new FabricantFigures(this), new ManipulateurFormes(this), new Crayon(this), new Gomme(this));
    }

    /**
     * clique sur le boutton enregistrer
     * @return vrai si la sauvegarde a réussi
     */
    public boolean enregistrer() {
        enregistrer.doClick();
        return sauvegardeReussi;
    }
}
