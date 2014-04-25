package fr.cea.ig.obo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.cea.ig.obo.model.Term;
import fr.cea.ig.obo.model.Variant;

public class Node implements Iterable<Variant> {

    private List<Variant>   variants;
    private Term            term;


    /**
     * @param depth
     * @param child
     * @return
     */
    private String recurseNode( final int depth, final Node child ){
        final int       indentSize  = 4;
        final int       indent      = indentSize * depth;
        StringBuilder   result      = new StringBuilder();
        String          indenter    = new String(new char[indent]).replace("\0", " ");
        boolean         needToAddOr = false;
        
        result.append( indenter + "-" + child.getTerm().getId() + "\n");
        
        for( Variant nodes : child.getVariants() ){
            if( ! needToAddOr ){
                needToAddOr = true;
            }
            else{
                result.append( indenter + "Or\n" );
                needToAddOr = false;
            }
            for( Node node : nodes ){
                result.append( recurseNode( depth + 1, node ) );
            }
        }
        return result.toString();
    }


    /**
     * @param term
     * @param nodes
     */
    public Node( final Term term, final List<Variant> nodes ){
        this.variants   = nodes;
        this.term       = term;
    }


    /**
     * @param term
     */
    public Node( final Term term ){
        this( term, new ArrayList<Variant>() );
    }


    public Term getTerm() {
        return term;
    }


    public String getId(){
        return term.getId();
    }


    public String getName() {
        return term.getName();
    }


    public String getDefinition() {
        return term.getDefinition();
    }


    public List<Variant> setVariants( final List<Variant> variants ) {
        return this.variants = variants;
    }


    public List<Variant> getVariants() {
        return variants;
    }


    public void addNode( final int index, final Node node ){ 
        if( index >= 0 && index < variants.size() )
            variants.get( index ).add( node );
        else
            throw new IndexOutOfBoundsException();
    }


    public void insertNode( final String termId, final Node node ){
        int     variantIndex= 0;
        int     nodeIndex   = 0;
        boolean isSearching = true;
        boolean isFound     = false;
        
        while( isSearching ){
            if( variantIndex < variants.size() ){
                nodeIndex =  variants.get( variantIndex ).countUntil( termId );
                if( nodeIndex == -1 )
                    variantIndex++;
                else{
                    isSearching = false;
                    isFound     = true;
                    variants.get( variantIndex ).add( nodeIndex, node );
                }
            }
            else
                isSearching = false;
        }
        if( ! isFound )
            throw new IndexOutOfBoundsException("Term id: " + termId + " was not found");
    }


    public boolean has( final String termId ){
        boolean             result      = false;
        boolean             isRunning   = true;
        Iterator<Variant>   iter        = variants.iterator();
        Variant             variant     = null;
        
        while( isRunning ){
            if( iter.hasNext() ){
                variant = iter.next();
                if( variant.has( termId ) ){
                    isRunning   = false;
                    result      = true;
                }
            }
            else
                isRunning = false;
        }
        return result;
    }


    @Override
    public Iterator<Variant> iterator() {
        return variants.iterator();
    }


    public Variant get( final int index ){
        return variants.get( index );
    }


    @Override
    public String toString(){
        StringBuilder   str         = new StringBuilder( term.getId() + "\n" );
        boolean         needToAddOr = false;
        for( Variant variant : variants ){
            if( ! needToAddOr ){
                needToAddOr = true;
            }
            else{
                str.append( "    Or\n" );
                needToAddOr = false;
            }
            for( Node node : variant )
                str.append( recurseNode(1, node) );
        }
        return str.toString();
    }
}
