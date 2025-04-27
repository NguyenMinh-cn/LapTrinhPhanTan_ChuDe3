// Thêm sự kiện cho nút thay đổi mật khẩu
thayĐổiMậtKhẩuButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Lấy mật khẩu từ các trường nhập
            String matKhauHienTai = new String(txtMKHT.getPassword());
            String matKhauMoi = new String(txtMKM.getPassword());
            String xacNhanMatKhau = new String(txtXNMK.getPassword());
            
            // Kiểm tra các trường mật khẩu
            if (matKhauHienTai.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập mật khẩu hiện tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtMKHT.requestFocus();
                return;
            }
            
            if (matKhauMoi.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập mật khẩu mới", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtMKM.requestFocus();
                return;
            }
            
            if (xacNhanMatKhau.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng xác nhận mật khẩu mới", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtXNMK.requestFocus();
                return;
            }
            
            // Kiểm tra độ dài mật khẩu mới (ít nhất 6 ký tự)
            if (matKhauMoi.length() < 6) {
                JOptionPane.showMessageDialog(null, "Mật khẩu mới phải có ít nhất 6 ký tự", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtMKM.requestFocus();
                return;
            }
            
            // Kiểm tra mật khẩu mới và xác nhận mật khẩu có khớp nhau không
            if (!matKhauMoi.equals(xacNhanMatKhau)) {
                JOptionPane.showMessageDialog(null, "Mật khẩu mới và xác nhận mật khẩu không khớp", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtXNMK.requestFocus();
                return;
            }
            
            // Kiểm tra mật khẩu hiện tại có đúng không
            TaiKhoanService taiKhoanService = (TaiKhoanService) Naming.lookup("rmi://localhost:9090/taiKhoanService");
            TaiKhoan taiKhoan = taiKhoanService.finByID(txtEmail.getText());
            
            if (taiKhoan == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin tài khoản", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!taiKhoan.getMatKhau().equals(matKhauHienTai)) {
                JOptionPane.showMessageDialog(null, "Mật khẩu hiện tại không đúng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtMKHT.requestFocus();
                return;
            }
            
            // Cập nhật mật khẩu mới
            taiKhoan.setMatKhau(matKhauMoi);
            boolean updated = taiKhoanService.update(taiKhoan);
            
            if (updated) {
                JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                // Xóa các trường mật khẩu
                txtMKHT.setText("");
                txtMKM.setText("");
                txtXNMK.setText("");
                // Quay lại tab thông tin tài khoản
                cardLayout.show(panelNoiDungCaiDat, "Card1");
            } else {
                JOptionPane.showMessageDialog(null, "Đổi mật khẩu thất bại. Vui lòng thử lại sau.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
});
