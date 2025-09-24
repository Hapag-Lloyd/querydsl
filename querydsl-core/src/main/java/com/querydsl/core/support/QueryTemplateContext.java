package com.querydsl.core.support;

public final class QueryTemplateContext {

  private static final ThreadLocal<Boolean> USES_QUERY_TEMPLATE =
      ThreadLocal.withInitial(() -> Boolean.FALSE);

  private QueryTemplateContext() {
    // Utility class
  }

  public static void setUsesQueryTemplate(boolean value) {
    USES_QUERY_TEMPLATE.set(value);
  }

  public static boolean isUsesQueryTemplate() {
    return USES_QUERY_TEMPLATE.get();
  }

  public static void clear() {
    USES_QUERY_TEMPLATE.remove();
  }
}
