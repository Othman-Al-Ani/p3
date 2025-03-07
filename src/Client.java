import se.mau.DA343A.VT25.projekt.Buffer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.Socket;

public class Client extends JFrame implements ChangeListener, Runnable{

    private int MaxPower;
    private String ApplianceName;
    private String ip;
    private int port;
    private Buffer buffer = new Buffer<>();

    private JFrame frame;
    private JLabel label;
    private JSlider slider;

    public Client(int MaxPower, String ApplianceName, String ip, int port){
        slider = new JSlider(0, MaxPower, 0);
        label = new JLabel(ApplianceName);
        frame = new JFrame();
        this.ip = ip;
        this.port = port;

        frame.setSize(300,300);

        slider.setBounds(50,30,100,100);
//        label.setBounds(50,60,100,100);

        frame.add(slider);
        frame.add(label);



    }


    @Override
    public void run() {
        frame.show(true);
//        slider.addPropertyChangeListener("value",this );
//        slider.addChangeListener(e -> {slider.firePropertyChange("value",0,slider.getValue());});

        slider.addChangeListener(this);
        try {
            Socket socket = new Socket(ip,port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//
//    }

    @Override
    public void stateChanged(ChangeEvent e) {
        System.out.println(slider.getValue());
        buffer.put(slider.getValue());


    }
}
