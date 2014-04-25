package fr.cea.ig.obo.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import fr.cea.ig.obo.Node;

public class Variant implements Iterable<Node> {

    private static long idCounter = 1L;
    
    private long id;
    private List<Node>      nodesList;
    private Set<String>     termVariant;
    //private Set<Integer>  variants;


    public Variant( ){
        id          = idCounter++;
        nodesList   = new ArrayList<Node>();
    }


    public Variant( final List<Node> termList ){
        id          = idCounter++;
        nodesList   = termList;
    }


    public Variant( final List<Node> termList, Set<String> variantId  ){
        id          = idCounter++;
        nodesList   = termList;
        termVariant = variantId;
    }


    public boolean hasVariantOf( final TermRelations term ) {
        boolean         result      = termVariant.contains( term.getId() );
        boolean         isRunning   = true;
        Iterator<Node>  iter        = nodesList.iterator();
        TermRelations   currentTerm = null;
        
        if( ! result ){
            
            while( isRunning ){
                
                if( iter.hasNext() ){
                    
                    currentTerm = (TermRelations) iter.next().getTerm();
                    
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


    public void add ( final int position, final Node node ){ // maybe check if node is not a variant and raise an error if it is
        nodesList.add(position, node);
    }


    public void add ( Node node ){
        nodesList.add( node );
    }


    @Override
    public Iterator<Node> iterator() {
        return nodesList.iterator();
    }


    public Node get( final int index ){
        return nodesList.get( index );
    }


    public boolean has( final String termId ){
        boolean         isRunning   = true;
        boolean         isPresent   = false;
        Iterator<Node>  iter        = nodesList.iterator();
        TermRelations   currentTerm = null;
        
        while( isRunning ){
            if ( iter.hasNext() ){
                currentTerm = (TermRelations) iter.next().getTerm();
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


    public Node find( final String termId ){
        Node            result      = null;
        boolean         isRunning   = true;
        Iterator<Node>  iter        = nodesList.iterator();
        Node            node        = null;
        
        while( isRunning ){
            if ( iter.hasNext() ){
                node = iter.next();
                if( node.getTerm().getId().equals( termId ) ){
                    isRunning   = false;
                    result        = node;
                }
            }
            else
                isRunning = false;
        }
        
        return result;
    }


    public int countUntil( final String termId ){
        int             result      = -1;
        int             currentIndex= 0;
        boolean         isRunning   = true;
        Iterator<Node>  iter        = nodesList.iterator();
        Node            node        = null;
        
        while( isRunning ){
            if ( iter.hasNext() ){
                node = iter.next();
                if( node.getTerm().getId().equals( termId ) ){
                    isRunning   = false;
                    result        = currentIndex;
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
        for( Node node : nodesList )
            str.append( node.toString() );
        return str.toString();
    }



    public int countUntilVariantOf( final TermRelations term ) {
        int             result      = -1;
        int             index       = 0;
        boolean         isSearching = true;
        Iterator<Node>  iter        = nodesList.iterator();
        TermRelations   current     = null;
        
        while( isSearching ){
            if( iter.hasNext() ){
                current = (TermRelations) iter.next().getTerm();
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
}
