/*
 *  To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author denisemartinez
 */
class App extends JFrame {
    JPanel panel, topPanel, toolbar;
    JMenuBar menubar, statusbar;
    //JToolBar toolbar;
    //JToolBar toolbar,drivebar, statusbar;
    JDesktopPane desktop;
    FileFrame fileFrame;
    //JButton simple, details;
    String currentDrive;
    
    public App(){
        
        panel = new JPanel();
        topPanel = new JPanel();
        menubar = new JMenuBar();
        desktop = new JDesktopPane();
        statusbar = new JMenuBar();
        toolbar = new JPanel();

        currentDrive = "/";
        //drivebar = new JToolBar();
        fileFrame = new FileFrame();
        }
    
    public void go(){
        //setting title to whole project frame
        this.setTitle("CECS 277 File Manager");
        
        //setting main panel
        panel.setLayout(new BorderLayout());
        
        //setting layout of the topPanel (includes the menu bar and the tool bar)
        topPanel.setLayout(new BorderLayout());
        //builds the menu and tool bar (both go into topPanel
        buildMenu();
        buildtoolbar();
        //builds statusbar which goes at bottom of main panel
        buildStatusBar();
        
        //setContentPane(desktop); idk what this does
        
        //adding topPanel to top of main panel
        panel.add(topPanel, BorderLayout.NORTH);
        //adding main panel to the main frame
        this.add(panel);
        
        //setting visibility of secondary frame true
        fileFrame.setVisible(true);
        //adding the fileFrame to the desktoppane
        desktop.add(fileFrame);
        //changing default blue color of desktop pane to white
        desktop.setBackground(Color.WHITE);
        //adding desktop pane to main panel
        panel.add(desktop, BorderLayout.CENTER);
        
        //setting size of main JFrame and setting visibility to true
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
           
    }
    
    //build status bar
    private void buildStatusBar(){
        JLabel drive = new JLabel("Current Drive: " + "Free Space: " + "Used Space: " + "Total Space: ");
        statusbar.add(drive);
        panel.add(statusbar, BorderLayout.SOUTH );
    }
    
    //build toolbar
    private void buildtoolbar(){
        // how to get drive letters
        File[] paths;
        
        paths = File.listRoots(); //static method
         
//        //creates list of files to put into the combobox
//        File file = new File("/");
//	File[] files;
//	files = file.listFiles();
//        //creates list of files to put into the combobox
//        //File file = new File("/"); //Users/denisemartinez/Desktop
////	File[] files;
////	files = file.listFiles();//File.listRoots();
////        String[] s1 = new String[files.length];
////        int i = 0;
////        for(File path: files){
////            s1[i] = path.toString();
////            i++;
////        }
//        //file.listFiles();

        //buttons details and simple
        JButton details;
        JButton simple;
        JComboBox driveSelection;
        driveSelection = new JComboBox(paths);
        details = new JButton("Details");
        simple = new JButton("Simple");
        //adding buttons to toolbar
        toolbar.add(driveSelection);
        toolbar.add(details);
        toolbar.add(simple);
        
        //adding toolbar to the bottom of the topPanel
        topPanel.add(toolbar, BorderLayout.SOUTH);
    }
    
    //building menu
    private void buildMenu() {
	//menus within menu bar
	JMenu fileMenu,treeMenu,windowMenu, helpMenu;
	fileMenu = new JMenu("File");
        treeMenu = new JMenu("Tree");
        windowMenu = new JMenu("Window");
	helpMenu = new JMenu("Help");
                
	//items within file menu
        JMenuItem rename = new JMenuItem("Rename");
	JMenuItem copy = new JMenuItem("Copy");
        JMenuItem delete = new JMenuItem("Delete");
	JMenuItem run = new JMenuItem("Run");
	JMenuItem exit = new JMenuItem("Exit");
                
        //items within tree menu
        JMenuItem expand = new JMenuItem("Expand");
	JMenuItem collapse = new JMenuItem("Collapse");
                
        //items within windowMenu
        JMenuItem newWindow = new JMenuItem("New");
	JMenuItem cascade = new JMenuItem("Cascade");
                
	//items within help
	JMenuItem help = new JMenuItem("Help");
	JMenuItem about = new JMenuItem("About");
		
	//adding actions to menu items
	run.addActionListener(new RunActionListener() );
	exit.addActionListener( new ExitActionListener() );
	about.addActionListener( new AboutActionListener() );
        help.addActionListener( new HelpActionListener() );
        newWindow.addActionListener(new NewActionListener() );
        cascade.addActionListener(new cascadeActionListener() );

        //additing items to filemenu
        fileMenu.add(rename);
        fileMenu.add(copy);
        fileMenu.add(delete);
        fileMenu.add(run);
        fileMenu.add(exit);
                
        //adding items to tree menu
        treeMenu.add(expand);
        treeMenu.add(collapse);
                
        //adding items to windowmenu
        windowMenu.add(newWindow);
        windowMenu.add(cascade);
                
        //adding items to helpMenu
        helpMenu.add(help);
        helpMenu.add(about);

        //adding menus to menu bar
        menubar.add(fileMenu);
	menubar.add(treeMenu);
        menubar.add(windowMenu);
	menubar.add(helpMenu);
                
	//adding menubar to top of topPanel
        topPanel.add(menubar, BorderLayout.NORTH);
    }

    private class cascadeActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JInternalFrame[] cascade = desktop.getAllFrames();
            int i = 10;
            int j = 10;
            for ( JInternalFrame k : cascade ){
                k.setLocation(i, j);
                i += 25;
                j += 25;
                k.moveToFront();
            }
        }   
    }

    private class NewActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FileFrame ff = new FileFrame();
            desktop.add(ff);
        }
    }
    
    private class HelpActionListener implements ActionListener {
        @Override
	public void actionPerformed(ActionEvent e) {
            HelpDlg dlg = new HelpDlg(null, true);
            dlg.setVisible(true);
	}
    }
        
    //action for exit menu item
    private class ExitActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);	
	}
    }
	
    //action for about menu item
    //brings up about dialog
    private class AboutActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

            AbtDlg dlg = new AbtDlg(null, true);
            dlg.setVisible(true);
	}
    }
    	
    private class RunActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
            if ( e.getActionCommand().equals("Run") ) {
		System.out.println("Running the program");
            }
            else {
		System.out.println("Debugging the program");
            }
			
	}
    }
}
    
    
    
    

