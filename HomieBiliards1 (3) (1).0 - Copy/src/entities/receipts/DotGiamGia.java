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
public class DotGiamGia {

    private int id;
    private String ma;
    private String ten;
    private String moTa;
    private double giamPhanTram;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Date ngayTao;
    private Date ngayCapNhat;
    private Date ngayXoa;
    private int trangThai;

}
