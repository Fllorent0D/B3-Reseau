/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_indep;

import java.awt.BorderLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import server.Message;

/**
 *
 * @author florentcardoen
 */
public class VENTES_REP extends javax.swing.JPanel {
    ClientINDEP client;
    /**
     * Creates new form VENTES_REP
     */
    public VENTES_REP() {
        initComponents();
        yearSpinner.setEditor(new JSpinner.DateEditor(yearSpinner, "yyyy"));

    }
    public void setClient(ClientINDEP cli)
    {
        client = cli;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        yearSpinner = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        monthCombo = new javax.swing.JComboBox<>();
        monthCheckBox = new javax.swing.JCheckBox();
        chartPane = new javax.swing.JPanel();
        genButton = new javax.swing.JButton();

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel2.setText("Représentation des ventes par catégorie");

        yearSpinner.setModel(new javax.swing.SpinnerDateModel());

        jLabel1.setText("Année :");

        monthCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Décembre" }));

        monthCheckBox.setSelected(true);
        monthCheckBox.setText("Mois :");
        monthCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                monthCheckBoxStateChanged(evt);
            }
        });
        monthCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout chartPaneLayout = new javax.swing.GroupLayout(chartPane);
        chartPane.setLayout(chartPaneLayout);
        chartPaneLayout.setHorizontalGroup(
            chartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chartPaneLayout.setVerticalGroup(
            chartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );

        genButton.setText("Générer rapport");
        genButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1)
                            .addComponent(monthCheckBox))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(monthCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(yearSpinner))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(genButton)))
                .addContainerGap(197, Short.MAX_VALUE))
            .addComponent(chartPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yearSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthCheckBox)
                    .addComponent(genButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chartPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void genButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genButtonActionPerformed
        Integer annee, mois;
        String str;
        Message response;
        HashMap data;
        Calendar cal = Calendar.getInstance();
        cal.setTime((Date)yearSpinner.getValue());
        annee = cal.get(Calendar.YEAR);
        
        if(monthCheckBox.isSelected())
            mois = monthCombo.getSelectedIndex() + 1;
        else
            mois = 0;
        
        response = client.Ventes_rep(annee, mois);
        if((Integer)response.getParam("status") == 0)
            JOptionPane.showMessageDialog(this,response.getParam("error"),"Erreur interne", JOptionPane.ERROR_MESSAGE);
        else
        {
            data = response.getParams();
            Iterator it = data.entrySet().iterator();
            DefaultPieDataset ds = new DefaultPieDataset();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                if(!((String)pair.getKey()).equals("status"))
                    ds.setValue((String)pair.getKey(), (Double)pair.getValue()); 
                
                //System.out.println(pair.getKey() + " = " + pair.getValue());
                it.remove();
            }
            str = "Représentation des ventes ";
            if(mois == 0)
                str += "de l'année "+ annee;
            else
                str += "du mois "+mois+"/"+annee;
            
            JFreeChart jfc = ChartFactory.createPieChart (str, ds, true, true, true);
            
            ChartPanel cp = new ChartPanel(jfc);
            //chartPane = new JPanel();
            chartPane.setLayout(new java.awt.BorderLayout());
            chartPane.add(cp);
            chartPane.setVisible(true);
            chartPane.validate();
            //System.out.println((Integer)response.getParam("status"));
        }
    }//GEN-LAST:event_genButtonActionPerformed

    private void monthCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthCheckBoxActionPerformed
    }//GEN-LAST:event_monthCheckBoxActionPerformed

    private void monthCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_monthCheckBoxStateChanged
        monthCombo.setEnabled(monthCheckBox.isSelected());
    }//GEN-LAST:event_monthCheckBoxStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chartPane;
    private javax.swing.JButton genButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JCheckBox monthCheckBox;
    private javax.swing.JComboBox<String> monthCombo;
    private javax.swing.JSpinner yearSpinner;
    // End of variables declaration//GEN-END:variables
}
