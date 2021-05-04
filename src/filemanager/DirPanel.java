/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.awt.Dimension;
import java.io.File;
import java.io.FileFilter;
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
    private JTree dirtree = new JTree();
    private FilePanel filepanel;
    DefaultTreeModel treemodel;

    File[] ACTIONdirs;
    File[] ACTIONfilteredDirs;
    
    
    public void setFilePanel( FilePanel fp){
        filepanel = fp;
    }
    
    public DirPanel(){        
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
    
    public void  createChildren( DefaultMutableTreeNode node){
        MyFileNode parentNode = (MyFileNode) node.getUserObject();
        File parentFile = new File( parentNode.getFile().getPath() );
        File[] parentList = parentFile.listFiles();
        
        if( parentList != null ){
            for( File file : parentList ){
                if( file.isDirectory() && file.listFiles() != null ){
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode( new MyFileNode(file.getPath()));
                    node.add(childNode);
                    File childFile = new File( file.getPath());
                    File[] childList = childFile.listFiles();
                    if( childList != null ){
                        for( File file2 : childList ){
                            if( file2.isDirectory() ){
                                DefaultMutableTreeNode secondChildNode = new DefaultMutableTreeNode( 
                                        new MyFileNode( file2.getPath()));
                                childNode.add(secondChildNode);
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    
    class MyTreeSelectionListener implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) dirtree.getLastSelectedPathComponent();
            MyFileNode mfn = (MyFileNode) node.getUserObject();
            createChildren(node);
            System.out.println(node.toString());
            System.out.println("this is path"+mfn.getFile().listFiles());
//            if(node.toString().equals("food")){
//                filepanel.fillList(new File("/Users/denisemartinez/Desktop"));
//            }
//            if(node.toString().equals("sports")){
//                filepanel.fillList(new File("/Users/denisemartinez/Desktop/CECS328"));
//            }
        }  
    }
    
    
    class treeSelectionListener implements TreeSelectionListener{

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            
            System.out.println("owo");
            System.out.println(dirtree.getMinSelectionRow());
            System.out.println(dirtree.getSelectionPath());//C:\\Node2\\SubNode0
            DefaultMutableTreeNode Emptynode = null; 

            DefaultMutableTreeNode node = (DefaultMutableTreeNode)  dirtree.getLastSelectedPathComponent();
            
            
            //PLEASE UNCOMENT THIS
            MyFileNode nfn = (MyFileNode) node.getUserObject();
            
            filepanel.fillList(nfn.getFile());
            
            ACTIONdirs = nfn.getFile().listFiles();
            System.out.println(nfn.getFile().getAbsolutePath());
            System.out.println(ACTIONdirs);
            //JOptionPane.showMessageDialog(scrollpane, nfn.getFile().getName());
            
            FileFilter directoryFilter = new FileFilter(){

                @Override
                public boolean accept(File file) {
                    // TODO Auto-generated method stub
                    return file.isDirectory();
                }
                    
            };
            
            ACTIONfilteredDirs = nfn.getFile().listFiles();
            if(nfn.isDirectory()){
                 
                try{
                    for(int k = 0; k <ACTIONdirs.length; k++){
                    if(ACTIONdirs[k].isDirectory()){
                        MyFileNode aNewFileNode = new MyFileNode(ACTIONdirs[k].getName(),ACTIONdirs[k]);

                        Emptynode = new DefaultMutableTreeNode(aNewFileNode);
                        node.add(Emptynode);

                    }
//                    MyFileNode aNewFileNode = new MyFileNode(ACTIONdirs[k].getName(),ACTIONdirs[k]);
//
//                    Emptynode = new DefaultMutableTreeNode(aNewFileNode);
//                    node.add(Emptynode);
  
                
                }
            }
            catch(Exception exc){
                System.out.println("Permisions needed to open this file");
                JOptionPane.showMessageDialog(scrollpane,exc +" \nFile Protected by System");
            }

            }


            System.out.println(node);
            //File file = new File("D:");

            //file.getAbsolutePath();//returns a string
        }


    }

}
