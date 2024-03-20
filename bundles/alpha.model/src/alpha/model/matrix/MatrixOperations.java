package alpha.model.matrix;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import alpha.model.matrix.factory.MatrixUserFactory;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLMatrix;

/**
 * Number of primitive matrix operations that work on 2D long arrays
 * Copied from original implementation in the legacy AlphaZ system.
 * 
 * @author yuki
 *
 */
public class MatrixOperations {

	/**
	 * Converts a matrix in 2D-vectors to 2D-arrays.
	 * 
	 * @param A
	 *            Matrix in 2D-vector
	 * @return Matrix in 2D-array
	 */
	public static long[][] toMatrix(Vector<Vector<Long>> A) {
		if (A.size() == 0 || A.get(0).size() == 0) {
			return null;
		}

		long[][] B = new long[A.size()][A.get(0).size()];

		for (int i = 0; i < B.length; i++) {
			for (int j = 0; j < B[0].length; j++) {
				B[i][j] = A.get(i).get(j);
			}
		}

		return B;
	}

	/**
	 * Converts a matrix in 2D-vectors to 2D-arrays.
	 * 
	 * @param A
	 *            Matrix in 2D-vector
	 * @return Matrix in 2D-array
	 */
	public static long[][] toLongMatrix(double[][] A) {
		long[][] B = new long[A.length][A[0].length];

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				B[i][j] = Math.round(A[i][j]);
			}
		}

		return B;
	}

	/**
	 * Converts a matrix in long to double.
	 * 
	 * @param A
	 *            Matrix in long
	 * @return Matrix in double
	 */
	public static double[][] toDoubleMatrix(long[][] A) {
		double[][] B = new double[A.length][A[0].length];

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				B[i][j] = (double) A[i][j];
			}
		}

		return B;
	}


	public static long[][] transpose(long[][] A) {
		if (A == null) return null;
		
		long[][] tA = new long[A[0].length][A.length];

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[i].length; j++) {
				tA[j][i] = A[i][j];
			}
		}

		return tA;
	}

	/**
	 * Swaps row i with row j.
	 * 
	 * @param A
	 *            Matrix to operate on.
	 * @param i
	 *            Index if a row to swap
	 * @param j
	 *            Index if a row to swap
	 */
	public static void swapRows(long[][] A, int i, int j) {
		int M = A[0].length;
		long temp;

		for (int c = 0; c < M; c++) {
			temp = A[i][c];
			A[i][c] = A[j][c];
			A[j][c] = temp;
		}
	}

	/**
	 * Swaps row i with row j.
	 * 
	 * @param A
	 *            Matrix to operate on.
	 * @param i
	 *            Index if a row to swap
	 * @param j
	 *            Index if a row to swap
	 */
	public static void swapRows(double[][] A, int i, int j) {
		int M = A[0].length;
		double temp;

		for (int c = 0; c < M; c++) {
			temp = A[i][c];
			A[i][c] = A[j][c];
			A[j][c] = temp;
		}
	}

	/**
	 * Appends additional rows to an existing matrix. Overload for appending
	 * rows of all zeros.
	 * 
	 * @param A
	 *            Matrix to append to
	 * @param num
	 *            Number of all zero rows to append
	 * @return New matrix with appended rows
	 */
	public static long[][] rowBind(long[][] A, int num) {
		return rowBind(A, num, null);
	}

	/**
	 * Appends additional rows to an existing matrix. Overload for appending the
	 * entire matrix to another.
	 * 
	 * @param A
	 *            Matrix to append to
	 * @param newRows
	 *            Another matrix to be appended
	 * @return New matrix with appended rows
	 */
	public static long[][] rowBind(long[][] A, long[][] newRows) {
		return rowBind(A, newRows.length, newRows);
	}

	/**
	 * Appends additional rows to an existing matrix.
	 * 
	 * @param A
	 *            Matrix to append to
	 * @param num
	 *            Number of rows to append
	 * @param newRows
	 *            Rows to append
	 * @return New matrix with appended rows
	 */
	public static long[][] rowBind(long[][] A, int num, long[][] newRows) {
		// If number negative, don't change
		if (num <= 0) {
			return A;
		}

		// If newRow is empty don't change
		if (newRows != null && (newRows.length == 0 || newRows[0].length == 0)) {
			return A;
		}

		int colSize;

		// Size > 0
		if (A == null || A.length == 0) {
			colSize = newRows[0].length;
			// Size does not match
		} else if (newRows != null && (A[0].length != newRows[0].length || num > newRows.length)) {
			return null;
		} else {
			colSize = A[0].length;
		}

		long[][] mat;

		if (A != null) {
			mat = new long[A.length + num][colSize];
		} else {
			mat = new long[num][colSize];
		}

		if (A != null) {
			for (int i = 0; i < A.length; i++) {
				for (int j = 0; j < colSize; j++) {
					mat[i][j] = A[i][j];
				}
			}
		}

		if (newRows != null) {
			for (int i = 0; i < num; i++) {
				for (int j = 0; j < colSize; j++) {
					if (A != null) {
						mat[A.length + i][j] = newRows[i][j];
					} else {
						mat[i][j] = newRows[i][j];
					}
				}
			}
		}

		return mat;
	}

	/**
	 * Appends additional rows to an existing matrix. Overload for appending
	 * rows of all zeros.
	 * 
	 * @param A
	 *            Matrix to append to
	 * @param num
	 *            Number of all zero rows to append
	 * @return New matrix with appended rows
	 */
	public static double[][] rowBind(double[][] A, int num) {
		return rowBind(A, num, null);
	}

	/**
	 * Appends additional rows to an existing matrix. Overload for appending the
	 * entire matrix to another.
	 * 
	 * @param A
	 *            Matrix to append to
	 * @param newRows
	 *            Another matrix to be appended
	 * @return New matrix with appended rows
	 */
	public static double[][] rowBind(double[][] A, double[][] newRows) {
		return rowBind(A, newRows.length, newRows);
	}

	/**
	 * Appends additional rows to an existing matrix.
	 * 
	 * @param A
	 *            Matrix to append to
	 * @param num
	 *            Number of rows to append
	 * @param newRows
	 *            Rows to append
	 * @return New matrix with appended rows
	 */
	public static double[][] rowBind(double[][] A, int num, double[][] newRows) {
		// If number negative, don't change
		if (num <= 0) {
			return A;
		}

		//
		if (newRows == null) {
			return A;
		}

		// If newRow is empty don't change
		if (newRows != null && (newRows.length == 0 || newRows[0].length == 0)) {
			return A;
		}

		int colSize;

		// Size > 0
		if (A == null || A.length == 0) {
			colSize = newRows[0].length;
			// Size does not match
		} else if (newRows != null && ((A != null && A[0].length != newRows[0].length) || num > newRows.length)) {
			return null;
		} else {
			colSize = A[0].length;
		}

		double[][] mat;

		if (A != null) {
			mat = new double[A.length + num][colSize];
		} else {
			mat = new double[num][colSize];
		}

		if (A != null) {
			for (int i = 0; i < A.length; i++) {
				for (int j = 0; j < colSize; j++) {
					mat[i][j] = A[i][j];
				}
			}
		}

		if (newRows != null) {
			for (int i = 0; i < num; i++) {
				for (int j = 0; j < colSize; j++) {
					if (A != null) {
						mat[A.length + i][j] = newRows[i][j];
					} else {
						mat[i][j] = newRows[i][j];
					}
				}
			}
		}

		return mat;
	}

	/**
	 * Appends additional columns to an existing matrix. Overload for appending
	 * columns of all zeros.
	 * 
	 * @param A
	 *            Matrix to append to
	 * @param num
	 *            Number of all zero columns to append
	 * @return New matrix with appended columns
	 */
	public static long[][] columnBind(long[][] A, int num) {
		return columnBind(A, num, null);
	}

	/**
	 * Appends additional columns to an existing matrix. Overload for appending
	 * the entire matrix to another.
	 * 
	 * @param A
	 *            Matrix to append to
	 * @param newCols
	 *            Another matrix to be appended
	 * @return New matrix with appended columns
	 */
	public static long[][] columnBind(long[][] A, long[][] newCols) {
		return columnBind(A, newCols[0].length, newCols);
	}

	/**
	 * Appends additional columns to an existing matrix.
	 * 
	 * @param A
	 *            Matrix to append to
	 * @param num
	 *            Number of columns to append
	 * @param newCols
	 *            Columns to append
	 * @return New matrix with appended columnss
	 */
	public static long[][] columnBind(long[][] A, int num, long[][] newCols) {
		// If number negative, don't change
		if (num <= 0) {
			return A;
		}
		// Size does not match
		if (newCols != null && ((A != null && A.length != newCols.length) || num > newCols[0].length)) {
			return null;
		}

		long[][] mat;

		if (A != null) {
			mat = new long[A.length][A[0].length + num];
		} else {
			mat = new long[newCols.length][num];
		}

		if (A != null) {
			for (int i = 0; i < mat.length; i++) {
				for (int j = 0; j < A[0].length; j++) {
					mat[i][j] = A[i][j];
				}
			}
		}

		if (newCols != null) {
			for (int i = 0; i < mat.length; i++) {
				for (int j = 0; j < num; j++) {
					if (A != null) {
						mat[i][A[i].length + j] = newCols[i][j];
					} else {
						mat[i][j] = newCols[i][j];
					}
				}
			}
		}

		return mat;
	}

	/**
	 * Appends additional columns to an existing matrix. Overload for appending
	 * columns of all zeros.
	 * 
	 * @param A
	 *            Matrix to append to
	 * @param num
	 *            Number of all zero columns to append
	 * @return New matrix with appended columns
	 */
	public static double[][] columnBind(double[][] A, int num) {
		return columnBind(A, num, null);
	}

	/**
	 * Appends additional columns to an existing matrix. Overload for appending
	 * the entire matrix to another.
	 * 
	 * @param A
	 *            Matrix to append to
	 * @param newCols
	 *            Another matrix to be appended
	 * @return New matrix with appended columns
	 */
	public static double[][] columnBind(double[][] A, double[][] newCols) {
		return columnBind(A, newCols[0].length, newCols);
	}

	/**
	 * Appends additional columns to an existing matrix.
	 * 
	 * @param A
	 *            Matrix to append to
	 * @param num
	 *            Number of columns to append
	 * @param newCols
	 *            Columns to append
	 * @return New matrix with appended columnss
	 */
	public static double[][] columnBind(double[][] A, int num, double[][] newCols) {
		// If number negative, don't change
		if (num <= 0) {
			return A;
		}
		// Size does not match
		if (newCols != null && ((A != null && A.length != newCols.length) || num > newCols[0].length)) {
			return null;
		}

		double[][] mat;

		if (A != null) {
			mat = new double[A.length][A[0].length + num];
		} else {
			mat = new double[newCols.length][num];
		}

		if (A != null) {
			for (int i = 0; i < mat.length; i++) {
				for (int j = 0; j < A[0].length; j++) {
					mat[i][j] = A[i][j];
				}
			}
		}

		if (newCols != null) {
			for (int i = 0; i < mat.length; i++) {
				for (int j = 0; j < num; j++) {
					if (A != null) {
						mat[i][A[i].length + j] = newCols[i][j];
					} else {
						mat[i][j] = newCols[i][j];
					}
				}
			}
		}

		return mat;
	}

	/**
	 * Returns the list of indexes for non-zero columns.
	 * 
	 * @param A
	 *            Matrix to operate on
	 * @return List of non-zero column indexes
	 */
	public static long[] nonZeroColumns(long[][] A) {
		long[] non_zeros = new long[A[0].length];

		for (long[] row : A) {
			// Increment one when non-zero
			for (int i = 0; i < non_zeros.length; i++) {
				if (row[i] != 0) {
					non_zeros[i]++;
				}
			}
		}

		return non_zeros;
	}

	/**
	 * Returns the matrix with all-zero columns removed. Overload when non-zero
	 * columns are not known yet.
	 * 
	 * @param A
	 *            Matrix to operate on
	 * @return Matrix with all-zero columns removed
	 */
	public static long[][] removeZeroColumns(long[][] A) {
		return removeZeroColumns(A, null);
	}

	/**
	 * Returns the matrix with all-zero columns removed.
	 * 
	 * @param A
	 *            Matrix to operate on
	 * @return List of non-zero column indexes
	 */
	public static long[][] removeZeroColumns(long[][] A, long[] non_zeros) {
		if (non_zeros == null) {
			non_zeros = nonZeroColumns(A);
		}

		// Count number of non zeros
		int nNZ = 0;

		for (long el : non_zeros) {
			if (el != 0) {
				nNZ++;
			}
		}

		// Reduced mat = matrix without column with just zeros.
		long[][] reduced_mat = new long[A.length][nNZ];

		// Copy matrix skipping zero columns
		for (int i = 0; i < A.length; i++) {
			int nz = 0;

			for (int j = 0; j < A[i].length; j++) {
				if (non_zeros[j] == 0) {
					nz++;
					continue;
				}
				reduced_mat[i][j - nz] = A[i][j];
			}
		}
		return reduced_mat;
	}

	/**
	 * Returns the vector with zero columns removed.
	 * 
	 * @param A
	 *            Vector to operate on
	 * @return List of non-zero column indexes
	 */
	public static long[] removeZeroColumns(long[] A, long[] non_zeros) {
		if (non_zeros == null) {
			non_zeros = nonZeroColumns(new long[][] { A });
		}

		// Count number of non zeros
		int nNZ = 0;

		for (long el : non_zeros) {
			if (el != 0) {
				nNZ++;
			}
		}

		// Reduced non zero = vector of non zeros with all elements > 0
		long[] reduced_non_zeros = new long[nNZ];
		int nz = 0;

		for (int i = 0; i < A.length; i++) {
			if (non_zeros[i] == 0) {
				nz++;
				continue;
			}
			reduced_non_zeros[i - nz] = non_zeros[i];
		}
		return reduced_non_zeros;
	}

	/**
	 * Returns if the two vectors are numerically equal.
	 * 
	 * @param A
	 *            vector
	 * @param B
	 *            vector
	 * @return true if A and B are equal.
	 */
	public static boolean isEqual(long[] A, long[] B) {

		// Check for null
		if (A == null || B == null) {
			return false;
		}

		// check if empty
		if (A.length == 0 || B.length == 0) {
			return false;
		}

		// check size
		if (A.length != B.length) {
			return false;
		}

		// Check
		for (int i = 0; i < A.length; i++) {
			if (A[i] != B[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns if the two vectors are numerically equal.
	 * 
	 * @param A
	 *            vector
	 * @param B
	 *            vector
	 * @return true if A and B are equal.
	 */
	public static boolean isEqual(double[] A, double[] B) {

		// Check for null
		if (A == null || B == null) {
			return false;
		}

		// check if empty
		if (A.length == 0 || B.length == 0) {
			return false;
		}

		// check size
		if (A.length != B.length) {
			return false;
		}

		// Check
		for (int i = 0; i < A.length; i++) {
			if (A[i] != B[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns if the two matrices are numerically equal.
	 * 
	 * @param A
	 *            matrix
	 * @param B
	 *            matrix
	 * @return true if A and B are equal.
	 */
	public static boolean isEqual(long[][] A, long[][] B) {
		// Check for null
		if (A == null || B == null) {
			return false;
		}

		// check if empty
		if (A.length == 0 || B.length == 0 || A[0].length == 0 || B[0].length == 0) {
			return false;
		}

		// check size
		if (A.length != B.length || A[0].length != B[0].length) {
			return false;
		}

		// Check
		for (int i = 0; i < A.length; i++) {
			if (!isEqual(A[i], B[i])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns if the two matrices are numerically equal.
	 * 
	 * @param A
	 *            matrix
	 * @param B
	 *            matrix
	 * @return true if A and B are equal.
	 */
	public static boolean isEqual(double[][] A, double[][] B) {
		// Check for null
		if (A == null || B == null) {
			return false;
		}

		// check if empty
		if (A.length == 0 || B.length == 0 || A[0].length == 0 || B[0].length == 0) {
			return false;
		}

		// check size
		if (A.length != B.length || A[0].length != B[0].length) {
			return false;
		}

		// Check
		for (int i = 0; i < A.length; i++) {
			if (!isEqual(A[i], B[i])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns a new vector containing alpha*X
	 * 
	 * @param X
	 *            vector to be multiplied
	 * @param alpha
	 *            scalar to be added
	 * @return alpha*X
	 */
	public static long[] scalarMultiplication(long[] X, long alpha) {
		if (X.length == 0) {
			return X;
		}
		long[] res = new long[X.length];

		for (int i = 0; i < X.length; i++) {
			res[i] = X[i] * alpha;
		}

		return res;
	}

	/**
	 * Returns a new vector containing alpha*X
	 * 
	 * @param X
	 *            vector to be multiplied
	 * @param alpha
	 *            scalar to be added
	 * @return alpha*X
	 */
	public static double[] scalarMultiplication(double[] X, double alpha) {
		if (X.length == 0) {
			return X;
		}
		double[] res = new double[X.length];

		for (int i = 0; i < X.length; i++) {
			res[i] = X[i] * alpha;
		}

		return res;
	}

	/**
	 * Returns a new matrix containing alpha*A
	 * 
	 * @param A
	 *            matrix to be multiplied
	 * @param alpha
	 *            scalar to be added
	 * @return alpha*A
	 */
	public static long[][] scalarMultiplication(long[][] A, long alpha) {
		if (A.length == 0) {
			return A;
		}
		long[][] res = new long[A.length][A[0].length];

		for (int i = 0; i < A.length; i++) {
			res[i] = scalarMultiplication(A[i], alpha);
		}

		return res;
	}

	/**
	 * Returns a new matrix containing alpha*A
	 * 
	 * @param A
	 *            matrix to be multiplied
	 * @param alpha
	 *            scalar to be added
	 * @return alpha*A
	 */
	public static double[][] scalarMultiplication(double[][] A, double alpha) {
		if (A.length == 0) {
			return A;
		}
		double[][] res = new double[A.length][A[0].length];

		for (int i = 0; i < A.length; i++) {
			res[i] = scalarMultiplication(A[i], alpha);
		}

		return res;
	}

	/**
	 * Removes columns from the vector given, and returns a new matrix
	 * 
	 * @param vec
	 *            vector to operate on
	 * @param columns
	 *            columns (indexed from 0) to remove
	 * @return new vector with columns removed
	 */
	public static long[] removeColumns(long[] vec, int[] columns) {
		// Empty vectors
		if (vec == null || vec.length == 0) {
			return null;
		}

		// When nothing to remove
		if (columns == null || columns.length == 0) {
			return vec;
		}

		// If all columns are to be removed
		if (vec.length <= columns.length) {
			return null;
		}

		// Body
		long[] res = new long[vec.length - columns.length];

		int cur = 0;

		for (int c = 0; c < vec.length; c++) {
			if (contains(c, columns)) {
				continue;
			} else {
				res[cur] = vec[c];
				cur++;
			}
		}

		return res;
	}

	/**
	 * Removes columns from the matrix given, and returns a new matrix.
	 * 
	 * @param mat
	 *            matrix to operate on
	 * @param columns
	 *            columns (indexed from 0) to remove
	 * @return new matrix with columns removed
	 */
	public static long[][] removeColumns(long[][] mat, int[] columns) {
		// Empty matrices
		if (mat == null || mat.length == 0 || mat[0].length == 0) {
			return null;
		}

		// When nothing to remove
		if (columns == null || columns.length == 0) {
			return mat;
		}

		// If all columns are to be removed
		if (mat[0].length <= columns.length) {
			return null;
		}

		// Body
		long[][] res = new long[mat.length][mat[0].length - columns.length];

		for (int r = 0; r < mat.length; r++) {
			res[r] = removeColumns(mat[r], columns);
		}

		return res;
	}
	private static boolean contains(int val, int[] col) {
		for (int v : col) {
			if (val == v) {
				return true;
			}
		}

		return false;
	}

	/* Written by Simon below */

	/**
	 * 
	 * Elements in row i add scale multiplication of scale and elements in row j
	 * 
	 * @param A
	 *            Matrix to operate on.
	 * @param i
	 *            Index if a row to be added
	 * @param j
	 *            Index if a row to add into row i
	 * @param scalar
	 *            Scalar to be multiplied
	 * 
	 * */
	public static void rowAddition(long[][] A, int i, int j, long scalar) {
		int M = A[0].length;

		for (int k = 0; k < M; k++) {
			A[i][k] = A[i][k] + A[j][k] * scalar;
		}
	}

	/**
	 * Elements in row i add scale multiplication of scale and elements in row j
	 * 
	 * @param A
	 *            Matrix to operate on.
	 * @param i
	 *            Index if a column to be added
	 * @param j
	 *            Index if a column to add into row i
	 * @param scalar
	 *            Scalar to be multiplied
	 * 
	 * */

	public static void columnAddition(long[][] A, int i, int j, long scalar) {
		int M = A.length;

		for (int k = 0; k < M; k++) {
			A[k][i] = A[k][i] + A[k][j] * scalar;
		}
	}

	/**
	 * Swaps column i with row j.
	 * 
	 * @param A
	 *            Matrix to operate on.
	 * @param i
	 *            Index if a column to swap
	 * @param j
	 *            Index if a column to swap
	 */

	public static void swapColumns(long[][] A, int i, int j) {
		for (int k = 0; k < A.length; k++) {
			long temp = A[k][i];

			A[k][i] = A[k][j];
			A[k][j] = temp;
		}
	}

	/**
	 * calculate the matrix multiplication A*B
	 * 
	 * @param A
	 *            Matrix to operate on.
	 * @param B
	 *            Matrix to operate on
	 * @return the multiplication of A*B
	 * 
	 * */
	public static long[][] matrixMultiplication(long[][] A, long[][] B) {
		if (A == null || B == null) {
			return null;
		}
		if (A[0].length != B.length) {
			return null;
		}
		long[][] C = new long[A.length][B[0].length];

		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C[0].length; j++) {
				C[i][j] = 0;
				for (int k = 0; k < A[0].length; k++) {
					C[i][j] += A[i][k] * B[k][j];
				}
			}
		}
		return C;
	}

	/**
	 * calculate the Smith Normal Form of A
	 * 
	 * @param A
	 *            Matrix to operate on.
	 * @return A list contains 3 elements: List[0] is the Smith Normal Form of
	 *         A, List[1] is P and List[2] is Q which P and Q are unimodular
	 *         matrix and P*A*Q=SNF of A
	 * 
	 * */
	public static ArrayList<long[][]> smithNormalForm(long[][] A) {

		/* initialize */
		ArrayList<long[][]> result = new ArrayList<long[][]>();
		int M = A.length;
		int N = A[0].length;
		int bound = Math.min(M, N);
		long[][] left, right;

		left = new long[M][M];
		right = new long[N][N];
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < M; j++) {
				if (i == j) {
					left[i][i] = 1;
				} else {
					left[i][j] = 0;
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == j) {
					right[i][i] = 1;
				} else {
					right[i][j] = 0;
				}
			}
		}

		/* operate row by row */
		for (int i = 0; i < bound; i++) {

			/* found gcd of the matrix */
			long gcd = A[i][i];

			for (int j = i; j < M; j++) {
				for (int k = i; k < N; k++) {
					gcd = gcd(A[j][k], gcd);
				}
			}

			/* Look for the min value */
			long Min = Long.MAX_VALUE;
			int posX = -1, posY = -1;

			for (int j = i; j < M; j++) {
				for (int k = i; k < N; k++) {
					if (Math.abs(A[j][k]) < Min && A[j][k] != 0) {
						posX = j;
						posY = k;
						Min = Math.abs(A[j][k]);
					}
				}
			}

			/* swap min value to left-up corner */
			if (posX != i) {
				if (posX == -1) {
					for (int j = 0; j < Math.min(M, N); j++) {
						if (A[j][j] < 0) {
							A[j][j] = -A[j][j];
							scalarMultiplication(left[j], -1);
						}
					}
					result.add(A);
					result.add(left);
					result.add(right);
					return result;
				}
				swapRows(A, i, posX);
				swapRows(left, i, posX);
			}
			if (posY != i) {
				if (posY == -1) {
					for (int j = 0; j < Math.min(M, N); j++) {
						if (A[j][j] < 0) {
							A[j][j] = -A[j][j];
							scalarMultiplication(left[j], -1);
						}
					}
					result.add(A);
					result.add(left);
					result.add(right);
					return result;
				}
				swapColumns(A, i, posY);
				swapColumns(right, i, posY);
			}
			while (true) {

				/* massage the row i */
				for (int j = i + 1; j < N; j++) {
					while (A[i][j] % A[i][i] != 0) {
						long scalar = -A[i][j] / A[i][i];

						columnAddition(A, j, i, scalar);
						columnAddition(right, j, i, scalar);
						swapColumns(A, i, j);
						swapColumns(right, i, j);
					}
					long scalar = -A[i][j] / A[i][i];

					columnAddition(A, j, i, scalar);
					columnAddition(right, j, i, scalar);
				}

				/* massage the column i */
				for (int j = i + 1; j < M; j++) {
					while (A[j][i] % A[i][i] != 0) {
						long scalar = -A[j][i] / A[i][i];

						rowAddition(A, j, i, scalar);
						rowAddition(left, j, i, scalar);
						swapRows(A, i, j);
						swapRows(left, i, j);
					}
					long scalar = -A[j][i] / A[i][i];

					rowAddition(A, j, i, scalar);
					rowAddition(left, j, i, scalar);
				}

				/* massage the row i */
				for (int j = i + 1; j < N; j++) {
					long scalar = -A[i][j] / A[i][i];

					columnAddition(A, j, i, scalar);
					columnAddition(right, j, i, scalar);
				}

				/* massage the column i */
				for (int j = i + 1; j < M; j++) {
					long scalar = -A[j][i] / A[i][i];

					rowAddition(A, j, i, scalar);
					rowAddition(left, j, i, scalar);
				}

				/* do the remaining */
				if (Math.abs(A[i][i]) != Math.abs(gcd)) {
					for (int j = i + 1; j < M; j++) {
						for (int k = i + 1; k < N; k++) {
							if (A[j][k] % A[i][i] != 0) {
								rowAddition(A, j, i, 1);
								rowAddition(left, j, i, 1);
								j = M + 1;
								k = N + 1;
							}
						}
					}
				} else {
					break;
				}
			}
		}
		for (int i = 0; i < Math.min(M, N); i++) {
			if (A[i][i] < 0) {
				A[i][i] = -A[i][i];
				left[i] = scalarMultiplication(left[i], -1);
			}
		}
		result.add(A);
		result.add(left);
		result.add(right);
		return result;
	}

	/**
	 * 
	 * Calculate the greatest common divider of two integers a and b
	 * 
	 * @param a
	 *            One input integer
	 * @param b
	 *            Another input integer
	 * @return Greatest common divider of a and b
	 */
	private static long gcd(long a, long b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}

	/**
	 * Calculate the inverse of a square unimodular integer matrix
	 * 
	 * @param Matrix
	 *            Input matrix
	 * @return The inverse matrix of input matrix
	 */

	public static long[][] intInverse(long[][] Matrix) {
		long[][] A = copyMatrix(Matrix);

		if (A.length == 1) {
			return A;
		}

		/**
		 * First deal with the left upper corner
		 */
		int M = A.length;
		long[][] left;

		left = new long[M][M];
		for (int i = 0; i < M; i++) {
			long sc = 1;

			if (A[i][0] != 0) {
				sc = A[i][0] / Math.abs(A[i][0]);
			}
			for (int j = 0; j < M; j++) {
				A[i][j] = A[i][j] * sc;
				if (i == j) {
					left[i][j] = sc;
				} else {
					left[i][j] = 0;
				}
			}
		}

		/* massage the column i */
		for (int i = 0; i < M; i++) {
			if (A[i][0] != 0) {
				swapRows(A, 0, i);
				swapRows(left, 0, i);
				break;
			}
		}
		for (int j = 1; j < M; j++) {
			while (A[j][0] % A[0][0] != 0) {
				long scalar = -A[j][0] / A[0][0];

				rowAddition(A, j, 0, scalar);
				rowAddition(left, j, 0, scalar);
				swapRows(A, 0, j);
				swapRows(left, 0, j);
			}
			long scalar = -A[j][0] / A[0][0];

			rowAddition(A, j, 0, scalar);
			rowAddition(left, j, 0, scalar);
		}

		/* massage the row i */
		long[][] smaller = new long[M - 1][M - 1];

		for (int i = 0; i < M - 1; i++) {
			for (int j = 0; j < M - 1; j++) {
				smaller[i][j] = A[i + 1][j + 1];
			}
		}
		long[][] vector = new long[1][M - 1];

		for (int i = 0; i < M - 1; i++) {
			vector[0][i] = A[0][i + 1];
		}
		smaller = intInverse(smaller);
		vector = matrixMultiplication(vector, smaller);
		long[][] left2 = new long[M][M];

		for (int i = 0; i < M; i++) {
			for (int j = 0; j < M; j++) {
				if (i == 0 && i != j) {
					left2[i][j] = -vector[0][j - 1];
				} else if (i == j) {
					left2[i][i] = 1;
				} else {
					left2[i][j] = 0;
				}
			}
		}
		long[][] left3 = new long[M][M];

		left3[0][0] = 1;
		for (int i = 1; i < M; i++) {
			left3[0][i] = 0;
			left3[i][0] = 0;
		}
		for (int i = 1; i < M; i++) {
			for (int j = 1; j < M; j++) {
				left3[i][j] = smaller[i - 1][j - 1];
			}
		}
		long[][] result = new long[M][M];

		result = matrixMultiplication(left2, left);
		result = matrixMultiplication(left3, result);
		return result;

	}

	/**
	 * Calculate inverse transformation of T with equations A (Only the
	 * equations in domain should be input) T is n*m, A is r*m, the return
	 * matrix should be m*n
	 * 
	 * @param T
	 *            Transformation matrix
	 * @param A
	 *            Equations matrix
	 * @return Inverse Matrix of T
	 */
	public static long[][] getInverseInContext(long[][] T, long[][] A) {
		long[][] com;

		// If A is not null, simplify A and combine it into T as the matrix that
		// should be operated on
		// else T is the matrix that should be operated on
		if (A != null && A.length > 0) {
			if (A[0].length != T[0].length) {
				throw new RuntimeException("Dimension of context and function does not match.");
			}
			// Find a unimodular matrix APrime to make APrime*X=0 is equivalent
			// to AX=0
			ArrayList<long[][]> aSNFList = smithNormalForm(A);
			long[][] newA = aSNFList.get(0);
			int valid = newA.length;

			for (int i = 0; i < newA.length; i++) {
				if (i >= newA[0].length) {
					valid = newA[0].length;
					break;
				}
				if (newA[i][i] == 0) {
					valid = i - 1;
					break;
				} else if (newA[i][i] != 1) {
					newA[i][i] = 1;
				}
			}
			long[][] APrime = new long[valid][A[0].length];

			for (int i = 0; i < APrime.length; i++) {
				for (int j = 0; j < APrime[i].length; j++) {
					APrime[i][j] = newA[i][j];
				}
			}
			APrime = matrixMultiplication(APrime, intInverse(aSNFList.get(2)));
			com = rowBind(T, APrime);
		} else {
			com = copyMatrix(T);
		}
		// pick out the constant column
		long[][] constant = new long[com.length][1];

		for (int i = 0; i < com.length; i++) {
			constant[i][0] = com[i][com[i].length - 1];
		}
		// remove constant column from combination matrix
		int[] index = new int[1];

		index[0] = com[0].length - 1;
		com = removeColumns(com, index);
		
		if (com.length < com[0].length) {
			throw new LinearAlgebraException("There is no left-inverse for this function in the given context.");
		}
		// Calculate the Smith Normal Form of combination matrix: com=P*snf*Q
		ArrayList<long[][]> snf = smithNormalForm(com);

		// If diagonal elements of snf are not all 1s, there is no left-inverse
		// of it.
		for (int i = 0; i < snf.get(0)[0].length; i++) {
			if (snf.get(0)[i][i] != 1) {
				throw new LinearAlgebraException("There is no left-inverse for this function in the given context.");
			}
		}
		// If diagonal elements of snf are all 1s, the left-inverse should be
		// Q^(-1) * [Id 0]^T * P^(-1)
		long[][] Id0 = new long[snf.get(2).length][snf.get(1).length];

		for (int i = 0; i < Id0.length; i++) {
			for (int j = 0; j < Id0[i].length; j++) {
				if (i == j) {
					Id0[i][j] = 1;
				} else {
					Id0[i][j] = 0;
				}
			}
		}
		long[][] result = matrixMultiplication(snf.get(2), Id0);

		result = matrixMultiplication(result, snf.get(1));
		// Calculate the new constant column
		long[][] newConst = matrixMultiplication(result, constant);

		newConst = scalarMultiplication(newConst, -1);
		long[][] res = new long[result.length][T.length];

		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[0].length; j++) {
				res[i][j] = result[i][j];
			}
		}

		// Combine the constant value back to the matrix
		return columnBind(res, newConst);
	}


	public static String MatrixToDep(long[][] Matrix, String[] Vars) {
		String result = new String();

		result = "(";
		for (int i = 0; i < Vars.length - 1; i++) {
			result = result + Vars[i] + ",";
		}
		result = result + Vars[Vars.length - 1] + "->";
		for (int i = 0; i < Matrix.length; i++) {
			String currentV = new String();

			for (int j = 0; j < Matrix[i].length - 1; j++) {
				if (Matrix[i][j] != 0) {
					String number = new Long(Matrix[i][j]).toString();

					if (number.charAt(0) == '+') {
						number = number.substring(1);
					}
					if (number.equals("1")) {
						number = "";
					}
					currentV = currentV + number + Vars[j] + "+";
				}
			}
			String number = new Long(Matrix[i][Matrix[i].length - 1]).toString();

			if (number.charAt(0) == '+') {
				number = number.substring(1);
			}
			if (currentV.length() == 0) {
				currentV = number;
			} else {
				if (Matrix[i][Matrix[i].length - 1] == 0) {
					currentV = currentV.substring(0, currentV.length() - 1);
				} else {
					currentV = currentV + number;
				}
			}
			while (currentV.indexOf("+-") != -1) {
				currentV = currentV.substring(0, currentV.indexOf("+-")) + currentV.substring(currentV.indexOf("+-") + 1);
			}
			result = result + currentV;
			if (i != Matrix.length - 1) {
				result = result + ",";
			}
		}
		return result + ")";
	}

	/**
	 * Converts the internal representation of domains to inequalities The first
	 * column of the internal representation denotes eq/ineq.
	 * 
	 * @param mat
	 * @return
	 */
	public static long[][] convertDomainToInequalities(long[][] mat) {

		Vector<Vector<Long>> res = new Vector<Vector<Long>>();

		// For each row
		for (int i = 0; i < mat.length; i++) {
			Vector<Long> eq = new Vector<Long>();
			Vector<Long> eqneg = new Vector<Long>();

			// Copy the constraints skipping the first col
			for (int j = 1; j < mat[i].length; j++) {
				eq.add(mat[i][j]);
				// If first col is 0, then it is a equality, construct the
				// negation
				if (mat[i][0] == 0) {
					eqneg.add(-mat[i][j]);
				}
			}
			res.add(eq);
			if (mat[i][0] == 0) {
				res.add(eqneg);
			}
		}

		return MatrixOperations.toMatrix(res);
	}

	/**
	 * Calculates determinant of a matrix
	 * 
	 * Took VB code from http://www.freevbcode.com/ShowCode.asp?ID=1685
	 * 
	 * @param matrix
	 * @return
	 */
	public static double determinant(long[][] matrix) {
		double deter = 1;
		double[][] mat = MatrixOperations.toDoubleMatrix(matrix);
		int N = mat.length;

		// non-square
		if (mat.length != mat[0].length) {
			return 0;
		}

		for (int k = 0; k < N; k++) {
			if (mat[k][k] == 0) {
				int j = k;
				boolean check;

				do {
					check = true;
					if (mat[k][j] == 0) {
						if (j == N - 1) {
							return 0;
						}
						check = false;
						j++;
					} else {
						for (int i = k; i < N; i++) {
							double temp = mat[i][j];

							mat[i][j] = mat[i][k];
							mat[i][k] = temp;
						}
						deter = -deter;
					}
				} while (!check);
			}
			deter = deter * mat[k][k];
			if (k < N - 1) {
				int kk = k + 1;

				for (int i = kk; i < N; i++) {
					for (int j = kk; j < N; j++) {
						mat[i][j] -= (mat[i][k] * mat[k][j] / mat[k][k]);
					}
				}
			}
		}

		return deter;
	}

	/**
	 * Returns true if the rows of the given matrix is linearly dependent. The
	 * check is done by testing if the determinant is 0 or not. If the
	 * determinant is 0, then the matrix does not have inverse, which in turn
	 * means that the trivial solution will be the only solution.
	 * 
	 * @param matrix
	 * @return
	 */
	public static boolean isLinearlyDependent(long[][] matrix) {
		// empty matrix is linearly independent
		if (matrix.length == 0) {
			return false;
		}

		// if number of vectors to test linear dependence is greater than the
		// size of the space, then it has to be dependent
		if (matrix[0].length < matrix.length + 1) {
			return true;
		}

		// transpose
		long[][] ldTest = MatrixOperations.transpose(matrix);

		// if its a square
		if (ldTest.length == ldTest[0].length) {
			// take the determinant
			double det = MatrixOperations.determinant(ldTest);

			return det == 0;
			// If it is not a square, all possible combination of reduced matrix
			// must be tested
		} else {
			// The available cols is the number of vectors given
			int availableCols = ldTest[0].length;
			int[] usedRows = new int[availableCols];

			// initialize with the top available rows
			for (int i = 0; i < availableCols; i++) {
				usedRows[i] = i;
			}

			while (true) {
				// construct a reduced matrix of mxm
				long[][] reducedMat = new long[availableCols][availableCols];

				for (int i = 0; i < usedRows.length; i++) {
					reducedMat[i] = ldTest[usedRows[i]];
				}
				// take the determinant
				double det = MatrixOperations.determinant(reducedMat);

				/*
				 * System.out.println("Linear Dependence Test");
				 * MatrixOperations.printMatrix(reducedMat);
				 * 
				 * System.out.println("Det"); System.out.println(det);
				 */

				// found linear independence
				if (det != 0) {
					return false;
				}

				// exit check
				if (usedRows[0] == ldTest.length - usedRows.length) {
					break;
				}

				// move to the next combination
				// start from the last row
				int current = 0;

				while (true) {
					// if the current row is already at the end
					if (usedRows[usedRows.length - 1 - current] == ldTest.length - 1 - current) {
						current++;
					} else {
						break;
					}
				}

				// increment to the next row
				usedRows[usedRows.length - 1 - current]++;
				// when a row above is updated, lower rows gets reset to above+1
				for (int i = current - 1; i > 0; i--) {
					usedRows[usedRows.length - 1 - i] = usedRows[usedRows.length - 1 - i + 1] + 1;
				}
			}

			return true;
		}
	}
	
	/**
	 * Prints out the given matrix to System.out.
	 * 
	 * @param A
	 *            matrix to print
	 */
	public static void printMatrix(long[][] A) {
		printMatrix(A, System.out);
	}

	/**
	 * Prints out the given matrix to the specified stream.
	 * 
	 * @param A
	 *            matrix to print
	 * @param out
	 *            PrintStream to output
	 */
	public static void printMatrix(long[][] A, PrintStream out) {
		if (A != null) {
			for (long[] row : A) {
				printVector(row, out);
			}
		} else {
			out.println(A);
		}
	}

	/**
	 * Prints out the given vector to the specified stream.
	 * 
	 * @param vec
	 *            Vector to output
	 * @param out
	 *            PrintStream to output
	 */
	public static void printVector(long[] vec, PrintStream out) {
		if (vec == null || vec.length == 0) {
			out.println("[]");
			return;
		}

		out.print("[");
		for (int i = 0; i < vec.length - 1; i++) {
			out.print(vec[i] + ", ");
		}
		out.println(vec[vec.length - 1] + "]");
	}
	

	/**
	 * Computes the basis vectors of Ker(A)
	 * 
	 * Returns null when the nullspace of A is empty.
	 * 
	 * This version uses isl_mat_right_kernel
	 * 
	 * @param A
	 * @return
	 */
	public static long[][] nullspace(long[][] A) {
		ISLMatrix mat = ISLMatrix.buildFromLongMatrix(A);
		long[][] ker =  mat.rightKernel().toLongMatrix();
		
		if (ker.length == 0 || ker[0].length == 0) return null;
		
		return ker;
	}
	
	/**
	 * Compute the spanning vectors of Ker(A).
	 * 
	 * This version is fully implemented in java, but has a bug that 
	 * causes the output to be incorrect for some matrices.
	 * 
	 * @param A
	 * @return [u_1 | ... | u_k] with Ker(A) = Vect(u_1, ..., u_k).
	 */
	public static long[][] nullspacePureJava(long[][] A) {
		
		if (A.length==0 || A[0].length ==0)
			throw new LinearAlgebraException("ZPolyhedraStaticMethod::nullspace: input matrix empty");
		
		// To get Ker(A), we first compute the HNF of A
		List<long[][]> lHnfA = HnfTransformation(A);
		long[][] HA = lHnfA.get(0);
		long[][] UA = lHnfA.get(1);
		
		// We compute the number of zero column at the end of HA.
		int lastNZCol = HA[0].length-1;
		boolean bNull = true;
		while ( (lastNZCol>=0) && bNull) {
			for (int i=0; i<HA.length;i++)
				if (HA[i][lastNZCol]!=0)
					bNull = false;
			if (bNull)
				lastNZCol--;
		}
		
		int firstZCol = lastNZCol+1;
		
		if (UA[0].length-firstZCol == 0) return null;
		
		// The searched vector correspond to the columns of U, starting from firstZCol
		long[][] KerA = new long[UA.length][UA[0].length-firstZCol];
		for (int i=0; i<UA.length; i++) {
			for (int j=0; j<UA[0].length-firstZCol; j++) {
				KerA[i][j] = UA[i][j+firstZCol];
			}
		}
		
		return KerA;
	}
	
	/**
	 * Check if Ker(A) \in Ker(B).
	 * Throw an exception if Ker(A) and Ker(B) lives on different space
	 * @param A
	 * @param B
	 * @return "true" if the inclusion is true, "false" else
	 */
	public static boolean inclusionKernel(long[][] A, long[][] B) {
		if (A[0].length != B[0].length)
			throw new LinearAlgebraException("ZPolyhedraStaticMethod::inclusionKernel: dimension incompatible");
		
		// We check that every vector generating Ker(H1) is null by H2.
		long[][] genKernA = nullspace(A);
		
		long[] vectTemp = new long[genKernA.length];
		for (int j=0; j<genKernA[0].length; j++) {
			// We take one of the generator vector of Ker(A): vectTemp
			for (int i=0; i<genKernA.length; i++) {
				vectTemp[i] = genKernA[i][j];
			}
			
			// We check if (B.vectTemp = 0), ie vectTemp \in Ker(B)
			long[] checkRes = matrixVectMultiplication(B, vectTemp);
			for (int i=0; i<checkRes.length; i++) {
				if (checkRes[i] != 0)
					return false;
			}
		}
		
		// All the generating vectors of Ker(A) has been tested...
		return true;
	}
	
	//from affine lattice

	/**
	 * Method copyVector: Copy a vector
	 * @param vect
	 * @return
	 */
	public static long[] copyVector(long[] vect) {
		long[] nvect = new long[vect.length];
		for (int i=0; i<vect.length; i++) {
			nvect[i] = vect[i];
		}
		return nvect;
	}
	
	/**
	 * Method copyVector: Copy a vector
	 * @param vect
	 * @return
	 */
	public static double[] copyVector(double[] vect) {
		double[] nvect = new double[vect.length];
		for (int i=0; i<vect.length; i++) {
			nvect[i] = vect[i];
		}
		return nvect;
	}
	
	/**
	 * Method copyMatrix: copy a long matrix and give the copy
	 * @param mat
	 * @return copy_mat
	 */
	public static long[][] copyMatrix(long[][] mat) {
		long[][] cmat = new long[mat.length][mat[0].length];
		
		for (int i=0; i<mat.length; i++)
			for (int j=0; j<mat[0].length; j++)
				cmat[i][j] = mat[i][j];
		return cmat;
	}

	/**
	 * Method copyMatrix: copy a double matrix and give the copy
	 * @param mat
	 * @return copy_mat
	 */
	public static double[][] copyMatrix(double[][] mat) {
		double[][] cmat = new double[mat.length][mat[0].length];
		
		for (int i=0; i<mat.length; i++)
			for (int j=0; j<mat[0].length; j++)
				cmat[i][j] = mat[i][j];
		return cmat;
	}
	
	/**
	 * Method toString: give a string representing of a long matrix (for pretty-printers)
	 * @param A
	 * @return str, representing the matrix A
	 */
	public static String toString(long[][] A) {
		if (A == null) return null;
		
		String strMat = new String();
		
		// Case if a matrix is void.
		if (A.length==0)
			return "[]";
		
		for (int i=0; i<A.length; i++) {
			strMat = strMat + "[";
			for (int j=0; j<A[i].length; j++)
				strMat = strMat + " " + A[i][j];
			strMat = strMat + " ]\n";
		}
		
		return strMat;
		}
	
	/**
	 * Method toString: give a string representing of a double matrix (for pretty-printers)
	 * @param A
	 * @return str, representing the matrix A
	 */
	public static String toString(double[][] A) {
		String strMat = new String();
		
		// Case if a matrix is void.
		if (A.length==0)
			return "[]";
		
		for (int i=0; i<A.length; i++) {
			strMat = strMat + "[";
			for (int j=0; j<A[0].length; j++)
				strMat = strMat + " " + A[i][j];
			strMat = strMat + " ]\n";
		}
		
		return strMat;
	}
	
	/**
	 * Method toString: give a string representing of a long vector (for pretty-printers)
	 * @param v
	 * @return str, representing the vector v
	 */
	public static String toString(long[] v) {
		String strVect = new String();
		
		if (v.length==0)
			return "[]";
		strVect = "[ ";
		for (int i=0; i<v.length; i++) {
			strVect = strVect + v[i] + " ";
		}
		strVect = strVect + "]";
		return strVect;
	}
	
	/**
	 * Method toString: give a string representing of a double vector (for pretty-printers)
	 * @param v
	 * @return str, representing the vector v
	 */
	public static String toString(double[] v) {
		String strVect = new String();
		
		if (v.length==0)
			return "[]";
		strVect = "[ ";
		for (int i=0; i<v.length; i++) {
			strVect = strVect + v[i] + " ";
		}
		strVect = strVect + "]";
		return strVect;
	}
	
	/**
	 * Compute vect1 = A.vect - long version
	 * @param A
	 * @param vect
	 * @return vect1
	 */	
	public static long[] matrixVectMultiplication(long[][] A, long[] vect) throws LinearAlgebraException {
		if (A.length==0)
			return new long[0];
		
		if (A[0].length != vect.length)
			throw new LinearAlgebraException("AffLatStatMeth::matrixVectMultiplication: dimension mismatch");
		
		long[] vect1 = new long[A.length];
		for (int i=0; i<A.length; i++) {
			for (int j=0; j<A[0].length; j++) {
				vect1[i] += A[i][j]*vect[j];
			}
		}
		
		return vect1;
	}

	/**
	 * Compute vect1 = A.vect - double version
	 * @param A
	 * @param vect
	 * @return vect1
	 */	
	public static double[] matrixVectMultiplication(double[][] A, double[] vect) throws LinearAlgebraException {
		if (A[0].length != vect.length)
			throw new LinearAlgebraException("AffLatStatMeth::matrixVectMultiplication: dimension mismatch");
		
		double[] vect1 = new double[vect.length];
		for (int i=0; i<A.length; i++) {
			for (int j=0; j<A[0].length; j++) {
				vect1[i] += A[i][j] * vect[j];
			}
		}
		
		return vect1;
	}
	
	/**
	 * Give back the submatrix of A, containing the columns i to j (both indexes included) (0<=i<=j<A[0].length).
	 * 
	 * @param A
	 * @param i
	 * @param j
	 * @return B, submatrix containing the column i to j of A
	 */
	public static long[][] submatrixColumn(long[][] A, int j1, int j2) throws LinearAlgebraException{
		if (j1>j2)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::submatrixColumn: j1>j2 is not allowed");
		if (j1>=A[0].length || j2>=A[0].length)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::submatrixColumn: j1 or j2 out of bound");
		
		long[][] B = new long[A.length][j2-j1+1];
		for (int i=0; i<A.length; i++)
			for (int j=j1; j<=j2; j++)
				B[i][j-j1] = A[i][j];
		
		return B;
	}
	
	/**
	 * Give back the submatrix of A, containing all the columns which indexes are in lColumn
	 * 
	 * @param A
	 * @param lColumn
	 * @return B, submatrix of A
	 */
	public static long[][] submatrixColumn(long[][] A, int[] lColumn) {
		long[][] B = new long[A.length][lColumn.length];
		for (int k1=0; k1<A.length; k1++)
			for (int k2=0; k2<lColumn.length; k2++)
				B[k1][k2] = A[k1][lColumn[k2]];
		
		return B;
	}
	
	/**
	 * Give back the submatrix of A, containing the columns i to j (both indexes included) (0<=i<=j<A[0].length).
	 * 
	 * @param A
	 * @param i
	 * @param j
	 * @return B, submatrix containing the column i to j of A
	 */
	public static long[][] submatrixRow(long[][] A, int i1, int i2) throws LinearAlgebraException {
		if (A.length==0)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::submatrixRow: A has zero row");
		if (i1>i2)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::submatrixRow: i1>i2 is not allowed");
		if (i1>=A.length || i2>=A.length)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::submatrixRow: i1 or i2 out of bound");
		
		long[][] B = new long[i2-i1+1][A[0].length];
		for (int i=i1; i<=i2; i++)
			for (int j=0; j<A[0].length; j++)
				B[i-i1][j] = A[i][j];
		
		return B;
	}
	
	/**
	 * Give back the submatrix of A, containing all the rows which indexes are in lRows
	 * 
	 * @param A
	 * @param lRows
	 * @return B, submatrix of A
	 */
	public static long[][] submatrixRow(long[][] A, int[] lRow) throws LinearAlgebraException {
		if (A.length==0)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::submatrixRow: A has zero row");
		
		long[][] B = new long[lRow.length][A[0].length];
		for (int i=0; i<lRow.length; i++)
			for (int j=0; j<A[0].length; j++)
				B[i][j] = A[lRow[i]][j];
		
		return B;
	}
	
	/**
	 * Give back a subvector of vect, containing all the coefficients i1 to i2 (both included)
	 * 
	 * @param vect
	 * @param i1
	 * @param i2
	 * @return vectRet
	 * @throws LinearAlgebraException
	 */
	public static long[] subvector(long[] vect, int i1, int i2) throws LinearAlgebraException {
		if (vect.length==0)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::subvector: A has zero row");
		if (i1>i2)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::subvector: i1>i2 is not allowed");
		if (i1>=vect.length || i2>=vect.length)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::subvector: i1 or i2 out of bound");
		
		long[] vectRet = new long[i2-i1+1];
		for (int i=i1; i<=i2; i++)
			vectRet[i-i1] = vect[i];
		
		return vectRet;
	}
	
	/**
	 * Give back a subvector of vect, containing all the coefficients which indexes are in lRow
	 * 
	 * @param vect
	 * @param lRow
	 * @return vectRet
	 */
	public static long[] subvector(long[] vect, int[] lRow) {
		long[] vectRet = new long[lRow.length];
		for (int i=0; i<lRow.length; i++)
			vectRet[i] = vect[lRow[i]];
		return vectRet;
	}
	
	
	/* -------------------------------------- */
	// Operation on the matrices
	
	/**
	 * Method rowAddition: L_i <- L_i + scalar*L_j on the double matrix A.
	 * @param A
	 * @param i
	 * @param j
	 * @param scalar
	 */
	public static void rowAddition(double[][] A, int i, int j, double scalar) throws LinearAlgebraException {
		if (i==j)
			throw new LinearAlgebraException("ZPolyhedraStaticMethod::rowAddition: indices equals");
		if (i>=A.length)
			throw new LinearAlgebraException("ZPolyhedraStaticMethod::rowAddition: first indice out of bound");
		if (j>=A.length)
			throw new LinearAlgebraException("ZPolyhedraStaticMethod::rowAddition: second indice out of bound");
		
		for (int k = 0; k<A[0].length; k++) {
			A[i][k] = A[i][k] + A[j][k] * scalar;
		}
	}
	
	/**
	 * Change the column nCol of matrix A into its opposite: C_j <- -C_j
	 * 
	 * @param A
	 * @param nCol number of the column
	 */
	public static void oppositeColumn(long[][] A, int nCol) throws LinearAlgebraException {
		if (nCol<0 || nCol>A[0].length)
			throw new LinearAlgebraException("ZPolyhedraStaticMethod::oppositeColumn: indice out of bound");
		
		for(int i=0; i<A.length; i++) {
			A[i][nCol] = -A[i][nCol];
		}
	}
	
	/**
	 * Change the row nRow of matrix A into its opposite: L_i <- -L_i
	 * 
	 * @param A
	 * @param nRow number of the column
	 */
	public static void oppositeRow(long[][] A, int nRow) throws LinearAlgebraException {
		if (nRow<0 || nRow>A.length)
			throw new LinearAlgebraException("ZPolyhedraStaticMethod::oppositeRow: indice out of bound");
		
		for(int j=0; j<A[0].length; j++) {
			A[nRow][j] = -A[nRow][j];
		}
	}
	
	/**
	 * Give the product between two matrices (basic algorithm in 0(n^3)
	 * @param A
	 * @param B
	 * @return A*B
	 */
	public static double[][] matrixMultiplication(double[][] A, double[][] B) throws LinearAlgebraException {
		
		// Check the compatibility of these matrices
		if (A[0].length != B.length)
			throw new LinearAlgebraException("AffLatStaticMeth::matrixMultiplication : dimentions incompatible");
		
		double[][] C = new double[A.length][B[0].length];
		for (int i=0; i<A.length; i++)
			for (int j=0; j<B[0].length; j++)
				for (int k=0; k<A[0].length; k++)
					C[i][j] += A[i][k] * B[k][j];
		return C;
	}
	
	/**
	 * Compute the trasposition of a double matrix
	 * @param A
	 * @return A^{T}
	 */
	public static double[][] transpose(double[][] A) {
		double[][] At = new double[A[0].length][A.length];
		
		for (int i=0; i<A[0].length; i++)
			for(int j=0; j<A.length; j++)
				At[i][j] = A[j][i];
		
		return At;
	}
	
	
	/* ---------------------------- */
	// More elaborate methods:
	
	
	/**
	 * Method to get the rank of a matrix: by Gauss-pivot algorithm
	 * @param A
	 * @return
	 */
	public static int getRank(long[][] A) {
		if (A.length==0 || A[0].length==0)
			return 0;
		double[][] AGauss = MatrixOperations.toDoubleMatrix(A);
		
		List<double[][]> lGauss = gaussRow(AGauss);
		return (int) lGauss.get(2)[0].length;
	}
	
	/**
	 * Gauss algorithm (rationnal) with rows operations.
	 * To get U, if we have [L_i <- L_i + lambda . L_j] on G, we do [C_j <- C_j - lambda . C_i]
	 * @param A
	 * @return List<double[][]> L with L[0] = G, L[1] = U, such that A = U.G
	 * 			L[2] = [V] with V the vector containing the r column number of the "corner" of G,
	 * 			with r the rank of A.
	 */
	public static List<double[][]> gaussRow(double[][] Ainp) {
		double[][] A = copyMatrix(Ainp);
		
		// If one dimension is null
		if (A.length==0 || A[0].length==0) {
			List<double[][]> ret = new ArrayList<double[][]>();
			double[][] U = new double[A.length][A.length];
			double[][] bord = new double[1][0];
			ret.add(A);
			ret.add(U);
			ret.add(bord);
			return ret;
		}
		
		// Creation of the transposition of the U matrix because row op are faster than column op)
		double[][] Ut = new double[A.length][A.length];
		for (int i=0; i<A.length; i++)
			Ut[i][i] = 1;
		
		// Creation of the vector of the border column indices
		int[] bord_temp = new int[A[0].length];
		
		// Gauss-pivot algorithm: we use operation on the lines...
		int num_pivot = 0; // Number of lines already taken into account.
		for (int j=0; j<A[0].length; j++) {
			
			// Search for a non-zero coefficient in the remaining lines
			int num_row = num_pivot;
			while (num_row<A.length && A[num_row][j]==0)
				num_row++;
			
			// If there is only zeros on this line: go to the next column.
			// Else, let's pivot !
			if (num_row!=A.length) {
				// Put the "non_zero" line in the "first" position
				MatrixOperations.swapRows(A, num_pivot, num_row);
				MatrixOperations.swapRows(Ut, num_pivot, num_row);
				
				// Make zeros appear on the rest of the column
				for (int i=num_pivot+1; i<A.length;i++) {
					if (A[i][j] != 0.) {
						double coeff= - A[i][j] / A[num_pivot][j];
						rowAddition(A, i, num_pivot, coeff);
						rowAddition(Ut, num_pivot, i, -coeff);
					}
				}
				bord_temp[num_pivot] = j;
				num_pivot++;
			}
		}
		
		double[][] bord = new double[1][num_pivot];
		for (int i=0; i<num_pivot; i++)
			bord[0][i] = (double) bord_temp[i];
		
		double[][] U = transpose(Ut);
		
		List<double[][]> ret = new ArrayList<double[][]>();
		ret.add(A);
		ret.add(U);
		ret.add(bord);
		
		return ret;
	}
	
	/**
	 * Gauss algorithm (rationnal) with columns operations.
	 * We do a transpose and we use Gauss algorithm with row operations...
	 * @param A
	 * @return List<double[][]> L with L[0] = G, L[1] = U, such that A = G.U
	 * 			L[2] = [V] with V the vector containing the r row number of the "corner" of G,
	 * 			with r the rank of A.
	 */
	public static List<double[][]> gaussColumn(double[][] A) {
		// If one dimension is null: case treated in gaussRow
		double[][] At = transpose(A);
		List<double[][]> ltransp = gaussRow(At);
		
		double[][] Gt = ltransp.get(0);
		double[][] Ut = ltransp.get(1);
		
		List<double[][]> lret = new ArrayList<double[][]>();
		lret.add(transpose(Gt));
		lret.add(transpose(Ut));
		lret.add(ltransp.get(2));
		return lret;
	}

	/**
	 * Compute the Hermite Normal Form with the associate unimodular matrix U.
	 * (ref: TLIP, ch 4, section 1, thm 4.1 for the idea of the algorithm
	 * "The Z-polyhedra Model" for the definition chosen of the HNF representation)
	 * 
	 * EDIT: the algorithm above is not polynomial because of numerical explosion issues
	 * The good algorithm is ch 5, section 3, is a bit more complicated
	 * and need some arrangement, because it works only on the full-row rank case.
	 * 
	 * @args integer matrix A full-row rank
	 * @return A 2-elements list with l[0] = H and l[1]=U
	 * with H=AU
	 */
	public static List<long[][]> HnfTransformation(long[][] A) throws LinearAlgebraException {
		
		int n = A.length;
		int m = A[0].length;
		
		// If one dimension is null
		if (n==0 || m==0) {
			List<long[][]> ret = new ArrayList<long[][]>();
			long[][] U = new long[A[0].length][A[0].length];
			ret.add(A);
			ret.add(U);
			return ret;
		}
		
		
		// STEP 1: We reduce the problem to an integer full-row rank matrix A1.
		// For that, we are looking at the submatrix A1 which is composed by the first linerarly independant
		// rows of A of maximal rank.
		double[][] Adbl = MatrixOperations.toDoubleMatrix(A);
		double[][] G = new double[n][m];
		int nrowG = 0;
		int[] lRow = new int[n];
		List<double[][]> gRow_temp = new ArrayList<double[][]>();
		
		for (int i=0; i<n; i++) {
			// We introduce a new row in G
			for (int j=0; j<m; j++)
				G[nrowG][j] = Adbl[i][j];
			
			// We do a Gauss algorithm on the row of G:
			// only the last row will be modified, because G is already partially on Gauss form...
			gRow_temp = gaussRow(G);
			
			// DEBUG
			//System.out.println("DEBUG (begin loop " + i + ")");
			//System.out.println("G =\n" + toString(G));
			//System.out.println("GAdbl =\n" + toString(gRow_temp.get(0)));
			
			// We observe if the last line is nullified...
			int ktemp=0;
			while ( (ktemp<m) && (gRow_temp.get(0)[nrowG][ktemp]==0.) )
				ktemp++;				// WARNING: perhaps some numerical imprecisions here !!!
			if (ktemp==m) {
				// the last line is nullified, so is linerarly dependant of the previous ones
				// => We keep the previous G
				// (So, here, we do nothing :) )
			} else {
				// We keep the last line on G.
				lRow[nrowG] = i;
				nrowG++;
			}
			
			// DEBUG
			//System.out.println("DEBUG (end loop " + i + ")");
			//System.out.println("DEBUG: nrowG = " + nrowG);
			//System.out.println("DEBUG: m = " + m + " | ktemp = " + ktemp);
			//for (int k=0; k<nrowG; k++)
			//	System.out.println("DEBUG: lRow(" + k + ") = " + lRow[k]);
			//System.out.println();
		}
		
		// We get A1 and lColumn, the list of the "corner" columns.
		int r = nrowG;
		long[][] A1 = new long[r][A[0].length];
		for (int i=0; i<r; i++)
			for (int j=0; j<A[0].length; j++)
				A1[i][j] = A[lRow[i]][j];
		
		int[] lColumn = new int[r];
		for (int i=0; i<r; i++)
			lColumn[i] = (int) gRow_temp.get(2)[0][i];
		
		// We get finally B
		long[][] B = submatrixColumn(A1, lColumn);
		
		// DEBUG
		//System.out.println("DEBUG HNF: A =\n" + toString(A));
		//for (int i=0; i<r; i++)
		//	System.out.println("DEBUG: lRow(" + i + ") =" + lRow[i]);
		//System.out.println("DEBUG HNF: A1 =\n" + toString(A1));
		//System.out.println("DEBUG HNF: B =\n" + toString(B));
		
		// Special case (if A is a zero matrix)
		if (B.length==0) {
			long[][] Hzero = new long[n][m];
			long[][] Uzero = new long[m][m];
			for (int i=0; i<m; i++)
				Uzero[i][i] = 1;
			List<long[][]> lRet = new ArrayList<long[][]>();
			lRet.add(Hzero);
			lRet.add(Uzero);
			return lRet;
		}
		
		// STEP 2: We create the matrix A' = [A1 | M.Id].
		// U' = [ Id | V ] : (m+r)*(m+r)
		//      [ 0  | 0 ]
		// with V = (epsilon. det(B).B^{-1}) in only the columns of A1 which are used for B.
		// and epsilon = sign(det(B)) and M = |det(B)|
		
		List<long[][]> ldetBinv = detInverse(B);
		long[][] detBinv = ldetBinv.get(0);
		long detB = ldetBinv.get(1)[0][0];
		long M = Math.abs(detB);
		long epsilon = 1;
		if (detB<0)
			epsilon = -1;
		
		// We build the matrix Aprime
		long[][] Aprime = new long[r][m+r];
		for (int i=0; i<r; i++) {
			for (int j=0; j<m; j++)
				Aprime[i][j] = A[i][j];
			Aprime[i][i+m] = M;
		}
		
		// We build the matrix Uprime
		long[][] Uprime = new long[m+r][m+r];
		for (int i=0; i<m; i++)
				Uprime[i][i] = 1;
		for (int i=0; i<r; i++)
			for (int j=0; j<r; j++)
				Uprime[lColumn[i]][m+j] = epsilon*detBinv[i][j];
		
		// DEBUG
		// System.out.println("-- DEBUG: Initialisation of APrime and UPrime :");
		// System.out.println("APrime =\n" + toString(Aprime));
		// System.out.println("UPrime =\n" + toString(Uprime));
		
		// STEP 3. To get U_{H'}, we repeat the same column operation on the matrix U'
		// than the column operations used on the matrix A' to get H' = (H | 0)
		// we get H' = A'. U'
		//
		// Idea of the algorithm: do the "Gauss algorithm" combined with Euclidian algorithm,
		// but by binding the values with the last column at the same time.
		// A' is full-row rank, and that's why nothing strange appears on the right... :)
		
		// DEBUG
		// long[][] AprimeDEBUG = copyMatrix(Aprime);
		
		int nZero = 0;
		long tempEucl = 0;
		for (int i=0; i<r; i++){
			
			// Search  of the first non-zero value
			nZero = i;
			while (nZero<Aprime[0].length && Aprime[i][nZero]==0)
				nZero++;
			if (nZero==Aprime[0].length)
				throw new LinearAlgebraException("AffLatStaticMethod::HnfTransf : not possible");
			
			// We put the corresponding column on the right place...
			MatrixOperations.swapColumns(Aprime, i, nZero);
			MatrixOperations.swapColumns(Uprime, i, nZero);
			
			if (Aprime[i][i] <0) {
				oppositeColumn(Aprime, i);
				oppositeColumn(Uprime, i);
			}
			
			// Search and elimination of the other non-zero value on the row i
			for (int j=i+1; j<Aprime[0].length; j++) {
				if (Aprime[i][j] !=0) {
					
					// We make the corresponding coefficient positive:
					if (Aprime[i][j] < 0) {
						oppositeColumn(Aprime, j);
						oppositeColumn(Uprime, j);
					}
					
					// Euclidian algorithm between columns i and j.
					while(Aprime[i][i] !=0 && Aprime[i][j]!=0) {
						if (Aprime[i][i] >= Aprime[i][j]) {
							tempEucl = (long) - (Aprime[i][i]/Aprime[i][j]);
							MatrixOperations.columnAddition(Aprime, i, j, tempEucl);
							MatrixOperations.columnAddition(Uprime, i, j, tempEucl);
						} else {
							tempEucl = (long) - (Aprime[i][j]/Aprime[i][i]);
							MatrixOperations.columnAddition(Aprime, j, i, tempEucl);
							MatrixOperations.columnAddition(Uprime, j, i, tempEucl);
						}
						
					}
					
					// At the end: one of the two coefficient Aprime[i][i] or Aprime[i][j] has been nullified.
					if (Aprime[i][i] ==0) {
						MatrixOperations.swapColumns(Aprime, i, j);
						MatrixOperations.swapColumns(Uprime, i, j);
					}
					
				}
			}
			// We now have only zero in the row i, after the column i.
			
			// We "arrange" the coefficient on the row i.
			for (int j=0; j<i; j++) {
				if (Aprime[i][j] >= Aprime[i][i]) {
					tempEucl = (long) -(Aprime[i][j] / Aprime[i][i]);
					MatrixOperations.columnAddition(Aprime, j, i, tempEucl);
					MatrixOperations.columnAddition(Uprime, j, i, tempEucl);
				}
				if (Aprime[i][j] < 0) {
					tempEucl = (long) (-Aprime[i][j] / Aprime[i][i]) + 1;
					MatrixOperations.columnAddition(Aprime, j, i, tempEucl);
					MatrixOperations.columnAddition(Uprime, j, i, tempEucl);
				}
			}
			
			// We put every coefficient on the rows below i, between 0 and M, using the last columns.
			for (int k=i+1; k<Aprime.length; k++) {
				for (int j=0; j<m+i+1; j++) {
					if (Aprime[k][j]>=M) {
						tempEucl = (long) - (Aprime[k][j] / M);
						MatrixOperations.columnAddition(Aprime, j, m+k, tempEucl);
						MatrixOperations.columnAddition(Uprime, j, m+k, tempEucl);
					}
					if (Aprime[k][j]<0) {
						tempEucl = (long) (-Aprime[k][j] / M) + 1;
						MatrixOperations.columnAddition(Aprime, j, m+k, tempEucl);
						MatrixOperations.columnAddition(Uprime, j, m+k, tempEucl);
					}
				}
			}
			// We have finished for the row i.
			
			// DEBUG
			// System.out.println("---- DEBUG HNF row " + i + " :\n");
			// System.out.println("Aprime =\n" + toString(Aprime));
			// System.out.println("Uprime =\n" + toString(Uprime));
			// System.out.println("A.Uprime =\n" + toString(matrixMultiplication(AprimeDEBUG,Uprime)));
		
		}
		// DEBUG
		// System.out.println("---- DEBUG HNF - end algo :");
		// System.out.println("Aprime =\n" + toString(Aprime));
		// System.out.println("Uprime =\n" + toString(Uprime));
		
		// Finally, H to the n first column of H' and
		// U correspond to the n*rg(H) first square of U_{H'}, plus (n-rg(H)) other linearly independent columns
		long[][] U = new long[m][m];
		long[][] HA1 = new long[r][m];
		
		long[][] Htemp = MatrixOperations.removeZeroColumns(Aprime);
		int rgHA1 = Htemp[0].length;
		
		for (int i=0; i<m; i++)
			for (int j=0; j<rgHA1; j++)
				U[i][j] = Uprime[i][j];
		
		for (int i=0; i<r; i++)
			for (int j=0; j<rgHA1; j++)
				HA1[i][j] = Aprime[i][j]; // The other columns are zeros.
		
		// DEBUG
		// System.out.println("rgHA1 = " + rgHA1);
		
		if (rgHA1 != m) {
			// We fetch the (m-rgHA1) remaining columns of U (corresponding to the kernel)
			// For this, we select (m-rgHA1) linearly independent columns of the last columns of U.
			// => Gauss Row algorithm
			long[][] UKer = submatrixColumn(Uprime, rgHA1, Uprime[0].length-1);
			double[][] lcolUKerDbl = gaussRow(MatrixOperations.toDoubleMatrix(UKer)).get(2);
			int[] lcolUKer = new int[lcolUKerDbl[0].length];
			for (int i=0; i<lcolUKer.length; i++)
				lcolUKer[i] = (int) lcolUKerDbl[0][i];
		
			for (int i=0; i<m; i++)
				for (int j=0; j<m-rgHA1; j++)
					U[i][j+rgHA1] = UKer[i][lcolUKer[j]];
		}
		
		// DEBUG
		// System.out.println("DEBUG: U =\n" + toString(U));
		// System.out.println("DEBUG: HA1 =\n" + toString(HA1));
		
		
		// STEP 3: We get back to the initial matrix A, by adding rows in HA1
		// We get H, using lRow
		
		long[][] H = new long[n][m];
		int ktemp = 0;
		
		for (int i=0; i<n; i++) {
			if (i==lRow[ktemp]) {	// We copy the corresponding row of HA1
				for (int j=0; j<m; j++)
					H[i][j] = HA1[ktemp][j];
				ktemp++;
			} else {		 		// The row is a_i^{T} . U with a_i^{T} the corresponding row of A
				for (int j=0; j<m; j++)
					for (int k=0; k<m; k++) {
						H[i][j] += A[i][k] * U[k][j];
					}
			}
		}
		
		// We return the result...
		List<long[][]> ret = new ArrayList<long[][]>();
		ret.add(H);
		ret.add(U);
		return ret;
	}

	// About the four following methods: double are used inside, so perhaps there is some numerical imprecision...
	// => use " Math.round(double) "
	
	/**
	 * Get (det(A).A^{-1}) with A an integer matrix
	 * @param A
	 * @return ldetAinv with ldetAinv.get(0) = detAinv and ldetAinv.get(1) = [[det(A)]]
	 */
	public static List<long[][]> detInverse(long[][] A) throws LinearAlgebraException {
		if (A.length != A[0].length)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::detInverse: matrix not square");
		if (A.length==0)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::detInverse: dimension of the matrix is null");
		
		double[][] Adbl = MatrixOperations.toDoubleMatrix(A);
		List<double[][]> linvdet = inverseDet(Adbl);
		
		long det = Math.round(linvdet.get(1)[0][0]);
		double[][] Ainv = linvdet.get(0);
		long[][] detAinv = new long[Ainv.length][Ainv[0].length];
		
		for (int i=0; i<Ainv.length; i++)
			for (int j=0; j<Ainv[0].length; j++)
				detAinv[i][j] = Math.round(det * Ainv[i][j]);
		
		List<long[][]> ldetAinv = new ArrayList<long[][]>();
		ldetAinv.add(detAinv);
		long[][] matDet = new long[1][1];
		matDet[0][0] = det;
		ldetAinv.add(matDet);
		
		return ldetAinv;
	}
	
/**
	 * Get the inverse of a matrix by the Gauss pivot method
	 * @param A
	 * @return List_Ainv with List_Ainv(0)= Ainv and List_Ainv(1) = [[det(A)]]
	 * @throws LinearAlgebraException 
	 */
	public static double[][] inverse(double[][] A) throws LinearAlgebraException {
		if (A.length != A[0].length)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::inverse: matrix not square");
		if (A.length==0)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::inverse: dimension of the matrix is null");
		
		List<double[][]> lInv = inverseDet(A);
		return lInv.get(0);
	}
	
	/**
 	 * Get the determinant on a matrix by the Gauss pivot method
	 * @param A
	 * @return det
	 * @throws LinearAlgebraException
	 */
	public static double determinant(double[][] A) throws LinearAlgebraException {
		if (A.length != A[0].length)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::determinant: matrix not square");
		if (A.length==0)
			throw new LinearAlgebraException("AffineLatticeStaticMethod::determinant: dimension of the matrix is null");
		
		List<double[][]> lInv = inverseDet(A);
		return lInv.get(1)[0][0];
	}
	
	/**
	 * Get the inverse and the determinant of a matrix by the Gauss pivot method
	 * @param A
	 * @return List_Ainv with List_Ainv(0)= Ainv and List_Ainv(1) = [[det(A)]]
	 * @throws LinearAlgebraException 
	 */
	public static List<double[][]> inverseDet(double[][] A) throws LinearAlgebraException {
		if (A.length!=A[0].length)
			throw new LinearAlgebraException("AffLatStaticMeth::inverse : the matrix is not square");
		if (A.length==0)
			throw new LinearAlgebraException("AffLatStaticMeth::inverse : dimension of the matrix is null");
		
		// Idea: do a Gauss pivot Method to get a diagonal matrix DG and U such that DG = U.A
		// To get U, we repeat exactly the same row operation on U that we use to get DG from A.
		
		// We have to do it in rational, else, by repeating Euclid algorithm, we might have
		// unbounded coefficients, therefore, non-polynomial complexity.
		
		// 0. Initialization of the matrices
		double[][] DG = copyMatrix(A); 
		double[][] U = new double[A.length][A.length];
		
		for (int i=0; i<A.length; i++)
			U[i][i] = 1.;
		
		// 1. "Double"-Pivot on the rows to get a diagonal matrix
		// If the matrix is not inversible, we throw an exception
		int nZrow = 0;
		double coeff = 0.;
		for (int j=0; j<A.length; j++) {
			
			// We search a row with a non-zero coefficient.
			nZrow = j;
			while (nZrow<A.length && DG[nZrow][j]==0)
				nZrow++;
			
			if (nZrow==A.length)
				throw new LinearAlgebraException("AffLatStaticMethod::inverse : the matrix is not inversible");
			
			// We put it on the first place
			if (nZrow != j) {
				MatrixOperations.swapRows(DG, j, nZrow);
				MatrixOperations.swapRows(U, j, nZrow);
			}
			
			// Elimination of the other coefficients on the top and the bottom of the row
			for (int i=0; i<A.length; i++) {
				if (i != j) {
					coeff = - DG[i][j]/DG[j][j];
					rowAddition(DG, i, j, coeff);					
					rowAddition(U, i, j, coeff);
				}
			}
		}
		// At the end, DG is diagonal and we have DG = U.A
		
		// 2. We compute and return the inverse (Ainv = (DG^{-1}).U ) and the determinant 
		// (reminder: DG is diagonal, so getting DG^{-1} is trivial)
		double det = 1;
		
		for (int i=0; i<A.length; i++) {
			det *= DG[i][i];
			DG[i][i] = 1/DG[i][i];
		}
		
		double[][] Ainv = matrixMultiplication(DG, U);
		double[][] Mat_det = new double[1][1];
		Mat_det[0][0] = det;
		
		List<double[][]> List_Ainv = new ArrayList<double[][]>();
		List_Ainv.add(Ainv);
		List_Ainv.add(Mat_det);
		return List_Ainv;
	}
	
	/** 
	 * Method to compute the gcd of all the coefficients of a vector
	 * @param v
	 * @return gcd(v)
	 */
	public static long gcdVect(long[] v) {
		long g = v[0];
		for (int i=1; i<v.length; i++)
			g = gcd(g,v[i]);
		return g;
	}
	
	/**
	 * Solver of the Diophantine equation Ax=b
	 * (ref: TLIP, ch 5, section 3, 5.3.b and 5.3.c for the algorithm)
	 * 
	 * @param A integer matrix
	 * @param b integer vector
	 * @return List of vector x_0, x_1, ..., x_t such as the set of solution of Ax=b is
	 * { x0 + a1.x1 + ... + at.xt | a1,...,at \in Z }
	 * OR null if there is no integer solution
	 */
	public static List<long[]> diophSysEqResolution(long[][] A, long[] b) {
		
		// STEP 1: Check for rational solution
		// We have at the beginning A.x=b, with A non full-row rank.
		// For this, we use the rationnal Gauss-Jordan algorithm
		// and we check that the last rows (null row in the Gauss matrix) are null in the corresponding b
		double[][] Adbl = MatrixOperations.toDoubleMatrix(A);
		List<double[][]> listGA = gaussRow(Adbl);
		
		double[][] UA = listGA.get(1); // A = UA . GA
		
		// First, we check the validity.
		double[][] invUA = inverse(UA);
		
		double[] bdbl = new double[b.length];
		for (int k=0; k<b.length; k++)
			bdbl[k] = (double) b[k];
		
		double[] invUb = matrixVectMultiplication(invUA, bdbl);
		int rank = listGA.get(2)[0].length;
				
		for (int i=rank; i<invUb.length; i++) { // WARNING: It might have some calculus imprecisions here...
			if (invUb[i]!=0.)
				return null;
		}
		
		// STEP 2: We suppress the redundant lines of A to get a new system A1.x=b1 with A1 full-row rank.
		// For this, we reuse the trick used at the beginning of our HNF algorithm
		// In summary, we compute the rational Gauss form of a submatrix of A,
		// in which we introduce each iteration a new row of A.
		// If the row is linearly dependent of the previous one (row of zero on the Gauss form), we don't keep it
		// Else, we keep it and save the corresponding row indice.
		// To get a full-row rank sub-matrix of A, we just take the row of A of corresponding indices.
		double[][] G = new double[A.length][A[0].length];
		int nrowG = 0;
		int[] lRow = new int[A.length];
		List<double[][]> gRow_temp = new ArrayList<double[][]>();
		
		for (int i=0; i<A.length; i++) {
			// We introduce a new row in G
			for (int j=0; j<A[0].length; j++)
				G[nrowG][j] = Adbl[i][j];
			
			// We do a Gauss algorithm on the row of G
			gRow_temp = gaussRow(G);
			
			// We observe if the last line is nullified...
			int ktemp=0;
			while ( (ktemp<A[0].length) && (gRow_temp.get(0)[nrowG][ktemp]==0.) )
				ktemp++;				// WARNING: NUMERICAL IMPRECISIONS POSSIBLE HERE !!!
			if (ktemp!=A[0].length) {
				// We keep the last line on G.
				lRow[nrowG] = i;
				nrowG++;
			}
		}
		
		// We get A1 and b1.
		int r = nrowG;
		long[][] A1 = new long[r][A[0].length];
		for (int i=0; i<r; i++)
			for (int j=0; j<A[0].length; j++)
				A1[i][j] = A[lRow[i]][j];
		long[] b1 = new long[r];
		for (int i=0; i<r; i++)
			b1[i] = b[lRow[i]];
		
		// The new system is A1.x = b1 with A1 full-row rank.
		
		// DEBUG:
		// System.out.println("DEBUG: A1 = " + toString(A1));
		// System.out.println("DEBUG: b1 = " + toString(b1));
		
		// STEP 3: we compute the HNF of A1 and get the spanning solution vectors.
		List<long[][]> hList = HnfTransformation(A1);
		long[][] H = hList.get(0);
		long[][] U = hList.get(1);
		
		// H = [ B | 0 ]
		long[][] B = MatrixOperations.removeZeroColumns(H);
		
		// DEBUG
		// System.out.println("DEBUG: H =\n" + toString(H));
		// System.out.println("DEBUG: U =\n" + toString(U));
		// System.out.println("DEBUG: A1.U =\n" + toString(MatrixOperations.matrixMultiplication(A1, U)));
		// System.out.println("DEBUG: B =\n" + toString(B));
		
		double[][] Binv;
		try {
			Binv = inverse(MatrixOperations.toDoubleMatrix(B));		// B should be inversible
		} catch (LinearAlgebraException e) {
			System.out.println("WARNING: Hnf_diophantien::diophSysEqResolution : Numerical imprecision in the elimination of redundant constraint:\n" +
					"This matrix should be inversible:\n" + toString(B));
			return null;
		}
		
		// We compute B^{-1}.b
		double[] db = new double[B.length];
		for (int i=0; i<b1.length; i++) {
			db[i] = (double) b1[i];
		}
		
		// We check that x0 = Binv*b is an integer vector (if not, there is no solution)
		double[] dblx0 = matrixVectMultiplication(Binv, db);
		long[] x0 = new long[dblx0.length];
		for (int i=0; i<x0.length; i++) {
			x0[i] = (long) Math.round(dblx0[i]);
		}
		
		long[] x0temp = matrixVectMultiplication(B, x0);
		if (!MatrixOperations.isEqual(x0temp, b1)) {return null;}
		
		// Construction of the huge matrix to get the solutions
		long[][] mat = new long[U[0].length][1+U[0].length-x0.length];
		for(int i=0; i<mat.length; i++) {
			if (i<x0.length) {
				mat[i][0]=x0[i];
			} else {
				mat[i][i-x0.length+1] = 1;
			}
		}
		
		// DEBUG
		// System.out.println("DEBUG: mat = \n" + toString(mat));
		// System.out.println("DEBUG: U = \n" + toString(U));
		
		// Computation of the matrix containing the solutions vectors
		long[][] sol_vectors = MatrixOperations.matrixMultiplication(U, mat);
		
		// Translation of this matrix into an ArrayList
		List<long[]> vectSol = new ArrayList<long[]>();
		for (int j=0; j<sol_vectors[0].length; j++) {
			long[] column = new long[sol_vectors.length];
			for (int i=0; i<sol_vectors.length; i++) {
				column[i] = sol_vectors[i][j];
			}
			
			// We simplify column by gcd(column)
			long gcd = gcdVect(column);
			if (gcd != 0) {
				for (int i=0; i<column.length; i++)
					column[i] = column[i]/gcd;
			}
			
			vectSol.add(column);
		}
		
		return vectSol;
	}
	
	
	/**
	 * Returns the intersection of two sets of basis vectors.
	 * The output contains basis vectors that are common to the two inputs.
	 * 
	 * The input basis vectors are assumed to be transposed.
	 * (Each row is a basis vector)
	 * 
	 * The output vectors are also transposed.
	 * 
	 * @param basisA
	 * @param basisB
	 * @return
	 */
	public static long[][] kernelIntersection(long [][] basisA, long[][] basisB) {
		if (basisA == null || basisB == null) return null;
		
		if (basisA.length == 0 || basisB.length == 0 || basisA[0].length != basisB[0].length) {
			throw new IllegalArgumentException();
		}

		long[][] kerBasisA = MatrixOperations.transpose(MatrixOperations.nullspace(basisA));
		long[][] kerBasisB = MatrixOperations.transpose(MatrixOperations.nullspace(basisB));
		
		long[][] combined = MatrixOperations.rowBind(kerBasisA, kerBasisB);
		
		return MatrixOperations.transpose(MatrixOperations.nullspace(combined));
	}
	
	/**
	 * Converts a 2D long array to {@link Matrix}.
	 * 
	 * @param array
	 * @param paramNames
	 * @param indexNames
	 * @return
	 */
	public static Matrix toMatrix(long[][] array, List<String> paramNames, List<String> indexNames) {
		return toMatrix(array, paramNames, indexNames, false, false);
	}

	
	/**
	 * Converts a 2D long array to {@link Matrix}.
	 * 
	 * @param array
	 * @param paramNames
	 * @param indexNames
	 * @return
	 */
	public static Matrix toMatrix(long[][] array, List<String> paramNames, List<String> indexNames, boolean linearPartOnly, boolean addImplicitParameterEqualities) {

		if ((array != null && array.length > 0 ) && 
				((linearPartOnly && paramNames.size() + indexNames.size() != array[0].length) || 
				(!linearPartOnly && paramNames.size() + indexNames.size() + 1 != array[0].length))) {
			throw new RuntimeException("Size of the matrix does not match the number of params/indices.\n" + paramNames + indexNames +"\n"+ MatrixOperations.toString(array));
		}
		
		final Matrix mat = MatrixUserFactory.createMatrix(paramNames, indexNames, linearPartOnly);
		
		if (addImplicitParameterEqualities) {
			for (int r = 0; r < paramNames.size(); r++) {
				long[] row = new long[mat.getNbColumns()];
				row[r] = 1;
				mat.getRows().add(MatrixUserFactory.createMatrixRow(row));
			}
		}
		
		if (array == null || array.length == 0) return mat;
		
		for (int r = 0; r < array.length; r++) {
			mat.getRows().add(MatrixUserFactory.createMatrixRow(array[r]));
		}
		
		return mat;
	}
	
	/**
	 * Creates an identity matrix. If the specified size is not square,
	 * the identity for the square region, padded with zeros are returned.
	 * 
	 * 
	 * @param nbRows
	 * @param nbCols
	 * @return
	 */
	public static long[][] createIdentity(int nbRows, int nbCols) {
		long[][] array = new long[nbRows][nbCols];

		int min = Math.min(nbRows, nbCols);
		
		for (int r = 0; r < min; r++) {
			array[r][r] = 1;
		}
		
		return array;
	}
	
	public static long[] bindVector(long[] vecA, long[] vecB) {
		long[] res = new long[vecA.length + vecB.length];
		for (int i = 0; i < vecA.length; i++) {
			res[i] = vecA[i];
		}
		for (int i = 0; i < vecB.length; i++) {
			res[i+vecA.length] = vecB[i];
		}
		
		return res;
	}
}
