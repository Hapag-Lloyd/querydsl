/*
 * Copyright (c) 2010 Mysema Ltd.
 * All rights reserved.
 *
 */
package com.mysema.query.alias;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;

import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Expression;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadataFactory;
import com.mysema.query.types.expr.SimpleExpression;
import com.mysema.query.types.path.*;

/**
 * Alias provides alias factory methods
 *
 * @author tiwe
 */
@SuppressWarnings("PMD")
public final class Alias {

    private static final AliasFactory aliasFactory = new AliasFactory(new DefaultPathFactory(), new DefaultTypeSystem());

    private static final SimplePath<Object> it = new SimplePath<Object>(Object.class, PathMetadataFactory.forVariable("it"));

    // exclude $-methods from Checkstyle checks
    //CHECKSTYLE:OFF
    /**
     * Convert the given alias to an expression
     *
     * @param <D>
     * @return
     */
    public static <D extends Expression<?>> D $() {
        return aliasFactory.<D>getCurrentAndReset();
    }

    public static <D> ArrayPath<D> $(D[] arg){
    return aliasFactory.<ArrayPath<D>> getCurrentAndReset();
    }

    public static NumberPath<BigDecimal> $(BigDecimal arg) {
        return aliasFactory.<NumberPath<BigDecimal>> getCurrentAndReset();
    }

    public static NumberPath<BigInteger> $(BigInteger arg) {
        return aliasFactory.<NumberPath<BigInteger>> getCurrentAndReset();
    }

    public static BooleanPath $(Boolean arg) {
        return aliasFactory.<BooleanPath> getCurrentAndReset();
    }

    public static NumberPath<Byte> $(Byte arg) {
        return aliasFactory.<NumberPath<Byte>> getCurrentAndReset();
    }
    
    public static <T extends Enum<T>> EnumPath<T> $(T arg){
        return aliasFactory.<EnumPath<T>> getCurrentAndReset();
    }

    public static <D, Q extends SimpleExpression<D>> CollectionPath<D, Q> $(Collection<D> args) {
        return aliasFactory.<CollectionPath<D,Q>> getCurrentAndReset();
    }

    public static <D extends Comparable<?>> ComparablePath<D> $(D arg) {
//        return aliasFactory.<ComparablePath<D>> getCurrentAndReset();
        return Alias.<D, ComparablePath<D>>getPath(arg);
    }

    public static NumberPath<Double> $(Double arg) {
        return aliasFactory.<NumberPath<Double>> getCurrentAndReset();
    }

    public static NumberPath<Float> $(Float arg) {
        return aliasFactory.<NumberPath<Float>> getCurrentAndReset();
    }

    public static NumberPath<Integer> $(Integer arg) {
        return aliasFactory.<NumberPath<Integer>> getCurrentAndReset();
    }

    public static DatePath<java.sql.Date> $(java.sql.Date arg) {
        return aliasFactory.<DatePath<java.sql.Date>> getCurrentAndReset();
    }

    public static DateTimePath<java.util.Date> $(java.util.Date arg) {
        return aliasFactory.<DateTimePath<java.util.Date>> getCurrentAndReset();
    }

    public static <D, Q extends SimpleExpression<D>> ListPath<D,Q> $(List<D> args) {
        return aliasFactory.<ListPath<D,Q>> getCurrentAndReset();
    }

    public static NumberPath<Long> $(Long arg) {
        return aliasFactory.<NumberPath<Long>> getCurrentAndReset();
    }

    public static <K, V, Q extends SimpleExpression<V>> MapPath<K, V, Q> $(Map<K, V> args) {
        return aliasFactory.<MapPath<K, V, Q>> getCurrentAndReset();
    }

    public static <D, Q extends SimpleExpression<D>> SetPath<D, Q> $(Set<D> args) {
        return aliasFactory.<SetPath<D, Q>> getCurrentAndReset();
    }

    public static NumberPath<Short> $(Short arg) {
        return aliasFactory.<NumberPath<Short>> getCurrentAndReset();
    }

    public static StringPath $(String arg) {
        return aliasFactory.<StringPath> getCurrentAndReset();
    }

    public static TimePath<Time> $(Time arg) {
        return aliasFactory.<TimePath<Time>> getCurrentAndReset();
    }

    public static DateTimePath<Timestamp> $(Timestamp arg) {
        return aliasFactory.<DateTimePath<Timestamp>> getCurrentAndReset();
    }
    
    @SuppressWarnings("unchecked")
    @Nullable
    public static <D> EntityPathBase<D> $(D arg) {
        EntityPathBase<D> rv = aliasFactory.<EntityPathBase<D>> getCurrentAndReset();
        if (rv != null) {
            return rv;
        }else if (arg instanceof EntityPath<?>){    
            return (EntityPathBase<D>)arg;
        } else if (arg instanceof ManagedObject) {
            return (EntityPathBase<D>) ((ManagedObject) arg).__mappedPath();
        } else {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Nullable
    private static <D, P extends Path<D>> P getPath(D arg) {
        P rv = aliasFactory.<P>getCurrentAndReset();
        if (rv != null) {
            return rv;
        }else if (arg instanceof Path<?>){    
            return (P)arg;
        } else if (arg instanceof ManagedObject) {
            return (P) ((ManagedObject) arg).__mappedPath();
        } else {
            return null;
        }
    }
    
    //CHECKSTYLE:ON

    public static <A> A alias(Class<A> cl) {
        return alias(cl, StringUtils.uncapitalize(cl.getSimpleName()));
    }

    public static <A> A alias(Class<A> cl, Expression<? extends A> expr) {
        return aliasFactory.createAliasForExpr(cl, expr);
    }

    public static <A> A alias(Class<A> cl, String var) {
        return aliasFactory.createAliasForVariable(cl, var);
    }

    /**
     * Convert the given alias to an expression
     *
     * @param <D>
     * @param arg
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <D> Expression<D> getAny(D arg) {
        Expression<D> current = (Expression<D>) aliasFactory.getCurrentAndReset();
        if (current != null) {
            return current;
        } else if (arg instanceof ManagedObject) {
            return (Expression<D>) ((ManagedObject) arg).__mappedPath();
        } else {
            throw new IllegalArgumentException("No path mapped to " + arg);
        }
    }

    public static void resetAlias() {
        aliasFactory.reset();
    }

    @SuppressWarnings("unchecked")
    public static <D> SimplePath<D> var(){
        return (SimplePath<D>) it;
    }

    @SuppressWarnings("unchecked")
    public static <D extends Comparable<?>> ComparablePath<D> var(D arg) {
        return new ComparablePath(arg.getClass(), "var"+arg);
    }

    @SuppressWarnings("unchecked")
    public static <D extends Number & Comparable<D>> NumberPath<D> var(D arg) {
        return new NumberPath(arg.getClass(), "var" + arg.getClass().getSimpleName()+arg);
    }

    @SuppressWarnings("unchecked")
    public static <D> EntityPathBase<D> var(D arg) {
        String var = "var"+ arg.getClass().getSimpleName() + "_" + arg.toString().replace(' ', '_');
        return new PathBuilder(arg.getClass(), var);
    }

    public static StringPath var(String arg) {
        return new StringPath(arg.replace(' ', '_'));
    }

    private Alias(){}

}
