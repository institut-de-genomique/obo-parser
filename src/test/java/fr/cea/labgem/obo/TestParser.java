package fr.cea.labgem.obo;

import java.net.URL;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import fr.cea.ig.obo.Parser;
import fr.cea.ig.obo.model.Cardinality;
import fr.cea.ig.obo.model.Relation;
import fr.cea.ig.obo.model.Term;
import fr.cea.ig.obo.model.TermRelations;
import fr.cea.ig.obo.model.ULS;
import fr.cea.ig.obo.model.UPA;

public class TestParser extends TestCase {
    
    private static URL file = Thread.currentThread().getContextClassLoader()
                                                    .getResource("unipath.obo");
    
    private Parser parser;
    
    @Before
    public void setUp() {
        try {
            parser = new Parser( file.getPath() );
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGetTerm() {
        Term    term    = parser.getTerm("UPa:UPA00033");
        assertEquals( "UPa:UPA00033", term.getId() );
    }
    
    @Test
    public void testULSVariant() {
        UPA                         term    = (UPA) parser.getTerm("UPa:UPA00033");
        List<List<TermRelations>>   childs  = term.getChilds();

        assertEquals( 2, childs.size() );
        assertEquals( "UPa:ULS00013", childs.get(1).get(0).getId() );
        assertEquals( "UPa:ULS00014", childs.get(1).get(1).getId() );
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
