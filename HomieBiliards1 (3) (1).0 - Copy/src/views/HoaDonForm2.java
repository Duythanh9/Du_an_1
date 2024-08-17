/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import entities.receipts.HoaDon;
import entities.subjects.KhachHang;
import entities.subjects.TaiKhoan;
import entities.products.SanPhamChiTiet;
import entities.products.SanPhamChiTiet1;
import entities.products.SanPham;
import entities.receipts.GiamGiaHoaDon;
import entities.receipts.LichSuHoaDon;
import entities.receipts.ThanhToan;
import entities.receipts.TrangThaiHoaDon;
import entities.receipts.HoaDonChiTiet;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import repositories.receipts.HoaDonRepository;
import repositories.receipts.HoaDonChiTietRepository;
import repositories.receipts.LichSuHoaDonRepository;
import repositories.products.SanPhamRepository;
import repositories.products.SanPhamChiTietRepository;
import repositories.receipts.LichSuHoaDonRepository;
import repositories.receipts.Rp_GiamGiaHD;
import repositories.receipts.ThanhToanRepository;
import repositories.subjects.KhachHangRepository;
import repositories.subjects.TaiKhoanRepository;
import utils.DateConverter;

/**
 *
 * @author Mtt
 */
public class HoaDonForm2 extends javax.swing.JPanel {

    private List<HoaDon> listHoaDon = new ArrayList<>();
    private List<HoaDonChiTiet> listHoaDonCT = new ArrayList<>();
    private List<SanPhamChiTiet1> listSPCT = new ArrayList<>();
    private HoaDonRepository reHoaDon = new HoaDonRepository();
    private HoaDonChiTietRepository reHoaDonCT = new HoaDonChiTietRepository();
    private List<LichSuHoaDon> lsHoaDon = new ArrayList<>();
    private LichSuHoaDonRepository relsHoaDon = new LichSuHoaDonRepository();
    private Rp_GiamGiaHD reGGHoaDon = new Rp_GiamGiaHD();
    private SanPhamChiTietRepository reSPCT = new SanPhamChiTietRepository();

    /**
     * Creates new form Welcome
     */
    public HoaDonForm2() {
        initComponents();
        fillHoaDon();
//        fillIndexSelected();
    }

    public void fillHoaDon() {
        listHoaDon = reHoaDon.selectAll();
        String hienThi[] = {"STT", "Mã hóa đơn", "Mã khách hàng", "Mã nhân viên", "Ngày tạo", "Ngày cập nhật", "Ngày xóa", "Giá trị", "Số tiền giảm", "Giá trị tổng", "Trạng thái hóa đơn", "Chương trình giảm giá"};
        DefaultTableModel modelHoaDon = new DefaultTableModel(hienThi, 0);
        modelHoaDon.setRowCount(0);
        int i = 1;
        if (txtTimKiem.getText().trim().equalsIgnoreCase("")) {
            for (HoaDon hd : listHoaDon) {
                Object[] row = {i, hd.getMaHoaDon(), MaKH(hd.getIdKhachHang()), MaTK(hd.getIdTaiKhoan()), hd.getNgayTao(), hd.getNgayCapNhat(), hd.getNgayXoa(), hd.getGiaTri(), hd.getSoTienGiam(), hd.getGiaTriTong(), TTHD(hd.getIdTrangThaiHoaDon()), MaGG(hd.getIdGiamGiaHoaDon())};
                modelHoaDon.addRow(row);
                i++;
            }
            tblHoaDon.setModel(modelHoaDon);
        } else {
            for (HoaDon hd : listHoaDon) {
                if (hd.getIdTrangThaiHoaDon().toString().contains(txtTimKiem.getText()) || hd.getMaHoaDon().toString().contains(txtTimKiem.getText()) || hd.getIdKhachHang().toString().contains(txtTimKiem.getText()) || hd.getIdTaiKhoan().toString().contains(txtTimKiem.getText())) {
//                if (hd.getTrangThaiHoaDon().contains(txtTimKiem.getText()) || hd.getMaHoaDon().contains(txtTimKiem.getText()) || hd.getMaKhachHang().contains(txtTimKiem.getText()) || hd.getMaTaiKhoan().contains(txtTimKiem.getText())) {
                    Object[] row = {i, hd.getMaHoaDon(), MaKH(hd.getIdKhachHang()), MaTK(hd.getIdTaiKhoan()), hd.getNgayTao(), hd.getNgayCapNhat(), hd.getNgayXoa(), hd.getGiaTri(), hd.getSoTienGiam(), hd.getGiaTriTong(), TTHD(hd.getIdTrangThaiHoaDon()), MaGG(hd.getIdGiamGiaHoaDon())};
                    modelHoaDon.addRow(row);
                    i++;
                }
            }
            tblHoaDon.setModel(modelHoaDon);
        }
    }

    public String MaGG(int i){
        String ten = "";
        ten = reGGHoaDon.GG(i);
        return ten;
    }
    
    public String MaTK(int i) {
        String ma = "";
        if (i < 10) {
            ma = "TK00" + i;
        } else if (i < 100) {
            ma = "TK0" + i;
        } else if (i < 1000) {
            ma = "TK" + i;
        }
        return ma;
    }

    public String MaKH(int i) {
        String ma = "";
        if (i < 10) {
            ma = "KH00" + i;
        } else if (i < 100) {
            ma = "KH0" + i;
        } else if (i < 1000) {
            ma = "KH" + i;
        }
        return ma;
    }

    public String TTHD(int i) {
        String tt = "";
        if (i == 1) {
            tt = "Chưa thanh toán";
        } else if (i == 2) {
            tt = "Đã thanh toán";
        } else if (i < 1000) {
            tt = "Đã hủy";
        }
        return tt;
    }

    public void FindHD(int id) {
        listHoaDon = reHoaDon.selectFind(id);
        int i = 1;
        String hienThi[] = {"STT", "Mã hóa đơn", "Mã khách hàng", "Mã nhân viên", "Ngày tạo", "Ngày cập nhật", "Ngày xóa", "Giá trị", "Số tiền giảm", "Giá trị tổng", "Trạng thái hóa đơn", "Chương trình giảm giá"};
        DefaultTableModel modelHoaDon = new DefaultTableModel(hienThi, 0);
        modelHoaDon.setRowCount(0);
        if (txtTimKiem.getText().trim().equalsIgnoreCase("")) {
            for (HoaDon hd : listHoaDon) {
                Object[] row = {i, hd.getMaHoaDon(), MaKH(hd.getIdKhachHang()), MaTK(hd.getIdTaiKhoan()), hd.getNgayTao(), hd.getNgayCapNhat(), hd.getNgayXoa(), hd.getGiaTri(), hd.getSoTienGiam(), hd.getGiaTriTong(), TTHD(hd.getIdTrangThaiHoaDon()), MaGG(hd.getIdGiamGiaHoaDon())};
                modelHoaDon.addRow(row);
                i++;
            }
            tblHoaDon.setModel(modelHoaDon);
        } else {
            for (HoaDon hd : listHoaDon) {
                if (hd.getIdTrangThaiHoaDon().toString().contains(txtTimKiem.getText()) || hd.getMaHoaDon().contains(txtTimKiem.getText()) || hd.getIdKhachHang().toString().contains(txtTimKiem.getText()) || hd.getIdTaiKhoan().toString().contains(txtTimKiem.getText())) {
                    Object[] row = {i, hd.getMaHoaDon(), MaKH(hd.getIdKhachHang()), MaTK(hd.getIdTaiKhoan()), hd.getNgayTao(), hd.getNgayCapNhat(), hd.getNgayXoa(), hd.getGiaTri(), hd.getSoTienGiam(), hd.getGiaTriTong(), TTHD(hd.getIdTrangThaiHoaDon()), MaGG(hd.getIdGiamGiaHoaDon())};
                    modelHoaDon.addRow(row);
                    i++;
                }
            }
            tblHoaDon.setModel(modelHoaDon);
        }
    }

    public void fillLS() {
        lsHoaDon = relsHoaDon.selectLSHD(tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 1).toString());
        String hienThi[] = {"Mã tài khoản", "Ngày cập nhật", "Ghi chú"};
        DefaultTableModel modelLSHD = new DefaultTableModel(hienThi, 0);
        modelLSHD.setRowCount(0);
        for (LichSuHoaDon ls : lsHoaDon) {
            Object[] row = {MaTK(ls.getIdTaiKhoan()), ls.getNgayCapNhat(), ls.getGhiChu()};
            modelLSHD.addRow(row);
        }
        tblLichSuHD.setModel(modelLSHD);
    }

    public void fillIndexSelected() {
        listSPCT = reSPCT.selectSPCT(tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 1).toString());
//        listSPCT = reSPCT.selectSPCT();
//        listGioHang = reHoaDonCT.selectHDCT((int) tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0));
        String hienThi[] = {"Stt", "Mã sản phẩm", "Màu sắc", "Chuôi", "Ngọn", "Tay cầm", "Ren", "Đầu cơ bản", "Đường kính đầu", "Trọng lượng", "Thương hiệu", "Xuất xứ", "Số lượng", "Đơn giá", "Bảo hành"};
        DefaultTableModel modelHDCT = new DefaultTableModel(hienThi, 0);
        modelHDCT.setRowCount(0);
        int i = 1;
        for (SanPhamChiTiet1 sp : listSPCT) {
            Object[] row = {i, sp.getMaSanPham(), sp.getMauSac(), sp.getChuoi(), sp.getNgon(), sp.getTayCam(), sp.getRen(), sp.getDauCoBan(), sp.getDuongKinhDau(), sp.getTrongLuong(), sp.getThuongHieu(), sp.getXuatXu(), sp.getSoLuong(), sp.getGiaBan(), sp.getBaoHanh()};
            i++;
            modelHDCT.addRow(row);
        }
        tblChiTietHoaDon.setModel(modelHDCT);
        listHoaDon = reHoaDon.selectAll();
        for (HoaDon hd : listHoaDon) {
            if (hd.getId() == (int) tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0)) {
                txtGhiChu.setText(hd.getGhiChu());
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ThanhToan = new javax.swing.ButtonGroup();
        txtTimKiem = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        lblDSHD = new javax.swing.JLabel();
        tblHoaDon1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        ChiTietHoaDon = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChiTietHoaDon = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cboSearch = new javax.swing.JComboBox<>();
        LichSuHD = new javax.swing.JPanel();
        lblGhiChu1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLichSuHD = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        btnFix = new javax.swing.JButton();
        GhiChu = new javax.swing.JPanel();
        lblGhiChu = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        txtMaHoaDon = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSoTienGiam = new javax.swing.JTextField();
        rdoPaid = new javax.swing.JRadioButton();
        rdoUnPaid = new javax.swing.JRadioButton();
        rdoDeleted = new javax.swing.JRadioButton();
        btnSearch1 = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        btnPDF1 = new javax.swing.JButton();
        btnPDF2 = new javax.swing.JButton();

        setBorder(new javax.swing.border.MatteBorder(null));
        setForeground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1677, 1014));
        setMinimumSize(new java.awt.Dimension(1677, 1014));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(1677, 1014));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTimKiem.setToolTipText("");
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-search-30.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        lblDSHD.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblDSHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDSHD.setText("Danh sách hóa đơn");

        tblHoaDon1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblHoaDon1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDon1MouseClicked(evt);
            }
        });

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã hóa đơn", "Mã khách hàng", "Mã nhân viên", "Ngày tạo", "Ngày cập nhật", "Ngày xóa", "Giá trị", "Số tiền giảm", "Giá trị tổng", "Trạng thái hóa đơn"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        tblHoaDon1.setViewportView(tblHoaDon);

        ChiTietHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        ChiTietHoaDon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblChiTietHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Stt", "Mã Sản phẩm", "Màu sắc", "Chuôi", "Ngọn", "Tay cầm", "Ren", "Đầu cơ bản", "Đường kính đầu", "Trọng lượng", "Thương hiệu", "Xuất xứ", "Số lượng", "Đơn giá", "Bảo hành"
            }
        ));
        tblChiTietHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblChiTietHoaDon);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Chi tiết hóa đơn");

        javax.swing.GroupLayout ChiTietHoaDonLayout = new javax.swing.GroupLayout(ChiTietHoaDon);
        ChiTietHoaDon.setLayout(ChiTietHoaDonLayout);
        ChiTietHoaDonLayout.setHorizontalGroup(
            ChiTietHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ChiTietHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        ChiTietHoaDonLayout.setVerticalGroup(
            ChiTietHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ChiTietHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addContainerGap())
        );

        cboSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cboSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Chưa thanh toán", "Đã hoàn thành", "Đã hủy" }));
        cboSearch.setToolTipText("");
        cboSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSearchActionPerformed(evt);
            }
        });

        LichSuHD.setBackground(new java.awt.Color(255, 255, 255));
        LichSuHD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblGhiChu1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblGhiChu1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGhiChu1.setText("Lịch sử hóa đơn");

        tblLichSuHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã tài khoản", "Ngày cập nhật", "Hành động"
            }
        ));
        jScrollPane2.setViewportView(tblLichSuHD);

        javax.swing.GroupLayout LichSuHDLayout = new javax.swing.GroupLayout(LichSuHD);
        LichSuHD.setLayout(LichSuHDLayout);
        LichSuHDLayout.setHorizontalGroup(
            LichSuHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LichSuHDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LichSuHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGhiChu1, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        LichSuHDLayout.setVerticalGroup(
            LichSuHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LichSuHDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGhiChu1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnDelete.setBackground(new java.awt.Color(255, 0, 0));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDelete.setText("Hủy hóa đơn");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnFix.setBackground(new java.awt.Color(102, 255, 102));
        btnFix.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnFix.setText("Lưu");
        btnFix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFixActionPerformed(evt);
            }
        });

        GhiChu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblGhiChu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblGhiChu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGhiChu.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtGhiChu.setRows(5);
        txtGhiChu.setText("null");
        jScrollPane4.setViewportView(txtGhiChu);

        javax.swing.GroupLayout GhiChuLayout = new javax.swing.GroupLayout(GhiChu);
        GhiChu.setLayout(GhiChuLayout);
        GhiChuLayout.setHorizontalGroup(
            GhiChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GhiChuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GhiChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(lblGhiChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        GhiChuLayout.setVerticalGroup(
            GhiChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GhiChuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Mã hóa đơn");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Mã khách hàng");

        txtMaKH.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKHActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Số tiền giảm");

        txtSoTienGiam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoTienGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoTienGiamActionPerformed(evt);
            }
        });

        ThanhToan.add(rdoPaid);
        rdoPaid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rdoPaid.setText("Đã thanh toán");
        rdoPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPaidActionPerformed(evt);
            }
        });

        ThanhToan.add(rdoUnPaid);
        rdoUnPaid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rdoUnPaid.setText("Chưa thanh toán");

        ThanhToan.add(rdoDeleted);
        rdoDeleted.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rdoDeleted.setText("Đã hủy");
        rdoDeleted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDeletedActionPerformed(evt);
            }
        });

        btnSearch1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSearch1.setText("Quét QR");
        btnSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch1ActionPerformed(evt);
            }
        });

        btnPDF.setBackground(new java.awt.Color(102, 255, 102));
        btnPDF.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPDF.setText("Xuất hóa đơn");
        btnPDF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPDFMouseClicked(evt);
            }
        });
        btnPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFActionPerformed(evt);
            }
        });

        btnPDF1.setBackground(new java.awt.Color(102, 255, 102));
        btnPDF1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPDF1.setText("Xuất excel CT");
        btnPDF1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDF1ActionPerformed(evt);
            }
        });

        btnPDF2.setBackground(new java.awt.Color(102, 255, 102));
        btnPDF2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPDF2.setText("Xuất excel HD");
        btnPDF2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDF2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LichSuHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GhiChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnFix, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnPDF1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPDF2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(lblDSHD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tblHoaDon1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSoTienGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(109, 109, 109)
                                .addComponent(rdoPaid)
                                .addGap(23, 23, 23)
                                .addComponent(rdoUnPaid)
                                .addGap(23, 23, 23)
                                .addComponent(rdoDeleted)
                                .addGap(148, 148, 148))
                            .addComponent(ChiTietHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(167, 167, 167))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblDSHD, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cboSearch)
                                .addComponent(btnSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)
                        .addComponent(tblHoaDon1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtSoTienGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1)
                                .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rdoPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rdoUnPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rdoDeleted, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(ChiTietHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(GhiChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LichSuHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPDF1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPDF2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFix, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(167, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        if (cboSearch.getSelectedItem().equals("Tất cả")) {
            fillHoaDon();
        } else if (cboSearch.getSelectedItem().equals("Chua thanh toan")) {
            FindHD(1);
        } else if (cboSearch.getSelectedItem().equals("Da thanh toan")) {
            FindHD(2);
        } else if (cboSearch.getSelectedItem().equals("Da huy")) {
            FindHD(3);
        }

    }//GEN-LAST:event_btnSearchActionPerformed

    private void tblHoaDon1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDon1MouseClicked
        // TODO add your handling code here:
//        fillIndexSelected(tblHoaDon.getSelectedRow());
//        listHoaDonCT = reHoaDonCT.selectHDCT((int) tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0));
//        String hienThi[] = {"Mã sản phẩm", "Số lượng", "Đơn giá", "Trạng thái"};
//        DefaultTableModel modelHDCT = new DefaultTableModel(hienThi, 0);
//        modelHDCT.setRowCount(0);
//        for (HoaDonChiTiet hdct : listHoaDonCT) {
//            Object[] row = {hdct.getIdSanPhamChiTiet(), hdct.getSoLuong(), hdct.getGiaBan(), hdct.getTrangThai()};
//            modelHDCT.addRow(row);
//        }
//        tblChiTietHoaDon.setModel(modelHDCT);
    }//GEN-LAST:event_tblHoaDon1MouseClicked

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        fillIndexSelected();
        fillLS();
        txtMaHoaDon.setText((String) tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 1));
        txtMaKH.setText((String) tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 2));
        txtSoTienGiam.setText((tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 8)).toString());
        if ((tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 10)).toString().equals("Đã thanh toán")) {
            rdoPaid.setSelected(true);
        }
        if ((tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 10)).toString().equals("Chưa thanh toán")) {
            rdoUnPaid.setSelected(true);
        }
        if ((tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 10)).toString().equals("Đã hủy")) {
            rdoDeleted.setSelected(true);
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnFixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFixActionPerformed
        String index1 = txtMaKH.getText().substring(3, 5);
        int idk = Integer.parseInt(index1);
        int tt = 0;
        if (rdoPaid.isSelected()) {
            tt = 2;
        }
        if (rdoUnPaid.isSelected()) {
            tt = 1;
        }
        if (rdoDeleted.isSelected()) {
            tt = 3;
        }
        String index2 = (tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 1).toString().substring(3, 5));
        int id = Integer.parseInt(index2);
        reHoaDon.updateHD(txtMaHoaDon.getText(), idk, Integer.parseInt(txtSoTienGiam.getText()), txtGhiChu.getText(), tt);
        relsHoaDon.update(id, "Sua hoa don");
        JOptionPane.showMessageDialog(this, "Sửa hóa đơn thành công");
        fillLS();
        fillHoaDon();
    }//GEN-LAST:event_btnFixActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String index = (tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 1).toString().replaceAll("[^0-9]", ""));
        int id = Integer.parseInt(index);
        reHoaDon.delete(id);
        relsHoaDon.update(id, "Huy hoa don");
        JOptionPane.showMessageDialog(this, "Hủy hóa đơn thành công");
        fillLS();
        fillHoaDon();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKHActionPerformed

    private void txtSoTienGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoTienGiamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoTienGiamActionPerformed

    private void cboSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSearchActionPerformed

    private void rdoPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPaidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoPaidActionPerformed

    private void btnSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearch1ActionPerformed

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed

        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        j.showSaveDialog(this);
    }//GEN-LAST:event_btnPDFActionPerformed

    private void rdoDeletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDeletedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoDeletedActionPerformed

    private void btnPDF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDF1ActionPerformed
        try {

            JFileChooser fileChooser = new JFileChooser("/");
            fileChooser.setDialogTitle("Chon noi luu");
            FileNameExtensionFilter extFilter = new FileNameExtensionFilter("Excel spreeadsheet files", "xls", "xlsx", "xism");
            fileChooser.setFileFilter(extFilter);

            int confirm = fileChooser.showSaveDialog(this);

            if (confirm == JFileChooser.APPROVE_OPTION) {
                XSSFWorkbook excelWorkbook = new XSSFWorkbook();
                XSSFSheet sheet = excelWorkbook.createSheet("SPCT");

                HashMap<Integer, ArrayList> tableData = new HashMap<>();

//                for (int row = 0; row < tblSanPhamChiTiet.getRowCount(); row++) {
//                    ArrayList<Object> rowData = new ArrayList<>();
//
//                    for (int col = 0; col < tblSanPhamChiTiet.getColumnCount(); col++) {
//
//                        rowData.add(tblSanPhamChiTiet.getValueAt(row, col));
//                    }
//                    tableData.put(row, rowData);
//                }
//                fill sheet with table data
                for (int row = 0; row < tblChiTietHoaDon.getRowCount(); row++) {
                    XSSFRow sheetRow = sheet.createRow(row);

                    for (int col = 1; col < tblChiTietHoaDon.getColumnCount(); col++) {
                        XSSFCell cell = sheetRow.createCell(col);

                        Object data = tblChiTietHoaDon.getValueAt(row, col);

                        if (data == null) {
                            continue;
                        }

                        cell.setCellValue(data.toString());
                    }

                }
                FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile() + ".xlsx");
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                excelWorkbook.write(bos);
                bos.close();

                JOptionPane.showMessageDialog(this, "Export thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnPDF1ActionPerformed

    private void btnPDF2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDF2ActionPerformed
        try {

            JFileChooser fileChooser = new JFileChooser("/");
            fileChooser.setDialogTitle("Chon noi luu");
            FileNameExtensionFilter extFilter = new FileNameExtensionFilter("Excel spreeadsheet files", "xls", "xlsx", "xism");
            fileChooser.setFileFilter(extFilter);

            int confirm = fileChooser.showSaveDialog(this);

            if (confirm == JFileChooser.APPROVE_OPTION) {
                XSSFWorkbook excelWorkbook = new XSSFWorkbook();
                XSSFSheet sheet = excelWorkbook.createSheet("SPCT");

                HashMap<Integer, ArrayList> tableData = new HashMap<>();

//                for (int row = 0; row < tblSanPhamChiTiet.getRowCount(); row++) {
//                    ArrayList<Object> rowData = new ArrayList<>();
//
//                    for (int col = 0; col < tblSanPhamChiTiet.getColumnCount(); col++) {
//
//                        rowData.add(tblSanPhamChiTiet.getValueAt(row, col));
//                    }
//                    tableData.put(row, rowData);
//                }
//                fill sheet with table data
                for (int row = 0; row < tblHoaDon.getRowCount(); row++) {
                    XSSFRow sheetRow = sheet.createRow(row);

                    for (int col = 1; col < tblHoaDon.getColumnCount(); col++) {
                        XSSFCell cell = sheetRow.createCell(col);

                        Object data = tblHoaDon.getValueAt(row, col);

                        if (data == null) {
                            continue;
                        }

                        cell.setCellValue(data.toString());
                    }

                }
                FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile() + ".xlsx");
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                excelWorkbook.write(bos);
                bos.close();

                JOptionPane.showMessageDialog(this, "Export thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        } // TODO add your handling code here:
    }//GEN-LAST:event_btnPDF2ActionPerformed

    private void tblChiTietHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietHoaDonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblChiTietHoaDonMouseClicked

    private void btnPDFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPDFMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPDFMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChiTietHoaDon;
    private javax.swing.JPanel GhiChu;
    private javax.swing.JPanel LichSuHD;
    private javax.swing.ButtonGroup ThanhToan;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFix;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnPDF1;
    private javax.swing.JButton btnPDF2;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JComboBox<String> cboSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblDSHD;
    private javax.swing.JLabel lblGhiChu;
    private javax.swing.JLabel lblGhiChu1;
    private javax.swing.JRadioButton rdoDeleted;
    private javax.swing.JRadioButton rdoPaid;
    private javax.swing.JRadioButton rdoUnPaid;
    private javax.swing.JTable tblChiTietHoaDon;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JScrollPane tblHoaDon1;
    private javax.swing.JTable tblLichSuHD;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSoTienGiam;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
