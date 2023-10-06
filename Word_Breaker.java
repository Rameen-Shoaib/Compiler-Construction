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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Word_Breaker
{
	private static ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
	
	private static int cmt=0;
	private static int line_no=0;
	
	public ArrayList<ArrayList<String>> getList() 
        {
		return list;
	}
	
	public static ArrayList<ArrayList<String>> Break_Words(String path) throws Exception
        {
//		String data=readFile(path);
//		breaker(data);
//		
		
		File file = new File(path);
		  
		BufferedReader br = new BufferedReader(new FileReader(file));

		String str;
		while((str=br.readLine())!= null)
                {
			line_no++;
			breaker(str);
		}
		
		return list;
	}
	
	private static void breaker(String str)
        {
		String temp = "";
		for (int i = 0; i < str.length(); i++) 
                {
			char c=str.charAt(i);
			
			if(str.charAt(i)=='\t') 
                        {
				if(!temp.isEmpty()) 
                                {
					list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
					temp="";
				}	
			}
			
			else if(str.charAt(i)=='$' && cmt==0) 
                        {
				cmt=1;
				i++;
				while(i<str.length()) 
                                {
					if(str.charAt(i)=='$')
                                        {
						cmt=0;
						break;
					}
					i++;
				}
			}
                        else if(cmt==1) 
                        {
				while(i<str.length())
                                {
					if(str.charAt(i)=='#') 
                                        {
						cmt=0;
						break;
					}
					i++;
				}
			}
			
			else if(str.charAt(i)=='\r' || str.charAt(i)=='\n')
                        {
				if(!temp.isEmpty()) 
                                {
					list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
					temp="";
				}		
			}
                        
                        else if(str.charAt(i)==' ')
                        {
				if(!temp.isEmpty())
                                {
					list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
					temp="";
				}
				
				continue;
			}
                        
			else if(isOperator(c))
                        {
				if(!temp.isEmpty()) 
                                {
					list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
					temp="";
				}
				
				// For operators this code will for both single (+,-) and double (++,&&..) operators
				char c2;
				
				//for suppose if ++ occur then we have to check + after 1 + this is why it take 2nd char in c2
				// condition is for handling IndexOutOfBound Exception
				temp+=String.valueOf(c);
			
				if(i+1>=str.length()) 
                                {
					list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
					break;
				}
					
				c2=str.charAt(i+1);
				
                                // Use switch case for each operator and also checking for the operators that can occur 1 more operator
				switch(c)
                                {
					case '+':
						
						// + can occur with + | = (++,+=)if found then concat with previous char that is store in temp
						if(c2=='+' || c2=='=')
                                                {
							temp="";
							temp=String.valueOf(c)+String.valueOf(c2);
							i++;
						}
						
						break;
                                                
					case '-':
	

						// - can occur with - | =  (--,-=) if found then concat with previous char that is store in temp
						if(c2=='-' || c2=='=')
                                                {
							temp="";
							temp=String.valueOf(c)+String.valueOf(c2);
							i++;
						}

						break;
                                                
					case '*':
						
						// * can occur with = (==)if found then concat with previous char that is store in temp
						if(c2=='=')
                                                {
							temp="";
							temp=String.valueOf(c)+String.valueOf(c2);
							i++;
						}
					
						break;
                                                
					case '/':
						
						// / can occur with = (/,/=) if found then concat with previous char that is store in temp
						if(c2=='=') 
                                                {
							temp="";
							temp=String.valueOf(c)+String.valueOf(c2);
							i++;
						}
                                                else if(c2=='/')
                                                {
							while(i<str.length())
                                                        {
								if(str.charAt(i)=='\r')
                                                                {
									break;
								}
								i++;
							}
							temp="";
						}
						
						break;
                                                
					case '%':

						// % can occur with = (%,%=) if found then concat with previous char that is store in temp
						if(c2=='=')
                                                {
							temp="";
							temp=String.valueOf(c)+String.valueOf(c2);
							i++;
						}
						
						break;
                                                
					case '!':
						
						// ! can occur with (!,!=) if found then concat with previous char that is store in temp
						if(c2=='=')
                                                {
							temp="";
							temp=String.valueOf(c)+String.valueOf(c2);
							i++;
						}
						
						break;
                                                
					case '>':
						
						// > can occur with (>,>=) if found then concat with previous char that is store in temp
						if(c2=='=') 
                                                {
							temp="";
							temp=String.valueOf(c)+String.valueOf(c2);
							i++;
						}
						
						break;
                                                
					case '<':
						// < can occur with (<,<=) if found then concat with previous char that is store in temp	
						
						if(c2=='=')
                                                {
							temp="";
							temp=String.valueOf(c)+String.valueOf(c2);
							i++;
						}
						
						break;
                                                
					case '=':
						// = can occur with (=,==) if found then concat with previous char that is store in temp
						
						if(c2=='=')
                                                {
							temp="";
							temp=String.valueOf(c)+String.valueOf(c2);
							i++;
						}
						
						break;
                                                
					case '&':
					
						// & must occur in && format
						
						if(c2=='&') 
                                                {
							temp="";
							temp=String.valueOf(c)+String.valueOf(c2);
							i++;
						}
						
						break;
                                                
					case '|':
				
						// | must occur in || format
						
						if(c2=='|')
                                                {
							temp="";
							temp=String.valueOf(c)+String.valueOf(c2);
							i++;
						}
						
						break;
				}
				
				if(!temp.isEmpty()) 
                                {
					list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
					temp="";
				}
				
			}
                        else if (isPunctuators(c))
                        {
				if(!temp.isEmpty())
                                {
					list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
					temp="";
				}
				
				list.add(new ArrayList<String>(Arrays.asList(String.valueOf(c),String.valueOf(line_no))));	
				
			}
                        else if(c=='\'') 
                        {
				if(!temp.isEmpty()) 
                                {
					list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
					temp="";
				}
				
				temp+=str.charAt(i);
				i++;
				
				if(i>=str.length())
                                {
					list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
					break;
				}
					
				c=str.charAt(i);
				
				if(c==' ' || c=='\r' || c=='\n')
                                {
					list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
					continue;
				
				}
                                else if(c=='\\')
                                {
					temp+=String.valueOf(c);
					i++;
					
					int j=0;
					while(j<2 && i<str.length()) 
                                        {
						if(str.charAt(i)==' ' || str.charAt(i)=='\r' || str.charAt(i)=='\n') 
                                                {
							break;
						}
						
						temp+=str.charAt(i);
						i++;
						j++;	
					}
					
				}
                                else 
                                {
					int j=0;
					while(j<2 && i<str.length()) 
                                        {
						if(str.charAt(i)==' ') 
							break;
						
						temp+=str.charAt(i);
						i++;
						j++;
					}	
				}
				i--;
				
				list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
				temp="";
				
		}
                        else if(c=='\"')
                {
			if(!temp.isEmpty())
                        {
				list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
				temp="";
			}
			
			 // "abc"
			
			temp+=String.valueOf(str.charAt(i));
			i++;
			
			while(i<str.length()) 
                        {

				if(str.charAt(i)=='\\' && str.charAt(i+1)=='\"') 
                                {
					temp+=String.valueOf(str.charAt(i))+str.charAt(i+1);
					i+=2;
					
				}
                                else if(str.charAt(i)=='\"') 
                                {
					break;
				}
                                else
                                {
					temp+=String.valueOf(str.charAt(i));
					i++;
				}
			}
			
			// if we have "+5 then we check for this conditon 
			if(i>=str.length())
                        {
				list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
				temp="";
				continue;
			}
			
			
			if(str.charAt(i)=='\"') 
                        {
					temp+=String.valueOf(str.charAt(i));	
			}
			
		
			list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
			temp="";
			

		   }
                        else if(str.charAt(i)=='.')
                   {
			
			boolean valid=Pattern.matches("[0-9]+", temp);
			
			if(!valid && !temp.isEmpty())
                        {
				list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
				temp="";
			}
			
			temp+=String.valueOf(str.charAt(i));
			i++;
			
			if(i>=str.length()) 
                        {
				list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
				break;
			}
			
			if(Character.isDigit(str.charAt(i)))
                        {
				while(i<str.length() && str.charAt(i)!=' ' && str.charAt(i)!='.' &&
						str.charAt(i)!='\'' && str.charAt(i)!='\"' &&  !isOperator(str.charAt(i))
						&& str.charAt(i)!='\r' && str.charAt(i)!='\n' && !isPunctuators(str.charAt(i)))
                                {
					temp+=str.charAt(i);
					i++;			
				}

				list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
				temp="";
				i--;
			
			}
                        else 
                        {
				list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
				temp="";
				i--;
			}
//			35a.a..55.77ab.abcb 55.h 66.77.42.
		}
                        else
                        {
			temp+=str.charAt(i);
			
			if(i+1>=str.length()) 
                        {
				list.add(new ArrayList<String>(Arrays.asList(temp,String.valueOf(line_no))));
				temp="";
			} 
		}	
	}	
}
	
    private static boolean isPunctuators(char c) 
    {
		//if(c==':' || c==';' || c==')' || c=='(' || c=='{' || c=='}' || c=='[' || c==']' || c==',' || c=='?' )
                if(c == ';' || c == '(' || c == ')' || c == '{' || c == '}'|| c=='[' || c==']' )     
                {
			return true;
		}
		
		return false;
	}
	
    private static boolean isOperator(char c) 
    {
		
		if(c=='+' || c=='-' || c=='*' || c=='/' || c=='%' || c=='=' || c=='<' || c=='>' || c=='!' || c=='&' || c=='|') 
                {
			return true;
		}
		
		return false;
    }
}
