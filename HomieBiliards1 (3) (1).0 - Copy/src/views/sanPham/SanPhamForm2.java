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

/**
 *
 * @author Mtt
 */
public class SanPhamForm2 extends javax.swing.JPanel {

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
     * Creates new form Welcome
     */
    public SanPhamForm2() {
        initComponents();

        sanPhamTblModel = (DefaultTableModel) tblSanPham.getModel();
        sanPhamChiTietTblModel = (DefaultTableModel) tblSanPhamChiTiet.getModel();
        thuocTinhTblModel = (DefaultTableModel) tblThuocTinhSanPham.getModel();

        spRepo = new SanPhamRepository();

        spctRepo = new SanPhamChiTietRepository();

        renderSanPhamChiTietFilterGroup();

        renderSanPhamTbl();
        renderSanPhamChiTietTbl(null);

    }

    public void reloadDisplays() {

        renderSanPhamChiTietFilterGroup();
        renderSanPhamTbl();
        renderSanPhamChiTietTbl(null);
    }

    //SanPham methods
    private void renderSanPhamTbl() {
        try {
            List<SanPham> list;
            int option = cbxSanPhamStatusFilter.getSelectedIndex();
            String searchTerm = txtSearchSanPham.getText();
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
                        item.getSoLuong(),
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupThuocTinhSanPham = new javax.swing.ButtonGroup();
        tabPaneSanPham = new javax.swing.JTabbedPane();
        pnlSanPham = new javax.swing.JPanel();
        pnlSearchSanPham = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTenSanPham = new javax.swing.JTextField();
        pnlSanPhamInfo = new javax.swing.JPanel();
        btnResetSanPhamForm = new javax.swing.JButton();
        btnUpdateSanPham = new javax.swing.JButton();
        btnDeleteSanPham = new javax.swing.JButton();
        btnCreateSanPham = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        pnlTableSanPham = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbxSanPhamStatusFilter = new javax.swing.JComboBox<>();
        txtSearchSanPham = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnFindSanPhamChiTiet = new javax.swing.JButton();
        pnlChiTietSanPham = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPhamChiTiet = new javax.swing.JTable();
        btnSearchQR = new javax.swing.JButton();
        btnExportExcel = new javax.swing.JButton();
        btnExportQR = new javax.swing.JButton();
        btnCreateSanPhamChiTiet1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtSearchSanPhamChiTiet = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnResetSanPhamChiTietForm = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        pnlThoucTinhSanPham = new javax.swing.JPanel();
        pnlCreateThuocTinhSanPham = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCreateThuocTinh = new javax.swing.JTextField();
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
        jScrollPane4 = new javax.swing.JScrollPane();
        tblThuocTinhSanPham = new javax.swing.JTable();

        setBorder(new javax.swing.border.MatteBorder(null));
        setForeground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1550, 900));
        setPreferredSize(new java.awt.Dimension(1500, 900));

        pnlSanPham.setPreferredSize(new java.awt.Dimension(1577, 1014));

        pnlSearchSanPham.setPreferredSize(new java.awt.Dimension(750, 100));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Thêm sản phẩm");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Tên");

        txtTenSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout pnlSearchSanPhamLayout = new javax.swing.GroupLayout(pnlSearchSanPham);
        pnlSearchSanPham.setLayout(pnlSearchSanPhamLayout);
        pnlSearchSanPhamLayout.setHorizontalGroup(
            pnlSearchSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchSanPhamLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlSearchSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(pnlSearchSanPhamLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(114, 114, 114))
        );
        pnlSearchSanPhamLayout.setVerticalGroup(
            pnlSearchSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchSanPhamLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlSearchSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(98, Short.MAX_VALUE))
        );

        pnlSanPhamInfo.setPreferredSize(new java.awt.Dimension(750, 100));

        btnResetSanPhamForm.setText("Reset form");
        btnResetSanPhamForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetSanPhamFormActionPerformed(evt);
            }
        });

        btnUpdateSanPham.setText("Sửa");
        btnUpdateSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSanPhamActionPerformed(evt);
            }
        });

        btnDeleteSanPham.setText("Xóa");
        btnDeleteSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSanPhamActionPerformed(evt);
            }
        });

        btnCreateSanPham.setText("Thêm");
        btnCreateSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSanPhamInfoLayout = new javax.swing.GroupLayout(pnlSanPhamInfo);
        pnlSanPhamInfo.setLayout(pnlSanPhamInfoLayout);
        pnlSanPhamInfoLayout.setHorizontalGroup(
            pnlSanPhamInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSanPhamInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlSanPhamInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSanPhamInfoLayout.createSequentialGroup()
                        .addComponent(btnResetSanPhamForm, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnDeleteSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSanPhamInfoLayout.createSequentialGroup()
                        .addComponent(btnCreateSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnUpdateSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(69, 69, 69))
        );
        pnlSanPhamInfoLayout.setVerticalGroup(
            pnlSanPhamInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSanPhamInfoLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(pnlSanPhamInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlSanPhamInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResetSanPhamForm, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Thông Tin Sản Phẩm");

        pnlTableSanPham.setOpaque(false);
        pnlTableSanPham.setPreferredSize(new java.awt.Dimension(1490, 800));

        jLabel2.setText("Trạng thái");

        cbxSanPhamStatusFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang bán", "Tất cả", "Ngừng bán" }));
        cbxSanPhamStatusFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSanPhamStatusFilterActionPerformed(evt);
            }
        });

        txtSearchSanPham.setText("Search...");
        txtSearchSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchSanPhamActionPerformed(evt);
            }
        });
        txtSearchSanPham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchSanPhamKeyTyped(evt);
            }
        });

        tblSanPham.setAutoCreateRowSorter(true);
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã", "Tên", "Số lượng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        btnFindSanPhamChiTiet.setText("Tìm SPCT");
        btnFindSanPhamChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindSanPhamChiTietActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTableSanPhamLayout = new javax.swing.GroupLayout(pnlTableSanPham);
        pnlTableSanPham.setLayout(pnlTableSanPhamLayout);
        pnlTableSanPhamLayout.setHorizontalGroup(
            pnlTableSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableSanPhamLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlTableSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTableSanPhamLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlTableSanPhamLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cbxSanPhamStatusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 828, Short.MAX_VALUE)
                        .addComponent(btnFindSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlTableSanPhamLayout.setVerticalGroup(
            pnlTableSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTableSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxSanPhamStatusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFindSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(291, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlSanPhamLayout = new javax.swing.GroupLayout(pnlSanPham);
        pnlSanPham.setLayout(pnlSanPhamLayout);
        pnlSanPhamLayout.setHorizontalGroup(
            pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSanPhamLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(679, 679, 679))
            .addGroup(pnlSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlTableSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 1439, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlSanPhamLayout.createSequentialGroup()
                        .addComponent(pnlSearchSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(122, 122, 122)
                        .addComponent(pnlSanPhamInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        pnlSanPhamLayout.setVerticalGroup(
            pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSanPhamLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel4)
                .addGap(35, 35, 35)
                .addGroup(pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlSearchSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(pnlSanPhamInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pnlTableSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPaneSanPham.addTab("SẢN PHẨM", pnlSanPham);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Thông tin sản phẩm chi tiết");

        pnlFilterSanPhamChiTiet.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bộ lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

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

        javax.swing.GroupLayout pnlFilterSanPhamChiTietLayout = new javax.swing.GroupLayout(pnlFilterSanPhamChiTiet);
        pnlFilterSanPhamChiTiet.setLayout(pnlFilterSanPhamChiTietLayout);
        pnlFilterSanPhamChiTietLayout.setHorizontalGroup(
            pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFilterSanPhamChiTietLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFilterSanPhamChiTietLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(22, 22, 22)
                        .addComponent(cbxFilterGia, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21))
                    .addGroup(pnlFilterSanPhamChiTietLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(cbxFilterMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19)))
                .addGap(18, 18, 18)
                .addGroup(pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbxFilterThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxFilterXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFilterSanPhamChiTietLayout.createSequentialGroup()
                        .addComponent(txtFilterGiaMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFilterGiaMax, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbxFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
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
                .addGap(19, 19, 19)
                .addGroup(pnlFilterSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbxFilterMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(cbxFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(cbxFilterXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

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

        btnSearchQR.setText("Quét QR");
        btnSearchQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchQRActionPerformed(evt);
            }
        });

        btnExportExcel.setText("Xuất Excel");
        btnExportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportExcelActionPerformed(evt);
            }
        });

        btnExportQR.setText("Xuất QR");
        btnExportQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportQRActionPerformed(evt);
            }
        });

        btnCreateSanPhamChiTiet1.setText("Thêm SPCT");
        btnCreateSanPhamChiTiet1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateSanPhamChiTiet1ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"), "Tìm kiếm ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(txtSearchSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(txtSearchSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 121, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        btnResetSanPhamChiTietForm.setText("Reset form");
        btnResetSanPhamChiTietForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetSanPhamChiTietFormActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlChiTietSanPhamLayout = new javax.swing.GroupLayout(pnlChiTietSanPham);
        pnlChiTietSanPham.setLayout(pnlChiTietSanPhamLayout);
        pnlChiTietSanPhamLayout.setHorizontalGroup(
            pnlChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChiTietSanPhamLayout.createSequentialGroup()
                .addGroup(pnlChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlChiTietSanPhamLayout.createSequentialGroup()
                        .addContainerGap(645, Short.MAX_VALUE)
                        .addGroup(pnlChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChiTietSanPhamLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(515, 515, 515))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnResetSanPhamChiTietForm, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlChiTietSanPhamLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(pnlChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlChiTietSanPhamLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(120, 120, 120)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlChiTietSanPhamLayout.createSequentialGroup()
                                .addComponent(pnlFilterSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlChiTietSanPhamLayout.createSequentialGroup()
                                .addComponent(btnCreateSanPhamChiTiet1)
                                .addGap(18, 18, 18)
                                .addComponent(btnExportExcel)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearchQR)
                                .addGap(18, 18, 18)
                                .addComponent(btnExportQR)))
                        .addGap(0, 200, Short.MAX_VALUE)))
                .addGap(49, 49, 49))
        );
        pnlChiTietSanPhamLayout.setVerticalGroup(
            pnlChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChiTietSanPhamLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlFilterSanPhamChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(pnlChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlChiTietSanPhamLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(pnlChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnExportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearchQR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExportQR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCreateSanPhamChiTiet1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(323, 323, 323))
                    .addGroup(pnlChiTietSanPhamLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(btnResetSanPhamChiTietForm, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(562, 562, 562))))
        );

        tabPaneSanPham.addTab("CHI TIẾT SẢN PHẨM", pnlChiTietSanPham);

        pnlCreateThuocTinhSanPham.setPreferredSize(new java.awt.Dimension(750, 100));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Thêm thuộc tính sản phẩm");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Tên");

        txtCreateThuocTinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCreateThuocTinh.setText("Tên thuộc tính...");

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

        javax.swing.GroupLayout pnlCreateThuocTinhSanPhamLayout = new javax.swing.GroupLayout(pnlCreateThuocTinhSanPham);
        pnlCreateThuocTinhSanPham.setLayout(pnlCreateThuocTinhSanPhamLayout);
        pnlCreateThuocTinhSanPhamLayout.setHorizontalGroup(
            pnlCreateThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateThuocTinhSanPhamLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlCreateThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCreateThuocTinhSanPhamLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(672, 672, 672))
                    .addGroup(pnlCreateThuocTinhSanPhamLayout.createSequentialGroup()
                        .addGroup(pnlCreateThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(pnlCreateThuocTinhSanPhamLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(pnlCreateThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlCreateThuocTinhSanPhamLayout.createSequentialGroup()
                                        .addComponent(btnCreateThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnResetFormThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtCreateThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlCreateThuocTinhSanPhamLayout.setVerticalGroup(
            pnlCreateThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateThuocTinhSanPhamLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlCreateThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCreateThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(32, 32, 32)
                .addGroup(pnlCreateThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateThuocTinh)
                    .addComponent(btnResetFormThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        btnGroupThuocTinhSanPham.add(rdoChuoi);
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

        btnGroupThuocTinhSanPham.add(rdoTayCam);
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

        btnGroupThuocTinhSanPham.add(rdoDauCoBan);
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

        btnGroupThuocTinhSanPham.add(rdoNgon);
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

        btnGroupThuocTinhSanPham.add(rdoRen);
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

        btnGroupThuocTinhSanPham.add(rdoThuongHieu);
        rdoThuongHieu.setText("Thương Hiệu");
        rdoThuongHieu.setActionCommand("thuong_hieu");
        rdoThuongHieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoThuongHieuMouseClicked(evt);
            }
        });

        btnGroupThuocTinhSanPham.add(rdoXuatXu);
        rdoXuatXu.setText("Xuất xứ");
        rdoXuatXu.setActionCommand("xuat_xu");
        rdoXuatXu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoXuatXuMouseClicked(evt);
            }
        });

        btnGroupThuocTinhSanPham.add(rdoMauSac);
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
                .addGap(29, 29, 29)
                .addGroup(pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlChooseThuocTinhSanPhamLayout.createSequentialGroup()
                        .addComponent(rdoChuoi)
                        .addGap(84, 84, 84)
                        .addComponent(rdoNgon))
                    .addGroup(pnlChooseThuocTinhSanPhamLayout.createSequentialGroup()
                        .addComponent(rdoTayCam)
                        .addGap(73, 73, 73)
                        .addComponent(rdoRen)))
                .addGap(73, 73, 73)
                .addGroup(pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rdoXuatXu)
                    .addComponent(rdoMauSac))
                .addGap(26, 26, 26)
                .addGroup(pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rdoThuongHieu)
                    .addComponent(rdoDauCoBan))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        pnlChooseThuocTinhSanPhamLayout.setVerticalGroup(
            pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChooseThuocTinhSanPhamLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoChuoi)
                    .addComponent(rdoNgon)
                    .addComponent(rdoXuatXu)
                    .addComponent(rdoThuongHieu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlChooseThuocTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoRen)
                    .addComponent(rdoTayCam)
                    .addComponent(rdoMauSac)
                    .addComponent(rdoDauCoBan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout pnlThoucTinhSanPhamLayout = new javax.swing.GroupLayout(pnlThoucTinhSanPham);
        pnlThoucTinhSanPham.setLayout(pnlThoucTinhSanPhamLayout);
        pnlThoucTinhSanPhamLayout.setHorizontalGroup(
            pnlThoucTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThoucTinhSanPhamLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(pnlThoucTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(pnlThoucTinhSanPhamLayout.createSequentialGroup()
                        .addComponent(pnlCreateThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlChooseThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 275, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlThoucTinhSanPhamLayout.setVerticalGroup(
            pnlThoucTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThoucTinhSanPhamLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlThoucTinhSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCreateThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlChooseThuocTinhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(309, Short.MAX_VALUE))
        );

        tabPaneSanPham.addTab("THUỘC TÍNH SẢN PHẨM", pnlThoucTinhSanPham);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabPaneSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 1522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(tabPaneSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 871, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

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
//            SanPham newSanPham = SanPham.builder().maSanPham(MaGenerator.generate("SP")).tenSanPham(txtTenSanPham.getText().trim()).build();
//
//            boolean pass = SanPhamValidator.validateCreate(newSanPham);
//
//            if (pass) {
//                spRepo.create(newSanPham);
//            }
//            renderSanPhamTbl();
//            JOptionPane.showMessageDialog(this, "Thêm thành công");
//
//            txtTenSanPham.setText("");
//            cbxSanPhamStatusFilter.setSelectedIndex(0);
//            txtSearchSanPham.setText("");
            reloadDisplays();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());

        }
    }//GEN-LAST:event_btnCreateSanPhamActionPerformed

    private void cbxSanPhamStatusFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSanPhamStatusFilterActionPerformed
        renderSanPhamTbl();
    }//GEN-LAST:event_cbxSanPhamStatusFilterActionPerformed

    private void txtSearchSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchSanPhamActionPerformed
        renderSanPhamTbl();
    }//GEN-LAST:event_txtSearchSanPhamActionPerformed

    private void btnSearchQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchQRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchQRActionPerformed

    private void btnExportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportExcelActionPerformed
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
                for (int row = 0; row < tblSanPhamChiTiet.getRowCount(); row++) {
                    XSSFRow sheetRow = sheet.createRow(row);

                    for (int col = 1; col < tblSanPhamChiTiet.getColumnCount(); col++) {
                        XSSFCell cell = sheetRow.createCell(col);

                        Object data = tblSanPhamChiTiet.getValueAt(row, col);

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
            reloadDisplays();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnExportExcelActionPerformed

    private void btnExportQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportQRActionPerformed

    }//GEN-LAST:event_btnExportQRActionPerformed

    private void btnCreateThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateThuocTinhActionPerformed
        try {
            String table = btnGroupThuocTinhSanPham.getSelection().getActionCommand();

            String newName = txtCreateThuocTinh.getText().trim();

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
            txtCreateThuocTinh.setText("");
            reloadDisplays();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnCreateThuocTinhActionPerformed

    private void rdoMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoMauSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoMauSacActionPerformed

    private void rdoRenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoRenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoRenActionPerformed

    private void btnResetFormThuocTinhSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetFormThuocTinhSanPhamActionPerformed
        txtCreateThuocTinh.setText("");    }//GEN-LAST:event_btnResetFormThuocTinhSanPhamActionPerformed

    private void btnCreateSanPhamChiTiet1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateSanPhamChiTiet1ActionPerformed
        try {
            new SanPhamChiTietCreateForm(this).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnCreateSanPhamChiTiet1ActionPerformed

    private void rdoChuoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoChuoiMouseClicked
        renderThuocTinhTbl(rdoChuoi.getActionCommand());
    }//GEN-LAST:event_rdoChuoiMouseClicked

    private void rdoTayCamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoTayCamMouseClicked
        renderThuocTinhTbl(rdoTayCam.getActionCommand());
    }//GEN-LAST:event_rdoTayCamMouseClicked

    private void rdoDauCoBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoDauCoBanMouseClicked
        renderThuocTinhTbl(rdoDauCoBan.getActionCommand());
    }//GEN-LAST:event_rdoDauCoBanMouseClicked

    private void rdoNgonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoNgonMouseClicked
        renderThuocTinhTbl(rdoNgon.getActionCommand());    }//GEN-LAST:event_rdoNgonMouseClicked

    private void rdoRenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoRenMouseClicked
        renderThuocTinhTbl(rdoRen.getActionCommand());    }//GEN-LAST:event_rdoRenMouseClicked

    private void rdoThuongHieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoThuongHieuMouseClicked
        renderThuocTinhTbl(rdoThuongHieu.getActionCommand());
    }//GEN-LAST:event_rdoThuongHieuMouseClicked

    private void rdoXuatXuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoXuatXuMouseClicked
        renderThuocTinhTbl(rdoXuatXu.getActionCommand());    }//GEN-LAST:event_rdoXuatXuMouseClicked

    private void rdoMauSacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoMauSacMouseClicked
        renderThuocTinhTbl(rdoMauSac.getActionCommand());    }//GEN-LAST:event_rdoMauSacMouseClicked

    private void btnResetSanPhamFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSanPhamFormActionPerformed
        txtTenSanPham.setText("");
        cbxSanPhamStatusFilter.setSelectedIndex(0);
        txtSearchSanPham.setText("");

        reloadDisplays();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnResetSanPhamFormActionPerformed

    private void btnUpdateSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSanPhamActionPerformed
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
            renderSanPhamTbl();
            JOptionPane.showMessageDialog(this, "Sửa thành công");

            txtTenSanPham.setText("");
            cbxSanPhamStatusFilter.setSelectedIndex(0);
            txtSearchSanPham.setText("");
            reloadDisplays();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());

        }
    }//GEN-LAST:event_btnUpdateSanPhamActionPerformed

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
            renderSanPhamTbl();
            JOptionPane.showMessageDialog(this, "Xóa thành công");

            txtTenSanPham.setText("");
            cbxSanPhamStatusFilter.setSelectedIndex(0);
            txtSearchSanPham.setText("");
            reloadDisplays();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }//GEN-LAST:event_btnDeleteSanPhamActionPerformed

    private void txtSearchSanPhamKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchSanPhamKeyTyped
        renderSanPhamTbl();
    }//GEN-LAST:event_txtSearchSanPhamKeyTyped

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

    private void btnFindSanPhamChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindSanPhamChiTietActionPerformed

        try {
            int idx = getSelectedSanPhamIndex();

            String ma = sanPhamTblModel.getValueAt(idx, 1).toString();

            int id = Stream.concat(spRepo.selectAll().stream(), spRepo.selectDeleted().stream()).filter(item -> item.getMaSanPham().equals(ma)).findFirst().get().getId();

            SanPham found = spRepo.selectById(id);
            String spName = found.getTenSanPham();

            if (found == null) {
                throw new RuntimeException("Không tìm thấy sản phẩm");
            }

            List<SanPhamChiTietResponse> list = new SanPhamChiTietService().selectAndPopulateAllSanPhamChiTiet();
            List<SanPhamChiTietResponse> match = new ArrayList<>();
            for (SanPhamChiTietResponse item : list) {
                if (item.getTenSanPham().equals(spName)) {
                    match.add(item);
                }
            }

            if (match.isEmpty()) {
                throw new RuntimeException("Sản phẩm này chưa có sản phẩm chi tiết");
            }
            tabPaneSanPham.setSelectedIndex(1);

            renderSanPhamChiTietTbl(match);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }//GEN-LAST:event_btnFindSanPhamChiTietActionPerformed

    private void tblSanPhamChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamChiTietMouseClicked
//        try {
//            int idx = getSelectedSanPhamChiTietIndex();
//
//            String ma = String.valueOf(sanPhamChiTietTblModel.getValueAt(idx, 2));
//
//            SanPhamChiTiet found = Stream.concat(spctRepo.selectAll().stream(), spctRepo.selectDeleted().stream()).filter(item -> item.getMaSanPham().equals(ma)).findFirst().orElse(null);
//
//            if (found == null) {
//                throw new RuntimeException("Không tìm thấy sản phẩm chi tiết");
//            }
//
//            new SanPhamChiTietEditForm(found, this).setVisible(true);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, e.getMessage());
//        }
    }//GEN-LAST:event_tblSanPhamChiTietMouseClicked

    private void btnResetSanPhamChiTietFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSanPhamChiTietFormActionPerformed

        reloadDisplays();

    }//GEN-LAST:event_btnResetSanPhamChiTietFormActionPerformed

    private void cbxFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFilterTrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxFilterTrangThaiActionPerformed

    private void rdoDauCoBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDauCoBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoDauCoBanActionPerformed

    private void rdoTayCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTayCamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoTayCamActionPerformed

    private void rdoChuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoChuoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoChuoiActionPerformed

    private void rdoNgonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNgonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNgonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateSanPham;
    private javax.swing.JButton btnCreateSanPhamChiTiet1;
    private javax.swing.JButton btnCreateThuocTinh;
    private javax.swing.JButton btnDeleteSanPham;
    private javax.swing.JButton btnExportExcel;
    private javax.swing.JButton btnExportQR;
    private javax.swing.JButton btnFindSanPhamChiTiet;
    private javax.swing.ButtonGroup btnGroupThuocTinhSanPham;
    private javax.swing.JButton btnResetFormThuocTinhSanPham;
    private javax.swing.JButton btnResetSanPhamChiTietForm;
    private javax.swing.JButton btnResetSanPhamForm;
    private javax.swing.JButton btnSearchQR;
    private javax.swing.JButton btnUpdateSanPham;
    private javax.swing.JComboBox<String> cbxFilterGia;
    private javax.swing.JComboBox<String> cbxFilterMauSac;
    private javax.swing.JComboBox<String> cbxFilterThuongHieu;
    private javax.swing.JComboBox<String> cbxFilterTrangThai;
    private javax.swing.JComboBox<String> cbxFilterXuatXu;
    private javax.swing.JComboBox<String> cbxSanPhamStatusFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel pnlChiTietSanPham;
    private javax.swing.JPanel pnlChooseThuocTinhSanPham;
    private javax.swing.JPanel pnlCreateThuocTinhSanPham;
    private javax.swing.JPanel pnlFilterSanPhamChiTiet;
    private javax.swing.JPanel pnlSanPham;
    private javax.swing.JPanel pnlSanPhamInfo;
    private javax.swing.JPanel pnlSearchSanPham;
    private javax.swing.JPanel pnlTableSanPham;
    private javax.swing.JPanel pnlThoucTinhSanPham;
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
    private javax.swing.JTextField txtCreateThuocTinh;
    private javax.swing.JTextField txtFilterGiaMax;
    private javax.swing.JTextField txtFilterGiaMin;
    private javax.swing.JTextField txtSearchSanPham;
    private javax.swing.JTextField txtSearchSanPhamChiTiet;
    private javax.swing.JTextField txtTenSanPham;
    // End of variables declaration//GEN-END:variables
}
