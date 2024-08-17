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
import lombok.ToString;

/**
 *
 * @author Nhu Quynh
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter 
@Setter 
@ToString
@Builder
public class HoaDonChiTietResponse {
    
    private Integer id;
    
    private String maHD;
    
    private Integer idHD;
    
    private Integer idSPCT;
    
    private Integer soLuong;
    
    private Integer giaBan;
    
    private Integer trangThai;
    
    private Double thanhTien;
    
    private String maSP;
    
    private String tenSP;
    
    private String moTa;
    
    private Date ngayTao;
    
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
    
}
