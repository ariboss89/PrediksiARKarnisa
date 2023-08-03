/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Helpers.Koneksi;
import Models.Autoregressive;
import Models.Penjualan;
import Models.Sortir;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ariramadhan
 */
public class PrediksiDao {

    private Koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;

    public void Save(String nama, int jumlah, Date tanggal, String bulan, String tahun) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "insert into penjualan(bahan, jumlah, tanggal, bulan, tahun)values('" + nama + "', '" + jumlah + "', '" + tanggal + "', '" + bulan + "', '" + tahun + "')";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data bahan berhasil di tambahkan");
        } catch (SQLException e) {
        }
    }

    public void Update(String nama, int jumlah, Date tanggal, String bulan, String tahun, int Id) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "update bahan set penjualan bahan='" + nama + "', jumlah='" + jumlah + "', tanggal='" + tanggal + "', bulan='" + bulan + "', tahun='" + tahun + "' where Id ='" + Id + "' ";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data penjualan berhasil di ubah");
        } catch (SQLException e) {

        }
    }

    public void Delete(int id) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "delete from penjualan where Id = '" + id + "'";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data penjualan berhasil di hapus");
        } catch (SQLException e) {

        }
    }

    public String[][] ShowDataPenjualan(Date tglAwal, Date tglAkhir) {

        res = null;
        String[][] data = null;
        con = new Koneksi();
        con.connect();
        int jumlahBaris = 0;
        try {
            st = con.conn.createStatement();
            query = "SELECT COUNT(Id) AS Jumlah FROM penjualan WHERE tanggal BETWEEN '" + tglAwal + "' AND '" + tglAkhir + "'";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from penjualan WHERE tanggal BETWEEN '" + tglAwal + "' AND '" + tglAkhir + "'";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][6];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("Id");
                data[r][1] = res.getString("bahan");
                data[r][2] = res.getString("jumlah");
                data[r][3] = res.getString("tanggal");
                data[r][4] = res.getString("bulan");
                data[r][5] = res.getString("tahun");

                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][6];
            for (r = 0; r < jmlBaris; r++) {
                for (int c = 0; c < 6; c++) {
                    data[r][c] = tmpArray[r][c];
                }
            }
            st.close();
            con.conn.close();
        } catch (SQLException e) {
            System.err.println("SQLException : " + e.getMessage());
        }
        return data;
    }

    public ArrayList<String> ListBahan() {
        ArrayList<String> dataKriteria = new ArrayList<>();
        con = new Koneksi();
        try {
            st = con.connect().createStatement();
            res = st.executeQuery("SELECT *FROM bahan");
            while (res.next()) {
                dataKriteria.add(res.getString("bahan"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal di request !!");
        }

        return dataKriteria;
    }

    public List<Sortir> SortirData(JTable tbl1, String[] arrBulan, String[] arrTahun ) {

        List<Sortir> listDataSortir = new ArrayList<>();
        List<Penjualan> listDataPenjualan = new ArrayList();

        Penjualan pn = new Penjualan();

        int countTbl = tbl1.getRowCount();
        for (int a = 0; a < countTbl; a++) {

            pn = new Penjualan();

            int jml = (Integer.parseInt(tbl1.getValueAt(a, 2).toString()));
            pn.setJumlah(jml);
//              pn.setTanggal(Date.valueOf(jTable1.getValueAt(a, 3).toString()));
            pn.setBulan(tbl1.getValueAt(a, 4).toString());
            pn.setTahun(tbl1.getValueAt(a, 5).toString());

            listDataPenjualan.add(pn);

        }

        List<Penjualan> listBulan = new ArrayList<>();

        String bulan, tahun;

        for (int a = 0; a < arrTahun.length; a++) {
            for (int b = 0; b < arrBulan.length; b++) {

                bulan = arrBulan[b];
                tahun = arrTahun[a];

                String bulanKu = bulan;
                String tahunKu = tahun;

                List<Penjualan> result = listDataPenjualan.stream()
                        .filter(x -> x.getBulan().equals(bulanKu) && x.getTahun().equals(tahunKu))
                        .collect(Collectors.toList());

                if (result.size() != 0) {

                    listBulan = result;

                    Sortir sd = new Sortir();

                    int totalPenjualan = 0;

                    for (int c = 0; c < listBulan.size(); c++) {
                        sd = new Sortir();
                        int penjualan = listBulan.get(c).getJumlah();

                        totalPenjualan += penjualan;

                    }

                    if (totalPenjualan != 0) {

                        sd.setBulan(bulan);
                        sd.setTahun(tahun);
                        sd.setTotal(totalPenjualan);

                        listDataSortir.add(sd);
                    }

                }

            }
        }
        
        return listDataSortir;

    }
    
    public List<Autoregressive> PrediksiAutoregressive(JTable tbl){
        Autoregressive ar;
        List<Autoregressive> listDataAutoregressive = new ArrayList();

        int countTbl = tbl.getRowCount();
        for (int a = 0; a < countTbl; a++) {

            ar = new Autoregressive();

            if (a == 0) {
                double nilaiYt = (Double.parseDouble(tbl.getValueAt(a, 0).toString()));
                double nilaiXt = 0.0;
                ar.setBulan(tbl.getValueAt(a, 1).toString());
                ar.setYt(nilaiYt);
                ar.setXt(nilaiXt);
                ar.setXy(nilaiYt * nilaiXt);
                ar.setX2(nilaiXt * nilaiXt);

            } else {

                double nilaiYt = (Double.parseDouble(tbl.getValueAt(a, 0).toString()));
                double nilaiXt = (Double.parseDouble(tbl.getValueAt(a - 1, 0).toString()));
                ar.setBulan(tbl.getValueAt(a, 1).toString());
                ar.setYt(nilaiYt);
                ar.setXt(nilaiXt);
                ar.setXy(nilaiYt * nilaiXt);
                ar.setX2(nilaiXt * nilaiXt);
            }

            listDataAutoregressive.add(ar);
        }
        
        return listDataAutoregressive;
    }
}
