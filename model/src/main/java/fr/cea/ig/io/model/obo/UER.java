package fr.cea.ig.io.model.obo;

import javax.validation.constraints.NotNull;

// UER: Enzymatic Reaction
/*
 *
 */
/*
 * @startuml
 *  class UER extends TermRelations {
  *     + UER( @NotNull final String id, @NotNull final String name, @NotNull final String definition, Relations relations )
 *  }
 * @enduml
 */
public class UER extends TermRelations {

    /**
     * @param id Id of this aggregated relations
     * @param name  name of this aggregated relations
     * @param definition description  aggregated relations
     * @param relations  List of Relation
     */
    public UER( @NotNull final String id, @NotNull final String name, @NotNull final String definition, Relations relations ) {
        super(id, name, definition, relations);
    }

}
