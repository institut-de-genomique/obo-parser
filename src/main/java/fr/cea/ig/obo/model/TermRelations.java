package fr.cea.ig.obo.model;

import java.util.Iterator;
import java.util.Set;

public class TermRelations extends Term {

    protected final Relations relations;
    
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

    public Set<Relation> getRelation( final String type ) {
        final String    tokenInput          = "has_input_compound";
        final String    tokenOutput         = "has_output_compound";
        final String    tokenPartOf         = "part_of";
        
        Set<Relation> relation = null;
        
        if( tokenInput.equals(type) )
            relation = relations.getInputCompound();
        else if( tokenOutput.equals(type) )
            relation = relations.getOutputCompound();
        else if( tokenPartOf.equals(type) )
            relation = relations.getPartOf();
        return relation;
    }
    
    @Override
    public String toString(){
        return super.toString() + "\n" + relations.toString();
    }

    public boolean isPartOf( final String id ) {
        return relations.isPartOf( id );
    }

    public boolean isPartOf( final Term term ) {
        return relations.isPartOf( term.getId() );
    }
    
    public boolean hasInputCompound(final String id ) {
        return relations.hasInputCompound( id );
    }
    
    public boolean hasInputCompound(final Term term ) {
        return relations.hasInputCompound( term.getId() );
    }
    
    public boolean hasOutputCompound(final String id ) {
        return relations.hasOutputCompound( id );
    }
    
    public boolean hasOutputCompound(final Term term ) {
        return relations.hasOutputCompound( term.getId() );
    }
    
    public boolean isVariantOf( TermRelations term ){
        boolean             result      = false;
        boolean             isSearching = true;
        Set<Relation>       relation    = relations.getPartOf();
        Iterator<Relation>  iter        = relation.iterator();
        Relation            rel         = null;
        while( isSearching ){
            if(! iter.hasNext() )
                isSearching = false;
            else {
                rel = iter.next();
                if( term.hasOutputCompound( rel.getIdRight() ) ){
                    isSearching = false;
                    result      = true;
                }
            }
        }
        return result;
        
    }

    public boolean isAfter( final TermRelations term ){
        boolean             result      = false;
        boolean             isSearching = true;
        Iterator<Relation>  iter        = relations.getOutputCompound().iterator();
        Relation            output       = null;
        while( isSearching ){
            if( iter.hasNext() ){
                output = iter.next();
                if( term.hasInputCompound( output.getIdLeft() ) ){
                    result = true;
                    isSearching = false;
                }
            }
            else
                isSearching = false;
        }
        return result;
    }
    
    public boolean isBefore( final TermRelations term ){
        boolean             result      = false;
        boolean             isSearching = true;
        Iterator<Relation>  iter        = relations.getInputCompound().iterator();
        Relation            input       = null;
        while( isSearching ){
            if( iter.hasNext() ){
                input = iter.next();
                if( term.hasOutputCompound( input.getIdLeft() ) ){
                    result = true;
                    isSearching = false;
                }
            }
            else
                isSearching = false;
        }
        return result;
    }
    
    public boolean isLinked( final TermRelations term ){
        return (isAfter(term) || isBefore(term) );
    }
}