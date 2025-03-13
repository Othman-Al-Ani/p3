import java.util.HashMap;
import java.util.Vector;

public class Consumption {
    //vectorn håller appliences.  string är typen av applience  double är consumption value.

    private Vector<ConsumptionData> listOfAppliences;
    Server server;

    public Consumption(Server server)
    {
        listOfAppliences = new Vector<>();
        this.server = server;
    }

    public synchronized void addAppliance(ConsumptionData data) {
        listOfAppliences.add(data);
        server.setApplianceSeries(data.getName());

    }

    public double applianceValue(int postion){
        return listOfAppliences.get(postion).getValue();
    }


    public synchronized void updateApplienceValue(String name ,Double consumptionValue)
    {
        for(ConsumptionData applience : listOfAppliences){
            if(applience.getName().equals(name)){
                applience.setValue(consumptionValue);
            }
        }
    }

    public synchronized void removeAppliance(ConsumptionData data){
        listOfAppliences.remove(data);
    }


    public double calculateConsumption (){
        double total = 0;

        for(ConsumptionData d: listOfAppliences)
        {
            total+= d.getValue();
        }
        return total;
    }

}
