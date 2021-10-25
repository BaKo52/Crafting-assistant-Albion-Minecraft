/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecraft;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bkott
 */
public class Minecraft {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        try {
            System.out.println(Searcher.trouve("minecraft:coal_block"));
        } catch (FileNotFoundException ex) {
            System.err.println("OSKOUR");
        }
    }
}
