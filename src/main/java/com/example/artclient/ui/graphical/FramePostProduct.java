package com.example.artclient.ui.graphical;

import com.example.artclient.domain.Product;
import com.example.artclient.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FramePostProduct extends JFrame {
    private final PanelPostProductMain panelPostProductMain;
    private final ProductService productService;
    private final String DEFAULTTEXT = "{\n" +
            "  \"designation\": \"ИБПШ.526824.001\",\n" +
            "  \"name\": \"Электродвигатель\",\n" +
            "  \"quantity\": 1,\n" +
            "  \"level\": 1,\n" +
            "  \"versionDate\": 2007,\n" +
            "  \"assembliesUnits\": [\n" +
            "    {\n" +
            "      \"designation\": \"ЖБИК.684222.002\",\n" +
            "      \"name\": \"Статор\",\n" +
            "      \"quantity\": 2,\n" +
            "      \"level\": 2,\n" +
            "      \"versionDate\": 2001,\n" +
            "      \"parts\": [\n" +
            "        {\n" +
            "          \"designation\": \"ЖБИК.301134.001\",\n" +
            "          \"name\": \"Корпус статора\",\n" +
            "          \"quantity\": 1,\n" +
            "          \"level\": 3,\n" +
            "          \"versionDate\": 1978\n" +
            "        },\n" +
            "        {\n" +
            "          \"designation\": \"ЖБИК.757481.001\",\n" +
            "          \"name\": \"Гильза прямоугольная\",\n" +
            "          \"quantity\": 45,\n" +
            "          \"level\": 3,\n" +
            "          \"versionDate\": 1985\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"designation\": \"ЖБИК.684261.003\",\n" +
            "      \"name\": \"Ротор\",\n" +
            "      \"quantity\": 1,\n" +
            "      \"level\": 2,\n" +
            "      \"versionDate\": 1999,\n" +
            "      \"parts\": [\n" +
            "        {\n" +
            "          \"designation\": \"ЖБИК.301176.001\",\n" +
            "          \"name\": \"Крышка ротора\",\n" +
            "          \"quantity\": 1,\n" +
            "          \"level\": 3,\n" +
            "          \"versionDate\": 1983\n" +
            "        },\n" +
            "        {\n" +
            "          \"designation\": \"ЖБИК.714662.001\",\n" +
            "          \"name\": \"Втулка\",\n" +
            "          \"quantity\": 10,\n" +
            "          \"level\": 3,\n" +
            "          \"versionDate\": 1995\n" +
            "        },\n" +
            "        {\n" +
            "          \"designation\": \"ЖБИК.758121.007\",\n" +
            "          \"name\": \"Болт\",\n" +
            "          \"quantity\": 16,\n" +
            "          \"level\": 3,\n" +
            "          \"versionDate\": 1972\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"designation\": \"ЖБИК.301131.001\",\n" +
            "      \"name\": \"Корпус\",\n" +
            "      \"quantity\": 1,\n" +
            "      \"level\": 2,\n" +
            "      \"versionDate\": 2009\n" +
            "    },\n" +
            "    {\n" +
            "      \"designation\": \"ЖБИК.758121.007\",\n" +
            "      \"name\": \"Болт\",\n" +
            "      \"quantity\": 20,\n" +
            "      \"level\": 2,\n" +
            "      \"versionDate\": 1989\n" +
            "    },\n" +
            "    {\n" +
            "      \"designation\": \"ЖБИК.757481.001\",\n" +
            "      \"name\": \"Гильза прямоугольная\",\n" +
            "      \"quantity\": 20,\n" +
            "      \"level\": 2,\n" +
            "      \"versionDate\": 2014\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    {

        panelPostProductMain = new PanelPostProductMain();
    }
    FramePostProduct(ProductService productService){
        super("Добавление нового изделия");
        this.productService=productService;
        this.setSize(700, 500);
        this.add(panelPostProductMain);
        panelPostProductMain.setBounds(0, 0, this.getWidth(), this.getHeight());

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent windowEvent) {
                JOptionPane.showMessageDialog(FramePostProduct.this,
                        "В программе реализована возможность создавать" +
                                " нового пользователь только в формате JSON,\n" +
                                " вы можете протестировать программу," +
                                " используя JSON по умолчанию или использовать свой.",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private class PanelPostProductMain extends JPanel {
        private final JScrollPane scrollPane;
        private final JTextPane inputJSON;
        private final JButton buttonPost;
        {
            inputJSON = new JTextPane();
            inputJSON.setText(DEFAULTTEXT);
            scrollPane = new JScrollPane(inputJSON);
            buttonPost =  new JButton("Добавить");
        }
        private PanelPostProductMain() {
            this.setLayout(new BorderLayout());
            this.add(scrollPane, BorderLayout.CENTER);
            this.add(buttonPost, BorderLayout.SOUTH);


            buttonPost.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Gson gson = new Gson();
                    try {
                        Product newProduct = gson.fromJson(inputJSON.getText(), Product.class);
                        inputJSON.setText("Новый продукт добавлен\n\n\n"
                                + productService.sendPostRequestProduct(newProduct).toString());
                    } catch (JsonSyntaxException ex) {
                        JOptionPane.showMessageDialog(panelPostProductMain,
                                "Введенный JSON некорректен", "Ошибка", JOptionPane.ERROR_MESSAGE);

                    }
                }
            });
        }
    }
}
