/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.sanPham;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import entities.products.Chuoi;
import entities.products.DauCoBan;
import entities.products.MauSac;
import entities.products.Ngon;
import entities.products.Ren;
import entities.products.SanPham;
import entities.products.SanPhamChiTiet;
import entities.products.TayCam;
import entities.products.ThuongHieu;
import entities.products.XuatXu;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import repositories.products.ChuoiRepository;
import repositories.products.DauCoBanRepository;
import repositories.products.MauSacRepository;
import repositories.products.NgonRepository;
import repositories.products.RenRepository;
import repositories.products.SanPhamChiTietRepository;
import repositories.products.SanPhamRepository;
import repositories.products.TayCamRepository;
import repositories.products.ThuongHieuRepository;
import repositories.products.XuatXuRepository;
import utils.validators.sanPhamChiTiet.SanPhamChiTietValidator;

/**
 *
 * @author Mtt
 */
public class SanPhamChiTietEditForm extends javax.swing.JFrame {

    /**
     * Creates new form SanPhamChiTietEditForm
     */
    private DefaultComboBoxModel cbxmSanPham;
    private DefaultComboBoxModel cbxmXuatXu;
    private DefaultComboBoxModel cbxmThuongHieu;
    private DefaultComboBoxModel cbxmMauSac;
    private DefaultComboBoxModel cbxmChuoi;
    private DefaultComboBoxModel cbxmTayCam;
    private DefaultComboBoxModel cbxmdauCoBan;
    private DefaultComboBoxModel cbxmNgon;
    private DefaultComboBoxModel cbxmRen;
    SanPhamChiTietRepository spctRepo;
    
    List<SanPham> sanPhamList;
    List<XuatXu> xuatXuList;
    List<ThuongHieu> thuongHieuList;
    List<MauSac> mauSacList;
    List<Chuoi> chuoiList;
    List<TayCam> tayCamList;
    List<DauCoBan> dauCoBanList;
    List<Ngon> ngonList;
    List<Ren> renList;
    SanPhamChiTiet state;
    SanPhamForm parent;
    SanPhamForm parent1;
    
    public SanPhamChiTietEditForm(SanPhamChiTiet sanPhamChiTiet, SanPhamForm component) {
        FlatLightLaf.setup();
        initComponents();
        state = sanPhamChiTiet;
        parent = component;
        
        init();
        
    }
    
    private void destroy() {
        this.dispose();
    }
    
    private void init() {
        cbxmSanPham = (DefaultComboBoxModel) cbxSanPhamList.getModel();
        cbxmXuatXu = (DefaultComboBoxModel) cbxXuatXu.getModel();
        cbxmThuongHieu = (DefaultComboBoxModel) cbxThuongHieu.getModel();
        cbxmMauSac = (DefaultComboBoxModel) cbxMauSac.getModel();
        cbxmChuoi = (DefaultComboBoxModel) cbxChuoi.getModel();
        cbxmTayCam = (DefaultComboBoxModel) cbxTayCam.getModel();
        cbxmdauCoBan = (DefaultComboBoxModel) cbxDauCoBan.getModel();
        cbxmNgon = (DefaultComboBoxModel) cbxNgon.getModel();
        cbxmRen = (DefaultComboBoxModel) cbxRen.getModel();
        
        spctRepo = new SanPhamChiTietRepository();
        fillComponents(state);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    private void fillComponents(SanPhamChiTiet sanPhamChiTiet) {
        try {
            /* COMPONENT NAMES SAVED TO REDUCE TEDIUM
        cbxmSanPham 
        cbxmXuatXu 
        cbxmThuongHieu 
        cbxmMauSac 
        cbxmChuoi 
        cbxmTayCam 
        cbxmdauCoBan 
        cbxmNgon 
        cbxmRen 
        cbxSanPhamList
        cbxXuatXu 
        cbxThuongHieu 
        cbxMauSac 
        cbxChuoi 
        cbxTayCam 
        cbxdauCoBan 
        cbxNgon 
        cbxRen 
             */
            sanPhamList = new SanPhamRepository().selectAll();
            xuatXuList = new XuatXuRepository().selectAll();
            thuongHieuList = new ThuongHieuRepository().selectAll();
            mauSacList = new MauSacRepository().selectAll();
            chuoiList = new ChuoiRepository().selectAll();
            tayCamList = new TayCamRepository().selectAll();
            dauCoBanList = new DauCoBanRepository().selectAll();
            ngonList = new NgonRepository().selectAll();
            renList = new RenRepository().selectAll();
            
            cbxmSanPham.removeAllElements();
            cbxmXuatXu.removeAllElements();
            cbxmThuongHieu.removeAllElements();
            cbxmMauSac.removeAllElements();
            cbxmChuoi.removeAllElements();
            cbxmTayCam.removeAllElements();
            cbxmdauCoBan.removeAllElements();
            cbxmNgon.removeAllElements();
            cbxmRen.removeAllElements();
            
            cbxmSanPham.addAll(sanPhamList.stream().map(item -> item.getTenSanPham()).toList());
            cbxmXuatXu.addAll(xuatXuList.stream().map(item -> item.getTen()).toList());
            cbxmThuongHieu.addAll(thuongHieuList.stream().map(item -> item.getTen()).toList());
            cbxmMauSac.addAll(mauSacList.stream().map(item -> item.getTen()).toList());
            cbxmChuoi.addAll(chuoiList.stream().map(item -> item.getTen()).toList());
            cbxmTayCam.addAll(tayCamList.stream().map(item -> item.getTen()).toList());
            cbxmdauCoBan.addAll(dauCoBanList.stream().map(item -> item.getTen()).toList());
            cbxmNgon.addAll(ngonList.stream().map(item -> item.getTen()).toList());
            cbxmRen.addAll(renList.stream().map(item -> item.getTen()).toList());
            
            cbxSanPhamList.setSelectedItem(sanPhamList.stream().filter(sp -> sp.getId() == sanPhamChiTiet.getIdSanPham()).findFirst().get().getTenSanPham());
            cbxXuatXu.setSelectedItem(xuatXuList.stream().filter(item -> item.getId() == sanPhamChiTiet.getXuatXu()).findFirst().get().getTen());
            cbxThuongHieu.setSelectedItem(thuongHieuList.stream().filter(item -> item.getId() == sanPhamChiTiet.getThuongHieu()).findFirst().get().getTen());
            cbxMauSac.setSelectedItem(mauSacList.stream().filter(item -> item.getId() == sanPhamChiTiet.getMauSac()).findFirst().get().getTen());
            cbxChuoi.setSelectedItem(chuoiList.stream().filter(item -> item.getId() == sanPhamChiTiet.getChuoi()).findFirst().get().getTen());
            cbxTayCam.setSelectedItem(tayCamList.stream().filter(item -> item.getId() == sanPhamChiTiet.getTayCam()).findFirst().get().getTen());
            cbxDauCoBan.setSelectedItem(dauCoBanList.stream().filter(item -> item.getId() == sanPhamChiTiet.getDauCoBan()).findFirst().get().getTen());
            cbxNgon.setSelectedItem(ngonList.stream().filter(item -> item.getId() == sanPhamChiTiet.getNgon()).findFirst().get().getTen());
            cbxRen.setSelectedItem(renList.stream().filter(item -> item.getId() == sanPhamChiTiet.getRen()).findFirst().get().getTen());
            
            txtSoLuong.setText(String.valueOf(sanPhamChiTiet.getSoLuong()));
            txtMoTa.setText(sanPhamChiTiet.getMoTa());
            txtGiaBan.setText(String.valueOf(sanPhamChiTiet.getGiaBan()));
            txtTrongLuong.setText(String.valueOf(sanPhamChiTiet.getTrongLuong()));
            txtBaoHanh.setText(String.valueOf(sanPhamChiTiet.getBaoHanh()));
            txtDuongKinhDau.setText(String.valueOf(sanPhamChiTiet.getDuongKinhDau()));
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
    }
    
    private SanPhamChiTiet parseFormSubmit() {
        try {
            SanPhamChiTiet newSpct = SanPhamChiTiet.builder()
                    .id(state.getId())
                    .idSanPham(sanPhamList.stream().filter(item -> item.getTenSanPham().equals(cbxSanPhamList.getSelectedItem())).findFirst().get().getId())
                    .xuatXu(xuatXuList.stream().filter(item -> item.getTen().equals(cbxXuatXu.getSelectedItem())).findFirst().get().getId())
                    .thuongHieu(thuongHieuList.stream().filter(item -> item.getTen().equals(cbxThuongHieu.getSelectedItem())).findFirst().get().getId())
                    .mauSac(mauSacList.stream().filter(item -> item.getTen().equals(cbxMauSac.getSelectedItem())).findFirst().get().getId())
                    .chuoi(chuoiList.stream().filter(item -> item.getTen().equals(cbxChuoi.getSelectedItem())).findFirst().get().getId())
                    .tayCam(tayCamList.stream().filter(item -> item.getTen().equals(cbxTayCam.getSelectedItem())).findFirst().get().getId())
                    .dauCoBan(dauCoBanList.stream().filter(item -> item.getTen().equals(cbxDauCoBan.getSelectedItem())).findFirst().get().getId())
                    .ngon(ngonList.stream().filter(item -> item.getTen().equals(cbxNgon.getSelectedItem())).findFirst().get().getId())
                    .ren(renList.stream().filter(item -> item.getTen().equals(cbxRen.getSelectedItem())).findFirst().get().getId())
                    .maSanPham(state.getMaSanPham())
                    .moTa(txtMoTa.getText().trim())
                    .giaBan(Integer.parseInt(txtGiaBan.getText().trim()))
                    .trongLuong(Integer.parseInt(txtTrongLuong.getText().trim()))
                    .baoHanh(Integer.parseInt(txtBaoHanh.getText().trim()))
                    .duongKinhDau(Double.parseDouble(txtDuongKinhDau.getText().trim()))
                    .soLuong(Integer.parseInt(txtSoLuong.getText()))
                    .build();
            
            return newSpct;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi lấy thông tin từ form. Kiểm tra khai báo đúng loại thông tin");
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

        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txtTrongLuong = new javax.swing.JTextField();
        txtBaoHanh = new javax.swing.JTextField();
        txtDuongKinhDau = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbxSanPhamList = new javax.swing.JComboBox<>();
        btnUpdateSanPhamChiTiet = new javax.swing.JButton();
        btnDeleteSanPhamChiTiet = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        cbxNgon = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        cbxRen = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        cbxTayCam = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        cbxThuongHieu = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cbxMauSac = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cbxChuoi = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cbxXuatXu = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbxDauCoBan = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();

        jButton3.setText("jButton3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thông tin sản phẩm chi tiết");
        setMinimumSize(new java.awt.Dimension(800, 800));
        setPreferredSize(new java.awt.Dimension(800, 700));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thông tin sản phẩm chi tiết");
        jLabel1.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Số lượng");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Mô tả");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Trọng lượng (g)");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Đường kính đầu (mm)");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Bảo hành (tháng)");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Sản phẩm");

        cbxSanPhamList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnUpdateSanPhamChiTiet.setText("Sửa");
        btnUpdateSanPhamChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSanPhamChiTietActionPerformed(evt);
            }
        });

        btnDeleteSanPhamChiTiet.setText("Xóa");
        btnDeleteSanPhamChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSanPhamChiTietActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Giá bán");

        jPanel2.setBackground(new java.awt.Color(214, 217, 223));

        cbxNgon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Ngọn");

        cbxRen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Ren");

        cbxTayCam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Tay cầm");

        cbxThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Thương hiệu");

        cbxMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Màu sắc");

        cbxChuoi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Chuôi");

        cbxXuatXu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Xuất xứ");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Đầu cơ bản");

        cbxDauCoBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9)
                    .addComponent(jLabel16)
                    .addComponent(cbxXuatXu, 0, 99, Short.MAX_VALUE)
                    .addComponent(cbxTayCam, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(102, 102, 102))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel14))
                        .addGap(160, 160, 160))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cbxThuongHieu, 0, 100, Short.MAX_VALUE)
                            .addComponent(cbxDauCoBan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(79, 79, 79)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxNgon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(111, 111, 111)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(cbxChuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(cbxRen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxChuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14)
                                .addComponent(jLabel15)
                                .addComponent(jLabel13))
                            .addComponent(jLabel16))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(cbxNgon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxTayCam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxDauCoBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(cbxRen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnUpdateSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeleteSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 40, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtTrongLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel17))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(cbxSanPhamList, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtGiaBan))
                                        .addGap(108, 108, 108)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtSoLuong)
                                        .addComponent(txtBaoHanh, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE))
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(txtDuongKinhDau, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(40, 40, 40))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbxSanPhamList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBaoHanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTrongLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDuongKinhDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateSanPhamChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSanPhamChiTietActionPerformed
        try {
            SanPhamChiTiet newSpct = parseFormSubmit();
            
            if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(this, "Cập nhât?", "Cập nhật", JOptionPane.OK_CANCEL_OPTION)) {
                return;
            }
            
            if (SanPhamChiTietValidator.validateUpdate(newSpct)) {
                spctRepo.update(newSpct, newSpct.getId());
            }
            
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            parent.renderSanPhamChiTietTbl(null);
            
            destroy();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnUpdateSanPhamChiTietActionPerformed

    private void btnDeleteSanPhamChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSanPhamChiTietActionPerformed
        try {
            SanPhamChiTiet newSpct = parseFormSubmit();
            
            if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(this, "Xóa?", "Xóa", JOptionPane.OK_CANCEL_OPTION)) {
                return;
            }
            
            spctRepo.delete(newSpct.getId());
            
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            parent.renderSanPhamChiTietTbl(null);
            destroy();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnDeleteSanPhamChiTietActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTietEditForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTietEditForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTietEditForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTietEditForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SanPhamChiTiet lespct = new SanPhamChiTietRepository().selectAll().get(0);
                new SanPhamChiTietEditForm(lespct, null).setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteSanPhamChiTiet;
    private javax.swing.JButton btnUpdateSanPhamChiTiet;
    private javax.swing.JComboBox<String> cbxChuoi;
    private javax.swing.JComboBox<String> cbxDauCoBan;
    private javax.swing.JComboBox<String> cbxMauSac;
    private javax.swing.JComboBox<String> cbxNgon;
    private javax.swing.JComboBox<String> cbxRen;
    private javax.swing.JComboBox<String> cbxSanPhamList;
    private javax.swing.JComboBox<String> cbxTayCam;
    private javax.swing.JComboBox<String> cbxThuongHieu;
    private javax.swing.JComboBox<String> cbxXuatXu;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtBaoHanh;
    private javax.swing.JTextField txtDuongKinhDau;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTrongLuong;
    // End of variables declaration//GEN-END:variables
}
