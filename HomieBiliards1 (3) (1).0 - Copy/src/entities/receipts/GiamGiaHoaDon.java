/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities.receipts;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class GiamGiaHoaDon {

 
    private Integer id;
    private String ma;
    private String ten;
    private String moTa;
    private Integer giaTriToiThieu;
    private Integer giaTriToiDa;
    private int giamPhanTram;
    private Integer giamSoToiDa;
    private Integer soLuong;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Date ngayTao;
    private Date ngayCapNhat;
    private Date ngayXoa;
    private int trangThai;
    private boolean loaiGiamGia;
//    private String trangThaitext;
   
}
