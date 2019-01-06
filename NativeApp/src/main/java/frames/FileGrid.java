/*
 * Created by JFormDesigner on Sun Oct 28 14:41:39 CET 2018
 */

package frames;

import javax.swing.event.*;

import Domain.FileInfoGridDTO;
import Domain.Message;
import com.google.gson.Gson;
import observers.Observer;
import service.FileInfoService;
import websocket.WebSocketSubject;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;

/**
 * @author xx
 */
public class FileGrid extends JPanel implements Observer {

    String[] columnNames = {"Name",
            "Version ",
            "Modified By",
            "Modified Date",
            "Size",
            "isEdited"};
    Object[][] data;
    JButton add;
    JButton refresh;
    JCheckBox offline;
    JFrame parent;
    List<FileInfoGridDTO> response;
    Boolean opened = Boolean.FALSE;
    private FileInfoService fileInfoService;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - xx
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel panel1;
    private JPanel panel2;
    private JScrollPane scrollPane2;
    private JTextArea textArea1;
    private JPanel panel3;
    private JButton button2;
    private JTextField textField1;
    private JComboBox comboBox1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public FileGrid(JFrame parent) {
        InitWebsockets();
        this.parent = parent;
        initComponents();
        fileInfoService = new FileInfoService();
        setDataToTable();
        initMyComponents();
        System.setProperty("nickname", JOptionPane.showInputDialog(this, "Enter your nickname"));
        this.fileInfoService.addUser(System.getProperty("nickname"));
    }

    private void InitWebsockets() {
        WebSocketSubject chatWebSocketSubject = new WebSocketSubject("/chat", "/app/send/message", "chat");
        chatWebSocketSubject.attach(this);
        WebSocketSubject newFileWebSocketSubject = new WebSocketSubject("/newFile", "/app/file/create", "newFile");
        newFileWebSocketSubject.attach(this);
        WebSocketSubject fileBlockWebSocketSubject = new WebSocketSubject("/blockFile", "/app/file/block", "blockFile");
        fileBlockWebSocketSubject.attach(this);
        WebSocketSubject unblockFileWebSocketSubject = new WebSocketSubject("/unblockFile", "/app/file/unblock", "unblockFile");
        unblockFileWebSocketSubject.attach(this);

    }

    private void setDataToTable() {
        data = getAllFiles();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        for (int count = 0; count < data.length; count++) {
            model.addRow(data[count]);
        }
        table1.setModel(model);
        table1.setDefaultEditor(Object.class, null);
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (!opened) {
                        InfoDialog dialog = new InfoDialog(parent, response.get(table1.getSelectedRow()).getId());
                        subjects.get("blockFile").attach(dialog);
                        subjects.get("unblockFile").attach(dialog);
                        dialog.setVisible(true);
                        opened = Boolean.TRUE;
                        dialog.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                super.windowClosing(e);
                                setDataToTable();
                                opened = Boolean.FALSE;
                            }

                            @Override
                            public void windowDeactivated(WindowEvent e) {
                                super.windowDeactivated(e);
                                opened = Boolean.FALSE;
                                setDataToTable();
                            }
                        });
                    }
                }
            }
        });
    }

    private void initMyComponents() {
        add = new JButton();
        refresh = new JButton();
        offline = new JCheckBox();
        add.setText("Add");
        refresh.setText("Refresh");
        offline.setText("Offline");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog addDialog = new AddDialog(parent);
                ((AddDialog) addDialog).addSubject("newFile", subjects.get("newFile"));
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
        offline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileInfoService.offline = offline.isSelected();
            }
        });
        panel1.add(add, BorderLayout.WEST);
        panel1.add(refresh, BorderLayout.EAST);
        panel1.add(offline, BorderLayout.CENTER);
        button2.setEnabled(false);
    }

    private Object[][] getAllFiles() {
        response = fileInfoService.getAllFiles();
        if (response != null) {
            Object[][] responseObject = new Object[response.size()][6];
            for (int i = 0; i < response.size(); i++) {
                FileInfoGridDTO fileInfo = response.get(i);
                responseObject[i][0] = fileInfo.getName();
                responseObject[i][1] = fileInfo.getVersion();
                responseObject[i][2] = fileInfo.getModifiedBy();
                responseObject[i][3] = fileInfo.getModifiedDate();
                responseObject[i][4] = fileInfo.getSize();
                responseObject[i][5] = fileInfo.getIsEditing();
            }
            return responseObject;
        }
        return new Object[0][6];
    }

    private void button2ActionPerformed(ActionEvent e) {
        String text = textField1.getText();
        textField1.setText("");
        Message message = new Message();
        message.setTo(Objects.requireNonNull(comboBox1.getSelectedItem()).toString());
        message.setFrom(System.getProperty("nickname"));
        message.setMessage(text);
        subjects.get("chat").sendMessage(message);
    }

    private void comboBox1ActionPerformed(ActionEvent e) {
        button2.setEnabled(true);
    }

    private void comboBox1PopupMenuWillBecomeVisible(PopupMenuEvent e) {
        List<String> users = this.fileInfoService.getChatUsers();
        comboBox1.removeAllItems();
        users.forEach(s -> comboBox1.addItem(s));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - xx
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        panel1 = new JPanel();
        panel2 = new JPanel();
        scrollPane2 = new JScrollPane();
        textArea1 = new JTextArea();
        panel3 = new JPanel();
        button2 = new JButton();
        textField1 = new JTextField();
        comboBox1 = new JComboBox();

        //======== this ========

        // JFormDesigner evaluation mark
        setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                        java.awt.Color.red), getBorder()));
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                if ("border".equals(e.getPropertyName())) throw new RuntimeException();
            }
        });

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

        //======== panel2 ========
        {
            panel2.setMinimumSize(new Dimension(200, 16));
            panel2.setLayout(new BorderLayout());

            //======== scrollPane2 ========
            {

                //---- textArea1 ----
                textArea1.setMinimumSize(new Dimension(500, 768));
                textArea1.setPreferredSize(new Dimension(200, 500));
                textArea1.setEnabled(false);
                textArea1.setEditable(false);
                scrollPane2.setViewportView(textArea1);
            }
            panel2.add(scrollPane2, BorderLayout.CENTER);

            //======== panel3 ========
            {
                panel3.setLayout(new BorderLayout());

                //---- button2 ----
                button2.setText("Send");
                button2.addActionListener(e -> button2ActionPerformed(e));
                panel3.add(button2, BorderLayout.EAST);
                panel3.add(textField1, BorderLayout.CENTER);

                //---- comboBox1 ----
                comboBox1.addActionListener(e -> comboBox1ActionPerformed(e));
                comboBox1.addPopupMenuListener(new PopupMenuListener() {
                    @Override
                    public void popupMenuCanceled(PopupMenuEvent e) {
                    }

                    @Override
                    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    }

                    @Override
                    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                        comboBox1PopupMenuWillBecomeVisible(e);
                    }
                });
                panel3.add(comboBox1, BorderLayout.NORTH);
            }
            panel2.add(panel3, BorderLayout.NORTH);
        }
        add(panel2, BorderLayout.EAST);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    @Override
    public void update(String type, Object message) {

        switch (type) {
            case "chat": {
                String object = new String((byte[]) message);
                Gson g = new Gson();
                Message message1 = g.fromJson(object, Message.class);
                if (System.getProperty("nickname").equals(message1.getFrom()) || System.getProperty("nickname").equals(message1.getTo())) {
                    textArea1.append(message1.getFrom() + " [" + message1.getTime() + "]: " + message1.getMessage() + "\n");
                    DefaultCaret caret = (DefaultCaret) textArea1.getCaret();
                    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                }
                break;
            }
            default:
                setDataToTable();
                break;
        }
    }

    @Override
    public void addSubject(String type, WebSocketSubject subject) {
        subjects.put(type, subject);
    }
}
