package UIComponents;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class OnlyNumericDocumentFilter extends DocumentFilter {

	private int maxValue;
	public OnlyNumericDocumentFilter(int maxValue){
		this.maxValue = maxValue;
	}
	
	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {
		// TODO Auto-generated method stub
		
		super.insertString(fb, offset, string, attr);
		
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		// TODO Auto-generated method stub
		super.remove(fb, offset, length);
	}
	
	

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		// TODO Auto-generated method stub
		//super.replace(fb, offset, length, text, attrs);
		//System.out.println(offset+"  "+text.length());
		if( text == null){
			super.replace(fb, offset,length,null, attrs);
		}
		else{
			try{
				int value = Integer.parseInt(text, 10);
				super.replace(fb, offset, length,text, attrs);
			}catch(NumberFormatException nFormat){
				super.replace(fb, offset, 1,null, attrs);

			}
		}
		
		
		
		
	}
	
	
	
}
