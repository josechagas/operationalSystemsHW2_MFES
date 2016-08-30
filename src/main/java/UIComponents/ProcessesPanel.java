package UIComponents;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mainpack.Main;
import models.Resource;
import models.ResourceConnection;
import models.Process;

public class ProcessesPanel extends JPanel {

	private ArrayList<JLabel> processIDLabel; //15
	private ArrayList<JLabel> resourceQuantLabel; //10*15
	private  boolean forRequiredResources = false;
	
	public ProcessesPanel(int x,int y,int width, int height,Color color,boolean forRequiredResources){
		this.setBounds(x, y, width, height);
		this.setBackground(color);
		this.setLayout(new GridLayout(15,11));
		this.forRequiredResources = forRequiredResources;
		addComponents();
	}
	
	
	public void updateValues(){
		
		ArrayList<Process> processes = new ArrayList<Process>();
		ArrayList<Resource> resources = new ArrayList<Resource>();

	    if (Main.comp != null){
	    	processes = Main.comp.getOperationalSystem().getProcesses();
	    	resources = Main.comp.getResources();
	    }
	    
		for(int i = 0 ; i < 15;i++){
			Process process = null;
			//first element of each line
		    String name = null;
			if(processes.size() > i){

				process = processes.get(i);
				name = "P "+process.getId();	
			}	
			processIDLabel.get(i).setText(name);
		    //
		    
			for(int j = 0 ; j < 10;j++){
				String quant = "0";
				
				if (process == null){
					quant = null;
				}
				else if(resources.size() > j){
					Resource resource = resources.get(j);
					if(forRequiredResources){//for matrix of requiredResources
						if(process.getResourceTryingAcquire() != null){
							quant = process.getResourceTryingAcquire().getId() == resource.getId() ? "1" : "0";
						}
					}
					else{//for matrix of using resources
						quant = ""+process.numbOfConnectionWithResource(resource.getId());
					}
				}
				resourceQuantLabel.get(j + i*10).setText(quant);
			}
		}
		
	}
	
	
	private void addComponents(){
		processIDLabel = new ArrayList<JLabel>();
		resourceQuantLabel = new ArrayList<JLabel>();

		
		
		ArrayList<Process> processes = new ArrayList<Process>();
		ArrayList<Resource> resources = new ArrayList<Resource>();

	    if (Main.comp != null){
	    	processes = Main.comp.getOperationalSystem().getProcesses();
	    	resources = Main.comp.getResources();
	    }
	    
	    
	    
		for(int i = 0 ; i < 15;i++){
			Process process = null;
			//first element of each line
		    String name = "P?";
			if(processes.size() > i){

				process = processes.get(i);
				name = "P "+process.getId();			
			}	
		    addProcessLabel(name);
		    //
		    
			for(int j = 0 ; j < 10;j++){
				String quant = "0";
				
				if (process == null){
					quant = null;
				}
				else if(resources.size() > j){
					Resource resource = resources.get(j);
					if(forRequiredResources){//for matrix of requiredResources
						quant = process.getResourceTryingAcquire().getId() == resource.getId() ? "1" : "0";
					}
					else{//for matrix of using resources
						quant = ""+process.numbOfConnectionWithResource(resource.getId());
					}
				}
				addResourceQuantLabel(quant);
			}
		}
		
	}
	
	private void addProcessLabel(String text){
		JLabel processLabel = new JLabel(text,SwingConstants.CENTER);
		processIDLabel.add(processLabel);
	    this.add(processLabel);
	}
	
	private void addResourceQuantLabel(String text){
		JLabel usedResourceQuantLabel = new JLabel(text,SwingConstants.CENTER);
		resourceQuantLabel.add(usedResourceQuantLabel);
	    this.add(usedResourceQuantLabel);
	}
}
