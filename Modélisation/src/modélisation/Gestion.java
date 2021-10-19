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
    
    private Inventaire inventairePrecedent; // Gestion qui précède l'inventaire actuel
    private Inventaire inventaireActuel; // Gestion qui suit l'inventaire actuel
    private Inventaire total; //inventaire qui stocke les ressources brutes final
   
    /*
    Constructeur d'inventaire
    */
    public Gestion(Inventaire invPrecedent, Inventaire invActuel){ 
        this.inventairePrecedent = invPrecedent;
        this.inventaireActuel = invActuel;
        this.total = new Inventaire();
    }
    /*
    Méthode qui permet le décraft d'objets
    */
    public void decraft(){
        //decraft rf
        
        //update rb
        
        //delete rb déjà comptées
    }
    
    /*
    Méthode qui échange les deux inventaires
    L'actuel devient précédent et on copie le précédent pour créer un nouvel actuel
    */
    public void echange(){
        
    }
    
    /*
    Méthode qui boucle decraft et echange tant qu'il y a des ressources raffinées
    */
    public void boucle(){
        
    }
}
