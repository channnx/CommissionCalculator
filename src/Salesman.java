/**
 * Created by Shiyang Chen on 2018/5/2.
 */
public class Salesman {
    private int level;
    private int index;
    private int team;
    private String name;
    private double salesAmount;
    private int salesCount;
    private int bonus;
    private double comm;


    public Salesman(int level, int index, String name, int team) {
        this.level = level;
        this.index = index;
        this.name = name;
        this.team = team;
        this.salesAmount = 0.0;
    }

    public double getSalesAmount() {
        return this.salesAmount;
    }

    public void setSalesAmount(double salesAmount) {
        this.salesAmount = salesAmount;
    }

    public int getSalesCount() {
        return this.salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    public int getIndex() {
        return this.index;
    }

    public int getTeam() {
        return this.team;
    }

    public int getLevel() {
        return this.level;
    }

    public String getName() {
        return this.name;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getBonus() {
        return this.bonus;
    }

    public void setComm(double comm) {
        this.comm = comm;
    }

    public double getComm() {
        return this.comm;
    }
}
