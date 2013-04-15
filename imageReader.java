import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.color.*;
import java.util.*;
import java.lang.Math;
import java.sql.*;

public class imageReader extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String IMG = "/Users/Lisa/Desktop/mlk.jpg";
	public String imgsDirectory = "/Users/Lisa/Desktop/Bottlecap3";
	// public static final String file = "/Users/Lisa/Desktop/pics.folder";
	// public static int count = 0;

	public int bottlecaps;
	
	public int X = 40;
	public int Y = 0;
	public int numberOfBottlecaps;
	public int row = 0;
	public int mode;
	public bottlecapReader BCReader;
	public int column = 0;
	public boolean fileFound;
	public boolean fileShouldUpdate;
	public bottlecapGui gui;
	public Object colors1 = new ArrayList<colorComp>();
	public ArrayList<colorComp> colors = new ArrayList<colorComp>();
	public ArrayList<colorComp> pixelColors = new ArrayList<colorComp>();
	public ArrayList<Double> distance = new ArrayList<Double>();
	public String sheet = new String();
	public int freq = 10;
	public static Calendar date = Calendar.getInstance();
	public static String fileNames = date.get(Calendar.MONTH) + "-"
			+ date.get(Calendar.DATE) + "-" + date.get(Calendar.YEAR) + ": ";
	public pane Panel = new pane(gui);
	public Point objDimensions = new Point();
	public boolean objIsEllipsoid;
	public static final int USER_SELECT_MODE = 1;
	public static final int SCAN_CAP_MODE = 2;

	public imageReader(String imageName, String imageDirectoryName,
			bottlecapGui gui) {

		this.gui = gui;
		this.IMG = imageName;
		this.imgsDirectory = imageDirectoryName;
		this.mode = gui.mode;
		this.setBackground(Color.white);
		this.objDimensions = new Point(Integer.parseInt(gui.xTextField.getText()), Integer.parseInt(gui.yTextField.getText()));
		this.objIsEllipsoid = (gui.tilingMode == bottlecapGui.HEXAGONAL_MODE);
		// if(xGridSize = java.lang.Math.max(xGridSize, yGridsize))

		// panel e = new panel(1200, 1200);
		// this.add(e);

	}

	public static void main(String[] args) {
		imageReader i = new imageReader(
				"/Users/Lisa/Desktop/Picasso_selfport1907.jpg" + "",
				"/Users/Lisa/Dropbox/Bottlecaps/Bottlecaps14", null);
		i.run();
	}

	@SuppressWarnings("unchecked")
	public void run() {

		// DL main image

		File f = null;
		if (mode == SCAN_CAP_MODE) {
			System.out.println("RIGHT!!! SCAN CAP ON");
			f = new File(imgsDirectory);
			colors = colors(f);
		}

		else {
			System.out.println("UH OH SCAN CAP OFF");
			for (int i = 0; i < freq; i++) {
				colorComp c = gui.chooser();
				if(gui.randomize){
					Random generator = new Random();
					for (int j = 0; j < c.quantity; j++) {
						int r = generator.nextInt(255);
						int g = generator.nextInt(210);
						int b = generator.nextInt(255);
						Color color = new Color(r,g,b);
						colorComp c1 = new colorComp(color, null, j, j, Integer.toString(color.getRed())+","+Integer.toString(color.getGreen())+","+Integer.toString(color.getBlue()));
						c1.quantity = 1;
						System.out.println("RANDOOOMMEMEOMEOMEOEEOMEOM" + c1.color + "  "+c.quantity);
						colors.add(c1);
					}
				}
				else{
					for (int j = 0; j < c.quantity; j++) {
						char s = c.title.charAt(0);
						if(Character.toString(s).equals("~"))
						{
						c.title = Integer.toString(c.color.getRed())+","+Integer.toString(c.color.getGreen())+","+Integer.toString(c.color.getRed()); 
						}
						colors.add(c);
					}
				}
				
			}
		}

		bottlecaps = colors.size();

		Collections.sort(colors, colorComp.YComparator);
		// colors.add()

		BufferedImage img;

		try {
			System.out.println("RATIOUS" + " : " + (double) objDimensions.y
					/ objDimensions.x);
			img = ImageIO.read(new File(IMG));
			double ratio = (double) img.getHeight() / img.getWidth();
			int yGridSize;
			int xGridSize;
			
			yGridSize = (int) (java.lang.Math.sqrt((double) bottlecaps) * ratio);
			
			xGridSize = bottlecaps / yGridSize;
			
			xGridSize *= java.lang.Math.sqrt(((double) objDimensions.y)/(double)objDimensions.x);
			yGridSize *= java.lang.Math.sqrt(((double) objDimensions.x)/(double)objDimensions.y);
			System.out.println("0?  "+ xGridSize);
			// yGridSize =(int) (ratio * xGridSize);
			int[] rgb;
			int pixelWidth = img.getWidth() / xGridSize;
			int pixelHeight = img.getHeight() / yGridSize;
			if (pixelWidth == 0) {
				pixelWidth = 1;
			}
			if (pixelHeight == 0) {
				pixelHeight = 1;
			}
			int red = 0;
			int green = 0;
			int blue = 0;
			int count = 0;
			while (Y < yGridSize) {

				X = 0;
				int space = 0;
				if (Y % 2 == 1 && objIsEllipsoid) {
					space = pixelWidth / 2;
				}
				while (X < xGridSize) {
					for (int j = Y * pixelHeight; j < (Y + 1) * pixelHeight; j++) {
						for (int i = (X * pixelWidth) + space; i < ((X + 1) * pixelWidth)
								+ space; i++) {
							if (i >= img.getWidth()) {
								break;
							}
							rgb = getPixelData(img, i, j);
							red += rgb[0];
							green += rgb[1];
							blue += rgb[2];
						}
						// blue + " " + pixelHeight * pixelWidth);
					}
					red = red / (pixelHeight * pixelWidth);
					green = green / (pixelHeight * pixelWidth);
					blue = blue / (pixelHeight * pixelWidth);
					count++;
					double[] rect = new double[4];
					int maxDim = java.lang.Math.max(xGridSize, yGridSize);
					int minDim = java.lang.Math.min(xGridSize, yGridSize);
					double coef = 0;
					pane p = new pane(gui);
					if (((double) objDimensions.x / objDimensions.y) > 1) {
						System.out.println("x wider");
						coef = (double) objDimensions.x / objDimensions.y;
						rect[0] =  X*coef*p.getWidth() / (double)maxDim + (double) space*p.getWidth()
								/ img.getWidth();
						rect[1] =   Y*p.getHeight() /(double) maxDim;
						rect[2] = ( 1*p.getHeight()  /(double) maxDim);
						rect[3] = ( 1*p.getWidth()  /(double) maxDim) * coef;
					} else {
						System.out.println("y wider" + this.getHeight());
						coef = (double) objDimensions.y / objDimensions.x;
						rect[0] = (double) X*p.getWidth()  / (double)maxDim + (double) space *p.getWidth()
								/ img.getWidth();
						rect[1] = ((double) Y*p.getHeight()  / (double)maxDim) *coef  ;
						rect[2] = ((double) 1*p.getHeight()  / (double)maxDim)* coef;
						rect[3] = ((double) 1*p.getWidth() /(double) maxDim);
					}

					colorComp ColorComp = new colorComp(new Color(red, green,
							blue), rect, X, Y, null);
					pixelColors.add(ColorComp);
					red = 0;
					green = 0;
					blue = 0;
					X++;
				}
				Y++;
			}
			int x = 0;
			Collections.sort(pixelColors, colorComp.YComparator);
			while (x < colors.size() && x < pixelColors.size()) {
				colorComp c = colors.get(x);
				colorComp p = pixelColors.get(x);
				p.title = c.title;
				p.color = c.color;

				x++;
			}

			Panel.repaint();
			// add(Panel);
			repaint();

			Collections.sort(pixelColors, colorComp.coordinateComparator);
			x = 0;
			while (x < pixelColors.size()) {
				colorComp c = pixelColors.get(x);
				// \System.out.println(c.x + ", " + c.y + " : " + c.title);
				Object[] info = new Object[3];
				info[0] = c.x;
				info[1] = c.y;
				info[2] = c.title;
				sheet = sheet.concat(Integer.toString(c.x) + ", "
						+ Integer.toString(c.y) + " :" + c.title + "\n");
				x++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (mode == 3) {
			try {

				String string = f.getName();
				string.concat(".sav");
				FileOutputStream saveFile = new FileOutputStream(string);

				// Create an ObjectOutputStream to put objects into save file.
				ObjectOutputStream save = new ObjectOutputStream(saveFile);

				// save.writeChars(fileNames);
				save.writeObject(pixelColors);
				save.close();
				System.out.println("saved");
			}
			// If there was an error, print the info.
			catch (Exception exc) {

				exc.printStackTrace();
			}
		}

		return;
		// save File

		// bottlecapReader bko = new bottlecapReader();
	}

	public class pane extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Dimension size = new Dimension(720,720);
		// private bottlecapGui gui;
		public pane(bottlecapGui gui) {
			this.setBackground(Color.white);
			this.setSize(size.width,size.height);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.white);
			g.fillRect(0,0,1000,1000);
			int i = 0;
			Collections.sort(pixelColors, colorComp.YComparator);
			Collections.sort(colors, colorComp.YComparator);
			int hmax = 0;
			int wmax = 0;
			while (i < colors.size() && i < pixelColors.size()) {
				colorComp c = colors.get(i);
				colorComp p = pixelColors.get(i);
				p.title = c.title;
				p.color = c.color;
				g.setColor(c.color);
				int x = (int) (pixelColors.get(i).rect[0]);
				int y = (int) (pixelColors.get(i).rect[1]);
				int h = (int) (pixelColors.get(i).rect[2]);
				int w = (int) (pixelColors.get(i).rect[3]);
				wmax = java.lang.Math.max(wmax, x);
				hmax = java.lang.Math.max(hmax, y);
				System.out.println(x+" "+y+" "+w+" "+h+" "+hmax+"  "+this.getHeight());
				if (objIsEllipsoid) {
					g.fillOval(x, y, w, h);
					
				} else {
				
					g.fillRect(x, y, w, h);
				}
				i++;
			}
			double scale = 0;
			
			if(hmax>this.getHeight()||wmax>this.getWidth()){
				g.clearRect(getBounds().x,getBounds().y,getWidth(),getHeight());
				g.setColor(Color.white);
				g.fillRect(0,0,1000,1000);
				if(hmax>this.getHeight()){
					scale = (double)this.getHeight()/hmax;
				}
				else{
					scale = (double)this.getWidth()/wmax;
				}
				i = 0;
			while (i < colors.size() && i < pixelColors.size()) {
				colorComp c = colors.get(i);
				colorComp p = pixelColors.get(i);
				p.title = c.title;
				p.color = c.color;
				g.setColor(c.color);
				int x = (int) (pixelColors.get(i).rect[0]*scale);
				int y = (int) (pixelColors.get(i).rect[1]*scale);
				int h = (int) (pixelColors.get(i).rect[2]*scale);
				int w = (int) (pixelColors.get(i).rect[3]*scale);
		
				if (objIsEllipsoid) {
					g.fillOval(x, y, w, h);
					
				} else {
					// g.fillRoundRect(x, y, w, h, 3, 3);
					g.fillRect(x, y, w, h);
				}
				System.out.println("fsfdfdsono "+x+" "+y+" "+w+" "+h+" "+hmax+"  "+wmax);
				
				
				// g.fillRect(x,y,w,h);
				// this.repaint(x, y, w, h);

				i++;
			}
			}
		}
	}

	private ArrayList<colorComp> colors(File dir) {
		ArrayList<colorComp> array = new ArrayList<colorComp>();
		int x = 0;
		if (dir.isFile()) {

			fileNames.concat(dir.getPath() + ",");
			BCReader = new bottlecapReader(dir, this);
			colorComp c = BCReader.scanCap();
			
			if (gui.showEachCap) {
			
				Color col = gui.optionPane(c.color);
				if (col != null) {
					c.color = col;
				}
						}
			for (int i = 0; i < c.x; i++) {
				array.add(c);
				x++;
			}
			return array;
		}

		else {
			// System.out.println("MMKKKKMKKKK " + dir.listFiles().length);
			for (int t = 1; t < dir.listFiles().length; t++) {
				if (dir.listFiles()[t] != null
						&& !dir.listFiles()[t].equals(null)) {
					File count = dir.listFiles()[t];

					System.out.println("frequency :" + count.getName());
					if (count.isFile()) {
						BCReader = new bottlecapReader(count, this);
						colorComp c = BCReader.scanCap();
					
						if (gui.showEachCap) {
						
							Color col = gui.optionPane(c.color);
							if (col != null) {
								c.color = col;
							}
							
						}
						if (gui != null) {
							gui.C = c;
						}

						// System.out.println("total pics :" + t);
						for (int i = 0; i < c.x; i++) {
							System.out.println();
							array.add(c);
							x++;
						}
					}

					else {
						int end = count.listFiles().length;
						int begin = 1;
						if (count.listFiles().length == 1) {
							begin = 0;
						}
						for (int t1 = begin; t1 < end; t1++) {

							{
								File img = count.listFiles()[t1];
								if (img != null && !img.equals(null)) {
									fileNames.concat(img.getPath() + ",");
									BCReader = new bottlecapReader(img, this);
									colorComp c = BCReader.scanCap();
								
									if (gui.showEachCap) {
										Color col = gui.optionPane(c.color);
										if (col != null) {
											c.color = col;
										}
											}
									System.out.println("number to add :" + c.x);
									// if (gui.equals(null)) {

									// gui.C = c;
									// }

									for (int i = 0; i < c.x; i++) {
										array.add(c);
										x++;
									}
								}
							}
						}
					}
				}
			}
		}
		// System.out.println("array size :" + array.size());
		return array;
	}

	// TO DO: make method go one directory deeper to get to image, after you
	// have made sufficient changes in bottlecapReader
	private void classify(int r, int g, int b, int x, int y) {
		double[] differences = new double[colors.size()];
		int t = 0;
		while (t < colors.size()) {
			float H = Color.RGBtoHSB(r, g, b, null)[0];
			float S = Color.RGBtoHSB(r, g, b, null)[1];
			float B = Color.RGBtoHSB(r, g, b, null)[2];
			float h = Color.RGBtoHSB(colors.get(t).color.getRed(),
					colors.get(t).color.getGreen(),
					colors.get(t).color.getBlue(), null)[0];
			float k = Color.RGBtoHSB(colors.get(t).color.getRed(),
					colors.get(t).color.getGreen(),
					colors.get(t).color.getBlue(), null)[1];
			float i = Color.RGBtoHSB(colors.get(t).color.getRed(),
					colors.get(t).color.getGreen(),
					colors.get(t).color.getBlue(), null)[2];
			differences[t] = (((h - H) * (h - H)) + ((k - S) * (k - S)) + ((i - B) * (i - B)));
			if (differences[t] < differences[0]) {
				differences[0] = differences[t];
				colorComp c = colors.get(0);
				colors.set(0, colors.get(t));
				colors.set(t, c);
			}
			t++;
		}
		// return differences[0];

	};

	public static double distance(Color C1, Color C2) {
		double difference;

		if (C1 == null || C2 == null) {
			return Double.MAX_VALUE;
		}
		float H = Color
				.RGBtoHSB(C1.getRed(), C1.getGreen(), C1.getBlue(), null)[0];
		float S = Color
				.RGBtoHSB(C1.getRed(), C1.getGreen(), C1.getBlue(), null)[1];
		float B = Color
				.RGBtoHSB(C1.getRed(), C1.getGreen(), C1.getBlue(), null)[2];
		float h = Color
				.RGBtoHSB(C2.getRed(), C2.getGreen(), C2.getBlue(), null)[0];
		float k = Color
				.RGBtoHSB(C2.getRed(), C2.getGreen(), C2.getBlue(), null)[1];
		float i = Color
				.RGBtoHSB(C2.getRed(), C2.getGreen(), C2.getBlue(), null)[2];
		difference = (((h - H) * (h - H)) + ((k - S) * (k - S)) + ((i - B) * (i - B)));
		return difference;
	};

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
