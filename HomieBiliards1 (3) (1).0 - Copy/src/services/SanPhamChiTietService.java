/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import config.DBConnect;
import entities.products.SanPham;
import entities.products.SanPhamChiTiet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import repositories.products.ChuoiRepository;
import repositories.products.SanPhamChiTietRepository;
import repositories.products.SanPhamRepository;
import responses.SanPhamChiTietResponse;

/**
 *
 * @author Mtt
 */
public class SanPhamChiTietService {

    public List<SanPhamChiTietResponse> selectAndPopulateAllSanPhamChiTiet() {

        List<SanPhamChiTietResponse> list = new ArrayList<>();
        String sql = """
SELECT        dbo.san_pham_chi_tiet.id as id
                                          , dbo.san_pham.ten_san_pham as ten_san_pham
                                          , dbo.san_pham_chi_tiet.ma_san_pham as ma_san_pham
                                          , dbo.san_pham_chi_tiet.mo_ta as mo_ta
                                          , dbo.thuong_hieu.ten as thuong_hieu
                                          , dbo.xuat_xu.ten AS xuat_xu
                                          , dbo.mau_sac.ten AS mau_sac
                                          , dbo.chuoi.ten AS chuoi
                                          , dbo.tay_cam.ten AS tay_cam
                                          , dbo.dau_co_ban.ten AS dau_co_ban
                                          , dbo.ngon.ten AS ngon
                                          , dbo.ren.ten AS ren
                                          , dbo.san_pham_chi_tiet.trong_luong as trong_luong
                                          , dbo.san_pham_chi_tiet.duong_kinh_dau as duong_kinh_dau
                                          , dbo.san_pham_chi_tiet.bao_hanh as bao_hanh
                                          , dbo.san_pham_chi_tiet.gia_ban as gia_ban
                                            , dbo.san_pham_chi_tiet.so_luong as so_luong
                                            , dbo.san_pham_chi_tiet.trang_thai as trang_thai
                                          FROM            dbo.san_pham_chi_tiet INNER JOIN
                                                                   dbo.san_pham ON dbo.san_pham_chi_tiet.id_san_pham = dbo.san_pham.id LEFT JOIN
                                                                   dbo.chuoi ON dbo.san_pham_chi_tiet.chuoi = dbo.chuoi.id LEFT JOIN
                                                                   dbo.dau_co_ban ON dbo.san_pham_chi_tiet.dau_co_ban = dbo.dau_co_ban.id LEFT JOIN
                                                                   dbo.mau_sac ON dbo.san_pham_chi_tiet.mau_sac = dbo.mau_sac.id LEFT JOIN
                                                                   dbo.ngon ON dbo.san_pham_chi_tiet.ngon = dbo.ngon.id LEFT JOIN
                                                                   dbo.ren ON dbo.san_pham_chi_tiet.ren = dbo.ren.id LEFT JOIN
                                                                   dbo.tay_cam ON dbo.san_pham_chi_tiet.tay_cam = dbo.tay_cam.id LEFT JOIN
                                                                   dbo.thuong_hieu ON dbo.san_pham_chi_tiet.thuong_hieu = dbo.thuong_hieu.id LEFT JOIN
                                                                   dbo.xuat_xu ON dbo.san_pham_chi_tiet.xuat_xu = dbo.xuat_xu.id
                     order by id desc
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(SanPhamChiTietResponse.builder()
                        .id(rs.getInt("id"))
                        .tenSanPham(rs.getString("ten_san_pham"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .moTa(rs.getString("mo_ta"))
                        .thuongHieu(rs.getString("thuong_hieu"))
                        .xuatXu(rs.getString("xuat_xu"))
                        .mauSac(rs.getString("mau_sac"))
                        .chuoi(rs.getString("chuoi"))
                        .tayCam(rs.getString("tay_cam"))
                        .dauCoBan(rs.getString("dau_co_ban"))
                        .ngon(rs.getString("ngon"))
                        .ren(rs.getString("ren"))
                        .trongLuong(rs.getInt("trong_luong"))
                        .duongKinhDau(rs.getFloat("duong_kinh_dau"))
                        .baoHanh(rs.getInt("bao_hanh"))
                        .giaBan(rs.getInt("gia_ban"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .soLuong(rs.getInt("so_luong"))
                        .trangThai(rs.getInt("trang_thai"))
                        .build());
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }

    }

    public List<SanPhamChiTietResponse> selectAndPopulateSanPhamChiTietByStatus(Integer status) {
        List<SanPhamChiTietResponse> list = new ArrayList<>();
        String sql = """
                     SELECT        dbo.san_pham_chi_tiet.id as id
                                          , dbo.san_pham.ten_san_pham as ten_san_pham
                                          , dbo.san_pham_chi_tiet.ma_san_pham as ma_san_pham
                                          , dbo.san_pham_chi_tiet.mo_ta as mo_ta
                                          , dbo.thuong_hieu.ten as thuong_hieu
                                          , dbo.xuat_xu.ten AS xuat_xu
                                          , dbo.mau_sac.ten AS mau_sac
                                          , dbo.chuoi.ten AS chuoi
                                          , dbo.tay_cam.ten AS tay_cam
                                          , dbo.dau_co_ban.ten AS dau_co_ban
                                          , dbo.ngon.ten AS ngon
                                          , dbo.ren.ten AS ren
                                          , dbo.san_pham_chi_tiet.trong_luong as trong_luong
                                          , dbo.san_pham_chi_tiet.duong_kinh_dau as duong_kinh_dau
                                          , dbo.san_pham_chi_tiet.bao_hanh as bao_hanh
                                          , dbo.san_pham_chi_tiet.gia_ban as gia_ban
                                            , dbo.san_pham_chi_tiet.so_luong as so_luong
                                            , dbo.san_pham_chi_tiet.trang_thai as trang_thai
                                          FROM            dbo.san_pham_chi_tiet INNER JOIN
                                                                   dbo.san_pham ON dbo.san_pham_chi_tiet.id_san_pham = dbo.san_pham.id LEFT JOIN
                                                                   dbo.chuoi ON dbo.san_pham_chi_tiet.chuoi = dbo.chuoi.id LEFT JOIN
                                                                   dbo.dau_co_ban ON dbo.san_pham_chi_tiet.dau_co_ban = dbo.dau_co_ban.id LEFT JOIN
                                                                   dbo.mau_sac ON dbo.san_pham_chi_tiet.mau_sac = dbo.mau_sac.id LEFT JOIN
                                                                   dbo.ngon ON dbo.san_pham_chi_tiet.ngon = dbo.ngon.id LEFT JOIN
                                                                   dbo.ren ON dbo.san_pham_chi_tiet.ren = dbo.ren.id LEFT JOIN
                                                                   dbo.tay_cam ON dbo.san_pham_chi_tiet.tay_cam = dbo.tay_cam.id LEFT JOIN
                                                                   dbo.thuong_hieu ON dbo.san_pham_chi_tiet.thuong_hieu = dbo.thuong_hieu.id LEFT JOIN
                                                                   dbo.xuat_xu ON dbo.san_pham_chi_tiet.xuat_xu = dbo.xuat_xu.id
                     """;

        if (status != null) {
            if (status == 1) {
                sql = sql.concat("\nwhere dbo.san_pham_chi_tiet.trang_thai = 1");
            }
            if (status == 0) {
                sql = sql.concat("\nwhere dbo.san_pham_chi_tiet.trang_thai = 0");
            }
        }

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(SanPhamChiTietResponse.builder()
                        .id(rs.getInt("id"))
                        .tenSanPham(rs.getString("ten_san_pham"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .moTa(rs.getString("mo_ta"))
                        .thuongHieu(rs.getString("thuong_hieu"))
                        .xuatXu(rs.getString("xuat_xu"))
                        .mauSac(rs.getString("mau_sac"))
                        .chuoi(rs.getString("chuoi"))
                        .tayCam(rs.getString("tay_cam"))
                        .dauCoBan(rs.getString("dau_co_ban"))
                        .ngon(rs.getString("ngon"))
                        .ren(rs.getString("ren"))
                        .trongLuong(rs.getInt("trong_luong"))
                        .duongKinhDau(rs.getFloat("duong_kinh_dau"))
                        .baoHanh(rs.getInt("bao_hanh"))
                        .giaBan(rs.getInt("gia_ban"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .soLuong(rs.getInt("so_luong"))
                        .trangThai(rs.getInt("trang_thai"))
                        .build());
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }

    }

    public List<SanPhamChiTietResponse> selectAndPopulateSanPhamChiTietByFilter(Integer giaOrder, String mauSacFilter, String xuatXuFilter, String thuongHieuFilter, Integer giaMin, Integer giaMax, Integer trangThaiFilter) {

        List<SanPhamChiTietResponse> list = new ArrayList<>();
        String sql = """
                     SELECT        dbo.san_pham_chi_tiet.id as id
                     , dbo.san_pham.ten_san_pham as ten_san_pham
                     , dbo.san_pham_chi_tiet.ma_san_pham as ma_san_pham
                     , dbo.san_pham_chi_tiet.mo_ta as mo_ta
                     , dbo.thuong_hieu.ten as thuong_hieu
                     , dbo.xuat_xu.ten AS xuat_xu
                     , dbo.mau_sac.ten AS mau_sac
                     , dbo.chuoi.ten AS chuoi
                     , dbo.tay_cam.ten AS tay_cam
                     , dbo.dau_co_ban.ten AS dau_co_ban
                     , dbo.ngon.ten AS ngon
                     , dbo.ren.ten AS ren
                     , dbo.san_pham_chi_tiet.trong_luong as trong_luong
                     , dbo.san_pham_chi_tiet.duong_kinh_dau as duong_kinh_dau
                     , dbo.san_pham_chi_tiet.bao_hanh as bao_hanh
                     , dbo.san_pham_chi_tiet.gia_ban as gia_ban
                     , dbo.san_pham_chi_tiet.so_luong as so_luong
                     , dbo.san_pham_chi_tiet.trang_thai as trang_thai
                     FROM            dbo.san_pham_chi_tiet INNER JOIN
                                              dbo.san_pham ON dbo.san_pham_chi_tiet.id_san_pham = dbo.san_pham.id LEFT JOIN
                                              dbo.chuoi ON dbo.san_pham_chi_tiet.chuoi = dbo.chuoi.id LEFT JOIN
                                              dbo.dau_co_ban ON dbo.san_pham_chi_tiet.dau_co_ban = dbo.dau_co_ban.id LEFT JOIN
                                              dbo.mau_sac ON dbo.san_pham_chi_tiet.mau_sac = dbo.mau_sac.id LEFT JOIN
                                              dbo.ngon ON dbo.san_pham_chi_tiet.ngon = dbo.ngon.id LEFT JOIN
                                              dbo.ren ON dbo.san_pham_chi_tiet.ren = dbo.ren.id LEFT JOIN
                                              dbo.tay_cam ON dbo.san_pham_chi_tiet.tay_cam = dbo.tay_cam.id LEFT JOIN
                                              dbo.thuong_hieu ON dbo.san_pham_chi_tiet.thuong_hieu = dbo.thuong_hieu.id LEFT JOIN
                                              dbo.xuat_xu ON dbo.san_pham_chi_tiet.xuat_xu = dbo.xuat_xu.id
                     """;
        sql = sql.concat("\nwhere dbo.san_pham_chi_tiet.gia_ban between " + giaMin + " and " + giaMax);

        if (trangThaiFilter != null) {
            sql = sql.concat("\nand dbo.san_pham_chi_tiet.trang_thai = " + trangThaiFilter);
        }


        if (mauSacFilter != null) {
            sql = sql.concat("\nand dbo.mau_sac.ten like N'%" + mauSacFilter + "%'");
        }
        if (thuongHieuFilter != null) {
            sql = sql.concat("\nand dbo.thuong_hieu.ten like N'%" + thuongHieuFilter + "%'");
        }
        if (xuatXuFilter != null) {
            sql = sql.concat("\nand dbo.xuat_xu.ten like N'%" + xuatXuFilter + "%'");
        }

        if (giaOrder != null) {
            if (giaOrder == 1) {
                sql = sql.concat("\norder by gia_ban DESC");
            } else {
                sql = sql.concat("\norder by gia_ban ASC");
            }
        }

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            

            ResultSet rs = stmt.executeQuery();

            System.err.println(sql);
            while (rs.next()) {
                list.add(SanPhamChiTietResponse.builder()
                        .id(rs.getInt("id"))
                        .tenSanPham(rs.getString("ten_san_pham"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .moTa(rs.getString("mo_ta"))
                        .thuongHieu(rs.getString("thuong_hieu"))
                        .xuatXu(rs.getString("xuat_xu"))
                        .mauSac(rs.getString("mau_sac"))
                        .chuoi(rs.getString("chuoi"))
                        .tayCam(rs.getString("tay_cam"))
                        .dauCoBan(rs.getString("dau_co_ban"))
                        .ngon(rs.getString("ngon"))
                        .ren(rs.getString("ren"))
                        .trongLuong(rs.getInt("trong_luong"))
                        .duongKinhDau(rs.getFloat("duong_kinh_dau"))
                        .baoHanh(rs.getInt("bao_hanh"))
                        .giaBan(rs.getInt("gia_ban"))
                        .trangThai(rs.getInt("trang_thai"))
                        .build());
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }
    }

}
