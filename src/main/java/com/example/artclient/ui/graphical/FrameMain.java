package com.example.artclient.ui.graphical;

import com.example.artclient.service.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameMain extends JFrame {
    private final JPanel panelMaster;
    private static FrameMain singleton;

    {
        panelMaster = new JPanel();
        this.add(panelMaster);
        this.setSize(600, 400);
        panelMaster.setBorder(BorderFactory.createLineBorder(Color.black));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelMaster.setLayout(null);
    }
    private FrameMain(ProductService productService){
        super("Главное меню");
        JButton buttonForm = new JButton("Получить печатную форму");
        JButton buttonNewProduct = new JButton("Добавить новое изделие");
        panelMaster.add(buttonForm);
        panelMaster.add(buttonNewProduct);
        buttonForm.setBounds(100 , 65, this.getWidth()-200, this.getHeight()-300);
        buttonNewProduct.setBounds(100 , 215, this.getWidth()-200, this.getHeight()-300);


        buttonForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameGetForm frameStaff = new FrameGetForm(productService);
                frameStaff.setVisible(true);
            }
        });
        buttonNewProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FramePostProduct framePostProduct = new FramePostProduct();
                framePostProduct.setVisible(true);
            }
        });
    }
    public static FrameMain getSingleton(ProductService productService)
    {
        if (singleton == null)
        {
            singleton = new FrameMain(productService);
        }
        return singleton;
    }
}
