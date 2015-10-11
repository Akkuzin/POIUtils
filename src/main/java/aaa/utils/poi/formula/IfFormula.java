package aaa.utils.poi.formula;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class IfFormula extends BaseFormula {

	protected static final String IF_FORMULA_NAME = "IF"; //$NON-NLS-1$
	protected static final String IF_FORMULA_DELIMITER = ","; //$NON-NLS-1$

	IBaseFormula condition;
	IBaseFormula valueIfTrue;
	IBaseFormula valueIfFalse;

	@Override
	public String formatAsString() {
		return IF_FORMULA_NAME
			+ surroundWithBrackets(Stream.of(	condition.formatAsString(),
												valueIfTrue.formatAsString(),
												valueIfFalse.formatAsString())
					.collect(joining(IF_FORMULA_DELIMITER)));
	}

}
