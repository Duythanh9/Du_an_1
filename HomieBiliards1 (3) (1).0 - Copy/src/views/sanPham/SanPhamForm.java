/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views.sanPham;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import responses.SanPhamChiTietResponse;
import responses.ThuocTinhResponse;
import services.SanPhamChiTietService;
import services.ThuocTinhService;
import utils.MaGenerator;
import utils.validators.sanPham.SanPhamValidator;
import utils.validators.thuocTinh.ThuocTinhValidator;

public class SanPhamForm extends javax.swing.JPanel {

    private DefaultTableModel sanPhamTblModel;
    private DefaultTableModel sanPhamChiTietTblModel;
    private DefaultTableModel thuocTinhTblModel;
    private SanPhamRepository spRepo;
    private SanPhamChiTietRepository spctRepo;

    private DefaultComboBoxModel cbxmFilterGiaBan;
    private DefaultComboBoxModel cbxmFilterMausac;
    private DefaultComboBoxModel cbxmFilterThuongHieu;
    private DefaultComboBoxModel cbxmFilterXuatXu;
    private DefaultComboBoxModel cbxmFilterTrangThai;

    private List giaBanOptions;
    private List<MauSac> mauSacOptions;
    private List<XuatXu> xuatXuOptions;
    private List<ThuongHieu> thuongHieuOptions;
    private List trangThaiOptions;

    /**
     * Creates new form SanPhamForm1
     */
    public SanPhamForm() {
        initComponents();
        sanPhamTblModel = (DefaultTableModel) tblSanPham.getModel();
        sanPhamChiTietTblModel = (DefaultTableModel) tblSanPhamChiTiet.getModel();
        thuocTinhTblModel = (DefaultTableModel) tblThuocTinhSanPham.getModel();

        spRepo = new SanPhamRepository();

        spctRepo = new SanPhamChiTietRepository();

        renderSanPhamChiTietFilterGroup();

        renderSanPhamTbl(spRepo.selectAll());
        renderSanPhamChiTietTbl(null);
        this.textSearch();

       
    }
    
    public void reloadDisplays() {

        renderSanPhamChiTietFilterGroup();
        renderSanPhamTbl(spRepo.selectAll());
        renderSanPhamChiTietTbl(null);
    }

    //SanPham methods
    private void renderSanPhamTbl(List<SanPham> list) {
        try {
//            List<SanPham> list;
            int option = cbxSanPhamStatusFilter.getSelectedIndex();
            String searchTerm = txtTimKiem.getText();
            sanPhamTblModel.setRowCount(0);

            if (option == 0) {
                list = spRepo.selectAll();
                if (!searchTerm.equals("Search...") && searchTerm != null && !searchTerm.isEmpty()) {
                    list = filterSanPhamBySearchTerm(list, searchTerm);
                }

                list.forEach((item) -> {
                    List<SanPhamChiTiet> found = spctRepo.selectBySanPhamId(item.getId());

                    if (found.isEmpty()) {
                        item.setSoLuong(0);

                    } else {

                        item.setSoLuong(found.stream().map(spct -> spct.getSoLuong()).reduce(0, Integer::sum));
                    }
                });
                int num = 1;

                for (SanPham item : list) {
                    sanPhamTblModel.addRow(new Object[]{num,
                        item.getMaSanPham(),
                        item.getTenSanPham(),
//                        item.getSoLuong(),
                        item.getTrang_thai() == 1 ? "Đang bán" : "Ngừng bán"
                    });
                    num++;
                }
            }

            if (option == 1) {
                list = Stream.concat(spRepo.selectAll().stream(), spRepo.selectDeleted().stream()).toList();
                if (!searchTerm.equals("Search...") && searchTerm != null && !searchTerm.isEmpty()) {
                    list = filterSanPhamBySearchTerm(list, searchTerm);
                }
                int num = 1;
                for (SanPham item : list) {
                    sanPhamTblModel.addRow(new Object[]{num,
                        item.getMaSanPham(),
                        item.getTenSanPham(),
                        item.getSoLuong(),
                        item.getTrang_thai() == 1 ? "Đang bán" : "Ngừng bán"});
                    num++;
                }
            }

            if (option == 2) {
                list = spRepo.selectDeleted();
                if (!searchTerm.equals("Search...") && searchTerm != null && !searchTerm.isEmpty()) {
                    list = filterSanPhamBySearchTerm(list, searchTerm);
                }
                int num = 1;
                for (SanPham item : list) {
                    sanPhamTblModel.addRow(new Object[]{num,
                        item.getMaSanPham(),
                        item.getTenSanPham(),
                        item.getSoLuong(),
                        item.getTrang_thai() == 1 ? "Đang bán" : "Ngừng bán"});
                    num++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    private List<SanPham> filterSanPhamBySearchTerm(List<SanPham> list, String str) {
        List<SanPham> found = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            str = str.toLowerCase();
            SanPham item = list.get(i);
            if (String.valueOf(item.getMaSanPham()).toLowerCase().contains(str) || item.getTenSanPham().toLowerCase().contains(str)) {
                found.add(item);
            }
        }

        return found;
    }

    private int getSelectedSanPhamIndex() {
        int idx = tblSanPham.getSelectedRow();

        if (idx == -1) {
            throw new RuntimeException("Chưa chọn sản phẩm nào trong bảng");
        }

        return idx;
    }

    //SanPhamChiTiet methods
    public void renderSanPhamChiTietTbl(List<SanPhamChiTietResponse> list) {
        try {
            sanPhamChiTietTblModel.setRowCount(0);
            if (list != null) {
                int num = 1;
                for (SanPhamChiTietResponse item : list) {
                    sanPhamChiTietTblModel.addRow(new Object[]{
                        num,
                        item.getTenSanPham(),
                        item.getMaSanPham(),
                        item.getMoTa(),
                        item.getThuongHieu(),
                        item.getXuatXu(),
                        item.getMauSac(),
                        item.getChuoi(),
                        item.getTayCam(),
                        item.getDauCoBan(),
                        item.getNgon(),
                        item.getRen(),
                        item.getTrongLuong(),
                        item.getDuongKinhDau(),
                        item.getBaoHanh(),
                        item.getGiaBan(),
                        item.getSoLuong(),
                        item.getTrangThai() == 1 ? "Đang bán" : "Ngừng bán"
                    });
                    num++;
                }
            } else {
                list = new SanPhamChiTietService().selectAndPopulateAllSanPhamChiTiet();

                int num = 1;
                for (SanPhamChiTietResponse item : list) {
                    sanPhamChiTietTblModel.addRow(new Object[]{
                        num,
                        item.getTenSanPham(),
                        item.getMaSanPham(),
                        item.getMoTa(),
                        item.getThuongHieu(),
                        item.getXuatXu(),
                        item.getMauSac(),
                        item.getChuoi(),
                        item.getTayCam(),
                        item.getDauCoBan(),
                        item.getNgon(),
                        item.getRen(),
                        item.getTrongLuong(),
                        item.getDuongKinhDau(),
                        item.getBaoHanh(),
                        item.getGiaBan(),
                        item.getSoLuong(),
                        item.getTrangThai() == 1 ? "Đang bán" : "Ngừng bán"
                    });
                    num++;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void renderSanPhamChiTietFilterGroup() {
        for (ActionListener al : cbxFilterGia.getActionListeners()) {
            cbxFilterGia.removeActionListener(al);
        }

        for (ActionListener al : cbxFilterMauSac.getActionListeners()) {
            cbxFilterMauSac.removeActionListener(al);
        }
        for (ActionListener al : cbxFilterXuatXu.getActionListeners()) {
            cbxFilterXuatXu.removeActionListener(al);
        }
        for (ActionListener al : cbxFilterThuongHieu.getActionListeners()) {
            cbxFilterThuongHieu.removeActionListener(al);
        }
        for (ActionListener al : txtFilterGiaMin.getActionListeners()) {
            txtFilterGiaMin.removeActionListener(al);
        }
        for (ActionListener al : txtFilterGiaMax.getActionListeners()) {
            txtFilterGiaMax.removeActionListener(al);
        }
        for (ActionListener al : cbxFilterTrangThai.getActionListeners()) {
            cbxFilterTrangThai.removeActionListener(al);
        }
        for (ActionListener al : txtSearchSanPhamChiTiet.getActionListeners()) {
            txtSearchSanPhamChiTiet.removeActionListener(al);
        }

        cbxmFilterGiaBan = (DefaultComboBoxModel) cbxFilterGia.getModel();
        cbxmFilterMausac = (DefaultComboBoxModel) cbxFilterMauSac.getModel();
        cbxmFilterXuatXu = (DefaultComboBoxModel) cbxFilterXuatXu.getModel();
        cbxmFilterThuongHieu = (DefaultComboBoxModel) cbxFilterThuongHieu.getModel();
        cbxmFilterTrangThai = (DefaultComboBoxModel) cbxFilterTrangThai.getModel();

        cbxmFilterGiaBan.removeAllElements();
        cbxmFilterMausac.removeAllElements();
        cbxmFilterXuatXu.removeAllElements();
        cbxmFilterThuongHieu.removeAllElements();
        cbxmFilterTrangThai.removeAllElements();

        giaBanOptions = new ArrayList();
        giaBanOptions.add("Không");
        giaBanOptions.add("Cao đến thấp");
        giaBanOptions.add("Thấp đến cao");
        cbxmFilterGiaBan.addAll(giaBanOptions);
        cbxFilterGia.setSelectedIndex(0);

        mauSacOptions = new MauSacRepository().selectAll();

        cbxmFilterMausac.removeAllElements();
        cbxmFilterMausac.addElement("Không");
        cbxmFilterMausac.addAll(mauSacOptions.stream().map(item -> item.getTen()).toList());
        cbxFilterMauSac.setSelectedIndex(0);

        xuatXuOptions = new XuatXuRepository().selectAll();

        cbxmFilterXuatXu.removeAllElements();
        cbxmFilterXuatXu.addElement("Không");
        cbxmFilterXuatXu.addAll(xuatXuOptions.stream().map(item -> item.getTen()).toList());
        cbxFilterXuatXu.setSelectedIndex(0);

        thuongHieuOptions = new ThuongHieuRepository().selectAll();

        cbxmFilterThuongHieu.removeAllElements();
        cbxmFilterThuongHieu.addElement("Không");
        cbxmFilterThuongHieu.addAll(thuongHieuOptions.stream().map(item -> item.getTen()).toList());
        cbxFilterThuongHieu.setSelectedIndex(0);

        txtFilterGiaMin.setText("0");
        txtFilterGiaMax.setText("100000000");

        trangThaiOptions = new ArrayList();
        trangThaiOptions.add("Đang bán");
        trangThaiOptions.add("Tất cả");
        trangThaiOptions.add("Ngừng bán");
        cbxmFilterTrangThai.addAll(trangThaiOptions);
        cbxFilterTrangThai.setSelectedIndex(0);

        txtSearchSanPhamChiTiet.setText("");

        cbxFilterGia.addActionListener(new FilterListener());
        cbxFilterMauSac.addActionListener(new FilterListener());
        cbxFilterThuongHieu.addActionListener(new FilterListener());
        cbxFilterTrangThai.addActionListener(new FilterListener());
        cbxFilterXuatXu.addActionListener(new FilterListener());
        txtFilterGiaMin.addActionListener(new FilterListener());
        txtFilterGiaMax.addActionListener(new FilterListener());
        txtSearchSanPhamChiTiet.addActionListener(new FilterListener());
    }

    private class FilterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            processSpctTableFilter();

        }

    }

    private void processSpctTableFilter() {
        try {
            Integer giaFilterVal;
            switch (cbxFilterGia.getSelectedItem().toString()) {
                case "Cao đến thấp":
                    giaFilterVal = 1;
                    break;
                case "Thấp đến cao":
                    giaFilterVal = 2;
                    break;
                default:
                    giaFilterVal = null;
            }

            String mauSacFilterVal = cbxFilterMauSac.getSelectedItem().toString().equals("Không") ? null : cbxFilterMauSac.getSelectedItem().toString();
            String xuatXuFilterVal = cbxFilterXuatXu.getSelectedItem().toString().equals("Không") ? null : cbxFilterXuatXu.getSelectedItem().toString();
            String thuongHieuFilterVal = cbxFilterThuongHieu.getSelectedItem().toString().equals("Không") ? null : cbxFilterThuongHieu.getSelectedItem().toString();
            Integer giaFilterMin = Integer.valueOf(txtFilterGiaMin.getText());
            Integer giaFilterMax = Integer.valueOf(txtFilterGiaMax.getText());
            Integer trangThaiFilter;

            switch (cbxFilterTrangThai.getSelectedIndex()) {
                case 0:
                    trangThaiFilter = 1;
                    break;

                case 2:
                    trangThaiFilter = 0;
                    break;
                default:
                    trangThaiFilter = null;
            }

            List<SanPhamChiTietResponse> rs = new SanPhamChiTietService().selectAndPopulateSanPhamChiTietByFilter(giaFilterVal, mauSacFilterVal, xuatXuFilterVal, thuongHieuFilterVal, giaFilterMin, giaFilterMax, trangThaiFilter);

            String searchTerm = txtSearchSanPhamChiTiet.getText().trim().toLowerCase();

            if (searchTerm != null && !searchTerm.isEmpty()) {
                rs = rs.stream().filter(spctrs
                        -> spctrs.getTenSanPham().toLowerCase().contains(searchTerm)
                        || spctrs.getMaSanPham().toLowerCase().contains(searchTerm)
                        || (spctrs.getMoTa() != null && spctrs.getMoTa().toLowerCase().contains(searchTerm))
                        || spctrs.getThuongHieu().toLowerCase().contains(searchTerm)
                        || spctrs.getXuatXu().toLowerCase().contains(searchTerm)
                        || spctrs.getMauSac().toLowerCase().contains(searchTerm)
                        || spctrs.getChuoi().toLowerCase().contains(searchTerm)
                        || spctrs.getTayCam().toLowerCase().contains(searchTerm)
                        || spctrs.getDauCoBan().toLowerCase().contains(searchTerm)
                        || spctrs.getNgon().toLowerCase().contains(searchTerm)
                        || spctrs.getRen().toLowerCase().contains(searchTerm)
                        || String.valueOf(spctrs.getTrongLuong()).contains(searchTerm)
                        || String.valueOf(spctrs.getDuongKinhDau()).contains(searchTerm)
                        || String.valueOf(spctrs.getBaoHanh()).contains(searchTerm)
                        || String.valueOf(spctrs.getGiaBan()).contains(searchTerm))
                        .toList();
            }

            renderSanPhamChiTietTbl(rs);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            txtFilterGiaMin.setText("0");
            txtFilterGiaMax.setText("100000000");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private int getSelectedSanPhamChiTietIndex() {
        int idx = tblSanPhamChiTiet.getSelectedRow();

        if (idx == -1) {
            throw new RuntimeException("Chưa chọn sản phẩm chi tiết nào trong bảng");
        }

        return idx;
    }

    //ThuocTinhSanPham methods
    private void renderThuocTinhTbl(String table) {
        List<ThuocTinhResponse> list = new ThuocTinhService().selectAllByTable(table);
        thuocTinhTblModel.setRowCount(0);
        int num = 1;
        for (ThuocTinhResponse item : list) {
            thuocTinhTblModel.addRow(new Object[]{num, item.getMa(), item.getTen()});
            num++;
        }

    }
    
     private void textSearch() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                timKiem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timKiem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                timKiem();
            }

        }
        );
    }

    private void timKiem() {
       
        String timKiem = txtTimKiem.getText().trim();
        renderSanPhamTbl(spRepo.search(timKiem));
    }

    
    
   

       


 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tabPaneSanPham = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        txtTenSanPham = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnCreateSanPham = new javax.swing.JButton();
        btnUpdateSanPham1 = new javax.swing.JButton();
        btnResetSanPhamForm = new javax.swing.JButton();
        btnDeleteSanPham = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbxSanPhamStatusFilter = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPhamChiTiet = new javax.swing.JTable();
        pnlFilterSanPhamChiTiet = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cbxFilterGia = new javax.swing.JComboBox<>();
        cbxFilterMauSac = new javax.swing.JComboBox<>();
        cbxFilterThuongHieu = new javax.swing.JComboBox<>();
        cbxFilterTrangThai = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        txtFilterGiaMin = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtFilterGiaMax = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        cbxFilterXuatXu = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        btnCreateSanPhamChiTiet1 = new javax.swing.JButton();
        txtSearchSanPhamChiTiet = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblThuocTinhSanPham = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        txtTenThuocTinh = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnCreateThuocTinh = new javax.swing.JButton();
        btnResetFormThuocTinhSanPham = new javax.swing.JButton();
        pnlChooseThuocTinhSanPham = new javax.swing.JPanel();
        rdoChuoi = new javax.swing.JRadioButton();
        rdoTayCam = new javax.swing.JRadioButton();
        rdoDauCoBan = new javax.swing.JRadioButton();
        rdoNgon = new javax.swing.JRadioButton();
        rdoRen = new javax.swing.JRadioButton();
        rdoThuongHieu = new javax.swing.JRadioButton();
        rdoXuatXu = new javax.swing.JRadioButton();
        rdoMauSac = new javax.swing.JRadioButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sản Phẩm");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Tên sản phẩm");

        btnCreateSanPham.setText("Thêm");
        btnCreateSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateSanPhamActionPerformed(evt);
            }
        });

        btnUpdateSanPham1.setText("Sửa");
        btnUpdateSanPham1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSanPham1ActionPerformed(evt);
            }
        });

        btnResetSanPhamForm.setText("Reset form");
        btnResetSanPhamForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetSanPhamFormActionPerformed(evt);
            }
        });

        btnDeleteSanPham.setText("Xóa");
        btnDeleteSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCreateSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnUpdateSanPham1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnDeleteSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(btnResetSanPhamForm, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnCreateSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateSanPham1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResetSanPhamForm, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        tblSanPham.setAutoCreateRowSorter(true);
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Mã", "Tên", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblSanPham.getTableHeader().setResizingAllowed(false);
        tblSanPham.getTableHeader().setReorderingAllowed(false);
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setResizable(false);
            tblSanPham.getColumnModel().getColumn(1).setResizable(false);
            tblSanPham.getColumnModel().getColumn(2).setResizable(false);
            tblSanPham.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel3.setText("Tìm kiếm");

        cbxSanPhamStatusFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang bán", "Tất cả", "Ngừng bán" }));
        cbxSanPhamStatusFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSanPhamStatusFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel3)
                        .addGap(42, 42, 42)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(132, 132, 132)
                        .addComponent(cbxSanPhamStatusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 572, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cbxSanPhamStatusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPaneSanPham.addTab("Sản phẩm", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        tblSanPhamChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên sp", "Mã", "Mô tả", "Thương hiệu", "Xuất xứ", "Màu", "Chuôi", "Tay", "Đầu", "Ngọn", "Rèn", "Trọng lượng", "Đường kính đầu", "Bảo hành", "Giá", "Số lượng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPhamChiTiet.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblSanPhamChiTiet.getTableHeader().setReorderingAllowed(false);
        tblSanPhamChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamChiTietMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPhamChiTiet);
        if (tblSanPhamChiTiet.getColumnModel().getColumnCount() > 0) {
            tblSanPhamChiTiet.getColumnModel().getColumn(0).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(1).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(2).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(3).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(4).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(5).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(6).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(7).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(8).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(9).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(10).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(11).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(12).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(13).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(14).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(15).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(16).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(17).setResizable(false);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlFilterSanPhamChiTiet.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bộ lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel7.setText("Xếp giá");

        jLabel8.setText("Màu sắc");

        jLabel19.setText("Xuất xứ");

        jLabel21.setText("Thương hiệu");

        cbxFilterGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxFilterMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxFilterThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang bán", "Tất cả", "Ngừng bán" }));
        cbxFilterTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFilterTrangThaiActionPerformed(evt);
            }
        });

        jLabel22.setText("Khoảng giá");

        txtFilterGiaMin.setText("0");

        jLabel23.setText("đến");

        txtFilterGiaMax.setText("100000000");

        jLabel20.setText("Trạng thái");

        cbxFilterXuatXu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxFilterXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFilterXuatXuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFilterSanPhamChiTietLayout = new javax.swing.GroupLayout(pnlFilterSanPhamChiTiet);
        pnlFilterSanPhamChiTiet.setLayout(pnlFilterSanPhamChiTietLayout);
        pnlFilterSanPhamChiTietLayout.setHorizontalGroup(
            pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFilterSanPhamChiTietLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFilterSanPhamChiTietLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18))
                    .addGroup(pnlFilterSanPhamChiTietLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(20, 20, 20)))
                .addGroup(pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbxFilterGia, 0, 98, Short.MAX_VALUE)
                    .addComponent(cbxFilterMauSac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(230, 230, 230)
                .addGroup(pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel19))
                .addGap(23, 23, 23)
                .addGroup(pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxFilterXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxFilterThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(168, 168, 168)
                .addGroup(pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFilterSanPhamChiTietLayout.createSequentialGroup()
                        .addComponent(txtFilterGiaMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addComponent(txtFilterGiaMax, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbxFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(154, Short.MAX_VALUE))
        );
        pnlFilterSanPhamChiTietLayout.setVerticalGroup(
            pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilterSanPhamChiTietLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxFilterGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(cbxFilterThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel22)
                    .addComponent(txtFilterGiaMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(txtFilterGiaMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbxFilterMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(cbxFilterXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(cbxFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        btnCreateSanPhamChiTiet1.setText("Thêm SPCT");
        btnCreateSanPhamChiTiet1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateSanPhamChiTiet1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(txtSearchSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCreateSanPhamChiTiet1)
                .addGap(327, 327, 327))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreateSanPhamChiTiet1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlFilterSanPhamChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFilterSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPaneSanPham.addTab("Chi tiết sản phẩm", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        tblThuocTinhSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Mã", "Tên"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblThuocTinhSanPham.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblThuocTinhSanPham.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tblThuocTinhSanPham);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtTenThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenThuocTinhActionPerformed(evt);
            }
        });

        jLabel4.setText("Tên thuộc tính");

        btnCreateThuocTinh.setText("Thêm");
        btnCreateThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateThuocTinhActionPerformed(evt);
            }
        });

        btnResetFormThuocTinhSanPham.setText("Reset");
        btnResetFormThuocTinhSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetFormThuocTinhSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtTenThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(378, 378, 378)
                .addComponent(btnCreateThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(btnResetFormThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreateThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResetFormThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        buttonGroup1.add(rdoChuoi);
        rdoChuoi.setText("Chuôi");
        rdoChuoi.setActionCommand("chuoi");
        rdoChuoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoChuoiMouseClicked(evt);
            }
        });
        rdoChuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoChuoiActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoTayCam);
        rdoTayCam.setText("Tay cầm");
        rdoTayCam.setActionCommand("tay_cam");
        rdoTayCam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoTayCamMouseClicked(evt);
            }
        });
        rdoTayCam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTayCamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoDauCoBan);
        rdoDauCoBan.setText("Đầu cơ bản");
        rdoDauCoBan.setActionCommand("dau_co_ban");
        rdoDauCoBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoDauCoBanMouseClicked(evt);
            }
        });
        rdoDauCoBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDauCoBanActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNgon);
        rdoNgon.setText("Ngọn");
        rdoNgon.setActionCommand("ngon");
        rdoNgon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoNgonMouseClicked(evt);
            }
        });
        rdoNgon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNgonActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoRen);
        rdoRen.setText("Ren");
        rdoRen.setActionCommand("ren");
        rdoRen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoRenMouseClicked(evt);
            }
        });
        rdoRen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoRenActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoThuongHieu);
        rdoThuongHieu.setText("Thương Hiệu");
        rdoThuongHieu.setActionCommand("thuong_hieu");
        rdoThuongHieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoThuongHieuMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoXuatXu);
        rdoXuatXu.setText("Xuất xứ");
        rdoXuatXu.setActionCommand("xuat_xu");
        rdoXuatXu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoXuatXuMouseClicked(evt);
            }
        });
        rdoXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoXuatXuActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoMauSac);
        rdoMauSac.setText("Màu sắc");
        rdoMauSac.setActionCommand("mau_sac");
        rdoMauSac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoMauSacMouseClicked(evt);
            }
        });
        rdoMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoMauSacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlChooseThuocTinhSanPhamLayout = new javax.swing.GroupLayout(pnlChooseThuocTinhSanPham);
        pnlChooseThuocTinhSanPham.setLayout(pnlChooseThuocTinhSanPhamLayout);
        pnlChooseThuocTinhSanPhamLayout.setHorizontalGroup(
            pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChooseThuocTinhSanPhamLayout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addGroup(pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoChuoi)
                    .addComponent(rdoTayCam))
                .addGap(173, 173, 173)
                .addGroup(pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoRen)
                    .addComponent(rdoNgon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
                .addGroup(pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoXuatXu)
                    .addComponent(rdoMauSac))
                .addGap(241, 241, 241)
                .addGroup(pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoThuongHieu)
                    .addComponent(rdoDauCoBan))
                .addGap(169, 169, 169))
        );
        pnlChooseThuocTinhSanPhamLayout.setVerticalGroup(
            pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChooseThuocTinhSanPhamLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoThuongHieu)
                    .addComponent(rdoXuatXu)
                    .addComponent(rdoNgon)
                    .addComponent(rdoChuoi))
                .addGap(24, 24, 24)
                .addGroup(pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoDauCoBan)
                    .addComponent(rdoMauSac)
                    .addComponent(rdoRen)
                    .addComponent(rdoTayCam))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlChooseThuocTinhSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlChooseThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabPaneSanPham.addTab("Thuộc tính sản phẩm", jPanel5);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabPaneSanPham)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPaneSanPham)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        try {
            int idx = getSelectedSanPhamIndex();

            String ma = sanPhamTblModel.getValueAt(idx, 1).toString();

            int id = Stream.concat(spRepo.selectAll().stream(), spRepo.selectDeleted().stream()).filter(item -> item.getMaSanPham().equals(ma)).findFirst().get().getId();

            SanPham found = spRepo.selectById(id);

            if (found == null) {
                throw new RuntimeException("Không tìm thấy sản phẩm");
            }

            txtTenSanPham.setText(found.getTenSanPham());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void tblSanPhamChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamChiTietMouseClicked
     try {
            int idx = getSelectedSanPhamChiTietIndex();

            String ma = String.valueOf(sanPhamChiTietTblModel.getValueAt(idx, 2));

            SanPhamChiTiet found = Stream.concat(spctRepo.selectAll().stream(), spctRepo.selectDeleted().stream()).filter(item -> item.getMaSanPham().equals(ma)).findFirst().orElse(null);

            if (found == null) {
                throw new RuntimeException("Không tìm thấy sản phẩm chi tiết");
            }

            new SanPhamChiTietEditForm(found, this).setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tblSanPhamChiTietMouseClicked

    private void cbxFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFilterTrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxFilterTrangThaiActionPerformed

    private void cbxFilterXuatXuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFilterXuatXuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxFilterXuatXuActionPerformed

    private void txtTenThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenThuocTinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenThuocTinhActionPerformed

    private void rdoChuoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoChuoiMouseClicked
        renderThuocTinhTbl(rdoChuoi.getActionCommand());
    }//GEN-LAST:event_rdoChuoiMouseClicked

    private void rdoChuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoChuoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoChuoiActionPerformed

    private void rdoTayCamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoTayCamMouseClicked
        renderThuocTinhTbl(rdoTayCam.getActionCommand());
    }//GEN-LAST:event_rdoTayCamMouseClicked

    private void rdoTayCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTayCamActionPerformed
        // TODO add your handling code here:
         renderThuocTinhTbl(rdoTayCam.getActionCommand());
    }//GEN-LAST:event_rdoTayCamActionPerformed

    private void rdoDauCoBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoDauCoBanMouseClicked
        renderThuocTinhTbl(rdoDauCoBan.getActionCommand());
    }//GEN-LAST:event_rdoDauCoBanMouseClicked

    private void rdoDauCoBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDauCoBanActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_rdoDauCoBanActionPerformed

    private void rdoNgonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoNgonMouseClicked
 renderThuocTinhTbl(rdoNgon.getActionCommand());
    }//GEN-LAST:event_rdoNgonMouseClicked

    private void rdoNgonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNgonActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_rdoNgonActionPerformed

    private void rdoRenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoRenMouseClicked

    }//GEN-LAST:event_rdoRenMouseClicked

    private void rdoRenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoRenActionPerformed
        // TODO add your handling code here:
         renderThuocTinhTbl(rdoRen.getActionCommand());
    }//GEN-LAST:event_rdoRenActionPerformed

    private void rdoThuongHieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoThuongHieuMouseClicked
        renderThuocTinhTbl(rdoThuongHieu.getActionCommand());
    }//GEN-LAST:event_rdoThuongHieuMouseClicked

    private void rdoXuatXuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoXuatXuMouseClicked

    }//GEN-LAST:event_rdoXuatXuMouseClicked

    private void rdoMauSacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoMauSacMouseClicked

    }//GEN-LAST:event_rdoMauSacMouseClicked

    private void rdoMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoMauSacActionPerformed
        // TODO add your handling code here:
         renderThuocTinhTbl(rdoMauSac.getActionCommand());
    }//GEN-LAST:event_rdoMauSacActionPerformed

    private void rdoXuatXuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoXuatXuActionPerformed
        // TODO add your handling code here:
         renderThuocTinhTbl(rdoXuatXu.getActionCommand());
    }//GEN-LAST:event_rdoXuatXuActionPerformed

    private void btnCreateSanPhamChiTiet1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateSanPhamChiTiet1ActionPerformed
        try {
            new SanPhamChiTietCreateForm(this).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnCreateSanPhamChiTiet1ActionPerformed

    private void btnCreateSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateSanPhamActionPerformed
        try {
            List<SanPham> list = spRepo.selectAllTK();
            int i = list.size()+1;
            String ma = "";
            if(i<10){
                ma = "SP00" + String.valueOf(i);
            } else if(i>=10 && i<100){
                ma = "SP0" + String.valueOf(i);
            } else {
                ma = "SP" + String.valueOf(i);
            }
            spRepo.createTK(txtTenSanPham.getText(), ma);
                        SanPham newSanPham = SanPham.builder().maSanPham(MaGenerator.generate("SP")).tenSanPham(txtTenSanPham.getText().trim()).build();
            
                        boolean pass = SanPhamValidator.validateCreate(newSanPham);
            
                        if (pass) {
                                spRepo.create(newSanPham);
                            }
                        renderSanPhamTbl(spRepo.selectAll());
                        JOptionPane.showMessageDialog(this, "Thêm thành công");
            
                        txtTenSanPham.setText("");
                        cbxSanPhamStatusFilter.setSelectedIndex(0);
                        txtTenThuocTinh.setText("");
            reloadDisplays();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());

        }
    }//GEN-LAST:event_btnCreateSanPhamActionPerformed

    private void btnUpdateSanPham1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSanPham1ActionPerformed
        try {
            int idx = getSelectedSanPhamIndex();

            boolean alreadyDisabled = sanPhamTblModel.getValueAt(idx, 4).toString().equals("Ngừng bán");

            if (alreadyDisabled) {
                throw new RuntimeException("Sản phẫm đã ngừng bán ");
            }

            String ma = sanPhamTblModel.getValueAt(idx, 1).toString();

            int id = spRepo.selectAll().stream().filter(item -> item.getMaSanPham().equals(ma)).findFirst().get().getId();

            SanPham found = spRepo.selectById(id);

            if (found == null) {
                throw new RuntimeException("Không tìm thấy sản phẩm");
            }

            SanPham newSanPham = SanPham.builder().maSanPham(found.getMaSanPham()).id(found.getId()).tenSanPham(txtTenSanPham.getText().trim()).build();

            boolean pass = SanPhamValidator.validateUpdate(newSanPham);

            if (pass) {
                spRepo.update(newSanPham, found.getId());
            }
            renderSanPhamTbl(spRepo.selectAll());
            JOptionPane.showMessageDialog(this, "Sửa thành công");

            txtTenSanPham.setText("");
            cbxSanPhamStatusFilter.setSelectedIndex(0);
            txtTimKiem.setText("");
            reloadDisplays();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());

        }
    }//GEN-LAST:event_btnUpdateSanPham1ActionPerformed

    private void btnResetSanPhamFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSanPhamFormActionPerformed
        txtTenSanPham.setText("");
        cbxSanPhamStatusFilter.setSelectedIndex(0);
        txtTimKiem.setText("");

        reloadDisplays();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnResetSanPhamFormActionPerformed

    private void btnDeleteSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSanPhamActionPerformed
        try {
            int idx = getSelectedSanPhamIndex();

            boolean alreadyDisabled = sanPhamTblModel.getValueAt(idx, 4).toString().equals("Ngừng bán");

            if (alreadyDisabled) {
                throw new RuntimeException("Sản phẫm đã ngừng bán ");
            }

            String ma = sanPhamTblModel.getValueAt(idx, 1).toString();

            int id = spRepo.selectAll().stream().filter(item -> item.getMaSanPham().equals(ma)).findFirst().get().getId();

            List<SanPhamChiTiet> dependencies = spctRepo.selectBySanPhamId(id);
            int cascade = dependencies.size();

            if (cascade > 0) {
                if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(this, "Sản phẩm trên có " + cascade + " sản phẩm chi tiết phụ thuộc. Khi xóa sẽ ảnh hưởng đến spct.", "Xóa", JOptionPane.OK_CANCEL_OPTION)) {
                    return;
                }
            }
            if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(this, "Xóa?", "Xóa", JOptionPane.OK_CANCEL_OPTION)) {
                return;
            }
            for (SanPhamChiTiet item : dependencies) {
                spctRepo.delete(item.getId());
            }
            spRepo.delete(id);
            renderSanPhamTbl(spRepo.selectAll());
            JOptionPane.showMessageDialog(this, "Xóa thành công");

            txtTenSanPham.setText("");
            cbxSanPhamStatusFilter.setSelectedIndex(0);
            txtTimKiem.setText("");
            reloadDisplays();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnDeleteSanPhamActionPerformed

    private void cbxSanPhamStatusFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSanPhamStatusFilterActionPerformed
        // TODO add your handling code here:
        renderSanPhamTbl(spRepo.selectAll());
    }//GEN-LAST:event_cbxSanPhamStatusFilterActionPerformed

    private void btnCreateThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateThuocTinhActionPerformed
        try {
            String table = buttonGroup1.getSelection().getActionCommand();

            String newName = txtTenThuocTinh.getText().trim();

            if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(this, "Thêm?", "Thêm", JOptionPane.OK_CANCEL_OPTION)) {
                return;
            }

            if (ThuocTinhValidator.validate(newName, table)) {
                switch (table) {
                    case "chuoi":
                    new ChuoiRepository().create(Chuoi.builder().ma(MaGenerator.generate("CH")).ten(newName).build());
                    break;
                    case "tay_cam":
                    new TayCamRepository().create(TayCam.builder().ma(MaGenerator.generate("TC")).ten(newName).build());
                    break;
                    case "xuat_xu":
                    new XuatXuRepository().create(XuatXu.builder().ma(MaGenerator.generate("XX")).ten(newName).build());
                    break;
                    case "dau_co_ban":
                    new DauCoBanRepository().create(DauCoBan.builder().ma(MaGenerator.generate("DBC")).ten(newName).build());
                    break;
                    case "ngon":
                    new NgonRepository().create(Ngon.builder().ma(MaGenerator.generate("NG")).ten(newName).build());
                    break;
                    case "ren":
                    new RenRepository().create(Ren.builder().ma(MaGenerator.generate("RN")).ten(newName).build());
                    break;
                    case "thuong_hieu":
                    new ThuongHieuRepository().create(ThuongHieu.builder().ma(MaGenerator.generate("TH")).ten(newName).build());
                    break;
                    case "mau_sac":
                    new MauSacRepository().create(MauSac.builder().ma(MaGenerator.generate("MS")).ten(newName).build());
                    break;
                    default:
                    throw new RuntimeException("Thuộc tính không tồn tại");
                }

            }

            renderThuocTinhTbl(table);
            txtTenThuocTinh.setText("");
            reloadDisplays();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnCreateThuocTinhActionPerformed

    private void btnResetFormThuocTinhSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetFormThuocTinhSanPhamActionPerformed

    }//GEN-LAST:event_btnResetFormThuocTinhSanPhamActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateSanPham;
    private javax.swing.JButton btnCreateSanPhamChiTiet1;
    private javax.swing.JButton btnCreateThuocTinh;
    private javax.swing.JButton btnDeleteSanPham;
    private javax.swing.JButton btnResetFormThuocTinhSanPham;
    private javax.swing.JButton btnResetSanPhamForm;
    private javax.swing.JButton btnUpdateSanPham1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxFilterGia;
    private javax.swing.JComboBox<String> cbxFilterMauSac;
    private javax.swing.JComboBox<String> cbxFilterThuongHieu;
    private javax.swing.JComboBox<String> cbxFilterTrangThai;
    private javax.swing.JComboBox<String> cbxFilterXuatXu;
    private javax.swing.JComboBox<String> cbxSanPhamStatusFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel pnlChooseThuocTinhSanPham;
    private javax.swing.JPanel pnlFilterSanPhamChiTiet;
    private javax.swing.JRadioButton rdoChuoi;
    private javax.swing.JRadioButton rdoDauCoBan;
    private javax.swing.JRadioButton rdoMauSac;
    private javax.swing.JRadioButton rdoNgon;
    private javax.swing.JRadioButton rdoRen;
    private javax.swing.JRadioButton rdoTayCam;
    private javax.swing.JRadioButton rdoThuongHieu;
    private javax.swing.JRadioButton rdoXuatXu;
    private javax.swing.JTabbedPane tabPaneSanPham;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblSanPhamChiTiet;
    private javax.swing.JTable tblThuocTinhSanPham;
    private javax.swing.JTextField txtFilterGiaMax;
    private javax.swing.JTextField txtFilterGiaMin;
    private javax.swing.JTextField txtSearchSanPhamChiTiet;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtTenThuocTinh;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
