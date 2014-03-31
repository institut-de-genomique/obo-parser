package fr.cea.labgem.obo.model;

import java.util.Iterator;
import java.util.Set;

public class Relations {

    private final Set<Relation> input_compound;
    private final Set<Relation> output_compound;
    private final Set<Relation> part_of;
    private final Set<Relation> is_a;
    
    
    private boolean hasCompound( final Set<Relation> relations, final String id ){
        return hasCompound( relations, id, true );
    }
    
    private boolean hasCompound( final Set<Relation> relations, final String id, final boolean isLeftId ){
        boolean             isPresent   = false;
        boolean             isSearching = true;
        Relation            relation    = null;
        Iterator<Relation>  iter        = relations.iterator();
        
        while( isSearching ){
            if ( iter.hasNext() ){
                relation = iter.next();
                if( isLeftId && relation.getIdLeft().equals(id) ){
                    isSearching = false;
                    isPresent   = true;
                }
                else if( ! isLeftId && relation.getIdRight().equals(id) ){
                    isSearching = false;
                    isPresent   = true;
                }
            }
            else
                isSearching = false;
        }
        return isPresent;
    }

    /**
     * @param input
     * @param output
     * @param part_of
     * @param is_a
     */
    public Relations(final Set<Relation> input, final Set<Relation> output, final Set<Relation> part_of, final Set<Relation> is_a) {
        this.input_compound     = input;
        this.output_compound    = output;
        this.part_of            = part_of;
        this.is_a               = is_a;
    }


    /**
     * @param input
     * @param output
     * @param part_of
     */
    public Relations(final Set<Relation> input, final Set<Relation> output,
            final Set<Relation> part_of) {
        this(input, output, part_of, null);
    }


    /**
     * @param is_a
     */
    public Relations(final Set<Relation> is_a) {
        this(null, null, null, is_a);
    }

    public Set<Relation> getInputCompound() {
        return input_compound;
    }


    public Set<Relation> getOutputCompound() {
        return output_compound;
    }


    public Set<Relation> getPartOf() {
        return part_of;
    }


    public Set<Relation> getIsA() {
        return is_a;
    }
    
    public boolean isPartOf( final String id ){
        return hasCompound(part_of, id);
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Relation item : input_compound)
            result.append("relationship:" + item.toString() + "\n");
        for (Relation item : output_compound)
            result.append("relationship:" + item.toString() + "\n");
        for (Relation item : part_of)
            result.append("relationship:" + item.toString() + "\n");
        return result.toString();
    }

}
