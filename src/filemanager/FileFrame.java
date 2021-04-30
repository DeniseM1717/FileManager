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

        this.setTitle("C:");
        this.getContentPane().add(splitpane);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setSize(800, 800);
        this.setVisible(true);
    }
}
