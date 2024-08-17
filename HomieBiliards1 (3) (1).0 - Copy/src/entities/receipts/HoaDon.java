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

/**
 *
 * @author Mtt
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HoaDon {

    private Integer id;
    private Integer idKhachHang;
    private String maHoaDon;
    private Integer idTaiKhoan;
    private Date ngayTao;
    private Date ngayCapNhat;
    private Date ngayXoa;
    private Integer giaTri;
    private Integer soTienGiam;
    private Integer giaTriTong;
    private String ghiChu;
    private Integer idGiamGiaHoaDon;
    private Integer idTrangThaiHoaDon;
    private  int trangThaiHoaDon;

}
