package si.fri.rso.project.vehicle.lib;

import java.time.Instant;

public class Vehicle {

    private Integer vehicleId;
    private String model;
    private Integer num_of_kilometers;
    private Integer lst_charge;
    private Integer num_of_charges_lst_month;
    private Instant created;



    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getNumOfKilometers() {
        return num_of_kilometers;
    }

    public void setNumOfKilometers(Integer num_of_kilometers) {
        this.num_of_kilometers = num_of_kilometers;
    }

    public Integer getLstCharge() {
        return lst_charge;
    }

    public void setLstCharge(Integer lst_charge) {
        this.lst_charge = lst_charge;
    }

    public Integer getNumOfChargesLstMonth() {
        return num_of_charges_lst_month;
    }

    public void setNumOfChargesLstMonth(Integer num_of_charges_lst_month) {
        this.num_of_charges_lst_month = num_of_charges_lst_month;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

}
