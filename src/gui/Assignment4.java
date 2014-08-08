package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/*
 * PURPOSE: A visualisation of the factory.
 * 
 * 			This is the Applet class.  There is no main method 
 * 			using an applet so this class launches the program.
 *
 */

public class Assignment4 extends JApplet{
	public static int width = 1000;
	public static int height = 600;
	public static int panelwidth = 150;
	
	// variables for double buffering
	private Image dbImage; 
	private Graphics dbg; 
		
	// add this as a member variable
	private JButton newMachineBtn;
	private JButton deleteBtn;
	private JButton newConveyerBtn;
	private JRadioButton machineSelection[];
	private ButtonGroup machineselectgroup;
	
	// the menu items from http://java.sun.com/docs/books/tutorial/uiswing/components/menu.html
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem;
	
	// where the data is stored
	Model model;
	
	/*
	 * This overrides a Java method.  It is called by the framework to initialize the applets
	 * 
	 * Its often unsafe to initialise GUI elements until the framework is ready
	 */
	public void init() {
		this.setSize(width,height);
		
		setLayout(null);
		
		// call all our initialisation
		createbuttons();
		addListeners();
		createMenu();

		model = new Model();
		
		//this is for drawing the orders on the conveyer belt
		int delay = 250; //milliseconds
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.incCurrTime();
				repaint();
			}
		};
		new Timer(delay, taskPerformer).start();
		

		//loadFactory();
		//model.linkMachines();
		repaint();

		
		
	}
	
	/*
	 * Create the menu for the save and loading
	 */
	public void createMenu() {
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("File Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Save file",
                KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		KeyEvent.VK_S, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
		"Saves the data");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Saving file");
				saveFactory();
			}
		});
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Load file",
                KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		KeyEvent.VK_L, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
		"Saves the data");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Load file");
//				loadFactory();
			}
		});
		menu.add(menuItem);
		this.setJMenuBar(menuBar);
	}
	
	
	/*
	 * Add all the listeners here, this is called by the init
	 */
	public void addListeners() {
		// setup the listener for all mouse clicks
		this.addMouseListener(new MouseAdapter () {
			public void mouseReleased(MouseEvent evt) {	
				model.mouseReleased(evt);			
				repaint();
				
		}
     	});	
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent evt) {
				model.onMouseMove(evt);
		    	repaint();
		}		
		});
		
		// this part was an extra, to add the escape to remove the current controller
		addKeyListener(new java.awt.event.KeyAdapter() {
			 public void keyPressed(KeyEvent e) {					
			 }
			 public void keyReleased(KeyEvent e) {	
				 if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {	
					 model.onCancelController();					
					}			
				
			 }
			 public void keyTyped(KeyEvent e) {
				
			 }
		});	
		


	}
	
	/*
	 * Create all the buttons
	 * 
	 */
	public void createbuttons() {
		// the new machine button
		newMachineBtn = new JButton("new machine");	
		newMachineBtn.setBounds(10,10,120,20);
		add(newMachineBtn);		
		
		// the radio group to select which new machine is to be created
		machineselectgroup = new ButtonGroup();		
		machineSelection = new JRadioButton[5];
		String machinelut[] = {"PCBMachine","BatteryMachine","ScreenMachine","AssemblyMachine","DeliveryMachine"};
		for (int ix = 0; ix < 5; ix++) {
			machineSelection[ix] = new JRadioButton(machinelut[ix]);
			machineSelection[ix].setBounds(20,40+ix*30,129,20);
			machineselectgroup.add(machineSelection[ix]);	
			add(machineSelection[ix]);			
		}		
		machineselectgroup.setSelected(machineSelection[0].getModel(),true);
		
		deleteBtn = new JButton("delete machine");	
		deleteBtn.setFont(deleteBtn.getFont().deriveFont(10.0f));
		deleteBtn.setBounds(10,190,120,20);
		add(deleteBtn);		
		
		newConveyerBtn = new JButton("new Conveyer");	
		newConveyerBtn.setFont(deleteBtn.getFont().deriveFont(10.0f));
		newConveyerBtn.setBounds(10,220,120,20);
		add(newConveyerBtn);		
			
		
		// when "new machine" is clicked, check which radio button is selected
		//    and do that action
		ActionListener onClicknm = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  String name = "";		      
		    	 
		    	  for (int ix = 0; ix < 5; ix++) {
		    		  if (machineSelection[ix].isSelected()) {
		    			  name = machineSelection[ix].getText();		    			 
		    		  }
		    	  }				    	  
		    	  model.onNewMachine(name);		    	  
		    	  System.out.println(name);		    	
		      }
		};		
		newMachineBtn.addActionListener(onClicknm);		
		
		ActionListener onClickdel = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	model.onDeleteObject();  
		    	repaint();
		      }
		};		
		deleteBtn.addActionListener(onClickdel);		
		
		ActionListener onClickconveyer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	model.onNewConveyer();  
		    	repaint();
		      }
		};		
		newConveyerBtn.addActionListener(onClickconveyer);	
	}
	
	public void paint(Graphics g) {
		// Initialise buffer 
		requestFocus();
		
		// create the double buffer if its the first time
		if (dbImage == null) 
		{ 
			// create the double buffer here
			dbImage = createImage (this.getSize().width, this.getSize().height); 		
			dbg = dbImage.getGraphics(); 	
		} 
	
		// clear the double buffer in background 
		dbg.setColor (getBackground ()); 
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height); 		
		dbg.setColor (getForeground()); 		
		
		// do the normal paint stuff here
		super.paint(dbg);		

		// do our rendering here
		render(dbg);
		
		// draw image on the screen 
		g.drawImage (dbImage, 0, 0, this); 		
	
	}
	
	// our drawing will go here not in the paint
	public void render(Graphics g) {	
		// draw the border to make it clear where machines can be placed	
		model.render(g);
	}
	
	
	// called by the menu to create a dialog box to save the factory.
	  public void saveFactory() {
	    	final JFileChooser fc = new JFileChooser();
	    	
	    	fc.setDialogType(JFileChooser.SAVE_DIALOG);
	    	//In response to a button click:
	    	int returnVal = fc.showSaveDialog(this);
	    	
	    	if (returnVal == JFileChooser.APPROVE_OPTION)  {
	    		 File file = fc.getSelectedFile();
	    	
	    		  String objectdata = model.getSaveString();
	    		 try {
	    		 FileWriter out = new FileWriter (file);
	    		 out.write(objectdata,0,objectdata.length());
	    		 out.close();
	    		 } catch(Exception e) {
	    			 System.err.println(e);
	    		 }    		     		
	    	}
	    	 repaint();
		}
		


	
}
