/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ramee
 */
package com.tokens;

import com.tokens.Syntax_Analyzer;
import java.awt.font.TextMeasurer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.tokens.Database_Connection;

public class Lexical_Analyzer
{
	private static ArrayList<ArrayList<String>> KW = new ArrayList<ArrayList<String>>();
	static 
        {
		
		KW.add(new ArrayList<String>(Arrays.asList("int", "DT")));
		KW.add(new ArrayList<String>(Arrays.asList("float", "DT")));
		KW.add(new ArrayList<String>(Arrays.asList("char", "DT")));
		KW.add(new ArrayList<String>(Arrays.asList("bool", "DT")));
		KW.add(new ArrayList<String>(Arrays.asList("String", "String")));
		KW.add(new ArrayList<String>(Arrays.asList("if", "if")));
		KW.add(new ArrayList<String>(Arrays.asList("else", "else")));
		KW.add(new ArrayList<String>(Arrays.asList("do", "do")));
		KW.add(new ArrayList<String>(Arrays.asList("while", "while")));
		KW.add(new ArrayList<String>(Arrays.asList("for", "for")));
		KW.add(new ArrayList<String>(Arrays.asList("break", "break")));
                KW.add(new ArrayList<String>(Arrays.asList("continue", "continue")));
		KW.add(new ArrayList<String>(Arrays.asList("interface", "interface")));
                KW.add(new ArrayList<String>(Arrays.asList("extends", "extends")));
		KW.add(new ArrayList<String>(Arrays.asList("class", "class")));
		KW.add(new ArrayList<String>(Arrays.asList("abstract", "abstract")));
		KW.add(new ArrayList<String>(Arrays.asList("static", "static")));
		KW.add(new ArrayList<String>(Arrays.asList("try", "try")));
		KW.add(new ArrayList<String>(Arrays.asList("catch", "catch")));
		KW.add(new ArrayList<String>(Arrays.asList("new", "new")));
		KW.add(new ArrayList<String>(Arrays.asList("this", "this")));
		KW.add(new ArrayList<String>(Arrays.asList("return", "return")));
		KW.add(new ArrayList<String>(Arrays.asList("void", "void")));
		KW.add(new ArrayList<String>(Arrays.asList("public", "public")));
		KW.add(new ArrayList<String>(Arrays.asList("private", "private")));
		KW.add(new ArrayList<String>(Arrays.asList("NULL","NULL")));
		KW.add(new ArrayList<String>(Arrays.asList("Null","NULL")));
		KW.add(new ArrayList<String>(Arrays.asList("null","NULL")));
		KW.add(new ArrayList<String>(Arrays.asList("main","main")));
                KW.add(new ArrayList<String>(Arrays.asList("args","args")));
                KW.add(new ArrayList<String>(Arrays.asList("mainclass","mainclass")));
                KW.add(new ArrayList<String>(Arrays.asList("package","package")));
                KW.add(new ArrayList<String>(Arrays.asList("import","import")));
                KW.add(new ArrayList<String>(Arrays.asList("implements","implements")));
                KW.add(new ArrayList<String>(Arrays.asList("finally","finally")));
	}
	
	private static ArrayList<ArrayList<String>> OP = new ArrayList<ArrayList<String>>();
	static 
        { 
                OP.add(new ArrayList<String>(Arrays.asList("++","inc_dec")));
		OP.add(new ArrayList<String>(Arrays.asList("--","inc_dec")));
		OP.add(new ArrayList<String>(Arrays.asList("*","MDM")));
		OP.add(new ArrayList<String>(Arrays.asList("/","MDM")));
		OP.add(new ArrayList<String>(Arrays.asList("%","MDM")));
		OP.add(new ArrayList<String>(Arrays.asList("+","PM")));
		OP.add(new ArrayList<String>(Arrays.asList("-","PM")));
		OP.add(new ArrayList<String>(Arrays.asList("<","ROP")));
		OP.add(new ArrayList<String>(Arrays.asList(">","ROP")));
		OP.add(new ArrayList<String>(Arrays.asList("<=","ROP")));
		OP.add(new ArrayList<String>(Arrays.asList(">=","ROP")));
		OP.add(new ArrayList<String>(Arrays.asList("==","ROP")));
		OP.add(new ArrayList<String>(Arrays.asList("!=","ROP")));
		OP.add(new ArrayList<String>(Arrays.asList("&&","AND")));
		OP.add(new ArrayList<String>(Arrays.asList("||","OR")));
		OP.add(new ArrayList<String>(Arrays.asList("=","=")));
		OP.add(new ArrayList<String>(Arrays.asList("+=","CAO")));
		OP.add(new ArrayList<String>(Arrays.asList("/=","CAO")));
		OP.add(new ArrayList<String>(Arrays.asList("-=","CAO")));
		OP.add(new ArrayList<String>(Arrays.asList("*=","CAO")));
		OP.add(new ArrayList<String>(Arrays.asList("%=","CAO")));
                OP.add(new ArrayList<String>(Arrays.asList("!","!")));
                OP.add(new ArrayList<String>(Arrays.asList("$","$")));
	}
	
	private ArrayList<ArrayList<String>> tokens = new ArrayList<ArrayList<String>>();
	
	public static void main(String[] args)throws Exception 
        {
		ArrayList<ArrayList<String>> tokenSet=new Lexical_Analyzer().getTokens();
		
                new Syntax_Analyzer(tokenSet);
                
		  new Lexical_Analyzer();
			for(int i=0;i<tokenSet.size();i++)
                        {
                            System.out.println(tokenSet.get(i));
                        }
                        
                                        
                //new Database_Connection();
	}
	
	int a;
	
	public Lexical_Analyzer() throws Exception 
        {
			ArrayList<ArrayList<String>> words=Word_Breaker.Break_Words("C:\\Users\\ramee\\Documents\\6th Semester\\Compiler Construction - I\\code.txt");
			
			String word="";
			String line_no="";
			String Class="";
			
			RE re=new RE();
			
			FileWriter writer = new FileWriter("C:\\Users\\ramee\\Documents\\6th Semester\\Compiler Construction - I\\output.txt");
			writer.write("");

			for (int i = 0; i < words.size(); i++)
                        {
				word=words.get(i).get(0);
				line_no=words.get(i).get(1);
				
				char ch=word.charAt(0);
				
		    	if(Character.isLetter(ch)) 
                        {
		    		if(re.isID(word)) 
                                {
		    			Class = isKW(word);
		    			if(Class.equals(""))
                                        {	
		    	 			if(word.equals("true") || word.equals("false")) 
                                                {
			    				Class = "bool_const";
			    			}
                                                else
                                                {
			    				Class = "ID";	 
                                                        
			    			}
		    			}
		    			if(Class.equals(word))
                                        {
		    				writer.write("( "+Class + " ,  , " + line_no+ " )\r\n");
		    				tokens.add(new ArrayList<String>(Arrays.asList(Class," ",line_no)));
		    			}
		    			else
                                        {	
		    				writer.write("( "+Class + " , "+word+" , " + line_no+ " )\r\n");
		    				tokens.add(new ArrayList<String>(Arrays.asList(Class, word, line_no)));
		    			}
		    		}
		    		else
                                {
		    			writer.write("( Invalid Token"+" , "+word+" , " + line_no+ " )\r\n");
		    			tokens.add(new ArrayList<String>(Arrays.asList("Invalid Token", word, line_no)));
		    		}
		    	}
		    	else if(ch == '_')
                        {
		    		if(re.isID(word))
                                {
		    				Class = "ID";
		    				writer.write("( "+Class + " , "+word+" , " + line_no+ " )\r\n");
		    				tokens.add(new ArrayList<String>(Arrays.asList(Class, word, line_no)));
		    		}
		    		else 
                                {
		    			
		    			tokens.add(new ArrayList<String>(Arrays.asList("Invalid Token", word, line_no)));
		    		}
		    	}
		    	else if(ch == '\"') 
                        {
		    		if(re.isStrConst(word))
                                {	
		    			Class = "string_const";
		    			word = word.substring(1, word.length()-1);
		    			writer.write("( "+Class + " , "+word+" , " + line_no+ " )\r\n");
		    			tokens.add(new ArrayList<String>(Arrays.asList(Class, word, line_no)));
		    		}
		    		else 
                                {
		    			writer.write("( Invalid Token "+" , "+word+" , " + line_no+ " )\r\n");
		    			tokens.add(new ArrayList<String>(Arrays.asList("Invalid Token", word, line_no)));
		    		}
		    	}
		    	else if (ch == '\'')
                        {
		    		if(re.isCharConst(word)) 
                                {
		    			Class = "char_const";
		    			word = word.substring(1, word.length()-1);
		    			writer.write("( "+Class + " , "+word+" , " + line_no+ " )\r\n");
		    			tokens.add(new ArrayList<String>(Arrays.asList(Class, word, line_no)));
		    		}
		    		else 
                                {
		    			writer.write("( Invalid Token "+ " , "+word+" , " + line_no+ " )\r\n");
		    			tokens.add(new ArrayList<String>(Arrays.asList("Invalid Token", word, line_no)));
		    		}
		    	}
		    	else if (Character.isDigit(ch)) 
                        {
		    		if(re.isIntConst(word)) 
                                {
		    			Class = "int_const";
		    			writer.write("( "+Class + " , "+word+" , " + line_no+ " )\r\n");
		    			tokens.add(new ArrayList<String>(Arrays.asList(Class, word, line_no)));
		    		}
		    		else if (re.isFloatConst(word)) 
                                {
		    			Class = "float_const";
		    			writer.write("( "+Class + " , "+word+" , " + line_no+ " )\r\n");
		    			tokens.add(new ArrayList<String>(Arrays.asList(Class, word, line_no)));
		    		}
		    		else 
                                {	
		    			writer.write("( Invalid Token "+" , "+word+" , " + line_no+ " )\r\n");
		    			tokens.add(new ArrayList<String>(Arrays.asList("Invalid Token", word, line_no)));
		    		}
		    	}
		    	else if (ch == '.') 
                        {
		    		if (re.isFloatConst(word))
                                {	
		    			Class = "float_const";
		    			writer.write("( "+Class + " , "+word+" , " + line_no+ " )\r\n");
		    			tokens.add(new ArrayList<String>(Arrays.asList(Class, word, line_no)));
		    		}
		    		else if (word.equals(".")) 
                                {
		    			writer.write("( ."+" ,  , " + line_no+ " )\r\n");
		    			tokens.add(new ArrayList<String>(Arrays.asList("."," ", line_no)));
		    		}
		    		else
                                {
		    			writer.write("( Invalid Token "+" , "+word+" , " + line_no+ " )\r\n");
		    			tokens.add(new ArrayList<String>(Arrays.asList("Invalid Token", word, line_no)));
		    		}
		    	}
		    	else if (isPunctuator(ch)) 
                        {
		    		Class = word;
		    		writer.write("( "+Class + " ,  , " + line_no+ " )\r\n");
		    		tokens.add(new ArrayList<String>(Arrays.asList(Class," " , line_no)));
		    	}
		    	else if(isOperator(ch)) 
                        {	
		    		Class = isOP(word);
		    		writer.write("( "+Class + " , "+word+" , " + line_no+ " )\r\n");
		    		tokens.add(new ArrayList<String>(Arrays.asList(Class, word , line_no)));
		    	}
		    	else
                        {	
		    		writer.write("( Invalid Token "+" , "+word+" , " + line_no+ " )\r\n");
		    		tokens.add(new ArrayList<String>(Arrays.asList("Invalid Token", word, line_no)));
		    	}
		    	
			}
			tokens.add(new ArrayList<String>(Arrays.asList("$", "$", line_no)));
			writer.close();
	}
	
	public ArrayList<ArrayList<String>> getTokens() 
        {
		String aString="@#";
		return tokens;
	}
	
	// method to check whether a character is operator or not
	
	private static boolean isOperator(char c) 
        {
		if(c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '&' || c == '|' || c == '!' || c == '<' || c == '>' || c == '=' ) 
                {
			return true;
		}
		return false;
	}
	
	// method to check whether a character is punctuator, if true, return true else, return false
	
	private static boolean isPunctuator(char c) 
        {
		//if(c == ';' || c == ':' || c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']' || c == ',' || c == '?' ) 
                if(c == ';' || c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']' )    
                {
			return true;
		}
		return false;
	}

	// method to check whether a word is operator or not
	// return class of word if it is operator
	private static String isOP (String word)
        {
		String Class = "";
		for (int i = 0; i < OP.size(); i++) 
                {
			if(OP.get(i).get(0).equals(word))
                        {
				Class = OP.get(i).get(1);
			}
		}
		return Class;
	}
	
	// method to check whether a word is keyword or not
	// return class of word if it is keyword
	private static String isKW (String word)
        {
		String Class = "";
		for(int i = 0; i < KW.size(); i++) 
                {
			if(KW.get(i).get(0).equals(word)) 
                        {
				Class = KW.get(i).get(1);
			}
		}
		return Class;
	}
	
	// for bool constant
	private static boolean isBoolConst(String input) 
        {
		if(input.equals("true") || input.equals("false")) 
                {
			return true;
		}
		return false;
        }
}
