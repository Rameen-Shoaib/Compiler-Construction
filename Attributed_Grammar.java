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

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("static-access")
public class SemanticAnalyzer {


	static int i = 0;
	static ArrayList<ArrayList<String>> tokenSet = new ArrayList<ArrayList<String>>();
	static int CR=0;
	static int FR=0;
	static int isStatic=0;
	static String name="null";
	static String type="null";
	static String cat="General";
	static String AM="public";
	static String TM="None";

	static String pl="null";
	static String al="null";
	static String parent="null";
	static String ar_type="null";
	static String Op = "null";
	static int isStaticM = 0;
	static int flag = 0;
	static String LO = "null";
	static String RO = "null";
	static int isReturn=0;
	static int s=0;
	static int isLoop=0;
	static int counter = 0;
	static int current_CR = 0;
		
	static Database_Connection database=new Database_Connection();
	
	public static void main(String[] args) throws Exception {
		
		new SemanticAnalyzer();
//		database.deleteErntries();
	
	}
	
	public  SemanticAnalyzer() throws Exception {


	  tokenSet = new Lexical_Analyzer().getTokens();
	  ArrayList<String> token=new ArrayList<String>(Arrays.asList("$","$","end"));
	  tokenSet.add(token);
			
		
	 
	 if(S()) {
		 
		 System.out.println("Valid Syntax...");

	 } else {
		 
		 System.out.println("Invalid Syntax...");
	 }
	 
	 database.deleteErntries();
	 
	}


	private boolean S() {
		
		if(def()) {
			
			if(tokenSet.get(i).get(0).equals ("public")) {
				String AM=tokenSet.get(i).get(0);
				i++;
				
				if(tokenSet.get(i).get(0).equals ("class")) {
					String type=tokenSet.get(i).get(0);
					i++;
				    
					if(tokenSet.get(i).get(0).equals ("ID")) {
						String name=tokenSet.get(i).get(1);
						i++;
						
						if(inherit(type)) {
							
							if(tokenSet.get(i).get(0).equals ("{")) {
								i++;
								int CR=database.create_dataTable();
								
								if(!database.insert_MT(CR,name, type, "General", parent)) {
									System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
								}
								parent="null";
								if(S_dash(CR)) {
									if(tokenSet.get(i).get(0).equals ("}")) {
										
										i++;
										if(def()) {
											
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
		
		return false;
	}
	
	private boolean S_dash(int CR) {

		String AM="Default";
		int isStatic=0;
		String name="null";
		String type="null";
		String TM="None";

		if(tokenSet.get(i).get(0).equals ("public")) {
			AM=tokenSet.get(i).get(0);
			i++;
			if(S_doubledash(AM,CR)) {
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("private")) {
			AM=tokenSet.get(i).get(0);
			i++;
			
			if(c_body3(AM,CR)) {
				if(tokenSet.get(i).get(0).equals ("public")) {
					AM=tokenSet.get(i).get(0);
					i++;
					
					if(tokenSet.get(i).get(0).equals ("static")) {
						isStatic=1;
						i++;
						
						if(tokenSet.get(i).get(0).equals ("void")) {
							type=tokenSet.get(i).get(0);
							i++;
						
							if(tokenSet.get(i).get(0).equals ("main")) {
								name=tokenSet.get(i).get(0);
								i++;
								
								
								al="null"+"->"+type;
								int FR=database.create_funTable();
								
								if(!database.insert_DT(CR, FR, name, al, AM, TM, isStatic, parent)) {
								
									System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
								}
							
								
								if(tokenSet.get(i).get(0).equals ("(")) {
									i++;
									
									
									if(tokenSet.get(i).get(0).equals (")")) {
										i++;
										
										if(tokenSet.get(i).get(0).equals ("{")) {
											i++;
											
											if(MST(type,CR,FR)) {
												
												if(tokenSet.get(i).get(0).equals ("}")) {
													i++;
													isReturn = 0;
													if(c_body(CR)) {
														
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
		else if(c_body3(AM,CR)) {
				if(tokenSet.get(i).get(0).equals ("public")) {
					AM=tokenSet.get(i).get(0);
					i++;
					
					if(tokenSet.get(i).get(0).equals ("static")) {
						isStatic=1;
						i++;
						
						if(tokenSet.get(i).get(0).equals ("void")) {
							type=tokenSet.get(i).get(0);
							i++;
							
							if(tokenSet.get(i).get(0).equals ("main")) {
								name=tokenSet.get(i).get(0);
								i++;
								
								al="null"+"->"+type;
								int FR=database.create_funTable();
								
								if(!database.insert_DT(CR, FR, name, al, AM, TM, isStatic, parent)) {
								
									System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
								}
								
								
								if(tokenSet.get(i).get(0).equals ("(")) {
									i++;
									
									if(tokenSet.get(i).get(0).equals (")")) {
										i++;
										
										if(tokenSet.get(i).get(0).equals ("{")) {
											i++;
											
											if(MST(type,CR,FR)) {
												
												if(tokenSet.get(i).get(0).equals ("}")) {
													i++;
													
													isReturn = 0;
													if(c_body(CR)) {
														
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
			}else if(tokenSet.get(i).get(0).equals("}")){
			    return true;
		    }
		
		
		return false;
	}
	
	private boolean S_doubledash(String AM,int CR) {
		
		int isStatic=0;
		String name="null";
		String type="null";
		String cat="General";
		String TM="null";

		if(tokenSet.get(i).get(0).equals ("const")) {
			TM=tokenSet.get(i).get(0);
			i++;	
			
			if(CST3(TM,AM,isStatic,CR)) {
				
				if(S_dash(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("DT")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(CST4(type,AM,isStatic,CR)) {
				
				if(S_dash(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("ID")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(CST_doubledash(type,AM,TM,isStatic,CR)) {
				if(S_dash(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("String")) {
			type=tokenSet.get(i).get(0);
			i++;

			if(CST6(type,AM,isStatic,CR)) {
				
				if(S_dash(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("var")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(CST6(type,AM,isStatic,CR)) {
				
				if(S_dash(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("void")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(tokenSet.get(i).get(0).equals ("(")) {
					i++;
					
					int FR=database.create_funTable();
					
					
					if(args(FR)) {
						al=al+"->"+type;
						
						if(!database.insert_DT(CR, FR, name, al, AM, TM, isStatic, parent)) {
							System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
						}
						
						al="null";
						parent="null";
						if(tokenSet.get(i).get(0).equals (")")) {
							i++;
							if(fun2(type,FR,CR)) {
								if(S_dash(CR)) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("class")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(inherit(type)) {
					
					int prevCR=CR;
					CR=database.create_dataTable();
					
					if(!database.insert_DT(prevCR, CR, name, type, "Default", "null", isStatic, parent)) {
						System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
					}
					
					parent="null";
					if(tokenSet.get(i).get(0).equals ("{")) {
						i++;
						
						if(c_body(CR)) {
							
							if(tokenSet.get(i).get(0).equals ("}")) {
								i++;
								if(S_dash(prevCR)) {
									
									return true;
								}
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("interface")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(inherit(type)) {
					
					
					if(tokenSet.get(i).get(0).equals ("{")) {
						i++;

						int prevCR=CR;
						CR=database.create_dataTable();
						
						if(!database.insert_DT(prevCR, CR, name, type, AM, TM, isStatic, parent)) {
							System.out.println("Redeclaration Errorline #="+tokenSet.get(i).get(2));
						}
						
							parent="null";
							
							if(int_body(CR)) {
							
								if(tokenSet.get(i).get(0).equals ("}")) {
								i++;
								
								if(S_dash(prevCR)) {
									
									return true;
								}
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("enum")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(tokenSet.get(i).get(0).equals ("{")) {
					i++;
				
					int prevCR=CR;
					CR=database.create_dataTable();
					
					if(!database.insert_DT(prevCR, CR, name, type, AM, TM, isStatic, parent)) {
						System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
					}
					parent="null";
					
					if(enum_body(CR)) {
						
						if(tokenSet.get(i).get(0).equals ("}")) {
							i++;
							
							if(S_dash(prevCR)) {
								
								return true;
							}
						}
					}
				}
			}
		}
		
		else if(tokenSet.get(i).get(0).equals ("static")) {
			isStatic=1;
			i++;
			
			
			if(S_tripledash(AM,CR,isStatic)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("abstract")) {
			// cat will goes as a TM 
			TM=tokenSet.get(i).get(0);
			i++;

			if(c_body2(TM,AM,CR)) {
				
				if(tokenSet.get(i).get(0).equals ("public")) {
					AM=tokenSet.get(i).get(0);
					i++;
					
					
					if(tokenSet.get(i).get(0).equals ("static")) {
						isStatic=1;
						i++;
						
						if(tokenSet.get(i).get(0).equals ("void")) {
							type=tokenSet.get(i).get(0);
							i++;
							
							if(tokenSet.get(i).get(0).equals ("main")) {
								name=tokenSet.get(i).get(0);
								i++;
								
								
								al="null"+"->"+type;
								int FR=database.create_funTable();
								
								if(!database.insert_DT(CR, FR, name, al, AM, TM, isStatic, parent)) {
								
									System.out.println("Redeclaration Errorline #="+tokenSet.get(i).get(2));
								}
								al="null";
								parent="null";
								 	
								if(tokenSet.get(i).get(0).equals ("(")) {
									i++;
									
									if(tokenSet.get(i).get(0).equals (")")) {
										i++;
										
										if(tokenSet.get(i).get(0).equals ("{")) {
											i++;
											
											if(MST(type,CR,FR)) {
												
												if(tokenSet.get(i).get(0).equals ("}")) {
													i++;
													
													if(c_body(CR)) {
														
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
		else if(tokenSet.get(i).get(0).equals ("sealed")) {
					// cat will goes as a TM 
					TM=tokenSet.get(i).get(0);
					i++;
					
					
					if(c_body2(TM,AM,CR)) {
						if(tokenSet.get(i).get(0).equals ("public")) {
							AM=tokenSet.get(i).get(0);
							i++;
							
							if(tokenSet.get(i).get(0).equals ("static")) {
								isStatic=1;
								i++;
								
								if(tokenSet.get(i).get(0).equals ("void")) {
									type=tokenSet.get(i).get(0);
									i++;
								
									if(tokenSet.get(i).get(0).equals ("main")) {
										name=tokenSet.get(i).get(0);
										i++;
										
										

										al="null"+"->"+type;
										int FR=database.create_funTable();
										
										if(!database.insert_DT(CR, FR, name, al, AM, TM, isStatic, parent)) {
										
											System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
										}
										
												
										if(tokenSet.get(i).get(0).equals ("(")) {
											i++;
											
											if(tokenSet.get(i).get(0).equals (")")) {
												i++;
												
												if(tokenSet.get(i).get(0).equals ("{")) {
													i++;
													
													if(MST(type,CR,FR)) {
														
														if(tokenSet.get(i).get(0).equals ("}")) {
															i++;
															
															if(c_body(CR)) {
																
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
			}else if(tokenSet.get(i).get(0).equals("}")) {
				return true;
				// line 584 update by true from false
			}
		
		return false;
	}
	
	private boolean S_tripledash(String AM,int CR,int isStatic) {
		
		String name="null";
		String type="null";
		String cat="General";
		String TM="None";

		
		if(tokenSet.get(i).get(0).equals ("const")) {
			TM=tokenSet.get(i).get(0);
			i++;
			
			if(CST3(TM,AM,isStatic,CR)) {
				
				if(S_dash(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("DT")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(CST4(type,AM,isStatic,CR)) {
				
				if(S_dash(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("ID")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			
			if(CST5(type,AM,isStatic,CR)) {
				
				if(S_dash(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("String")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(CST6(type,AM,isStatic,CR)) {
				
				if(S_dash(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("var")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(CST6(type,AM,isStatic,CR)) {
				
				if(S_dash(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("void")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(S1(AM,CR,isStatic,type)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("class")) {
				type=tokenSet.get(i).get(0);
				i++;
				
				if(tokenSet.get(i).get(0).equals ("ID")) {
					name=tokenSet.get(i).get(0);
					i++;
					
					if(inherit(type)) {
						
						int prevCR=CR;
						CR=database.create_dataTable();
						
						if(!database.insert_DT(prevCR, CR, name, type, AM, TM, isStatic, parent)) {
							System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
						}
						parent="null";
						if(tokenSet.get(i).get(0).equals ("{")) {
							i++;
							
							if(c_body(CR)) {
								
								if(tokenSet.get(i).get(0).equals ("}")) {
									i++;
									
									if(S_dash(prevCR)) {
										
										return true;
									}
								}
							}
						}
					}
				}
			}else if(tokenSet.get(i).get(0).equals("}")) {
				return true;
			}
		
			
		return false;
	}
	
	private boolean S1(String AM,int CR,int isStatic,String type) {
		
		String TM="null";
		String name="null";
		int FR=0;
		
		if(tokenSet.get(i).get(0).equals ("ID")) {
			name=tokenSet.get(i).get(1);
			i++;
			
			
			if(tokenSet.get(i).get(0).equals ("(")) {
				i++;
				FR=database.create_funTable();
				
				if(args(FR)) {
					
					al=al+"->"+type;
//					System.out.println("here->"+name);
					if(!database.insert_DT(CR, FR, name, al, AM, TM, isStatic, parent)) {
						System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
					}
					
					if(tokenSet.get(i).get(0).equals (")")) {
						i++;
						
						if(fun2(type,FR,CR)) {
							
							if(S_dash(CR)) {
								
								return true;
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("Main")) {
				name=tokenSet.get(i).get(0);
				i++;
				
				if(tokenSet.get(i).get(0).equals ("(")) {
					i++;
					FR=database.create_funTable();
					
					al="null"+"->"+type;
					if(!database.insert_DT(CR, FR, name, al, AM, TM, isStatic, parent)) {
						System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
					}
					
					if(tokenSet.get(i).get(0).equals (")")) {
						i++;
						
						if(tokenSet.get(i).get(0).equals ("{")) {
							i++;
							
							if(MST(type,CR,FR)) {
								
								if(tokenSet.get(i).get(0).equals ("}")) {
									i++;
									if(c_body(CR)) {
										
										return true;
									}
								}
							}
						}
					}
				}
			}else if(tokenSet.get(i).get(0).equals("}")) {
				return true;
			}
		
			
		return false;
	}

	private boolean def() {
		
		if(tokenSet.get(i).get(0).equals ("abstract") || tokenSet.get(i).get(0).equals ("sealed") ||
				tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("class")||
				tokenSet.get(i).get(0).equals ("enum") || tokenSet.get(i).get(0).equals ("interface")) {
			
			if(def_st()) {
				if(def()) {
					return true;
				}
			}
			
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("$")) {
				
				return true;
			}
		}
		
		return false;
	}
	
	private boolean def_st() {

		String type="null";
		String name="null";
		if(tokenSet.get(i).get(0).equals ("abstract") || tokenSet.get(i).get(0).equals ("sealed")||
				tokenSet.get(i).get(0).equals ("class") || tokenSet.get(i).get(0).equals ("staic") ) {
			if(abs_sea()) {
				
//				if(static_dash()) {
					
					if(tokenSet.get(i).get(0).equals ("class")) {
						type=tokenSet.get(i).get(0);
						i++;
						if(tokenSet.get(i).get(0).equals ("ID")) {
							name=tokenSet.get(i).get(1);
							i++;
							
							
							if(inherit(type)) {
							
								int CR=database.create_dataTable();
								
								if(!database.insert_MT(CR,name, type, cat, parent)) {
									System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
								}
								parent="null";
								cat="General";
								
								if(tokenSet.get(i).get(0).equals ("{")) {
									i++;
									
									if(c_body(CR)) {
										if(tokenSet.get(i).get(0).equals ("}")) {
											i++;
											return true;
										}
									}
								}
							}
						}
					}
//				}
			}
		}else if(tokenSet.get(i).get(0).equals ("interface")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(inherit(type)) {
					
					if(tokenSet.get(i).get(0).equals ("{")) {
						i++;
						
						int CR=database.create_dataTable();
						
						if(!database.insert_MT(CR,name, type, cat, parent)) {
							System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
						}
						parent="null";
						cat="General";
						
						if(int_body(CR)) {
							
							if(tokenSet.get(i).get(0).equals ("}")) {
								i++;
								
								return true;
							}
						}
					}
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("enum")) {
				type=tokenSet.get(i).get(0);
				i++;
				
				
				if(tokenSet.get(i).get(0).equals ("ID")) {
					name=tokenSet.get(i).get(1);
					i++;
					
					if(inherit(type)) {
						
						int CR=database.create_dataTable();
						
						if(!database.insert_MT(CR,name, type, cat, parent)) {
							System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
						}
						
						parent="null";
						cat="General";
						
						
						if(tokenSet.get(i).get(0).equals ("{")) {
							i++;
							
							if(enum_body(CR)) {
								
								if(tokenSet.get(i).get(0).equals ("}")) {
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
	
	@SuppressWarnings("unused")
	private boolean Access_mod() {
		
		if(tokenSet.get(i).get(0).equals ("public")) {
			AM=tokenSet.get(i).get(0);
			i++;
			return true;
		}
		else if(tokenSet.get(i).get(0).equals ("private")) {
			AM=tokenSet.get(i).get(0);
			i++;
			return true;
		}
		else
			if(tokenSet.get(i).get(0).equals ("abstract") || tokenSet.get(i).get(0).equals ("sealed")||
					tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("ID") || 
					tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("var") ||
					tokenSet.get(i).get(0).equals ("void") || tokenSet.get(i).get(0).equals ("static")||
					tokenSet.get(i).get(0).equals ("const")) {
				
				return true;
			}
		return false;
	}
	
	private boolean abs_sea() {
		
		if(tokenSet.get(i).get(0).equals ("abstract")) {
			cat=tokenSet.get(i).get(0);
			i++;
			return true;
		}
		else if(tokenSet.get(i).get(0).equals ("sealed")) {
			cat=tokenSet.get(i).get(0);
			i++;
			return true;
		}
		else
			if(tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("class")) {
				
				return true;
			}
		return false;
	}
	
	private boolean static_dash() {
		
		if(tokenSet.get(i).get(0).equals ("static")) {
			isStatic=1;
			i++;
			return true;
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("class") || tokenSet.get(i).get(0).equals ("DT") ||
					tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("String") ||
					tokenSet.get(i).get(0).equals ("var") || tokenSet.get(i).get(0).equals ("void") ||
					tokenSet.get(i).get(0).equals ("const")) {
				
				return true;
			}
		}
		return false;
	}
	
	private boolean inherit(String type) {
		
		int c=0;
		if(tokenSet.get(i).get(0).equals (":")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				Type_MT type_MT=database.lookUp_MT(name);
				
				if(type_MT==null) {
				
					System.out.println("Class Undeclared,line #="+tokenSet.get(i).get(2));
			
				}else if (type.equals("class")) {
					
					if(type.equals(type_MT.type)) {
					
						c++;
						parent=name;
					
					}else if(type_MT.type.equals("interface")){
						
						parent=name;
						
					}else {
						System.out.println("Must be class or inteface,line #="+tokenSet.get(i).get(2));
					}
				}
				if(inherit2(this.parent,type,c)) {
					return true;
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("{")) {
				
				return true;
			}
		}
		return false;
	}
	
	private boolean  inherit2(String parent,String type,int c) {
		
		if(tokenSet.get(i).get(0).equals (",")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				
				Type_MT type_MT=database.lookUp_MT(name);
		
				if(type_MT==null) {
		
					System.out.println("Undeclared Error,line #="+tokenSet.get(i).get(2));
				
				}else if (type.equals("class")) {
				
					if(type.equals(type_MT.type) && c<1) {
						
						c++;
						this.parent=parent+","+name;;
					}else {
						System.out.println("Only single class inheritance is supported,line #="+tokenSet.get(i).get(2));
					}
					
				}else if(type_MT.type.equals("interface")){
					
					this.parent=parent+","+name;;
							
					
				}else {
					System.out.println("Must be class or inteface,line #="+tokenSet.get(i).get(2));
					
				}
			
				if(inherit2(this.parent, type, c)) {
					
					return true;
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("{")) {
				
				return true;
			}
		}
		return false;
	}

	

	private boolean c_body(int CR) {
		String AM="Default";
		
		
		if(tokenSet.get(i).get(0).equals ("public")) {
			AM=tokenSet.get(i).get(0);
			i++;
			if(c_body3(AM,CR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("private")) {
			AM=tokenSet.get(i).get(0);
			i++;
			
			if(c_body3(AM,CR)) {
				
				return true;
			}
		}
		else if(c_body3(AM,CR)) {
			
			return true;
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("}")) {
				
				return true;
			}
		}
		return false;
	}
	
	private boolean c_body3(String AM,int CR) {
		
		int isStatic=0;
		String name="null";
		String type="null";
		String cat="General";
		String TM="None";

		
		if(tokenSet.get(i).get(0).equals ("const")) {
			TM=tokenSet.get(i).get(0);
			i++;
			
			if(CST3(TM,AM,isStatic,CR)) {
				
				if(c_body(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("DT")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(CST4(type,AM,isStatic,CR)) {
				
				if(c_body(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("ID")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(CST_doubledash(type,AM,TM,isStatic,CR)) {
				
				if(c_body(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("String")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			
			if(CST6(type,AM,isStatic,CR)) {
				
				if(c_body(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("var")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(CST6(type,AM,isStatic,CR)) {
				
				if(c_body(CR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("void")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				
				if(tokenSet.get(i).get(0).equals ("(")) {
					i++;
					
					int FR=database.create_funTable();
					
					
					if(args(FR)) {
					
						al=al+"->"+type;
						
						if(!database.insert_DT(CR, FR, name, al, AM, TM, isStatic, "null")) {
							System.out.println("Redeclaration error,line #="+tokenSet.get(i).get(2));
						}
						
						al="null";
						
						if(tokenSet.get(i).get(0).equals (")")) {
							i++;
							
							if(fun2(type,FR,CR)) {
								
								if(c_body(CR)) {
									
									return true;
								}
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("class")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(inherit(type)) {
					
					int prevCR=CR;
					CR=database.create_dataTable();
					
					if(!database.insert_DT(prevCR, CR, name, type, AM,TM ,isStatic, parent)) {
						System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
					}
					
					parent="null";
					
					if(tokenSet.get(i).get(0).equals ("{")) {
						i++;
						
						if(c_body(CR)) {
							
							if(tokenSet.get(i).get(0).equals ("}")) {
								i++;
								
								if(c_body(prevCR)) {
									
									return true;
								}
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("interface")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(inherit(type)) {
					
					int prevCR=CR;
					CR=database.create_dataTable();
					
					if(!database.insert_DT(prevCR, CR, name, type, AM, TM, isStatic, parent)){
						System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
					}
					
					parent="null";
					
					if(tokenSet.get(i).get(0).equals ("{")) {
						i++;
						
						if(int_body(CR)) {
							
							if(tokenSet.get(i).get(0).equals ("}")) {
								i++;
								
								if(c_body(prevCR)) {
									
									return true;
								}
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("enum")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(inherit(type)) {
					
					int prevCR=CR;
					CR=database.create_dataTable();
					
					if(!database.insert_DT(prevCR, CR, name, type, AM, TM, isStatic, parent)){
						System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
					}
					
					parent="null";
					
					if(tokenSet.get(i).get(0).equals ("{")) {
						i++;
						
						if(enum_body(CR)) {
							
							if(tokenSet.get(i).get(0).equals ("}")) {
								i++;
								
								if(c_body(prevCR)) {
									
									return true;
								}
							}
						}
					}
				}
			}
		}
		
		else if(tokenSet.get(i).get(0).equals ("static")) {
			isStatic=1;
			i++;
			
			if(c_body1(isStatic,AM,CR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("abstract")) {
			cat=tokenSet.get(i).get(0);
			i++;
			
			if(c_body2(cat,AM,CR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("sealed")) {
			cat=tokenSet.get(i).get(0);
			i++;
			
			if(c_body2(cat,AM,CR)) {
				
				return true;
			}
		}
		return false;
	}
	
	private boolean c_body1(int isStatic, String AM, int CR) {
		
		String name="null";
		String type="null";
		String cat="General";
		
		if(tokenSet.get(i).get(0).equals ("const") || tokenSet.get(i).get(0).equals ("DT") ||
				tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("String") ||
				tokenSet.get(i).get(0).equals ("var") || tokenSet.get(i).get(0).equals ("void")) {
			
			if(CST2(isStatic,AM,CR)) {
				
				if(c_body(CR)) {
					
					return true;
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("class")) {
				type=tokenSet.get(i).get(0);
				i++;
				
				if(tokenSet.get(i).get(0).equals ("ID")) {
					name=tokenSet.get(i).get(1);
					i++;
					
					if(inherit(type)) {
						
						int prevCR=CR;
						CR=database.create_dataTable();
						
						if(!database.insert_DT(prevCR, CR, name, type, AM, "null", isStatic, parent)) {
							System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
						}
						
						parent="null";
						
						if(tokenSet.get(i).get(0).equals ("{")) {
							i++;
							
							if(c_body(CR)) {
								
								if(tokenSet.get(i).get(0).equals ("}")) {
									i++;
									
									if(c_body(prevCR)) {
										
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
	
	private boolean c_body2(String TM,String AM,int CR) {
		
		int isStatic=0;
		String name="null";
		String cat="General";
		String type="null";
		
		if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("ID") ||
				tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("var") ||
				tokenSet.get(i).get(0).equals ("void")) {
			
			if(return_type()) {
				type=ar_type;
				if(tokenSet.get(i).get(0).equals ("ID")) {
					name=tokenSet.get(i).get(1);
					i++;
					
					if(tokenSet.get(i).get(0).equals ("(")) {
						i++;
						
						int FR=database.create_funTable();
						
						
						if(args(FR)) {
							
							al=al+"->"+type;
							Type_MT T=database.lookUp_MT(type);
							
							if(T==null) {
						
								System.out.println("Class is Undeclared,line #="+tokenSet.get(i).get(2));
							
							}else if(!T.type.equals("class")) {
								
								System.out.println("Return type must be class,line #="+tokenSet.get(i).get(2));
							
							}else if(!database.insert_DT(CR,FR, name, al, AM, TM, isStatic, parent)) {
							
								System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
							
							}
							
							parent="null";
							al="null";
							
							if(tokenSet.get(i).get(0).equals (")")) {
								i++;
								
								if(fun2(type,FR,CR)) {
									
									if(c_body(CR)) {
										
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("class")) {
				type=tokenSet.get(i).get(0);
				i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(inherit(type)) {
					
					int prevCR=CR;
					CR=database.create_dataTable();
					
					if(!database.insert_DT(prevCR, CR, name, type, AM, TM, isStatic, parent)) {
						System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
					}
					parent="null";
					
					if(tokenSet.get(i).get(0).equals ("{")) {
						i++;
						
						if(c_body(CR)) {
							
							if(tokenSet.get(i).get(0).equals ("}")) {
								i++;
								
								if(c_body(prevCR)) {
									
									return true;
								}
							}
						}
					}
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("static")) {
				isStatic=1;
				i++;
				
				if(c_body_dash(isStatic,AM,TM,CR)) {
					
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean c_body_dash(int isStatic,String AM, String TM, int CR) {
		
		String name="null";
		String type="null";
		
		if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("ID") ||
				tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("var") ||
				tokenSet.get(i).get(0).equals ("void")) {
			
			if(return_type()) {
				// because args type will change ar_type hence i have to store this in temp variable
				type=this.ar_type;
				
				if(tokenSet.get(i).get(0).equals ("ID")) {
					name=tokenSet.get(i).get(1);
					i++;
					
					if(tokenSet.get(i).get(0).equals ("(")) {
						i++;
						
						int FR=database.create_funTable();
						
						if(args(FR)) {
			
							al=al+"->"+type;
							Type_MT T=database.lookUp_MT(type);
							
							if(T==null) {
						
								System.out.println("Class is Undeclared,line #="+tokenSet.get(i).get(2));
							
							}else if(!T.type.equals("class")) {
								
								System.out.println("Return type must be classline #="+tokenSet.get(i).get(2));
							
							}else if(!database.insert_DT(CR,FR, name, al, AM, TM, isStatic, parent)) {
							
								System.out.println("Redeclaration Errorline #="+tokenSet.get(i).get(2));
							
							}
							
							parent="null";
							al="null";
										
							if(tokenSet.get(i).get(0).equals (")")) {
								i++;
								
								if(fun2(type,FR,CR)) {
									
									if(c_body(CR)) {
										
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("class")) {
				type=tokenSet.get(i).get(0);
				i++;
				
				if(tokenSet.get(i).get(0).equals ("ID")) {
					name=tokenSet.get(i).get(1);
					i++;
					
					if(inherit(type)) {
						
						int prevCR=CR;
						CR=database.create_dataTable();
						
						if(!database.insert_DT(prevCR, CR, name, type, AM, TM, isStatic, parent)) {
							System.out.println("Redeclaration errorline #="+tokenSet.get(i).get(2));
						}
						parent="null";
						
						if(tokenSet.get(i).get(0).equals ("{")) {
							i++;
							
							if(c_body(CR)) {
								
								if(tokenSet.get(i).get(0).equals ("}")) {
									i++;
									
									if(c_body(prevCR)) {
										
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
	private boolean CST_doubledash(String type,String AM,String TM,int isStatic,int CR) {
		
		if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("[")) {
			
			if(CST5(type,AM,isStatic,CR)) {
				
				return true;
			}
		}
		else {
			// for constructor and here type has the name
			if(tokenSet.get(i).get(0).equals ("(")) {
				i++;
				
				int FR=database.create_funTable();
				
				if(args(FR)) {
					
					al=al+"->"+"null";
					
					if(!database.insert_DT(CR, FR, type, al, AM, TM, 0, parent)) {
						System.out.println("Redeclaration of Constructorline #="+tokenSet.get(i).get(2));
					}
					
					al="null";
					parent="null";
					
					if(tokenSet.get(i).get(0).equals (")")) {
						i++;
						
						if(tokenSet.get(i).get(0).equals ("{")) {
							i++;
							
							if(MST(type,CR,FR)) {
				
								
								if(tokenSet.get(i).get(0).equals ("}")) {
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
	
	private boolean CST2(int isStatic,String AM,int CR) {
		
		String name="null";
		String type="null";
		String TM="null";

		if(tokenSet.get(i).get(0).equals ("const")) {
			TM=tokenSet.get(i).get(0);
			i++;

			if(CST3(TM,AM,isStatic,CR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("DT")) {
			type=tokenSet.get(i).get(1);
			i++;

			if(CST4(type,AM,isStatic,CR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("ID")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(CST5(type,AM,isStatic,CR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("String")) {
			type=tokenSet.get(i).get(0);
			i++;
				
			if(CST6(type,AM,isStatic,CR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("var")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(CST6(type,AM,isStatic,CR)) {
				
				return true;
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("void")) {
				type=tokenSet.get(i).get(0);
				i++;
				
				if(tokenSet.get(i).get(0).equals ("ID")) {
					name=tokenSet.get(i).get(1);
					i++;
					
					if(tokenSet.get(i).get(0).equals ("(")) {
						i++;
						
						int FR=database.create_funTable();
						
						if(args(FR)) {
							
							al=al+"->"+type;
							
							if(!database.insert_DT(CR, FR, name, al, AM, TM, isStatic, parent)) {
								System.out.println("Redeclaration Errorline #="+tokenSet.get(i).get(2));
							}
							
							al="null";
							parent="null";
							
							if(tokenSet.get(i).get(0).equals (")")) {
								i++;
								
								if(fun2(type,FR,CR)) {
									
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
	
	private boolean CST3(String TM,String AM,int isStatic,int CR) {
		
		String type="null";
		String name="null";
		
		if(tokenSet.get(i).get(0).equals ("DT")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(!database.insert_DT(CR, 0, name, type, AM, TM, isStatic, parent)) {
					System.out.println("Redecalation Erorline #="+tokenSet.get(i).get(2));
				}
				
				
				
				if(init(type, CR)) {
					
					if(list(type,TM,AM,CR,isStatic)) {
						
						return true;
					}
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				type=tokenSet.get(i).get(1);
				i++;
				
				if(tokenSet.get(i).get(0).equals ("ID")) {
					name=tokenSet.get(i).get(1);
					i++;

					
					if(init2(type,name,TM,AM,isStatic, CR)) {
						
						if(obj2(type,TM,AM,CR,isStatic)) {
							
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean CST4(String type,String AM,int isStatic, int CR) {
		
		String name="null";
		
		if(tokenSet.get(i).get(0).equals ("ID")) { //previouly DT was here
			name=tokenSet.get(i).get(1);
			i++;
			
			if(CST4_dash(name,type,AM,isStatic,CR)) {
				
				return true;	
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("[")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals ("]")) {
					i++;
					
					if(arr_O()) {
						
						if(tokenSet.get(i).get(0).equals ("ID")) {
							i++;
							
							if(tokenSet.get(i).get(0).equals ("(")) {
								i++;
								// pass default arguments in this array portion
								if(args(0)) {
									
									if(tokenSet.get(i).get(0).equals (")")) {
										i++;
										
										if(fun2(type,0,0)) {
											
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
		
		return false;
	}
	
	private boolean CST4_dash(String name,String type,String AM,int isStatic,int CR) {
		String TM="null";
		
		if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals (";") ||
				tokenSet.get(i).get(0).equals (",") || tokenSet.get(i).get(0).equals ("[")) {
			if(!database.insert_DT(CR, 0, name, type, AM, "null", isStatic, "null")) {
				System.out.println("Redeclaration Error, line # ="+tokenSet.get(i).get(2));
			}
			
			if(CST7(type,TM,AM,CR)) {
				
				return true;
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("(")) {
				i++;
				int FR=database.create_funTable();
				
				if(args(FR)) {
					al=al+"->"+type;
					if(!database.insert_DT(CR, FR, name, al, AM, TM, isStatic, parent)) {
						System.out.println("Redeclaration Errorline #="+tokenSet.get(i).get(2));
					}
					al="null";
					parent="null";
					
					if(tokenSet.get(i).get(0).equals (")")) {
						i++;
						
						if(fun2(type,FR,CR)) {
							
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean CST5(String type,String AM,int isStatic,int CR) {
		
		String name="null";
		if(tokenSet.get(i).get(0).equals ("ID")) { // update ID from DT
			name=tokenSet.get(i).get(1);
			i++;
			
			if(CST5_dash(name,type,AM,isStatic,CR)) {
				
				return true;
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("[")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals ("]")) {
					i++;
					
					if(arr_O()) {
						
						if(tokenSet.get(i).get(0).equals ("ID")) {
							i++;
							
							if(tokenSet.get(i).get(0).equals ("(")) {
								i++;
								
								if(args(0)) {
									al="null";
									
									if(tokenSet.get(i).get(0).equals (")")) {
										i++;
										
										if(fun2("null",0,0)) {
											
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
		
		return false;
	}
	
	private boolean CST5_dash(String name,String type,String AM,int isStatic,int CR) {
		String TM="null";
		
		if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals ("ID") ||
				tokenSet.get(i).get(0).equals ("[")) {
		
			
				
			if(CST8(name,type,TM,AM,CR)) {
				
				return true;
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("(")) {
				i++;
				int FR=database.create_funTable();
				
				if(args(FR)) {
					
					if(tokenSet.get(i).get(0).equals (")")) {
						i++;
						
						Type_MT T=database.lookUp_MT(type);
						al=al+"->"+type;
						
						if(T==null) {
					
							System.out.println("Class is Undeclared,line #="+tokenSet.get(i).get(2));
						
						}else if(!T.type.equals("class")) {
							System.out.println("Object type must be class, line #="+tokenSet.get(i).get(2));
						
						}else if(!database.insert_DT(CR, FR, name, al, AM, "null", isStatic, parent)){
							
							System.out.println("Redeclaration Error, line #="+tokenSet.get(i).get(2));
						}
						
						parent="null";
						al="null";
						
						if(fun2(type,FR,CR)) {
							
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean CST6(String type,String AM,int isStatic,int CR) {
		
		String name="null";
		if(tokenSet.get(i).get(0).equals ("ID")) { // update to ID
			name=tokenSet.get(i).get(1);
			i++;
			
			if(CST6_dash(name,type,AM,isStatic,CR)) {
				
				return true;
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("[")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals ("]")) {
					i++;
					
					if(arr_O()) {
						
						if(tokenSet.get(i).get(0).equals ("ID")) {
							i++;
							
							if(tokenSet.get(i).get(0).equals ("(")) {
								i++;
								
								if(args(0)) {
									al="null";
									
									if(tokenSet.get(i).get(0).equals (")")) {
										i++;
										
										if(fun2("null",0,0)) {
											
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
		
		return false;
	}
	
	private boolean CST6_dash(String name,String type,String AM,int isStatic,int CR) {
		
		if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				if(arr_dec_dash()) {
					
					return true;
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("(")) {
				i++;
				int FR=database.create_funTable();
				
				if(args(FR)) {
					
					al=al+"->"+type;
					if(!database.insert_DT(CR, FR, name, al, AM, "null", isStatic, parent)) {
						System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
					}
					al="null";
					parent="null";
					
					if(tokenSet.get(i).get(0).equals (")")) {
						i++;
						
						if(fun2(type,FR,CR)) {
							
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean CST7(String type,String AM,String TM,int CR) {
		
		if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals (",") ||
				tokenSet.get(i).get(0).equals (";")) {
			
			if(init(type, CR)) {
				
				if(list(type,TM,AM,CR,0)) {
					
					return true;
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("[")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals ("]")) {
					i++;
					
					if(arr_dec_dash()) {
						
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean CST8(String name,String type,String TM,String AM,int CR) {
		
		if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals ("ID")) {

			
			if(init2(type,name,TM,AM,0,CR)) {
				
				if(obj2(type,TM,AM,CR,0)) {
					
					return true;
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("[")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals ("]")) {
					i++;
					
					if(arr_dec_dash()) {
						
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean fun2(String type,int FR,int CR) {
		
		
		Type_MT T1=database.lookUp_MT(CR);
		Type_DT T2=database.lookUp_fn_DT(FR);
		
		if(tokenSet.get(i).get(0).equals("{")) {
			i++;
			if (T1!=null) {
				if (T2!=null) {

					if(T2.typeMod.equals("abstract")) {
						System.out.println("Abstract method does not have body,line #="+tokenSet.get(i).get(2));
					}		
				}
			}
			
			
			if (MST(type, CR, FR)) {
				
				if (tokenSet.get(i).get(0).equals("}")) {
					isReturn = 0;
					i++;
					
					return true;
				}
			}
		}else if (tokenSet.get(i).get(0).equals(";")) {
			
			
			if(T1!=null) {
				if(!T1.type.equals("class")) {
					
					System.out.println("Only make abstract method in abstract class,line #="+tokenSet.get(i).get(2));
			
				}else if(!T1.cat.equals("abstract")){
					
					System.out.println("Class must be abstract, line #="+tokenSet.get(i).get(2));
				
				}else if (T2.typeMod.equals("abstract")) {
					
					System.out.println("Method must be abstract,line #="+tokenSet.get(i).get(2));
			
				}
			}
			
			i++;
			return true;
		}
		
		
		return false;
	}
	
	private boolean return_type() {
		
		if(tokenSet.get(i).get(0).equals ("DT") || tokenSet.get(i).get(0).equals ("ID") || 
				tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("var")) {
			
			if(Data_type()) {
				
				if(arr()) {
					
					return true;
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("void")) {
				ar_type=tokenSet.get(i).get(0);
				i++;
			
				return true;
			}
		}
		return false;
	}
	
	private boolean arr() {
		
		if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				if(arr_O()) {
					
					return true;
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				
				return true;
			}
		}
		return false;
	}
	
	private boolean arr_O() {
		
		if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				return true;
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				
				return true;
			}
		}
		return false;
	}
	

	private boolean Data_type() {
		
		if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("DT") ||
				tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("var")) {
			
			
			if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("DT")) {
				ar_type=tokenSet.get(i).get(1);
		
			}else {
				ar_type=tokenSet.get(i).get(0);
			}
			i++;
			
			return true;
		}
		return false;
	}
	
	private boolean args(int FR) {
		// int a,int b
		String TM="None";
		
		if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("DT") ||
				tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("var")) {
			
			if(args_type()) {
				
				if(tokenSet.get(i).get(0).equals ("ID")) {
					i++;
					String name=tokenSet.get(i).get(1);
					al=ar_type;
					
					if(!database.insert_FT(FR, name, al,TM)) {
						System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
					}
					
					if(args2(FR)) {
						
						return true;
					}
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals (")")) {
				
				return true;
			}
		}
		
		return false;
	}
	
	private boolean args2(int FR) {
		
		String TM="null";
		if(tokenSet.get(i).get(0).equals (",")) {
			i++;
			
			if(args_type()) {
				
				if(tokenSet.get(i).get(0).equals ("ID")) {
					i++;
				
					String name=tokenSet.get(i).get(1);
					al=al+","+ar_type;
					
					if(!database.insert_FT(FR, name, ar_type,TM)) {
						System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
					}
					
					if(args2(FR)) {
						
						return true;
					}
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals (")")) {
				
				return true;
			}
		}
		return false;
	}
	
	private boolean args_type() {
		
		if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("DT") ||
				tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("var")) {
			
			if(Data_type()) {
				
				if(arr()) {
					
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean int_body(int CR) {
		
		if(tokenSet.get(i).get(0).equals ("public") || tokenSet.get(i).get(0).equals ("private") ||
			tokenSet.get(i).get(0).equals ("static") || tokenSet.get(i).get(0).equals ("DT") || 
			tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("String") ||
			tokenSet.get(i).get(0).equals ("var") || tokenSet.get(i).get(0).equals ("const") ||
			tokenSet.get(i).get(0).equals ("void") ) {
			
			if(IST(CR)) {
				
				if(int_body(CR)) {
					
					return true;
				}
				
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("}")) {
				
				return true;
			}
		}
		return false;
	}
	
	private boolean IST(int IR) {
		String AM="Default";
		String type="null";
		String name="null";
		String TM="null";
		int isStatic=0;
		
		if(tokenSet.get(i).get(0).equals ("public")) {
			AM=tokenSet.get(i).get(0);
			i++;
			
			if(IST_dash(AM,IR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("private")) {
			i++;
			
			if(IST_dash(AM,IR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("static")) {
			isStatic=1;
			i++;
			
			if(IST_doubledash(AM,isStatic,IR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("DT")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(IST2(type,IR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("ID")) {
			name=tokenSet.get(i).get(1);
			i++;
			
			if(IST3(name,IR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("String")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(IST4(type,IR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("var")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(IST4(type,IR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("const")) {
			TM=tokenSet.get(i).get(0);
			
			i++;
			
			if(IST1(AM,TM,isStatic,IR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("void")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(tokenSet.get(i).get(0).equals ("(")) {
					i++;
					int FR=database.create_funTable();

					
					
					if(args(FR)) {
						
						al=al+"->"+type;
						if(!database.insert_DT(IR, FR, name, al, AM, TM, isStatic, "null")) {
							System.out.println("redeclaration error,line #="+tokenSet.get(i).get(2));
						}
						
						al="null";
						if(tokenSet.get(i).get(0).equals (")")) {
							i++;
							
							if(tokenSet.get(i).get(0).equals (";")) {
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
	
	private boolean IST_dash(String AM,int IR) {
		
		int isStatic=0;
		if(tokenSet.get(i).get(0).equals ("static")) { //update from ID
		    isStatic=1;
			i++;
			
			if(IST_doubledash(AM,isStatic,IR)) {
				
				return true;
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("const") || tokenSet.get(i).get(0).equals ("DT") ||
					tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("String") ||
					tokenSet.get(i).get(0).equals ("var")) {
				
				if(IST_doubledash(AM,isStatic,IR)) {
					
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean IST_doubledash(String AM,int isStatic,int IR) {
		
		String TM="null";
		String name="null";
		String type="null";

		
		if(tokenSet.get(i).get(0).equals ("const")) {
			TM=tokenSet.get(i).get(0);
			i++;
			
			if(IST1(AM,TM,isStatic,IR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("DT")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;

				if(!database.insert_DT(IR, 0, name, type, AM, TM, isStatic, "null")) {
					System.out.println("Redecalation Eror");
				}
				
				
				if(IST5(AM,type,isStatic,IR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("ID")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				type=tokenSet.get(i).get(1);
				i++;
				
				if(IST6(name,AM,type,isStatic,IR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("String")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals ("[")) {
					i++;
					
					if(tokenSet.get(i).get(0).equals ("]")) {
						i++;
						
						if(arr_dec_dash()) {
							
							return true;
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("var")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals ("[")) {
					i++;
					
					if(tokenSet.get(i).get(0).equals ("]")) {
						i++;
						
						if(arr_dec_dash()) {
							
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean IST1(String AM,String TM,int isStatic,int IR) {
		
		String name="null";
		String type="null";

		if(tokenSet.get(i).get(0).equals ("DT")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(init(type, IR)) {
					
					if(list(type,TM,AM,isStatic,IR)) {
						
						return true;
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("ID")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(init2(type,name,TM,AM,isStatic,IR)) {
					
					if(obj2(type,TM,AM,isStatic,IR)) {
						
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean IST2(String type,int IR) {
		
		if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				if(arr_O()) {
					
					if(tokenSet.get(i).get(0).equals ("ID")) {
						i++;
						
						if(tokenSet.get(i).get(0).equals ("(")) {
							i++;
							
							if(args(0)) {
								
								if(tokenSet.get(i).get(0).equals (")")) {
									i++;
									
									if(tokenSet.get(i).get(0).equals (";")) {
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
		else {
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				String name=tokenSet.get(i).get(1);
				i++;
				
				if(IST2_dash(name,type,IR)) {
					
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean IST2_dash(String name,String type,int IR) {
		
		if(tokenSet.get(i).get(0).equals ("(")) {
			i++;
			int FR=database.create_funTable();
			
			if(args(FR)) {
				
				al=al+"->"+type;
				
				if(!database.insert_DT(IR, FR, name, al, "Default", "null", 0, "null")) {
					System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
				}
				
				al="null";
				parent="null";
				
				if(tokenSet.get(i).get(0).equals (")")) {
					i++;
					
					if(tokenSet.get(i).get(0).equals (";")) {
						i++;
						
						return true;
					}
				}
			}
			
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals (";") ||
					tokenSet.get(i).get(0).equals (",") || tokenSet.get(i).get(0).equals ("[")) {
				
				if (!database.insert_DT(IR, 0, name, type, "Default", "null", 0, "null")) {
					System.out.println("Redeclaration Error, line #="+tokenSet.get(i).get(2));
				}
				
				if(IST5("Default",type,0,IR)) {
					
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean IST3(String name,int IR) {
		
		if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				if(arr_O()) {
					
					if(tokenSet.get(i).get(0).equals ("ID")) {
						i++;
						
						if(tokenSet.get(i).get(0).equals ("(")) {
							i++;
							
							if(args(0)) {
								
								if(tokenSet.get(i).get(0).equals (")")) {
									i++;
									
									if(tokenSet.get(i).get(0).equals (";")) {
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
		else {
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				String name2=tokenSet.get(i).get(1);
				i++;
				
				if(IST3_dash(name2,name,IR)) {
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean IST3_dash(String name,String type,int IR) {
		
		if(tokenSet.get(i).get(0).equals ("(")) {
			i++;
			int FR=database.create_funTable();
			if(args(FR)) {
				
				al=al+"->"+type;
				
				Type_MT T=database.lookUp_MT(type);
				
				if(T==null) {
				
					System.out.println("Undeclared Class");
				
				}else if (!T.type.equals("class")) {
				
					System.out.println("Must be class type");
				
				}else if(!database.insert_DT(IR, FR, name, al, "Default", "null", 0, "null")) {
					System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
				}
				
				al="null";
				
				
				if(tokenSet.get(i).get(0).equals (")")) {
					i++;
					
					if(tokenSet.get(i).get(0).equals (";")) {
						i++;
						
						return true;
					}
				}
			}
			
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals ("ID") 
					|| tokenSet.get(i).get(0).equals ("[")) {
				
				if(IST6(name,"Default",type,0,IR)) {
					
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean IST4(String type,int IR) {
		
		if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				if(arr_O()) {
					
					if(tokenSet.get(i).get(0).equals ("ID")) {
						i++;
						
						if(tokenSet.get(i).get(0).equals ("(")) {
							i++;
							
							if(args(0)) {
								
								if(tokenSet.get(i).get(0).equals (")")) {
									i++;
									
									if(tokenSet.get(i).get(0).equals (";")) {
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
		else {
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				String name=tokenSet.get(i).get(1);
				i++;
				
				if(IST4_dash(name,type,IR)) {
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean IST4_dash(String name,String type,int IR) {
		
		if(tokenSet.get(i).get(0).equals ("(")) {
			i++;
			int FR=database.create_funTable();
			if(args(FR)) {
				
				al=al+"->"+type;
				
				if(!database.insert_DT(IR,FR, name, al,"Default", "null", 0, parent)) {
					System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
				}
				
				al="null";
				parent="null";
				
				
				if(tokenSet.get(i).get(0).equals (")")) {
					i++;
					
					if(tokenSet.get(i).get(0).equals (";")) {
						i++;
						
						return true;
					}
				}
			}
			
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("[")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals ("]")) {
					i++;
					
					if(arr_dec_dash()) {
						
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean IST5(String AM,String type,int isStatic,int IR) {
		
		if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals (";") ||
				tokenSet.get(i).get(0).equals (",")) {
			
			if(init(type, IR)) {
				
				if(list(type,"null",AM,isStatic,IR)) {
					
					return true;
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("[")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals ("]")) {
					i++;
					
					if(arr_dec_dash()) {
						
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean IST6(String name,String AM,String type,int isStatic,int IR) {
		
		if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals ("ID")) {
			
			if(init2(type,name,"null",AM,isStatic,IR)) {
				
				if(obj2(type,"null",AM,isStatic,IR)) {
					
					return true;
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("[")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals ("]")) {
					i++;
					
					if(arr_dec_dash()) {
						
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean enum_body(int CR) {
		if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("public") ||
				tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") ||
				tokenSet.get(i).get(0).equals ("const") || tokenSet.get(i).get(0).equals ("DT") ||
				tokenSet.get(i).get(0).equals ("abstract") || tokenSet.get(i).get(0).equals ("sealed") ||
				tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("var") ||
				tokenSet.get(i).get(0).equals ("void")) {
			if(EST(CR)) {
				
				if(enum_body(CR)) {
					
					return true;
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("}")) {
				
				return true;
			}
		}
		return false;
	}
	
	private boolean EST(int ER) {
		String AM="Default";
		int isStatic=0;
		String name="null";
		String type="null";
		String TM="null";
		
		if(tokenSet.get(i).get(0).equals ("ID")) {
			name=tokenSet.get(i).get(1);
			i++;
			
			if(EST_dash(name,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("public")) {
			AM=tokenSet.get(i).get(0);
			i++;
			
			if(EST_doubledash(AM,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("private")) {
			AM=tokenSet.get(i).get(0);
			i++;
			
			if(EST_doubledash(AM,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("static")) {
			isStatic=1;
			i++;
			
			if(EST1(AM,isStatic,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("const")) {
			TM=tokenSet.get(i).get(0);
			i++;
			
			if(EST3(AM,TM,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("DT")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(EST2(AM,type,isStatic,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("abstract")) {
			
			if(static_dash()) {
				
				if(return_type()) {
					
					if(tokenSet.get(i).get(0).equals ("ID")) {
						i++;
						
						if(tokenSet.get(i).get(0).equals ("(")) {
							i++;
							
							if(args(0)) {
								
								if(tokenSet.get(i).get(0).equals (")")) {
									i++;
									
									if(fun2(type,0,0)) {
										
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("sealed")) {
			
			if(static_dash()) {
				
				if(return_type()) {
					
					if(tokenSet.get(i).get(0).equals ("ID")) {
						i++;
						
						if(tokenSet.get(i).get(0).equals ("(")) {
							i++;
							
							if(args(0)) {
								
								if(tokenSet.get(i).get(0).equals (")")) {
									i++;
									
									if(fun2(type,0,0)) {
										
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("String")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(EST4(AM,type,isStatic,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("var")) {
			type=tokenSet.get(i).get(0);
			
			i++;
			
			if(EST4(AM,type,isStatic,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("void")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(tokenSet.get(i).get(0).equals ("(")) {
					i++;
					
					int FR=database.create_funTable();
					if(args(FR)) {
						
						al=al+"->"+type;
						
						if(!database.insert_DT(ER, FR, name, al, AM, TM, isStatic, parent)) {
							System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
						}
						
						al="null";
						parent="null";
						
						if(tokenSet.get(i).get(0).equals (")")) {
							i++;
							
							if(fun2(type,FR,ER)) {
								
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean EST_dash(String name,int ER) {
		
		if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals (",") ||
				tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("public") ||
				tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") ||
				tokenSet.get(i).get(0).equals ("const") || tokenSet.get(i).get(0).equals ("DT") ||
				tokenSet.get(i).get(0).equals ("abstract") || tokenSet.get(i).get(0).equals ("sealed") ||
				tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("var") ||
				tokenSet.get(i).get(0).equals ("void")) {
		
			if (!database.insert_DT(ER, 0, name, "enum_const", "Default", "null", 0, "null")) {
				System.out.println("Redeclaration Error, line #="+tokenSet.get(i).get(2));
			}
			
			if(ev()) {
				
				if(ev2()) {
					
					return true;
				}
			}
			
		}else if(tokenSet.get(i).get(0).equals ("ID")) {
			String name2=tokenSet.get(i).get(1);
			i++;
			
			if(EST_dash1(name,name2,"Default",0,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("(")) {
			i++;
			// construtor
			int FR=database.create_funTable();
			
			if(args(FR)) {
				
				al=al+"->"+"null";
				
				if(!database.insert_DT(ER, FR, name, al, "Default", "null", 0, "null")) {
					System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
				}
				
				al="null";
				
				
				if(tokenSet.get(i).get(0).equals (")")) {
					i++;
					
					if(tokenSet.get(i).get(0).equals ("{")) {
						i++;
						
						if(MST("null",FR,ER)) {
							
							if(tokenSet.get(i).get(0).equals ("}")) {
								i++;
								
								return true;
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				if(arr_O()) {
					
					if(tokenSet.get(i).get(0).equals ("ID")) {
						i++;
						
						if(tokenSet.get(i).get(0).equals ("(")) {
							i++;
							
							if(args(0)) {
								
								if(tokenSet.get(i).get(0).equals (")")) {
									i++;
									
									if(fun2("null",0,0)) {
										
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
	
	private boolean EST_dash1(String type,String name,String AM,int isStatic,int ER) {
		
		if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				if(arr_dec_dash()) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals ("ID")) {
			
			if(init2(type,name,"null",AM,isStatic,ER)) {
				
				if(obj2(type,"null",AM,isStatic,ER)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("(")) {
			i++;
			int FR=database.create_funTable();
			if(args(FR)) {
				
				Type_MT T=database.lookUp_MT(type);
				
				if (T==null) {
					
					System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
				}else if (!T.type.equals("class")) {
					System.out.println("Must be class type,line #="+tokenSet.get(i).get(2));
				}else if (!database.insert_DT(ER, FR, name, type, AM, "null", isStatic, "null")) {
					System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
				}
				
				if(tokenSet.get(i).get(0).equals (")")) {
					i++;
					
					if(fun2(type,FR,ER)) {
						
						return true;
					}
				}
			}
			
		}
		return false;
	}
	
	private boolean EST_doubledash(String AM,int ER) {
		
		int isStatic=0;
		String name="null";
		String type="null";
		String TM="None";

		if(tokenSet.get(i).get(0).equals ("static")) {
			isStatic=1;
			i++;
			
			if(EST1(AM,isStatic,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("const")) {
			TM=tokenSet.get(i).get(0);
			i++;
			
			if(EST3(AM,TM,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("DT")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(EST2(AM,type,isStatic,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("ID")) {
			name=tokenSet.get(i).get(1);
			i++;
			
			if(EST_doubledash1(AM,name,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("abstract")) {
			
			if(static_dash()) {
				
				if(return_type()) {
					
					if(tokenSet.get(i).get(0).equals ("ID")) {
						i++;
						
						if(tokenSet.get(i).get(0).equals ("(")) {
							i++;
							
							if(args(0)) {
								
								if(tokenSet.get(i).get(0).equals (")")) {
									i++;
									
									if(fun2("null",0,0)) {
										
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("sealed")) {
			
			if(static_dash()) {
				
				if(return_type()) {
					
					if(tokenSet.get(i).get(0).equals ("ID")) {
						i++;
						
						if(tokenSet.get(i).get(0).equals ("(")) {
							i++;
							
							if(args(0)) {
								
								if(tokenSet.get(i).get(0).equals (")")) {
									i++;
									
									if(fun2("null",0,0)) {
										
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("String")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(EST4(AM,type,isStatic,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("var")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(EST4(AM,type,isStatic,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("void")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(tokenSet.get(i).get(0).equals ("(")) {
					i++;
					int FR=database.create_funTable();
					
					if(args(FR)) {
						
						al=al+"->"+type;
						
						if(!database.insert_DT(ER, FR, name, al, AM, TM, isStatic, parent)) {
							System.out.println("Redeclaration Error, line #="+tokenSet.get(i).get(2));
						}
						
						al="null";
						parent="null";
				
						
						if(tokenSet.get(i).get(0).equals (")")) {
							i++;
							
							if(fun2(type,FR,ER)) {
								
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean EST_doubledash1(String AM,String name,int ER) {
		
		if(tokenSet.get(i).get(0).equals ("[") || tokenSet.get(i).get(0).equals ("ID")) {
			
			if(EST13(name,AM,0,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("(")) {
			i++;
			// constructor
			int FR=database.create_funTable();
			if(args(FR)) {
			
				al=al+"->"+"null";
				
				if(!database.insert_DT(ER, FR, name, al, AM, "null", 0, parent)) {
					System.out.println("Redeclaration Error");
				}
				
				al="null";
				parent="null";
		
				if(tokenSet.get(i).get(0).equals (")")) {
					i++;
					
					if(tokenSet.get(i).get(0).equals ("{")) {
						i++;
						
						if(MST("null",FR,ER)) {
							
							if(tokenSet.get(i).get(0).equals ("}")) {
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
	
	private boolean EST1(String AM,int isStatic,int ER) {
		
		String name="null";
		String type="null";
		String TM="None";

		if(tokenSet.get(i).get(0).equals ("const")) {
			TM=tokenSet.get(i).get(0);
			i++;
			
			if(EST3(AM,TM,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("DT")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(EST2(AM,type,isStatic,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("ID")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(EST13(type,AM,isStatic,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("String")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(EST4(AM,type,isStatic,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("var")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(EST4(AM,type,isStatic,ER)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("void")) {
			type=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(tokenSet.get(i).get(0).equals ("(")) {
					i++;
					int FR=database.create_funTable();
					
					if(args(FR)) {
						
						al=al+"->"+type;
						
						if(!database.insert_DT(ER, FR, name, al, AM, TM, isStatic, parent)) {
							System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
						}
						
						al="null";
						parent="null";
				
						
						if(tokenSet.get(i).get(0).equals (")")) {
							i++;
							
							if(fun2(type,FR,ER)) {
								
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}	
	
	private boolean EST13(String name,String AM,int isStatic,int ER) {
		
		if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				if(arr_O()) {
					
					if(tokenSet.get(i).get(0).equals ("ID")) {
						i++;
						
						if(tokenSet.get(i).get(0).equals ("(")) {
							i++;
							
							if(args(0)) {
								
								if(tokenSet.get(i).get(0).equals (")")) {
									i++;
									
									if(fun2("null",0,0)) {
										
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				String name2=tokenSet.get(i).get(1);
				i++;
				
				if(EST_dash1(name,name2,AM,isStatic,ER)) {
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean EST2(String AM,String type,int isStatic,int ER) {
		
		if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				if(arr_O()) {
					
					if(tokenSet.get(i).get(0).equals ("ID")) {
						i++;
						
						if(tokenSet.get(i).get(0).equals ("(")) {
							i++;
							
							if(args(0)) {
								
								if(tokenSet.get(i).get(0).equals (")")) {
									i++;
									
									if(fun2("null",0,0)) {
										
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				String name=tokenSet.get(i).get(1);
				i++;
				
				if(EST2_dash(type,name,AM,isStatic,ER)) {
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean EST2_dash(String type,String name,String AM,int isStatic,int ER) {
		
		
		if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals (";") ||
				tokenSet.get(i).get(0).equals (",")) {
			
			if(!database.insert_DT(ER, 0, name, type, AM, "null", isStatic, "null")) {
				System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
			}
			
			
			if(init(type, ER)) {
				
				if(list(type,"null",AM,isStatic,ER)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("(")) {
			i++;
			int FR=database.create_funTable();
			
			if(args(FR)) {
				
				al=al+"->"+type;
				
				if(!database.insert_DT(ER, FR, name, al, AM, "null", isStatic, parent)) {
					System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
				}
				
				al="null";
				parent="null";
			
				if(tokenSet.get(i).get(0).equals (")")) {
					i++;
					
					if(fun2(type,FR,ER)) {
						
						return true;
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				if(arr_dec_dash()) {
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean EST3(String AM,String TM,int ER) {
		
		String name="null";
		String type="null";
		int isStatic=0;

		if(tokenSet.get(i).get(0).equals ("DT")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(init(type, ER)) {
					
					if(list(type,TM,AM,isStatic,ER)) {
						
						return true;
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("ID")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(init2(type,name,TM,AM,isStatic,ER)) {
					
					if(obj2(type,TM,AM,isStatic,ER)) {
						
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean EST4(String AM,String type,int isStatic,int ER) {
		
		if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				if(arr_O()) {
					
					if(tokenSet.get(i).get(0).equals ("ID")) {
						i++;
						
						if(tokenSet.get(i).get(0).equals ("(")) {
							i++;
							
							if(args(0)) {
								
								if(tokenSet.get(i).get(0).equals (")")) {
									i++;
									
									if(fun2("null",0,0)) {
										
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				String name=tokenSet.get(i).get(1);
				i++;
				
				if(EST4_dash(name,AM,type,isStatic,ER)) {
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean EST4_dash(String name,String AM,String type,int isStatic,int ER) {

		if(tokenSet.get(i).get(0).equals ("(")) {
			i++;
			int FR=database.create_funTable();
			
			if(args(FR)) {
				
				al=al+"->"+type;
				
				if(!database.insert_DT(ER, FR, name, al, AM, "null", isStatic, parent)) {
					System.out.println("Redeclaration Error, line #="+tokenSet.get(i).get(2));
				}
				
				al="null";
				parent="null";
			
				
				if(tokenSet.get(i).get(0).equals (")")) {
					i++;
					
					if(fun2(type,FR,ER)) {
						
						return true;
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				if(arr_dec_dash()) {
					
					return true;
				}
			}
		}
			
		return false;
	}
	
	private boolean ev2() {
		
		if(tokenSet.get(i).get(0).equals (",")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				i++;
				
				if(ev()) {
					
					if(ev2()) {
						
						return true;
					}
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("public")||
					tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") ||
					tokenSet.get(i).get(0).equals ("const") || tokenSet.get(i).get(0).equals ("DT")|| 
					tokenSet.get(i).get(0).equals ("abstract") || tokenSet.get(i).get(0).equals ("sealed") ||
					tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("var") ||
					tokenSet.get(i).get(0).equals ("void")) {
				
				return true;
			}
		}
		return false;
	}
	
	private boolean ev() {
		
		if(tokenSet.get(i).get(0).equals ("=")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("int_const")) {
				i++;
				
				return true;
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("ID") || tokenSet.get(i).get(0).equals ("public")||
					tokenSet.get(i).get(0).equals ("private") || tokenSet.get(i).get(0).equals ("static") ||
					tokenSet.get(i).get(0).equals ("const") || tokenSet.get(i).get(0).equals ("DT")|| 
					tokenSet.get(i).get(0).equals ("abstract") || tokenSet.get(i).get(0).equals ("sealed") ||
					tokenSet.get(i).get(0).equals ("String") || tokenSet.get(i).get(0).equals ("var") ||
					tokenSet.get(i).get(0).equals ("void") || tokenSet.get(i).get(0).equals (",")) {
				return true;
			}
		}
		return false;
	}
	
	private boolean case_st(String type,int CR,int FR) {
		
		if(tokenSet.get(i).get(0).equals ("case")) {
			i++;
			
			if(constants()) {
				
				if (this.type.equals("bool")) {
					System.out.println("Cannot use bool as case constant,line #="+tokenSet.get(i).get(2));
				}
				this.type="null";
				if(tokenSet.get(i).get(0).equals (":")) {
					i++;
					
					if(case_body(type,CR,FR)) {
						
						if(tokenSet.get(i).get(0).equals ("break")) {
							i++;
							
							if(tokenSet.get(i).get(0).equals (":")) {
								i++;
								
								if(case_st(type,CR,FR)) {
									
									return true;
								}
							}
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("default")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals (":")) {
				i++;
				
				if(MST(type,CR,FR)) {
					
					return true;
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals ("}")) {
				
				return true;
			}
		}
		return false;
	}
	
	private boolean case_body(String type,int CR,int FR) {
		
		String name="null";
		String type1="null";
		String TM="None";

		if(tokenSet.get(i).get(0).equals ("const")) {
			TM=tokenSet.get(i).get(0);
			
			i++;
			
			if(case_dash(TM,CR,FR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("DT")) {
			type1=tokenSet.get(i).get(1);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
//				Type_DT T=database.lookUp_att_DT(CR, name);
//				
//				if(T!=null) {
//				
//					System.out.println("Already define in class,line #="+tokenSet.get(i).get(2));
//				
//				}else 
//					
				if(!database.insert_FT(FR, name, type1, TM)) {
					System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
				}
			
				if(case_doubledash(type1,CR,FR)) {
					
					return true;
				}
			}
			
		}
		else if(tokenSet.get(i).get(0).equals ("while")) {
			
			if(while_stmt(type,CR,FR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("for")) {
			
			if(for_stmt(type,CR,FR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("if")) {
			
			if(if_elif_stmt(type,CR,FR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("do")) {
			
			if(dowhile_stmt(type,CR,FR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("forEach")) {
			
			if(forEach(type,CR,FR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("switch")) {
			
			if(switch_stmt(type,CR,FR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("this")) {
			i++;
			
			if(tb2(CR)) {
				
				if(tokenSet.get(i).get(0).equals("ID")) {
					name=tokenSet.get(i).get(1);
					i++;
					
					if(case1(name,CR,FR)) {
						
						return true;
					}
				}
			
			}
		}
		else if(tokenSet.get(i).get(0).equals ("base")) {
			
			Type_MT T=database.lookUp_MT(CR);
			
			  if(T!=null) {
				  
				  if(T.parent=="null") {
					
					  System.out.println("There is no parent class,line #="+tokenSet.get(i).get(2));
			
				  }else {
					  
					  String parents[]=T.parent.split(",");
					  
					  for (int i = 0; i < parents.length; i++) {
						String p=parents[i];
						
						Type_MT t1=database.lookUp_MT(p);
						
						if (t1==null) {
							
							System.out.println("There is no parent class,line #="+tokenSet.get(i).get(2));
						
						}else if(!t1.type.equals("class")) {
							System.out.println("Parent must be class,line #="+tokenSet.get(i).get(2));
						}else {
							CR=t1.id;
						}
					}
				  }
				  
			  }else {
				  System.out.println("Undeclared Class");
			  }
			
			i++;
			
			
			
			
			if(tb2(CR)) {
				
				if(tokenSet.get(i).get(0).equals("ID")) {
					i++;
					
					if(case1(name,CR,FR)) {
						
						return true;
					}
				}
			
			}
		}
		else if(tokenSet.get(i).get(0).equals ("inc_dec")) {
			
			i++;
			
			if(X(CR)) {
			
				if (database.compatibility(this.type)==null) {
					
					System.out.println("Type error,line #="+tokenSet.get(i).get(2));
				}
				this.type="null";
				if(tokenSet.get(i).get(0).equals(";")) {
					i++;
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("ID")) {
		
			name=tokenSet.get(i).get(1);
			
			i++;
			
			if(case2(name,CR,FR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("String")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals ("[")) {
					i++;
					
					if(tokenSet.get(i).get(0).equals ("]")) {
						i++;
						
						if(arr_dec_dash()) {
							
							return true;
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("var")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals ("[")) {
					i++;
					
					if(tokenSet.get(i).get(0).equals ("]")) {
						i++;
						
						if(arr_dec_dash()) {
							
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean case_dash(String TM,int CR,int FR) {
		
		String name="null";
		String type="null";

		if(tokenSet.get(i).get(0).equals ("DT")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
//				Type_DT T=database.lookUp_att_DT(CR, name);
				
//				if(T!=null) {
//					System.out.println("Already define in class,line #="+tokenSet.get(i).get(2));
//				}else
				if (!database.insert_FT(FR, name, type, TM)) {
					System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
				}
				
				if(init(type, CR)) {
					
					if(list(type,"null","Default",FR,0)) {
						
						return true;
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("ID")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(init2(type,name,TM,"Default",0, FR)) {
					
					if(obj2(type,TM,"Default",FR,0)) {
						
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean case_doubledash(String type,int CR,int FR) {
		
		if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals (";") ||
				tokenSet.get(i).get(0).equals (",")) {
			
			if(init(type, CR)) {
				
				if(list(type,"null","Default",FR,0)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("]")) {
				i++;
				
				if(arr_dec_dash()) {
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean case1(String name,int CR,int FR) {
		
		if(tokenSet.get(i).get(0).equals (".")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				String name2=tokenSet.get(i).get(1);

				Type_MT T=database.lookUp_MT(name);
				
				if(T==null) {
					Type_FT T2=database.lookUp_FT(FR, name2);
					if(T2==null) {
						System.out.println("Undeclaration Error,line #="+tokenSet.get(i).get(2));
					}else {
						Type_MT T3=database.lookUp_MT(T2.name);
						if(T3==null) {
							System.out.println("Undeclared class,line #="+tokenSet.get(i).get(2));
						}else if (!T3.type.equals("class")) {
							System.out.println("Must be class type,line #="+tokenSet.get(i).get(2));
						}else {
							CR=T3.id;
							if(T2.typeMod.equals("static")) {
								System.out.println("Acces Issue,line #="+tokenSet.get(i).get(2));
							}
						}
					}
				}else {
					CR=T.id;
				}
				

				i++;
				
				if(case1(name2,CR,FR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals ("CAO") || 
				tokenSet.get(i).get(0).equals ("inc_dec")) {
			
			if(case1_dash(name,CR,FR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(OE(CR)) {
				
				if(tokenSet.get(i).get(0).equals ("]")) {
					i++;
					
					if(arr2()) {
						
						if(case12()) {
							
							return true;
						}
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("(")) {
			i++;
			
			if(params(CR)) {
				
				if(tokenSet.get(i).get(0).equals(")")) {
					i++;
					
					Type_DT T=database.lookUp_fn_DT(CR, name, pl);
					if(T==null) {
						System.out.println("Undeclaration Error,line #="+tokenSet.get(i).get(2));
					}
					
					if (T.accessMod.equals("private")) {
						System.out.println("Access issue,line #="+tokenSet.get(i).get(2));
					}
					
					String temp[]=T.type.split("->");
					String type=temp[temp.length-1];
				
				if(tokenSet.get(i).get(0).equals (")")) {
					i++;
					
					if(case13(type,CR,FR)) {
						
						return true;
					}
				}
			}
		}
	}
			return false;
	}
	
	@SuppressWarnings("null")
	private boolean case1_dash(String name,int CR,int FR) {
		String type="None";
		
		if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals ("CAO")) {
			
			if(ass_opr()) {
				
				if(OE(CR)) {
					
					Type_DT T=database.lookUp_att_DT(CR, name);
					
					if(T==null) {
						Type_FT T2=database.lookUp_FT(FR,name);
						if(T2==null) {
							System.out.println("Undeclaration Error,line #="+tokenSet.get(i).get(2));
						}else {
							type=T2.type;
						}
						
						if(T.name.equals("private")) {
							System.out.println("Access Issue,line #="+tokenSet.get(i).get(2));
						}
						
					}else {
						type=T.type;
					}
					
					String type2=database.compatibility(type, this.type, this.Op);
					
					if (type2==null) {
						System.out.println("Type Check Error,line #="+tokenSet.get(i).get(2));
					}

					this.type="null";
					if(tokenSet.get(i).get(0).equals (";")) {
						i++;
						
						return true;
					}
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("inc-dec")) {
			Op=tokenSet.get(i).get(0);
			i++;

			Type_DT T=database.lookUp_att_DT(CR, name);
			
			if(T==null) {
				Type_FT T2=database.lookUp_FT(FR,name);
				if(T2==null) {
					System.out.println("Undeclaration Error,line #="+tokenSet.get(i).get(2));
				}else {
					type=T2.type;
				}
				
				if(T.name.equals("private")) {
					System.out.println("Access Issue,line #="+tokenSet.get(i).get(2));
				}
				
			}else {
				type=T.type;
			}
			
			@SuppressWarnings("static-access")
			String type2=database.compatibility(type);
			
			if (type2==null) {
				System.out.println("Type Check Error,line #="+tokenSet.get(i).get(2));
			}

			if(tokenSet.get(i).get(0).equals (";")) {
				i++;
				
				return true;
			}
		}
		
		return false;
	}
	
	private boolean case12() {
		
		if(tokenSet.get(i).get(0).equals (".")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				i++;
				
				if(case1("null",0,0)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("=") || tokenSet.get(i).get(0).equals ("CAO") || 
				tokenSet.get(i).get(0).equals ("inc_dec")) {
			
			if(case1_dash("null",0,0)) {
				
				return true;
			}
		}
		return false;
	}
	
	private boolean case13(String type,int CR,int FR) {
		
		if(tokenSet.get(i).get(0).equals (".")) {
			i++;

			Type_MT T=database.lookUp_MT(type);
			
			if(T==null) {
				System.out.println("Undeclaration Error,line #="+tokenSet.get(i).get(2));
			}else {
				CR=T.id;
			}
			
			if(tokenSet.get(i).get(0).equals ("ID")) {
				String name=tokenSet.get(i).get(1);
				i++;
				
				
				
				if(case1(name,CR,FR)) {
					
					return true;
				}
			}
		}
		else if(tokenSet.get(i).get(0).equals ("[")) {
			i++;
			
			if(OE(CR)) {
				
				if(tokenSet.get(i).get(0).equals ("]")) {
					i++;
					
					if(arr2()) {
						
						if(case12()) {
							
							return true;
						}
					}
				}
			}
		}
		else {
			
			if(tokenSet.get(i).get(0).equals(";")) {
				i++;
				
				return true;
			}
		}
		return false;
	}
	
	private boolean case2(String type,int CR,int FR) {
		
		if(tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("=") ||
				tokenSet.get(i).get(0).equals("CAO") || tokenSet.get(i).get(0).equals("inc_dec") ||
				tokenSet.get(i).get(0).equals("[") || tokenSet.get(i).get(0).equals("(")) {
			
			if(case1(type,CR,FR)) {
				
				return true;
			}
		}
		else if(tokenSet.get(i).get(0).equals("ID")) {
			String name=tokenSet.get(i).get(0);
			i++;
			
			if(init2(type,name,"null","Default",FR,0)) {
				if(obj2(type,"null","Default",0,FR)) {
					return true;
				}
			}
			
		}
		return false;
	}
	

	private boolean MST(String type,int CR,int FR) {
		
//		{ const, DT, while, for, if, do, forEach, 
//			switch, ID, this, base, inc_dec, return, pass, break, try, String, var, throw }
	
		
		if(tokenSet.get(i).get(0).equals("const") || tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("while")||
			tokenSet.get(i).get(0).equals("for") || tokenSet.get(i).get(0).equals("if") || tokenSet.get(i).get(0).equals("do") ||
			tokenSet.get(i).get(0).equals("forEach") || tokenSet.get(i).get(0).equals("switch") || tokenSet.get(i).get(0).equals("ID") ||
			tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("base") || tokenSet.get(i).get(0).equals("inc_dec")||
			tokenSet.get(i).get(0).equals("return") || tokenSet.get(i).get(0).equals("pass") || tokenSet.get(i).get(0).equals("break") ||
			tokenSet.get(i).get(0).equals("try") || tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("var") ||
			tokenSet.get(i).get(0).equals("throw")) {
			
			if(isReturn==1) {
				System.out.println("Invalid declaration after return stmt,line #="+tokenSet.get(i).get(2));
			}
			
			if(SST(type,CR,FR)) {
				if(MST(type,CR,FR)) {
					return true;
				}
			}
			
		}else if(tokenSet.get(i).get(0).equals("}")) {
			return true;
		}
		
		return false;
	}

	

	private boolean SST(String type,int CR,int FR) {
		String name="null";
		String type1="null";
		String TM="None";

		if(tokenSet.get(i).get(0).equals("const")) {
			TM=tokenSet.get(i).get(0);
			i++;
			
			if(SST_dash(TM,CR,FR)) {
				return true;
			}
		}else if(tokenSet.get(i).get(0).equals("DT")) {
			type1=tokenSet.get(i).get(1);
			i++;
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
		
//				Type_DT T=database.lookUp_att_DT(CR, name);
//				
//				if(T!=null) {
//				
//					System.out.println("Already define in class,line #="+tokenSet.get(i).get(2));
//				
//				}else 
				if(!database.insert_FT(FR, name, type1, TM)) {
					System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
				}
				
				if(SST1(type1,CR,FR)) {
					return true;
				}
			}
		}else if (while_stmt(type,CR,FR) || for_stmt(type,CR,FR) || if_elif_stmt(type,CR,FR) || dowhile_stmt(type,CR,FR) || forEach(type,CR,FR) || switch_stmt(type,CR,FR)
				|| return_stmt(type,CR,FR) || try_catch_stmt(type,CR,FR) || throw_stmt()) {
		
			return true;
		
		}else if(tokenSet.get(i).get(0).equals("ID")) {
			name=tokenSet.get(i).get(1);
			i++;
			
			
			if(SST2(name,CR,FR)) {
				return true;
			}
			
		}else if(tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("base")) {
			
			if(tokenSet.get(i).get(0).equals("base")) {
				Type_MT T=database.lookUp_MT(CR);
				
			  if(T!=null) {
				  
				  if(T.parent=="null") {
					
					  System.out.println("There is no parent class,line #="+tokenSet.get(i).get(2));
			
				  }else {
					  
					  String parents[]=T.parent.split(",");
					  
					  for (int i = 0; i < parents.length; i++) {
						String p=parents[i];
						
						Type_MT t1=database.lookUp_MT(p);
						
						if (t1==null) {
							
							System.out.println("There is no parent class,line #="+tokenSet.get(i).get(2));
						
						}else if(!t1.type.equals("class")) {
							System.out.println("Parent must be class,line #="+tokenSet.get(i).get(2));
						}else {
							CR=t1.id;
						}
					}
				  }
				  
			  }else {
				  System.out.println("Undeclared Class,line #="+tokenSet.get(i).get(2));
			  }
			}
			
			
			i++;
			
			
			
			if(tb2(CR)) {
				if(tokenSet.get(i).get(0).equals("ID")) {
					name=tokenSet.get(i).get(1);
					i++;
					
					if(SST3(name,CR,FR)) {
						return true;
					}
				}
			}
			
		}else if(tokenSet.get(i).get(0).equals("inc_dec")) {
			String op=tokenSet.get(i).get(0);
			
			i++;
			
			if(X(CR)) {
				
				type=database.compatibility(ar_type);
				if(type==null) {
					System.out.println("Type Check Error,line #="+tokenSet.get(i).get(2));
				}
				
				if(tokenSet.get(i).get(0).equals(";")){
					i++;
					
					return true;
				}
			}
		
		}else if(tokenSet.get(i).get(0).equals("pass") || tokenSet.get(i).get(0).equals("break")){
			
			i++;
			if(isLoop<=0) {
				System.out.println("Must occur in a Loop,line #="+tokenSet.get(i).get(2));
			}
			
			if(tokenSet.get(i).get(0).equals(";")) {
				i++;
				
				return true;
			}
		}else if(tokenSet.get(i).get(0).equals("String") || tokenSet.get(i).get(0).equals("var")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals("[")) {
					i++;
					if(tokenSet.get(i).get(0).equals("]")) {
						i++;
						
						if(arr_dec_dash()) {
							return true;
						}
					}
				}
			}
				
			
		}
		
		return false;
	}
	
	private boolean SST_dash(String TM,int CR,int FR) {
		
		String name="null";
		String type="null";

		if(tokenSet.get(i).get(0).equals("DT")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
//				Type_DT T=database.lookUp_att_DT(CR, name);
//				
//				if(T!=null) {
//					System.out.println("Already define in class,line #="+tokenSet.get(i).get(2));
//				}else 
				if (!database.insert_FT(FR, name, type, TM)) {
					System.out.println("Redeclaration Error, line #="+tokenSet.get(i).get(2));
				}
				
				if(init(type, CR)) {
					if(list(type,TM,"Default",0,FR)) {
						return true;
					}
				}
			}
		}else if (tokenSet.get(i).get(0).equals("ID")) {
			type=tokenSet.get(i).get(1);
			i++;
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
				
				if(init2(type,name,TM,"Defalut",FR,0)) {
					if(obj2(type,TM,"Default",0,FR)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean SST1(String type,int CR,int FR) {
		
		if(init(type, CR)){
			if(list(type,"null","Default",0,FR)) {
				return true;
			}
		}else if(tokenSet.get(i).get(0).equals("[")){
			i++;
			if(tokenSet.get(i).get(0).equals("]")) {
				i++;
				if(arr_dec_dash()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean SST2(String name,int CR,int FR) {
		
		
		if(SST3(name,CR,FR)) {
			return true;
		}else if(tokenSet.get(i).get(0).equals("ID")) {
			String type=name;
			name=tokenSet.get(i).get(1);
			i++;
			
			
			if(SST_double_dash(type,name,CR,FR)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean SST_double_dash(String type,String name,int CR,int FR) {
		
		if(init2(type,name,"null","Default",FR,0)) {
			if(obj2(type,"null","Default",0,FR)) {
				return true;
			}
		}else if(tokenSet.get(i).get(0).equals("[")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals("]")) {
				i++;
				if(arr_dec_dash()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	private boolean SST3(String name,int CR,int FR) {
		
		if(tokenSet.get(i).get(0).equals(".")) {
			i++;
		
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				String name2=tokenSet.get(i).get(1);
				i++;
			
				Type_MT T=database.lookUp_MT(name);
				
				if(T==null) {
					Type_FT T2=database.lookUp_FT(FR, name2);
					if(T2==null) {
						System.out.println("Undeclaration Error,line #="+tokenSet.get(i).get(2));
					}else {
						Type_MT T3=database.lookUp_MT(T2.name);
						if(T3==null) {
			
							System.out.println("Undeclared class,line #="+tokenSet.get(i).get(2));
						
						}else if (!T3.type.equals("class")) {
						
							System.out.println("Must be class type,line #="+tokenSet.get(i).get(2));
						
						}else {
							CR=T3.id;
							if(T2.typeMod.equals("static")) {
								System.out.println("Acces Issue,line #="+tokenSet.get(i).get(2));
							}
						}
					}
				}else {
					CR=T.id;
				}
				
				
				if(SST3(name2,CR,FR)) {
					return true;
				}
			}
		}else if(SST3_dash(name,CR,FR)) {
			return true;
		
		}else if(tokenSet.get(i).get(0).equals("[")) {
			i++;
			
			if(OE(CR)) {
				if(tokenSet.get(i).get(0).equals("]")) {
					i++;
					
					if(arr2()) {
							if(SST32()) {
								return true;
							}
					}
				}
			}
		}else if (tokenSet.get(i).get(0).equals("(")) {
			i++;
			
			if(params(CR)) {
				if(tokenSet.get(i).get(0).equals(")")) {
					i++;
					
					Type_DT T=database.lookUp_fn_DT(CR, name, pl);
					if(T==null) {
						System.out.println("Undeclaration Error,line #="+tokenSet.get(i).get(2));
					}
					
					if (T.accessMod.equals("private")) {
						System.out.println("Access issue,line #="+tokenSet.get(i).get(2));
					}
					
					String temp[]=T.type.split("->");
					String type=temp[temp.length-1];
					
					if(SST33(type,CR,FR)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("null")
	private boolean SST3_dash(String name,int CR,int FR) {
		String type="None";
		if(ass_opr()) {
			if(OE(CR)) {
				Type_DT T=database.lookUp_att_DT(CR, name);
				
				if(T==null) {
					Type_FT T2=database.lookUp_FT(FR,name);
					if(T2==null) {
						System.out.println("Undeclaration Error,line #="+tokenSet.get(i).get(2));
					}else {
						type=T2.type;
					}
					
				}else {
					type=T.type;
					
					if(T.name.equals("private")) {
						System.out.println("Access Issue,line #="+tokenSet.get(i).get(2));
					}
					
				}
				
				String type2=database.compatibility(type, this.type, this.Op);
				
				if (type2==null) {
					System.out.println("Type Check Error,line #="+tokenSet.get(i).get(2));
				}
				
				this.type="null";
				if(tokenSet.get(i).get(0).equals(";")) {
					i++;
				return true;
				}
			}
		}else if (tokenSet.get(i).get(0).equals("inc_dec")) {
			Op=tokenSet.get(i).get(0);
			i++;
	
			Type_DT T=database.lookUp_att_DT(CR, name);
			
			if(T==null) {
				Type_FT T2=database.lookUp_FT(FR,name);
				if(T2==null) {
					System.out.println("Undeclaration Error,line #="+tokenSet.get(i).get(2));
				}else {
					type=T2.type;
				}
				
				if(T.name.equals("private")) {
					System.out.println("Access Issueline #="+tokenSet.get(i).get(2));
				}
				
			}else {
				type=T.type;
			}
			
			String type2=database.compatibility(type);
			
			if (type2==null) {
				System.out.println("Type Check Error,line #="+tokenSet.get(i).get(2));
			}
			
			
			
			if(tokenSet.get(i).get(0).equals(";")) {
				i++;
				return true;
			}
			
			return true;
		
		}
		
		
		return false;
	}
	
	private boolean SST32() {
		
		if(tokenSet.get(i).get(0).equals(".")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				i++;
				
				if(SST3("None",0,0)) {
					return true;
				}
			}
		}else if(SST3_dash("None",0,0)) {
			return true;
		}
		
		return false;
	}
	
	private boolean SST33(String type,int CR,int FR) {
	
		if(tokenSet.get(i).get(0).equals(".")) {
			i++;
			
			Type_MT T=database.lookUp_MT(type);
			
			if(T==null) {
				System.out.println("Undeclaration Error,line #="+tokenSet.get(i).get(2));
			}else {
				CR=T.id;
			}
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				String name=tokenSet.get(i).get(1);
				i++;
				
				if(SST3(name,CR,FR)) {
					return true;
				}
			}
		}else if (tokenSet.get(i).get(0).equals("[")) {
			i++;
			if(OE(CR)) {
				
				if(tokenSet.get(i).get(0).equals("]")) {
					i++;
					
					if(arr2()) {
						if(SST32()) {
							return true;
						}
					}
				}
			}
			
		}else if (tokenSet.get(i).get(0).equals(";")) {
			i++;
			return true;
		}
		
		
		return false;
	}
	
	private boolean body(String type,String KW,int CR,int FR) {
		
		if(tokenSet.get(i).get(0).equals(";")) {
			i++; 
			return true;
		}else if(SST(type,CR,FR)) {
			return true;
		}else if (tokenSet.get(i).get(0).equals("{")) {
			i++;
			s++;
			
			database.createScope(KW, s, isLoop);
			
			if(MST(type,CR,FR)) {
				if(tokenSet.get(i).get(0).equals("}")) {
					i++;
					Scope temp=database.getScope();
					
					if(temp.KW.equals("while") || temp.KW.equals("do") || temp.KW.equals("for") || temp.KW.equals("forEach")) {
						isLoop--;
					}
					s--;
					database.destroyScope();
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	private boolean while_stmt(String type,int CR,int FR) {
	
		String KW="";
		if(tokenSet.get(i).get(0).equals("while")) {
			KW=tokenSet.get(i).get(0);
			i++;
			isLoop++;
			
			if(tokenSet.get(i).get(0).equals("(")) {
				i++;
				if(OE(CR)) {
					
					if(tokenSet.get(i).get(0).equals(")")) {
						i++;

						if(!type.equals("bool")) {
							System.out.println("Must be Boolean type,line #="+tokenSet.get(i).get(2));
						}
						
						if(body(KW,type,CR,FR)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean for_stmt(String type,int CR,int FR) {
		
		
		if(tokenSet.get(i).get(0).equals("for")) {
			String KW=tokenSet.get(i).get(0);
			i++;
			isLoop++;
			
			if(tokenSet.get(i).get(0).equals("(")) {
				i++;
				if(c1(CR,FR)) {
					
					if(c2(CR)) {
						if (!this.type.equals("bool")) {
							System.out.println("Type Error,line #="+tokenSet.get(i).get(2));
						}
						this.type="null";
						
						if(tokenSet.get(i).get(0).equals(";")) {
							i++;
									
							if(c3(CR)) {
								
								if(tokenSet.get(i).get(0).equals(")")) {
									i++;
									if(body(KW,type,CR,FR)) {
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
	
	private boolean if_elif_stmt(String type,int CR,int FR) {
		
		if(tokenSet.get(i).get(0).equals("if")) {
			String KW=tokenSet.get(i).get(0);
			i++;
			if(tokenSet.get(i).get(0).equals("(")) {
				i++;
		
				if(OE(CR)) {
					if(tokenSet.get(i).get(0).equals(")")) {
						i++;
						if(body(KW,type,CR,FR)) {
							if(elif(type,CR,FR)) {
								if(else_dash(type,CR,FR)) {
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
	
	private boolean dowhile_stmt(String type,int CR,int FR) {
		
		if(tokenSet.get(i).get(0).equals("do")) {
			String KW=tokenSet.get(i).get(0);
			isLoop++;
			i++;
			
			if(body(KW,type,CR,FR)) {
				
				if(tokenSet.get(i).get(0).equals("while")) {
					i++;
					
					if(tokenSet.get(i).get(0).equals("(")) {
						
						i++;
						
						if(OE(CR)) {
							
							if(!this.type.equals("bool")) {
								System.out.println("Must be boolean type,,line #="+tokenSet.get(i).get(2));
							}
							this.type="null";
							if(tokenSet.get(i).get(0).equals(")")) {
								i++;
								
								if(tokenSet.get(i).get(0).equals(";")) {
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
	
	private boolean forEach(String type,int CR,int FR) {
		
		
		if(tokenSet.get(i).get(0).equals("forEach")) {
			String KW=tokenSet.get(i).get(0);
			isLoop++;
			i++;
			
			if(tokenSet.get(i).get(0).equals("(")) {
				i++;
				int isID=0;
				if(tokenSet.get(i).get(0).equals("ID")) {
					isID=1;
				}
				if(Data_type()) {
					
					
					
					
					if(tokenSet.get(i).get(0).equals("ID")) {
						String name=tokenSet.get(i).get(1);
						i++;
					
						if(isID==1) {
							Type_MT T1=database.lookUp_MT(ar_type);
							
							if(T1==null) {
								System.out.println("Undeclared Class,line #="+tokenSet.get(i).get(2));
							
							}else if(!T1.type.equals("class")){
								
								System.out.println("Must be class type,line #="+tokenSet.get(i).get(2));
						
							}else if(!T1.cat.equals("abstract")) {
								
								System.out.println("Must not be abstract,,line #="+tokenSet.get(i).get(2));
								
							}
								
									
							}else {
								Type_DT T2=database.lookUp_att_DT(CR, name);
								
								if (T2!=null) {
									
									System.out.println("Already defined in Class,line #="+tokenSet.get(i).get(2));
									
								}else {
									
									Type_FT T3=database.lookUp_FT(FR, name);
									if(T3!=null) {
										System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
									}
						
								}
							}	 
					
					if(tokenSet.get(i).get(0).equals("in")) {
							i++;
							
							if(tokenSet.get(i).get(0).equals("ID")) {
								name=tokenSet.get(i).get(1);
								i++;
								
								Type_DT att=database.lookUp_att_DT(CR, name);
								if (att==null) {
									
									Type_FT fn_att=database.lookUp_FT(FR, name);
									if (fn_att==null) {
							
										System.out.println("Undeclaration Error,line #="+tokenSet.get(i).get(2));
									
									}else if(!fn_att.type.equals(ar_type)){
										
										System.out.println("Type Error,line #="+tokenSet.get(i).get(2));
										
									}
								}else if (!att.type.equals(ar_type)) {
									System.out.println("Type Error,line #="+tokenSet.get(i).get(2));
								}
									
									
								}
								
								if(tokenSet.get(i).get(0).equals(")")) {
									i++;
									
									if(body(KW,type,CR,FR)) {
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
	
	private boolean switch_stmt(String type,int CR,int FR) {
		
		if(tokenSet.get(i).get(0).equals("switch")) {
			String KW=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals("(")) {
				i++;
				
				
				if(constants()) {
					if (this.type.equals("bool")) {
						
						System.out.println("Cannot use bool in Switch,line #="+tokenSet.get(i).get(2));
					}
					this.type="null";
					if(tokenSet.get(i).get(0).equals(")")) {
						i++;
						if(tokenSet.get(i).get(0).equals("{")) {
							i++;
							s++;
							
							database.createScope(KW, s, isLoop);
							if(case_st(type,CR,FR)) {
								if(tokenSet.get(i).get(0).equals("}")) {
									i++;
									
									s--;
									database.destroyScope();
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
	
	private boolean return_stmt(String type,int CR,int FR) {
		
		
		if(tokenSet.get(i).get(0).equals("return")) {
			
			i++;
			
			
			
			if(RST(CR)) {
			
				Scope scope=database.getScope();
				if(scope!=null) {
					
					if(this.type.equals(";")) {
						if(!type.equals("void")) {
							System.out.println("Type must be void,line #="+tokenSet.get(i).get(2));
						}
					}
				}else if (!this.type.equals(type)) {
					System.out.println("Return type Error,,line #="+tokenSet.get(i).get(2));
					isReturn=1;
				}else {
					isReturn=1;
				}
				this.type="null";
				return true;
			}
		}
		return false;
	}
	
	private boolean try_catch_stmt(String type,int CR,int FR) {
		
		if(tokenSet.get(i).get(0).equals("try")) {
			String KW=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals("{")) {
				s++;
				i++;
				
				database.createScope(KW, s, isLoop);
				if(MST(type,CR,FR)) {
					
					if(tokenSet.get(i).get(0).equals("}")) {
						s--;
						database.destroyScope();
						i++;
						
						if(tokenSet.get(i).get(0).equals("catch")) {
							KW=tokenSet.get(i).get(0);
							i++;
							
							if(tokenSet.get(i).get(0).equals("(")) {
								i++;
								
								if(tokenSet.get(i).get(0).equals("ID")) { // for Exception
									
									i++;
									
									if(tokenSet.get(i).get(0).equals(")")) {
										i++;
										
										if(tokenSet.get(i).get(0).equals("{")) {
											s++;
											i++;
											
											database.createScope(KW, s, isLoop);
											if(MST(type,CR,FR)) {
												
												if(tokenSet.get(i).get(0).equals("}")) {
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
	
	private boolean throw_stmt() {
		
		if(tokenSet.get(i).get(0).equals("throw")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals("new")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals("ID")) {
					
					i++;
					
					if(tokenSet.get(i).get(0).equals("(")) {
						i++;
						
						if(params(CR)) {
							
							if(tokenSet.get(i).get(0).equals(")")) {
								i++;
								
								if(tokenSet.get(i).get(0).equals(";")) {
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
	
	// assignment
	
	private boolean assignment() {
		
		if(X(CR)) {
			String t1=this.type;
			this.type="null";
			if(ass_opr()) {
				String op=Op;
				if(OE(CR)) {
					String t2=type;
					String T=database.compatibility(t1, t2, op);
					this.type="null";
					if (T==null) {
						
						System.out.println("Type Uncompatible Error,line #="+tokenSet.get(i).get(2));
					}
					return true;
				}
			}
		}
		
		return false;
	}
	
	// ass_opr
	private boolean ass_opr() {
		
		if(tokenSet.get(i).get(0).equals("=") || tokenSet.get(i).get(0).equals("CAO")) {
			Op=tokenSet.get(i).get(0);
			i++;
			
			return true;
		}
		return false;
	}
	
	
	// init2 
	
	@SuppressWarnings("unused")
	private boolean init2(String type,String name,String TM,String AM,int isStatic,int id) {
		String name2="";
		if(tokenSet.get(i).get(0).equals("=")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals("new")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals("ID")) {
					name2=tokenSet.get(i).get(1);
					i++;
	
					
					
					// object simple instantiation
					
					if(tokenSet.get(i).get(0).equals("(")) {
						i++;
						if (params(id)) {
	
							Type_MT T=database.lookUp_MT(type);
							
							if (T==null) {
							
								System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
				
							}else if(!T.type.equals("class")) {
								
								System.out.println("Must be class type,line #="+tokenSet.get(i).get(2));
								
							}else if (T.cat.equals("abstract")) {
								
								System.out.println("Could not instantiate an abstract class object,line #="+tokenSet.get(i).get(2));
							
							}else {
								
								Type_MT T2=database.lookUp_MT(name2);
								
								
								if(!T2.name.equals(type)){
									if(T2.parent==null){
										System.out.println("Could not instantiate object,line #="+tokenSet.get(i).get(2));
									
									}else{
										String parents[]=T2.parent.split(",");
										String tempP="";
										
										
										for (int i = 0; i < parents.length; i++) {
											
											if(parents[i].equals(type)) {
								
												Type_DT	constructor=database.lookUp_fn_DT(T2.id, name2, pl+"->"+"null");
												
												
												if (constructor==null && !pl.equals("null")) {
												
													System.out.println("Constructor is not defined,line #="+tokenSet.get(i).get(2));
												
												}else if (id<20000) {
													
													if (!database.insert_DT(id, 0, name, type, AM, TM, isStatic, "null")) {
														System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
													}
													
												}else if (!database.insert_FT(id, name, type, TM)) {
													System.out.println("Redeclaration Error");	
												}
											}
											
										}
										
										
									}
								}else{
							
									Type_DT	constructor=database.lookUp_fn_DT(T2.id, name2, pl+"->"+"null");
									
									if (constructor==null && !pl.equals("null")) {
									
										System.out.println("Constructor is not defined,line #="+tokenSet.get(i).get(2));
									
									}else if (id<20000) {
										
										if (!database.insert_DT(id, 0, name, type, AM, TM, isStatic, "null")) {
											System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));
										}
										
									}else if (!database.insert_FT(id, name, type, TM)) {
										System.out.println("Redeclaration Error,line #="+tokenSet.get(i).get(2));	
									}
								}
								
							}
							
							if (tokenSet.get(i).get(0).equals(")")) {
								i++;
								if (tokenSet.get(i).get(0).equals(";")) {
									i++;
									return true;
								}
							}
						}
					}
					
//					if(Y()) {
//						if(tokenSet.get(i).get(0).equals(";")) {
//							i++;
//							return true;
//						}
//					}
				
				
				}
			}
		}
		return false;
	}
	
	// obj2
	
	private boolean obj2(String type,String TM,String AM,int isStatic,int id) {
	
		if(tokenSet.get(i).get(0).equals(",")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				String name=tokenSet.get(i).get(1);
				i++;
				
				if(init2(type,name,TM,AM,isStatic,id)) {
					if(obj2(type,TM,AM,isStatic,id)) {
						return true;
					}
				}
			}
		}else if (tokenSet.get(i).get(0).equals(";")) {
			return true;
			
//			{ public, private, const, DT, ID, String, var, void, class, interface, enum, static, abstract, 
//				sealed, while, for, if, do, forEach, switch, this, base, inc_dec, return, pass, break, try, throw, elif, else, } }

		}else if(tokenSet.get(i).get(0).equals("public") || tokenSet.get(i).get(0).equals("private") || tokenSet.get(i).get(0).equals("const")||
				tokenSet.get(i).get(0).equals("DT") || tokenSet.get(i).get(0).equals("ID") || tokenSet.get(i).get(0).equals("String") ||
				tokenSet.get(i).get(0).equals("var") || tokenSet.get(i).get(0).equals("void") || tokenSet.get(i).get(0).equals("class") ||
				tokenSet.get(i).get(0).equals("interface") || tokenSet.get(i).get(0).equals("enum") || tokenSet.get(i).get(0).equals("static")||
				tokenSet.get(i).get(0).equals("abstract") || tokenSet.get(i).get(0).equals("sealed") || tokenSet.get(i).get(0).equals("while")||
				tokenSet.get(i).get(0).equals("for") || tokenSet.get(i).get(0).equals("if") || tokenSet.get(i).get(0).equals("do") ||
				tokenSet.get(i).get(0).equals("forEach") || tokenSet.get(i).get(0).equals("switch") || tokenSet.get(i).get(0).equals("this") ||
				tokenSet.get(i).get(0).equals("base") || tokenSet.get(i).get(0).equals("inc_dec") || tokenSet.get(i).get(0).equals("return") ||
				tokenSet.get(i).get(0).equals("pass") || tokenSet.get(i).get(0).equals("break") || tokenSet.get(i).get(0).equals("try") ||
				tokenSet.get(i).get(0).equals("throw") || tokenSet.get(i).get(0).equals("elif") || tokenSet.get(i).get(0).equals("else") ||
				tokenSet.get(i).get(0).equals("}")
				) {
			
				return true;
		}
		return false;
	}
	
	
	private boolean c1(int CR,int FR) {

		if(tokenSet.get(i).get(0).equals(";")) {
			i++;
			return true;
		}else if (dec(CR,FR)) {
			return true;
		}else if (assignment()) {
			if(tokenSet.get(i).get(0).equals(";")) {
				i++;
				return true;
			}
		}
		return false;
	}
	
	private boolean c2(int CR) {
		
		if(OE(CR)) {
			return true;
			
		
		// null
		}else if(tokenSet.get(i).get(0).equals(";")) {
			return true;
		}
		return false;
	}
	
	// c3
	
	private boolean c3(int CR) {
		if(X(CR)) {
			String type=this.type;
			this.type="null";
			if(c3_dash(type, CR)) {
				return true;
			}
		}else if (tokenSet.get(i).get(0).equals("inc_dec")) {
			String op=tokenSet.get(i).get(0);
			i++;
			
			if(X(CR)) {
				
				String T=database.compatibility(this.type);
				this.type="null";
				if (T==null) {
					
					System.out.println("Type incompatible Error,line #="+tokenSet.get(i).get(2));
				}
				
				return true;
			}
			
			// null
		}else if(tokenSet.get(i).get(0).equals(")")){
			return true;
		}
		
		return false;
	}
	
	// c3'
	
	private boolean c3_dash(String type, int CR) {
		if(ass_opr()) {
			String op=Op;
			Op="null";
			if(OE(CR)) {
				String t2=this.type;
				this.type="null";
				
				String T=database.compatibility(type, t2, op);
				if (T==null) {
					System.out.println("Type incompatible Error,line #="+tokenSet.get(i).get(2));
				}
				return true;
			}
		}else if(tokenSet.get(i).get(0).equals("inc_dec")){
			i++;
			if (database.compatibility(type)==null) {
				System.out.println("Type incompatible Error,line #="+tokenSet.get(i).get(2));
			}
			return true;
		}
		
		return false;
	}
	
	// elif
	
	private boolean elif(String type,int CR,int FR) {
		
		if(tokenSet.get(i).get(0).equals("elif")) {
			String KW=tokenSet.get(i).get(0);
			i++;
			
			if(tokenSet.get(i).get(0).equals("(")) {
				i++;
				
				if(OE(CR)) {
					
					if(!this.type.equals("bool")){
						System.out.println("Must be Boolean type,line #="+tokenSet.get(i).get(2));
					}
					this.type="null";
					if(tokenSet.get(i).get(0).equals(")")) {
						i++;
						
						if(body(KW,type,CR,FR)) {
							if(elif(type,CR,FR)) {
								return true;
							}
						}
					}
				}
			}
		//const, DT, while, for, if, do, forEach, switch, ID, this, base, inc_dec,
//			return, pass, break, try, String, var, throw, elif, else, }
		
		}else if (tokenSet.get(i).get(0).equals("else")) {
			return true;
		}
		
		return false;
	}
	
	
	// else_dash
	
	private boolean else_dash(String type,int CR,int FR) {
		
		if(tokenSet.get(i).get(0).equals("else")) {
			String KW=tokenSet.get(i).get(0);
			i++;
			
			if(body(KW,type,CR,FR)) {
				return true;
			}
		
		}
		
		return false;
	}


	private boolean arr_dec_dash() {
		if(tokenSet.get(i).get(0).equals("[")) {
			i++;
			if(tokenSet.get(i).get(0).equals("]")) {
				i++;
				if(val()) {
					if(tokenSet.get(i).get(0).equals(";")) {
						i++;
						return true;
					}
				}
			}
		}else if (val()) {
			//System.out.println("from val "+tokenSet.get(i).get(0));
			if(tokenSet.get(i).get(0).equals(";")) {
				i++;
				return true;
				
			}
		}
		
		return false;
	}
	
	// val implementation
	
	
	private boolean val() {
		
		if(tokenSet.get(i).get(0).equals("=")) {
			i++;
			if(val_dash()) {
				return true;
			}
		}else if(tokenSet.get(i).get(0).equals(";")) {
			return true;
		}
		
		
		return false;
	}
	
	// val' implementation
	
	private boolean	val_dash() {
	
		if(tokenSet.get(i).get(0).equals("{")) {
			i++;
			
			if(v1()) {
				return true;
			}
		}
		return false;
	}
	
	// v1,v2 ,v3, val_1D impelmentation
	
	private boolean v1() {
		
		if(val_1D()) {
			if(tokenSet.get(i).get(0).equals("}")) {
				i++;
				return true;
				
			}
			
		}else if(tokenSet.get(i).get(0).equals("{")) {
			i++;
			
			if(val_1D()) {
				if(tokenSet.get(i).get(0).equals("}")) {
					i++;
					
					if(v3()) {
						if(tokenSet.get(i).get(0).equals("}")) {
							i++;
							
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
		
	private boolean v2() {
		
		
		if(tokenSet.get(i).get(0).equals(",")) {
			i++;
			if(val_1D()) {
				return true;
			}
		}else if(tokenSet.get(i).get(0).equals("}")) {
			return true;
		}
		return false;
	}
	
	private boolean v3() {
		if(tokenSet.get(i).get(0).equals(",")) {
			i++;
			if(tokenSet.get(i).get(0).equals("{")) {
				i++;
				if(val_1D()) {
					
					if(tokenSet.get(i).get(0).equals("}")) {
						i++;
						if(v3()) {
							return true;
						}
					}
				}	
			}
			
		}else if(tokenSet.get(i).get(0).equals("}")) {
			return true;
		}
		
		return false;
	}
	
	private boolean val_1D() {
		
		if(OE(CR)) {
			if(v2()) {
				return true;
			}
		}else if(tokenSet.get(i).get(0).equals("new")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals("(")) {
					i++;
					
					if(params(CR)) {
						if(tokenSet.get(i).get(0).equals(")")) {
							i++;
							
							if(v2()) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	
	// RST implementation
	
	private boolean RST(int CR) {
		
		int flag = 0;
		int isStaticM = 0;
		int counter = 0;
		
		
		if(tokenSet.get(i).get(0).equals(";")) {
			this.type = "void";
			i++;
			
			
			return true;
			
		}else if(tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("base")){
			i++;
			
			if(RST_dash()) {
				return true;
			}
			
		}else if (tokenSet.get(i).get(0).equals("ID")) {
			
			String name = tokenSet.get(i).get(1);
			i++;
			
			
			if(F_dash(CR, name, isStaticM, flag, counter)) {
				
				if(exp(CR)) {
					
					if(tokenSet.get(i).get(0).equals(";")) {
						i++;
						return true;
					}
				}
			}
			
		}else if (constants()) {
			
			if(exp(CR)) {
				
				if(tokenSet.get(i).get(0).equals(";")) {
					i++;
					
					return true;
				}
			}
		}else if (tokenSet.get(i).get(0).equals("(")) {
			i++;
			
			if(OE(CR)) {
				
				if(tokenSet.get(i).get(0).equals(")")) {
					i++;
					if(exp(CR)) {
						if(tokenSet.get(i).get(0).equals(";")) {
							i++;
							return true;
						}
					}
				}
			}
		}else if (tokenSet.get(i).get(0).equals("!")) {
			i++;
			
			// Op = tokenSet.get(i).get(1);
			
			if(F(CR)) {
				
				
				if(!this.type.equals("bool")) {
					
					System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
					
				}
				
				if(exp(CR)) {
					
					if(tokenSet.get(i).get(0).equals(";")) {
						i++;
						return true;
					}
				}
			}
		}else if (tokenSet.get(i).get(0).equals("inc_dec")) {
			String Op = tokenSet.get(i).get(1);
			i++;
			
			
			if(X(CR)) {
				
				String T = database.compatibility(type);
				if(T == null) {
					
					System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
				}
				else {
					this.type = T;
				}
				
				if(exp(CR)) {
					
					if(tokenSet.get(i).get(0).equals(";")) {
						
						i++;
						
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	// RST' and RST''
	
	private boolean RST_dash() {
		
		if(tokenSet.get(i).get(0).equals(".")) {
			i++;
			if(tokenSet.get(i).get(0).equals("ID")) {
				i++;
				
				if(F_dash(CR, name, isStaticM, flag, counter)) {
					
					if(exp(CR)) {
						if(tokenSet.get(i).get(0).equals(";")) {
							i++;
							return true;
						}
					}
				}
			}
		
		}else if(tokenSet.get(i).get(0).equals("(")) {
			i++;
			
			if(params(CR)) {
				
				if(tokenSet.get(i).get(0).equals(")")) {
					i++;
					
					if(RST_double_dash()) {
						return true;
					}
				}
			}
		}
		return true;
	}
	
	private boolean RST_double_dash() {
		
		if(tokenSet.get(i).get(0).equals(".")) {
			i++;
			if(tokenSet.get(i).get(0).equals("ID")) {
				i++;
				
				if(F_dash(CR, name, isStaticM, flag, counter)) {
					
					if(exp(CR)) {
						if(tokenSet.get(i).get(0).equals(";")) {
							i++;
							return true;
						}
					}
				}
			}
		
		}else if(tokenSet.get(i).get(0).equals(";")) {
			i++;
			return true;
		}
		
		return false;
	}
	
	// dec implementation
	
	private boolean dec(int CR,int FR) {
		if(tokenSet.get(i).get(0).equals("const") || tokenSet.get(i).get(0).equals("DT")) {
			
			
			String type="";
			String name="";
			
			if(tokenSet.get(i).get(0).equals("DT")) {
				type=tokenSet.get(i).get(1);
				i++;
				if(tokenSet.get(i).get(0).equals("ID")) {
					name=tokenSet.get(i).get(1);
					i++;
					
//					Type_DT att=database.lookUp_att_DT(CR, name);
//					if (att!=null) {
//						
//						System.out.println("Redeclaration Error");
//					
//					}else
					if(!database.insert_FT(FR, name, type, "null")){
						System.out.println("Redeclaration Error");
					}
					
					
					if(init(type, CR)) {
						
						if(list(type,"null","Default",CR,FR)) {
							return true;
						}
					}
				}
				
			}
			
		}
		
		return false;
	}
	
	private boolean init(String type, int CR) {
	
		// Update to OE
		if(tokenSet.get(i).get(0).equals("=")) {
			String op=tokenSet.get(i).get(0);
			i++;
//			
//			if(tokenSet.get(i).get(0).equals("int_const") || tokenSet.get(i).get(0).equals("float_const") 
//					|| tokenSet.get(i).get(0).equals("short_const") || tokenSet.get(i).get(0).equals("long_const")
//					|| tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("string_const") || tokenSet.get(i).get(0).equals("bool_const")) {
//				
//				String T=database.compatibility(type, tokenSet.get(i).get(0), op);
//				
//				if (T==null) {
//					
//					System.out.println("Type Error in init,line no #"+tokenSet.get(i).get(2));
//				}
//				i++;
//			
//				return true;
//			}
			
			if(OE(CR)) {
				String T=database.compatibility(type, this.type, op);
				
				if (T==null) {
					
					System.out.println("Type Error in init,line no #"+tokenSet.get(i).get(2));
				}
				i++;
				return true;
			}
			
		}else {
			
			
			if(tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",")) {
				return true;
			
			}
			
			
		}
		

		return false;
	}
	
	private boolean list(String type,String TM,String AM,int id,int isStatic) {
		
//		System.out.println(tokenSet.get(i).get(0));
		String name="null";
		if(tokenSet.get(i).get(0).equals(";")) {
			i++;
			return true;
			
		}else if(tokenSet.get(i).get(0).equals(",")) {
			
			i++;
			if(tokenSet.get(i).get(0).equals("ID")) {
				name=tokenSet.get(i).get(1);
				i++;
			
				
				if (id<20000) {
					
					if(!database.insert_DT(id, 0, name, type, AM, TM, isStatic, "null")) {
						System.out.println("Redeclaration Error");
				     }
				}else if (!database.insert_FT(id, name, type, TM)) {
					System.out.println("Redeclaration Error");
					
				}
				
				if(init(type, id)) {
					return list(type,TM,AM,id,isStatic);
				}
			}
		}
		
		return false;
	}

	
	// Implementation of params
	
	private boolean params(int CR) {
		
		int flag = 0;
		int isStaticM = 0;
		int counter = 0;
		
		if(tokenSet.get(i).get(0).equals("this")) {
			i++;
			if(th_dash()) {
				if(p2(CR, isStaticM, flag, counter)) {
					
					return true;
				}
			}
		}else if (tokenSet.get(i).get(0).equals("base")) {
			i++;
			
			if(th_dash()) {
				
				if(p2(CR, isStaticM, flag, counter)) {
					
					return true;
				}
			}
			
		}else if(tokenSet.get(i).get(0).equals("ID")) {
			
			String name = tokenSet.get(i).get(1);
			i++;
			
			if(F_dash(CR, name, isStaticM, flag, counter)) {
				if(exp(CR)) {
					if(p2(CR, isStaticM, flag, counter)) {
						
						pl = type;
						return true;
					}
				}
			}
			
		}else if(p2(CR, isStaticM, flag, counter)) {
			
			pl = this.type;
			
			return true;
		
			
		}else if(constants()) {
			
			if(exp(CR)) {
				
				if(p2(CR, isStaticM, flag, counter)) {
					
					pl = this.type;
					return true;
				}
			}
		}else if(tokenSet.get(i).get(0).equals("(")) {
			
			i++;
			if(OE(CR)) {
				if(tokenSet.get(i).get(0).equals(")")) {
					i++;
					if(exp(CR)) {
						if(p2(CR, isStaticM, flag, counter)) {
							
							pl = this.type;
							return true;
						}

					}
				}
			}
		}else if (tokenSet.get(i).get(0).equals("!")) {
			i++;
			
			Op = tokenSet.get(i).get(1);
			
			if(F(CR)) {
				
				if(!this.type.equals("bool")) {
					
					System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
					this.type = "null"; 
				}
				
				if(exp(CR)) {
					
					if(p2(CR, isStaticM, flag, counter)) {
					
						pl = this.type;
						return true;
					}

				}
			}
			
		}else if(tokenSet.get(i).get(0).equals("inc_dec")) {
			i++;
			
			String Op = tokenSet.get(i).get(1);
			
			if(X(CR)) {
				
				String T = database.compatibility(type);
				if(T == null) {
					
					System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
					this.type = "null";
				}
				else {
					this.type = T;
				}
				
				if(exp(CR)) {

					if(p2(CR, isStaticM, flag, counter)) {
						
						pl = this.type;
						
						return true;
					}

				}
			}
		}else if(tokenSet.get(i).get(0).equals(")")) {
			
			pl = "null";
			
			return true;
		}
		
			
		return false;
	}
	
	
	private boolean th_dash() {
		
		if(tokenSet.get(i).get(0).equals("(")) {
			i++;
			
			if(params(CR)) {
				if(tokenSet.get(i).get(0).equals(")")) {
					i++;
					
					if(th_2dash()) {
						return true;
					}
				}
			}
		}else if (tokenSet.get(i).get(0).equals(".")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				i++;
				
				if(F_dash(CR, name, isStaticM, flag, counter)) {
					if(exp(CR)) {
						return true;
					}
				}
			}
		}else if(tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals(",")) {
			return true;
		}
		
		return false;
	}
	
	private boolean th_2dash() {
	
//		if(p2()) {
//			return true;
//		}else
		
		
		if(tokenSet.get(i).get(0).equals(".")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				i++;
				
				if(F_dash(CR, name, isStaticM, flag, counter)) {
					if(exp(CR)) {
						return true;
					}
				}
			}
		}else if(tokenSet.get(i).get(0).equals(")")) {
			return true;
		}
		
		return false;
	}

	
	private boolean exp(int CR) {
	
		if(T_dash(CR)) {
			
			if(E_dash(CR)) {
			
				if(RE_dash(CR)) {
			
					if(AE_dash(CR)) {
				
						if(OE_dash(CR)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	
	private boolean p2(int CR, int isStaticM, int flag, int counter) {
		
		if(tokenSet.get(i).get(0).equals(",")) {
			i++;
			
			if(p2_dash(CR, isStaticM, flag, counter)) {
				
				pl = pl + "," + this.type;
				
				if(p2(CR, isStaticM, flag, counter)) {
					
					return true;
				}
			}

		}else if(tokenSet.get(i).get(0).equals(")")){
			
			return true;
		}
		
		
		return false;
	}
	
	private boolean p2_dash(int CR, int isStaticM, int flag, int counter) {
		
		if(tokenSet.get(i).get(0).equals("this")) {
			i++;
			
			if(th_double_dash()) {
				return true;
			}
		}else if(tokenSet.get(i).get(0).equals("base")) {
			i++;
			if(th_double_dash()) {
				return true;
			}
		}else if(tokenSet.get(i).get(0).equals("ID")) {
		
			String name = tokenSet.get(i).get(1);
			i++;
		
			
			if(F_dash(CR, name, isStaticM, flag, counter)) {
				if(exp(CR)) {
					
					
					return true;
				}
			}
		}else if(constants()) {
			
			if(exp(CR)) {
				
				return true;
			}
		
		}else if(tokenSet.get(i).get(0).equals("(")) {
			i++;
			if(OE(CR)) {
				
				if(tokenSet.get(i).get(0).equals(")")) {
					i++;
					if(exp(CR)) {
						
						return true;
					}
				}
			}
		}else if(tokenSet.get(i).get(0).equals("!")) {
			i++;
			
			Op = tokenSet.get(i).get(1);
			
			if(F(CR)) {
				
				
				if(!type.equals("bool")) {
					
					System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
					type = "null";
				}
				
				if(exp(CR)) {
					
					return true;
				}
			}
			
		}else if(tokenSet.get(i).get(0).equals("inc_dec")) {
			i++;
			
			Op = tokenSet.get(i).get(1);
			
			if(X(CR)) {
				
				String T = database.compatibility(type);
				if(T == null) {
					
					System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
					this.type = "null";
				}
				else {
					this.type = T;
				}
				
				if(exp(CR)) {
					return true;
				}
			}
		}
		
		return false;
	
	}
	
	private boolean th_double_dash() {
		
		if(tokenSet.get(i).get(0).equals("(")) {
			
			i++;
			
			if(params(CR)) {
				if(tokenSet.get(i).get(0).equals(")")) {
					i++;
					
					if(th_2double_dash()) {
						return true;
					}
				}
			}
			
		}else if(tokenSet.get(i).get(0).equals(".")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				i++;
				
				if(F_dash(CR, name, isStaticM, flag, counter)) {
					if(exp(CR)) {
						return true;
					}
				}
			}
		}
		
		
		return false;
	}
	
	private boolean th_2double_dash() {
		
		if(tokenSet.get(i).get(0).equals(".")) {
			
			i++;
			if(tokenSet.get(i).get(0).equals("ID")) {
				i++;
				
				if(F_dash(CR, name, isStaticM, flag, counter)) {
					if(exp(CR)) {
						return true;
					}
				}
			}
			
		}else if(tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals(")")) {
			return true;
		}
		
		return false;
	}
	
	
	
	
	
	
	
	
	// Implementation of Y
	
	
	private boolean Y() {
		
		if(tokenSet.get(i).get(0).equals(".")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				i++;
				
				if(Y()) {
					
					return true;
				}
			}
		}else if(tokenSet.get(i).get(0).equals("(")) {
			
			i++;
			if(params(CR)) {
			
				if(tokenSet.get(i).get(0).equals(")")) {
					i++;
					
					if(Y1()) {
						return true;
					}
				}
			}
			
		}else if(arr_dash()) {
			
			if(tokenSet.get(i).get(0).equals(".")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals("ID")) {
					i++;
					
					if(Y()) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean Y1() {
		
		if(tokenSet.get(i).get(0).equals(".")) {
			i++;
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				i++;
				
				if(Y()) {
					return true;
				}
			}
		}else if(arr_dash()) {
			
			if(tokenSet.get(i).get(0).equals(".")) {
				i++;
				
				if(tokenSet.get(i).get(0).equals("ID")) {
					i++;
					if(Y()) {
						return true;
					}
				}
			}
		}else if(tokenSet.get(i).get(0).equals(";")) {
			return true;
		}
		
		return false;
	}
	
	private boolean arr_dash() {
	
		if(tokenSet.get(i).get(0).equals("[")) {
			i++;
			
			if(OE(CR)) {
				
				if(tokenSet.get(i).get(0).equals("]")) {
					i++;
					
					if(arr2()) {
						return true;
					}
					
				}
		}
		}
		return false;
	}
	
	
	// OE implementation
	
	private boolean OE(int CR) {
		
		if(tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("base") || tokenSet.get(i).get(0).equals("ID") ||
				tokenSet.get(i).get(0).equals("int_const") 
				|| tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("bool_const") || tokenSet.get(i).get(0).equals("string_const")
			|| tokenSet.get(i).get(0).equals("float_const") || tokenSet.get(i).get(0).equals("(") || tokenSet.get(i).get(0).equals("!")
			|| tokenSet.get(i).get(0).equals("inc_dec")
			
				) {

			if(AE(CR)) {
				
				if(OE_dash(CR)) {
					return true;
				}
			}
			
			
			
		}
		
		return false;
	}
	

	

	private boolean OE_dash(int CR) {

		if(tokenSet.get(i).get(0).equals("OR")) {
		
			String Op = tokenSet.get(i).get(0);
			String LO = this.type;
			
			i++;
			
			
			if(AE(CR)) {
				
				
				String RO = this.type;
				
				String T = database.compatibility(LO, RO, Op);
				if(T == null) {
					
					System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
					this.type = "null";
				}
				else {
					this.type = T;
				}
				
				if(OE_dash(CR)) {
					return true;
				}
			}

		}else if(tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals("]") || 
				tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("}")){
			
			
			
			return true;	
		}
		
		return false;
	}
	
	private boolean AE(int CR) {
		
		
		if(RE(CR)) {
				if(AE_dash(CR)) {
					
					return true;
				}
			}
			
			
		return false;
	}


	private boolean AE_dash(int CR) {
 
		
		if(tokenSet.get(i).get(0).equals("AND")) {
			
			String Op = tokenSet.get(i).get(0);
			String LO = this.type;
			i++;
			
			
			if(RE(CR)) {
				
				String RO = this.type;
				
				String T = database.compatibility(LO, RO, Op);
				if(T == null) {
					
					System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
					this.type = "null";
				}
				else {
					
					this.type = T;
				}
				if(AE_dash(CR)) {
					return true;
				}
			}
		}else if(tokenSet.get(i).get(0).equals("OR") ||tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals("]") || 
				tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("}")) {
		
			return true;
		}
		
		return false;
	}
	


	private boolean RE(int CR) {
	
		
		if(E(CR)) {
				if(RE_dash(CR)) {
					return true;
				}
			}
			
		
		
		return false;
	}
	
	private boolean RE_dash(int CR) {
		
		
		if(tokenSet.get(i).get(0).equals("ROP")) {

			String Op = tokenSet.get(i).get(0);
			String LO = this.type;
			i++;
			
			
			if(E(CR)) {
				
				String RO = this.type;
				
				String T = database.compatibility(LO, RO, Op);
				if(T == null) {
					
					System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
					this.type = "null";
				}
				
				if(RE_dash(CR)) {
					return true;
				}
			}
			
		}else if(tokenSet.get(i).get(0).equals("AND") || tokenSet.get(i).get(0).equals("OR") ||tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals("]") || 
				tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("}")){
			
			return true;
		}
		
		return false;
	}
	

	private boolean E(int CR) {

		if(T(CR)) {
			if(E_dash(CR)) {
				return true;
			}
		}
		
		return false;
	}
	

	private boolean E_dash(int CR) {

	
		if(tokenSet.get(i).get(0).equals("PM")) {

			String Op = tokenSet.get(i).get(0);
			String LO = this.type;
			i++;
			
			if(T(CR)) {
				
				String RO = this.type;
				
				String T = database.compatibility(LO, RO, Op);
				if(T == null) {
					
					System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
					this.type = "null";
				}
				else {
					this.type = T;
				}
				
				if(E_dash(CR)) {
					
					return true;
				}
			}
		}else if(tokenSet.get(i).get(0).equals("ROP") || tokenSet.get(i).get(0).equals("AND") || tokenSet.get(i).get(0).equals("OR") ||tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals("]") || 
				
				tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("}")) {
		
			return true;
		}
		
		return false;
	}
	

	private boolean T(int CR) {

		if(F(CR)) {
			if(T_dash(CR)) {
				return true;
			}
		}
		
		
		return false;
	}
	

	private boolean T_dash(int CR) {
		
		if(tokenSet.get(i).get(0).equals("MDM")) {

			String Op = tokenSet.get(i).get(0);
			String LO = this.type;
			i++;
			
			
			if(F(CR)) {
				
				String RO = this.type;
				
				String T = database.compatibility(LO, RO, Op);
				if(T == null) {
					
					System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
					this.type = "null";
				}
				else {
					
					this.type = T;
				}
				
				if(T_dash(CR)) {
					return true;
				}
			}
		}else if(tokenSet.get(i).get(0).equals("PM") || tokenSet.get(i).get(0).equals("ROP") || tokenSet.get(i).get(0).equals("AND") || tokenSet.get(i).get(0).equals("OR") ||tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals("]") || 
				
				tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("}")) {
	
			return true;
		}
		
		return false;
	}
	

	private boolean F(int CR) {

		int isStaticM = 0;
		int flag = 0;
		int counter = 0;
		
		
		if(tb(CR)) {
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				
				String name = tokenSet.get(i).get(1);
				i++;
				
				if(F_dash(CR, name, isStaticM, flag, counter)) {
					return true;
				}
			}
		}else if(constants()) {
			
			return true;
			
		}else if(tokenSet.get(i).get(0).equals("(")) {
			i++;
			if(OE(CR)) {
				if(tokenSet.get(i).get(0).equals(")")) {
					i++;
					return true;
					
				}
			}
		}else if(tokenSet.get(i).get(0).equals("!")) {
			i++;
			
			String Op = tokenSet.get(i).get(1);
			
			if(F(CR)) {
				
				if(!this.type.equals("bool")) {
					
					System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
					this.type = "null";
				}
				
				
				return true;
			}
			
			
		}else if(tokenSet.get(i).get(0).equals("inc_dec")){
			
			String Op = tokenSet.get(i).get(1);
			i++;
			
			if(X(CR)) {
				
				String T = database.compatibility(type);
				if(T == null) {
					
					System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
					this.type = "null";
				}
				else {
					
					this.type = T;
				}
				return true;
			}
			
			
		}
		
		return false;
	}
	

	@SuppressWarnings("unused")
	private boolean F_dash(int CR, String name, int isStaticM, int flag, int counter) {
		
		int FR = database.get_FR();
		
		if(tokenSet.get(i).get(0).equals(".")) {
			i++;
			
			
			Type_MT T = database.lookUp_MT(name);
			
			
			if(flag != 1) {
				
				
				if(T == null) {
					
					Type_FT T2 = database.lookUp_FT(FR, name);
					
					if(T2 == null) {
						
						System.out.println("Undeclared error, line # = " + tokenSet.get(i).get(2));
					}
					else {
						
						T = database.lookUp_MT(T2.type);
						
						if(T == null) {
							
							System.out.println("Class undeclared, line # = " + tokenSet.get(i).get(2));
						}
					}
					
				}
				
				else {
					
					isStaticM = 1;
				}
				
				flag = 1;
				
			}
			
			else {
				
				Type_DT T3 = database.lookUp_att_DT(CR, name);
				
				if(T3 == null) {
					
					System.out.println("Undeclared error , line # = " + tokenSet.get(i).get(2));
				}
				else {
					
					T = database.lookUp_MT(T3.type);
					if(T == null) {
						
						System.out.println("Class undeclared, line # = " + tokenSet.get(i).get(2));
					}
					else {
						
						if(T3.accessMod.equals("private")) {
							
							System.out.println("Access issue , line # = " + tokenSet.get(i).get(2));
						}
						if(counter == 0 && T3.isStatic != isStaticM) {
							
							System.out.println("Static member should be access in static way , line # = " + tokenSet.get(i).get(2));
						}
						
					}
					
				}
				
				counter++;
				CR = T.id;
			}
			
			
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				
				name = tokenSet.get(i).get(1);
				i++;
				
				
				if(F_dash(CR, name, isStaticM, flag, counter)) {
				
					return true;
				}
			}
			
		}else if(tokenSet.get(i).get(0).equals("[")){
			i++;
			if(OE(CR)) {
				
				if(tokenSet.get(i).get(0).equals("]")) {
					i++;
					
					if(arr2()) {
						
						if(F2()) {
							return true;
						}
					}
				}
			}
		}else if(tokenSet.get(i).get(0).equals("(")) {
			
			i++;
			
			if(params(CR)) {
				
				if(tokenSet.get(i).get(0).equals(")")) {
					i++;
					
					if(F3(CR, name, isStaticM, flag, counter)) {
						return true;
					}
				}
			}
			
		}else if(F_double_dash()) {
			return true;
		
		
		}
		
		return false;
	}
	

	private boolean F2() {
		
		if(tokenSet.get(i).get(0).equals(".")) {
			
			i++;
			if(tokenSet.get(i).get(0).equals("ID")) {
				i++;
				
				if(F_dash(CR, name, isStaticM, flag, counter)) {
					return true;
				}
			}
			
		}else if(F_double_dash()) {
			
			return true;
		}
		
		return false;
	}
	

	private boolean F3(int CR, String name, int isStaticM, int flag, int counter) {
		
		if(tokenSet.get(i).get(0).equals(".")) {
			i++;
			
			Type_DT T = database.lookUp_fn_DT(CR, name, pl);
			if(T == null) {
				
				System.out.println("Undeclared error, line # = " + tokenSet.get(i).get(2));
			}
			else {
				
				
				if(counter == 1 && T.isStatic != isStaticM) {
					
					System.out.println("Static members should be access in static way, line # = " + tokenSet.get(i).get(2));
				}
				if(T.accessMod.equals("private")) {
					
					System.out.println("Access Issue, line # = " + tokenSet.get(i).get(2));
				}
				if(T.isStatic != isStaticM) {
					
					System.out.println("Static members should be access in static manner, line # = " + tokenSet.get(i).get(2));
				}
				
				String Type = T.type;
				int j = Type.indexOf('>');
				String retType = Type.substring(j, Type.length()-1);
				
				Type_MT T2 = database.lookUp_MT(retType);
				
				if(T2 == null || !T2.type.equals("class")) {
					
					Type_DT T3 = database.lookUp_C_DT(CR, retType);
					
					if(T3 == null || !T3.type.equals("class")) {
						
						System.out.println("Return type mismatch error, line # = " + tokenSet.get(i).get(2));
					}
					else {
						
						CR = T3.mainid;
					}
					
				}
				
				else {
					
					CR = T2.id;
							
					
				}
			}
			
			
			
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				
				name = tokenSet.get(i).get(1);
				i++;
				
				
				if(F_dash(CR, name, isStaticM, flag, counter)) {
					return true;
				}
			}
			
		}else if(tokenSet.get(i).get(0).equals("[")) {
			
			i++;
			if(OE(CR)) {
				
				if(tokenSet.get(i).get(0).equals("]")) {
					i++;
					
					if(arr2()) {
						
						if(F2()) {
							
							return true;
						}
					}
				}
			}
		}else if(tokenSet.get(i).get(0).equals("MDM") || tokenSet.get(i).get(0).equals("PM") || tokenSet.get(i).get(0).equals("ROP") ||
				tokenSet.get(i).get(0).equals("AND") || tokenSet.get(i).get(0).equals("OR") ||tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals("]") || 
				tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("}")
			 ){
			
			Type_DT T = database.lookUp_fn_DT(CR, name, pl);
			
			if(T == null) {
				
				System.out.println("Undeclared error, line # = " + tokenSet.get(i).get(2));
				this.type = "null";
			}
			else {
				
				if(T.accessMod.equals("private")) {
					
					System.out.println("Access Issue, line # = " + tokenSet.get(i).get(2));
				}
				if(T.isStatic != isStaticM) {
					
					System.out.println("Static members should be access in static manner, line # = " + tokenSet.get(i).get(2));
				}
				
				String Type = T.type;
				int j = Type.indexOf('>');
				this.type = Type.substring(j+1);
			}
			
			
			return true;
			
		}
		
		
		return false;
	}
	

	private boolean F_double_dash() {
		
		

		if(tokenSet.get(i).get(0).equals("inc_dec")) {
			
			String Op = tokenSet.get(i).get(1);
			i++;
			
			String T = database.compatibility(type);
			if(T == null) {
				
				System.out.println("Type mismatch error, line # = " + tokenSet.get(i).get(2));
				this.type = "null";
			}
			else {
				
				this.type = T;
			}
			
			
			return true;
		
			
//			{ MDM, PM, ROP, AND, OR, ), ], ;, , , } }
		
		}else if(tokenSet.get(i).get(0).equals("MDM") || tokenSet.get(i).get(0).equals("PM") || tokenSet.get(i).get(0).equals("ROP") ||
				tokenSet.get(i).get(0).equals("AND") || tokenSet.get(i).get(0).equals("OR") ||tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals("]") || 
				tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("}")
			 ){

			return true;
			
		}
		
		return false;
	}
	
	
	
	private boolean constants() {
		
		if(tokenSet.get(i).get(0).equals("int_const") || tokenSet.get(i).get(0).equals("float_const") 
				|| tokenSet.get(i).get(0).equals("char_const") || tokenSet.get(i).get(0).equals("string_const")
				|| tokenSet.get(i).get(0).equals("string_const")) {
			switch(tokenSet.get(i).get(0)) {
				
			case "int_const":
				this.type = "int";
				break;
				
			case "float_const":
				this.type ="float";
				break;
			
			case "char_constant":
				this.type ="char";
				break;
				
			case "string_const":
				this.type ="String";
				break;
			case "bool_const":
				this.type ="bool";
				break;
			
			}
			i++;
			return true;
		}
		return false;
	}
	
	
	// X non Terminal implementation
	
	private boolean X(int CR) {
		
		int flag = 0;
		int isStaticM = 0;
		int counter = 0;
		
		if(tb(CR)) {
			
			if(tokenSet.get(i).get(0).equals("ID") ) {
				
				System.out.println("ID found");
				String name = tokenSet.get(i).get(1);
				i++;
				
				if(x_dash(CR, name, isStaticM, flag, counter)) {
					System.out.println("true");
					return true;
				}
				
			}
		}
		
		return false;
	
	}
	
	
	
	private boolean tb(int CR) {
	
		if(tokenSet.get(i).get(0).equals("this") || tokenSet.get(i).get(0).equals("base")) {
			
			i++;
			
			if(tb2(CR)) {
				return true;
			}
			
		}else {
			
			if(tokenSet.get(i).get(0).equals("ID")){
				return true;
			}
		}
		
		return false;
	}
	
	
	private boolean tb2(int CR) {
		
		if(tokenSet.get(i).get(0).equals(".")) {
			i++;
			
			return true;
			
		}else if(tokenSet.get(i).get(0).equals("(")) {
			i++;
			if(params(CR)) {
			
				if(tokenSet.get(i).get(0).equals(")")) {
					i++;
					if(tokenSet.get(i).get(0).equals(".")) {
						i++;
						return true;
					}
				}
			}
			
			
		}
		
		return false;
	}
	private boolean x_dash(int CR, String name, int isStaticM, int flag, int counter) {

		
		if(x_double_dash(CR, name, isStaticM, flag, counter)) {
			return true;
		}
		else if(x1()) {
			
			if(x_double_dash(CR, name, isStaticM, flag, counter)) {
				return true;
			}
		}else if(x2(CR, name, isStaticM, flag, counter)){
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unused")
	private boolean x_double_dash(int CR, String name, int isStaticM, int flag, int counter) {
		
		int FR = database.get_FR();
		
		if(tokenSet.get(i).get(0).equals(".")) {
			i++;
			
			Type_MT T= database.lookUp_MT(name);
			
			
			if(flag != 1) {
				
				
				if(T == null) {
					
					Type_FT T2 = database.lookUp_FT(FR, name);
					
					if(T2 == null) {
						
						System.out.println("Undeclared error, line # = " + tokenSet.get(i).get(2));
					}
					else {
						
						T = database.lookUp_MT(T2.type);
						
						if(T2 == null) {
							
							System.out.println("Class undeclared, line # = " + tokenSet.get(i).get(2));
						}
					}
					
				}
				
				else {
					
					isStaticM = 1;
				}
				
				flag = 1;
				
			}
			
			else {
				
				Type_DT T3 = database.lookUp_att_DT(CR, name);
				
				if(T3 == null) {
					
					System.out.println("Undeclared error , line # = " + tokenSet.get(i).get(2));
				}
				else {
					
					T = database.lookUp_MT(T3.type);
					if(T == null) {
						
						System.out.println("Class undeclared, line # = " + tokenSet.get(i).get(2));
					}
					else {
						
						if(T3.accessMod.equals("private")) {
							
							System.out.println("Access issue , line # = " + tokenSet.get(i).get(2));
						}
						if(counter == 0 && T3.isStatic != isStaticM) {
							
							System.out.println("Static member should be access in static way , line # = " + tokenSet.get(i).get(2));
						}
						
					}
					
				}
				
				counter++;
			}
			
			
			CR = T.id;
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				
				name = tokenSet.get(i).get(1);
				i++;
				
				
				if(x_dash(CR, name, isStaticM, flag, counter)) {
				
					return true;
				
				}
			}
		}else {
			if(tokenSet.get(i).get(0).equals("MDM") || tokenSet.get(i).get(0).equals("PM") || 
					tokenSet.get(i).get(0).equals("ROP") || tokenSet.get(i).get(0).equals("AND") ||
					tokenSet.get(i).get(0).equals("OR") || tokenSet.get(i).get(0).equals(")") ||
					tokenSet.get(i).get(0).equals("]") || tokenSet.get(i).get(0).equals(";") || tokenSet.get(i).get(0).equals(",") ||
					tokenSet.get(i).get(0).equals("}") || tokenSet.get(i).get(0).equals("SA") ||
					tokenSet.get(i).get(0).equals("CAO") || tokenSet.get(i).get(0).equals("inc_dec")) {
				
				
				if(flag != 1) {
					
					Type_FT T = database.lookUp_FT(FR, name);
					
					if(T == null) {
						
						System.out.println("Undeclared error , line # = " + tokenSet.get(i).get(2));
						this.type = "null";
					}
					else {
						
						this.type = T.type;
					}
				}
				
				else {
					
					Type_DT T = database.lookUp_att_DT(CR, name);
					
					if(T == null) {
						
						System.out.println("Undeclared Error, line # = " + tokenSet.get(i).get(2));
						this.type = "null";
					}
					else {
						
						this.type = T.type;
					}
				}
				
				
				return true;
			}
		}
		
		return false;
	}
	
	private boolean x1() {
		
		if(tokenSet.get(i).get(0).equals("'[")) {
			i++;
		
			if(OE(CR)) {
				if(tokenSet.get(i).get(0).equals("]")) {
					i++;
					
					if(arr2()) {
						return true;
					}
					
				}
			}
		}
		
		
		return false;
	}
	
	private boolean x2(int CR, String name, int isStaticM, int flag, int counter) {
		
		if(tokenSet.get(i).get(0).equals("(")) {
			i++;
			
			if(params(CR)) {
				
				if(tokenSet.get(i).get(0).equals(")")) {
					i++;
					if(x_op(CR, name, isStaticM, flag, counter)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean x_op(int CR, String name, int isStaticM, int flag, int counter) {
		
		if(tokenSet.get(i).get(0).equals(".")) {
			i++;
			
			Type_DT T = database.lookUp_fn_DT(CR, name, pl);
			if(T == null) {
				
				System.out.println("Undeclared error, line # = " + tokenSet.get(i).get(2));
			}
			else {
				
				if(counter == 1 && T.isStatic != isStaticM) {
					
					System.out.println("static member should be access in static way, line # = " + tokenSet.get(i).get(2));
				}
				
				else{
					
					String Type = T.type;
					int j = Type.indexOf('>');
					String retType = Type.substring(j+1);
					
					Type_MT T2 = database.lookUp_MT(retType);
					
					if(T2 == null) {
						
						Type_DT T3 = database.lookUp_C_DT(CR, retType);
						if(T3 == null) {
							
							System.out.println("Return type mismatch error, line # = " + tokenSet.get(i).get(2));
						}
						
						else {
							
							CR = T3.mainid;
						}
					}
					
					else {
						
						CR = T2.id;
					}
				}
				
			}
			
			if(tokenSet.get(i).get(0).equals("ID")) {
				
				name = tokenSet.get(i).get(1);
				i++;
				
				
				if(x_dash(CR, name, isStaticM, flag, counter)) {
				
					return true;
				
				}
			}
			
		}else {
			
			if(x_dash(CR, name, isStaticM, flag, counter)) {
				if(x_double_dash(CR, name, isStaticM, flag, counter)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	
	private boolean arr2() {
		
		if(tokenSet.get(i).get(0).equals("[")) {
			i++;
			if(OE(CR)) {
				if(tokenSet.get(i).get(0).equals("]")) {
					i++;
					return true;
				
				}
			}
		}else {
			
//			. , MDM, PM, ROP, AND, OR, ), ], ;, , , }, =, CAO, inc_dec
			
			if(tokenSet.get(i).get(0).equals(".") || tokenSet.get(i).get(0).equals("MDM") || tokenSet.get(i).get(0).equals("PM")||
					tokenSet.get(i).get(0).equals("ROP") || tokenSet.get(i).get(0).equals("AND") || tokenSet.get(i).get(0).equals("OR")||
					tokenSet.get(i).get(0).equals(")") || tokenSet.get(i).get(0).equals("]") || tokenSet.get(i).get(0).equals(";") ||
					tokenSet.get(i).get(0).equals(",") || tokenSet.get(i).get(0).equals("}") || tokenSet.get(i).get(0).equals("=") ||
					tokenSet.get(i).get(0).equals("CAO") || tokenSet.get(i).get(0).equals("inc_dec")) {
				
				return true;
			}
			
		}
		return false;
	}

}

