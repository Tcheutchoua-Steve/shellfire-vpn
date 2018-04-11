/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PremiumVPNNagScreen.java
 *
 * Created on 13.07.2011, 13:44:49
 */
package de.shellfire.vpn.gui;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.xnap.commons.i18n.I18n;

import de.shellfire.vpn.Util;
import de.shellfire.vpn.gui.helper.MoveMouseListener;
import de.shellfire.vpn.i18n.Language;
import de.shellfire.vpn.i18n.VpnI18N;

/**
 * 
 * @author bettmenn
 */
public class LicenseAcceptScreen extends javax.swing.JDialog {
  private static Logger log = Util.getLogger(LicenseAcceptScreen.class.getCanonicalName());
  private final LoginForm aparent;
  private MoveMouseListener mml;

  /** Creates new form PremiumVPNNagScreen */
  public LicenseAcceptScreen(java.awt.Frame parent, boolean modal, ActionListener al) {
    super(parent, modal);
    aparent = (LoginForm) parent;
    initComponents();

    this.setLocationRelativeTo(null);
    if (al != null)
      jAbortButton.addActionListener(al);

    this.initLicense();
    this.enableMouseMoveListener();
    this.pack();

  }


  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method
   * is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jHeaderPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jContentPanel = new javax.swing.JPanel();
        jLoginPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jAbortButton = new javax.swing.JButton();
        jAcceptButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Shellfire VPN 2"); // NOI18N
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jHeaderPanel.setBackground(new java.awt.Color(18, 172, 229));
        jHeaderPanel.setName("jHeaderPanel"); // NOI18N
        jHeaderPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(ShellfireVPNMainForm.getLogo());
        jLabel6.setAlignmentY(0.0F);
        jLabel6.setName("jLabel6"); // NOI18N
        jHeaderPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 100));

        jContentPanel.setBackground(new java.awt.Color(64, 69, 73));
        jContentPanel.setName("jContentPanel"); // NOI18N
        jContentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLoginPanel.setName("jLoginPanel"); // NOI18N
        jLoginPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel1.setText(i18n.tr("Remaining waiting time:"));
        jLabel1.setName("jLabel1"); // NOI18N
        jLoginPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 470, 157, -1));

        jAbortButton.setFont(new java.awt.Font("Tahoma", 0, 14));
        jAbortButton.setText(i18n.tr("Reject"));
        jAbortButton.setName("jAbortButton"); // NOI18N
        jAbortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAbortButtonActionPerformed(evt);
            }
        });
        jLoginPanel.add(jAbortButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 267, 25));

        jAcceptButton.setFont(new java.awt.Font("Tahoma", 0, 14));
        jAcceptButton.setText(i18n.tr("Accept"));
        jAcceptButton.setName("jAcceptButton"); // NOI18N
        jAcceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAcceptButtonActionPerformed(evt);
            }
        });
        jLoginPanel.add(jAcceptButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 430, 267, 25));

        jScrollPane1.setBackground(new java.awt.Color(18, 172, 229));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(19, 20));
        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(450, 400));

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        jLoginPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 570, -1));

        jContentPanel.add(jLoginPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 610, 460));

        jHeaderPanel.add(jContentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 650, 500));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 24));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("<html>"+i18n.tr("Accept licence")+"</html>");
        jLabel2.setName("jLabel2"); // NOI18N
        jHeaderPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 340, 80));

        getContentPane().add(jHeaderPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 3, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jAcceptButtonActionPerformed(java.awt.event.ActionEvent evt) {
  aparent.licenseAccepted();
    cleanRemove();
  }
    
  
  private void jAbortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAbortButtonActionPerformed
    aparent.licenseNotAccepted();
    cleanRemove();
  }//GEN-LAST:event_jAbortButtonActionPerformed

  // GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAbortButton;
    private javax.swing.JButton jAcceptButton;
    private javax.swing.JPanel jContentPanel;
    private javax.swing.JPanel jHeaderPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jLoginPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
  private static I18n i18n = VpnI18N.getI18n();


  void cleanRemove() {
    this.dispose();
    aparent.repaint();
  }


  private void enableMouseMoveListener() {

    if (mml == null)
      mml = new MoveMouseListener(this);

    this.addMouseListener(mml);
    this.addMouseMotionListener(mml);

  }

    private void initLicense() {
        Language lang = VpnI18N.getLanguage();
        String name = "de";
        if (lang != null && lang.getName() != null)
            name = lang.getKey();
        
        log.debug("Name: " +name);
        String text = "License";
        String filename = com.apple.eio.FileManager.getPathToApplicationBundle() + "/Contents/Java/openvpn/license_" + name + ".txt";
        try {
        	text = Util.fileToString(filename);
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
        }
        
        jTextArea1.setText(text);
        jTextArea1.setCaretPosition(0);            
                
    }
}
