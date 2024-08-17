/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.products;

import config.DBConnect;
import entities.products.SanPham;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DateConverter;
import repositories.FullRepository;
import utils.MaGenerator;

/**
 *
 * @author Mtt
 */
public class SanPhamRepository implements FullRepository<SanPham> {

    @Override
    public List<SanPham> selectAll() {
        List<SanPham> list = new ArrayList<>();

        String sql = """
                     SELECT [id]
                           ,[ma_san_pham]
                           ,[ten_san_pham]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[ngay_xoa]
                           ,[trang_thai]
                       FROM [dbo].[san_pham]
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(SanPham.builder()
                        .id(rs.getInt("id"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .tenSanPham(rs.getString("ten_san_pham"))
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .trang_thai(rs.getInt("trang_thai"))
                        .build());
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }
    }
    
    public List<SanPham> selectAllTK() {
        List<SanPham> list = new ArrayList<>();

        String sql = """
                     SELECT [id]
                            ,[ma_san_pham]
                           ,[ten_san_pham]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[ngay_xoa]
                           ,[trang_thai]
                       FROM [dbo].[san_pham]
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(SanPham.builder()
                        .id(rs.getInt("id"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .tenSanPham(rs.getString("ten_san_pham"))
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .trang_thai(rs.getInt("trang_thai"))
                        .build());
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }
    }

    @Override
    public List<SanPham> selectDeleted() {

        List<SanPham> list = new ArrayList<>();

        String sql = """
                     SELECT [id]
                     ,[ma_san_pham]
                           ,[ten_san_pham]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[ngay_xoa]
                           ,[trang_thai]
                     ,[ma_san_pham]
                       FROM [dbo].[san_pham]
                       where [trang_thai] = 0
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(SanPham.builder()
                        .id(rs.getInt("id"))
                        .tenSanPham(rs.getString("ten_san_pham"))
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .trang_thai(rs.getInt("trang_thai"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .build());
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }
    }

    public SanPham selectById(int id) {
        String sql = """
                     SELECT [id]
                     ,[ma_san_pham]
                           ,[ten_san_pham]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[ngay_xoa]
                           ,[trang_thai]
                           ,[ma_san_pham]
                       FROM [dbo].[san_pham]
                       where id = ?
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return SanPham.builder()
                        .id(rs.getInt("id"))
                        .tenSanPham(rs.getString("ten_san_pham"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .build();
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Loi db");
        }
    }

    @Override
    public boolean create(SanPham newSanPham) {
        String sql = """
                   INSERT INTO [dbo].[san_pham]
                              ([ten_san_pham]
                              ,[ngay_tao]
                              ,[ngay_cap_nhat]
                              ,[ma_san_pham])
                        VALUES
                              (?
                              ,?
                              ,?
                              ,?
                              )
                   
                   """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            stmt.setObject(1, newSanPham.getTenSanPham());
            stmt.setObject(2, DateConverter.getDateNow());
            stmt.setObject(3, DateConverter.getDateNow());
            stmt.setObject(4, newSanPham.getMaSanPham());

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Them that bai");
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }
    }
    
    public boolean createTK(String ten, String ma) {
        String sql = """
                   INSERT INTO [dbo].[san_pham]
                                         ([ten_san_pham]
                                         ,[ngay_tao]
                                         ,[ngay_cap_nhat]
                                         ,[ngay_xoa]
                                         ,[trang_thai]
                                         ,[ma_san_pham])
                                   VALUES
                                         (?
                                         ,GETDATE()
                                         ,null
                                         ,null
                                         ,0
                                         ,?)
                   """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            stmt.setObject(1, ten);
            stmt.setObject(2, ma);

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Them that bai");
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }
    }

    @Override
    public boolean update(SanPham sanPham, int id) {
        String sql = """
                     UPDATE [dbo].[san_pham]
                        SET [ten_san_pham] = ?
                           ,[ngay_cap_nhat] = ?
                      WHERE id = ?
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, sanPham.getTenSanPham());
            stmt.setObject(2, DateConverter.getDateNow());
            stmt.setObject(3, id);

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Cap nhat that bai");
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = """
                     UPDATE [dbo].[san_pham]
                        SET [ngay_xoa] = ?
                           ,[trang_thai] = 0
                      WHERE id = ?
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, DateConverter.getDateNow());
            stmt.setObject(2, id);

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Cap nhat that bai");
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }

    }
    
     public List<SanPham> search(String canTim) {
        List<SanPham> list = new ArrayList<>();

        String sql = """
                     SELECT [id]
                           ,[ma_san_pham]
                           ,[ten_san_pham]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[ngay_xoa]
                           ,[trang_thai]
                       FROM [dbo].[san_pham] where [ma_san_pham] =? or ten_san_pham like?
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1,'%'+canTim+'%');
            stmt.setString(2,sql);
           
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(SanPham.builder()
                        .id(rs.getInt("id"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .tenSanPham(rs.getString("ten_san_pham"))
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .trang_thai(rs.getInt("trang_thai"))
                        .build());
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }
    }

}
