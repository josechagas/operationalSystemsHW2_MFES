package UIComponents;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

public class NewResourcePanel extends JPanel {
	
	private JLabel resourceNameLabel;
	private JLabel resourceQuantLabel;
	
	private JTextField resourceNameTextField;
	private JTextField resourceQuantTextField;
	
	
	
	public String getResourceName(){
		return resourceNameTextField.getText();
	}
	
	
	public int getResourceQuant(){
		try{
			int value = Integer.parseInt(resourceQuantTextField.getText(), 10);
			return  value;
		}catch(NumberFormatException nFormat){
			return  -1;

		}
	}
	
	public NewResourcePanel(int width,int height,Color color){
		this.setSize(width,height);
		this.setBackground(color);
		this.setLayout(new GridLayout(2,6));
		
		addComponents();
		resetValues();
	}
	
	
	public NewResourcePanel(int x,int y,int width,int height,Color color){
		this.setBounds(x, y, width, height);
		this.setBackground(color);
		this.setLayout(new GridLayout(2,6));
		
		addComponents();
		resetValues();
	}
	/**
	 * Call this method to clean all values
	 * */
	public void resetValues(){
		resourceNameTextField.setText(null);
		resourceQuantTextField.setText(null);
	}
	
	/**
	 * Call this method for you know if there is some value equal to null
	 * @return false when there is not a null value, or true when there is some value null,
	 * */
	public boolean isThereSomeValueNull(){
		return resourceQuantTextField.getText().isEmpty() || getResourceName().isEmpty() ;
	}
	
	private void addComponents(){
		addResourceNameLabel();
		addResourceQuantLabel();
		
		addResourceNameTF();
		addResourceQuantTF();
	}
	
	private void addResourceNameLabel(){
		resourceNameLabel = new JLabel("Nome do Recurso:",SwingConstants.CENTER);
		
	    this.add(resourceNameLabel);
	}
	
	
	
	private void addResourceQuantLabel(){
		resourceQuantLabel = new JLabel("Numero de Instancias:",SwingConstants.CENTER);
		
	    this.add(resourceQuantLabel);
	}
	
	private void addResourceNameTF(){
		resourceNameTextField = new JTextField();
		resourceNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
	    this.add(resourceNameTextField);
	}
	
	
	
	private void addResourceQuantTF(){
		resourceQuantTextField = new JTextField();
		resourceQuantTextField.setHorizontalAlignment(SwingConstants.CENTER);
		((AbstractDocument)resourceQuantTextField.getDocument()).setDocumentFilter(new OnlyNumericDocumentFilter(6));
	    this.add(resourceQuantTextField);
	}
}
