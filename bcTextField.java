
import java.awt.Color;

import ljing.*;

public class bcTextField extends TextField{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8059384509151224804L;
	private bottlecapGui gui;
	public <integer> bcTextField(bottlecapGui gui){
		super("# of bottlecaps",20);
		this.setBackground(Color.white);
		this.gui= gui;
	}
	public void actionPerformed(){
		this.setBackground(new Color(170,150,170));
		gui.howmanyBCs = Integer.parseInt(getText());
	}
}
