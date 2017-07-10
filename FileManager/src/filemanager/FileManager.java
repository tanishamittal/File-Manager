/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mukul Tanisha
 */
public class FileManager {

    private JPanel gui;
    private FileSystemView fsv;
    private Desktop desktop;
    private JTable table;
    private ListSelectionListener listSelectionListener;
    
    public Container getGui()
    {
        if(gui == null)
        {
            gui = new JPanel(new BorderLayout(2,2));
            gui.setBorder(new EmptyBorder(5,5,5,5));
            fsv = FileSystemView.getFileSystemView();
            desktop = Desktop.getDesktop();
            JPanel detailView = new JPanel(new BorderLayout(3,3));
            table = new JTable();
            //allows to select only one item in the table at one time
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            //automatically sorts the entries in rows 
            table.setAutoCreateRowSorter(true);
            //to disable the vertical lines from being shown
            table.setShowVerticalLines(false);
            
            listSelectionListener = new ListSelectionListener() {
                 public void valueChanged(ListSelectionEvent e) {
                     int row = table.getSelectionModel().getLeadSelectionIndex();
                     setFileDetails(((TableModel)table.getModel()).getFile(row));
                 }     
            };
           
        }
         return gui;
    }
    //function to set details of the selected file(unimplemented right now)
   private void setFileDetails(File currentFile) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
   
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }catch(Exception e){}
            JFrame frame = new JFrame("File Manager");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            FileManager fm = new FileManager();
            frame.setContentPane(fm.getGui());
            
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setPreferredSize(frame.getSize());
            frame.setVisible(true);
            }
        });
        
    }
    
}
// a TableModel to hold all the details about the files
class TableModel extends AbstractTableModel
{
     private File[] files;
    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    private String[] columns = {
        "Icon",
        "File",
        "Path/name",
        "Size",
        "Last Modified",
        "R",
        "W",
        "E",
        "D",
        "F",
    };
    
    TableModel(File[] files)
    {
        this.files = files;
    }

    @Override
    public int getRowCount() {   
        return files.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    public File getFile(int rowIndex)
    {
    return files[rowIndex];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        File currentF = files[rowIndex];
        switch(columnIndex)
        {
            case 0:
                return fileSystemView.getSystemIcon(currentF);
            case 1:
                return fileSystemView.getSystemDisplayName(currentF);
            case 2:
                return currentF.getPath();
            case 3:
                return currentF.length();
            case 4:
                return currentF.lastModified();
            case 5:
                return currentF.canRead();
            case 6:
                return currentF.canWrite();
            case 7:
                return currentF.canExecute();
            case 8:
                return currentF.isDirectory();
            case 9:
                return currentF.isFile();
                default:System.out.println("Error");
        }
        return "";
    }
    
}
