import java.util.HashMap;
import java.util.Vector;

public class Consumption {
    //vectorn håller appliences.  string är typen av applience  double är consumption value.

    private Vector<ConsumptionData> listOfAppliences;
    private Server server;

    public Consumption(Server server)
    {
        listOfAppliences = new Vector<>();
        this.server = server;
    }

    public synchronized void addAppliance(ConsumptionData data) {
        listOfAppliences.add(data);
        System.out.println(data.getName());
        server.addNewApplianceSeries(data.getName());

    }

    public double applianceValue(int postion){
        return listOfAppliences.get(postion).getValue();
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
