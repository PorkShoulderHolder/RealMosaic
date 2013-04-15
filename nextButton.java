
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
public class nextButton extends JButton implements ActionListener {

	
		private static final long serialVersionUID = 1L;
		private bottlecapGui gui;
		public nextButton(String text,bottlecapGui gui) {
			super(text);
			this.gui = gui;
			this.setSize(60, 60);
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			mousePressed();
		}

		/** This is called when this button is pressed. */
		public void mousePressed() {
			gui.startNext();
		}

	}

