/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

/**
 *
 * @author denisemartinez
 */
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

/**
 *
 * @author denisemartinez
 */
public class FilePanel extends JPanel {
    private JScrollPane scrollpane = new JScrollPane();
    private JTree fileTree = new JTree();
    
    public FilePanel(){
        scrollpane.setViewportView(fileTree);
        
        add(scrollpane);
    }
}
