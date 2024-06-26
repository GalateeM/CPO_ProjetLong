package n7simulator.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import n7simulator.modele.Bibliotheque;
import n7simulator.modele.Partie;
import n7simulator.modele.Temps;
import n7simulator.modele.jauges.Jauge;
import n7simulator.modele.professeur.GestionProfesseurs;
import n7simulator.modele.professeur.Professeur;

/**
 * Classe permettant d'accéder aux données concernant les valeurs de début de
 * partie dans la base de données
 */
public class ValDebPartieDAO {
	
	private ValDebPartieDAO() {}

	public static void initialiserPartieSauvegardee() {
		initialiserDonneesDebutPartie();
		Connection connexionDB = null;

		try {
			// connexion à la base de données
			connexionDB = DatabaseConnection.getDBConnexion();
		} catch (SQLException e) {
			System.err
					.println("Erreur lors de la récupération des données de début de partie dans la base de données.");
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnection.closeDBConnexion(connexionDB);
			} catch (Exception e) {
				System.err.println("Erreur lors de la fermeture de la connexion");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Récupère les valeurs en base de données et set les données de la partie.
	 */
	public static void initialiserDonneesDebutPartie() {

		Partie partie = Partie.getInstance();
		Temps temps = partie.getTemps();
		Connection connexionDB = null;

		try {
			// connexion à la base de données
			connexionDB = DatabaseConnection.getDBConnexion();

			// requête à la base de données
			String query = "SELECT * FROM ValDebPartie";
			ResultSet resultDB = DatabaseConnection.effectuerRequete(query, connexionDB);

			// parcours du résultat pour instancier la partie
			while (resultDB.next()) {
				partie.getGestionEleves().inscrireEleves(resultDB.getInt("NbEleve"));
				Jauge jaugeArgent = partie.getJaugeArgent();
				jaugeArgent.reinitialiserValeur(resultDB.getInt("Argent"));
				
				Jauge jaugeBonheur = partie.getJaugeBonheur();
				jaugeBonheur.reinitialiserValeur(resultDB.getInt("Bonheur"));
				
				Jauge jaugePedagogie = partie.getJaugePedagogie();
				jaugePedagogie.reinitialiserValeur(resultDB.getInt("Pedagogie"));

				// transformation de la date
				String dateString = resultDB.getString("dateDeb");
				temps.setJourneeEnCours(LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				
			}
			
			initialiserProfesseurs();
			partie.getFoy().setConsommablesListe(ConsommableFoyDAO.getAllConsommableFoy());
			Bibliotheque.getInstance().setNbLivre(0);
			Partie.setEstPerdue(false);

		} catch (SQLException e) {
			System.err
					.println("Erreur lors de la récupération des données de début de partie dans la base de données.");
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnection.closeDBConnexion(connexionDB);
			} catch (Exception e) {
				System.err.println("Erreur lors de la fermeture de la connexion");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Reinitialise la liste des professeurs embauches pour le debut de la partie
	 */
	private static void initialiserProfesseurs() {
		//reinitialiser les professeurs
		Partie partie = Partie.getInstance();
		GestionProfesseurs gestionProfs = partie.getGestionProfesseurs();
		List<Professeur> profsNonEmbauches = ProfesseurDAO.getAllProfesseurs();
		gestionProfs.initialiserListeProfesseurs(new ArrayList<Professeur>(), profsNonEmbauches);
	}

}