package n7simulator.controller.consommable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Bouton permettant l'ouverture de la vue de gestion des consommables
 */
public class BoutonGestionConsommables extends JButton {
	
	
	public BoutonGestionConsommables() {
		this.setText("Modifier");
        this.addActionListener(new ActionOuvrirModification());
	}
	
	class ActionOuvrirModification implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new ConsommableFoyController();
		}
		
	}

}