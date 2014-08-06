package fr.cea.ig.io.model.obo;

// Patway
public class UPA extends TermRelations {
    private Relation isA;
    private Relation superPathway;

    /**
     * @param id Id of this aggregated relations
     * @param name  name of this aggregated relations
     * @param definition description  aggregated relations
     * @param relations  List of Relation
     * @param isA pathway type
     * @param superPathway Relation to a super pathway
     */
    public UPA( final String id, final String name, final String definition, final Relations relations, final Relation isA, final Relation superPathway ) {
        super(id, name, definition, relations);
        this.isA            = isA;
        this.superPathway   = superPathway;
    }

    public Relation getIsA() {
        return isA;
    }
    public Relation getSuperPathway() {
        return superPathway;
    }
}
