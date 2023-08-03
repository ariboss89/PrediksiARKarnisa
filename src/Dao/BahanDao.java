/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Helpers.Koneksi;
import Models.Bahan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author ariramadhan
 */
public class BahanDao extends Bahan{
    
    private Koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;
    
    public void Save(String nama, String kategori,String satuan, int harga) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "insert into bahan(bahan, kategori, satuan, harga)values('" + nama + "', '"+kategori+"', '"+satuan+"', '"+harga+"')";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data bahan berhasil di tambahkan");
        } catch (SQLException e) {
        }
    }
    
    public void Update(String nama, String kategori, String satuan, int harga, int id) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "update bahan set bahan='" + nama + "', kategori='" + kategori + "', satuan='" + satuan + "', harga='" + harga + "' where Id ='"+id+"' ";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data bahan berhasil di ubah");
        } catch (SQLException e) {
            
        }
    }
    
    public void Delete(int id) {
        con = new Koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "delete from bahan where Id = '" + id + "'";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data bahan berhasil di hapus");
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
            query = "SELECT COUNT(Id) AS Jumlah FROM bahan";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from bahan";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][5];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("Id");
                data[r][1] = res.getString("bahan");
                data[r][2] = res.getString("kategori");
                data[r][3] = res.getString("satuan");
                data[r][4] = res.getString("harga");
                
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][5];
            for (r = 0; r < jmlBaris; r++) {
                for(int c = 0; c < 5; c++) {
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
    
    public ArrayList<String> ListKategori(){
        ArrayList<String> dataKriteria = new ArrayList<>();
        con= new Koneksi();
        try{
            st = con.connect().createStatement();
            res = st.executeQuery("SELECT *FROM kategori");
            while(res.next()){
                dataKriteria.add(res.getString("nama"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Data gagal di request !!");
        }
        
        return dataKriteria;
    }
    
}
