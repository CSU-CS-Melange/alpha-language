package alpha.codegen;

import alpha.model.memorymapper.IdentityMemoryMapper;
import alpha.model.memorymapper.MemoryMapper;
import alpha.model.tiler.Tiler;

/**
 * A class to store all the various codegen options.
 * This way, the signatures of the code generators do not need to change.
 */
@SuppressWarnings("all")
public class CodegenOptions {
  /**
   * The base data type of the program (typically float)
   */
  private BaseDataType valueType;

  /**
   * The tiler (optional)
   */
  private Tiler tiler = null;

  /**
   * The memory mapper object
   */
  private MemoryMapper mapper;

  /**
   * Whether to normalize the system before codegen
   */
  private boolean normalize = false;

  /**
   * Whether to add the inline keyword to the evaluate function
   */
  private boolean inlineFunction = false;

  /**
   * Whether to manually inline functions (replace the function call with the actual function body)
   */
  private boolean inlineCode = false;

  /**
   * Whether to accumulate points in a reduction according to a schedule as opposed to ad hoc reductions
   */
  private boolean scheduledReductions = false;

  /**
   * Whether to detect cycles in codegen. May or may not work with ScheduledC.
   */
  private boolean cycleDetection = false;

  /**
   * Whether to insert OpenMP parallel pragmas automatically.
   */
  private boolean ompPragmas = false;

  /**
   * Whether to allocate only the memory needed for variables, as opposed to allocating the bounding box.
   */
  private boolean polyhedralMemory = false;

  public CodegenOptions(final BaseDataType valueType) {
    this.valueType = valueType;
    IdentityMemoryMapper _identityMemoryMapper = new IdentityMemoryMapper();
    this.mapper = _identityMemoryMapper;
  }

  public static CodegenOptions create(final BaseDataType valueType) {
    return new CodegenOptions(valueType);
  }

  public CodegenOptions setTiler(final Tiler tiler) {
    CodegenOptions _xblockexpression = null;
    {
      this.tiler = tiler;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setMapper(final MemoryMapper mapper) {
    CodegenOptions _xblockexpression = null;
    {
      this.mapper = mapper;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setNormalize() {
    CodegenOptions _xblockexpression = null;
    {
      this.normalize = true;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setInlineFunction() {
    CodegenOptions _xblockexpression = null;
    {
      this.inlineFunction = true;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setInlineCode() {
    CodegenOptions _xblockexpression = null;
    {
      this.inlineCode = true;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setScheduledReductions() {
    CodegenOptions _xblockexpression = null;
    {
      this.scheduledReductions = true;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setCycleDetection() {
    CodegenOptions _xblockexpression = null;
    {
      this.cycleDetection = true;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setOmpPragmas() {
    CodegenOptions _xblockexpression = null;
    {
      this.ompPragmas = true;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setPolyhedralMemory() {
    CodegenOptions _xblockexpression = null;
    {
      this.polyhedralMemory = true;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setNormalize(final boolean b) {
    CodegenOptions _xblockexpression = null;
    {
      this.normalize = b;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setInlineFunction(final boolean b) {
    CodegenOptions _xblockexpression = null;
    {
      this.inlineFunction = b;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setInlineCode(final boolean b) {
    CodegenOptions _xblockexpression = null;
    {
      this.inlineCode = b;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setScheduledReductions(final boolean b) {
    CodegenOptions _xblockexpression = null;
    {
      this.scheduledReductions = b;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setCycleDetection(final boolean b) {
    CodegenOptions _xblockexpression = null;
    {
      this.cycleDetection = b;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setOmpPragmas(final boolean b) {
    CodegenOptions _xblockexpression = null;
    {
      this.ompPragmas = b;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public CodegenOptions setPolyhedralMemory(final boolean b) {
    CodegenOptions _xblockexpression = null;
    {
      this.polyhedralMemory = b;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public BaseDataType getValueType() {
    return this.valueType;
  }

  public Tiler getTiler() {
    return this.tiler;
  }

  public MemoryMapper getMapper() {
    return this.mapper;
  }

  public boolean getNormalize() {
    return this.normalize;
  }

  public boolean getInlineFunction() {
    return this.inlineFunction;
  }

  public boolean getInlineCode() {
    return this.inlineCode;
  }

  public boolean getScheduledReductions() {
    return this.scheduledReductions;
  }

  public boolean getCycleDetection() {
    return this.cycleDetection;
  }

  public boolean getOmpPragmas() {
    return this.ompPragmas;
  }

  public boolean getPolyhedralMemory() {
    return this.polyhedralMemory;
  }
}
