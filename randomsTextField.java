
import java.awt.Color;

import ljing.*;

public class randomsTextField extends TextField{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8059384509151224804L;
	private bottlecapGui gui;
	public <integer> randomsTextField(bottlecapGui gui){
		super("# of colors",30);
		this.setBackground(Color.white);
		this.gui= gui;
	}
	public void actionPerformed(){
		this.setBackground(new Color(170,150,170));
		gui.howmanyColors = Integer.parseInt(getText());
		
	}

}
