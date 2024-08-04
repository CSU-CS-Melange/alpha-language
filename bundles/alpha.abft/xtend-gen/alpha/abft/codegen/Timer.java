package alpha.abft.codegen;

import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class Timer {
  public static CharSequence generateTimer() {
    return Timer.generate();
  }

  public static CharSequence generate() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include<stdio.h>");
    _builder.newLine();
    _builder.append("#include<sys/time.h>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static double start, stop;        /* store the times locally */");
    _builder.newLine();
    _builder.append("static int start_flag, stop_flag; /* flag timer use */");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void initialize_timer()");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("start = 0.0;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("stop  = 0.0;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void reset_timer()");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("initialize_timer();");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void start_timer()");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("struct timeval time;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if (gettimeofday(&time, NULL) < 0)");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("perror(\"start_timer,gettimeofday\");");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("start = (((double) time.tv_sec) + ((double) time.tv_usec)/1000000);");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void stop_timer()");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("struct timeval time;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("if (gettimeofday(&time, NULL) < 0)");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("perror(\"stop_timer,gettimeofday\");");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("stop = (((double) time.tv_sec) + ((double) time.tv_usec)/1000000);");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("double elapsed_time()");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("return (stop-start);");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
