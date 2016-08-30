package UIComponents;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mainpack.Main;
import models.Resource;

public class ResourcesNamesPanel extends JPanel {
	
	private ArrayList<JLabel> namesLabel;
	private ArrayList<JLabel> idsLabel;

	
	public ResourcesNamesPanel(int x,int y,int width, int height,Color color){
		this.setBounds(x, y, width, height);
		this.setBackground(color);
		this.setLayout(new GridLayout(2,11));
		
		addComponents();
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
	    
	    for(int i = 0 ; i < 10 ; i++){
			String name = null;
			if(resources.size() > i){
				name = resources.get(i).getName();			

			}		
			namesLabel.get(i).setText(name);
		}
	    
	}
	
	private void addComponents(){
		namesLabel = new ArrayList<JLabel>();
		idsLabel = new ArrayList<JLabel>();

		JLabel blankLabel = new JLabel("R id:",SwingConstants.RIGHT);
	    this.add(blankLabel);
		
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
	    
	    
	    //
		JLabel titleLabel = new JLabel("R.Name:",SwingConstants.RIGHT);
	    this.add(titleLabel);
		//
		for(int i = 0 ; i < 10 ; i++){
			String name = null;
			if(resources.size() > i){
				name = resources.get(i).getName();			

			}		
			addNameLabelAt(i, name);
		}
		
	}
	
	private void addNameLabelAt(int x,String text){
		JLabel nameLabel = new JLabel(text,SwingConstants.CENTER);
		namesLabel.add(nameLabel);
	    this.add(nameLabel);
	    
	}
	
	private void addIDLabelAt(int x,String text){
		JLabel idLabel = new JLabel(text,SwingConstants.CENTER);
		idsLabel.add(idLabel);
	    this.add(idLabel);
	    
	}
}

