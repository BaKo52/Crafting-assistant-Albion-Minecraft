/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modélisation;

/**
 *
 * @author tomhu
 */
public class Modélisation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Inventaire inv = new Inventaire();
        
        for (int i = 2; i < 9; i++) {
            System.out.println("Entrez votre nombre de ressources raffinées de niveau " + i + " :");
            switch(i){
                case 2:
                    inv.setrF2(Clavier.lireInt());
                    break;
                case 3:
                    inv.setrF3(Clavier.lireInt());
                    break;
                case 4:
                    inv.setrF4(Clavier.lireInt());
                    break;
                case 5:
                    inv.setrF5(Clavier.lireInt());
                    break;
                case 6:
                    inv.setrF6(Clavier.lireInt());
                    break;
                case 7:
                    inv.setrF7(Clavier.lireInt());
                    break;
                case 8:
                    inv.setrF8(Clavier.lireInt());
                    break;
            }
        }
        
        Gestion g = new Gestion(inv);
        
        g.boucle();
        
        g.toString();
    }
    
}
