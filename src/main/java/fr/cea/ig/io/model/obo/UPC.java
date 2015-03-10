package fr.cea.ig.io.model.obo;

import javax.validation.constraints.NotNull;

// Compound
public class UPC extends Term {

    /**
     * @param id Id of this aggregated relations
     * @param name  name of this aggregated relations
     * @param definition description  aggregated relations
     */
    public UPC( @NotNull final String id, @NotNull final String name, @NotNull final String definition) {
        super(id, name, definition);
    }

}
