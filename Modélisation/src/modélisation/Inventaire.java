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
public class Inventaire {
    
    private Inventaire inventairePrécédent; // Inventaire qui précède l'inventaire actuel
    private Inventaire inventaireSuivant; // Inventaire qui suit l'inventaire actuel
   
    /*
    Constructeur d'inventaire
    */
    public Inventaire(){
        inventairePrécédent = new Inventaire();
        
    }
    /*
    Méthode qui permet le décraft d'objets
    */
    public void decraft(){
        
    }
}
