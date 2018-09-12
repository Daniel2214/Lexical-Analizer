

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Stack;

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
//           System.out.println();
//           System.out.println(line);
           
           
           
          
            System.out.println("");
            //System.out.println("TERM");
            //System.out.println(tokensTable.get(i).getValue());
            if(tokensTable.get(i).getId() == "PALABRA RESERVADA"){

                ArrayList<Token> intermediario = new ArrayList();
                ArrayList<Token> posDeclaraciones = new ArrayList();
                ArrayList<Token> posAsignacion = new ArrayList();

                System.out.println("Value is: " + tokensTable.get(i).getValue());

                if(tokensTable.get(i).getValue().equals("principal")){

                    if(mainAppears == false ){
                        mainAppears = true;
                        
                        int length = 0;
                        ArrayList<Token> intermediarioPrincipal = new ArrayList<Token>();
                        while(tokensTable.get(i).getLineNum() == line){
                            intermediarioPrincipal.add(tokensTable.get(i));
                            i++;
                            length++;
                        }
                        
                        
                        if(length==4){
                            if(isPrincipal(intermediarioPrincipal)){
                                System.out.println("Header de principal correcto");
                                
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
                                         System.out.println("Error en declaracion en la linea " + line + ", en el termino " + (i-1) );
                                    }
                                    posDeclaraciones.clear();
                                }
                               
                                
 //                               System.out.println(" i al terminar declaracion es " + i);
                                
                                
                                //Revisar asignaciones
                                while (tokensTable.get(i).getId().equals("IDENTIFICADOR")){
                                    if(declarationExist == false){
                                        declarationError = true;
                                        System.out.println("Error: asignacion antes de declarar en la linea " + line + ", en el termino " + (i-1));
                                        while(tokensTable.get(i).getLineNum() == line){
                                            i++;
                                        }
                                        i--;
                                    }else{
                                        line ++;
                                        //System.out.println("HADOUKEN!!!");
                                        while(tokensTable.get(i).getLineNum() == line){
                                            posAsignacion.add(tokensTable.get(i));
                                            i++;
                                        }
                                        
                                        if(isAsignacion(posAsignacion)){
                                            System.out.println("ASIGNACION!!!");
                                        }else{
                                            System.out.println("Error en linea " + line + ", termino " + i);
                                        }
                                        
                                    }
                                    posAsignacion.clear();
                                }
                                if(declarationError == false){
                                    i--;
                                }
                                
                                if(tokensTable.get(i+1).getValue().equals("regresa")){
                                   
                                        System.out.println("Error en linea " + line + ", return en principal");
                                    
                                    i++;
                                }
                                
                                
                            }else{
                                System.out.println("header de principal incorrecto linea " + line + ", caracter " + i);
                            }
                        }else{
                        
                        }

                        
                    }else{
                        System.out.println("Error en la linea " + line + ", principal ya se había declarado");
                    }
                    
                    

                    
                }else if (tokensTable.get(i).getValue().equals("entero") || tokensTable.get(i).getValue().equals("real") || tokensTable.get(i).getValue().equals("logico")){

                    int size = 0;
 
                    while(tokensTable.get(i).getLineNum() == line){
                        intermediario.add(tokensTable.get(i));
                        size++;
                        i++;
                    }
                    //i--;
//                    System.out.println("i is: " + i);
//                    System.out.println("Size is: " + size);

                    if(size<=3){
                        System.out.println("Podria ser una declaracion");
                        
                    }else{
                        System.out.println("Podria ser una funcion");
                        if(isFuncion(intermediario)){

                            if(mainAppears == false){
//                                System.out.println("todo chill con la funcion");
//                                System.out.println(" i is" + i);
//                                
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
                                         System.out.println("Error en declaracion en la linea " + line + ", en el termino " + (i-1) );
                                    }
                                    posDeclaraciones.clear();
                                }
                               
                                
 //                               System.out.println(" i al terminar declaracion es " + i);
                                
                                
                                //Revisar asignaciones
                                while (tokensTable.get(i).getId().equals("IDENTIFICADOR")){
                                    if(declarationExist == false){
                                        declarationError = true;
                                        System.out.println("Error: asignacion antes de declarar en la linea " + line + ", en el termino " + (i-1));
                                        while(tokensTable.get(i).getLineNum() == line){
                                            i++;
                                        }
                                        i--;
                                    }else{
                                        line ++;
                                        //System.out.println("HADOUKEN!!!");
                                        while(tokensTable.get(i).getLineNum() == line){
                                            posAsignacion.add(tokensTable.get(i));
                                            i++;
                                        }
                                        
                                        if(isAsignacion(posAsignacion)){
                                            System.out.println("ASIGNACION!!!");
                                        }else{
                                            System.out.println("Error en linea " + line + ", termino " + i);
                                        }
                                        
                                    }
                                    posAsignacion.clear();
                                }
                                if(declarationError == false){
                                    i--;
                                }
                                
                                if(tokensTable.get(i+1).getValue().equals("regresa")){
                                    if(!tokensTable.get(i+2).getId().equals("IDENTIFICADOR")){
                                        System.out.println("Error en linea " + line + ", return no regresa identificador");
                                    }
                                    i++;
                                }
                                
                                
                               
                            }else{
                                System.out.println("Error, function is declared after main. Line: " + line + ", Character: " + i);
                            }
                        }else{
                            System.out.println("No es correcta la declaración de la funcion en la linea " + line);
                        }
                    }
                }

                System.out.println("end");
                
            }else{

                //System.out.println("Error en la linea " + line + ", termino " + i);  
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
            //System.out.println((params.get(i)));
            if(i%3 == 0){
                
                //System.out.println(params.get(i));
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

    private static boolean isAsignacion(ArrayList<Token> posAsignaciones) {
       
        boolean parentesisAlreadyChecked = false;
        if(posAsignaciones.size()>=1){
            if(posAsignaciones.get(0).getId().equals("IDENTIFICADOR") && posAsignaciones.get(1).getValue().equals("=")){
                for(int i = 2; i<posAsignaciones.size(); i++){
                    if(!posAsignaciones.get(i).getValue().equals("=")){
                        if(posAsignaciones.get(i).getValue().equals("(") && parentesisAlreadyChecked == false){
                            parentesisAlreadyChecked = true;
                            if(parantesisBalanceados(posAsignaciones)){
                                System.out.println("parentesis balanceados");
                                if(goodOperators(posAsignaciones)){
                                    return true;
                                }
                                return false;
                            }else{
                                System.out.println("error en paréntesis");
                                return false;
                            }
                        }else{
                            if(goodOperators(posAsignaciones)){
                                return true;
                            }
                            return false;
                        }
                    }
                }
            }
        }
        
        return false;
    }

    private static boolean isDeclaracion(ArrayList<Token> posDeclaraciones) {
        //System.out.println("Hello");
        
        if(posDeclaraciones.size()>=1 && posDeclaraciones.size()==3){
            //System.out.println("Size: " + posDeclaraciones.size());
            if(posDeclaraciones.get(0).getValue().equals("entero") || posDeclaraciones.get(0).getValue().equals("real")  || posDeclaraciones.get(0).getValue().equals("logico")){
                if(posDeclaraciones.get(1).getId().equals("IDENTIFICADOR")){
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean parantesisBalanceados(ArrayList<Token> posAsignaciones){
         
        Stack<String> stack = new Stack<String>();
        
        for(int i = 2; i<posAsignaciones.size(); i++){
            if(posAsignaciones.get(i).getValue().equals("(")){
                stack.push( "(" );
            }else if (posAsignaciones.get(i).getValue().equals(")")){
                if(stack.isEmpty()){
                    return false;
                }
                stack.pop();
            }
        
        }
        
        if(stack.isEmpty()){
            return true;
        }

        return false;
    }

    private static boolean goodOperators(ArrayList<Token> posAsignaciones) {
        
        
        for(int i = 0; i<posAsignaciones.size(); i++){
            if(posAsignaciones.get(i).getValue().equals("+") || posAsignaciones.get(i).getValue().equals("-") || posAsignaciones.get(i).getValue().equals("*") || posAsignaciones.get(i).getValue().equals("/") ||
               posAsignaciones.get(i).getValue().equals("^") || posAsignaciones.get(i).getValue().equals(">") || posAsignaciones.get(i).getValue().equals("<") ||
               posAsignaciones.get(i).getValue().equals("&") || posAsignaciones.get(i).getValue().equals("|") || posAsignaciones.get(i).getValue().equals("==")){
//                System.out.println("el signo previo es es " +  posAsignaciones.get(i-1).getValue());
//               System.out.println("el signo que lee es " +  posAsignaciones.get(i).getValue());
//               System.out.println("el signo que sigue es " +  posAsignaciones.get(i+1).getValue());
               if((i+1)<posAsignaciones.size()){
                   if((posAsignaciones.get(i-1).getId().equals("IDENTIFICADOR") || posAsignaciones.get(i-1).getValue().equals(")") || posAsignaciones.get(i-1).getId().equals("REAL") || posAsignaciones.get(i-1).getId().equals("ENTERO")) && 
                      (posAsignaciones.get(i+1).getId().equals("IDENTIFICADOR") || posAsignaciones.get(i+1).getValue().equals("(") || posAsignaciones.get(i+1).getId().equals("REAL") || posAsignaciones.get(i+1).getId().equals("ENTERO"))){
                       //System.out.println("todo cool");
                   }else{
                       return false;
                   }
               }
            }
        }
        
        return true;
    }

    private static boolean isPrincipal(ArrayList<Token> intermediarioPrincipal) {
        
        if(intermediarioPrincipal.get(0).getValue().equals("principal")){
            if(intermediarioPrincipal.get(1).getValue().equals("(")){
               if(intermediarioPrincipal.get(2).getValue().equals(")")){
                    if(intermediarioPrincipal.get(3).getValue().equals("{")){
                        return true;
                    }
                }     
            }
        }
        
        return false;
    }

}
