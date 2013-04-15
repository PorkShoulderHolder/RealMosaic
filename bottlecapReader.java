import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.lang.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Graphics;
import java.awt.event.*;
import javax.imageio.ImageIO;

public class bottlecapReader extends JPanel {

	private static final long serialVersionUID = 1L;

	public static int xGridSize = 100;
	public static int yGridSize = 100;;
	public static int bottlecaps = xGridSize * yGridSize;
	public static int X = 0;
	public static int Y = 0;

	static int row = 0;
	static int column = 0;
	static colorComp[] colorsSort = new colorComp[bottlecaps];
	static colorComp[] pixelSort = new colorComp[bottlecaps];
	static ArrayList<colorComp> colors = new ArrayList<colorComp>();
	static ArrayList<colorComp> pixelColors = new ArrayList<colorComp>();
	static ArrayList<Double> distance = new ArrayList<Double>();
	public ArrayList<Point> points = new ArrayList<Point>(); 
	public ArrayList<int[]> bottlecapBounds = new ArrayList<int[]>();
	public int period = 20;
	public double threshold = .1;
	public colorComp colorInfo;
	public  File IMG;
	private imageReader imgReader;
	public panel Panel;
	public static final int USER_SELECTED_MODE = 1;
	public static final int SCAN_CAP_MODE = 2;
	Color color;
	// colors.add()
	public bottlecapReader(File img,imageReader imgReader){
		super();
		this.IMG = img;
		this.imgReader = imgReader;
		this.setBounds(0, 0, 300, 300);
		this.setBackground(Color.white);
		this.setVisible(true);
		Border lineBdr = BorderFactory.createLineBorder(color.black,3);
		Border titledBdr = BorderFactory.createTitledBorder(lineBdr, IMG.getName());
		this.setBorder(titledBdr);
	
		//panel Panel = new panel(1200, 1200, imgReader);
		//this.imgReader.gui.removeAll();
		//imgReader.gui.add(this);
		//this.add(Panel);
	}
	
	public  colorComp scanCap(){
		
		
		 //Get the jvm heap size.
		        long heapSize = Runtime.getRuntime().totalMemory();
		        
		        //Print the jvm heap size.
		       // System.out.println("Heap Size = " + heapSize);
		ArrayList <int[]> boundsTemp = new ArrayList<int[]>();
		int red = 0;
		int green = 0;
		int blue = 0;
		int count = 0;
		try {
			BufferedImage img = ImageIO.read(IMG);
			//StdDraw.clear();
			int maxDim = java.lang.Math.max(img.getHeight(), img.getWidth());

			int[] rgb;
			Queue<Double> movingAvg = new Queue<Double>();
			double movingAvgSum = 0.0;

			for (int j = 0; j < img.getHeight(); j++) {
				movingAvgSum = 0.0;
				for (int i = 0; i < period; i++) {
					if (!movingAvg.isEmpty()) {
						movingAvg.dequeue();
					}

				}
				for (int i = 0; i < img.getWidth(); i++) {

					rgb = getPixelData(img, i, j);
					if (movingAvg.size() >= period) {
						movingAvgSum -= movingAvg.dequeue();
					}
					movingAvgSum += (double) Color.RGBtoHSB(rgb[0], rgb[1],
							rgb[2], null)[2];
					movingAvg.enqueue((double) Color.RGBtoHSB(rgb[0], rgb[1],
							rgb[2], null)[2]);

					if (java.lang.Math.abs(stdDev(movingAvg,
							(double) movingAvgSum / movingAvg.size())) > threshold
							&& stdDev(movingAvg, (double) movingAvgSum
									/ movingAvg.size()) != Double.NaN) {
						int[] arr = { i, j };

						boundsTemp.add(arr);
						break;

					}
				}
				movingAvgSum = 0.0;
				for (int i = 0; i < period; i++) {
					if (!movingAvg.isEmpty()) {
						movingAvg.dequeue();
					}

				}
				for (int i = img.getWidth() - 1; i >= 0; i--) {

					rgb = getPixelData(img, i, j);
					if (movingAvg.size() >= period) {
						movingAvgSum -= movingAvg.dequeue();
					}
					movingAvgSum += (double) Color.RGBtoHSB(rgb[0], rgb[1],
							rgb[2], null)[2];
					movingAvg.enqueue((double) Color.RGBtoHSB(rgb[0], rgb[1],
							rgb[2], null)[2]);

					if (java.lang.Math.abs(stdDev(movingAvg,
							(double) movingAvgSum / movingAvg.size())) > threshold
							&& stdDev(movingAvg, (double) movingAvgSum
									/ movingAvg.size()) != Double.NaN) {
						int[] arr = { i, j };
						boundsTemp.add(arr);
						break;

					}
				}

				// blue + " " + pixelHeight * pixelWidth);
			}
			int x1 = 0;
			int x2 = 0;
			int y1 = 0;
			int y2 = 0;
			int t = 0;
			bottlecapBounds = boundsTemp;
			// / StdDraw.
			for (int[] point : bottlecapBounds) {
				if(t<bottlecapBounds.size()-1){
				t++;
				}
				 x1 = point[0];
				 x2 = bottlecapBounds.get(t)[0];
				 y1 = point[1];
				 y2 = bottlecapBounds.get(t)[1];
				if (y1 == y2) {
					System.out.println(count);
					while (x1 < x2) {
						points.add(new Point(x1,y1));
						rgb = getPixelData(img, x1, y1);
						red += rgb[0];
						green += rgb[1];
						blue += rgb[2];
						count++;
						x1++;
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (count != 0) {
			red /= count;
			green /= count;
			blue /= count;
			
		}
	 color = new Color(red, green, blue);
	 System.out.println(color);
		int frequency = 0;
		String frqString = IMG.getParent();

		int indexBegin = (-1);
		int indexEnd = (-1);
		for (int k = 1; k < frqString.length(); k++) {
			String digit = frqString.substring(k - 1, k);
			if (digit.equals("_")) {

				indexBegin = k;
				//System.out.println("1");
			} else if (digit.equals(".")) {
				indexEnd = k;

				//System.out.println("2");
				break;

			}
			if (k == frqString.length() - 1 && indexBegin != -1) {
				indexEnd = frqString.length();
				//System.out.println("3");

			} else if (k == frqString.length() - 1 && indexEnd == -1
					&& indexBegin == -1) {
				frqString = IMG.getName();
				k = 0;
				//System.out.println("4");
				// break;
			}
		}

		frequency = Integer.parseInt(frqString.substring(indexBegin, indexEnd));
		//System.out.println(" : " + frequency);
		//System.out.println("read frequency :"+frequency);
		colorInfo = new colorComp(color, null, frequency, count,
				IMG.getName());
		System.out.println(colorInfo.title + "  " + color);
		
		int timer = 2;
	
		//(int) ((java.lang.Math.log(frequency)+1)*100));
		
		if(imgReader.gui.showEachCap==true){
	
		Border ipTitle = BorderFactory.createTitledBorder(IMG.getName());
		Border dpTitle = BorderFactory.createTitledBorder("Percieved Shape");
		Border cspTitle = BorderFactory.createTitledBorder("Percieved Color");
		imgReader.gui.removeAll();
		imgReader.gui.add(this);
		imgReader.gui.add(new detectionPanel());
		imgReader.gui.add(new swatchPanel());
		imgReader.gui.repaint();
		
		}
		
		return colorInfo;
	}
	
	public class detectionPanel extends JPanel{
		public detectionPanel(){
			this.setBounds(0,300,300,300);
			Border lineBdr = BorderFactory.createLineBorder(Color.black,3);
			Border titledBdr = BorderFactory.createTitledBorder(lineBdr, "Perceived Image");
			this.setBorder(titledBdr);
		}
		public void paintComponent(Graphics g){
			Image img = null;
			
			try {
				img = ImageIO.read(IMG);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int count = 0;
			for (Point point : points) {
				g.setColor(new Color(count*255/points.size(),count*255/points.size(),count*255/points.size()));
				g.drawRect(point.x*getHeight()/img.getWidth(this),point.y*getHeight()/img.getHeight(this),1,1);
				System.out.print("dd");
				count++;
			}
			for (int[] point : bottlecapBounds) {
				g.setColor(Color.RED);
				g.drawRect(point[0]*getHeight()/img.getWidth(this),point[1]*getHeight()/img.getHeight(this),3,3);
				System.out.print("dd");
				
			}
		}
	}
	public class swatchPanel extends JPanel{
		public swatchPanel(){
			this.setBounds(300,0,300,300);
			Border lineBdr = BorderFactory.createLineBorder(Color.black,3);
			Border titledBdr = BorderFactory.createTitledBorder(lineBdr, "Perceived Color");
			this.setBorder(titledBdr);
		}
		public void paintComponent(Graphics g){
			g.setColor(color);
			if(imgReader.objIsEllipsoid){
			g.fillOval(50, 50, 200, 200);
		}
			else{
			g.fillRoundRect(50, 50, 200, 200, 10, 10);
			}
			}
	}
	
	public void paintComponent(Graphics g){
		g.clearRect(0, 0, 300, 300);
		g.setColor(Color.white);
		g.fillRect(0, 0, 300, 300);
		int count = 0;
		Image img = null;
		
		try {
			img = ImageIO.read(IMG);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		g.drawImage(img, 20, 20, 200, 200, this);
	}
	

	private static double stdDev(Queue<Double> data, double movingAvgSum) {
		// sd is sqrt of sum of (values-mean) squared divided by n - 1
		// Calculate the mean
		Iterator<Double> iterator = data.iterator();
		double mean = movingAvgSum;
		final int n = data.size();
		if (n < 2) {
			return Double.NaN;
		}

		// calculate the sum of squares
		double sum = 0;
		while (iterator.hasNext()) {
			final double v = (Double) iterator.next() - mean;
			sum += v * v;

		}
		// Change to ( n - 1 ) to n if you have complete data instead of a
		// sample.
		return Math.sqrt(sum / (n - 1));
	}

	private static int[] getPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);
		int rgb[] = new int[] { (argb >> 16) & 0xff, // red
				(argb >> 8) & 0xff, // green
				(argb) & 0xff // blue
		};

		// System.out.println("rgb: " + rgb[0] + " " + rgb[1] + " " + rgb[2]);
		return rgb;

	}


}
