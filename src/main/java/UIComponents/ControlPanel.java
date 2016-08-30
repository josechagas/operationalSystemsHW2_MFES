package UIComponents;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ControlPanel extends JPanel implements Runnable {

	private ResourcesPanel existingPanel;
	
	private ResourcesNamesPanel resourcesPanel;
	
	private ResourcesPanel availablePanel;
	
	private ProcessesPanel useResourcesMatrix;
	
	private ProcessesPanel matrixOfResourcesRequired;

	
	public ControlPanel(int x,int y,int width, int height,Color color){
		
		this.setBounds(x, y, width, height);
		this.setBackground(color);
		this.setLayout(null);
		
		addComponents();
	}	
	
	
	private void addComponents(){
		addResourcesPanel();
		addExistingPanel();
		addAvailablePanel();
		
		addUseResourcesMatrix();
		addMatrixOfResourcesRequired();
	}
	
	/**
	 * add a panel that shows the name and id of all resources
	 * */
	private void addResourcesPanel(){
		
		resourcesPanel = new ResourcesNamesPanel(5, 5,this.getWidth() - 15,40,Color.GRAY);
		this.add(resourcesPanel);
		
	}
	/**
	 * add a panel that shows the number of instances and id of all resources
	 * */
	private void addExistingPanel(){
		
		existingPanel = new ResourcesPanel(5,40 + 10,this.getWidth()/2 - 5,40,Color.GRAY,false);
		this.add(existingPanel);
		
	}
	/**
	 * add a panel that shows the number of instances that are not being used and id of all resources
	 * */
	private void addAvailablePanel(){
		
		availablePanel = new ResourcesPanel(this.getWidth()/2 + 5,40 + 10,this.getWidth()/2 - 15,40,Color.GRAY,true);
		this.add(availablePanel);
		
	}
	
	/**
	 * add a panel that shows a matrix of processes and the number of resources of each kind being used
	 * */
	private void addUseResourcesMatrix(){
		useResourcesMatrix = new ProcessesPanel(5,availablePanel.getY() + 40 + 5,this.getWidth()/2 - 5 ,this.getHeight() - (availablePanel.getY() + 40) - 10,Color.GRAY,false);
		
		this.add(useResourcesMatrix);
	
	}
	
	/**
	 * add a panel that shows a matrix of processes and the number of resources of each kind that are being required by corresponding process
	 * */
	private void addMatrixOfResourcesRequired(){
		matrixOfResourcesRequired = new ProcessesPanel(useResourcesMatrix.getWidth() + 10,availablePanel.getY() + 40 + 5,this.getWidth()/2 - 15,this.getHeight() - (availablePanel.getY() + 40) - 10,Color.GRAY,true);
		
		this.add(matrixOfResourcesRequired);
	
	}
	
	public void updateProcessesPanels(){
		useResourcesMatrix.updateValues();
		matrixOfResourcesRequired.updateValues();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		existingPanel.addValues();
		resourcesPanel.addValues();
		availablePanel.addValues();
		useResourcesMatrix.updateValues();
		while(true){
			try {Thread.sleep(100);} 
			catch (InterruptedException e) {e.printStackTrace();}
			availablePanel.updateValues();
			updateProcessesPanels();

		}
		
	}
	
	
}
