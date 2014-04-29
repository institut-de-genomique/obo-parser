package fr.cea.ig.obo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.cea.ig.obo.model.Cardinality;
import fr.cea.ig.obo.model.Relation;
import fr.cea.ig.obo.model.Relations;
import fr.cea.ig.obo.model.Term;
import fr.cea.ig.obo.model.TermRelations;
import fr.cea.ig.obo.model.UCR;
import fr.cea.ig.obo.model.UER;
import fr.cea.ig.obo.model.ULS;
import fr.cea.ig.obo.model.UPA;
import fr.cea.ig.obo.model.UPC;

public class Parser {
    private static int PAGE_SIZE            = 4_096;
    private static int DEFAULT_NUMBER_PAGE  = 10; 
    private Map<String,Term> terms;


    private String extractQuotedString( final String line ){
        String result = null;
        int quoteStart= line.indexOf("\"");
        int quoteEnd  = -1;
        
        if( quoteStart >= 0){
            quoteEnd = line.substring( quoteStart + 1 ).indexOf("\"");
            if( quoteEnd >= 0 )
                quoteStart++;
                result = line.substring(quoteStart, quoteStart + quoteEnd );
        }
        
        return result;
    }


    private Cardinality parseCardinality( final String line ){
        final String    cardinalityToken    = "cardinality";
        final String    orderToken          = "order";
        final String    isPrimaryToken      = "is_primary";
        final String    isAlternateToken    = "is_alternate";
        final String    directionToken      = "direction";
        String          number              = "";
        String          order               = "";
        Boolean         isPrimary           = false;
        Boolean         isAlternate         = false;
        String          direction           = "";
        String          tmp                 = null;
        int             cardinatilityPos    = line.indexOf( cardinalityToken );
        int             orderPos            = line.indexOf( orderToken );
        int             isPrimaryPos        = line.indexOf( isPrimaryToken );
        int             isAlternatePos      = line.indexOf( isAlternateToken );
        int             directionPos        = line.indexOf( directionToken );
        
        if( cardinatilityPos >=0 )
            number = extractQuotedString( line.substring(cardinatilityPos) );
        if( orderPos >=0 )
            order = extractQuotedString( line.substring(orderPos) );
        if( directionPos >=0 )
            order = extractQuotedString( line.substring(directionPos) );
        if( isPrimaryPos >=0 ){
            tmp = extractQuotedString( line.substring(isPrimaryPos) );
            if( tmp != null )
                isPrimary = ( "False".equals(tmp) ) ? false : true;
        }
        if( isAlternatePos >=0 ){
            tmp = extractQuotedString( line.substring(isAlternatePos) );
            if( tmp != null )
                isAlternate = ( "False".equals(tmp) ) ? false : true;
        }
        
        return new Cardinality( number, order, direction, isPrimary, isAlternate );
    }
    
    private static Term termFactory(final String id, final String name, final String namespace, final String definition, final Set<Relation>   has_input_compound, final Set<Relation> has_output_compound, final Set<Relation> part_of, final Relation isA, final Relation superPathway ) throws ParseException{
        Term term = null;
        if( namespace.equals("reaction") )
            term = new UCR( id, name, definition, new Relations(has_input_compound, has_output_compound, part_of ) );
        else if( namespace.equals("enzymatic_reaction") )
            term = new UER( id, name, definition, new Relations(has_input_compound, has_output_compound, part_of ) );
        else if( namespace.equals("linear_sub_pathway") )
            term = new ULS( id, name, definition, new Relations(has_input_compound, has_output_compound, part_of ) );
        else if( namespace.equals("pathway") )
            term = new UPA( id, name, definition, new Relations(has_input_compound, has_output_compound, part_of ), isA, superPathway );
        else if( namespace.equals("compound") )
            term = new UPC( id, name, definition );
        else
            throw new ParseException("Unknown namespace: " + namespace, -1 );
        return term;
    }


    private void saveTerm(final String id, final String name, final String namespace, final String definition, final Set<Relation>   has_input_compound, final Set<Relation> has_output_compound, final Set<Relation> part_of, final Relation isA, final Relation superPathway ) throws ParseException{
        Term term = termFactory( id, name, namespace, definition, has_input_compound, has_output_compound,  part_of, isA, superPathway );
        boolean isTermWithRelation = true;
        if( term instanceof UPC )
            isTermWithRelation = false;
        
        if( isTermWithRelation ){
            
            Set<Relation> relations = ((TermRelations) term).getRelation("part_of");
            TermRelations parent    = null;
            
            for( Relation relation : relations ){
                
                parent =  (TermRelations) terms.get( relation.getIdLeft() );
                
                if( parent != null )
                    parent.add( term );
                else{
                    parent = new TermRelations( relation.getIdLeft(),"", "" );
                    parent.add( term );
                    terms.put(relation.getIdLeft(), parent);
                }
            }
            
            Term termRelation = terms.get( id );
            if( termRelation != null ){
                List<List<Term>> childs = ((TermRelations) termRelation).getChilds();
                ((TermRelations) term).addAll( childs );
            }
            
            terms.put(id, term);
        }
        else
            terms.put(id, term);
    }


    /**
     * @param type
     * @param line
     * @return
     */
    private Relation parseRelationShip( final String type, final String line ){
        Cardinality     cardinality     = null;
        int             index           = 0;
        String          name            = "";
        String[]        cursor          = { "idLeft", "idRight", "name" };
        String          idLeft          = "";
        String          idRight         = "";
        String[]        splittedLine    = line.split("!");
        
        for( String item : splittedLine ){ // unbound array if do not fit expected format
            if( "idLeft".equals(cursor[ index ]) ){
                int cardinalityPos = item.indexOf("{cardinality");
                if( cardinalityPos != -1 ){
                    cardinality = parseCardinality( item.substring(cardinalityPos) );
                    idLeft      = item.substring( 0, cardinalityPos ).trim();
                }
                else
                    idLeft = item.trim();
            }
            else if( "idRight".equals(cursor[ index ]) ){
                idRight = item.trim();
            }
            else if( "name".equals(cursor[ index ]) ){
                name = item.trim();
            }
            index++;
        }
        
        return new Relation( type, idLeft, cardinality, idRight, name );
    }


    /**
     * 
     * @param type
     * @param line
     * @return
     */
    private Relation parseRelation( final String type, final String line ){
        Cardinality     cardinality     = null;
        String[]        splittedLine    = line.split("!");
        String          idLeft          = splittedLine[0].trim();
        String          name            = splittedLine[1].trim();
        String          idRight         = "";
        
        return new Relation( type, idLeft, cardinality, idRight, name );
    }


    /**
     * @param path
     * @param numberPage
     * @throws ParseException
     * @throws IOException
     */
    public Parser(final String path, final int numberPage ) throws ParseException, IOException {
        InputStreamReader   isr     = new InputStreamReader( new FileInputStream(path), Charset.forName("US-ASCII") );
        BufferedReader      br      = new BufferedReader( isr, PAGE_SIZE * numberPage );
        terms = new HashMap<String,Term>();
        String line = br.readLine();
        
        String          id                  = null;
        String          name                = null;
        String          namespace           = null;
        String          definition          = null;
        Set<Relation>   has_input_compound  = new HashSet<Relation>();
        Set<Relation>   has_output_compound = new HashSet<Relation>();
        Set<Relation>   part_of             = new HashSet<Relation>();
        Relation        isA                 = null;
        Relation        superPathway        = null;
        final String    tokenInput          = "has_input_compound";
        final String    tokenOutput         = "has_output_compound";
        final String    tokenPartOf         = "part_of";
        final String    tokenIsA            = "is_a";
        final String    tokenSuperPathway   = "uniprot_super_pathway";
        
        while( line != null ){
            if( line.startsWith("[Typedef]") ){
                line                = br.readLine();
                boolean isRunning   = true;
                while( isRunning ){
                    if( line == null )
                        isRunning   = false;
                    else if( line.equals("") )
                        isRunning   = false;
                    else
                        line = br.readLine();
                }
            }
            else if( line.startsWith("[Term]") ){
                if( id != null ){
                    saveTerm(id, name, namespace, definition, has_input_compound, has_output_compound, part_of, isA, superPathway);
                    id                  = null;
                    name                = null;
                    namespace           = null;
                    definition          = null;
                    has_input_compound  = new HashSet<Relation>();
                    has_output_compound = new HashSet<Relation>();
                    part_of             = new HashSet<Relation>();
                    isA                 = null;
                    superPathway        = null;
                }
            }
            else if( line.startsWith("id:") )
                id = line.substring( 4 );
            else if( line.startsWith("name:") )
                name = line.substring( 6 );
            else if( line.startsWith("namespace:") )
                namespace = line.substring( 11 );
            else if( line.startsWith("def:") )
                definition = line.substring( 5 );
            else if( line.startsWith("relationship:") ){
                if( line.contains(tokenInput) )
                    has_input_compound.add( parseRelationShip( tokenInput, line.substring( line.indexOf(tokenInput) + tokenInput.length() ).trim() ) );
                else if( line.contains(tokenOutput) )
                    has_output_compound.add( parseRelationShip( tokenOutput, line.substring( line.indexOf(tokenOutput) + tokenOutput.length() ).trim() ) );
                else if( line.contains( tokenPartOf ) )
                    part_of.add( parseRelationShip( tokenPartOf, line.substring( line.indexOf(tokenPartOf) + tokenPartOf.length() ).trim() ) );
            }
            else if( line.startsWith( "is_a:" ) )
               isA = parseRelation( tokenIsA, line.substring( line.indexOf(tokenIsA) + tokenIsA.length() + 1 ).trim() );
            else if( line.startsWith( "uniprot_super_pathway:" ) )
                superPathway = parseRelation( tokenSuperPathway, line.substring( line.indexOf(tokenSuperPathway) + tokenSuperPathway.length() + 1 ).trim() );
            line = br.readLine();
        }
        saveTerm(id, name, namespace, definition, has_input_compound, has_output_compound, part_of, isA, superPathway );
        isr.close();
        br.close();
    }


    /**
     * @param path
     * @throws ParseException
     * @throws IOException
     */
    public Parser( final String path ) throws ParseException, IOException {
        this( path, DEFAULT_NUMBER_PAGE );
    }


    /**
     * @param id
     * @return
     */
    public Term getTerm( final String id ){
        return terms.get( id );
    }
}
