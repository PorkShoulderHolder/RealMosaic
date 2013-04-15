
import java.awt.Color;

import ljing.*;

public class pictureTextField extends TextField{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8059384509151224804L;
	private bottlecapGui gui;
	public <integer> pictureTextField(bottlecapGui gui){
		super(" picture pathname",20);
		this.setBackground(Color.white);
		this.gui= gui;
	}
	public void actionPerformed(){
		this.setBackground(Color.GREEN);
		gui.getMainPicture(getText());
	}

}
