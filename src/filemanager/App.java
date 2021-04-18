/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
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
    JPanel panel, topPanel;
    JMenuBar menubar, statusbar;
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


        //toolbar = new JToolBar();
        //drivebar = new JToolBar();
        fileFrame = new FileFrame();
        }
    
    public void go(){
        this.setTitle("CECS 277 File Manager");
        panel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        buildMenu();
        buildStatusBar();
        //setContentPane(desktop);
        topPanel.add(menubar, BorderLayout.NORTH);
        panel.add(topPanel, BorderLayout.NORTH);
        this.add(panel);
        fileFrame.setVisible(true);
        desktop.add(fileFrame);
        
        desktop.setBackground(Color.WHITE);
        panel.add(desktop, BorderLayout.CENTER);
        //buildtoolbar();
        //topPanel.add(toolbar, BorderLayout.SOUTH);
        currentDrive = "C:";
        //panel.add(statusbar, BorderLayout.SOUTH);
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
		//adding menubar to JPanel on the north side
		panel.add(menubar, BorderLayout.NORTH);
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

			AboutDlg dlg = new AboutDlg();
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
    
    
    
    

