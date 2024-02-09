/**
 */
package alpha.model.impl;

import alpha.model.AlphaFunction;
import alpha.model.CalculatorExpressionVisitor;
import alpha.model.JNIFunction;
import alpha.model.ModelPackage;
import alpha.model.POLY_OBJECT_TYPE;

import alpha.model.util.AlphaUtil;

import fr.irisa.cairn.jnimap.isl.ISLMultiAff;

import fr.irisa.cairn.jnimap.runtime.JNIObject;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JNI Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.impl.JNIFunctionImpl#getAlphaFunction <em>Alpha Function</em>}</li>
 *   <li>{@link alpha.model.impl.JNIFunctionImpl#getZ__internal_cache_islMAff <em>Zinternal cache isl MAff</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JNIFunctionImpl extends CalculatorExpressionImpl implements JNIFunction {
	/**
	 * The cached value of the '{@link #getAlphaFunction() <em>Alpha Function</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlphaFunction()
	 * @generated
	 * @ordered
	 */
	protected AlphaFunction alphaFunction;

	/**
	 * The default value of the '{@link #getZ__internal_cache_islMAff() <em>Zinternal cache isl MAff</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZ__internal_cache_islMAff()
	 * @generated
	 * @ordered
	 */
	protected static final ISLMultiAff ZINTERNAL_CACHE_ISL_MAFF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getZ__internal_cache_islMAff() <em>Zinternal cache isl MAff</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZ__internal_cache_islMAff()
	 * @generated
	 * @ordered
	 */
	protected ISLMultiAff z__internal_cache_islMAff = ZINTERNAL_CACHE_ISL_MAFF_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JNIFunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.JNI_FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlphaFunction getAlphaFunction() {
		return alphaFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAlphaFunction(AlphaFunction newAlphaFunction, NotificationChain msgs) {
		AlphaFunction oldAlphaFunction = alphaFunction;
		alphaFunction = newAlphaFunction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.JNI_FUNCTION__ALPHA_FUNCTION, oldAlphaFunction, newAlphaFunction);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlphaFunction(AlphaFunction newAlphaFunction) {
		if (newAlphaFunction != alphaFunction) {
			NotificationChain msgs = null;
			if (alphaFunction != null)
				msgs = ((InternalEObject)alphaFunction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.JNI_FUNCTION__ALPHA_FUNCTION, null, msgs);
			if (newAlphaFunction != null)
				msgs = ((InternalEObject)newAlphaFunction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.JNI_FUNCTION__ALPHA_FUNCTION, null, msgs);
			msgs = basicSetAlphaFunction(newAlphaFunction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.JNI_FUNCTION__ALPHA_FUNCTION, newAlphaFunction, newAlphaFunction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLMultiAff getZ__internal_cache_islMAff() {
		return z__internal_cache_islMAff;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setZ__internal_cache_islMAff(ISLMultiAff newZ__internal_cache_islMAff) {
		ISLMultiAff oldZ__internal_cache_islMAff = z__internal_cache_islMAff;
		z__internal_cache_islMAff = newZ__internal_cache_islMAff;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.JNI_FUNCTION__ZINTERNAL_CACHE_ISL_MAFF, oldZ__internal_cache_islMAff, z__internal_cache_islMAff));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLMultiAff getISLMultiAff() {
		ISLMultiAff _xifexpression = null;
		ISLMultiAff _z__internal_cache_islMAff = this.getZ__internal_cache_islMAff();
		boolean _tripleNotEquals = (_z__internal_cache_islMAff != null);
		if (_tripleNotEquals) {
			_xifexpression = this.getZ__internal_cache_islMAff().copy();
		}
		else {
			_xifexpression = null;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setISLMultiAff(final ISLMultiAff islMAff) {
		this.setZ__internal_cache_islMAff(this.ensureDomainAndRangeAreNamed(islMAff));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public POLY_OBJECT_TYPE getType() {
		POLY_OBJECT_TYPE _xifexpression = null;
		ISLMultiAff _z__internal_cache_islMAff = this.getZ__internal_cache_islMAff();
		boolean _tripleNotEquals = (_z__internal_cache_islMAff != null);
		if (_tripleNotEquals) {
			_xifexpression = POLY_OBJECT_TYPE.FUNCTION;
		}
		else {
			_xifexpression = null;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JNIObject getISLObject() {
		return this.getISLMultiAff();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final CalculatorExpressionVisitor visitor) {
		visitor.visitJNIFunction(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String plainToString() {
		return this.getAlphaFunction().plainToString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLMultiAff ensureDomainAndRangeAreNamed(final ISLMultiAff maff) {
		final List<String> inputNames = maff.getSpace().getInputNames();
		final int inputCount = maff.getSpace().getNbInputs();
		final List<String> outputNames = maff.getSpace().getOutputNames();
		final int outputCount = maff.getSpace().getNbOutputs();
		ISLMultiAff retMaff = maff;
		if (((inputNames == null) || (inputNames.size() != inputCount))) {
			retMaff = AlphaUtil.renameInputs(maff);
		}
		if (((outputNames == null) || (outputNames.size() != outputCount))) {
			retMaff = AlphaUtil.renameOutputs(maff);
		}
		return retMaff;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.JNI_FUNCTION__ALPHA_FUNCTION:
				return basicSetAlphaFunction(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.JNI_FUNCTION__ALPHA_FUNCTION:
				return getAlphaFunction();
			case ModelPackage.JNI_FUNCTION__ZINTERNAL_CACHE_ISL_MAFF:
				return getZ__internal_cache_islMAff();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.JNI_FUNCTION__ALPHA_FUNCTION:
				setAlphaFunction((AlphaFunction)newValue);
				return;
			case ModelPackage.JNI_FUNCTION__ZINTERNAL_CACHE_ISL_MAFF:
				setZ__internal_cache_islMAff((ISLMultiAff)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelPackage.JNI_FUNCTION__ALPHA_FUNCTION:
				setAlphaFunction((AlphaFunction)null);
				return;
			case ModelPackage.JNI_FUNCTION__ZINTERNAL_CACHE_ISL_MAFF:
				setZ__internal_cache_islMAff(ZINTERNAL_CACHE_ISL_MAFF_EDEFAULT);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelPackage.JNI_FUNCTION__ALPHA_FUNCTION:
				return alphaFunction != null;
			case ModelPackage.JNI_FUNCTION__ZINTERNAL_CACHE_ISL_MAFF:
				return ZINTERNAL_CACHE_ISL_MAFF_EDEFAULT == null ? z__internal_cache_islMAff != null : !ZINTERNAL_CACHE_ISL_MAFF_EDEFAULT.equals(z__internal_cache_islMAff);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (z__internal_cache_islMAff: ");
		result.append(z__internal_cache_islMAff);
		result.append(')');
		return result.toString();
	}

} //JNIFunctionImpl
