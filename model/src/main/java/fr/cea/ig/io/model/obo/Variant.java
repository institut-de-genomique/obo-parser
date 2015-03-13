package fr.cea.ig.io.model.obo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Variant implements Iterable<Term> {

    private static          long    idCounter = 1L;
    private static final    Object  countLock = new Object();

    private final long          id;
    private List<Term>          childs;
    private Set<String>         termVariant;

    private void incrementCount() {
    }


    /**
     * getVariant is a function which convert a two dimensional list of terms
     * which are part of another term to a list of each possible way using 
     * Disjunctive normal form.
     * Result is wrote into variable named variantsList as this function used
     * his reference
     * @param terms two dimensional list, the second dimension tell which term is possible to find
     * @param variantsList convert terms to Disjunctive normal form.
     */
    public static void getVariant( final List<List<Term>> terms, List<Variant> variantsList ){
        getVariant( terms, variantsList, 0, 0 );
    }
    
    private static void getVariant( final List<List<Term>> terms, List<Variant> variantsList, final int line , final int column ){
        Variant variant  = null;
        if(  line < terms.size() && column < terms.get(line).size() ){
            if( variantsList.size() > 0 ){
                ArrayList<Term> r = new ArrayList<Term>( variantsList.get( variantsList.size() - 1 ).getTerms() );
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
        synchronized (countLock) {
            idCounter++;
            id      = idCounter;
        }
        childs      = new ArrayList<Term>();
        incrementCount();
    }


    public Variant( final Term term ){
        synchronized (countLock) {
            idCounter++;
            id      = idCounter;
        }
        childs      = new ArrayList<Term>( Arrays.asList( term ) );
        incrementCount();
    }

    public Variant( final List<Term> termList ){
        synchronized (countLock) {
            idCounter++;
            id      = idCounter;
        }
        childs      = termList;
    }


    public Variant( final List<Term> termList, Set<String> variantId  ){
        synchronized (countLock) {
            idCounter++;
            id      = idCounter;
        }
        childs      = termList;
        termVariant = variantId;
    }


    public boolean hasVariantOf( final Term term ) {
        boolean         result          = termVariant.contains( term.getId() );
        boolean         isRunning       = true;
        Iterator<Term>  iter  = childs.iterator();
        Term   currentTerm = null;
        
        if( ! result ){
            
            while( isRunning ){
                
                if( iter.hasNext() ){
                    
                    currentTerm = (Term) iter.next();
                    
                    if( term instanceof TermRelations && currentTerm instanceof TermRelations && ((TermRelations)term).isVariantOf( (TermRelations) currentTerm ) ){
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


    public void add ( final int position, final Term node ){ // maybe check if node is not a variant and raise an error if it is
        childs.add(position, node);
    }


    public void add ( final Term term ){
        childs.add( term );
    }


    public void addAll ( final List<Term> terms ){
        childs.addAll( terms );
    }


    public Set<String> getTermVariant() {
        return termVariant;
    }


    public void addAll ( final Variant variant ){
        childs.addAll( variant.getTerms() );
        termVariant.addAll( variant.getTermVariant() );
    }


    public List<Term> getTerms() {
        return childs;
    }


    @Override
    public Iterator<Term> iterator() {
        return childs.iterator();
    }


    public Term get( final int index ){
        return childs.get( index );
    }


    public boolean has( final String termId ){
        boolean                 isRunning   = true;
        boolean                 isPresent   = false;
        Iterator<Term>  iter       = childs.iterator();
        Term   currentTerm = null;
        
        while( isRunning ){
            if ( iter.hasNext() ){
                currentTerm = (Term) iter.next();
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


    public Term find( final String termId ){
        Term           result      = null;
        boolean                 isRunning   = true;
        Iterator<Term> iter        = childs.iterator();
        Term           term        = null;
        
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
        Iterator<Term> iter        = childs.iterator();
        Term           term        = null;
        
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
        for( Term term : childs )
            str.append( term.toString() );
        return str.toString();
    }


    public int countUntilVariantOf( final Term term ) {
        int                     result      = -1;
        int                     index       = 0;
        boolean                 isSearching = true;
        Iterator<Term> iter        = childs.iterator();
        Term           current     = null;
        
        while( isSearching ){
            if( iter.hasNext() ){
                current = (Term) iter.next();
                if( term instanceof TermRelations && current instanceof TermRelations && ((TermRelations)current).isVariantOf( (TermRelations) term ) ){
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
        for( Term term : childs )
            ids.add( term.getId() );
        return ids;
    }


    public int size(){
        return childs.size();
    }


    public List<String> getTermName() {
        List<String>    names     = new ArrayList<String>( childs.size() );
        for( Term term : childs )
            names.add( term.getName() );
        return names;
    }
}
