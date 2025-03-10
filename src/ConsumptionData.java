public class ConsumptionData {
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
