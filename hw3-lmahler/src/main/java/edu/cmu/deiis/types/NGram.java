

/* First created by JCasGen Wed Sep 11 13:44:28 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** 
 * Updated by JCasGen Mon Sep 30 20:49:59 EDT 2013
 * XML source: C:/Users/Lars/workspace_kepler/hw3-lmahler/src/main/resources/descriptors/deiis_types.xml
 * @generated */
public class NGram extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(NGram.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected NGram() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public NGram(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public NGram(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public NGram(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: elements

  /** getter for elements - gets An array containing pointers to the individual elements contained within the NGram.
   * @generated */
  public FSArray getElements() {
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_elements == null)
      jcasType.jcas.throwFeatMissing("elements", "edu.cmu.deiis.types.NGram");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((NGram_Type)jcasType).casFeatCode_elements)));}
    
  /** setter for elements - sets An array containing pointers to the individual elements contained within the NGram. 
   * @generated */
  public void setElements(FSArray v) {
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_elements == null)
      jcasType.jcas.throwFeatMissing("elements", "edu.cmu.deiis.types.NGram");
    jcasType.ll_cas.ll_setRefValue(addr, ((NGram_Type)jcasType).casFeatCode_elements, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for elements - gets an indexed value - 
   * @generated */
  public Annotation getElements(int i) {
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_elements == null)
      jcasType.jcas.throwFeatMissing("elements", "edu.cmu.deiis.types.NGram");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((NGram_Type)jcasType).casFeatCode_elements), i);
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((NGram_Type)jcasType).casFeatCode_elements), i)));}

  /** indexed setter for elements - sets an indexed value - 
   * @generated */
  public void setElements(int i, Annotation v) { 
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_elements == null)
      jcasType.jcas.throwFeatMissing("elements", "edu.cmu.deiis.types.NGram");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((NGram_Type)jcasType).casFeatCode_elements), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((NGram_Type)jcasType).casFeatCode_elements), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: elementType

  /** getter for elementType - gets The type of the elements contained in the elements field.
   * @generated */
  public String getElementType() {
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_elementType == null)
      jcasType.jcas.throwFeatMissing("elementType", "edu.cmu.deiis.types.NGram");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NGram_Type)jcasType).casFeatCode_elementType);}
    
  /** setter for elementType - sets The type of the elements contained in the elements field. 
   * @generated */
  public void setElementType(String v) {
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_elementType == null)
      jcasType.jcas.throwFeatMissing("elementType", "edu.cmu.deiis.types.NGram");
    jcasType.ll_cas.ll_setStringValue(addr, ((NGram_Type)jcasType).casFeatCode_elementType, v);}    
   
    
  //*--------------*
  //* Feature: ngramOrder

  /** getter for ngramOrder - gets The order (or degree) of the ngram: 1 (unigram), 2 (bigram), 3 (trigram), and so on.
   * @generated */
  public int getNgramOrder() {
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_ngramOrder == null)
      jcasType.jcas.throwFeatMissing("ngramOrder", "edu.cmu.deiis.types.NGram");
    return jcasType.ll_cas.ll_getIntValue(addr, ((NGram_Type)jcasType).casFeatCode_ngramOrder);}
    
  /** setter for ngramOrder - sets The order (or degree) of the ngram: 1 (unigram), 2 (bigram), 3 (trigram), and so on. 
   * @generated */
  public void setNgramOrder(int v) {
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_ngramOrder == null)
      jcasType.jcas.throwFeatMissing("ngramOrder", "edu.cmu.deiis.types.NGram");
    jcasType.ll_cas.ll_setIntValue(addr, ((NGram_Type)jcasType).casFeatCode_ngramOrder, v);}    
   
    
  //*--------------*
  //* Feature: source

  /** getter for source - gets The source of the NGram (i.e. was the NGram created from Question text or Answer text?).
   * @generated */
  public String getSource() {
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "edu.cmu.deiis.types.NGram");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NGram_Type)jcasType).casFeatCode_source);}
    
  /** setter for source - sets The source of the NGram (i.e. was the NGram created from Question text or Answer text?). 
   * @generated */
  public void setSource(String v) {
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "edu.cmu.deiis.types.NGram");
    jcasType.ll_cas.ll_setStringValue(addr, ((NGram_Type)jcasType).casFeatCode_source, v);}    
  }

    