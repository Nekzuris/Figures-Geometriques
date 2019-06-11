package vue;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controleur.BarreEtat;
import controleur.Crayon;
import controleur.FabricantFigures;
import controleur.Gomme;
import controleur.ManipulateurFormes;
import modele.DessinModele;

/**
 * affiche le dessin a partir de son modèle
 */
public class VueDessin extends JPanel implements Observer {

    /** modèle du dessin */
    private DessinModele dmodele;
    /** barre d'état */
    private BarreEtat be;
    /** créateur de formes */
    private FabricantFigures ff;
    /** manipulateur de formes */
    private ManipulateurFormes mf;
    /** crayon */
    private Crayon cr;
    /** gomme */
    private Gomme gm;

    /**
     * constructeur
     * @param be barre d'état
     */
    public VueDessin(BarreEtat be) {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1280, 720));
        this.be = be;

        addComponentListener(new ComponentListener() { // provisoir, la taille du dessin doit etre gerer dans le modele
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension d = getSize();
                be.setDimension(d.width, d.height);
            }

            public void componentShown(ComponentEvent e) {}
            public void componentMoved(ComponentEvent e) {}
            public void componentHidden(ComponentEvent e) {}
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                be.setCurseur(x, y);
            }
            public void mouseDragged(MouseEvent e) {}
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseExited(MouseEvent e) {
                be.setCurseur(-1, -1);
            }
            public void mouseReleased(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {}
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_Z:
                        if (e.isControlDown())
                            dmodele.ctrlZ();
                        break;
                }
            }
            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
        });
        setFocusable(true);
        requestFocus();
    }

    /**
     * lie les MouseListeneur a la vue
     * @param ff createur de formes
     * @param mf manipulateur de formes
     * @param cr crayon
     * @param gm gomme
     */
    public void setListener(FabricantFigures ff, ManipulateurFormes mf, Crayon cr, Gomme gm) {
        this.ff = ff;
        this.mf = mf;
        this.cr = cr;
        this.gm = gm;
    }

    @Override
    public void update(Observable dm, Object arg) {
        //dmodele = (DessinModele) dm;
        repaint();
    }

    /**
     * dessine les figures et les traits
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dmodele != null) {
            dmodele.paintFigures(g);
            dmodele.paintTrait(g);
        }
    }

    /**
     * active le créateur de formes
     */
    public void activeCreationFigure() {
        desactiveCreationFigure();
        addMouseListener(ff);
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }

    /**
     * désactive le créateur de formes
     */
    public void desactiveCreationFigure() {
        removeMouseListener(ff);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * active le manipulateur de formes
     */
    public void activeManipulationsSouris() {
        desactiveManipulationsSouris();
        addMouseListener(mf);
        addMouseMotionListener(mf);
        addKeyListener(mf);
    }

    /**
     * désactive le manipulateur de formes
     */
    public void desactiveManipulationsSouris() {
        removeMouseListener(mf);
        removeMouseMotionListener(mf);
        removeKeyListener(mf);
    }

    /**
     * active le crayon
     */
    public void activeCrayon() {
        desactiveCrayon();
        addMouseListener(cr);
        addMouseMotionListener(cr);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/crayon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setCursor(getToolkit().createCustomCursor(img, new java.awt.Point(2,28), "Crayon"));
    }

    /**
     * désactive le crayon
     */
    public void desactiveCrayon() {
        removeMouseListener(cr);
        removeMouseMotionListener(cr);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


    /**
     * active la gomme
     */
    public void activeGomme() {
        desactiveGomme();
        addMouseListener(gm);
        addMouseMotionListener(gm);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/gomme.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }        Cursor curseurGomme = getToolkit().createCustomCursor(img, new java.awt.Point(7,24), "Gomme");
        setCursor(curseurGomme);
    }

    /**
     * désactive la gomme
     */
    public void desactiveGomme() {
        removeMouseListener(gm);
        removeMouseMotionListener(gm);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * lie la vue au modèle du dessin
     * @param dm modèle du dessin
     */
    public void liee(DessinModele dm) {
        if (dmodele != null)
            dmodele.deleteObservers();

        dmodele = dm;
        dm.addObserver(this);
        repaint();
    }

}
