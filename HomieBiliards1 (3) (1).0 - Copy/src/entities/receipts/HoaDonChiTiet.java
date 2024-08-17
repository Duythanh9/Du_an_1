/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities.receipts;

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
public class HoaDonChiTiet {

    private int id;
    private int idHoaDon;
    private int idSanPhamChiTiet;
    private int soLuong;
    private int giaBan;
    private int trangThai;
}
