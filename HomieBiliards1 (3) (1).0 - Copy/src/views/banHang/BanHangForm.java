/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views.banHang;

import entities.receipts.HoaDon;
import entities.subjects.KhachHang;
import entities.subjects.TaiKhoan;
import entities.products.SanPhamChiTiet;
import entities.products.SanPham;
import entities.products.SanPhamChiTiet1;
import entities.receipts.GiamGiaHoaDon;
import entities.receipts.LichSuHoaDon;
import entities.receipts.ThanhToan;
import entities.receipts.TrangThaiHoaDon;
import entities.receipts.HoaDonChiTiet;
import entities.receipts.GioHang;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
import responses.HoaDonChiTietResponse;
import responses.HoaDonResponse;
import responses.SanPhamChiTietResponse;
import utils.DateConverter;
import views.sanPham.TableKhachHang;

/**
 *
 * @author Mtt
 */
public class BanHangForm extends javax.swing.JPanel {

    private HoaDonRepository hoaDonRepository;
    private DefaultTableModel dtmHoaDon;
    private SanPhamChiTietRepository sanPhamChiTietRepository;
    private DefaultTableModel dtmSanPham;
    private Integer indexHoaDonSelected;
    private HoaDonChiTietRepository hoaDonChiTietRepository;
    private DefaultTableModel dtmHoaDonChiTiet;

    public BanHangForm() {
        initComponents();
        hoaDonRepository = new HoaDonRepository();
        sanPhamChiTietRepository = new SanPhamChiTietRepository();
        hoaDonChiTietRepository = new HoaDonChiTietRepository();

        dtmSanPham = (DefaultTableModel) tbSanPham.getModel();
        dtmHoaDon = (DefaultTableModel) tbHoaDon.getModel();
        dtmHoaDonChiTiet = (DefaultTableModel) tbHoaDonChiTiet.getModel();

        showTableHoaDon(hoaDonRepository.getAllByStatuss());
//        showTableHoaDon(hoaDonRepository.getAllByStatuss());
        showTableSanPhamChiTiet(sanPhamChiTietRepository.getAll());
        indexHoaDonSelected = tbHoaDon.getSelectedRow();
        btnKhach.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new  TableKhachHang().setVisible(true);
            }
            
        });
         setVisible(true);
    }

    private void showTableHoaDon(ArrayList<HoaDonResponse> lists) {
        dtmHoaDon.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        lists.forEach(s -> dtmHoaDon.addRow(new Object[]{
            index.getAndIncrement(), s.getMaHoaDon(), s.getMaTK(), s.getGiaTriTong(),
            s.getNgayTao(), s.getTrangThaiHoaDon()
        }));
    }

    private void showTableSanPhamChiTiet(ArrayList<SanPhamChiTietResponse> lists) {
        dtmSanPham.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        lists.forEach(s -> dtmSanPham.addRow(new Object[]{
            index.getAndIncrement(), s.getMaSanPham(), s.getTenSanPham(), s.getNgayTao(), s.getGiaBan(),
            s.getMauSac(), s.getChuoi(), s.getTayCam(), s.getDauCoBan(), s.getNgon(), s.getRen(), s.getTrongLuong(),
            s.getDuongKinhDau(), s.getThuongHieu(), s.getBaoHanh(), s.getXuatXu(), s.getSoLuong(), s.getTrangThai() == 1 ? "Đang bán" : "Ngừng bán"

        }));

    }

    private void showTableHoaDonChiTiet(ArrayList<HoaDonChiTietResponse> list) {
        dtmHoaDonChiTiet.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        list.forEach(s -> dtmHoaDonChiTiet.addRow(new Object[]{
            index.getAndIncrement(), s.getMaSP(), s.getTenSP(), s.getBaoHanh(), s.getGiaBan(),
            s.getSoLuong(), s.getThanhTien()
        }));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GrBaoHanh = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblSLSP = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        lblTienMat = new javax.swing.JLabel();
        lblTienThua = new javax.swing.JLabel();
        lblTenKhachHang = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        txtTienMat = new javax.swing.JTextField();
        lblChuyenKhoan = new javax.swing.JLabel();
        txtChuyenKhoan = new javax.swing.JTextField();
        txtTenKhachHang = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        txtNgayTao = new javax.swing.JTextField();
        btnXoaGioHang = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        btnKhach = new javax.swing.JButton();
        btnKhachLe = new javax.swing.JButton();
        lblTaoDonHang = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbHoaDon = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbHoaDonChiTiet = new javax.swing.JTable();
        btnTaoHoaDon = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.MatteBorder(null));
        setForeground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1677, 1014));
        setMinimumSize(new java.awt.Dimension(1677, 1014));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(1677, 1014));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblSLSP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSLSP.setText("Ngày tạo:");

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongTien.setText("Tổng tiền:");

        lblTienMat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTienMat.setText("Tiền mặt:");

        lblTienThua.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTienThua.setText("Tiền thừa:");

        lblTenKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenKhachHang.setText("Tên khách hàng:");

        lblSDT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSDT.setText("Số điện thoại:");

        txtTienMat.setEditable(false);
        txtTienMat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTienMat.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTienMat.setText("0");
        txtTienMat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienMatActionPerformed(evt);
            }
        });

        lblChuyenKhoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblChuyenKhoan.setText("Chuyển khoản");

        txtChuyenKhoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtChuyenKhoan.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtChuyenKhoan.setText("0");
        txtChuyenKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChuyenKhoanActionPerformed(evt);
            }
        });

        txtTenKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKhachHangActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Thông tin khách hàng");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        btnXoaGioHang.setBackground(new java.awt.Color(255, 0, 0));
        btnXoaGioHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaGioHang.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaGioHang.setText("Xóa giỏ hàng");
        btnXoaGioHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaGioHangActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(0, 204, 0));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Địa chỉ:");

        btnKhach.setText("Khách");

        btnKhachLe.setText("Khách lẻ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(147, 147, 147)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(btnXoaGioHang, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(36, 36, 36)
                            .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblTienThua)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTienMat, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblChuyenKhoan)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtChuyenKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTienMat)
                                .addComponent(lblTongTien))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTongTien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblSLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblSDT)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTenKhachHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnKhachLe, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(btnKhach)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKhach)
                    .addComponent(btnKhachLe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenKhachHang)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSDT)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSLSP)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTongTien)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTienMat)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTienThua)
                    .addComponent(txtTienMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChuyenKhoan)
                    .addComponent(txtChuyenKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaGioHang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThanhToan))
                .addGap(23, 23, 23))
        );

        lblTaoDonHang.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTaoDonHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTaoDonHang.setText("Đơn hàng");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sp", "Tên sp", "Ngày tạo", "Giá bán", "Màu sắc", "Chuôi", "Tay cầm", "Đầu cơ bắn ", "Ngọn", "Ren", "Trọng lượng", "Đường kính đầu", "Thương hiệu", "Bảo hành", "Xuất xứ", "Số lượng", "Trạng thái"
            }
        ));
        tbSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSanPhamMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbSanPham);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1036, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 45, Short.MAX_VALUE))
        );

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tbHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Mã nhân viên", "Tổng tiền", "Ngày tạo", "Trạng thái"
            }
        ));
        tbHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHoaDonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbHoaDon);

        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tbHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Bảo hành", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ));
        jScrollPane5.setViewportView(tbHoaDonChiTiet);

        btnTaoHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTaoHoaDon.setText("Tạo hóa đơn");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("Bộ lọc");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(434, 434, 434)
                .addComponent(jLabel2)
                .addContainerGap(594, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel2)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTaoDonHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 964, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 142, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lblTaoDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 131, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaGioHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaGioHangActionPerformed

    }//GEN-LAST:event_btnXoaGioHangActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed

        HoaDonResponse response = hoaDonRepository.getAllByStatuss().get(indexHoaDonSelected);
        response.setTenKhachHang(txtTenKhachHang.getText());
        response.setDienThoai(txtSDT.getText());
        response.setDiaChi(txtDiaChi.getText());

        hoaDonRepository.updateThongTin(response);
        showTableHoaDon(hoaDonRepository.getAllByStatuss());
        dtmHoaDonChiTiet.setRowCount(0);

        txtSDT.setText("");
        txtTenKhachHang.setText("");
        txtNgayTao.setText("");
        txtTongTien.setText("");
        txtTienMat.setText("");
        txtDiaChi.setText("");


    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        HoaDon hd = HoaDon.builder()
                .idKhachHang(11)
                .idTaiKhoan(1)
                .build();

        hoaDonRepository.addHoaDon(hd);
        showTableHoaDon(hoaDonRepository.getAllByStatuss());
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void tbHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHoaDonMouseClicked
        indexHoaDonSelected = tbHoaDon.getSelectedRow();
        HoaDonResponse response = hoaDonRepository.getAllByStatuss().get(indexHoaDonSelected);
        txtTenKhachHang.setText(response.getTenKhachHang());
        txtSDT.setText(response.getDienThoai().toString());
        txtNgayTao.setText(response.getNgayTao() + "");
        txtTongTien.setText(response.getGiaTriTong() + "");
        txtDiaChi.setText(response.getDiaChi());
        showTableHoaDonChiTiet(hoaDonChiTietRepository.getAll(response.getId()));


    }//GEN-LAST:event_tbHoaDonMouseClicked


    private void tbSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPhamMouseClicked
        // TODO add your handling code here:
        int row = tbSanPham.getSelectedRow();
        SanPhamChiTietResponse spctr = sanPhamChiTietRepository.getAll().get(row);
        HoaDonResponse response = hoaDonRepository.getAllByStatuss().get(indexHoaDonSelected);

        String soLuong = JOptionPane.showInputDialog("Số lượng là: ", "0");
        if (Integer.valueOf(soLuong) <= 0) {
            JOptionPane.showMessageDialog(this, "Không được nhập số lượng nhỏ hơn 1");
            return;
        }

//        ArrayList<HoaDonChiTietResponse> hoaDonChiTietResponses = hoaDonChiTietRepository.getAll(response.getId());
//
//        HoaDonChiTietResponse donChiTietResponse = null;
//
//        for (HoaDonChiTietResponse detail : hoaDonChiTietResponses) {
//            if (detail.getIdSPCT().equals(spctr.getId())) {
//                donChiTietResponse = detail;
//                break;
//            }
//        }
        
      

        HoaDonChiTietResponse hoaDonChiTietResponse
                = HoaDonChiTietResponse.builder()
                        .idHD(response.getId())
                        .idSPCT(spctr.getId())
                        .maSP(spctr.getMaSanPham())
                        .tenSP(spctr.getTenSanPham())
                        .soLuong(Integer.parseInt(soLuong))
                        .thanhTien(Double.valueOf(soLuong) * spctr.getGiaBan())
                        .trangThai(spctr.getTrangThai())
                        .giaBan(spctr.getGiaBan())
                        .moTa(spctr.getMoTa())
                        .ngayTao(spctr.getNgayTao())
                        .mauSac(spctr.getMauSac())
                        .chuoi(spctr.getChuoi())
                        .tayCam(spctr.getTayCam())
                        .dauCoBan(spctr.getDauCoBan())
                        .ngon(spctr.getNgon())
                        .ren(spctr.getRen())
                        .trongLuong(spctr.getTrongLuong())
                        .duongKinhDau(spctr.getDuongKinhDau())
                        .thuongHieu(spctr.getThuongHieu())
                        .baoHanh(spctr.getBaoHanh())
                        .xuatXu(spctr.getXuatXu())
                        .build();
        hoaDonChiTietRepository.getAll(response.getId()).add(hoaDonChiTietResponse);
        spctr.setSoLuong(spctr.getSoLuong() - Integer.valueOf(soLuong));
        sanPhamChiTietRepository.updateSoLuong(spctr);
        hoaDonChiTietRepository.add(hoaDonChiTietResponse);
        showTableSanPhamChiTiet(sanPhamChiTietRepository.getAll());
        showTableHoaDonChiTiet(hoaDonChiTietRepository.getAll(response.getId()));

        response.setGiaTriTong(showTotalMoney(hoaDonChiTietRepository.getAll(response.getId())));
        hoaDonRepository.updateTongTien(response);
        txtTongTien.setText(response.getGiaTriTong() + "");
        showTableHoaDon(hoaDonRepository.getAllByStatuss());

    }//GEN-LAST:event_tbSanPhamMouseClicked

    private double showTotalMoney(ArrayList<HoaDonChiTietResponse> lists) {
        if (lists == null || lists.isEmpty()) {
            return 0.0;
        }

        return lists.stream()
                .filter(Objects::nonNull) // Ensure we don't encounter NullPointerException
                .collect(Collectors.summingDouble(HoaDonChiTietResponse::getThanhTien));
    }
    private void txtTenKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKhachHangActionPerformed

    private void txtChuyenKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChuyenKhoanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChuyenKhoanActionPerformed

    private void txtTienMatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienMatActionPerformed

    }//GEN-LAST:event_txtTienMatActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GrBaoHanh;
    private javax.swing.JButton btnKhach;
    private javax.swing.JButton btnKhachLe;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnXoaGioHang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel lblChuyenKhoan;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblSLSP;
    private javax.swing.JLabel lblTaoDonHang;
    private javax.swing.JLabel lblTenKhachHang;
    private javax.swing.JLabel lblTienMat;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable tbHoaDon;
    private javax.swing.JTable tbHoaDonChiTiet;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JTextField txtChuyenKhoan;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTienMat;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
