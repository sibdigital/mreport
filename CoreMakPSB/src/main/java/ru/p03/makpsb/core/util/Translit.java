/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.makpsb.core.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author timofeevan
 */
public class Translit {
    private final Map<Character, String> letters = new HashMap<>();
    private final Map<String, String> rletters = new HashMap<>();
    
    private void init(){
        letters.put('А', "A");
        letters.put('Б', "B");
        letters.put('В', "V");
        letters.put('Г', "G");
        letters.put('Д', "D");
        letters.put('Е', "E");
        letters.put('Ё', "E");
        letters.put('Ж', "ZH");
        letters.put('З', "Z");
        letters.put('И', "I");
        letters.put('Й', "I");
        letters.put('К', "K");
        letters.put('Л', "L");
        letters.put('М', "M");
        letters.put('Н', "N");
        letters.put('О', "O");
        letters.put('П', "P");
        letters.put('Р', "R");
        letters.put('С', "S");
        letters.put('Т', "T");
        letters.put('У', "U");
        letters.put('Ф', "F");
        letters.put('Х', "H");
        letters.put('Ц', "C");
        letters.put('Ч', "CH");
        letters.put('Ш', "SH");
        letters.put('Щ', "SH");
        letters.put('Ъ', "");
        letters.put('Ы', "Y");
        letters.put('Ь', "");
        letters.put('Э', "E");
        letters.put('Ю', "U");
        letters.put('Я', "YA");
        letters.put('а', "a");
        letters.put('б', "b");
        letters.put('в', "v");
        letters.put('г', "g");
        letters.put('д', "d");
        letters.put('е', "e");
        letters.put('ё', "e");
        letters.put('ж', "zh");
        letters.put('з', "z");
        letters.put('и', "i");
        letters.put('й', "i");
        letters.put('к', "k");
        letters.put('л', "l");
        letters.put('м', "m");
        letters.put('н', "n");
        letters.put('о', "o");
        letters.put('п', "p");
        letters.put('р', "r");
        letters.put('с', "s");
        letters.put('т', "t");
        letters.put('у', "u");
        letters.put('ф', "f");
        letters.put('х', "h");
        letters.put('ц', "c");
        letters.put('ч', "ch");
        letters.put('ш', "sh");
        letters.put('щ', "sh");
        letters.put('ъ', "");
        letters.put('ы', "y");
        letters.put('ь', "");
        letters.put('э', "e");
        letters.put('ю', "u");
        letters.put('я', "ya");
        letters.put('1', "1");
        letters.put('2', "2");
        letters.put('3', "3");
        letters.put('4', "4");
        letters.put('5', "5");
        letters.put('6', "6");
        letters.put('7', "7");
        letters.put('8', "8");
        letters.put('9', "9");
        letters.put('0', "0"); 
        letters.put(' ', " "); 
        letters.put('.', "."); 
        letters.put('_', "_");
        for (Map.Entry<Character, String> entry : letters.entrySet()) {
            rletters.put(entry.getValue(), entry.getValue());
        }
    }
    
    private String translateOne(Character symbol, boolean onlyTranslated){
        String get = letters.get(symbol);
        if (get == null && rletters.get(symbol.toString()) != null){
            get = rletters.get(symbol.toString());
        }
        return get != null ? get : ( onlyTranslated == false ? symbol.toString() : "");
    }
    
    public String translate(String string){
        return translate(string, false);
    }
    
    public  String translate(String string, boolean onlyTranslated){
        String result = "";
        if (string != null){
            for (int i = 0; i < string.length(); i++) {
               result += translateOne(string.charAt(i), onlyTranslated);
            }
        }
        return result;
    }
    
    public  String translate(String string, boolean onlyTranslated, boolean replaceNotAllowedSymbols){
        if (replaceNotAllowedSymbols == true){
            return translate(replaceNotAllowedSymbols(string), onlyTranslated);
        }
        return translate(string, onlyTranslated);
    }
    
    public static String replaceNotAllowedSymbols(String... arguments) {
        String result = "";
        for (String s : arguments) {
            result += (s.replace(File.separator, "_").replace(":", "_").replace("/", "_") + "_");
        }
        return result;
    }
    
    private static Translit INSTANCE = null;

    public static Translit instance(){
        if (INSTANCE == null){
            INSTANCE = new Translit();
        }
        return INSTANCE;
    }
    
    private Translit(){
        init();
    }
}
