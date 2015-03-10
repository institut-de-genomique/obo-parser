package fr.cea.ig.io.model.obo;

public class Relation {

    private final String        type;
    private final String        name;
    private final String        idLeft;
    private final String        idRight;
    private final Cardinality   cardinality;
    
    /**
     * @param type Relation type as UPA, UPC, EUR, ULS, UCR
     * @param idLeft Id of left term in given relation
     * @param cardinality Cardinality between term left and right
     * @param idRight Id of right term in given relation
     * @param name Relation name
     */
    public Relation( final String type, final String idLeft, final Cardinality cardinality, final String idRight, final String name ){
        this.type           = type;
        this.idLeft         = idLeft;
        this.cardinality    = cardinality;
        this.idRight        = idRight;
        this.name           = name;
    }
    
    /**
     * @param type Relation type as UPA, UPC, EUR, ULS, UCR
     * @param id Id of left term in given relation
     */
    public Relation( final String type, final String id ){
        this( type, id, null, "", "" );
    }
    
    /**
     * @param type Relation type as UPA, UPC, EUR, ULS, UCR
     * @param id Id of left term in given relation
     * @param cardinality Cardinality between term left and right
     */
    public Relation( final String type, final String id, final Cardinality cardinality ){
        this( type, id, cardinality, "", "" );
    }
    
    /**
     * @param type Relation type as UPA, UPC, EUR, ULS, UCR
     * @param id Id of left term in given relation
     * @param name Relation name
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

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (idLeft != null ? idLeft.hashCode() : 0);
        result = 31 * result + (idRight != null ? idRight.hashCode() : 0);
        result = 31 * result + (cardinality != null ? cardinality.hashCode() : 0);
        return result;
    }
}
