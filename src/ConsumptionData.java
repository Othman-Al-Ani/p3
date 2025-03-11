public class ConsumptionData {

    //varje applience ger oss consumptiondata. vilket är string namn och ett value i double.
    // appleince uttrycks som consumptiondata objekt som lagras i vår consumption - vector

    private double value;
    private String name;

    public ConsumptionData(String name, double value){
        this.value = value;
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
