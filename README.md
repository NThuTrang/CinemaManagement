# Hệ Thống Quản Lý Rạp Chiếu Phim

## Giới thiệu
Dự án **Xây dựng hệ thống Quản lý rạp chiếu phim** là giải pháp phần mềm nhằm số hóa và tối ưu hóa quy trình vận hành của rạp chiếu phim truyền thống.

Trong bối cảnh nhu cầu giải trí tăng cao, việc quản lý thủ công (bán vé, xếp chỗ, lịch chiếu...) gặp nhiều sai sót và tốn kém thời gian. Hệ thống này được phát triển để giải quyết các bài toán trên, giúp:
* **Tối ưu hóa vận hành:** Thay thế quy trình thủ công, giảm thiểu sai sót và sự phụ thuộc vào nhân sự.
* **Nâng cao trải nghiệm:** Tăng tốc độ phục vụ khách hàng, quản lý hội viên hiệu quả.
* **Hỗ trợ ra quyết định:** Cung cấp báo cáo doanh thu, thống kê dữ liệu chính xác cho nhà quản lý.

## Chức năng của hệ thống

Hệ thống được phân quyền rõ ràng cho hai đối tượng sử dụng: **Quản lý (Admin)** và **Nhân viên**.

### 1. Phân hệ Quản lý (Admin)
Dành cho cấp quản lý để điều hành toàn bộ hoạt động của rạp:

* **Quản lý Phim & Suất chiếu:**
    * Quản lý thông tin phim (Thêm/Sửa/Xóa), Thể loại, Định dạng (2D, 3D, IMAX...).
    * Quản lý Lịch chiếu: Sắp xếp suất chiếu, hệ thống tự động tạo số lượng vé dựa trên sơ đồ ghế phòng chiếu.
* **Quản lý Cơ sở vật chất:**
    * Quản lý Phòng chiếu và Loại màn hình.
    * Quản lý danh sách Đồ ăn/Thức uống (Menu).
* **Quản lý Người dùng:**
    * Quản lý Nhân viên: Thêm mới, cập nhật thông tin, cấp lại mật khẩu.
    * Quản lý Khách hàng: Thông tin hội viên, lịch sử tích điểm.
* **Báo cáo & Thống kê:**
    * Theo dõi và lập báo cáo doanh thu theo thời gian hoặc theo từng bộ phim.
    * Quản lý kho vé (số vé đã bán/còn lại).

### 2. Phân hệ Nhân viên (Staff)
Giao diện bán hàng (POS) dành cho nhân viên tại quầy:

* **Bán vé:** Hiển thị sơ đồ ghế trực quan (phân biệt màu sắc loại ghế/tình trạng).
    * Hỗ trợ chọn phim, suất chiếu, vị trí ngồi và thanh toán.
* **Bán đồ ăn:** Order đồ ăn/nước uống kèm theo vé hoặc mua lẻ.
    * Tính tổng hóa đơn và in hóa đơn cho khách.
* **Hỗ trợ khách hàng:** Tra cứu thông tin hội viên.

## Công nghệ sử dụng
* **Ngôn ngữ:** C#
* **Cơ sở dữ liệu:** SQL Server
* **Giao diện:** WinForms

## Thành viên nhóm
* Thành viên 1: Nguyễn Thu Trang
* Thành viên 2: Đinh Yến Linh
* Thành viên 3: Trần Thị Hoài Thương
