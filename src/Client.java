import se.mau.DA343A.VT25.projekt.Buffer;
import se.mau.DA343A.VT25.projekt.net.SecurityTokens;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame implements ChangeListener, Runnable{


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
        frame.setLayout(new GridLayout(2,1));
        this.ip = ip;
        this.port = port;

        this.ApplianceName = ApplianceName;

        frame.setSize(300,300);

        label.setFont(new Font("Arial", Font.BOLD, 20));

        slider.setMajorTickSpacing((MaxPower/4));
        slider.setMinorTickSpacing(1);
        slider.setLabelTable(slider.createStandardLabels(MaxPower/4));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        //slider.setBounds(50,30,100,100);
        label.setBounds(50,60,100,100);


        frame.add(label);
        frame.add(slider);





    }


    @Override
    public void run() {
        frame.show(true);
//        slider.addPropertyChangeListener("value",this );
//        slider.addChangeListener(e -> {slider.firePropertyChange("value",0,slider.getValue());});
       SecurityTokens token = new SecurityTokens("martinsKuk");

        slider.addChangeListener(this);
        try {
            Socket socket = new Socket(ip,port);

            DataOutputStream out = new DataOutputStream( socket.getOutputStream());


            out.writeUTF(token.generateToken());

            out.writeUTF( ApplianceName);


            while (true){

                out.write((int) buffer.get());


            }

        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//
//    }




    @Override
    public void stateChanged(ChangeEvent e) {

        buffer.put(slider.getValue());


    }
}
