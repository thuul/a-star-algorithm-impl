/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.test;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import local.ikram.test.sp.TilePathGraph;

/**
 *
 * @author walle
 */
public class Main {

    private static JFrame form;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        TilePathGraph.newInstance();

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        EventQueue.invokeLater(() -> {
            form = new MainForm();
            form.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            form.setLocation(0, 0);
            form.setAlwaysOnTop(false);
            form.setExtendedState(JFrame.MAXIMIZED_BOTH);
            form.setTitle("ASSESMENT - A-Star Algorithm Test Engine");
            form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            form.setVisible(true);
        });
    }

}
