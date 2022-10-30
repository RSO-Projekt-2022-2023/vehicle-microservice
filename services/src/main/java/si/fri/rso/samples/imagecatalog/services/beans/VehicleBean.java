package si.fri.rso.samples.imagecatalog.services.beans;

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

import si.fri.rso.samples.imagecatalog.lib.Vehicle;
import si.fri.rso.samples.imagecatalog.models.converters.VehicleConverter;
import si.fri.rso.samples.imagecatalog.models.entities.VehicleEntity;


@RequestScoped
public class VehicleBean {

    private Logger log = Logger.getLogger(VehicleBean.class.getName());

    @Inject
    private EntityManager em;

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

        Vehicle imageMetadata = VehicleConverter.toDto(vehicleEntity);

        return imageMetadata;
    }

    public Vehicle createImageMetadata(Vehicle vehicle) {

        VehicleEntity imageMetadataEntity = VehicleConverter.toEntity(vehicle);

        try {
            beginTx();
            em.persist(imageMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (imageMetadataEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return VehicleConverter.toDto(imageMetadataEntity);
    }

    public Vehicle putImageMetadata(Integer id, Vehicle vehicle) {

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
