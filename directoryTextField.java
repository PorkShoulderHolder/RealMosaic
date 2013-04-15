
import java.awt.Color;

import ljing.*;

public class directoryTextField extends TextField{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8059384509151224804L;
	private bottlecapGui gui;
	public <integer> directoryTextField(bottlecapGui gui){
		super(" directory",20);

		this.gui= gui;
	}
	public void actionPerformed(){
		this.setBackground(Color.GREEN);
		gui.getPicsDirectory(getText());
	}

}
