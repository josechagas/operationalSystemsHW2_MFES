package UIComponents;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

public class RemoveProcessPanel extends JPanel{

	
	private JLabel removeProcessLabel;
	private JTextField removeProcessTF;

	public int getProcessID(){
		try{
			int value = Integer.parseInt(removeProcessTF.getText(), 10);
			return  value;
		}catch(NumberFormatException nFormat){
			System.out.println("erro em getUseTimeInterval RemoveProcessPanel");
			return  -1;

		}
	}	
	public RemoveProcessPanel(int width,int height,Color color){
		this.setSize(width,height);
		this.setBackground(color);
		this.setLayout(new GridLayout(2,6));
		addComponents();
	}
	
	public RemoveProcessPanel(int x,int y,int width,int height,Color color){
		this.setBounds(x, y, width, height);
		this.setBackground(color);
		this.setLayout(new GridLayout(2,2));
		
		addComponents();
	}
	
	/**
	 * Call this method to clean all values
	 * */
	public void resetValues(){
		removeProcessTF.setText(null);
	}
	
	/**
	 * Call this method for you know if there is some value equal to null
	 * @return false when there is not a null value, or true when there is some value null,
	 * */
	public boolean isThereSomeValueNull(){
		return removeProcessTF.getText().isEmpty();
	}
	
	private void addComponents(){
		addRemoveProcessLabel();
		addRemoveProcessTF();
	}
	
	private void addRemoveProcessLabel(){
		removeProcessLabel = new JLabel("Id do processo",SwingConstants.CENTER);
	    this.add(removeProcessLabel);
	}
	
	private void addRemoveProcessTF(){
		removeProcessTF = new JTextField();
		removeProcessTF.setHorizontalAlignment(SwingConstants.CENTER);
		((AbstractDocument)removeProcessTF.getDocument()).setDocumentFilter(new OnlyNumericDocumentFilter(6));		
	    this.add(removeProcessTF);
	}
}
