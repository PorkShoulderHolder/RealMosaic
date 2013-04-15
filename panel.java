import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;
import java.util.*;

	public class panel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public ArrayList<int[]> bottlecapBounds =new ArrayList<int[]>();
		public colorComp colorInfo = new colorComp(Color.white, null, 1, 1, "placeholder");
		
		panel(int x, int y, imageReader ImageReader) {
			this.setSize(x, y);
			this.setDoubleBuffered(true);
			try{
			this.bottlecapBounds = ImageReader.BCReader.bottlecapBounds;
			this.colorInfo = ImageReader.BCReader.colorInfo;
			}
			catch(NullPointerException e){
				
			}
		}

		@Override
		public void paint(Graphics G) {
			int maxDim = java.lang.Math.max(this.getSize().height,
					this.getSize().width);
			//Image image = null;
			/*try {
				image = ImageIO.read(IMG);
								
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};*/
			G.clearRect(0, 0,this.getSize().width,
					this.getSize().height);
			for (int[] point : bottlecapBounds) {
				G.setColor(Color.black);
				G.fillRect(point[0],this.getSize().height - 300*point[1], 1, 1);
				
			}
			//G.drawImage(image, 0, 0, 200, 200, 0, 0, image.getWidth(this),image.getHeight(this), this);

			G.setColor(colorInfo.color);
			G.fillOval(400, 100, 170, 170);
			
			//image.flush();
			
			//image = null;

			Toolkit.getDefaultToolkit().sync();
			G.dispose();
			
		}

	}
