package UIComponents;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

public class NewProcessPanel extends JPanel {
	
	private JLabel processRequestTimeIntervalLabel;
	private JLabel processUseTimeIntervalLabel;
	
	private JTextField processRequestTimeIntervalTF;
	private JTextField processUseTimeIntervalTF;
	
	
	
	//int timeToRequestResource, int timeToReleaseResource
	
	public int getRequestTimeInterval(){
		try{
			int value = Integer.parseInt(processRequestTimeIntervalTF.getText(), 10);
			return  value;
		}catch(NumberFormatException nFormat){
			return  -1;

		}
		
	}
	
	public int getUseTimeInterval(){
		try{
			int value = Integer.parseInt(processUseTimeIntervalTF.getText(), 10);
			return  value;
		}catch(NumberFormatException nFormat){
			return  -1;

		}
	}
	
	public NewProcessPanel(int width,int height,Color color){
		this.setSize(width,height);
		this.setBackground(color);
		this.setLayout(new GridLayout(2,6));
		
		addComponents();
		resetValues();
	}
	
	public NewProcessPanel(int x,int y,int width,int height,Color color){
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
		processRequestTimeIntervalTF.setText(null);
		processUseTimeIntervalTF.setText(null);
	}
	
	/**
	 * Call this method for you know if there is some value equal to null
	 * @return false when there is not a null value, or true when there is some value null,
	 * */
	public boolean isThereSomeValueNull(){
		return processRequestTimeIntervalTF.getText().isEmpty() || processUseTimeIntervalTF.getText().isEmpty();
	}
	
	private void addComponents(){
		addProcessRequestTimeIntervalLabel();
		addProcessUseTimeIntervalLabel();
		
		addProcessRequestTimeIntervalTF();
		addProcessUseTimeIntervalTF();
	}
	
	
	
	
	private void addProcessRequestTimeIntervalLabel(){
		processRequestTimeIntervalLabel = new JLabel("Intervalo entre solicitacoes",SwingConstants.CENTER);
		
	    this.add(processRequestTimeIntervalLabel);
	}
	
	private void addProcessUseTimeIntervalLabel(){
		processUseTimeIntervalLabel = new JLabel("Tempo de utilizacao(s)",SwingConstants.CENTER);
		
	    this.add(processUseTimeIntervalLabel);
	}
	
	
	
	private void addProcessRequestTimeIntervalTF(){
		processRequestTimeIntervalTF = new JTextField();
		processRequestTimeIntervalTF.setHorizontalAlignment(SwingConstants.CENTER);
		((AbstractDocument)processRequestTimeIntervalTF.getDocument()).setDocumentFilter(new OnlyNumericDocumentFilter(6));		
	    this.add(processRequestTimeIntervalTF);
	}
	
	private void addProcessUseTimeIntervalTF(){
		processUseTimeIntervalTF = new JTextField();
		processUseTimeIntervalTF.setHorizontalAlignment(SwingConstants.CENTER);
		((AbstractDocument)processUseTimeIntervalTF.getDocument()).setDocumentFilter(new OnlyNumericDocumentFilter(6));
	    this.add(processUseTimeIntervalTF);
	}
}
