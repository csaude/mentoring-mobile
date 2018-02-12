package mz.org.fgh.mentoring.process.model;

import java.util.List;

import mz.org.fgh.mentoring.dto.IndicatorHelper;
import mz.org.fgh.mentoring.model.BeanResource;

/**
 * Created by steliomo on 11/3/17.
 */

public class IndicatorBeanResource extends BeanResource {

    private List<IndicatorHelper> indicators;

    private List<String> indicatorsUuids;

    public List<IndicatorHelper> getIndicators() {
        return this.indicators;
    }

    public void setIndicators(List<IndicatorHelper> indicators) {
        this.indicators = indicators;
    }

    public List<String> getIndicatorsUuids() {
        return indicatorsUuids;
    }
}
