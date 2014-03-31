package fr.cea.labgem.obo.model;

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
        this( number, null, null, null, null );
    }
    
    /**
     * @param number
     * @param order
     */
    public Cardinality( final String number, final String order ){
        this( number, order, null, null, null );
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
        if( order != null )
            result.append("order=\"" + order + "\"" );
        if( is_primary != null )
            result.append("is_primary=\"" + is_primary + "\"" );
        if( is_alternate != null )
            result.append("is_alternate=\"" + is_alternate + "\"" );
        result.append("}");
        return result.toString();
    }
}
