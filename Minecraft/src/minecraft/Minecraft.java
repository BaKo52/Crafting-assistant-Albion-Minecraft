/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecraft;

import java.io.FileNotFoundException;

/**
 *
 * @author bkott
 */
public class Minecraft {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        System.out.println("Entrez l'id de l'objet :");
        try {
            System.out.println(Searcher.trouve(Clavier.lireString()));
        } catch (FileNotFoundException ex) {
            System.err.println("OSKOUR");
        }
    }
}
