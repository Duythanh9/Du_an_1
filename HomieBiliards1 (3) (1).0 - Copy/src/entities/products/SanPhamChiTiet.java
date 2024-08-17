/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities.products;

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
public class SanPhamChiTiet {
    private int id;
    private int idSanPham;
    private String maSanPham;
    private String moTa;
    private Date ngayTao;
    private Date ngayCapNhat;
    private Date ngayXoa;
    private int trangThai;
    private int giaBan;
    private int mauSac;
    private int chuoi;
    private int tayCam;
    private int dauCoBan;
    private int ngon;
    private int ren;
    private int trongLuong;
    private double duongKinhDau;
    private int thuongHieu;
    private int baoHanh;
    private int xuatXu;
    private int soLuong;
}
