/*
 * Created by JFormDesigner on Sun Oct 28 18:43:30 CET 2018
 */

package frames;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Domain.CreatedFileInfoDTO;
import observers.Observer;
import service.FileInfoService;
import websocket.WebSocketSubject;

/**
 * @author xx
 */
public class AddDialog extends JDialog implements Observer {
    private FileInfoService fileInfoService;
    public AddDialog(Frame owner) {
        super(owner);
        initComponents();
        fileInfoService =new FileInfoService();
    }

    public AddDialog(Dialog owner) {
        super(owner);
        initComponents();
    }

    private void button2ActionPerformed(ActionEvent e) {
        this.dispose();
    }

    private void button1ActionPerformed(ActionEvent e) {
        CreatedFileInfoDTO createdFileInfoDTO=new CreatedFileInfoDTO();
        createdFileInfoDTO.setName(textField1.getText());
        createdFileInfoDTO.setDescription(textField2.getText());
        createdFileInfoDTO.setContent(textArea1.getText());
        fileInfoService.addFile(createdFileInfoDTO);
        subjects.get("newFile").sendMessage("newFile");
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - xx
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        label3 = new JLabel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        button1 = new JButton();
        button2 = new JButton();

        //======== this ========
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Enter File Name");

        //---- label2 ----
        label2.setText("Enter Description");

        //---- label3 ----
        label3.setText("Enter Content");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textArea1);
        }

        //---- button1 ----
        button1.setText("Add");
        button1.addActionListener(this::button1ActionPerformed);

        //---- button2 ----
        button2.setText("Cancel");
        button2.addActionListener(e -> button2ActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(58, 58, 58)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(label1)
                        .addComponent(label2)
                        .addComponent(label3))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(textField2, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                        .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
                    .addContainerGap())
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(69, 69, 69)
                    .addComponent(button1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                    .addComponent(button2)
                    .addGap(90, 90, 90))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(48, 48, 48)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1)
                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(21, 21, 21)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label2)
                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(48, 48, 48)
                            .addComponent(label3)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(button1, GroupLayout.Alignment.TRAILING)
                        .addComponent(button2, GroupLayout.Alignment.TRAILING))
                    .addGap(33, 33, 33))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - xx
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JTextField textField2;
    private JLabel label3;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JButton button1;
    private JButton button2;

    // JFormDesigner - End of variables declaration  //GEN-END:variables

    @Override
    public void update(String type,Object message) {
    }

    @Override
    public void addSubject(String type,WebSocketSubject subject) {
        subjects.put(type,subject);
    }
}
