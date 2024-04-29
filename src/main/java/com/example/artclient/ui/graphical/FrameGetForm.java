package com.example.artclient.ui.graphical;

import com.example.artclient.service.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameGetForm extends JFrame {
    private final PanelGetFormMain panelGetFormMain;
    private final ProductService productService;
    {
        panelGetFormMain = new PanelGetFormMain();
    }
    FrameGetForm(ProductService productService){
        super("Комплексные числа");
        this.productService=productService;
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.add(panelGetFormMain);
        panelGetFormMain.setVisible(true);
        panelGetFormMain.setBounds(0, 0, this.getWidth(), this.getHeight());
    }

    private class PanelGetFormMain extends JPanel {
        private final PanelInputData panelInputData;
        private final PanelTable panelTable;
        private final String[] columnsHeader = {"Изделие", "Ближайшая сборка", "Компонент",
                "Уровень", "Кол. на изделие", "Кол. на ближайшую сборку", "Количество"};

        {
            panelInputData = new PanelInputData();
            panelTable = new PanelTable();
        }

        private PanelGetFormMain() {

            this.setLayout(new BorderLayout());
            this.add(panelInputData, BorderLayout.NORTH);
            this.add(panelTable, BorderLayout.CENTER);
        }
        private class PanelInputData extends JPanel {
            private final JLabel labelDesignation;
            private final JTextField inputFieldDesignation;
            private final JLabel labelDataVervion;
            private final JTextField inputFieldVervion;
            private final JButton buttonGetForm;
            private final JButton buttonClear;
            {
                labelDesignation = new JLabel("Введите констукторное обозначение изделия");
                inputFieldDesignation = new JTextField();
                labelDataVervion = new JLabel("Введите дату изготовления изготовления изделия");
                inputFieldVervion = new JTextField();
                buttonGetForm = new JButton("Получить данные");
                buttonClear = new JButton("Очистить");
            }
            private PanelInputData() {
                this.setLayout(new GridLayout(3, 2));
                this.add(labelDesignation);
                this.add(inputFieldDesignation);
                this.add(labelDataVervion);
                this.add(inputFieldVervion);
                this.add(buttonGetForm);
                this.add(buttonClear);

                buttonGetForm.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getForm(inputFieldDesignation.getText(), inputFieldVervion.getText());
                    }
                });

                buttonClear.addActionListener(e -> panelTable.tableForm.setModel(new DefaultTableModel()));
            }
        }
        private void getForm(String designation, String versionDateString) {
            try {
                if (designation.isEmpty() || versionDateString.isEmpty()) {
                    throw new StringIndexOutOfBoundsException();
                }
                int versionDate = Integer.parseInt(versionDateString.trim());
                panelTable.tableForm.setModel(new DefaultTableModel(productService.sendGetRequestForm(designation.trim(),
                        versionDate), columnsHeader));
            } catch (StringIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null,
                        "Заполните все поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Введите корректные значения", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
        private class PanelTable extends JPanel {
            private final JTable tableForm;

            private PanelTable() {
                this.setLayout(new BorderLayout());
                tableForm =  new JTable();
                tableForm.setEnabled(false);
                JScrollPane scrollPane = new JScrollPane(tableForm);
                this.add(scrollPane, BorderLayout.CENTER);
            }
        }
    }
}
