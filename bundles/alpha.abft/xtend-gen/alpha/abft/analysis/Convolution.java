package alpha.abft.analysis;

import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.List;
import java.util.Map;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class Convolution {
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final ISLSet domain;

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final int radius;

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final Map<List<Integer>, Double> coeffs;

  public Convolution(final ISLSet domain, final int radius, final Map<List<Integer>, Double> coeffs) {
    this.domain = domain;
    this.radius = radius;
    this.coeffs = coeffs;
  }

  @Pure
  public ISLSet getDomain() {
    return this.domain;
  }

  @Pure
  public int getRadius() {
    return this.radius;
  }

  @Pure
  public Map<List<Integer>, Double> getCoeffs() {
    return this.coeffs;
  }
}
