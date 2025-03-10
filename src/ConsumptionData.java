import java.util.HashMap;

public class ConsumptionData {

    // denna håller consumption data från alla appliences.

    //hashmapsen håller appliences.  string är vad det är  double är consumption value.

    private HashMap<String,Double> listOfAppliences;

    public ConsumptionData()
    {
        listOfAppliences = new HashMap<>();
    }


    // flera klientar kan uppdatera consumption värdet så denna metod måste va syncronised.
    public synchronized void updateApplienceValue(String name ,Double consumptionValue)
    {
        listOfAppliences.put(name,consumptionValue);
    }

    //denna meotd handlar också om consumption value hantering av flera klienter så denna ska vara syncornized.
    public synchronized double calculateConsumption (){

        double total = 0;

        for(double d: listOfAppliences.values())
        {
            total+= d;
        }
        return total;
    }

    //upg: if a client sends the data incorrectly disconnect it.
    // flera klientar kan tas bort så ha synzorncatsation. paramtern är akutella klient nament. vi tar bort den frpn hashmap
    public synchronized void removeAppliance(String name)
    {
        listOfAppliences.remove(name);
    }



}
