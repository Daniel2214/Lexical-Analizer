/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Daniel Jimenez <danieljimenez2214 at gmail.com>
 */
public class Token {
    
    private String id;
    private String value;
    private int lineNum;
    private int startChar;

    public Token(String id, String value, int lineNum, int startChar) {
        this.id = id;
        this.value = value;
        this.lineNum = lineNum;
        this.startChar = startChar;
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public int getStartChar() {
        return startChar;
    }

    public void setStartChar(int startChar) {
        this.startChar = startChar;
    }


}
