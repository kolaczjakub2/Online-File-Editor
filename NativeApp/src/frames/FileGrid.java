/*
 * Created by JFormDesigner on Sun Oct 28 14:41:39 CET 2018
 */

package frames;

import Domain.FileInfoGridDTO;
import service.FileService;


import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * @author xx
 */
public class FileGrid extends JPanel {

    String[] columnNames = {"Name",
            "Version ",
            "Modified By",
            "Modified Date",
            "Size"};
    Object[][] data;
    JButton add;
    JButton refresh;
    JFrame parent;
    List<FileInfoGridDTO> response;
    private FileService fileService;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - xx
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel panel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    public FileGrid(JFrame parent) {
        this.parent = parent;
        initComponents();
        fileService = new FileService();
        setDataToTable();
        initMyComponents();
    }

    private void setDataToTable() {
        System.out.println("set data to table");
        data = getAllFiles();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        for (int count = 0; count < data.length; count++) {
            model.addRow(data[count]);
        }
        table1.setModel(model);
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JDialog dialog = new InfoDialog(parent, response.get(table1.getSelectedRow()).getId());
                    dialog.setVisible(true);
                    dialog.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            super.windowClosing(e);
                            setDataToTable();
                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {
                            super.windowDeactivated(e);
                            setDataToTable();
                        }
                    });
                }
            }
        });
    }

    private void initMyComponents() {
        add = new JButton();
        refresh = new JButton();
        add.setText("Add");
        refresh.setText("Refresh");

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog addDialog = new AddDialog(parent);
                addDialog.setVisible(true);
                addDialog.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            super.windowClosing(e);
                            setDataToTable();
                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {
                            super.windowDeactivated(e);
                            setDataToTable();
                        }
                });
            }
        });
        refresh.addActionListener(e -> setDataToTable());
        panel1.add(add, BorderLayout.WEST);
        panel1.add(refresh, BorderLayout.EAST);

    }

    private Object[][] getAllFiles() {
        response = fileService.getAllFiles();
        Object[][] responseObject = new Object[response.size()][5];
        for (int i = 0; i < response.size(); i++) {
            FileInfoGridDTO fileInfo = response.get(i);
            responseObject[i][0] = fileInfo.getName();
            responseObject[i][1] = fileInfo.getVersion();
            responseObject[i][2] = fileInfo.getModifiedBy();
            responseObject[i][3] = fileInfo.getModifiedDate();
            responseObject[i][4] = fileInfo.getSize();
        }
        return responseObject;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - xx
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        panel1 = new JPanel();

        //======== this ========

        // JFormDesigner evaluation mark
        setBorder(new javax.swing.border.CompoundBorder(
            new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

        setLayout(new BorderLayout());

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(table1);
        }
        add(scrollPane1, BorderLayout.CENTER);

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout());
        }
        add(panel1, BorderLayout.SOUTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

}
