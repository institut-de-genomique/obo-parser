package fr.cea.ig.obo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.cea.ig.obo.model.Term;

public class Node{
    private Set<Node>   nodes;
    private Term        term;
    
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
        
        result.append( indenter + "-" + child.getTerm().getId() + "\n");
        
        for( Node node : child.getNodes() )
            result.append( recurseNode( depth + 1, node ) );
        return result.toString();
    }
    
    /**
     * @param nodes
     * @param term
     */
    public Node( HashSet<Node> nodes, Term term ){
        this.nodes = nodes;
        this.term  = term;
    }
    
    /**
     * @param term
     */
    public Node( Term term ){
        this( new HashSet<Node>(), term );
    }

    public Term getTerm() {
        return term;
    }

    public Set<Node> getNodes() {
        return nodes;
    }
    
    public void addNode( Node node ){
        nodes.add( node );
    }
    
    @Override
    public String toString(){
        StringBuilder   str         = new StringBuilder( term.getId() + "\n" );
        for( Node node : nodes )
            str.append( recurseNode(1, node) );
        return str.toString();
    }
    
    /**
     * 
     * @param depth Get term until depth x. Depth equal to -1 involve to get all sub terms.
     * @return A List of sub term from given Node
     */
    public List<Term> getSubTerms( final int maxDepth, final int currentDepth ){
        List<Term>  result          = new ArrayList<Term>( );
        boolean needGoingInDepth    = false;
        
        result.add( term );
        
        if ( nodes == null )
            needGoingInDepth = false;
        else if( maxDepth == -1 )
            needGoingInDepth = true;
        else if ( currentDepth < maxDepth )
            needGoingInDepth = true;
        
        if( needGoingInDepth ){
            for( Node node : nodes )
                result.addAll( node.getSubTerms(maxDepth, currentDepth + 1) );
        }
        
        return result;
    }
    
    public List<Term> getSubTerms( final int maxDepth ){
        return getSubTerms( maxDepth, 0 );
    }
    
    public List<Term> getSubTerms( ){
        return getSubTerms( -1, 0 );
    }
    
    public boolean hasSubNode() {
        return nodes.size() > 0;
    }
}
