/*
 *  To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

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
    JComboBox driveSelection;

    
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
        buildStatusBar("/");
                
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
    private void buildStatusBar( String currentDrive){
        statusbar.removeAll();
        File file = new File(currentDrive);
        //1024 is used to convert to GB
        int freeSpace = (int) (file.getUsableSpace()/(1024 * 1024 * 1024));
        int totalSpace = (int) (file.getTotalSpace()/(1024 * 1024 * 1024));
        int usedSpace = totalSpace - freeSpace;
        
        JLabel statusBarInfo = new JLabel("Current Drive: " + 
                currentDrive + " Free Space: " + freeSpace + "GB" 
                        + " Used Space: " + usedSpace + "GB" 
                                + " Total Space: " + totalSpace + "GB");
        statusbar.add(statusBarInfo);
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
        driveSelection = new JComboBox(paths);
        details = new JButton("Details");
        simple = new JButton("Simple");
        
        details.addActionListener(new DetailsSimpleActionListener() );
        simple.addActionListener( new DetailsSimpleActionListener() );
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
        expand.addActionListener(new ExpandCollapseListener() );
        collapse.addActionListener(new ExpandCollapseListener() );
        run.addActionListener( new RunActionListener()  );
        copy.addActionListener( new CopyActionListener() );
        delete.addActionListener(new DeleteActionListener() );
        rename.addActionListener( new RenameActionListener() );

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

    private class CopyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println( "You copied a file.");
            FileFrame active = (FileFrame) desktop.getSelectedFrame();
            DataBack dbdlg = new DataBack(null, true);
            int indexFile = active.filepanel.list.getSelectedIndex();
            File file = active.filepanel.filesArray.get(indexFile);
            dbdlg.setFromField(file.getParent());
            dbdlg.setTitle("Copy");
            dbdlg.setVisible(true);
            //String filedir = file.getParent();
            String tofield = dbdlg.getToField();
            
            //System.out.println("ToField is " + filedir);
        }

    }

    private class RenameActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FileFrame active = (FileFrame) desktop.getSelectedFrame();
            DataBack dbdlg = new DataBack(null, true);
            int indexFile = active.filepanel.list.getSelectedIndex();
            File file = active.filepanel.filesArray.get(indexFile);
            dbdlg.setFromField(file.getName());
            dbdlg.setTitle("Rename");
            dbdlg.setVisible(true);
            String tofield = dbdlg.getToField();
            
            if( active.filepanel.details ){
                long lsize = file.length();
                DecimalFormat dformat = new DecimalFormat("#,###");
                String size = dformat.format(lsize);
                        
                Date lastModified = new Date(file.lastModified());
                String fileName = dbdlg.getToField(); 
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String date = formatter.format(lastModified);
                String detailedFile = fileName + "     Date Modified: "+date +"    " +"Size: "+size;
                active.filepanel.model.setElementAt(detailedFile, indexFile);
                        
            }
            else{
                active.filepanel.model.setElementAt(tofield, indexFile);
                System.out.println("ToField is " + tofield);

            }
        }
    }

    private class DetailsSimpleActionListener implements ActionListener {
        @Override
            public void actionPerformed(ActionEvent e){
                String event = e.getActionCommand();
                FileFrame active = (FileFrame) desktop.getSelectedFrame();
                if(active == null){
                    return;
                }
                if(event.equals("Details")){
                      System.out.println("You pressed details.");
                      active.filepanel.details = true;
                      DefaultMutableTreeNode node = (DefaultMutableTreeNode)  active.dirpanel.getDirTree().getLastSelectedPathComponent();
                      MyFileNode nfn = (MyFileNode) node.getUserObject();
                      active.filepanel.fillList(nfn.getFile());
                    }
                
                if(event.equals("Simple")){
                      System.out.println("You pressed simple.");
                      active.filepanel.details = false;
                      DefaultMutableTreeNode node = (DefaultMutableTreeNode)  active.dirpanel.getDirTree().getLastSelectedPathComponent();
                      MyFileNode nfn = (MyFileNode) node.getUserObject();
                      active.filepanel.fillList(nfn.getFile());
                    }
                }
            }


    

    public class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            FileFrame active = (FileFrame) desktop.getSelectedFrame();
            DeleteDLG deleteDialog = new DeleteDLG(null, true);
            deleteDialog.setVisible(true);
            if (deleteDialog.delete){
                if ( active.filepanel.list != null){
                    //File filename = (File) active.filepanel.list.getSelectedValue();
                    int indexFile = active.filepanel.list.getSelectedIndex();
                    active.filepanel.filesArray.remove(indexFile);
                    active.filepanel.model.removeElement(active.filepanel.list.getSelectedValue());
                }
            }   
        }       
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
            FileFrame active = (FileFrame) desktop.getSelectedFrame();
            //FileFrame active = fileFrame;
            if ( active.filepanel.list != null){
                String filename = active.filepanel.list.getSelectedValue().toString();
                int indexFile = active.filepanel.list.getSelectedIndex();
                File file = active.filepanel.filesArray.get(indexFile);
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open( new File ( file.getAbsolutePath()));
            } catch (IOException ex) {
                System.out.println("Can't open file.");
                //Logger.getLogger(FilePanel.class.getName()).log(Level.SEVERE, null, ex);
            }  
            }
	}
    }
 
    
    public class toolbarBoxAction implements ActionListener{  
        @Override
        public void actionPerformed(ActionEvent e){
            String s = (String) driveSelection.getSelectedItem();
            System.out.println("you selected " + s);
            buildStatusBar(s);
            panel.revalidate();
        }
    }
    
     public class ExpandCollapseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileFrame active = (FileFrame) desktop.getSelectedFrame();
            if(active ==null){
                return;
            }
            JTree temp = active.dirpanel.getDirTree();
            int row = temp.getMinSelectionRow();
            if (e.getActionCommand().equals("Expand")&& temp.isCollapsed(row))
                temp.expandRow(row);
            if (e.getActionCommand().equals("Collapse")&& temp.isExpanded(row))
                temp.collapseRow(row);
        }
    }
}
    
    
    
    

