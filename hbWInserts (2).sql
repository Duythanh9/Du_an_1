CREATE DATABASE [homie_billiard]
GO

USE [homie_billiard]
GO
/****** Object:  Table [dbo].[san_pham]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[san_pham](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten_san_pham] [varchar](max) NULL,
	[ngay_tao] [date] NULL,
	[ngay_cap_nhat] [date] NULL,
	[ngay_xoa] [date] NULL,
	[trang_thai] [bit] NULL,
	[ma_san_pham] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[chuoi]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[chuoi](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [varchar](max) NULL,
	[ma] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tay_cam]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tay_cam](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [varchar](max) NULL,
	[ma] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[dau_co_ban]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dau_co_ban](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [varchar](max) NULL,
	[ma] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ngon]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ngon](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [varchar](max) NULL,
	[ma] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xuat_xu]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xuat_xu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [varchar](max) NULL,
	[ma] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ren]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ren](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [varchar](max) NULL,
	[ma] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[thuong_hieu]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[thuong_hieu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [varchar](max) NULL,
	[ma] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[mau_sac]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mau_sac](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [varchar](max) NULL,
	[ma] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[san_pham_chi_tiet]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON

GO
CREATE TABLE [dbo].[san_pham_chi_tiet](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_san_pham] [int] NULL,
	[ma_san_pham] [varchar](max) NULL,
	[mo_ta] [varchar](max) NULL,
	[ngay_tao] [date] NULL,
	[ngay_cap_nhat] [date] NULL,
	[ngay_xoa] [date] NULL,
	[trang_thai] [bit] NULL,
	[gia_ban] [int] NULL,
	[mau_sac] [int] NULL,
	[chuoi] [int] NULL,
	[tay_cam] [int] NULL,
	[dau_co_ban] [int] NULL,
	[ngon] [int] NULL,
	[ren] [int] NULL,
	[trong_luong] [int] NULL,
	[duong_kinh_dau] [float] NULL,
	[thuong_hieu] [int] NULL,
	[bao_hanh] [int] NULL,
	[xuat_xu] [int] NULL,
	[ten_san_pham] [varchar](max) NULL,
	[so_luong] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[giam_gia_hoa_don]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[giam_gia_hoa_don](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [varchar](max) NULL,
	[ten] [varchar](max) NULL,
	[mo_ta] [varchar](max) NULL,
	[gia_tri_toi_thieu] [int] NULL,
	[gia_tri_toi_da] [int] NULL,
	[giam_phan_tram] [float] NULL,
	[giam_so_toi_da] [int] NULL,
	[ngay_bat_dau] [date] NULL,
	[ngay_ket_thuc] [date] NULL,
	[ngay_tao] [date] NULL,
	[ngay_cap_nhat] [date] NULL,
	[ngay_xoa] [date] NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hoa_don]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[hoa_don](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_hoa_don] [varchar](max) NULL,
	[id_khach_hang] [int] NULL,
	[id_tai_khoan] [int] NULL,
	[ngay_tao] [date] NULL,
	[ngay_cap_nhat] [date] NULL,
	[ngay_xoa] [date] NULL,
	[gia_tri] [int] NULL,
	[so_tien_giam] [int] NULL,
	[gia_tri_tong] [int] NULL,
	[ghi_chu] [varchar](max) NULL,
	[id_trang_thai_hoa_don] [int] NULL,
	[id_giam_gia_hoa_don] [int] NULL,
	
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hoa_don_chi_tiet]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hoa_don_chi_tiet](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_hoa_don_chi_tiet] [varchar](max) NULL,
	[id_hoa_don] [int] NULL,
	[id_san_pham_chi_tiet] [int] NULL,
	[so_luong] [int] NULL,
	[gia_ban] [int] NULL,
	[trang_thai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[khach_hang]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[khach_hang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten_khach_hang] [varchar](max) NULL,
	[email] [varchar](max) NULL,
	[dien_thoai] [varchar](max) NULL,
	[dia_chi] [varchar](max) NULL,
	[ghi_chu] [varchar](max) NULL,
	[ngay_tao] [date] NULL,
	[ngay_cap_nhat] [date] NULL,
	[nguoi_tao] [int] NULL,
	[nguoi_cap_nhat] [int] NULL,
	[ngay_xoa] [date] NULL,
	[trang_thai] [bit] NULL,
	[ma] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[lich_su_hoa_don]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[lich_su_hoa_don](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_hoa_don] [int] NULL,
	[id_tai_khoan] [int] NULL,
	[ngay_cap_nhat] [date] NULL,
	[ghi_chu] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tai_khoan]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tai_khoan](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[email] [varchar](max) NULL,
	[dien_thoai] [varchar](max) NULL,
	[password] [varchar](max) NULL,
	[ho_ten] [varchar](max) NULL,
	[dia_chi] [varchar](max) NULL,
	[ghi_chu] [varchar](max) NULL,
	[chuc_vu] [bit] NULL,
	[ngay_tao] [date] NULL,
	[ngay_cap_nhat] [date] NULL,
	[ngay_xoa] [date] NULL,
	[trang_thai] [bit] NULL,
	[ma] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[thanh_toan]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[thanh_toan](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_hoa_don] [int] NULL,
	[ngay_thanh_toan] [date] NULL,
	[so_tien_mat] [int] NULL,
	[so_tien_the] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[trang_thai_hoa_don]    Script Date: 7/20/2024 12:54:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[trang_thai_hoa_don](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [varchar](max) NULL,
	[ghi_chu] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


/****** Object:  Index [UQ__thanh_to__342B812BC7C477FE]    Script Date: 7/20/2024 12:54:35 PM ******/
ALTER TABLE [dbo].[thanh_toan] ADD UNIQUE NONCLUSTERED 
(
	[id_hoa_don] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[giam_gia_hoa_don] ADD  DEFAULT ((1)) FOR [trang_thai]
GO
ALTER TABLE [dbo].[hoa_don_chi_tiet] ADD  DEFAULT ((1)) FOR [trang_thai]
GO
ALTER TABLE [dbo].[khach_hang] ADD  DEFAULT ((1)) FOR [trang_thai]
GO
ALTER TABLE [dbo].[san_pham] ADD  DEFAULT ((1)) FOR [trang_thai]
GO
ALTER TABLE [dbo].[san_pham_chi_tiet] ADD  DEFAULT ((1)) FOR [trang_thai]
GO
ALTER TABLE [dbo].[tai_khoan] ADD  DEFAULT ((1)) FOR [trang_thai]
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD FOREIGN KEY([id_giam_gia_hoa_don])
REFERENCES [dbo].[giam_gia_hoa_don] ([id])
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD FOREIGN KEY([id_khach_hang])
REFERENCES [dbo].[khach_hang] ([id])
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD FOREIGN KEY([id_tai_khoan])
REFERENCES [dbo].[tai_khoan] ([id])
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD FOREIGN KEY([id_trang_thai_hoa_don])
REFERENCES [dbo].[trang_thai_hoa_don] ([id])
GO
ALTER TABLE [dbo].[hoa_don_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_hoa_don])
REFERENCES [dbo].[hoa_don] ([id])
GO
ALTER TABLE [dbo].[hoa_don_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_san_pham_chi_tiet])
REFERENCES [dbo].[san_pham_chi_tiet] ([id])
GO
ALTER TABLE [dbo].[khach_hang]  WITH CHECK ADD FOREIGN KEY([nguoi_cap_nhat])
REFERENCES [dbo].[tai_khoan] ([id])
GO
ALTER TABLE [dbo].[khach_hang]  WITH CHECK ADD FOREIGN KEY([nguoi_tao])
REFERENCES [dbo].[tai_khoan] ([id])
GO
ALTER TABLE [dbo].[lich_su_hoa_don]  WITH CHECK ADD FOREIGN KEY([id_hoa_don])
REFERENCES [dbo].[hoa_don] ([id])
GO
ALTER TABLE [dbo].[lich_su_hoa_don]  WITH CHECK ADD FOREIGN KEY([id_tai_khoan])
REFERENCES [dbo].[tai_khoan] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([chuoi])
REFERENCES [dbo].[chuoi] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([dau_co_ban])
REFERENCES [dbo].[dau_co_ban] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_san_pham])
REFERENCES [dbo].[san_pham] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([mau_sac])
REFERENCES [dbo].[mau_sac] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([tay_cam])
REFERENCES [dbo].[tay_cam] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([thuong_hieu])
REFERENCES [dbo].[thuong_hieu] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([xuat_xu])
REFERENCES [dbo].[xuat_xu] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([ngon])
REFERENCES [dbo].[ngon] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([ren])
REFERENCES [dbo].[ren] ([id])
GO
ALTER TABLE [dbo].[thanh_toan]  WITH CHECK ADD FOREIGN KEY([id_hoa_don])
REFERENCES [dbo].[hoa_don] ([id])

INSERT INTO [dbo].[san_pham] (ten_san_pham, ngay_tao, ngay_cap_nhat, ngay_xoa, trang_thai, ma_san_pham)
VALUES 
('San Pham 1', '2024-01-01', '2024-01-02', NULL, 1, 'SP001'),
('San Pham 2', '2024-01-03', '2024-01-04', NULL, 1, 'SP002'),
('San Pham 3', '2024-01-05', '2024-01-06', NULL, 1, 'SP003'),
('San Pham 4', '2024-01-07', '2024-01-08', NULL, 1, 'SP004'),
('San Pham 5', '2024-01-09', '2024-01-10', NULL, 1, 'SP005'),
('San Pham 6', '2024-01-11', '2024-01-12', NULL, 1, 'SP006'),
('San Pham 7', '2024-01-13', '2024-01-14', NULL, 1, 'SP007'),
('San Pham 8', '2024-01-15', '2024-01-16', NULL, 1, 'SP008'),
('San Pham 9', '2024-01-17', '2024-01-18', NULL, 1, 'SP009'),
('San Pham 10', '2024-01-19', '2024-01-20', NULL, 1, 'SP010');
-- Inserting records into [dbo].[chuoi]
INSERT INTO [dbo].[chuoi] (ten, ma)
VALUES 
('Chuoi 1', 'CH001'),
('Chuoi 2', 'CH002'),
('Chuoi 3', 'CH003'),
('Chuoi 4', 'CH004'),
('Chuoi 5', 'CH005'),
('Chuoi 6', 'CH006'),
('Chuoi 7', 'CH007'),
('Chuoi 8', 'CH008'),
('Chuoi 9', 'CH009'),
('Chuoi 10', 'CH010');

-- Inserting records into [dbo].[tay_cam]
INSERT INTO [dbo].[tay_cam] (ten, ma)
VALUES 
('Tay cam 1', 'TC001'),
('Tay cam 2', 'TC002'),
('Tay cam 3', 'TC003'),
('Tay cam 4', 'TC004'),
('Tay cam 5', 'TC005'),
('Tay cam 6', 'TC006'),
('Tay cam 7', 'TC007'),
('Tay cam 8', 'TC008'),
('Tay cam 9', 'TC009'),
('Tay cam 10', 'TC010');

-- Inserting records into [dbo].[dau_co_ban]
INSERT INTO [dbo].[dau_co_ban] (ten, ma)
VALUES 
('Dau co ban 1', 'DC001'),
('Dau co ban 2', 'DC002'),
('Dau co ban 3', 'DC003'),
('Dau co ban 4', 'DC004'),
('Dau co ban 5', 'DC005'),
('Dau co ban 6', 'DC006'),
('Dau co ban 7', 'DC007'),
('Dau co ban 8', 'DC008'),
('Dau co ban 9', 'DC009'),
('Dau co ban 10', 'DC010');

-- Inserting records into [dbo].[ngon]
INSERT INTO [dbo].[ngon] (ten, ma)
VALUES 
('Ngon 1', 'NG001'),
('Ngon 2', 'NG002'),
('Ngon 3', 'NG003'),
('Ngon 4', 'NG004'),
('Ngon 5', 'NG005'),
('Ngon 6', 'NG006'),
('Ngon 7', 'NG007'),
('Ngon 8', 'NG008'),
('Ngon 9', 'NG009'),
('Ngon 10', 'NG010');

-- Inserting records into [dbo].[xuat_xu]
INSERT INTO [dbo].[xuat_xu] (ten, ma)
VALUES 
('Xuat xu 1', 'XX001'),
('Xuat xu 2', 'XX002'),
('Xuat xu 3', 'XX003'),
('Xuat xu 4', 'XX004'),
('Xuat xu 5', 'XX005'),
('Xuat xu 6', 'XX006'),
('Xuat xu 7', 'XX007'),
('Xuat xu 8', 'XX008'),
('Xuat xu 9', 'XX009'),
('Xuat xu 10', 'XX010');

-- Inserting records into [dbo].[ren]
INSERT INTO [dbo].[ren] (ten, ma)
VALUES 
('Ren 1', 'RE001'),
('Ren 2', 'RE002'),
('Ren 3', 'RE003'),
('Ren 4', 'RE004'),
('Ren 5', 'RE005'),
('Ren 6', 'RE006'),
('Ren 7', 'RE007'),
('Ren 8', 'RE008'),
('Ren 9', 'RE009'),
('Ren 10', 'RE010');

-- Inserting records into [dbo].[thuong_hieu]
INSERT INTO [dbo].[thuong_hieu] (ten, ma)
VALUES 
('Thuong hieu 1', 'TH001'),
('Thuong hieu 2', 'TH002'),
('Thuong hieu 3', 'TH003'),
('Thuong hieu 4', 'TH004'),
('Thuong hieu 5', 'TH005'),
('Thuong hieu 6', 'TH006'),
('Thuong hieu 7', 'TH007'),
('Thuong hieu 8', 'TH008'),
('Thuong hieu 9', 'TH009'),
('Thuong hieu 10', 'TH010');

-- Inserting records into [dbo].[mau_sac]
INSERT INTO [dbo].[mau_sac] (ten, ma)
VALUES 
('Mau sac 1', 'MS001'),
('Mau sac 2', 'MS002'),
('Mau sac 3', 'MS003'),
('Mau sac 4', 'MS004'),
('Mau sac 5', 'MS005'),
('Mau sac 6', 'MS006'),
('Mau sac 7', 'MS007'),
('Mau sac 8', 'MS008'),
('Mau sac 9', 'MS009'),
('Mau sac 10', 'MS010');

-- Inserting records into [dbo].[san_pham_chi_tiet]
INSERT INTO [dbo].[san_pham_chi_tiet] (id_san_pham, ma_san_pham, mo_ta, ngay_tao, ngay_cap_nhat, ngay_xoa, trang_thai, gia_ban, mau_sac, chuoi, tay_cam, dau_co_ban, ngon, ren, trong_luong, duong_kinh_dau, thuong_hieu, bao_hanh, xuat_xu, ten_san_pham, so_luong)
VALUES 
(1, 'SP001', 'Mo ta san pham A', GETDATE(), NULL, NULL, 1, 1000, 1, 1, 1, 1, 1, 1, 500, 10.5, 1, 12, 1, 'San pham A', 10),
(2, 'SP002', 'Mo ta san pham B', GETDATE(), NULL, NULL, 1, 2000, 2, 2, 2, 2, 2, 2, 600, 12.5, 2, 24, 2, 'San pham B', 20),
(3, 'SP003', 'Mo ta san pham C', GETDATE(), NULL, NULL, 1, 1500, 3, 3, 3, 3, 3, 3, 550, 11.5, 3, 18, 3, 'San pham C', 15),
(4, 'SP004', 'Mo ta san pham D', GETDATE(), NULL, NULL, 1, 1200, 4, 4, 4, 4, 4, 4, 520, 10.0, 4, 30, 4, 'San pham D', 8),
(5, 'SP005', 'Mo ta san pham E', GETDATE(), NULL, NULL, 1, 1800, 5, 5, 5, 5, 5, 5, 580, 13.0, 5, 22, 5, 'San pham E', 12),
(6, 'SP006', 'Mo ta san pham F', GETDATE(), NULL, NULL, 1, 2200, 6, 6, 6, 6, 6, 6, 650, 14.0, 6, 25, 6, 'San pham F', 18),
(7, 'SP007', 'Mo ta san pham G', GETDATE(), NULL, NULL, 1, 2500, 7, 7, 7, 7, 7, 7, 700, 15.0, 7, 20, 7, 'San pham G', 25),
(8, 'SP008', 'Mo ta san pham H', GETDATE(), NULL, NULL, 1, 1700, 8, 8, 8, 8, 8, 8, 540, 11.0, 8, 27, 8, 'San pham H', 30),
(9, 'SP009', 'Mo ta san pham I', GETDATE(), NULL, NULL, 1, 1300, 9, 9, 9, 9, 9, 9, 510, 9.5, 9, 15, 9, 'San pham I', 22),
(10, 'SP010', 'Mo ta san pham J', GETDATE(), NULL, NULL, 1, 2000, 10, 10, 10, 10, 10, 10, 560, 12.0, 10, 19, 10, 'San pham J', 14);

-- Inserting records into [dbo].[giam_gia_hoa_don]
INSERT INTO [dbo].[giam_gia_hoa_don] ([ma], [ten], [mo_ta], [gia_tri_toi_thieu], [gia_tri_toi_da], [giam_phan_tram], [giam_so_toi_da], [ngay_bat_dau], [ngay_ket_thuc], [ngay_tao], [ngay_cap_nhat], [ngay_xoa], [trang_thai])
VALUES
('GG001', 'Khuyen mai mua he', 'Giam gia 10% cho don hang tu 500,000 VND', 500000, 1000000, 10, NULL, '2024-06-01', '2024-08-31', GETDATE(), NULL, NULL, 1),
('GG002', 'Khuyen mai sinh nhat', 'Giam gia 15% cho don hang tu 1,000,000 VND', 1000000, 2000000, 15, NULL, '2024-07-01', '2024-09-30', GETDATE(), NULL, NULL, 1),
('GG003', 'Giam gia cuoi nam', 'Giam gia 5% cho tat ca don hang', 0, NULL, 5, NULL, '2024-12-01', '2024-12-31', GETDATE(), NULL, NULL, 1),
('GG004', 'Giam gia so luong lon', 'Giam gia 20% cho don hang tren 2,000,000 VND', 2000000, NULL, 20, NULL, '2024-06-01', '2024-12-31', GETDATE(), NULL, NULL, 1),
('GG005', 'Khuyen mai mua 1 tang 1', 'Mua 1 san pham duoc tang 1 san pham gia tri tuong duong', 0, NULL, NULL, 0, '2024-07-01', '2024-07-31', GETDATE(), NULL, NULL, 1),
('GG006', 'Giam gia sinh nhat khach hang', 'Giam gia 10% cho khach hang co sinh nhat trong thang', 0, NULL, 10, NULL, '2024-07-01', '2024-07-31', GETDATE(), NULL, NULL, 1),
('GG007', 'Giam gia theo ma', 'Giam gia 50,000 VND cho don hang tren 500,000 VND voi ma giam gia GG007', 500000, NULL, NULL, 50000, '2024-07-01', '2024-12-31', GETDATE(), NULL, NULL, 1),
('GG008', 'Khuyen mai dau nam', 'Giam gia 10% cho tat ca san pham trong thang dau nam', 0, NULL, 10, NULL, '2024-01-01', '2024-01-31', GETDATE(), NULL, NULL, 1),
('GG009', 'Giam gia theo san pham', 'Giam gia 30% cho san pham A', 0, NULL, 30, NULL, '2024-08-01', '2024-08-31', GETDATE(), NULL, NULL, 1),
('GG010', 'Khuyen mai mua thu', 'Giam gia 10% cho don hang tu 300,000 VND', 300000, 500000, 10, NULL, '2024-09-01', '2024-11-30', GETDATE(), NULL, NULL, 1);


INSERT INTO [dbo].[tai_khoan] (email, dien_thoai, password, ho_ten, dia_chi, ghi_chu, chuc_vu, ngay_tao, ngay_cap_nhat, ngay_xoa, trang_thai, ma)
VALUES
('email1@example.com', '0123456789', 'password1', 'Nguyen Van A', '123 Duong A, Quan B, Thanh pho C', NULL, 1, GETDATE(), NULL, NULL, 1, 'TK001'),
('email2@example.com', '0987654321', 'password2', 'Tran Thi B', '456 Duong B, Quan C, Thanh pho D', NULL, 0, GETDATE(), NULL, NULL, 1, 'TK002'),
('email3@example.com', '0123456781', 'password3', 'Le Van C', '789 Duong C, Quan D, Thanh pho E', NULL, 1, GETDATE(), NULL, NULL, 1, 'TK003'),
('email4@example.com', '0987654322', 'password4', 'Pham Thi D', '101 Duong D, Quan E, Thanh pho F', NULL, 0, GETDATE(), NULL, NULL, 1, 'TK004'),
('email5@example.com', '0123456782', 'password5', 'Hoang Van E', '202 Duong E, Quan F, Thanh pho G', NULL, 1, GETDATE(), NULL, NULL, 1, 'TK005'),
('email6@example.com', '0987654323', 'password6', 'Nguyen Thi F', '303 Duong F, Quan G, Thanh pho H', NULL, 0, GETDATE(), NULL, NULL, 1, 'TK006'),
('email7@example.com', '0123456783', 'password7', 'Vu Van G', '404 Duong G, Quan H, Thanh pho I', NULL, 1, GETDATE(), NULL, NULL, 1, 'TK007'),
('email8@example.com', '0987654324', 'password8', 'Do Thi H', '505 Duong H, Quan I, Thanh pho J', NULL, 0, GETDATE(), NULL, NULL, 1, 'TK008'),
('email9@example.com', '0123456784', 'password9', 'Bui Van I', '606 Duong I, Quan J, Thanh pho K', NULL, 1, GETDATE(), NULL, NULL, 1, 'TK009'),
('email10@example.com', '0987654325', 'password10', 'Nguyen Van J', '707 Duong J, Quan K, Thanh pho L', NULL, 0, GETDATE(), NULL, NULL, 1, 'TK010');

INSERT INTO [dbo].[khach_hang] (ten_khach_hang, email, dien_thoai, dia_chi, ghi_chu, ngay_tao, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, ngay_xoa, trang_thai, ma)
VALUES
('Nguyen Van A', 'nguyenvana@example.com', '0123456789', '123 Duong A, Quan B, Thanh pho C', NULL, GETDATE(), NULL, 1, NULL, NULL, 1, 'KH001'),
('Tran Thi B', 'tranthib@example.com', '0987654321', '456 Duong B, Quan C, Thanh pho D', NULL, GETDATE(), NULL, 1, NULL, NULL, 1, 'KH002'),
('Le Van C', 'levanc@example.com', '0123456781', '789 Duong C, Quan D, Thanh pho E', NULL, GETDATE(), NULL, 1, NULL, NULL, 1, 'KH003'),
('Pham Thi D', 'phamthid@example.com', '0987654322', '101 Duong D, Quan E, Thanh pho F', NULL, GETDATE(), NULL, 1, NULL, NULL, 1, 'KH004'),
('Hoang Van E', 'hoangvane@example.com', '0123456782', '202 Duong E, Quan F, Thanh pho G', NULL, GETDATE(), NULL, 1, NULL, NULL, 1, 'KH005'),
('Nguyen Thi F', 'nguyenthif@example.com', '0987654323', '303 Duong F, Quan G, Thanh pho H', NULL, GETDATE(), NULL, 1, NULL, NULL, 1, 'KH006'),
('Vu Van G', 'vuvang@example.com', '0123456783', '404 Duong G, Quan H, Thanh pho I', NULL, GETDATE(), NULL, 1, NULL, NULL, 1, 'KH007'),
('Do Thi H', 'dothih@example.com', '0987654324', '505 Duong H, Quan I, Thanh pho J', NULL, GETDATE(), NULL, 1, NULL, NULL, 1, 'KH008'),
('Bui Van I', 'buivani@example.com', '0123456784', '606 Duong I, Quan J, Thanh pho K', NULL, GETDATE(), NULL, 1, NULL, NULL, 1, 'KH009'),
('Nguyen Van J', 'nguyenvanj@example.com', '0987654325', '707 Duong J, Quan K, Thanh pho L', NULL, GETDATE(), NULL, 1, NULL, NULL, 1, 'KH010');

INSERT INTO [dbo].[trang_thai_hoa_don] ([ten], [ghi_chu])
VALUES
(N'Chua thanh toan', 'Don da tao nhung chua thanh toan'),
(N'Da thanh toan', 'Don da thanh toan'),
(N'Da huy', 'Don huy');

INSERT INTO [dbo].[hoa_don] ([ma_hoa_don], [id_khach_hang], [id_tai_khoan], [ngay_tao], [ngay_cap_nhat], [ngay_xoa], [gia_tri], [so_tien_giam], [gia_tri_tong], [ghi_chu], [id_trang_thai_hoa_don], [id_giam_gia_hoa_don])
VALUES
('HD001', 1, 1, GETDATE(), NULL, NULL, 500000, 50000, 450000, 'Note 1', 1, 1),
('HD002', 2, 2, GETDATE(), NULL, NULL, 1000000, 150000, 850000, 'Note 2', 2, 2),
('HD003', 3, 1, GETDATE(), NULL, NULL, 1500000, 0, 1500000, 'Note 3', 1, 3),
('HD004', 4, 3, GETDATE(), NULL, NULL, 2000000, 200000, 1800000, 'Note 4', 2, 4),
('HD005', 5, 2, GETDATE(), NULL, NULL, 700000, 70000, 630000, 'Note 5', 1, 5),
('HD006', 6, 1, GETDATE(), NULL, NULL, 1200000, 120000, 1080000, 'Note 6', 3, 6),
('HD007', 7, 2, GETDATE(), NULL, NULL, 800000, 0, 800000, 'Note 7', 1, 7),
('HD008', 8, 3, GETDATE(), NULL, NULL, 950000, 95000, 855000, 'Note 8', 2, 8),
('HD009', 9, 1, GETDATE(), NULL, NULL, 600000, 60000, 540000, 'Note 9', 1, 9),
('HD010', 10, 2, GETDATE(), NULL, NULL, 1100000, 110000, 990000, 'Note 10', 3, 10);

INSERT INTO [dbo].[hoa_don_chi_tiet] ([ma_hoa_don_chi_tiet], [id_hoa_don], [id_san_pham_chi_tiet], [so_luong], [gia_ban], [trang_thai])
VALUES
('HDCT001', 1, 1, 2, 250000, 1),
('HDCT002', 2, 2, 1, 500000, 1),
('HDCT003', 3, 3, 3, 500000, 1),
('HDCT004', 4, 4, 1, 1000000, 1),
('HDCT005', 5, 5, 1, 700000, 1),
('HDCT006', 6, 6, 2, 600000, 1),
('HDCT007', 7, 7, 1, 800000, 1),
('HDCT008', 8, 8, 1, 950000, 1),
('HDCT009', 9, 9, 2, 300000, 1),
('HDCT010', 10, 10, 1, 1100000, 1);

INSERT INTO [dbo].[lich_su_hoa_don] ([id_hoa_don], [id_tai_khoan], [ngay_cap_nhat], [ghi_chu])
VALUES
(1, 1, GETDATE(), 'Cap nhat trang thai don hang'),
(2, 2, GETDATE(), 'Thay doi thong tin khach hang'),
(3, 3, GETDATE(), 'Cap nhat ghi chu don hang'),
(4, 4, GETDATE(), 'Thay doi phuong thuc thanh toan'),
(5, 5, GETDATE(), 'Cap nhat ngay giao hang'),
(6, 6, GETDATE(), 'Thay doi dia chi giao hang'),
(7, 7, GETDATE(), 'Cap nhat trang thai thanh toan'),
(8, 8, GETDATE(), 'Cap nhat giam gia'),
(9, 9, GETDATE(), 'Thay doi san pham trong don hang'),
(10, 10, GETDATE(), 'Cap nhat thong tin khach hang');

INSERT INTO [dbo].[thanh_toan] ([id_hoa_don], [ngay_thanh_toan], [so_tien_mat], [so_tien_the])
VALUES
(1, GETDATE(), 100000, 500000),
(2, GETDATE(), 200000, 800000),
(3, GETDATE(), 300000, 700000),
(4, GETDATE(), 150000, 850000),
(5, GETDATE(), 250000, 450000),
(6, GETDATE(), 400000, 600000),
(7, GETDATE(), 500000, 300000),
(8, GETDATE(), 100000, 900000),
(9, GETDATE(), 300000, 500000),
(10, GETDATE(), 200000, 800000);

GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "san_pham_chi_tiet"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 136
               Right = 215
            End
            DisplayFlags = 280
            TopColumn = 6
         End
         Begin Table = "san_pham"
            Begin Extent = 
               Top = 160
               Left = 26
               Bottom = 290
               Right = 196
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "chuoi"
            Begin Extent = 
               Top = 6
               Left = 253
               Bottom = 102
               Right = 423
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "dau_co_ban"
            Begin Extent = 
               Top = 6
               Left = 461
               Bottom = 102
               Right = 631
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "mau_sac"
            Begin Extent = 
               Top = 221
               Left = 731
               Bottom = 317
               Right = 901
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "ngon"
            Begin Extent = 
               Top = 12
               Left = 714
               Bottom = 108
               Right = 884
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "ren"
            Begin Extent = 
               Top = 79
               Left = 926
               Bottom = 175
               Right = 1096
            End
            DisplayFlags = 2' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'View_1'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane2', @value=N'80
            TopColumn = 0
         End
         Begin Table = "tay_cam"
            Begin Extent = 
               Top = 196
               Left = 938
               Bottom = 292
               Right = 1108
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "thuong_hieu"
            Begin Extent = 
               Top = 211
               Left = 289
               Bottom = 307
               Right = 459
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "xuat_xu"
            Begin Extent = 
               Top = 217
               Left = 509
               Bottom = 313
               Right = 679
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'View_1'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=2 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'View_1'
GO
USE [master]
GO
ALTER DATABASE [homie_billiard] SET  READ_WRITE 
GO
