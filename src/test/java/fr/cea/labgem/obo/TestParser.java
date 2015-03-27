package fr.cea.labgem.obo;

import fr.cea.ig.io.model.obo.*;
import fr.cea.ig.io.parser.OboParser;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TestParser extends TestCase {
    
    private static URL file = Thread.currentThread().getContextClassLoader()
                                                    .getResource("unipath.obo");
    
    private OboParser oboParser;
    
    @Before
    public void setUp() {
        try {
            oboParser = new OboParser( file.getPath() );
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGetTerm() {
        Term    term    = oboParser.getTerm("UPa:UPA00033");
        assertEquals( "UPa:UPA00033", term.getId() );
    }
    
    @Test
    public void testULSVariant() {
        UPA                term    = (UPA) oboParser.getTerm("UPa:UPA00033");
        List<List<Term>>   childs  = term.getChilds();

        assertEquals( 2, childs.size() );
        assertEquals( "UPa:ULS00013", childs.get(1).get(0).getId() );
        assertEquals( "UPa:ULS00014", childs.get(1).get(1).getId() );
    }

    
    @Test
    public void testRelation() {
        UPA         term     = (UPA) oboParser.getTerm("UPa:UPA00033");
        Relation    relation = new Relation( "is_a", "UPa:UPA00404", "L-lysine biosynthesis" );
        Iterator<Relation> iter = term.getIsA().iterator();
        assertTrue(iter.hasNext());
        assertEquals( iter.next().toString(), relation.toString() );
    }
    
    @Test
    public void testCardinality() {
        ULS             term        = (ULS) oboParser.getTerm("UPa:ULS00012");
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
