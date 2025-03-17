import se.mau.DA343A.VT25.projekt.Buffer;
import se.mau.DA343A.VT25.projekt.IAppExitingCallback;
import se.mau.DA343A.VT25.projekt.net.SecurityTokens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;


import java.io.*;
import java.net.Socket;

public class Client extends JFrame implements Runnable, IAppExitingCallback {
    private final String ApplianceName;
    private final String ip;
    private final int port;
    private final Buffer<Integer> buffer = new Buffer<>();
    private final JFrame frame;
    private final JSlider slider;
    private final String StringToken;
    private boolean closing = false;

    public Client(int MaxPower, String ApplianceName, String ip, int port) {
        slider = new JSlider(0, MaxPower, 0);
        JLabel label = new JLabel(ApplianceName);
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

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exiting();
            }
        });

        SecurityTokens token = new SecurityTokens("goon");
        StringToken = token.generateToken();
        slider.addChangeListener(_ -> updateSliderValue());
    }

    @Override
    public void run() {  // aktiv client
        frame.setVisible(true);

        try {
            Socket socket = new Socket(ip, port);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF(StringToken);
            out.writeUTF(ApplianceName);
            out.writeInt(slider.getValue());

            while (!closing) {

                out.writeInt(1);
                out.writeInt(buffer.get());

            }
            out.writeInt(0);
            out.writeUTF(StringToken);
            socket.close();


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateSliderValue(){
            buffer.put(slider.getValue());
    }

    @Override
    public void exiting() {   // när client exitar
        closing = true;
        buffer.put(slider.getValue());
        frame.dispose();
    }

}
