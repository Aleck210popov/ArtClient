package com.example.artclient.ui.graphical;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameMain extends JFrame {
    private final JPanel panelMaster;

    {
        panelMaster = new JPanel();
        this.add(panelMaster);
        this.setSize(600, 400);
        panelMaster.setBorder(BorderFactory.createLineBorder(Color.black));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelMaster.setLayout(null);
    }
    public FrameMain(){
        super("Главное меню");
        JButton buttonForm = new JButton("Получить печатную форму");
        panelMaster.add(buttonForm);
        buttonForm.setBounds(100 , 80, this.getWidth()-200, this.getHeight()-200);
        FrameGetForm frameStaff = FrameGetForm.getSingleton();
        frameStaff.setVisible(false);

        buttonForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameStaff.setVisible(true);
            }
        });
    }
}
