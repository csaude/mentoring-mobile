package mz.org.fgh.mentoring.service;

import java.util.List;

import javax.inject.Inject;

import mz.org.fgh.mentoring.config.dao.CabinetDAO;
import mz.org.fgh.mentoring.config.model.Cabinet;

/**
 * Created by steliomo on 4/12/18.
 */

public class CabinetServiceImpl implements CabinetService {

    @Inject
    CabinetDAO cabinetDAO;

    @Inject
    public CabinetServiceImpl() {
    }

    @Override
    public void precessCabinets(List<Cabinet> cabinets) {

        for (Cabinet cabinet : cabinets) {

            Cabinet foundCabinet = cabinetDAO.findByUuid(cabinet.getUuid());

            if (foundCabinet != null) {
                cabinetDAO.update(cabinet);
            } else {
                cabinetDAO.create(cabinet);
            }
        }
    }

    @Override
    public List<Cabinet> findAllCabinets() {
        return cabinetDAO.findAll();
    }
}
