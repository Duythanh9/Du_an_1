/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.subjects;

import config.DBConnect;
import entities.subjects.TaiKhoan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;

/**
 *
 * @author Mtt
 */
public class TaiKhoanRepository  {
    public ArrayList<TaiKhoan> getAll() {
        String sql = """
                   SELECT 
                             [email]
                             ,[dien_thoai]
                             ,[password]
                             ,[ho_ten]
                             ,[dia_chi]
                             ,[ghi_chu]
                             ,[chuc_vu]
                             ,[ngay_tao]
                             ,[ngay_cap_nhat]
                             ,[ngay_xoa]
                             ,[trang_thai]
                             ,[ma]
                             
                         FROM [dbo].[tai_khoan]""";
        ArrayList<TaiKhoan> listTK = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TaiKhoan tk = TaiKhoan.builder()
                        .email(rs.getString(1))
                        .dienThoai(rs.getString(2))
                        .password(rs.getString(3))
                        .hoTen(rs.getString(4))
                        .diaChi(rs.getString(5))
                        .ghiChu(rs.getString(6))
                        .chucVu(rs.getBoolean(7))
                        .ngayTao(rs.getDate(8))
                        .ngayCapNhat(rs.getDate(9))
                        .ngayXoa(rs.getDate(10))
                        .trangThai(rs.getBoolean(11))
                        .ma(rs.getString(12))
//                        .hinhAnh(rs.getString(13))
                        .build();
                listTK.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return listTK;
    }
    public static void main(String[] args) {
        System.out.println(new TaiKhoanRepository().getAll());
    }
    public boolean add(TaiKhoan tk) {
        int check = 0;
        String sql = """
                    INSERT INTO [dbo].[tai_khoan]
                               ([email]
                               ,[dien_thoai]
                               ,[password]
                               ,[ho_ten]
                               ,[dia_chi]
                               ,[ghi_chu]
                               ,[chuc_vu]
                               ,[ngay_tao]
                               ,[ngay_cap_nhat]
                               ,[ngay_xoa]
                               ,[trang_thai]
                               ,[ma])
                         VALUES
                               (?,?,?,?,?,?,?,?,?,?,?,?)
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, tk.getEmail());
            ps.setObject(2, tk.getDienThoai());
            ps.setObject(3, tk.getPassword());
            ps.setObject(4, tk.getHoTen());
            ps.setObject(5, tk.getDiaChi());
            ps.setObject(6, tk.getGhiChu());
            ps.setObject(7, tk.isChucVu());
            ps.setObject(8, tk.getNgayTao());
            ps.setObject(9, tk.getNgayCapNhat());
            ps.setObject(10, tk.getNgayXoa());
            ps.setObject(11, tk.isTrangThai());
//            ps.setObject(12, tk.getHinhAnh());
            ps.setObject(12, tk.getMa());

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return check > 0;
    }
    

    public boolean update(TaiKhoan tk, String ma) {
        int check = 0;
        String sql = """
                   UPDATE [dbo].[tai_khoan]
                      SET [email] = ?
                         ,[dien_thoai] = ?
                         ,[password] = ?
                         ,[ho_ten] = ?
                         ,[dia_chi] = ?
                         ,[ghi_chu] = ?
                         ,[chuc_vu] = ?
                         ,[ngay_tao] = ?
                         ,[ngay_cap_nhat] = ?
                         ,[ngay_xoa] = ?
                         ,[trang_thai] = ?
                    WHERE [ma]=?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, tk.getEmail());
            ps.setObject(2, tk.getDienThoai());
            ps.setObject(3, tk.getPassword());
            ps.setObject(4, tk.getHoTen());
            ps.setObject(5, tk.getDiaChi());
            ps.setObject(6, tk.getGhiChu());
            ps.setObject(7, tk.isChucVu());
            ps.setObject(8, tk.getNgayTao());
            ps.setObject(9, tk.getNgayCapNhat());
            ps.setObject(10, tk.getNgayXoa());
            ps.setObject(12, tk.isTrangThai());
//            ps.setObject(11, tk.getHinhAnh());
            ps.setObject(11, tk.getMa());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean delete(String ma) {
        int check = 0;
        String sql = """
DELETE FROM [dbo].[tai_khoan]
      WHERE [ma] = ?""";

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
    public ArrayList<TaiKhoan> search(String tenVC) {
        String sql = """
          SELECT                       
                                        [id ]
                                       ,[ma]               
                                       ,[email]
                                       ,[dien_thoai]
                                       ,[password]
                                       ,[ho_ten]
                                       ,[dia_chi]
                                       ,[ghi_chu]
                                       ,[chuc_vu]
                                       ,[ngay_tao]
                                       ,[ngay_cap_nhat]
                                       ,[ngay_xoa]
                                       ,[trang_thai]

                                       
                                   FROM [dbo].[tai_khoan] where ho_ten like ?""";
        ArrayList<TaiKhoan> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, "%" + tenVC + "%"); // like => %%: contans : Kiem tra chua
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lists.add(new TaiKhoan(
                        rs.getInt(1),
                        rs.getString(2  ),
                        rs.getString(3),
                        rs.getString(4 ),
                        rs.getString(5 ),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getBoolean(9),
                        rs.getDate(10),
                        rs.getDate(11),
                        rs.getDate(12),
                        rs.getBoolean(13)
//                        rs.getString(14)
                        
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }
}



