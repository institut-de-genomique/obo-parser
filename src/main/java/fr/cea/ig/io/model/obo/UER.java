package fr.cea.ig.io.model.obo;

// UER: Enzymatic Reaction
public class UER extends TermRelations {

    /**
     * @param id Id of this aggregated relations
     * @param name  name of this aggregated relations
     * @param definition description  aggregated relations
     * @param relations  List of Relation
     */
    public UER( final String id, final String name, final String definition, Relations relations ) {
        super(id, name, definition, relations);
    }

}
