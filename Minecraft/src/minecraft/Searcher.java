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
import java.util.stream.Collectors;
import java.util.regex.*;

/**
 *
 * @author bkott
 */
public final class Searcher {
    private static String str = "src\\Minecraft\\recipes";
    private static Path p = Paths.get(str);
    
    //regex car fml
    private static Pattern pattern;
    private static Matcher matcher;
    
    private static Scanner monScanner = null;
    
    public static Map<String, Integer> trouve(String id) throws FileNotFoundException{
        Map<String, Integer> map = cost(search(id));
        
        return map;
    }
    
    private static Map<String, Integer> cost(Path p){
        //les deux HashMaps
        HashMap<String, Integer> mapResult = new HashMap<>(); //association entre ressources et coût
        HashMap<String, String> mapKey = new HashMap<>(); //association entre symbole et ressources
        
        String data = "";
        String chara = ""; 
        String minecraftId = "";
        
        
        boolean hasKey = false;
        
        try {
            monScanner = new Scanner(new File(p.toString()));
        } catch (FileNotFoundException ex) {
            System.err.println("SCANNER OU FICHIER");
        }
        
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
                System.out.println(mapKey);
                
                mapResult = analysePattern(mapKey, p);
                
                hasKey = false;
            }else if (data.contains("key") || data.contains("ingredients")) {
                hasKey = true;
                data = monScanner.nextLine();
            }else{
                data = monScanner.nextLine();
            }
        }
        
        return mapResult;
    }
    
    private static Path search(String id) throws FileNotFoundException{
        Path res = null;
        List<Path> paths = listFiles(p);
        boolean hasResult = false;
        
        for (Path path : paths) {
            System.out.println("On examine :\n      " + path);
            File myObj = new File(path.toString());
            
            monScanner = new Scanner(myObj);
            
            while (monScanner.hasNextLine()) 
            {
                String data = monScanner.nextLine();
                if(hasResult){
                    if(data.contains(id)){
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

    private static HashMap<String, Integer> analysePattern(HashMap<String, String> mapKey, Path p) {
        HashMap<String, Integer> result = new HashMap<>();
        
        
        
        return result;
    }
    
}
