/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package dispatch.server.thrift.backend;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-05-18")
public class Tracker implements org.apache.thrift.TBase<Tracker, Tracker._Fields>, java.io.Serializable, Cloneable, Comparable<Tracker> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Tracker");

  private static final org.apache.thrift.protocol.TField VENDOR_FIELD_DESC = new org.apache.thrift.protocol.TField("vendor", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField MODEL_FIELD_DESC = new org.apache.thrift.protocol.TField("model", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField IDENTIFIER_FIELD_DESC = new org.apache.thrift.protocol.TField("identifier", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField PHONE_NUMBER_FIELD_DESC = new org.apache.thrift.protocol.TField("phoneNumber", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TrackerStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TrackerTupleSchemeFactory();

  public java.lang.String vendor; // required
  public java.lang.String model; // required
  public java.util.List<java.lang.String> identifier; // required
  public java.lang.String phoneNumber; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    VENDOR((short)1, "vendor"),
    MODEL((short)2, "model"),
    IDENTIFIER((short)3, "identifier"),
    PHONE_NUMBER((short)4, "phoneNumber");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // VENDOR
          return VENDOR;
        case 2: // MODEL
          return MODEL;
        case 3: // IDENTIFIER
          return IDENTIFIER;
        case 4: // PHONE_NUMBER
          return PHONE_NUMBER;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.PHONE_NUMBER};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.VENDOR, new org.apache.thrift.meta_data.FieldMetaData("vendor", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MODEL, new org.apache.thrift.meta_data.FieldMetaData("model", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IDENTIFIER, new org.apache.thrift.meta_data.FieldMetaData("identifier", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.PHONE_NUMBER, new org.apache.thrift.meta_data.FieldMetaData("phoneNumber", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Tracker.class, metaDataMap);
  }

  public Tracker() {
  }

  public Tracker(
    java.lang.String vendor,
    java.lang.String model,
    java.util.List<java.lang.String> identifier)
  {
    this();
    this.vendor = vendor;
    this.model = model;
    this.identifier = identifier;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Tracker(Tracker other) {
    if (other.isSetVendor()) {
      this.vendor = other.vendor;
    }
    if (other.isSetModel()) {
      this.model = other.model;
    }
    if (other.isSetIdentifier()) {
      java.util.List<java.lang.String> __this__identifier = new java.util.ArrayList<java.lang.String>(other.identifier);
      this.identifier = __this__identifier;
    }
    if (other.isSetPhoneNumber()) {
      this.phoneNumber = other.phoneNumber;
    }
  }

  public Tracker deepCopy() {
    return new Tracker(this);
  }

  @Override
  public void clear() {
    this.vendor = null;
    this.model = null;
    this.identifier = null;
    this.phoneNumber = null;
  }

  public java.lang.String getVendor() {
    return this.vendor;
  }

  public Tracker setVendor(java.lang.String vendor) {
    this.vendor = vendor;
    return this;
  }

  public void unsetVendor() {
    this.vendor = null;
  }

  /** Returns true if field vendor is set (has been assigned a value) and false otherwise */
  public boolean isSetVendor() {
    return this.vendor != null;
  }

  public void setVendorIsSet(boolean value) {
    if (!value) {
      this.vendor = null;
    }
  }

  public java.lang.String getModel() {
    return this.model;
  }

  public Tracker setModel(java.lang.String model) {
    this.model = model;
    return this;
  }

  public void unsetModel() {
    this.model = null;
  }

  /** Returns true if field model is set (has been assigned a value) and false otherwise */
  public boolean isSetModel() {
    return this.model != null;
  }

  public void setModelIsSet(boolean value) {
    if (!value) {
      this.model = null;
    }
  }

  public int getIdentifierSize() {
    return (this.identifier == null) ? 0 : this.identifier.size();
  }

  public java.util.Iterator<java.lang.String> getIdentifierIterator() {
    return (this.identifier == null) ? null : this.identifier.iterator();
  }

  public void addToIdentifier(java.lang.String elem) {
    if (this.identifier == null) {
      this.identifier = new java.util.ArrayList<java.lang.String>();
    }
    this.identifier.add(elem);
  }

  public java.util.List<java.lang.String> getIdentifier() {
    return this.identifier;
  }

  public Tracker setIdentifier(java.util.List<java.lang.String> identifier) {
    this.identifier = identifier;
    return this;
  }

  public void unsetIdentifier() {
    this.identifier = null;
  }

  /** Returns true if field identifier is set (has been assigned a value) and false otherwise */
  public boolean isSetIdentifier() {
    return this.identifier != null;
  }

  public void setIdentifierIsSet(boolean value) {
    if (!value) {
      this.identifier = null;
    }
  }

  public java.lang.String getPhoneNumber() {
    return this.phoneNumber;
  }

  public Tracker setPhoneNumber(java.lang.String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public void unsetPhoneNumber() {
    this.phoneNumber = null;
  }

  /** Returns true if field phoneNumber is set (has been assigned a value) and false otherwise */
  public boolean isSetPhoneNumber() {
    return this.phoneNumber != null;
  }

  public void setPhoneNumberIsSet(boolean value) {
    if (!value) {
      this.phoneNumber = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case VENDOR:
      if (value == null) {
        unsetVendor();
      } else {
        setVendor((java.lang.String)value);
      }
      break;

    case MODEL:
      if (value == null) {
        unsetModel();
      } else {
        setModel((java.lang.String)value);
      }
      break;

    case IDENTIFIER:
      if (value == null) {
        unsetIdentifier();
      } else {
        setIdentifier((java.util.List<java.lang.String>)value);
      }
      break;

    case PHONE_NUMBER:
      if (value == null) {
        unsetPhoneNumber();
      } else {
        setPhoneNumber((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case VENDOR:
      return getVendor();

    case MODEL:
      return getModel();

    case IDENTIFIER:
      return getIdentifier();

    case PHONE_NUMBER:
      return getPhoneNumber();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case VENDOR:
      return isSetVendor();
    case MODEL:
      return isSetModel();
    case IDENTIFIER:
      return isSetIdentifier();
    case PHONE_NUMBER:
      return isSetPhoneNumber();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof Tracker)
      return this.equals((Tracker)that);
    return false;
  }

  public boolean equals(Tracker that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_vendor = true && this.isSetVendor();
    boolean that_present_vendor = true && that.isSetVendor();
    if (this_present_vendor || that_present_vendor) {
      if (!(this_present_vendor && that_present_vendor))
        return false;
      if (!this.vendor.equals(that.vendor))
        return false;
    }

    boolean this_present_model = true && this.isSetModel();
    boolean that_present_model = true && that.isSetModel();
    if (this_present_model || that_present_model) {
      if (!(this_present_model && that_present_model))
        return false;
      if (!this.model.equals(that.model))
        return false;
    }

    boolean this_present_identifier = true && this.isSetIdentifier();
    boolean that_present_identifier = true && that.isSetIdentifier();
    if (this_present_identifier || that_present_identifier) {
      if (!(this_present_identifier && that_present_identifier))
        return false;
      if (!this.identifier.equals(that.identifier))
        return false;
    }

    boolean this_present_phoneNumber = true && this.isSetPhoneNumber();
    boolean that_present_phoneNumber = true && that.isSetPhoneNumber();
    if (this_present_phoneNumber || that_present_phoneNumber) {
      if (!(this_present_phoneNumber && that_present_phoneNumber))
        return false;
      if (!this.phoneNumber.equals(that.phoneNumber))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetVendor()) ? 131071 : 524287);
    if (isSetVendor())
      hashCode = hashCode * 8191 + vendor.hashCode();

    hashCode = hashCode * 8191 + ((isSetModel()) ? 131071 : 524287);
    if (isSetModel())
      hashCode = hashCode * 8191 + model.hashCode();

    hashCode = hashCode * 8191 + ((isSetIdentifier()) ? 131071 : 524287);
    if (isSetIdentifier())
      hashCode = hashCode * 8191 + identifier.hashCode();

    hashCode = hashCode * 8191 + ((isSetPhoneNumber()) ? 131071 : 524287);
    if (isSetPhoneNumber())
      hashCode = hashCode * 8191 + phoneNumber.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(Tracker other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetVendor()).compareTo(other.isSetVendor());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVendor()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vendor, other.vendor);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetModel()).compareTo(other.isSetModel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetModel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.model, other.model);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIdentifier()).compareTo(other.isSetIdentifier());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIdentifier()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.identifier, other.identifier);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPhoneNumber()).compareTo(other.isSetPhoneNumber());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPhoneNumber()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.phoneNumber, other.phoneNumber);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("Tracker(");
    boolean first = true;

    sb.append("vendor:");
    if (this.vendor == null) {
      sb.append("null");
    } else {
      sb.append(this.vendor);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("model:");
    if (this.model == null) {
      sb.append("null");
    } else {
      sb.append(this.model);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("identifier:");
    if (this.identifier == null) {
      sb.append("null");
    } else {
      sb.append(this.identifier);
    }
    first = false;
    if (isSetPhoneNumber()) {
      if (!first) sb.append(", ");
      sb.append("phoneNumber:");
      if (this.phoneNumber == null) {
        sb.append("null");
      } else {
        sb.append(this.phoneNumber);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TrackerStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TrackerStandardScheme getScheme() {
      return new TrackerStandardScheme();
    }
  }

  private static class TrackerStandardScheme extends org.apache.thrift.scheme.StandardScheme<Tracker> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Tracker struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // VENDOR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.vendor = iprot.readString();
              struct.setVendorIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MODEL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.model = iprot.readString();
              struct.setModelIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // IDENTIFIER
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.identifier = new java.util.ArrayList<java.lang.String>(_list8.size);
                java.lang.String _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = iprot.readString();
                  struct.identifier.add(_elem9);
                }
                iprot.readListEnd();
              }
              struct.setIdentifierIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PHONE_NUMBER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.phoneNumber = iprot.readString();
              struct.setPhoneNumberIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Tracker struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.vendor != null) {
        oprot.writeFieldBegin(VENDOR_FIELD_DESC);
        oprot.writeString(struct.vendor);
        oprot.writeFieldEnd();
      }
      if (struct.model != null) {
        oprot.writeFieldBegin(MODEL_FIELD_DESC);
        oprot.writeString(struct.model);
        oprot.writeFieldEnd();
      }
      if (struct.identifier != null) {
        oprot.writeFieldBegin(IDENTIFIER_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.identifier.size()));
          for (java.lang.String _iter11 : struct.identifier)
          {
            oprot.writeString(_iter11);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.phoneNumber != null) {
        if (struct.isSetPhoneNumber()) {
          oprot.writeFieldBegin(PHONE_NUMBER_FIELD_DESC);
          oprot.writeString(struct.phoneNumber);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TrackerTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TrackerTupleScheme getScheme() {
      return new TrackerTupleScheme();
    }
  }

  private static class TrackerTupleScheme extends org.apache.thrift.scheme.TupleScheme<Tracker> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Tracker struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetVendor()) {
        optionals.set(0);
      }
      if (struct.isSetModel()) {
        optionals.set(1);
      }
      if (struct.isSetIdentifier()) {
        optionals.set(2);
      }
      if (struct.isSetPhoneNumber()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetVendor()) {
        oprot.writeString(struct.vendor);
      }
      if (struct.isSetModel()) {
        oprot.writeString(struct.model);
      }
      if (struct.isSetIdentifier()) {
        {
          oprot.writeI32(struct.identifier.size());
          for (java.lang.String _iter12 : struct.identifier)
          {
            oprot.writeString(_iter12);
          }
        }
      }
      if (struct.isSetPhoneNumber()) {
        oprot.writeString(struct.phoneNumber);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Tracker struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.vendor = iprot.readString();
        struct.setVendorIsSet(true);
      }
      if (incoming.get(1)) {
        struct.model = iprot.readString();
        struct.setModelIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.identifier = new java.util.ArrayList<java.lang.String>(_list13.size);
          java.lang.String _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = iprot.readString();
            struct.identifier.add(_elem14);
          }
        }
        struct.setIdentifierIsSet(true);
      }
      if (incoming.get(3)) {
        struct.phoneNumber = iprot.readString();
        struct.setPhoneNumberIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

