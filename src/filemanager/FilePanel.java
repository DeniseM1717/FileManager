/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.awt.Desktop;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JList;
import javax.swing.JPanel;
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


    
    public FilePanel(){
        list.setPreferredSize(new Dimension(1000,500000));
        this.setDropTarget(new MyDropTarget());
        list.setDragEnabled(true);

        list.setModel(model);
        list.addListSelectionListener(new listSelctionListener() );
        
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


    }

    public void fillList(File dir) {
        fileToName.clear();
        File[] files;
        
        files = dir.listFiles();
        model.clear();
        list.removeAll();
        for( int i = 0; i < files.length; i++){
            if( !files[i].isHidden()){
                if(files[i].isFile()){
                    fileToName.put(files[i].getName(), files[i]);
                    model.addElement(files[i].getName());
                }
            }
        }
        list.setModel(model);
    }

    private class listSelctionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
           
               Desktop desktop = Desktop.getDesktop();
               String filename = list.getSelectedValue().toString();
               
               File file = fileToName.get(filename);
            try {
                desktop.open( new File ( file.getAbsolutePath()));
            } catch (IOException ex) {
                System.out.println("Can't open file.");
                //Logger.getLogger(FilePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
               
            
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
