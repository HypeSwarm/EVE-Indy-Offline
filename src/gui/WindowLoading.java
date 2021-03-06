/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.JRootPane;
import javax.swing.JScrollBar;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 *
 * @author HypeSwarm
 */
public class WindowLoading extends javax.swing.JFrame {

    /**
     * Creates new form Loading
     */
    public WindowLoading() {
        super.setUndecorated(true);
        super.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        initComponents();
        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        loadingSDEText = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        sdeLoadingBar = new javax.swing.JProgressBar();
        overallLoadingBar = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setAutoscrolls(true);

        loadingSDEText.setEditable(false);
        loadingSDEText.setColumns(20);
        loadingSDEText.setRows(5);
        loadingSDEText.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        loadingSDEText.setEnabled(false);
        jScrollPane1.setViewportView(loadingSDEText);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Loading SDE Database Files");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        sdeLoadingBar.setBackground(new java.awt.Color(0, 0, 0));
        sdeLoadingBar.setForeground(new java.awt.Color(0, 0, 153));
        sdeLoadingBar.setStringPainted(true);

        overallLoadingBar.setBackground(new java.awt.Color(0, 0, 0));
        overallLoadingBar.setForeground(new java.awt.Color(0, 0, 153));
        overallLoadingBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        overallLoadingBar.setStringPainted(true);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Overall Progess");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Current SDE File Progress");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(overallLoadingBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sdeLoadingBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sdeLoadingBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(overallLoadingBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea loadingSDEText;
    private javax.swing.JProgressBar overallLoadingBar;
    private javax.swing.JProgressBar sdeLoadingBar;
    // End of variables declaration//GEN-END:variables
    
    private double pLast;
    private double pOverall;
    public void updateLoadingBars(double percent){
        percent=percent*100;
        percent=(double)Math.round(percent);
        sdeLoadingBar.setValue((int)Math.round(percent/100));
        sdeLoadingBar.setString(percent/100+"%");
        if(pLast>percent/100){
            pOverall+=percent/100/8;
        }else{
            pOverall+=(percent/100-pLast)/8;
        }
        overallLoadingBar.setValue((int)pOverall);
        overallLoadingBar.setString(((double)Math.round(pOverall*100))/100+"%");
        pLast=percent/100;
    }
    
    public void addLoadingText(String text){
        addLoadingText(text,true);
    }
    
    public void addLoadingText(String text,boolean newLine){
        if(newLine){
            loadingSDEText.append("\n"+text);
        }else{
            try{
                Document doc = loadingSDEText.getDocument();
                Element root = doc.getDefaultRootElement().getElement(loadingSDEText.getLineCount()-1);
                doc.remove(root.getStartOffset(),root.getEndOffset()-root.getStartOffset()-1);
                doc.insertString(root.getStartOffset(), text, null);
            }catch(BadLocationException e){}
        }
        JScrollBar vertical = jScrollPane1.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }
}
