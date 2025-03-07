import java.util.HashMap;

public class ConsumptionData {

    // denna håller consumption data från alla appliences.

    //hashmapsen håller appliences.  string är vad det är  double är consumption value.

    private HashMap<String,Double> listOfAppliences;

    public ConsumptionData()
    {
        listOfAppliences = new HashMap<>();
    }


    public void updateApplienceValue(String name ,Double consumptionValue)
    {

        listOfAppliences.put(name,consumptionValue);
    }

    public double calculateConsumption (){

        double total = 0;

        for(double d: listOfAppliences.values())
        {
            total+= d;
        }
        return total;
    }




}
