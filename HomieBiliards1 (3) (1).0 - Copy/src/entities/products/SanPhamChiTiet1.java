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
public class SanPhamChiTiet1 {
    private int id;
    private String maSanPham;
    private String tenSanPham;
    private int giaBan;
    private String mauSac;
    private String  chuoi;
    private String  tayCam;
    private String  dauCoBan;
    private String  ngon;
    private String  ren;
    private int trongLuong;
    private double duongKinhDau;
    private String  thuongHieu;
    private int baoHanh;
    private String  xuatXu;
    private int soLuong;
}
