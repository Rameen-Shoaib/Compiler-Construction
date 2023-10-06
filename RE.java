/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tokens;

/**
 *
 * @author ramee
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RE 
{	
	// RE for identifier
		public static boolean isID(String input)
                {		
			Pattern p = Pattern.compile("(([a-zA-Z_]\\w*[a-zA-Z\\d])|([a-zA-Z]))");
			Matcher m = p.matcher(input);
			
			if(m.matches()) 
                        {
				return true;
			}
			return false;	
		}
		
		// RE for character constants
		public static boolean isCharConst(String input)
                {
			Pattern p = Pattern.compile("\'((\\\\[\'\"\\\\])|(\\\\[bfnrt0])|([\\!#-&\\(-/0-9:-@A-Z\\[\\]-`ac-eg-mo-qsu-z\\{-~])|([bnfrt0]))\'");
			Matcher m = p.matcher(input);
			
			if(m.matches())
                        {
				return true;
			}
			return false;	
		}
		
		// RE for string constants
		public static boolean isStrConst(String input)
                {
			Pattern p = Pattern.compile("\"((\\\\[\'\"\\\\])|(\\\\[bfnrt0])|([\\!#-&\\(-/0-9:-@A-Z\\[\\]-`ac-eg-mo-qsu-z\\{-~])|([bnfrt0])|(\\s))*\"");
			Matcher m = p.matcher(input);
			
			if(m.matches())
                        {
				return true;
			}
			return false;
		}
		
		// RE for integer constants
		public static boolean isIntConst(String input)
                {
			Pattern p = Pattern.compile("[+-]?\\d+");
			Matcher m = p.matcher(input);
			
			if(m.matches())
                        {	
				return true;
			}
			return false;
		}
		
		// RE for floating point constants
		public static boolean isFloatConst(String input)
                {
			Pattern p = Pattern.compile("[+-]?\\d*\\.\\d+");
			Matcher m = p.matcher(input);
			
			if(m.matches())
                        {
				return true;
			}
			return false;
		}
}
