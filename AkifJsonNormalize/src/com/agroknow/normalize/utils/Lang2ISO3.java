package com.agroknow.normalize.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jdom.Element;

public class Lang2ISO3 
{
	public String match(String value) throws IOException
	{
		String absolute_path=System.getProperty("user.dir")+System.getProperty("file.separator")+""
				+ "assets"+System.getProperty("file.separator");
		
		/*
		 * 	TODO: 
		 * 		rethink about case sensitive/insensitive
		 * 
		 * */
		
			//System.out.println("GOT IN WITH:"+value);
		
			FileInputStream fstream = new FileInputStream(absolute_path+"iso-languagecodes.db");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;
			while ((strLine = br.readLine()) != null)   
			{

				String[] langs=strLine.split("\t");
			  
				boolean found=false;

				for(int i=0;i<langs.length;i++)
				{
					if(value.equalsIgnoreCase(langs[i]))
					{
						br.close();
						return langs[0];
					}
						
				}

				
			}
			br.close();
    				
		return value;
		
	}
	
	
}
