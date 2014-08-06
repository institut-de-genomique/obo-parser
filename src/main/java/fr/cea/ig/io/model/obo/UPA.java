package fr.cea.ig.io.model.obo;

// Patway
public class UPA extends TermRelations {
    private Relation isA;
    private Relation superPathway;
    
    /**
     * @param id
     * @param name
     * @param definition
     * @param relations
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
