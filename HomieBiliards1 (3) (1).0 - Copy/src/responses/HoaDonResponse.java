/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responses;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Mtt
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HoaDonResponse {
    private Integer id;
    private String tenKhachHang;
    private String maKhachHang;
    private String tenTaiKhoan;
    private Date ngayTao;
    private String ngayCapNhat;
    private String ngayXoa;
    private Integer giaTri;
    private Integer soTienGiam;
    private Double giaTriTong;
    private String ghiChu;
    private Integer idTrangThaiHoaDon;
    private String trangThaiHoaDon;
    private Integer idGiamGiaHoaDon;
    private String maHoaDon;
    private String dienThoai;
    private Integer idKH;
    private Integer idTaiKhoan;
    private String maTK;
    private String cvTK;
    private String maGiamGiaHD;
    private String tenGiamGiaHD;
    private String diaChi;
}
