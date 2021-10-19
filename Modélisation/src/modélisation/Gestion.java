/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modélisation;

/**
 * Classe qui gère l'inventaire
 * @author tomhu
 */
public class Gestion {
    
    private Inventaire inventaireSuivant; // Gestion qui précède l'inventaire actuel
    private Inventaire inventaireActuel; // Gestion qui suit l'inventaire actuel
    private Inventaire total; //inventaire qui stocke les ressources brutes final
   
    /*
    Constructeur de gestion
    */
    public Gestion(Inventaire invActuel){ 
        this.inventaireActuel = invActuel;
        this.inventaireSuivant = new Inventaire();
        this.total = new Inventaire();
    }
    /*
    Méthode qui permet le décraft d'objets
    */
    private void decraft(){
        inventaireActuel.decraft();
    }
    
    /*
    Méthode qui échange les deux inventaires
    L'actuel devient précédent et on copie le précédent pour créer un nouvel actuel
    */
    private void echange(){
        inventaireSuivant = inventaireActuel;
    }
    
    /*
    Méthode qui boucle decraft et echange tant qu'il y a des ressources raffinées
    */
    public void boucle(){
        decraft();
        clean();
        echange();
    }

    /**
     * Méthode "nettoyant" les ressources déjà compté et les stockent
     */
    private void clean() {
        
    }
}
