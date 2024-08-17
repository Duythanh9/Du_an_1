/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils.validators.sanPham;

import entities.products.SanPham;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import repositories.products.SanPhamRepository;
import responses.ThuocTinhResponse;
import services.ThuocTinhService;
import utils.MaGenerator;

/**
 *
 * @author Mtt
 */
public class SanPhamValidator {
    
    public static boolean validateCreate(SanPham sanPham) {
                
        if (sanPham.getTenSanPham() == null) {
            throw new RuntimeException("Ten phai duoi 100 ky tu, khong co ky tu dac biet");
        }
        
        if (sanPham.getTenSanPham().isEmpty() || sanPham.getTenSanPham().length() > 100) {
            throw new RuntimeException("Ten phai duoi 100 ky tu, khong co ky tu dac biet");
        }
        
        Pattern pattern = Pattern.compile("[/,:<>!~@#$%^&()+=?()\"|!\\[#$-]");
        Matcher patternMatcher = pattern.matcher(sanPham.getTenSanPham());
        if (patternMatcher.find() == true) {
            throw new RuntimeException("Ten phai duoi 100 ky tu, khong co ky tu dac biet");
        }
        
        List<SanPham> compare = new SanPhamRepository().selectAll();
        
        for (SanPham item : compare) {
            if (sanPham.getTenSanPham().equals(item.getTenSanPham())) {
                throw new RuntimeException("Trùng tên sản phẩm");
            }
        }
        
        for (SanPham item : compare) {
            if (sanPham.getMaSanPham().equals(item.getMaSanPham())) {
                sanPham.setMaSanPham(MaGenerator.generate("SP"));
                return validateCreate(sanPham);
            }
        }
        
        return true;
    }
    
    public static boolean validateUpdate(SanPham sanPham) {
        
        if (sanPham.getTenSanPham() == null) {
            throw new RuntimeException("Ten phai duoi 100 ky tu, khong co ky tu dac biet");
        }
        
        if (sanPham.getTenSanPham().isEmpty() || sanPham.getTenSanPham().length() > 100) {
            throw new RuntimeException("Ten phai duoi 100 ky tu, khong co ky tu dac biet");
        }
        
        Pattern pattern = Pattern.compile("[/,:<>!~@#$%^&()+=?()\"|!\\[#$-]");
        Matcher patternMatcher = pattern.matcher(sanPham.getTenSanPham());
        if (patternMatcher.find() == true) {
            throw new RuntimeException("Ten phai duoi 100 ky tu, khong co ky tu dac biet");
        }
        
        List<SanPham> compare = new SanPhamRepository().selectAll();
        
        for (SanPham item : compare) {
            if (!sanPham.getMaSanPham().equals(item.getMaSanPham()) && sanPham.getTenSanPham().equals(item.getTenSanPham())) {
                throw new RuntimeException("Trùng tên sản phẩm");
            }
        }
        return true;
    }
}
