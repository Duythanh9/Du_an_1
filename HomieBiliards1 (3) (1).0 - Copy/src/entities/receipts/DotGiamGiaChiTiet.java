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
public class DotGiamGiaChiTiet {

    private int id;
    private int idDotGiamGia;
    private int idSanPhamChiTiet;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private double giamPhanTramTrungBinh;
    private int trangThai;

}
