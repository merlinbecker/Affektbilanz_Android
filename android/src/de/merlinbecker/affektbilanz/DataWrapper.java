package de.merlinbecker.affektbilanz;

import java.util.ArrayList;
import java.util.regex.*;


public class DataWrapper {
	
	private static DataWrapper instance = null;
	public ArrayList<Sheets> list;
	
	private DataWrapper() {
	
	}
	
	public ArrayList<Sheets> getList() {
		
		if (this.list == null) this.list = new ArrayList<Sheets>();
		return this.list;
	}
	
	public static DataWrapper getInstance() {
		if (instance == null) {
			instance = new DataWrapper();
		}
		return instance;
	}
	
}
