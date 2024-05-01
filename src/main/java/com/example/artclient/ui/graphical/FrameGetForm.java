package com.example.artclient.ui.graphical;

import com.example.artclient.domain.Product;
import com.example.artclient.exception.IncorrectDatesException;
import com.example.artclient.exception.StringIsEmptyException;
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
        super("Таблица изделие");
        this.productService=productService;
        this.setSize(1000, 700);
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

                buttonClear.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panelTable.tableForm.setModel(new DefaultTableModel());
                        inputFieldDesignation.setText(null);
                        inputFieldVersion.setText(null);
                    }
                });
            }
        }
        private void getForm(String designationString, String versionDateString) {
            try {
                if (designationString.isEmpty() || versionDateString.isEmpty()) {
                    throw new StringIsEmptyException("Введённые строки пустые");
                }
                int versionDate = Integer.parseInt(versionDateString.trim());
                if (versionDate > 2030 || versionDate < 1950) {
                    throw new IncorrectDatesException("Не корректные даты");
                }
                String designation = designationString.trim();
                product = productService.sendGetRequestProduct(designation, versionDate);
                stringArrProduct = product.getForm();

                panelTable.tableForm.setModel(new DefaultTableModel(stringArrProduct, columnsHeader));
            } catch (StringIsEmptyException ex) {
                JOptionPane.showMessageDialog(null,
                        "Заполните все поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Введите корректные значения", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (IncorrectDatesException ex) {
                JOptionPane.showMessageDialog(null,
                        "Дата изготовления детали может быть выбрана " +
                                "от 1950 до 2030", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
        private class PanelTable extends JPanel {
            private final JTable tableForm;
            private final JScrollPane scrollPane;
            private boolean quantityEditingEnabled;

            {
                this.quantityEditingEnabled = false;
                tableForm =  new JTable() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        if (column == 6) {
                            return quantityEditingEnabled;
                        }
                        return false;
                    }
                };
                scrollPane = new JScrollPane(tableForm);
            }
            

            private PanelTable() {
                this.setLayout(new BorderLayout());

                this.add(scrollPane, BorderLayout.CENTER);
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

                buttonDelete.addActionListener(e -> {
                    if (product!=null) {
                        int result = JOptionPane.showConfirmDialog(null,
                                "Вы уверены, что хотите удалить продукт "
                                        + product.getDesignation()+ " " + product.getName() + "?",
                                "Подтверждение удаления",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (result == JOptionPane.YES_OPTION) {
                            panelInputData.inputFieldDesignation.setText(null);
                            panelInputData.inputFieldVersion.setText(null);
                            deleteProduct();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Выберете изделие", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                });

                buttonEdit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (product!=null) {
                            if (isEdit) {
                                isEdit = false;
                                buttonEdit.setText("Редоктировать данные");
                                panelTable.quantityEditingEnabled = false;
                                buttonUpdate.setEnabled(false);
                                panelTable.tableForm.setModel(new DefaultTableModel(stringArrProduct, columnsHeader));
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "В программе реализована возможность" +
                                                " изменять только количество деталей\n" +
                                                "Вы можете изменить данные в столбце \"Количество\".",
                                        "Ошибка", JOptionPane.ERROR_MESSAGE);
                                isEdit = true;
                                buttonEdit.setText("Отменить изменения");
                                panelTable.quantityEditingEnabled = true;
                                buttonUpdate.setEnabled(true);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Выберете изделие", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                buttonUpdate.addActionListener(e -> {
                    int result = JOptionPane.showConfirmDialog(null,
                            "Вы уверены, что хотите изменить данные продукта "
                                    + product.getDesignation()+ " " + product.getName() + "?",
                            "Подтверждение удаления",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        isEdit = false;
                        buttonEdit.setText("Редоктировать данные");
                        panelInputData.inputFieldDesignation.setText(null);
                        panelInputData.inputFieldVersion.setText(null);
                        buttonUpdate.setEnabled(false);
                        updateProduct();
                    }

                });
            }
            private void deleteProduct() {
                productService.sendDeleteRequestProduct(product);
                panelTable.tableForm.setModel(new DefaultTableModel());
                product = null;
                JOptionPane.showMessageDialog(null, "Продукт успешно удален", "Успех", JOptionPane.INFORMATION_MESSAGE);
            }
            private void updateProduct() {
                DefaultTableModel model = (DefaultTableModel) panelTable.tableForm.getModel();
                int rowCount = model.getRowCount();
                int columnCount = model.getColumnCount();
                String[][] data = new String[rowCount][columnCount];

                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < columnCount; j++) {
                        data[i][j] = (String) model.getValueAt(i, j);
                    }
                }
                Product newProduct = new Product(data, product);
                product = productService.sendPutRequestProduct(product.getId(), newProduct);
                stringArrProduct = product.getForm();
                panelTable.tableForm.setModel(new DefaultTableModel(stringArrProduct, columnsHeader));
            }

        }
    }
}
