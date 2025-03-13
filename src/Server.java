import se.mau.DA343A.VT25.projekt.LiveXYSeries;
import se.mau.DA343A.VT25.projekt.ServerGUI;
import se.mau.DA343A.VT25.projekt.net.ListeningSocket;
import se.mau.DA343A.VT25.projekt.net.ListeningSocketConnectionWorker;
import se.mau.DA343A.VT25.projekt.net.SecurityTokens;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Server extends ListeningSocket {

    private final ServerGUI serverGUI;
    private final Consumption consumption;

    private final Timer timer;
    private final LiveXYSeries<Double> series;

    private final SecurityTokens securityTokens;

    private final Vector<LiveXYSeries<Double>> applianceSeries;
    private final Vector<String> applianceNames;

    public Server(int listeningPort) {
        super(listeningPort);
        serverGUI = new ServerGUI("Server GUI");
        this.consumption = new Consumption(this);
        timer = new Timer();
        series = new LiveXYSeries<>("Total Consumption", 20);
        applianceSeries = new Vector<>();
        applianceNames = new Vector<>();


        SwingUtilities.invokeLater(serverGUI::createAndShowUI);
        SwingUtilities.invokeLater(()->serverGUI.addSeries(series));
        securityTokens = new SecurityTokens("goon");
        displayData();

    }

    //uppdaterar gui
    public void displayData(){
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(()-> serverGUI.setTotalConsumption(consumption.calculateConsumption()));
                        SwingUtilities.invokeLater(() -> series.addValue((double) (System.currentTimeMillis()/1000), consumption.calculateConsumption()));

                        for (int i = 0; i < applianceSeries.size(); i++) {
                            int finalI = i;
                            SwingUtilities.invokeLater(() -> applianceSeries.get(finalI).addValue((double) (System.currentTimeMillis()/1000), consumption.applianceValue(finalI)));

                        }
                    }
                }, 0, 1000);
    }

    public void addNewApplianceSeries(String name){
        String temp = name;
        if(applianceNames.contains(name)){
            int c = 1;
            temp =  name + c;
            while(applianceNames.contains(temp)){
                c++;
                temp =  name + c;

            }
        }

        applianceNames.add(temp);
        LiveXYSeries<Double> newSeries = new LiveXYSeries<>(temp, 20);
        applianceSeries.add(newSeries);
        SwingUtilities.invokeLater(()->serverGUI.addSeries(newSeries));
    }

    @Override
    public ListeningSocketConnectionWorker createNewConnectionWorker() {
        return new ConnectionWorker(consumption, serverGUI, securityTokens);
    }
}