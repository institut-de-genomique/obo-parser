package fr.cea.ig.io.model.obo;

import javax.validation.constraints.NotNull;

/*
 *
 */
/*
 * @startuml
 * abstract class Term {
 *  - id                : String
 *  - name              : String
 *  - definition        : String
 *  + Term(@NotNull final String id, @NotNull final String name, @NotNull final String definition)
 *  + getId()           : String
 *  + getName()         : String
 *  + getDefinition()   : String
 *  + toString()        : String
 * }
 * @enduml
 */
public abstract class Term {

    protected final String id;
    protected final String name;
    protected final String definition;

    /**
     * @param id Term id
     * @param name Term name
     * @param definition Term description
     */
    public Term(@NotNull final String id, @NotNull final String name, @NotNull final String definition) {
        this.id         = id;
        this.name       = name;
        this.definition = definition;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDefinition() {
        return definition;
    }

    @Override
    public String toString() {
        return  "[Term]"                    + "\n"  +
                "id: UPa:"  + id            + "\n" +
                "name: "    + name          + "\n" +
                "def: "     + definition;
    }

    public String getNameSpace() { //(final Class<? extends Term> cls
        String result = null;
        final String className = this.getClass().getCanonicalName();
        
        if ("fr.cea.ig.obo.model.UCR".equals(className))
            result= "reaction";
        else if ("fr.cea.ig.obo.model.UER".equals(className))
            result= "enzymatic_reaction";
        else if ("fr.cea.ig.obo.model.ULS".equals(className))
            result= "linear_sub_pathway";
        else if ("fr.cea.ig.obo.model.UPA".equals(className))
            result= "pathway";
        else if ("fr.cea.ig.obo.model.UPC".equals(className))
            result= "compound";
        
        return result;
    }
}
