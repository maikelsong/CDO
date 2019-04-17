/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.4.1</a>, using an XML
 * Schema.
 * $Id$
 */

package com.cdoframework.cdolib.database.xsd;

/**
 * 
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class BlockType implements java.io.Serializable {

    private java.util.List<com.cdoframework.cdolib.database.xsd.BlockTypeItem> _items;

    public BlockType() {
        super();
        this._items = new java.util.ArrayList<com.cdoframework.cdolib.database.xsd.BlockTypeItem>();
    }

    /**
     * 
     * 
     * @param vBlockTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addBlockTypeItem(final com.cdoframework.cdolib.database.xsd.BlockTypeItem vBlockTypeItem) throws java.lang.IndexOutOfBoundsException {
        this._items.add(vBlockTypeItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vBlockTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addBlockTypeItem(final int index,final com.cdoframework.cdolib.database.xsd.BlockTypeItem vBlockTypeItem) throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vBlockTypeItem);
    }

    /**
     * Method enumerateBlockTypeItem.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.cdoframework.cdolib.database.xsd.BlockTypeItem> enumerateBlockTypeItem() {
        return java.util.Collections.enumeration(this._items);
    }

    /**
     * Method getBlockTypeItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.cdoframework.cdolib.database.xsd.BlockTypeItem at the
     * given index
     */
    public com.cdoframework.cdolib.database.xsd.BlockTypeItem getBlockTypeItem(final int index) throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getBlockTypeItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return _items.get(index);
    }

    /**
     * Method getBlockTypeItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.cdoframework.cdolib.database.xsd.BlockTypeItem[] getBlockTypeItem() {
        com.cdoframework.cdolib.database.xsd.BlockTypeItem[] array = new com.cdoframework.cdolib.database.xsd.BlockTypeItem[0];
        return this._items.toArray(array);
    }

    /**
     * Method getBlockTypeItemCount.
     * 
     * @return the size of this collection
     */
    public int getBlockTypeItemCount() {
        return this._items.size();
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid() {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * Method iterateBlockTypeItem.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.cdoframework.cdolib.database.xsd.BlockTypeItem> iterateBlockTypeItem() {
        return this._items.iterator();
    }

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(final java.io.Writer out) throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, out);
    }

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(final org.xml.sax.ContentHandler handler) throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, handler);
    }

    /**
     */
    public void removeAllBlockTypeItem() {
        this._items.clear();
    }

    /**
     * Method removeBlockTypeItem.
     * 
     * @param vBlockTypeItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeBlockTypeItem(final com.cdoframework.cdolib.database.xsd.BlockTypeItem vBlockTypeItem) {
        boolean removed = _items.remove(vBlockTypeItem);
        return removed;
    }

    /**
     * Method removeBlockTypeItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.cdoframework.cdolib.database.xsd.BlockTypeItem removeBlockTypeItemAt(final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (com.cdoframework.cdolib.database.xsd.BlockTypeItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vBlockTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setBlockTypeItem(final int index,final com.cdoframework.cdolib.database.xsd.BlockTypeItem vBlockTypeItem) throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setBlockTypeItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vBlockTypeItem);
    }

    /**
     * 
     * 
     * @param vBlockTypeItemArray
     */
    public void setBlockTypeItem(final com.cdoframework.cdolib.database.xsd.BlockTypeItem[] vBlockTypeItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vBlockTypeItemArray.length; i++) {
                this._items.add(vBlockTypeItemArray[i]);
        }
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * com.cdoframework.cdolib.database.xsd.BlockType
     */
    public static com.cdoframework.cdolib.database.xsd.BlockType unmarshal(final java.io.Reader reader) throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.cdoframework.cdolib.database.xsd.BlockType) org.exolab.castor.xml.Unmarshaller.unmarshal(com.cdoframework.cdolib.database.xsd.BlockType.class, reader);
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate() throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}
