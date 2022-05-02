package Srbiau.ac.CFGA;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class prioritizeNodes{

	static int ProcessNumber;
	static int ProcessNode;
	static double[] ProcessList;
	static int rowTotal;
	
	
	
	//public static double[] TimeDate(String[][] IntervaltimeText,String[][] AcceptancetimeText,String ServiceType, char Status, int NodeNum) throws IOException
	public static double[] TimeDate() throws IOException
	{      
		String ProcessPath ="D://FileData";
		XSSFWorkbook ProcessFile = new XSSFWorkbook(ProcessPath);
		XSSFSheet sheet = ProcessFile.getSheet("Sheet1");
		rowTotal= sheet.getLastRowNum();
		String[][] IntervaltimeText= new String [rowTotal][2];
		String[][] AcceptancetimeText=new String [rowTotal][2];
		ProcessList = new double[rowTotal];
		char [][] Status = new char [rowTotal][2];
		long startTime = System.currentTimeMillis();
		for(int j=0;j<rowTotal-1;j++) {
			IntervaltimeText[j][1] = Clustering.ReadStringCellData(j,10,ProcessPath);
			AcceptancetimeText[j][1]= Clustering.ReadStringCellData(j,2,ProcessPath);
			Status[j][1]='A';
		}
		
		Date Intervaldate = new Date();
		Date Acceptancedate = new Date();
		Date SystemDate = new Date();
		LocalTime SystemDateTest = LocalTime.now(); // Gets the current time
		SimpleDateFormat IntervalTime = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat AcceptanceTime = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat SystemDateTime= new SimpleDateFormat("HH:mm:ss");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		 String SystemDateText =SystemDateTest.format(formatter);
		
		
		for(int j=0;j<rowTotal-1;j++) {
			try {
				Intervaldate = IntervalTime.parse(IntervaltimeText[j][1]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    try {
				Acceptancedate = AcceptanceTime.parse(AcceptancetimeText[j][1]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    try {
				SystemDate = SystemDateTime.parse(SystemDateText);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		long TimeDifferentDate =  SystemDate.getTime()-Intervaldate.getTime(); 
		 
		
		String NodePath = "D:\\fileData";
		int NodeNum = sheet.getLastRowNum()-1;
		if (TimeDifferentDate < Acceptancedate.getTime() && Status[j][1]=='A' && NodeNum > ProcessNode )
		 NodeNum =  NodeNum - ProcessNode; 
		else
	        Status[j][1] = 'I';
		if(Status[j][1]== 'A') 
			ProcessList[j]= Clustering.ReadNumericCellData(j,0,ProcessPath);
		    //System.out.println("ProcessList = " + ProcessList[j]);
		}
		 long stopTime = System.currentTimeMillis();
		 System.out.println("this is " + (stopTime - startTime));
		ProcessFile.close();
		return ProcessList;
	}
    
	
}
	
    
    
    

		   
 
         
    
    


