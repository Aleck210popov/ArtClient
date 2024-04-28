package com.example.artclient.ui.graphical;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameGetForm extends JFrame {
    private final PanelGetFormMain panelGetFormMain;
    private static FrameGetForm singleton;
    {
        panelGetFormMain = new PanelGetFormMain();
    }
    private FrameGetForm(){
        super("Комплексные числа");
        this.setSize(700, 500);
        this.add(panelGetFormMain);
        panelGetFormMain.setVisible(true);
        panelGetFormMain.setBounds(0, 0, this.getWidth(), this.getHeight());
    }
    public static FrameGetForm getSingleton()
    {
        if (singleton == null)
        {
            singleton = new FrameGetForm();
        }
        return singleton;
    }

    private class PanelGetFormMain extends JPanel {
        private final PanelInputData panelInputData;
        private final PanelTable panelTable;
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
            }
        }
        private void getForm (String designation, String versionDate) {

        }
        private class PanelTable extends JPanel {
            private final JTextPane textGetForm;
            private final JScrollPane scrollPane;
            {
                textGetForm = new JTextPane();
                scrollPane = new JScrollPane(textGetForm);
            }
            private PanelTable() {
                this.setBackground(Color.YELLOW);
            }
        }
    }
}
