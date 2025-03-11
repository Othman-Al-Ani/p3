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
    private String StringToken;

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

        SecurityTokens token = new SecurityTokens("martin");
        StringToken = token.generateToken();

    }


    @Override
    public void run() {  // aktiv client
        frame.show(true);
        slider.addPropertyChangeListener("value", this);
        slider.addChangeListener(e -> {
            slider.firePropertyChange("value", 0, slider.getValue());
        });

        // slider.addChangeListener(this);



        try {
            socket = new Socket(ip, port);

            out = new DataOutputStream(socket.getOutputStream());


            out.writeUTF(StringToken);

            out.writeUTF(ApplianceName);


            while (true) {


                out.writeInt(1);
                out.writeInt(buffer.get());


            }


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(slider.getValue());
        buffer.put(slider.getValue());

    }


    @Override
    public void exiting() {   // när client exitar

        try {
            System.out.println("closed");
            out.writeInt(0);
            out.writeUTF(StringToken);

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
