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
        //update des ressources brutes
        inventaireSuivant.setrB8(inventaireActuel.getrB8() + ( 6 * inventaireActuel.getrF8() ));
        inventaireSuivant.setrB7(inventaireActuel.getrB7() + ( 5 * inventaireActuel.getrF7() ));
        inventaireSuivant.setrB6(inventaireActuel.getrB6() + ( 4 * inventaireActuel.getrF6() ));
        inventaireSuivant.setrB5(inventaireActuel.getrB5() + ( 3 * inventaireActuel.getrF5() ));
        inventaireSuivant.setrB4(inventaireActuel.getrB4() + ( 2 * inventaireActuel.getrF4() ));
        inventaireSuivant.setrB3(inventaireActuel.getrB3() + ( 2 * inventaireActuel.getrF3() ));
        inventaireSuivant.setrB2(inventaireActuel.getrB2() + inventaireActuel.getrF2());
        
        //update des ressources raffinées
        inventaireSuivant.setrF8(inventaireActuel.getrF8() - inventaireSuivant.getrB8()/6);
        inventaireSuivant.setrF7(inventaireActuel.getrF7() - inventaireSuivant.getrB7()/5 + inventaireSuivant.getrB8()/6);
        inventaireSuivant.setrF6(inventaireActuel.getrF6() - inventaireSuivant.getrB6()/4 + inventaireSuivant.getrB7()/5);
        inventaireSuivant.setrF5(inventaireActuel.getrF5() - inventaireSuivant.getrB5()/3 + inventaireSuivant.getrB6()/4);
        inventaireSuivant.setrF4(inventaireActuel.getrF4() - inventaireSuivant.getrB4()/2 + inventaireSuivant.getrB5()/3);
        inventaireSuivant.setrF3(inventaireActuel.getrF3() - inventaireSuivant.getrB3()/2 + inventaireSuivant.getrB4()/2);
        inventaireSuivant.setrF2(inventaireActuel.getrF2() - inventaireSuivant.getrB2() + inventaireSuivant.getrB3()/2);
    }
    
    /*
    Méthode qui boucle decraft et echange tant qu'il y a des ressources raffinées
    */
    public void boucle(){
        decraft();
        clean();
        echange();
        
        while ((inventaireSuivant.getrF2() 
                + inventaireSuivant.getrF3() 
                + inventaireSuivant.getrF4() 
                + inventaireSuivant.getrF5() 
                + inventaireSuivant.getrF6() 
                + inventaireSuivant.getrF7()
                + inventaireSuivant.getrF8()) != 0)
        {            
            decraft();
            clean();
            echange();
        }
    }

    /**
     * Méthode "nettoyant" les ressources déjà compté et les stockent
     */
    private void clean() {
        //on update notre total
        total.setrB2(inventaireSuivant.getrB2() + total.getrB2());
        total.setrB3(inventaireSuivant.getrB3() + total.getrB3());
        total.setrB4(inventaireSuivant.getrB4() + total.getrB4());
        total.setrB5(inventaireSuivant.getrB5() + total.getrB5());
        total.setrB6(inventaireSuivant.getrB6() + total.getrB6());
        total.setrB7(inventaireSuivant.getrB7() + total.getrB7());
        total.setrB8(inventaireSuivant.getrB8() + total.getrB8());
        
        //on delete les ressources brutes de notre inventaire suivant
        inventaireSuivant.setrB2(0);
        inventaireSuivant.setrB3(0);
        inventaireSuivant.setrB4(0);
        inventaireSuivant.setrB5(0);
        inventaireSuivant.setrB6(0);
        inventaireSuivant.setrB7(0);
        inventaireSuivant.setrB8(0);
    }

    /*
    Méthode qui échange les deux inventaires
    L'actuel devient précédent et on copie le précédent pour créer un nouvel actuel
    */
    private void echange() {
        Inventaire temp;
        
        temp = inventaireActuel;
        inventaireActuel = inventaireSuivant;
        inventaireSuivant = temp;
    }
    /**
     * Override de la méthode toString()
     */
    @Override
    public String toString(){
        String str = "";
        
        System.out.println("Vous avez besoin de " + total.getrB2() + " ressources brutes de niveau 2.\n");
        System.out.println("Vous avez besoin de " + total.getrB3() + " ressources brutes de niveau 3.\n");
        System.out.println("Vous avez besoin de " + total.getrB4() + " ressources brutes de niveau 4.\n");
        System.out.println("Vous avez besoin de " + total.getrB5() + " ressources brutes de niveau 5.\n");
        System.out.println("Vous avez besoin de " + total.getrB6() + " ressources brutes de niveau 6.\n");
        System.out.println("Vous avez besoin de " + total.getrB7() + " ressources brutes de niveau 7.\n");
        System.out.println("Vous avez besoin de " + total.getrB8() + " ressources brutes de niveau 8.\n");
        
        return str;
    }
}
