/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Helpers.Koneksi;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ariramadhan
 */
public class ReportDao {

    private Koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;

    public void CetakLaporan(String bahan, String awal, String sampai, String koef0, String koef1, String hasil) {
        
        con = new Koneksi();

        try {
            HashMap parameter = new HashMap();
            File file = new File("src/Report/LaporanHasilPrediksi.jasper");
            parameter.put("bahan", bahan);
            parameter.put("tanggalAwal", awal);
            parameter.put("tanggalAkhir", sampai);
            parameter.put("nilaiKoefisien0", koef0);
            parameter.put("nilaiKoefisien1", koef1);
            parameter.put("hasil", hasil);
            JasperReport jp = (JasperReport) JRLoader.loadObject(file);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jp, parameter, con.connect());
            JasperViewer.viewReport(jasperPrint, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
