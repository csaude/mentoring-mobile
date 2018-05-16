package mz.org.fgh.mentoring.service;

import java.util.List;

import mz.org.fgh.mentoring.config.model.Cabinet;

/**
 * Created by steliomo on 4/12/18.
 */

public interface CabinetService {

    void precessCabinets(List<Cabinet> cabinets);

    List<Cabinet> findAllCabinets();
}
