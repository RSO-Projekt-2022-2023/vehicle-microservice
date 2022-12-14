package si.fri.rso.project.vehicle.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "vehicles")
@NamedQueries(value =
        {
                @NamedQuery(name = "VehicleEntity.getAll", query = "SELECT im FROM VehicleEntity im"),
                @NamedQuery(name = "VehicleEntity.GetVehiclesByUserId", query = "SELECT im FROM VehicleEntity im WHERE im.user_id = ?1"),
        })
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="user_id")
    private Integer user_id;

    @Column(name = "model")
    private String model;

    @Column(name = "num_of_kilometers")
    private Integer num_of_kilometers;

    @Column(name = "lst_charge")
    private Integer lst_charge; //charge in kWh

    @Column(name = "num_of_charges_lst_month")
    private Integer num_of_charges_lst_month;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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


    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }
}