package Srbiau.ac.CFGA;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Arrays;
import java.util.Collections;

//import org.apache.poi.ss.usermodel.CellType;
 


public class Vehicles {
static double RoadDistance;
static double HighwayDistance;
static int BaseStationNumber;
static int ClusterNumber;
static double ClusterDistance;
double TempValue;
static String TempStringValue;
static String CMPStringValue;
static String Path;
static String NodePath;
static String TrafficData;
static double[][] DistanceArray;
static double[][] TotalCapacity;
static double[][] ClusterDistancelong;
static double clusterInitial=0.0;
static int[] NodeCluster;
static int rowTotal;
static double[]  NodeTotalCapacityArray;
static double[] NodeOutlierCapacityArray;
static double[] NodeIntervalCapacityArray;
static double[] arr;

//obtaining input bytes from a file  
	public static double[] clusterdata() throws IOException
	{
Path ="D:\\Fileaddress";
NodePath ="D:\\Fileaddress";
TrafficData ="D:\\Fileaddress";
XSSFWorkbook NodeFile = new XSSFWorkbook(NodePath);
XSSFSheet sheet = NodeFile.getSheet("Sheet1");
rowTotal= sheet.getLastRowNum();
//System.out.println("rowTotal = " + rowTotal);
RoadDistance = Clustering.ReadNumericCellData(2,1,Path);
HighwayDistance = Clustering.ReadNumericCellData(1,1,Path);
ClusterNumber =0;
CMPStringValue =new String("BaseStation");

//Identify cluster Number
for(int j=0;j<rowTotal-1;j++) {
	TempStringValue = Clustering.ReadStringCellData(j,1,NodePath);
	if (TempStringValue.equals(CMPStringValue)) {
	ClusterNumber = ClusterNumber +1;
	}
}

ClusterDistance = HighwayDistance/ClusterNumber;
double[][] ClusterDistancelong = new double[ClusterNumber][ClusterNumber];
for ( int k=0;k< ClusterNumber; k++) {
		ClusterDistancelong[k][1]=clusterInitial+ClusterDistance;
		//System.out.println("rowTotal = "+ (clusterInitial+ClusterDistance));
		clusterInitial = clusterInitial+ClusterDistance;
	}
// Identify Node Cluster
//Identify Priority
double[][] DistanceArray = new double[rowTotal][rowTotal];
double[] NodeTotalCapacityArray = new double[rowTotal];
double[] NodeOutlierCapacityArray = new double[rowTotal];
double[] NodeIntervalCapacityArray = new double[rowTotal];
double[] arr = new double[rowTotal];
	for(int j=1;j<16;j++) {
		DistanceArray[j][1] = Clustering.ReadNumericCellData(j,2,TrafficData);
		NodeTotalCapacityArray[j] = Clustering.ReadNumericCellData(j,11,TrafficData);
		NodeOutlierCapacityArray[j] = Clustering.ReadNumericCellData(j,10,TrafficData);
		NodeIntervalCapacityArray[j] = Clustering.ReadNumericCellData(j,9,TrafficData);
		for(int K=0;K<ClusterNumber; K++)
		{
		//if (DistanceArray[j][1]<=ClusterDistancelong)
	//	{
	//		NodeCluster[j]=K;
			
		//}
		
		}
		arr[j]= NodeTotalCapacityArray[j]-(NodeOutlierCapacityArray[j] +NodeIntervalCapacityArray[j]);
		//System.out.println("NodeList = " + arr[j]);
		 
		
	}
	  Arrays.sort(arr);
	  NodeFile.close();

	return arr;
}
	
	}


	
	

