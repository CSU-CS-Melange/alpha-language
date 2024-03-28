/**
 */
package alpha.codegen.impl;

import alpha.codegen.AlphaOp;
import alpha.codegen.C_BINARY_OP;
import alpha.codegen.C_REDUCTION_OP;
import alpha.codegen.C_UNARY_OP;
import alpha.codegen.CodegenPackage;

import alpha.model.BINARY_OP;
import alpha.model.REDUCTION_OP;
import alpha.model.UNARY_OP;

import com.google.common.base.Objects;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Alpha Op</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class AlphaOpImpl extends MinimalEObjectImpl.Container implements AlphaOp {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AlphaOpImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.ALPHA_OP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public C_UNARY_OP toCUnaryOp(final UNARY_OP o) {
		try {
			C_UNARY_OP _xifexpression = null;
			boolean _equals = Objects.equal(o, UNARY_OP.NOT);
			if (_equals) {
				_xifexpression = C_UNARY_OP.NOT;
			}
			else {
				C_UNARY_OP _xifexpression_1 = null;
				boolean _equals_1 = Objects.equal(o, UNARY_OP.NEGATE);
				if (_equals_1) {
					_xifexpression_1 = C_UNARY_OP.NEGATE;
				}
				else {
					String _plus = (o + " operator does not have a C_UNARY_OP definition");
					throw new Exception(_plus);
				}
				_xifexpression = _xifexpression_1;
			}
			return _xifexpression;
		}
		catch (Throwable _e) {
			throw org.eclipse.xtext.xbase.lib.Exceptions.sneakyThrow(_e);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public C_BINARY_OP toCBinaryOp(final BINARY_OP o) {
		try {
			C_BINARY_OP _xifexpression = null;
			boolean _equals = Objects.equal(o, BINARY_OP.MIN);
			if (_equals) {
				_xifexpression = C_BINARY_OP.MIN;
			}
			else {
				C_BINARY_OP _xifexpression_1 = null;
				boolean _equals_1 = Objects.equal(o, BINARY_OP.MAX);
				if (_equals_1) {
					_xifexpression_1 = C_BINARY_OP.MAX;
				}
				else {
					C_BINARY_OP _xifexpression_2 = null;
					boolean _equals_2 = Objects.equal(o, BINARY_OP.MUL);
					if (_equals_2) {
						_xifexpression_2 = C_BINARY_OP.MUL;
					}
					else {
						C_BINARY_OP _xifexpression_3 = null;
						boolean _equals_3 = Objects.equal(o, BINARY_OP.DIV);
						if (_equals_3) {
							_xifexpression_3 = C_BINARY_OP.DIV;
						}
						else {
							C_BINARY_OP _xifexpression_4 = null;
							boolean _equals_4 = Objects.equal(o, BINARY_OP.MOD);
							if (_equals_4) {
								_xifexpression_4 = C_BINARY_OP.MOD;
							}
							else {
								C_BINARY_OP _xifexpression_5 = null;
								boolean _equals_5 = Objects.equal(o, BINARY_OP.ADD);
								if (_equals_5) {
									_xifexpression_5 = C_BINARY_OP.ADD;
								}
								else {
									C_BINARY_OP _xifexpression_6 = null;
									boolean _equals_6 = Objects.equal(o, BINARY_OP.SUB);
									if (_equals_6) {
										_xifexpression_6 = C_BINARY_OP.SUB;
									}
									else {
										C_BINARY_OP _xifexpression_7 = null;
										boolean _equals_7 = Objects.equal(o, BINARY_OP.AND);
										if (_equals_7) {
											_xifexpression_7 = C_BINARY_OP.AND;
										}
										else {
											C_BINARY_OP _xifexpression_8 = null;
											boolean _equals_8 = Objects.equal(o, BINARY_OP.OR);
											if (_equals_8) {
												_xifexpression_8 = C_BINARY_OP.OR;
											}
											else {
												C_BINARY_OP _xifexpression_9 = null;
												boolean _equals_9 = Objects.equal(o, BINARY_OP.XOR);
												if (_equals_9) {
													_xifexpression_9 = C_BINARY_OP.XOR;
												}
												else {
													C_BINARY_OP _xifexpression_10 = null;
													boolean _equals_10 = Objects.equal(o, BINARY_OP.EQ);
													if (_equals_10) {
														_xifexpression_10 = C_BINARY_OP.EQ;
													}
													else {
														C_BINARY_OP _xifexpression_11 = null;
														boolean _equals_11 = Objects.equal(o, BINARY_OP.NE);
														if (_equals_11) {
															_xifexpression_11 = C_BINARY_OP.NE;
														}
														else {
															C_BINARY_OP _xifexpression_12 = null;
															boolean _equals_12 = Objects.equal(o, BINARY_OP.GE);
															if (_equals_12) {
																_xifexpression_12 = C_BINARY_OP.GE;
															}
															else {
																C_BINARY_OP _xifexpression_13 = null;
																boolean _equals_13 = Objects.equal(o, BINARY_OP.GT);
																if (_equals_13) {
																	_xifexpression_13 = C_BINARY_OP.GT;
																}
																else {
																	C_BINARY_OP _xifexpression_14 = null;
																	boolean _equals_14 = Objects.equal(o, BINARY_OP.LE);
																	if (_equals_14) {
																		_xifexpression_14 = C_BINARY_OP.LE;
																	}
																	else {
																		C_BINARY_OP _xifexpression_15 = null;
																		boolean _equals_15 = Objects.equal(o, BINARY_OP.LT);
																		if (_equals_15) {
																			_xifexpression_15 = C_BINARY_OP.LT;
																		}
																		else {
																			String _plus = (o + " operator does not have a C_BINARY_OP definition");
																			throw new Exception(_plus);
																		}
																		_xifexpression_14 = _xifexpression_15;
																	}
																	_xifexpression_13 = _xifexpression_14;
																}
																_xifexpression_12 = _xifexpression_13;
															}
															_xifexpression_11 = _xifexpression_12;
														}
														_xifexpression_10 = _xifexpression_11;
													}
													_xifexpression_9 = _xifexpression_10;
												}
												_xifexpression_8 = _xifexpression_9;
											}
											_xifexpression_7 = _xifexpression_8;
										}
										_xifexpression_6 = _xifexpression_7;
									}
									_xifexpression_5 = _xifexpression_6;
								}
								_xifexpression_4 = _xifexpression_5;
							}
							_xifexpression_3 = _xifexpression_4;
						}
						_xifexpression_2 = _xifexpression_3;
					}
					_xifexpression_1 = _xifexpression_2;
				}
				_xifexpression = _xifexpression_1;
			}
			return _xifexpression;
		}
		catch (Throwable _e) {
			throw org.eclipse.xtext.xbase.lib.Exceptions.sneakyThrow(_e);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public C_REDUCTION_OP toCReductionOp(final REDUCTION_OP o) {
		try {
			C_REDUCTION_OP _xifexpression = null;
			boolean _equals = Objects.equal(o, REDUCTION_OP.MIN);
			if (_equals) {
				_xifexpression = C_REDUCTION_OP.MIN;
			}
			else {
				C_REDUCTION_OP _xifexpression_1 = null;
				boolean _equals_1 = Objects.equal(o, REDUCTION_OP.MAX);
				if (_equals_1) {
					_xifexpression_1 = C_REDUCTION_OP.MAX;
				}
				else {
					C_REDUCTION_OP _xifexpression_2 = null;
					boolean _equals_2 = Objects.equal(o, REDUCTION_OP.PROD);
					if (_equals_2) {
						_xifexpression_2 = C_REDUCTION_OP.PROD;
					}
					else {
						C_REDUCTION_OP _xifexpression_3 = null;
						boolean _equals_3 = Objects.equal(o, REDUCTION_OP.SUM);
						if (_equals_3) {
							_xifexpression_3 = C_REDUCTION_OP.SUM;
						}
						else {
							C_REDUCTION_OP _xifexpression_4 = null;
							boolean _equals_4 = Objects.equal(o, REDUCTION_OP.AND);
							if (_equals_4) {
								_xifexpression_4 = C_REDUCTION_OP.AND;
							}
							else {
								C_REDUCTION_OP _xifexpression_5 = null;
								boolean _equals_5 = Objects.equal(o, REDUCTION_OP.OR);
								if (_equals_5) {
									_xifexpression_5 = C_REDUCTION_OP.OR;
								}
								else {
									C_REDUCTION_OP _xifexpression_6 = null;
									boolean _equals_6 = Objects.equal(o, REDUCTION_OP.XOR);
									if (_equals_6) {
										_xifexpression_6 = C_REDUCTION_OP.XOR;
									}
									else {
										String _plus = (o + " operator does not have a C_REDUCTION_OP definition");
										throw new Exception(_plus);
									}
									_xifexpression_5 = _xifexpression_6;
								}
								_xifexpression_4 = _xifexpression_5;
							}
							_xifexpression_3 = _xifexpression_4;
						}
						_xifexpression_2 = _xifexpression_3;
					}
					_xifexpression_1 = _xifexpression_2;
				}
				_xifexpression = _xifexpression_1;
			}
			return _xifexpression;
		}
		catch (Throwable _e) {
			throw org.eclipse.xtext.xbase.lib.Exceptions.sneakyThrow(_e);
		}
	}

} //AlphaOpImpl
