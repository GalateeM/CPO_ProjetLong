package n7simulator.controller.startmenu;

import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import n7simulator.N7Simulator;
import n7simulator.database.GestionBddSauvegarde;
import n7simulator.modele.Partie;

/**
 * Classe représentant le formulaire de nouvelle partie
 */
public class NouvellePartieFormulaire extends JPanel {

	/**
	 * Champ texte permettant de rentrer le nom de la partie
	 */
	JTextField fieldNomPartie;

	/**
	 * Obtenir le formulaire de nouvelle partie
	 */
	public NouvellePartieFormulaire(NouvellePartieBouton ancetre) {
		this.setLayout(new java.awt.GridLayout(2, 1, 5, 5));

		JLabel messageLabel = new JLabel("Nom de la partie");

		// Saisie du nom de la partie
		this.fieldNomPartie = new JTextField(10);

		// Ajouter des composants
		this.add(messageLabel);
		this.add(fieldNomPartie);

		// Afficher la boite de dialogue
		boolean isValidInput = false;
		while (!isValidInput) {
			//creation de la fenetre de dialogue
			int result = JOptionPane.showConfirmDialog(null, this, "Nouvelle Partie", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);

			if (result == JOptionPane.OK_OPTION) { 
				if (testerNomValide()) {
					//initialisation de la partie
					isValidInput = true;
					Window win = SwingUtilities.getWindowAncestor(ancetre.getParent());
					win.dispose();
					Partie partie = Partie.getInstance();
					partie.initNomPartie(this.fieldNomPartie.getText().trim());
					N7Simulator.sauvegarderPartie();
					N7Simulator.initNouvellePartie();
				}
			} else {
				isValidInput = true;
			}
		}
	}

	/**
	 * Nom de la partie saisi valide ? (non vide, et n'existe pas deja)
	 * @return : si le nom est valide 
	 */
	private boolean testerNomValide() {
		String nomPartie = this.fieldNomPartie.getText().trim();
		String messageErreur = null;
		
		if (nomPartie.isEmpty()) {
			messageErreur = "Le nom de la partie ne peut pas être vide.";
		} else if (!GestionBddSauvegarde.nomPartieDisponible(nomPartie)) {
			messageErreur = "La partie " + nomPartie + " existe déjà !";
		}
		
		if (messageErreur != null) {		
			JOptionPane.showMessageDialog(this, messageErreur, "Erreur", JOptionPane.ERROR_MESSAGE);
			return false;
		}		
		return true;
	}
}
