/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Helpers.Koneksi;
import Models.Penjualan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author ariramadhan
 */
public class PenjualanDao extends Penjualan{
    
    private Koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;
    
    public void Save(String nama, int jumlah,Date tanggal, String bulan, String tahun) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "insert into penjualan(bahan, jumlah, tanggal, bulan, tahun)values('" + nama + "', '"+jumlah+"', '"+tanggal+"', '"+bulan+"', '"+tahun+"')";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data bahan berhasil di tambahkan");
        } catch (SQLException e) {
        }
    }
    
    public void Update(String nama, int jumlah,Date tanggal, String bulan, String tahun, int Id) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "update bahan set penjualan bahan='" + nama + "', jumlah='" + jumlah + "', tanggal='" + tanggal + "', bulan='" + bulan + "', tahun='" + tahun + "' where Id ='"+Id+"' ";
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

    public String[][] ShowData() {

        res = null;
        String[][] data = null;
        con = new Koneksi();
        con.connect();
        int jumlahBaris = 0;
        try {
            st = con.conn.createStatement();
            query = "SELECT COUNT(Id) AS Jumlah FROM penjualan";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from penjualan";
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
                for(int c = 0; c < 6; c++) {
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
    
    public ArrayList<String> ListBahan(){
        ArrayList<String> dataKriteria = new ArrayList<>();
        con= new Koneksi();
        try{
            st = con.connect().createStatement();
            res = st.executeQuery("SELECT *FROM bahan");
            while(res.next()){
                dataKriteria.add(res.getString("bahan"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Data gagal di request !!");
        }
        
        return dataKriteria;
    }
    
}
