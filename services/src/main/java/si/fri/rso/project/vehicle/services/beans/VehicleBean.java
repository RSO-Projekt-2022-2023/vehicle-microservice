package si.fri.rso.project.vehicle.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rso.project.vehicle.lib.Vehicle;
import si.fri.rso.project.vehicle.models.converters.VehicleConverter;
import si.fri.rso.project.vehicle.models.entities.VehicleEntity;


@RequestScoped
public class VehicleBean {

    private Logger log = Logger.getLogger(VehicleBean.class.getName());

    @Inject
    private EntityManager em;

    @Timed
    public List<Vehicle> getVehicle() {

        TypedQuery<VehicleEntity> query = em.createNamedQuery(
                "VehicleEntity.getAll", VehicleEntity.class);

        List<VehicleEntity> resultList = query.getResultList();

        return resultList.stream().map(VehicleConverter::toDto).collect(Collectors.toList());

    }

    public List<Vehicle> getVehicleFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, VehicleEntity.class, queryParameters).stream()
                .map(VehicleConverter::toDto).collect(Collectors.toList());
    }

    public Vehicle getVehicle(Integer id) {

        VehicleEntity vehicleEntity = em.find(VehicleEntity.class, id);

        if (vehicleEntity == null) {
            throw new NotFoundException();
        }

        Vehicle vehicle = VehicleConverter.toDto(vehicleEntity);

        return vehicle;
    }

    public Vehicle createVehicle(Vehicle vehicle) {

        VehicleEntity vehicleEntity = VehicleConverter.toEntity(vehicle);

        try {
            beginTx();
            em.persist(vehicleEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (vehicleEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return VehicleConverter.toDto(vehicleEntity);
    }

    public Vehicle putVehicle(Integer id, Vehicle vehicle) {

        VehicleEntity c = em.find(VehicleEntity.class, id);

        if (c == null) {
            return null;
        }

        VehicleEntity updatedImageMetadataEntity = VehicleConverter.toEntity(vehicle);

        try {
            beginTx();
            updatedImageMetadataEntity.setId(c.getId());
            updatedImageMetadataEntity = em.merge(updatedImageMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return VehicleConverter.toDto(updatedImageMetadataEntity);
    }

    public boolean deleteImageMetadata(Integer id) {

        VehicleEntity imageMetadata = em.find(VehicleEntity.class, id);

        if (imageMetadata != null) {
            try {
                beginTx();
                em.remove(imageMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
