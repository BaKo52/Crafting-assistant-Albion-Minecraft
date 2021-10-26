/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.regex.*;

/**
 *
 * @author bkott
 */
public final class Searcher {
    private static final String str = "src\\Minecraft\\recipes";
    private static final Path p = Paths.get(str);
    
    //regex car fml
    private static Pattern pattern;
    private static Matcher matcher;
    
    private static Scanner monScanner = null;
    
    private static String data = "";
    
    public static Map<String, Integer> trouve(String id) throws NullPointerException, FileNotFoundException, IOException{
        Map<String, Integer> map = ingredients(search(id));
        Path p;
        int count; //compte de ressource données par craft
        int nbRequis; //nombre de ressource requise
        String entry;
        
        Object listeKey[] = map.keySet().toArray();
        
        for (Object entree : listeKey) {
            entry = entree.toString();
            
            p = search(entry);
            
            if(!p.equals(null)){
                Map<String, Integer> temp = ingredients(search(entry));
                
                for (String ancetre : temp.keySet()) {
                    count = count(p);
                
                    nbRequis = map.get(entry) / count;
                    if((map.get(entry) % count) != 0){
                        nbRequis++;
                    }

                    map.put(ancetre, nbRequis * count);
                }
                
                map.remove(entry);
            }
        }
        
        return map;
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
            
            while (monScanner.hasNextLine()){
                
                
                if(hasIngredients){
                    data = monScanner.nextLine();
                    pattern = Pattern.compile("minecraft:(.)*");
                    
                    while(!data.contains("]")){
                        data = monScanner.nextLine();
                        matcher = pattern.matcher(data);
                        if(matcher.find()){
                            data = matcher.group();
                            
                            data = data.substring(0, data.length()-1);
                        
                            result.put(data, result.getOrDefault(data, 0) + 1);

                            data = monScanner.nextLine();

                            data = monScanner.nextLine();
                        }  
                    }                  
                    
                    hasIngredients = false;
                }else if(data.contains("ingredients")){
                    hasIngredients = true;
                    data = monScanner.nextLine();
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
