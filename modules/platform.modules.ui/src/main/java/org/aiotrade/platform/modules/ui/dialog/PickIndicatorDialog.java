/*
 * Copyright (c) 2006-2007, AIOTrade Computing Co. and Contributors
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *    
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *    
 *  o Neither the name of AIOTrade Computing Co. nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *    
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.aiotrade.platform.modules.ui.dialog;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.aiotrade.lib.charting.view.ChartViewContainer;
import org.aiotrade.lib.math.timeseries.MasterTSer;
import org.aiotrade.lib.math.timeseries.TUnit$Day$;
import org.aiotrade.lib.math.timeseries.computable.Indicator;
import org.aiotrade.lib.securities.PersistenceManager$;
import org.aiotrade.platform.modules.ui.netbeans.windows.AnalysisChartTopComponent;
import scala.collection.mutable.Map;

/**
 *
 * @author Caoyuan Deng
 * 
 * @credits 
 *     msayag@users.sourceforge.net - fix: should put a default option value to 
 * resultMap to avoid null value when dialog is closed by click x
 */
public class PickIndicatorDialog extends javax.swing.JDialog {

    private Map<String, Object> nameMapResult;

    /** Creates new form PickIndicatorDialog */
    public PickIndicatorDialog(java.awt.Frame parent, boolean modal, Map<String, Object> nameMapResult) {
        super(parent, modal);
        initComponents();

        this.nameMapResult = nameMapResult;

        scala.collection.Iterator<Indicator> indicators = PersistenceManager$.MODULE$.apply().lookupAllRegisteredServices(Indicator.class, "Indicators").iterator();
        List<Indicator> inds = new ArrayList<Indicator>();
        while (indicators.hasNext()) {
            inds.add(indicators.next());
        }
        indicatorList.setListData(inds.toArray());

        if (inds.size() > 0) {
            indicatorList.setSelectedIndex(0);
        }

        multipleEnableCheckBox.setSelected(false);

        pack();

        setLocationRelativeTo(parent);

        indicatorList.requestFocus();

        ActionListener OkActionListener = new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkButtonActionPerformed(evt);
            }
        };

        OkButton.registerKeyboardAction(
                OkActionListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        ActionListener CancelActionListener = new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        };

        CancelButton.registerKeyboardAction(
                OkActionListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true),
                JComponent.WHEN_IN_FOCUSED_WINDOW);


        ChartViewContainer viewContainer = AnalysisChartTopComponent.selected().get().selectedViewContainer().get();

        nameMapResult.put("Option", new Integer(JOptionPane.CANCEL_OPTION));
        if (viewContainer != null) {
            MasterTSer masterSer = viewContainer.controller().masterSer();
            nameMapResult.put("nUnits", new Integer(masterSer.freq().nUnits()));
            nameMapResult.put("unit", masterSer.freq().unit());
        } else {
            nameMapResult.put("nUnits", new Integer(1));
            nameMapResult.put("unit", TUnit$Day$.MODULE$);
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        OkButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        multipleEnableCheckBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        indicatorList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        OkButton.setFont(new java.awt.Font("DialogInput", 0, 11));
        OkButton.setText("OK");
        OkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkButtonActionPerformed(evt);
            }
        });

        CancelButton.setFont(new java.awt.Font("DialogInput", 0, 11));
        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(" Please select one indicator "));
        jPanel1.setFont(new java.awt.Font("SansSerif", 0, 12));

        multipleEnableCheckBox.setFont(new java.awt.Font("DialogInput", 0, 11));
        multipleEnableCheckBox.setText("Allow multiple indicators on main chart window");
        multipleEnableCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        multipleEnableCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        indicatorList.setFont(new java.awt.Font("DialogInput", 0, 11));
        indicatorList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "item1", "item2" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        indicatorList.setSelectedIndex(0);
        jScrollPane1.setViewportView(indicatorList);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, multipleEnableCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 244, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 22, Short.MAX_VALUE)
                .add(multipleEnableCheckBox)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(OkButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 74, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(CancelButton)
                        .add(72, 72, 72))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(CancelButton)
                    .add(OkButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        nameMapResult.put("Option", new Integer(JOptionPane.CANCEL_OPTION));
        dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void OkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkButtonActionPerformed
        nameMapResult.put("Option", new Integer(JOptionPane.OK_OPTION));
        nameMapResult.put("selectedIndicator", indicatorList.getSelectedValue());
        nameMapResult.put("multipleEnable", multipleEnableCheckBox.isSelected());
        dispose();
    }//GEN-LAST:event_OkButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new PickIndicatorDialog(new javax.swing.JFrame(), true, null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JButton OkButton;
    private javax.swing.JList indicatorList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox multipleEnableCheckBox;
    // End of variables declaration//GEN-END:variables
}
