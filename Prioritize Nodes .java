package Srbiau.ac.CFGA;


import ilog.concert.*;
import ilog.cplex.*;
import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.FormulaEvaluator;  
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 


public class CheckPoints {
	static double[] Distance;
	static double speed;
	static double AttendanceTime;
	static int TrafficData;
	static double RoadDistance;
	static double HighwayDistance;
	static int BaseStationNumber;
	static int ClusterNumber;
	static double ClusterDistance;
	static double TempValue;
	static String TempStringValue;
	static String CMPStringValue;
	static String Path;
	static String NodePath;
	static int rowTotal;
	static double[] ClusterDistancelong;
	static double clusterInitial=0;
	static int[] NodeCluster;
	public static int[] NodeMobility() throws IOException {
		
		String TrafficPath ="D:\\fileaddress";
		XSSFWorkbook TrafficFile = new XSSFWorkbook(TrafficPath);
		XSSFSheet sheetTraffic = TrafficFile.getSheet("Sheet1");
		String Path ="D:\\fileaddress";//configure based on scn
		String NodePath ="D:\\fileaddress";
		XSSFWorkbook NodeFile = new XSSFWorkbook(NodePath);
		XSSFSheet sheet = NodeFile.getSheet("Sheet1");
		rowTotal= sheet.getLastRowNum();
		System.out.println("rowTotal = " + rowTotal);
		RoadDistance = Clustering.ReadNumericCellData(2,1,Path); //configure based on scn
		HighwayDistance = Clustering.ReadNumericCellData(1,1,Path);//configure based on scn
		ClusterNumber =0;
		CMPStringValue =new String("BaseStation");
		TrafficData = sheetTraffic.getLastRowNum();
		for(int j1=0;j1<rowTotal-1;j1++) {
			TempStringValue = Clustering.ReadStringCellData(j1,1,NodePath);//configure based on scn
			if (TempStringValue.equals(CMPStringValue)) {
			ClusterNumber = ClusterNumber +1;
			}
		}
			ClusterDistance = HighwayDistance/ClusterNumber;
			double[] Distance = new double[TrafficData];
		for( int j=1;j<TrafficData-1;j++) {
			AttendanceTime = Clustering.ReadNumericCellData(j,6,TrafficPath);
			speed=Clustering.ReadNumericCellData(j,3,TrafficPath);
            Distance[j]= AttendanceTime * speed;
				
	}
		double[] ClusterDistancelong = new double[ClusterNumber];
		for ( int k=1;k< ClusterNumber; k++) {
			ClusterDistancelong[k]=clusterInitial+ClusterDistance;
			clusterInitial = clusterInitial+ClusterDistancelong[k];
		}
	// Identify Node Cluster
	//Identify Priority
		int[] NodeCluster = new int[15];
		for(int j=0;j<16-1;j++) {
			for(int K=0;K<ClusterNumber; K++)
			{
			if (Distance[j]<=ClusterDistancelong[K])
			{
				NodeCluster[j]=K;
				
			}
			
			}
			 
			
		}
		NodeFile.close();
		TrafficFile.close();
		return NodeCluster;
		
	
	}
}
