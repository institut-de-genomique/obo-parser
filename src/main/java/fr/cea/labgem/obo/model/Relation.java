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
        this( type, id, null, "", "" );
    }
    
    /**
     * @param type
     * @param id
     * @param cardinality
     */
    public Relation( final String type, final String id, final Cardinality cardinality ){
        this( type, id, cardinality, "", "" );
    }
    
    /**
     * @param type
     * @param id
     * @param name
     */
    public Relation( final String type, final String id, String name ){
        this( type, id, null, "", name );
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
        StringBuilder result = new StringBuilder( type + ": " + idLeft );
        if( cardinality != null )
            result.append( " " + cardinality.toString() );
        if( idRight != null && ! idRight.isEmpty() )
            result.append( " ! " + idRight );
        if( name != null && ! name.isEmpty() )
            result.append( " ! " + name );
        return result.toString();
    }
    
    @Override
    public boolean equals(Object obj){
        boolean result = false;
        if (obj == this)
            result = true;
        else if( obj == null || obj.getClass() != this.getClass() )
            result = false;
        else {
            Relation relation = (Relation) obj;
            if( relation.getCardinality().equals(cardinality)   &&
                relation.getIdLeft().equals(idLeft)             &&
                relation.getIdRight().equals(idRight)           &&
                relation.getName().equals(name)                 &&
                relation.getType().equals(type)                 )
                result = true;
        }
        return result;
    }
}