import se.mau.DA343A.VT25.projekt.Buffer;
import se.mau.DA343A.VT25.projekt.IAppExitingCallback;
import se.mau.DA343A.VT25.projekt.ServerGUI;
import se.mau.DA343A.VT25.projekt.net.SecurityTokens;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame implements PropertyChangeListener, Runnable, IAppExitingCallback {


    private IAppExitingCallback onExitingCallback;
    private String ApplianceName;
    private String ip;
    private int port;
    private Buffer<Integer> buffer = new Buffer<>();
    private DataOutputStream out;
    private JFrame frame;
    private JLabel label;
    private JSlider slider;
    private Socket socket;

    public Client(int MaxPower, String ApplianceName, String ip, int port) throws IOException {
        slider = new JSlider(0, MaxPower, 0);
        label = new JLabel(ApplianceName);
        frame = new JFrame();
        frame.setLayout(new GridLayout(2, 1));
        this.ip = ip;
        this.port = port;

        this.ApplianceName = ApplianceName;

        frame.setSize(300, 300);

        label.setFont(new Font("Arial", Font.BOLD, 20));

        slider.setMajorTickSpacing((MaxPower / 4));
        slider.setMinorTickSpacing(1);
        slider.setLabelTable(slider.createStandardLabels(MaxPower / 4));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        //slider.setBounds(50,30,100,100);
        label.setBounds(50, 60, 100, 100);


        frame.add(label);
        frame.add(slider);


        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                exiting();

            }
        });


    }


    @Override
    public void run() {
        frame.show(true);
        slider.addPropertyChangeListener("value", this);
        slider.addChangeListener(e -> {
            slider.firePropertyChange("value", 0, slider.getValue());
        });

        // slider.addChangeListener(this);

        SecurityTokens token = new SecurityTokens("martin");

        try {
            socket = new Socket(ip, port);

            out = new DataOutputStream(socket.getOutputStream());


            out.writeUTF(token.generateToken());

            out.writeUTF(ApplianceName);


            while (true) {


                out.write(1);
                out.write(buffer.get());


            }


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        buffer.put(slider.getValue());

    }


    @Override
    public void exiting() {

        try {
            System.out.println("closed");
            out.write(0);
            out.write(buffer.get());

            socket.close();
        } catch (IOException |InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
