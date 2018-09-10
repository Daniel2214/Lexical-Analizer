

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Daniel Jimenez <danieljimenez2214 at gmail.com>
 */
public class SyntacticAnalizer {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        demo tokensTable = new demo();
        
        InputStream inputStream = new FileInputStream("C:/Users/dan_1/Desktop/code.txt");
        Reader inputStreamReader = new InputStreamReader(inputStream);

        ArrayList<Token> tokenTable = demo.analize(inputStreamReader);
        
        
        System.out.println("___________________________________________________________________");
        System.out.println("");
        System.out.println("| TOKEN TABLE |");
        System.out.println("");
        for(int i=0; i<tokenTable.size(); i++){
            
            System.out.println("| ID: " + tokenTable.get(i).getId()+ " | " + "Value: " + tokenTable.get(i).getValue()+" | Line: "+tokenTable.get(i).getLineNum() + " | Inline position:" + tokenTable.get(i).getStartChar());
        }
        
        
        System.out.println("");
        System.out.println("__________________________________");
        System.out.println("Now the Syntactic Analizer");
        
        analizar(tokenTable);
        

    }
    
    
    public static void analizar(ArrayList<Token> tokensTable){
    
        int line;
        boolean checkLine = false;
        for(int i=0; i<tokensTable.size(); i++){
        
           line = tokensTable.get(i).getLineNum();
           System.out.println();
           System.out.println(line);
           
           if(checkLine == false){
           
               checkLine = true; 
               System.out.println("");
               System.out.println("TERM");
               System.out.println(tokensTable.get(i).getValue());
               if(tokensTable.get(i).getId() == "PALABRA RESERVADA"){
                   
                   ArrayList<Token> intermediario = new ArrayList();

                   System.out.println("Value is: " + tokensTable.get(i).getValue());
                   
                   if(tokensTable.get(i).getValue()== "principal"){
                       
                       System.out.println("paps");
                   }else if (tokensTable.get(i).getValue().equals("entero") || tokensTable.get(i).getValue().equals("real") || tokensTable.get(i).getValue().equals("logico")){
                       
                       int size = 0;
                      
                       while(tokensTable.get(i).getLineNum() == line){
                           intermediario.add(tokensTable.get(i));
                           size++;
                           i++;
                       }
                       i--;
                       System.out.println("i is: " + i);
                       System.out.println("Size is: " + size);
                       
                       if(size<=3){
                           System.out.println("Podria ser una asignacion");
                           checkLine = false;
                       }else{
                           System.out.println("Podria ser una funcion");
                           isFuncion(intermediario);
                           checkLine = false;
                       }
                       
                   
                   }
                   
                   System.out.println("GEEDGFDSFGFS");
               }else if (tokensTable.get(i).getId() == "IDENTIFICADOR"){
                   checkLine = false;
               }else{
               
                   checkLine = false;
               }
           }
           
           checkLine = false;
        }
    
    }

    private static boolean isFuncion(ArrayList<Token> intermediario) {
        
      
        
        if(intermediario.get(1).getId().equals("IDENTIFICADOR") && intermediario.get(2).getValue().equals("(")){
            int j = 3;
            ArrayList params = new ArrayList();
            while(!intermediario.get(j).getValue().equals(")")){
                
                //System.out.println(intermediario.get(j).getId());
                
                params.add(intermediario.get(j).getId());
              
                j++;

            }
            System.out.println(intermediario.get(j).getValue());
            if((isParams(params) == true) && (intermediario.get(j).getValue().equals(")")) && (intermediario.get(j+1).getValue().equals("{"))){
                System.out.println("Header de la funcion correcto");
            }
            
        }     
       
        
        
        return false;
    }
    
    private static boolean isParams(ArrayList params){
        
        int comas = 0, terms = 0;
        
        boolean tipoDeDatoConVariable = false;
        
        
        for(int i=0; i< params.size(); i++){
            System.out.println((params.get(i)));
            if(i%3 == 0){
                
                System.out.println(params.get(i));
                if(params.get(i).equals("PALABRA RESERVADA")){
                    tipoDeDatoConVariable = true;
                    terms++;
                }else{
                  return  false;
                }
            }else if(i%3 == 1){
                 
                if(params.get(i).equals("IDENTIFICADOR")){
                    tipoDeDatoConVariable = false;                
                }else{
                    return false;
                }
            }else if(i%3 == 2){
                //System.out.println("Coma");
                comas++;
            }
            
            
            
        
        }
        if((terms-1 != comas) || (tipoDeDatoConVariable == true)){
            return false;
        }
        //System.out.println("Total terms: " + terms);
        //System.out.println("Total comas: " + comas);
        
        return true;
    }

}
