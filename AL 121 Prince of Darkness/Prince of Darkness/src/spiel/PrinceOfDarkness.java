package spiel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicProgressBarUI;

import karte.Held;
import karte.Karte;

/**
  * GUI zum Spiel Prince of Darkness.
  *
  * Diese Klasse erstellt ein Spielfenster, in dem zwei Helden gegeneinander kämpfen.
  * Die Lebenspunkte werden mit Fortschrittsbalken angezeigt.
  * Über einen Button wird jede neue Kampfrunde gestartet.
  *
  * @version 0.1 vom 24.10.2012
  * @author Tenbusch
  */
public class PrinceOfDarkness extends JFrame {
  // Anfang Attribute
  private int runde = 1;
  private int lebenspunkteLinks, lebenspunkteRechts; //Lebenspunkte am Anfang eines Matches
  private JPanel pnl_Hintergrund = new JPanel(null, true); // Das ist der Hintergrund der GUI.
    private Karte karteLinks;
    private Karte karteRechts;
    private JPanel pnl_held1_dummy = new JPanel(null, true);
    private JPanel pnl_held2_dummy = new JPanel(null, true);
    private JProgressBar pgb_lebenLinks = new JProgressBar();
    private JProgressBar pgb_lebenRechts = new JProgressBar();
    private JButton btn_next = new JButton();
    private JLabel lbl_gewinner = new JLabel();
    private JLabel lbl_lebensverlustLinks = new JLabel();
    private JLabel lbl_lebensverlustRechts = new JLabel();
  // Ende Attribute
 
  /**
   * Konstruktor der GUI.
   * Er erstellt das Fenster, richtet alle Komponenten ein
   * und zeigt die beiden Heldenkarten an.
   */
 
  public PrinceOfDarkness(String title, Held heldLinks, Held heldRechts) {
    // Frame-Initialisierung
    super(title);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
   // Größe des Fensters festlegen.
    int frameWidth = 1024; 
    int frameHeight = 768;
    setSize(frameWidth, frameHeight);
    // Bildschirmgröße holen, damit das Fenster mittig angezeigt werden kann.
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    // Fenster darf nicht vergrößert oder verkleinert werden.
    setResizable(false);
   // ContentPane holen und kein Layout verwenden, damit Positionen manuell gesetzt werden.
    Container cp = getContentPane();
    cp.setLayout(null);
    karteLinks = new Karte(heldLinks);
    karteRechts= new Karte(heldRechts);
    // Anfang Komponenten
   
    // Hintergrundpanel über die ganze Spielfläche setzen und schwarz färben.
    pnl_Hintergrund.setBounds(0, 0, 1024, 745);
    pnl_Hintergrund.setBackground(Color.BLACK);

    cp.add(pnl_Hintergrund);

   // Unsichtbarer Platzhalter für den linken Heldenbereich.
    pnl_held1_dummy.setBounds(10, 10, 270, 500);
    pnl_held1_dummy.setVisible(false);
    pnl_Hintergrund.add(pnl_held1_dummy);
   // Unsichtbarer Platzhalter für den rechten Heldenbereich.
    pnl_held2_dummy.setBounds(740, 10, 270, 500);
    pnl_held2_dummy.setVisible(false);
    pnl_Hintergrund.add(pnl_held2_dummy);
   // Lebensbalken für den linken Helden einrichten.
    pgb_lebenLinks.setBounds(288, 10, 210, 16);
    pgb_lebenLinks.setBackground(Color.WHITE);
    pgb_lebenLinks.setMaximum(heldLinks.getMaxLeben());
    pgb_lebenLinks.setValue(heldLinks.getAktLeben());
    pgb_lebenLinks.setForeground(Color.RED);
    pgb_lebenLinks.setStringPainted(true);
    pgb_lebenLinks.setString(heldLinks.getAktLeben() + "");
   // Farben im linken Lebensbalken anpassen.
    pgb_lebenLinks.setUI(new BasicProgressBarUI(){
      protected Color getSelectionBackground() { return Color.RED; }
      protected Color getSelectionForeground() { return Color.WHITE; }
    });
    pnl_Hintergrund.add(pgb_lebenLinks);
    pgb_lebenRechts.setBounds(520, 10, 210, 16);
    pgb_lebenRechts.setBackground(Color.RED);
    pgb_lebenRechts.setMaximum(heldRechts.getMaxLeben());
    pgb_lebenRechts.setValue(0);
    pgb_lebenRechts.setForeground(Color.WHITE);
    pgb_lebenRechts.setStringPainted(true);
    pgb_lebenRechts.setString(heldRechts.getAktLeben() + "");
    pgb_lebenRechts.setUI(new BasicProgressBarUI(){
      protected Color getSelectionBackground() { return Color.WHITE; }
      protected Color getSelectionForeground() { return Color.RED; }
    });
    pnl_Hintergrund.add(pgb_lebenRechts);
    btn_next.setBounds(392, 224, 251, 89);
    btn_next.setText(runde + ". Runde");
    btn_next.setMargin(new Insets(2, 2, 2, 2));
    btn_next.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btn_next_ActionPerformed(evt);
      }
    });
    btn_next.setBorder(BorderFactory.createEtchedBorder(0, Color.BLUE, Color.WHITE));
    btn_next.setBackground(Color.BLUE);
    btn_next.setForeground(Color.YELLOW);
    btn_next.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
    pnl_Hintergrund.add(btn_next);
    lbl_gewinner.setBounds(392, 144, 251, 73);
    lbl_gewinner.setText("");
    lbl_gewinner.setBackground(Color.BLACK);
    lbl_gewinner.setOpaque(true);
    lbl_gewinner.setForeground(Color.RED);
    lbl_gewinner.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
    lbl_gewinner.setHorizontalAlignment(SwingConstants.CENTER);
    pnl_Hintergrund.add(lbl_gewinner);
    lbl_lebensverlustLinks.setBounds(288, 32, 110, 20);
    lbl_lebensverlustLinks.setText("");
    lbl_lebensverlustLinks.setBackground(Color.BLACK);
    lbl_lebensverlustLinks.setOpaque(true);
    lbl_lebensverlustLinks.setForeground(Color.RED);
    lbl_lebensverlustLinks.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
    pnl_Hintergrund.add(lbl_lebensverlustLinks);
    lbl_lebensverlustRechts.setBounds(620, 32, 110, 20);
    lbl_lebensverlustRechts.setText("");
    lbl_lebensverlustRechts.setBackground(Color.BLACK);
    lbl_lebensverlustRechts.setOpaque(true);
    lbl_lebensverlustRechts.setForeground(Color.RED);
    lbl_lebensverlustRechts.setHorizontalAlignment(SwingConstants.RIGHT);
    lbl_lebensverlustRechts.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
    pnl_Hintergrund.add(lbl_lebensverlustRechts);
    // Ende Komponenten
    karteLinks.setBounds(10, 10, 260, 500);
    karteRechts.setBounds(750, 10, 260, 500);
    pnl_Hintergrund.add(karteLinks);
    pnl_Hintergrund.add(karteRechts);
    lebenspunkteLinks = karteLinks.getHeld().getAktLeben();
    lebenspunkteRechts= karteRechts.getHeld().getAktLeben();
    setVisible(true);
  }

  // Anfang Methoden
  public void btn_next_ActionPerformed(ActionEvent evt) {
    Held l = karteLinks.getHeld();
    Held r = karteRechts.getHeld();
    
    if(btn_next.getText().equals("Reset")){
      btn_next.setText("1. Runde");
      lebenspunkteLinks = l.getMaxLeben();
      lebenspunkteRechts= r.getMaxLeben();
      l.setAktLeben(l.getMaxLeben());
      r.setAktLeben(r.getAktLeben());
      pgb_lebenLinks.setValue(lebenspunkteLinks);
      pgb_lebenRechts.setValue(0);
      pgb_lebenLinks.setString(lebenspunkteLinks + "");
      pgb_lebenRechts.setString(lebenspunkteRechts + "");
      lbl_gewinner.setText("");
      lbl_lebensverlustLinks.setText("");
      lbl_lebensverlustRechts.setText("");
    }else{
      //Schaden f�r linken Held berechnen
      int schadenLinks = l.getAktLeben();
      l.leiden(r.angreifen());
      schadenLinks -= l.getAktLeben();
      
      //Schaden f�r rechten Held berechnen
      int schadenRechts = r.getAktLeben();
      r.leiden(l.angreifen());
      schadenRechts -= r.getAktLeben();
      
      //Schaden anzeigen
      lbl_lebensverlustLinks.setText(-1 * schadenLinks + "");
      lbl_lebensverlustRechts.setText(-1 * schadenRechts + "");
      lebenspunkteLinks -= schadenLinks;
      lebenspunkteRechts -= schadenRechts;
      pgb_lebenLinks.setValue(lebenspunkteLinks);
      pgb_lebenRechts.setValue(pgb_lebenRechts.getMaximum()-lebenspunkteRechts);
      pgb_lebenLinks.setString("(" + lebenspunkteLinks + " / " + l.getMaxLeben() + ")");
      pgb_lebenRechts.setString("(" + lebenspunkteRechts + " / " + r.getMaxLeben() + ")");

      //pr�fen ob ein Held besiegt wurde
      if (lebenspunkteLinks <= 0 && lebenspunkteRechts <= 0){
        btn_next.setText("Reset");
        runde = 0;
        lbl_gewinner.setText("Unentschieden!");
        pgb_lebenLinks.setString("0");
        pgb_lebenRechts.setString("0");
      }else if (lebenspunkteLinks <= 0){
        btn_next.setText("Reset");
        runde = 0;
        lbl_gewinner.setText(r.getName() + " gewinnt!");
        pgb_lebenLinks.setString("0");
      }else if (lebenspunkteRechts <= 0){
        btn_next.setText("Reset");
        runde = 0;
        lbl_gewinner.setText(l.getName() + " gewinnt!");
        pgb_lebenRechts.setString("0");
      }else{
        btn_next.setText(++runde + ". Runde");
      }
    }
  }

  // Ende Methoden
}
