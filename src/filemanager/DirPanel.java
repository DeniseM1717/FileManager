/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

/**
 *
 * @author denisemartinez
 */
public class DirPanel extends JPanel {
    private JScrollPane scrollpane = new JScrollPane();
    private JTree dirtree = new JTree();
    
    public DirPanel(){
        scrollpane.setViewportView(dirtree);
        
        add(scrollpane);
    }
}
