package org.kaivos.röda.commands;

import static org.kaivos.röda.RödaValue.STRING;

import java.io.File;
import java.util.Arrays;

import org.kaivos.röda.IOUtils;
import org.kaivos.röda.Interpreter;
import org.kaivos.röda.Interpreter.RödaScope;
import org.kaivos.röda.RödaValue;
import org.kaivos.röda.runtime.Function.Parameter;
import org.kaivos.röda.type.RödaNamespace;
import org.kaivos.röda.type.RödaNativeFunction;

public final class ImportPopulator {
	
	private ImportPopulator() {}

	public static void populateImport(Interpreter I, RödaScope S) {
		S.setLocal("import", RödaNativeFunction.of("import", (typeargs, args, kwargs, scope, in, out) -> {
			for (RödaValue value : args) {
				String filename = value.str();
				File file = IOUtils.getMaybeRelativeFile(I.currentDir, filename);
				I.loadFile(file, I.G);
			}
		}, Arrays.asList(new Parameter("files", false, STRING)), true));
		S.setLocal("localImport", RödaNativeFunction.of("localImport", (typeargs, args, kwargs, scope, in, out) -> {
			for (RödaValue value : args) {
				String filename = value.str();
				File file = IOUtils.getMaybeRelativeFile(I.currentDir, filename);
				I.loadFile(file, scope, true);
			}
		}, Arrays.asList(new Parameter("files", false, STRING)), true));
		S.setLocal("safeLocalImport", RödaNativeFunction.of("safeLocalImport", (typeargs, args, kwargs, scope, in, out) -> {
			for (RödaValue value : args) {
				String filename = value.str();
				File file = IOUtils.getMaybeRelativeFile(I.currentDir, filename);
				I.loadFile(file, scope, false);
			}
		}, Arrays.asList(new Parameter("files", false, STRING)), true));
		
		S.setLocal("importNamespace", RödaNativeFunction.of("importNamespace", (typeargs, args, kwargs, scope, in, out) -> {
			for (RödaValue value : args) {
				String filename = value.str();
				File file = IOUtils.getMaybeRelativeFile(I.currentDir, filename);
				RödaScope newScope = new RödaScope(I.G);
				I.loadFile(file, newScope);
				out.push(RödaNamespace.of(newScope));
			}
		}, Arrays.asList(new Parameter("files", false, STRING)), true));
		S.setLocal("localImportNamespace", RödaNativeFunction.of("localImportNamespace", (typeargs, args, kwargs, scope, in, out) -> {
			for (RödaValue value : args) {
				String filename = value.str();
				File file = IOUtils.getMaybeRelativeFile(I.currentDir, filename);
				RödaScope newScope = new RödaScope(scope);
				I.loadFile(file, newScope);
				out.push(RödaNamespace.of(newScope));
			}
		}, Arrays.asList(new Parameter("files", false, STRING)), true));
	}
}
