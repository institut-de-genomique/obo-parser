package fr.cea.ig.io.model.obo;

public class Cardinality {
    private final String    number;
    private final String    order;
    private final Boolean   is_primary;
    private final Boolean   is_alternate;
    private final String    direction;
    
    /**
     * @param number
     * @param order
     * @param direction
     * @param is_primary
     * @param is_alternate
     */
    public Cardinality( final String number, final String order, final String direction, final Boolean is_primary, Boolean is_alternate ){
        this.number         = number;
        this.order          = order;
        this.direction      = direction;
        this.is_primary     = is_primary;
        this.is_alternate   = is_alternate;
    }
    
    /**
     * @param number
     */
    public Cardinality( final String number ){
        this( number, "", "", false, false );
    }
    
    /**
     * @param number
     * @param order
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
    public boolean equals(Object obj){
        boolean result = false;
        if (obj == this)
            result = true;
        else if( obj == null || obj.getClass() != this.getClass() )
            result = false;
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
}
