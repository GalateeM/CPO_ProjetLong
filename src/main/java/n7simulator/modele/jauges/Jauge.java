package n7simulator.modele.jauges;

import java.util.Observable;


/**
 * Cette classe définit la structure générale des jauges
 * pour notre simulateur de l'N7; la valeur d'une jauge ne peut etre
 * inférieure à zéro.
 */
public class Jauge extends Observable{
    /**
     * Le nom de la jauge.
     */
    private String nom;

	/**
     * la valeur entière de la jauge.
     */
    private double valeur;

    /**
     * Initialiser une jauge avec une valeur initialie.
     * La valeur initiale est mise à 0 la valeur passée est inférieure à 0
     * @param nom le nom descriptif de la jauge
     * @param initValue la valeur initiale de la jauge.
     */
    public Jauge(String nom, int initValue){
        this.nom = nom;
        valeur = initValue < 0 ? 0 : initValue;
    }

    /**
     * Initialiser une jauge avec pour valeur initiale 0
     * @param nom le nom descriptif de la jauge.
     */ 
    public Jauge(String nom){
        this(nom,0);
    }
    /**
     * Obtenir la valeur de la jauge.
     * @return la valeur de la jauge
     */
    public double getValue(){
        return valeur;
    }
    /**
     * obtenir le nom de la jauge.
     * @return le nom de la jauge
     */
    public String getNom(){
        return this.nom;
    }
    /**
     * augmenter ou reduire la valeur de la jauge suivant
     * le signe de la valeur en paramètre: La jauge repasse à zéro si après déduction
     * elle est inférieure à zéro.
     * Leve une exception si la valeur atteint 0.
     * @param valeur la valeur a ajouter à la jauge
     */
    public void ajouter(double valeur) {
        this.valeur += this.valeur + valeur > 0 ? valeur : -this.valeur;
        setChanged();
        notifyObservers(this.valeur);
        if(this.valeur == 0) {
        	throw new ValeurNulleException(this);
        }
    }

    @Override
    public String toString(){
        return this.getNom() + " : " + getValue();
    }
    
    /**
     * Permet de reinitialiser les jauges en debut de partie
     */
    public void reinitialiserValeur(double valeurDebut) {
    	this.valeur = valeurDebut;
    }
}