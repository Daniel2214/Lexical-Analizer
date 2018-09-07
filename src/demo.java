
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

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
        InputStream inputStream = new FileInputStream("C:/Users/dan_1/Desktop/code.txt");
        Reader inputStreamReader = new InputStreamReader(inputStream);

        analize(inputStreamReader);
    }

    public static void analize(Reader inputStreamReader) throws IOException {

        int data = inputStreamReader.read();
        String[][] tokens;
        boolean tokenError, singlePoint;
        int point;
        String state;

        while (data != -1) {
            System.out.println("root: " + data);
            char ch = (char) data;
            System.out.println("New term");
            System.out.println(ch);
            tokenError = false;
            point = 0;
            singlePoint = false;
            state = "CORRECT";
            

            if (Character.isDigit(ch)) {
                String num = "";
                num += ch;
                
                while (Character.isWhitespace(ch) != true) {
                    data = inputStreamReader.read();
                    if (data == -1) {
                        break;
                    }
                    ch = (char) data;
                    System.out.println("data: " + ch);
                    if(Character.isWhitespace(ch) == false){
                        if ((Character.isDigit(ch) == true || (ch == ".".charAt(0)))) {
                            
                            if((ch == ".".charAt(0))&& singlePoint == true){
                                
                                while (Character.isWhitespace(ch) == false) {
                                    state = "ERROR";
                                    System.out.println(state);
                                    data = inputStreamReader.read();
                                    ch = (char) data;
                                }
                                tokenError = true;
                                break;
                                
                            }
                            
                            point = 0;
                            if((ch == ".".charAt(0))){
                                point = 1;
                                singlePoint = true;
                            }
                            System.out.println("in" + ch);
                            num += ch;
                            tokenError=false;
                        } else {
                            
                            while (Character.isWhitespace(ch) == false) {
                                state = "ERROR";
                                System.out.println(state);
                                data = inputStreamReader.read();
                                ch = (char) data;
                            }
                            tokenError = true;
                            break;
                            
                        }
                    }
                }
                
                if(point!=0){
                    state= "ERROR";
                }
                
                
                System.out.println("State:" + state);
                if((tokenError == false) && (point == 0)){
                        System.out.println("NUMBER:" + num);
                        System.out.println("");
                        System.out.println("");

                }

            }

            data = inputStreamReader.read();
        }

    }

}
