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
    
    public static Map<String, Integer> trouve(String id) throws FileNotFoundException{
        Map<String, Integer> map = cost(search(id));
        
        return map;
    }
    
    private static Map<String, Integer> cost(Path p){
        //les deux HashMaps
        HashMap<String, Integer> mapResult = new HashMap<>(); //association entre ressources et coût
        HashMap<String, String> mapKey = new HashMap<>(); //association entre symbole et ressources
        
                
        Scanner monScanner = null;
        boolean hasKey = false;
        
        try {
            monScanner = new Scanner(new File(p.toString()));
        } catch (FileNotFoundException ex) {
            System.err.println("SCANNER OU FICHIER");
        }
            
        while (monScanner.hasNextLine()) 
        {
            String data = monScanner.nextLine();
            
            if(hasKey){
                pattern = Pattern.compile("\".\"");
                matcher = pattern.matcher(data);
                
                if(matcher.find()){
                    System.out.println(matcher.group());
                }
                
            }
            
            if (data.contains("key")) {
                hasKey = true;
            }
        }
        
        return mapResult;
    }
    
    private static Path search(String id) throws FileNotFoundException{
        Path res = null;
        List<Path> paths = listFiles(p);
        Scanner monScanner = null;
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
    
}
