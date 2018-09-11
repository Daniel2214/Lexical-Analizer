

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
        boolean mainAppears = false;
        boolean declarationExist = false;
        boolean declarationError = false;
        for(int i=0; i<tokensTable.size(); i++){
        
           line = tokensTable.get(i).getLineNum();
           System.out.println();
           System.out.println(line);
           
           
           
          
            System.out.println("");
            System.out.println("TERM");
            System.out.println(tokensTable.get(i).getValue());
            if(tokensTable.get(i).getId() == "PALABRA RESERVADA"){

                ArrayList<Token> intermediario = new ArrayList();
                ArrayList<Token> posDeclaraciones = new ArrayList();

                System.out.println("Value is: " + tokensTable.get(i).getValue());

                if(tokensTable.get(i).getValue().equals("principal")){

                    if(mainAppears == false ){
                        mainAppears = true;
                    }

                    System.out.println("paps");
                }else if (tokensTable.get(i).getValue().equals("entero") || tokensTable.get(i).getValue().equals("real") || tokensTable.get(i).getValue().equals("logico")){

                    int size = 0;
 
                    while(tokensTable.get(i).getLineNum() == line){
                        intermediario.add(tokensTable.get(i));
                        size++;
                        i++;
                    }
                    //i--;
                    System.out.println("i is: " + i);
                    System.out.println("Size is: " + size);

                    if(size<=3){
                        System.out.println("Podria ser una declaracion");
                        
                    }else{
                        System.out.println("Podria ser una funcion");
                        if(isFuncion(intermediario)){

                            if(mainAppears == false){
                                System.out.println("todo chill con la funcion");
                                System.out.println(" i is" + i);
                                
                                //Revisar declaraciones
                                while (tokensTable.get(i).getValue().equals("entero") || tokensTable.get(i).getValue().equals("real") || tokensTable.get(i).getValue().equals("logico")){
                                    line ++;
                                    while(tokensTable.get(i).getLineNum() == line){
                                        posDeclaraciones.add(tokensTable.get(i));
                                        i++;
                                    }
                                     if(isDeclaracion(posDeclaraciones)){
                                         System.out.println("Declaración correcta");
                                         declarationExist = true;
                                     }else{
                                         System.out.println("Error en declaracion en la linea " + line + ", en el caracter " + (i-1) );
                                     }
                                     posDeclaraciones.clear();
                                }
                               
                                
                                System.out.println(" i al terminar declaracion es " + i);
                                
                                
                                //Revisar asignaciones
                                while (tokensTable.get(i).getId().equals("IDENTIFICADOR")){
                                    if(declarationExist == false){
                                        declarationError = true;
                                        System.out.println("Error: asignacion antes de declarar en la linea " + line + ", en el caracter " + (i-1));
                                        while(tokensTable.get(i).getLineNum() == line){
                                            System.out.println("popo ");
                                            i++;
                                        }
                                        i--;
                                    }else{
                                        line ++;
                                        System.out.println("HADOUKEN!!!");
                                        while(tokensTable.get(i).getLineNum() == line){
                                            System.out.println("papaya " + tokensTable.get(i).getValue());
                                            i++;
                                        }
                                        
                                    } 
                                }
                                if(declarationError == false){
                                    i--;
                                }
                                
                                
                                
                                
                               
                            }else{
                                System.out.println("Error, function is declared after main. Line: " + line + ", Character: " + i);
                            }
                        }
                    }
                }

                System.out.println("GEEDGFDSFGFS");
            }else if (tokensTable.get(i).getId() == "IDENTIFICADOR"){
               
            }else{

                
            }
            declarationExist = false;
            declarationError = false;
        }
    }

    private static boolean isFuncion(ArrayList<Token> intermediario) {
        
      
        
        if(intermediario.get(1).getId().equals("IDENTIFICADOR") && intermediario.get(2).getValue().equals("(")){
            int j = 3;
            ArrayList params = new ArrayList();
            while(!intermediario.get(j).getValue().equals(")")){                
                params.add(intermediario.get(j).getId());
              
                j++;

            }
            
            
            if((isParams(params) == true) && (intermediario.get(j).getValue().equals(")")) && (intermediario.get(j+1).getValue().equals("{"))){
                System.out.println("Header de la funcion correcto");
                return true;
            }
            
        }     
       
        
        
        return false;
    }
    
    private static boolean isParams(ArrayList params){
        
        int comas = 0, terms = 0;
        
        boolean tipoDeDatoConVariable = false;
        
        if (params.isEmpty()){
            return true;
        }
        
        
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

    private static void isAsignacion(ArrayList<Token> tokensTable, int line, int i) {
        

    }

    private static boolean isDeclaracion(ArrayList<Token> posDeclaraciones) {
        System.out.println("Hello");
        
        if(posDeclaraciones.size()>=1){
            System.out.println("Size: " + posDeclaraciones.size());
            if(posDeclaraciones.get(0).getValue().equals("entero") || posDeclaraciones.get(0).getValue().equals("real")  || posDeclaraciones.get(0).getValue().equals("logico")){
                if(posDeclaraciones.get(1).getId().equals("IDENTIFICADOR")){
                    return true;
                }
            }
        }
        return false;
    }

}
