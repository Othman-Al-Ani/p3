import java.util.HashMap;
import java.util.Vector;

public class Consumption {
    //vectorn håller appliences.  string är vad det är  double är consumption value.

    private Vector<ConsumptionData> listOfAppliences;

    public Consumption()
    {
        listOfAppliences = new Vector<>();
    }

    public synchronized void addAppliance(String name, double initialValue) {
        listOfAppliences.add(new ConsumptionData(name, initialValue));
    }


    public synchronized void updateApplienceValue(String name ,Double consumptionValue)
    {
        for(ConsumptionData applience : listOfAppliences){
            if(applience.getName().equals(name)){
                applience.setValue(consumptionValue);
            }
        }
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
