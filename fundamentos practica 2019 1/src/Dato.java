public class Dato {
    private String Station;
    private String name;
    private String Date;
    private double PRCP;
    private double TAVG;
    private double TMAX;
    private double TMIN;


    public Dato(String station, String name, String date, double PRCP, double TAVG, double TMAX, double TMIN) {
        Station = station;
        this.name = name;
        Date = date;
        this.PRCP = PRCP;
        this.TAVG = TAVG;
        this.TMAX = TMAX;
        this.TMIN = TMIN;
    }

    public String getStation() {
        return Station;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return Date;
    }

    public double getPRCP() {
        return PRCP;
    }

    public double getTAVG() {
        return TAVG;
    }

    public double getTMAX() {
        return TMAX;
    }

    public double getTMIN() {
        return TMIN;
    }
}
