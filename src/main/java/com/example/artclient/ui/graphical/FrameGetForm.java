package com.example.artclient.ui.graphical;

import com.example.artclient.domain.Product;
import com.example.artclient.service.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameGetForm extends JFrame {
    private final PanelGetFormMain panelGetFormMain;
    private final ProductService productService;
    private Product product;
    private String[][] stringArrProduct;
    private final String[] columnsHeader = {"Изделие", "Ближайшая сборка", "Компонент",
            "Уровень", "Кол. на изделие", "Кол. на ближайшую сборку", "Количество"};
    {
        panelGetFormMain = new PanelGetFormMain();
    }
    FrameGetForm(ProductService productService){
        super("Комплексные числа");
        this.productService=productService;
        this.setSize(700, 1000);
        this.add(panelGetFormMain);
        panelGetFormMain.setVisible(true);
        panelGetFormMain.setBounds(0, 0, this.getWidth(), this.getHeight());
    }

    private class PanelGetFormMain extends JPanel {
        private final PanelInputData panelInputData;
        private final PanelTable panelTable;
        private final PanelButton panelButton;


        {
            panelInputData = new PanelInputData();
            panelTable = new PanelTable();
            panelButton = new PanelButton();
        }

        private PanelGetFormMain() {

            this.setLayout(new BorderLayout());
            this.add(panelInputData, BorderLayout.NORTH);
            this.add(panelTable, BorderLayout.CENTER);
            this.add(panelButton, BorderLayout.SOUTH);
            
        }
        private class PanelInputData extends JPanel {
            private final JLabel labelDesignation;
            private final JTextField inputFieldDesignation;
            private final JLabel labelDataVersion;
            private final JTextField inputFieldVersion;
            private final JButton buttonGetForm;
            private final JButton buttonClear;
            {
                labelDesignation = new JLabel("Введите констукторное обозначение изделия");
                inputFieldDesignation = new JTextField();
                labelDataVersion = new JLabel("Введите дату изготовления изготовления изделия");
                inputFieldVersion = new JTextField();
                buttonGetForm = new JButton("Получить данные");
                buttonClear = new JButton("Очистить");
            }
            private PanelInputData() {
                this.setLayout(new GridLayout(3, 2));
                this.add(labelDesignation);
                this.add(inputFieldDesignation);
                this.add(labelDataVersion);
                this.add(inputFieldVersion);
                this.add(buttonGetForm);
                this.add(buttonClear);

                buttonGetForm.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getForm(inputFieldDesignation.getText(), inputFieldVersion.getText());
                    }
                });

                buttonClear.addActionListener(e -> panelTable.tableForm.setModel(new DefaultTableModel()));
            }
        }
        private void getForm(String designationString, String versionDateString) {
            try {
                if (designationString.isEmpty() || versionDateString.isEmpty()) {
                    throw new StringIndexOutOfBoundsException();
                }
                int versionDate = Integer.parseInt(versionDateString.trim());
                String designation = designationString.trim();
                product = productService.sendGetRequestProduct(designation, versionDate);
                stringArrProduct = product.getForm();

                panelTable.tableForm.setModel(new DefaultTableModel(stringArrProduct, columnsHeader));
            } catch (StringIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null,
                        "Заполните все поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Введите корректные значения", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
        public class PanelTable extends JPanel {
            private final JTable tableForm;
            private final JScrollPane scrollPane;

            {
                tableForm =  new JTable();
                scrollPane = new JScrollPane(tableForm);
            }
            

            private PanelTable() {
                this.setLayout(new BorderLayout());

                this.add(scrollPane, BorderLayout.CENTER);
                
                tableForm.setEnabled(false);
                tableForm.getTableHeader().setReorderingAllowed(false);
            }
        }
        private class PanelButton extends JPanel {
            private final JButton buttonEdit;
            private final JButton buttonDelete;
            private final JButton buttonUpdate;
            private boolean isEdit;
            {
                buttonEdit = new JButton("Редоктировать данные");
                buttonUpdate = new JButton("Обновить данные");
                buttonDelete = new JButton("Удалить продукт");
                isEdit = false;
            }
            private PanelButton() {
                this.setLayout(new GridLayout(1 ,3));
                this.setBackground(Color.BLUE);
                this.add(buttonEdit);
                this.add(buttonUpdate);
                this.add(buttonDelete);

                buttonUpdate.setEnabled(false);

                buttonDelete.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (product!=null) {
                            int result = JOptionPane.showConfirmDialog(null,
                                    "Вы уверены, что хотите удалить продукт "
                                            + product.getDesignation()+ " " + product.getName() + "?",
                                    "Подтверждение удаления",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (result == JOptionPane.YES_OPTION) {
                                deleteProduct();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Выберете изделие", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                buttonEdit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (isEdit) {
                            isEdit = false;
                            buttonEdit.setText("Редоктировать данные");
                            panelTable.tableForm.setEnabled(false);
                            buttonUpdate.setEnabled(false);
                            panelTable.tableForm.setModel(new DefaultTableModel(stringArrProduct, columnsHeader));
                        } else {
                            isEdit = true;
                            buttonEdit.setText("Отменить изменения");
                            panelTable.tableForm.setEnabled(true);
                            buttonUpdate.setEnabled(true);
                        }
                    }
                });
                buttonUpdate.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DefaultTableModel model = (DefaultTableModel) panelTable.tableForm.getModel();
                        int rowCount = model.getRowCount();
                        int columnCount = model.getColumnCount();
                        String[][] data = new String[rowCount][columnCount];

                        for (int i = 0; i < rowCount; i++) {
                            for (int j = 0; j < columnCount; j++) {
                                data[i][j] = (String) model.getValueAt(i, j);
                            }
                        }
                        Product newProduct = new Product(data);
                        product = productService.sendPutRequestProduct(product.getId(), newProduct);
                        stringArrProduct = product.getForm();
                        panelTable.tableForm.setModel(new DefaultTableModel(stringArrProduct, columnsHeader));
                    }
                });
            }
            private void deleteProduct() {
                productService.sendDeleteRequestProduct(product);
                panelTable.tableForm.setModel(new DefaultTableModel());
                product = null;
                JOptionPane.showMessageDialog(null, "Продукт успешно удален", "Успех", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
