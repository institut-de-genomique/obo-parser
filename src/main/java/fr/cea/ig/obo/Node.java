package fr.cea.ig.obo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.cea.ig.obo.model.Term;

public class Node{
    private List<List<Node>>          variants;
    private Term                term;
    
    /**
     * @param depth
     * @param child
     * @return
     */
    private String recurseNode( final int depth, final Node child ){
        final int       indentSize  = 4;
        int             indent      = indentSize * depth;
        StringBuilder   result      = new StringBuilder();
        String          indenter    = new String(new char[indent]).replace("\0", " ");
        boolean         needToAddOr = false;
        
        result.append( indenter + "-" + child.getTerm().getId() + "\n");
        
        for( List<Node> nodes : child.getVariants() ){
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
    
    private static int find( final String termNameToFind, final List<Node> nodes ){
        int     index       = -1;
        boolean isSearching = true;
        while( isSearching ){
            if( index < nodes.size() ){
                final Node      node        = nodes.get(index);
                final Term      term        = node.getTerm();
                final String    termName    = term.getName();
                if( ! termName.equals( termNameToFind ) )
                    index++;
                else
                    isSearching = false;
            }
            else{
                isSearching = false;
                index       = -1;
            }
        }
        return index;
    }
    
    private static boolean has(final String termName, final List<List<Node>> nodes ){
        boolean isFound     = false;
        boolean isSearching = true;
        int     variantIndex= 0;
        int     nodeIndex   = 0;
        while( isSearching ){
            if( variantIndex < nodes.size()){
                nodeIndex = find(termName, nodes.get(variantIndex));
                if( nodeIndex == -1 )
                    variantIndex++;
                else{
                    isSearching = false;
                    isFound     = true;
                }
            }
            else
                isSearching = false;
        }
        return isFound;
    }
    
    /**
     * @param term
     * @param nodes
     */
    public Node( Term term, List<List<Node>> nodes ){
        this.variants   = nodes;
        this.term       = term;
    }
    
    /**
     * @param term
     */
    public Node( Term term ){
        this( term, new ArrayList<List<Node>>() );
    }

    public Term getTerm() {
        return term;
    }

    public List<List<Node>> setVariants( List<List<Node>> variants ) {
        return this.variants = variants;
    }
    public List<List<Node>> getVariants() {
        return variants;
    }

    public void addNode( final int index, final Node node ){ 
        if( index >= 0 && index < variants.size() )
            variants.get( index ).add( node );
        else
            throw new IndexOutOfBoundsException();
    }
    
    public void insertNode( final String termName, final Node node ){
        int     variantIndex= 0;
        int     nodeIndex   = 0;
        boolean isSearching = true;
        boolean isFound     = false;
        
        while( isSearching ){
            if( variantIndex < variants.size() ){
                nodeIndex = find(termName, variants.get(variantIndex));
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
            throw new IndexOutOfBoundsException("Term named: " + termName + " was not found");
    }
    
    public boolean has( final String termName ){
        return has( termName, variants );
    }
    
    
    @Override
    public String toString(){
        StringBuilder   str         = new StringBuilder( term.getId() + "\n" );
        boolean         needToAddOr = false;
        for( List<Node> variant : variants ){
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
    
    public boolean hasSubNode() {
        return variants.size() > 0;
    }
}
