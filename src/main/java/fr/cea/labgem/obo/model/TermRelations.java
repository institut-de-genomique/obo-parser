package fr.cea.labgem.obo.model;

import java.util.Set;

public class TermRelations extends Term {

    private final Relations relations;
    
    /**
     * @param id
     * @param name
     * @param definition
     * @param relations
     */
    public TermRelations( final String id, final String name, final String definition, Relations relations ) {
        super(id, name, definition );
        this.relations = relations;
    }

    public Relations getRelations() {
        return relations;
    }

    public Set<Relation> getRelation( String type ) {
        final String    tokenInput          = "has_input_compound";
        final String    tokenOutput         = "has_output_compound";
        final String    tokenPartOf         = "part_of";
        final String    tokenIsA            = "is_a";
        
        Set<Relation> relation = null;

        if( tokenInput.equals(type) )
            relation = relations.getInputCompound();
        if( tokenOutput.equals(type) )
            relation = relations.getOutputCompound();
        if( tokenPartOf.equals(type) )
            relation = relations.getPartOf();
        if( tokenIsA.equals(type) )
            relation = relations.getIsA();
        return relation;
    }
    
    @Override
    public String toString(){
        return super.toString() + "\n" + relations.toString();
    }

}
