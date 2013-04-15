import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ljing.*;

public class bottlecapGui extends Gui {

	private Button savedImagesButton = new Button("Saved Image Sets");
	private Label instructions = new Label("\n		The Component images must be each named, or be each within a file with a name ending with _X, where X is the quantity of that \n" +
										   "object or 'pixel' ...i.e. how many objects are being represented by that image. These files must then be enclosed in the folder which \n" +
										   "is referenced using 'Component Images' The Pixel Dimensions are a proportional representation of the objects width and height, so  \n" +
										   "an entry of (1,2) is equivalent to one of (3,6). Lastly, if you choose to 'scan' your own images to derive object color, use of the \n" +
										   "'Supervised Scan' option is recommended in order to achieve the best results, especially with large projects. \n\n" +
										   "											Have Fun!");
	private Label copyright = new Label("Sam Fox Royston 2012");
	private Button newImagesButton = new Button("Process new Image set");
	private mainMenoButton mainMenuButton = new mainMenoButton("main menu",this);
	private JButton NextButton = new nextButton("Start", this);
	public colorComp C = new colorComp(null, null, 1, 1, "fake title");
	public String mainPicture;
	public String picsDirectory;
	public JTextArea area;
	public imageReader imgReader;
	public int howmanyColors = 20;
	public int howmanyBCs = 4000;
	public boolean showEachCap;
	public boolean randomize;
	public int mode = 1;
	public int tilingMode = 1;
	//JButton randomColorButton = new JButton("a random color");
	TextField count = new TextField("how many?", 10);
	TextField xTextField = new TextField("X", 4);
	TextField yTextField = new TextField("Y", 4);
	private randomsTextField randInput = new randomsTextField(this);
	private bcTextField bcInput = new bcTextField(this);
	private JButton mainPicInput = new JButton("Picture File");
	private JButton directoryInput = new JButton("Component Images");
	private JButton diagnostic = new JButton("Supervised Scan");
	private JButton tilingInfo = new JButton("Info");
	JButton randomColorButton = new JButton("A Random Selection");
	JButton eachColorRandom = new JButton("Each a Random Color");
	JButton finishButton = new JButton("Finish");
	JSlider slider = new JSlider();
	private JToggleButton toggle = new JToggleButton(Integer.toString(slider.getValue())+"random colors", false);
	private panel Panel;
	public JPanel textInputPanel = new JPanel();
	public JPanel userSelectPanel = new JPanel();
	public JPanel scanCapPanel = new JPanel();
	public JPanel rootPanel = new JPanel();
	public JPanel insetPanel = new JPanel();
	public JPanel directoryInputPanel = new JPanel();
	public JPanel fileInputPanel = new JPanel();
	public JPanel tilingPanel = new JPanel();
	private JPanel instructionsPanel = new JPanel();
	private Label imgFileName = new Label();
	private Label directoryFileName = new Label();
	private JFileChooser fileChooser = new JFileChooser();
	private  JFileChooser directoryChooser = new JFileChooser();
	private JDialog userSelect;
	private JColorChooser colorSelect = new JColorChooser();
	private  JRadioButton userSelectedMode = new JRadioButton("user choice");
	private  JRadioButton scanCapMode = new JRadioButton("scan pictures");
	private  ButtonGroup modes = new ButtonGroup();
	private JRadioButton hexagonalTiling = new JRadioButton("Hexagonal");
	private JRadioButton gridTiling = new JRadioButton("Grid");
	private  ButtonGroup tilingModes = new ButtonGroup();
	public Color color;
	public static final int USER_SELECT_MODE = 1;
	public static final int SCAN_CAP_MODE = 2;
	public static final int HEXAGONAL_MODE = 1;
	public static final int GRID_MODE = 2;
	// private ShowDialogBox dialogBox = new ShowDialogBox();
	public JOptionPane optionPane = new JOptionPane();

	public static void main(String[] args) {
		bottlecapGui gui = new bottlecapGui(null, null, null);
		// imgReader = new imageReader("/Users/Lisa/Desktop/mlk.jpg",
		// "/Users/Lisa/Desktop/pics5",gui);
		// imgReader.run();
		
		
		gui.title ="RealMosaic";
		gui.display();
		gui.initPanels(gui);
		gui.makeGraph(null);
	
		
	}

	public bottlecapGui(String string, String string2, colorComp c) {
		toggle.addItemListener(itemListener);
		mainPicInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				browseForPicture();

			}
		});
		directoryInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				browseForComponents();

			}
		});
		diagnostic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				diagnosticTest();

			}
			
		});
		finishButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			userSelect.setVisible(false);
			rootPanel.remove(userSelect);
			//eachColorRandom.setSelected(false);
			}
		});
		randomColorButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			colorSelect.setColor(randomColor());
			System.out.println("random color?"+ " " +colorSelect.getColor());
			colorSelect.repaint();
			//eachColorRandom.setSelected(false);
			}
		});
		eachColorRandom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			randomize = true;
			userSelect.setVisible(false);
			rootPanel.remove(userSelect);
			//randomColorButton.setSelected(false);
			}
		});
		tilingInfo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(rootPanel, "		Hexagonal tiling is the most compact way to pack ellipses,\n" +
													     "while a grid tiling would be appropriate for rectangular shaped \n" +
													     "pixels. If your objects are rectangular, either is ok.","Tiling Info",JOptionPane.INFORMATION_MESSAGE);
				}
			});
		this.mainPicture = string;
		this.picsDirectory = string2;
		rootPanel.setBackground(Color.white);
		scanCapPanel.setBackground(new Color(120,150,220));
		userSelectPanel.setBackground(new Color(220,150,120));
		this.setBackground(Color.white);
		this.C = c;
			//set up panel for when user selects colors
	
	}
	
	public void initPanels(Gui gui){
		rootPanel.setBounds(gui.getBounds());
		copyright.setFont(new Font("d",1,7));
		copyright.setBackground(Color.white);
		rootPanel.setVisible(true);
		insetPanel.setVisible(true);
		rootPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		finishButton.setSize(30,30);
			//set up smaller panel for taking pixel dimensions
		Border etchedBdr = BorderFactory.createEtchedBorder();
		Border titledBdr = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,3),"Pixel Dimensions");
		Border otherBdr = BorderFactory.createMatteBorder(4, 4, 4, 4, getBackground());
		textInputPanel.setBackground(new Color(170,150,170));
		fileInputPanel.setBackground(new Color(170,150,170));
		fileInputPanel.setBorder(BorderFactory.createLineBorder(Color.black,3));
		tilingPanel.setBackground(new Color(170,150,170));
		directoryInputPanel.setBackground(new Color(170,150,170));
		textInputPanel.setBorder(titledBdr);
		textInputPanel.add(xTextField);
		textInputPanel.add(yTextField);
		textInputPanel.setVisible(true);
		hexagonalTiling.setSelected(true);
		directoryInputPanel.setBorder(BorderFactory.createLineBorder(Color.black,3));
		directoryInputPanel.add(directoryInput);
		directoryFileName.setBackground(directoryInputPanel.getBackground());
		directoryInputPanel.add(directoryFileName);
		directoryInputPanel.setVisible(true);
		fileInputPanel.add(mainPicInput);
		fileInputPanel.add(imgFileName);
		imgFileName.setBackground(fileInputPanel.getBackground());
		fileInputPanel.setVisible(true);
		instructions.setBackground(Color.white);
		userSelectPanel.setBorder(etchedBdr);
		Dimension s = new Dimension((int) (rootPanel.getWidth()/1.5),rootPanel.getHeight()/3);
		System.out.println(rootPanel.getWidth()/2+"   "+rootPanel.getHeight()/3);
		scanCapPanel.setPreferredSize(s);
		scanCapPanel.setLayout(new FlowLayout(FlowLayout.CENTER));//new BoxLayout(scanCapPanel, BoxLayout.Y_AXIS));
		scanCapPanel.setBorder(etchedBdr);
		scanCapPanel.add(directoryInputPanel);
		scanCapPanel.add(diagnostic);
		scanCapPanel.setVisible(true);
		Border titledBdr2 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,3),"Instructions");
		instructionsPanel.setVisible(true);
		instructions.setBackground(new Color(238,250,238));
		instructionsPanel.setBackground(new Color(238,250, 238));
		instructionsPanel.add(instructions);
		instructionsPanel.setBorder(titledBdr2);
		userSelectPanel.setPreferredSize(s);
		userSelectPanel.add(randInput);
		
		userSelectPanel.setVisible(true);
		Border titledBdr1 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,3),"Tiling Configuration");

		tilingPanel.add(hexagonalTiling);
		tilingPanel.add(gridTiling);
		tilingPanel.add(tilingInfo);
		tilingPanel.setVisible(true);
		tilingPanel.setBorder(titledBdr1);
		hexagonalTiling.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				tilingMode = HEXAGONAL_MODE;
			}
		});
		tilingModes.add(hexagonalTiling);
		gridTiling.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				tilingMode = GRID_MODE;
			}
		});
		tilingModes.add(gridTiling);
		userSelectedMode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				mode = USER_SELECT_MODE;
				//rootPanel.removeAll();
				
				if(rootPanel.isAncestorOf(scanCapPanel)){
				rootPanel.remove(scanCapPanel);
				}
				userSelectPanel.add(NextButton);
				userSelectPanel.add(textInputPanel);
				userSelectPanel.add(tilingPanel);
				rootPanel.add(userSelectPanel);
				rootPanel.validate();
				repaint();
			}
		});
		modes.add(userSelectedMode);
		scanCapMode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				mode = SCAN_CAP_MODE;
				if(rootPanel.isAncestorOf(userSelectPanel)){
					rootPanel.remove(userSelectPanel);
					}
				scanCapPanel.add(NextButton);
				scanCapPanel.add(textInputPanel);
				scanCapPanel.add(tilingPanel);
				rootPanel.add(scanCapPanel);
				rootPanel.validate();	
				repaint();
			}
		});
		slider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				count.setText(Integer.toString(slider.getValue()));
			}
			}
		);
		modes.add(scanCapMode);
		}

	public void makeGraph(JPanel addPanel) {
		removeAll();
		rootPanel.removeAll();
		rootPanel.add(copyright);
		rootPanel.add(instructionsPanel);
		rootPanel.add(userSelectedMode);
		rootPanel.add(scanCapMode);
		rootPanel.add(fileInputPanel);
		if(addPanel!=(null)){
			rootPanel.add(addPanel);
		
		}
		mainPicInput.setBackground(Color.white);
		directoryInput.setBackground(Color.white);
		rootPanel.validate();
		add(rootPanel);
		validate();
		repaint();

	}

	public void browseForPicture() {

		fileChooser.showOpenDialog(getParent());
		imgFileName.setText(": " + fileChooser.getSelectedFile().getName());
		fileInputPanel.validate();
		validate();
		repaint();
		}
	

	public void browseForComponents() {
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		directoryChooser.showOpenDialog(getParent());
		directoryFileName.setText(": "
				+ directoryChooser.getSelectedFile().getName());
		directoryInputPanel.validate();
		validate();
		repaint();
		}

	public void diagnosticTest() {
		
		if(errorCheck()){
			return;
		}
		mainPicture = fileChooser.getSelectedFile().getPath();
		picsDirectory = directoryChooser.getSelectedFile().getPath();
		removeAll();
		System.out.println("AAAKAKAKAKAK");
		mainMenuButton.setSize(40,20);
		mainMenuButton.setBackground(new Color(238,250,238));
		
		// mainPicture = fileChooser.getSelectedFile().getPath();
		// picsDirectory = directoryChooser.getSelectedFile().getPath();
		
		showEachCap = true;
		imgReader = new imageReader(mainPicture, picsDirectory, this);
		add(mainMenuButton,getWidth()-((getWidth()-imgReader.Panel.getWidth())/2),30);
		// JOptionPane.showConfirmDialog(this,"Roseindia.net");
		imgReader.run();
		removeAll();
		imgReader.Panel.setVisible(true);
		add(imgReader.Panel);
		JInternalFrame frame = new JInternalFrame("coordinates");

		area = new JTextArea(40, 50);

		area.setMargin(new Insets(5, 5, 5, 5));
		area.setFont(new Font("monospaced", Font.PLAIN, 12));
		area.setCaretPosition(0);
		area.setDragEnabled(true);
		System.out.println(imgReader.sheet.length() + "nono");
		area.setText(imgReader.sheet);
		// area.setBounds(0, 0, 600, 600);

		frame.getContentPane().add(new JScrollPane(area));
		frame.getContentPane().setBounds(getVisibleRect());
		frame.setSize(300, 600);
		frame.setLocation(700, 100);
		frame.setVisible(true);
		mainMenuButton.setAlignmentX(CENTER_ALIGNMENT);
		mainMenuButton.setSize(40,20);
		mainMenuButton.setBackground(new Color(238,250,238));
		add(mainMenuButton,getWidth()-((getWidth()-imgReader.Panel.getWidth())/2),30);
		repaint();
	}

	public Color optionPane(Color c) {
		if (JOptionPane.showConfirmDialog(this, "Is this correct?",
				"Scan verification", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
			return JColorChooser.showDialog(this, "color editor",c);
			};
		return null;
		}
	public colorComp chooser(){ 
		randomize = false;
		slider.setMaximum(10000);
		slider.setMinimum(0);
		randomColorButton.setBounds(0,0,100,40);
		eachColorRandom.setBounds(45,0,100,40);
		JPanel panel = new JPanel();
		panel.setVisible(true);
		userSelect = new JDialog();
		userSelect.setBounds(200,200 , 500, 500);
		userSelect.setResizable(false);
		userSelect.setModal(true);
		userSelect.setContentPane(panel);
		colorSelect.setVisible(true);
		
		colorSelect.setBounds(0, 300, 300, 200);
		color = new Color (255,255,255);
		
		count.setText(Integer.toString(slider.getValue()));
		count.setBounds(20, 100, 100, 30);
		TextField title = new TextField("~object name? (optional)~", 15);
		title.setFont(new Font("dialog",1,9));
		title.setBounds(100, 100, 100, 30);
		colorSelect.setVisible(true);
		panel.add(colorSelect);
		panel.repaint();
		panel.add(count);
		panel.add(title);
		panel.add(finishButton);
		panel.add(eachColorRandom);
		panel.add(randomColorButton);
		panel.add(slider);
		userSelect.setTitle("Select attibutes");
		userSelect.setLocation(200, 200);
		userSelect.setVisible(true);
		

		
		color = colorSelect.getColor();
		colorComp c = new colorComp(color,null,0,0,title.getText());
	    c.quantity = Integer.parseInt(count.getText());
		System.out.println("finished"+" "+title.getText()+" "+color+" "+c.quantity);
		return c;
	}
	public Color randomColor(){
		Random generator = new Random();
		int r = generator.nextInt(255);
		int g = generator.nextInt(210);
		int b = generator.nextInt(255);
		return new Color(r,g,b);
	}

	public void startNext() {
		
		if(errorCheck()){
			return;
		}
		removeAll();
		showEachCap = false;
		
			mainPicture = fileChooser.getSelectedFile().getPath();
		if (mode==SCAN_CAP_MODE) {
			picsDirectory = directoryChooser.getSelectedFile().getPath();
			
			imgReader = new imageReader(mainPicture, picsDirectory, this);
			imgReader.mode = SCAN_CAP_MODE;
			imgReader.objDimensions.x =Integer.parseInt(xTextField.getText());
			imgReader.objDimensions.y =Integer.parseInt(yTextField.getText());
			imgReader.run();
			removeAll();
			imgReader.Panel.setVisible(true);
			add(imgReader.Panel);
		
			JInternalFrame frame = new JInternalFrame("coordinates");

			area = new JTextArea(40, 50);

			area.setMargin(new Insets(5, 5, 5, 5));
			area.setFont(new Font("monospaced", Font.PLAIN, 12));
			area.setCaretPosition(0);
			area.setDragEnabled(true);
			System.out.println(imgReader.sheet.length() + "nono");
			area.setText(imgReader.sheet);
			frame.getContentPane().add(new JScrollPane(area));
			frame.getContentPane().setBounds(getVisibleRect());
			frame.setSize(300, 600);
			frame.setLocation(700, 100);
			frame.setVisible(true);
		} else {
			
			howmanyColors = Integer.parseInt(randInput.getText());

			imgReader = new imageReader(mainPicture, picsDirectory, this);
			// panel Panel = new panel(1000, 1000,
			// bottlecapReader.bottlecapBounds, C);
			imgReader.mode = USER_SELECT_MODE;
			imgReader.objDimensions.x =Integer.parseInt(xTextField.getText());
			imgReader.objDimensions.y =Integer.parseInt(yTextField.getText());
			imgReader.freq = howmanyColors;
			//imgReader.bottlecaps = howmanyBCs;
			imgReader.run();
			removeAll();
			imgReader.Panel.setVisible(true);
			add(imgReader.Panel);

			JFrame frame = new JFrame("coordinates");

			area = new JTextArea(40, 50);

			area.setMargin(new Insets(5, 5, 5, 5));
			area.setFont(new Font("monospaced", Font.PLAIN, 12));
			area.setCaretPosition(0);
			area.setDragEnabled(true);
			System.out.println(imgReader.sheet.length() + "nono");
			area.setText(imgReader.sheet);
			// area.setBounds(0, 0, 600, 600);

			frame.getContentPane().add(new JScrollPane(area));
			frame.getContentPane().setBounds(getVisibleRect());
			frame.setSize(300, 600);
			frame.setLocation(700, 100);
			frame.setVisible(true);
			
		}
		mainMenuButton.setAlignmentX(CENTER_ALIGNMENT);
		mainMenuButton.setSize(40,20);
		mainMenuButton.setBackground(new Color(238,250,238));
		add(mainMenuButton,getWidth()-((getWidth()-imgReader.Panel.getWidth())/2),30);
		repaint();
	

	}

	ItemListener itemListener = new ItemListener() {
		public void itemStateChanged(ItemEvent itemEvent) {
			int state = itemEvent.getStateChange();
			if (state == ItemEvent.SELECTED) {

				//randOn = true;

				rootPanel.add(randInput, 600, 600);
				rootPanel.add(bcInput, 600, 645);
				rootPanel.remove(directoryInput);
				toggle.setText("random on");
				rootPanel.repaint();
			} else {
				//randOn = false;
				rootPanel.add(directoryInput);
				rootPanel.remove(randInput);
				rootPanel.remove(bcInput);
				toggle.setText("random off");
				rootPanel.repaint();
			}
		}
	};
	public boolean errorCheck(){
		if(ready() != 0){
			int result = ready();
			System.out.println("FAIL" + result);
			String type;
			if(result == 1){
			 type = "Error 1: Select the Main Image input.";
			}
			else if(result == 2){
			type = "Error 2: Make sure the selected file is an Image";
			}
			else if(result == 3){
				type = "Error 3: Check 'Pixel Dimensions' field";
				}
			else if(result == 4){
				type = "Error 4: Select the directory file (pixels)";
				}
			else {
				type = "Error 5: Check the '# of colors' field";
				}
			String msg = "Complete all required fields \n" + type;
			JOptionPane.showMessageDialog(this, msg, "Data Error", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		else{
			return false;
		}
	}
	
	public int ready(){
		boolean  ready = true;
		if(fileChooser.getSelectedFile()== null){
			return 1;
		}
		
		try{
		
			Image i = ImageIO.read(fileChooser.getSelectedFile());
			double ratio = (double) i.getHeight(this) / i.getWidth(this);
			
		}
		catch(java.lang.Throwable e){
			e.printStackTrace();
			return 2;
		}
		
		try{
			Integer.parseInt(xTextField.getText());
			Integer.parseInt(yTextField.getText());
			}catch(NumberFormatException e){
			return 3;
			}
		if(mode == SCAN_CAP_MODE){
			if( directoryChooser.getSelectedFile() == null
		){
				return 4;
			}
		}
		else{
			try{
				Integer.parseInt(randInput.getText());
				}catch(NumberFormatException e){
				return 5;
				}
		}
	return 0;
		}

	public void getPicsDirectory(String text) {

		picsDirectory = (text);
	}

	public void getMainPicture(String text) {
		mainPicture = (text);
	}

	public void setColor(colorComp c) {
		C = c;
	}
}
