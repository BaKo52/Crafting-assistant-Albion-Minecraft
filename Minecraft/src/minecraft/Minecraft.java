/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecraft;

import java.io.FileNotFoundException;
import java.io.IOException;

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
            System.err.println("Problème de scanner aka path");
            System.err.println(ex.getMessage());
        } catch (NullPointerException ex) {
            System.err.println("Saisie incorrecte");
            System.err.println(ex.getMessage());
        } catch (IOException ex){
            System.err.println("IO");
            System.err.println(ex.getClass());
            System.err.println(ex.getMessage());
        }
    }
}
