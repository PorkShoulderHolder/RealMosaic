
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
public class savedImagesButton extends JButton implements ActionListener {

	
		private static final long serialVersionUID = 1L;

		public savedImagesButton(String text) {
			super(text);
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			mousePressed();
		}

		/** This is called when this button is pressed. */
		public void mousePressed() {
			
		}

	}


