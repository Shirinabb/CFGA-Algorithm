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

public class SystemCostModel {
	
	 //static double[] 
	static double  NodeCostArray;
	static double NodeEnergyArray;
	static double NodeServiceCostArray;
	static double NodeRechargeEnergyArray;
	static double ProcessEnergyArray;
	static double NodeIntervalCapacityArray;
	static double NodeOutlierCapacityArray;
	static double NodeTotalCapacityArray;
	static double NodeMaintenanceCostArray;
	static double NodeConfigurationCostArray;
	static double NodeDeviceCostArray;
	static double ServiceMaintainanceCostArray;
	static double ServiceConfigurationCostArray;
	static double ServiceResponseCostArray;
	static double NodeMaintainCostArray;
	static double ServiceTotalEnergy;
	static double   SUMNodeCostArray =0;
	static double SUMNodeEnergyArray=0;
	static double SUMNodeServiceCostArray=0;
	static double MessageCommunicationEnergy;
	static double SUMCOST=0;
	static double SystemCost;
	static double SUMNodeRechargeEnergyArray=0;
	static double SUMProcessEnergyArray=0;
	static double SUMNodeMaintainCostArray=0;
	static double TotalNodeExistedEnergyArray=0;
	static double SUMNodeIntervalCapacityArray=0;
	static double SUMNodeOutlierCapacityArray=0;
	static double SUMNodeTotalCapacityArray=0;
	static double SUMNodeMaintenanceCostArray=0;
	static double SUMNodeConfigurationCostArray=0;
	static double SUMNodeDeviceCostArray=0;
	static double SystemFixCost=0;
	static double ServiceNodeConsumption;
	static double NodeCapacityConsumption;
	static double FixedNodeCostConsumption;
	static double SUMServiceMaintainanceCostArray=0;
	static double SUMServiceConfigurationCostArray=0;
	static double SUMServiceResponseCostArray=0;
	static double SUMSystemMaintenanceCost=0;
	static double SUMSystemResponseCost=0;
	static double SUMSystemConfigurationCost=0;
	static double SUMServiceTotalEnergy=0;
	static double SUMMessageCommunicationEnergy=0;
	static double SystemTotalExistedEnergy=0;
	static double SystemFaultTeloranceEnergy=0;
	static double SystemRoutingEnergy=0;
	static double SystemIntevalLoadBalancing=0;
	static double Systemload =0;
	static double SystemOutlierLoadBalancing=0;
	static double SystemLoadBalancing=0;
	static double fixedServiceCost;
	static double fixedSystemCost;
	static double FixedSystemCostConsumption;
	static class Data {
        static int        nNodes;
        int        nServices;
        double[]   NodesCost;
        double[]   foodMin;
        double[]   foodMax;
        double[]   nutrMin;
        double[]   nutrMax;
        double[][] ServicesCost;
  
 
      public  static IloCplex buildModelByColumn(
    		  //IloMPModeler  model
    		  //,
               // Data          data,
               // IloNumVar[]   CostNode
                //IloNumVarType type
                ) throws IloException, IOException {
        	String ServicePath ="D:\\Fileaddress";
        	String NodePath ="D:\\Fileaddress";
        	String SystemPath ="D:\\Fileaddress";
        	String ProcessPath ="D:\\Fileaddress";
        	String TrafficPath ="D:\\Fileaddress";
        	String MessagePath ="D:\\Fileaddress";
        	IloCplex     model = new IloCplex();
        	XSSFWorkbook NodeFile = new XSSFWorkbook(NodePath);
        	XSSFSheet sheet = NodeFile.getSheet("Sheet1");
        	int nodes= sheet.getLastRowNum();
        	XSSFWorkbook ServiceFile = new XSSFWorkbook(ServicePath);
        	XSSFSheet sheetService = ServiceFile.getSheet("Sheet1");
        	int services = sheetService.getLastRowNum();
        	XSSFWorkbook SystemFile = new XSSFWorkbook(SystemPath);
        	XSSFSheet sheetSystem = SystemFile.getSheet("Sheet1");
        	int SystemRow = sheetSystem.getLastRowNum();
        	XSSFWorkbook ProcessFile = new XSSFWorkbook(ProcessPath);
        	XSSFSheet sheetProcess = ProcessFile.getSheet("Sheet1");
        	int Processes = sheetProcess.getLastRowNum();
        	XSSFWorkbook TrafficFile = new XSSFWorkbook(TrafficPath);
        	XSSFSheet sheetTraffic = TrafficFile.getSheet("Sheet1");
        	int TrafficData = sheetTraffic.getLastRowNum();
        	XSSFWorkbook MessageFile = new XSSFWorkbook(MessagePath);
        	XSSFSheet Messagesheet = MessageFile.getSheet("Sheet1");
        	int Messages= Messagesheet.getLastRowNum();
        	for(int j=1;j<nodes-1;j++) {
        		NodeCostArray= Clustering.ReadNumericCellData(j,4,NodePath);
        		SUMNodeCostArray= (SUMNodeCostArray+NodeCostArray);
        	}
        	for(int j=1;j<nodes-1;j++) {
        		NodeEnergyArray = Clustering.ReadNumericCellData(j,2,NodePath);
        		SUMNodeEnergyArray= (SUMNodeEnergyArray+NodeEnergyArray);
        	}
        	for(int j=1;j<services-1;j++) {
        		NodeServiceCostArray = Clustering.ReadNumericCellData(j,7,ServicePath);
        		SUMNodeServiceCostArray=(SUMNodeServiceCostArray+NodeServiceCostArray);
        	}
        	SUMCOST = SUMNodeServiceCostArray+SUMNodeEnergyArray+SUMNodeCostArray;
        	SystemCost=Clustering.ReadNumericCellData(1,7,ServicePath);
        	 IloNumVar   SUMCostNode;
        	 SUMCostNode= model.numVar(SUMCOST, 0);
        	 
//minimize sum(n in Node) NodeCost[n]+sum(s in Service) ServicePrice[s]+ sum(n in Node) NodeEnergy[n]+SystemFixCost;
//model.addMinimize(model.sum(SUMCostNode,SystemCost));

//ServiceNodeConsumption   sum(n in Node) TotalNodeEnergy[n]+sum(n in Node) RechargeEnergy[n]+sum(p in Process) ProcessEnergy[p]<=TotalNodeEnergyExisted;

for(int j=1;j<nodes-1;j++) {
	NodeRechargeEnergyArray = Clustering.ReadNumericCellData(j,7,NodePath);
	SUMNodeRechargeEnergyArray=(SUMNodeRechargeEnergyArray+NodeRechargeEnergyArray);
}
for( int j=1;j<Processes-1;j++) {
	ProcessEnergyArray = Clustering.ReadNumericCellData(j,8,ProcessPath);
	SUMProcessEnergyArray=(SUMProcessEnergyArray+ProcessEnergyArray);
}
// RT Response time
//DT DelayTime
//PT ProcessTime 
ServiceNodeConsumption=SUMProcessEnergyArray+SUMNodeRechargeEnergyArray+SUMNodeEnergyArray;
IloNumVar   SUMServiceNodeConsumption;
SUMServiceNodeConsumption= model.numVar(ServiceNodeConsumption, 0);
// NodeCapacityConsumption:NodeIntervalCapacity[n]+NodeOutlieCapacity[n]<=NodeCapacity[n];
for( int j=1;j<TrafficData-1;j++) {
	NodeIntervalCapacityArray = Clustering.ReadNumericCellData(j,9,TrafficPath);
	SUMNodeIntervalCapacityArray=(SUMNodeIntervalCapacityArray+NodeIntervalCapacityArray);
//}
//for( int j=1;j<TrafficData-1;j++) {
	NodeOutlierCapacityArray = Clustering.ReadNumericCellData(j,10,TrafficPath);
	SUMNodeOutlierCapacityArray=(SUMNodeOutlierCapacityArray+NodeOutlierCapacityArray);
//}
//NodeCapacityConsumption=SUMNodeIntervalCapacityArray+SUMNodeOutlierCapacityArray;
//IloNumVar   SUMNodeCapacityConsumption;
//SUMNodeCapacityConsumption= model.numVar(NodeCapacityConsumption, 0);
//for( int j=1;j<TrafficData-1;j++) {
	NodeTotalCapacityArray = Clustering.ReadNumericCellData(j,11,TrafficPath);
	SUMNodeTotalCapacityArray=(SUMNodeTotalCapacityArray+NodeTotalCapacityArray);
	NodeCapacityConsumption=SUMNodeIntervalCapacityArray+SUMNodeOutlierCapacityArray;
	//IloNumVar   SUMNodeCapacityConsumption;
	//SUMNodeCapacityConsumption= model.numVar(NodeCapacityConsumption, 0);
}
//NodeCapacityConsumption=SUMNodeIntervalCapacityArray+SUMNodeOutlierCapacityArray;
//model.minimize(model.sum(SUMCostNode,SystemCost));

//SystemEnergy sum(s in Service)TotalServiceEnergy[s]<=TotalExistedSystemEnergy;
for(int j=1;j<services-1;j++) {
	ServiceTotalEnergy = Clustering.ReadNumericCellData(j,9,ServicePath);
	SUMServiceTotalEnergy=(SUMServiceTotalEnergy+ServiceTotalEnergy);
}
SystemTotalExistedEnergy=Clustering.ReadNumericCellData(13,1,SystemPath);


IloNumVar   SUMfixedSystemTotalEnergy;
SUMfixedSystemTotalEnergy= model.numVar(SystemTotalExistedEnergy, 0);
IloRange[]   constraint = new IloRange[nodes];
constraint[1] = model.ge(SUMServiceNodeConsumption,TotalNodeExistedEnergyArray);
//constraint[2]= model.addRange(NodeCapacityConsumption,SUMNodeOutlierCapacityArray);
constraint[2]= model.ge(SUMfixedSystemTotalEnergy,SUMServiceTotalEnergy);
model.minimize(model.sum(SUMCostNode,SystemCost));
NodeFile.close();
ServiceFile.close();
SystemFile.close();
ProcessFile.close();
TrafficFile.close();
MessageFile.close();
return model;
      }

      public static void ReliabilityModel(String[] args) {
	// TODO Auto-generated method stub
try {
    String          filename  = "D:\\Fileaddress";
    boolean         byColumn  = false;
    IloNumVarType   varType   = IloNumVarType.Float;

    for (int i = 1; i < args.length; i++) {
       if ( args[i].charAt(0) == '-') {
          switch (args[i].charAt(1)) {
          case 'c':
             byColumn = true;
             break;
          case 'i':
             varType = IloNumVarType.Int;
             break;
          default:
             usage();
             return;
          }
       }
       else {
          filename = args[i];
          break;
       }
    }
    String NodePath ="D:\\Fileaddress";
    XSSFWorkbook NodeFile = new XSSFWorkbook(NodePath);
	XSSFSheet sheet = NodeFile.getSheet("Sheet1");
	int nodes= sheet.getLastRowNum();
    
    //Data data = new Data(filename);
	IloCplex     cplex = new IloCplex();
IloNumVar[]  NodeCost   = new IloNumVar[nodes];
//buildModelByRow (cplex, NodeCost, varType);


//if ( byColumn ) 
cplex = buildModelByColumn();
//   else buildModelByRow (cplex, NodeCost, varType);

      // Solve model
 
      if ( cplex.solve() ) { 
        System.out.println();
        System.out.println("Solution status = " + cplex.getStatus());
        System.out.println();
        //System.out.println("cost = " + cplex.getObjValue());
        
        System.out.println();
      }
      else
      {
    	  System.out.println("error= " + cplex.getValues(NodeCost));
    	  //System.out.println("error= " + cplex.getInfeasibilities(arg0));
    	  
    	  //FeasOpt parameter
      }
	 // Objective:
	  // minimize sum(j) Buy[j] * foodCost[j]
	//minimize sum(n in Node) NodeCost[n]+sum(s in Service) ServicePrice[s]+ sum(n in Node) NodeEnergy[n]+SystemFixCost;
	//// Constraints:
	  // forall foods i: nutrMin[i] <= sum(j) Buy[j] * nutrPer[i][j] <= nutrMax[j]
  // sum(n in Node) TotalNodeEnergy[n]+sum(n in Node) RechargeEnergy[n]+sum(p in Process) ProcessEnergy[p]<=TotalNodeEnergyExisted;;
    //NodeIntervalCapacity[n]+NodeOutlieCapacity[n]<=NodeCapacity[n];
   // MaintenanceCost[n]+ConfigurationCost[n]+deviceCost[n]<= SystemFixCost;
    //MaintenanceServiceCost[s]+ConfigurationServiceCost[s]+ResponseServiceCost[s]<=100000;
      // sum(s in Service)TotalServiceEnergy[s]<=TotalExistedSystemEnergy;
     //SystemIntervalCapacity+SystemOutlieCapacity<=TotalCapacity;
      cplex.end();
      NodeFile.close();
        }
        catch (IloException ex) {
           System.out.println("Concert Error: " + ex);
        }
       // catch (InputDataReader.InputDataReaderException ex) {
         //  System.out.println("Data Error: " + ex);
       // }
       // catch (java.io.IOException ex) {
         //  System.out.println("IO Error: " + ex);
       // }
 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
  
     static void usage() {
        System.out.println(" ");
               System.out.println("options: -c  build model by column");
        System.out.println("         -i  use integer variables");
        System.out.println(" ");
     }
  }
	
	
}































