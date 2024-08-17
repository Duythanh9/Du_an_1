/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities.subjects;

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
public class KhachHang {

    private Integer id;
    private String ma;
    private String tenKhachHang;
    private String email;
    private String dienThoai;
    private String diaChi;
    private String ghiChu;
    private Date ngayTao;
    private Date ngayCapNhat;
    private Integer nguoiTao;
    private Integer nguoiCapNhat;
    private Date ngayXoa;
    private Integer trangThai;
}
