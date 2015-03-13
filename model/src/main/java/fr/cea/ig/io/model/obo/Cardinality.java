package fr.cea.ig.io.model.obo;

/*
 *
 */
/*
 * @startuml
 * class Cardinality{
 *  - number                        : String
 *  - order                         : String
 *  - is_primary                    : Boolean
 *  - is_alternate                  : Boolean
 *  - direction                     : String
 *  + Cardinality( final String number, final String order, final String direction, final Boolean is_primary, Boolean is_alternate )
 *  + Cardinality( @NotNull final String number )
 *  + Cardinality( final String number, final String order )
 *  + getDirection()                : String
 *  + getNumber()                   : String
 *  + getOrder()                    : String
 *  + getIs_primary()               : Boolean
 *  + getIs_alternate()             : Boolean
 *  + toString()                    : String
 *  + equals( final Object obj )    : boolean
 *  + hashCode()                    : int
 * }
 * @enduml
 */
public class Cardinality {
    private final String    number;
    private final String    order;
    private final Boolean   is_primary;
    private final Boolean   is_alternate;
    private final String    direction;
    
    /**
     * @param number number of relationships of a given type that may be defined for instances of this term
     * @param order term position into the relation described
     * @param direction Left to Right (LR), Right to Left (RL)
     * @param is_primary describes compound is primary or not
     * @param is_alternate term can to be switched by another
     */
    public Cardinality( final String number, final String order, final String direction, final Boolean is_primary, Boolean is_alternate ){
        this.number         = number;
        this.order          = order;
        this.direction      = direction;
        this.is_primary     = is_primary;
        this.is_alternate   = is_alternate;
    }
    
    /**
     * @param number number of relationships of a given type that may be defined for instances of this term
     */
    public Cardinality( final String number ){
        this( number, "", "", false, false );
    }
    
    /**
     * @param number number of relationships of a given type that may be defined for instances of this term
     * @param order term position into the relation described
     */
    public Cardinality( final String number, final String order ){
        this( number, order, "", false, false );
    }

    public String getDirection() {
        return direction;
    }

    public String getNumber() {
        return number;
    }
    
    public String getOrder() {
        return order;
    }

    public Boolean getIs_primary() {
        return is_primary;
    }

    public Boolean getIs_alternate() {
        return is_alternate;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder( "{cardinality=\"" + number +"\"" );
        if( order != null && ! order.isEmpty() )
            result.append(",order=\"" + order + "\"" );
        if( is_primary != null && is_primary == true )
            result.append( ",is_primary=\"True\"" );
        if( is_alternate != null && is_alternate == true )
            result.append( ",is_alternate=\"True\"" );
        result.append("}");
        return result.toString();
    }
    
    @Override
    public boolean equals( final Object obj){
        boolean result = false;
        if( obj == null || obj.getClass() != this.getClass() )
            result = false;
        else if (obj == this)
            result = true;
        else {
            Cardinality cardinality = (Cardinality) obj;
            if( cardinality.getDirection().equals(direction)        &&
                cardinality.getNumber().equals(number)              &&
                cardinality.getOrder().equals(order)                &&
                cardinality.getIs_primary().equals(is_primary)      &&
                cardinality.getIs_alternate().equals(is_alternate)  )
                result = true;
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (is_primary != null ? is_primary.hashCode() : 0);
        result = 31 * result + (is_alternate != null ? is_alternate.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }
}
