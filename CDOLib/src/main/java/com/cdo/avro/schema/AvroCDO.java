/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.cdo.avro.schema;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class AvroCDO extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 8521175148681024244L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AvroCDO\",\"namespace\":\"com.cdo.avro.schema\",\"fields\":[{\"name\":\"level\",\"type\":\"int\"},{\"name\":\"fields\",\"type\":{\"type\":\"map\",\"values\":\"bytes\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public int level;
  @Deprecated public java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> fields;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public AvroCDO() {}

  /**
   * All-args constructor.
   * @param level The new value for level
   * @param fields The new value for fields
   */
  public AvroCDO(java.lang.Integer level, java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> fields) {
    this.level = level;
    this.fields = fields;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return level;
    case 1: return fields;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: level = (java.lang.Integer)value$; break;
    case 1: fields = (java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'level' field.
   * @return The value of the 'level' field.
   */
  public java.lang.Integer getLevel() {
    return level;
  }

  /**
   * Sets the value of the 'level' field.
   * @param value the value to set.
   */
  public void setLevel(java.lang.Integer value) {
    this.level = value;
  }

  /**
   * Gets the value of the 'fields' field.
   * @return The value of the 'fields' field.
   */
  public java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> getFields() {
    return fields;
  }

  /**
   * Sets the value of the 'fields' field.
   * @param value the value to set.
   */
  public void setFields(java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> value) {
    this.fields = value;
  }

  /**
   * Creates a new AvroCDO RecordBuilder.
   * @return A new AvroCDO RecordBuilder
   */
  public static com.cdo.avro.schema.AvroCDO.Builder newBuilder() {
    return new com.cdo.avro.schema.AvroCDO.Builder();
  }

  /**
   * Creates a new AvroCDO RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new AvroCDO RecordBuilder
   */
  public static com.cdo.avro.schema.AvroCDO.Builder newBuilder(com.cdo.avro.schema.AvroCDO.Builder other) {
    return new com.cdo.avro.schema.AvroCDO.Builder(other);
  }

  /**
   * Creates a new AvroCDO RecordBuilder by copying an existing AvroCDO instance.
   * @param other The existing instance to copy.
   * @return A new AvroCDO RecordBuilder
   */
  public static com.cdo.avro.schema.AvroCDO.Builder newBuilder(com.cdo.avro.schema.AvroCDO other) {
    return new com.cdo.avro.schema.AvroCDO.Builder(other);
  }

  /**
   * RecordBuilder for AvroCDO instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AvroCDO>
    implements org.apache.avro.data.RecordBuilder<AvroCDO> {

    private int level;
    private java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> fields;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.cdo.avro.schema.AvroCDO.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.level)) {
        this.level = data().deepCopy(fields()[0].schema(), other.level);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.fields)) {
        this.fields = data().deepCopy(fields()[1].schema(), other.fields);
        fieldSetFlags()[1] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing AvroCDO instance
     * @param other The existing instance to copy.
     */
    private Builder(com.cdo.avro.schema.AvroCDO other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.level)) {
        this.level = data().deepCopy(fields()[0].schema(), other.level);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.fields)) {
        this.fields = data().deepCopy(fields()[1].schema(), other.fields);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'level' field.
      * @return The value.
      */
    public java.lang.Integer getLevel() {
      return level;
    }

    /**
      * Sets the value of the 'level' field.
      * @param value The value of 'level'.
      * @return This builder.
      */
    public com.cdo.avro.schema.AvroCDO.Builder setLevel(int value) {
      validate(fields()[0], value);
      this.level = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'level' field has been set.
      * @return True if the 'level' field has been set, false otherwise.
      */
    public boolean hasLevel() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'level' field.
      * @return This builder.
      */
    public com.cdo.avro.schema.AvroCDO.Builder clearLevel() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'fields' field.
      * @return The value.
      */
    public java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> getFields() {
      return fields;
    }

    /**
      * Sets the value of the 'fields' field.
      * @param value The value of 'fields'.
      * @return This builder.
      */
    public com.cdo.avro.schema.AvroCDO.Builder setFields(java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> value) {
      validate(fields()[1], value);
      this.fields = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'fields' field has been set.
      * @return True if the 'fields' field has been set, false otherwise.
      */
    public boolean hasFields() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'fields' field.
      * @return This builder.
      */
    public com.cdo.avro.schema.AvroCDO.Builder clearFields() {
      fields = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public AvroCDO build() {
      try {
        AvroCDO record = new AvroCDO();
        record.level = fieldSetFlags()[0] ? this.level : (java.lang.Integer) defaultValue(fields()[0]);
        record.fields = fieldSetFlags()[1] ? this.fields : (java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer>) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
