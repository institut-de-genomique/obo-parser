package fr.cea.labgem.obo;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import fr.cea.ig.obo.Node;
import fr.cea.ig.obo.Parser;
import fr.cea.ig.obo.model.Cardinality;
import fr.cea.ig.obo.model.Relation;
import fr.cea.ig.obo.model.Term;
import fr.cea.ig.obo.model.ULS;
import fr.cea.ig.obo.model.UPA;

public class TestParser extends TestCase {
    
    private static URL file = Thread.currentThread().getContextClassLoader()
                                                    .getResource("unipath.obo");
    
    private Parser parser;
    
    @Before
    public void setUp() throws ParseException, IOException {
        parser = new Parser( file.getPath() );
    }
    
    @Test
    public void testRootTree() {
        Term    term    = parser.getTerm("UPa:UPA00033");
        assertEquals( "UPa:UPA00033", term.getId() );
    }

    @Test
    public void testSubTermsWithDepthZero() {
        Node        root    = parser.getTerms("UPa:UPA00033");
        List<Term> subTerms = root.getSubTerms(0 , 0);
        assertEquals(subTerms.get(0).getId() , root.getTerm().getId() );
    }
    
    @Test
    public void testSubTermsWithDepthOne() {
        Node        root    = parser.getTerms("UPa:UPA00033");
        List<Term>  expected= new ArrayList<Term>(4);
        expected.add( root.getTerm() );
        expected.add( parser.getTerms("UPa:ULS00012").getTerm() );
        expected.add( parser.getTerms("UPa:ULS00013").getTerm() );
        expected.add( parser.getTerms("UPa:ULS00014").getTerm() );
        
        List<Term> subTerms = root.getSubTerms( 1 );
        for( Term term : expected )
            assertEquals(subTerms.contains(term) , true );
    }
    
    @Test
    public void testRelation() {
        UPA         term     = (UPA) parser.getTerm("UPa:UPA00033");
        Relation    relation = new Relation( "is_a", "UPa:UPA00404", "L-lysine biosynthesis" );
        assertEquals( term.getIsA().toString(), relation.toString() );
    }
    
    @Test
    public void testCardinality() {
        ULS             term        = (ULS) parser.getTerm("UPa:ULS00012");
        Relation        relation1   = new Relation( "has_input_compound", "UPa:UPC00026", new Cardinality( "1" ), "UPa:UPC00026", "2-oxoglutarate" );
        Relation        relation2   = new Relation( "has_output_compound", "UPa:UPC00956", new Cardinality( "1" ), "UPa:UPC00956", "L-alpha-aminoadipate" );
        Relation        relation3   = new Relation( "part_of", "UPa:UPA00033", new Cardinality( "1")  );
        Set<Relation>   sr1         = term.getRelation( "has_input_compound");
        Relation[]      arr1        = sr1.toArray(new Relation[sr1.size()] );
        Set<Relation>   sr2         = term.getRelation( "has_output_compound");
        Relation[]      arr2        = sr2.toArray(new Relation[sr1.size()] );
        Set<Relation>   sr3         = term.getRelation( "part_of");
        Relation[]      arr3        = sr3.toArray(new Relation[sr1.size()] );
        assertEquals( "UPa:ULS00012", term.getId() );
        assertEquals( arr1[0].equals(relation1), true );
        assertEquals( arr2[0].equals(relation2), true );
        assertEquals( arr3[0].equals(relation3), true );
        
    }
}
