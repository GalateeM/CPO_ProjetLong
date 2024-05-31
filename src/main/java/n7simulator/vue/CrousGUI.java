/**
 * 
 */
package n7simulator.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 */
public class CrousGUI extends JPanel implements Observer {
	
	private Integer qualite;
	
	private Double prixRevente;
	
	private JLabel labelQualite;
	
	private JLabel labelRevente;

	/**
	 * 
	 */
	public CrousGUI() {
		qualite = 1;
		prixRevente = 2.0;

		this.setLayout(new GridLayout(2,1));
		

		this.add(new JLabel("CROUS : "));
		
		JPanel contenu = new JPanel();
		
		JPanel informations = new JPanel(new GridLayout(2,1));
		
		JPanel qualiteRepas = new JPanel(new FlowLayout());
		qualiteRepas.add(new JLabel("Qualité du repas : "));
		labelQualite = new JLabel(qualite.toString());
		qualiteRepas.add(labelQualite);
		informations.add(qualiteRepas, BorderLayout.WEST);
		
		JPanel panelRevente = new JPanel();
		panelRevente.add(new JLabel("Prix de revente du repas : "));
		labelRevente = new JLabel(prixRevente.toString());
		panelRevente.add(labelRevente);
		informations.add(panelRevente);

		contenu.setAlignmentX(LEFT_ALIGNMENT);
		contenu.setBackground(Color.red);
		
		contenu.add(informations);

		this.add(contenu);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
}
