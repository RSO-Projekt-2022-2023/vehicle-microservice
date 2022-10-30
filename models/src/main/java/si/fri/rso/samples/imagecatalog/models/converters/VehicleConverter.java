package si.fri.rso.samples.imagecatalog.models.converters;


import si.fri.rso.samples.imagecatalog.lib.*;
import si.fri.rso.samples.imagecatalog.models.entities.*;

public class VehicleConverter {

    public static Vehicle toDto(VehicleEntity entity) {

        Vehicle dto = new Vehicle();
        dto.setVehicleId(entity.getId());
        dto.setCreated(entity.getCreated());
        dto.setNumOfKilometers(entity.getNumOfKilometers());
        dto.setModel(entity.getModel());
        dto.setNumOfChargesLstMonth(entity.getNumOfChargesLstMonth());
        dto.setLstCharge(entity.getLstCharge());

        return dto;

    }

    public static VehicleEntity toEntity(Vehicle dto) {

        VehicleEntity entity = new VehicleEntity();
        entity.setCreated(dto.getCreated());
        entity.setNumOfKilometers(dto.getNumOfKilometers());
        entity.setModel(dto.getModel());
        entity.setNumOfChargesLstMonth(dto.getNumOfChargesLstMonth());
        entity.setLstCharge(dto.getLstCharge());

        return entity;

    }

}
