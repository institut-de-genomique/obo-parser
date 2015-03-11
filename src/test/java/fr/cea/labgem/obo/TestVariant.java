package fr.cea.labgem.obo;


import fr.cea.ig.io.model.obo.Term;
import fr.cea.ig.io.model.obo.TermRelations;
import fr.cea.ig.io.model.obo.Variant;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestVariant extends TestCase {
    
    @Test
    public void testVariant() {
        List<List<Term>> terms = new ArrayList<List<Term>>();
        terms.add( new ArrayList<Term>( Arrays.asList(new TermRelations("1a","1a","1a"), new TermRelations("1b","1b","1b") ) ) );
        terms.add( new ArrayList<Term>( Arrays.asList(new TermRelations("2a","2a","2a") ) ) );
        terms.add( new ArrayList<Term>( Arrays.asList(new TermRelations("3a","3a","3a"), new TermRelations("3b","3b","3b") ) ) );
        terms.add( new ArrayList<Term>( Arrays.asList(new TermRelations("4a","4a","4a"), new TermRelations("4b","4b","4b") ) ) );
        
        List<Variant> variantsList = new ArrayList<Variant>();
        
        Variant.getVariant(terms, variantsList );
        
        assertEquals( "1a", variantsList.get(0).get(0).getName() );
        assertEquals( "2a", variantsList.get(0).get(1).getName() );
        assertEquals( "3a", variantsList.get(0).get(2).getName() );
        assertEquals( "4a", variantsList.get(0).get(3).getName() );
         
        assertEquals( "1a", variantsList.get(1).get(0).getName() );
        assertEquals( "2a", variantsList.get(1).get(1).getName() );
        assertEquals( "3a", variantsList.get(1).get(2).getName() );
        assertEquals( "4b", variantsList.get(1).get(3).getName() );
         
        assertEquals( "1a", variantsList.get(2).get(0).getName() );
        assertEquals( "2a", variantsList.get(2).get(1).getName() );
        assertEquals( "3b", variantsList.get(2).get(2).getName() );
        assertEquals( "4a", variantsList.get(2).get(3).getName() );
         
        assertEquals( "1a", variantsList.get(3).get(0).getName() );
        assertEquals( "2a", variantsList.get(3).get(1).getName() );
        assertEquals( "3b", variantsList.get(3).get(2).getName() );
        assertEquals( "4b", variantsList.get(3).get(3).getName() );
         
        assertEquals( "1b", variantsList.get(4).get(0).getName() );
        assertEquals( "2a", variantsList.get(4).get(1).getName() );
        assertEquals( "3a", variantsList.get(4).get(2).getName() );
        assertEquals( "4a", variantsList.get(4).get(3).getName() );
         
        assertEquals( "1b", variantsList.get(5).get(0).getName() );
        assertEquals( "2a", variantsList.get(5).get(1).getName() );
        assertEquals( "3a", variantsList.get(5).get(2).getName() );
        assertEquals( "4b", variantsList.get(5).get(3).getName() );
        
        assertEquals( "1b", variantsList.get(6).get(0).getName() );
        assertEquals( "2a", variantsList.get(6).get(1).getName() );
        assertEquals( "3b", variantsList.get(6).get(2).getName() );
        assertEquals( "4a", variantsList.get(6).get(3).getName() );
        
        assertEquals( "1b", variantsList.get(7).get(0).getName() );
        assertEquals( "2a", variantsList.get(7).get(1).getName() );
        assertEquals( "3b", variantsList.get(7).get(2).getName() );
        assertEquals( "4b", variantsList.get(7).get(3).getName() );

    }
}

