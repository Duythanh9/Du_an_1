/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.products;

import config.DBConnect;
import entities.products.SanPhamChiTiet;
import entities.products.SanPhamChiTiet1;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DateConverter;
import repositories.FullRepository;
import responses.SanPhamChiTietResponse;

/**
 *
 * @author Mtt
 */
public class SanPhamChiTietRepository implements FullRepository<SanPhamChiTiet> {

    @Override
    public List<SanPhamChiTiet> selectAll() {
        List<SanPhamChiTiet> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[id_san_pham]
                           ,[ma_san_pham]
                           ,[mo_ta]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[ngay_xoa]
                           ,[trang_thai]
                           ,[gia_ban]
                           ,[mau_sac]
                           ,[chuoi]
                           ,[tay_cam]
                           ,[dau_co_ban]
                           ,[ngon]
                           ,[ren]
                           ,[trong_luong]
                           ,[duong_kinh_dau]
                           ,[thuong_hieu]
                           ,[bao_hanh]
                           ,[xuat_xu]
                       FROM [dbo].[san_pham_chi_tiet]                                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(SanPhamChiTiet.builder()
                        .id(rs.getInt("id"))
                        .idSanPham(rs.getInt("id_san_pham"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .moTa(rs.getString("mo_ta"))
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .trangThai(rs.getInt("trang_thai"))
                        .giaBan(rs.getInt("gia_ban"))
                        .mauSac(rs.getInt("mau_sac"))
                        .chuoi(rs.getInt("chuoi"))
                        .tayCam(rs.getInt("tay_cam"))
                        .dauCoBan(rs.getInt("dau_co_ban"))
                        .ngon(rs.getInt("ngon"))
                        .ren(rs.getInt("ren"))
                        .trongLuong(rs.getInt("trong_luong"))
                        .duongKinhDau(rs.getDouble("duong_kinh_dau"))
                        .thuongHieu(rs.getInt("thuong_hieu"))
                        .baoHanh(rs.getInt("bao_hanh"))
                        .xuatXu(rs.getInt("xuat_xu"))
                        .build());
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }
    }
    
    
    public ArrayList<SanPhamChiTietResponse> getAll() {
        String sql = """
                    SELECT dbo.san_pham_chi_tiet.id, dbo.san_pham_chi_tiet.ma_san_pham, dbo.san_pham.ten_san_pham, 
                     dbo.san_pham_chi_tiet.mo_ta, dbo.san_pham_chi_tiet.ngay_tao, dbo.san_pham_chi_tiet.gia_ban, dbo.mau_sac.ten, 
                            dbo.chuoi.ten AS Expr1, dbo.tay_cam.ten AS Expr2, dbo.dau_co_ban.ten AS Expr3, 
                     dbo.ngon.ten AS Expr4, dbo.ren.ten AS Expr5, dbo.san_pham_chi_tiet.trong_luong, dbo.san_pham_chi_tiet.duong_kinh_dau, 
                            dbo.thuong_hieu.ten AS Expr6, dbo.san_pham_chi_tiet.bao_hanh, dbo.xuat_xu.ten AS Expr7, 
                     dbo.san_pham_chi_tiet.so_luong, dbo.san_pham_chi_tiet.trang_thai
                                FROM  dbo.san_pham_chi_tiet INNER JOIN
                            dbo.chuoi ON dbo.san_pham_chi_tiet.chuoi = dbo.chuoi.id INNER JOIN
                            dbo.dau_co_ban ON dbo.san_pham_chi_tiet.dau_co_ban = dbo.dau_co_ban.id INNER JOIN
                            dbo.mau_sac ON dbo.san_pham_chi_tiet.mau_sac = dbo.mau_sac.id INNER JOIN
                            dbo.tay_cam ON dbo.san_pham_chi_tiet.tay_cam = dbo.tay_cam.id INNER JOIN
                            dbo.ngon ON dbo.san_pham_chi_tiet.ngon = dbo.ngon.id INNER JOIN
                            dbo.ren ON dbo.san_pham_chi_tiet.ren = dbo.ren.id INNER JOIN
                            dbo.thuong_hieu ON dbo.san_pham_chi_tiet.thuong_hieu = dbo.thuong_hieu.id INNER JOIN
                            dbo.xuat_xu ON dbo.san_pham_chi_tiet.xuat_xu = dbo.xuat_xu.id INNER JOIN
                            dbo.san_pham ON dbo.san_pham_chi_tiet.id_san_pham = dbo.san_pham.id                  
                     """;
        ArrayList<SanPhamChiTietResponse> lists = new ArrayList<>();

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lists.add(new SanPhamChiTietResponse(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13),
                        rs.getFloat(14),
                        rs.getString(15),
                        rs.getInt(16),
                        rs.getString(17),
                        rs.getInt(18),
                        rs.getInt(19)
                ));

            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return lists;

    }
    
     public List<SanPhamChiTiet1> selectSPCT(String ma) {
//     public List<SanPhamChiTiet1> selectSPCT() {
        List<SanPhamChiTiet1> list = new ArrayList<>();
        String sql = """
                     SELECT        dbo.hoa_don.ma_hoa_don, dbo.san_pham_chi_tiet.id AS id_san_pham_chi_tiet, dbo.san_pham_chi_tiet.ma_san_pham, dbo.hoa_don_chi_tiet.so_luong, dbo.hoa_don_chi_tiet.gia_ban, dbo.xuat_xu.ten AS xuat_xu, dbo.thuong_hieu.ten AS thuong_hieu, dbo.ren.ten AS ren, 
                                                                   dbo.mau_sac.ten AS mau_sac, dbo.chuoi.ten AS chuoi, dbo.dau_co_ban.ten AS dau_co_ban, dbo.ngon.ten AS ngon, dbo.san_pham_chi_tiet.bao_hanh, dbo.san_pham_chi_tiet.trong_luong, dbo.tay_cam.ten AS tay_cam, 
                                                                   dbo.san_pham_chi_tiet.duong_kinh_dau
                                          FROM            dbo.hoa_don_chi_tiet INNER JOIN
                                                                   dbo.san_pham_chi_tiet ON dbo.hoa_don_chi_tiet.id = dbo.san_pham_chi_tiet.id INNER JOIN
                                                                   dbo.san_pham ON dbo.san_pham_chi_tiet.id_san_pham = dbo.san_pham.id AND dbo.san_pham_chi_tiet.id_san_pham = dbo.san_pham.id AND dbo.san_pham_chi_tiet.id_san_pham = dbo.san_pham.id INNER JOIN
                                                                   dbo.hoa_don ON dbo.hoa_don_chi_tiet.id_hoa_don = dbo.hoa_don.id AND dbo.hoa_don_chi_tiet.id_hoa_don = dbo.hoa_don.id INNER JOIN
                                                                   dbo.chuoi ON dbo.san_pham_chi_tiet.chuoi = dbo.chuoi.id AND dbo.san_pham_chi_tiet.chuoi = dbo.chuoi.id AND dbo.san_pham_chi_tiet.chuoi = dbo.chuoi.id INNER JOIN
                                                                   dbo.dau_co_ban ON dbo.san_pham_chi_tiet.dau_co_ban = dbo.dau_co_ban.id AND dbo.san_pham_chi_tiet.dau_co_ban = dbo.dau_co_ban.id AND dbo.san_pham_chi_tiet.dau_co_ban = dbo.dau_co_ban.id INNER JOIN
                                                                   dbo.mau_sac ON dbo.san_pham_chi_tiet.mau_sac = dbo.mau_sac.id AND dbo.san_pham_chi_tiet.mau_sac = dbo.mau_sac.id AND dbo.san_pham_chi_tiet.mau_sac = dbo.mau_sac.id INNER JOIN
                                                                   dbo.ngon ON dbo.san_pham_chi_tiet.ngon = dbo.ngon.id AND dbo.san_pham_chi_tiet.ngon = dbo.ngon.id AND dbo.san_pham_chi_tiet.ngon = dbo.ngon.id INNER JOIN
                                                                   dbo.ren ON dbo.san_pham_chi_tiet.ren = dbo.ren.id AND dbo.san_pham_chi_tiet.ren = dbo.ren.id AND dbo.san_pham_chi_tiet.ren = dbo.ren.id INNER JOIN
                                                                   dbo.thuong_hieu ON dbo.san_pham_chi_tiet.thuong_hieu = dbo.thuong_hieu.id AND dbo.san_pham_chi_tiet.thuong_hieu = dbo.thuong_hieu.id AND dbo.san_pham_chi_tiet.thuong_hieu = dbo.thuong_hieu.id INNER JOIN
                                                                   dbo.xuat_xu ON dbo.san_pham_chi_tiet.xuat_xu = dbo.xuat_xu.id AND dbo.san_pham_chi_tiet.xuat_xu = dbo.xuat_xu.id AND dbo.san_pham_chi_tiet.xuat_xu = dbo.xuat_xu.id INNER JOIN
                                                                   dbo.tay_cam ON dbo.san_pham_chi_tiet.tay_cam = dbo.tay_cam.id AND dbo.san_pham_chi_tiet.tay_cam = dbo.tay_cam.id AND dbo.san_pham_chi_tiet.tay_cam = dbo.tay_cam.id
                                          						 WHERE dbo.hoa_don.ma_hoa_don = ?
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, ma);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(SanPhamChiTiet1.builder()
                        .id(rs.getInt("id_san_pham_chi_tiet"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .giaBan(rs.getInt("gia_ban"))
                        .mauSac(rs.getString("mau_sac"))
                        .chuoi(rs.getString("chuoi"))
                        .tayCam(rs.getString("tay_cam"))
                        .dauCoBan(rs.getString("dau_co_ban"))
                        .ngon(rs.getString("ngon"))
                        .ren(rs.getString("ren"))
                        .trongLuong(rs.getInt("trong_luong"))
                        .duongKinhDau(rs.getDouble("duong_kinh_dau"))
                        .thuongHieu(rs.getString("thuong_hieu"))
                        .baoHanh(rs.getInt("bao_hanh"))
                        .xuatXu(rs.getString("xuat_xu"))
                        .soLuong(rs.getInt("so_luong"))
                        .build());
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }
    }   
     
     public List<SanPhamChiTiet1> selectSPCT1() {
//     public List<SanPhamChiTiet1> selectSPCT() {
        List<SanPhamChiTiet1> list = new ArrayList<>();
        String sql = """
                     SELECT        dbo.san_pham.id, dbo.san_pham.ten_san_pham,  dbo.san_pham_chi_tiet.id AS id_san_pham_chi_tiet, dbo.san_pham_chi_tiet.ma_san_pham, dbo.san_pham_chi_tiet.so_luong, dbo.san_pham_chi_tiet.bao_hanh, dbo.san_pham_chi_tiet.gia_ban, dbo.chuoi.ten AS chuoi, 
                                              dbo.tay_cam.ten AS tay_cam, dbo.dau_co_ban.ten AS dau_co_ban, dbo.thuong_hieu.ten AS thuong_hieu, dbo.mau_sac.ten AS mau_sac, dbo.xuat_xu.ten AS xuat_xu, dbo.ngon.ten AS ngon, dbo.ren.ten AS ren, 
                                              dbo.san_pham_chi_tiet.duong_kinh_dau, dbo.san_pham_chi_tiet.trong_luong
                     FROM            dbo.san_pham INNER JOIN
                                              dbo.san_pham_chi_tiet ON dbo.san_pham.id = dbo.san_pham_chi_tiet.id_san_pham INNER JOIN
                                              dbo.chuoi ON dbo.san_pham_chi_tiet.chuoi = dbo.chuoi.id INNER JOIN
                                              dbo.dau_co_ban ON dbo.san_pham_chi_tiet.dau_co_ban = dbo.dau_co_ban.id INNER JOIN
                                              dbo.mau_sac ON dbo.san_pham_chi_tiet.mau_sac = dbo.mau_sac.id INNER JOIN
                                              dbo.ngon ON dbo.san_pham_chi_tiet.ngon = dbo.ngon.id INNER JOIN
                                              dbo.ren ON dbo.san_pham_chi_tiet.ren = dbo.ren.id INNER JOIN
                                              dbo.tay_cam ON dbo.san_pham_chi_tiet.tay_cam = dbo.tay_cam.id INNER JOIN
                                              dbo.thuong_hieu ON dbo.san_pham_chi_tiet.thuong_hieu = dbo.thuong_hieu.id INNER JOIN
                                              dbo.xuat_xu ON dbo.san_pham_chi_tiet.xuat_xu = dbo.xuat_xu.id
                                              						 order by ma_san_pham
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
//            stmt.setObject(1, ma);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(SanPhamChiTiet1.builder()
                        .id(rs.getInt("id_san_pham_chi_tiet"))
                        .tenSanPham(rs.getString("ten_san_pham"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .giaBan(rs.getInt("gia_ban"))
                        .mauSac(rs.getString("mau_sac"))
                        .chuoi(rs.getString("chuoi"))
                        .tayCam(rs.getString("tay_cam"))
                        .dauCoBan(rs.getString("dau_co_ban"))
                        .ngon(rs.getString("ngon"))
                        .ren(rs.getString("ren"))
                        .trongLuong(rs.getInt("trong_luong"))
                        .duongKinhDau(rs.getDouble("duong_kinh_dau"))
                        .thuongHieu(rs.getString("thuong_hieu"))
                        .baoHanh(rs.getInt("bao_hanh"))
                        .xuatXu(rs.getString("xuat_xu"))
                        .soLuong(rs.getInt("so_luong"))
                        .build());
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }
    }   
    
    @Override
    public List<SanPhamChiTiet> selectDeleted() {

        List<SanPhamChiTiet> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[id_san_pham]
                           ,[ma_san_pham]
                           ,[mo_ta]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[ngay_xoa]
                           ,[trang_thai]
                           ,[gia_ban]
                           ,[mau_sac]
                           ,[chuoi]
                           ,[tay_cam]
                           ,[dau_co_ban]
                           ,[ngon]
                           ,[ren]
                           ,[trong_luong]
                           ,[duong_kinh_dau]
                           ,[thuong_hieu]
                           ,[bao_hanh]
                           ,[xuat_xu]
                       FROM [dbo].[san_pham_chi_tiet]
                       where trang_thai = 0
                     
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(SanPhamChiTiet.builder()
                        .id(rs.getInt("id"))
                        .idSanPham(rs.getInt("id_san_pham"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .moTa(rs.getString("mo_ta"))
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .trangThai(rs.getInt("trang_thai"))
                        .giaBan(rs.getInt("gia_ban"))
                        .mauSac(rs.getInt("mau_sac"))
                        .chuoi(rs.getInt("chuoi"))
                        .tayCam(rs.getInt("tay_cam"))
                        .dauCoBan(rs.getInt("dau_co_ban"))
                        .ngon(rs.getInt("ngon"))
                        .ren(rs.getInt("ren"))
                        .trongLuong(rs.getInt("trong_luong"))
                        .duongKinhDau(rs.getDouble("duong_kinh_dau"))
                        .thuongHieu(rs.getInt("thuong_hieu"))
                        .baoHanh(rs.getInt("bao_hanh"))
                        .xuatXu(rs.getInt("xuat_xu"))
                        .build());
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }

    }

    public List<SanPhamChiTiet> selectByFilters(Integer giaFiler, Integer mauSacFilter, Integer xuatXuFilter, Integer thuongHieuFilter) {
        List<SanPhamChiTiet> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[id_san_pham]
                           ,[ma_san_pham]
                           ,[mo_ta]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[ngay_xoa]
                           ,[trang_thai]
                           ,[gia_ban]
                           ,[mau_sac]
                           ,[chuoi]
                           ,[tay_cam]
                           ,[dau_co_ban]
                           ,[ngon]
                           ,[ren]
                           ,[trong_luong]
                           ,[duong_kinh_dau]
                           ,[thuong_hieu]
                           ,[bao_hanh]
                           ,[xuat_xu]
                       FROM [dbo].[san_pham_chi_tiet]
                       where trang_thai = 1
                        
                     """;

        if (mauSacFilter != null) {
            sql.concat("\nand mau_sac = " + mauSacFilter);
        }
        if (thuongHieuFilter != null) {
            sql.concat("\nand thuong_hieu = " + thuongHieuFilter);
        }
        if (xuatXuFilter != null) {
            sql.concat("\nand xuat_xu = " + xuatXuFilter);
        }

        if (giaFiler != null) {
            if (giaFiler == 0) {
                sql.concat("\norder by gia_ban DESC");
            } else {
                sql.concat("\norder by gia_ban ASC");
            }
        }

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            System.out.println(sql);

            while (rs.next()) {
                list.add(SanPhamChiTiet.builder()
                        .id(rs.getInt("id"))
                        .idSanPham(rs.getInt("id_san_pham"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .moTa(rs.getString("mo_ta"))
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .trangThai(rs.getInt("trang_thai"))
                        .giaBan(rs.getInt("gia_ban"))
                        .mauSac(rs.getInt("mau_sac"))
                        .chuoi(rs.getInt("chuoi"))
                        .tayCam(rs.getInt("tay_cam"))
                        .dauCoBan(rs.getInt("dau_co_ban"))
                        .ngon(rs.getInt("ngon"))
                        .ren(rs.getInt("ren"))
                        .trongLuong(rs.getInt("trong_luong"))
                        .duongKinhDau(rs.getDouble("duong_kinh_dau"))
                        .thuongHieu(rs.getInt("thuong_hieu"))
                        .baoHanh(rs.getInt("bao_hanh"))
                        .xuatXu(rs.getInt("xuat_xu"))
                        .build());
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }

    }

    public List<SanPhamChiTiet> selectBySanPhamId(int id) {

        List<SanPhamChiTiet> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[id_san_pham]
                           ,[ma_san_pham]
                           ,[mo_ta]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[ngay_xoa]
                           ,[trang_thai]
                           ,[gia_ban]
                           ,[mau_sac]
                           ,[chuoi]
                           ,[tay_cam]
                           ,[dau_co_ban]
                           ,[ngon]
                           ,[ren]
                           ,[trong_luong]
                           ,[duong_kinh_dau]
                           ,[thuong_hieu]
                           ,[bao_hanh]
                           ,[xuat_xu]
                           ,[so_luong]
                       FROM [dbo].[san_pham_chi_tiet]
                       where id_san_pham = ?
                     
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(SanPhamChiTiet.builder()
                        .id(rs.getInt("id"))
                        .idSanPham(rs.getInt("id_san_pham"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .moTa(rs.getString("mo_ta"))
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .trangThai(rs.getInt("trang_thai"))
                        .giaBan(rs.getInt("gia_ban"))
                        .mauSac(rs.getInt("mau_sac"))
                        .chuoi(rs.getInt("chuoi"))
                        .tayCam(rs.getInt("tay_cam"))
                        .dauCoBan(rs.getInt("dau_co_ban"))
                        .ngon(rs.getInt("ngon"))
                        .ren(rs.getInt("ren"))
                        .trongLuong(rs.getInt("trong_luong"))
                        .duongKinhDau(rs.getDouble("duong_kinh_dau"))
                        .thuongHieu(rs.getInt("thuong_hieu"))
                        .baoHanh(rs.getInt("bao_hanh"))
                        .xuatXu(rs.getInt("xuat_xu"))
                        .soLuong(rs.getInt("so_luong"))
                        .build());
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }

    }

    public SanPhamChiTiet selectById(int id) {

        List<SanPhamChiTiet> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[id_san_pham]
                           ,[ma_san_pham]
                           ,[mo_ta]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[ngay_xoa]
                           ,[trang_thai]
                           ,[gia_ban]
                           ,[mau_sac]
                           ,[chuoi]
                           ,[tay_cam]
                           ,[dau_co_ban]
                           ,[ngon]
                           ,[ren]
                           ,[trong_luong]
                           ,[duong_kinh_dau]
                           ,[thuong_hieu]
                           ,[bao_hanh]
                           ,[xuat_xu]
                       FROM [dbo].[san_pham_chi_tiet]
                       where trang_thai = 1
                       and id = ?
                     
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return SanPhamChiTiet.builder()
                        .id(rs.getInt("id"))
                        .idSanPham(rs.getInt("id_san_pham"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .moTa(rs.getString("mo_ta"))
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .trangThai(rs.getInt("trang_thai"))
                        .giaBan(rs.getInt("gia_ban"))
                        .mauSac(rs.getInt("mau_sac"))
                        .chuoi(rs.getInt("chuoi"))
                        .tayCam(rs.getInt("tay_cam"))
                        .dauCoBan(rs.getInt("dau_co_ban"))
                        .ngon(rs.getInt("ngon"))
                        .ren(rs.getInt("ren"))
                        .trongLuong(rs.getInt("trong_luong"))
                        .duongKinhDau(rs.getDouble("duong_kinh_dau"))
                        .thuongHieu(rs.getInt("thuong_hieu"))
                        .baoHanh(rs.getInt("bao_hanh"))
                        .xuatXu(rs.getInt("xuat_xu"))
                        .build();
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }

    }

    public SanPhamChiTiet findDuplicate(SanPhamChiTiet sanPhamChiTiet) {

        SanPhamChiTiet found;
        String sql = """
                     SELECT [id]
                           ,[id_san_pham]
                           ,[ma_san_pham]
                           ,[mo_ta]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[ngay_xoa]
                           ,[trang_thai]
                           ,[gia_ban]
                           ,[mau_sac]
                           ,[chuoi]
                           ,[tay_cam]
                           ,[dau_co_ban]
                           ,[ngon]
                           ,[ren]
                           ,[trong_luong]
                           ,[duong_kinh_dau]
                           ,[thuong_hieu]
                           ,[bao_hanh]
                           ,[xuat_xu]
                           ,[ten_san_pham]
                           ,[so_luong]
                       FROM [dbo].[san_pham_chi_tiet]
                       WHERE id_san_pham = ?
                       and [mau_sac] = ?
                       and [chuoi] = ?
                       and [tay_cam] = ?
                       and [dau_co_ban] = ?
                       and [ngon] = ?
                       and [ren] = ?
                       and [thuong_hieu] = ?
                       and [xuat_xu] = ?
                       and [trong_luong] = ?
                       and [duong_kinh_dau] = ?
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, sanPhamChiTiet.getIdSanPham());
            stmt.setObject(2, sanPhamChiTiet.getMauSac());
            stmt.setObject(3, sanPhamChiTiet.getChuoi());
            stmt.setObject(4, sanPhamChiTiet.getTayCam());
            stmt.setObject(5, sanPhamChiTiet.getDauCoBan());
            stmt.setObject(6, sanPhamChiTiet.getNgon());
            stmt.setObject(7, sanPhamChiTiet.getRen());
            stmt.setObject(8, sanPhamChiTiet.getThuongHieu());
            stmt.setObject(9, sanPhamChiTiet.getXuatXu());
            stmt.setObject(10, sanPhamChiTiet.getTrongLuong());
            stmt.setObject(11, sanPhamChiTiet.getDuongKinhDau());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return SanPhamChiTiet.builder()
                        .id(rs.getInt("id"))
                        .trangThai(rs.getInt("trang_thai"))
                        .build();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }

    }

    @Override
    public boolean create(SanPhamChiTiet newSanPhamChiTiet) {
        String sql = """
                     INSERT INTO [dbo].[san_pham_chi_tiet]
                                ([id_san_pham]
                                ,[ma_san_pham]
                                ,[mo_ta]
                                ,[ngay_tao]
                                ,[ngay_cap_nhat]
                                ,[gia_ban]
                                ,[mau_sac]
                                ,[chuoi]
                                ,[tay_cam]
                                ,[dau_co_ban]
                                ,[ngon]
                                ,[ren]
                                ,[trong_luong]
                                ,[duong_kinh_dau]
                                ,[thuong_hieu]
                                ,[bao_hanh]
                                ,[xuat_xu]
                                ,[so_luong])
                          VALUES
                                (?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?)
                     
                     """;
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            stmt.setObject(1, newSanPhamChiTiet.getIdSanPham());
            stmt.setObject(2, newSanPhamChiTiet.getMaSanPham());
            stmt.setObject(3, newSanPhamChiTiet.getMoTa());
            stmt.setObject(4, DateConverter.getDateNow());
            stmt.setObject(5, DateConverter.getDateNow());
            stmt.setObject(6, newSanPhamChiTiet.getGiaBan());
            stmt.setObject(7, newSanPhamChiTiet.getMauSac());
            stmt.setObject(8, newSanPhamChiTiet.getChuoi());
            stmt.setObject(9, newSanPhamChiTiet.getTayCam());
            stmt.setObject(10, newSanPhamChiTiet.getDauCoBan());
            stmt.setObject(11, newSanPhamChiTiet.getNgon());
            stmt.setObject(12, newSanPhamChiTiet.getRen());
            stmt.setObject(13, newSanPhamChiTiet.getTrongLuong());
            stmt.setObject(14, newSanPhamChiTiet.getDuongKinhDau());
            stmt.setObject(15, newSanPhamChiTiet.getThuongHieu());
            stmt.setObject(16, newSanPhamChiTiet.getBaoHanh());
            stmt.setObject(17, newSanPhamChiTiet.getXuatXu());
            stmt.setObject(18, newSanPhamChiTiet.getSoLuong());

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Them that bai");
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Loi db");

        }
    }

    @Override
    public boolean update(SanPhamChiTiet sanPhamChiTiet, int id) {
        String sql = """
                     UPDATE [dbo].[san_pham_chi_tiet]
                        SET [id_san_pham] = ?
                           ,[ma_san_pham] = ?
                           ,[mo_ta] = ?
                           ,[ngay_cap_nhat] = ?
                           ,[gia_ban] = ?
                           ,[mau_sac] = ?
                           ,[chuoi] = ?
                           ,[tay_cam] = ?
                           ,[dau_co_ban] = ?
                           ,[ngon] = ?
                           ,[ren] = ?
                           ,[trong_luong] = ?
                           ,[duong_kinh_dau] = ?
                           ,[thuong_hieu] = ?
                           ,[bao_hanh] = ?
                           ,[xuat_xu] = ?
                           ,[so_luong] = ?
                      WHERE id = ?
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, sanPhamChiTiet.getIdSanPham());
            stmt.setObject(2, sanPhamChiTiet.getMaSanPham());
            stmt.setObject(3, sanPhamChiTiet.getMoTa());
            stmt.setObject(4, DateConverter.getDateNow());
            stmt.setObject(5, sanPhamChiTiet.getGiaBan());
            stmt.setObject(6, sanPhamChiTiet.getMauSac());
            stmt.setObject(7, sanPhamChiTiet.getChuoi());
            stmt.setObject(8, sanPhamChiTiet.getTayCam());
            stmt.setObject(9, sanPhamChiTiet.getDauCoBan());
            stmt.setObject(10, sanPhamChiTiet.getNgon());
            stmt.setObject(11, sanPhamChiTiet.getRen());
            stmt.setObject(12, sanPhamChiTiet.getTrongLuong());
            stmt.setObject(13, sanPhamChiTiet.getDuongKinhDau());
            stmt.setObject(14, sanPhamChiTiet.getThuongHieu());
            stmt.setObject(15, sanPhamChiTiet.getBaoHanh());
            stmt.setObject(16, sanPhamChiTiet.getXuatXu());
            stmt.setObject(17, sanPhamChiTiet.getSoLuong());
            stmt.setObject(18, id);

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Cap nhat that bai");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Loi db");

        }
    }

    @Override
    public boolean delete(int id) {
        String sql = """
                     UPDATE [dbo].[san_pham_chi_tiet]
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
    public boolean updateSoLuong(SanPhamChiTietResponse response) {
        
        int check = 0;
        String sql = """
                     UPDATE [dbo].[san_pham_chi_tiet]
                        SET 
                           [so_luong] = ?
                      WHERE id = ?
                     """;

        try(Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, response.getSoLuong());
            ps.setObject(2, response.getId());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean reinstate(int id) {
        String sql = """
                     UPDATE [dbo].[san_pham_chi_tiet]
                        SET [trang_thai] = 1
                      WHERE id = ?
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            stmt.setObject(1, id);

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Cap nhat that bai");
            }
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }
    }

}
