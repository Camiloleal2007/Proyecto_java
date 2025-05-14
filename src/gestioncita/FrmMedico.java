/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gestioncita;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author LENOVO-V14G3-ABA
 */
public class FrmMedico extends javax.swing.JFrame {

    /**
     * Creates new form FrmMedico
     */
    private DefaultTableModel modeloCitasActivas;
    private DefaultTableModel modeloCitasAtendidas;
    Operaciones operaciones = new Operaciones();
    FrmLogin login = new FrmLogin();
    String Nombre = login.Nombre();
    String Correo = login.Correo();
    String Telefono = login.Telefono();

    public FrmMedico() {
        initComponents();
        modeloCitasActivas = new DefaultTableModel();
        modeloCitasAtendidas = new DefaultTableModel();

        String[] columnasCitasActivas = {"Fecha", "Hora", "Paciente","Medico","Tipo Cita", "Atender"};
        String[] columnasCitasAtendidas = {"Fecha", "Hora", "Paciente", "Medico", "Tipo Cita", "Diagnóstico", "Solución"};

        modeloCitasActivas.setColumnIdentifiers(columnasCitasActivas);

        modeloCitasAtendidas.setColumnIdentifiers(columnasCitasAtendidas);

        TbCitasMedicas1.setModel(modeloCitasActivas);
        TbCitaAtendidas.setModel(modeloCitasAtendidas);

        TbCitasMedicas1.getColumn("Atender").setCellRenderer(new ButtonRenderer());
        TbCitasMedicas1.getColumn("Atender").setCellEditor(new ButtonEditor(new JCheckBox()));
        cargarDatos();

    }

    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Atender");
            button.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Obtener datos de la cita
                    String fecha = table.getValueAt(selectedRow, 0).toString();
                    String hora = table.getValueAt(selectedRow, 1).toString();
                    String paciente = table.getValueAt(selectedRow, 2).toString();
                    String especialidad = table.getValueAt(selectedRow, 4).toString();

                    // Pedir diagnóstico y solución
                    String diagnostico = JOptionPane.showInputDialog(null, "Ingrese el diagnóstico:");
                    if (diagnostico == null || diagnostico.trim().isEmpty()) {
                        return;
                    }

                    String solucion = JOptionPane.showInputDialog(null, "Ingrese la solución:");
                    if (solucion == null || solucion.trim().isEmpty()) {
                        return;
                    }

                    boolean exito = operaciones.atenderCita(paciente, Nombre, fecha, hora, especialidad, diagnostico, solucion);

                    if (exito) {
                        
                        operaciones.eliminarCitaActiva(paciente, Nombre, fecha, hora, especialidad);
                        ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                        JOptionPane.showMessageDialog(null, "Cita atendida correctamente.");
                        cargarDatos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al guardar la cita.");
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Atender";
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setText("Atender");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            return this;
        }
    }

    public void cargarDatos() {
        modeloCitasActivas.setRowCount(0);
        modeloCitasAtendidas.setRowCount(0);

        List<Object[]> citasAtendidas = operaciones.obtenerCitasAtendidas(Nombre);

        DefaultTableModel modelo = (DefaultTableModel) TbCitasMedicas1.getModel();
        modelo.setRowCount(0);

        List<Object[]> citas = operaciones.obtenerCitasDelMedico(Nombre);
        for (Object[] fila : citas) {
            modelo.addRow(fila);
        }

        for (Object[] fila : citasAtendidas) {
            modeloCitasAtendidas.addRow(fila);
            
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        TbHistorialMedico = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbCitasMedicas = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        BtnAgendar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TbCitaAtendidas = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TbCitasMedicas1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        BtnSalir = new javax.swing.JButton();

        TbHistorialMedico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        jScrollPane2.setViewportView(TbHistorialMedico);

        jLabel4.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel4.setText("Historial Medico");

        jLabel1.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabel1.setText("Portal Del Paciente");

        jLabel2.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel2.setText("Gestione sus citas médicas e historial");

        TbCitasMedicas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane1.setViewportView(TbCitasMedicas);

        jLabel3.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel3.setText("Citas Medicas");

        BtnAgendar.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        BtnAgendar.setText("Agendar Cita");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TbCitaAtendidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jScrollPane3.setViewportView(TbCitaAtendidas);

        jLabel5.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel5.setText("Citas Atendidas ");

        jLabel6.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabel6.setText("Portal Del Medico");

        jLabel7.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel7.setText("Gestione sus citas Pendientes");

        TbCitasMedicas1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jScrollPane4.setViewportView(TbCitasMedicas1);

        jLabel8.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel8.setText("Citas Pendientes");

        BtnSalir.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        BtnSalir.setText("Salir");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(661, 661, 661)
                        .addComponent(BtnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnSalir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        // TODO add your handling code here:
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BtnSalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMedico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAgendar;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JTable TbCitaAtendidas;
    private javax.swing.JTable TbCitasMedicas;
    private javax.swing.JTable TbCitasMedicas1;
    private javax.swing.JTable TbHistorialMedico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
