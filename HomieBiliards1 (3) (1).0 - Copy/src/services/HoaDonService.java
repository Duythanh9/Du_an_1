/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import config.DBConnect;
import entities.receipts.HoaDon;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import responses.HoaDonResponse;
import utils.DateConverter;

/**
 *
 * @author Mtt
 */
public class HoaDonService {
    
    public List<HoaDonResponse> selectNew() {
        List<HoaDonResponse> list = new ArrayList<>();
        String sql = """
SELECT        dbo.hoa_don.id
, dbo.khach_hang.ten_khach_hang
, dbo.tai_khoan.ho_ten AS ten_tai_khoan
, dbo.hoa_don.ngay_tao
, dbo.hoa_don.ngay_cap_nhat
, dbo.hoa_don.ngay_xoa
, dbo.hoa_don.gia_tri
, dbo.hoa_don.so_tien_giam
, dbo.hoa_don.gia_tri_tong
, dbo.hoa_don.ghi_chu
, dbo.hoa_don.id_giam_gia_hoa_don
, dbo.hoa_don.ma_hoa_don
, dbo.hoa_don.id_trang_thai_hoa_don AS trang_thai_hoa_don
, dbo.khach_hang.dien_thoai
, dbo.khach_hang.ma as ma_khach_hang
FROM            dbo.hoa_don LEFT OUTER JOIN
                         dbo.khach_hang ON dbo.hoa_don.id_khach_hang = dbo.khach_hang.id LEFT OUTER JOIN
                         dbo.tai_khoan ON dbo.hoa_don.id_tai_khoan = dbo.tai_khoan.id
						 where dbo.hoa_don.id_trang_thai_hoa_don=1
                     order by dbo.hoa_don.id desc
                     """;
        
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                list.add(HoaDonResponse.builder()
                        .id(rs.getInt("id"))
                        .tenKhachHang(rs.getString("ten_khach_hang"))
                        .tenTaiKhoan(rs.getString("ten_tai_khoan"))
                        .ngayTao(new Date())
                        .ngayCapNhat(DateConverter.dateToString(rs.getDate("ngay_cap_nhat")))
                        .giaTri(rs.getInt("gia_tri"))
                        .soTienGiam(rs.getInt("so_tien_giam"))
                        .giaTriTong(rs.getDouble("gia_tri_tong"))
                        .ghiChu(rs.getString("ghi_chu"))
                        .idTrangThaiHoaDon(rs.getInt("trang_thai_hoa_don"))
                        .idGiamGiaHoaDon(rs.getInt("id_giam_gia_hoa_don"))
                        .maHoaDon(rs.getString("ma_hoa_don"))
                        .dienThoai(rs.getString("dien_thoai"))
                        .maKhachHang(rs.getString("ma_khach_hang"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
            
        }
    }
}
