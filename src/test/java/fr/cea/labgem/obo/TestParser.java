package fr.cea.labgem.obo;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

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
        Node    root    = parser.getTerms("UPa:UPA00033");
        assertEquals( "UPa:UPA00033", root.getTerm().getId() );
    }

}
