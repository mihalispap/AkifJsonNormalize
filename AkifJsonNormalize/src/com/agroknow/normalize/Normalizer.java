package com.agroknow.normalize;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.agroknow.normalize.utils.Lang2ISO3;

public class Normalizer {

	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		

        if (args.length != 2) {
            System.err.println("Usage: param1(inputdir) param2(outputdir)");                
            System.exit(1);
        } 
		String output=args[1];
        File file = new File(output);
		file.mkdirs();
        
        String folder_path=args[0];
        
        File folder = new File(folder_path);
        //File[] listOfFiles = folder.listFiles();

        List<File> listOfFiles=new ArrayList<File>();
        //File[] listOfFiles = null;
        
        for (int i = 0; i < folder.list().length; i++) 
        {
        	File temp_folder=new File(folder.getAbsolutePath()+File.separator+folder.list()[i]);
        	System.out.println(folder.getAbsolutePath()+File.separator+folder.list()[i]);
        	if(temp_folder.isDirectory())
        	{        		
        		File[] temp_a=null;
        		temp_a= temp_folder.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".json");
                    }
                });
        		
        		
        		for(int j=0;j<temp_a.length;j++)
        			listOfFiles.add(temp_a[j]);
        	}
        }
        /*
        listOfFiles = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".json");
            }
        });
        */
        //folder.list
        
        Lang2ISO3 toISO=new Lang2ISO3();
        
        for (int i = 0; i < listOfFiles.size(); i++) 
        {
        	if (listOfFiles.get(i).isFile()) 
        	{
        		System.out.println("File " + listOfFiles.get(i).getName());
        		
        		try
                {
        			String s=new String(Files.readAllBytes(Paths.get(listOfFiles.get(i).getAbsolutePath())));
        			JSONObject obj = new JSONObject(s);
        			//String pageName = obj.getJSONObject("pageInfo").getString("pageName");
        	
        			String set = obj.getString("set");
        			System.out.println("SET:"+set);
        			
        			//oeorganiceprints,aglrfaocdx,prodinraagro,oeintute,sercmicro,oeagroasis,greenideas
        			
        			if(!set.equalsIgnoreCase("greenideas")
        					&& !set.equalsIgnoreCase("oeintute")
        					&& !set.equalsIgnoreCase("oeorganiceprints")
        					&& !set.equalsIgnoreCase("aglrfaocdx")
        					&& !set.equalsIgnoreCase("prodinraagro")
        					&& !set.equalsIgnoreCase("sercmicro")
        					&& !set.equalsIgnoreCase("oeagroasis"))
        				continue;
        			
        			JSONArray arr = obj.getJSONArray("expressions");
        			for (int j = 0; j < arr.length(); j++)
        			{
        				String lang = arr.getJSONObject(j).getString("language");

        				

        				arr.getJSONObject(j).put("language",toISO.match(lang));
        				
        			}
        			
        			file = new File(output+File.separator+set);
        			file.mkdirs();
        			
        			PrintWriter writer = new PrintWriter(output+File.separator+set+File.separator+listOfFiles.get(i).getName(), "UTF-8");
        			writer.print(obj.toString());
        			writer.close();
        			System.out.println(obj.toString());
                }
                catch(java.lang.Exception e)
                {
                	e.printStackTrace();
                }
        		
        		
        	} 
        	else if (listOfFiles.get(i).isDirectory()) 
        	{
        		System.out.println("Directory " + listOfFiles.get(i).getName());
            }
        }
	}

}
