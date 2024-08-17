/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities.products;

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
public class Ngon extends ThuocTinh{
    private int id;
    private String ten;
    String ma;
    public String getTen() {
        return this.ten;
    }

    public int getId() {
        return this.id;
    }

    public String getMa() {
        return this.ma;
    }
}
