package UIComponents;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mainpack.Main;
import models.Resource;

public class ResourcesPanel extends JPanel{

	private ArrayList<JLabel> quantLabel;
	private ArrayList<JLabel> idsLabel;
	private boolean forAvailable = false;
	
	public ResourcesPanel(int x,int y,int width, int height,Color color,boolean forAvailable){
		
		this.setBounds(x, y, width, height);
		this.setBackground(color);
		this.setLayout(new GridLayout(2,11));
		this.forAvailable = forAvailable;
		
		if(forAvailable){
			addComponentsForAvailable();
		}
		else{
			addComponents();
		}
	}
	
	public void addValues(){
		ArrayList<Resource> resources = new ArrayList<Resource>();

	    if (Main.comp != null){
			resources = Main.comp.getResources();
	    }
	    
	    for(int i = 0 ; i < 10 ; i++){
			String name = null;
			if(resources.size() > i){
				name = ""+resources.get(i).getId();			
			}
			idsLabel.get(i).setText(name);
			idsLabel.get(i).repaint();
		}
		
	    if(forAvailable){
	    	for(int i = 0 ; i < 10 ; i++){
				String quant = null;
				if(resources.size() > i){
					quant = ""+resources.get(i).getAvailableQuant();
				}	
				quantLabel.get(i).setText(quant);
				quantLabel.get(i).repaint();

			}
	    }
	    else{
	    	for(int i = 0 ; i < 10 ; i++){
				String quant = null;
				if(resources.size() > i){
					quant = ""+resources.get(i).getQuant();
				}	
				quantLabel.get(i).setText(quant);
				quantLabel.get(i).repaint();

			}
	    }
		
		
	}
	
	
	public void updateValues(){
		if(forAvailable){
			ArrayList<Resource> resources = new ArrayList<Resource>();

		    if (Main.comp != null){
				resources = Main.comp.getResources();
		    }
		    
		    for(int i = 0 ; i < 10 ; i++){
				String name = null;
				if(resources.size() > i){
					name = ""+resources.get(i).getId();			
				}
				idsLabel.get(i).setText(name);
				idsLabel.get(i).repaint();
			}
			
			for(int i = 0 ; i < 10 ; i++){
				String quant = null;
				if(resources.size() > i){
					quant = ""+resources.get(i).getAvailableQuant();
				}	
				quantLabel.get(i).setText(quant);
				quantLabel.get(i).repaint();

			}
		}
	}
	
	
	private void addComponents(){
		quantLabel = new ArrayList<JLabel>();
		idsLabel = new ArrayList<JLabel>();
		//
		JLabel blankLabel = new JLabel("R id:",SwingConstants.RIGHT);
	    this.add(blankLabel);
		//
		ArrayList<Resource> resources = new ArrayList<Resource>();

	    if (Main.comp != null){
			resources = Main.comp.getResources();
	    }
	    
		for(int i = 0 ; i < 10 ; i++){
			String name = null;
			if(resources.size() > i){
				name = resources.get(i).getName();			

			}		
			addIDLabelAt(i, name);
		}
		
		addArrayName("E:");
		
		for(int i = 0 ; i < 10 ; i++){
			String quant = null;
			if(resources.size() > i){
				quant = ""+resources.get(i).getQuant();
			}
			addQuantLabelAt(i, quant);
		}
	}
	
	
	private void addComponentsForAvailable(){
		quantLabel = new ArrayList<JLabel>();
		idsLabel = new ArrayList<JLabel>();
		//
		JLabel blankLabel = new JLabel("R id:",SwingConstants.RIGHT);
	    this.add(blankLabel);
		//
		ArrayList<Resource> resources = new ArrayList<Resource>();

	    if (Main.comp != null){
			resources = Main.comp.getResources();
	    }
	    
		for(int i = 0 ; i < 10 ; i++){
			String name = null;
			if(resources.size() > i){
				name = resources.get(i).getName();			

			}		
			addIDLabelAt(i, name);
		}
		
		addArrayName("A:");
		
		for(int i = 0 ; i < 10 ; i++){
			String quant = null;
			if(resources.size() > i){
				quant = ""+resources.get(i).getAvailableQuant();
			}
			addQuantLabelAt(i, quant);
		}
	}
	
	
	
	
	
	private void addArrayName(String name){
		
		JLabel arrayNameLabel = new JLabel(name,SwingConstants.RIGHT);
	    this.add(arrayNameLabel);
	}
	
	private void addIDLabelAt(int x,String text){
		JLabel idLabel = new JLabel(text,SwingConstants.CENTER);
		idsLabel.add(idLabel);
	    this.add(idLabel);
	    
	}
	
	private void addQuantLabelAt(int x,String quant){
		JLabel quantL = new JLabel(quant,SwingConstants.CENTER);
		quantLabel.add(quantL);
	    this.add(quantL);
	}
}
