package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by steliomo on 4/12/18.
 */

public class Cabinet extends GenericEntity {

    private String name;

    public Cabinet() {
    }

    public Cabinet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
