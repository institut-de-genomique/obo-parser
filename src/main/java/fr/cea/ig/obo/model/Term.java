package fr.cea.ig.obo.model;

public abstract class Term {

    private final String id;
    private final String name;
    private final String definition;

    /**
     * @param id
     * @param name
     * @param definition
     */
    public Term(final String id, final String name, final String definition) {
        this.id = id;
        this.name = name;
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
        
        if ("fr.cea.labgem.obo.model.UCR".equals(className))
            result= "reaction";
        else if ("fr.cea.labgem.obo.model.UER".equals(className))
            result= "enzymatic_reaction";
        else if ("fr.cea.labgem.obo.model.ULS".equals(className))
            result= "linear_sub_pathway";
        else if ("fr.cea.labgem.obo.model.UPA".equals(className))
            result= "pathway";
        else if ("fr.cea.labgem.obo.model.UPC".equals(className))
            result= "compound";
        
        return result;
    }
}
