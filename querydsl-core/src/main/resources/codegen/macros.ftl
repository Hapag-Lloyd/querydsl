<#assign reserved = ["isnull", "isnotnull", "getType", "getMetadata", "toString", "hashCode", "getClass", "notify", "notifyAll", "wait"]>

<#macro classContent decl embeddable>    
    <#if !embeddable>
    public static final ${pre}${decl.simpleName} ${decl.uncapSimpleName} = new ${pre}${decl.simpleName}("${decl.uncapSimpleName}");
    </#if>
    <#list decl.stringFields as field>
        <@stringField field=field/>
    </#list>    
    <#list decl.booleanFields as field>
        <@booleanField field=field/>    
    </#list>    
    <#list decl.simpleFields as field>
        <@simpleField field=field/>
    </#list>
    <#list decl.comparableFields as field>
        <@comparableField field=field/>
    </#list>
    <#list decl.dateFields as field>
        <@dateField field=field/>
    </#list>
    <#list decl.dateTimeFields as field>
        <@dateTimeField field=field/>
    </#list>
    <#list decl.timeFields as field>
        <@timeField field=field/>
    </#list>    
    <#list decl.numericFields as field>
        <@numericField field=field/>
    </#list>
    <#list decl.simpleMaps as field>
        <@simpleMap field=field/>
       </#list>  
    <#list decl.simpleCollections as field>
        <@simpleCollection field=field/>
    </#list>  
    <#list decl.simpleLists as field>
        <@simpleList field=field/>
    </#list>                
    <#list decl.entityMaps as field>
        <@entityMap field=field/>
    </#list> 
    <#list decl.entityCollections as field>
        <@entityCollection field=field/>
    </#list>
    <#list decl.entityLists as field>
        <@entityList field=field/>
    </#list>     
      <#list decl.entityFields as field>
        <@entityField field=field/>
    </#list>
    
  <#-- constructors -->           
    <#if !embeddable>
    public ${pre}${decl.simpleName}(@NotEmpty String path) {
          this(${decl.name}.class, path);        
    }
    public ${pre}${decl.simpleName}(Class<? extends ${decl.name}> cl, @NotEmpty String path) {
          super(cl, "${decl.simpleName}", path);
    <#list decl.entityFields as field>
          <#if !reserved?seq_contains(field.name)>
          _${field.name}();
          </#if>    
    </#list>     
    }    
    </#if>     
    public ${pre}${decl.simpleName}(PathMetadata<?> metadata) {
         super(${decl.name}.class, "${decl.simpleName}", metadata);
    }
</#macro>   

<#macro booleanField field>
    /** ${field.docString} */
    public final PBoolean ${field.name} = _boolean("${field.name}");
</#macro>

<#macro comparableField field>
    /** ${field.docString} */
    public final PComparable<${field.typeName}> ${field.name} = _comparable("${field.name}",${field.typeName}.class);
</#macro>

<#macro dateField field>
    /** ${field.docString} */
    public final PDate<${field.typeName}> ${field.name} = _date("${field.name}",${field.typeName}.class);
</#macro>

<#macro dateTimeField field>
    /** ${field.docString} */
    public final PDateTime<${field.typeName}> ${field.name} = _dateTime("${field.name}",${field.typeName}.class);
</#macro>

<#macro timeField field>
    /** ${field.docString} */
    public final PTime<${field.typeName}> ${field.name} = _time("${field.name}",${field.typeName}.class);
</#macro>

<#macro entityCollection field>
    /** ${field.docString} */
    public final PEntityCollection<${field.typeName}> ${field.name} = _entitycol("${field.name}",${field.typeName}.class, "${field.simpleTypeName}");
</#macro>

<#macro entityField field>
    /** ${field.docString} */
    public ${field.typePackage}.${pre}${field.simpleTypeName} ${field.name};
    <#if !reserved?seq_contains(field.name)>
    public ${field.typePackage}.${pre}${field.simpleTypeName} _${field.name}() {
         if (${field.name} == null) ${field.name} = new ${field.typePackage}.${pre}${field.simpleTypeName}(PathMetadata.forProperty(this,"${field.name}"));
         return ${field.name};
    }
    </#if>
</#macro>

<#macro entityList field>
    /** ${field.docString} */
    public final PEntityList<${field.typeName}> ${field.name} = _entitylist("${field.name}",${field.typeName}.class,"${field.simpleTypeName}");
    public ${field.typePackage}.${pre}${field.simpleTypeName} ${field.name}(int index) {
        return new ${field.typePackage}.${pre}${field.simpleTypeName}(PathMetadata.forListAccess(${field.name},index));
    }
    public ${field.typePackage}.${pre}${field.simpleTypeName} ${field.name}(com.mysema.query.types.expr.Expr<Integer> index) {
        return new ${field.typePackage}.${pre}${field.simpleTypeName}(PathMetadata.forListAccess(${field.name},index));
    }
</#macro>

<#macro entityMap field>
    /** ${field.docString} */
    public final PEntityMap<${field.keyTypeName},${field.typeName}> ${field.name} = _entitymap("${field.name}",${field.keyTypeName}.class,${field.typeName}.class,"${field.simpleTypeName}");
    public ${field.typePackage}.${pre}${field.simpleTypeName} ${field.name}(${field.keyTypeName} key) {
        return new ${field.typePackage}.${pre}${field.simpleTypeName}(PathMetadata.forMapAccess(${field.name},key));
    }
    public ${field.typePackage}.${pre}${field.simpleTypeName} ${field.name}(com.mysema.query.types.expr.Expr<${field.keyTypeName}> key) {
        return new ${field.typePackage}.${pre}${field.simpleTypeName}(PathMetadata.forMapAccess(${field.name},key));
    }
</#macro>
     
<#macro numericField field>
    /** ${field.docString} */
    public final PNumber<${field.typeName}> ${field.name} = _number("${field.name}",${field.typeName}.class);
</#macro>
         
<#macro simpleField field>
    /** ${field.docString} */
    public final PSimple<${field.typeName}> ${field.name} = _simple("${field.name}",${field.typeName}.class);
</#macro>
     
<#macro simpleMap field>
    /** ${field.docString} */
    public final PComponentMap<${field.keyTypeName},${field.typeName}> ${field.name} = _simplemap("${field.name}",${field.keyTypeName}.class,${field.typeName}.class);
    public PSimple<${field.typeName}> ${field.name}(${field.keyTypeName} key) {
        return new PSimple<${field.typeName}>(${field.typeName}.class,PathMetadata.forMapAccess(${field.name},key));
    }
    public PSimple<${field.typeName}> ${field.name}(com.mysema.query.types.expr.Expr<${field.keyTypeName}> key) {
        return new PSimple<${field.typeName}>(${field.typeName}.class,PathMetadata.forMapAccess(${field.name},key));
    }
</#macro>     

<#macro simpleCollection field>
    /** ${field.docString} */
    public final PComponentCollection<${field.typeName}> ${field.name} = _simplecol("${field.name}",${field.typeName}.class);
</#macro>

<#macro simpleList field>
    /** ${field.docString} */
    public final PComponentList<${field.typeName}> ${field.name} = _simplelist("${field.name}",${field.typeName}.class);
    public PSimple<${field.typeName}> ${field.name}(int index) {
        return new PSimple<${field.typeName}>(${field.typeName}.class,PathMetadata.forListAccess(${field.name},index));
    }
    public PSimple<${field.typeName}> ${field.name}(com.mysema.query.types.expr.Expr<Integer> index) {
        return new PSimple<${field.typeName}>(${field.typeName}.class,PathMetadata.forListAccess(${field.name},index));
    }
</#macro>

<#macro stringField field>
    /** ${field.docString} */
    public final PString ${field.name} = _string("${field.name}");
</#macro>


