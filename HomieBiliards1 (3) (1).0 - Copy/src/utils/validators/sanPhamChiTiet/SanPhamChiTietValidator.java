package utils.validators.sanPhamChiTiet;

import entities.products.SanPhamChiTiet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import repositories.products.SanPhamChiTietRepository;
import utils.MaGenerator;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Mtt
 */
public class SanPhamChiTietValidator {

    public static boolean validateUpdate(SanPhamChiTiet sanPhamChiTiet) {
        Pattern pattern = Pattern.compile("[/,:<>!~@#$%^&()+=?()\"|!\\[#$-]");

        //text
        String mota = sanPhamChiTiet.getMoTa();

        if (mota != null && !mota.isEmpty()) {
            Matcher patternMoTaMatcher = pattern.matcher(mota);
            if (patternMoTaMatcher.find() == true) {
                throw new RuntimeException("Mô tả không được có ký tự đặc biệt");
            }
        }

        //nums
        int giaBan = sanPhamChiTiet.getGiaBan();

        if (giaBan < 0) {
            throw new RuntimeException("Giá bán phải là số lớn hơn 0");
        }

        int trongLuong = sanPhamChiTiet.getTrongLuong();

        if (trongLuong < 0) {
            throw new RuntimeException("Trọng lượng phải là số lớn hơn 0");
        }

        int baoHanh = sanPhamChiTiet.getBaoHanh();

        if (baoHanh < 0) {
            throw new RuntimeException("Bảo hành phải là số lớn hơn 0");
        }

        double duongKinhDau = sanPhamChiTiet.getDuongKinhDau();

        if (duongKinhDau < 0) {
            throw new RuntimeException("Đường kính đầu phải là số lớn hơn 0");
        }

        int soLuong = sanPhamChiTiet.getSoLuong();
        if (soLuong < 0) {
            throw new RuntimeException("Số lượng phải từ 0 trở lên");
        }

        return true;
    }

    public static boolean validateCreate(SanPhamChiTiet sanPhamChiTiet) {
        Pattern pattern = Pattern.compile("[/,:<>!~@#$%^&()+=?()\"|!\\[#$-]");

        String mota = sanPhamChiTiet.getMoTa();
        //text
        if (mota != null && !mota.isEmpty()) {
            Matcher patternMoTaMatcher = pattern.matcher(mota);
            if (patternMoTaMatcher.find() == true) {
                throw new RuntimeException("Mô tả không được có ký tự đặc biệt");
            }
        }

        

        //nums
        int giaBan = sanPhamChiTiet.getGiaBan();

        if (giaBan < 0) {
            throw new RuntimeException("Giá bán phải là số lớn hơn 0");
        }

        int trongLuong = sanPhamChiTiet.getTrongLuong();

        if (trongLuong < 0) {
            throw new RuntimeException("Trọng lượng phải là số lớn hơn 0");
        }

        int baoHanh = sanPhamChiTiet.getBaoHanh();

        if (baoHanh < 0) {
            throw new RuntimeException("Bảo hành phải là số lớn hơn 0");
        }

        double duongKinhDau = sanPhamChiTiet.getDuongKinhDau();

        if (duongKinhDau < 0) {
            throw new RuntimeException("Đường kính đầu phải là số lớn hơn 0");
        }

        int soLuong = sanPhamChiTiet.getSoLuong();
        if (soLuong < 0) {
            throw new RuntimeException("Số lượng phải từ 0 trở lên");
        }

        List<SanPhamChiTiet> compare = new SanPhamChiTietRepository().selectAll();
        
        for (SanPhamChiTiet item : compare) {
            if (item.getMaSanPham().equals(sanPhamChiTiet.getMaSanPham())) {
                sanPhamChiTiet.setMaSanPham(MaGenerator.generate("SPCT"));
                return validateCreate(sanPhamChiTiet);
            }
        }
        return true;
    }

    

}
