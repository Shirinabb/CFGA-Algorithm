package Srbiau.ac.CFGA;
import java.io.FileInputStream;  
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.*;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
import java.io.File;   
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
  


public class Clustering {
	public static double ReadNumericCellData(int vRow, int vColumn, String filePathAdrr) throws IOException  
	{  
	double value= 0;          //variable for storing the cell value  
	Workbook wb=null;           //initialize Workbook null  
	try  
	{  
	//reading data from a file in the form of bytes  
	//FileInputStream fis=new FileInputStream("D:\\Filename"); 
	FileInputStream fis=new FileInputStream(filePathAdrr); 
	//constructs an XSSFWorkbook object, by buffering the whole stream into the memory  
	wb=new XSSFWorkbook(fis);  
	}  
	catch(FileNotFoundException e)  
	{  
	e.printStackTrace();  
	}  
	catch(IOException e1)  
	{  
	e1.printStackTrace();  
	}  
	Sheet sheet=wb.getSheet("Sheet1");;   //getting the XSSFSheet object at given index  
	Row row=sheet.getRow(vRow); //returns the logical row  
	Cell cell=row.getCell(vColumn); //getting the cell representing the given column  
	
    	value = cell.getNumericCellValue();
    	wb.close();
	             //returns the cell value  
    	return value;               //returns the cell value  

	}  
	
	public static String ReadStringCellData(int vRow, int vColumn, String filePathAdrr) throws IOException  
	{  
	String value= null;          //variable for storing the cell value  
	Workbook wb=null;           //initialize Workbook null  
	try  
	{  
	//reading data from a file in the form of bytes  
	//FileInputStream fis=new FileInputStream("D:\\Filename"); 
	FileInputStream fis=new FileInputStream(filePathAdrr); 
	//constructs an XSSFWorkbook object, by buffering the whole stream into the memory  
	wb=new XSSFWorkbook(fis);  
	}  
	catch(FileNotFoundException e)  
	{  
	e.printStackTrace();  
	}  
	catch(IOException e1)  
	{  
	e1.printStackTrace();  
	}  
	Sheet sheet=wb.getSheet("Sheet1");;   //getting the XSSFSheet object at given index  
	Row row=sheet.getRow(vRow); //returns the logical row  
	Cell cell=row.getCell(vColumn); //getting the cell representing the given column  
	
    	value = cell.getStringCellValue();
	             //returns the cell value  
    	wb.close();
    	return value;               //returns the cell value  
    	
	}  
	
}

	