package frames;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.GroupLayout;
import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Sat Jan 28 13:36:43 CET 2017
 */



/**
 * @author Jakub KoÅacz
 */
public class MainFrame extends JFrame {
    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - xx
        panel1 = new FileGrid(this);

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(panel1, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - xx
    private FileGrid panel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
