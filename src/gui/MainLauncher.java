/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.bulenkov.darcula.DarculaLaf;
import indy.DataArrayLists;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicLookAndFeel;
import sde.SDEController;

/**
 *
 * @author HypeSwarm
 */
public class MainLauncher {
    public static WindowLoading LOADING;
    public static SDEController SDE;
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //Set the program look and feel (AKA DARK MODE BEST MODE)
        BasicLookAndFeel darcula = new DarculaLaf();
        try {
            UIManager.setLookAndFeel(darcula);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Open loading window and load SDE and previous data
        LOADING=new WindowLoading();
        SDE=new SDEController();
        DataArrayLists.loadData();
        //Close loading window
        LOADING.dispose();
        
        //Run main program Interface
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainInterface().setVisible(true);
            }
        });
    }
    
}
