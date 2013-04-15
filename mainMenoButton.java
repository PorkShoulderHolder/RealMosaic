
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
public class mainMenoButton extends JButton implements ActionListener {

	
		private static final long serialVersionUID = 1L;
		private bottlecapGui gui;
		public mainMenoButton(String text,bottlecapGui gui) {
			super(text);
			this.gui = gui;
			addActionListener(this);
			this.setBackground(new Color(238,250,238));
			this.setSize(40, 20);
		}

		public void actionPerformed(ActionEvent e) {
			mousePressed();
		}

		/** This is called when this button is pressed. */
		public void mousePressed() {
			gui.makeGraph(null);
		}

	}

