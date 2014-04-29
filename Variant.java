package fr.cea.ig.obo.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Variant implements Iterable<TermRelations> {

    private static long idCounter = 1L;
    
    private long                id;
    private List<TermRelations> childs;
    private Set<String>         termVariant;

    /**
     * getVariant is a function which convert a two dimensional list of terms
     * which are part of another term to a list of each possible way using 
     * Disjunctive normal form.
     * Result is wrote into variable named variantsList as this function used
     * his reference
     * @param terms two dimensional list, the second dimension tell which term is possible to find
     * @param variantsList convert terms to Disjunctive normal form.
     * @param line current position into terms list
     * @param column current position into terms list
     * @return
     */
    public static void getVariant( final List<List<TermRelations>> terms, List<Variant> variantsList ){
        getVariant( terms, variantsList, 0, 0 );
    }
    private static void getVariant( final List<List<TermRelations>> terms, List<Variant> variantsList, final int line , final int column ){
        Variant variant  = null;
        if(  line < terms.size() && column < terms.get(line).size() ){
            if( variantsList.size() > 0 ){
                ArrayList<TermRelations> r = new ArrayList<TermRelations>( variantsList.get( variantsList.size() - 1 ).getTerms() );
                variant = new Variant( r );
                variantsList.get( variantsList.size() - 1 ).add( terms.get(line).get(column) );
            }
            else{
                variant  = new Variant( );;
                variantsList.add( new Variant( terms.get(line).get(column) ) );
            }
            if( line+1 < terms.size() )
                getVariant( terms, variantsList, line+1, 0 );
            if( line < terms.size() && column+1 < terms.get(line).size() ){
                variantsList.add( variant );
                getVariant( terms, variantsList, line, column+1 );
            }
        }
    }


    public Variant( ){
        id          = idCounter++;
        childs      = new ArrayList<TermRelations>();
    }


    public Variant( final TermRelations term ){
        id          = idCounter++;
        childs      = new ArrayList<TermRelations>( Arrays.asList( term ) );
    }

    public Variant( final List<TermRelations> termList ){
        id          = idCounter++;
        childs      = termList;
    }


    public Variant( final List<TermRelations> termList, Set<String> variantId  ){
        id          = idCounter++;
        childs      = termList;
        termVariant = variantId;
    }


    public boolean hasVariantOf( final TermRelations term ) {
        boolean         result          = termVariant.contains( term.getId() );
        boolean         isRunning       = true;
        Iterator<TermRelations>  iter  = childs.iterator();
        TermRelations   currentTerm = null;
        
        if( ! result ){
            
            while( isRunning ){
                
                if( iter.hasNext() ){
                    
                    currentTerm = (TermRelations) iter.next();
                    
                    if( term.isVariantOf( currentTerm ) ){
                        result      = true;
                        isRunning   = false;
                        termVariant.add( term.getId() );
                    }
                    
                }
                else
                    isRunning = false;
            }
        }
        
        return result;
    }


    public long getId() {
        return id;
    }


    public void add ( final int position, final TermRelations node ){ // maybe check if node is not a variant and raise an error if it is
        childs.add(position, node);
    }


    public void add ( final TermRelations term ){
        childs.add( term );
    }


    public void addAll ( final List<TermRelations> terms ){
        childs.addAll( terms );
    }


    public Set<String> getTermVariant() {
        return termVariant;
    }


    public void addAll ( final Variant variant ){
        childs.addAll( variant.getTerms() );
        termVariant.addAll( variant.getTermVariant() );
    }


    public List<TermRelations> getTerms() {
        return childs;
    }


    @Override
    public Iterator<TermRelations> iterator() {
        return childs.iterator();
    }


    public TermRelations get( final int index ){
        return childs.get( index );
    }


    public boolean has( final String termId ){
        boolean                 isRunning   = true;
        boolean                 isPresent   = false;
        Iterator<TermRelations>  iter       = childs.iterator();
        TermRelations   currentTerm = null;
        
        while( isRunning ){
            if ( iter.hasNext() ){
                currentTerm = (TermRelations) iter.next();
                if( currentTerm.getId().equals( termId ) ){
                    isRunning   = false;
                    isPresent   = true;
                }
            }
            else
                isRunning = false;
        }
        
        return isPresent;
    }


    public TermRelations find( final String termId ){
        TermRelations           result      = null;
        boolean                 isRunning   = true;
        Iterator<TermRelations> iter        = childs.iterator();
        TermRelations           term        = null;
        
        while( isRunning ){
            if ( iter.hasNext() ){
                term = iter.next();
                if( term.getId().equals( termId ) ){
                    isRunning   = false;
                    result      = term;
                }
            }
            else
                isRunning = false;
        }
        
        return result;
    }


    public int countUntil( final String termId ){
        int                     result      = -1;
        int                     currentIndex= 0;
        boolean                 isRunning   = true;
        Iterator<TermRelations> iter        = childs.iterator();
        TermRelations           term        = null;
        
        while( isRunning ){
            if ( iter.hasNext() ){
                term = iter.next();
                if( term.getId().equals( termId ) ){
                    isRunning   = false;
                    result      = currentIndex;
                }
                else
                    currentIndex++;
            }
            else
                isRunning = false;
        }
        
        return result;
    }


    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for( TermRelations term : childs )
            str.append( term.toString() );
        return str.toString();
    }


    public int countUntilVariantOf( final TermRelations term ) {
        int                     result      = -1;
        int                     index       = 0;
        boolean                 isSearching = true;
        Iterator<TermRelations> iter        = childs.iterator();
        TermRelations           current     = null;
        
        while( isSearching ){
            if( iter.hasNext() ){
                current = (TermRelations) iter.next();
                if( current.isVariantOf(term) ){
                    isSearching = false;
                    result      = index;
                }
                else
                    index++;
            }
            else
                isSearching = false;
        }
        return result;
    }


    public List<String> getTermId(){
        List<String>    ids     = new ArrayList<String>( childs.size() );
        for( TermRelations term : childs )
            ids.add( term.getId() );
        return ids;
    }


    public int size(){
        return childs.size();
    }
}
