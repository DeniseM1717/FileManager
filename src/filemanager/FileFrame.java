/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;

/**
 *
 * @author denisemartinez
 */
public class FileFrame extends JInternalFrame {
    //static String currentSelected;
    JSplitPane splitpane;
    FileFrame() { //App app
        

        DirPanel dirpanel = new DirPanel();
        FilePanel filepanel = new FilePanel();
        dirpanel.setFilePanel(filepanel);
        
        
        splitpane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, dirpanel, filepanel );
        splitpane.setOneTouchExpandable(true);
        splitpane.setResizeWeight(.3);
        splitpane.getLeftComponent().setSize((int)(splitpane.getWidth()*.5), splitpane.getHeight());

        this.setTitle("/");
        this.getContentPane().add(splitpane);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setSize(500, 500);
        this.setResizable(true);
        this.setVisible(true);
            }
}
