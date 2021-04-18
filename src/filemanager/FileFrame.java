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
    
    JSplitPane splitpane;
    public FileFrame(){
        splitpane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, new DirPanel(), new FilePanel() );
        
        this.setTitle("C:");
        this.getContentPane().add(splitpane);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setSize(700, 500);
        this.setVisible(true);
    }
}
