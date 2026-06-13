package com.turkcell.lyraapp.data.home;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class MockHomeRepository_Factory implements Factory<MockHomeRepository> {
  @Override
  public MockHomeRepository get() {
    return newInstance();
  }

  public static MockHomeRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static MockHomeRepository newInstance() {
    return new MockHomeRepository();
  }

  private static final class InstanceHolder {
    static final MockHomeRepository_Factory INSTANCE = new MockHomeRepository_Factory();
  }
}
