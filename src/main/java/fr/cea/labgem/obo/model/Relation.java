package fr.cea.labgem.obo.model;

public class Relation {

    private final String        type;
    private final String        name;
    private final String        idLeft;
    private final String        idRight;
    private final Cardinality   cardinality;
    
    /**
     * @param type
     * @param idLeft
     * @param cardinality
     * @param idRight
     * @param name
     */
    public Relation( final String type, final String idLeft, final Cardinality cardinality, final String idRight, final String name ){
        this.type           = type;
        this.idLeft         = idLeft;
        this.cardinality    = cardinality;
        this.idRight        = idRight;
        this.name           = name;
    }
    
    /**
     * @param type
     * @param id
     */
    public Relation( final String type, final String id ){
        this( type, id, null, null, null );
    }
    
    /**
     * @param type
     * @param id
     * @param name
     */
    public Relation( final String type, final String id, String name ){
        this( type, id, null, null, name );
    }
    

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getIdLeft() {
        return idLeft;
    }

    public String getIdRight() {
        return idRight;
    }

    public Cardinality getCardinality() {
        return cardinality;
    }
    
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder( type + " UPa:" + idLeft );
        if( cardinality != null )
            result.append( " " + cardinality.toString() );
        if( idRight != null )
            result.append( " ! UPa:" + idRight );
        if( name != null )
            result.append( " ! " + name );
        return result.toString();
    }
}