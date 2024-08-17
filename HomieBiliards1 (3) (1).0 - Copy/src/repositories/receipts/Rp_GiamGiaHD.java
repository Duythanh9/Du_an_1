/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.receipts;

import entities.receipts.GiamGiaHoaDon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import config.DBConnect;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nguye
 */
public class Rp_GiamGiaHD {

    public ArrayList<GiamGiaHoaDon> getAll() {
        String sql = """
                    SELECT
                     	    [ma]
                           ,[ten]
                           ,[mo_ta]
                           ,[gia_tri_toi_thieu]
                           ,[gia_tri_toi_da]
                           ,[giam_phan_tram]
                           ,[giam_so_toi_da]
                           ,[so_luong]
                           ,[ngay_bat_dau]
                           ,[ngay_ket_thuc]
                           ,[trang_thai]
                           ,[loai_giam_gia]
                       FROM [dbo].[giam_gia_hoa_don] where [trang_thai]=1 or [trang_thai] =2 or [trang_thai] =3""";
        ArrayList<GiamGiaHoaDon> listGG = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GiamGiaHoaDon response = GiamGiaHoaDon.builder()
                        .ma(rs.getString(1))
                        .ten(rs.getString(2))
                        .moTa(rs.getString(3))
                        .giaTriToiThieu(rs.getInt(4))
                        .giaTriToiDa(rs.getInt(5))
                        .giamPhanTram(rs.getInt(6))
                        .giamSoToiDa(rs.getInt(7))
                        .soLuong(rs.getInt(8))
                        .ngayBatDau(rs.getDate(9))
                        .ngayKetThuc(rs.getDate(10))
                        .trangThai(rs.getInt(11))
                        .loaiGiamGia(rs.getBoolean(12))
                        .build();
                listGG.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return listGG;
    }
    
    public String GG(int id) {
        String ten = "";
        String sql = """
                    SELECT
                     [ten]
                       FROM [dbo].[giam_gia_hoa_don]
                     where id = ?
                     """;
        ArrayList<GiamGiaHoaDon> listGG = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ten = rs.getString("ten");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return ten;
    }
    
    public ArrayList<GiamGiaHoaDon> getAllTrangThai( int trangThai) {
        String sql = """
                    SELECT
                     	   [ma]
                           ,[ten]
                           ,[mo_ta]
                           ,[gia_tri_toi_thieu]
                           ,[gia_tri_toi_da]
                           ,[giam_phan_tram]
                           ,[giam_so_toi_da]
                           ,[so_luong]
                           ,[ngay_bat_dau]
                           ,[ngay_ket_thuc]
                           ,[trang_thai]
                           ,[loai_giam_gia]
                           
                       FROM [dbo].[giam_gia_hoa_don] where [trang_thai]=?""";
        ArrayList<GiamGiaHoaDon> listGG = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GiamGiaHoaDon response = GiamGiaHoaDon.builder()
                        .ma(rs.getString(1))
                        .ten(rs.getString(2))
                        .moTa(rs.getString(3))
                        .giaTriToiDa(rs.getInt(4))
                        .giaTriToiThieu(rs.getInt(5))
                        .giamPhanTram(rs.getInt(6))
                        .giamSoToiDa(rs.getInt(7))
                        .soLuong(rs.getInt(8))
                        .ngayBatDau(rs.getDate(9))
                        .ngayKetThuc(rs.getDate(10))
                        .trangThai(rs.getInt(11))
                        .loaiGiamGia(rs.getBoolean(12))
                        .build();
                listGG.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return listGG;
    }

    public boolean add(GiamGiaHoaDon gghd) {
        int check = 0;
        String sql = """
                    INSERT INTO [dbo].[giam_gia_hoa_don]
                               ([ma]
                               ,[ten]
                               ,[mo_ta]
                               ,[gia_tri_toi_thieu]
                               ,[gia_tri_toi_da]
                               ,[giam_phan_tram]
                               ,[giam_so_toi_da]
                               ,[so_luong]
                               ,[ngay_bat_dau]
                               ,[ngay_ket_thuc]
                               ,[trang_thai]
                               ,[loai_giam_gia])
                         VALUES
                               (?,?,?,?,?,?,?,?,?,?,1)
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, gghd.getMa());
            ps.setObject(2, gghd.getTen());
            ps.setObject(3, gghd.getMoTa());
            ps.setObject(4, gghd.getGiaTriToiDa());
            ps.setObject(5, gghd.getGiaTriToiThieu());
            ps.setObject(6, gghd.getGiamPhanTram());
            ps.setObject(7, gghd.getGiamSoToiDa());
            ps.setObject(8, gghd.getSoLuong());
            ps.setObject(9, new Date());
            ps.setObject(10, gghd.getNgayKetThuc());

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return check > 0;
    }

    public boolean update(GiamGiaHoaDon newGiamGia, String ma) {
        int check = 0;
        String sql = """
                   UPDATE [dbo].[giam_gia_hoa_don]
                      SET 
                   	  [ten] = ?
                         ,[mo_ta] = ?
                         ,[gia_tri_toi_thieu] = ?
                         ,[gia_tri_toi_da] = ?
                         ,[giam_phan_tram] = ?
                         ,[giam_so_toi_da] = ?
                         ,[so_luong] = ?
                         ,[ngay_bat_dau] = ?
                         ,[ngay_ket_thuc] = ?
                    WHERE [ma]=?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, newGiamGia.getTen());
            ps.setObject(2, newGiamGia.getMoTa());
            ps.setObject(3, newGiamGia.getGiaTriToiThieu());
             ps.setObject(4, newGiamGia.getGiaTriToiDa());
            ps.setObject(5, newGiamGia.getGiamPhanTram());
            ps.setObject(6, newGiamGia.getGiamSoToiDa());
            ps.setObject(7, newGiamGia.getSoLuong());
            ps.setObject(8, newGiamGia.getNgayBatDau());
            ps.setObject(9, newGiamGia.getNgayKetThuc());
        ps.setObject(10, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean delete(String ma) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[giam_gia_hoa_don]
                         SET 
                            [trang_thai] = 3
                       WHERE ma = ?""";

//        String sql = """
//                     DELETE FROM [dbo].[giam_gia_hoa_don]
//                           WHERE [ma] =?
//                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public ArrayList<GiamGiaHoaDon> search(String maVC) {
        String sql = """
          SELECT [id]
                ,[ma]
                ,[ten]
                ,[mo_ta]
                ,[gia_tri_toi_thieu]
                ,[gia_tri_toi_da]
                ,[giam_phan_tram]
                ,[giam_so_toi_da]
                ,[so_luong]
                ,[ngay_bat_dau]
                ,[ngay_ket_thuc]
                ,[ngay_tao]
                ,[ngay_cap_nhat]
                ,[ngay_xoa]
                ,[trang_thai]
                ,[loai_giam_gia]
            FROM [dbo].[giam_gia_hoa_don] where ma like ?""";
        ArrayList<GiamGiaHoaDon> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, "%" + maVC + "%"); // like => %%: contans : Kiem tra chua
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lists.add(new GiamGiaHoaDon(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getDate(10),
                        rs.getDate(11),
                        rs.getDate(12),
                        rs.getDate(13),
                        rs.getDate(14),
                        rs.getInt(15),
                        rs.getBoolean(16)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }

    public List<GiamGiaHoaDon> locVoucher(String ma) {
        List<GiamGiaHoaDon> list = new ArrayList<>();
        String sql = """
                    SELECT [id]
                          ,[ma]
                          ,[ten]
                          ,[mo_ta]
                          ,[gia_tri_toi_thieu]
                          ,[gia_tri_toi_da]
                          ,[giam_phan_tram]
                          ,[giam_so_toi_da]
                          ,[so_luong]
                          ,[ngay_bat_dau]
                          ,[ngay_ket_thuc]
                          ,[trang_thai]
                          
                      FROM [dbo].[giam_gia_hoa_don] where [trang_thai] = ?
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, ma);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                 GiamGiaHoaDon response = GiamGiaHoaDon.builder()
                        .ma(rs.getString(1))
                        .ten(rs.getString(2))
                        .moTa(rs.getString(3))
                        .giaTriToiDa(rs.getInt(4))
                        .giaTriToiThieu(rs.getInt(5))
                        .giamPhanTram(rs.getInt(6))
                        .giamSoToiDa(rs.getInt(7))
                        .soLuong(rs.getInt(8))
                        .ngayBatDau(rs.getDate(9))
                        .ngayKetThuc(rs.getDate(10))
                        .trangThai(rs.getInt(11))            
                        .build();
                 list.add(response);
                
            }
             return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }
    
     public GiamGiaHoaDon checkTrung(String maCheck) {
         GiamGiaHoaDon gg = null;
        String sql = """
                    SELECT
                     	   [ma]
                           ,[ten]
                           ,[mo_ta]
                           ,[gia_tri_toi_thieu]
                           ,[gia_tri_toi_da]
                           ,[giam_phan_tram]
                           ,[giam_so_toi_da]
                           ,[so_luong]
                           ,[ngay_bat_dau]
                           ,[ngay_ket_thuc]
                           ,[trang_thai]
                       FROM [dbo].[giam_gia_hoa_don] where [ma]=?""";
       
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
           
            ps.setObject(1, maCheck);
             ResultSet rs = ps.executeQuery();
           
            while (rs.next()) {                
                String maVC,tenVC,moTa;
                int giaTriToiThieu,giaTriToiDa,giamSoTD,soLuong,trangThai;
                int giamPT;
                boolean loaiGiamGia;
                Date ngayBD, ngayKT;
                
                maVC = rs.getString(1);
                tenVC = rs.getString(2);
                moTa = rs.getString(3);
                giaTriToiThieu = rs.getInt(4);
                giaTriToiDa = rs.getInt(5);
                giamPT = rs.getInt(6);
                giamSoTD = rs.getInt(7);
                soLuong = rs.getInt(8);
                ngayBD = rs.getDate(9);
                ngayKT = rs.getDate(10);
                trangThai = rs.getInt(11);
                loaiGiamGia= rs.getBoolean(12);
                gg = new GiamGiaHoaDon(null, moTa, tenVC, moTa, giaTriToiThieu, giaTriToiDa, giamPT, giamSoTD, soLuong, ngayBD, ngayKT, null, null, null, trangThai,loaiGiamGia);
            }
            return gg;
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return null;
    }
    
//    public static void main(String[] args) {
//        System.out.println(new GiamGiaHoaDon().toString());
//    }
}
    
