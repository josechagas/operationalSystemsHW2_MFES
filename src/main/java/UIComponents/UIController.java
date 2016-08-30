


package UIComponents;

import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

import mainpack.Main;
import models.*;
import models.Process;

public class UIController extends JFrame {

	
	
	private NewResourcePanel newRPanel;
	private JButton newResourceButton;
	
	private NewProcessPanel newPPanel;
	private JButton newProcessButton;
	
	private RemoveProcessPanel removePPanel;
	private JButton removeProcessButton;
	
	private JButton startButton;
	

	private ControlPanel controlP;

	public UIController(){
		this.setSize(1100,800);
		this.setResizable(false);
		//add all components
		addComponents();
		addBasicListeners();
		this.setVisible(true);
	}
	
	public UIController(String title){
		this.setTitle(title);
		//add all components
		

		this.setSize(1100,800);
		this.setResizable(false);
		addComponents();
		addBasicListeners();
		this.setVisible(true);
		
	}
	
	
	private void addBasicListeners(){
		this.addWindowListener(new WindowAdapter() {
	    	//coloca todos os metodos necessarios para acabar com todas as threads
	    	public void windowClosing(WindowEvent windowEvent){

	    		if (Main.comp != null){
					for(Resource r : Main.comp.getResources()){
						System.out.println("\n\n\n resource "+r.getName()+" tem quant "+r.getQuant()+" e tem available "+r.getAvailableQuant());
					}
				}
	            System.exit(0);
	    	}        
	    }); 
	}
	
	
	public void startUpdating(){
		Thread compThread = new Thread(controlP);
		compThread.start();
		
	}
	
	
	private void addComponents(){
		this.setLayout(null);
		
		addNewResourcePanel();
		addNewResourceButton();
		
		addNewProcessPanel();
		addNewProcessButton();
		
		addRemoveProcessPanel();
		addRemoveProcessButton();
		
		addStartSimulationButton();
		
		addControlPanel();

	}
	
	private void addControlPanel(){
		controlP = new ControlPanel( 0, newRPanel.getHeight(),this.getWidth(),692,Color.WHITE);
		this.add(controlP);
	}
	
	/**
	 * Add a panel with all components necessary to add a new resource
	 * */
	private void addNewResourcePanel(){
		newRPanel = new NewResourcePanel(1000, 40, new Color(0.5f,0.5f,0.5f));
		
		this.add(newRPanel);
		
	}
	
	private void addNewProcessPanel(){
		newPPanel = new NewProcessPanel(0,this.getHeight() - 68,400, 40, new Color(0.5f,0.5f,0.5f));
		
		this.add(newPPanel);
		
	}
	
	private void addRemoveProcessPanel(){
		removePPanel = new RemoveProcessPanel(newProcessButton.getWidth() + newProcessButton.getX() +  1,this.getHeight() - 68,225, 40, new Color(0.5f,0.5f,0.5f));
		
		this.add(removePPanel);
		
	}
	
	/**
	 * Adds the button that adds a new resource
	 * */
	private void addNewResourceButton(){
		newResourceButton = new JButton("Add");
		newResourceButton.setForeground(Color.WHITE);
		newResourceButton.setBounds(newRPanel.getWidth() + 1,newRPanel.getY(), 100, newRPanel.getHeight());
		newResourceButton.setBackground( new Color(0.5f,0.5f,0.5f));
		newResourceButton.addActionListener(new ActionListener() {
			 
	            public void actionPerformed(ActionEvent e)
	            {
	            	if(!newRPanel.isThereSomeValueNull()){
	            		
	            		if(Main.comp == null){
	            			Resource newResource = new Resource(0,newRPanel.getResourceName(),newRPanel.getResourceQuant());
	            			ArrayList<Resource> array = new ArrayList<Resource>();
	            			array.add(newResource);
	            			
	            			Main.InitializeSystem(array);
	            			
	            			startButton.setEnabled(true);
	            			
							System.out.println("Added new resource with name "+newRPanel.getResourceName()+" id "+newResource.getId()+" quantidade "+newRPanel.getResourceQuant());

	            		}
	            		else if(Main.comp.isThereAResourceWithName(newRPanel.getResourceName())){
							System.out.println("There is a resource with this name "+newRPanel.getResourceName());
	            		}
	            		else if(Main.comp.numberOfResources() == 10){
							System.out.println("You can not create more resources , limit 10");
	            		}
	            		else{
	            			Resource newResource = new Resource(Main.comp.numberOfResources(),newRPanel.getResourceName(),newRPanel.getResourceQuant());
							Main.comp.addNewResource(newResource);
							System.out.println("Added new resource with name "+newRPanel.getResourceName()+" id "+newResource.getId()+" quantidade "+newRPanel.getResourceQuant());
	            		}
	            		
	            		newRPanel.resetValues();
	            	}
	            	else{
						System.out.println("Can not add a new resource because there is some value null");
	            	}
	            	
	            	
	            }
	     });
		this.add(newResourceButton);
	}
	
	/**
	 * Adds the button that adds a new process
	 * */
	private void addNewProcessButton(){
		newProcessButton = new JButton("Add");
		newProcessButton.setForeground(Color.WHITE);
		newProcessButton.setBounds(newPPanel.getWidth() + 1,newPPanel.getY(), 100, newPPanel.getHeight());
		newProcessButton.setBackground( new Color(0.4f,0.4f,0.4f));
		newProcessButton.addActionListener(new ActionListener() {
			 
	            public void actionPerformed(ActionEvent e)
	            {
	     
	            	if(!newPPanel.isThereSomeValueNull()){
	            		Process newProcess = new Process(Main.comp.getOperationalSystem().getNextID(), newPPanel.getRequestTimeInterval(), newPPanel.getUseTimeInterval());
	            		Main.comp.getOperationalSystem().addNewProcess(newProcess);
	            		
	            		System.out.println("Added new process with "+" request interval "+newPPanel.getRequestTimeInterval()+" utilization interval "+newPPanel.getUseTimeInterval());
	            		
	            		removeProcessButton.setEnabled(true);
	            		
	            		if(Main.comp.getOperationalSystem().numberOfProcesses() == 15){
	            			newProcessButton.setEnabled(false);
	 						System.out.println("You can not create more processes , limit 15");
	             		}
	            		newPPanel.resetValues();

	            		controlP.updateProcessesPanels();
	            	}
	            	else{
						System.out.println("Can not add a new process because there is some value null");
	            	}
	            }
	     });
		
		newProcessButton.setEnabled(false);
		this.add(newProcessButton);
	}
	
	
	/**
	 * Adds the button that removes an existing process
	 * */
	private void addRemoveProcessButton(){
		removeProcessButton = new JButton("Remover");
		removeProcessButton.setForeground(Color.WHITE);
		removeProcessButton.setBounds(removePPanel.getWidth() + removePPanel.getX() + 1,removePPanel.getY(), 100, removePPanel.getHeight());
		removeProcessButton.setBackground( new Color(0.4f,0.4f,0.4f));
		removeProcessButton.addActionListener(new ActionListener() {
			 
	            public void actionPerformed(ActionEvent e)
	            {
	            	
	            	if(!removePPanel.isThereSomeValueNull()){
	            		
	            		if(!Main.comp.getOperationalSystem().removeProcessWithID(removePPanel.getProcessID())){
							System.out.println("Process does not exists");//falha para remover
	            		}
	            		else{//sucesso na remocao
	            			newProcessButton.setEnabled(true);
	            			Main.comp.getOperationalSystem().showedAlertOfDeadlock = false;//depois de uma acao do usuario libera para mostrar o alerta de deadlock
	            			if(Main.comp.getOperationalSystem().numberOfProcesses() == 0){
	            				removeProcessButton.setEnabled(false);
	                		}
	            		}
	            		removePPanel.resetValues();

	            	}
	            	else{
						System.out.println("Can not remove a process because you do not choosed the id");
	            	}
	            }
	     });
		removeProcessButton.setEnabled(false);
		this.add(removeProcessButton);
	}
	
	
	/**
	 * Adds the button that starts the simulation
	 * */
	private void addStartSimulationButton(){
		startButton = new JButton("Iniciar");
		startButton.setForeground(Color.WHITE);
		startButton.setBounds(removeProcessButton.getWidth() + removeProcessButton.getX() + 1,removeProcessButton.getY(), 275, removeProcessButton.getHeight());
		startButton.setBackground( new Color(1f,0f,0f));
		startButton.addActionListener(new ActionListener() {
			 
	            public void actionPerformed(ActionEvent e)
	            {
	            	newResourceButton.setEnabled(false);
	        		newProcessButton.setEnabled(true);
	        		
	        		startButton.setEnabled(false);
	        		startUpdating();

	        		Main.startSimulation();
	            }
	     });
		startButton.setEnabled(false);
		this.add(startButton);
	}
}
