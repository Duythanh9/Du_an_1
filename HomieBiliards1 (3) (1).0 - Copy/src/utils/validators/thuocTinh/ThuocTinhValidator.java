/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils.validators.thuocTinh;

import entities.products.Chuoi;
import entities.products.DauCoBan;
import entities.products.MauSac;
import entities.products.Ngon;
import entities.products.Ren;
import entities.products.TayCam;
import entities.products.ThuocTinh;
import entities.products.ThuongHieu;
import entities.products.XuatXu;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import repositories.products.ChuoiRepository;
import repositories.products.DauCoBanRepository;
import repositories.products.MauSacRepository;
import repositories.products.NgonRepository;
import repositories.products.RenRepository;
import repositories.products.TayCamRepository;
import repositories.products.ThuongHieuRepository;
import repositories.products.XuatXuRepository;
import responses.ThuocTinhResponse;
import services.ThuocTinhService;
import utils.MaGenerator;

/**
 *
 * @author Mtt
 */
public class ThuocTinhValidator {

    public static boolean validate(String newName, String table) throws Exception {
        ThuocTinh newTt;
        switch (table) {
            case "chuoi":
                newTt = Chuoi.builder().ma(MaGenerator.generate("CH")).ten(newName).build();
                break;
            case "tay_cam":
                newTt = TayCam.builder().ma(MaGenerator.generate("TC")).ten(newName).build();
                break;
            case "xuat_xu":
                newTt = XuatXu.builder().ma(MaGenerator.generate("XX")).ten(newName).build();
                break;
            case "dau_co_ban":
                newTt = DauCoBan.builder().ma(MaGenerator.generate("DBC")).ten(newName).build();
                break;
            case "ngon":
                newTt = Ngon.builder().ma(MaGenerator.generate("NG")).ten(newName).build();
                break;
            case "ren":
                newTt = Ren.builder().ma(MaGenerator.generate("RN")).ten(newName).build();
                break;
            case "thuong_hieu":
                newTt = ThuongHieu.builder().ma(MaGenerator.generate("TH")).ten(newName).build();
                break;
            case "mau_sac":
                newTt = MauSac.builder().ma(MaGenerator.generate("MS")).ten(newName).build();
                break;
            default:
                throw new RuntimeException("Thuộc tính không tồn tại");
        }

        if (newTt.getTen() == null) {
            throw new RuntimeException("Ten phai duoi 100 ky tu, khong co ky tu dac biet");
        }

        if (newTt.getTen().isEmpty() || newTt.getTen().length() > 100) {
            throw new RuntimeException("Ten phai duoi 100 ky tu, khong co ky tu dac biet");
        }

        Pattern pattern = Pattern.compile("[/,:<>!~@#$%^&()+=?()\"|!\\[#$-]");
        Matcher patternMatcher = pattern.matcher(newName);
        if (patternMatcher.find() == true) {
            throw new RuntimeException("Ten phai duoi 100 ky tu, khong co ky tu dac biet");
        }

        List<ThuocTinhResponse> compare = new ThuocTinhService().selectAllByTable(table);

        for (ThuocTinhResponse item : compare) {
            if (newTt.getTen().equals(item.getTen())) {
                throw new RuntimeException("Ten trung");
            }
        }

        for (ThuocTinhResponse item : compare) {
            if (newTt.getMa().equals(item.getMa())) {
                return ThuocTinhValidator.validate(newTt.getTen(), table);
            }
        }

        return true;
    }
}
