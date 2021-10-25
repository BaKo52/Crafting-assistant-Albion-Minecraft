/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
* Classe servant à lire les entrées clavier de l'utilisateur.
* @author Franck Marzani
* @version 1.1
*/
public class Clavier
{ 
    /**
    * Fonction servant à lire une chaine de charactère.
    * @return Renvoi la chaine qu'a entrée l'utilisateur.
    */
    public static String lireString ()   // lecture d'une chaine
    { 
    String ligne_lue = null ;
    try
    { InputStreamReader lecteur = new InputStreamReader (System.in) ;
      BufferedReader entree = new BufferedReader (lecteur) ;
      ligne_lue = entree.readLine() ;
    }
    catch (IOException err)
    { System.exit(0) ;
    }
    return ligne_lue ;
  }
    
    /**
    * Fonction servant à lire un float.
    * @return Renvoi le float qu'a entré l'utilisateur.
    */
  public static float lireFloat ()   // lecture d'un float
  { float x=0 ;   // valeur a lire
    try
    { String ligne_lue = lireString() ;
      x = Float.parseFloat(ligne_lue) ;
    }
    catch (NumberFormatException err)
    { System.out.println ("*** Erreur de donnee ***") ;
      System.exit(0) ;
    }
    return x ;
  }
    /**
    * Fonction servant à lire un double.
    * @return Renvoi le double qu'a entré l'utilisateur.
    */
  public static double lireDouble ()   // lecture d'un double
  { double x=0 ;   // valeur a lire
    try
    { String ligne_lue = lireString() ;
      x = Double.parseDouble(ligne_lue) ;
    }
    catch (NumberFormatException err)
    { System.out.println ("*** Erreur de donnee ***") ;
      System.exit(0) ;
    }
    return x ;
  }
  
    /**
    * Fonction servant à lire un entier. 
    * @return Renvoi l'entier qu'a entré l'utilisateur.
    */
  public static int lireInt ()         // lecture d'un int
  { int n=0 ;   // valeur a lire
    try
    { String ligne_lue = lireString() ;
      n = Integer.parseInt(ligne_lue) ;
      }
    catch (NumberFormatException err)
    { System.out.println ("*** Erreur de donnee ***") ;
      System.exit(0) ;
    }
    return n ;
  }
}
