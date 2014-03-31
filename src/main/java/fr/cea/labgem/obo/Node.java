package fr.cea.labgem.obo;

import java.util.HashSet;
import java.util.Set;

import fr.cea.labgem.obo.model.Term;

public class Node{
    private Set<Node>   nodes;
    private Term        term;
    
    /**
     * @param depht
     * @param child
     * @return
     */
    private String recurseNode( final int depht, final Node child ){
        final int       indentSize  = 4;
        int             indent      = indentSize * depht;
        StringBuilder   result      = new StringBuilder();
        String          indenter    = new String(new char[indent]).replace("\0", " ");
        
        result.append( indenter + "-" + child.getTerm().getId() + "\n");
        
        for( Node node : child.getNodes() )
            result.append( recurseNode( depht + 1, node ) );
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
}
