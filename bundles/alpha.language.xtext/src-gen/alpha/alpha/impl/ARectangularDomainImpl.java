/**
 * generated by Xtext 2.13.0
 */
package alpha.alpha.impl;

import alpha.alpha.ARectangularDomain;
import alpha.alpha.AlphaPackage;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ARectangular Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.alpha.impl.ARectangularDomainImpl#getDimensions <em>Dimensions</em>}</li>
 *   <li>{@link alpha.alpha.impl.ARectangularDomainImpl#getNames <em>Names</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ARectangularDomainImpl extends APolyhedralObjectExpressionImpl implements ARectangularDomain
{
  /**
   * The cached value of the '{@link #getDimensions() <em>Dimensions</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDimensions()
   * @generated
   * @ordered
   */
  protected EList<String> dimensions;

  /**
   * The cached value of the '{@link #getNames() <em>Names</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNames()
   * @generated
   * @ordered
   */
  protected EList<String> names;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ARectangularDomainImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return AlphaPackage.Literals.ARECTANGULAR_DOMAIN;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getDimensions()
  {
    if (dimensions == null)
    {
      dimensions = new EDataTypeEList<String>(String.class, this, AlphaPackage.ARECTANGULAR_DOMAIN__DIMENSIONS);
    }
    return dimensions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getNames()
  {
    if (names == null)
    {
      names = new EDataTypeEList<String>(String.class, this, AlphaPackage.ARECTANGULAR_DOMAIN__NAMES);
    }
    return names;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case AlphaPackage.ARECTANGULAR_DOMAIN__DIMENSIONS:
        return getDimensions();
      case AlphaPackage.ARECTANGULAR_DOMAIN__NAMES:
        return getNames();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case AlphaPackage.ARECTANGULAR_DOMAIN__DIMENSIONS:
        getDimensions().clear();
        getDimensions().addAll((Collection<? extends String>)newValue);
        return;
      case AlphaPackage.ARECTANGULAR_DOMAIN__NAMES:
        getNames().clear();
        getNames().addAll((Collection<? extends String>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case AlphaPackage.ARECTANGULAR_DOMAIN__DIMENSIONS:
        getDimensions().clear();
        return;
      case AlphaPackage.ARECTANGULAR_DOMAIN__NAMES:
        getNames().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case AlphaPackage.ARECTANGULAR_DOMAIN__DIMENSIONS:
        return dimensions != null && !dimensions.isEmpty();
      case AlphaPackage.ARECTANGULAR_DOMAIN__NAMES:
        return names != null && !names.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (dimensions: ");
    result.append(dimensions);
    result.append(", names: ");
    result.append(names);
    result.append(')');
    return result.toString();
  }

} //ARectangularDomainImpl