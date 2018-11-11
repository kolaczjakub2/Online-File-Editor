/*
 * Created by JFormDesigner on Sun Oct 28 19:53:45 CET 2018
 */

package frames;

import Domain.CreatedFileInfoDTO;
import Domain.FullFileInfoDTO;
import service.FileService;

import java.awt.*;
import java.awt.event.*;
import java.util.UUID;
import javax.swing.*;

/**
 * @author xx
 */
public class InfoDialog extends JDialog {
    private FullFileInfoDTO fullFileInfoDTO;
    private FileService fileService;
    private UUID uuid;

    public InfoDialog(Frame owner, UUID uuid) {
        super(owner);
        initComponents();
        fileService=new FileService();
        this.uuid=uuid;
        initMyComponents();
    }

    private void initMyComponents() {
        fullFileInfoDTO=fileService.getFile(uuid);
        label2.setText(fullFileInfoDTO.getCreatedBy());
        label4.setText(fullFileInfoDTO.getCreatedDate().toString());
        label10.setText(fullFileInfoDTO.getModifiedBy());
        label7.setText(fullFileInfoDTO.getModifiedDate().toString());
        label9.setText(fullFileInfoDTO.getVersion().toString());
        label12.setText(fullFileInfoDTO.getSize());
        textField1.setEnabled(false);
        textField1.setText(fullFileInfoDTO.getName());
        textField2.setEnabled(false);
        textField2.setText(fullFileInfoDTO.getDescription());
        textArea1.setEnabled(false);
        textArea1.setText(fullFileInfoDTO.getContent());
    }

    public InfoDialog(Dialog owner) {
        super(owner);
        initComponents();
    }

    private void checkBox2ActionPerformed(ActionEvent e) {
        textArea1.setEnabled(checkBox2.isSelected());
    }

    private void checkBox1ActionPerformed(ActionEvent e) {
        textField2.setEnabled(checkBox1.isSelected());
    }

    private void checkBox3ActionPerformed(ActionEvent e) {
        textField1.setEnabled(checkBox3.isSelected());
    }

    private void button2ActionPerformed(ActionEvent e) {
        dispose();
    }

    private void button1ActionPerformed(ActionEvent e) {
        CreatedFileInfoDTO createdFileInfoDTO=new CreatedFileInfoDTO();
        createdFileInfoDTO.setName(textField1.getText());
        createdFileInfoDTO.setDescription(textField2.getText());
        createdFileInfoDTO.setContent(textArea1.getText());
        fullFileInfoDTO=fileService.editFile(createdFileInfoDTO,uuid);
        initMyComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - xx
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        label8 = new JLabel();
        label9 = new JLabel();
        label10 = new JLabel();
        label11 = new JLabel();
        label12 = new JLabel();
        label13 = new JLabel();
        textField1 = new JTextField();
        checkBox1 = new JCheckBox();
        label14 = new JLabel();
        textField2 = new JTextField();
        checkBox2 = new JCheckBox();
        label15 = new JLabel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        checkBox3 = new JCheckBox();
        button1 = new JButton();
        button2 = new JButton();

        //======== this ========
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Created By :");

        //---- label2 ----
        label2.setText("text");

        //---- label3 ----
        label3.setText("Created Date : ");

        //---- label4 ----
        label4.setText("text");

        //---- label5 ----
        label5.setText("Modified By :");

        //---- label6 ----
        label6.setText("Modified Date: ");

        //---- label7 ----
        label7.setText("text");

        //---- label8 ----
        label8.setText("Version : ");

        //---- label9 ----
        label9.setText("text");

        //---- label10 ----
        label10.setText("text");

        //---- label11 ----
        label11.setText("Size :");

        //---- label12 ----
        label12.setText("text");

        //---- label13 ----
        label13.setText("Enter File Name :");

        //---- checkBox1 ----
        checkBox1.setText("Edit");
        checkBox1.addActionListener(e -> checkBox1ActionPerformed(e));

        //---- label14 ----
        label14.setText("Enter File Description");

        //---- checkBox2 ----
        checkBox2.setText("Edit");
        checkBox2.addActionListener(e -> checkBox2ActionPerformed(e));

        //---- label15 ----
        label15.setText("Enter File Content");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textArea1);
        }

        //---- checkBox3 ----
        checkBox3.setText("Edit");
        checkBox3.addActionListener(e -> checkBox3ActionPerformed(e));

        //---- button1 ----
        button1.setText("Save");
        button1.addActionListener(e -> button1ActionPerformed(e));

        //---- button2 ----
        button2.setText("Cancel");
        button2.addActionListener(e -> button2ActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(31, 31, 31)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(label15, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(label14, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(label13, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label8, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(label9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label1)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label5)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(label10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label11, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(label12, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(textField2, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                                .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                                .addComponent(scrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(checkBox2)
                                .addComponent(checkBox1)
                                .addComponent(checkBox3)))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(label3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(label4, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label7, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)))))
                    .addContainerGap())
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(63, 63, 63)
                    .addComponent(button1)
                    .addGap(135, 135, 135)
                    .addComponent(button2)
                    .addGap(0, 99, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(label1)
                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label2)
                            .addComponent(label3)
                            .addComponent(label4)))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label6)
                            .addComponent(label7))
                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label5)
                            .addComponent(label10)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label8)
                        .addComponent(label9)
                        .addComponent(label11)
                        .addComponent(label12))
                    .addGap(25, 25, 25)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label13)
                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkBox3))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label14)
                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkBox1))
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label15)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(34, 34, 34)
                            .addComponent(checkBox2)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(button1)
                        .addComponent(button2))
                    .addGap(33, 33, 33))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - xx
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JLabel label11;
    private JLabel label12;
    private JLabel label13;
    private JTextField textField1;
    private JCheckBox checkBox1;
    private JLabel label14;
    private JTextField textField2;
    private JCheckBox checkBox2;
    private JLabel label15;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JCheckBox checkBox3;
    private JButton button1;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
