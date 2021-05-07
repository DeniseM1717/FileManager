/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.awt.Dimension;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author denisemartinez
 */
public class DirPanel extends JPanel {
    private JScrollPane scrollpane = new JScrollPane();
    private JTree dirtree;
    private FilePanel filepanel;
    DefaultTreeModel treemodel;

    File[] dirFiles;
    File[] filterDirFiles;
    
    
    public JTree getDirTree(){
        return dirtree;
    }
    public void setFilePanel( FilePanel fp){
        filepanel = fp;
    }
    
    public DirPanel(){
        dirtree = new JTree();
        dirtree.setPreferredSize(new Dimension(1000,500000));             
        dirtree.addTreeSelectionListener(new treeSelectionListener());
        loadTree("/");
        this.add(dirtree);
        
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
        //scrollpane.createVerticalScrollBar();
        add(scrollpane);
    }

    private void loadTree(String fileName) {
        MyFileNode rootFile = new MyFileNode(fileName);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootFile);
        DefaultTreeModel model = new DefaultTreeModel(root);
        
        DefaultMutableTreeNode dir = null;
        File[] dirs = rootFile.getFile().listFiles();
        for( int i = 0; i < dirs.length; i++){
            if(dirs[i].isDirectory() && dirs[i].listFiles() != null ){
                //for debugging
                System.out.println(dirs[i].getAbsolutePath());
                dir = new DefaultMutableTreeNode(new MyFileNode(dirs[i].getName(), dirs[i]));
                root.add(dir);
                
                File[] dirChildren = dirs[i].listFiles();
                for ( File file : dirChildren ){
                    if( file.isDirectory() && file.listFiles() != null){
                        //for debugging
                        System.out.println(file.getAbsolutePath());
                        DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode( new MyFileNode(file.getName(), file) );
                        dir.add(fileNode);
                    }
                }
            }
        }
        dirtree.setModel(model);
    }
    
   
    class treeSelectionListener implements TreeSelectionListener{

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            
            System.out.println(dirtree.getSelectionPath());
            DefaultMutableTreeNode Emptynode = null; 
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)  dirtree.getLastSelectedPathComponent();
            MyFileNode nfn = (MyFileNode) node.getUserObject();
            filepanel.fillList(nfn.getFile());
            dirFiles = nfn.getFile().listFiles();
            System.out.println(nfn.getFile().getAbsolutePath());
            System.out.println(dirFiles);            
            if(nfn.isDirectory()){
                try{
                    for(int k = 0; k <dirFiles.length; k++){
                    if(dirFiles[k].isDirectory()){
                        MyFileNode aNewFileNode = new MyFileNode(dirFiles[k].getName(),dirFiles[k]);
                        Emptynode = new DefaultMutableTreeNode(aNewFileNode);
                        node.add(Emptynode);
                    }
                }
            }
            catch(Exception exc){
                System.out.println("Permisions needed to open this file");
                JOptionPane.showMessageDialog(scrollpane,exc +" \nFile Protected by System");
            }
            }
            System.out.println(node);
        }
    }
}
