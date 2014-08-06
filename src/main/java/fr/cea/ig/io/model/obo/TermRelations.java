package fr.cea.ig.io.model.obo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TermRelations extends Term {

    protected final Relations relations;


    protected List<List<Term>> childs;


    /**
     * @param id Id of this aggregated relations
     * @param name name of this aggregated relations
     * @param definition description  aggregated relations
     * @param relations List of Relation
     */
    public TermRelations( final String id, final String name, final String definition, Relations relations ) {
        super(id, name, definition );
        this.relations  = relations;
        this.childs     = new ArrayList<List<Term>>();
    }

    /**
     * @param id Id of this aggregated relations
     * @param name  name of this aggregated relations
     * @param definition description  aggregated relations
     */
    public TermRelations( final String id, final String name, final String definition ) {
        super(id, name, definition );
        this.relations  = new Relations();
        this.childs     = new ArrayList<List<Term>>();
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


    public boolean hasAtLeastOneCommonOutputCompound( final TermRelations term ){
        boolean             result      = false;
        boolean             isSearching = true;
        Set<Relation>       relation    = relations.getOutputCompound();
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


    public boolean hasAtLeastOneCommonInputCompound( final TermRelations term ){
        boolean             result      = false;
        boolean             isSearching = true;
        Set<Relation>       relation    = relations.getInputCompound();
        Iterator<Relation>  iter        = relation.iterator();
        Relation            rel         = null;
        while( isSearching ){
            if(! iter.hasNext() )
                isSearching = false;
            else {
                rel = iter.next();
                if( term.hasInputCompound( rel.getIdRight() ) ){
                    isSearching = false;
                    result      = true;
                }
            }
        }
        return result;
        
    }


    public boolean isVariantOf( final TermRelations term ) {
        return hasAtLeastOneCommonOutputCompound( term ) && hasAtLeastOneCommonInputCompound( term );
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


    public void add( final Term term ){
        boolean                 isSearching = true;
        Iterator<List<Term>>    iter        = childs.iterator();
        List<Term>              currentList = null;
        Term                    currentTerm = null;
        
        if( childs.size() > 0 && childs.get(0).size() > 0 && !(childs.get(0).get(0) instanceof TermRelations) ){
            childs.add( new ArrayList<Term>( Arrays.asList( term ) ) );
            isSearching = false;
        }
        
        while ( isSearching ){
            if( iter.hasNext() ){
                currentList = iter.next();
                currentTerm = currentList.get(0);
                if( term instanceof TermRelations && ((TermRelations)currentTerm).isVariantOf( (TermRelations)term) ){
                    currentList.add( term );
                    isSearching = false;
                }
            }
            else{
                isSearching = false;
                childs.add( new ArrayList<Term>( Arrays.asList(term) ) );
            }
        }
        
    }


    public void addAll( final List<List<Term>> terms ){
        childs.addAll(terms);
    }

    public List<Term> get( final int index ){
        return childs.get( index );
    }


    public Iterator<List<Term>> iterator(){
        return childs.iterator();
    }


    public List<List<Term>> getChilds() {
        return childs;
    }


};