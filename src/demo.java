
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
public class demo {

    public static void main(String[] args) throws FileNotFoundException, IOException {
//        InputStream inputStream = new FileInputStream("C:/Users/dan_1/Desktop/code.txt");
//        Reader inputStreamReader = new InputStreamReader(inputStream);
//
//        ArrayList<Token> tokenTable = analize(inputStreamReader);
//        
//        
//        System.out.println("___________________________________________________________________");
//        System.out.println("");
//        System.out.println("| TOKEN TABLE |");
//        System.out.println("");
//        for(int i=0; i<tokenTable.size(); i++){
//            
//            System.out.println("| ID: " + tokenTable.get(i).getId()+ " | " + "Value: " + tokenTable.get(i).getValue()+" | Line: "+tokenTable.get(i).getLineNum() + " | Inline position:" + tokenTable.get(i).getStartChar());
//        }
//        
    }

    public static ArrayList<Token> analize(Reader inputStreamReader) throws IOException {

        int data = inputStreamReader.read();
        String[][] tokens;
        boolean tokenError, singlePoint;
        int point, tokenPosition = 0, line = 1;
        String state;
        Token token;
        ArrayList<Token> tokensTable = new ArrayList<Token>();
        int position = 0;
        String id  = "";
        char newLineFlag;
        
        while (data != -1) {
            //System.out.println("INICIAL: " + data);
            char ch = (char) data;
            newLineFlag = ch;
            System.out.println("¡¡¡¡NEW TERM!!!!");
            
            if(Character.isWhitespace(ch) == false){
                tokenPosition++;
            }
            
            System.out.println("INITIAL: " + ch);
            tokenError = false;
            point = 0;
            singlePoint = false;
            state = "CORRECT";
            
            
            //_____________When character is Digit______________________________
            
            if (Character.isDigit(ch)) {
                String num = "";
                num += ch;
                id = "ENTERO";
                int errorAt=0;
                
                while (Character.isWhitespace(ch) == false) {
                    data = inputStreamReader.read();
                    position++;
                    if (data == -1) {
                        break;
                    }
                    ch = (char) data;
                    //System.out.println("data: " + ch);
                    if(Character.isWhitespace(ch) == false){
                        if ((Character.isDigit(ch) == true || (ch == ".".charAt(0)))) {
                            
                            
                            if((ch == ".".charAt(0))&& singlePoint == true){
                                id = "REAL";
                                errorAt = position+1;
                                while (Character.isWhitespace(ch) == false) {
                                    //state = "ERROR";
                                    id = "ENTERO";
                                    System.out.println(state);
                                    //System.out.println(id);
                                    data = inputStreamReader.read();
                                    position ++ ;
                                    ch = (char) data;
                                    System.out.println("Error at character: " + errorAt);
                                    if(data == -1){break;}
                                    
                                }
                                tokenError = true;
                                break;
                                
                            }
                            
                            point = 0;
                            if((ch == ".".charAt(0))){
                                id = "REAL";
                                point = 1;
                                singlePoint = true;
                            }
                            //System.out.println("in" + ch);
                            num += ch;
                            tokenError=false;
                        } else {
                            errorAt = position+1;
                            System.out.println("Error at character: " + errorAt);
                            while (Character.isWhitespace(ch) == false) {
                                state = "ERROR";
                                id = "ENTERO";
                                System.out.println(state);
                                //System.out.println(id);
                                data = inputStreamReader.read();
                                position++;
                                ch = (char) data;
                                if(data == -1){break;}
                                
                            }
                            tokenError = true;
                            break;
                            
                        }
                    }
                }
                
                if(point!=0){
                    state= "ERROR";
                    id="ENTERO";
                    System.out.println(state);
                    System.out.println("Error at character: " + errorAt);
                }
                
                
               
                if((tokenError == false) && (point == 0)){
                        
                        System.out.println(id+": " + num);
                        System.out.println("");
                        System.out.println("");
                        System.out.println("EL ID ES: " + id);
                        
                        token = new Token(id, num, line, tokenPosition);
                        tokensTable.add(token);

                }

            }
            //__If character starts with a letter_______________________________
            else if(Character.isLetter(ch)){
                String word = "";
                word += ch;
                id = "IDENTIFICADOR";
                boolean isPalabraRes =false;
                boolean error = false;
                int errorPosition = 0;
                
                while(Character.isWhitespace(ch)==false){
                    data = inputStreamReader.read();
                    position ++;
                    if(data == -1){break;}
                    ch = (char)data;
                    if(Character.isWhitespace(ch) == false){
                        if(Character.isLetter(ch) || Character.isDigit(ch)){
                            word += ch;
                            if(Character.isDigit(ch)==false){
                                isPalabraRes = isPalabraReservada(word);
                                System.out.println(isPalabraRes);
                            }
                            error = false;
                        }else{

                            errorPosition = position+1;
                            while (Character.isWhitespace(ch) == false) {

                                    data = inputStreamReader.read();
                                    position++;
                                    ch = (char) data;
                                    System.out.println(data);
                                    if(data == -1){break;}
                            }

                            error = true;
                            break;
                        }
                    }
                    
                    if(isPalabraRes){
                        id = "PALABRA RESERVADA";
                        
                    }
                    
                    
                    System.out.println(error);
                
                }
                
                if(error == false){
                    token = new Token(id, word, line, tokenPosition);
                    tokensTable.add(token);
                }else{
                    System.out.println("Error at character " + errorPosition );
                }
            }
            
            //_____________________Signo de puntuacion__________________________
            else if (isSignosDePuntuacion(ch)){
                
                
                String word = "";
                word += ch;
                
//                while(Character.isWhitespace(ch)==false){
//                    data = inputStreamReader.read();
//                    position ++;
//                    if(data == -1){break;}
//                    ch = (char)data;
//                   
//                    word += ch;
//                    id = "SIGNO";
//                   
//
//                }
                
                id = "SIGNO";
                
                token = new Token(id, word, line, tokenPosition);
                tokensTable.add(token);
            }
            
            
            //_____________Operador_____________________________________________
            
            else if (isOperador(ch)){
                
                id = "Operador ";
                String extra = "";
                String word = "";
                word += ch;
                
                extra = tipoDeOperador(word);
                
                while(Character.isWhitespace(ch)==false){
                    data = inputStreamReader.read();
                    position++;
                    if(data == -1){break;}
                    ch = (char) data;
                    
                    if(Character.isWhitespace(ch) == false){
                        word+=ch;
                        System.out.println(word);
                        extra = tipoDeOperador(word);
                        System.out.println("Yolo" + extra);
                    }
                }
                
                id += extra;
                
                System.out.println(id);
                
                token = new Token(id, word, line, tokenPosition);
                tokensTable.add(token);
            }
          
            
            
            
            
            
            
            //______________Empieza con punto___________________________________
            if(ch == ".".charAt(0)){
                
                while(Character.isWhitespace(ch)==false){
                    data = inputStreamReader.read();
                    position ++;
                    if(data == -1){break;}
                    ch = (char)data;
                }
          
            }

            
            data = inputStreamReader.read();
            
            if(newLineFlag == ";".charAt(0) || newLineFlag == "{".charAt(0) || newLineFlag == "}".charAt(0)){
                line++;
                tokenPosition = 0;
            }
        }
        
        
        return tokensTable;
    }
    
    public static boolean isPalabraReservada(String word){
        
        switch(word){
                case "principal": 
                    return true;
                case "entero":
                    return true;
                case "real":
                    return true;
                case "logico":
                    return true;
                case "si":
                    return true;
                case "mientras":
                    return true;
                case "regresa":
                    return true;
                case "verdadero":
                    return true;
                case "false":
                    return true;
        }
        return false;
    }
    
    public static boolean isSignosDePuntuacion(char word){
        
        if(word == "(".charAt(0))
           return true;
        else if(word == ")".charAt(0))
            return true;
        else if (word == ";".charAt(0))
            return true;
        else if (word == ",".charAt(0))
            return true;
        else if (word == "{".charAt(0))
            return true;
        else if (word == "}".charAt(0))
            return true;
               
        return false;
    }
    
    public static boolean isOperador(char word){
        
        if(word == "+".charAt(0))
            return true;
        else if(word == "-".charAt(0))
            return true;
        else if(word == "*".charAt(0))
            return true;
        else if(word == "/".charAt(0))
            return true;
        else if(word == "^".charAt(0))
            return true;
        else if(word == ">".charAt(0))
            return true;
        else if(word == "<".charAt(0))
            return true;
        else if(word == "=".charAt(0))
            return true;
        else if(word == "&".charAt(0))
            return true;
        else if(word == "|".charAt(0))
            return true;
        else if(word == "!".charAt(0))
            return true;
        return false;
    }
    
    public static String tipoDeOperador(String word){
        
        
    
        if(word.equals("+") || word.equals("-") || word.equals("*") || word.equals("/")){
            return "ARITMETICO";
        }
        else if(word.equals(">") || word.equals("<") || word.equals("==")){
            System.out.println("holi");
            return "RELACIONAL";
        }
        else if(word.equals("&") || word.equals("|") || word.equals("!")){
            return "LOGICO";
        }
        else if(word.equals("=")){
            return "ASIGNACION";
        }
        
        return "";
    
    }

}
