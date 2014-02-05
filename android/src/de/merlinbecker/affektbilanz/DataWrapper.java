package de.merlinbecker.affektbilanz;

import java.util.ArrayList;
import java.util.regex.*;


public class DataWrapper {
	
	private static DataWrapper instance = null;
	public ArrayList<Sheets> list;
	
	private DataWrapper() {
	
	}
	
	public void sendData(ArrayList<Sheets> data) {
		this.list = data;
	}
	
	public ArrayList<Sheets> getData() {
		return this.list;
	}
	
	public static DataWrapper getInstance() {
		if (instance == null) {
			instance = new DataWrapper();
		}
		return instance;
	}
	
	/**Hilfsfunktionen**/
	 /*public String implode(String glue, String[] strArray)
	    {
	        String ret = "";
	        for(int i=0;i<strArray.length;i++)
	        {
	            ret += (i == strArray.length - 1) ? strArray[i] : strArray[i] + glue;
	        }
	        return ret;
	    }*/
	    
	    public String[] explode(String separator, String data){
	    	return data.split(separator);
	    	//return new String[2];
	    }
	    
	  public String implode(String str, String strArray[]) {
		  
		  
		  return str;
	  }
	    
	/*************/
}
