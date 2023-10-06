/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tokens;

import com.tokens.Database_Connection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ramee
 */

public class Syntax_Analyzer
{
    
        ArrayList<ArrayList<String>> tokenSet = new ArrayList<ArrayList<String>>();
        static int i = 0;
        
	static int CR=0;
	static int FR=0;
	static boolean isStatic;
        static boolean isAbstract;
        static boolean isFinal;
	static String name="null";
	static String type="null";
        static String ext="null";
        static String imp="null";
	static String AM="None";
	static String TM="None";
	static String pl="null";
	static String al="null";
        static String Op = "null";
        static int s=0;
	static int isLoop=0;
        static String LO = "null";
	static String RO = "null";
        static String KW = "";
	static String ar_type="null";
	static int isStaticM = 0;
	static int flag = 0;
	static int isReturn=0;
	static int counter = 0;
	static int current_CR = 0;
		
	static Database_Connection database = new Database_Connection();
    
    public  Syntax_Analyzer(ArrayList<ArrayList<String>>TS) throws Exception
    {
        tokenSet = TS;
        if(S())
        {
            System.out.println(TS.get(i).get(0));
            if(TS.get(i).get(0).equals("$"))
            {
                System.out.println("Valid Syntax...");
            }
            
        }
        else
        {
            System.out.println("Invalid Syntax...");
            System.out.println(TS.get(i).get(2)+" "+TS.get(i).get(1));
        }

    }
    
    private boolean S()
    {
        if(tokenSet.get(i).get(0).equals ("class") || tokenSet.get(i).get(0).equals ("package") || tokenSet.get(i).get(0).equals ("import") || tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("mainclass") || tokenSet.get(i).get(0).equals ("interface") || tokenSet.get(i).get(0).equals ("{") || tokenSet.get(i).get(0).equals ("abstract"))
        {
            if(package_())
            {
                
                if(import_())
                {
                    
                    if(def()) 
                    {

                        if(acc_modifier())
                        {                

                            if (c_modifier())
                            {

                                if(tokenSet.get(i).get(0).equals ("mainclass")) 
                                {
                                    type = tokenSet.get(i).get(0);
                                    i++;

                                    if(tokenSet.get(i).get(0).equals ("ID"))
                                    {
                                     name = tokenSet.get(i).get(1);   
                                     i++;

                                        if(inherits(type))
                                        {
                                            
                                            int CR=database.create_dataTable();
								
                                            if(!database.insert_MT(CR,name, type, TM, ext, imp)) 
                                            {
						System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
                                            }

                                            if(tokenSet.get(i).get(0).equals ("{")) 
                                            {
                                                String KW = "";
                                                s++;
                                                database.createScope(KW, s, isLoop);
                                                i++;

                                                if(body1()) 
                                                {
                                                    
                                                    if(tokenSet.get(i).get(0).equals ("main"))
                                                    {
                                                        name = tokenSet.get(i).get(0);
                                                        i++;

                                                        if(tokenSet.get(i).get(0).equals ("("))
                                                        {
                                                            i++;

                                                            if(tokenSet.get(i).get(0).equals ("String"))
                                                            {
                                                                type = tokenSet.get(i).get(0);
                                                                i++;

                                                                if(tokenSet.get(i).get(0).equals ("args"))
                                                                {
                                                                    name = tokenSet.get(i).get(0);
                                                                    i++;

                                                                    if(tokenSet.get(i).get(0).equals ("["))
                                                                    {
                                                                        i++;

                                                                        if(tokenSet.get(i).get(0).equals ("]"))
                                                                        {
                                                                            i++;

                                                                                if(tokenSet.get(i).get(0).equals (")"))
                                                                                {
                                                                                    i++;
                                                                                    
                                                                                    al="null"+"->"+type;
                                                                                    int FR=database.create_funTable();

                                                                                    if(!database.insert_DT(CR, FR, name, al, AM, isStatic, isFinal, isAbstract)) 
                                                                                    {
                                                                                        System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
                                                                                    }
                                                                                 
                                                                                    if(tokenSet.get(i).get(0).equals ("{"))
                                                                                    {
                                                                                        s++;
                                                                                        database.createScope(KW, s, isLoop);
                                                                                        i++;

                                                                                        if(MST(type, CR, FR))
                                                                                        {

                                                                                            if(tokenSet.get(i).get(0).equals ("}"))
                                                                                            {
                                                                                                s--;
                                                                                                database.destroyScope();
                                                                                                i++;

                                                                                                if(body1())
                                                                                                {

                                                                                                    if(tokenSet.get(i).get(0).equals ("}"))
                                                                                                    {
                                                                                                        s--;
                                                                                                        database.destroyScope();
                                                                                                        i++;

                                                                                                        if(defs())
                                                                                                        {
                                                                                                            return true;
                                                                                                        }
                                                                                                    }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }	
                            }
                        }
                    }
                }
                }
            }
        }
        
        if(tokenSet.get(i).get(0).equals ("$"))
        {
            return true;
        }
        
        return false;
    }
    
    private boolean def()
    {
        if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("class") || tokenSet.get(i).get(0).equals ("interface") || tokenSet.get(i).get(0).equals ("{") || tokenSet.get(i).get(0).equals ("abstract"))
        {
            
            if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("class") || tokenSet.get(i).get(0).equals ("interface") || tokenSet.get(i).get(0).equals ("{") || tokenSet.get(i).get(0).equals ("abstract"))
            {
                if(class_dec())
                {

                    if (defs())
                    {
                         return true;
                    }
                }
            }
        }
            
        if(tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("mainclass") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("abstract") || tokenSet.get(i).get(0).equals("final"))
        {
                return true;
        }
                    
        return false; 
    }
    
    private boolean defs()
    {
        if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("$") || tokenSet.get(i).get(0).equals("mainclass") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("abstract") || tokenSet.get(i).get(0).equals("final")) 
        {
            return true;
        }
        return false;
    }
    
    private boolean acc_modifier()
    {
        if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private"))
        {
            if(tokenSet.get(i).get(0).equals ("public")) 
            {
                AM=tokenSet.get(i).get(0);
                i++;
                return true;
            }

            else if(tokenSet.get(i).get(0).equals ("private"))
            {
                AM=tokenSet.get(i).get(0);
                i++;
                return true;         
            }
        }
        
        if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("abstract") || tokenSet.get(i).get(0).equals ("mainclass") || tokenSet.get(i).get(0).equals ("interface") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char") || tokenSet.get(i).get(0).equals ("void") || tokenSet.get(i).get(0).equals ("ID"))
        {
            return true;
        }
        return false;
    }
    
    private boolean c_modifier()
    {
        if(tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("abstract") || tokenSet.get(i).get(0).equals ("final")) 
        {
            if(tokenSet.get(i).get(0).equals ("static"))
            {
                TM=tokenSet.get(i).get(0);
                isStatic=true;
                i++;
                return true;
            }
            
            else if(tokenSet.get(i).get(0).equals ("abstract"))
            {
                TM=tokenSet.get(i).get(0);
                isAbstract=true;
                i++;
                return true;
            }
            
            else if(tokenSet.get(i).get(0).equals ("final"))
            {
                TM=tokenSet.get(i).get(0);
                isFinal=true;
                i++;
                return true;
            }
        }
        
        if(tokenSet.get(i).get(0).equals ("mainclass")) 
        {
            return true;
        }
        return false;
    }
    
    private boolean c_modifier1()
    {
        if(tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final")) 
        {
            if(tokenSet.get(i).get(0).equals ("static"))
            {
                TM=tokenSet.get(i).get(0);
                isStatic=true;
                i++;
                return true;
            }
            
            else if(tokenSet.get(i).get(0).equals ("final"))
            {
                TM=tokenSet.get(i).get(0);
                isFinal=true;
                i++;
                return true;
            }
        }
        
        if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char") || tokenSet.get(i).get(0).equals ("void") || tokenSet.get(i).get(0).equals ("ID")  || tokenSet.get(i).get(0).equals ("class")) 
        {
            return true;
        }
        return false;
    }
    
    private boolean c_modifier2()
    {
            if(tokenSet.get(i).get(0).equals ("abstract"))
            {
                TM=tokenSet.get(i).get(0);
                isAbstract=true;
                i++;
                return true;
            }
        return false;
    }
    
    private boolean data_type()
    {
        if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char"))
        { 
            if(tokenSet.get(i).get(0).equals ("DT"))
            {
                type=tokenSet.get(i).get(1);
                i++;
                return true;
            }
            
            else if(tokenSet.get(i).get(0).equals ("int"))
            {
                type=tokenSet.get(i).get(0);
                i++;
                return true;  
            }
            
            else if(tokenSet.get(i).get(0).equals ("String"))
            {
                type=tokenSet.get(i).get(0);
                i++;
                return true;  
            }
            
            else if(tokenSet.get(i).get(0).equals ("float"))
            {
                type=tokenSet.get(i).get(0);
                i++;
                return true;  
            }
            
            else if(tokenSet.get(i).get(0).equals ("bool"))
            {
                type=tokenSet.get(i).get(0);
                i++;
                return true;  
            }
            
            else if(tokenSet.get(i).get(0).equals ("char"))
            {
                type=tokenSet.get(i).get(0);
                i++;
                return true;  
            }
        }
        return false;
    }
    
    private boolean while_(String type,int CR,int FR)
    {
            String KW="";
            if(tokenSet.get(i).get(0).equals ("while")) 
            {
                KW=tokenSet.get(i).get(0);
		i++;
		isLoop++;

                if(tokenSet.get(i).get(0).equals ("(")) 
                {
                    i++;

                    if(OE(CR))
                    {

                        if(tokenSet.get(i).get(0).equals (")")) 
                        {
                            i++;
                            
                            if(!type.equals("bool")) 
                            {
				System.out.println("Must be Boolean type,line #="+tokenSet.get(i).get(2));
                            }

                            if(tokenSet.get(i).get(0).equals ("{")) 
                            {
                                i++;

                                if(body1())
                                {

                                    if(tokenSet.get(i).get(0).equals ("}"))
                                    {
                                        i++;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }

            }
        return false;
    }
    
    private boolean do_while(String type,int CR,int FR)
    {
            if(tokenSet.get(i).get(0).equals ("do")) 
            {
                String KW=tokenSet.get(i).get(0);
		isLoop++;
                i++;

                if(tokenSet.get(i).get(0).equals ("{")) 
                {
                    i++;

                    if(body1())
                    {

                        if(tokenSet.get(i).get(0).equals ("}"))
                        {
                            i++;

                            if(tokenSet.get(i).get(0).equals ("wihle")) 
                            {
                                i++;

                                if(tokenSet.get(i).get(0).equals ("(")) 
                                {
                                    i++;

                                    if(OE(CR))
                                    {
                                        if(!this.type.equals("bool")) 
                                        {
                                            System.out.println("Must be boolean type,,line #="+tokenSet.get(i).get(2));
					}
					this.type="null";

                                        if(tokenSet.get(i).get(0).equals (")")) 
                                        {
                                            i++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        return false;
    }
    
    private boolean if_else(String type,int CR,int FR)
    {
            if(tokenSet.get(i).get(0).equals ("if")) 
            {
                String KW=tokenSet.get(i).get(0);
                i++;

                if(tokenSet.get(i).get(0).equals ("(")) 
                {
                    i++;

                    if(OE(CR))
                    {

                        if(tokenSet.get(i).get(0).equals (")")) 
                        {
                            i++;

                            if(tokenSet.get(i).get(0).equals ("{")) 
                            {
                                i++;

                                if(body1())
                                {

                                    if(tokenSet.get(i).get(0).equals ("}"))
                                    {
                                        i++;

                                        if(else_(type, CR, FR))
                                        {
                                            return true; 
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        return false;
    }
    
    private boolean else_(String type,int CR,int FR)
    {
        if(tokenSet.get(i).get(0).equals ("else")) 
        {
            String KW=tokenSet.get(i).get(0);
            i++;
            
            if(tokenSet.get(i).get(0).equals ("{")) 
            {
                i++;
                
                if(body1())
                {
                    
                    if(tokenSet.get(i).get(0).equals ("}"))
                    {
                        i++;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean for_(String type,int CR,int FR)
    {
            if(tokenSet.get(i).get(0).equals ("for")) 
            {
                String KW=tokenSet.get(i).get(0);
		i++;
		isLoop++;

                if(tokenSet.get(i).get(0).equals ("(")) 
                {
                    i++;

                    if(c1(CR, FR))
                    {

                        if(tokenSet.get(i).get(0).equals (";"))
                        {
                            i++;

                            if(c2(CR))
                            {
                                if (!this.type.equals("bool")) 
                                {
                                    System.out.println("Type Error,line #="+tokenSet.get(i).get(2));
				}
				this.type="null";

                                if(tokenSet.get(i).get(0).equals (";"))
                                {
                                    i++;

                                    if(c3(CR))
                                    {

                                        if(tokenSet.get(i).get(0).equals (")"))
                                        {
                                            i++;

                                            if(tokenSet.get(i).get(0).equals ("{")) 
                                            {
                                                i++;

                                                if(body1())
                                                {

                                                    if(tokenSet.get(i).get(0).equals ("}"))
                                                    {
                                                        i++;
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        return false;
    }
    
    private boolean c1(int CR, int FR)
    {
        if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char") || tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("this") || tokenSet.get(i).get(0).equals ("super") || tokenSet.get(i).get(0).equals ("new"))
        {
            if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char"))
            {
                if(declare(CR, FR))
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("this") || tokenSet.get(i).get(0).equals ("super") || tokenSet.get(i).get(0).equals ("new"))
            {
                    if (assign_st())
                    {
                        return true;
                    }
            }
        }
        
        if(tokenSet.get(i).get(0).equals (";"))
        {
            return true;
        }
        return false;
    }
    
    private boolean c2(int CR)
    {
        if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("this") || tokenSet.get(i).get(0).equals ("super") || tokenSet.get(i).get(0).equals ("new") || tokenSet.get(i).get(0).equals ("int_const") || tokenSet.get(i).get(0).equals ("String_const") || tokenSet.get(i).get(0).equals ("float_const") || tokenSet.get(i).get(0).equals ("bool_const") || tokenSet.get(i).get(0).equals ("char_const") || tokenSet.get(i).get(0).equals ("(") || tokenSet.get(i).get(0).equals ("!"))
        {
            if (OE(CR))
            {
                return true;
            }
        }
        
        if(tokenSet.get(i).get(0).equals (";"))
        {
            return true;
        }
        return false;
    }
    
    private boolean c3(int CR)
    {
        if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("inc_dec") || tokenSet.get(i).get(0).equals ("++") || tokenSet.get(i).get(0).equals ("--"))
        {
            if (inc_dec_st())
            {
                String op=tokenSet.get(i).get(0);
                return true;
            }
        }
        
        if(tokenSet.get(i).get(0).equals (")"))
        {
            return true;
        }
        return false;
    }
    
    private boolean const_()
    {
        if(tokenSet.get(i).get(0).equals ("int_const") || tokenSet.get(i).get(0).equals ("float_const") || tokenSet.get(i).get(0).equals ("String_const") || tokenSet.get(i).get(0).equals ("bool_const") || tokenSet.get(i).get(0).equals ("char_const"))
        {
           if(tokenSet.get(i).get(0).equals ("int_const"))
           {
                this.type = "int";
                i++;
                return true;
           }
           
           else if(tokenSet.get(i).get(0).equals ("float_const"))
           {
                this.type = "float";
                i++;
                return true;
           }
           
           else if(tokenSet.get(i).get(0).equals ("char_const"))
           {
                this.type = "char";
                i++;
                return true;
           }
           
           else if(tokenSet.get(i).get(0).equals ("bool_const"))
           {
                this.type = "bool";
                i++;
                return true;
           }
           
           else if(tokenSet.get(i).get(0).equals ("String_const"))
           {
                this.type = "String";
                i++;
                return true;
           }
        }
        return false;
    }
    
    private boolean break_()
    {
            if(tokenSet.get(i).get(0).equals ("break")) 
            {
                i++;

                if(tokenSet.get(i).get(0).equals (";")) 
                {
                    i++;
                    return true;
                }
            }
        return false;
    }
    
    private boolean continue_()
    {
            if(tokenSet.get(i).get(0).equals ("continue")) 
            {
                i++;

                if(tokenSet.get(i).get(0).equals (";")) 
                {
                    i++;
                    return true;
                }
            }
        return false;
    }
    
    private boolean declare(int CR,int FR)
    {
        if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char"))
        {
            if(data_type())
            {
                
                if(tokenSet.get(i).get(0).equals ("ID")) 
                {
//                    name=tokenSet.get(i).get(1);
                    i++;
                    
                    if(!database.insert_FT(FR, name, type, CR))
                    {
                        System.out.println("Redeclaration Error");
                    }
                    
                    if(initial(type, CR))
                    {
                        if(list(type,"null","Default",CR,FR))
                        {
                            return true;
                        }      
                    }
                }
            }
        }
        return false;
    }
    
    private boolean list(String type,String TM,String AM,int id, int Static)
    {
        if(tokenSet.get(i).get(0).equals (";") || tokenSet.get(i).get(0).equals (","))
        {
            if(tokenSet.get(i).get(0).equals (";")) 
            {
                i++;
                return true;
            }

            else if(tokenSet.get(i).get(0).equals (",")) 
            {
                i++;

                if(tokenSet.get(i).get(0).equals ("ID")) 
                {
                    name=tokenSet.get(i).get(1);
                    i++;
                    
                    if (id<20000)
                    {
                        if(!database.insert_DT(id, 0, name, type, AM, isStatic, isFinal, isAbstract)) 
                        {
                            System.out.println("Redeclaration Error");
			}
                    }
                    
                    else if (!database.insert_FT(id, name, type, CR))
                    {
			System.out.println("Redeclaration Error");		
                    }

                    if(initial(type, id))
                    {

                        if(list(type, TM, AM, id, Static))
                        {
                            return true;
                        }    
                    }
                }
            }
        }
        return false;
    }
    
    private boolean initial(String type, int CR)
    {
            if(tokenSet.get(i).get(0).equals ("=")) 
            {
                String op=tokenSet.get(i).get(0);
                i++;

                if(initial_())
                {                    
                    return true;
                }
            }
        
        else if(tokenSet.get(i).get(0).equals (";") || tokenSet.get(i).get(0).equals (","))
        {
            return true;
        }
        return false;
    }
    
    private boolean initial_()
    {
        if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("this") || tokenSet.get(i).get(0).equals ("super") || tokenSet.get(i).get(0).equals ("new") || tokenSet.get(i).get(0).equals ("int_const") || tokenSet.get(i).get(0).equals ("String_const") || tokenSet.get(i).get(0).equals ("float_const") || tokenSet.get(i).get(0).equals ("bool_const") || tokenSet.get(i).get(0).equals ("char_const") || tokenSet.get(i).get(0).equals ("(") || tokenSet.get(i).get(0).equals ("!"))
        {
            
            if(tokenSet.get(i).get(0).equals ("ID")) 
            {                
                i++;

                if(initial(type, CR))
                {
                    return true;
                }
            }

            if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("this") || tokenSet.get(i).get(0).equals ("super") || tokenSet.get(i).get(0).equals ("new") || tokenSet.get(i).get(0).equals ("int_const") || tokenSet.get(i).get(0).equals ("String_const") || tokenSet.get(i).get(0).equals ("float_const") || tokenSet.get(i).get(0).equals ("bool_const") || tokenSet.get(i).get(0).equals ("char_const") || tokenSet.get(i).get(0).equals ("(") || tokenSet.get(i).get(0).equals ("!"))
            {
                if(OE(CR)) 
                {
                    String T=database.compatibility(type, this.type, "=");	
                    if (T==null)
                    {
                        System.out.println("Mismatched Error. Type Error in init,line no #"+tokenSet.get(i).get(2));
                    }
                    return true;
                }
            }
        }
        return false;
        }
    
    private boolean assign_st()
    {
        if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("this") || tokenSet.get(i).get(0).equals ("super") || tokenSet.get(i).get(0).equals ("new"))
        {
            if(list_()) 
            {
                
                if(LHP())
                { 
                    String t1=this.type;
                    this.type="null";
                    
                    if(tokenSet.get(i).get(0).equals ("=")) 
                    {
                        String op=Op;
                        i++;

                        if(OE(CR))
                        {
                            String t2=type;
                            String T=database.compatibility(t1, t2, op);
                            this.type="null";
                            if (T==null) 
                            {		
				System.out.println("Type Uncompatible Error,line #="+tokenSet.get(i).get(2));
                            }

                            if(tokenSet.get(i).get(0).equals (";"))
                            {
                                i++;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean list_()
    {
        if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("this") || tokenSet.get(i).get(0).equals ("super") || tokenSet.get(i).get(0).equals ("new"))
        {

            if (tokenSet.get(i).get(0).equals("ID")) 
            {
                name=tokenSet.get(i).get(1);
                i++;
                return true;
            }           

            else if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("this") || tokenSet.get(i).get(0).equals ("super") || tokenSet.get(i).get(0).equals ("new"))
            {
                if(fn_call())
                {
                    if(LHP2())
                    {
                        return true;
                    }
                }
            }

            else if(tokenSet.get(i).get(0).equals ("ID"))
            {
                if (arr_call())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("this")) 
            {
                        i++;

                        if(LHP())
                        {
                            return true;
                        }
            }

            else if(tokenSet.get(i).get(0).equals("super"))
            {
                i++;

                if(LHP())
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean LHP()
    {
        if (tokenSet.get(i).get(0).equals("."))
        {
            i++;
            
            if (LHP_()) 
            {
                return true;
            }
        }
        
        if (tokenSet.get(i).get(0).equals("=") || tokenSet.get(i).get(0).equals ("inc_dec") || tokenSet.get(i).get(0).equals("++") || tokenSet.get(i).get(0).equals("--") || tokenSet.get(i).get(0).equals(";"))
        {
            return true;
        }
        return false;
    }
    
    private boolean LHP_()
    {
        if (tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") ||tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new"))
        {
            if(tokenSet.get(i).get(0).equals("ID"))
            {
                if(arr_call()) 
                {

                    if (LHP())
                    {
                        return true;
                    }
                }
            }

            else if(tokenSet.get(i).get(0).equals("ID")) 
            {
                name=tokenSet.get(i).get(1);
                i++;

                if (LHP())
                {
                    return true;
                }

            }
            
            else if (tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new"))
            {
                if(fn_call())
                {

                    if (LHP2()) 
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean LHP2()
    {
        if (tokenSet.get(i).get(0).equals("."))
        {
            i++;

            if (LHP_()) 
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean inc_dec_st()
    {
        if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("inc_dec") || tokenSet.get(i).get(0).equals ("++") || tokenSet.get(i).get(0).equals ("--"))
        {
            if(tokenSet.get(i).get(0).equals ("ID")) 
            {
                name=tokenSet.get(i).get(1);
                i++;

                if (LHP()) 
                {
                    String T = database.compatibility(type);
                    if(T == null) 
                    {
                        System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
			this.type = "null";
                    }
				
                    else 
                    {
                        this.type = T;
                    }

                    if (tokenSet.get(i).get(0).equals("inc_dec"))
                    //if (inc_dec())
                    {
                        String op=tokenSet.get(i).get(0);
                        i++;

                        if (tokenSet.get(i).get(0).equals(";")) 
                        {
                            i++;
                            return true;
                        }
                    }
                }
            } 

            else if(tokenSet.get(i).get(0).equals ("inc_dec") || tokenSet.get(i).get(0).equals("--")|| tokenSet.get(i).get(0).equals("++"))
            {
                if (tokenSet.get(i).get(0).equals("inc_dec"))
                //if (inc_dec())
                {
                    String op=tokenSet.get(i).get(0);
                    i++;

                    if (tokenSet.get(i).get(0).equals("ID"))
                    {
                        i++;
                        name=tokenSet.get(i).get(1);

                        if (LHP())
                        {
                            String T = database.compatibility(type);
                            if(T == null) 
                            {
                                System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
                                this.type = "null";
                            }

                            else 
                            {
                                this.type = T;
                            }
                            
                            if (tokenSet.get(i).get(0).equals(";")) 
                            {
                                i++;
                                return true;
                             }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    /*
    private boolean inc_dec()
    {
        if (tokenSet.get(i).get(0).equals("++") || tokenSet.get(i).get(0).equals("--")) 
        {
            if (tokenSet.get(i).get(0).equals("++")) 
            {
                i++;
                return true;
            }

            else if(tokenSet.get(i).get(0).equals("--"))
            {
                i++;
                return true;
            }
        }
        return false;
    }
    */
    
    private boolean OE(int CR)
    {
        if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("this") ||tokenSet.get(i).get(0).equals ("super") ||tokenSet.get(i).get(0).equals ("new")||tokenSet.get(i).get(0).equals ("int_const") ||tokenSet.get(i).get(0).equals ("String_const") ||tokenSet.get(i).get(0).equals ("float_const") ||tokenSet.get(i).get(0).equals ("char_const")||tokenSet.get(i).get(0).equals ("(")||tokenSet.get(i).get(0).equals ("!"))
        {
            if(AE(CR))
            {

                if (OE_(CR)) 
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean OE_(int CR)
    {
        if (tokenSet.get(i).get(0).equals("OR") || tokenSet.get(i).get(0).equals("||")) 
        {
            String Op = tokenSet.get(i).get(0);
            String LO = this.type;
            i++;
            
            if (AE(CR)) 
            {
                String RO = this.type;
				
		String T = database.compatibility(LO, RO, Op);
		if(T == null)
                {
                    System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
                    this.type = "null";
		}
                
                else 
                {
                    this.type = T;
		}
                
                if (OE_(CR))
                {
                    return true;
                }
            }
        }
        
        if (tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("]") || tokenSet.get(i).get(0).equals("}")) 
        {
            return true;
        }
        return false;
    }
    
    private boolean AE(int CR)
    {
        if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("this") ||tokenSet.get(i).get(0).equals ("super") ||tokenSet.get(i).get(0).equals ("new")||tokenSet.get(i).get(0).equals ("int_const") ||tokenSet.get(i).get(0).equals ("String_const") ||tokenSet.get(i).get(0).equals ("float_const") ||tokenSet.get(i).get(0).equals ("char_const")||tokenSet.get(i).get(0).equals ("(")||tokenSet.get(i).get(0).equals ("!"))
        {
            if(RE(CR))
            {

                if (AE_(CR)) 
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean AE_(int CR)
    {
        if (tokenSet.get(i).get(0).equals("AND") || tokenSet.get(i).get(0).equals("&&"))
        {
            String Op = tokenSet.get(i).get(0);
            String LO = this.type;
            i++;
            
            if (RE(CR))
            {
                String RO = this.type;
				
		String T = database.compatibility(LO, RO, Op);
		if(T == null) 
                {
                    System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
                    this.type = "null";
		}
                
		else 
                {
                    this.type = T;
		}
                
                if (AE_(CR)) 
                {
                    return true;
                }
             }
        }
        
        if (tokenSet.get(i).get(0).equals("OR") || tokenSet.get(i).get(0).equals("||") || tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("]") || tokenSet.get(i).get(0).equals("}"))
        {
            return true;
        }
        return false;
    }
    
    /*
    private boolean AE_()
    {
        if (tokenSet.get(i).get(0).equals("&&"))
        {
            i++;
            
            if (RE())
            {
                
                if (AE_()) 
                {
                    return true;
                }
             }
        }
        
        if (tokenSet.get(i).get(0).equals("||"))
        {
            return true;
        }
        return false;
    }
    */
    
    private boolean RE(int CR)
    {
        if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("this") ||tokenSet.get(i).get(0).equals ("super") ||tokenSet.get(i).get(0).equals ("new")||tokenSet.get(i).get(0).equals ("int_const") ||tokenSet.get(i).get(0).equals ("String_const") ||tokenSet.get(i).get(0).equals ("float_const") ||tokenSet.get(i).get(0).equals ("char_const")||tokenSet.get(i).get(0).equals ("(")||tokenSet.get(i).get(0).equals ("!"))
        {
            if(E(CR))
            {

                if (RE_(CR))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean RE_(int CR)
    {
        if (tokenSet.get(i).get(0).equals("ROP"))
        {
            String Op = tokenSet.get(i).get(0);
            String LO = this.type;
            i++;
            
            if (E(CR))
            {
                String RO = this.type;
				
		String T = database.compatibility(LO, RO, Op);
		if(T == null) 
                {
                    System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
                    this.type = "null";
		}
                
                if (RE_(CR)) 
                {
                    return true;
                }
            }
        }
        
        if (tokenSet.get(i).get(0).equals("AND") || tokenSet.get(i).get(0).equals("&&") || tokenSet.get(i).get(0).equals("OR") ||  tokenSet.get(i).get(0).equals("||") || tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("]") || tokenSet.get(i).get(0).equals("}"))
        {
            return true;
        }
        return false;
    }
    
    /*
    private boolean RE_()
    {
        if (tokenSet.get(i).get(0).equals("ROP"))
        {
            i++;
            
            if (E())
            {
                
                if (RE_()) 
                {
                    return true;
                }
            }
        }
        
        if (tokenSet.get(i).get(0).equals("&&"))
        {
            return true;
        }
        return false;
    }
    */
    
    private boolean E(int CR)
    {
        if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("this") ||tokenSet.get(i).get(0).equals ("super") ||tokenSet.get(i).get(0).equals ("new")||tokenSet.get(i).get(0).equals ("int_const") ||tokenSet.get(i).get(0).equals ("String_const") ||tokenSet.get(i).get(0).equals ("float_const") ||tokenSet.get(i).get(0).equals ("char_const")||tokenSet.get(i).get(0).equals ("(")||tokenSet.get(i).get(0).equals ("!"))
        {
            if(T(CR))
            {

                if (E_(CR))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean E_(int CR)
    {
        if (tokenSet.get(i).get(0).equals("PM"))
        {
            String Op = tokenSet.get(i).get(0);
            String LO = this.type;
            i++;
            
            if (T(CR))
            {
                String RO = this.type;
				
		String T = database.compatibility(LO, RO, Op);
		if(T == null) 
                {
                    System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
                    this.type = "null";
		}
                
		else
                {
                    this.type = T;
		}
                
                if (E_(CR))
                {
                    return true;
                }
            }
        }
        
        if (tokenSet.get(i).get(0).equals("ROP") || tokenSet.get(i).get(0).equals("AND") || tokenSet.get(i).get(0).equals("&&") || tokenSet.get(i).get(0).equals("OR") || tokenSet.get(i).get(0).equals("||") || tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("]") || tokenSet.get(i).get(0).equals("}"))
        {
            return true;
        }
        return false;
    }
    
    /*
    private boolean E_()
    {
        if (tokenSet.get(i).get(0).equals("PM"))
        {
            i++;
            
            if (T())
            {
                
                if (E_())
                {
                    return true;
                }
            }
        }
        
        if (tokenSet.get(i).get(0).equals("ROP"))
        {
            return true;
        }
        return false;
    }
    */
    
    private boolean T(int CR)
    {
        if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("this") ||tokenSet.get(i).get(0).equals ("super") ||tokenSet.get(i).get(0).equals ("new")||tokenSet.get(i).get(0).equals ("int_const") ||tokenSet.get(i).get(0).equals ("String_const") ||tokenSet.get(i).get(0).equals ("float_const") ||tokenSet.get(i).get(0).equals ("char_const")||tokenSet.get(i).get(0).equals ("(")||tokenSet.get(i).get(0).equals ("!"))
        {
            if(F(CR))
            {

                if (T_(CR))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean T_(int CR)
    {
        if (tokenSet.get(i).get(0).equals("MDM")) 
        {
            String Op = tokenSet.get(i).get(0);
            String LO = this.type;
            i++;
            
            if (F(CR)) 
            {
                String RO = this.type;
				
		String T = database.compatibility(LO, RO, Op);
		if(T == null)
                {
                    System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
                    this.type = "null";
		}
				
                else 
                {
                    this.type = T;
		}
                
                if (T_(CR))
                {
                    return true;
                }
            }
        }
        if (tokenSet.get(i).get(0).equals("PM") || tokenSet.get(i).get(0).equals("ROP") || tokenSet.get(i).get(0).equals("AND") || tokenSet.get(i).get(0).equals("&&") || tokenSet.get(i).get(0).equals("OR") || tokenSet.get(i).get(0).equals("||") || tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("]") || tokenSet.get(i).get(0).equals("}"))
        {
            return true;
        }
        return false;
    }
    
    /*
    private boolean T_()
    {
        if (tokenSet.get(i).get(0).equals("MDM")) 
        {
            i++;
            
            if (F()) 
            {
                
                if (T_())
                {
                    return true;
                }
            }
        }
        if (tokenSet.get(i).get(0).equals("PM"))
        {
            return true;
        }
        return false;
    }
    */
    
    private boolean F(int CR)
    {
        int isStaticM = 0;
	int flag = 0;
	int counter = 0;
                
        if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("this") ||tokenSet.get(i).get(0).equals ("super") ||tokenSet.get(i).get(0).equals ("new")||tokenSet.get(i).get(0).equals ("int_const") ||tokenSet.get(i).get(0).equals ("String_const") ||tokenSet.get(i).get(0).equals ("float_const") ||tokenSet.get(i).get(0).equals ("char_const")||tokenSet.get(i).get(0).equals ("(")||tokenSet.get(i).get(0).equals ("!"))
        {
            if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("this") ||tokenSet.get(i).get(0).equals ("super") ||tokenSet.get(i).get(0).equals ("new")) 
            {
                if (list1()) 
                {

                    if (RHP()) 
                    {
                        return true;
                    }
                }
            }

            else if(tokenSet.get(i).get(0).equals ("int_const") ||tokenSet.get(i).get(0).equals ("String_const") ||tokenSet.get(i).get(0).equals ("float_const") ||tokenSet.get(i).get(0).equals ("bool_const")||tokenSet.get(i).get(0).equals ("char_const"))
            {
                if(const_())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("("))
            {
                    i++;

                    if (OE(CR)) 
                    {
                        if (tokenSet.get(i).get(0).equals(")"))
                        {
                            i++;
                            return true;
                        }
                    }
            }

            else if(tokenSet.get(i).get(0).equals("!"))
            {
                    i++;
                    String Op = tokenSet.get(i).get(1);

                    if (F(CR))
                    {
                        if(!this.type.equals("bool"))
                        {
                            System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
                            this.type = "null";
			}
                        return true;
                    }
            }
        }
        return false;
    }
    
    private boolean list1()
    {
        if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("this") ||tokenSet.get(i).get(0).equals ("super") ||tokenSet.get(i).get(0).equals ("new")) 
        {
            if(tokenSet.get(i).get(0).equals("ID"))
            {
                name = tokenSet.get(i).get(1);
                i++;
                return true;
            }
        }

        else if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("this") ||tokenSet.get(i).get(0).equals ("super") ||tokenSet.get(i).get(0).equals ("new")) 
        {
            if(fn_call())
            {
                return true;
            }
        }

        else if(tokenSet.get(i).get(0).equals ("ID"))
        {
            if(arr_call())
            {
                return true;
            }
        }
        
        else if(tokenSet.get(i).get(0).equals("this"))
        {
            i++;
            
            if (RHP2()) 
            {
                return true;
            }
        }
        
        else if(tokenSet.get(i).get(0).equals("super"))
        {
            i++;
            
            if (RHP2()) 
            {
                return true;
            }
        }

        else if(tokenSet.get(i).get(0).equals ("new")) 
        {
            if(obj_dec())
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean RHP()
    {
        if(tokenSet.get(i).get(0).equals("."))
        {
            i++;
            
            if (RHP_())
            {
                return true;
            }
        }
        
        if(tokenSet.get(i).get(0).equals ("MDM"))
        {
            return true;
        }
        return false;
    }
    
    private boolean RHP_()
    {
        if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("this") ||tokenSet.get(i).get(0).equals ("super") ||tokenSet.get(i).get(0).equals ("new")) 
        {
            if (tokenSet.get(i).get(0).equals("ID")) 
            {
                if(arr_call())
                {

                    if (RHP()) 
                    {
                        return true;
                    }
                }
            }

            else if(tokenSet.get(i).get(0).equals("ID"))
            {
                name = tokenSet.get(i).get(1);
                i++;

                if (RHP())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("this") ||tokenSet.get(i).get(0).equals ("super") ||tokenSet.get(i).get(0).equals ("new")) 
            {
                if(fn_call()) 
                {

                    if (RHP())
                    {
                        return true;
                    }
                }
            }
           }
        return false;
    }
    
    private boolean RHP2()
    {
        if(tokenSet.get(i).get(0).equals("."))
        {
            i++;
            
            if (RHP_()) 
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean arr_call()
    {
            if(tokenSet.get(i).get(0).equals("ID"))
            {
                name = tokenSet.get(i).get(1);
                i++;

                if (tokenSet.get(i).get(0).equals("["))
                {
                    i++;

                    if (OE(CR))
                    {

                        if (tokenSet.get(i).get(0).equals("]"))
                        {
                            i++;
                            return true;
                        }
                    }
                }
            }
        return false;
    }
    
    private boolean fn_call()
    {
        if(tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new"))
        {
            if (tokenSet.get(i).get(0).equals("ID")) 
            {
                name = tokenSet.get(i).get(1);
                i++;

                if (P())
                {
                    return true;
                }
            }

            else if (tokenSet.get(i).get(0).equals("ID")) 
            {
                if(arr_call())
                {

                    if (z())
                    {
                        return true;
                    }
                }
            }

            else if(tokenSet.get(i).get(0).equals("this")) 
            {
                i++;

                if (z())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("super")) 
            {
                i++;

                if (z()) 
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("new")) 
            {
                if(obj_dec()) 
                {
                    if (z())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean P()
    {
        if(tokenSet.get(i).get(0).equals(".") || tokenSet.get(i).get(0).equals("("))
        {
            if(tokenSet.get(i).get(0).equals("."))
            {
                if(z())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("("))
            {
                i++;

                if (argu()) 
                {

                    if (tokenSet.get(i).get(0).equals(")")) 
                    {
                        i++;

                        if (y())
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean z()
    {
            if(tokenSet.get(i).get(0).equals(".")) 
            {

                if (z_()) 
                {
                    return true;
                }
            }
        return false;
    }
    
    private boolean z_()
    {
        if(tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("new"))
        {

            if(tokenSet.get(i).get(0).equals("ID"))
            {
                name = tokenSet.get(i).get(1);
                i++;

                if (z__())
                {
                    return true;
                }
            }

            else if (tokenSet.get(i).get(0).equals("ID"))
            {
                if (arr_call()) 
                {

                    if (z())
                    {
                        return true;
                    }
                }
            }

            else if (tokenSet.get(i).get(0).equals("new")) 
            {
                if(obj_dec()) 
                {

                    if (z())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean z__()
    {
        if(tokenSet.get(i).get(0).equals(".") || tokenSet.get(i).get(0).equals("("))
        {
            if (tokenSet.get(i).get(0).equals(".")) 
            {
                if(z())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("(")) 
            {
                i++;

                if (argu()) 
                {

                    if (tokenSet.get(i).get(0).equals(")"))
                    {
                        i++;

                        if (y())
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean y()
    {
        if (tokenSet.get(i).get(0).equals(".")) 
        {
            if (tokenSet.get(i).get(0).equals(".")) 
            {
                if(z())
                {
                    return true;
                }
            }
        }
        
        if (tokenSet.get(i).get(0).equals(".")) 
        {
            return true;
        }
        return false;
    }
    
    private boolean argu()
    {
        if (tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new")|| tokenSet.get(i).get(0).equals("int_const") || tokenSet.get(i).get(0).equals("String_const") || tokenSet.get(i).get(0).equals("float_const") || tokenSet.get(i).get(0).equals("bool_const") || tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("(")|| tokenSet.get(i).get(0).equals("!"))
        {
            if (tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new") || tokenSet.get(i).get(0).equals("int_const") || tokenSet.get(i).get(0).equals("String_const") || tokenSet.get(i).get(0).equals("float_const") || tokenSet.get(i).get(0).equals("bool_const") || tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("(") || tokenSet.get(i).get(0).equals("!")) 
            {
                if(OE(CR))
                {

                    if (argus())
                    {
                        return true;
                    }
                }
            }
        }
        
        if(tokenSet.get(i).get(0).equals(")"))
        {
            return true;
        }
        return false;
    }
    
    private boolean argus()
    {
            if(tokenSet.get(i).get(0).equals(","))
            {
                i++;
                
                if(OE(CR)) 
                {
                    pl = pl + "," + this.type;

                    if (argus())
                    {
                        return true;
                    }
                }
            }
        
        if(tokenSet.get(i).get(0).equals(")"))
        {   
            return true;
        }
        return false;
    }
    
    private boolean obj_dec()
    {
            if(tokenSet.get(i).get(0).equals("new")) 
            {
                i++;

                if (tokenSet.get(i).get(0).equals("ID"))
                {
                    name = tokenSet.get(i).get(1);
                    i++;

                    if (tokenSet.get(i).get(0).equals("("))
                    {

                        if (argu()) 
                        {

                            if (tokenSet.get(i).get(0).equals(")"))
                            {
                                return true;                            
                            }
                        }
                    }
                }
            }
        return false;
    }
    
    private boolean try_catch(String type,int CR,int FR)
    {
            if(tokenSet.get(i).get(0).equals("try")) 
            {
                String KW=tokenSet.get(i).get(0);
                i++;

                if (tokenSet.get(i).get(0).equals("{"))
                {
                    s++;
                    database.createScope(KW, s, isLoop);
                    i++;

                    if (MST(type, CR, FR))
                    {

                        if (tokenSet.get(i).get(0).equals("}")) 
                        {
                            s--;
                            database.destroyScope();
                            i++;

                            if (catch_(type, CR, FR))
                            {

                                if (finally_())
                                {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        return false;
    }
    
    private boolean catch_(String type,int CR,int FR)
    {
            if(tokenSet.get(i).get(0).equals("catch"))
            {
                String KW=tokenSet.get(i).get(0);
                i++;

                if (tokenSet.get(i).get(0).equals("(")) 
                {
                    i++;

                    if (tokenSet.get(i).get(0).equals("ID")) 
                    {
                        name=tokenSet.get(i).get(1);
                        i++;

                        if (tokenSet.get(i).get(0).equals("ID")) 
                        {
                            name=tokenSet.get(i).get(1);
                            i++;

                            if (tokenSet.get(i).get(0).equals(")")) 
                            {
                                i++;

                                if (tokenSet.get(i).get(0).equals("{")) 
                                {
                                    s++;
                                    database.createScope(KW, s, isLoop);
                                    i++;

                                    if (MST(type, CR, FR))
                                    {
                                        if (tokenSet.get(i).get(0).equals("}"))
                                        {
                                            s--;
                                            database.destroyScope();
                                            i++;

                                            if (catch_(type, CR, FR))
                                            {
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        
        if(tokenSet.get(i).get(0).equals("finally"))
        {
            return true;
        }
        return false;
    }
    
    private boolean finally_()
    {
            if(tokenSet.get(i).get(0).equals("finally"))
            {
                String KW=tokenSet.get(i).get(0);
                i++;

                if (tokenSet.get(i).get(0).equals("{")) 
                {
                    s++;
                    database.createScope(KW, s, isLoop);
                    i++;

                    if (MST(type, CR, FR))
                    {

                        if (tokenSet.get(i).get(0).equals("}")) 
                        {
                            s--;
                            database.destroyScope();
                            i++;
                            return true;
                        }
                    }
                }
            }
        return false;
    }
    
    private boolean array()
    {
        if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("int") ||tokenSet.get(i).get(0).equals("float") ||tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char"))
        {    
            if(data_type())
            {
                if (tokenSet.get(i).get(0).equals("[")) 
                {
                    i++;

                    if (I()) 
                    {

                        if (tokenSet.get(i).get(0).equals("]")) 
                        {
                            i++;

                            if (X())
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean X()
    {
        if(tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("["))
        {
            if(tokenSet.get(i).get(0).equals("ID"))
            {
                name=tokenSet.get(i).get(1);
                i++;

                if (A1())
                {

                    if (tokenSet.get(i).get(0).equals(";")) 
                    {
                        i++;
                        return true;
                    }
                }
            }

            else if(tokenSet.get(i).get(0).equals("["))
            {
                i++;

                if (I()) 
                {

                    if (tokenSet.get(i).get(0).equals("]")) 
                    {
                        i++;

                        if (tokenSet.get(i).get(0).equals("ID"))
                        {
                            name=tokenSet.get(i).get(1);
                            i++;

                            if (A3()) 
                            {
                                if (tokenSet.get(i).get(0).equals(";")) 
                                {
                                return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean A1()
    {
            if(tokenSet.get(i).get(0).equals("="))
            {
                i++;

                if (A1_())
                {
                    return true;
                }
            }
        return false;
    }
    
    private boolean A1_()
    {
        if(tokenSet.get(i).get(0).equals("{") || tokenSet.get(i).get(0).equals("new"))
        {
            if(tokenSet.get(i).get(0).equals("{"))
            {
                i++;

                if (A2())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("new"))
            {
                i++;

                if (data_type())
                {

                    if (tokenSet.get(i).get(0).equals("[")) 
                    {
                        i++;

                        if (tokenSet.get(i).get(0).equals("]"))
                        {
                            i++;
                            return true;
                        }
                    }
                 }
            }
        }
        return false;
    }
    
    private boolean A2()
    {
        if (tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new") || tokenSet.get(i).get(0).equals("int_const") || tokenSet.get(i).get(0).equals("String_const") || tokenSet.get(i).get(0).equals("float_const") || tokenSet.get(i).get(0).equals("bool_const") || tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("(") || tokenSet.get(i).get(0).equals("!"))
        {
            if(OE(CR)) 
            {

                if (A2_()) 
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean A2_()
    {
        if(tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("}"))
        {
            if(tokenSet.get(i).get(0).equals(","))
            {
                i++;

                if(A2()) 
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("}")) 
            {
                i++;
                return true;
            }
        }
        return false;
    }
    
    private boolean I()
    {
        if (tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new") || tokenSet.get(i).get(0).equals("int_const") || tokenSet.get(i).get(0).equals("String_const") || tokenSet.get(i).get(0).equals("float_const") || tokenSet.get(i).get(0).equals("bool_const") || tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("(") || tokenSet.get(i).get(0).equals("!"))
        {
            if(OE(CR)) 
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean A3()
    {
        if(tokenSet.get(i).get(0).equals("=")) 
        {
            i++;
            
            if (A3_())
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean A3_()
    {
        if(tokenSet.get(i).get(0).equals("{") || tokenSet.get(i).get(0).equals("new")) 
        {
            if(tokenSet.get(i).get(0).equals("{")) 
            {
                i++;

                if (tokenSet.get(i).get(0).equals("{"))
                {
                    i++;

                    if (A4())
                    {
                        return true;
                    }
                }
            }

            else if(tokenSet.get(i).get(0).equals("new"))
            {
                i++;

                if(data_type())
                {
                    if(tokenSet.get(i).get(0).equals("["))
                    {
                        i++;

                        if(tokenSet.get(i).get(0).equals("]"))
                        {
                            i++;

                            if(tokenSet.get(i).get(0).equals("["))
                            {
                                i++;

                                if(tokenSet.get(i).get(0).equals("]"))
                                {
                                    i++;
                                    return true;
                                }
                            }
                        }
                    }
                 }
            }
        }
        return false;
    }
    
    private boolean A4()
    {
        if (tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new") || tokenSet.get(i).get(0).equals("int_const") || tokenSet.get(i).get(0).equals("String_const") || tokenSet.get(i).get(0).equals("float_const") || tokenSet.get(i).get(0).equals("bool_const") || tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("(") || tokenSet.get(i).get(0).equals("!"))
        {
            if(OE(CR))
            {
                if (A4_())
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean A4_()
    {
        if(tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("}"))
        {
            if(tokenSet.get(i).get(0).equals(","))
            {
                i++;

                if (A4())
                {
                    return true;
                }
            }

           else if(tokenSet.get(i).get(0).equals("}")) 
           {
               i++;

               if (A5()) 
               {
                   return true;
               }
           }
        }
        return false;
    }
    
    private boolean A5()
    {
        if(tokenSet.get(i).get(0).equals(","))
        {
            i++;
            
            if(tokenSet.get(i).get(0).equals("{"))
            {
                i++;
                
                if (A5_())
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean A5_()
    {
        if (tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new") || tokenSet.get(i).get(0).equals("int_const") || tokenSet.get(i).get(0).equals("String_const") || tokenSet.get(i).get(0).equals("float_const") || tokenSet.get(i).get(0).equals("bool_const") || tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("(") || tokenSet.get(i).get(0).equals("!"))
        {
            if(OE(CR))
            {

                if (A6())
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean A6()
    {
        if(tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("}"))
        {
            if(tokenSet.get(i).get(0).equals(","))
            {
                i++;

                if(A5())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("}"))
            {
                i++;

                if (tokenSet.get(i).get(0).equals("}"))
                {
                    i++;
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean return_(String type,int CR,int FR)
    {
        if(tokenSet.get(i).get(0).equals("return")) 
        {
            i++;
            
            if (OE(CR))
            {
                Scope scope=database.getScope();
		if(scope!=null)
                {
                    if(this.type.equals(";")) 
                    {
                        if(!type.equals("void")) 
                        {
                            System.out.println("Type must be void,line #="+tokenSet.get(i).get(2));
			}
                    }
		}
                
                else if (!this.type.equals(type))
                {
                    System.out.println("Return type Error,,line #="+tokenSet.get(i).get(2));
                    isReturn=1;
		}
                
                else
                {
                    isReturn=1;
		}
                
                this.type="null";
		return true;
            }
        }
        return false;
    }
    
    private boolean this_()
    {
        if(tokenSet.get(i).get(0).equals("this"))
        {
            i++;
            
            if (tokenSet.get(i).get(0).equals(".")) 
            {
                i++;
                
                if (tokenSet.get(i).get(0).equals("ID"))
                {
                    name=tokenSet.get(i).get(1);
                    i++;
                    
                    if (tokenSet.get(i).get(0).equals("=")) 
                    {
                        i++;
                        
                        if (tokenSet.get(i).get(0).equals("ID"))
                        {
                            name=tokenSet.get(i).get(1);
                            i++;
                            
                            if (tokenSet.get(i).get(0).equals(";"))
                            {
                                i++;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        
        else if(tokenSet.get(i).get(0).equals("this"))
        {
            i++;
            
            if (tokenSet.get(i).get(0).equals("."))
            {
                i++;
                if (function(type, FR, CR))
                {
                    if (tokenSet.get(i).get(0).equals(";"))
                    {
                        i++;
                        return true;
                    }
                }
            }
        }
        
        else if(tokenSet.get(i).get(0).equals("this")) 
        {
            i++;
            
            if (tokenSet.get(i).get(0).equals("(")) 
            {
                i++;
                
                if (parameter())
                {
                    if (tokenSet.get(i).get(0).equals(")")) 
                    {
                        i++;
                        
                        if (tokenSet.get(i).get(0).equals(";"))
                        {
                            i++;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean super_()
    {
        if(tokenSet.get(i).get(0).equals("super")) 
        {
            i++;
            
            if (tokenSet.get(i).get(0).equals(".")) 
            {
                i++;
                
                if (function(type, FR, CR)) 
                {
                    
                    if (tokenSet.get(i).get(0).equals(";"))
                    {
                        i++;
                        return true;
                    }
                }
            }
        }
        
        else if(tokenSet.get(i).get(0).equals("super"))
        {            
            i++;
            
            if (tokenSet.get(i).get(0).equals("(")) 
            {
                i++;
                
                if (parameter())
                {
                    
                    if (tokenSet.get(i).get(0).equals(")")) 
                    {
                        i++;
                        
                        if (tokenSet.get(i).get(0).equals(";"))
                        {
                            i++;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean null_()
    {
        if (tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char")) 
        {
            if(acc_modifier())
            {

                if (c_modifier1()) 
                {

                    if (data_type()) 
                    {

                        if (tokenSet.get(i).get(0).equals("ID")) 
                        {
                            name=tokenSet.get(i).get(1);
                            i++;

                            if (tokenSet.get(i).get(0).equals("=")) 
                            {
                                i++;

                                if (tokenSet.get(i).get(0).equals("null")) 
                                {
                                    i++;
                                    return true;
                                 }
                            }
                         }
                    }
                 }
            }
        }
        return false;
    }
    
    /*
    private boolean parameter_()
    {
        if(tokenSet.get(i).get(0).equals("ID"))
        {
            i++;
            
            if (para_list_())
            {
                return true;
            }
        }
        return false;
    }

    
    private boolean para_list_()
    {
        if(tokenSet.get(i).get(0).equals(",")) 
        {
            i++;
            
            if(tokenSet.get(i).get(0).equals("ID"))
            {
                i++;
            
                if (para_list_()) 
                {
                    return true;
                }
            }
        }
        return false;
    }
    */
    
    private boolean package_()
    {
        if(tokenSet.get(i).get(0).equals("package")) 
        {
            i++;
            
            if (tokenSet.get(i).get(0).equals("ID")) 
            {
                name=tokenSet.get(i).get(1);
                i++;
                
                if (tokenSet.get(i).get(0).equals(";")) 
                {
                    i++;
                    return true;
                }
            }
       }
        return false;
    }
    
    private boolean import_()
    {
        if(tokenSet.get(i).get(0).equals("import"))
        {
            i++;
            
            if (pkg_name()) 
            {
                
                if (tokenSet.get(i).get(0).equals("."))
                {
                    i++;
                    
                    if (name())
                    {
                        
                        if (tokenSet.get(i).get(0).equals("."))
                        {
                            i++;
                            
                            if (Class_name())
                            {
                                
                                if (tokenSet.get(i).get(0).equals(";")) 
                                {
                                    i++;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean pkg_name()
    {
        if(tokenSet.get(i).get(0).equals("ID")) 
        {
            name=tokenSet.get(i).get(1);
            i++;
            return true;
        }
        return false;
    }
    
    private boolean name()
    {
        if(tokenSet.get(i).get(0).equals("ID"))
        {
            name=tokenSet.get(i).get(1);
            i++;
            return true;
        }
        return false;
    }
    
    private boolean Class_name()
    {
        if(tokenSet.get(i).get(0).equals("ID"))
        {
            name=tokenSet.get(i).get(1);
            i++;
            return true;
        }

        return false;
    }
    
    private boolean function(String type,int FR,int CR)
    {
        if (tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals("void") || tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("abstract")) 
        {
            if (tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals("void") || tokenSet.get(i).get(0).equals("ID")) 
            {
                if(simple_fun(type, CR))
                {

                    if (tokenSet.get(i).get(0).equals(";"))
                    {
                        i++;
                        return true;
                    }
                }
            }
            
            else if (tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("abstract")) 
            {
                if(abs_fun(type, CR)) 
                {

                    if (tokenSet.get(i).get(0).equals(";"))
                    {
                        i++;
                        return true;
                    }
                }
            }
            
            else if (tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals("void") || tokenSet.get(i).get(0).equals("ID")) 
            {
                if(interface_fun(type, CR))
                {
                    if (tokenSet.get(i).get(0).equals(";"))
                    {
                        i++;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean simple_fun(String type, int CR)
    {
        if (tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals("void") || tokenSet.get(i).get(0).equals("ID")) 
        {
            if(acc_modifier())
            {

                if (c_modifier1()) 
                {

                    if (ret_type()) 
                    {

                        if (tokenSet.get(i).get(0).equals("ID"))
                        {
                            name=tokenSet.get(i).get(1);
                            i++;

                            if (tokenSet.get(i).get(0).equals("(")) 
                            {
                                i++;

                                if (parameter()) 
                                {

                                    if (tokenSet.get(i).get(0).equals(")"))
                                    {
                                        al=type+"->"+type;
                                        //int FR=database.create_funTable();

                                        if(!database.insert_DT(CR, FR, name, al, AM, isStatic, isFinal, isAbstract)) 
                                        {
                                            System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
                                        }                          
                                        
                                        i++;

                                        if (tokenSet.get(i).get(0).equals("{"))
                                        {
                                            String KW = "";
                                            s++;
                                            database.createScope(KW, s, isLoop);
                                            i++;

                                            if (SST(type, CR, FR))
                                            {

                                                if (MST(type, CR, FR))
                                                {

                                                    if (tokenSet.get(i).get(0).equals("}")) 
                                                    {
                                                        s--;
                                                        database.destroyScope();
                                                        i++;
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                 }
                             }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean ret_type()
    {
        if (tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals("void") || tokenSet.get(i).get(0).equals("ID")) 
        {
            if (tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char")) 
            {
                if(data_type())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("void"))
            {
                ar_type=tokenSet.get(i).get(0);
                i++;
                return true;
            }

            else if(tokenSet.get(i).get(0).equals("ID"))
            {
                name=tokenSet.get(i).get(1);
                i++;

                if (x1()) 
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean x1()
    {
            if (tokenSet.get(i).get(0).equals("["))
            {
                i++;

                if (tokenSet.get(i).get(0).equals("]"))
                {
                    i++;
                    return true;
                }
            }
            
            if (tokenSet.get(i).get(0).equals("ID"))
            {
                name=tokenSet.get(i).get(1);
                return true;
            }

        return false;
    }
    
    private boolean parameter()
    {
        if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") ||tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char"))
        {
            if (data_type()) 
            {

                if (tokenSet.get(i).get(0).equals("ID"))
                {
                    name=tokenSet.get(i).get(1);
                    i++;
                    
                    al=ar_type;
					
                    if(!database.insert_FT(FR, name, al, CR)) 
                    {
			System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
                    }

                    if (para_list())
                    {
                        return true;
                    }
                }
            }
        }
        
        if(tokenSet.get(i).get(0).equals (")"))
        {
            return true;
        }
        return false;
    }
    
    private boolean para_list()
    {
            if (tokenSet.get(i).get(0).equals(","))
            {
                i++;

                if (data_type())
                {

                    if (tokenSet.get(i).get(0).equals("ID"))
                    {
                        name=tokenSet.get(i).get(1);
                        i++;

                        al=al+","+ar_type;
					
			if(!database.insert_FT(FR, name, ar_type, CR)) 
                        {
                            System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
			}
                        
                        if (para_list())
                        {
                        return true;
                        }
                    }
                }
            }
        
        if(tokenSet.get(i).get(0).equals (")"))
        {
            return true;
        }
        return false;
    }
    
    private boolean abs_fun(String type ,int CR)
    {
        if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("abstract"))
        {
            Type_MT T1=database.lookUp_MT(name);
            Type_DT T2=database.lookUp_fn_DT(FR, name, al);
            
            if (acc_modifier())
            {

                if (tokenSet.get(i).get(0).equals("abstract"))
                {
                    TM=tokenSet.get(i).get(0);
                    i++;

                    if (ret_type()) 
                    {
                        if (tokenSet.get(i).get(0).equals("ID"))
                        {
                            name=tokenSet.get(i).get(1);
                            i++;

                            if (tokenSet.get(i).get(0).equals("("))
                            {
                                i++;

                                if (parameter()) 
                                {
                                    if (tokenSet.get(i).get(0).equals(")")) 
                                    {
                                        i++;
                                        
                                        al=type+"->"+type;
                                        //int FR=database.create_funTable();

                                        if(!database.insert_DT(CR, FR, name, al, AM, isStatic, isFinal, isAbstract)) 
                                        {
                                            System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
                                        } 
                                        
                                        if (T1!=null) 
                                        {
                                            if (T2!=null) 
                                            {

                                                if(T2.equals("abstract")) 
                                                {
                                                    System.out.println("Abstract method does not have body,line #="+tokenSet.get(i).get(2));
                                                }		                                           
                                            }
                                        }
                                        
                                        if(T1!=null)
                                        {
                                            if(!T1.type.equals("class")) 
                                            {
                                                System.out.println("Only make abstract method in abstract class,line #="+tokenSet.get(i).get(2));			
                                            }
                                            
                                            else if(!T1.equals("abstract"))
                                            {
                                                System.out.println("Class must be abstract, line #="+tokenSet.get(i).get(2));
                                            }
                                            
                                            else if (T2.equals("abstract")) 
                                            {
						System.out.println("Method must be abstract,line #="+tokenSet.get(i).get(2));
                                            }
                                        }
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean interface_fun(String type ,int CR)
    {
        if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char") || tokenSet.get(i).get(0).equals ("void") || tokenSet.get(i).get(0).equals ("ID"))
        {
            Type_MT T1=database.lookUp_MT(name);
            Type_DT T2=database.lookUp_fn_DT(FR, name, al);
            
            if (acc_modifier()) 
            {
                if (ret_type())
                {

                    if (tokenSet.get(i).get(0).equals("ID")) 
                    {
                        name=tokenSet.get(i).get(1);
                        i++;

                        if (tokenSet.get(i).get(0).equals("("))
                        {
                            i++;

                            if (parameter())
                            {

                                if (tokenSet.get(i).get(0).equals(")")) 
                                {
                                    i++;
                                    
                                    al=type+"->"+type;
                                        //int FR=database.create_funTable();

                                        if(!database.insert_DT(CR, FR, name, al, AM, isStatic, isFinal, isAbstract)) 
                                        {
                                            System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
                                        }
                                        
                                        /*
                                        if (T1!=null) 
                                        {
                                            if (T2!=null) 
                                            {

                                                if(T2.equals("abstract")) 
                                                {
                                                    System.out.println("Abstract method does not have body,line #="+tokenSet.get(i).get(2));
                                                }		                                           
                                            }
                                        }
                                        
                                        if(T1!=null)
                                        {
                                            if(!T1.type.equals("class")) 
                                            {
                                                System.out.println("Only make abstract method in abstract class,line #="+tokenSet.get(i).get(2));			
                                            }
                                            
                                            else if(!T1.equals("abstract"))
                                            {
                                                System.out.println("Class must be abstract, line #="+tokenSet.get(i).get(2));
                                            }
                                            
                                            else if (T2.equals("abstract")) 
                                            {
						System.out.println("Method must be abstract,line #="+tokenSet.get(i).get(2));
                                            }
                                        }
                                        */
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean class_dec()
    {
        if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("class") || tokenSet.get(i).get(0).equals ("interface") || tokenSet.get(i).get(0).equals ("{") || tokenSet.get(i).get(0).equals ("abstract"))
        {
            if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("class"))
            {
                if(acc_modifier())
                {

                    if(c_modifier1()) 
                    {

                        if (tokenSet.get(i).get(0).equals("class"))
                        {
                            type = tokenSet.get(i).get(0);
                            i++;

                            if (tokenSet.get(i).get(0).equals("ID")) 
                            {
                                name=tokenSet.get(i).get(1);
                                i++;

                                if (T__())
                                {
                                    return true;
                                }
                            }
                        }
                    }
               }
            }

            else if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("class"))
            { 
                if (acc_modifier())
                {

                     if (TM()) 
                     {

                         if (tokenSet.get(i).get(0).equals("class")) 
                         {
                             type = tokenSet.get(i).get(0);
                             i++;

                             if (tokenSet.get(i).get(0).equals("ID"))
                             {
                                 name=tokenSet.get(i).get(1);
                                 i++;

                                 if (inherits(type)) 
                                 {
                                     int CR=database.create_dataTable();
								
                                     if(!database.insert_MT(CR,name, type, TM, ext, imp)) 
                                     {
                                        System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
                                     }
                                            
                                     if (U()) 
                                    {
                                        return true;
                                    }
                                 }
                             }
                          }
                     }
                }
            }

           else if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("interface") || tokenSet.get(i).get(0).equals ("{"))
           {
                if (interface_(type))
                {
                    int CR=database.create_dataTable();
								
                    if(!database.insert_MT(CR,name, type, TM, ext, imp)) 
                    {
                        System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
                    }
                    
                   if (U()) 
                   {
                       return true;
                   }
                }
           }

           else if(tokenSet.get(i).get(0).equals ("class"))
           {
                if (implements_(type))
                {
                    int CR=database.create_dataTable();
								
                    if(!database.insert_MT(CR,name, type, TM, ext, imp)) 
                    {
                        System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
                    }
                    
                    if (T__()) 
                    {
                        return true;
                    }
                }
           }

           else if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("abstract") || tokenSet.get(i).get(0).equals ("{"))
           {
                if (abstract_(type))
                {
                    int CR=database.create_dataTable();
								
                    if(!database.insert_MT(CR,name, type, TM, ext, imp)) 
                    {
                        System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
                    }
                    
                    if (U()) 
                    {
                        return true;
                    }
                }
           }

           else if (extends_(type))
           {
               int CR=database.create_dataTable();
								
               if(!database.insert_MT(CR,name, type, TM, ext, imp)) 
               {
                    System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
               }
               
               if (T__()) 
               {
                   return true;
               }
           }
        }
        return false;
    }
    
    private boolean TM()
    {
        if (tokenSet.get(i).get(0).equals("static"))
        {
            TM = tokenSet.get(i).get(0);
            isStatic = true;
            i++;
            return true;
        }
        
        if(tokenSet.get(i).get(0).equals ("class"))
        {
            return true;
        }
        return false;
    }
    
    private boolean T__()
    {
            if (tokenSet.get(i).get(0).equals("{"))
            {
                String KW = "";
                s++;
                database.createScope(KW, s, isLoop);
                i++;

                if (cl_SST()) 
                {

                    if (cl_MST()) 
                    {

                        if (tokenSet.get(i).get(0).equals("}"))
                        {
                            s--;
                            database.destroyScope();
                            i++;
                            return true;
                        }
                    }
                }
            }
        return false;
    }
    
    private boolean cl_SST()
    {
        if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char") || tokenSet.get(i).get(0).equals ("void") || tokenSet.get(i).get(0).equals ("ID"))
        {
            if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char"))
            {
                if (class_var_dec())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char") || tokenSet.get(i).get(0).equals ("void") || tokenSet.get(i).get(0).equals ("ID"))
            {
                if (simple_fun(type, CR))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean cl_MST()
    {
        if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char") || tokenSet.get(i).get(0).equals ("void") || tokenSet.get(i).get(0).equals ("ID"))
        {
            if (cl_SST()) 
            {
                if (cl_MST()) 
                {
                    return true;
                }
            }
        }

        if (tokenSet.get(i).get(0).equals("}")) 
        {
            return true;
        }
        return false;
    }
    
    private boolean class_var_dec()
    {
        if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char"))
        {
            if (acc_modifier())
            {
                if (c_modifier1())
                {
                    if (declare(CR, FR))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean inherits(String type)
    {
        if (tokenSet.get(i).get(0).equals("extends")) 
        {
            i++;
            
            if (tokenSet.get(i).get(0).equals("ID"))
            {
                    name=tokenSet.get(i).get(1);
                    i++;
                    
                    Type_MT T=database.lookUp_MT(name);
                    
                    if(T == null)
                    {
                        System.out.println("Undeclared Identifier!");
                    }
                    
                    if(TM.equals("final") && type.equals("class"))
                    {
                        System.out.println("Final class can't be inherited");                      
                    }
                    return true;
            }
            
        }

        if(tokenSet.get(i).get(0).equals("{")) 
        {
                return true;
        } 
        return false;
    }
    
    private boolean interface_(String type)
    {
        if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("interface"))
        {
            if (acc_modifier())
            {

                if (tokenSet.get(i).get(0).equals("interface")) 
                {
                    type = tokenSet.get(i).get(0);
                    i++;

                    if (tokenSet.get(i).get(0).equals("ID")) 
                    {
                        name=tokenSet.get(i).get(1);
                        i++;
                        
                        Type_MT T=database.lookUp_MT(name);
                    
                        if(T == null)
                        {
                            System.out.println("Undeclared Identifier!");
                        }

                        if(type.equals("interface"))
                        {
                            System.out.println("Interface class has no body");                      
                        }
                        
                    return true;
                    }
                }
            }
        }
        
        if (tokenSet.get(i).get(0).equals("{")) 
        {
            return true;
        } 
        return false;
    }
    
    private boolean implements_(String type)
    {
            if (tokenSet.get(i).get(0).equals("class")) 
            {
                type=tokenSet.get(i).get(0);
                i++;

                if (tokenSet.get(i).get(0).equals("ID")) 
                {
                    name=tokenSet.get(i).get(1);
                    i++;
                    
                    Type_MT T1=database.lookUp_MT(name);
                    
                    if(T1 == null)
                    {
                        System.out.println("Undeclared Identifier!");
                    }

                    if (tokenSet.get(i).get(0).equals("implements")) 
                    {
                        i++;

                        if (tokenSet.get(i).get(0).equals("ID")) 
                        {
                            name=tokenSet.get(i).get(1);
                            i++;
                            
                            Type_MT T=database.lookUp_MT(name);
                    
                            if(T == null)
                            {
                                System.out.println("Undeclared Identifier!");
                            }
                          
                            if (imp_())
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        return false;
    }
    
    private boolean imp_()
    {
        if (tokenSet.get(i).get(0).equals(","))
        {
            i++;
            
            if (tokenSet.get(i).get(0).equals("ID")) 
            {
                name=tokenSet.get(i).get(1);
                 i++;
                 
                 Type_MT T=database.lookUp_MT(name);
                    
                 if(T == null)
                 {
                    System.out.println("Undeclared Identifier!");
                 }
                 
                 pl += "," + name;
                 
                 if (imp_()) 
                 {
                     return true;
                 }
            }
        }
        
        if (tokenSet.get(i).get(0).equals("{")) 
        {
            return true;
        } 
        return false;
    }
    
    private boolean abstract_(String type)
    {
        if (tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("abstract")) 
        {
            if (acc_modifier()) 
            {

                if (tokenSet.get(i).get(0).equals("abstract")) 
                {
                    TM=tokenSet.get(i).get(0);
                    i++;

                    if (tokenSet.get(i).get(0).equals("class"))
                    {
                        type=tokenSet.get(i).get(0);
                        i++;

                        if (tokenSet.get(i).get(0).equals("ID")) 
                        {
                            name=tokenSet.get(i).get(1);
                            i++;
                            
                            Type_MT T=database.lookUp_MT(name);
                    
                            if(T == null)
                            {
                                System.out.println("Undeclared Identifier!");
                            }

                            if(TM.equals("abstract"))
                            {
                                System.out.println("Abstact class has no body and has only abstact function");                      
                            }
                            
                            return true;
                        }
                    }
                }
            }
        }
        
        if (tokenSet.get(i).get(0).equals("{")) 
        {
            return true;
        } 
        return false;
    }
    
    private boolean extends_(String type)
    {
        if (tokenSet.get(i).get(0).equals("class"))
        {
            type=tokenSet.get(i).get(0);
            i++;
            
            if (tokenSet.get(i).get(0).equals("ID"))
            {
                name=tokenSet.get(i).get(1);                                
                i++;
                
                Type_MT T=database.lookUp_MT(name);
                
                if(T == null)
                {
                    System.out.println("Undeclared Identifier!");
                 
                }
                
                if (tokenSet.get(i).get(0).equals("extends"))
                {
                    i++;
                    
                    if (tokenSet.get(i).get(0).equals("ID"))
                    {
                        name=tokenSet.get(i).get(1);
                        i++;
                        
                        Type_MT T1=database.lookUp_MT(name);
                
                        if(T1 == null)
                        {
                            System.out.println("Undeclared Identifier!");

                        }
                        
                        return true;
                    }
                }
            }
        }
        
        if (tokenSet.get(i).get(0).equals("{")) 
        {
            return true;
        } 
        return false;
    }
    
    private boolean U()
    {
            if (tokenSet.get(i).get(0).equals("{"))
            {
                String KW = "";
                s++;
                database.createScope(KW, s, isLoop);
                i++;

                if (ab_SST()) 
                {

                    if (ab_MST()) 
                    {

                        if (tokenSet.get(i).get(0).equals("}")) 
                        {
                            s--;
                            database.destroyScope();
                            i++;
                            return true;
                        }
                    }
                }
            }
        return false;
    }
    
    private boolean ab_SST()
    {
        if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char") || tokenSet.get(i).get(0).equals ("void") || tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("abstract"))
        {
            if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char"))
            {
                if (class_var_dec())
                {
                    return true;
                }
            }
            
            else if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char") || tokenSet.get(i).get(0).equals ("void") || tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("abstract"))
            {
                if (function(type, FR, CR))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean ab_MST()
    {
        if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char") || tokenSet.get(i).get(0).equals ("void") || tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("abstract"))
        {
            if (ab_SST())
            {

              if (ab_MST())
              {
                  return true;
              }
            }
        }
          
        if (tokenSet.get(i).get(0).equals("}"))
        {
             return true;
        }
        return false;
    }
    
    private boolean body(String type,String KW,int CR,int FR)
    {
        if (tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals("{"))
        {
            if (tokenSet.get(i).get(0).equals(";"))
            {
                i++;
                return true;
            }

            else if (tokenSet.get(i).get(0).equals("{"))
            {
                i++;
                s++;
                database.createScope(KW, s, isLoop);

                if (MST(type, CR, FR)) 
                {

                    if (tokenSet.get(i).get(0).equals("}")) 
                    {
                        Scope temp=database.getScope();
					
			if(temp.KW.equals("while") || temp.KW.equals("do") || temp.KW.equals("for")) 
                        {
                            isLoop--;
			}
                        
			s--;			
                        database.destroyScope();
                        i++;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean body1()
    {
        if (tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals("while") || tokenSet.get(i).get(0).equals("for")  || tokenSet.get(i).get(0).equals("if") || tokenSet.get(i).get(0).equals("do")  || tokenSet.get(i).get(0).equals("continue") || tokenSet.get(i).get(0).equals("break") || tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals ("inc_dec") || tokenSet.get(i).get(0).equals("++") || tokenSet.get(i).get(0).equals("--") || tokenSet.get(i).get(0).equals("try") || tokenSet.get(i).get(0).equals("int_const") || tokenSet.get(i).get(0).equals("String_const") || tokenSet.get(i).get(0).equals("float_const") || tokenSet.get(i).get(0).equals("bool_const") || tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("(") || tokenSet.get(i).get(0).equals("!") || tokenSet.get(i).get(0).equals("return") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("abstract") || tokenSet.get(i).get(0).equals("void") || tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals("{"))
        {
            if (tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals("while") || tokenSet.get(i).get(0).equals("for")  || tokenSet.get(i).get(0).equals("if") || tokenSet.get(i).get(0).equals("do")  || tokenSet.get(i).get(0).equals("continue") || tokenSet.get(i).get(0).equals("break") || tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals("++") || tokenSet.get(i).get(0).equals("--") || tokenSet.get(i).get(0).equals("try") || tokenSet.get(i).get(0).equals("int_const") || tokenSet.get(i).get(0).equals("String_const") || tokenSet.get(i).get(0).equals("float_const") || tokenSet.get(i).get(0).equals("bool_const") || tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("(") || tokenSet.get(i).get(0).equals("!") || tokenSet.get(i).get(0).equals("return") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("abstract") || tokenSet.get(i).get(0).equals("void"))
            {
                if (SST(type, CR, FR))
                {
                    return true;
                }
            }

            else if (tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals("{"))
            {
                if (body(type, KW, CR, FR))
                {
                   return true;
                }
            }
        }
            
            if (tokenSet.get(i).get(0).equals("main") || tokenSet.get(i).get(0).equals("}"))
            {
                   return true;
            }
        return false;
    }
    
    private boolean MST(String type,int CR,int FR)
    {
        //if (tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("while") || tokenSet.get(i).get(0).equals("for")  || tokenSet.get(i).get(0).equals("if") || tokenSet.get(i).get(0).equals("do")  || tokenSet.get(i).get(0).equals("continue") || tokenSet.get(i).get(0).equals("break") || tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals ("inc_dec") || tokenSet.get(i).get(0).equals("++") || tokenSet.get(i).get(0).equals("--") || tokenSet.get(i).get(0).equals("try") || tokenSet.get(i).get(0).equals("return") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("abstract") || tokenSet.get(i).get(0).equals("void"))
        //{ 
        if (tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("while") || tokenSet.get(i).get(0).equals("for")  || tokenSet.get(i).get(0).equals("if") || tokenSet.get(i).get(0).equals("do")  || tokenSet.get(i).get(0).equals("continue") || tokenSet.get(i).get(0).equals("break") || tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals ("inc_dec") || tokenSet.get(i).get(0).equals("++") || tokenSet.get(i).get(0).equals("--") || tokenSet.get(i).get(0).equals("try") || tokenSet.get(i).get(0).equals("int_const") || tokenSet.get(i).get(0).equals("String_const") || tokenSet.get(i).get(0).equals("float_const") || tokenSet.get(i).get(0).equals("bool_const") || tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("(") || tokenSet.get(i).get(0).equals("!") || tokenSet.get(i).get(0).equals("return") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("abstract") || tokenSet.get(i).get(0).equals("void"))
        {
            if(isReturn==1)
            {
		System.out.println("Invalid declaration after return stmt,line #="+tokenSet.get(i).get(2));
            }
            
            if(SST(type, CR, FR))
            {
                if (MST(type, CR, FR))
                {
                    return true;
                }
            }
        }
            
            if (tokenSet.get(i).get(0).equals("}"))
            {
                   return true;
            }   
        return false;
    }
    
    private boolean SST(String type,int CR,int FR)
    {
        if (tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("while") || tokenSet.get(i).get(0).equals("for")  || tokenSet.get(i).get(0).equals("if") || tokenSet.get(i).get(0).equals("do")  || tokenSet.get(i).get(0).equals("continue") || tokenSet.get(i).get(0).equals("break") || tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals ("inc_dec") || tokenSet.get(i).get(0).equals("++") || tokenSet.get(i).get(0).equals("--") || tokenSet.get(i).get(0).equals("try") || tokenSet.get(i).get(0).equals("int_const") || tokenSet.get(i).get(0).equals("String_const") || tokenSet.get(i).get(0).equals("float_const") || tokenSet.get(i).get(0).equals("bool_const") || tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("(") || tokenSet.get(i).get(0).equals("!") || tokenSet.get(i).get(0).equals("return") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("abstract") || tokenSet.get(i).get(0).equals("void"))
        {
            if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char"))
            {
                    if(declare(CR, FR))
                    {
                        return true;
                    }
            }

            else if(tokenSet.get(i).get(0).equals ("for")) 
            {
                if (for_(type, CR, FR))
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("if")) 
            {
                if(if_else(type, CR, FR))
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("do")) 
            {
                if(do_while(type, CR, FR))
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("continue")) 
            {
                if(continue_())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("break")) 
            {
                if(break_())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new"))
            {
                if(fn_call())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("new")) 
            {
                if(obj_dec())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("while")) 
            {
                if(while_(type, CR, FR))
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("this") || tokenSet.get(i).get(0).equals ("super") || tokenSet.get(i).get(0).equals ("new"))
            {
                if(assign_st())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("++") || tokenSet.get(i).get(0).equals ("--"))
            {
                if(inc_dec_st())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("try")) 
            {
                if(try_catch(type, CR, FR))
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("ID"))
            {
                if(arr_call())
                {
                    return true;
                }
            }
            
            else if(tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals("String") ||tokenSet.get(i).get(0).equals("float") ||tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char"))
            {
                if(array())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("int_const") || tokenSet.get(i).get(0).equals ("float_const") || tokenSet.get(i).get(0).equals ("String_const") || tokenSet.get(i).get(0).equals ("bool_const") || tokenSet.get(i).get(0).equals ("char_const"))
            {
                if(const_())
                {
                   return true; 
                }
            }

            else if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("this") ||tokenSet.get(i).get(0).equals ("super") ||tokenSet.get(i).get(0).equals ("new")||tokenSet.get(i).get(0).equals ("int_const") ||tokenSet.get(i).get(0).equals ("String_const") ||tokenSet.get(i).get(0).equals ("float_const") ||tokenSet.get(i).get(0).equals ("char_const")||tokenSet.get(i).get(0).equals ("(")||tokenSet.get(i).get(0).equals ("!"))
            {
                if(OE(CR))
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("return")) 
            {
                if(return_(type, CR, FR))
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char"))
            {
                if(data_type())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private")) 
            {
                if(acc_modifier())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("abstract") || tokenSet.get(i).get(0).equals ("final")) 
            {
                if(c_modifier())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("final")) 
            {
                if(c_modifier1())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("abstract")) 
            {
                if(c_modifier2())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("this"))
            {
                if(this_())
                {
                    return true;
                }
            }

            else if (tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char")) 
            {
                if(null_())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("this"))
            {
                if(super_())
                {
                    return true;
                }
            }

            else if (tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals("void") || tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("abstract")) 
            {
                if(function(type, FR, CR))
                {
                    return true;
                }
            }
        }
        
        if(tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("main") || tokenSet.get(i).get(0).equals("}") || tokenSet.get(i).get(0).equals("while") || tokenSet.get(i).get(0).equals("for")  || tokenSet.get(i).get(0).equals("if") || tokenSet.get(i).get(0).equals("do")  || tokenSet.get(i).get(0).equals("continue") || tokenSet.get(i).get(0).equals("break") || tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals("++") || tokenSet.get(i).get(0).equals("--") || tokenSet.get(i).get(0).equals("try") || tokenSet.get(i).get(0).equals("int_const") || tokenSet.get(i).get(0).equals("String_const") || tokenSet.get(i).get(0).equals("float_const") || tokenSet.get(i).get(0).equals("bool_const") || tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("(") || tokenSet.get(i).get(0).equals("!") || tokenSet.get(i).get(0).equals("return") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("abstract") || tokenSet.get(i).get(0).equals("void"))
        {
                   return true;
        }
        return false;
    }
    
}
    
    /*
    private boolean SST()
    {
        if (tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("while") || tokenSet.get(i).get(0).equals("for")  || tokenSet.get(i).get(0).equals("if") || tokenSet.get(i).get(0).equals("do")  || tokenSet.get(i).get(0).equals("continue") || tokenSet.get(i).get(0).equals("break") || tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals("++") || tokenSet.get(i).get(0).equals("--") || tokenSet.get(i).get(0).equals("try") || tokenSet.get(i).get(0).equals("return") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("abstract") || tokenSet.get(i).get(0).equals("void"))
        {
            if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("int") || tokenSet.get(i).get(0).equals ("float") || tokenSet.get(i).get(0).equals ("bool") || tokenSet.get(i).get(0).equals ("char"))
            {
                    if(declare())
                    {
                        return true;
                    }
            }
            
            else if(tokenSet.get(i).get(0).equals ("while")) 
            {
                if(while_())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("for")) 
            {
                if (for_())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("if")) 
            {
                if(if_else())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("do")) 
            {
                if(do_while())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("continue")) 
            {
                if(continue_())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("break")) 
            {
                if(break_())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("super") || tokenSet.get(i).get(0).equals("new"))
            {
                if(fn_call())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("new")) 
            {
                if(obj_dec())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("this") || tokenSet.get(i).get(0).equals ("super") || tokenSet.get(i).get(0).equals ("new"))
            {
                if(assign_st())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals ("ID") ||tokenSet.get(i).get(0).equals ("++") || tokenSet.get(i).get(0).equals ("--"))
            {
                if(inc_dec_st())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("try")) 
            {
                if(try_catch())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("ID"))
            {
                if(arr_call())
                {
                    return true;
                }
            }
            
            else if(tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals("String") ||tokenSet.get(i).get(0).equals("float") ||tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char"))
            {
                if(array())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("return")) 
            {
                if(return_())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("this"))
            {
                if(this_())
                {
                    return true;
                }
            }

            else if (tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char")) 
            {
                if(null_())
                {
                    return true;
                }
            }

            else if(tokenSet.get(i).get(0).equals("this"))
            {
                if(super_())
                {
                    return true;
                }
            }

            else if (tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("static") || tokenSet.get(i).get(0).equals("final") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("int") || tokenSet.get(i).get(0).equals("float") || tokenSet.get(i).get(0).equals("bool") || tokenSet.get(i).get(0).equals("char") || tokenSet.get(i).get(0).equals("void") || tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("abstract")) 
            {
                if(function())
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    */