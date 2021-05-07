/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

//import filemanager.App.DeleteActionListener;
import java.awt.Desktop;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import java.awt.Dimension;
import static java.awt.SystemColor.desktop;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 *
 * @author Dr. Hoffman
 */

public class FilePanel extends JPanel {
    //private JScrollPane scrollpane = new JScrollPane();
    //private JTree filetree = new JTree();
    
    JList list = new JList();
    DefaultListModel model = new DefaultListModel();
    private JScrollPane scrollpane = new JScrollPane();
    HashMap<String, File> fileToName = new HashMap<String, File>();
    Boolean details = false;
    ArrayList<File> filesArray = new ArrayList<>();


    
    public FilePanel(){
        list.setPreferredSize(new Dimension(1000,500000));
        this.setDropTarget(new MyDropTarget());
        list.setDragEnabled(true);

        list.setModel(model);
        //list.addListSelectionListener(new listSelctionListener() );
        
        add(list);
        scrollpane.setViewportView(list);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        scrollpane.createVerticalScrollBar();
        add(scrollpane);

            MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){

                    String selectedFile = (String) list.getSelectedValue();
                    System.out.println("SELECTED FILE IS NOW "+ selectedFile.toString());

                    String[] splited = selectedFile.split("    ");
                    System.out.println("MESSAGE FROM FILE PANEL! OUR SELECTED FILE IS " + splited[0] );
                }
                if (e.getClickCount() == 2) {
    
                    
                   Desktop desktop = Desktop.getDesktop();
               String listSelection = list.getSelectedValue().toString();
               int indexSelectedValue = list.getSelectedIndex();
               File file = filesArray.get(indexSelectedValue);
               System.out.println(listSelection);
               String filename = null;
               
               System.out.println(filename);
               //File file = fileToName.get(filename);
            try {
                desktop.open( new File ( file.getAbsolutePath()));
            } catch (IOException ex) {
                System.out.println("Can't open file.");
                //Logger.getLogger(FilePanel.class.getName()).log(Level.SEVERE, null, ex);
            }  


        
                }
                if (e.getButton() == MouseEvent.BUTTON3){

                    popMenu ourPopMenu;

                    try{

                        ourPopMenu = new popMenu();
                        ourPopMenu.show(e.getComponent(), e.getX(), e.getY());


                    }
                    catch (Exception ex){}

                }
            }
        };
        list.addMouseListener(mouseListener);

    }

    public void fillList(File dir) {
        //fileToName.clear();
        File[] files;
        
        files = dir.listFiles();
        model.clear();
        list.removeAll();
        filesArray.clear();
        for( int i = 0; i < files.length; i++){
            if( !files[i].isHidden()){
                if(files[i].isFile()){
                    if( details ){
                        long lsize = files[i].length();
                        DecimalFormat dformat = new DecimalFormat("#,###");
                        String size = dformat.format(lsize);
                        
                        Date lastModified = new Date(files[i].lastModified());
                        String fileName = files[i].getName();
                        //fileName = String.format("%-" + 40 + "s", fileName);
                        //size = String.format("Size: " + "%" + 12 + "s", size);
                        
                        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                        String date = formatter.format(lastModified);
                        //return fileName + date + size;
                        
                        fileToName.put(fileName, files[i]);
                        model.addElement(fileName + "     Date Modified: "+date +"    " +"Size: "+size);
                        filesArray.add(files[i]);
                        //fileToName.put(files[i].getName(), files[i]);
                        //model.addElement(files[i].getName());
                    }
                    else{
                        //fileToName.put(files[i].getName(), files[i]);
                        model.addElement(files[i].getName());
                        filesArray.add(files[i]);
                    }
                }
            }
        }
        list.setModel(model);
    }

    private  class RenameActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DataBack dbdlg = new DataBack(null, true);
            int indexFile = list.getSelectedIndex();
            File file =filesArray.get(indexFile);
            dbdlg.setFromField(file.getName());
            dbdlg.setTitle("Rename");
            dbdlg.setVisible(true);
            String tofield = dbdlg.getToField();
            
            if( details ){
                long lsize = file.length();
                DecimalFormat dformat = new DecimalFormat("#,###");
                String size = dformat.format(lsize);
                        
                Date lastModified = new Date(file.lastModified());
                String fileName = dbdlg.getToField(); 
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String date = formatter.format(lastModified);
                String detailedFile = fileName + "     Date Modified: "+date +"    " +"Size: "+size;
                model.setElementAt(detailedFile, indexFile);
                        
            }
            else{
                model.setElementAt(tofield, indexFile);
                System.out.println("ToField is " + tofield);

            }
        }

        
    }

    private  class CopyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println( "You copied a file.");
            DataBack dbdlg = new DataBack(null, true);
            int indexFile = list.getSelectedIndex();
            File file = filesArray.get(indexFile);
            dbdlg.setFromField(file.getParent());
            dbdlg.setTitle("Copy");
            dbdlg.setVisible(true);
            //String filedir = file.getParent();
            String tofield = dbdlg.getToField();
            
            //System.out.println("ToField is " + filedir);
        }

       
    }

    private  class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DeleteDLG deleteDialog = new DeleteDLG(null, true);
            deleteDialog.setVisible(true);
            if (deleteDialog.delete){
                if ( list != null){
                    //File filename = (File) active.filepanel.list.getSelectedValue();
                    int indexFile = list.getSelectedIndex();
                    filesArray.remove(indexFile);
                    model.removeElement(list.getSelectedValue());
                    
                }
            }
        } 
    }

    private class listSelctionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
           
               Desktop desktop = Desktop.getDesktop();
               String listSelection = list.getSelectedValue().toString();
               int indexSelectedValue = list.getSelectedIndex();
               File file = filesArray.get(indexSelectedValue);
               System.out.println(listSelection);
               String filename = null;
               
               System.out.println(filename);
               //File file = fileToName.get(filename);
            try {
                desktop.open( new File ( file.getAbsolutePath()));
            } catch (IOException ex) {
                System.out.println("Can't open file.");
                //Logger.getLogger(FilePanel.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
    }
    

    private class popMenu extends JPopupMenu{

        JMenuItem rename  = new JMenuItem("Rename");
        JMenuItem copy  = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem delete = new JMenuItem("Delete");

        public popMenu(){

            add(rename);
            add(copy);
            this.addSeparator();
            add(delete);
            delete.addActionListener( new DeleteActionListener() );
            copy.addActionListener( new CopyActionListener() );
            rename.addActionListener( new RenameActionListener() );

        }



    }
 
    /*************************************************************************
     * class MyDropTarget handles the dropping of files onto its owner
     * (whatever JList it is assigned to). As written, it can process two
     * types: strings and files (String, File). The String type is necessary
     * to process internal source drops from another FilePanel object. The
     * File type is necessary to process drops from external sources such 
     * as Windows Explorer or IOS.
     * 
     * Note: no code is provided that actually copies files to the target
     * directory. Also, you may need to adjust this code if your list model
     * is not the default model. JList assumes a toString operation is
     * defined for whatever class is used.
     */
    class MyDropTarget extends DropTarget {
    /**************************************************************************
     * 
     * @param evt the event that caused this drop operation to be invoked
     */    
        public void drop(DropTargetDropEvent evt){
            try {
                //types of events accepted
                evt.acceptDrop(DnDConstants.ACTION_COPY);
                //storage to hold the drop data for processing
                List result = new ArrayList();
                //what is being dropped? First, Strings are processed
                if(evt.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor)){     
                    String temp = (String)evt.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    //String events are concatenated if more than one list item 
                    //selected in the source. The strings are separated by
                    //newline characters. Use split to break the string into
                    //individual file names and store in String[]
                    String[] next = temp.split("\\n");
                    //add the strings to the listmodel
                    for(int i=0; i<next.length;i++)
                        model.addElement(next[i]); 
                }
                else{ //then if not String, Files are assumed
                    result =(List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    //process the input
                    for(Object o : result){
                        System.out.println(o.toString());
                        model.addElement(o.toString());
                    }
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
    }

}
