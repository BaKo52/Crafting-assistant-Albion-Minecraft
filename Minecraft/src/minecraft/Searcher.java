/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.regex.*;

/**
 *
 * @author bkott
 */
public final class Searcher {
    private static final String str = "src\\Minecraft\\recipes";
    private static final Path p = Paths.get(str);
    
    private static Map<String, Integer> mapSurplus = new HashMap<>();
    private static Map<String, Integer> mapIngredientsParent = new HashMap<>();
    private static Map<String, Integer> mapIngredientsFils = new HashMap<>();
    
    //regex car fml
    private static Pattern pattern;
    private static Matcher matcher;
    
    private static Scanner monScanner = null;
    
    private static String data = "";
    
    public static Map<String, Integer> trouve(String id) throws NullPointerException, FileNotFoundException, IOException{
        mapIngredientsParent = ingredients(search(id));
        
        Path chemin;
        int nbItemCree; //compte de ressource données par craft
        int nbItemRequis; //nombre de ressource requise
        int nbCraftRequis; //nombre de fois ou le craft devra être fait
        String entryString;
        
        while (estDecraftable(mapIngredientsParent)) {
            Object listeKey[] = mapIngredientsParent.keySet().toArray();
            
            
            for (Object entryObject : listeKey) {
                entryString = entryObject.toString();
                chemin = search(entryString);

                if(chemin != null){
                    nbItemCree = count(chemin);
                    mapIngredientsFils = ingredients(search(entryString));

                    nbItemRequis = mapIngredientsParent.get(entryObject.toString());

                    if((nbItemRequis % nbItemCree) == 0){
                        nbCraftRequis = nbItemRequis / nbItemCree;
                    }else{
                        float nbItemRequisF = nbItemRequis;
                        float nbItemCreeF = nbItemCree;
                        
                        nbCraftRequis = BigDecimal.valueOf(nbItemRequisF / nbItemCreeF).setScale(0, RoundingMode.UP).intValue();
                    }                           
                    
                    mapSurplus.put(entryString, nbItemCree * nbCraftRequis - nbItemRequis);
                    
                    //n'enlevez pas cette ligne sinon boucle infinie
                    mapIngredientsParent.remove(entryString);
                    
                    for (String filsString : mapIngredientsFils.keySet()) {
                        //Si on décommente la ligne suivante on peut avoir le bon résultat pour l'épée et houe en bois mais NullPointerException pour la quasi-totalité des autres crafts
                        //checkSurplus(entryString, filsString, nbItemRequis, nbCraftRequis);
                        mapIngredientsParent.put(filsString, mapIngredientsParent.getOrDefault(filsString, 0) + nbCraftRequis * mapIngredientsFils.get(filsString));
                    }
                }
            }
        }
        
        return mapIngredientsParent;
    }
    
    private static boolean checkSurplus(String idToCheck, String idToCraft, int nbItemRequis, int nbCraftRequis){
        boolean res;
        
        if(mapSurplus.containsKey(idToCheck)){
            if(mapSurplus.get(idToCheck) >= nbItemRequis){
                res = true;
                if((mapSurplus.get(idToCheck) - nbItemRequis) == 0){
                    mapSurplus.remove(idToCheck);
                    mapIngredientsParent.remove(idToCraft);
                }else{
                    mapSurplus.put(idToCheck, mapSurplus.get(idToCheck) - nbItemRequis);
                    mapIngredientsParent.remove(idToCraft);
                }
            }else{
                mapSurplus.put(idToCheck, mapSurplus.get(idToCheck) - nbItemRequis);
                mapIngredientsParent.replace(idToCraft, mapIngredientsParent.get(idToCraft) - mapSurplus.get(idToCheck));
                res = false;
            }
        }else{
            res = false;
        }
        
        return res;
    }
    
    private static boolean estDecraftable(Map<String, Integer> map) throws FileNotFoundException{
        boolean bool = false;
        String id = "";
        
        for (Object o : map.keySet()) {
            id = o.toString();
            
            if(search(id) != null){
                bool = true;
            }
        }
        
        return bool;
    }
    
    private static int count(Path p) throws NullPointerException, FileNotFoundException, IOException{
        int count = 1;
        boolean hasCount = false;
        
        monScanner = new Scanner(p);
        
        
        while (monScanner.hasNextLine()) {            
            if(hasCount){
                pattern = Pattern.compile("(\\d)");
                matcher = pattern.matcher(data);
                matcher.find();
                
                data = matcher.group();
                
                count = Integer.parseInt(data);
                
                data = monScanner.nextLine();
                
                hasCount = false;
            }else if(data.contains("count")){
                hasCount = true;
            }else{
                data = monScanner.nextLine();
            }
        }
        
        
        return count;
    }
    
    private static Map<String, Integer> ingredients(Path p) throws NullPointerException, FileNotFoundException{
        //les deux HashMaps
        HashMap<String, Integer> mapResult = new HashMap<>(); //association entre ressources et coût
        HashMap<String, String> mapKey = new HashMap<>(); //association entre symbole et ressources
        
        String chara = ""; 
        String minecraftId = "";
        
        boolean hasKey = false;
        boolean hasIngredients = false;
        
        HashMap<String, Integer> count = new HashMap<>();
        
        monScanner = new Scanner(new File(p.toString()));
        
        data = monScanner.nextLine();
            
        while (monScanner.hasNextLine()) 
        {        
            if(hasKey){
                while (!data.equals("  },")) {                    
                    //on set le pattern pour prendre le char
                    pattern = Pattern.compile("\".\"");
                    matcher = pattern.matcher(data);
                    matcher.find();

                    chara = matcher.group().substring(1, 2);
                    data = monScanner.nextLine();

                    //on set le pattern pour prendre l'id de minecraft
                    pattern = Pattern.compile("minecraft:[[\\w][_]]*");
                    matcher = pattern.matcher(data);
                    matcher.find();

                    minecraftId = matcher.group();

                    monScanner.nextLine();
                    data = monScanner.nextLine();
                    
                    mapKey.put(chara, minecraftId);
                }
                //System.out.println(mapKey);
                
                hasKey = false;
            }else if (data.contains("key")) {
                hasKey = true;
                data = monScanner.nextLine();
            }else if(data.contains("ingredients")){
                hasIngredients = true;
                data = monScanner.nextLine();
            }else{
                data = monScanner.nextLine();
            }
        }
        mapResult = analysePattern(mapKey, p, hasIngredients);
        
        return mapResult;
    }
    
    private static Path search(String id) throws FileNotFoundException{
        Path res = null;
        List<Path> paths = listFiles(p);
        boolean hasResult = false;
        File myObj = null;
        
        for (Path path : paths) {
            //System.out.println("On examine :\n      " + path);
            myObj = new File(path.toString());
            
            monScanner = new Scanner(myObj);
            
            while (monScanner.hasNextLine()) 
            {
                data = monScanner.nextLine();
                if(hasResult){
                    // if(data.contains(id)){
                    //     res = path;
                    // }
                    
                    pattern = Pattern.compile(id + "\"");
                    matcher = pattern.matcher(data);
                    
                    if(matcher.find()){
                        res = path;
                    }                    
                    
                    hasResult = false;
                }

                if(data.contains("result"))
                {
                    hasResult = true;
                }
            }
        }
        
        return res;
    }

    private static List<Path> listFiles(Path get) {
        List<Path> result = null;
        
        try
        {
            result = Files.walk(p)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } 
        catch (IOException ex) 
        {
            System.err.println("WALK");
        }
        
        return result;
    }

    private static HashMap<String, Integer> analysePattern(HashMap<String, String> mapKey, Path p, boolean isShapeless) {
        HashMap<String, Integer> result = new HashMap<>();
        HashMap<String, Integer> nbChar = new HashMap<>();
        
        try {
            monScanner = new Scanner(p);
        } catch (IOException ex) {
            System.err.println("SCANNER analysePattern");
        }
        
        if(isShapeless){
            //logique pour recette shapeless
            
            boolean hasIngredients = false; 
            String tempStr = "";
            
            while (monScanner.hasNextLine()){
                if(hasIngredients){
                    data = monScanner.nextLine();
                    pattern = Pattern.compile("minecraft:(.)*");
                    
                    while(!data.contains("]") && monScanner.hasNextLine()){
                        data = monScanner.nextLine();
                        matcher = pattern.matcher(data);
                        if(matcher.find()){
                            tempStr = matcher.group();
                            
                            tempStr = tempStr.substring(0, tempStr.length()-1);
                        
                            result.put(tempStr, result.getOrDefault(tempStr, 0) + 1);
                        }  
                    }                  
                    
                    hasIngredients = false;
                }else if(data.contains("ingredients")){
                    hasIngredients = true;
                }else{
                    data = monScanner.nextLine();
                }
            }
        }else{
            //logique pour shaped
            
            boolean hasPattern = false;
            
            while (monScanner.hasNextLine()) {
                if(hasPattern){
                    String l1 = "";
                    String l2 = "";
                    String l3 = "";
                    
                    //on set le pattern pour prendre le char
                    pattern = Pattern.compile("\"(.)*\"");
                    
                    //on trouve la première ligne
                    matcher = pattern.matcher(data);
                    matcher.find();
                    data = matcher.group();
                    data = data.substring(1, data.length()-1);
                    l1 = data.trim();
                    
                    
                    //lecture de la ligne suivante
                    data = monScanner.nextLine();
                    
                    if(!data.contains("]")){
                        //on trouve la seconde ligne si elle existe
                        matcher = pattern.matcher(data);
                        matcher.find();
                        data = matcher.group();
                        data = data.substring(1, data.length()-1);
                        l2 = data.trim();

                        //ligne suivante
                        data = monScanner.nextLine();

                        if(!data.contains("]")){
                            //on trouve la troisième ligne si elle existe
                            matcher = pattern.matcher(data);
                            matcher.find();
                            data = matcher.group();
                            data = data.substring(1, data.length()-1);
                            l3 = data.trim();
                        }
                    }
                    
                    String lFinal = l1;
                    
                    if(!l2.isEmpty()){
                        lFinal = lFinal.concat(l2);
                    }
                    
                    if(!l3.isEmpty()){
                        lFinal = lFinal.concat(l3);
                    }
                    
                    String c;
                    
                    for (int i = 0; i < lFinal.length(); i++) {
                        c = String.valueOf(lFinal.charAt(i));
                        
                        if(!c.contains(" ")){
                            nbChar.put(c, nbChar.getOrDefault(c, 0) + 1);
                        }
                    }
                    
                    for (String character : nbChar.keySet()) {
                        result.put(mapKey.get(character), nbChar.get(character));
                    }
                    
                    hasPattern = false;
                }else if(data.contains("pattern")){
                    hasPattern = true;
                    data = monScanner.nextLine();
                }else{
                    data = monScanner.nextLine();
                }
            }
        }
        
        return result;
    }
    
}
