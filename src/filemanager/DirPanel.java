/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author denisemartinez
 */
public class DirPanel extends JPanel {
    private JScrollPane scrollpane = new JScrollPane();
    private JTree dirtree = new JTree();
    private FilePanel filepanel;
    
    public void setFilePanel( FilePanel fp){
        filepanel = fp;
    }
    
    public DirPanel(){
        dirtree.addTreeSelectionListener(new MyTreeSelectionListener());
        scrollpane.setViewportView(dirtree);
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
        
        add(scrollpane);
    }
    
    class MyTreeSelectionListener implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            System.out.println(e.getPath());
            
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    dirtree.getLastSelectedPathComponent();
            System.out.println(node.toString());
            if(node.toString().equals("food")){
                filepanel.fillList(new File("/Users/denisemartinez/Desktop"));
            }
            if(node.toString().equals("sports")){
                filepanel.fillList(new File("/Users/denisemartinez/Desktop/CECS328"));
            }

        }
        
    }
}
