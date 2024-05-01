package com.example.artclient.ui.graphical;

import com.example.artclient.service.ProductService;

public class StartGraphicalInterface {
    public StartGraphicalInterface(ProductService productService){
        FrameMain frame = FrameMain.getSingleton(productService);
        frame.setVisible(true);
    }
}
