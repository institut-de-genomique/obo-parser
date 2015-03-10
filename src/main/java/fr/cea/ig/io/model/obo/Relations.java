package fr.cea.ig.io.model.obo;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*
 *
 */
/*
 * @startuml
 * class Relations {
 *  - input_compound    : Set<Relation>
 *  - output_compound   : Set<Relation>
 *  - part_of           : Set<Relation>
 *  - hasCompound( @NotNull final Set<Relation> relations, @NotNull final String id ) : boolean
 *  - hasCompound( @NotNull final Set<Relation> relations, @NotNull final String id, final boolean isLeftId ) : boolean
 *  + Relations(final Set<Relation> input, final Set<Relation> output, final Set<Relation> part_of)
 *  + Relations()
 *  + getInputCompound()    : Set<Relation>
 *  + getOutputCompound()   : Set<Relation>
 *  + getPartOf()           : Set<Relation>
 *  + isPartOf( @NotNull final String id )          : boolean
 *  + hasInputCompound( @NotNull final String id )  : boolean
 *  + hasOutputCompound( @NotNull final String id ) : boolean
 *  + toString()                                    : String
 * }
 * @enduml
 */
public class Relations {

    private final Set<Relation> input_compound;
    private final Set<Relation> output_compound;
    private final Set<Relation> part_of;
    
    
    private boolean hasCompound( @NotNull final Set<Relation> relations, @NotNull final String id ){
        return hasCompound( relations, id, true );
    }


    private boolean hasCompound( @NotNull final Set<Relation> relations, @NotNull final String id, final boolean isLeftId ){
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
     * @param input Relation entry
     * @param output Relation to the output
     * @param part_of This relations is contained into a given relations list
     */
    public Relations(final Set<Relation> input, final Set<Relation> output, final Set<Relation> part_of) {
        this.input_compound     = input;
        this.output_compound    = output;
        this.part_of            = part_of;
    }
    
    public Relations(){
        this.input_compound     = new HashSet<Relation>();
        this.output_compound    = new HashSet<Relation>();
        this.part_of            = new HashSet<Relation>();
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


    public boolean isPartOf( @NotNull final String id ){
        return hasCompound(part_of, id);
    }


    public boolean hasInputCompound( @NotNull final String id ){
        return hasCompound(input_compound, id);
    }


    public boolean hasOutputCompound( @NotNull final String id ){
        return hasCompound(output_compound, id, false);
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
