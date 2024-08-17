/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import entities.receipts.GiamGiaHoaDon;
import javax.swing.table.DefaultTableModel;
import repositories.receipts.Rp_GiamGiaHD;

import utils.DateConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author nguye
 */
public class GiamGiaForm extends javax.swing.JPanel {

    private DefaultTableModel dtmGG = new DefaultTableModel();

    private Rp_GiamGiaHD repoGG = new Rp_GiamGiaHD();

    /**
     * Creates new form QuanLyPhieuGiamGiaForm
     */
    public GiamGiaForm() {
        initComponents();
        dtmGG = (DefaultTableModel) tblGiamGiaHD.getModel();
        this.fillToTable(repoGG.getAll());
        this.textSearch();
    }

    private void addComboBox() {
        cboLoc.addActionListener(evt -> {
            String selectedItem = cboLoc.getSelectedItem().toString();

            switch (selectedItem) {
                case "Đang diễn ra":
                    fillToTable(repoGG.getAllTrangThai(1));
                    break;
                case "Sắp diễn ra ":
                    fillToTable(repoGG.getAllTrangThai(2));
                    break;
                case "Đã kết thúc ":
                    fillToTable(repoGG.getAllTrangThai(3));
                    break;
                case "Tất cả ":
                    fillToTable(repoGG.getAll());
                    break;
            }
        });
    }


    private void fillToTable(ArrayList<GiamGiaHoaDon> listGiamGia) {
        dtmGG.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        listGiamGia.forEach(s -> dtmGG.addRow(new Object[]{
            index.getAndIncrement(), s.getMa(), s.getTen(), s.getMoTa(),
            s.getGiaTriToiThieu(), s.getGiaTriToiDa(), s.getGiamPhanTram(),s.getGiamSoToiDa() ,s.getSoLuong(), s.getNgayBatDau(),
            s.getNgayKetThuc(), s.getTrangThai() == 1 ? "Đang diễn ra " : s.getTrangThai() == 2 ? "Sắp diễn ra" : s.getTrangThai() == 3 ? "Đã kết thúc" : "Tất cả", s.isLoaiGiamGia() ?"Tien mat":"Chuyen khoan"
        }));
    }

    private void showTable(int index) {
        GiamGiaHoaDon gg = repoGG.getAll().get(index);
        txtMaVC.setText(gg.getMa());
        txtTenVC.setText(gg.getTen());
        txtMoTa.setText(gg.getMoTa());
        txtGiaTriTD.setText(gg.getGiaTriToiDa() + "");
        txtGiaTriTT.setText(gg.getGiaTriToiThieu() + "");
        txtGiaTriPT.setText(gg.getGiamPhanTram() + "");
        txtGiamSoTD.setText(gg.getGiamSoToiDa() + "");
        txtSoLuong.setText(gg.getSoLuong() + "");
        jdNgayBD.setDate(gg.getNgayBatDau());
        jdNgayKT.setDate(gg.getNgayKetThuc());

    }

    private GiamGiaHoaDon getFormData() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the pattern to match your date format
//
//        try {
//           
//        } catch (ParseException e) {
//            e.printStackTrace();
//            // Handle the exception as needed (e.g., show an error message to the user)
//            return null;
//        }

        String ma = txtMaVC.getText().trim().toString();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Moi ban nhap ma");
            txtMaVC.requestFocus();
            return null;
        }
        String ten = txtTenVC.getText().trim().toString();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Moi ban nhap ten");
            txtTenVC.requestFocus();
            return null;
        }
        String moTa = txtMoTa.getText().trim().toString();
        if (moTa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Moi ban nhap moTa");
            txtMoTa.requestFocus();
            return null;
        }

        // gia tri toi thieu
        Integer giaTriToiThieu = -1;
        try {
            giaTriToiThieu = Integer.parseInt(txtGiaTriTT.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Moi ban nhap gia tri toi thieu");
            txtGiaTriTT.requestFocus();
            return null;
        }

        // gia tri toi da
        Integer giaTriToiDa = 0;
        try {
            giaTriToiDa = Integer.parseInt(txtGiaTriTD.getText());
            if (giaTriToiDa <= 499) {
                JOptionPane.showMessageDialog(this, "Gia tri toi da phai > 499");
                txtGiaTriTD.requestFocus();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Moi ban nhap gia tri toi da");
            txtGiaTriTD.requestFocus();
            return null;
        }

        // phan tram
        int giamPhanTram = 0;
        try {
            giamPhanTram = Integer.parseInt(txtGiaTriPT.getText());
            if (giamPhanTram  <=0 ) {
                JOptionPane.showMessageDialog(this, "Giam phan tram phai lon hon 0");
                txtGiaTriPT.requestFocus();
                return null;
            }else if(giamPhanTram>=101){
                JOptionPane.showMessageDialog(this, "Giam phan tram phai nho hon 100");
                txtGiaTriPT.requestFocus();
                 return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Moi ban nhap gia tri soPT");
            txtGiaTriPT.requestFocus();
            return null;
        }

        //So toi da      
        Integer giamSoToiDa = 0;
        try {
            giamSoToiDa = Integer.parseInt(txtGiamSoTD.getText());
            if (giamSoToiDa <= 400) {
                JOptionPane.showMessageDialog(this, "So toi da phai lon hon 400");
                txtGiamSoTD.requestFocus();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Moi ban nhap gia tri giam so pt");
            txtGiamSoTD.requestFocus();
            return null;
        }

        // So luong
        Integer soLuong = 0;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "So luong phai lon hon 0");
                txtSoLuong.requestFocus();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Moi ban nhap so luong");
            txtSoLuong.requestFocus();
            return null;
        }
        Date ngayBD = jdNgayBD.getDate();
        Date ngayKT = jdNgayKT.getDate();

        if (ngayBD.after(ngayKT) || !ngayBD.before(ngayKT)) {
            if (ngayKT == null) {
                JOptionPane.showMessageDialog(this, "Moi ban nhap ngay thang");
                jdNgayKT.requestFocus();
                return null;
            } else {
                JOptionPane.showMessageDialog(this, "Ngay bat dau phai sau ngay ket thuc");
                jdNgayKT.requestFocus();
                return null;
            }
        }

//        if (ngayKT==null) {
//            JOptionPane.showMessageDialog(this, "Moi ban nhap ngay kt");
//            jdNgayKT.requestFocus();
//            return null;
//        }
       return new GiamGiaHoaDon(null, ma, ten, moTa, giaTriToiThieu, giaTriToiDa, 0, giamSoToiDa, soLuong, ngayBD, ngayKT, ngayKT, ngayKT, ngayKT, 0, true);

    }

    private void textSearch() {
        txt_TimKiem.getDocument().addDocumentListener(new DocumentListener() {
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
        String timKiem = txt_TimKiem.getText().trim();
        fillToTable(repoGG.search(timKiem));
    }

    public void reset() {
        txtMaVC.setText("");
        txtTenVC.setText("");
        txtMoTa.setText("");
        txtGiaTriTD.setText("");
        txtGiaTriTT.setText("");
        txtGiaTriPT.setText("");
        txtSoLuong.setText("");
        jdNgayBD.setDate(null);
        jdNgayKT.setDate(null);
//        cbxTrangThai.setSelectedItem("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGiamGiaHD = new javax.swing.JTable();
        txt_TimKiem = new javax.swing.JTextField();
        cboLoc = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        lblMaVC = new javax.swing.JLabel();
        lblTenVC = new javax.swing.JLabel();
        txtMaVC = new javax.swing.JTextField();
        txtTenVC = new javax.swing.JTextField();
        lblGiaTriTD = new javax.swing.JLabel();
        lblGiaTriTT = new javax.swing.JLabel();
        lblGiamPT = new javax.swing.JLabel();
        txtGiaTriTD = new javax.swing.JTextField();
        txtGiaTriPT = new javax.swing.JTextField();
        lblNgayBD = new javax.swing.JLabel();
        lblNgayKT = new javax.swing.JLabel();
        txtGiaTriTT = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        lblGiamSoTD = new javax.swing.JLabel();
        txtGiamSoTD = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtMoTa = new javax.swing.JTextField();
        jdNgayBD = new com.toedter.calendar.JDateChooser();
        jdNgayKT = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Phiếu giảm giá", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tblGiamGiaHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã voucher", "Tên voucher", "Mô tả", "Giá trị tôi thiểu", "Giá trị tối đa", "Giảm phần trăm", "Giảm số tối đa", "Số lượng", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái", "Loại giảm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGiamGiaHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGiamGiaHDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGiamGiaHD);
        if (tblGiamGiaHD.getColumnModel().getColumnCount() > 0) {
            tblGiamGiaHD.getColumnModel().getColumn(0).setResizable(false);
            tblGiamGiaHD.getColumnModel().getColumn(1).setResizable(false);
            tblGiamGiaHD.getColumnModel().getColumn(2).setResizable(false);
            tblGiamGiaHD.getColumnModel().getColumn(3).setResizable(false);
            tblGiamGiaHD.getColumnModel().getColumn(4).setResizable(false);
            tblGiamGiaHD.getColumnModel().getColumn(5).setResizable(false);
            tblGiamGiaHD.getColumnModel().getColumn(6).setResizable(false);
            tblGiamGiaHD.getColumnModel().getColumn(7).setResizable(false);
            tblGiamGiaHD.getColumnModel().getColumn(8).setResizable(false);
            tblGiamGiaHD.getColumnModel().getColumn(9).setResizable(false);
            tblGiamGiaHD.getColumnModel().getColumn(10).setResizable(false);
            tblGiamGiaHD.getColumnModel().getColumn(11).setResizable(false);
            tblGiamGiaHD.getColumnModel().getColumn(12).setResizable(false);
        }

        txt_TimKiem.setToolTipText("");
        txt_TimKiem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_TimKiem.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_TimKiem.setName(""); // NOI18N
        txt_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TimKiemActionPerformed(evt);
            }
        });

        cboLoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang diễn ra", "Sắp diễn ra", "Đã kết thúc", "Tất cả" }));
        cboLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180)
                .addComponent(cboLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblMaVC.setText("Mã voucher");

        lblTenVC.setText("Tên voucher");

        txtMaVC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaVCActionPerformed(evt);
            }
        });

        txtTenVC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenVCActionPerformed(evt);
            }
        });

        lblGiaTriTD.setText("Giá trị tối đa");

        lblGiaTriTT.setText("Giá trị tối thiểu");

        lblGiamPT.setText("Giảm phần trăm");

        txtGiaTriTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTriTDActionPerformed(evt);
            }
        });

        txtGiaTriPT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTriPTActionPerformed(evt);
            }
        });

        lblNgayBD.setText("Ngày bắt đầu");

        lblNgayKT.setText("Ngày kết thúc");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Đã kết thúc");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        lblGiamSoTD.setText("Giảm số tối đa");

        txtGiamSoTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiamSoTDActionPerformed(evt);
            }
        });

        jLabel2.setText("Mô tà");

        jLabel3.setText("Số lượng");

        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenVC)
                            .addComponent(lblMaVC)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMoTa, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addComponent(txtMaVC)
                            .addComponent(txtTenVC)
                            .addComponent(txtSoLuong))
                        .addGap(125, 135, Short.MAX_VALUE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)
                        .addComponent(btnXoa)
                        .addGap(96, 96, 96)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGiamPT)
                            .addComponent(lblGiaTriTD)
                            .addComponent(lblNgayBD))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jdNgayBD, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addComponent(txtGiaTriTD)
                            .addComponent(txtGiaTriPT))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblGiaTriTT)
                                    .addComponent(lblGiamSoTD))
                                .addGap(65, 65, 65)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGiaTriTT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGiamSoTD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblNgayKT)
                                .addGap(71, 71, 71)
                                .addComponent(jdNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaTriTD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGiaTriTT)
                            .addComponent(txtGiaTriTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGiaTriTD)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaVC)
                            .addComponent(txtMaVC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGiamPT)
                            .addComponent(txtGiaTriPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGiamSoTD)
                            .addComponent(txtGiamSoTD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTenVC)
                            .addComponent(txtTenVC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jdNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNgayKT, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdNgayBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(lblNgayBD)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi))
                .addGap(21, 21, 21))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quản  Lý Phiếu Giảm Giá");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void txtMaVCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaVCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaVCActionPerformed

    private void txtGiaTriTDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTriTDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTriTDActionPerformed

    private void txtGiaTriPTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTriPTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTriPTActionPerformed

    private void txtTenVCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenVCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenVCActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
//        GiamGiaHoaDon gg = repoGG.getAll().get(tblGiamGiaHD.getSelectedRow());
//        repoGG.update(getFormData(), gg.getMa());
//        fillToTable(repoGG.getAll());

           String ma = txtMaVC.getText().trim();
        if (getFormData()!= null) {
            if (repoGG.update(getFormData(), ma)) {
                JOptionPane.showMessageDialog(this, "Sua thanh cong");
                this.fillToTable(repoGG.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Sua that bai");
            }
        }
        
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        GiamGiaHoaDon gg = repoGG.getAll().get(tblGiamGiaHD.getSelectedRow());
        repoGG.delete(gg.getMa());
        fillToTable(repoGG.getAll());
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblGiamGiaHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGiamGiaHDMouseClicked
        showTable(tblGiamGiaHD.getSelectedRow());
    }//GEN-LAST:event_tblGiamGiaHDMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
       if(getFormData()!= null){
           if (repoGG.checkTrung(txtMaVC.getText())!=null) {
               JOptionPane.showMessageDialog(this, "trung ma");
               txtMaVC.requestFocus();
           }else{
               repoGG.add(getFormData());
               JOptionPane.showConfirmDialog(this, "Ban co muon them khong");
               fillToTable(repoGG.getAll());
               JOptionPane.showMessageDialog(this, "them thanh cong");
           }
           JOptionPane.showMessageDialog(this, "them that bai");
       }

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        this.reset();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void txtGiamSoTDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiamSoTDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiamSoTDActionPerformed

    private void txt_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TimKiemActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_TimKiemActionPerformed

    private void cboLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLocActionPerformed
        // TODO add your handling code here:\
        String selectedItem = cboLoc.getSelectedItem().toString();

        if (selectedItem.equals("Đang diễn ra")) {
            this.fillToTable(repoGG.getAllTrangThai(1));
        } else if (selectedItem.equals("Sắp diễn ra")) {
            this.fillToTable(repoGG.getAllTrangThai(2));
        } else if (selectedItem.equals("Đã kết thúc")) {
            this.fillToTable(repoGG.getAllTrangThai(3));
        } else if (selectedItem.equals("Tất cả")) {
            this.fillToTable(repoGG.getAll());
        } else {
            // Xử lý trường hợp mặc định hoặc không hợp lệ nếu cần
            JOptionPane.showMessageDialog(this, "Trạng thái không hợp lệ");
        }
    }//GEN-LAST:event_cboLocActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboLoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdNgayBD;
    private com.toedter.calendar.JDateChooser jdNgayKT;
    private javax.swing.JLabel lblGiaTriTD;
    private javax.swing.JLabel lblGiaTriTT;
    private javax.swing.JLabel lblGiamPT;
    private javax.swing.JLabel lblGiamSoTD;
    private javax.swing.JLabel lblMaVC;
    private javax.swing.JLabel lblNgayBD;
    private javax.swing.JLabel lblNgayKT;
    private javax.swing.JLabel lblTenVC;
    private javax.swing.JTable tblGiamGiaHD;
    private javax.swing.JTextField txtGiaTriPT;
    private javax.swing.JTextField txtGiaTriTD;
    private javax.swing.JTextField txtGiaTriTT;
    private javax.swing.JTextField txtGiamSoTD;
    private javax.swing.JTextField txtMaVC;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenVC;
    private javax.swing.JTextField txt_TimKiem;
    // End of variables declaration//GEN-END:variables
}
