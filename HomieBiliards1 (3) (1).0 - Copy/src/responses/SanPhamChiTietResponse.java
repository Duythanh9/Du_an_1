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
public class SanPhamChiTietResponse {

    private Integer id;
    private String maSanPham;
    private String tenSanPham;
    private String moTa;
    private Date ngayTao;
    private Integer giaBan;
    private String mauSac;
    private String chuoi;
    private String tayCam;
    private String dauCoBan;
    private String ngon;
    private String ren;
    private Integer trongLuong;
    private float duongKinhDau;
    private String thuongHieu;
    private Integer baoHanh;
    private String xuatXu;
    private Integer soLuong;
    private Integer trangThai;

}
