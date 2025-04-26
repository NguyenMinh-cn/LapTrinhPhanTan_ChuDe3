package gui.custom;
import com.github.lgooddatepicker.components.*;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Locale;
public class PanelThoiGianThi {
    private final JPanel panel;
    private final DateTimePicker dtpStart;
    private final DateTimePicker dtpEnd;

    public PanelThoiGianThi() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        Font inputFont = new Font("Arial", Font.PLAIN, 20);

        JLabel lblStart = new JLabel("Thời gian bắt đầu:");
        lblStart.setFont(inputFont);
        lblStart.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblEnd = new JLabel("Thời gian kết thúc:");
        lblEnd.setFont(inputFont);
        lblEnd.setAlignmentX(Component.LEFT_ALIGNMENT);

        DatePickerSettings viDate1 = new DatePickerSettings(new Locale("vi", "VN"));
        TimePickerSettings viTime1 = new TimePickerSettings();
        viDate1.setAllowKeyboardEditing(false);
        viTime1.use24HourClockFormat();
        viDate1.setFormatForDatesCommonEra("dd/MM/yyyy");
        dtpStart = new DateTimePicker(viDate1, viTime1);
        customizePicker(dtpStart, inputFont);

        DatePickerSettings viDate2 = new DatePickerSettings(new Locale("vi", "VN"));
        TimePickerSettings viTime2 = new TimePickerSettings();
        viDate2.setAllowKeyboardEditing(false);
        viTime2.use24HourClockFormat();
        viDate2.setFormatForDatesCommonEra("dd/MM/yyyy");
        dtpEnd = new DateTimePicker(viDate2, viTime2);
        customizePicker(dtpEnd, inputFont);

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startPanel.setOpaque(false);
        startPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        startPanel.add(lblStart);
        startPanel.add(Box.createVerticalStrut(5));
        startPanel.add(dtpStart);

        JPanel endPanel = new JPanel();
        endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.Y_AXIS));
        endPanel.setOpaque(false);
        endPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        endPanel.add(lblEnd);
        endPanel.add(Box.createVerticalStrut(5));
        endPanel.add(dtpEnd);

        panel.add(startPanel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(endPanel);
    }

    private void customizePicker(DateTimePicker picker, Font font) {
        picker.setFont(font);
        picker.setAlignmentX(Component.LEFT_ALIGNMENT);
        picker.setMaximumSize(new Dimension(700, 45));
        picker.setBackground(Color.WHITE);
        // Date field
        JTextField dateField = picker.getDatePicker().getComponentDateTextField();
        dateField.setFont(font);
        dateField.setMinimumSize(new Dimension(100, 40));
        dateField.setPreferredSize(new Dimension(300, 40));
        dateField.setBackground(new Color(245, 245, 245));
        dateField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        dateField.setForeground(new Color(33, 33, 33));

        // Time field
        JTextField timeField = picker.getTimePicker().getComponentTimeTextField();
        timeField.setFont(font);
        timeField.setMinimumSize(new Dimension(70, 40));
        timeField.setPreferredSize(new Dimension(150, 40));
        timeField.setBackground(new Color(245, 245, 245));
        timeField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        timeField.setForeground(new Color(33, 33, 33));

        // Nút toggle
        JButton dateButton = picker.getDatePicker().getComponentToggleCalendarButton();
        dateButton.setBackground(new Color(189, 224, 254));
        dateButton.setPreferredSize(new Dimension(30, 40));
        dateButton.setIcon(FontIcon.of(MaterialDesign.MDI_CALENDAR, 16, Color.BLACK));
        dateButton.setText("");

        JButton timeButton = picker.getTimePicker().getComponentToggleTimeMenuButton();
        timeButton.setBackground(new Color(189, 224, 254));
        timeButton.setPreferredSize(new Dimension(30, 40));
        timeButton.setIcon(FontIcon.of(MaterialDesign.MDI_CLOCK, 16, Color.BLACK));
        timeButton.setText("");

        // Tùy chỉnh font cho popup lịch
        DatePickerSettings settings = picker.getDatePicker().getSettings();
        settings.setFontCalendarDateLabels(new Font("Arial", Font.PLAIN, 16));
        settings.setFontCalendarWeekdayLabels(new Font("Arial", Font.PLAIN, 16));
        settings.setFontTodayLabel(new Font("Arial", Font.PLAIN, 16));
        settings.setFontClearLabel(new Font("Arial", Font.PLAIN, 16));
    }

    public JPanel getPanel() {
        return panel;
    }

    public LocalDateTime getStartDateTime() {
        return dtpStart.getDateTimeStrict();
    }

    public LocalDateTime getEndDateTime() {
        return dtpEnd.getDateTimeStrict();
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 400);
        frame.setLayout(new BorderLayout());

        PanelThoiGianThi panelThoiGianThi = new PanelThoiGianThi();
        frame.add(panelThoiGianThi.getPanel(), BorderLayout.CENTER);

        // Thêm nút "Lấy giá trị" để demo
        JButton btnGetValues = new JButton("Lấy giá trị");
        btnGetValues.setFont(new Font("Arial", Font.PLAIN, 16));
        btnGetValues.setBackground(new Color(33, 150, 243));
        btnGetValues.setForeground(Color.WHITE);
        btnGetValues.setPreferredSize(new Dimension(120, 40));
        btnGetValues.addActionListener(e -> {
            LocalDateTime startDateTime = panelThoiGianThi.getStartDateTime();
            LocalDateTime endDateTime = panelThoiGianThi.getEndDateTime();

            if (startDateTime != null) {
                System.out.println("Thời gian bắt đầu: " + startDateTime);
            } else {
                System.out.println("Thời gian bắt đầu chưa được chọn.");
            }

            if (endDateTime != null) {
                System.out.println("Thời gian kết thúc: " + endDateTime);
            } else {
                System.out.println("Thời gian kết thúc chưa được chọn.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnGetValues);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null); // Căn giữa
        frame.setVisible(true);
    }
}