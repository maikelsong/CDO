/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.cdoframework.cdolib.service.framework.transfilter.xsd;

/**
 * 
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class PostPushCache implements java.io.Serializable {

    /**
     * Field syn.
     */
    private boolean syn = false;

    /**
     * Keeps track of whether primitive field syn has been set
     * already.
     */
    private boolean _hassyn;

    /**
     * Field requestCondition.
     */
    private com.cdoframework.cdolib.service.framework.transfilter.xsd.RequestCondition requestCondition;

    /**
     * Field responseCondition.
     */
    private com.cdoframework.cdolib.service.framework.transfilter.xsd.ResponseCondition responseCondition;

    private java.util.List<com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache> pushCacheList;

    public PostPushCache() {
        super();
        this.pushCacheList = new java.util.ArrayList<com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache>();
    }

    /**
     * 
     * 
     * @param vPushCache
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPushCache(final com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache vPushCache) throws java.lang.IndexOutOfBoundsException {
        this.pushCacheList.add(vPushCache);
    }

    /**
     * 
     * 
     * @param index
     * @param vPushCache
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPushCache(final int index,final com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache vPushCache) throws java.lang.IndexOutOfBoundsException {
        this.pushCacheList.add(index, vPushCache);
    }

    /**
     */
    public void deleteSyn() {
        this._hassyn= false;
    }

    /**
     * Method enumeratePushCache.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache> enumeratePushCache() {
        return java.util.Collections.enumeration(this.pushCacheList);
    }

    /**
     * Method getPushCache.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache
     * at the given index
     */
    public com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache getPushCache(final int index) throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this.pushCacheList.size()) {
            throw new IndexOutOfBoundsException("getPushCache: Index value '" + index + "' not in range [0.." + (this.pushCacheList.size() - 1) + "]");
        }

        return pushCacheList.get(index);
    }

    /**
     * Method getPushCache.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache[] getPushCache() {
        com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache[] array = new com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache[0];
        return this.pushCacheList.toArray(array);
    }

    /**
     * Method getPushCacheCount.
     * 
     * @return the size of this collection
     */
    public int getPushCacheCount() {
        return this.pushCacheList.size();
    }

    /**
     * Returns the value of field 'requestCondition'.
     * 
     * @return the value of field 'RequestCondition'.
     */
    public com.cdoframework.cdolib.service.framework.transfilter.xsd.RequestCondition getRequestCondition() {
        return this.requestCondition;
    }

    /**
     * Returns the value of field 'responseCondition'.
     * 
     * @return the value of field 'ResponseCondition'.
     */
    public com.cdoframework.cdolib.service.framework.transfilter.xsd.ResponseCondition getResponseCondition() {
        return this.responseCondition;
    }

    /**
     * Returns the value of field 'syn'.
     * 
     * @return the value of field 'Syn'.
     */
    public boolean getSyn() {
        return this.syn;
    }

    /**
     * Method hasSyn.
     * 
     * @return true if at least one Syn has been added
     */
    public boolean hasSyn() {
        return this._hassyn;
    }

    /**
     * Returns the value of field 'syn'.
     * 
     * @return the value of field 'Syn'.
     */
    public boolean isSyn() {
        return this.syn;
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
     * Method iteratePushCache.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache> iteratePushCache() {
        return this.pushCacheList.iterator();
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
    public void removeAllPushCache() {
        this.pushCacheList.clear();
    }

    /**
     * Method removePushCache.
     * 
     * @param vPushCache
     * @return true if the object was removed from the collection.
     */
    public boolean removePushCache(final com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache vPushCache) {
        boolean removed = pushCacheList.remove(vPushCache);
        return removed;
    }

    /**
     * Method removePushCacheAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache removePushCacheAt(final int index) {
        java.lang.Object obj = this.pushCacheList.remove(index);
        return (com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vPushCache
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPushCache(final int index,final com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache vPushCache) throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this.pushCacheList.size()) {
            throw new IndexOutOfBoundsException("setPushCache: Index value '" + index + "' not in range [0.." + (this.pushCacheList.size() - 1) + "]");
        }

        this.pushCacheList.set(index, vPushCache);
    }

    /**
     * 
     * 
     * @param vPushCacheArray
     */
    public void setPushCache(final com.cdoframework.cdolib.service.framework.transfilter.xsd.PushCache[] vPushCacheArray) {
        //-- copy array
        pushCacheList.clear();

        for (int i = 0; i < vPushCacheArray.length; i++) {
                this.pushCacheList.add(vPushCacheArray[i]);
        }
    }

    /**
     * Sets the value of field 'requestCondition'.
     * 
     * @param requestCondition the value of field 'requestCondition'
     */
    public void setRequestCondition(final com.cdoframework.cdolib.service.framework.transfilter.xsd.RequestCondition requestCondition) {
        this.requestCondition = requestCondition;
    }

    /**
     * Sets the value of field 'responseCondition'.
     * 
     * @param responseCondition the value of field
     * 'responseCondition'.
     */
    public void setResponseCondition(final com.cdoframework.cdolib.service.framework.transfilter.xsd.ResponseCondition responseCondition) {
        this.responseCondition = responseCondition;
    }

    /**
     * Sets the value of field 'syn'.
     * 
     * @param syn the value of field 'syn'.
     */
    public void setSyn(final boolean syn) {
        this.syn = syn;
        this._hassyn = true;
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
     * com.cdoframework.cdolib.service.framework.transfilter.xsd.PostPushCache
     */
    public static com.cdoframework.cdolib.service.framework.transfilter.xsd.PostPushCache unmarshal(final java.io.Reader reader) throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.cdoframework.cdolib.service.framework.transfilter.xsd.PostPushCache) org.exolab.castor.xml.Unmarshaller.unmarshal(com.cdoframework.cdolib.service.framework.transfilter.xsd.PostPushCache.class, reader);
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
